package com.custom.view;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.activity.FragmentEconomyInfo;
import com.incito.finshine.activity.adapter.AdapterPopCustomerMarketing;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.CustomerMarketingStatistics;
import com.incito.finshine.entity.InvestmentDistributionStatistics;
import com.incito.finshine.entity.InvestmentRecord;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.RequestDefine;
import com.incito.finshine.network.Request.RequestType;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

/**
 * <dl>
 * <dt>PopCsInvestDetail.java</dt>
 * <dd>Description:客户管理  客户投资详情</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-5-30 上午9:36:34</dd>
 * </dl>
 * 
 * @author lihs
 */
public class PopCsInvestDetail {

	private static final String TAG = PopCsInvestDetail.class.getSimpleName();
	private Customer customer;
	
	private Context context = null;
	
	private View popView = null; 
	
	private View clickView = null;
	
	private PopupWindow popWin = null;
	
	private CustomerMarketingStatistics customerMarketing;
	private List<InvestmentDistributionStatistics> investList;
	private AdapterPopCustomerMarketing adapterMarketing;
	
	TextView saleName;
	TextView purchasedCustomerQty;
	TextView customerQty;
	TextView purchasedQuantity;
	TextView purchasedTotalAmount;
	ListView listInvestMarketing;
	
	private int popDirection = 0;
	
	/**popwindow 弹出方向**/
	public static final int  LEFT_2_RIGHT = 1; 
	public static final int  RIGHT_2_LEFT = 2; 
	public static final int  TOP_2_BOTTOM = 3; 
	
	private int backGroundId = -1;
	/**popwindow 背景图片**/
	
	private SPManager spManager;
	 
	public PopCsInvestDetail(Context context,View clickView,Customer cs,int backGroundId,int popDirection) {
		
		super();
		this.customer = cs;
		this.clickView = clickView;
		this.context = context;
		
		this.backGroundId = backGroundId;
		this.popDirection = popDirection;
		
		spManager = SPManager.getInstance();
		initPopwindow(clickView);
		getCustomerMarketing();
	}
	
	//获取理财师   统计的数量  上部分
	private boolean getCustomerMarketing(){
		JSONObject json = new JSONObject();
		try {
			json.put("salesId", spManager.getLongValue(SPManager.ID));
			json.put("expirationDays", 30);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Request request = new Request(RequestDefine.RQ_CUSTOMER_POST_MARKETING,
				RequestType.POST, json, handlerGetCustomerMarketing);
		CoreManager.getInstance().postRequest(request);
		
		return true;
	}
	
	//获取理财师   统计的数量  下部分
		private boolean getInvestmentDistribution(){
			JSONObject json = new JSONObject();
			try {
				json.put("salesId", spManager.getLongValue(SPManager.ID));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Request request = new Request(RequestDefine.RQ_CUSTOMER_POST_INVEST_DISTRIBUTION,
					RequestType.POST, json, handlerGetInvestmentDistribution);
			CoreManager.getInstance().postRequest(request);
			
			return true;
		}
	
	
	private JsonHttpResponseHandler handlerGetCustomerMarketing = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.i(TAG, "success o= " + response.toString());

			int statues = response.optInt("status");
			if (0 == statues) {
				Log.i(TAG, "success a= " + response.toString());
				Gson gson = new Gson();
				
				customerMarketing = gson.fromJson(response.optString("item"),
						CustomerMarketingStatistics.class);
				
				if (customerMarketing != null)
				getInvestmentDistribution();
			}
		}

		@Override
		public void onSuccess(JSONArray response) {
			Log.i(TAG, "success a= " + response.toString());
		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
		}
	};

	private JsonHttpResponseHandler handlerGetInvestmentDistribution = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.i(TAG, "success o= " + response.toString());

			int statues = response.optInt("status");
			if (0 == statues) {
				Log.i(TAG, "success a= " + response.toString());
				Gson gson = new Gson();
				
				investList = gson.fromJson(response.optString("list"),
						new TypeToken<List<InvestmentDistributionStatistics>>() {
				}.getType());
				
				initDate();
				
				if (adapterMarketing != null) {
					adapterMarketing.setItemList(investList);
					adapterMarketing.setLayout(R.layout.row_pop_invest_analysis);
					listInvestMarketing.setAdapter(adapterMarketing);
				}
			}
		}

		@Override
		public void onSuccess(JSONArray response) {
			Log.i(TAG, "success a= " + response.toString());
		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
		}
	};

	
	
	private void initDate(){
		saleName.setText(spManager.getStringValue(SPManager.NAME));
		purchasedCustomerQty.setText(customerMarketing.getPurchasedCustomerQty() + "人");
		customerQty.setText(customerMarketing.getCustomerQty() + "人");
		purchasedQuantity.setText(customerMarketing.getPurchasedQuantity() + "个");
		purchasedTotalAmount.setText(customerMarketing.getPurchasedTotalAmount() + "万");
	}
	
	private void initPopwindow(View clickView){
		
		popView = LayoutInflater.from(context).inflate(R.layout.pop_cs_invest_detail, null);
		popWin = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
		
		if (backGroundId > -1) {
			popView.findViewById(R.id.lt_pop).setBackgroundResource(backGroundId);
		} 
		
		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
		
		saleName = (TextView) popView.findViewById(R.id.saleName);
		purchasedCustomerQty = (TextView) popView.findViewById(R.id.purchasedCustomerQty);
		customerQty = (TextView) popView.findViewById(R.id.customerQty);
		purchasedQuantity = (TextView) popView.findViewById(R.id.purchasedQuantity);
		//签单额数据统计有问题
		purchasedTotalAmount = (TextView) popView.findViewById(R.id.purchasedTotalAmount);
	
		listInvestMarketing = (ListView) popView.findViewById(R.id.investList);
		if (adapterMarketing == null)
			adapterMarketing = new AdapterPopCustomerMarketing(context);
	}
 
    
    public void showPopWindow(){
    	if (popWin != null) {
    		int[] location = new int[2];
            clickView.getLocationOnScreen(location);
            switch (popDirection) {
			case LEFT_2_RIGHT:
				// 从左到右弹出
				popWin.setAnimationStyle(R.style.anim_left_2_right);
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY, location[0] + clickView.getWidth() + 2, location[1] + clickView.getHeight()/2 - 52);
				break;
			case RIGHT_2_LEFT:
				// 从右到左弹出
				popWin.setAnimationStyle(R.style.product_more_windown_animstyle);
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY, location[0] + 2, location[1] + clickView.getHeight()/2 - 54);
				break;
			case TOP_2_BOTTOM:
				popWin.setAnimationStyle(R.style.anim_top_2_bottom);
				int screenW = CommonUtil.getScreenWidth(context);
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY, screenW - 610, location[1] + clickView.getHeight() + 3);
				break;
			default:
				break;
			}
		}
    }
	
    public void closePopWindow(){
    	if (popWin != null) {
			if (popWin.isShowing()) {
				popWin.dismiss();
				popWin = null;
			}
		}
    }
    
	public boolean isShowing(){
		
		if (popWin != null) {
			if (popWin.isShowing()) {
				return true;
			}
		}
		return false;
	}

}
