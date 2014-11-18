package com.incito.finshine.activity;

import com.incito.finshine.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * <dl>
 * <dt>ActSignContract.java</dt>
 * <dd>Description:客户营销——合同签订第二步</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月10日 上午11:18:35</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class ActSignContractSecond extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_sign_contract_second);
		init();
	}

	private boolean init() {
		TextView title = (TextView) findViewById(R.id.textTitle);
		title.setText(R.string.title_sign_contract);
		
		ImageView back = (ImageView) findViewById(R.id.imageBack);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
//		Button cancel = (Button)findViewById(R.id.btnCancel);
//		cancel.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				finish();
//			}
//		});
//		Button confirm = (Button)findViewById(R.id.btnConfirm);
//		confirm.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent();
//				i.setClass(ActSignContract.this,ActOrderPayment.class);
//				startActivity(i);
//			}
//		});
		return true;
	}
}
