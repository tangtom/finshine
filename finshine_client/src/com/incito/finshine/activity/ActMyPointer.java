package com.incito.finshine.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.core.util.AppToast;
import com.custom.view.CommSortView;
import com.custom.view.CommSortView.RefreshSortListener;
import com.custom.view.CommonWaitDialog;
import com.custom.view.FirstPagePop;
import com.custom.view.PopCustomerService;
import com.custom.view.PopDatePicker;
import com.custom.view.PopDatePicker.DatePickerListener;
import com.custom.view.PopPointerAnalyse;
import com.custom.view.PopPointerMarket;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterMyPointer;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.Pointer;
import com.incito.finshine.entity.PointerQueryVariable;
import com.incito.finshine.entity.Product;
import com.incito.finshine.entity.User;
import com.incito.finshine.manager.Constants;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.DateUtil;
import com.incito.utility.PinyinComparator;
import com.incito.wisdomsdk.event.EventBus;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

public class ActMyPointer extends Activity implements OnClickListener,OnItemClickListener,DatePickerListener{

	private final String TAG = ActMyPointer.class.getSimpleName();
	private Button btnFilter;
	private ListView listPointerOrder;
	private AdapterMyPointer adapterMyPointer;
	private ImageView imageBack,imgPointerAnalyse,imgPointerHeader,search_icon,imgPointerMarket;
	private TextView textTitle,txtNoResult; 
	private CommSortView sortView = null;
	private SPManager spManager;
	private List<Pointer> pointerLists;
	private List<Pointer> pointerLists1;
	private List<Pointer> pointerLists2;
	private PointerQueryVariable queryVariable;//查询条件实体类
	private Pointer pointer;//我的积分实体类
	private CommonWaitDialog dialog = null;
	private Button buttonReset,btnSearch;
	private Spinner spinnerPointer;
	private EditText editOrderDate,etSearchText;
	private int sortStatus = 0;
	private PopDatePicker popDatePicker;
	private List<Long> orderLists = new ArrayList<Long>();//存放isNew状态的订单ID
	private LinearLayout layoutFilter;
	
