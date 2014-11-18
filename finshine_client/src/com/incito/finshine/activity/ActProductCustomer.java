package com.incito.finshine.activity;

import com.custom.view.CommSortView;
import com.custom.view.CommSortView.RefreshSortListener;
import com.custom.view.CommonWaitDialog;
import com.custom.view.DlgCitySelected;
import com.custom.view.DlgCommFilter;
import com.custom.view.DlgCommFilter.RefreshFilterListener;
import com.custom.view.FirstPagePop;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterAddTargetCustomer;
import com.incito.finshine.activity.adapter.AdapterAddTargetCustomer.ViewHolder;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.FilterUtil;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


 
/**
 * <dl>
 * <dt>ActProductCustomer.java</dt>
 * <dd>Description:新增目标客户</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-10 下午1:33:14</dd>
 * </dl>
 * 
 * @author lihs
 */
public class ActProductCustomer extends Activity implements OnClickListener{
	
	
	private static final String TAG ="ActProductCustomer";
	
	private static final int INVEST_FUND_HOBBY = 1;
	
	private EditText etSearchCustomer = null;
	
	private CheckBox cbSelectAll;  
	private TextView tvSelectedCount;  
	
	private ArrayList<Customer> customerLists ;
	private Map<Integer,String[]> map;//add by SGDY for bug#3783
	private ListView customerList;
	private AdapterAddTargetCustomer targetCustomerAdapter;
	
	private CommSortView addCsSort = null;
	private String targetCustomerIds;//接受已添加的目标客户ID
	private Spinner spinnerCustom;
	private Spinner spinnerEffectiveness;
	private Spinner spinnerSaleChance;
	private Spinner spinnerPublisher;
	private Button cityBtn,btnDeadline,btnProfit,btnDividend,btnInvestment1;
	private LinearLayout layFilter;
	private CommonWaitDialog waitDlg = null;
	private ImageView search_icon;
	
