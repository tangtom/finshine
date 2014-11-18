package com.incito.finshine.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.custom.view.CommonWaitDialog;
import com.custom.view.FirstPagePop;
import com.custom.view.PopProdDetailList;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterProductList;
import com.incito.finshine.entity.Product;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

/**
 * <dl>
 * <dt>ActProductDetail.java</dt>
 * <dd>Description:产品详情 分为理财师自己看的 和给客户看的版本</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-13 上午9:05:28</dd>
 * </dl>
 * 
 * @author lihs
 */
public class ActProductDetail extends Activity implements OnClickListener {

	private static final String TAG = "ActProductDetail";
	public static final String FLAG = "CustomerVersion";

	public static final String PRODUCT_ID = "product_id";

	public static final String INTENT_PRODUCT_NAME = "product_name";
	private SPManager spManager;
	private CommonWaitDialog dialog = null;
	private Button btnUseCommission;
	private int isUsed;
	private Product product;

	private int isCustomer = 1;// 0:客户看的版本 1：理财师版本
	private long id = 0;
	
	public Product getProduct() {
		if (product == null) {
			CommonUtil.showToast("产品数据为空", this);
			return new Product();
		}
		return product;
	}

	private JsonHttpResponseHandler handlerGetProductDetail = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.d(TAG, "success o= " + response.toString());

			closeDialog();
			try {
				if (response.getString("status").equals("0")) {
					JSONObject obj = response.getJSONObject("result");
					isUsed = obj.getInt("isUsed");
					switch (isUsed) {
					case 0:
						break;
					case 1:
						btnUseCommission.setText("查看佣金券");
						break;
					default:
						break;
					}
					Gson gson = new Gson();
					product = gson.fromJson(obj.getJSONObject("product")
							.toString(), Product.class);
					refreshDataByInternet(product);
				}
			} catch (JSONException e) {

				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			CommonUtil.showToast("查询产品详情失败" + content, ActProductDetail.this);
			closeDialog();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_product_detail);

		initTopTitle();

		initUI();

		initData();

		CommonUtil.hideSoftInputFromWindow(this);

