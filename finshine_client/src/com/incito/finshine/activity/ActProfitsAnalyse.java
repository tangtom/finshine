package com.incito.finshine.activity;

import com.incito.finshine.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ActProfitsAnalyse extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_profits_analyse);
		init();
	}
	private boolean init()
	{
		ImageView back = (ImageView)findViewById(R.id.imageAnalyse);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		return true;
	}
}
