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

public class PopCustomerService implements OnClickListener{
	private Context context = null;
	private View popView = null;
	private View clickView = null;
	private Button btnSure,btnCancel;
	public PopupWindow getPopWin() {
		return popWin;
	}
	private PopupWindow popWin = null;

	/** popwindow 弹出方向 **/
	public static final int TOP_2_BOTTOM = 3;

	private int popDirection = TOP_2_BOTTOM;


	public PopCustomerService(Context context, View clickView) {

		super();
		this.clickView = clickView;
		this.context = context;
		initPopwindow(clickView);
	}

	private void initPopwindow(View clickView) {

		popView = LayoutInflater.from(context).inflate(
				R.layout.pop_customer_service, null);
		popWin = new PopupWindow(popView, 400, 300, true);

		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
		popWin.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				CommonUtil.hideSoftInputFromWindow((Activity)context);
				
			}
		});
		btnSure = (Button)popView.findViewById(R.id.btnSure);
		btnCancel = (Button)popView.findViewById(R.id.btnCancel);
		btnSure.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}

	public void showPopWindow() {

		if (popWin != null) {
			int[] location = new int[2];
			clickView.getLocationOnScreen(location);
			switch (popDirection) {
			case TOP_2_BOTTOM:
				popWin.setAnimationStyle(R.style.anim_top_2_bottom);
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,450, location[1] + clickView.getHeight() + 190);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.btnSure:
			CommonUtil.dialPhone(context, "13576895541");
			break;
		case R.id.btnCancel:
			closePopWindow();
			break;
		default:break;
		}
	}

}
