package com.custom.view;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.incito.finshine.R;
import com.incito.finshine.activity.ActBind11;
import com.incito.finshine.activity.FragmentDetail;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.event.EventBus;

public class PopMarketSign implements OnClickListener{
	private Context context = null;
	private View popView = null;
	private View clickView = null;
	private Button btnSure;
	private Button buttonReturn;
	private Button btnCancel;
	private SignView myView;
	private ImageView imgView;
	private Dialog dialog;
	public PopupWindow getPopWin() {
		return popWin;
	}
	private PopupWindow popWin = null;
	/** popwindow 弹出方向 **/
	public static final int TOP_2_BOTTOM = 3;
	private int popDirection = TOP_2_BOTTOM;
	
	public PopMarketSign(Context context, View clickView) {

		super();
		this.clickView = clickView;
		this.context = context;
		dialog = new Dialog(context,R.style.CustomProgressDialog);
		initPopwindow(clickView);
		/*设置点击外部不关闭*/
		popWin.setOutsideTouchable(false);
	}
	
	private void initPopwindow(View clickView) {

		popView = LayoutInflater.from(context).inflate(
				R.layout.pop_market_sign, null);
		/*LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);*/
		
		LayoutParams params = new LayoutParams(1000,600);
		dialog.addContentView(popView, params);
		popWin = new PopupWindow(popView, 1000, 600, true);
 		/*popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,140, 80);*/
		popWin.setBackgroundDrawable(new ColorDrawable());
		dialog.setCanceledOnTouchOutside(false);
		popWin.setOutsideTouchable(false);
		popWin.setTouchable(true);
		popWin.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				CommonUtil.hideSoftInputFromWindow((Activity)context);
				
			}
		});
		btnSure = (Button)popView.findViewById(R.id.buttonSure);
		btnSure.setOnClickListener(this);
		buttonReturn = (Button)popView.findViewById(R.id.buttonReturn);
		buttonReturn.setOnClickListener(this);
		btnCancel = (Button)popView.findViewById(R.id.buttonCancel);
		btnCancel.setOnClickListener(this);
		imgView = (ImageView)popView.findViewById(R.id.imgView);
		
		/*------------画笔准备--------------*/
		Paint mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(0xFF000000);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(10);
		myView = (SignView) popView.findViewById(R.id.signView);
		myView.setPaint(mPaint);
	}
	public void showPopWindow() {

		if (popWin != null) {
			int[] location = new int[2];
			clickView.getLocationOnScreen(location);
			switch (popDirection) {
			case TOP_2_BOTTOM:
//				popWin.setAnimationStyle(R.style.anim_top_2_bottom);
//				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,140, 80);
				dialog.show();
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
		switch(v.getId())
		{
		case R.id.buttonSure:
			dialog.dismiss();
			closePopWindow();
			EventBus.getDefault().postSticky(myView.getView());
			break;
		case R.id.buttonReturn:
			myView.paintClear();
			break;
		case R.id.buttonCancel:
			dialog.dismiss();
			closePopWindow();
			break;
		}
	}
}
