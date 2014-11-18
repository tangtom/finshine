package com.incito.finshine.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.incito.finshine.R;

/**
 * 
 * <dl>
 * <dt>FragmentBindFirst.java</dt>
 * <dd>Description:客户营销 绑定第一步 Fragment</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月16日 下午5:00:59</dd>
 * </dl>
 * 
 * @author LiNing
 */

public class FragmentBindFirst11 extends FragmentDetail{
	private View view;

	public static FragmentBindFirst11 newInstance(int id) {

		FragmentBindFirst11 f = new FragmentBindFirst11();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		view = inflater.inflate(R.layout.frag_customer_market_bind_first11,
				container, false);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}	
}
