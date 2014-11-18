/*
 * Copyright (c) 2012, Andy.fang Corporation, All Rights Reserved
 */
package com.incito.finshine.activity.dialog;

import com.android.core.entry.Static;
import com.incito.finshine.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

/**
 * @description 自定义对话框
 * @author Andy.fang
 * @createDate 2014年8月20日
 * @version 1.0
 */
public class CrmsDialog extends Dialog {
	Context mContext;

	public CrmsDialog(Context context) {
		this(context, R.style.CustomProgressDialog);
	}

	public CrmsDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
		init();
	}

	void init() {
		View mView = Static.INFLATER.inflate(R.layout.sysconfig, null);
		TextView telephone = (TextView) mView.findViewById(R.id.tv_telephone);
		Button ensure = (Button) mView.findViewById(R.id.btn_ensure);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		addContentView(mView, params);
		setCanceledOnTouchOutside(true);
		telephone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mContext.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:400-000-1234")));
			}
		});
		ensure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

	}

}
