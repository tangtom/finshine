package com.incito.finshine.activity;

import android.support.v4.app.Fragment;


public class FragmentDetail extends Fragment {

	protected static final String FIELD_ID = "FragmentID";

	public int getFragmentId() {
		return getArguments().getInt(FIELD_ID);
	}
}