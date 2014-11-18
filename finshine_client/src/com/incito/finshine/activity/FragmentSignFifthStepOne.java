package com.incito.finshine.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.incito.finshine.entity.ContractBatch;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.FaxTradingAgreement;
import com.incito.finshine.entity.MarketCsOrder;
import com.incito.finshine.entity.MarketStateReslut;
import com.incito.finshine.entity.RiskAssessment;
import com.incito.finshine.entity.RiskIndicator;
import com.incito.finshine.entity.RiskIndicatorItem;
import com.incito.finshine.entity.TradingBusinessApplication;
import com.incito.finshine.entity.spinner.AcceptanceType;
import com.incito.finshine.entity.spinner.AnnualIncome;
import com.incito.finshine.entity.spinner.Diploma;
import com.incito.finshine.entity.spinner.FaxTradingAgreementParam;
import com.incito.finshine.entity.spinner.IDType;
import com.incito.finshine.entity.spinner.Job;
import com.incito.finshine.entity.spinner.Nationality;
import com.incito.finshine.entity.spinner.ShippingMethod;
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
 * 
 * <dl>
 * <dt>FragmentBindFirst.java</dt>
 * <dd>Description:客户营销 签订合同第二步 小二步 Fragment</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月16日 下午5:00:59</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class FragmentSignFifthStepOne extends FragmentDetail implements OnClickListener {

	View view;
	private MyViewPager mPager;// 页卡内容
	private List<View> listViews; // Tab页面列表
	private View view1, view2, view3, view4, view5;
	public CommFlipDot flipDot = null;

	private static int currentView = 0;// 判断当前是哪一个
	/* 界面5-1 */
	public TextView txtFirstParty;
	public TextView txtSecondParty;
	public TextView fax;
	public TextView faxPhone;
	public TextView txtExpressAddress;
	public TextView txtPostCode;
	public TextView txtFirstContent;
	public TextView txtSecondContent;
	public TextView txtThirdContent;

	// private TextView txtContent1;
	// private TextView txtContent2;
	// private TextView txtContent3;
	// private TextView txtContent4;
	// private TextView txtContent5;
	/* 界面5-2 */
	private TextView txtSecondContentOne;
	/* 界面5-3 */
	private TextView txtThirdContentOne;
	/* 界面5-4 */
	private TextView txtForthContentOne;
	/* 界面5-5 */
	private ImageView btnPlanTableSign;
	private TextView txtFaxTableCompany;
	private TextView txtFaxTableSignDate;

	private ContractBatch contractBatch;

	private FaxTradingAgreement faxTrading;
	private FaxTradingAgreementParam faxTradingParam;

	//http用
	private static final int DOWNLOAD_INVESTOR_SIGN = 0;
	private static final int UPLOAD_BASE_INFO = 1;
	private static final int UPLOAD_ATTACHMENT = 2;
	private static final int GET_CONTRACT_INFO = 3;//代码重复   又重新获取一遍数据

	// 标记照片是否上传 （是否有图片出来）
	private boolean upLoadSign = false;

	private StringBuffer pictureMiddleName = new StringBuffer();

	private HttpUtils httpUtils = null;

	private MarketCsOrder marketCs;

	private boolean isUpdateSuccess = false;

	private MarketStateReslut marketResult = null;
	
	public static FragmentSignFifthStepOne newInstance(int id, int position) {

		FragmentSignFifthStepOne f = new FragmentSignFifthStepOne();
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

		flipDot = new CommFlipDot(getActivity(), 5, (LinearLayout) view.findViewById(R.id.ltDot));

		initViewPager();
		EventBus.getDefault().register(this, "setImageView");

		return view;
	}

	private void initViewPager() {

		// 上一步
		Button btnPre = (Button) view.findViewById(R.id.btnPre);
		btnPre.setOnClickListener(this);
		// 下一步
		Button btnNext = (Button) view.findViewById(R.id.btnNext);
		btnNext.setOnClickListener(this);

		mPager = (MyViewPager) view.findViewById(R.id.viewPagerCustomerDetail);
		mPager.setPagingEnabled(false);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getActivity().getLayoutInflater();
		view1 = mInflater.inflate(R.layout.act_sign_contract_5_1, null);
		listViews.add(view1);
		view2 = mInflater.inflate(R.layout.act_sign_contract_5_2, null);
		listViews.add(view2);
		view3 = mInflater.inflate(R.layout.act_sign_contract_5_3, null);
		listViews.add(view3);
		view4 = mInflater.inflate(R.layout.act_sign_contract_5_4, null);
		listViews.add(view4);
		view5 = mInflater.inflate(R.layout.act_sign_contract_5_5, null);
		listViews.add(view5);

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

		for (int i = 0; i < 5; i++) {
			((TextView) listViews.get(i).findViewById(R.id.title)).setText("专项资产管理计划传真交易协议书(二)");
		}

		/* 界面5-1 */
		txtFirstParty = (TextView) view1.findViewById(R.id.txtFirstParty);
		txtSecondParty = (TextView) view1.findViewById(R.id.txtSecondParty);
		fax = (TextView) view1.findViewById(R.id.fax);
		faxPhone = (TextView) view1.findViewById(R.id.faxPhone);
		txtExpressAddress = (TextView) view1.findViewById(R.id.txtExpressAddress);
		txtPostCode = (TextView) view1.findViewById(R.id.txtPostCode);
		txtFirstContent = (TextView) view1.findViewById(R.id.txtFirstContent);
		txtSecondContent = (TextView) view1.findViewById(R.id.txtSecondContent);
		txtThirdContent = (TextView) view1.findViewById(R.id.txtThirdContent);

		/* 界面5-2 */
		txtSecondContentOne = (TextView) view2.findViewById(R.id.txtSecondContentOne);
		/* 界面5-3 */
		txtThirdContentOne = (TextView) view3.findViewById(R.id.txtThirdContentOne);
		/* 界面5-4 */
		txtForthContentOne = (TextView) view4.findViewById(R.id.txtForthContentOne);
		/* 界面5-5 */
		btnPlanTableSign = (ImageView) view5.findViewById(R.id.btnPlanTableSign);
		btnPlanTableSign.setOnClickListener(this);
		txtFaxTableCompany = (TextView) view5.findViewById(R.id.txtFaxTableCompany);
		txtFaxTableSignDate = (TextView) view5.findViewById(R.id.txtFaxTableSignDate);

		initData();
		downIcon();
		isEdit();
	}

	// 下载图片附件  签名
	private void downIcon() {
		if ((faxTrading == null)) {
			return;
		}
		// 本地有，显示本地的，没有网络加载，加载成功后覆盖本地文件
		if (CommonUtil.isNotEmptyfile(Constant.INVESTOR_SIGN + Constant.DOWN_INVESTOR_SIGN + pictureMiddleName
				+ faxTrading.getId() + "-7.jpg")) {
			btnPlanTableSign.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.INVESTOR_SIGN
					+ Constant.DOWN_INVESTOR_SIGN + pictureMiddleName + faxTrading.getId() + "-7.jpg",
					Constant.SIGN_WIDTH, Constant.SIGN_HEIGHT));
			upLoadSign = true;
		} else if (CommonUtil.isNotEmptyfile(Constant.INVESTOR_SIGN + Constant.UP_INVESTOR_SIGN + pictureMiddleName
				+ faxTrading.getId() + "-7.jpg")) {
			btnPlanTableSign.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.INVESTOR_SIGN
					+ Constant.UP_INVESTOR_SIGN + pictureMiddleName + faxTrading.getId() + ".-7jpg", Constant.SIGN_WIDTH,
					Constant.SIGN_HEIGHT));
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
			Gson gson = new Gson();
			switch (currentType) {

			case DOWNLOAD_INVESTOR_SIGN:
				// 获取客户签名
				reqId = RequestDefine.MARKET_SC_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", faxTrading.getId());
				params.put("transactionType", "FAXBUSINESS");
				params.put("moduleType", "AUTOGRAPH");

				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;

			case UPLOAD_BASE_INFO:
				// 更新基本信息
				reqType = RequestType.PUT;
				reqId = RequestDefine.MARKET_SC_SECOND_SAVE_CONTRACT;

				JSONObject jsonRisk = new JSONObject(gson.toJson(((ActCustomerMarketProgress) getActivity())
						.getRiskAssessmentParam()));
				JSONObject jsonTrading = new JSONObject(gson.toJson(((ActCustomerMarketProgress) getActivity())
						.getTradingParam()));
				JSONObject jsonFax1 = new JSONObject(gson.toJson(((ActCustomerMarketProgress) getActivity())
						.getFaxParam1()));
				JSONObject jsonFax2 = new JSONObject(gson.toJson(((ActCustomerMarketProgress) getActivity())
						.getFaxParam2()));

				params.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
				params.put("customerId", marketCs.getCustomerId());
				params.put("salesOrderId", marketCs.getSalesOrderId());
				params.put("riskAssessmentParam", jsonRisk);
				params.put("tradingBusinessApplicationParam", jsonTrading);
				// params.put("tradingBusinessApplicationParam",
				// (TradingBusinessApplicationParam)SessionUtil.getObj("tradParam"));

				params.put("faxTradingAgreementParam1", jsonFax1);
				params.put("faxTradingAgreementParam2", jsonFax2);

				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(true);
				break;

			case UPLOAD_ATTACHMENT:
				// 更新附件 注意图片的名字！！！
				reqType = RequestType.POST;
				reqId = RequestDefine.MARKET_SC_SECOND_SAVE_ATTACHMENT;
				// note：用的是reqParams 不是jsonObject
				reqParams.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID) + "");
				reqParams.put("customerId", marketCs.getCustomerId() + "");
				reqParams.put("salesOrderId", marketCs.getSalesOrderId() + "");
				try {
					reqParams.put(
							"autographOfRiskAssessment",
							CommonUtil.getFile(Constant.INVESTOR_SIGN, Constant.UP_INVESTOR_SIGN + pictureMiddleName
									+ contractBatch.getAssessment().getId() + "-4.jpg"));
					reqParams.put(
							"autographOfTradingBusiness",
							CommonUtil.getFile(Constant.INVESTOR_SIGN, Constant.UP_INVESTOR_SIGN + pictureMiddleName
									+ contractBatch.getApplication().getId() + "-5.jpg"));
					reqParams.put(
							"autographOfFaxBusiness1",
							CommonUtil.getFile(Constant.INVESTOR_SIGN, Constant.UP_INVESTOR_SIGN + pictureMiddleName
									+ contractBatch.getAgreement1().getId() + "-6.jpg"));
					reqParams.put(
							"autographOfFaxBusiness2",
							CommonUtil.getFile(Constant.INVESTOR_SIGN, Constant.UP_INVESTOR_SIGN + pictureMiddleName
									+ contractBatch.getAgreement2().getId() + "-7.jpg"));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				httpUtils = new HttpUtils(getActivity(), reqId, reqType, null);
				httpUtils.setRequestParms(reqParams);
				httpUtils.setShowDiloag(true);
				httpUtils.setUploadFile(true);
				break;
				
			case GET_CONTRACT_INFO:
				// 获取合同签订第二步基本信息
				reqId = RequestDefine.MARKET_SC_SECOND_GET_CONTRACT;
				params.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
				params.put("customerId", marketCs.getCustomerId());
				params.put("salesOrderId", marketCs.getSalesOrderId());
				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(true);
				break;
			}

			httpUtils.setSuccessListener(new SuccessReslut() {

				@Override
				public void getResluts(JSONObject response) {
					if (TextUtils.isEmpty(response.toString())) {
						return;
					}
					String states = response.optString("status");
					if (Request.RESLUT_OK.equals(states)) {
						switch (currentType) {
						case UPLOAD_BASE_INFO:
							// 更新合同基本信息

							if (Request.RESLUT_OK.equals(states)) {
								initHttpType(UPLOAD_ATTACHMENT);
							} else {
								CommonUtil.showToast("上传失败", getActivity());
							}
							break;

						case UPLOAD_ATTACHMENT:
							// 更新附件 成功之后将down的图片删除 并且将up的图片重命名 失败不变
							isUpdateSuccess = response.optString("status").equals(Request.RESLUT_OK);

							if (isUpdateSuccess) {
								marketResult = JsonParse.getMarketStepResluts(response);
								marketCs = ((ActCustomerMarketProgress) getActivity()).getMarketCs();
								if (marketCs != null) {
									marketCs.setMarketReslut(marketResult);
									JsonParse.setMarketReslut(marketResult);
									marketCs.setMarketingStatusId(marketResult.getMarketingStatusId());
									// 要不要。。。。
									((ActCustomerMarketProgress) getActivity()).setMarketCsOrder(marketCs);
								}

								CommonUtil.renameFile(Constant.INVESTOR_SIGN, Constant.DOWN_INVESTOR_SIGN
										+ pictureMiddleName + contractBatch.getAssessment().getId() + "-4.jpg",
										Constant.UP_INVESTOR_SIGN + pictureMiddleName
												+ contractBatch.getAssessment().getId() + "-4.jpg");
								CommonUtil.renameFile(Constant.INVESTOR_SIGN, Constant.DOWN_INVESTOR_SIGN
										+ pictureMiddleName + contractBatch.getApplication().getId() + "-5.jpg",
										Constant.UP_INVESTOR_SIGN + pictureMiddleName
												+ contractBatch.getApplication().getId() + "-5.jpg");
								CommonUtil.renameFile(Constant.INVESTOR_SIGN, Constant.DOWN_INVESTOR_SIGN
										+ pictureMiddleName + contractBatch.getAgreement1().getId() + "-6.jpg",
										Constant.UP_INVESTOR_SIGN + pictureMiddleName
												+ contractBatch.getAgreement1().getId() + "-6.jpg");
								CommonUtil.renameFile(Constant.INVESTOR_SIGN, Constant.DOWN_INVESTOR_SIGN
										+ pictureMiddleName + contractBatch.getAgreement2().getId() + "-7.jpg",
										Constant.UP_INVESTOR_SIGN + pictureMiddleName
												+ contractBatch.getAgreement2().getId() + "-7.jpg");

								((ActCustomerMarketProgress) getActivity()).showDetails(
										ActCustomerMarketProgress.F_ORDER_PAYMENT2, 0);
							}
							else {
								CommonUtil.showToast("上传信息失败", getActivity());
							}
							break;
							
						case GET_CONTRACT_INFO:
							// 获取合同第二步基本信息
							if (Request.RESLUT_OK.equals(states)) {
								try {
									Log.i("tag", "Object = " + response.toString());
									Gson gson = new Gson();
									JSONObject assessment = response.optJSONObject("item").optJSONObject("assessment");
									
									RiskAssessment riskAssessment = gson.fromJson(assessment.toString(), RiskAssessment.class);
									ArrayList<RiskIndicator> indicators = new ArrayList<RiskIndicator>();
									JSONArray questions = assessment.getJSONArray("indicators");
									for (int i = 0; i < questions.length(); i++) {
										JSONObject q = questions.optJSONObject(i);

										RiskIndicator item = gson.fromJson(q.toString(), RiskIndicator.class);
										indicators.add(item);
										ArrayList<RiskIndicatorItem> indicatorItems = new ArrayList<RiskIndicatorItem>();
										Log.i("tag", "item = " + item.getTitle());
										JSONArray options = q.getJSONArray("items");
										for (int j = 0; j < options.length(); j++) {
											JSONObject o = options.getJSONObject(j);

											RiskIndicatorItem oi = gson.fromJson(o.toString(), RiskIndicatorItem.class);
											Log.i("tag", "option = " + oi.getValue());
											indicatorItems.add(oi);
										}
										item.setItems(indicatorItems);
									}

									JSONObject applicant = assessment.optJSONObject("applicant");
									Customer customer = gson.fromJson(applicant.toString(), Customer.class);

									JSONObject idType = assessment.optJSONObject("idType");
									IDType idtype = gson.fromJson(idType.toString(), IDType.class);

									JSONObject diplomaObj = assessment.optJSONObject("diploma");
									Diploma diploma = gson.fromJson(diplomaObj.toString(), Diploma.class);

									JSONObject jobObj = assessment.getJSONObject("job");
									Job job = gson.fromJson(jobObj.toString(), Job.class);
									
									//暂时的
									JSONObject annualIncomeObj = assessment.optJSONObject("annualIncome");
									AnnualIncome annualIncome = null;
									if (annualIncomeObj == null){
										annualIncome = new AnnualIncome();
									}else{
										annualIncome = gson.fromJson(annualIncomeObj.toString(),
												AnnualIncome.class);
									}

									riskAssessment.setIndicators(indicators);
									riskAssessment.setApplicant(customer);
									riskAssessment.setIdType(idtype);
									riskAssessment.setDiploma(diploma);
									riskAssessment.setJob(job);
									riskAssessment.setAnnualIncome(annualIncome);

									// CoreManager.getInstance().getProduct().setRiskAssessment(riskAssessment);
									((ActCustomerMarketProgress) getActivity()).setRiskAssessment(riskAssessment);

									JSONObject application = response.getJSONObject("item").optJSONObject("application");
									JSONObject acceptanceTypeObj = application.optJSONObject("acceptanceType");
									AcceptanceType acceptanceType = null;
									if (acceptanceTypeObj == null){
										acceptanceType = new AcceptanceType();
									}else{
										acceptanceType = gson.fromJson(acceptanceTypeObj.toString(),
												AcceptanceType.class);
									}
									
									JSONObject nationalityObj = application.optJSONObject("nationality");
									Nationality nationality = gson.fromJson(nationalityObj.toString(), Nationality.class);
									JSONObject shippingMethodObj = application.optJSONObject("shippingMethod");
									ShippingMethod shippingMethod = gson.fromJson(shippingMethodObj.toString(),
											ShippingMethod.class);
									TradingBusinessApplication trading = gson.fromJson(application.toString(),
											TradingBusinessApplication.class);
									trading.setApplicant(customer);
									trading.setAcceptanceType(acceptanceType);
									trading.setNationality(nationality);
									trading.setShippingMethod(shippingMethod);
									trading.setIdType(idtype);
									// CoreManager.getInstance().getProduct().setTrading(trading);
									((ActCustomerMarketProgress) getActivity()).setTrading(trading);

									JSONObject agreement1 = response.optJSONObject("item").optJSONObject("agreement1");
									FaxTradingAgreement fax1 = gson.fromJson(agreement1.toString(),
											FaxTradingAgreement.class);
									// CoreManager.getInstance().getProduct().setFax1(fax1);
									((ActCustomerMarketProgress) getActivity()).setFax1(fax1);

									JSONObject agreement2 = response.optJSONObject("item").optJSONObject("agreement2");
									FaxTradingAgreement fax2 = gson.fromJson(agreement2.toString(),
											FaxTradingAgreement.class);
									// CoreManager.getInstance().getProduct().setFax1(fax2);
									((ActCustomerMarketProgress) getActivity()).setFax2(fax2);

									contractBatch = new ContractBatch();
									contractBatch.setAssessment(riskAssessment);
									contractBatch.setApplication(trading);
									contractBatch.setAgreement1(fax1);
									contractBatch.setAgreement2(fax2);
									// CoreManager.getInstance().getProduct().setContractBatch(contractBatch);
									((ActCustomerMarketProgress) getActivity()).setContractBatch(contractBatch);
								} catch (JSONException e) {
									e.printStackTrace();
								}

								initData();
								downIcon();
								isEdit();

							}
							break;
						}
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

						case DOWNLOAD_INVESTOR_SIGN:
							// 获取客户签名
							file = Constant.INVESTOR_SIGN;
							fileName = Constant.DOWN_INVESTOR_SIGN + pictureMiddleName + faxTrading.getId() + "-7.jpg";
							CommonUtil.storeFile(binaryData, file, fileName);
							Bitmap bitMap = BitmapManager.decodeSampledBitmapFromFile(file + "/" + fileName,
									Constant.SIGN_WIDTH, Constant.SIGN_HEIGHT);
							upLoadSign = true;
							btnPlanTableSign.setImageBitmap(bitMap);
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

	private void initData() {
		// marketResult =
		// CoreManager.getInstance().getProduct().getMarketResult();
		// contractBatch =
		// CoreManager.getInstance().getProduct().getContractBatch();

		marketCs = ((ActCustomerMarketProgress) getActivity()).getMarketCs();
	
		contractBatch = ((ActCustomerMarketProgress) getActivity()).getContractBatch();
		if (contractBatch == null){
			initHttpType(GET_CONTRACT_INFO);
		}
		faxTrading = ((ActCustomerMarketProgress) getActivity()).getFax2();
		if (faxTrading == null)
			return;

		pictureMiddleName.append(marketCs.getCustomerId() + "-");
		pictureMiddleName.append(faxTrading.getSalesOrderId() + "-");

		txtFirstParty.setText(faxTrading.getCustomerName());
		txtSecondParty.setText(faxTrading.getPublisherName());
		fax.setText(faxTrading.getServiceFax());
		faxPhone.setText(faxTrading.getConfirmedTelephone());
		txtExpressAddress.setText(faxTrading.getPostAddress());
		txtPostCode.setText(faxTrading.getPostCode());

		txtFaxTableCompany.setText(faxTrading.getPublisherName());
		txtFaxTableSignDate.setText(DateUtil.formatDate2String(new Date(faxTrading.getDateOfSign()),
				DateUtil.FORMAT_YYYY_MM_DD_ZH));

		// <$p$> 暂时的分页标示符 需要后台讲合同内容变成下面的str1-str5格式
		String[] tempContent = faxTrading.getContent().split("p");
		for (int i = 0; i < tempContent.length; i++) {

			((TextView) listViews.get(i).findViewById(R.id.txtContent)).setText(tempContent[i]);
		}
	}

	private boolean isFullData() {
		if (!upLoadSign) {
			CommonUtil.showToast("请先签名", getActivity());
			return false;
		}
		return true;
	}

	private boolean isEdit() {
		if (marketCs.getMarketingStatusId() > 3) {
			btnPlanTableSign.setEnabled(false);
			return false;
		}
		return true;
	}

	private void switchView(boolean isPre) {
		if (isPre) // 上一页
		{
			if (currentView == 0) {
				((ActCustomerMarketProgress) getActivity()).showDetails(ActCustomerMarketProgress.F_SIGN_FOURTH, 4);
			} else {
				currentView--;
				mPager.setCurrentItem(currentView, true);
			}
		}

		else // 下一页
		{
			if (currentView == 4) {
				if (marketCs.getMarketingStatusId() < 4) {// 编辑模式
					if (isFullData()) {
						Button btnNext = (Button) view.findViewById(R.id.btnNext);
						btnNext.setText("提交");

						faxTradingParam = new FaxTradingAgreementParam(faxTrading);
						// CoreManager.getInstance().getProduct().setFaxParam2(faxTradingParam);
						((ActCustomerMarketProgress) getActivity()).setFaxParam2(faxTradingParam);
						 initHttpType(UPLOAD_BASE_INFO);
					}
				}else{//预览模式
					 ((ActCustomerMarketProgress) getActivity()).showDetails(
					 ActCustomerMarketProgress.F_ORDER_PAYMENT2, 0);
				}
			} else {
				currentView++;
				mPager.setCurrentItem(currentView, true);
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
				+ pictureMiddleName + faxTrading.getId() + "-7.jpg");
		upLoadSign = true;
	}
}
