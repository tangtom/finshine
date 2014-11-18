package com.custom.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.incito.finshine.R;
import com.incito.finshine.entity.FirstPageCount;
import com.incito.utility.CommonUtil;

public class PopCustomerMarketDetail {


	private Context context = null;

	private View popView = null;

	private View clickView = null;

	public PopupWindow getPopWin() {
		return popWin;
	}
	private PopupWindow popWin = null;

	/** popwindow 弹出方向 **/
	public static final int TOP_2_BOTTOM = 3;

	private int popDirection = TOP_2_BOTTOM;

	private FirstPageCount csOrder = null;
	
	public void setCsOrder(FirstPageCount csOrder) {
		this.csOrder = csOrder;
	}

	public PopCustomerMarketDetail(Context context, View clickView, 
			int popDirection) {

		super();

		this.clickView = clickView;
		this.context = context;

		this.popDirection = popDirection;

		initPopwindow(clickView);
	}

	private void initPopwindow(View clickView) {

		popView = LayoutInflater.from(context).inflate(R.layout.pop_customer_market_detail, null);
		popWin = new PopupWindow(popView, 280, 350, true);

		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
	}

	public void showPopWindow() {

		initData();

		if (popWin != null) {
			int[] location = new int[2];
			clickView.getLocationOnScreen(location);
			switch (popDirection) {
			case TOP_2_BOTTOM:
				popWin.setAnimationStyle(R.style.anim_top_2_bottom);
				int screenW = CommonUtil.getScreenWidth(context);
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,screenW - 300, location[1] + clickView.getHeight() + 3);
				break;
			default:
				break;
			}
		}
	}

	private void initData() {
		
		if (csOrder != null) {
			((TextView)popView.findViewById(R.id.tvHistoryRecord)).setText(String.valueOf(csOrder.getPurchasedQuantity()));
			((TextView)popView.findViewById(R.id.tvAlreadyCs)).setText(String.valueOf(csOrder.getBindQty()));
			((TextView)popView.findViewById(R.id.tvTotalCs)).setText(String.valueOf(csOrder.getCustomerQty()));
			((TextView)popView.findViewById(R.id.tvCurrentOrder)).setText(String.valueOf(csOrder.getMarketingQty()));
		}else{
			((TextView)popView.findViewById(R.id.tvHistoryRecord)).setText(String.valueOf(0));
			((TextView)popView.findViewById(R.id.tvAlreadyCs)).setText(String.valueOf(0));
			((TextView)popView.findViewById(R.id.tvTotalCs)).setText(String.valueOf(0));
			((TextView)popView.findViewById(R.id.tvCurrentOrder)).setText(String.valueOf(0));
		}
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
