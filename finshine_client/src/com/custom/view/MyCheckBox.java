package com.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.CheckBox;

public class MyCheckBox extends CheckBox {
	
	public boolean clickFlag = false;
	public MyCheckBox(Context context, AttributeSet attrs) {
		
		super(context, attrs);
		clickFlag = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		 int action = event.getAction() & MotionEvent.ACTION_MASK;
		if(action == MotionEvent.ACTION_DOWN){
			clickFlag = true;
		}
		return super.onTouchEvent(event);
	}


	
	
}
