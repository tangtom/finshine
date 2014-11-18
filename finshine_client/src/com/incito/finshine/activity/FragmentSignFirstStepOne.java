package com.incito.finshine.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.core.util.AppToast;
import com.custom.view.CommFlipDot;
import com.custom.view.DlgCommFilter;
import com.custom.view.MyViewPager;
import com.custom.view.PopDatePicker;
import com.custom.view.DlgCommFilter.RefreshFilterListener;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterCustomerBaseDetailViewPager;
import com.incito.finshine.activity.dialog.DialogProductAppointShare;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.Bank;
import com.incito.finshine.entity.ContractBaseData;
import com.incito.finshine.entity.ContractInfoItem;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.ExpireCustomer;
import com.incito.finshine.entity.FirstPageCount;
import com.incito.finshine.entity.MarketCsOrder;
import com.incito.finshine.entity.MarketStateReslut;
import com.incito.finshine.entity.Product;
import com.incito.finshine.entity.RemainBudgetReturn;
import com.incito.finshine.entity.User;
import com.incito.finshine.manager.BitmapManager;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.JsonParse;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FaliureResult;
import com.incito.finshine.network.HttpUtils.FileSuccessReslut;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.LogCito;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.utility.MoneyFormat;
import com.incito.wisdomsdk.event.EventBus;
import com.incito.wisdomsdk.net.http.RequestParams;
import com.incito.wisdomsdk.utils.BitmapUtils;