	private JsonHttpResponseHandler mhandler = new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(JSONObject response) {
			try {
				closeDialog();
				Log.i(TAG,response.toString());
				pointerLists.clear();
				pointerLists1.clear();
				pointerLists2.clear();
				JSONArray result = response.getJSONArray("result");
				if(result.length() > 0){
					for(int i = 0;i < result.length();i ++){
						try {
							JSONObject jsonObject = result.getJSONObject(i);
							long id = jsonObject.getLong("id");
							String orderNumber = jsonObject.getString("orderNumber");
							String publisher = jsonObject.getString("publisher");
							double amount = jsonObject.getDouble("amount");
							Gson gson = new Gson();
							Product product = gson.fromJson(jsonObject.getJSONObject("product").toString(), Product.class);
							Customer customer = gson.fromJson(jsonObject.getJSONObject("customer").toString(), Customer.class);
							User salesman = gson.fromJson(jsonObject.getJSONObject("salesman").toString(), User.class);
							Date dateOfCreate = DateUtil.formatString2Date(DateUtil.getDateTimeByFormatAndMs(jsonObject.getLong("dateOfCreate"),DateUtil.FORMAT_YYYY_MM_DD),DateUtil.FORMAT_YYYY_MM_DD);
							double fixedCommissionRatio = jsonObject.getDouble("fixedCommissionRatio");
							double extraCommissionRatio = jsonObject.getDouble("extraCommissionRatio");
							double fixedCommission = jsonObject.getDouble("fixedCommission");
							double extraCommission = jsonObject.getDouble("extraCommission");
							long result_fk = jsonObject.getLong("result_fk");
							short isNew = Short.parseShort(jsonObject.getString("isNew"));
							if(isNew == 1)
							{
								orderLists.add(id);
							}
							pointer = new Pointer();
							pointer.setCustomer(customer);
							pointer.setProduct(product);
							pointer.setSalesman(salesman);
							pointer.setAmount(amount);
							pointer.setId(id);
							pointer.setOrderNumber(orderNumber);
							pointer.setPublisher(publisher);
							pointer.setDateOfCreate(dateOfCreate);
							pointer.setFixedCommission(fixedCommission);
							pointer.setFixedCommissionRatio(fixedCommissionRatio);
							pointer.setExtraCommission(extraCommission);
							pointer.setExtraCommissionRatio(extraCommissionRatio);
							pointer.setResult_fk(result_fk);
//							Log.d(TAG, jsonObject.getString("isNew") + "---------" + jsonObject.getInt("isNew"));
							pointer.setIsNew(isNew);
							pointerLists.add(pointer);
							pointerLists1.add(pointer);
							pointerLists2.add(pointer);
						} catch (JSONException e) {
							e.printStackTrace();
							Log.i(TAG, e.getMessage());
						}
					}
				}
				switch(sortStatus)
				{
				case 0:
					break;
				case 1:
					ComparatorPointerByTime pointer = new ComparatorPointerByTime();
					Collections.sort(pointerLists, pointer);
					break;
				case 2:
//					PinyinComparator comparator = new PinyinComparator();
//					comparator.setCompareStr("pointer");
//					Collections.sort(pointerLists, comparator);
					ComparatorPointerByProductName pointer1 = new ComparatorPointerByProductName ();
					Collections.sort(pointerLists, pointer1);
					break;
				default:
					break;
				}
				Log.i(TAG, pointerLists.isEmpty() + " ");
				if(pointerLists.isEmpty()){
					txtNoResult.setVisibility(View.VISIBLE);
					listPointerOrder.setVisibility(View.GONE);
				}else{
					listPointerOrder.setVisibility(View.VISIBLE);
					txtNoResult.setVisibility(View.GONE);
				}
				adapterMyPointer.notifyDataSetChanged();
				
				if(!orderLists.isEmpty())
				{
					for(long id : orderLists)
					{
						Request request = new Request(RequestDefine.POINTER_RQ_ORDER_STATUS_UPDATE,id, RequestType.PUT, null, mHandler);
						CoreManager.getInstance().postRequest(request);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				closeDialog();
			}
			super.onSuccess(response);
		}
		
		

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, content);
			closeDialog();
		}



