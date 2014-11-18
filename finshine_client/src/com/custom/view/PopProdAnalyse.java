package com.custom.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.incito.finshine.R;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.RequestDefine;
import com.incito.finshine.network.Request.RequestType;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

public class PopProdAnalyse{
	private Context context = null;
	private View popView = null;
	private View clickView = null;
	private TextView txtProdTotal,txtProdWillSale,txtProdsaleing,txtProdsaled;
	private List<Integer> listStatus = new ArrayList<Integer>();////将要向服务器请求的数据
	private List<Long> listBackStatus = new ArrayList<Long>();//存储从服务器返回的数据
	public PopupWindow getPopWin() {
		return popWin;
	}
	private PopupWindow popWin = null;

	/** popwindow 弹出方向 **/
	public static final int TOP_2_BOTTOM = 3;

	private int popDirection = TOP_2_BOTTOM;


	public PopProdAnalyse(Context context, View clickView) {

		super();
		this.clickView = clickView;
		this.context = context;
		initPopwindow(clickView);
	}

	private void initPopwindow(View clickView) {

		popView = LayoutInflater.from(context).inflate(
				R.layout.pop_prod_analyse, null);
		popWin = new PopupWindow(popView, 275, 300, true);
		txtProdTotal = (TextView)popView.findViewById(R.id.txtProdTotal);
		txtProdWillSale = (TextView)popView.findViewById(R.id.txtProdWillSale);
		txtProdsaleing = (TextView)popView.findViewById(R.id.txtProdsaleing);
		txtProdsaled = (TextView)popView.findViewById(R.id.txtProdsaled);
		listStatus.add(5);
		listStatus.add(6);
		getServiceData(listStatus.get(0));
		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
		popWin.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				CommonUtil.hideSoftInputFromWindow((Activity)context);
				
			}
		});
	}
	
	public void valueChange()
	{
		txtProdTotal.setText(String.valueOf(listBackStatus.get(0) + listBackStatus.get(1)));
//		txtProdWillSale.setText(String.valueOf(listBackStatus.get(1)));
		txtProdsaleing.setText(String.valueOf(listBackStatus.get(0)));
		txtProdsaled.setText(String.valueOf(listBackStatus.get(1)));
	}
	
	private JsonHttpResponseHandler mHandler = new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(JSONObject response) {
			try {
				if(response.getLong("status") == 0)
				{
					listBackStatus.add(response.getLong("item"));
					if(!listStatus.isEmpty())
					{
						listStatus.remove(0);
						if(listStatus.size() > 0)
						{
							getServiceData(listStatus.get(0));
						}
						else
						{
							valueChange();
						}
					}
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.onSuccess(response);
		}

		@Override
		public void onSuccess(int statusCode, JSONObject response) {
			super.onSuccess(statusCode, response);
		}

		@Override
		public void onFailure(Throwable e, JSONObject errorResponse) {
			// TODO Auto-generated method stub
			super.onFailure(e, errorResponse);
		}
		
	};

	public void showPopWindow() {

		if (popWin != null) {
			int[] location = new int[2];
			clickView.getLocationOnScreen(location);
			switch (popDirection) {
			case TOP_2_BOTTOM:
				popWin.setAnimationStyle(R.style.anim_top_2_bottom);
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,930, location[1] + clickView.getHeight() + 3);
				break;
			default:
				break;
			}
		}
	}
	
	public void getServiceData(int status)
	{
		JSONObject params = new JSONObject();
		try {
			params.put("prodStatus",status);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Request request = new Request(RequestDefine.RQ_STATUS_ANALYSE,RequestType.POST,params, mHandler);
		CoreManager.getInstance().postRequest(request);
	}
	
	public void closePopWindow() {
		if (popWin != null) {
			if (popWin.isShowing()) {
				popWin.dismiss();
//				popWin = null;
			}
		}
	}

	public boolean isShowing() {

		if (popWin != null) {
			if (popWin.isShowing()) {
				return true;
			}
		}
		return false;
	}

}
