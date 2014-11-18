package com.custom.view;

import com.incito.finshine.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ExtpandButton extends RelativeLayout {
	
	private Button btnMain;
	private Button btnExt;
	public ExtpandButton(Context context) {
		super(context);

	}

	public ExtpandButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.expand_button);
		a.recycle();
	}
}
