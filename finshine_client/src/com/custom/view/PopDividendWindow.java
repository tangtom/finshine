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
import com.incito.finshine.activity.adapter.AdapterPopCustomerDivided;
import com.incito.finshine.activity.adapter.AdapterPopCustomerMarketing;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.CustomerMarketingStatistics;
import com.incito.finshine.entity.InvestmentDistributionStatistics;
import com.incito.finshine.entity.ProdProfitResult;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.RequestDefine;
import com.incito.finshine.network.Request.RequestType;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

//分红弹出的界面
public class PopDividendWindow {

	private static final String TAG = PopDividendWindow.class.getSimpleName();

	private Customer customer;
	private Context context = null;

	private View popView = null;

	private View clickView = null;

	private PopupWindow popWin = null;

	/** popwindow 弹出方向 **/
	public static final int LEFT_2_RIGHT = 1;
	public static final int RIGHT_2_LEFT = 2;
	public static final int TOP_2_BOTTOM = 3;

	private int popDirection = 0;

	private int backGroundId = -1;
	/** popwindow 背景图片 **/

	private AdapterPopCustomerDivided adapterMarketing;

	private List<ProdProfitResult> dividedList;

	TextView customerName;
	TextView dividedCount;
	ListView listInvestMarketing;

	public PopDividendWindow(Context context, View clickView, Customer cs,
			int backGroundId, int popDirection) {

		super();
		this.customer = cs;
		this.clickView = clickView;
		this.context = context;

		this.backGroundId = backGroundId;
		this.popDirection = popDirection;

		initPopwindow(clickView);

		getProdProfitList();
	}

	private void initPopwindow(View clickView) {

		popView = LayoutInflater.from(context).inflate(
				R.layout.pop_share_money, null);
		// popWin = new PopupWindow(popView,
		// LinearLayout.LayoutParams.WRAP_CONTENT,
		// LinearLayout.LayoutParams.WRAP_CONTENT,true);
		popWin = new PopupWindow(popView, 550, 350, true);

		// if (backGroundId > -1) {
		// popView.findViewById(R.id.lt_pop).setBackgroundResource(backGroundId);
		// }
		//
	    popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);

		customerName = (TextView) popView.findViewById(R.id.txtCustomerName);
		dividedCount = (TextView) popView.findViewById(R.id.txtDividedCount);

		customerName.setText(customer.getName());
		dividedCount.setText(customer.getProdDividendQty() + "");

		listInvestMarketing = (ListView) popView.findViewById(R.id.investList);
		if (adapterMarketing == null)
			adapterMarketing = new AdapterPopCustomerDivided(context);
	}

	// 获取客户分红列表
	private boolean getProdProfitList() {
		JSONObject json = new JSONObject();
		try {
			json.put("customerId", customer.getId());
			json.put("expirationDays", 14);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Request request = new Request(RequestDefine.RQ_CUSTOMER_POST_DIVIDED,
				RequestType.POST, json, handlerGetProdProfitList);
		CoreManager.getInstance().postRequest(request);

		return true;
	}

	private JsonHttpResponseHandler handlerGetProdProfitList = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.i(TAG, "success o= " + response.toString());

			int statues = response.optInt("status");
			if (0 == statues) {
				Log.i(TAG, "success a= " + response.toString());
				Gson gson = new Gson();

				dividedList = gson.fromJson(response.optString("list"),
						new TypeToken<List<ProdProfitResult>>() {
						}.getType());

				if (dividedList != null)
				{
					if (adapterMarketing != null) {
						adapterMarketing.setItemList(dividedList);
						listInvestMarketing.setAdapter(adapterMarketing);
					}
				}
			}
		}

		@Override
		public void onSuccess(JSONArray response) {
			Log.i(TAG, "success a= " + response.toString());
		}

		@Override
		public void onFailure(Throwable error, String content) {
			CommonUtil.showToast("获取分红信息失败", context);
			Log.i(TAG, "onFailure = " + content);
		}
	};

	
	public void showPopWindow(){
    	if (popWin != null) {
    		int[] location = new int[2];
            clickView.getLocationOnScreen(location);
            
//            int screenW = CommonUtil.getScreenWidth(context);
//    		popSMw.showAtLocation(view, Gravity.NO_GRAVITY, screenW / 2 - 200,
//    				location[1] - 14);
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
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY, screenW / 2 - 200, location[1] + 54);
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