	private JsonHttpResponseHandler handlerGetCustomer = new JsonHttpResponseHandler() {
		 
		 
		
		public void onSuccess(JSONObject response) {
			closeDialog();
			Log.d(TAG,"success a= " + response.toString());
			Gson gson = new Gson(); 
			 try {
				JSONArray jo = response.getJSONArray("list");
				if(customerLists != null && customerLists.size() > 0)
				{
					customerLists.clear();
				}
				customerLists = gson.fromJson(jo.toString(), new TypeToken<List<Customer>>() {}.getType());
				/*------------------删除目标客户中已存在的客户--------------------*/
				List<Customer> cs = new ArrayList<Customer>();
				if(targetCustomerIds != null){
					String[] ids = targetCustomerIds.split(",");
					for(String id : ids){
						for(Customer c : customerLists){
							if(c != null){
								if(Long.parseLong(id) == c.getId()){
									cs.add(c);
								}
							}
						}
					}
				}
				customerLists.removeAll(cs);
				Log.i(TAG, customerLists.size() + "------------");
//				if (customerLists != null ) {
//					JSONObject params = new JSONObject();
//					for (Customer item : customerLists) {
//						try {
//							params.put("customerIds", item.getId());
//							params.put("expirationDays", 14);
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//						//批量获取客户的投资次数
//						Request request = new Request( RequestDefine.RQ_CUSTOMER_POST_ECONOMY_LIST, RequestType.POST,params, handlerGetCustomerEconumy);
//						CoreManager.getInstance().postRequest(request);
//					}
//				}
				if (targetCustomerAdapter != null) {
					 targetCustomerAdapter.setCustomerList(customerLists);
					 targetCustomerAdapter.setSortWays(AdapterAddTargetCustomer.ASSETS_TOTLE);
					 if (customerLists != null && customerLists.size() > 0) {
						 customerList.setSelection(0);
					 }
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} 
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			CommonUtil.showToast("获取客户信息失败"+content, ActProductCustomer.this);
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_product_target_customer);
		Intent i = this.getIntent();
		if(i != null && i.hasExtra("targetCustomerIds")){
			targetCustomerIds = i.getStringExtra("targetCustomerIds");
		}
		initTopTitle();
		
		initUI();
		
		initData();
		
		CommonUtil.hideSoftInputFromWindow(this);
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(R.string.total_asset);
		ids.add(R.string.invest_total_money);
		ids.add(R.string.invest_times);
		addCsSort = new CommSortView(this, ids, (LinearLayout)findViewById(R.id.lt_sort), R.string.total_asset);
		addCsSort.setRefreshSortListener(new RefreshSortListener() {
				
				@Override
				public void doDataSort(int id) {
					 if (targetCustomerAdapter != null) {
						 targetCustomerAdapter.setSortWays(id);
						 if (customerLists != null && customerLists.size() > 0) {
							 customerList.setSelection(0);
						 }
					}
				}
		}); 
		 
	}
 
	private void initTopTitle() {

		TextView topTitle = (TextView) findViewById(R.id.textTitle);
		topTitle.setText(getResources().getString(R.string.title_add_target_customer));

		ImageView btnBack = (ImageView) findViewById(R.id.imageBack);
		btnBack.setOnClickListener(this);

	}

	private void initUI(){
		
		spinnerCustom = (Spinner) findViewById(R.id.spinnerCustom);
		spinnerEffectiveness = (Spinner) findViewById(R.id.spinnerEffectiveness);
		spinnerSaleChance = (Spinner) findViewById(R.id.spinnerSaleChance);
		spinnerPublisher = (Spinner) findViewById(R.id.spinnerPublisher);
		initSpinner(spinnerCustom,
				getResources().getStringArray(R.array.custom_status), 0);
		initSpinner(spinnerEffectiveness,
				getResources().getStringArray(R.array.effectiveness_status), 1);
		initSpinner(spinnerSaleChance,
				getResources().getStringArray(R.array.sale_chance), 0);
		initSpinner(spinnerPublisher,
				getResources().getStringArray(R.array.publisher_status), 0);
		
		etSearchCustomer = (EditText)findViewById(R.id.et_search_text);
		etSearchCustomer.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length() > 0)
				{
					search_icon.setImageDrawable(ActProductCustomer.this.getResources().getDrawable(R.drawable.delete));
				}
				else
				{
					search_icon.setImageDrawable(getResources().getDrawable(R.drawable.product_search_icon));
				}
				targetCustomerAdapter.getFilter().filter(s.toString());
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		 
		layFilter = (LinearLayout)findViewById(R.id.filter);
		
		search_icon = (ImageView)findViewById(R.id.search_icon);
		search_icon.setOnClickListener(this);
		findViewById(R.id.btnFilter).setOnClickListener(this);
		findViewById(R.id.buttonReset).setOnClickListener(this);
		findViewById(R.id.btnSearch).setOnClickListener(this);
		
		cbSelectAll = (CheckBox)findViewById(R.id.cb_checkall);
		cbSelectAll.setOnClickListener(this);
		tvSelectedCount = (TextView)findViewById(R.id.tv_selected_customer);
		 
		findViewById(R.id.btn_sure_add_customer).setOnClickListener(this);
		customerLists = new ArrayList<Customer>();
		customerList = (ListView)findViewById(R.id.listCustomer);
		targetCustomerAdapter = new AdapterAddTargetCustomer(ActProductCustomer.this,cbSelectAll,tvSelectedCount);
		targetCustomerAdapter.setCustomerList(customerLists);
		customerList.setAdapter(targetCustomerAdapter);
		customerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				 
				if (position < 0) {
					return;
				}
				ViewHolder holder = (ViewHolder)view.getTag();
			    holder.cbSelected.toggle();
			    
			    targetCustomerAdapter.select(position, holder.cbSelected.isChecked());
			    targetCustomerAdapter.notifyDataSetChanged();
			}
		});
		
		// 弹出投资偏好
		btnInvestment1 = (Button) findViewById(R.id.btnInvestment1);
		btnInvestment1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DlgCitySelected cityDialog = new DlgCitySelected(
						ActProductCustomer.this, btnInvestment1.getText()
								.toString(),
						DlgCitySelected.CATEGORY_SEL_INVEST_HOBBY, "投资偏好");
				cityDialog
						.setRefreshSortListener(new DlgCitySelected.SelctedCityListener() {

							@Override
							public void selectedCity(String city) {
								btnInvestment1.setText(city);
							}
						});
				cityDialog.showDialog();

			}
		});
		
		// 弹出省选城市
		cityBtn = (Button) findViewById(R.id.btnCity);
		cityBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DlgCitySelected cityDialog = new DlgCitySelected(
						ActProductCustomer.this, cityBtn.getText()
								.toString(), DlgCitySelected.CATEGORY_SEL_CITY,
						"城市");
				cityDialog
						.setRefreshSortListener(new DlgCitySelected.SelctedCityListener() {

							@Override
							public void selectedCity(String city) {
								cityBtn.setText(city);
							}
						});

				cityDialog.showDialog();
			}
		});
		// 重置筛选条件
		Button reset = (Button) findViewById(R.id.buttonReset);
		reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				spinnerCustom.setSelection(0, true);
				spinnerPublisher.setSelection(0, true);
				spinnerSaleChance.setSelection(0, true);
				spinnerEffectiveness.setSelection(1, true);

				cityBtn.setText("全部");
				btnDeadline.setText("全部");
				btnProfit.setText("全部");
				btnDividend.setText("全部");
				btnInvestment1.setText("全部");
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
		
		btnDeadline = (Button) findViewById(R.id.btnDeadline);
		btnProfit = (Button) findViewById(R.id.btnProfit);
		btnDividend = (Button) findViewById(R.id.btnDividend);
		// 筛选弹出的列表加EditText
				initDlgCommonFilter(btnDeadline, R.array.deadline_status,
						R.string.deadlinePerference, true, 1);
				initDlgCommonFilter(btnProfit, R.array.profit_status,
						R.string.profitPerference, true, 2);
				initDlgCommonFilter(btnDividend, R.array.dividend_status,
						R.string.dividendPerference, true, 1);
	}
	
	private void initDlgCommonFilter(final Button btn, final int listId,
			final int title, final boolean isSingle, final int model) {

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				DlgCommFilter fDeadLind = new DlgCommFilter(
						ActProductCustomer.this, listId, title, btn
								.getText().toString(), true, model);
				fDeadLind.setInputValue(map);
				fDeadLind.setListener(new RefreshFilterListener() {

					@Override
					public void doRefresh(String reslut, int position,boolean b,int title) {
						((Button) v).setText(reslut);
						if(b)
						{
							String[] str = reslut.split("-");
							String []s = new String[2];
							switch(title)
							{
							case R.string.profitPerference://收益偏好
								s[0] = str[0].split("%")[0];
								s[1] = str[1].split("%")[0];
								break;
							case R.string.dividendPerference://分红偏好
							case R.string.deadlinePerference://期限偏好
								s[0] = str[0];
								s[1] = str[1].split("个月")[0];
								break;
							}
							map.put(title, s);
						}
						else
						{
							Iterator<Map.Entry<Integer, String[]>> it = map.entrySet().iterator(); 
							while(it.hasNext())
							{
								Map.Entry<Integer, String[]> entry=it.next();
								if(entry.getKey() == title)
								{
									it.remove();
								}
							}
						}
					}
				});
				fDeadLind.showDialog();
			}
		});

	}
	private void initPullPop() {

		FirstPagePop firstPage = new FirstPagePop(this,
				findViewById(R.id.imageNavigate));
		firstPage.showPopWindow();
	}
	private void initData(){
		getCustomerBaseInfoList();
		map = new HashMap<Integer,String[]>();
//		JSONObject json = new JSONObject();
//		try {
//			json.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		Request request = new Request(RequestDefine.RQ_CUSTOMER_GET_LIST, RequestType.POST, json, handlerGetCustomer);
//		CoreManager.getInstance().postRequest(request);
		
//		List<Customer> list = new ArrayList<Customer>();
//		Customer custom = null;
//		for (int i = 0; i < 10; i++) {
//			custom = new Customer();
//			custom.setAge(i);
//			custom.setName("sss"+i);
//			custom.setInvestNumber(i);
//			custom.setCurrentInvestValue(Long.parseLong(12+i+""));
//			custom.setTotalAsset((Long.parseLong(12-i+"")));
//			list.add(custom);
//		}
//		targetCustomerAdapter.setCustomerList(list);
	}

 
 
	@Override
	public void onClick(View v) {
        switch (v.getId()) {
		case R.id.imageBack:
			finish();
			break;
	   case R.id.buttonReset:
			
			break;
		case R.id.btnSearch:
			layFilter.setVisibility(View.GONE);
			getCustomerBaseInfoList();
			break;
		case R.id.btnFilter:
			if (View.VISIBLE == layFilter.getVisibility()) {
				layFilter.setVisibility(View.GONE);
			}else{
				layFilter.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.btn_sure_add_customer:
			List<Customer> list = targetCustomerAdapter.getSeletCustomerData();
			if (list == null || list.size() <=0) {
				CommonUtil.showToast("请您选中要添加的目标客户 ", this);
				return;
			} 
		    if (instances != null) {
				instances.addTargetCustomer(list);
			}
			finish();
			break;
		case R.id.cb_checkall:
			if (customerLists == null) {
				return;
			}
			targetCustomerAdapter.isSeletAllCustomer(cbSelectAll.isChecked());
			break;
		case R.id.search_icon:
			if(search_icon.getDrawable().getCurrent().getConstantState() == getResources().getDrawable(R.drawable.delete).getConstantState())
			{
				etSearchCustomer.setText("");
			}
			//targetCustomerAdapter.filterByCustomerName(etSearchCustomer.getText().toString());
			break;
		default:
			break;
		}		
	} 
	
	private void initSpinner(Spinner sp, String[] dataList, int selectIndex) {

		ArrayAdapter adapter = new ArrayAdapter<String>(this,
				R.layout.common_spinner_item, dataList);// android.R.layout.simple_spinner_item
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//
		sp.setAdapter(adapter);
		sp.setSelection(selectIndex, true);
	}
	
	private boolean getCustomerBaseInfoList() {

		String[] effective = { "", "A", "D" };
		String[] investValue = getResources().getStringArray(
				R.array.investment2_status);
		JSONObject filterJson = new JSONObject();
		try {
			filterJson.put("salesId",
					SPManager.getInstance().getLongValue(SPManager.ID));
			filterJson.put("customerType",
					spinnerCustom.getSelectedItemPosition());
			filterJson.put("status",
					effective[spinnerEffectiveness.getSelectedItemPosition()]);
			filterJson.put("salesOpp",
					spinnerSaleChance.getSelectedItemPosition());
			filterJson.put("city",
					cityBtn.getText().toString().equals("全部") ? "" : cityBtn
							.getText().toString());
			filterJson.put(
					"prodPreference",
					spinnerPublisher.getSelectedItemPosition() == 0 ? ""
							: getResources().getStringArray(
									R.array.publisher_status)[spinnerPublisher
									.getSelectedItemPosition()]);

			filterJson.put("periodPreferencesLow",
					FilterUtil.getSelectedBtnValue(btnDeadline.getText()
							.toString(), true));
			filterJson.put("periodPreferencesHigh", FilterUtil
					.getSelectedBtnValue(btnDeadline.getText().toString(),
							false));

			filterJson.put("revenuePreferencesLow", FilterUtil
					.getPercentBtnValue(btnProfit.getText().toString(), true));
			filterJson.put("revenuePreferencesHigh", FilterUtil
					.getPercentBtnValue(btnProfit.getText().toString(), false));

			filterJson.put("bonusPreferencesLow",
					FilterUtil.getSelectedBtnValue(btnDividend.getText()
							.toString(), true));
			filterJson.put("bonusPreferencesHigh", FilterUtil
					.getSelectedBtnValue(btnDividend.getText().toString(),
							false));

			filterJson.put("prodFirstType", FilterUtil.getInvestmentValue(
					btnInvestment1.getText().toString(), true, investValue));
			filterJson.put("prodSecondtype", FilterUtil.getInvestmentValue(
					btnInvestment1.getText().toString(), false, investValue));

			filterJson.put("expirationDays", 14);
			filterJson.put("customerIds", targetCustomerIds);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.i("筛选的客户管理的参数是", filterJson.toString());
		waitDlg = new CommonWaitDialog(this, "", R.string.load_data);

		Request request = new Request(RequestDefine.RQ_CUSTOMER_GET_LIST,
				RequestType.POST, filterJson, handlerGetCustomer);
		// Request request = new Request(RequestDefine.RQ_CUSTOMER_GET_LIST,
		// RequestType.GET, null, handlerGetCustomer);
		CoreManager.getInstance().postRequest(request);
		return true;
	}

	private void resetQuryConditions(){
		
		etSearchCustomer.setText(null);
		targetCustomerAdapter.setSortWays(AdapterAddTargetCustomer.ASSETS_TOTLE);
	 
		if (addCsSort != null) {
			addCsSort.selePointPosition(R.string.total_asset);
		}
	}
	
	private int count = 0;
	
	private JsonHttpResponseHandler handlerGetCustomerEconumy = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.i(TAG, "success o= " + response.toString());
            count ++;
			if (Request.RESLUT_OK.equals(response.optString("status"))) {
				JSONArray arr = response.optJSONArray("list");
				for(int i = 0; i < arr.length(); i++) {//遍历JSONArray  
	                  
	                try {
						JSONObject oj = arr.getJSONObject(i);
						if (oj.has("id")){
							for (Customer c : customerLists){
								if (oj.optLong("id") == c.getId()){
									c.setInvestNumber(oj.optInt("purchasedQuantity"));
									c.setInvestTotalAsserts(oj.optInt("purchasedTotalAmount"));
									c.setProdDividendQty(oj.optInt("prodDividendQty"));
									c.setSimilarProductQuantity(oj.optLong("similarProductQuantity"));
									 
								}
							}
						}
							
					} catch (JSONException e) {
						e.printStackTrace();
					}  
	            }  
			}
			if (count == customerLists.size()) {
				 if (targetCustomerAdapter != null) {
					 targetCustomerAdapter.setCustomerList(customerLists);
					 targetCustomerAdapter.setSortWays(AdapterAddTargetCustomer.ASSETS_TOTLE);
					 if (customerLists != null && customerLists.size() > 0) {
						 customerList.setSelection(0);
					 }
				}
			}
		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			count ++;
			if (count == customerLists.size()) {
				 if (targetCustomerAdapter != null) {
					 targetCustomerAdapter.setCustomerList(customerLists);
					 targetCustomerAdapter.setSortWays(AdapterAddTargetCustomer.ASSETS_TOTLE);
				}
			}
		}
	};
	
	
	// 回调数据
	public static AddTargetCustomerListener instances;
	
	public static void setAddTargetCustomerListener(AddTargetCustomerListener instance){
		ActProductCustomer.instances = instance;
	}
	
	public interface AddTargetCustomerListener{
		
		public void addTargetCustomer(List<Customer> list);
	}
 
	private void closeDialog() {
		if (waitDlg != null) {
			waitDlg.clearAnimation();
		}
	}
}
