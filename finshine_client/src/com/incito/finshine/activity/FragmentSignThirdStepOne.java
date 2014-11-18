package com.incito.finshine.activity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.core.util.AppToast;
import com.android.core.util.SessionUtil;
import com.custom.view.CommFlipDot;
import com.custom.view.DlgCommFilter;
import com.custom.view.MyViewPager;
import com.custom.view.PopDatePicker;
import com.custom.view.PopMarketSign;
import com.custom.view.DlgCommFilter.RefreshFilterListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterCustomerBaseDetailViewPager;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.BindingAgreement;
import com.incito.finshine.entity.ContractBatch;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.MarketCsOrder;
import com.incito.finshine.entity.MarketStateReslut;
import com.incito.finshine.entity.TradingBusinessApplication;
import com.incito.finshine.entity.spinner.TradingBusinessApplicationParam;
import com.incito.finshine.manager.BitmapManager;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.JsonParse;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FileSuccessReslut;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.LogCito;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.wisdomsdk.event.EventBus;
import com.incito.wisdomsdk.net.http.RequestParams;
import com.incito.wisdomsdk.utils.BitmapUtils;

/**
 * NOTE：照片的名字中不应该以customerid作为唯一标识 图片存的路径：图片类型+上传/下载的标识图片名+客户id+
 * 销售订单ID+当前所处的步骤合同的id
 * <dl>
 * <dt>FragmentBindFirst.java</dt>
 * <dd>Description:客户营销 签订合同第三步 小一步 Fragment</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月16日 下午5:00:59</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class FragmentSignThirdStepOne extends FragmentDetail implements OnClickListener {

	View view;
	private MyViewPager mPager;// 页卡内容
	private List<View> listViews; // Tab页面列表
	private View view1, view2, view3, view4;
	public CommFlipDot flipDot = null;

	private static int currentView = 0;// 判断当前是哪一个

	/* 界面3-1 */
	private Spinner investmentType;
	private TextView proposer;
	private RadioGroup radioSex;
	private TextView IDType;
	private Spinner nationalityStatus;
	private TextView certificateValidity;
	private TextView certificateNumber;
	/* 界面3-2 */
	private EditText editBankName;
	private EditText editBankID;
	private TextView editBankCardID;
	private TextView editBankNameTotal;
	private Spinner editBillSendWay;
	private TextView txtPhobe;
	private EditText editBillSendAddress;
	private EditText editFax;
	private EditText editEmail;
	private EditText contactPhone;
	private EditText postCode;
	/* 界面3-3 */
	private TextView productName;
	private TextView txtAmount;
	private TextView txtCapitalAmount;
	private ImageView imgCredentOpp;
	private ImageView imgCredentDef;
	private ImageView imgBankOpp;
	private ImageView imgBankDef;
	/* 界面3-4 */
	private ImageView btnPlanTableSign;
	private TextView txtPlanTableSignDate;

	// private ContractBatch contractBatch;

	private TradingBusinessApplication trad = null;
	private TradingBusinessApplicationParam tradParam = null;

	private StringBuffer pictureMiddleName = new StringBuffer();// 注意
																// 这里的照片路径是上两个画面里带来的额
																// 不是这个里面的

	private HttpUtils httpUtils = null;

	// http用 感觉没有必要再请求数据
	// private static final int GET_BASE_INFO = 0;
	private static final int DOWNLOAD_CREDENT_OPPOSIVE = 1;
	private static final int DOWNLOAD_CREDENT_DEFENSIVE = 2;
	private static final int DOWNLOAD_BANK_OPPOSIVE = 3;
	private static final int DOWNLOAD_BANK_DEFENSIVE = 4;
	private static final int DOWNLOAD_INVESTOR_SIGN = 5;

	// 标记照片是否上传 （是否有图片出来）
	private boolean upLoadSign = false;
	// private static MarketCsOrder marketCs;
	// private MarketStateReslut marketResult;

	private MarketCsOrder marketCs;

	public static FragmentSignThirdStepOne newInstance(int id, int position) {

		FragmentSignThirdStepOne f = new FragmentSignThirdStepOne();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);
		currentView = position;
		// contractBatch = con;

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

		flipDot = new CommFlipDot(getActivity(), 4, (LinearLayout) view.findViewById(R.id.ltDot));

		initViewPager();
		EventBus.getDefault().register(this, "setImageView");

		// view = inflater.inflate(R.layout.act_sign_contract_3_1, container,
		// false);
		// initUI();
		return view;
	}

	private void initViewPager() {

		mPager = (MyViewPager) view.findViewById(R.id.viewPagerCustomerDetail);
		mPager.setPagingEnabled(false);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getActivity().getLayoutInflater();
		view1 = mInflater.inflate(R.layout.act_sign_contract_3_1, null);
		listViews.add(view1);
		view2 = mInflater.inflate(R.layout.act_sign_contract_3_2, null);
		listViews.add(view2);
		view3 = mInflater.inflate(R.layout.act_sign_contract_3_3, null);
		listViews.add(view3);
		view4 = mInflater.inflate(R.layout.act_sign_contract_3_4, null);
		listViews.add(view4);

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
		/* 界面3-1 */
		investmentType = (Spinner) view1.findViewById(R.id.investmentType);
		proposer = (TextView) view1.findViewById(R.id.proposer);
		radioSex = (RadioGroup) view1.findViewById(R.id.radioSex);
		IDType = (TextView) view1.findViewById(R.id.IDType);
		nationalityStatus = (Spinner) view1.findViewById(R.id.nationalityStatus);
		certificateValidity = (TextView) view1.findViewById(R.id.certificateValidity);
		certificateNumber = (TextView) view1.findViewById(R.id.certificateNumber);
		/* 界面3-2 */
		editBankName = (EditText) view2.findViewById(R.id.editBankName);
		editBankID = (EditText) view2.findViewById(R.id.editBankID);
		editBankCardID = (TextView) view2.findViewById(R.id.editBankCardID);
		editBankNameTotal = (TextView) view2.findViewById(R.id.editBankNameTotal);
		editBillSendWay = (Spinner) view2.findViewById(R.id.editBillSendWay);
		txtPhobe = (TextView) view2.findViewById(R.id.txtPhobe);
		editBillSendAddress = (EditText) view2.findViewById(R.id.editBillSendAddress);
		editFax = (EditText) view2.findViewById(R.id.editFax);
		editEmail = (EditText) view2.findViewById(R.id.editEmail);
		contactPhone = (EditText) view2.findViewById(R.id.contactPhone);
		postCode = (EditText) view2.findViewById(R.id.postCode);
		/* 界面3-3 */
		productName = (TextView) view3.findViewById(R.id.productName);
		txtAmount = (TextView) view3.findViewById(R.id.txtAmount);
		txtCapitalAmount = (TextView) view3.findViewById(R.id.txtCapitalAmount);
		imgCredentOpp = (ImageView) view3.findViewById(R.id.imgCredentOpp);
		imgCredentDef = (ImageView) view3.findViewById(R.id.imgCredentDef);
		imgBankOpp = (ImageView) view3.findViewById(R.id.imgBankOpp);
		imgBankDef = (ImageView) view3.findViewById(R.id.imgBankDef);

		/* 界面3-4 */
		btnPlanTableSign = (ImageView) view4.findViewById(R.id.btnPlanTableSign);
		btnPlanTableSign.setOnClickListener(this);
		txtPlanTableSignDate = (TextView) view4.findViewById(R.id.txtPlanTableSignDate);

		loadSpinner();
		initData();
		downIcon();
		isEdit();
	}

	private void initData() {

		trad = ((ActCustomerMarketProgress) getActivity()).getTrading();
		if (trad == null)
			return;

		pictureMiddleName.append(trad.getApplicant().getId() + "-");
		pictureMiddleName.append(trad.getSalesOrderId() + "-");
		pictureMiddleName.append(trad.getId() + "-5.jpg");

		investmentType.setSelection(trad.getAcceptanceType().getId() > 1 ? trad.getAcceptanceType().getId() - 1 : 0,
				true);
		proposer.setText(trad.getApplicantName());
		((RadioButton)radioSex.getChildAt(trad.getGender() > 1 ? 1 : 0)).setChecked(true);// 确认性别问题
		IDType.setText(trad.getIdType().getName());
		nationalityStatus.setSelection(trad.getNationality().getId() > 1 ? trad.getNationality().getId() - 1 : 0, true);
		
		if ("9999-12-31".equals(DateUtil.formatDate2String(new Date(trad.getIdDateOfExpire()),
				DateUtil.FORMAT_YYYY_MM_DD))) {
			certificateValidity.setText("长期"); // 证件有效期
		} else {
			certificateValidity.setText(DateUtil.formatDate2String(new Date(trad.getIdDateOfExpire()),
					DateUtil.FORMAT_YYYY_MM_DD_ZH));
		}
//		certificateValidity.setText(DateUtil.formatDate2String(new Date(trad.getIdDateOfExpire()),
//				DateUtil.FORMAT_YYYY_MM_DD_ZH));
		certificateNumber.setText(trad.getIdNumber());

		editBankName.setText(trad.getBankOwner());
		editBankID.setText(trad.getBankCode());
		editBankCardID.setText(trad.getBankAccount());
		editBankNameTotal.setText(trad.getBankFullName());
		editBillSendWay.setSelection(trad.getShippingMethod().getId() > 0 ? trad.getShippingMethod().getId() - 1 : 0,
				true);
		txtPhobe.setText(trad.getCellPhone());
		editBillSendAddress.setText(trad.getShippingAddress());
		editFax.setText(trad.getFaxNumber());
		editEmail.setText(trad.getEmail());

		contactPhone.setText(trad.getTelephone());
		postCode.setText(trad.getPostCode());

		productName.setText(trad.getProductName());
		txtAmount.setText(trad.getSubscribeUnits() + "");
		txtCapitalAmount.setText(trad.getCapitalLetters());

		// 投资人说明是本地写的目前
		txtPlanTableSignDate.setText(DateUtil.formatDate2String(new Date(trad.getDateOfSign()),
				DateUtil.FORMAT_YYYY_MM_DD_ZH));
	}

	// 下载图片附件
	private void downIcon() {
		if ((trad == null)) {
			return;
		}
		// 本地有，显示本地的，没有网络加载，加载成功后覆盖本地文件
		if (CommonUtil.isNotEmptyfile(Constant.CERTIFICATE + Constant.DOWN_CER_OPP + pictureMiddleName)) {
			
			imgCredentOpp.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.CERTIFICATE
					+ Constant.DOWN_CER_OPP + pictureMiddleName, Constant.COMMOM_WIDTH, Constant.COMMOM_WIDTH));
		} else {
			initHttpType(DOWNLOAD_CREDENT_OPPOSIVE);
		}

		if (CommonUtil.isNotEmptyfile(Constant.CERTIFICATE + Constant.DOWN_CER_DEF + pictureMiddleName)) {
			imgCredentDef.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.CERTIFICATE
					+ Constant.DOWN_CER_DEF + pictureMiddleName, Constant.COMMOM_WIDTH, Constant.COMMOM_WIDTH));
		} else {
			initHttpType(DOWNLOAD_CREDENT_DEFENSIVE);
		}

		if (CommonUtil.isNotEmptyfile(Constant.BANKCARD + Constant.DOWN_BANK_OPP + pictureMiddleName)) {
			imgBankOpp.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.BANKCARD 
					+ Constant.DOWN_BANK_OPP + pictureMiddleName, Constant.COMMOM_WIDTH, Constant.COMMOM_WIDTH));
		} else {
			initHttpType(DOWNLOAD_BANK_OPPOSIVE);
		}

		if (CommonUtil.isNotEmptyfile(Constant.BANKCARD + Constant.DOWN_BANK_DEF + pictureMiddleName)) {
			imgBankDef.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.BANKCARD 
					+ Constant.DOWN_BANK_DEF + pictureMiddleName, Constant.COMMOM_WIDTH, Constant.COMMOM_WIDTH));
		} else {
			initHttpType(DOWNLOAD_BANK_DEFENSIVE);
		}

		if (CommonUtil.isNotEmptyfile(Constant.INVESTOR_SIGN + Constant.DOWN_INVESTOR_SIGN + pictureMiddleName)) {
			btnPlanTableSign.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.INVESTOR_SIGN + "/"
					+ Constant.DOWN_INVESTOR_SIGN + pictureMiddleName, Constant.SIGN_WIDTH, Constant.SIGN_HEIGHT));
			upLoadSign = true;
		} else if (CommonUtil.isNotEmptyfile(Constant.INVESTOR_SIGN + Constant.UP_INVESTOR_SIGN + pictureMiddleName)) {
			btnPlanTableSign.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.INVESTOR_SIGN + "/"
					+ Constant.UP_INVESTOR_SIGN + pictureMiddleName, Constant.SIGN_WIDTH, Constant.SIGN_HEIGHT));
			upLoadSign = true;
		} else {
			initHttpType(DOWNLOAD_INVESTOR_SIGN);
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
			case DOWNLOAD_CREDENT_OPPOSIVE:
				// 证件照正面
				reqId = RequestDefine.MARKET_SC_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", trad.getId());
				params.put("transactionType", "TRADINGBUSINESS");
				params.put("moduleType", "IDPOSITIVEPHOTO");
				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;

			case DOWNLOAD_CREDENT_DEFENSIVE:
				// 证件照反面
				reqId = RequestDefine.MARKET_SC_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", trad.getId());
				params.put("transactionType", "TRADINGBUSINESS");
				params.put("moduleType", "IDNEGATIVEPHOTO");
				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;

			case DOWNLOAD_BANK_OPPOSIVE:
				// 银行卡正面
				reqId = RequestDefine.MARKET_SC_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", trad.getId());
				params.put("transactionType", "TRADINGBUSINESS");
				params.put("moduleType", "BANKPOSITIVEPHOTO");
				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;

			case DOWNLOAD_BANK_DEFENSIVE:
				// 银行卡反面
				reqId = RequestDefine.MARKET_SC_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", trad.getId());
				params.put("transactionType", "TRADINGBUSINESS");
				params.put("moduleType", "BANKNEGATIVEPHOTO");
				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;

			case DOWNLOAD_INVESTOR_SIGN:
				// 获取客户签名
				reqId = RequestDefine.MARKET_SC_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", trad.getId());
				params.put("transactionType", "TRADINGBUSINESS");
				params.put("moduleType", "AUTOGRAPH");

				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;

			}

			httpUtils.setSuccessListener(new SuccessReslut() {

				@Override
				public void getResluts(JSONObject response) {

				}
			});

			httpUtils.setFileSuccessListener(new FileSuccessReslut() {

				@Override
				public void getResluts(int responeCode, byte[] binaryData) {

					String file = "";
					String fileName = "";

					if (responeCode == 200 && binaryData != null && binaryData.length > 0) {

						switch (currentType) {
						case DOWNLOAD_CREDENT_OPPOSIVE:
							file = Constant.CERTIFICATE;
							fileName = Constant.DOWN_CER_OPP + pictureMiddleName;
							CommonUtil.storeFile(binaryData, file, fileName);
							Bitmap bitMap1 = BitmapManager.decodeSampledBitmapFromFile(file + "/" + fileName,
									Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
							imgCredentOpp.setImageBitmap(bitMap1);
							break;
						case DOWNLOAD_CREDENT_DEFENSIVE:
							file = Constant.CERTIFICATE;
							fileName = Constant.DOWN_CER_DEF + pictureMiddleName;
							CommonUtil.storeFile(binaryData, file, fileName);
							Bitmap bitMap2 = BitmapManager.decodeSampledBitmapFromFile(file + "/" + fileName,
									Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
							imgCredentDef.setImageBitmap(bitMap2);
							break;
						case DOWNLOAD_BANK_OPPOSIVE:
							file = Constant.BANKCARD;
							fileName = Constant.DOWN_BANK_OPP + pictureMiddleName;
							CommonUtil.storeFile(binaryData, file, fileName);
							Bitmap bitMap3 = BitmapManager.decodeSampledBitmapFromFile(file + "/" + fileName,
									Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
							imgBankOpp.setImageBitmap(bitMap3);
							break;
						case DOWNLOAD_BANK_DEFENSIVE:
							file = Constant.BANKCARD;
							fileName = Constant.DOWN_BANK_DEF + pictureMiddleName;
							CommonUtil.storeFile(binaryData, file, fileName);
							Bitmap bitMap4 = BitmapManager.decodeSampledBitmapFromFile(file + "/" + fileName,
									Constant.COMMOM_WIDTH, Constant.COMMOM_HEIGHT);
							imgBankDef.setImageBitmap(bitMap4);
							break;

						case DOWNLOAD_INVESTOR_SIGN:
							// 获取客户签名
							file = Constant.INVESTOR_SIGN;
							fileName = Constant.DOWN_INVESTOR_SIGN + pictureMiddleName;
							CommonUtil.storeFile(binaryData, file, fileName);
							Bitmap bitMap5 = BitmapManager.decodeSampledBitmapFromFile(file + "/" + fileName,
									Constant.SIGN_WIDTH, Constant.SIGN_HEIGHT);
							btnPlanTableSign.setImageBitmap(bitMap5);
							upLoadSign = true;
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

	private void loadSpinner() {
		initSpinner(nationalityStatus, getResources().getStringArray(R.array.nationality_status), 0);
		initSpinner(investmentType, getResources().getStringArray(R.array.purchase_type), 0);
		initSpinner(editBillSendWay, getResources().getStringArray(R.array.purchase_send), 0);
	}

	private void initSpinner(Spinner sp, String[] dataList, int selectIndex) {
		ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.common_spinner_item, dataList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//
		sp.setAdapter(adapter);
		sp.setSelection(selectIndex, true);
	}

	private boolean isFullDataFirst() {
		trad.getAcceptanceType().setId(investmentType.getSelectedItemPosition() + 1);
		trad.setGender((short)((radioSex.getCheckedRadioButtonId() == R.id.man) ? 1 : 2));// 确认一下
		trad.getNationality().setId(nationalityStatus.getSelectedItemPosition() + 1);

		return true;
	}

	private boolean isFullDataSecond() {

		if (TextUtils.isEmpty(editBankName.getText().toString())) {
			CommonUtil.showToast("请填写银行户名", getActivity());
			return false;
		} else if (TextUtils.isEmpty(editBankID.getText().toString())) {
			CommonUtil.showToast("请填写银行行号", getActivity());
			return false;
		}

		// else if ((editBillSendWay.getSelectedItemPosition() != 2)
		// & (TextUtils.isEmpty(editBillSendAddress.getText().toString()))) {
		// CommonUtil.showToast("请填账单寄送地址", getActivity());
		// return false;
		// }

		trad.setBankOwner(editBankName.getText().toString());
		trad.setBankCode(editBankID.getText().toString());
		trad.getShippingMethod().setId(editBillSendWay.getSelectedItemPosition() + 1);
		trad.setShippingAddress(editBillSendAddress.getText().toString());
		trad.setFaxNumber(editFax.getText().toString());
		trad.setEmail(editEmail.getText().toString());
		trad.setTelephone(contactPhone.getText().toString());
		trad.setPostCode(postCode.getText().toString());

		isFullDataFirst();
		return true;
	}

	private boolean isFullDataFourth() {
		if (!upLoadSign) {
			CommonUtil.showToast("请先签名", getActivity());
			return false;
		}

		return true;
	}

	private boolean isEdit() {
		// marketResult =
		// ((ActCustomerMarketProgress)getActivity()).getMarketCs().getMarketReslut();
		// marketResult = JsonParse.getMarketReslut();

		marketCs = ((ActCustomerMarketProgress) getActivity()).getMarketCs();
		if (marketCs != null) {
			if (marketCs.getMarketingStatusId() > 3) {
				investmentType.setEnabled(false);
				
				radioSex.getChildAt(0).setClickable(false);
				radioSex.getChildAt(1).setClickable(false);
				nationalityStatus.setEnabled(false);

				editBankName.setEnabled(false);
				editBankID.setEnabled(false);
				editBillSendWay.setEnabled(false);
				editBillSendAddress.setEnabled(false);
				editFax.setEnabled(false);
				editEmail.setEnabled(false);
				contactPhone.setEnabled(false);
				postCode.setEnabled(false);
				btnPlanTableSign.setEnabled(false);
				return false;
			}
		}
		return true;
	}

	private void switchView(boolean isPre) {
		if (isPre) // 上一页
		{
			if (currentView == 0) {
				((ActCustomerMarketProgress) getActivity()).showDetails(ActCustomerMarketProgress.F_SIGN_SECOND, 8);
			} else {
				currentView--;
				mPager.setCurrentItem(currentView, true);
			}
		}

		else // 下一页
		{
			if (marketCs.getMarketingStatusId() < 4) {// 编辑模式
				if (currentView == 3) {
					if (isFullDataFourth()) {
						tradParam = new TradingBusinessApplicationParam(trad);
						// 没必要再对原数据set了吧？？？
						((ActCustomerMarketProgress) getActivity()).setTradingParam(tradParam);
						// CoreManager.getInstance().getProduct().setTradingParam(tradParam);
						((ActCustomerMarketProgress) getActivity()).showDetails(
								ActCustomerMarketProgress.F_SIGN_FOURTH, 0);
					} else
						return;

				} else if (currentView == 1) {
					if (isFullDataSecond()) {
						currentView++;
						mPager.setCurrentItem(currentView, true);
					} else
						return;
				} else {
					currentView++;
					mPager.setCurrentItem(currentView, true);
				}
			} 
			else// 预览模式
			{
				if (currentView == 3) {
					((ActCustomerMarketProgress) getActivity()).showDetails(ActCustomerMarketProgress.F_SIGN_FOURTH, 0);
				} else {
					currentView++;
					mPager.setCurrentItem(currentView, true);
				}
			}

		}
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.btnPre:
			switchView(true);
			break;
		case R.id.btnNext:
			switchView(false);
			break;
		case R.id.btnPlanTableSign:
			PopMarketSign pms = new PopMarketSign(this.getActivity(), v);
			pms.showPopWindow();
			break;

		default:
			break;
		}
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

	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	// 签名完成之后存到本地
	public void setImageView(Bitmap bm) {
		btnPlanTableSign.setImageBitmap(bm);

		CommonUtil.storeFile(BitmapUtils.bmpToByteArray(bm, false), Constant.INVESTOR_SIGN, Constant.UP_INVESTOR_SIGN
				+ pictureMiddleName);
		upLoadSign = true;
	}

}
