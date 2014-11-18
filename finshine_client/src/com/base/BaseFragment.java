/*
 * Copyright (c) 2012, Andy.fang Corporation, All Rights Reserved
 */
package com.base;

import com.android.core.base.BFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

/**
 * @description 
 * @author   Andy.fang
 * @createDate 2014年8月18日
 * @version  1.0
 */
public class BaseFragment extends BFragment {
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// getActivity().getActionBar().setDisplayUseLogoEnabled(false);
		// getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
		// getActivity().getActionBar().setDisplayShowTitleEnabled(false);
		// getActivity().getActionBar().setHomeButtonEnabled(false);
		// getActivity().getActionBar().setDisplayShowHomeEnabled(false);
		// getActivity().getActionBar().setDisplayShowTitleEnabled(false);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}
}
