/*
 * Copyright (c) 2012, Andy.fang Corporation, All Rights Reserved
 */
package com.base;

import com.android.core.base.FActivity;

import android.os.Bundle;
import android.view.KeyEvent;

/**
 * @description
 * @author Andy.fang
 * @createDate 2014年8月6日
 * @version 1.0
 */
public class BaseActivity extends FActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
}
