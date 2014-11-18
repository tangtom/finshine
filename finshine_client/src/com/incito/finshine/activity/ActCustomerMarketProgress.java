package com.incito.finshine.activity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.core.listener.DialogListener;
import com.android.core.util.DialogUtil;
import com.custom.view.FirstPagePop;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterCustomerMarketing;
import com.incito.finshine.entity.BindingAgreement;
import com.incito.finshine.entity.ContractBaseData;
import com.incito.finshine.entity.ContractBatch;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.FaxTradingAgreement;
import com.incito.finshine.entity.MarketCsOrder;
import com.incito.finshine.entity.MarketStateReslut;
import com.incito.finshine.entity.OrderInfoItem;
import com.incito.finshine.entity.Product;
import com.incito.finshine.entity.RiskAssessment;
import com.incito.finshine.entity.TradingBusinessApplication;
import com.incito.finshine.entity.spinner.FaxTradingAgreementParam;
import com.incito.finshine.entity.spinner.RiskAssessmentParam;
import com.incito.finshine.entity.spinner.TradingBusinessApplicationParam;
import com.incito.finshine.manager.JsonParse;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.wisdomsdk.event.EventBus;

/**
 * 
 * <dl>
 * <dt>ActCustomerMarketProgress.java</dt>
 * <dd>Description:客户营销所有流程的最大的Fragment</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月12日 上午10:41:25</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class ActCustomerMarketProgress extends FragmentActivity implements
		MarketStatus {

	private final String TAG = ActCustomerMarketProgress.class.getSimpleName();

	// 当前是第几步骤了
	//步骤从2到5
	public static long marketStates = 0;
	// 当前第几个view
	//当前第几个view从1到9
	public static int currentView;

	public static final int F_PRODUCT_COMPARE = 0;
	public static final int F_BIND = 1;

	public static final int F_SIGN_FIRST = 2;
	public static final int F_SIGN_SECOND = 3;
	public static final int F_SIGN_THIRD = 4;
	public static final int F_SIGN_FOURTH = 5;
	public static final int F_SIGN_FIFTH = 6;

	//public static final int F_ORDER_PAYMENT1 = 7;
	public static final int F_ORDER_PAYMENT2 = 8;
	public static final int F_TRANSACTION = 9;

	private RelativeLayout titleLayout;
	private TextView title;

	private MarketCsOrder marketCs;
	private Product prod;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		System.out.println("ActCustomerMarketProgress onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_customer_market_progress);
		init();
		Intent i = getIntent();
		if (i != null) {
			currentView = i.getExtras().getInt("Position");
			if (i.hasExtra(AdapterCustomerMarketing.ACTION_SELE_ORDER)) {
				marketCs = (MarketCsOrder) i
						.getSerializableExtra(AdapterCustomerMarketing.ACTION_SELE_ORDER);
				httpUtils = new HttpUtils(ActCustomerMarketProgress.this,
						RequestDefine.RQ_PRODUCT_DETAIL_GET, RequestType.GET,
						null);
			}
			if (i.hasExtra("product")) {
				prod = (Product) i.getSerializableExtra("product");
			}
		}
		System.out.println("ActCustomerMarketProgress onStart");
		initData();
		queryData();
		findViewById(R.id.imageNavigate).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initPullPop();
			}
		});
	}
	
	private void initPullPop(){
		 
		FirstPagePop firstPage = new FirstPagePop(this, findViewById(R.id.imageNavigate));
		firstPage.setPosition(3);
		firstPage.showPopWindow();
	}
	@Override
	protected void onStart() {
		marketStates = marketCs.getMarketingStatusId();
		showDetails(currentView, 0);
		super.onStart();
	}

	public void queryData() {
		System.out.print("ActCustomerMarketProgress : 理财师id="
				+ marketCs.getSalesId() + " 客户id:" + marketCs.getCustomerId()
				+ " 产品id:" + marketCs.getProdId());
//		initHttpUtils(QUERY_CUSTOMER_INFO);
//		initHttpUtils(QUERY_BIND_PROTECAL);
		initHttpUtils(QUERY_PRODUCT_INFO);
//		queryOrderInfo();
//		queryContractInfo();

	}

	// 无论从哪里选择数据 都会走这个地方，我重新组装一个对象
	private void initData() {
		if (marketCs == null) {
			// 从选择产品过来
			marketCs = new MarketCsOrder();
			MarketStateReslut marRes = JsonParse.getMarketReslut();
			marketCs.setId(marRes.getId());
			marketCs.setContractId(marRes.getContractId());
			marketCs.setBindProtecolId(marRes.getBindProtelId() + "");
			marketCs.setMarketingStatusId(marRes.getMarketingStatusId());
			marketCs.setProdId(marRes.getProdId());
			marketCs.setSalesId(marRes.getSalesId());
			marketCs.setSalesOrderId(marRes.getSalesOrderId());
			marketCs.setStatus(marRes.getStatus());
			marketCs.setCustomerId(marRes.getCustomerId());
			marketCs.setProd(prod);
			if(prod != null)
				marketCs.setProdName(prod.getProdName());
			marketCs.setMarketReslut(marRes);
		}
	}
	//返回到产品详情
	public void toProductDetail()
	{
		startActivity(new Intent(ActCustomerMarketProgress.this,ActProductDetail.class).putExtra(ActProductDetail.PRODUCT_ID, marketCs.getProdId()).putExtra(ActProductDetail.FLAG, 1));
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			toCustomerMarketing();
		}
		return super.onKeyDown(keyCode, event);
	}
	private boolean init() {
		title = (TextView) findViewById(R.id.textTitle);
		ImageView back = (ImageView) findViewById(R.id.imageBack);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toCustomerMarketing();
			}
		});

		titleLayout = (RelativeLayout) findViewById(R.id.commonTitle);

		return true;
	}
	
	public void toCustomerMarketing(){
		DialogUtil.dialog("确定返回交易首页吗？", "", this, new DialogListener(){

			@Override
			public void afterDilog() {
				//点击确定后的回调
				startActivity(new Intent(ActCustomerMarketProgress.this,ActCustomerMarketing.class));
				finish();
			}
			
		});
	}

	
	
	public void showDetails(int id, int position) {
		Log.i(TAG, "showDetails = " + id);
	    currentView = id;
		FragmentDetail details = (FragmentDetail) getSupportFragmentManager()
				.findFragmentById(R.id.wholeFrameLayout);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if (details == null || !(details.getFragmentId() == id)) {

			// Make new fragment to show this selection.
			switch (id) {
			// 选择产品 产品比较
			case F_PRODUCT_COMPARE:
//				details = ActCustomerMarketProductCompare.newInstance(id,
//						position);
//				titleLayout.setVisibility(View.GONE);
				break;
			// 绑定协议
			case F_BIND:
				details = ActBind11.newInstance(id, position);
				titleLayout.setVisibility(View.VISIBLE);
				title.setText(R.string.title_bind);
				break;
			// 合同签订1
			case F_SIGN_FIRST:
				details = FragmentSignFirstStepOne.newInstance(id, position);
				titleLayout.setVisibility(View.VISIBLE);
				title.setText(R.string.title_sign_contract);
				break;
			// 合同签订2
			case F_SIGN_SECOND:
				details = FragmentSignSecondStepOne.newInstance(id, position);
				titleLayout.setVisibility(View.VISIBLE);
				title.setText(R.string.title_sign_contract);
				break;
			// 合同签订3
			case F_SIGN_THIRD:
				details = FragmentSignThirdStepOne.newInstance(id, position);
				titleLayout.setVisibility(View.VISIBLE);
				title.setText(R.string.title_sign_contract);
				break;
			// 合同签订4
			case F_SIGN_FOURTH:
				details = FragmentSignFourthStepOne.newInstance(id, position);
				titleLayout.setVisibility(View.VISIBLE);
				title.setText(R.string.title_sign_contract);
				break;
			// 合同签订5
			case F_SIGN_FIFTH:
				details = FragmentSignFifthStepOne.newInstance(id, position);
				titleLayout.setVisibility(View.VISIBLE);
				title.setText(R.string.title_sign_contract);
				break;
			// 订单支付1
//			case F_ORDER_PAYMENT1:
//				details = FragmentCustomerMarketOrderPaidFirst.newInstance(id);
//				titleLayout.setVisibility(View.VISIBLE);
//				title.setText(R.string.title_order_payment);
//				break;
			// 订单支付2
			case F_ORDER_PAYMENT2:
				details = FragmentCustomerMarketOrderPaidSecond.newInstance(id);
				titleLayout.setVisibility(View.VISIBLE);
				title.setText(R.string.title_order_payment);
				break;
			// 订单结果
			case F_TRANSACTION:
				details = ActCustomerMarketTransactionResult.newInstance(id,
						position);
				titleLayout.setVisibility(View.VISIBLE);
				title.setText(R.string.title_order_result);
				break;
			default:
				break;
			}
			// Execute a transaction, replacing any existing fragment
			// with this one inside the frame.
			ft.replace(R.id.wholeFrameLayout, details);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}
	}

	private static final int QUERY_BIND_PROTECAL = 1;// 查询绑定协议接口，查询绑定协议ID
	private static final int QUERY_PRODUCT_INFO = 2; // 查询产品详情
	private static final int QUERY_CUSTOMER_INFO = 3; // 查询客户信息

	private HttpUtils httpUtils = null;

	private void initHttpUtils(final int type) {

		JSONObject params = new JSONObject();
		try {
			switch (type) {
			case QUERY_BIND_PROTECAL:
				params.put("salesId", marketCs.getSalesId());
				params.put("customerId", marketCs.getCustomerId());
				httpUtils = new HttpUtils(ActCustomerMarketProgress.this,
						RequestDefine.MARKET_RQ_QUERY_BIND_PROTECAL,
						RequestType.POST, params);
				httpUtils.setShowDiloag(false);
				break;
			case QUERY_PRODUCT_INFO:
				httpUtils = new HttpUtils(ActCustomerMarketProgress.this,
						RequestDefine.RQ_PRODUCT_DETAIL_GET, RequestType.GET,
						null);
				Log.i(TAG, marketCs.getProdId() + "----");
				httpUtils.setCustomerId(marketCs.getSalesId());
				httpUtils.setProductId(marketCs.getProdId());
				httpUtils.setShowDiloag(true);
				break;
			case QUERY_CUSTOMER_INFO:
				httpUtils = new HttpUtils(this,
						RequestDefine.RQ_CUSTOMER_GET_SINGLE, RequestType.GET,
						null);
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

					case QUERY_BIND_PROTECAL:
						JSONObject obj;
						try {
							obj = response.getJSONObject("item");
							marketCs.setBindProtecolId(String.valueOf(obj
									.optLong("id")));
							System.out.println("绑定协议iD:"
									+ String.valueOf(obj.optLong("id")));
						} catch (JSONException e) {
							e.printStackTrace();
						}
						break;
					case QUERY_PRODUCT_INFO:
						try {
							JSONObject prodDetail = response
									.getJSONObject("result");
							Gson gson = new Gson();
							Product product = gson.fromJson(prodDetail
									.getJSONObject("product").toString(),
									Product.class);
							if (product != null){
								marketCs.setProd(product);
								marketCs.setProdName(product.getProdName());
							}
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
						break;
					case QUERY_CUSTOMER_INFO:
						try {
							Gson gson = new Gson();
							Customer customer = gson.fromJson(
									response.toString(), Customer.class);
							marketCs.setCustomer(customer);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					default:
						break;
					}
				}
			});
			httpUtils.executeRequest();

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	List<OrderInfoItem> orderList;

/*	private void queryOrderInfo() {

		*//**
		 * 根据订单id 和客户id 和理财师 id 查询该订单信息
		 *//*
		JSONObject params = new JSONObject();
		try {
			params.put("salesId", marketCs.getSalesId());
			params.put("customerId", marketCs.getCustomerId());
			params.put("salesOrderId", marketCs.getSalesOrderId());

		} catch (JSONException e) {

			e.printStackTrace();
			return;
		}

		HttpUtils httpUtils = new HttpUtils(this,
				RequestDefine.MARKET_RQ_QUERY_ORDER_LIST, RequestType.POST,
				params);
		httpUtils.setShowDiloag(false);
		httpUtils.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {
				Log.i(TAG, response.toString() + "--------");
				if (Request.RESLUT_OK.equals(response.optString("status"))) {
					Gson gson = new Gson();
					orderList = gson.fromJson(response.optJSONArray("list")
							.toString(), new TypeToken<List<OrderInfoItem>>() {
					}.getType());
					if (marketCs != null) {
						marketCs.setMarketingStatusId(orderList.get(0)
								.getOrderStatusId());
						marketCs.setOrderlists(orderList);
					}
				}
			}
		});
		httpUtils.executeRequest();
	}

	private void queryContractInfo() {

		if (marketCs.getContractId() == 0) {
			return;
		}
		JSONObject params = new JSONObject();
		try {
			params.put("id", marketCs.getContractId());
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		HttpUtils httpUtils = new HttpUtils(this,
				RequestDefine.MARKET_RQ_QUERY_CONTRACT_INFO, RequestType.POST,
				params);
		httpUtils.setShowDiloag(false);
		httpUtils.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {
				Log.i(TAG, response.toString() + "++++");
				if (Request.RESLUT_OK.equals(response.optString("status"))) {
					Gson gson = new Gson();
					ContractInfoItem contractInfo;
					try {
						contractInfo = gson.fromJson(
								response.getJSONObject("item").toString(),
								new TypeToken<ContractInfoItem>() {
								}.getType());
						if (marketCs != null) {
							marketCs.setMarketingStatusId(contractInfo.getId());
							marketCs.setContractinfo(contractInfo);
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
	}*/

	@Override
	protected void onDestroy() {
		super.onDestroy();
		marketCs = null;
		// currentView = -1;
	}

	@Override
	public int getMarketStatus(MarketCsOrder marketCs) {
		return (int) marketCs.getMarketingStatusId();
	}

	@Override
	public MarketCsOrder getMarketCs() {
		System.out.println(marketCs.getMarketingStatusId());
		return marketCs;
	}
	
	public void setMarketCsOrder(MarketCsOrder marketCs) {
		this.marketCs = marketCs;
	}

	@Override
	public boolean canEdit(long bindAgreeMentStatus) {
		// 根据当前的营销状态显示当前是可编辑还是只显示
		if (bindAgreeMentStatus >= marketStates - 1) {
			// 如果
			return true;
		}
		return false;
	}
	
	//绑定协议
	private BindingAgreement bindingAgreement;

	//第一步
	private ContractBaseData contractBaseData;
	private ContractBatch contractBatch;
	//第二步
	private RiskAssessment riskAssessment;
	private RiskAssessmentParam riskAssessmentParam;
	private TradingBusinessApplication trading;
	private TradingBusinessApplicationParam tradingParam;
	private FaxTradingAgreement fax1;
	private FaxTradingAgreementParam faxParam1;
	private FaxTradingAgreement fax2;
	private FaxTradingAgreementParam faxParam2;
	
	
	public BindingAgreement getBindingAgreement() {
		return bindingAgreement;
	}

	public void setBindingAgreement(BindingAgreement bindingAgreement) {
		this.bindingAgreement = bindingAgreement;
	}
	
	public ContractBaseData getContractBaseData() {
		return contractBaseData;
	}
	public void setContractBaseData(ContractBaseData contractBaseData) {
		this.contractBaseData = contractBaseData;
	}
	public ContractBatch getContractBatch() {
		return contractBatch;
	}
	public void setContractBatch(ContractBatch contractBatch) {
		this.contractBatch = contractBatch;
	}
	
	public RiskAssessment getRiskAssessment() {
		return riskAssessment;
	}
	public void setRiskAssessment(RiskAssessment riskAssessment) {
		this.riskAssessment = riskAssessment;
	}
	
	public RiskAssessmentParam getRiskAssessmentParam() {
		return riskAssessmentParam;
	}
	public void setRiskAssessmentParam(RiskAssessmentParam riskAssessmentParam) {
		this.riskAssessmentParam = riskAssessmentParam;
	}
	public TradingBusinessApplication getTrading() {
		return trading;
	}
	public void setTrading(TradingBusinessApplication trading) {
		this.trading = trading;
	}
	public FaxTradingAgreement getFax1() {
		return fax1;
	}
	public void setFax1(FaxTradingAgreement fax1) {
		this.fax1 = fax1;
	}
	public FaxTradingAgreement getFax2() {
		return fax2;
	}
	public void setFax2(FaxTradingAgreement fax2) {
		this.fax2 = fax2;
	}
	
	public TradingBusinessApplicationParam getTradingParam() {
		return tradingParam;
	}
	public void setTradingParam(TradingBusinessApplicationParam tradingParam) {
		this.tradingParam = tradingParam;
	}
	public FaxTradingAgreementParam getFaxParam1() {
		return faxParam1;
	}
	public void setFaxParam1(FaxTradingAgreementParam faxParam1) {
		this.faxParam1 = faxParam1;
	}
	public FaxTradingAgreementParam getFaxParam2() {
		return faxParam2;
	}
	public void setFaxParam2(FaxTradingAgreementParam faxParam2) {
		this.faxParam2 = faxParam2;
	}
}
