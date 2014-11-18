package com.incito.finshine.test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.incito.finshine.R;
import com.incito.finshine.activity.FragmentDetail;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.manager.CoreManager;

public class ActTestCenter extends FragmentActivity {
	private final String TAG = ActTestCenter.class.getSimpleName();
	public static final int F_HOME = 1;
	public static final int F_CUSTOMER = 2;
	public static final int F_PRODUCT = 3;
	public static final int F_MARKETING = 4;
	public static int current_fragment = F_HOME;

	private Customer customer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_center);

		init();

		showDetails(F_MARKETING);

	}

	private boolean init() {
		CoreManager.getInstance().init();
//		Button home = (Button) findViewById(R.id.btnHome);
//		home.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				showDetails(F_HOME);
//			}
//		});
//		Button customer = (Button) findViewById(R.id.btnCustomer);
//		customer.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				showDetails(F_CUSTOMER);
//			}
//		});
//		Button product = (Button) findViewById(R.id.btnProduct);
//		product.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				showDetails(F_PRODUCT);
//			}
//		});
//		Button marketing = (Button) findViewById(R.id.btnMarketing);
//		marketing.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				showDetails(F_MARKETING);
//			}
//		});
		return true;
	}

	public void showDetails(int id) {
		Log.i(TAG, "showDetails = " + id);

		FragmentDetail details = (FragmentDetail) getSupportFragmentManager()
				.findFragmentById(R.id.details);
		if (details == null || !(details.getFragmentId() == id)) {

			// Make new fragment to show this selection.
			switch (id) {
			case F_HOME:
				details = FragmentHome.newInstance(id);
				break;
			case F_CUSTOMER:
				details = FragmentCustomer.newInstance(id);
				break;
			case F_PRODUCT:
				details = FragmentProduct.newInstance(id);
				break;
			case F_MARKETING:
				details = FragmentMarketing.newInstance(id);
				break;

			default:
				details = FragmentCustomer.newInstance(id);
				break;
			}

			// Execute a transaction, replacing any existing fragment
			// with this one inside the frame.
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.replace(R.id.details, details);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}

	}

	public Customer getCustomer() {
		return customer;
	}
}
