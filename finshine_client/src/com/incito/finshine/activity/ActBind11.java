package com.incito.finshine.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.core.util.AppToast;
import com.android.core.util.StrUtil;
import com.custom.view.CommFlipDot;
import com.custom.view.DlgCommFilter;
import com.custom.view.DlgCommFilter.RefreshFilterListener;
import com.custom.view.MyViewPager;
import com.custom.view.PopMarketSign;
import com.custom.view.PopValidityDatePicker;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterCustomerBaseDetailViewPager;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.Bank;
import com.incito.finshine.entity.BindingAgreement;
import com.incito.finshine.entity.ContractBaseData;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.IDType;
import com.incito.finshine.entity.MarketCsOrder;
import com.incito.finshine.entity.MarketStateReslut;
import com.incito.finshine.entity.User;
import com.incito.finshine.manager.BitmapManager;
import com.incito.finshine.manager.JsonParse;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FileSuccessReslut;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.wisdomsdk.event.EventBus;
import com.incito.wisdomsdk.net.http.RequestParams;
import com.incito.wisdomsdk.utils.BitmapUtils;

public class ActBind11 extends FragmentDetail implements OnClickListener, OnItemSelectedListener {
	public View view, view1, view2;
	private MyViewPager mPager;// 页卡内容
	private List<View> listViews; // Tab页面列表
	public CommFlipDot flipDot = null;
	private MarketStateReslut marketResult = null;
	private int currentType = 0;// 照片的类型
	// 标记照片是否上传 （是否有图片出来）
	private boolean upLoadOpp = false;
	private boolean uploadDef = false;
	private boolean uploadReal = false;
	private boolean uploadAuto = false;

	private static int currentView = 0;// 判断当前是哪一个
	private MarketCsOrder marketCs;
	private HttpUtils httpUtils = null;
	private BindingAgreement bindingAgreement = null;
	private boolean isUpdateSuccess = false;
	private boolean hasSMS = false;
	private File albumTakePhotoFile;

	// http用
	private static final int GET_BASE_INFO = 0;
	private static final int DOWNLOAD_IDENTIFICATION_OPPOSIVE = 1;
	private static final int DOWNLOAD_IDENTIFICATION_DEFENSIVE = 2;
	private static final int DOWNLOAD_REAL_PHOTO = 3;
	private static final int DOWNLOAD_AUTO_GRAPH = 4;
	private static final int DOWNLOAD_ASSET_CONTRACT = 5;
	private static final int SEND_SMS = 7;
	private static final int UPDATE_BASE_INFO = 8;
	private static final int UPDATE_ATTACHMENT = 9;

	private static final int UPLOAD_IDENTIFICATION_OPPOSIVE = 11;
	private static final int UPLOAD_IDENTIFICATION_DEFENSIVE = 12;
	private static final int UPLOAD_REAL_PHOTO = 13;
	private static final int UPLOAD_AUTO_GRAPH = 14;

	private Button btnPre, btnNext;

	private TextView txtCsName1, txtCsName2, txtSalesName, textcontent, textDate;
	private ImageView imageSign;
	private EditText editName;
	private Spinner spinnerIdenfication;
	private EditText idnCode;
	private EditText effectiveDate;
	private EditText etPhone;
	private EditText smsVerfication;

	private Button uploadOpp;
	private Button uploadDefen;
	private Button uploadIcon;
	private Button btnGetSms;
	private ImageView ivOpposive;
	private ImageView ivDefensive;
	private ImageView csIcon;
	private PopValidityDatePicker popDatePicker;
	// private String STOREPATH;

	private StringBuffer pictureMiddleName = new StringBuffer();

