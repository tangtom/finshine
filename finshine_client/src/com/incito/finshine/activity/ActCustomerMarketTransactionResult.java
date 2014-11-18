package com.incito.finshine.activity;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.MarKetCustomer;
import com.incito.finshine.entity.MarketCsOrder;
import com.incito.finshine.entity.OrderInfoItem;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.MoneyFormat;

/**
 * 
 * <dl>
 * <dt>ActCustomerMarketTransactionResult.java</dt>
 * <dd>Description:客户营销 交易结果</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月12日 下午2:15:15</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class ActCustomerMarketTransactionResult extends FragmentDetail {
	private View view;

	public static ActCustomerMarketTransactionResult newInstance(int id,
			int position) {

		ActCustomerMarketTransactionResult f = new ActCustomerMarketTransactionResult();
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
		view = inflater.inflate(
				R.layout.act_customer_market_transaction_result, container,
				false);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		queryOrderInfo();
	}

	@Override
	public void onStart() {
		init();
		super.onStart();
	}

	private boolean init() {

		// 上一页
		Button btnPre = (Button) view.findViewById(R.id.btnPre);
		btnPre.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((ActCustomerMarketProgress) getActivity()).showDetails(
						ActCustomerMarketProgress.F_ORDER_PAYMENT2, 0);
			}
		});
		// 进入订单管理
		Button btnEntetOrderManager = (Button) view
				.findViewById(R.id.btnEntetOrderManager);
		btnEntetOrderManager.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				ActCustomerMarketProgress.currentView = ActCustomerMarketProgress.F_TRANSACTION;

				MarKetCustomer mar = new MarKetCustomer();
				mar.setSalesId(order.getSalesId());
				mar.setId(order.getCustomerId());
				Intent i = new Intent();
				i.putExtra(Constant.ACTION_TO_ORDER_MANAGER, mar);
				i.putExtra(ActCustomerMarketOrderManager.KEY_ISORDERMANAGER,
						true);
				i.setClass(getActivity(), ActCustomerMarketOrderManager.class);
				getActivity().startActivity(i);
			}
		});
		return true;
	}

	List<OrderInfoItem> orderList;
	MarketCsOrder order;

	private void queryOrderInfo() {

		/**
		 * 根据订单id 和客户id 和理财师 id 查询该订单信息
		 */
		ActCustomerMarketProgress activity = (ActCustomerMarketProgress) getActivity();
		order = activity.getMarketCs();
		JSONObject params = new JSONObject();
		try {
			params.put("salesId", order.getSalesId());
			params.put("customerId", order.getCustomerId());
			params.put("salesOrderId", order.getSalesOrderId());

		} catch (JSONException e) {

			e.printStackTrace();
			return;
		}

		HttpUtils httpUtils = new HttpUtils(getActivity(),
				RequestDefine.MARKET_RQ_QUERY_ORDER_LIST, RequestType.POST,
				params);
		httpUtils.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {

				if (Request.RESLUT_OK.equals(response.optString("status"))) {
					Gson gson = new Gson();
					orderList = gson.fromJson(response.optJSONArray("list")
							.toString(), new TypeToken<List<OrderInfoItem>>() {
					}.getType());
					refreshData();
				}
			}
		});
		httpUtils.executeRequest();
	}

	private void refreshData() {

		if (orderList != null && orderList.size() > 0) {
			OrderInfoItem order = orderList.get(0);
			// 订单编号
			((TextView) view.findViewById(R.id.tv1)).setText(order
					.getSalesOrderNumber());
			// 生成时间
			((TextView) view.findViewById(R.id.tv2)).setText(order
					.getCreatedStr());
			// 订单总额
			((TextView) view.findViewById(R.id.tv3)).setText(String
					.valueOf(order.getChangeAmount()));
			// 订单状态
			((TextView) view.findViewById(R.id.tv4)).setText(order
					.getOrderStatusName());
			// 客户姓名
			((TextView) view.findViewById(R.id.tv5)).setText(order
					.getCustomerName());
			// 产品名称
			((TextView) view.findViewById(R.id.tv6)).setText(order
					.getProdName());
			// 订购份额
			((TextView) view.findViewById(R.id.tv7)).setText(String
					.valueOf(order.getChangeAmount()));
			// 认购金额
			((TextView) view.findViewById(R.id.tv8)).setText(String
					.valueOf(order.getChangeAmount()));

			double money = order.getChangeAmount();
			BigDecimal bd = new BigDecimal(money);
			String moneys = "";
			if (money > 0) {
				moneys = "人民币" + new MoneyFormat().format(bd.toPlainString());
			} else {
				moneys = "人民币 0 万元";
			}
			((TextView) view.findViewById(R.id.tv9)).setText(moneys);
		}
	}

}
