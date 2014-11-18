package com.incito.finshine.activity;

import java.io.FileNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.MarketCsOrder;
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
import com.incito.wisdomsdk.net.http.RequestParams;

/**
 * 
 * <dl>
 * <dt>FragmentBindFirst.java</dt>
 * <dd>Description:客户营销 绑定第二步 Fragment</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月16日 下午5:00:59</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class FragmentBindSecond extends FragmentDetail implements OnClickListener {

	private View view;
	private ActBind actBind;
	
	public static FragmentBindSecond newInstance(int id) {

		FragmentBindSecond f = new FragmentBindSecond();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);

		return f;
	}
	
 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		view = inflater.inflate(R.layout.frag_customer_market_bind_second,container, false);
		actBind = ActBind.instanceA;
		
		marketCs = ((ActCustomerMarketProgress) getActivity()).getMarketCs();
		bindSignFile = Constant.SIGN_PROTECOL_FILE_UP + marketCs.getCustomerId()+".jpg";
		initUI();
		initHttpUtils(QUERY_BIND_PROTECAL);
		cs = marketCs.getCustomer();
		if (cs != null) {
			initData(true);
		}else{
			initHttpUtils(QUERY_CS_INFO);// 拿到手机号
		}
		
		return view;
	}

	@Override
	public void onStart() { 
		super.onStart();
	}
	
	// 判断该界面是可以编辑还是只是显示界面 true 是可以编辑，否则不可以编辑
	public boolean CanEdit(){
		LogCito.d("可以编辑协议FragmentSecondFirst"+((ActCustomerMarketProgress)getActivity()).canEdit(ActCustomerMarketProgress.F_BIND));
		return ((ActCustomerMarketProgress)getActivity()).canEdit(ActCustomerMarketProgress.F_BIND);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!CanEdit()) {
			 // 不可以编辑
			btnGetSms.setEnabled(false);
			smsVerfication.setEnabled(false);
			editBind.setEnabled(false);
			etPhone.setEnabled(false);
		}
		
		if (CommonUtil.isNotEmptyfile(Constant.SIGN_DIC_BIND + bindSignFile)) {
			UPLOAD_SIGN_FILE = true; 
			editBind.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.SIGN_DIC_BIND+ bindSignFile, Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT));
		}else {
			UPLOAD_SIGN_FILE = false;
		}
	}
	
	private void initUI() {

		
		btnGetSms = (Button) view.findViewById(R.id.btnGetVerfication);// 按钮
		smsVerfication = (EditText) view.findViewById(R.id.etSmsVaerfication);
		btnGetSms.setOnClickListener(this);
		// 绑定 签署协议按钮
		editBind = (ImageView) view.findViewById(R.id.editBind);
		editBind.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 绑定 PDF文件，
				Intent i = new Intent();
				i.setClass(getActivity(), ActContractDetail.class);
				i.putExtra(ActContractDetail.KEY_CUSTOMER_ID, marketCs.getCustomerId());
				getActivity().startActivity(i);
			}
		});
	}

	private String bindSignFile = null;
	private boolean UPLOAD_SIGN_FILE = false;
	private Customer cs = null;
	private ImageView editBind;
	private MarketCsOrder marketCs;
	private EditText etPhone;
	private Button btnGetSms;
	private EditText smsVerfication;

	public  static final int QUERY_BIND_PROTECAL = 1;// 查询绑定协议接口，查询绑定协议ID
	private static final int GET_SMS_VERFICATION = 2;// 获取验证码

	public static final int UPLOAD_BIND_PROTECAL_FILE = 3;// 查询绑定协议接口，查询绑定协议ID
	private static final int BIND_PROTECOL = 4;// 进行绑定协议
	private static final int QUERY_CS_INFO = 5;// 查询客户信息
	private static final int DOWN_LOAD_BIND_FILE = 6;
	
	private long bindProtecalID = 0l;// 绑定协议id
	private HttpUtils httpUtils = null;

	public void initHttpUtils(final int type) {
		
		RequestParams reqParams = new RequestParams();
		JSONObject params = new JSONObject();
		try {
			switch (type) {
			case DOWN_LOAD_BIND_FILE:
				params.put("attachmentId",bindProtecalID);
				httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_DOWNLOAD_FILE, RequestType.POST, params);
				httpUtils.setDownLoadFile(true);
				httpUtils.setShowDiloag(false);
				break;
			case GET_SMS_VERFICATION:
				params.put("phoneNo", etPhone.getText().toString());
				params.put("tranFk", bindProtecalID);
				params.put("tranTypeFk", Constant.BIND_AGREE_MENT_TRAN);
				httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_SMS_VERFICATION, RequestType.POST, params);
				break;
			case QUERY_BIND_PROTECAL:
				params.put("salesId", marketCs.getSalesId());
				params.put("customerId", marketCs.getCustomerId());
				httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_QUERY_BIND_PROTECAL, RequestType.POST, params);
				httpUtils.setShowDiloag(false);
				break;
			case UPLOAD_BIND_PROTECAL_FILE:
				httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_UPLOAD_PROTECAL_FILE, RequestType.POST, null);
				reqParams.put("bindingAgreementId", String.valueOf(bindProtecalID));
				try {
					reqParams.put("bindingAgreementFile", CommonUtil.getFile(Constant.SIGN_DIC_BIND,bindSignFile));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				httpUtils.setRequestParms(reqParams);
				httpUtils.setUploadFile(true);
				break;
			case BIND_PROTECOL:
				if (TextUtils.isEmpty(smsVerfication.getText().toString())) {
					CommonUtil.showToast("请输入短信验证码", getActivity());
					return;
				}
				params.put("recordId", marketCs.getId());
				params.put("bindingAgreementId", bindProtecalID);
				params.put("phoneNo",etPhone.getText().toString());
				params.put("prodId",marketCs.getProdId());
				params.put("verificationCode", smsVerfication.getText().toString());
				httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_BIND_PROTECAL, RequestType.POST, params);
				break;
			case QUERY_CS_INFO:
			     httpUtils = new HttpUtils(getActivity(),RequestDefine.RQ_CUSTOMER_GET_SINGLE, RequestType.GET, null);
				 httpUtils.setShowDiloag(false);
				 httpUtils.setCustomerId(marketCs.getCustomerId());
				 break;
			default:
				break;
			}
			
			httpUtils.setSuccessListener(new SuccessReslut() {
				
				@Override
				public void getResluts(JSONObject response) {
					switch (type) {
					case GET_SMS_VERFICATION:
						if (Request.RESLUT_OK.equals(response.optString("status"))) {
							CommonUtil.showToast("发送短信验证码成功", getActivity());
						}
						break;
					case QUERY_BIND_PROTECAL:
						JSONObject obj;
						try {
							obj = response.getJSONObject("item");
							bindProtecalID = obj.optLong("id");
							//下载绑定附件显示
							initHttpUtils(DOWN_LOAD_BIND_FILE);
							marketCs.setBindProtecolId(String.valueOf(bindProtecalID));
						} catch (JSONException e) {
							e.printStackTrace();
						}
						break;
					case UPLOAD_BIND_PROTECAL_FILE:
						if (Request.RESLUT_OK.equals(response.optString("status"))) {
							CommonUtil.showToast("上传绑定协议附件成功", getActivity());
							initHttpUtils(BIND_PROTECOL);
						}else{
							CommonUtil.showToast("上传绑定协议附件失败", getActivity());
						}
						break;
					case BIND_PROTECOL:
						if (Request.RESLUT_OK.equals(response.optString("status"))) {
							CommonUtil.showToast("绑定协议成功", getActivity());
							if (marketCs != null) {
								marketCs.setMarketReslut(JsonParse.getMarketStepResluts(response));
							}
							 ActCustomerMarketProgress.marketStates = ActCustomerMarketProgress.F_SIGN_FIRST;//更改
							((ActCustomerMarketProgress) getActivity()).showDetails(ActCustomerMarketProgress.F_SIGN_FIRST, 0);
							 ActCustomerMarketProgress.currentView = ActCustomerMarketProgress.F_SIGN_FIRST;
						}else{
							if ("1".equals(response.optString("status"))) {
								CommonUtil.showToast("短信验证码错误", getActivity());
							}
							CommonUtil.showToast("绑定协议失败", getActivity());
						}
						break;
					case QUERY_CS_INFO:
						try {
						  	cs  = new Gson().fromJson(response.toString(), new TypeToken<Customer>() {}.getType());
							initData(false);
							marketCs.setCustomer(cs);
						} catch (JsonSyntaxException e) {
							e.printStackTrace();
						}  
						 break;
					default:
						break;
					}
				}
			});
			 
			httpUtils.setFileSuccessListener(new FileSuccessReslut() {
				
				@Override
				public void getResluts(int statesCode, byte[] binaryData) {
					// TODO Auto-generated method stub
					if (statesCode == 200  && binaryData != null) {
						LogCito.d("下载附件成功marketCs.getCustomerId() = "+marketCs.getCustomerId());
						CommonUtil.storeFile(binaryData,Constant.SIGN_DIC_BIND, Constant.SIGN_PROTECOL_FILE_DOWN+ marketCs.getCustomerId()+".jpg");
//						Bitmap bitMap1 = BitmapManager.decodeSampledBitmapFromFile(Constant.SIGN_DIC_BIND + Constant.SIGN_PROTECOL_FILE_DOWN+ marketCs.getCustomerId()+".jpg", Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
//						editBind.setImageBitmap(bitMap1);
					}
				}
			});
			httpUtils.executeRequest();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
 
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btnGetVerfication:
			if (!TextUtils.isEmpty(etPhone.getText().toString()) && etPhone.getText().toString().matches("^[1][0-9]{10}$")) {
				initHttpUtils(GET_SMS_VERFICATION);
			}else{
				CommonUtil.showToast("请输入合法的手机号码", getActivity());
			}
			break;
		default:
			break;
		}
	}

	public boolean CheckData(){
		 
		if (TextUtils.isEmpty(etPhone.getText().toString()) ||  !etPhone.getText().toString().matches("^[1][0-9]{10}$")) {
			CommonUtil.showToast("请输入手机号码", getActivity());
			return false;
		}else if (TextUtils.isEmpty(smsVerfication.getText().toString())) {
			CommonUtil.showToast("请输入短信验证码", getActivity());
			return false;
		}
		
//		else if (!UPLOAD_SIGN_FILE) {
//			CommonUtil.showToast("请选择签名文件", getActivity());
//			return false;
//		}
		
//		else if(bindProtecalID <= 0){
//			initHttpUtils(QUERY_BIND_PROTECAL);
//			CommonUtil.showToast("尚未创建绑定协议", getActivity());
//			return false;
//		}
		return true;
	}
	

	public void initData(boolean isEdit){
		
		LogCito.d("更新用户信息 bind2");
		etPhone = (EditText) view.findViewById(R.id.etPhone);
		if (isEdit) {
			// cell1 是手机号
			etPhone.setText(marketCs.getCustomer().getCellPhone1());
		}else{
			actBind.temp.setCellPhone1(etPhone.getText().toString());
		}
	}
	
	
}
