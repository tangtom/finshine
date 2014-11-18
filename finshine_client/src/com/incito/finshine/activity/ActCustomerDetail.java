package com.incito.finshine.activity;

import java.util.ArrayList;
import java.util.List;

import com.custom.view.CommSortView;
import com.custom.view.FirstPagePop;
import com.custom.view.CommSortView.RefreshSortListener;
import com.incito.finshine.R;
import com.incito.finshine.common.IntentDefine;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.manager.CoreManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * <dl>
 * <dt>ActCustomerDetail.java</dt>
 * <dd>Description:客户管理中Fragment的五个条的首界面</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月4日 下午3:25:07</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class ActCustomerDetail extends FragmentActivity {
	private final String TAG = ActCustomerDetail.class.getSimpleName();
	public static final int F_BASE_INFO = R.string.cm_base_info;
	public static final int F_ECONOMY_INFO = R.string.cm_economy_info;
	public static final int F_CONTENT_SUMMARY = R.string.cm_content_summary;
	public static final int F_INVEST_RECORD = R.string.cm_invest_record;
	public static final int F_INVEST_ANALYSE = R.string.cm_invest_analyse;

	public static int current_fragment = F_BASE_INFO;

	public static final String KEY_SHOW_FRAGMENT = "key_show_fragment";

	Button base;
	Button economy;
	Button invest;

	private Customer customer;
	private boolean isRecord = false;//判断光标所在位置

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_customer_detail);
		Bundle b = getIntent().getExtras();
		long customerId = b.getLong(IntentDefine.CUSTOMER_ID, -1);
		if (customerId == -1) {
			customer = (Customer) b.getSerializable(IntentDefine.CUSTOMER_OBJ);
		} else {
			Log.d(TAG, "customerId = " + customerId);
			try {
				customer = CoreManager.getInstance().getCustomerManager()
						.getCustomer(customerId);
				if(customer == null)
				{
					customer = new Customer();
					customer.setId(customerId);
				}
			} catch (Exception e) {
			}
		}
		if (getIntent().hasExtra(KEY_SHOW_FRAGMENT)) {
			//隐藏软键盘
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			isRecord = true;
			showDetails(F_INVEST_RECORD);
		} else {
			isRecord = false;
			showDetails(F_BASE_INFO);
		}
		init();
	}

	private boolean init() {
		TextView title = (TextView) findViewById(R.id.textTitle);
		title.setText(R.string.title_customer_management);
		ImageView back = (ImageView) findViewById(R.id.imageBack);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		List<Integer> btnList = new ArrayList<Integer>();
		btnList.add(R.string.cm_base_info);
		btnList.add(R.string.cm_economy_info);
		btnList.add(R.string.cm_content_summary);
		btnList.add(R.string.cm_invest_record);
		btnList.add(R.string.cm_invest_analyse);

		CommSortView sortView;
		if(isRecord)
		{
			sortView = new CommSortView(this, btnList,
					(LinearLayout) findViewById(R.id.buttonbars),
					R.string.cm_invest_record);
		}
		else
		{
			sortView = new CommSortView(this, btnList,
						(LinearLayout) findViewById(R.id.buttonbars),
						R.string.cm_base_info);
		}
		sortView.setRefreshSortListener(new RefreshSortListener() {

			@Override
			public void doDataSort(int id) {

				showDetails(id);
			}
		});
		findViewById(R.id.imageNavigate).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						initPullPop();
					}
				});
		return true;
	}
	
	private void initPullPop() {

		FirstPagePop firstPage = new FirstPagePop(this,
				findViewById(R.id.imageNavigate));
		firstPage.setPosition(1);
		firstPage.showPopWindow();
	}

	public void showDetails(int id) {
		Log.i(TAG, "showDetails = " + id);

		FragmentDetail details = (FragmentDetail) getSupportFragmentManager()
				.findFragmentById(R.id.details);
		if (details == null || !(details.getFragmentId() == id)) {

			// Make new fragment to show this selection.
			switch (id) {
			case F_BASE_INFO:
				details = FragmentBaseInfo.newInstance(id);
				break;
			case F_ECONOMY_INFO:
				details = FragmentEconomyInfo.newInstance(id);
				break;
			case F_CONTENT_SUMMARY:
				details = FragmentContactSummery.newInstance(id);
				break;
			case F_INVEST_RECORD:
				details = FragmentInvestRecord.newInstance(id);
				break;
			case F_INVEST_ANALYSE:
				details = FragmentInvestAnalisy.newInstance(id);
				break;

			default:
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
	// add by SGDY at 2014/8/13 19:37
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK && event.getRepeatCount() == 0)//按下的如果是BACK，同时没有重复
		{
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	public Customer getCustomer() {
		return customer;
	}
}
