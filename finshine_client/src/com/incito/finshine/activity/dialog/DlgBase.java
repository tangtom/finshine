package com.incito.finshine.activity.dialog;

import com.incito.finshine.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public class DlgBase extends Dialog {
	private ImageView close;

	public DlgBase(Context context) {
		super(context);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		close =(ImageView)findViewById(R.id.imgClose);
		
		close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
}
