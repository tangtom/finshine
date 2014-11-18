package com.incito.finshine.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.custom.view.CommonWaitDialog;
import com.custom.view.PopProdDetailList;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterProductList;
import com.incito.finshine.entity.Product;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

/**
 * 
 * <dl>
 * <dt>ActCustomerMarketProductCompare.java</dt>
 * <dd>Description:客户营销的   选择产品的比较</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月16日 下午1:25:39</dd>
 * </dl>
 * 
 * @author LiNing
 */

public class ActCustomerMarketProductCompare extends FragmentDetail implements
		OnClickListener {

	private View view;
	private static final String TAG = "ActProductDetail";

	public static final String PRODUCT_ID = "product_id";

	public static final String INTENT_PRODUCT_NAME = "product_name";

	private CommonWaitDialog dialog = null;

	private Product product;

	public Product getProduct() {
		if (product == null) {
			CommonUtil.showToast("产品数据为空", this.getActivity());
			return new Product();
		}
		return product;
	}

	public static ActCustomerMarketProductCompare newInstance(int id,
			int position) {

		ActCustomerMarketProductCompare f = new ActCustomerMarketProductCompare();
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
		view = inflater.inflate(R.layout.act_product_detail, container, false);
		init();
		return view;
	}
 

	@Override
	public void onStart() {
		super.onStart();
	}
	
	private JsonHttpResponseHandler handlerGetProductDetail = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.d(TAG, "success o= " + response.toString());
			
			closeDialog();
			try {
				JSONObject obj = response.getJSONObject("item");
				Gson gson = new Gson();
				product = gson.fromJson(obj.toString(), Product.class);
 				refreshDataByInternet(product);
			} catch (JSONException e) {

				e.printStackTrace();
			}
		}


		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			CommonUtil.showToast("查询产品详情失败"+content, ActCustomerMarketProductCompare.this.getActivity());
			closeDialog();
		}
	};
	

	private void init() {
		initTopTitle();

		initUI();

		initData();

		CommonUtil.hideSoftInputFromWindow(this.getActivity());
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		closeDialog();
	}

	private void initTopTitle() {

		TextView topTitle = (TextView) view.findViewById(R.id.textTitle);
		topTitle.setText(getResources()
				.getString(R.string.title_product_detail));

		ImageView btnBack = (ImageView) view.findViewById(R.id.imageBack);
		btnBack.setOnClickListener(this);

		view.findViewById(R.id.ivPopMenu).setVisibility(View.VISIBLE);
		view.findViewById(R.id.ivPopMenu).setOnClickListener(this);

	}

	private void initUI() {

		view.findViewById(R.id.btn_marketing).setOnClickListener(this);
		view.findViewById(R.id.btn_order_share).setOnClickListener(this);

		view.findViewById(R.id.iv_send_sms).setOnClickListener(this);
		view.findViewById(R.id.iv_star).setOnClickListener(this);
		
		view.findViewById(R.id.topLayout).setVisibility(View.GONE);
	}

	private void initData() {
		
		long id = ((ActCustomerMarketProgress)getActivity()).getMarketCs().getProdId();
		Log.i(TAG, "product id = " + id);
		if (dialog == null) {
			dialog = new CommonWaitDialog(this.getActivity(), "", R.string.load_data);
		}
		Request getProductDetail = new Request(RequestDefine.RQ_PRODUCT_DETAIL_GET, id, RequestType.GET, null,handlerGetProductDetail);
		CoreManager.getInstance().postRequest(getProductDetail);
	}

	private void refreshDataByInternet(Product product) {

		((TextView) view.findViewById(R.id.tvFixCommision)).setText(product
				.getProdCommissionBase() + "%");
		((TextView) view.findViewById(R.id.tvPayCommision)).setText(product
				.getProdCommissionBase() + "%");
		String[] proTypeLevelFirst = (String[]) getResources().getStringArray(
				R.array.product__type);
		;// 一级类别
		String[] proTypeLevelSecondAffince = (String[]) getResources()
				.getStringArray(R.array.product__affiance_type);
		;// 二级类别 基金资管
		String[] proTypeLevelSecondFund = (String[]) getResources()
				.getStringArray(R.array.product__fund_type);
		;// 二级类别 基金

		((TextView) view.findViewById(R.id.tv_product_highlight)).setText(product
				.getProdHighLights());
		((TextView) view.findViewById(R.id.tv_manager)).setText(product
				.getProdAdmin());

		long type1 = product.getProdFirstType();
		long type2 = product.getProdSecondtype();
		StringBuffer sb = new StringBuffer();
		try {
			sb.append((type1 == 1) ? (proTypeLevelSecondFund[(int) (type2 - 1)])
					: (proTypeLevelSecondAffince[(int) (type2 - 1)]));
			((TextView) view.findViewById(R.id.tv_product_type))
					.setText(proTypeLevelFirst[(int) (type1 - 1)]
							+ sb.toString());
		} catch (Exception e) {
		}
		((TextView) view.findViewById(R.id.tv_product_states)).setText(CommonUtil
				.getProductStates(product.getProdStatus()));
		((TextView) view.findViewById(R.id.tv_publish_date))
				.setText(DateUtil.getDateTimeByFormatAndMs(
						product.getProdOnDateTime(), DateUtil.FORMAT_YYYY_MM_DD)
						+ " - "
						+ DateUtil.getDateTimeByFormatAndMs(
								product.getProdEnDateTime(),
								DateUtil.FORMAT_YYYY_MM_DD));
		((TextView) view.findViewById(R.id.tv_product_scale)).setText(product
				.getProdSize() + "");
		((TextView) view.findViewById(R.id.tv_product_deadline)).setText(product
				.getProdTime() + "年");
		((TextView) view.findViewById(R.id.tv_invest_income_rate)).setText(product
				.getProdYieldFixed() + "");
		((TextView) view.findViewById(R.id.tv_mark)).setText(product
				.getProdComment());
		((TextView) view.findViewById(R.id.tv_buy_start_point)).setText(product
				.getProdStart() + "万");
		((TextView) view.findViewById(R.id.tv_counter_party)).setText(product
				.getProdCounterParty());
		((TextView) view.findViewById(R.id.tv_money_useing)).setText(product
				.getProdUse());
		((TextView) view.findViewById(R.id.tv_source_of_repayment)).setText(product
				.getProdRepayment());
		((TextView) view.findViewById(R.id.tv_risk_control_measures))
				.setText(product.getProdRisk());
		((TextView) view.findViewById(R.id.tv_income_distribution_way))
				.setText(product.getProdIncomeType());
		((TextView) view.findViewById(R.id.tv_income_distribution_date))
				.setText(product.getProdIncomeDateTime());
		((TextView) view.findViewById(R.id.tv_product_code)).setText(product
				.getProdCode());
		((TextView) view.findViewById(R.id.tv_publisher_phone)).setText(product
				.getProdCstTel());
		((TextView) view.findViewById(R.id.tv_publisher_email)).setText(product
				.getProdPubEmail());

		((TextView) view.findViewById(R.id.tv_product_name)).setText(product
				.getProdName());

		try {
			Long isCollected = getProduct().getIsSave();
			if (isCollected.longValue() == AdapterProductList.COLLECTED) {
				// 是收藏变成取消收藏
				((ImageView) view.findViewById(R.id.iv_star))
						.setImageResource(R.drawable.ic_has_collected);
			} else {

				((ImageView) view.findViewById(R.id.iv_star))
						.setImageResource(R.drawable.ic_collect);
			}
		} catch (Exception e) {

		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.imageBack:
			getActivity().finish();
			break;

		case R.id.ivPopMenu:
			List<Integer> icon = new ArrayList<Integer>();
			icon.add(R.drawable.ic_prod_inform);
//			icon.add(R.drawable.ic_train_video);
			icon.add(R.drawable.ic_contact_customer);
//			icon.add(R.drawable.ic_identification);
//			icon.add(R.drawable.ic_appoint_share);
			List<Integer> text = new ArrayList<Integer>();
			text.add(R.string.product_information);
//			text.add(R.string.train_video);
			text.add(R.string.contact_customer);
//			text.add(R.string.idenfication);
//			text.add(R.string.order_share);
			PopProdDetailList pop = new PopProdDetailList(this.getActivity(), v,
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
		default:
			break;
		}
	}
	
	private JsonHttpResponseHandler handlerCollRefreshProduct = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.d(TAG, "success o= " + response.toString());
			if (Request.RESLUT_OK.equals(response.opt("status"))) {
				CommonUtil.showToast("收藏成功", ActCustomerMarketProductCompare.this.getActivity());
				initData();
				 
			}else{
				CommonUtil.showToast("收藏失败",  ActCustomerMarketProductCompare.this.getActivity());
			}
		}
		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			CommonUtil.showToast("收藏失败"+content,  ActCustomerMarketProductCompare.this.getActivity());
		}
	};
	
	private JsonHttpResponseHandler handlerCancleCollRefreshProduct = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.d(TAG, "success o= " + response.toString());
			if (Request.RESLUT_OK.equals(response.opt("status"))) {
				CommonUtil.showToast("取消收藏成功",  ActCustomerMarketProductCompare.this.getActivity());
				initData();
				 
			}else{
				CommonUtil.showToast("取消收藏失败",  ActCustomerMarketProductCompare.this.getActivity());
			}
		}
		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			CommonUtil.showToast("取消收藏失败"+content,  ActCustomerMarketProductCompare.this.getActivity());
		}
	};
	
	private  void closeDialog(){
		if (dialog != null) {
			dialog.clearAnimation();
		}
	}

}
