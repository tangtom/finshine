package com.incito.finshine.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.custom.view.PopMarketSign;
import com.incito.finshine.R;

/**
 * 
 * <dl>
 * <dt>FragmentBindFirst.java</dt>
 * <dd>Description:客户营销 绑定第二步 Fragment</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月16日 下午5:00:59</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class FragmentBindSecond11 extends FragmentDetail implements OnClickListener{

	private View view;
	private ActBind actBind;
	private Button btnSign;
	
	public static FragmentBindSecond11 newInstance(int id) {

		FragmentBindSecond11 f = new FragmentBindSecond11();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);

		return f;
	}
	
 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		view = inflater.inflate(R.layout.frag_customer_market_bind_second11,container, false);
		actBind = ActBind.instanceA;
		return view;
	}

	public void initView()
	{
//		btnSign = (Button)view.findViewById(R.id.imageSign);
		btnSign.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.imageSign:
			PopMarketSign pms = new PopMarketSign(this.getActivity(),v);
			pms.showPopWindow();
			break;
		}
	}
//		btnSign = (Button)view.findViewById(R.id.btnSign);
//		btnSign.setOnClickListener(this);
}


	