		findViewById(R.id.imageNavigate).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						initPullPop();
					}
				});
	}

	private void initPullPop() {

		FirstPagePop firstPage = new FirstPagePop(this,
				findViewById(R.id.imageNavigate));
		firstPage.showPopWindow();

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		closeDialog();
	}

	private void initTopTitle() {
		
		if (getIntent().getExtras() != null) {
			id = getIntent().getExtras().getLong(PRODUCT_ID);
			isCustomer = getIntent().getExtras().getInt(FLAG);
			Log.i(TAG, "product id = " + id);
		}
		

		TextView topTitle = (TextView) findViewById(R.id.textTitle);
		topTitle.setText(getResources()
				.getString(R.string.title_product_detail));

		ImageView btnBack = (ImageView) findViewById(R.id.imageBack);
		btnBack.setOnClickListener(this);

//		findViewById(R.id.ivPopMenu).setVisibility(View.VISIBLE);  //暂时隐藏此按钮
		findViewById(R.id.ivPopMenu).setOnClickListener(this);

	}

	private void initUI() {
		
		findViewById(R.id.btn_marketing).setOnClickListener(this);
		findViewById(R.id.btn_order_share).setOnClickListener(this);

		findViewById(R.id.iv_send_sms).setOnClickListener(this);
		findViewById(R.id.iv_star).setOnClickListener(this);
		btnUseCommission = (Button) findViewById(R.id.btnUseCommission);
		btnUseCommission.setOnClickListener(this);
		
		if (isCustomer == 0)
			findViewById(R.id.topLayout).setVisibility(View.GONE);
	}

	private void initData() {
		if (dialog == null) {
			dialog = new CommonWaitDialog(this, "", R.string.load_data);
		}
		spManager = SPManager.getInstance();
		Request getProductDetail = new Request(
				RequestDefine.RQ_PRODUCT_DETAIL_GET,
				spManager.getLongValue(SPManager.ID), id, RequestType.GET,
				null, handlerGetProductDetail);
		CoreManager.getInstance().postRequest(getProductDetail);
	}
    
 
    private void refreshDataByInternet(Product product){
    	
    	((TextView)findViewById(R.id.tvFixCommision)).setText(product.getProdCommissionBase()+"%");
    	((TextView)findViewById(R.id.tvPayCommision)).setText(product.getRewardCommission()+"%");
    	
    	String[] proTypeLevelFirst = (String[])getResources().getStringArray(
  				R.array.product__type);;// 一级类别
    	String[] proTypeLevelSecondAffince = (String[]) getResources()
  				.getStringArray(R.array.product__affiance_type);;// 二级类别 基金资管
    	String[] proTypeLevelSecondFund = (String[]) getResources()
  				.getStringArray(R.array.product__fund_type);;// 二级类别 基金
    	
    	((TextView)findViewById(R.id.tv_product_highlight)).setText(product.getProdHighLights());
    	((TextView)findViewById(R.id.tv_manager)).setText(product.getProdAdmin());
    	
    	 long type1 = product.getProdFirstType();
		 long type2 = product.getProdSecondtype();
		 StringBuffer sb = new StringBuffer();
//		 try {
//			 sb.append((type1 == 1) ? (proTypeLevelSecondFund[(int) (type2-1)]) : (proTypeLevelSecondAffince[(int) (type2-1)]));
//		    ((TextView)findViewById(R.id.tv_product_type)).setText(proTypeLevelFirst[(int) (type1-1)] + sb.toString());
//		} catch (Exception e) {
//		}
		 ((TextView)findViewById(R.id.tv_product_type)).setText(product.getProdFirstTypeStr() + "----" + product.getProdSecondtypeStr());
    	((TextView)findViewById(R.id.tv_product_states)).setText(CommonUtil.getProductStates(product.getProdStatus()));
    	((TextView)findViewById(R.id.tv_publish_date)).setText(
				 DateUtil.getDateTimeByFormatAndMs(product.getProdOnDateTime(), DateUtil.FORMAT_YYYY_MM_DD)+" - "+
						 DateUtil.getDateTimeByFormatAndMs(product.getProdEnDateTime(), DateUtil.FORMAT_YYYY_MM_DD));
    	((TextView)findViewById(R.id.tv_product_scale)).setText(product.getProdSize()+"亿");
    	((TextView)findViewById(R.id.tv_product_deadline)).setText(product.getProdTime().intValue()+"月");
    	((TextView)findViewById(R.id.tv_invest_income_rate)).setText(product.getProdYieldFixed()+"%");
    	((TextView)findViewById(R.id.tv_mark)).setText(product.getProdComment());
    	((TextView)findViewById(R.id.tv_buy_start_point)).setText(product.getProdStart()+"万");
    	((TextView)findViewById(R.id.tv_counter_party)).setText(product.getProdCounterParty());
    	((TextView)findViewById(R.id.tv_money_useing)).setText(product.getProdUse());
    	((TextView)findViewById(R.id.tv_source_of_repayment)).setText(product.getProdRepayment());
    	((TextView)findViewById(R.id.tv_risk_control_measures)).setText(product.getProdRisk());
    	((TextView)findViewById(R.id.tv_income_distribution_way)).setText("每" + product.getProdIncomeType() + "个月");
    	((TextView)findViewById(R.id.tv_income_distribution_date)).setText(product.getProdIncomeDateTime());
    	((TextView)findViewById(R.id.tv_product_code)).setText(product.getProdCode());
    	((TextView)findViewById(R.id.tv_publisher_phone)).setText(product.getProdCstTel());
    	((TextView)findViewById(R.id.tv_publisher_email)).setText(product.getProdPubEmail());
    	((TextView)findViewById(R.id.tv_product_preferences)).setText(product.getProdPreference());
    	((TextView)findViewById(R.id.tv_product_name)).setText(product.getProdName());
    	
    	try {
			Long isCollected = getProduct().getIsSave();
			if (isCollected.longValue() == AdapterProductList.COLLECTED) {
				// 是收藏变成取消收藏
				((ImageView) findViewById(R.id.iv_star))
						.setImageResource(R.drawable.ic_has_collected);
			} else {

				((ImageView) findViewById(R.id.iv_star))
						.setImageResource(R.drawable.ic_collect);
			}
		} catch (Exception e) {

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.imageBack:
			//
//			startActivity(new Intent(this, ActProductManagement.class));
			finish();
			break;

		case R.id.ivPopMenu:
			List<Integer> icon = new ArrayList<Integer>();
			List<Integer> text = new ArrayList<Integer>();
			if (isCustomer == 0){
				icon.add(R.drawable.ic_prod_inform);
				icon.add(R.drawable.ic_contact_customer);
				
				text.add(R.string.product_information);
				text.add(R.string.contact_customer);
			}else
			{
				icon.add(R.drawable.ic_prod_inform);
				icon.add(R.drawable.ic_train_video);
				icon.add(R.drawable.ic_contact_customer);
				icon.add(R.drawable.ic_identification);
				icon.add(R.drawable.ic_appoint_share);
				
				text.add(R.string.product_information);
				text.add(R.string.train_video);
				text.add(R.string.contact_customer);
				text.add(R.string.idenfication);
				text.add(R.string.order_share);
			}
			
			PopProdDetailList pop = new PopProdDetailList(this, v,
					getProduct(), PopProdDetailList.TOP_2_BOTTOM, icon, text);
			pop.showPopWindow();
			break;
		case R.id.iv_star:
			// 收藏 该产品
			JSONObject json = null;
			try {
				Long collected = getProduct().getIsSave();
				json = new JSONObject();
				Request request = null;
				if (collected != null
						&& collected == AdapterProductList.COLLECTED) {
					request = new Request(RequestDefine.RQ_PRODUCT_COLLECTION,
							getProduct().getId(), RequestType.POST, json,
							handlerCancleCollRefreshProduct);
				} else {
					request = new Request(RequestDefine.RQ_PRODUCT_COLLECTION,
							getProduct().getId(), RequestType.POST, json,
							handlerCollRefreshProduct);
				}
				CoreManager.getInstance().postRequest(request);
			} catch (Exception e) {

			}
			break;
		case R.id.btnUseCommission:
			switch (isUsed) {
			case 0:
				if(product != null)
				{
					Intent i = new Intent(this, ActPointerCommission.class);
					i.putExtra("productId", product.getId()).putExtra("fromClass",
							"detail");
					startActivity(i);
					this.finish();
				}
				break;
			case 1:
				if(product != null)
				{
					Intent intent = new Intent(this, ActQueryCommission.class);
					intent.putExtra("productId", product.getId()).putExtra(
							"fromClass", "detail");
					startActivity(intent);
				}
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
	}

	private JsonHttpResponseHandler handlerCollRefreshProduct = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.d(TAG, "success o= " + response.toString());
			if (Request.RESLUT_OK.equals(response.opt("status"))) {
				CommonUtil.showToast("收藏成功", ActProductDetail.this);
				initData();

			} else {
				CommonUtil.showToast("收藏失败", ActProductDetail.this);
			}
		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			CommonUtil.showToast("收藏失败" + content, ActProductDetail.this);
		}
	};

	private JsonHttpResponseHandler handlerCancleCollRefreshProduct = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.d(TAG, "success o= " + response.toString());
			if (Request.RESLUT_OK.equals(response.opt("status"))) {
				CommonUtil.showToast("取消收藏成功", ActProductDetail.this);
				initData();

			} else {
				CommonUtil.showToast("取消收藏失败", ActProductDetail.this);
			}
		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			CommonUtil.showToast("取消收藏失败" + content, ActProductDetail.this);
		}
	};

	private void closeDialog() {
		if (dialog != null) {
			dialog.clearAnimation();
		}
	}
}