	public static ActBind11 newInstance(int id, int position) {

		ActBind11 f = new ActBind11();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);
		// marketCs = data;
		currentView = position;
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (container == null) {
			return null;
		}
		EventBus.getDefault().register(this, "setDatePicker");
		EventBus.getDefault().register(this, "setImageView");
		view = inflater.inflate(R.layout.act_bind11, container, false);
		flipDot = new CommFlipDot(getActivity(), 2, (LinearLayout) view.findViewById(R.id.ltDot));
		initViewPager();
		return view;
	}

	private void initViewPager() {

		mPager = (MyViewPager) view.findViewById(R.id.viewPagerCustomerDetail);
	    mPager.setPagingEnabled(false);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getActivity().getLayoutInflater();
		view1 = mInflater.inflate(R.layout.frag_customer_market_bind_first11, null);
		listViews.add(view1);
		view2 = mInflater.inflate(R.layout.frag_customer_market_bind_second11, null);
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

	// 激活控件
	private void initUI() {
		btnPre = (Button) view.findViewById(R.id.btnPre);
		btnPre.setOnClickListener(this);
		btnNext = (Button) view.findViewById(R.id.btnNext);
		btnNext.setOnClickListener(this);
		txtCsName1 = (TextView) view1.findViewById(R.id.txtCsName);
		txtCsName2 = (TextView) view2.findViewById(R.id.txtCsName);
		txtSalesName = (TextView) view1.findViewById(R.id.txtSalesName);
		textcontent = (TextView) view2.findViewById(R.id.textcontent);
		imageSign = (ImageView) view2.findViewById(R.id.imageSign);
		imageSign.setOnClickListener(this);
		textDate = (TextView) view2.findViewById(R.id.textDate);
		editName = (EditText) view1.findViewById(R.id.editName);
		spinnerIdenfication = (Spinner) view1.findViewById(R.id.spinnerIdenfication);
		idnCode = (EditText) view1.findViewById(R.id.idenficationNumber);
		effectiveDate = (EditText) view1.findViewById(R.id.btnDate);
		effectiveDate.setKeyListener(null);//禁止手写
		etPhone = (EditText) view1.findViewById(R.id.etPhone);
		smsVerfication = (EditText) view1.findViewById(R.id.etSmsVaerfication);

		ivOpposive = (ImageView) view1.findViewById(R.id.ivOposive);
		ivDefensive = (ImageView) view1.findViewById(R.id.ivdefensive);
		csIcon = ((ImageView) view1.findViewById(R.id.csIcon));
		uploadIcon = (Button) view1.findViewById(R.id.btnUploadIcon);
		uploadOpp = (Button) view1.findViewById(R.id.btnPositive);
		uploadDefen = (Button) view1.findViewById(R.id.btndefensive);
		btnGetSms = (Button) view1.findViewById(R.id.btnGetVerfication);// 按钮

		ivOpposive.setOnClickListener(this);
		ivDefensive.setOnClickListener(this);
		uploadOpp.setOnClickListener(this);
		csIcon.setOnClickListener(this);
		uploadIcon.setOnClickListener(this);
		uploadDefen.setOnClickListener(this);

		btnGetSms.setOnClickListener(this);
		editName.setOnClickListener(this);
		idnCode.setOnClickListener(this);
		effectiveDate.setOnClickListener(this);
		effectiveDate.setKeyListener(null);
		etPhone.setOnClickListener(this);
		smsVerfication.setOnClickListener(this);
		initSpinner(spinnerIdenfication, getResources().getStringArray(R.array.idenfication_type), false);

		marketCs = ((ActCustomerMarketProgress) getActivity()).getMarketCs();
		if (marketCs != null) {
			marketResult = marketCs.getMarketReslut();
			bindingAgreement = ((ActCustomerMarketProgress) getActivity()).getBindingAgreement();
			if (bindingAgreement == null) {
				initHttpType(GET_BASE_INFO);
			} else {
				initData();
				downIcon();
				isEdit();
			}
		}
	}

	private void initHttpType(final int currentType) {

		try {
			JSONObject params = new JSONObject();
			RequestParams reqParams = new RequestParams();
			int reqId = 0;
			RequestType reqType = null;
			switch (currentType) {
			case GET_BASE_INFO:
				// 获取绑定协议资料
				reqType = RequestType.POST;
				reqId = RequestDefine.MARKET_BIND_BASEINFO;
				params.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
				params.put("customerId", marketCs.getCustomerId());
				params.put("salesOrderId", marketCs.getSalesOrderId());

				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(true);
				break;

			case DOWNLOAD_IDENTIFICATION_OPPOSIVE:
				// 获取证件正面照
				reqType = RequestType.POST;
				reqId = RequestDefine.MARKET_BIND_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", bindingAgreement.getId());
				params.put("transactionType", "BINDINGAGREEMENT");
				params.put("moduleType", "POSITIVEPHOTO");

				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;

			case DOWNLOAD_IDENTIFICATION_DEFENSIVE:
				// 获取证件反面照
				reqType = RequestType.POST;
				reqId = RequestDefine.MARKET_BIND_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", bindingAgreement.getId());
				params.put("transactionType", "BINDINGAGREEMENT");
				params.put("moduleType", "NEGATIVEPHOTO");

				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;

			case DOWNLOAD_REAL_PHOTO:
				// 获取真实照片
				reqType = RequestType.POST;
				reqId = RequestDefine.MARKET_BIND_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", bindingAgreement.getId());
				params.put("transactionType", "BINDINGAGREEMENT");
				params.put("moduleType", "REALPHOTO");

				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;

			case DOWNLOAD_AUTO_GRAPH:
				// 获取电子签名
				reqType = RequestType.POST;
				reqId = RequestDefine.MARKET_BIND_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", bindingAgreement.getId());
				params.put("transactionType", "BINDINGAGREEMENT");
				params.put("moduleType", "AUTOGRAPH");

				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;

			case SEND_SMS:
				// 发送验证码
				reqType = RequestType.POST;
				reqId = RequestDefine.MARKET_RQ_SMS_VERFICATION;
				params.put("phoneNo", bindingAgreement.getCustomer().getCellPhone1());
				params.put("tranTypeFk", Constant.BIND_AGREE_MENT_TRAN);
				params.put("tranFk", marketCs.getSalesOrderId());
				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				break;

			case UPDATE_BASE_INFO:
				// 更新基本信息
				reqType = RequestType.PUT;
				reqId = RequestDefine.MARKET_BIND_BASEINFO;
				params.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
				params.put("customerId", bindingAgreement.getCustomer().getId());
				params.put("salesOrderId", marketCs.getSalesOrderId());
				params.put("customerName", bindingAgreement.getCustomerName());
				params.put("idTypeId", bindingAgreement.getIdType().getId());
				params.put("idNumber", bindingAgreement.getIdNumber());
				params.put("idDateOfExpire", DateUtil.formatDate2String(new Date(bindingAgreement.getIdDateOfExpire()),
						DateUtil.FORMAT_YYYY_MM_DD));//
				params.put("cellphone", bindingAgreement.getCellphone());
				params.put("verificationCode", smsVerfication.getText().toString()); // 手机验证码
				params.put("dateOfSign", DateUtil.formatDate2String(new Date(bindingAgreement.getDateOfSign()),
						DateUtil.FORMAT_YYYY_MM_DD));//
				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(true);
				break;

			case UPDATE_ATTACHMENT:
				// 更新证件信息
				reqType = RequestType.POST;
				reqId = RequestDefine.MARKET_BIND_UPDATE_ATTACHMENT;
				reqParams.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID) + "");
				reqParams.put("customerId", marketCs.getCustomerId() + "");
				reqParams.put("salesOrderId", marketCs.getSalesOrderId() + "");
				try {
					reqParams.put("idPositivePhoto",
							CommonUtil.getFile(Constant.CERTIFICATE, Constant.UP_CER_OPP + pictureMiddleName));
					reqParams.put("idNegativePhoto",
							CommonUtil.getFile(Constant.CERTIFICATE, Constant.UP_CER_DEF + pictureMiddleName));
					reqParams.put("realPicture",
							CommonUtil.getFile(Constant.CERTIFICATE, Constant.UP_REAL_PHOTO + pictureMiddleName));
					reqParams.put("autograph",
							CommonUtil.getFile(Constant.CERTIFICATE, Constant.UP_AUTO_GRAPH + pictureMiddleName));
				} catch (FileNotFoundException e1) {
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
						// 获取绑定协议基本信息
						if (Request.RESLUT_OK.equals(states)) {
							try {
								JSONObject obj = response.getJSONObject("item");
								Gson gson = new Gson();
								bindingAgreement = gson.fromJson(obj.toString(), BindingAgreement.class);
								JSONObject customerObj = obj.getJSONObject("customer");
								Customer customer = gson.fromJson(customerObj.toString(), Customer.class);
								JSONObject userObj = obj.getJSONObject("salesman");
								User user = gson.fromJson(userObj.toString(), User.class);
								JSONObject idTypeObj = obj.getJSONObject("idType");
								IDType idType = gson.fromJson(idTypeObj.toString(), IDType.class);
								bindingAgreement.setId(obj.getInt("id"));
								bindingAgreement.setCustomer(customer);
								bindingAgreement.setSalesman(user);
								bindingAgreement.setCustomerName(obj.getString("customerName"));
								bindingAgreement.setIdType(idType);
								bindingAgreement.setIdNumber(obj.getString("idNumber"));
								bindingAgreement.setIdDateOfExpire(obj.getLong("idDateOfExpire"));
								bindingAgreement.setCellphone(obj.getString("cellphone"));
								bindingAgreement.setContent(obj.getString("content"));
								bindingAgreement.setDateOfSign(obj.getLong("dateOfSign"));

							} catch (JSONException e) {
								e.printStackTrace();
							}

							((ActCustomerMarketProgress) getActivity()).setBindingAgreement(bindingAgreement);
							initData();
							downIcon();
							isEdit();
						}
						break;

					case UPDATE_BASE_INFO:
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

						if (isUpdateSuccess) {// 成功 跳转

							marketResult = JsonParse.getMarketStepResluts(response);
							if (marketCs != null) {
								marketCs.setMarketReslut(marketResult);
								// marketCs.setSalesOrderId(marketResult.getSalesOrderId());
								marketCs.setMarketingStatusId(marketResult.getMarketingStatusId());
								((ActCustomerMarketProgress) getActivity()).setMarketCsOrder(marketCs);
							}

							CommonUtil.renameFile(Constant.CERTIFICATE, Constant.DOWN_CER_OPP + pictureMiddleName,
									Constant.UP_CER_OPP + pictureMiddleName);
							CommonUtil.renameFile(Constant.CERTIFICATE, Constant.DOWN_CER_DEF + pictureMiddleName,
									Constant.UP_CER_DEF + pictureMiddleName);
							CommonUtil.renameFile(Constant.CERTIFICATE, Constant.DOWN_REAL_PHOTO + pictureMiddleName,
									Constant.UP_REAL_PHOTO + pictureMiddleName);
							CommonUtil.renameFile(Constant.CERTIFICATE, Constant.DOWN_AUTO_GRAPH + pictureMiddleName,
									Constant.UP_AUTO_GRAPH + pictureMiddleName);

							((ActCustomerMarketProgress) getActivity()).showDetails(
									ActCustomerMarketProgress.F_SIGN_FIRST, 0);
						} else {
							AppToast.ShowToast("上传信息失败");
						}
						break;

					case SEND_SMS:
						if (Request.RESLUT_OK.equals(states)) {
							hasSMS = true;
							AppToast.ShowToast("获取短信验证码成功,请您注意查收");
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
						case DOWNLOAD_IDENTIFICATION_OPPOSIVE:
							// 获取证件照正面
							file = Constant.CERTIFICATE;
							fileName = Constant.DOWN_CER_OPP + pictureMiddleName;
							CommonUtil.storeFile(binaryData, file, fileName);
							Bitmap bitMap1 = BitmapManager.decodeSampledBitmapFromFile(file + "/" + fileName,
									Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
							ivOpposive.setImageBitmap(bitMap1);
							upLoadOpp = true;
							break;

						case DOWNLOAD_IDENTIFICATION_DEFENSIVE:
							// 获取证件照反面
							file = Constant.CERTIFICATE;
							fileName = Constant.DOWN_CER_DEF + pictureMiddleName;
							CommonUtil.storeFile(binaryData, file, fileName);
							Bitmap bitMap2 = BitmapManager.decodeSampledBitmapFromFile(file + "/" + fileName,
									Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
							ivDefensive.setImageBitmap(bitMap2);
							uploadDef = true;
							break;

						case DOWNLOAD_REAL_PHOTO:
							// 获取真实照片
							file = Constant.CERTIFICATE;
							fileName = Constant.DOWN_REAL_PHOTO + pictureMiddleName;
							CommonUtil.storeFile(binaryData, file, fileName);
							Bitmap bitMap3 = BitmapManager.decodeSampledBitmapFromFile(file + "/" + fileName,
									Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
							csIcon.setImageBitmap(bitMap3);
							uploadReal = true;
							break;

						case DOWNLOAD_AUTO_GRAPH:
							// 获取电子签名
							file = Constant.CERTIFICATE;
							fileName = Constant.DOWN_AUTO_GRAPH + pictureMiddleName;
							CommonUtil.storeFile(binaryData, file, fileName);
							Bitmap bitMap4 = BitmapManager.decodeSampledBitmapFromFile(file + "/" + fileName,
									Constant.SIGN_WIDTH, Constant.SIGN_HEIGHT);
							imageSign.setImageBitmap(bitMap4);
							uploadAuto = true;
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

	// 下载图片附件
	private void downIcon() {
		// 查询合同信息
		if (bindingAgreement == null) {
			return;
		}
		showdefaultIcon();
	}

	// 显示本地的，然后网络加载，加载成功后覆盖本地文件
	private void showdefaultIcon() {
		if (bindingAgreement.getCustomer().getId() <= 0) {
			return;
		}
		if (CommonUtil.isNotEmptyfile(Constant.CERTIFICATE + Constant.DOWN_CER_OPP + pictureMiddleName)) {
			// 证件照正面
			ivOpposive.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.CERTIFICATE
					+ Constant.DOWN_CER_OPP + pictureMiddleName, Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			upLoadOpp = true;
		} else {
			initHttpType(DOWNLOAD_IDENTIFICATION_OPPOSIVE);
		}

		if (CommonUtil.isNotEmptyfile(Constant.CERTIFICATE + Constant.DOWN_CER_DEF + pictureMiddleName)) {
			// 证件照反面
			ivDefensive.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.CERTIFICATE
					+ Constant.DOWN_CER_DEF + pictureMiddleName, Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			uploadDef = true;
		} else {
			initHttpType(DOWNLOAD_IDENTIFICATION_DEFENSIVE);
		}

		if (CommonUtil.isNotEmptyfile(Constant.CERTIFICATE + Constant.DOWN_REAL_PHOTO + pictureMiddleName)) {
			// 获取真实照片
			csIcon.setImageBitmap(BitmapManager
					.decodeSampledBitmapFromFile(Constant.CERTIFICATE + Constant.DOWN_REAL_PHOTO + pictureMiddleName,
							Constant.USER_ICON_WIDTH, Constant.USER_ICON_HEIGHT));
			uploadReal = true;
		} else {
			initHttpType(DOWNLOAD_REAL_PHOTO);
		}
		if (CommonUtil.isNotEmptyfile(Constant.CERTIFICATE + Constant.DOWN_AUTO_GRAPH + pictureMiddleName)) {
			// 签名
			imageSign.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.CERTIFICATE
					+ Constant.DOWN_AUTO_GRAPH + pictureMiddleName, Constant.SIGN_WIDTH, Constant.SIGN_HEIGHT));
			uploadAuto = true;
		} else {
			initHttpType(DOWNLOAD_AUTO_GRAPH);
		}
	}

	private void initData() {

		if (bindingAgreement == null) {
			return;
		}

		pictureMiddleName.append(bindingAgreement.getCustomer().getId() + "-");
		pictureMiddleName.append(marketCs.getSalesOrderId() + "-");
		pictureMiddleName.append(bindingAgreement.getId() + "-2.jpg");

		txtSalesName.setText(bindingAgreement.getSalesman().getName());
		txtCsName1.setText(bindingAgreement.getCustomerName());
		editName.setText(bindingAgreement.getCustomerName());
		spinnerIdenfication.setSelection(bindingAgreement.getIdType().getId() > 1 ? (bindingAgreement.getIdType()
				.getId() - 1) : 0);
		idnCode.setText(StrUtil.isBlank(bindingAgreement.getIdNumber()) ? "" : bindingAgreement.getIdNumber());

		// SimpleDateFormat("yyyy-MM-dd").format(bindingAgreement.getIdDateOfExpire()));
		if ("9999-12-31".equals(DateUtil.formatDate2String(new Date(bindingAgreement.getIdDateOfExpire()),
				DateUtil.FORMAT_YYYY_MM_DD))) {
			effectiveDate.setText("长期"); // 证件有效期
		} else {
			effectiveDate.setText(DateUtil.formatDate2String(new Date(bindingAgreement.getIdDateOfExpire()),
					DateUtil.FORMAT_YYYY_MM_DD_ZH));
		}

		etPhone.setText(bindingAgreement.getCellphone());

		textcontent.setText(bindingAgreement.getContent());// 分页不？
		txtCsName2.setText(bindingAgreement.getCustomerName());
		textDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(bindingAgreement.getDateOfSign()));
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
					case UPLOAD_IDENTIFICATION_OPPOSIVE: // 上传正面证件照
						albumTakePhotoFile = CommonUtil.getFile(Constant.CERTIFICATE, Constant.UP_CER_OPP
								+ pictureMiddleName);
						break;
					case UPLOAD_IDENTIFICATION_DEFENSIVE: // 上传反面证件照
						albumTakePhotoFile = CommonUtil.getFile(Constant.CERTIFICATE, Constant.UP_CER_DEF
								+ pictureMiddleName);
						break;
					case UPLOAD_REAL_PHOTO: // 上传真实照片
						albumTakePhotoFile = CommonUtil.getFile(Constant.CERTIFICATE, Constant.UP_REAL_PHOTO
								+ pictureMiddleName);
						break;

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

	// 显示图片 从本地
	private void showIcon(Intent data, Bitmap bitmap) {
		bitmap = CommonUtil.saveLocalFile(data, bitmap);
		switch (currentType) {
		case UPLOAD_IDENTIFICATION_OPPOSIVE:
			CommonUtil.storeFile(BitmapUtils.bmpToByteArray(CommonUtil.saveLocalFile(data, bitmap), false),
					Constant.CERTIFICATE, Constant.UP_CER_OPP + pictureMiddleName);
			ivOpposive.setImageBitmap(bitmap);
			upLoadOpp = true;
			break;
		case UPLOAD_IDENTIFICATION_DEFENSIVE:
			CommonUtil.storeFile(BitmapUtils.bmpToByteArray(CommonUtil.saveLocalFile(data, bitmap), false),
					Constant.CERTIFICATE, Constant.UP_CER_DEF + pictureMiddleName);
			ivDefensive.setImageBitmap(bitmap);
			uploadDef = true;
			break;

		case UPLOAD_REAL_PHOTO: // 真实照
			CommonUtil.storeFile(BitmapUtils.bmpToByteArray(CommonUtil.saveLocalFile(data, bitmap), false),
					Constant.CERTIFICATE, Constant.UP_REAL_PHOTO + pictureMiddleName);

			csIcon.setImageBitmap(bitmap);
			uploadReal = true;
			break;

		case UPLOAD_AUTO_GRAPH: // 签名
			CommonUtil.storeFile(BitmapUtils.bmpToByteArray(CommonUtil.saveLocalFile(data, bitmap), false),
					Constant.CERTIFICATE, Constant.UP_AUTO_GRAPH + pictureMiddleName);
			imageSign.setImageBitmap(bitmap);
			uploadAuto = true;
			break;
		}
	}

	private void switchView(boolean isPre) {
		if (isPre) // 上一页
		{
			if (currentView == 0) {
				// ((ActCustomerMarketProgress)
				// getActivity()).showDetails(ActCustomerMarketProgress.F_PRODUCT_COMPARE,
				// 0);
				((ActCustomerMarketProgress) getActivity()).toProductDetail();
			} else {
				currentView--;
				mPager.setCurrentItem(currentView, true);
			}
		} else // 下一页
		{
			if (currentView == 1) {
				if (marketCs.getMarketingStatusId() < 3) {// 编辑模式
					if (isFullDataSecond()) {
						initHttpType(UPDATE_BASE_INFO);
					}
				} else {// 预览模式直接跳
					((ActCustomerMarketProgress) getActivity()).showDetails(ActCustomerMarketProgress.F_SIGN_FIRST, 0);
				}

			} else {
				if (marketCs.getMarketingStatusId() < 3) {// 编辑模式
					if (isFullDataFirst()) {
						currentView++;
						mPager.setCurrentItem(currentView, true);
					}
				} else {// 预览模式直接跳
					currentView++;
					mPager.setCurrentItem(currentView, true);
				}

			}
		}
	}

	public void isEdit() {
		if (marketCs.getMarketingStatusId() > 2) {
			editName.setEnabled(false);
			spinnerIdenfication.setEnabled(false);
			idnCode.setEnabled(false);
			effectiveDate.setEnabled(false);
			etPhone.setEnabled(false);
			smsVerfication.setEnabled(false);
			imageSign.setEnabled(false);
			uploadOpp.setEnabled(false);
			uploadOpp.setEnabled(false);
			uploadDefen.setEnabled(false);
			uploadIcon.setEnabled(false);
			btnGetSms.setEnabled(false);
			ivOpposive.setEnabled(false);
			ivDefensive.setEnabled(false);
			csIcon.setEnabled(false);
		}
	}

	// Spinner设置
	private void initSpinner(Spinner sp, String[] dataList, boolean isResaveDefaultValue) {

		if (isResaveDefaultValue) {
			sp.setSelection(0, true);
		} else {
			ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.common_spinner_item, dataList);// android.R.layout.simple_spinner_item
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//
			sp.setAdapter(adapter);
			sp.setOnItemSelectedListener(this);
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == getActivity().RESULT_OK) {
			try {
				Bitmap selectImage = null;
				if (requestCode == Constant.REQUEST_ALBUM_PHOTO_RESLUT) {
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
						doCropPhoto(new File(file));

					} else {
						Uri url = data.getData();
						try {
							Intent intent = getCropImageIntent(url);
							startActivityForResult(intent, Constant.PHOTO_PICKED_WITH_DATA);
						} catch (Exception e) {
						}
					}
				} else if (requestCode == Constant.REQUEST_TAKE_PHOTO) {
					doCropPhoto(albumTakePhotoFile);

				} else if (requestCode == Constant.PHOTO_PICKED_WITH_DATA) {
					showIcon(data, selectImage);
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

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.btnPositive:
			seleIcon(UPLOAD_IDENTIFICATION_OPPOSIVE);
			break;
		case R.id.btndefensive:
			seleIcon(UPLOAD_IDENTIFICATION_DEFENSIVE);
			break;

		case R.id.btnUploadIcon:
			seleIcon(UPLOAD_REAL_PHOTO);
			break;

		case R.id.btnDate:
			if (popDatePicker == null) {
				popDatePicker = new PopValidityDatePicker(getActivity(), effectiveDate);
			}
			
			popDatePicker.showPopWindow();
			break;

		case R.id.imageSign:
			PopMarketSign pms = new PopMarketSign(this.getActivity(), v);
			pms.showPopWindow();
			break;
		case R.id.btnPre:
			switchView(true);
			break;
		case R.id.btnNext:
			switchView(false);
			break;
		case R.id.btnGetVerfication:
			initHttpType(SEND_SMS);
			break;
		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	public void setImageView(Bitmap bm) {
		imageSign.setImageBitmap(bm);
		CommonUtil.storeFile(BitmapUtils.bmpToByteArray(bm, false), Constant.CERTIFICATE, Constant.UP_AUTO_GRAPH
				+ pictureMiddleName);
		uploadAuto = true;
	}

	public void setDatePicker(String value) {
		effectiveDate.setText(value);
	}

	/**
	 * @description 数据有效性检查
	 * @author Andy.fang
	 * @createDate 2014年8月11日
	 * @return
	 */
	private boolean isFullDataFirst() {
		if (StrUtil.isBlank(editName.getText().toString())) {
			AppToast.ShowToast("请输入客户姓名");
			return false;
		} else if (StrUtil.isBlank(spinnerIdenfication.getSelectedItemPosition() + "")) {
			AppToast.ShowToast("请选择证件的类型");
			return false;
		} else if (StrUtil.isBlank(idnCode.getText().toString())) {
			AppToast.ShowToast("请输入证件号");
			return false;
		} else if (StrUtil.isBlank(effectiveDate.getText().toString())) {
			AppToast.ShowToast("请输入证件有效期");
			return false;
		} else if (StrUtil.isBlank(etPhone.getText().toString())) {
			AppToast.ShowToast("请输入手机号");
			return false;
		} else if (StrUtil.isBlank(smsVerfication.getText().toString())) {
			AppToast.ShowToast("请输入验证码");
			return false;
		} else if (!upLoadOpp) {
			AppToast.ShowToast("请上传证件正面照");
			return false;
		} else if (!uploadDef) {
			AppToast.ShowToast("请上传银行卡反面照");
			return false;
		} else if (!uploadReal) {
			AppToast.ShowToast("请上传真实照片");
			return false;
		}
		bindingAgreement.setCustomerName(editName.getText().toString());
		bindingAgreement.getIdType().setId(spinnerIdenfication.getSelectedItemPosition() + 1);
		bindingAgreement.setIdNumber(idnCode.getText().toString());
		if (effectiveDate.getText().toString().equals("长期")) {
			bindingAgreement.setIdDateOfExpire(DateUtil.formatString2Date("9999年12月31日",DateUtil.FORMAT_YYYY_MM_DD_ZH).getTime());
		} else {
			bindingAgreement.setIdDateOfExpire(DateUtil.formatString2Date(effectiveDate.getText().toString(),
					DateUtil.FORMAT_YYYY_MM_DD_ZH).getTime());// 证件有效期
		}
		bindingAgreement.setCellphone(etPhone.getText().toString());
		return true;
	}

	/**
	 * @description 数据有效性检查
	 * @author Andy.fang
	 * @createDate 2014年8月11日
	 * @return
	 */
	private boolean isFullDataSecond() {
		if (!uploadAuto) {
			AppToast.ShowToast("请上传签名");
			return false;
		}
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

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
		}

		@Override
		public void onPageSelected(int curPage) {
			if (flipDot != null) {
				flipDot.setSeletion(curPage);
			}
		}

	}

}