/**
 * NOTE：照片的名字中不应该以customerid作为唯一标识 图片存的路径：图片类型+上传/下载的标识图片名+客户id+
 * 销售订单ID+当前所处的步骤合同的id
 * <dl>
 * <dt>FragmentBindFirst.java</dt>
 * <dd>Description:客户营销 签订合同第一大步 第一次小步 Fragment</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月16日 下午5:00:59</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class FragmentSignFirstStepOne extends FragmentDetail implements OnClickListener {

	View view;
	private MyViewPager mPager;// 页卡内容
	private List<View> listViews; // Tab页面列表
	private View view1, view2;
	public CommFlipDot flipDot = null;

	private static int currentView = 0;// 判断当前是哪一个
	
	RemainBudgetReturn rbr = null;//预约详情model

	// View1
	private TextView customerName;
	private TextView productType;
	private EditText numberEdit;
	private TextView numberTxt;
	private TextView price;
	private Spinner bankID;
	private EditText bankNumber;
	private Button btnBankFront;
	private Button btnBankObserve;
	private ImageView imgBankFront;
	private ImageView imgBankObserve;
	
	private LinearLayout layoutAppointMsg;//预约份额不足时提示的Layout
	private TextView tvAppointMsg;//预约份额不足时提示预约份额的详细数字
	private TextView tvAppointService;//预约客服电话

	// view2
	/* 电子签名确认书签名页 */
	private ImageView imgESignature;
	private Button btnESignature;

	/* 资产管理合同签名页 */
	private ImageView imgEAMSignature;
	private Button btnEAMSignature;

	/* 现场视频 */
	private ImageView imgAliveVedio;
	private Button btnAliveVedio;

	/* 获取验证码 */
	private EditText edittCode;
	private Button btnCode;

	private MarketCsOrder marketCs;

	private StringBuffer pictureMiddleName = new StringBuffer();

	private HttpUtils httpUtils = null;
	private ContractBaseData contractBaseData = null;
	private MarketStateReslut marketResult = null;

	private File albumTakePhotoFile;

	// http用
	private static final int GET_BASE_INFO = 0;
	private static final int DOWNLOAD_BANK_OPPOSIVE = 1;
	private static final int DOWNLOAD_BANK_DEFENSIVE = 2;
	private static final int DOWNLOAD_RISK_IMAGE = 3;
	private static final int DOWNLOAD_ASSET_CONTRACT = 4;
	private static final int DOWNLOAD_VIDEO = 5;
	private static final int SEND_SMS = 6;
	private static final int UPDATE_BASE_INFO = 7;
	private static final int UPDATE_ATTACHMENT = 8;

	// 获取照片用
	private static final int UPLOAD_BANK_OPPOSIVE = 9;
	private static final int UPLOAD_BANK_DEFENSIVE = 10;
	private static final int UPLOAD_RISK_IMAGE = 11;
	private static final int UPLOAD_ASSET_CONTRACT = 12;
	private static final int UPLOAD_VIDEO = 13;

	private int currentType = 0;// 照片的类型
	// 标记照片是否上传 （是否有图片出来）
	private boolean upLoadOpp = false;
	private boolean uploadDefensive = false;
	private boolean uploadRiskImg = false;
	private boolean uploadAssetContract = false;
	// private boolean uploadVideo = false;

	private boolean hasSMS = false;
	private boolean isUpdateSuccess = false;

	private String videoPath;

	public static FragmentSignFirstStepOne newInstance(int id, int position) {

		FragmentSignFirstStepOne f = new FragmentSignFirstStepOne();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);
		// marketCs = data;
		currentView = position;
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}

		view = inflater.inflate(R.layout.frag_sign_contract_viewpager, container, false);// frag_base_custom_normal

		flipDot = new CommFlipDot(getActivity(), 2, (LinearLayout) view.findViewById(R.id.ltDot));

		initViewPager();
		return view;
	}

	private void initViewPager() {

		mPager = (MyViewPager) view.findViewById(R.id.viewPagerCustomerDetail);
		mPager.setPagingEnabled(false);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getActivity().getLayoutInflater();
		view1 = mInflater.inflate(R.layout.act_sign_contract_1_1, null);
		listViews.add(view1);
		view2 = mInflater.inflate(R.layout.act_sign_contract_1_2, null);
		listViews.add(view2);

		initUI();

		mPager.setAdapter(new AdapterCustomerBaseDetailViewPager(listViews));
		mPager.setCurrentItem(currentView);
		flipDot.setSeletion(currentView);
		mPager.setOnPageChangeListener(new MyPage(flipDot));
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void initUI() {

		// 上一步
		Button btnPre = (Button) view.findViewById(R.id.btnPre);
		btnPre.setOnClickListener(this);
		// 下一步
		Button btnNext = (Button) view.findViewById(R.id.btnNext);
		btnNext.setOnClickListener(this);

		customerName = (TextView) view1.findViewById(R.id.customerName);
		productType = (TextView) view1.findViewById(R.id.productType);
		numberEdit = (EditText) view1.findViewById(R.id.numberEdit);
		numberEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				if (!TextUtils.isEmpty(s.toString())) {
					Product prod = marketCs.getProd();
					double money = 0.0d;
					Log.i("", "prod is null " + (prod == null));
					if (prod != null) {
						money = Long.parseLong(s.toString()) * prod.getProdPrice();
						BigDecimal bd = new BigDecimal(money);
						if (money != 0) {
							price.setText(new MoneyFormat().format(bd.toPlainString()));
						}
						numberTxt.setText(bd.toPlainString());
					}
				} else {
					numberTxt.setText("0");
					price.setText("零万元整");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		numberTxt = (TextView) view1.findViewById(R.id.numberTxt);
		price = (TextView) view1.findViewById(R.id.price);

		bankID = (Spinner) view1.findViewById(R.id.bankID);
		ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.common_spinner_item, getActivity()
				.getResources().getStringArray(R.array.bank_num));// android.R.layout.simple_spinner_item
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//
		bankID.setAdapter(adapter);

		bankNumber = (EditText) view1.findViewById(R.id.bankNumber);

		btnBankFront = (Button) view1.findViewById(R.id.btnBankFront);
		btnBankObserve = (Button) view1.findViewById(R.id.btnBankObserve);
		btnBankFront.setOnClickListener(this);
		btnBankObserve.setOnClickListener(this);
		imgBankFront = (ImageView) view1.findViewById(R.id.imgBankFront);
		imgBankObserve = (ImageView) view1.findViewById(R.id.imgBankObserve);
		
		layoutAppointMsg = (LinearLayout)view1.findViewById(R.id.layout_appoint_msg);
		tvAppointMsg = (TextView)view1.findViewById(R.id.tv_appoint_msg);
		tvAppointService = (TextView)view1.findViewById(R.id.tv_appoint_service);
		tvAppointService.setOnClickListener(this);

		imgESignature = (ImageView) view2.findViewById(R.id.imgESignature);
		imgEAMSignature = (ImageView) view2.findViewById(R.id.imgEAMSignature);
		imgAliveVedio = (ImageView) view2.findViewById(R.id.imgAliveVedio);
		edittCode = (EditText) view2.findViewById(R.id.edittCode);

		btnESignature = (Button) view2.findViewById(R.id.btnESignature);
		btnESignature.setOnClickListener(this);
		btnEAMSignature = (Button) view2.findViewById(R.id.btnEAMSignature);
		btnEAMSignature.setOnClickListener(this);
		btnAliveVedio = (Button) view2.findViewById(R.id.btnAliveVedio);
		btnAliveVedio.setOnClickListener(this);
		btnCode = (Button) view2.findViewById(R.id.btnCode);
		btnCode.setOnClickListener(this);

		marketCs = ((ActCustomerMarketProgress) getActivity()).getMarketCs();
		if (marketCs != null) {
			marketResult = JsonParse.getMarketReslut();
			contractBaseData = ((ActCustomerMarketProgress) getActivity()).getContractBaseData();
			if (contractBaseData == null) {
				initHttpType(GET_BASE_INFO);
			} else { // 有值 直接初始化
				initData();
				downIcon();
				isEdit();
			}
		}
	}

	private void initData() {
		if (contractBaseData != null) {

			pictureMiddleName.append(contractBaseData.getCustomer().getId() + "-");
			pictureMiddleName.append(contractBaseData.getSalesOrderId() + "-");
			pictureMiddleName.append(contractBaseData.getId() + "-3.jpg");

			customerName.setText(contractBaseData.getCustomer().getName());
			productType.setText(marketCs.getProdName());// 产品名称没给
			numberEdit.setText(contractBaseData.getSubscribeUnits() > 0 ? (contractBaseData.getSubscribeUnits() + "")
					: "");
			numberTxt.setText(contractBaseData.getSubscribeUnits() > 0 ? (contractBaseData.getSubscribeUnits() + "")
					: "");
			price.setText(contractBaseData.getCapitalLetters() != null ? (contractBaseData.getCapitalLetters() + "")
					: "");// 数字转成大写表达

			bankID.setSelection((int) (contractBaseData.getBank().getId() > 1 ? contractBaseData.getBank().getId() - 1
					: 0), true);
			bankNumber.setText(contractBaseData.getBankCardNumber());
		}
	}

	private void initHttpType(final int currentType) {

		try {
			JSONObject params = new JSONObject();
			RequestParams reqParams = new RequestParams();
			int reqId = 0;
			RequestType reqType = null;
			reqType = RequestType.POST;
			switch (currentType) {
			case GET_BASE_INFO:
				// 获取合同签订基本信息
				reqId = RequestDefine.MARKET_SC_FIRST_GET_BASEINFO;
				params.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
				params.put("customerId", marketCs.getCustomerId());
				params.put("salesOrderId", marketCs.getSalesOrderId());
				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(true);
				break;

			case DOWNLOAD_BANK_OPPOSIVE:
				// 获取银行卡正面照
				reqId = RequestDefine.MARKET_SC_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", contractBaseData.getId());
				params.put("transactionType", "CONTRACTBASEDATA");
				params.put("moduleType", "POSITIVEPHOTO");

				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;

			case DOWNLOAD_BANK_DEFENSIVE:
				// 获取银行卡反面照
				reqId = RequestDefine.MARKET_SC_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", contractBaseData.getId());
				params.put("transactionType", "CONTRACTBASEDATA");
				params.put("moduleType", "NEGATIVEPHOTO");

				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;

			case DOWNLOAD_RISK_IMAGE:
				// 获取电子签名确认书
				reqId = RequestDefine.MARKET_SC_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", contractBaseData.getId());
				params.put("transactionType", "CONTRACTBASEDATA");
				params.put("moduleType", "ELECTRONICCONTRACT");

				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;

			case DOWNLOAD_ASSET_CONTRACT:
				// 获取资产合同
				reqId = RequestDefine.MARKET_SC_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", contractBaseData.getId());
				params.put("transactionType", "CONTRACTBASEDATA");
				params.put("moduleType", "ASSETMENT");

				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;

			case DOWNLOAD_VIDEO:
				// reqType = RequestType.GET; 获取视频
				reqId = RequestDefine.MARKET_SC_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", contractBaseData.getId());
				params.put("transactionType", "CONTRACTBASEDATA");
				params.put("moduleType", "LIVEVIDEO");

				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;

			case SEND_SMS:
				// 发送验证码
				reqId = RequestDefine.MARKET_RQ_SMS_VERFICATION;
				params.put("phoneNo", contractBaseData.getCustomer().getCellPhone1());
				params.put("tranTypeFk", Constant.CON_TRACT_SIGN_TRAN);
				params.put("tranFk", contractBaseData.getSalesOrderId());
				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				break;

			case UPDATE_BASE_INFO:
				// 更新基本信息
				reqType = RequestType.PUT;
				reqId = RequestDefine.MARKET_SC_FIRST_SAVE_BASEINFO;
				params.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
				params.put("customerId", contractBaseData.getCustomer().getId());
				params.put("salesOrderId", contractBaseData.getSalesOrderId());
				params.put("subscribeUnits", contractBaseData.getSubscribeUnits());
				params.put("capitalLetters", contractBaseData.getCapitalLetters());
				params.put("bankId", contractBaseData.getBank().getId());
				params.put("bankCardNumber", contractBaseData.getBankCardNumber());
				params.put("verificationCode", edittCode.getText().toString());// 手机验证码
				Log.e("Sign", "subscribeUnits"+contractBaseData.getSubscribeUnits());
				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(true);
				break;

			case UPDATE_ATTACHMENT:
				// 更新附件
				reqType = RequestType.POST;
				reqId = RequestDefine.MARKET_SC_FIRST_SAVE_ATTACHMENT;
				// note：用的是reqParams 不是jsonObject
				reqParams.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID) + "");
				reqParams.put("customerId", marketCs.getCustomerId() + "");
				reqParams.put("salesOrderId", marketCs.getSalesOrderId() + "");
				try {
					if (CommonUtil.isNotEmptyfile(Constant.BANKCARD + Constant.UP_BANK_OPP + pictureMiddleName)) {
						reqParams.put("positivePictureOfBankCard",
								CommonUtil.getFile(Constant.BANKCARD, Constant.UP_BANK_OPP + pictureMiddleName));
					} else {
						reqParams.put("positivePictureOfBankCard",
								CommonUtil.getFile(Constant.BANKCARD, Constant.DOWN_BANK_OPP + pictureMiddleName));
					}
					if (CommonUtil.isNotEmptyfile(Constant.BANKCARD + Constant.UP_BANK_DEF + pictureMiddleName)) {
						reqParams.put("negativePictureOfBankCard",
								CommonUtil.getFile(Constant.BANKCARD, Constant.UP_BANK_DEF + pictureMiddleName));
					} else {
						reqParams.put("negativePictureOfBankCard",
								CommonUtil.getFile(Constant.BANKCARD, Constant.DOWN_BANK_DEF + pictureMiddleName));
					}
					if (CommonUtil.isNotEmptyfile(Constant.RISK_PREF + Constant.UP_RISK + pictureMiddleName)) {
						reqParams.put("pictureOfElectronicContract",
								CommonUtil.getFile(Constant.RISK_PREF, Constant.UP_RISK + pictureMiddleName));
					} else {
						reqParams.put("pictureOfElectronicContract",
								CommonUtil.getFile(Constant.RISK_PREF, Constant.DOWN_RISK + pictureMiddleName));
					}
					if (CommonUtil.isNotEmptyfile(Constant.ASSET_CONTRACT + Constant.UP_ASSET + pictureMiddleName)) {
						reqParams.put("pictureOfAssetmentManagement",
								CommonUtil.getFile(Constant.ASSET_CONTRACT, Constant.UP_ASSET + pictureMiddleName));
					} else {
						reqParams.put("pictureOfAssetmentManagement",
								CommonUtil.getFile(Constant.ASSET_CONTRACT, Constant.DOWN_ASSET + pictureMiddleName));
					}

					if (CommonUtil.isNotEmptyfile(videoPath)){
						reqParams.put("liveVideo", CommonUtil.getFile(videoPath));
					}
//					reqParams.put("liveVideo", videoPath);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				httpUtils = new HttpUtils(getActivity(), reqId, reqType, null);
				httpUtils.setRequestParms(reqParams);
				httpUtils.setShowDiloag(true);
				httpUtils.setUploadFile(true);
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
					String states = response.optString("status");
					switch (currentType) {
					case GET_BASE_INFO:
						// 获取合同基本信息
						if (Request.RESLUT_OK.equals(states)) {
							try {
								JSONObject obj = response.getJSONObject("item");
								Gson gson = new Gson();

								JSONObject customerObj = response.getJSONObject("item").getJSONObject("customer");
								Customer customer = gson.fromJson(customerObj.toString(), Customer.class);

								JSONObject userObj = response.getJSONObject("item").getJSONObject("salesman");
								User user = gson.fromJson(userObj.toString(), User.class);

								JSONObject bankObj = response.getJSONObject("item").getJSONObject("bank");
								Bank bank = gson.fromJson(bankObj.toString(), Bank.class);

								contractBaseData = gson.fromJson(obj.toString(), ContractBaseData.class);
								contractBaseData.setBank(bank);
								contractBaseData.setCustomer(customer);
								contractBaseData.setSalesman(user);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							// CoreManager.getInstance().getProduct()
							// .setContractBaseData(contractBaseData);
							((ActCustomerMarketProgress) getActivity()).setContractBaseData(contractBaseData);
							initData();
							downIcon();
							isEdit();
						}
						break;

					case UPDATE_BASE_INFO:
						// 更新合同基本信息 可能验证码不对 要获取出msg的信息

						if (Request.RESLUT_OK.equals(states)) {
							initHttpType(UPDATE_ATTACHMENT);
						} else if (states.equals("2")) {
							CommonUtil.showToast("手机验证失败", getActivity());
						} else {
							CommonUtil.showToast("上传失败", getActivity());
						}
						break;

					case UPDATE_ATTACHMENT:
						// 更新附件 成功之后将down的图片删除 并且将up的图片重命名 失败不变
						isUpdateSuccess = response.optString("status").equals(Request.RESLUT_OK);
						if (isUpdateSuccess) {
							marketResult = JsonParse.getMarketStepResluts(response);
							if (marketResult != null) {
								marketCs.setMarketReslut(marketResult);// 不用再放到单例里面了
								JsonParse.setMarketReslut(marketResult);
								// marketCs.setSalesOrderId(marketResult.getSalesOrderId());
								marketCs.setMarketingStatusId(marketResult.getMarketingStatusId());
								((ActCustomerMarketProgress) getActivity()).setMarketCsOrder(marketCs);
								// CoreManager.getInstance().getProduct().setMarketResult(marketResult);
							}

							// 是否成功？？？
							CommonUtil.renameFile(Constant.BANKCARD, Constant.DOWN_BANK_OPP + pictureMiddleName,
									Constant.UP_BANK_OPP + pictureMiddleName);
							CommonUtil.renameFile(Constant.BANKCARD, Constant.DOWN_BANK_DEF + pictureMiddleName,
									Constant.UP_BANK_DEF + pictureMiddleName);
							CommonUtil.renameFile(Constant.RISK_PREF, Constant.DOWN_RISK + pictureMiddleName,
									Constant.UP_RISK + pictureMiddleName);
							CommonUtil.renameFile(Constant.ASSET_CONTRACT, Constant.DOWN_ASSET + pictureMiddleName,
									Constant.UP_ASSET + pictureMiddleName);

							((ActCustomerMarketProgress) getActivity()).showDetails(
									ActCustomerMarketProgress.F_SIGN_SECOND, 0);
						} else {
							CommonUtil.showToast("上传信息失败", getActivity());
						}
						break;

					case SEND_SMS:
						if (Request.RESLUT_OK.equals(states)) {
							hasSMS = true;
							CommonUtil.showToast("获取短信验证码成功,请您注意查收", getActivity());
						} else
							hasSMS = false;
						break;

					default:
						break;
					}
				}
			});

			httpUtils.setFileSuccessListener(new FileSuccessReslut() {
				@Override
				public void getResluts(int responeCode, byte[] binaryData) {

					String file = "";
					String fileName = "";

					if (responeCode == 200 && binaryData != null && binaryData.length > 0) {

						switch (currentType) {
						case DOWNLOAD_BANK_OPPOSIVE:
							// 获取银行卡正面照
							file = Constant.BANKCARD;
							fileName = Constant.DOWN_BANK_OPP + pictureMiddleName;
							CommonUtil.storeFile(binaryData, file, fileName);
							Bitmap bitMap1 = BitmapManager.decodeSampledBitmapFromFile(file + "/" + fileName,
									Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
							upLoadOpp = true;
							imgBankFront.setImageBitmap(bitMap1);

							break;

						case DOWNLOAD_BANK_DEFENSIVE:
							// 获取银行卡反面照
							file = Constant.BANKCARD;
							fileName = Constant.DOWN_BANK_DEF + pictureMiddleName;
							CommonUtil.storeFile(binaryData, file, fileName);
							Bitmap bitMap2 = BitmapManager.decodeSampledBitmapFromFile(file + "/" + fileName,
									Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
							uploadDefensive = true;
							imgBankObserve.setImageBitmap(bitMap2);
							break;

						case DOWNLOAD_RISK_IMAGE:
							// 获取电子签名确认书
							file = Constant.RISK_PREF;
							fileName = Constant.DOWN_RISK + pictureMiddleName;
							CommonUtil.storeFile(binaryData, file, fileName);
							Bitmap bitMap3 = BitmapManager.decodeSampledBitmapFromFile(file + "/" + fileName,
									Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
							uploadRiskImg = true;
							imgESignature.setImageBitmap(bitMap3);
							break;

						case DOWNLOAD_ASSET_CONTRACT:
							// 获取资产合同
							file = Constant.ASSET_CONTRACT;
							fileName = Constant.DOWN_ASSET + pictureMiddleName;
							CommonUtil.storeFile(binaryData, file, fileName);
							Bitmap bitMap4 = BitmapManager.decodeSampledBitmapFromFile(file + "/" + fileName,
									Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
							uploadAssetContract = true;
							imgEAMSignature.setImageBitmap(bitMap4);
							break;

						default:
							break;
						}
					}
				}
			});
			httpUtils.executeRequest();

		} catch (JSONException e1) {

		}
	}

	// 下载图片附件 本地有 显示本地的，没有 显示网络加载的
	private void downIcon() {
		// 查询合同信息
		if (contractBaseData == null || contractBaseData.getCustomer().getId() <= 0) {
			return;
		}
		// 银行卡正面
		if (CommonUtil.isNotEmptyfile(Constant.BANKCARD + Constant.DOWN_BANK_OPP + pictureMiddleName)) {
			imgBankFront.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.BANKCARD
					+ Constant.DOWN_BANK_OPP + pictureMiddleName, Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT));
			upLoadOpp = true;
		} else if (CommonUtil.isNotEmptyfile(Constant.BANKCARD + Constant.UP_BANK_OPP + pictureMiddleName)) {
			imgBankFront.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.BANKCARD
					+ Constant.UP_BANK_OPP + pictureMiddleName, Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT));
			upLoadOpp = true;
		} else {
			initHttpType(DOWNLOAD_BANK_OPPOSIVE);
		}

		if (CommonUtil.isNotEmptyfile(Constant.BANKCARD + Constant.DOWN_BANK_DEF + pictureMiddleName)) {
			imgBankObserve.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.BANKCARD
					+ Constant.DOWN_BANK_DEF + pictureMiddleName, Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT));
			uploadDefensive = true;
		} else if (CommonUtil.isNotEmptyfile(Constant.BANKCARD + Constant.UP_BANK_DEF + pictureMiddleName)) {
			imgBankObserve.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.BANKCARD
					+ Constant.UP_BANK_DEF + pictureMiddleName, Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT));
			uploadDefensive = true;
		} else {
			initHttpType(DOWNLOAD_BANK_DEFENSIVE);
		}

		if (CommonUtil.isNotEmptyfile(Constant.RISK_PREF + Constant.DOWN_RISK + pictureMiddleName)) {
			imgESignature.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.RISK_PREF
					+ Constant.DOWN_RISK + pictureMiddleName, Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT));
			uploadRiskImg = true;
		} else if (CommonUtil.isNotEmptyfile(Constant.RISK_PREF + Constant.UP_RISK + pictureMiddleName)) {
			imgESignature.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.RISK_PREF
					+ Constant.UP_RISK + pictureMiddleName, Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT));
			uploadRiskImg = true;
		} else {
			initHttpType(DOWNLOAD_RISK_IMAGE);
		}

		if (CommonUtil.isNotEmptyfile(Constant.ASSET_CONTRACT + Constant.DOWN_ASSET + pictureMiddleName)) {
			// 优先显示下载过
			imgEAMSignature.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.ASSET_CONTRACT
					+ Constant.DOWN_ASSET + pictureMiddleName, Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT));
			uploadAssetContract = true;
		} else if (CommonUtil.isNotEmptyfile(Constant.ASSET_CONTRACT + Constant.UP_ASSET + pictureMiddleName)) {
			// 优先显示下载过
			imgEAMSignature.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.ASSET_CONTRACT
					+ Constant.UP_ASSET + pictureMiddleName, Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT));
			uploadAssetContract = true;
		} else {
			initHttpType(DOWNLOAD_ASSET_CONTRACT);
		}
	}

	// 点击上传图片 选择对应的方式上传
	private void seleIcon(final int type) {
		currentType = type;

		DlgCommFilter dlgCommFilter = new DlgCommFilter(getActivity(), R.array.select_icon, R.string.ablum_selec, "拍照",
				true, 0);
		dlgCommFilter.setListener(new RefreshFilterListener() {

			@Override
			public void doRefresh(String reslut, int position, boolean b, int title) {
				if (position == 0) {
					// 拍照
					Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
					switch (type) {
					case UPLOAD_BANK_OPPOSIVE: // 正面银行卡
						albumTakePhotoFile = CommonUtil.getFile(Constant.BANKCARD, Constant.UP_BANK_OPP
								+ pictureMiddleName);
						break;
					case UPLOAD_BANK_DEFENSIVE: // 反面银行卡
						albumTakePhotoFile = CommonUtil.getFile(Constant.BANKCARD, Constant.UP_BANK_DEF
								+ pictureMiddleName);
						break;
					case UPLOAD_RISK_IMAGE: // 电子签名照
						albumTakePhotoFile = CommonUtil.getFile(Constant.RISK_PREF, Constant.UP_RISK
								+ pictureMiddleName);
						break;
					case UPLOAD_ASSET_CONTRACT: // 资产管理合同照
						albumTakePhotoFile = CommonUtil.getFile(Constant.ASSET_CONTRACT, Constant.UP_ASSET
								+ pictureMiddleName);
						break;

					// case UPLOAD_VIDEO: // 视频
					// albumTakePhotoFile = CommonUtil.getFile(
					// Constant.BANKCARD, Constant.UP_BANK_DEF
					// + marketCs.getCustomerId() + ".jpg");
					// break;

					default:
						break;
					}
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(albumTakePhotoFile));
					startActivityForResult(intent, Constant.REQUEST_TAKE_PHOTO);
				} else {
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

	// 显示图片 存到本地
	private void showIcon(Intent data, Bitmap bitmap) {
		bitmap = CommonUtil.saveLocalFile(data, bitmap);
		switch (currentType) {
		case UPLOAD_BANK_OPPOSIVE:
			CommonUtil.storeFile(BitmapUtils.bmpToByteArray(CommonUtil.saveLocalFile(data, bitmap), false),
					Constant.BANKCARD, Constant.UP_BANK_OPP + pictureMiddleName);
			imgBankFront.setImageBitmap(bitmap);
			upLoadOpp = true;
			break;
		case UPLOAD_BANK_DEFENSIVE:
			CommonUtil.storeFile(BitmapUtils.bmpToByteArray(CommonUtil.saveLocalFile(data, bitmap), false),
					Constant.BANKCARD, Constant.UP_BANK_DEF + pictureMiddleName);
			imgBankObserve.setImageBitmap(bitmap);
			uploadDefensive = true;
			break;

		case UPLOAD_RISK_IMAGE: // 电子签名照
			CommonUtil.storeFile(BitmapUtils.bmpToByteArray(CommonUtil.saveLocalFile(data, bitmap), false),
					Constant.RISK_PREF, Constant.UP_RISK + pictureMiddleName);

			imgESignature.setImageBitmap(bitmap);
			uploadRiskImg = true;
			break;

		case UPLOAD_ASSET_CONTRACT: // 资产管理合同照
			CommonUtil.storeFile(BitmapUtils.bmpToByteArray(CommonUtil.saveLocalFile(data, bitmap), false),
					Constant.ASSET_CONTRACT, Constant.UP_ASSET + pictureMiddleName);

			imgEAMSignature.setImageBitmap(bitmap);
			uploadAssetContract = true;
			break;
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			try {
				Bitmap selectImage = null;
				if (requestCode == Constant.REQUEST_ALBUM_PHOTO_RESLUT) {// 从相册选择
					String file = data.getDataString();
					if (!TextUtils.isEmpty(file) && file.startsWith("file:///")) {
						if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
							file = file.replace("file://", "");
							try {
								file = URLDecoder.decode(file, "UTF-8");
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
						}
						//doCropPhoto(new File(file));
						Uri uri = Uri.parse(albumTakePhotoFile.getPath());
						Intent intent = new Intent();
						intent.setDataAndType(uri, "image/*");
						showIcon(intent, selectImage);
					} else {
						Uri url = data.getData();
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
				} else if (requestCode == Constant.REQUEST_CAPTURE_VIDEO) {
//					Toast.makeText(getActivity(), "录像完成，保存路径：" + data.getDataString(), Toast.LENGTH_LONG).show();
					Log.i("tag", "vedio = " + data.getDataString());

					String[] proj = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
					Cursor cursor = getActivity().managedQuery(data.getData(), proj, null, null, null);
					int image_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					int id_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
					cursor.moveToFirst();

					videoPath = cursor.getString(image_column_index);
					int id = cursor.getInt(id_column_index);
					Log.i("tag", "vedio = " + videoPath);
					
					File videoFile = CommonUtil.getFile(videoPath);
//					FileInputStream fis = new FileInputStream(videoFile);
//				    long fileLen = fis.available(); //这就是文件大小
					double size = (double) videoFile.length() / 1024 / 1024;//单位是mb
				    if (size > 10){//10M  10737418240
				    	AppToast.ShowToast("视频录制过大，无法上传，请小于10M");
				    }else{
				    	final Bitmap b = Video.Thumbnails.getThumbnail(getActivity().getContentResolver(), id,
								Images.Thumbnails.MINI_KIND, null);
						if (b != null) {
							Log.i("tag", "bitmap w = " + b.getWidth());
							Log.i("tag", "bitmap h = " + b.getHeight());
							imgAliveVedio.setImageBitmap(b);
						}
				    }
					
				}
			} catch (Exception e) {
				e.getLocalizedMessage();
			}
		}
	}

	// 裁剪图片
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
	
	private void checkAppointState(final View v){//检查服务器预约状态
		JSONObject json = new JSONObject();
		try {
			json.put("prodId",marketCs.getProdId());
			json.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
			json.put("removeSalesOrderId",marketCs.getSalesOrderId());
			json.put("currentAmount", numberEdit.getText().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		HttpUtils httpUtils = new HttpUtils(this.getActivity(),RequestDefine.APPOINT_RQ_PRODUCT_CHECK,RequestType.POST,json);
		httpUtils.setSuccessListener(new SuccessReslut() {
			
			@Override
			public void getResluts(JSONObject response) {
				// TODO Auto-generated method stub
				Log.i("", response.toString());
				try {
					String status = response.getString("status");
					if(status.equals("1")){
						if(!response.isNull("item")){
							Gson gson = new Gson();
							rbr = gson.fromJson(response.getJSONObject("item").toString(), RemainBudgetReturn.class);
							if(rbr != null){
								switch(Integer.parseInt(rbr.getCheckStatus())){
								case 1:
								case 2:
								case 3:
								case 4:
									layoutAppointMsg.setVisibility(View.VISIBLE);
									tvAppointMsg.setText((int)rbr.getRemainAmount() + "/" + (int)rbr.getOriginalAmount() + "万");
									AppToast.ShowToast("剩余预约份额不足！");
									break;
								}
							}
						}else{
							AppToast.ShowToast("获取已预约份额失败!");
						}
					}else if(status.equals("0")){
						layoutAppointMsg.setVisibility(View.GONE);
						switchView(false);
					}					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		httpUtils.setFaliureResult(new FaliureResult() {
			
			@Override
			public void getResluts(String msg) {
				// TODO Auto-generated method stub
				AppToast.ShowToast("获取已预约份额失败!");
			}
		});
		httpUtils.executeRequest();
	}
	
	private void seleVedio(){//视频点击上传时
		
	}

	@Override
	public void onClick(final View v) {

		switch (v.getId()) {
		case R.id.btnBankFront:
			seleIcon(UPLOAD_BANK_OPPOSIVE);
			break;
		case R.id.btnBankObserve:
			seleIcon(UPLOAD_BANK_DEFENSIVE);
			break;

		case R.id.btnESignature:
			seleIcon(UPLOAD_RISK_IMAGE);
			break;
		case R.id.btnEAMSignature:
			seleIcon(UPLOAD_ASSET_CONTRACT);
			break;
		case R.id.btnAliveVedio:
			seleVedio();
			Intent intent = new Intent();
			intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			startActivityForResult(intent, Constant.REQUEST_CAPTURE_VIDEO);
			break;

		case R.id.btnPre:
			switchView(true);
			break;
		case R.id.btnNext:
			checkAppointState(v);
			break;

		case R.id.btnCode:
			initHttpType(SEND_SMS);
			break;
		case R.id.tv_appoint_service:
			if(rbr.getIssuerPhone() != null){
				CommonUtil.dialPhone(this.getActivity(), rbr.getIssuerPhone());
			}
			break;
		default:
			break;
		}
	}

	private void switchView(boolean isPre) {
		if (isPre) // 上一页
		{
			if (currentView == 0) {
				// 提示是否放弃当前的信息
				((ActCustomerMarketProgress) getActivity()).showDetails(ActCustomerMarketProgress.F_BIND, 1);
			} else {
				currentView--;
				mPager.setCurrentItem(currentView, true);
			}
		}

		else // 下一页
		{
			if (currentView == 1) {
				// 保存信息
				if (marketCs.getMarketingStatusId() < 4) {// 编辑模式
					if (isFullDataSecond()) {
						initHttpType(UPDATE_BASE_INFO);
						// ((ActCustomerMarketProgress)
						// getActivity()).showDetails(
						// ActCustomerMarketProgress.F_SIGN_SECOND, 0);
					}
				} else {// 预览直接跳
					((ActCustomerMarketProgress) getActivity()).showDetails(ActCustomerMarketProgress.F_SIGN_SECOND, 0);
				}
			} else {

				if (marketCs.getMarketingStatusId() < 4) {// 编辑模式
					// 第一页跳转到第二页的时候 判断第一页是否信息填满
					if (isFullDataFirst()) {
						currentView++;
						mPager.setCurrentItem(currentView, true);
					}
				} else {// 预览模式
					currentView++;
					mPager.setCurrentItem(currentView, true);
				}
			}
		}
	}

	private boolean isFullDataFirst() {
		if (TextUtils.isEmpty(numberEdit.getText().toString()) || Long.parseLong(numberEdit.getText().toString()) == 0) {
			CommonUtil.showToast("请输入预约产品的数量", getActivity());
			return false;
		}
		// else if (TextUtils.isEmpty(bankID.getSelectedItemPosition() )) {
		// CommonUtil.showToast("请选择支付银行的类型", getActivity());
		// return false;
		// }
		else if (TextUtils.isEmpty(bankNumber.getText().toString())) {
			CommonUtil.showToast("请输入银行卡号", getActivity());
			return false;
		}

		else if (!upLoadOpp) {
			CommonUtil.showToast("请上传银行卡正面照", getActivity());
			return false;
		} else if (!uploadDefensive) {
			CommonUtil.showToast("请上传银行卡反面照", getActivity());
			return false;
		}

		contractBaseData.setSubscribeUnits(Integer.parseInt(numberEdit.getText().toString()));
		contractBaseData.setCapitalLetters(price.getText().toString());
		contractBaseData.getBank().setId(bankID.getSelectedItemPosition() + 1);
		contractBaseData.setBankCardNumber(bankNumber.getText().toString());

		return true;
	}

	private boolean isFullDataSecond() {

		if (!uploadRiskImg) {
			CommonUtil.showToast("请上传电子签名确认书签名页", getActivity());
			return false;
		} else if (!uploadAssetContract) {
			CommonUtil.showToast("请上传资产管理合同签名页", getActivity());
			return false;
		} else if (TextUtils.isEmpty(edittCode.getText().toString())) {
			CommonUtil.showToast("请输入手机验证码", getActivity());
			return false;
		}
		// else if (!hasSMS) {
		// CommonUtil.showToast("获取手机验证码超时，请重新获取", getActivity());
		// return false;
		// }
		return true;
	}

	// 判断一下当前页是否可以编辑
	private boolean isEdit() {
		if (marketCs.getMarketingStatusId() > 3) {
			numberEdit.setEnabled(false);
			bankID.setEnabled(false);
			bankNumber.setEnabled(false);
			btnBankFront.setEnabled(false);
			btnBankObserve.setEnabled(false);
			imgBankFront.setEnabled(false);
			imgBankObserve.setEnabled(false);

			imgESignature.setEnabled(false);
			btnESignature.setEnabled(false);
			imgEAMSignature.setEnabled(false);
			btnEAMSignature.setEnabled(false);
			imgAliveVedio.setEnabled(false);
			btnAliveVedio.setEnabled(false);
			edittCode.setEnabled(false);
			btnCode.setEnabled(false);
			return false;
		}
		return true;
	}

	private class MyPage implements OnPageChangeListener {

		CommFlipDot flipDot = null;

		public MyPage(CommFlipDot flipDot) {
			super();
			this.flipDot = flipDot;
		}

		@Override
		public void onPageScrollStateChanged(int status) {

		}

		@Override
		public void onPageScrolled(int curPage, float arg1, int arg2) {
			currentView = curPage;
			// 判断 设置可否滑动

		}

		@Override
		public void onPageSelected(int curPage) {
			if (flipDot != null) {
				flipDot.setSeletion(curPage);
			}
		}

	}

}
