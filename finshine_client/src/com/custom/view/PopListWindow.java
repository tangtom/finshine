package com.custom.view;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.incito.finshine.R;

public class PopListWindow implements OnClickListener{

	
	private Context context = null;
	
	private View popView = null; 
	
	private View clickView = null;
	
	private PopupWindow popWin = null;
	
	private List<Integer> idList = null;
	
	private int popDirection = 0;
	
	/**popwindow 弹出方向**/
	public static final int  LEFT_2_RIGHT = 1; 
	public static final int  RIGHT_2_LEFT = 2; 
	
	private int backGroundId = -1;
	/**popwindow 背景图片**/
	
	private Object obj = null;
 
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	public PopListWindow(Context context,View clickView,List<Integer> idList,int backGroundId,int popDirection) {
		
		super();
		this.idList = idList;
		this.clickView = clickView;
		this.context = context;
		
		this.backGroundId = backGroundId;
		this.popDirection = popDirection;
		
		initPopwindow(clickView);
	}
	
	
	
	private void initPopwindow(View clickView){
		
		popView = LayoutInflater.from(context).inflate(R.layout.pop_list_window, null);
		popWin = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
		if (backGroundId > -1) {
			addLt = (LinearLayout)popView.findViewById(R.id.lt_list_pop);
			addLt.setBackgroundResource(backGroundId);
		} 
		
		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
		
		addView();
	}
	
	private LinearLayout addLt = null;
    public void addView(){
    	
    	 TextView tv = null;
         if (idList != null && idList.size() > 0) {
//        	 LayoutParams params =  new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        	 addLt.setLayoutParams(params);
//        	 addLt.setBackgroundResource(R.drawable.bg_comm);
//        	 addLt.setGravity(Gravity.CENTER);
//        	 addLt.setOrientation(LinearLayout.HORIZONTAL);
        	 for (Integer item : idList) {
        		 tv = new TextView(context);
        		 // 不起效果
        		 tv.setTextColor(this.context.getResources().getColorStateList(R.drawable.select_common_text_brownd));
        		 tv.setBackgroundResource(R.drawable.shape_common_radiusbtn);
        		 tv.setText(item);
        		 tv.setOnClickListener(this);
        		 LinearLayout.LayoutParams paramsBtn = new LinearLayout.LayoutParams(120, 45);
        		 paramsBtn.setMargins(0, 0, 10, 0);
        		 tv.setLayoutParams(paramsBtn);
        		 tv.setId(item);
        		 tv.setGravity(Gravity.CENTER);
        		 tv.setTextSize(16);
/*        		 if(item == R.string.application_feed)
        		 {
        			 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(140, 45);
            		 params.setMargins(0, 0, 10, 0);
            		 tv.setLayoutParams(params);
            		 tv.setId(item);
            		 tv.setGravity(Gravity.CENTER);
        			 tv.setTextSize(12);
        		 }*/
        		 addLt.addView(tv);
			}
		 }
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
				if (idList.size() == 3) {
					popWin.showAtLocation(clickView, Gravity.NO_GRAVITY, location[0] - 430, location[1] + clickView.getHeight()/2 - 54);
				}else if(idList.size() == 6){
					popWin.showAtLocation(clickView, Gravity.NO_GRAVITY, location[0] - 830, location[1] + clickView.getHeight()/2 - 56);
				}
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
	
 
	
	private CallBackCLickEvent interCb = null;
 
	public void setCallBackCLickEvent(CallBackCLickEvent interCb) {
		this.interCb = interCb;
	}

	public interface CallBackCLickEvent{
		public void doClick(int position,Object obj);
	}

	@Override
	public void onClick(View v) {

		Integer tag = (Integer)v.getId();
		for (int i = 0 ; i< idList.size() ; i++) {
			if(idList.get(i).intValue() == tag.intValue()){
				if (interCb != null) {
//					closePopWindow();
					interCb.doClick(i, interCb);
					return;
				}
			}
		}
	}
}
