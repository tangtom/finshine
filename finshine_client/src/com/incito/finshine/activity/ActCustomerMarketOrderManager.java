package com.incito.finshine.activity;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.custom.view.FirstPagePop;
import com.custom.view.PopDatePicker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterCustomerMarketOrderManager;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.HistoryOrderItem;
import com.incito.finshine.entity.MarKetCustomer;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.DateUtil;
import com.incito.wisdomsdk.event.EventBus;

/**
 * <dl>
 * <dt>ActCustomerMarketOrderManager.java</dt>
 * <dd>Description:历史订单 或者订单管理 只是查询接口的参数不一样就ok</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-17 下午11:10:09</dd>
 * </dl>
 * 
 * @author lihs
 */
public class ActCustomerMarketOrderManager extends Activity implements
		OnClickListener {
	
	private LinearLayout filterLayout;
	
	private MarKetCustomer marCs = null;
	AdapterCustomerMarketOrderManager adapterOrderManager = null;
	private List<HistoryOrderItem> historyList = null;
	public static final  String KEY_ISORDERMANAGER  = "key_isordermanager";// 表示是来自订单管理
	private boolean isFromOrderManager = false;
	
	private String salesId  = "";
	private long csutomerID =  0l;
	private EditText et_search_text,editOrderDate;
	private Spinner spinnerSaledResult,spinnerOrderType;
	private ImageView search_icon;
	private SPManager spManager;
	private Button buttonReset,btnSearch;
	private PopDatePicker popDatePicker;
	private boolean isFilter = false;
	/*订单起始日期
	 * 
	 */
	private String orderStartTime;

	/*订单结束日期
	 * 
	 */
	private String orderEndTime;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_customer_market_order_manager);
		EventBus.getDefault().registerSticky(this);
		Intent i =getIntent();
		if (i != null && i.hasExtra(Constant.ACTION_TO_ORDER_MANAGER)) {
			marCs = (MarKetCustomer)i.getSerializableExtra(Constant.ACTION_TO_ORDER_MANAGER);
			if (i.hasExtra(KEY_ISORDERMANAGER)) {
				isFromOrderManager =  true;
			}
			salesId = marCs.getSalesId()+"";
			csutomerID = marCs.getId();
		}else if (i != null && i.hasExtra(Constant.SALES_ID)) {
			
			salesId = i.getStringExtra(Constant.SALES_ID);
			csutomerID = i.getLongExtra(Constant.CUSTOMER_ID, -1);
		}
		init();
	}

	private boolean init() {
		
		editOrderDate = (EditText)findViewById(R.id.editOrderDate);
		editOrderDate.setOnClickListener(this);
		spinnerSaledResult = (Spinner)findViewById(R.id.spinnerSaledResult);
		ArrayAdapter adapter = new ArrayAdapter<String>(this,
				R.layout.common_spinner_item, this.getResources().getStringArray(R.array.saled_result));// android.R.layout.simple_spinner_item
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//
		spinnerSaledResult.setAdapter(adapter);
		spinnerSaledResult.setSelection(0, true);
		spinnerOrderType = (Spinner)findViewById(R.id.spinnerOrderType);
		ArrayAdapter adapter1 = new ArrayAdapter<String>(this,
				R.layout.common_spinner_item, this.getResources().getStringArray(R.array.order_type));// android.R.layout.simple_spinner_item
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//
		spinnerOrderType.setAdapter(adapter1);
		buttonReset = (Button)findViewById(R.id.buttonReset);
		btnSearch = (Button)findViewById(R.id.btnSearch); 
		buttonReset.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		if(TextUtils.isEmpty(salesId))
		{
			spManager = SPManager.getInstance();
			salesId = String.valueOf(spManager.getLongValue(SPManager.ID));
		}
		TextView title = (TextView) findViewById(R.id.textTitle);
		title.setText(R.string.customer_market_order_manager);

		ImageView back = (ImageView) findViewById(R.id.imageBack);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		filterLayout = (LinearLayout) findViewById(R.id.filter);
		
		search_icon = (ImageView)findViewById(R.id.search_icon);//搜素输入框中的按钮
		search_icon.setOnClickListener(this);
		et_search_text = (EditText)findViewById(R.id.et_search_text);//搜索输入框
		et_search_text.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int count) {
				// TODO Auto-generated method stub
				search_icon.setImageDrawable(count == 0 ? getResources().getDrawable(R.drawable.product_search_icon) : getResources().getDrawable(R.drawable.delete));
				if(adapterOrderManager != null)
				{
					adapterOrderManager.getFilter().filter(cs);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		findViewById(R.id.imageNavigate).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initPullPop();
			}
		});

		Button btnFilter = (Button) findViewById(R.id.btnFilter);
		btnFilter.setOnClickListener(this);

		initHttpUtils();
		
		ListView list = (ListView) findViewById(R.id.listHistoricOrder);
	    adapterOrderManager = new AdapterCustomerMarketOrderManager(this);
		list.setAdapter(adapterOrderManager);
		
		return true;
	}
	
	private void initPullPop() {
		FirstPagePop firstPage = new FirstPagePop(this,
				findViewById(R.id.imageNavigate));
		firstPage.showPopWindow();
	}
	
	public void onEvent(String value)
	{	
		if(!value.equals("全部"))
		{
			String[] str = value.split("----");
			orderStartTime = DateUtil.formatDate2String(DateUtil.formatString2Date(str[0], DateUtil.FORMAT_YYYY_MM_DD_ZH), DateUtil.FORMAT_YYYY_MM_DD);
			orderEndTime = DateUtil.formatDate2String(DateUtil.formatString2Date(str[1], DateUtil.FORMAT_YYYY_MM_DD_ZH), DateUtil.FORMAT_YYYY_MM_DD);
		}
		else
		{
			orderStartTime = null;
			orderEndTime = null;
		}
		editOrderDate.setText(value);
	}
	
	private void initHttpUtils(){
		
		JSONObject json = null;
		try {
			json = new JSONObject();
			json.put("salesId",salesId);
			json.put("customerId",csutomerID);
			if(isFilter)
			{
				if(orderStartTime != null)
				{
					json.put("orderStartTime",orderStartTime).put("orderEndTime", orderEndTime);
					isFilter = false;
				}
				json.put("orderStatusId", spinnerOrderType.getSelectedItemId());//支付结果
				json.put("tradingResultId", spinnerSaledResult.getSelectedItemId());//购买结果
			}
		} catch (Exception e) {
			 
		}
		Log.i("", json.toString());
		HttpUtils httpUtil = new HttpUtils(this, RequestDefine.MARKET_QUERY_HISTORY_ORDERS, RequestType.POST, json);
		httpUtil.setSuccessListener(new SuccessReslut() {
			
			@Override
			public void getResluts(JSONObject response) {
				 if (response == null) {
					return;
				 }
				 if (Request.RESLUT_OK.equals(response.optString("status"))) {
					try {
						JSONArray jsonArr = (JSONArray)response.getJSONArray("list");
						Gson gson = new Gson();
						historyList = gson.fromJson(jsonArr.toString(), new TypeToken<List<HistoryOrderItem>>(){}.getType());
//						Log.i("", historyList.size() + "------------------");
//						for(HistoryOrderItem o : historyList)
//						{
//							Log.i("",o.getSalesOrderNumber());
//						}
						adapterOrderManager.setItemList(historyList);
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		httpUtil.executeRequest();
	   }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK && event.getRepeatCount() == 0)//按下的如果是BACK，同时没有重复
		{
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnFilter:
			if (filterLayout.isShown())
				filterLayout.setVisibility(View.GONE);
			else
				filterLayout.setVisibility(View.VISIBLE);
			break;
		case R.id.search_icon:
			if(search_icon.getDrawable().getCurrent().getConstantState() == getResources().getDrawable(R.drawable.delete).getConstantState())
			{
				et_search_text.setText("");
			}
			break;
		case R.id.editOrderDate:
			if(popDatePicker == null)
			{
				popDatePicker = new PopDatePicker(this,v);
			}
			popDatePicker.showPopWindow();
			break;
		case R.id.buttonReset:
			spinnerOrderType.setSelection(0, true);
			spinnerSaledResult.setSelection(0, true);
			editOrderDate.setText("全部");
			orderStartTime = null;
			orderEndTime = null;
			break;
		case R.id.btnSearch:
			isFilter = true;
			filterLayout.setVisibility(View.GONE);
			initHttpUtils();
			break;
		default:
			break;
		}
	}
}
