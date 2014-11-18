package com.custom.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {
	
	 private boolean enabled;//true：可以滑    false:不能滑动

		public MyViewPager(Context context, AttributeSet attrs) {
			super(context, attrs);
			this.enabled = true;
		}

			//触摸没有反应就可以了
		    @Override
		    public boolean onTouchEvent(MotionEvent event) {
		        if (this.enabled) {
		            return super.onTouchEvent(event);
		        }
		  
		        return false;
		    }

//		    @Override
//			public boolean arrowScroll(int arg0) {
//				// TODO Auto-generated method stub
//				return super.arrowScroll(arg0);
//			}

			@Override
		    public boolean onInterceptTouchEvent(MotionEvent event) {
		        if (this.enabled) {
		            return super.onInterceptTouchEvent(event);
		        }
		 
		        return false;
		    }
		 
		    public void setPagingEnabled(boolean enabled) {
		        this.enabled = enabled;
		    }

}
