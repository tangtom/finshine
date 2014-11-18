package com.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class OverrideLinearLayout extends LinearLayout {

	public OverrideLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}    
	
	public OverrideLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
     * By default ViewGroup call setPressed on each child view, this take into account duplicateparentstate parameter
     */
    @Override
    protected void  dispatchSetPressed(boolean pressed) {
         for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.isDuplicateParentStateEnabled()){
                getChildAt(i).setPressed(pressed);
            }
        }
     }

}
