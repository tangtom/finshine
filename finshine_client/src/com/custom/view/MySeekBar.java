package com.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class MySeekBar extends SeekBar {

	public MySeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		 
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		 
		 // 不执行滑动
		 return false;
	}

}
