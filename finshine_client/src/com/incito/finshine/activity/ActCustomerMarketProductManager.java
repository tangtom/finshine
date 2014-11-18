package com.incito.finshine.activity;

import com.incito.finshine.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ActCustomerMarketProductManager extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_customer_market_order_manager);
		init();
	}
	private boolean init() {
		TextView title = (TextView) findViewById(R.id.textTitle);
		title.setText(R.string.title_bind);
		
		ImageView back = (ImageView) findViewById(R.id.imageBack);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		return true;
	}

}
