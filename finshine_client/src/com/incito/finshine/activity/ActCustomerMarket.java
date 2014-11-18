package com.incito.finshine.activity;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.custom.view.FirstPagePop;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterCustomerMarket;
import com.incito.finshine.entity.ExpireCustomer;

/**
 * <dl>
 * <dt>ActCustomerMarket.java</dt>
 * <dd>Description:营销机会 </dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-5-21 下午4:09:00</dd>
 * </dl>
 * 
 * @author lihs
 */
public class ActCustomerMarket extends Activity implements OnClickListener {

	private static final String TAG = "ActCustomerMarket";

	public static final String IS_FROM_MARKET = "is_from_market";
	private Context context;
	private GridView marketGridV = null;
	private AdapterCustomerMarket adCusMarket = null;
	private List<ExpireCustomer> expireCustomer = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = context;
		setContentView(R.layout.act_customer_market);
		
		expireCustomer = (List<ExpireCustomer>)getIntent().getSerializableExtra(IS_FROM_MARKET);
		
		initTopTitle();
		
		initData();
		findViewById(R.id.imageNavigate).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initPullPop();
			}
		});
	}
	private void initPullPop(){
		 
		FirstPagePop firstPage = new FirstPagePop(this, findViewById(R.id.imageNavigate));
		firstPage.showPopWindow();
	}
	 
	private void initTopTitle() {

		TextView topTitle = (TextView) findViewById(R.id.textTitle);
		topTitle.setText("营销机会");

		ImageView btnBack = (ImageView) findViewById(R.id.imageBack);
		btnBack.setOnClickListener(this);

	}
	
	private void initData() {

		StringBuffer sb = new StringBuffer();
		sb.append("发现"+"<font color='#FF0000'>" + expireCustomer.size() + "</font>" +"个营销机会");
		((TextView)findViewById(R.id.tv_find_market_chance)).setText(Html.fromHtml(sb.toString()));
		 
		marketGridV = (GridView) findViewById(R.id.gridview_market_customer);
		 
		adCusMarket = new AdapterCustomerMarket(this);
		adCusMarket.setRefeshData(expireCustomer);
		marketGridV.setAdapter(adCusMarket);
		 
		marketGridV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
			}
		});

	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK && event.getRepeatCount() == 0)//按下的如果是BACK，同时没有重复
		{
			startActivity(new Intent(this,ActCustomerMarketing.class));
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		 switch (v.getId()) {
		case R.id.imageBack:
			startActivity(new Intent(this,ActCustomerMarketing.class));
			finish();
			break;

		default:
			break;
		}
	}
}