		@Override
		public void onSuccess(int statusCode, JSONArray response) {
			super.onSuccess(statusCode, response);
			Log.i(TAG,"jsonarray");
			Log.i(TAG, response.toString());

		}
		
		
	};
	
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	// add by SGDY at 2014/8/13 19:41
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK && event.getRepeatCount() == 0)//按下的如果是BACK，同时没有重复
		{
			startActivity(new Intent(this,ActFinshineHomePage.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	public void getData(){
		if(dialog == null){
			dialog = new CommonWaitDialog(this, "", R.string.load_data);
		}
		JSONObject params = new JSONObject();
		try {
			params.put("user_fk", queryVariable.getUser_fk());
			params.put("sortOrder", queryVariable.getSortOrder());
			params.put("status_fk", queryVariable.getStatus_fk());
			if(queryVariable.getFromDate() != null && queryVariable.getToDate() != null){
				params.put("fromDate", DateUtil.formatDate2String(queryVariable.getFromDate(), DateUtil.FORMAT_YYYY_MM_DD));
				params.put("toDate", DateUtil.formatDate2String(queryVariable.getToDate(), DateUtil.FORMAT_YYYY_MM_DD));
			}else{
				
			}
			Log.i(TAG, params.toString());
			Request request = new Request(RequestDefine.POINTER_RQ_MY_POINTER, RequestType.POST, params, mhandler);
			CoreManager.getInstance().postRequest(request);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_my_pointer);
		EventBus.getDefault().registerSticky(this);
		initView();
		initData();
	}
	
	public void initData(){
		pointerLists = new ArrayList<Pointer>();
		pointerLists1 = new ArrayList<Pointer>();
		pointerLists2 = new ArrayList<Pointer>();
		adapterMyPointer = new AdapterMyPointer(this,pointerLists);
		listPointerOrder.setAdapter(adapterMyPointer);
		spManager = SPManager.getInstance();
		queryVariable = new PointerQueryVariable();
		queryVariable.setUser_fk(spManager.getLongValue(SPManager.ID));
		queryVariable.setStatus_fk(Constants.POINTER_STATUS_DEFAULT);
		queryVariable.setSortOrder(Constants.POINTER_ORDER_SORT_DEFAULT);
		getData();
	}
	
	private void initPullPop() {
		FirstPagePop firstPage = new FirstPagePop(this,
				findViewById(R.id.imageNavigate));
		firstPage.setPosition(4);
		firstPage.showPopWindow();
	}

	public void initView(){
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(R.string.pointer_sort_default);
		ids.add(R.string.pointer_transaction_time);
		ids.add(R.string.pointer_product_name);
		sortView = new CommSortView(this, ids, (LinearLayout)findViewById(R.id.sortButton), R.string.pointer_sort_default);
		sortView.setRefreshSortListener(new RefreshSortListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void doDataSort(int id) {
				switch(id){
				case R.string.pointer_sort_default:
					sortStatus = 0;
					pointerLists.clear();
					for(Pointer p : pointerLists1)
					{
						pointerLists.add(p);
					}
					adapterMyPointer.notifyDataSetChanged();
					break;
				case R.string.pointer_transaction_time:
					sortStatus = 1;
					ComparatorPointerByTime pointer = new ComparatorPointerByTime();
					Collections.sort(pointerLists, pointer);
					adapterMyPointer.notifyDataSetChanged();
					break;
				case R.string.pointer_product_name:
					sortStatus = 2;
//					PinyinComparator comparator = new PinyinComparator();
//					comparator.setCompareStr("pointer");
//					Collections.sort(pointerLists, comparator);
					ComparatorPointerByProductName pointer1 = new ComparatorPointerByProductName ();
					Collections.sort(pointerLists, pointer1);
					adapterMyPointer.notifyDataSetChanged();
					break;
				default:break;
				}
			}
		});
		btnFilter = (Button)findViewById(R.id.btnFilter);
		btnFilter.setOnClickListener(this);
		buttonReset = (Button)findViewById(R.id.buttonReset);
		buttonReset.setOnClickListener(this);
		btnSearch = (Button)findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(this);
		listPointerOrder = (ListView)findViewById(R.id.listPointerOrder);
		imageBack = (ImageView)findViewById(R.id.imageBack);
		imageBack.setOnClickListener(this);
		textTitle = (TextView)findViewById(R.id.textTitle);
		textTitle.setText("佣金");
		txtNoResult = (TextView)findViewById(R.id.txtNoResult);
		imgPointerAnalyse = (ImageView)findViewById(R.id.imgPointerAnalyse);
		imgPointerAnalyse.setOnClickListener(this);
		imgPointerHeader = (ImageView)findViewById(R.id.imgPointerHeader);
		imgPointerHeader.setOnClickListener(this);
		imgPointerMarket = (ImageView)findViewById(R.id.imgPointerMarket);
		imgPointerMarket.setOnClickListener(this);
		spinnerPointer = (Spinner)findViewById(R.id.spinnerPointer);
		ArrayAdapter adapter = new ArrayAdapter<String>(this,
				R.layout.common_spinner_item, this.getResources().getStringArray(R.array.order_status));// android.R.layout.simple_spinner_item
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//
		spinnerPointer.setAdapter(adapter);
		editOrderDate = (EditText)findViewById(R.id.editOrderDate);
		editOrderDate.setOnClickListener(this);
		search_icon = (ImageView)findViewById(R.id.search_icon);
		search_icon.setOnClickListener(this);
		etSearchText = (EditText)findViewById(R.id.et_search_text);
		findViewById(R.id.imageNavigate).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initPullPop();
			}
		});
		etSearchText.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int count) {
				if(cs.length() != 0){
					etSearchText.setTextAppearance(ActMyPointer.this, R.style.Txt14Gray808080);
					search_icon.setImageDrawable(getResources().getDrawable(R.drawable.delete));
				}else{
					etSearchText.setTextAppearance(ActMyPointer.this, R.style.Txt12Gray808080);
					search_icon.setImageDrawable(getResources().getDrawable(R.drawable.product_search_icon));
				}
				if(adapterMyPointer != null)
				{
					adapterMyPointer.getFilter().filter(cs);
				}
			}
			
		});
		etSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId == EditorInfo.IME_ACTION_SEARCH)
				{
					searchByCustomerName();
				}
				return false;
			}
		});
		spinnerPointer.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View v,
					int position, long id) {
				switch(position)
				{
				case 0:
					queryVariable.setStatus_fk(Constants.POINTER_STATUS_DEFAULT);
					break;
				case 1:
					queryVariable.setStatus_fk(Constants.POINTER_STATUS_VERIFING);
					break;
				case 2:
					queryVariable.setStatus_fk(Constants.POINTER_STATUS_ACCOUNTED);
					break;
				default:break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnFilter:
			layoutFilter = (LinearLayout)findViewById(R.id.layoutFilter);
			int visibility = (layoutFilter.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
			layoutFilter.setVisibility(visibility);
			break;
		case R.id.imageBack:
			startActivity(new Intent(this,ActFinshineHomePage.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			break;
		case R.id.imgPointerAnalyse:
			PopPointerAnalyse popPointerAnalyse = new PopPointerAnalyse(this,v);
			popPointerAnalyse.showPopWindow();
			break;
		case R.id.imgPointerHeader:
			//PopCustomerService popCustomerService = new PopCustomerService(this,v);
			//popCustomerService.showPopWindow();
			AppToast.ShowToast(R.string.com_coming_soon);
			break;
		case R.id.buttonReset:
			editOrderDate.setText("全部");
			spinnerPointer.setSelection(0, true);
			queryVariable.setStatus_fk(Constants.POINTER_STATUS_DEFAULT);
			queryVariable.setSortOrder(Constants.POINTER_ORDER_SORT_DEFAULT);
			queryVariable.setFromDate(null);
			queryVariable.setToDate(null);
			break;
		case R.id.btnSearch:
			layoutFilter.setVisibility(View.GONE);
			getData();
			break;
		case R.id.editOrderDate:
			if(popDatePicker == null)
			{
				popDatePicker = new PopDatePicker(this,v);
			}
			popDatePicker.showPopWindow();
			break;
		case R.id.search_icon:
//			searchByCustomerName();
			if(search_icon.getDrawable().getCurrent().getConstantState() == getResources().getDrawable(R.drawable.delete).getConstantState())
			{
				etSearchText.setText("");
			}
			break;
		case R.id.imgPointerMarket:
//			PopPointerMarket popPointerMarket = new PopPointerMarket(this,v);
//			popPointerMarket.showPopWindow();
			AppToast.ShowToast(R.string.com_coming_soon);
			break;
		default:
			break;
		}
	}
	
	public void searchByCustomerName()
	{
		String name = etSearchText.getText().toString().trim();
		pointerLists.clear();
		pointerLists1.clear();
		for(Pointer p : pointerLists2)
		{
			pointerLists.add(p);
			pointerLists1.add(p);
		}
		if(name.isEmpty())
		{
			//CommonUtil.showToast("请输入客户姓名", this);
		}else
		{
			List<Pointer> pointers  = new ArrayList<Pointer>();
			for(Pointer p : pointerLists)
			{
				if(!p.getCustomer().getName().contains(name) && !p.getOrderNumber().contains(name))
				{
					pointers.add(p);
				}
			}
			pointerLists.removeAll(pointers);
			pointerLists1.removeAll(pointers);
		}
		switch(sortStatus)
		{
		case 0:
			break;
		case 1:
			ComparatorPointerByTime pointer = new ComparatorPointerByTime();
			Collections.sort(pointerLists, pointer);
			break;
		case 2:
//			PinyinComparator comparator = new PinyinComparator();
//			comparator.setCompareStr("pointer");
//			Collections.sort(pointerLists, comparator);
			ComparatorPointerByProductName pointer1 = new ComparatorPointerByProductName ();
			Collections.sort(pointerLists, pointer1);
			break;
		default:
			break;
		}
		if(pointerLists.isEmpty()){
			txtNoResult.setVisibility(View.VISIBLE);
			listPointerOrder.setVisibility(View.GONE);
		}else{
			listPointerOrder.setVisibility(View.VISIBLE);
			txtNoResult.setVisibility(View.GONE);
		}
		adapterMyPointer.notifyDataSetChanged();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	private  void closeDialog(){
		if (dialog != null) {
			dialog.clearAnimation();
			dialog = null;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
		if(pointerLists.get(position).getIsNew() == 1)//判断订单状态是否为news，点击更新状态
		{
//			Request request = new Request(RequestDefine.POINTER_RQ_ORDER_STATUS, pointerLists.get(position).getId(), RequestType.PUT,null, mHandler);
//			CoreManager.getInstance().postRequest(request);
		}
	}

	private JsonHttpResponseHandler mHandler = new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, JSONObject response) {
			super.onSuccess(statusCode, response);
		}

		@Override
		public void onFailure(Throwable e, JSONObject errorResponse) {
			super.onFailure(e, errorResponse);
			Log.i(TAG, "errorresponse is " + errorResponse);
		}
		
		@Override
		public void onSuccess(JSONObject response) {
			super.onSuccess(response);
			Log.i(TAG, "response is " + response);
		}
		
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void onEvent(String value)
	{	
		if(!value.equals("全部"))
		{
			String[] str = value.split("----");
			String str1 = str[0];
			String str2 = str[1];
			queryVariable.setFromDate(DateUtil.formatString2Date(str1, DateUtil.FORMAT_YYYY_MM_DD_ZH));
			queryVariable.setToDate(DateUtil.formatString2Date(str2, DateUtil.FORMAT_YYYY_MM_DD_ZH));
		}
		editOrderDate.setText(value);
	}
	@Override
	public void setTextValue(String value) {
//		String[] str = value.split("----");
//		String str1 = str[0];
//		String str2 = str[1];
//		queryVariable.setFromDate(DateUtil.formatString2Date(str1, DateUtil.FORMAT_YYYY_MM_DD_ZH));
//		queryVariable.setToDate(DateUtil.formatString2Date(str2, DateUtil.FORMAT_YYYY_MM_DD_ZH));
//		editOrderDate.setText(value);
	}
	PinyinComparator comparator = new PinyinComparator();
	public class ComparatorPointerByProductName implements Comparator{
		@Override
		public int compare(Object obj1, Object obj2) {
			Pointer pointer1 = (Pointer)obj1;
			Pointer pointer2 = (Pointer)obj2;
//			int flag = pointer1.getProduct().getProdName().compareTo(
//					pointer2.getProduct().getProdName()
//					);
			comparator.setCompareStr("pointer");
			int flag = comparator.compare(pointer1, pointer2);
			if(flag == 0){
				return pointer2.getOrderNumber().compareTo(pointer1.getOrderNumber());
			}
			return flag;
		}

	}
	public class ComparatorPointerByTime  implements Comparator{

		@Override
		public int compare(Object obj1, Object obj2) {
			Pointer pointer1 = (Pointer)obj1;
			Pointer pointer2 = (Pointer)obj2;
			int flag = DateUtil.formatDate2String(pointer2.getDateOfCreate(), DateUtil.FORMAT_YYYYMMDD).compareTo(
					DateUtil.formatDate2String(pointer1.getDateOfCreate(), DateUtil.FORMAT_YYYYMMDD)
					);
			if(flag == 0){
				int flag2 = pointer2.getOrderNumber().compareTo(pointer1.getOrderNumber());
				return flag2;
			}
			return flag;
		}

	}
}
