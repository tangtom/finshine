package com.custom.view;

import java.math.BigDecimal;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterPopUseCommission;
import com.incito.finshine.entity.PropsAvailable;
import com.incito.finshine.entity.PropsUsedItemWSEntity;
import com.incito.utility.CommonUtil;

public class PopPointerMarket{
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


	public PopPointerMarket(Context context, View clickView) {

		super();
		this.clickView = clickView;
		this.context = context;
		initPopwindow(clickView);
	}

	private void initPopwindow(View clickView) {

		popView = LayoutInflater.from(context).inflate(
				R.layout.pop_pointer_market, null);
		popWin = new PopupWindow(popView, 300, 300, true);

		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
		popWin.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				CommonUtil.hideSoftInputFromWindow((Activity)context);
				
			}
		});
		Button btnPointerMarket = (Button)popView.findViewById(R.id.btnPointerMarket);
		btnPointerMarket.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				closePopWindow();
			}
		});
	}

	public void showPopWindow() {

		if (popWin != null) {
			int[] location = new int[2];
			clickView.getLocationOnScreen(location);
			switch (popDirection) {
			case TOP_2_BOTTOM:
				popWin.setAnimationStyle(R.style.anim_top_2_bottom);
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,750, location[1] + clickView.getHeight() + 3);
				break;
			default:
				break;
			}
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
