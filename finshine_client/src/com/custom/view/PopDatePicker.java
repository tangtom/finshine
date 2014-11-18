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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.codans.blossom.datepicker.DateDrumPicker;
import com.incito.finshine.R;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.wisdomsdk.event.EventBus;

public class PopDatePicker implements OnClickListener,OnCheckedChangeListener{
	private final String TAG = PopPointerAnalyse.class.getSimpleName();
//	private Product prod;

	private Context context = null;

	private View popView = null;

	private View clickView = null;
	private Button btnSure;
	private Button btnRadioAll;
	private DateDrumPicker datepickerStartTime,datepickerEndTime;
	private TextView textStartTime,textEndTime,txtPopTitle;
	private boolean isJudge = false;//判断点击此控件的地方
	private boolean isProductManager = false;//判断是否为产品管理类的点击
	
	public void setIsProductManager(boolean isProductManager)
	{
		this.isProductManager = isProductManager;
	}
	
	public PopupWindow getPopWin() {
		return popWin;
	}
	private PopupWindow popWin = null;

	/** popwindow 弹出方向 **/
	public static final int TOP_2_BOTTOM = 3;

	private int popDirection = TOP_2_BOTTOM;


	public PopDatePicker(Context context, View clickView) {

		super();
		//this.prod = prod;


		this.clickView = clickView;
		this.context = context;

		//this.popDirection = popDirection;

		initPopwindow(clickView);
	}

	public PopDatePicker(Context context, View clickView,boolean isJudge) {

		super();
		//this.prod = prod;


		this.clickView = clickView;
		this.context = context;
		this.isJudge = isJudge;
		//this.popDirection = popDirection;

		initPopwindow(clickView);
	}
	
	private void initPopwindow(View clickView) {
		popView = LayoutInflater.from(context).inflate(R.layout.pop_datepicker, null);
		popWin = new PopupWindow(popView, 1040, 470, true);

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
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,120, location[1] + clickView.getHeight() + 3);
				break;
			default:
				break;
			}
		}
	}

	private void initData() {
		btnSure = (Button)popView.findViewById(R.id.buttonSure);
		btnSure.setOnClickListener(this);
		btnRadioAll = (Button)popView.findViewById(R.id.btnRadioAll);
		btnRadioAll.setOnClickListener(this);
		txtPopTitle = (TextView)popView.findViewById(R.id.txtPopTitle);
		if(isJudge)
		{
			txtPopTitle.setText("有效期");
			btnRadioAll.setVisibility(View.INVISIBLE);
		}
		datepickerStartTime = (DateDrumPicker)popView.findViewById(R.id.datepickerStartTime);
		datepickerEndTime = (DateDrumPicker)popView.findViewById(R.id.datepickerEndTime);
		textStartTime = (TextView)popView.findViewById(R.id.textStartTime);
		textEndTime = (TextView)popView.findViewById(R.id.textEndTime);
		datepickerStartTime.setOnDateChangedListener(new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker arg0, int year, int month, int date) {
				StringBuffer sb = new StringBuffer();
		        sb.append(year).append("年").append(month).append("月").append(date).append("日");
		        textStartTime.setText(sb.toString());
			}
		});
		datepickerEndTime.setOnDateChangedListener(new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker arg0, int year, int month, int date) {
				StringBuffer sb = new StringBuffer();
		        sb.append(year).append("年").append(month).append("月").append(date).append("日");
		        textEndTime.setText(sb.toString());
			}
		});
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
			String startTime = textStartTime.getText().toString();
			String endTime = textEndTime.getText().toString();
			long longStartTime = DateUtil.formatString2Date(startTime,DateUtil.FORMAT_YYYY_MM_DD_ZH).getTime();
			long longEndTime = DateUtil.formatString2Date(endTime,DateUtil.FORMAT_YYYY_MM_DD_ZH).getTime();
			if(isProductManager)//产品管理处的筛选
			{
				
			}
			else//我的积分处的筛选
			{
				if(longEndTime > System.currentTimeMillis())
				{
					CommonUtil.showToast("结束时间不能大于当前时间",context);
					return;
				}
			}
			
			if(longStartTime >= longEndTime)
			{
				CommonUtil.showToast("开始时间必须小于结束时间",context);
			}else
			{
//				DatePickerListener datepickerListener = (DatePickerListener)context;
//				datepickerListener.setTextValue(startTime + "----" + endTime );
				EventBus.getDefault().postSticky(startTime + "----" + endTime );
				closePopWindow();
			}
			break;
		case R.id.btnRadioAll:
			EventBus.getDefault().postSticky("全部");
			closePopWindow();
			break;
		default:break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean b) {
		if(b){
			EventBus.getDefault().postSticky("全部");
			arg0.setChecked(!b);
			closePopWindow();
		}
	}

}
