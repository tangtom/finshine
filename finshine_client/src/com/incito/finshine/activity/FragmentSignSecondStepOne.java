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
import android.widget.AdapterView.OnItemSelectedListener;

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
import com.incito.finshine.entity.ContractBaseData;
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
import com.incito.finshine.entity.spinner.IDType;
import com.incito.finshine.entity.spinner.Job;
import com.incito.finshine.entity.spinner.Nationality;
import com.incito.finshine.entity.spinner.RiskAssessmentParam;
import com.incito.finshine.entity.spinner.ShippingMethod;
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
 * <dd>Description:客户营销 签订合同第二步 小一步 Fragment</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月16日 下午5:00:59</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class FragmentSignSecondStepOne extends FragmentDetail implements OnClickListener {

	View view;

	private MyViewPager mPager;// 页卡内容
	private List<View> listViews; // Tab页面列表
	private View view1, view2, view3, view4, view5, view6, view7, view8, view9;
	public CommFlipDot flipDot = null;

	private static int currentView = 0;// 判断当前是哪一个

	/* 界面2-1 */
	private TextView proposerName;
	private TextView firstFill;
	private TextView papersType;
	private TextView validity;
	private TextView papersNO;

	/* spinner */
	private Spinner spinnerDiploma;
	private Button spinnerFinancialGoal;
	private Spinner spinnerJob;
	private Button spinnerInvestSource;
	private Spinner spinnerIncome;

	/* 界面2-2 */
	private RadioGroup radioGroupAnswer1;
	private RadioGroup radioGroupAnswer2;
	private RadioGroup radioGroupAnswer3;
	private RadioGroup radioGroupAnswer4;
	private RadioGroup radioGroupAnswer5;
	private RadioGroup radioGroupAnswer6;

	/* 界面2-3 */
	private TextView txtRiskNum;
	private TextView txtRiskType;

	/* 界面2-4 */
	private RadioGroup radioGroupRiskSelection;
	private ImageView btnRiskTableSign;
	private TextView txtRiskTableSignDate;
	private TextView txtCompanyAddress;
	private TextView txtCompanyTel;
	private TextView txtCompanyFax;

	// http用
	private static final int GET_CONTRACT_INFO = 0;
	private static final int DOWNLOAD_INVESTOR_SIGN = 1;

	// 标记照片是否上传 （是否有图片出来）
	private boolean upLoadSign = false;

	private MarketCsOrder marketCs;
	private MarketStateReslut marketResult = null;

	private ContractBatch contractBatch;
	private RiskAssessment riskAssessment;
	private RiskAssessmentParam riskAssessmentParam;
	private int point1 = 0;
	private int point2 = 0;
	private int point3 = 0;
	private int point4 = 0;
	private int point5 = 0;
	private int point6 = 0;
	private HttpUtils httpUtils = null;

	private StringBuffer pictureMiddleName = new StringBuffer();

	public static FragmentSignSecondStepOne newInstance(int id, int position) {

		FragmentSignSecondStepOne f = new FragmentSignSecondStepOne();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);

		currentView = position;
		// marketCs = data;
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

		flipDot = new CommFlipDot(getActivity(), 9, (LinearLayout) view.findViewById(R.id.ltDot));

		initViewPager();
		EventBus.getDefault().register(this, "setImageView");
		return view;
	}

	private void initViewPager() {

		mPager = (MyViewPager) view.findViewById(R.id.viewPagerCustomerDetail);
		mPager.setPagingEnabled(false);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getActivity().getLayoutInflater();
		view1 = mInflater.inflate(R.layout.act_sign_contract_2_1, null);
		listViews.add(view1);
		view2 = mInflater.inflate(R.layout.act_sign_contract_2_2, null);
		listViews.add(view2);
		view5 = mInflater.inflate(R.layout.act_sign_contract_2_2, null);
		view6 = mInflater.inflate(R.layout.act_sign_contract_2_2, null);
		view7 = mInflater.inflate(R.layout.act_sign_contract_2_2, null);
		view8 = mInflater.inflate(R.layout.act_sign_contract_2_2, null);
		view9 = mInflater.inflate(R.layout.act_sign_contract_2_2, null);
		listViews.add(view5);
		listViews.add(view6);
		listViews.add(view7);
		listViews.add(view8);
		listViews.add(view9);
		view3 = mInflater.inflate(R.layout.act_sign_contract_2_3, null);
		listViews.add(view3);
		view4 = mInflater.inflate(R.layout.act_sign_contract_2_4, null);
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
		/* 界面2-1 */
		proposerName = (TextView) view1.findViewById(R.id.proposerName);
		firstFill = (TextView) view1.findViewById(R.id.firstFill);
		papersType = (TextView) view1.findViewById(R.id.papersType);
		validity = (TextView) view1.findViewById(R.id.validity);
		papersNO = (TextView) view1.findViewById(R.id.papersNO);
		/* 界面2-2 */
		// radioGroupAnswer = (RadioGroup) view2
		// .findViewById(R.id.radioGroupAnswer);
		// radioGroupQuestion = (TextView) view2
		// .findViewById(R.id.radioGroupQuestion);
		/* 界面2-3 */
		txtRiskNum = (TextView) view3.findViewById(R.id.txtRiskNum1);
		txtRiskType = (TextView) view3.findViewById(R.id.txtRiskType);
		/* 界面2-4 */
		btnRiskTableSign = (ImageView) view4.findViewById(R.id.btnRiskTableSign);
		btnRiskTableSign.setOnClickListener(this);
		radioGroupRiskSelection = (RadioGroup) view4.findViewById(R.id.radioGroupRiskSelection);
		txtRiskTableSignDate = (TextView) view4.findViewById(R.id.txtRiskTableSignDate);
		txtCompanyAddress = (TextView) view4.findViewById(R.id.txtCompanyAddress);
		txtCompanyTel = (TextView) view4.findViewById(R.id.txtCompanyTel);
		txtCompanyFax = (TextView) view4.findViewById(R.id.txtCompanyFax);
		loadSpinner();
	}

	private void loadSpinner() {
		spinnerDiploma = (Spinner) view1.findViewById(R.id.spinnerDiploma);

		initSpinner(spinnerDiploma, getResources().getStringArray(R.array.diploma_status), 0);

		spinnerJob = (Spinner) view1.findViewById(R.id.spinnerJob);
		initSpinner(spinnerJob, getResources().getStringArray(R.array.customer_job), 0);

		spinnerIncome = (Spinner) view1.findViewById(R.id.spinnerIncome);
		initSpinner(spinnerIncome, getResources().getStringArray(R.array.annul_income), 0);

		spinnerFinancialGoal = (Button) view1.findViewById(R.id.spinnerFinancialGoal);
		// 筛选弹出的列表加EditText
		initDlgCommonFilter(spinnerFinancialGoal, R.array.purchase_reason, R.string.purchaseReason, false, 0);

		spinnerInvestSource = (Button) view1.findViewById(R.id.spinnerInvestSource);
		// 筛选弹出的列表加EditText
		initDlgCommonFilter(spinnerInvestSource, R.array.investSource, R.string.investResource, false, 0);

		// marketResult =
		// CoreManager.getInstance().getProduct().getMarketResult();
		
		marketCs = ((ActCustomerMarketProgress)getActivity()).getMarketCs();
//		marketResult = ((ActCustomerMarketProgress) getActivity()).getMarketCs().getMarketReslut();
		if (marketCs != null) {
			contractBatch = ((ActCustomerMarketProgress) getActivity()).getContractBatch();
			if (contractBatch == null) {
				initHttpType(GET_CONTRACT_INFO);
			} else {
				initData();
				downIcon();
				isEdit();
			}
		}
	}

	private void initData() {
		riskAssessment = ((ActCustomerMarketProgress) getActivity()).getRiskAssessment();
		if (riskAssessment == null)
			return;
		initQuestion();
		riskAssessmentParam = new RiskAssessmentParam(riskAssessment);

		pictureMiddleName.append(riskAssessment.getApplicant().getId() + "-");
		pictureMiddleName.append(riskAssessment.getSalesOrderId() + "-");
		pictureMiddleName.append(riskAssessment.getId() + "-4.jpg");

		proposerName.setText(riskAssessment.getApplicant().getName());
		firstFill.setText(riskAssessment.getFirstTimeWrite() == 0 ? "否" : "是");
		papersType.setText(riskAssessment.getIdType().getName());
		
		if ("9999-12-31".equals(DateUtil.formatDate2String(new Date(riskAssessment.getIdDateOfExpire()),
				DateUtil.FORMAT_YYYY_MM_DD))) {
			validity.setText("长期"); // 证件有效期
		} else {
			validity.setText(DateUtil.formatDate2String(new Date(riskAssessment.getIdDateOfExpire()),
					DateUtil.FORMAT_YYYY_MM_DD_ZH));
		}
//		validity.setText(DateUtil.formatDate2String(new Date(riskAssessment.getIdDateOfExpire()),
//				DateUtil.FORMAT_YYYY_MM_DD_ZH));// 证件有效期
		papersNO.setText(riskAssessment.getIdNumber());

		spinnerDiploma.setSelection(riskAssessment.getDiploma().getId() > 1 ? riskAssessment.getDiploma().getId() - 1
				: 0, true);
		spinnerJob.setSelection(riskAssessment.getJob().getId() > 1 ? riskAssessment.getJob().getId() - 1 : 0, true);
		spinnerIncome.setSelection(riskAssessment.getAnnualIncome().getId() > 1 ? riskAssessment.getAnnualIncome()
				.getId() - 1 : 0, true);
		spinnerFinancialGoal.setText((riskAssessment.getPurchaseReasons() == null || riskAssessment
				.getPurchaseReasons().isEmpty()) ? "" : riskAssessment.getPurchaseReasons());
		spinnerInvestSource.setText((riskAssessment.getInvestmentSources() == null || riskAssessment
				.getInvestmentSources().isEmpty()) ? "" : riskAssessment.getInvestmentSources());

		((RadioButton)radioGroupRiskSelection.getChildAt(riskAssessment.getBehavior() == 1 ? 0 : 1)).setChecked(true);

		txtRiskTableSignDate.setText(DateUtil.formatDate2String(new Date(riskAssessment.getDateOfSign()),
				DateUtil.FORMAT_YYYY_MM_DD_ZH));
		
		txtRiskNum.setText(String.valueOf(riskAssessment.getTotalPoints()));
		
		if (riskAssessment.getTotalPoints() < 10){
			txtRiskType.setText("保守型");
		}
		else if (riskAssessment.getTotalPoints() > 9 && riskAssessment.getTotalPoints() < 16){
			txtRiskType.setText("稳健型");
		}
		else{
			txtRiskType.setText("积极型");
		}
			
		txtCompanyAddress.setText(riskAssessment.getCompanyAddress() == null ? "获取数据失败" : riskAssessment.getCompanyAddress());
		txtCompanyTel.setText(riskAssessment.getTelephone() == null ? "获取数据失败" : riskAssessment.getTelephone());
		txtCompanyFax.setText(riskAssessment.getFaxNumber() == null ? "获取数据失败" : riskAssessment.getFaxNumber());
	}

	private void initQuestion() {
		if (riskAssessment == null)
			return;
		List<RiskIndicator> questions = riskAssessment.getIndicators();
		radioGroupAnswer1 = (RadioGroup) view2.findViewById(R.id.radioGroupAnswer);
		initQuestion(questions.get(0), view2);

		radioGroupAnswer2 = (RadioGroup) view5.findViewById(R.id.radioGroupAnswer);

		initQuestion(questions.get(1), view5);

		radioGroupAnswer3 = (RadioGroup) view6.findViewById(R.id.radioGroupAnswer);
		initQuestion(questions.get(2), view6);

		radioGroupAnswer4 = (RadioGroup) view7.findViewById(R.id.radioGroupAnswer);
		initQuestion(questions.get(3), view7);

		radioGroupAnswer5 = (RadioGroup) view8.findViewById(R.id.radioGroupAnswer);
		initQuestion(questions.get(4), view8);

		radioGroupAnswer6 = (RadioGroup) view9.findViewById(R.id.radioGroupAnswer);
		initQuestion(questions.get(5), view9);
	}

	private void initQuestion(RiskIndicator indicator, View view) {// 初始化题目和选项
		List<RiskIndicatorItem> options = indicator.getItems();
		RadioGroup group = (RadioGroup) view.findViewById(R.id.radioGroupAnswer);
		RadioButton op1 = (RadioButton) view.findViewById(R.id.rdoOption1);
		op1.setText(options.get(0).getValue());
		op1.setChecked(options.get(0).isSelected());
		RadioButton op2 = (RadioButton) view.findViewById(R.id.rdoOption2);
		op2.setText(options.get(1).getValue());
		op2.setChecked(options.get(1).isSelected());
		RadioButton op3 = (RadioButton) view.findViewById(R.id.rdoOption3);
		op3.setText(options.get(2).getValue());
		op3.setChecked(options.get(2).isSelected());
		RadioButton op4 = (RadioButton) view.findViewById(R.id.rdoOption4);
		op4.setText(options.get(3).getValue());
		op4.setChecked(options.get(3).isSelected());
		TextView question = (TextView) view.findViewById(R.id.txtQuestion);
		question.setText(indicator.getTitle());
	}

	private void initDlgCommonFilter(final Button btn, final int listId, final int title, final boolean isSingle,
			final int model) {

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				DlgCommFilter fDeadLind = new DlgCommFilter(getActivity(), listId, title, btn.getText().toString(),
						isSingle, model);
				fDeadLind.setListener(new RefreshFilterListener() {

					@Override
					public void doRefresh(String reslut, int position,boolean b,int title) {

						((Button) v).setText(reslut);
					}
				});
				fDeadLind.setHiddenInput(true);
				fDeadLind.showDialog();
			}
		});

	}

	private void initHttpType(final int currentType) {

		try {
			JSONObject params = new JSONObject();
			int reqId = 0;
			RequestType reqType = null;
			reqType = RequestType.POST;
			switch (currentType) {
			case GET_CONTRACT_INFO:
				// 获取合同签订第二步基本信息
				reqId = RequestDefine.MARKET_SC_SECOND_GET_CONTRACT;
				params.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
				params.put("customerId", marketCs.getCustomerId());
				params.put("salesOrderId", marketCs.getSalesOrderId());
				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(true);
				break;

			case DOWNLOAD_INVESTOR_SIGN:
				// 获取客户签名
				reqId = RequestDefine.MARKET_SC_DOWNLOAD_ATTACHMENT;
				params.put("transactionId", contractBatch.getAssessment().getId());
				params.put("transactionType", "CLIENTRISKASSESSMENT");
				params.put("moduleType", "AUTOGRAPH");

				httpUtils = new HttpUtils(getActivity(), reqId, reqType, params);
				httpUtils.setShowDiloag(false);
				httpUtils.setDownLoadFile(true);
				break;
			}

			httpUtils.setSuccessListener(new SuccessReslut() {

				@Override
				public void getResluts(JSONObject response) {

					if (TextUtils.isEmpty(response.toString())) {
						return;
					}
					Log.i("FragmentSignSecondStepOne", response + "");
					String states = response.optString("status");
					switch (currentType) {
					case GET_CONTRACT_INFO:
						// 获取合同第二步基本信息
						if (Request.RESLUT_OK.equals(states)) {
							try {
								Log.i("tag", "Object = " + response.toString());
								Gson gson = new Gson();
								JSONObject assessment = response.optJSONObject("item").optJSONObject("assessment");
								// riskAssessment = new RiskAssessment();

								riskAssessment = gson.fromJson(assessment.toString(), RiskAssessment.class);
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
						case DOWNLOAD_INVESTOR_SIGN:
							// 获取客户签名
							file = Constant.INVESTOR_SIGN;
							fileName = Constant.DOWN_INVESTOR_SIGN + pictureMiddleName;
							CommonUtil.storeFile(binaryData, file, fileName);
							Bitmap bitMap1 = BitmapManager.decodeSampledBitmapFromFile(file + "/" + fileName,
									Constant.SIGN_WIDTH, Constant.SIGN_HEIGHT);
							btnRiskTableSign.setImageBitmap(bitMap1);
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

	// 下载图片附件
	private void downIcon() {
		// 查询合同信息
		if (contractBatch == null) {
			return;
		}
		// 本地有 down 取down
		if (CommonUtil.isNotEmptyfile(Constant.INVESTOR_SIGN + Constant.DOWN_INVESTOR_SIGN + pictureMiddleName)) {
			btnRiskTableSign.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.INVESTOR_SIGN
					+ Constant.DOWN_INVESTOR_SIGN + pictureMiddleName, Constant.SIGN_WIDTH, Constant.SIGN_HEIGHT));
			upLoadSign = true;
			return;
		}

		if (riskAssessment != null) { // 本地有up
			if (CommonUtil.isNotEmptyfile(Constant.INVESTOR_SIGN + Constant.UP_INVESTOR_SIGN + pictureMiddleName)) {
				btnRiskTableSign.setImageBitmap(BitmapManager.decodeSampledBitmapFromFile(Constant.INVESTOR_SIGN
						+ Constant.UP_INVESTOR_SIGN + pictureMiddleName, Constant.SIGN_WIDTH, Constant.SIGN_HEIGHT));
				upLoadSign = true;
				return;
			}
		}
		initHttpType(DOWNLOAD_INVESTOR_SIGN);
	}

	private void initSpinner(Spinner sp, String[] dataList, int selectIndex) {
		ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.common_spinner_item, dataList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//
		sp.setAdapter(adapter);
		// sp.setOnItemSelectedListener((OnItemSelectedListener) this);
		sp.setSelection(selectIndex, true);

	}

	private void switchView(boolean isPre) {
		if (isPre) // 上一页
		{
			if (currentView == 0) {
				((ActCustomerMarketProgress) getActivity()).showDetails(ActCustomerMarketProgress.F_SIGN_FIRST, 1);
			} else {
				currentView--;
				mPager.setCurrentItem(currentView, true);
			}
		}

		else // 下一页
		{
			if (marketCs.getMarketingStatusId() < 4){//编辑模式
				if ((currentView > 0) && (currentView < 7)) {
					int checkid = -1;
					switch (currentView) {
					case 1:
						checkid = radioGroupAnswer1.getCheckedRadioButtonId();
						break;
					case 2:
						checkid = radioGroupAnswer2.getCheckedRadioButtonId();
						break;
					case 3:
						checkid = radioGroupAnswer3.getCheckedRadioButtonId();
						break;
					case 4:
						checkid = radioGroupAnswer4.getCheckedRadioButtonId();
						break;
					case 5:
						checkid = radioGroupAnswer5.getCheckedRadioButtonId();
						break;
					case 6:
						checkid = radioGroupAnswer6.getCheckedRadioButtonId();
						break;
					default:
						break;
					}

					// 四个选项
					int checkedIndex = -1;
					switch (checkid) {
					case R.id.rdoOption1:
						checkedIndex = 0;
						break;
					case R.id.rdoOption2:
						checkedIndex = 1;
						break;
					case R.id.rdoOption3:
						checkedIndex = 2;
						break;
					case R.id.rdoOption4:
						checkedIndex = 3;
						break;
					default:
						break;
					}

					if (checkedIndex == -1) {
						CommonUtil.showToast("请选择其中一个选项", getActivity());
						return;
					}

					RiskIndicator indicator = riskAssessment.getIndicators().get(currentView - 1);
					riskAssessment.getIndicators().get(currentView - 1).getItems().get(checkedIndex).setSelected(true);
					int point = indicator.getItems().get(checkedIndex).getPoints();

					switch (currentView) {
					case 1:
						riskAssessmentParam.setIndicatorValue1(indicator.getItems().get(checkedIndex).getKey());
						point1 = point;
						break;
					case 2:
						riskAssessmentParam.setIndicatorValue2(indicator.getItems().get(checkedIndex).getKey());
						point2 = point;
						break;
					case 3:
						riskAssessmentParam.setIndicatorValue3(indicator.getItems().get(checkedIndex).getKey());
						point3 = point;
						break;
					case 4:
						riskAssessmentParam.setIndicatorValue4(indicator.getItems().get(checkedIndex).getKey());
						point4 = point;
						break;
					case 5:
						riskAssessmentParam.setIndicatorValue5(indicator.getItems().get(checkedIndex).getKey());
						point5 = point;
						break;
					case 6:
						riskAssessmentParam.setIndicatorValue6(indicator.getItems().get(checkedIndex).getKey());
						point6 = point;
						break;
					default:
						break;
					}
					Log.i("Question", "you got " + point + " at " + (currentView - 1) + " question");
				}
				if (currentView == 6) {
					int score = point1 + point2 + point3 + point4 + point5 + point6;
					if (score < 10)
						txtRiskType.setText("保守型");
					else if (score > 9 && score < 16)
						txtRiskType.setText("稳健型");
					else
						txtRiskType.setText("积极型");

					txtRiskNum.setText(String.valueOf(score));

					riskAssessment.setRiskTypeDesc(txtRiskType.getText().toString());
					riskAssessment.setTotalPoints(score);
					riskAssessmentParam.setTotalPoints(score);
				}
				if (currentView == 8) {
					if (isFullDataFourth()) {
						// CoreManager.getInstance().getProduct().setRiskAssessmentParam(riskAssessmentParam);
						((ActCustomerMarketProgress) getActivity()).setRiskAssessment(riskAssessment);
						((ActCustomerMarketProgress) getActivity()).setRiskAssessmentParam(riskAssessmentParam);
						((ActCustomerMarketProgress) getActivity()).showDetails(ActCustomerMarketProgress.F_SIGN_THIRD, 0);
					} else
						return;
				} else {
					currentView++;
					mPager.setCurrentItem(currentView, true);
				}
			}else{//预览模式   直接跳
				if (currentView == 8) {
					((ActCustomerMarketProgress) getActivity()).showDetails(ActCustomerMarketProgress.F_SIGN_THIRD, 0);
				}else{
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
		case R.id.btnRiskTableSign:
			PopMarketSign pms = new PopMarketSign(this.getActivity(), v);
			pms.showPopWindow();
			break;
		default:
			break;
		}
	}

	private boolean isFullDataFirst() {
		// if (TextUtils.isEmpty(spinnerFinancialGoal.getText().toString())) {
		// CommonUtil.showToast("请选择购买原因", getActivity());
		// return false;
		// } else if
		// (TextUtils.isEmpty(spinnerInvestSource.getText().toString())) {
		// CommonUtil.showToast("请选择投资来源", getActivity());
		// return false;
		// }

		riskAssessment.getDiploma().setId(spinnerDiploma.getSelectedItemPosition() + 1);
		riskAssessment.getJob().setId(spinnerJob.getSelectedItemPosition() + 1);
		riskAssessment.getAnnualIncome().setId(spinnerIncome.getSelectedItemPosition() + 1);
		riskAssessment.setPurchaseReasons(spinnerFinancialGoal.getText().toString());
		riskAssessment.setInvestmentSources(spinnerInvestSource.getText().toString());

		riskAssessmentParam.setDiplomaId(spinnerDiploma.getSelectedItemPosition() + 1);
		riskAssessmentParam.setJobId(spinnerJob.getSelectedItemPosition() + 1);
		riskAssessmentParam.setAnnualIncomeId(spinnerIncome.getSelectedItemPosition() + 1);
		riskAssessmentParam.setPurchaseReasons(spinnerFinancialGoal.getText().toString());
		riskAssessmentParam.setInvestmentResources(spinnerInvestSource.getText().toString());
		return true;
	}

	private boolean isFullDataFourth() {
		if (!upLoadSign) {
			CommonUtil.showToast("请先签名", getActivity());
			return false;
		}

		isFullDataFirst();
		// 交易默认选2 此时不做判断
		riskAssessment.setBehavior(radioGroupRiskSelection.getCheckedRadioButtonId() == R.id.select1 ? 1 : 2);
		riskAssessmentParam.setBehaviorId(radioGroupRiskSelection.getCheckedRadioButtonId() == R.id.select1 ? 1 : 2);
		return true;
	}

	private boolean isEdit() {
		if (marketCs.getMarketingStatusId() > 3) {
			spinnerDiploma.setEnabled(false);
			spinnerFinancialGoal.setEnabled(false);
			spinnerIncome.setEnabled(false);
			spinnerInvestSource.setEnabled(false);
			spinnerJob.setEnabled(false);

			radioGroupRiskSelection.getChildAt(0).setClickable(false);
			radioGroupRiskSelection.getChildAt(1).setClickable(false);
			btnRiskTableSign.setEnabled(false);
			
			for (int i = 0; i<4; i++){
				radioGroupAnswer1.getChildAt(i).setClickable(false);
				radioGroupAnswer2.getChildAt(i).setClickable(false);
				radioGroupAnswer3.getChildAt(i).setClickable(false);
				radioGroupAnswer4.getChildAt(i).setClickable(false);
				radioGroupAnswer5.getChildAt(i).setClickable(false);
				radioGroupAnswer6.getChildAt(i).setClickable(false);
			}
			
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
		btnRiskTableSign.setImageBitmap(bm);

		CommonUtil.storeFile(BitmapUtils.bmpToByteArray(bm, false), Constant.INVESTOR_SIGN, Constant.UP_INVESTOR_SIGN
				+ pictureMiddleName);
		upLoadSign = true;
	}

}
