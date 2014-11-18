package com.incito.finshine.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.incito.finshine.R;

/**
 * 
 * <dl>
 * <dt>ActOrderPayment.java</dt>
<<<<<<< HEAD
 * <dd>Description:客户营销——订单支付  第一步第二步Fragment切换</dd>
=======
 * <dd>Description:客户营销——订单支付  第一步第二步Fragment切换   未用</dd>
>>>>>>> 20a979ca9a4c91ee5ccc52acf613419759b6646f
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月10日 上午11:17:28</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class ActOrderPayment extends FragmentDetail {

	private final String TAG = ActOrderPayment.class.getSimpleName();

	public static final int F_FIRST = 0;
	public static final int F_SECOND = 1;
	
	View view;

	public static ActOrderPayment newInstance(int id) {

		ActOrderPayment f = new ActOrderPayment();
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
		view = inflater.inflate(R.layout.act_customer_market_order_paid, container, false);
		
		init();
		showDetails(F_FIRST);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	private boolean init() {
		
//		TextView title = (TextView) findViewById(R.id.textTitle);
//		title.setText(R.string.title_order_payment);
//
//		ImageView back = (ImageView) findViewById(R.id.imageBack);
//		back.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				finish();
//			}
//		});

		return true;
	}

	public void showDetails(int id) {
		Log.i(TAG, "showDetails = " + id);

		FragmentDetail details = (FragmentDetail) getFragmentManager()
				.findFragmentById(R.id.mainFragmentLayout);
		if (details == null || !(details.getFragmentId() == id)) {

			// Make new fragment to show this selection.
			switch (id) {
			case F_FIRST:
				//details = FragmentCustomerMarketOrderPaidFirst.newInstance(id);
				break;
			case F_SECOND:
				details = FragmentCustomerMarketOrderPaidSecond.newInstance(id);
				break;

			default:
				break;
			}

			// Execute a transaction, replacing any existing fragment
			// with this one inside the frame.
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.mainFragmentLayout, details);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}
	}
}
