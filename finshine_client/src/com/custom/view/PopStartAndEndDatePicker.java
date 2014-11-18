package com.custom.view;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.codans.blossom.datepicker.DateDrumPicker;
import com.incito.finshine.R;
import com.incito.utility.CommonUtil;

public class PopStartAndEndDatePicker implements OnClickListener{
	private final String TAG = PopStartAndEndDatePicker.class.getSimpleName();
//	private Product prod;

	private Context context = null;

	private View popView = null;

	private View clickView = null;
	private Button btnSure;
	
	private DateDrumPicker datepickerStartTime,datepickerEndTime;
	private TextView textStartTime,textEndTime;
	
	public PopupWindow getPopWin() {
		return popWin;
	}
	private PopupWindow popWin = null;

	/** popwindow 弹出方向 **/
	public static final int TOP_2_BOTTOM = 3;

	private int popDirection = TOP_2_BOTTOM;


	public PopStartAndEndDatePicker(Context context, View clickView) {

		super();

		this.clickView = clickView;
		this.context = context;

		//this.popDirection = popDirection;

		initPopwindow(clickView);
	}

	private void initPopwindow(View clickView) {

		popView = LayoutInflater.from(context).inflate(
				R.layout.pop_start_end_datepicker, null);
		popWin = new PopupWindow(popView, 1100, 420, true);

		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
		popWin.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				CommonUtil.hideSoftInputFromWindow((Activity)context);
				
			}
		});
		initData();
	}

	public void showPopWindow() {

		if (popWin != null) {
			int[] location = new int[2];
			clickView.getLocationOnScreen(location);
			switch (popDirection) {
			case TOP_2_BOTTOM:
				popWin.setAnimationStyle(R.style.anim_top_2_bottom);
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,200, location[1] + clickView.getHeight() + 3);
				break;
			default:
				break;
			}
		}
	}

	private void initData() {
		btnSure = (Button)popView.findViewById(R.id.buttonSure);
		btnSure.setOnClickListener(this);
//		Calendar calendar=Calendar.getInstance();
//        int year=calendar.get(Calendar.YEAR);
//        int monthOfYear=calendar.get(Calendar.MONTH);
//        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
//		datepickerStartTime = (DateDrumPicker)popView.findViewById(R.id.datepickerStartTime);
//		datepickerEndTime = (DateDrumPicker)popView.findViewById(R.id.datepickerEndTime);
		textStartTime = (TextView)popView.findViewById(R.id.textStartTime);
		textEndTime = (TextView)popView.findViewById(R.id.textEndTime);
//		textEndTime.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
//		datepickerStartTime.init(year, monthOfYear, dayOfMonth, new OnDateChangedListener(){
//
//			@Override
//			public void onDateChanged(DatePicker view, int year, int monthOfYear,
//					int dayOfMonth) {
//				textStartTime.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
//			}
//			
//		});
//		datepickerEndTime.init(year, monthOfYear, dayOfMonth, new OnDateChangedListener(){
//
//			@Override
//			public void onDateChanged(DatePicker view, int year, int monthOfYear,
//					int dayOfMonth) {
//				textEndTime.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
//			}
//			
//		});
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
	
	public interface DatePickerListener{
		public void setTextValue(String value);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.buttonSure:
			DatePickerListener datePickerListener = (DatePickerListener)context;
			datePickerListener.setTextValue(textStartTime.getText().toString() + "----" + textEndTime.getText().toString());
			closePopWindow();
			break;
		default:break;
		}
	}

}
