package com.incito.finshine.activity;

import com.custom.view.CommSortView;
import com.custom.view.CommSortView.RefreshSortListener;
import com.custom.view.DeviceLinearLayout;
import com.custom.view.FirstPagePop;
import com.custom.view.PopAddInvester;
import com.custom.view.PopCsInvestDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.activity.ActProductCustomer.AddTargetCustomerListener;
import com.incito.finshine.activity.adapter.AdapterFindCustomer;
import com.incito.finshine.common.IntentDefine;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.Product;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * <dl>
 * <dt>ActFindTargetCustomer.java</dt>
 * <dd>Description:发现目标客户</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-11 上午12:08:01</dd>
 * </dl>
 * 
 * @author lihs
 */
public class ActFindTargetCustomer extends Activity implements OnClickListener {
	
	private final String TAG = ActFindTargetCustomer.class.getSimpleName();
	
	private static final int FIND_TARGET_CS = 1;
	private static final int DELETE_TARGET_CS = 2;
	private static final int ADD_TARGET_CS = 3;
	public static final String PROD_ACTION = "prod";
	private int currentType = FIND_TARGET_CS;
	private List<Customer> listCus;
	private ListView listCustomer;
	private AdapterFindCustomer findCustomerAdap;
	private CheckBox selectAllCustomer;
	private TextView tvSeleCount;
	private List<Customer> findCustomer = null;
	private Product pro;
	private String targetCustomerIds;//存放目标客户ID
	private HttpUtils httpUtil = null;
	private EditText etCustomerSearch;
	private ImageView search_icon;
//	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		this.context = context;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_find_target_customers);
		
		if (getIntent() != null && getIntent().hasExtra(PROD_ACTION)) {
			pro = (Product)getIntent().getSerializableExtra(PROD_ACTION);
		}
		
		TextView title = (TextView) findViewById(R.id.textTitle);
		title.setText(R.string.title_find_target_customer);
		findViewById(R.id.imageBack).setOnClickListener(this);
		if (getIntent() != null && getIntent().getBooleanExtra("market", false)) {
			title.setText(R.string.market_customer);
		}
		
		customerSort();
		
//		initQueryData();
		
		currentType =FIND_TARGET_CS;
		operatonDataByType(currentType, null);
		
		init();
		findViewById(R.id.imageNavigate).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						initPullPop();
					}
				});
	}
	private void initPullPop() {

		FirstPagePop firstPage = new FirstPagePop(this,
				findViewById(R.id.imageNavigate));
		firstPage.showPopWindow();
	}

	
	private void init() {

	
		// 搜索客户
		etCustomerSearch = (EditText) findViewById(R.id.et_search_text);
		etCustomerSearch.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (findCustomerAdap != null) {
					findCustomerAdap.getFilter().filter(s.toString());
				}
				if(count > 0)
				{
					search_icon.setImageDrawable(ActFindTargetCustomer.this.getResources().getDrawable(R.drawable.delete));
				}
				else
				{
					search_icon.setImageDrawable(ActFindTargetCustomer.this.getResources().getDrawable(R.drawable.product_search_icon));
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		});
		
		search_icon = (ImageView)findViewById(R.id.search_icon);
		search_icon.setOnClickListener(this);
		
		findViewById(R.id.btn_add_customer).setOnClickListener(this);
		findViewById(R.id.btn_del_customer).setOnClickListener(this);
		findViewById(R.id.btn_send_emali).setOnClickListener(this);
		findViewById(R.id.btn_send_sms).setOnClickListener(this);
