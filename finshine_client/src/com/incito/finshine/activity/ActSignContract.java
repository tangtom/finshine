package com.incito.finshine.activity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codans.blossom.datepicker.DlgDatePicker;
import com.custom.view.CommonDialog;
import com.custom.view.CommonDialog.BtnClickedListener;
import com.custom.view.DlgCommFilter;
import com.custom.view.DlgCommFilter.RefreshFilterListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.ContractInfoItem;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.MarketCsOrder;
import com.incito.finshine.entity.MarketStateReslut;
import com.incito.finshine.entity.OrderInfoItem;
import com.incito.finshine.entity.Product;
import com.incito.finshine.manager.BitmapManager;
import com.incito.finshine.manager.JsonParse;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FileSuccessReslut;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.LogCito;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.utility.MoneyFormat;
import com.incito.wisdomsdk.net.http.RequestParams;
import com.incito.wisdomsdk.utils.BitmapUtils;

/**
 * 
 * <dl>
 * <dt>ActSignContract.java</dt>
 * <dd>Description:客户营销——合同签订</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月10日 上午11:18:35</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class ActSignContract extends FragmentDetail implements OnClickListener {

	private Map<Integer, Integer> viewMap;
	private  static int currentView = 0;

	
	private LinearLayout stepLayout;
	private View view;
	private MarketCsOrder orderCs = null;
	String smsCode;
	String money;
	
	// 标记是否正面和反面都已经上传
	private boolean upLoadOpp =false;
	private boolean uploadDefensive =false;
	

	public static ActSignContract newInstance(int id, int position) {
		ActSignContract f = new ActSignContract();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);
		
		currentView = position;
		return f;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		view = inflater.inflate(R.layout.act_sign_contract, container, false);
		orderCs = ((ActCustomerMarketProgress)getActivity()).getMarketCs();
 
		initStep();
		initUI();
		LogCito.d("合同id："+contractId);
		return view;
	}
	
	@Override
	public void onStart() {
		
		queryMarketStatues();
		LogCito.d("ActSignContract onStart currentView ");
		super.onStart();
	}
	
 
 
		@Override
	   public void onResume() {
			super.onResume();
			
		}
	
	
	private boolean initStep() {

		viewMap = new HashMap<Integer, Integer>();
		viewMap.put(0, R.layout.act_sign_contract_first);
		viewMap.put(1, R.layout.act_sign_contract_second);
		viewMap.put(2, R.layout.act_sign_contract_third);
		stepLayout = (LinearLayout) view.findViewById(R.id.linearStep);
		stepLayout.addView((View) getActivity().getLayoutInflater().inflate(viewMap.get(currentView), null));
		// 上一步
		Button btnPre = (Button) view.findViewById(R.id.btnPre);
		btnPre.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentView--;
				if (currentView < 0) {
					 ActCustomerMarketProgress.currentView = ActCustomerMarketProgress.F_BIND;
					((ActCustomerMarketProgress) getActivity()).showDetails(ActCustomerMarketProgress.F_BIND, 1);
				} else {
					stepLayout.removeAllViews();
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 620);
					stepLayout.addView((View) getActivity().getLayoutInflater().inflate(viewMap.get(currentView), null),params);
					initUI();
				}
			}
		});

		// 下一步
		Button btnNext = (Button) view.findViewById(R.id.btnNext);
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				     if (!CanEdit) {
				    	 currentView ++; 
				    	 if (currentView > 2) {
							 // 点击事件的相应
							  currentView = 2;
							  ActCustomerMarketProgress.currentView = ActCustomerMarketProgress.F_ORDER_PAYMENT2;
							 ((ActCustomerMarketProgress) getActivity()).showDetails(ActCustomerMarketProgress.F_ORDER_PAYMENT2, 0);
							 
						    }else{
							 stepLayout.removeAllViews();
							 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 620);
							 stepLayout.addView((View) getActivity().getLayoutInflater().inflate(viewMap.get(currentView), null),params);
							 initUI();
						}
					     return;
				     }
					 if (!checkData()) {
						 return;
					 }
					 upLoadOpp = false;
				 	 uploadDefensive = false;
					 currentView ++; 
					 if (currentView > 2) {
						 // 点击事件的相应
						        currentView = 2;
							    CommonDialog internetSett = new CommonDialog(getActivity());
						        internetSett.setTitle(R.string.alert);
						        internetSett.setMessage("您确定与理财师"+orderCs.getSalesId()+"进行合同签订吗？");
						        internetSett.setPositiveButton(new BtnClickedListener() {

						            public void onBtnClicked() { 
						            	 //
						    			initHttpType(UPLOAD_ALL_CONTRACT);
						            }
						        }, R.string.btn_ok);
						        internetSett.setCancleButton(null, R.string.btn_cancle);
						        internetSett.showDialog();
						 
					    }else{
						 stepLayout.removeAllViews();
						 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 620);
						 stepLayout.addView((View) getActivity().getLayoutInflater().inflate(viewMap.get(currentView), null),params);
						 initUI();
					}
			}
		});
		return true;
	}

	private TextView csName;
	private EditText shareCount; // 份额
	private TextView buyMOney; // 认购金额
	private EditText bankName; // 银行名称
	private TextView bankCardNum;// 银行卡号
	private Button uploadOp;// 通用上传按钮正面
	private Button uploadDe;// 通用上传反面
	private ImageView opposiveIcon;// 通用显示正面
	private ImageView defensiveIcon;// 通用显示反面
	private TextView txtProductName;//产品名称
	private Button signContract21;// 签署合同21
	private Button signContract22;// 签署合同22
	private Button signContract31;// 签署合同31
	private EditText comeIntime3;// 入境时间
	private EditText phoneVerfication3;// 手机验证码
	private Button getSmsVerfication;// 获取短信验证码
	private TextView tvMoneyChinese;

	private void initUI() {

		LogCito.d("当前第几个view :"+currentView);
		View curView = view;
		uploadOp = (Button) curView.findViewById(R.id.btnUploadOP);// 通用上传按钮
		uploadDe = (Button) curView.findViewById(R.id.btnUploadDe);// 通用下载按钮
		opposiveIcon = (ImageView) curView.findViewById(R.id.ivOposive);// 通用显示正面
		defensiveIcon = (ImageView) curView.findViewById(R.id.ivdefensive);// 通用显示反面

		uploadOp.setOnClickListener(this);
		uploadDe.setOnClickListener(this);
		opposiveIcon.setOnClickListener(this);
		defensiveIcon.setOnClickListener(this);

		switch (currentView) {
		case CONTRACT_STEP1:
			csName = (TextView) curView.findViewById(R.id.tvCustomName);
			txtProductName = (TextView)curView.findViewById(R.id.txtProductName);
			shareCount = (EditText) curView.findViewById(R.id.etShareCount);
			buyMOney = (TextView) curView.findViewById(R.id.tvBuyMoney);
			bankCardNum = (EditText) curView.findViewById(R.id.bankCode);
			bankName = (EditText) curView.findViewById(R.id.bankName);
			tvMoneyChinese = (TextView) curView.findViewById(R.id.digitToChinese);
			bankName.setOnClickListener(this);
			csName.setText(orderCs.getCustomerName());
			txtProductName.setText("资产委托人认购" + orderCs.getProdName());
			shareCount.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					
					if (!TextUtils.isEmpty(s.toString())) {
						Product prod = orderCs.getProd();
						double money = 0.0d;
						Log.i("", "prod is null " + (prod == null));
						if (prod != null) {
							money = Long.parseLong(s.toString())*prod.getProdPrice();
							BigDecimal bd = new BigDecimal(money);
//							money =money*10000;
							if (money != 0) {
								tvMoneyChinese.setText(new MoneyFormat().format(bd.toPlainString() + "0000") + ")");
							}
//							万元(人民币 贰佰万元  元整 )
//							money =money/10000;
							buyMOney.setText(bd.toPlainString()+"万元(人民币");
						}
					}else{
						buyMOney.setText("0万元(人民币");
						tvMoneyChinese.setText("0元整)");
					}
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
				}
				
				@Override
				public void afterTextChanged(Editable s) {
				}
			});
			initData();
			break;
		case CONTRACT_STEP2:
			signContract21 = (Button) curView.findViewById(R.id.btnSignContract1);
			signContract22 = (Button) curView.findViewById(R.id.btnSignContract2);
			signContract21.setOnClickListener(this);
			signContract22.setOnClickListener(this);
			break;
		case CONTRACT_STEP3:
			comeIntime3 = (EditText) curView.findViewById(R.id.passporttime);// 入境时间
			signContract31 = (Button) curView.findViewById(R.id.btnSignContract3);
			phoneVerfication3 = (EditText) curView.findViewById(R.id.etSMScode);// 手机验证码
			getSmsVerfication = (Button) curView.findViewById(R.id.etSmsVerfication3); // 获取短信验证码
			signContract31.setOnClickListener(this);
			getSmsVerfication.setOnClickListener(this);
			comeIntime3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 弹出入境时间对话框进行修改还是显示
					showDatePickerDialog(R.string.select_time);
				}
			});
			if (orderCs.getCustomer() != null && orderCs.getCustomer().getPassportEntryTime() != 0) {
				comeIntime3.setText(DateUtil.formatDate2String(new Date(orderCs.getCustomer().getPassportEntryTime()), DateUtil.FORMAT_YYYY_MM_DD));
			}
			break;
		default:
			break;
		}
	}
	
	
	 public void showDatePickerDialog(int title) {
		DlgDatePicker picker = new DlgDatePicker(getActivity(), title, new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				comeIntime3.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
			}
		});
		picker.show();
		 
	}
	
	private void initData(){
		
		if (orderCs != null) {
			csName.setText(orderCs.getCustomerName());
			List<OrderInfoItem> orderlist = orderCs.getOrderlist();
			if (orderlist != null) {
				OrderInfoItem item = orderlist.get(0);
				shareCount.setText(item.getProductQuantity()+"");
				buyMOney.setText(item.getChangeAmount()+"");
				if (TextUtils.isEmpty(item.getBankCardNumber())) {
					bankCardNum.setText("");
				}else{
					bankCardNum.setText(orderlist.get(0).getBankCardNumber()+"");
				}
				buyMOney.setText(String.valueOf(orderlist.get(0).getTotalAmount())+"万元(人民币");
				try {
					 tvMoneyChinese.setText(new MoneyFormat().format(String.valueOf(orderlist.get(0).getTotalAmount())) + "元整");
				} catch (Exception e) {
					 
				}
				
			}else{
				bankCardNum.setText("");
				buyMOney.setText("0万元(人民币");
				tvMoneyChinese.setText("0元整)");
			}
			if (orderCs.getContractinfo() != null) {
				bankName.setText(getActivity().getResources().getStringArray(R.array.bank_num)[(int) orderCs.getContractinfo().getBankId()]);
			}
		}
	}

	/** 银行卡正面 **/
	private static final int UPLOAD_BANK_OPPOSIVE = 7;
	/** 银行卡反面 **/
	private static final int UPLOAD_BANK_DEFENSIVE = 8;
	/** 风险偏好合同 **/
	private static final int UPLOAD_RISK_PREFERENCE = 3;
	/** 资产托管合同 **/
	private static final int UPLOAD_PROFIT_PREFERENCE = 4;
	/** 产品申购合同 风险揭示照 **/
	private static final int UPLOAD_RISK_SHOW_ECPECTED = 5;
	/** 上传合同照 **/
	private static final int UPLOAD_CONTRACT_PHOTO = 6;
	
	private static final int UPLOAD_ALL_CONTRACT = 9;
	
	private static final int GET_SMS_VERFICATION = 2;// 获取验证码
	private static final int SIGN_CONTRACT = 10;
	
	private int currentType = 0;
			
	/** 合同步骤 **/
	private static final int CONTRACT_STEP1 = 0;
	private static final int CONTRACT_STEP2 = 1;
	private static final int CONTRACT_STEP3 = 2;

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.bankName:
			// 银行卡号
			DlgCommFilter dlg = new DlgCommFilter(getActivity(), R.array.bank_num,R.string.bank_title , "", true, 0);
			dlg.setListener(new RefreshFilterListener() {
				
				@Override
				public void doRefresh(String reslut, int position,boolean b,int title) {
					
					bankType = position + 1;
					bankName.setText(getActivity().getResources().getStringArray(R.array.bank_num)[position]);
				}
			});
			dlg.setHiddenInput(true);
			dlg.showDialog();
			break;
		case R.id.btnSignContract3:
			//  一次性上传合同