//		findViewById(R.id.img_add_invest).setOnClickListener(this);
//		findViewById(R.id.img_data_analise).setOnClickListener(this);
		findViewById(R.id.img_add_invest).setVisibility(View.GONE);
		findViewById(R.id.img_data_analise).setVisibility(View.GONE);
		 
		tvSeleCount = (TextView)findViewById(R.id.tv_selected_customer);
		selectAllCustomer = (CheckBox)findViewById(R.id.checkSelectAll);
		selectAllCustomer.setOnClickListener(this);
		
		listCustomer = (ListView) findViewById(R.id.listCustomerDetail);
		findCustomerAdap = new AdapterFindCustomer(this, selectAllCustomer,tvSeleCount,pro);
		ArrayList<Customer> infos = new ArrayList<Customer>();
		findCustomerAdap.setCustomerList(infos);
		listCustomer.setAdapter(findCustomerAdap);
		listCustomer.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {

				DeviceLinearLayout deviceImage = (DeviceLinearLayout) view.findViewById(R.id.lt_signal_select);
				CheckBox signleCb = (CheckBox) view.findViewById(R.id.cb_selete_customer);
				if (deviceImage.clickFlag) {
					deviceImage.clickFlag = false;
					signleCb.toggle();

					findCustomerAdap.select(pos, signleCb.isChecked());
					findCustomerAdap.notifyDataSetChanged();
				} else {
					
					if (getIntent() != null && getIntent().getBooleanExtra("market", false)) {
						return;
					}
					Intent i = new Intent();
					i.setClass(ActFindTargetCustomer.this, ActCustomerDetail.class);
					i.putExtra(IntentDefine.CUSTOMER_ID, findCustomerAdap.getItemId(pos));
//					i.putExtra(IntentDefine.CUSTOMER_OBJ, (Customer)findCustomerAdap.getItem(pos));
					startActivity(i);
				}
			}
		});
		
		ActProductCustomer.setAddTargetCustomerListener(new AddTargetCustomerListener() {

					@Override
					public void addTargetCustomer(List<Customer> list) {

						// 对已经添加的客户 就不在添加了
						if (findCustomer == null) {
							findCustomer = new ArrayList<Customer>();
						}
						List<Customer> tempList = findCustomer;
						List<Customer> addDataList = new ArrayList<Customer>();
						// 排重
						Customer selCustomer = null;
						boolean isAdded = false;
						for (int i = 0; i < list.size(); i++) {
							isAdded = false;
							selCustomer = list.get(i);
							for (int j = 0; j < tempList.size(); j++) {
								if (selCustomer != null && tempList.get(j) != null && ( selCustomer.getId()  == tempList.get(j).getId())) {
									isAdded = true;
									break;
								}
							}
							if (!isAdded) {
								addDataList.add(selCustomer);
							}
						 }
						 // 添加目标数据
						if (addDataList.size() > 0) {
//							addTargetCustomers(addDataList);
							currentType = ADD_TARGET_CS;
							operatonDataByType(currentType, addDataList);
						} else {
							CommonUtil.showToast("选择的数据已是您的目标客户", ActFindTargetCustomer.this);
						}
					}
				});
    }

	 
	private void customerSort() {

		List<Integer> list = new ArrayList<Integer>();
		list.add(R.string.total_asset);
		list.add(R.string.invest_total_money);
		list.add(R.string.invest_times);
		CommSortView sortView = new CommSortView(this, list,(LinearLayout) findViewById(R.id.sortButton),
				R.string.total_asset);
		sortView.setRefreshSortListener(new RefreshSortListener() {

			@Override
			public void doDataSort(int id) {
				if (findCustomerAdap != null) {
					findCustomerAdap.setSortWays(id);
				}
			}
		});
	}

	private static final int GET_INVEST_INFO = 5;
	
	private void operatonDataByType(final int type,List<Customer> csList ){
		
		if (pro == null || TextUtils.isEmpty(pro.getId() + "")){
			return;
		}
		int requestId = 0;
		RequestType reqType = RequestType.POST; 
		try {
			JSONObject params = new JSONObject();
			switch (type) {
			case FIND_TARGET_CS:
				requestId = RequestDefine.RQ_QUERY_TARGET_CUSTOMER;
				reqType = RequestType.GET;
				params = null;
				break;
			case DELETE_TARGET_CS:
				if (csList == null || csList.size() == 0) {
					CommonUtil.showToast("请至少选择一个要删除的客户", this);
					return;
				}
				List<Customer> notSeleted = new ArrayList<Customer>();
				Log.i(TAG, findCustomer.size() + "--------- is findCustomer size");
				for (Customer customer : findCustomer) {
					Log.i(TAG, customer.getName() + "--------- is findCustomer name");
					if (!csList.contains(customer)) {
						notSeleted.add(customer);
					}
				}
				Log.i(TAG, notSeleted.size() + "-----------is notselected size");
				// 传递未选中的客户id
				StringBuffer customerIds = new StringBuffer();
				for (Customer customer : notSeleted) {
					customerIds.append(customer.getId() + ",");
				}
				if (customerIds.length() > 0) {
					params.put("customIds",customerIds.substring(0, customerIds.length() - 1));
				} else {
					params.put("customIds", "");
				}
				requestId = RequestDefine.RQ_DELETE_TARGET_CUSTOMER;
				reqType = RequestType.POST;
				break;
			case ADD_TARGET_CS:
				if (csList.size() == 0) {
					return;
				}
				StringBuffer customerId = new StringBuffer();
				for (Customer customer : csList) {
					if (customer != null) {
						customerId.append(customer.getId() + ",");
					}
				}
				params.put("customIds", customerId.substring(0, customerId.length() - 1));
				requestId = RequestDefine.RQ_ADD_TARGET_CUSTOMER;
				reqType = RequestType.POST;
				break;
			default:
				break;
			}
			httpUtil = new HttpUtils(this, requestId, reqType, params);
			httpUtil.setCustomerId(pro.getId());
			httpUtil.setSuccessListener(new SuccessReslut() {

				@Override
				public void getResluts(JSONObject response) {
					
					Log.d(TAG, "success o= " + response.toString());
					switch (currentType) {
					case FIND_TARGET_CS:
						// {"msg":"查询成功","status":"0","item":{"id":3,"lastMod":1400586480000,"created":1400586480000,"status":"A","prodId":63,"salesId":1,"customIds":"1,2,3,4","version":1}}
						Log.d(TAG, "success a= " + response.toString());
						if (Request.RESLUT_OK.equals(response.opt("status"))) {
							try {
								List<Customer> lists = new ArrayList<Customer>();
								if(!response.isNull("item")){
									JSONArray arr = response.getJSONArray("item");
									Log.i(TAG, arr.toString());
									Gson gson = new Gson();
									if (arr != null) {
										findCustomer = gson.fromJson(arr.toString(), new TypeToken<List<Customer>>() {}.getType());
										StringBuffer sb = new StringBuffer();
										for(Customer c:findCustomer)
										{
											if(c != null)
											{
												lists.add(c);
												sb.append(c.getId());
												sb.append(",");
											}
										}
										targetCustomerIds = sb.toString().substring(0, sb.length() - 1);
										CoreManager.getInstance().getCustomerManager()
										.setListCustomer(lists);
										Log.i(TAG, "findCustomer size is " + findCustomer.size());
										findCustomer = lists;
									}else{
										findCustomer = null;
										startActivity(new Intent(ActFindTargetCustomer.this, ActProductCustomer.class));
/*										if(SPManager.getInstance().getLongValue(SPManager.ID) == 1)
										{
											getServerAllCustomerList();
										}*///取消假数据
									}
								}else{
									findCustomer = null;
									startActivity(new Intent(ActFindTargetCustomer.this, ActProductCustomer.class));
/*									if(SPManager.getInstance().getLongValue(SPManager.ID) == 1)
									{
										getServerAllCustomerList();
									}*/
								}
									if (findCustomerAdap != null) {
										findCustomerAdap.setCustomerList(lists);
										findCustomerAdap.setSortWays(R.string.total_asset);// 默认排序
	 
//										JSONObject params = new JSONObject();
	//									for (Customer item : findCustomer) {
	//										try {
	//											params.put("customerIds", item.getId());
	////											params.put("expirationDays", 14);
	//										 } catch (JSONException e) {
	//											e.printStackTrace();
	//										}
	//										Request request = new Request( RequestDefine.RQ_CUSTOMER_POST_ECONOMY_LIST, RequestType.POST,params, handlerGetCustomerEconumy);
	//										CoreManager.getInstance().postRequest(request);
	//									}
									}
								
								
							  } catch (JSONException e) {

								e.printStackTrace();
							}
						}
						break;
					case DELETE_TARGET_CS:
						if (Request.RESLUT_OK.equals(response.opt("status"))) {
							CommonUtil.showToast("删除目标客户成功",  ActFindTargetCustomer.this);
							// 重新查询数据刷新
							currentType = FIND_TARGET_CS;
							operatonDataByType(currentType,null);
						 } else {
							CommonUtil.showToast("删除目标客户失败",  ActFindTargetCustomer.this);
						}
						break;
					case ADD_TARGET_CS:
						if (Request.RESLUT_OK.equals(response.opt("status"))) {
							CommonUtil.showToast("添加目标客户成功",  ActFindTargetCustomer.this);
							// 重新查询数据刷新
							currentType = FIND_TARGET_CS;
							operatonDataByType(currentType,null);
						} else {
							CommonUtil.showToast("添加目标客户失败",  ActFindTargetCustomer.this);
						}
					
						break;
					}
				}
			});
			httpUtil.executeRequest();
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
	}
	
	public void getServerAllCustomerList()
	{
		JSONObject params = new JSONObject();
		try {
			params.put("periodPreferencesHigh", 0);
			params.put("bonusPreferencesLow", -1);
			params.put("customerType", 0);
			params.put("status", "A");
			params.put("prodFirstType", 0);
			params.put("bonusPreferencesHigh", 0);
			params.put("periodPreferencesLow", -1);
			params.put("prodSecondtype", 0);
			params.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
			params.put("revenuePreferencesLow", -1);
			params.put("salesOpp", 0);
			params.put("expirationDays", 14);
			params.put("city", "");
			params.put("prodPreference", "");
			params.put("revenuePreferencesHigh", 0);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Request request = new Request(RequestDefine.RQ_CUSTOMER_GET_LIST,
				RequestType.POST, params, handlerGetCustomer);
		CoreManager.getInstance().postRequest(request);
	}
	/*---------
	 * 
	 * 针对张亮的需求所做的假数据 SGDY 2014/8/13
	 * 
	 * -----------*/
	private JsonHttpResponseHandler handlerGetCustomer = new JsonHttpResponseHandler() {
		
		public void onSuccess(JSONObject response) {
			Log.d(TAG,"success a= " + response.toString());
			Gson gson = new Gson(); 
			 try {
				JSONArray jo = response.getJSONArray("list");
				findCustomer = gson.fromJson(jo.toString(), new TypeToken<List<Customer>>() {}.getType());
				List<Customer> lists = new ArrayList<Customer>();
				for(int i = 0;i < findCustomer.size();i ++)
				{
					Customer c = findCustomer.get(i);
					if(c != null)
					{
						lists.add(c);
					}
					if(lists.size() == 2)
					{
						break;
					}
				}
				currentType = ADD_TARGET_CS;
				operatonDataByType(currentType, lists);
/*				if (findCustomerAdap != null) {
					findCustomerAdap.setCustomerList(lists);
					findCustomerAdap.setSortWays(R.string.total_asset);// 默认排序
				}*/
			} catch (JSONException e) {
				e.printStackTrace();
			} 
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			CommonUtil.showToast("获取客户信息失败"+content, ActFindTargetCustomer.this);
		}
	};
	/*---------------------------------------------------------*/
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btnSearch:
			break;
		case R.id.checkSelectAll:
			if (findCustomer == null) {
				return;
			}
			findCustomerAdap.isSeletAllCustomer(selectAllCustomer.isChecked());
			break;
		case R.id.btn_add_customer:
			// 添加目标客户
			Intent in = new Intent(this, ActProductCustomer.class);
			if(targetCustomerIds != null){
				in.putExtra("targetCustomerIds", targetCustomerIds);
			}
			startActivity(in);

			break;
		case R.id.btn_del_customer:
			// 调用接口进行删除数据
			currentType = DELETE_TARGET_CS;
			operatonDataByType(currentType, findCustomerAdap.getSeletCustomerData());
//			deleteTargetCustomers(findCustomerAdap.getSeletCustomerData());
			break;
		case R.id.btn_send_emali:
			break;
			
		case R.id.btn_send_sms:
			StringBuffer targetPhone = new StringBuffer();
			List<Customer> list = findCustomerAdap.getSeletCustomerData();
			for (Customer customer : list) {
				targetPhone.append(customer.getCellPhone1() + ",");
			}
			if (TextUtils.isEmpty(targetPhone.toString())) {
				CommonUtil
						.showToast("短信号码不能为空", ActFindTargetCustomer.this);
				return;
			}
			CommonUtil.sendSms(ActFindTargetCustomer.this,targetPhone.toString());
			break;
		case R.id.imageBack:
			finish();
			break;
		case R.id.img_add_invest:
			PopAddInvester popAddView = new PopAddInvester(this);
			popAddView.setListener(new PopAddInvester.RefreshCustomerListener() {

						@Override
						public void doRefresh(final Customer c) {
							findCustomerAdap.setCustomerList(CoreManager.getInstance().getCustomerManager().getListCustomer());
						}
					});
			popAddView.showAddInvestDlg();
			break;
		case R.id.img_data_analise:
			// 显示客户个人信息PopupWindow
			PopCsInvestDetail popDataAnalise = new PopCsInvestDetail(ActFindTargetCustomer.this, v, null,R.drawable.bg_cs_data_analyise,PopCsInvestDetail.TOP_2_BOTTOM);
			popDataAnalise.showPopWindow();
			break;
		case R.id.search_icon:
			if(search_icon.getDrawable().getCurrent().getConstantState() == getResources().getDrawable(R.drawable.delete).getConstantState())
			{
				etCustomerSearch.setText("");
			}
			break;
		default:
			break;
		}
	}
	 
	private int count = 0;
	
	private JsonHttpResponseHandler handlerGetCustomerEconumy = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.i(TAG, "success o= " + response.toString());
            count ++;
            if (findCustomer != null && findCustomer.size() > 0) {
			
			if (Request.RESLUT_OK.equals(response.optString("status"))) {
				JSONArray arr = response.optJSONArray("list");
				for(int i = 0; i < arr.length(); i++) {//遍历JSONArray 
	                try {
						JSONObject oj = arr.getJSONObject(i);
//						if (oj.has("id")){
//							for (Customer c : findCustomer){
//								if (oj.optLong("id") == c.getId()){
//									c.setInvestNumber(oj.optInt("purchasedQuantity"));
//									c.setInvestTotalAsserts(oj.optInt("purchasedTotalAmount"));
//									c.setDividentCount(oj.optInt("prodDividendQty"));
//									c.setSimilarProductQuantity(oj.optLong("similarProductQuantity"));
//									 
//								}
//							}
//						}
							
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
	            }  
			}
			
			if (findCustomerAdap != null) {
				findCustomerAdap.setCustomerList(findCustomer);
				findCustomerAdap.setSortWays(R.string.total_asset);// 默认排序
			}
			}
		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			count ++;
			if (findCustomerAdap != null) {
				findCustomerAdap.setCustomerList(findCustomer);
				findCustomerAdap.setSortWays(R.string.total_asset);// 默认排序
			}
		}
	};
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (httpUtil != null && httpUtil.getDialog() != null) {
			httpUtil.getDialog().clearAnimation();
		}
	}
}