//			smsCode = phoneVerfication3.getText().toString();
//			if (TextUtils.isEmpty(smsCode)) {
//				CommonUtil.showToast("请先获取短信验证码", getActivity());
//				return;
//			}
//			initHttpType(UPLOAD_ALL_CONTRACT);
			break;
		case R.id.etSmsVerfication3:
			initHttpType(GET_SMS_VERFICATION);
		    break;
		case R.id.ivOposive:
			switch (currentView) {
			case CONTRACT_STEP1:
				seleIcon(UPLOAD_BANK_OPPOSIVE);
				break;
			case CONTRACT_STEP2:
				seleIcon(UPLOAD_RISK_PREFERENCE);
				break;
			case CONTRACT_STEP3:
				seleIcon(UPLOAD_RISK_SHOW_ECPECTED);
				break;
			default:
				break;
			}
			break;
		case R.id.ivdefensive:
			switch (currentView) {
			case CONTRACT_STEP1:
				seleIcon(UPLOAD_BANK_DEFENSIVE);
				break;
			case CONTRACT_STEP2:
				seleIcon(UPLOAD_PROFIT_PREFERENCE);
				break;
			case CONTRACT_STEP3:
				seleIcon(UPLOAD_CONTRACT_PHOTO);
				break;
			default:
				break;
			}
			break;
		default:
			CommonUtil.showToast("亲，稍等一起上传，好吧！", getActivity());
			break;
		}
	 }
	
	 MarketStateReslut marketReslut;
	 HttpUtils  httpUtils = null;
	 
     private void initHttpType(final int type){
		
		try {
			JSONObject params = new JSONObject();
			RequestParams  reqParams = new RequestParams();
			switch (type) {
			case UPLOAD_ALL_CONTRACT:
				//上传合同签名附件
				LogCito.d("上传合同附件的ID:"+contractId);
				httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_CONTRACT_SIGN_FILE, RequestType.POST, null);
				reqParams.put("contractId", contractId+"");
				reqParams.put("bankCardFrontalFile",  CommonUtil.getFile(Constant.BANKCARD, Constant.UP_BANK_OPP+orderCs.getCustomerId()+".jpg"));
				reqParams.put("bankCardOppositeFile",  CommonUtil.getFile(Constant.BANKCARD, Constant.UP_BANK_DEF+orderCs.getCustomerId()+".jpg"));
				reqParams.put("riskContractSignedFile", CommonUtil.getFile(Constant.RISK_PREF, Constant.UP_RISK+orderCs.getCustomerId()+".jpg"));
				reqParams.put("assetContractSignedFile",CommonUtil.getFile(Constant.ASSET_CONTRACT, Constant.UP_ASSET+orderCs.getCustomerId()+".jpg"));
				reqParams.put("productContractSignedFile",CommonUtil.getFile(Constant.CONTRACT, Constant.UP_CONTRACT+orderCs.getCustomerId()+".jpg"));
				httpUtils.setRequestParms(reqParams);
				httpUtils.setUploadFile(true);
				break;
			case GET_SMS_VERFICATION:
				if (TextUtils.isEmpty(orderCs.getCustomer().getCellPhone1())) {
//					CommonUtil.showToast("请输入手机号码", getActivity());
					queryCsInfo();
					return;
				}
				params.put("phoneNo", orderCs.getCustomer().getCellPhone1());
				params.put("tranFk",  contractId);
				params.put("tranTypeFk", Constant.CON_TRACT_SIGN_TRAN);
				httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_SMS_VERFICATION, RequestType.POST, params);
				break;
			case SIGN_CONTRACT:
				if (contractId <= 0 || saleSorderId <= 0) {
					CommonUtil.showToast("合同尚未创建成功", getActivity());
					return;
				}
				prodCount = Long.parseLong(shareCount.getText().toString());
	    		bankCard = bankCardNum.getText().toString();
	    		bankType = getBankID(bankName.getText().toString().toString());
				params.put("id",contractId);
				params.put("productQuantity", prodCount+"");
				params.put("totalAmount", money);
				params.put("bankId", bankType);
				params.put("customerId", orderCs.getCustomerId());
				params.put("bankCardNumber", bankCard);
				params.put("verificationCode", smsCode);
				params.put("recordId",orderCs.getId());
				params.put("orderId",saleSorderId);
				httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_SIGN_CONTRACT, RequestType.POST, params);
				break;
			default:
				break;
			}
	
			httpUtils.setSuccessListener(new SuccessReslut() {
				
				@Override
				public void getResluts(JSONObject response) {
					if (TextUtils.isEmpty(response.toString())) {
						return;
					}
					if (response.optString("status").equals(Request.RESLUT_OK)) {
					 // 上传合同签名附件成功
					 switch (type) {
					 case UPLOAD_ALL_CONTRACT:
						CommonUtil.showToast("上传合同附件成功", getActivity());
						marketReslut = JsonParse.getMarketStepResluts(response);
						if (orderCs != null) {
							 orderCs.setMarketReslut(marketReslut);
						}
						initHttpType(SIGN_CONTRACT);
						break;
					 case GET_SMS_VERFICATION:
						 CommonUtil.showToast("获取短信验证码成功,请您注意查收", getActivity());
					    break;
					 case SIGN_CONTRACT:
						 marketReslut = JsonParse.getMarketStepResluts(response);
						 if (orderCs != null) {
							 orderCs.setMarketReslut(marketReslut);
						 }
						 ActCustomerMarketProgress.marketStates = ActCustomerMarketProgress.F_ORDER_PAYMENT2;
						 ActCustomerMarketProgress.currentView = ActCustomerMarketProgress.F_ORDER_PAYMENT2;
						((ActCustomerMarketProgress) getActivity()).showDetails(ActCustomerMarketProgress.F_ORDER_PAYMENT2, 0);
						 CommonUtil.showToast("签订合同成功", getActivity());
						 break;
					default:
						break;
					  }
				   }
				}
			});
			httpUtils.executeRequest();
			
		}  catch (Exception e) {
	  }
	}
 
     
     private void seleIcon(final int type){
    	 
    	 currentType = type;
    	 
    	 DlgCommFilter dlgCommFilter = new DlgCommFilter(getActivity(), R.array.select_icon, R.string.ablum_selec, "拍照", true, 0);
         dlgCommFilter.setListener(new RefreshFilterListener() {
			
			@Override
			public void doRefresh(String reslut, int position,boolean b,int title) {
				 if (position == 0) {
					 // 拍照
					 Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
					 switch (type) {
					  case UPLOAD_BANK_OPPOSIVE:
						  albumTakePhotoFile = CommonUtil.getFile(Constant.BANKCARD, Constant.UP_BANK_OPP+orderCs.getCustomerId()+".jpg");
						break;
					  case UPLOAD_BANK_DEFENSIVE:
						  albumTakePhotoFile = CommonUtil.getFile(Constant.BANKCARD, Constant.UP_BANK_DEF+orderCs.getCustomerId()+".jpg");
							break;
					  case UPLOAD_RISK_PREFERENCE:
						  albumTakePhotoFile = CommonUtil.getFile(Constant.RISK_PREF, Constant.UP_RISK+orderCs.getCustomerId()+".jpg");
							break;
					  case UPLOAD_PROFIT_PREFERENCE:
						  albumTakePhotoFile = CommonUtil.getFile(Constant.ASSET_CONTRACT, Constant.UP_ASSET+orderCs.getCustomerId()+".jpg");
							break;
					  case UPLOAD_CONTRACT_PHOTO:
						   // 合同照
						   albumTakePhotoFile = CommonUtil.getFile(Constant.CONTRACT, Constant.UP_CONTRACT+orderCs.getCustomerId()+".jpg");
							break;
					  case UPLOAD_RISK_SHOW_ECPECTED:
						   // 上传风险揭示照片
						  albumTakePhotoFile = CommonUtil.getFile(Constant.RISK_SHOW, Constant.UP_RISK+orderCs.getCustomerId()+".jpg");
						   break;
					default:
						break;
					}
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(albumTakePhotoFile));
					startActivityForResult(intent, Constant.REQUEST_TAKE_PHOTO);
				 }else{
					// 从相册选择
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/*");
					startActivityForResult(intent, Constant.REQUEST_ALBUM_PHOTO_RESLUT);
				}
			}
		  });
         dlgCommFilter.setHiddenInput(true);
         dlgCommFilter.showDialog();
     }
     
     private void showIcon(Intent data,Bitmap bitmap){
    	 
    	 bitmap =  CommonUtil.saveLocalFile(data, bitmap);
    	  switch (currentType) {
		  case UPLOAD_BANK_OPPOSIVE:
			   CommonUtil.storeFile(BitmapUtils.bmpToByteArray(CommonUtil.saveLocalFile(data, bitmap), false), Constant.BANKCARD, Constant.UP_BANK_OPP+orderCs.getCustomerId()+".jpg");
			   opposiveIcon.setImageBitmap(bitmap);
			   upLoadOpp = true;
			  break;
		  case UPLOAD_RISK_PREFERENCE:
			   CommonUtil.storeFile(BitmapUtils.bmpToByteArray(CommonUtil.saveLocalFile(data, bitmap), false), Constant.RISK_PREF, Constant.UP_RISK+orderCs.getCustomerId()+".jpg");
			   opposiveIcon.setImageBitmap(bitmap);
			   upLoadOpp = true;
			  break;
		  case UPLOAD_RISK_SHOW_ECPECTED:
			   CommonUtil.storeFile(BitmapUtils.bmpToByteArray(CommonUtil.saveLocalFile(data, bitmap), false), Constant.RISK_SHOW, Constant.UP_RISK+orderCs.getCustomerId()+".jpg");
			   opposiveIcon.setImageBitmap(bitmap);
			   upLoadOpp = true;
			   break;
		  case UPLOAD_BANK_DEFENSIVE:
			  CommonUtil.storeFile(BitmapUtils.bmpToByteArray(CommonUtil.saveLocalFile(data, bitmap), false), Constant.BANKCARD, Constant.UP_BANK_DEF+orderCs.getCustomerId()+".jpg");
			  defensiveIcon.setImageBitmap(bitmap);
			  uploadDefensive = true;
			  break;
		  case UPLOAD_PROFIT_PREFERENCE:
			  CommonUtil.storeFile(BitmapUtils.bmpToByteArray(CommonUtil.saveLocalFile(data, bitmap), false),Constant.ASSET_CONTRACT,Constant.UP_ASSET+orderCs.getCustomerId()+".jpg");
			  defensiveIcon.setImageBitmap(bitmap);
			  uploadDefensive = true;
			  break;
		  case UPLOAD_CONTRACT_PHOTO:
			  CommonUtil.storeFile(BitmapUtils.bmpToByteArray(CommonUtil.saveLocalFile(data, bitmap), false),Constant.CONTRACT, Constant.UP_CONTRACT+orderCs.getCustomerId()+".jpg");
			  defensiveIcon.setImageBitmap(bitmap);
			  uploadDefensive = true;
			  break;
    	 }
     }
     private File albumTakePhotoFile;
     
     @SuppressWarnings("static-access")
 	@Override
 	public void onActivityResult(int requestCode, int resultCode, Intent data) {
 		super.onActivityResult(requestCode, resultCode, data);
 		if (resultCode ==  getActivity().RESULT_OK) {
 			try {
 				Bitmap selectImage = null;
 				if (requestCode == Constant.REQUEST_ALBUM_PHOTO_RESLUT) {
 					String file = data.getDataString();
 					if (!TextUtils.isEmpty(file) && file.startsWith("file:///")) {
 						if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
 							file = file.replace("file://","");
 							 try {
 								 file = URLDecoder.decode(file, "UTF-8");
 							} catch (UnsupportedEncodingException e) {
 								e.printStackTrace();
 							}
 						}
 						//doCropPhoto(new File(file));
 						Uri uri = Uri.parse(file);
 						Intent intent = new Intent();
 						intent.setDataAndType(uri, "image/*");
 						showIcon(intent, selectImage);
 					}else{
 						Uri url  = data.getData();
 						try {
 							Intent intent = new Intent();
 	 						intent.setDataAndType(url, "image/*");
 	 						showIcon(intent, selectImage);
 						  } catch (Exception e) {
 						}
 					}
 				} else if (requestCode == Constant.REQUEST_TAKE_PHOTO) {
 					
 					//doCropPhoto(albumTakePhotoFile);
 					Uri uri = Uri.parse(albumTakePhotoFile.getPath());
					Intent intent = new Intent();
					intent.setDataAndType(uri, "image/*");
					showIcon(intent, selectImage);
 					
 				} else if (requestCode == Constant.PHOTO_PICKED_WITH_DATA) {
 					showIcon(data, selectImage);
 				}
 			} catch (Exception e) {
 				e.getLocalizedMessage();
 			}
 		}
 	}
   
 	protected void doCropPhoto(File f) {
 		try {
 			Intent intent = getCropImageIntent(Uri.fromFile(f));
 			startActivityForResult(intent, Constant.PHOTO_PICKED_WITH_DATA);
 		} catch (Exception e) {
 			Toast.makeText(getActivity(), "裁剪图片异常", Toast.LENGTH_SHORT).show();
 		}
 	}

 	private Intent getCropImageIntent(Uri photoUri) {
 		Intent intent = null;
 		intent = new Intent("com.android.camera.action.CROP");
 		intent.setDataAndType(photoUri, "image/*");
 		intent.putExtra("crop", "true");
 		intent.putExtra("aspectX", 1);
 		intent.putExtra("aspectY", 1);
 		intent.putExtra("outputX", 300);
 		intent.putExtra("outputY", 300);
 		intent.putExtra("return-data", true);
 		return intent;
 	}
 	
	
	private void downIcon() {
		
		// 查询合同信息
		if (orderCs == null) {
			return;
		}
		ContractInfoItem item = orderCs.getContractinfo();
		if (item != null) {
			switch (currentView) {
			case CONTRACT_STEP1:
				if (item.getBankCardFrontalId() > 0) {
					downLoadIconByOpposive(item.getBankCardFrontalId());
				}
				if (item.getBankCardOppositeId() > 0) {
					downLoadIconByDefensive(item.getBankCardOppositeId());
				}
				break;
			case CONTRACT_STEP2:
				if (item.getAssetContractSignedId() > 0) {
					downLoadIconByDefensive(item.getAssetContractSignedId());
				}
				if (item.getRiskContractSignedId() > 0) {
					downLoadIconByOpposive(item.getRiskContractSignedId());
				}
				break;
			case CONTRACT_STEP3:

				if (item.getProductContractSignedId() > 0) {
					downLoadIconByDefensive(item.getProductContractSignedId());
				}
				break;
			default:
				break;
			}
		}else{
			showdefaultIcon();
		}
	}
 	
   private void downLoadIconByOpposive(long fileId){
		
	   LogCito.d("下载合同的文件id= "+fileId);
	   
		JSONObject params = new JSONObject();
		try {
			params.put("attachmentId",fileId);
		} catch (Exception e) {
		}
		HttpUtils	httpUtils =  new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_DOWNLOAD_FILE, RequestType.POST, params);
		httpUtils.setShowDiloag(false);
		httpUtils.setDownLoadFile(true);
		httpUtils.setFileSuccessListener(new FileSuccessReslut() {
			
			@Override
			public void getResluts(int responeCode,byte[] binaryData) {
				
				if (responeCode == 200 && binaryData != null) {
					
					String file = "";
					String fileName = "";
					switch (currentView) {
					case CONTRACT_STEP1:
						file = Constant.BANKCARD;
						fileName = Constant.DOWN_BANK_OPP+orderCs.getCustomerId()+".jpg";
						break;
					case CONTRACT_STEP2:
						file = Constant.RISK_PREF;
						fileName = Constant.DOWN_RISK + orderCs.getCustomerId()+".jpg";
						break;
					case CONTRACT_STEP3:
						file  =  Constant.RISK_SHOW;
						fileName = Constant.DOWN_RISK_SHOW + orderCs.getCustomerId()+".jpg";
						break;
					default:
						break;
					}
					// 下载 正面
					CommonUtil.storeFile(binaryData,file,fileName);
					Bitmap bitMap1 = BitmapManager.decodeSampledBitmapFromFile(file, Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
					opposiveIcon.setImageBitmap(bitMap1);
				}
			}
		});
		httpUtils.executeRequest();
	}
   
    private void downLoadIconByDefensive(long fileID){
		
    	 LogCito.d("下载合同的文件id= "+fileID);
		JSONObject params = new JSONObject();
		try {
			params.put("attachmentId",fileID);
		} catch (Exception e) {
		}
		HttpUtils	httpUtils =  new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_DOWNLOAD_FILE, RequestType.POST, params);
		httpUtils.setShowDiloag(false);
		httpUtils.setDownLoadFile(true);
		httpUtils.setFileSuccessListener(new FileSuccessReslut() {
			
			@Override
			public void getResluts(int responeCode,byte[] binaryData) {
				
				String file = "";
				String fileName = "";
				switch (currentView) {
				case CONTRACT_STEP1:
					file = Constant.BANKCARD;
					fileName = Constant.DOWN_BANK_OPP+orderCs.getCustomerId()+".jpg";
					break;
				case CONTRACT_STEP2:
					file = Constant.RISK_PREF;
					fileName = Constant.DOWN_ASSET + orderCs.getCustomerId()+".jpg";
					break;
				case CONTRACT_STEP3:
					file  =  Constant.RISK_SHOW;
					fileName = Constant.DOWN_CONTRACT + orderCs.getCustomerId()+".jpg";
					break;
				default:
					break;
				}
				// 下载 正面
				CommonUtil.storeFile(binaryData,file,fileName);
				Bitmap bitMap1 = BitmapManager.decodeSampledBitmapFromFile(file, Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
				defensiveIcon.setImageBitmap(bitMap1);
				}
		});
		httpUtils.executeRequest();
	}
	
	private long prodCount = 0;//产品数量
	private int bankType = -1;// 银行类型
	private String bankCard ="";// 银行卡号
    private boolean  checkData(){
    	
    	if (currentView == CONTRACT_STEP1) {
    		// 证件照上传下载问题
    		if (TextUtils.isEmpty(shareCount.getText().toString()) || Long.parseLong(shareCount.getText().toString()) == 0) {
    			CommonUtil.showToast("请输入预约产品的数量", getActivity());
    			return false;
    		}else if (TextUtils.isEmpty(bankName.getText().toString())) {
    			CommonUtil.showToast("请选择支付银行的类型", getActivity());
    			return false;
    		}else if (TextUtils.isEmpty(bankCardNum.getText().toString())) {
    			CommonUtil.showToast("请输入银行卡号", getActivity());
    			return false;
    		}else if (!upLoadOpp) {
    			CommonUtil.showToast("请上传银行卡正面照", getActivity());
    			return false;
			}else if (!uploadDefensive) {
    			CommonUtil.showToast("请上传银行卡反面照", getActivity());
    			return false;
			}
    		
    		prodCount = Long.parseLong(shareCount.getText().toString());
    		money=  prodCount*(orderCs.getProd().getProdPrice())+"";
    		bankCard = bankCardNum.getText().toString();
    		bankType = getBankID(bankName.getText().toString().toString());
    		return true;
		}else if (currentView == CONTRACT_STEP2) {
			 if (!upLoadOpp) {
	    			CommonUtil.showToast("请上传风险偏好照片", getActivity());
	    			return false;
				}else if (!uploadDefensive) {
	    			CommonUtil.showToast("请上传资产托管照片", getActivity());
	    			return false;
				}
				return true;
			}else if (currentView == CONTRACT_STEP3) {
				 if (!upLoadOpp) {
		    			CommonUtil.showToast("请上传产品申购合同照片", getActivity());
		    			return false;
					}else if (!uploadDefensive) {
		    			CommonUtil.showToast("请上传合同照", getActivity());
		    			return false;
					}else {
						smsCode = phoneVerfication3.getText().toString();
		    			if (TextUtils.isEmpty(smsCode)) {
		    				CommonUtil.showToast("请先获取短信验证码", getActivity());
		    				return false;
		    			}
					}
					return true;
			}
    	  return false;
    }
    
    private  int getBankID(String bankName){
    	String [] id =   getActivity().getResources().getStringArray(R.array.bank_num);//[(int) orderCs.getContractinfo().getBankId()];
    	for (int i = 0; i < id.length; i++) {
			if (bankName.equals(id[i])) {
				return i;
			}
		}
    	return 0;
    }
    
    private long contractId = 0;
    private void queryContractInfo() {
		
		JSONObject params = new JSONObject();
		try {
			LogCito.d("SIGN :查询合同信息");
			params.put("id", orderCs.getContractId());
		} catch (JSONException e) {

			e.printStackTrace();
			return;
		}
		HttpUtils httpUtils = new HttpUtils(getActivity(),RequestDefine.MARKET_RQ_QUERY_CONTRACT_INFO, RequestType.POST,params);
		httpUtils.setSuccessListener(new SuccessReslut() {
			
			@Override
			public void getResluts(JSONObject response) {
				
				if (Request.RESLUT_OK.equals(response.optString("status"))) {
					Gson gson = new Gson();
					ContractInfoItem contractInfo;
					try {
						contractInfo = gson.fromJson(response.getJSONObject("item").toString(), new TypeToken<ContractInfoItem>() {}.getType());
						 if (orderCs != null) {
							 orderCs.setMarketingStatusId(contractInfo.getId());
							 orderCs.setContractinfo(contractInfo);
							 downIcon();
						 }
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				   
				}
			}
		});
		httpUtils.executeRequest();
	}
    
    
   private long saleSorderId = 0l;
    
   private void queryMarketStatues(){
		
		LogCito.d("ACTBIND 营销状态查询：");
		JSONObject params = new JSONObject();
		HttpUtils httpUtils = null;
	    // 查询营销记录 ,获取营销状态
		try {
			params.put("id", orderCs.getId());
			params.put("customerId",orderCs.getCustomerId());
			params.put("prodId",orderCs.getProdId());
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_QUERY_CSINFO, RequestType.POST, params);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		httpUtils.setSuccessListener(new SuccessReslut() {
			
			@Override
			public void getResluts(JSONObject response) {
				if (Request.RESLUT_OK.equals(response.optString("status"))) {
					try {
						List<MarketStateReslut>  market = new Gson().fromJson(response.getJSONArray("list").toString(),new TypeToken<List<MarketStateReslut>>() {}.getType());
						if (market != null) {
							long marketStatues = market.get(0).getMarketingStatusId();
							if (marketStatues - 1 > ActCustomerMarketProgress.F_SIGN_FIRST) {//更改
								CannotEdit();
								CanEdit = false;
							}
							LogCito.d("营销状态查询成功 signContract：marketStatues"+marketStatues);
							contractId = market.get(0).getContractId();
							LogCito.d("营销状态查询成功合同id: signContract：contractId:"+contractId);
							saleSorderId = market.get(0).getSalesOrderId();
							LogCito.d("营销状态查询成功订单ID: saleSorderId:"+saleSorderId);
 
							LogCito.d("营销状态查询成功 signContract：contractId:"+contractId);
							
							saleSorderId = market.get(0).getSalesOrderId();
							
 
							queryContractInfo();
						}
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		httpUtils.executeRequest();
		
	}
     
    
 
 	// 显示本地的，然后网络加载，加载成功后覆盖本地文件
	private void showdefaultIcon() {
		
		if (orderCs.getCustomerId() <= 0) {
			return;
		}
		switch (currentView) {
		case CONTRACT_STEP1:
			if (CommonUtil.isNotEmptyfile(Constant.BANKCARD + Constant.DOWN_BANK_OPP + orderCs.getCustomerId() + ".jpg")) {
				// 优先显示下载过的头像
				opposiveIcon.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.BANKCARD + Constant.DOWN_BANK_OPP + orderCs.getCustomerId() + ".jpg",Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			} 
			else if (CommonUtil.isNotEmptyfile(Constant.BANKCARD + Constant.UP_BANK_OPP + orderCs.getCustomerId() + ".jpg")) {
				// 否则显示上次选择的照片
				opposiveIcon.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.BANKCARD + Constant.UP_BANK_OPP + orderCs.getCustomerId() + ".jpg",Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			}
			
			if (CommonUtil.isNotEmptyfile(Constant.BANKCARD + Constant.UP_BANK_DEF + orderCs.getCustomerId() + ".jpg")) {
				// 优先显示下载过的头像
				defensiveIcon.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.BANKCARD + Constant.UP_BANK_DEF + orderCs.getCustomerId() + ".jpg",Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			
			} else if (CommonUtil.isNotEmptyfile(Constant.BANKCARD + Constant.UP_BANK_DEF + orderCs.getCustomerId() + ".jpg")) {
				// 否则显示上次选择的照片
				defensiveIcon.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.BANKCARD + Constant.UP_BANK_DEF + orderCs.getCustomerId() + ".jpg",Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			}
			break;
		case CONTRACT_STEP2:
			
			if (CommonUtil.isNotEmptyfile(Constant.RISK_PREF+Constant.DOWN_RISK+orderCs.getCustomerId()+".jpg")) {
				// 优先显示下载过的头像
				opposiveIcon.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.RISK_PREF+Constant.DOWN_RISK+orderCs.getCustomerId()+".jpg",Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			
			} else if (CommonUtil.isNotEmptyfile(Constant.RISK_PREF + Constant.UP_RISK + orderCs.getCustomerId() + ".jpg")) {
				// 否则显示上次选择的照片
				opposiveIcon.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.RISK_PREF + Constant.UP_RISK + orderCs.getCustomerId() + ".jpg",Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			}
			
			if (CommonUtil.isNotEmptyfile(Constant.ASSET_CONTRACT + Constant.DOWN_ASSET+orderCs.getCustomerId()+".jpg")) {
				// 否则显示上次选择的照片
				defensiveIcon.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.ASSET_CONTRACT + Constant.DOWN_ASSET + orderCs.getCustomerId() + ".jpg",Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			}else if (CommonUtil.isNotEmptyfile(Constant.ASSET_CONTRACT + Constant.UP_ASSET+orderCs.getCustomerId()+".jpg")) {
				// 优先显示下载过的头像
				defensiveIcon.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.ASSET_CONTRACT + Constant.UP_ASSET+orderCs.getCustomerId()+".jpg",Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			} 
			break;
		case CONTRACT_STEP3:
			if (CommonUtil.isNotEmptyfile(Constant.CONTRACT+ Constant.DOWN_CONTRACT+orderCs.getCustomerId()+".jpg")) {
				// 优先显示下载过的头像
				opposiveIcon.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.CONTRACT+ Constant.DOWN_CONTRACT+orderCs.getCustomerId()+".jpg",Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			} else if (CommonUtil.isNotEmptyfile(Constant.CONTRACT+ Constant.UP_CONTRACT+orderCs.getCustomerId()+".jpg")) {
				// 否则显示上次选择的照片
				opposiveIcon.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.CONTRACT+ Constant.UP_CONTRACT+orderCs.getCustomerId()+".jpg",Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			}
			
			if (CommonUtil.isNotEmptyfile(Constant.RISK_SHOW + Constant.DOWN_RISK+orderCs.getCustomerId()+".jpg")) {
				// 优先显示下载过的头像
				defensiveIcon.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.RISK_SHOW + Constant.DOWN_RISK+orderCs.getCustomerId()+".jpg",Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			} else if (CommonUtil.isNotEmptyfile(Constant.RISK_SHOW + Constant.UP_RISK+orderCs.getCustomerId()+".jpg")) {
				// 否则显示上次选择的照片
				defensiveIcon.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.RISK_SHOW + Constant.UP_RISK+orderCs.getCustomerId()+".jpg",Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			}
			break;

		default:
			break;
		}
	}
	
	boolean CanEdit = true;
	private void CannotEdit(){
			 // 不可以编辑
			uploadOp.setEnabled(false);
			uploadDe.setEnabled(false);
			opposiveIcon.setEnabled(false);
			defensiveIcon.setEnabled(false);
			switch (currentView) {
			case CONTRACT_STEP1:
				bankCardNum.setEnabled(false);
				bankName.setEnabled(false);
				bankName.setEnabled(false);
				shareCount.setEnabled(false);
				break;
			case CONTRACT_STEP2:
				signContract21.setEnabled(false);
				signContract22.setEnabled(false);
				break;
			case CONTRACT_STEP3:
				comeIntime3.setEnabled(false);// 入境时间
				signContract31.setEnabled(false);
				phoneVerfication3.setEnabled(false);
				getSmsVerfication.setEnabled(false);
				break;
			default:
				break;
			}
	}
	
	
	private void queryCsInfo(){
		
		 HttpUtils httpUtils = new HttpUtils(getActivity(),RequestDefine.RQ_CUSTOMER_GET_SINGLE, RequestType.GET, null);
		 httpUtils.setShowDiloag(false);
		 httpUtils.setCustomerId(orderCs.getCustomerId());
		 httpUtils.setSuccessListener(new SuccessReslut() {
			
			@Override
			public void getResluts(JSONObject response) {
				try {
					Customer cs  = new Gson().fromJson(response.toString(), new TypeToken<Customer>() {}.getType());
					orderCs.setCustomer(cs);
					initHttpType(GET_SMS_VERFICATION);
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}  
				
			}
		});
	}
}
