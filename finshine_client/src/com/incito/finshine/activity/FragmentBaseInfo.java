package com.incito.finshine.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.core.util.AppToast;
import com.codans.blossom.datepicker.DlgDatePicker;
import com.custom.view.CommonDialog;
import com.custom.view.CommonDialog.BtnClickedListener;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.net.http.AsyncHttpResponseHandler;

/**
 * 
 * <dl>
 * <dt>FragmentBaseInfo.java</dt>
 * <dd>Description:客户管理 基本信息 按钮编辑和保存之后切换Fragment下不同的viewPager界面</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月5日 下午8:23:34</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class FragmentBaseInfo extends FragmentDetail implements
		OnDateChangedListener, OnClickListener {

	private static final String TAG = FragmentBaseInfo.class.getSimpleName();
	public static final int F_BASE_INFO = 0;
	public static final int F_BASE_INFO_EDIT = 1;

	private Button btnEdit;
	private Button btnConfirm;
	private Button btnCancel;
	private LinearLayout btnBars;

	// ListView listContactHistory;
	// AdapterContactHistory adapterContactHistory;
	TextView textInfo;
	ImageButton imgBtnMore;
	ImageButton imgBtnEdit;
	Button cancelBtn;
	Button confirmBtn;

	View base_info_editView;
	View view;
	FragmentDetail details;

	boolean isNormal = true;
	private Customer customer;
	private Customer tempcustomer;
	private EditText editBirthday;

//	private int investNumber = 0;// 投资次数
	
	private long customerID = -1;

	private AsyncHttpResponseHandler handlerUpdateCustomer = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			Gson gson = new Gson();
			Customer c = gson.fromJson(content, Customer.class);
			// CoreManager.getInstance().getCustomerManager().addCustomer(c);

			// 成功更新界面 否则不更新
//			c.setInvestNumber(investNumber);// 不可编辑 所以没有二次获取
			CoreManager.getInstance().getCustomerManager().updateCustomer(c);

			customer = CoreManager.getInstance().getCustomerManager()
					.getCustomer(c.getId());
			boolean ret = details instanceof FragmentCustomBaseInfoShow;
			if (ret) {
				((FragmentCustomBaseInfoShow) details).initUIData(customer);
				Log.i(TAG, customer.getAge() + "-------------");
			}

			Toast.makeText(FragmentBaseInfo.this.getActivity(), "更新用户成功",
					Toast.LENGTH_SHORT).show();

		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			Toast.makeText(FragmentBaseInfo.this.getActivity(), "更新用户失败",
					Toast.LENGTH_SHORT).show();
		}
	};

	public static FragmentBaseInfo newInstance(int id) {

		FragmentBaseInfo f = new FragmentBaseInfo();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		customer = ((ActCustomerDetail) getActivity()).getCustomer();// 直接从本地获取customer的值，可能会有很多bug，因为不止一个地方会跳到这个界面
		if (customer != null)
//			investNumber = customer.getInvestNumber();
			customerID = customer.getId();//所有界面跳过来都要给个id
		if (container == null) {
			return null;
		}
		view = inflater.inflate(R.layout.frag_base_info, container, false);
		
		getCustomerInfo();//重新从后台获取数据
		initUI();
		
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {// 1
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
	}
	
	private boolean getCustomerInfo() {
		if (customerID == -1)
			CommonUtil.showToast("获取客户信息失败", getActivity());
		else {
			Request request = new Request(RequestDefine.RQ_CUSTOMER_GET_SINGLE,
					customerID, RequestType.GET, null, handlerGetCustomer);

			CoreManager.getInstance().postRequest(request);
		}
		return true;
	}

	private AsyncHttpResponseHandler handlerGetCustomer = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			Gson gson = new Gson();
			customer = gson.fromJson(content, Customer.class);
			if (customer != null)
			{
				CoreManager.getInstance().getCustomerManager().updateCustomer(customer);//更新customer
				showDetails(F_BASE_INFO, 0, customer);
			}
			else
				Toast.makeText(getActivity(), "获取客户信息失败",
						Toast.LENGTH_SHORT).show();
			
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			AppToast.ShowToast("获取用户信息失败");
		}
	};

//	private boolean getCustomerEconomyInfo() {
//		JSONObject json = new JSONObject();
//		try {
//			json.put("customerIds", customerID);
//			json.put("expirationDays", 14);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//
//		Request request = new Request(
//				RequestDefine.RQ_CUSTOMER_POST_ECONOMY_LIST, RequestType.POST,
//				json, handlerGetCustomerEconumy);
//		CoreManager.getInstance().postRequest(request);
//
//		return true;
//	}

//	private JsonHttpResponseHandler handlerGetCustomerEconumy = new JsonHttpResponseHandler() {
//		@Override
//		public void onSuccess(JSONObject response) {
//			Log.i(TAG, "success o= " + response.toString());
//
//			int statues = response.optInt("status");
//			if (0 == statues) {
//
//				JSONArray arr = response.optJSONArray("list");
//
//				for (int i = 0; i < arr.length(); i++) {// 遍历JSONArray
//
//					try {
//						JSONObject oj = arr.getJSONObject(i);
//						if (oj.optLong("id") == customer.getId()) {
//							customer.setInvestNumber(oj
//									.optInt("purchasedQuantity"));
//							customer.setInvestTotalAsserts(oj
//									.optInt("purchasedTotalAmount"));
//							customer.setDividentCount(oj
//									.optInt("prodDividendQty"));
////							investNumber = customer.getInvestNumber();
//						}
//
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//
//			}
//			CoreManager.getInstance().getCustomerManager().updateCustomer(customer);//更新customer
//			
//		}
//
//		@Override
//		public void onSuccess(JSONArray response) {
//			// closeDialog();
//			Log.i(TAG, "success a= " + response.toString());
//		}
//
//		@Override
//		public void onFailure(Throwable error, String content) {
//			// closeDialog();
//			Log.i(TAG, "onFailure = " + content);
//		}
//	};


	private void initUI() {

		btnBars = (LinearLayout) view.findViewById(R.id.buttonBarsLinear);

		// 编辑
		btnEdit = (Button) view.findViewById(R.id.btnEdit);
		btnEdit.setOnClickListener(this);

		// 保存
		btnConfirm = (Button) view.findViewById(R.id.btnConfirm);
		btnConfirm.setOnClickListener(this);

		// 取消
		btnCancel = (Button) view.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);

		details = (FragmentDetail) getFragmentManager()// getActivity().getSupportFragmentManager()
				.findFragmentById(R.id.customBaseInfoDetails);
	}

	public void showDetails(int id, int position, Customer customer) {
		Log.i(TAG, "showDetails = " + id);
		// FragmentDetail details = (FragmentDetail) getFragmentManager()//
		// getActivity().getSupportFragmentManager()
		// .findFragmentById(R.id.customBaseInfoDetails);
		// if (details == null || !(details.getFragmentId() == id)) {
		// Make new fragment to show this selection.
		switch (id) {
		case F_BASE_INFO:
			details = FragmentCustomBaseInfoShow.newInstance(id, position,
					customer);
			break;
		case F_BASE_INFO_EDIT:
			details = FragmentCustomBaseInfoEdit.newInstance(id, position,
					customer);
			break;

		default:
			break;
		}

		// Execute a transaction, replacing any existing fragment
		// with this one inside the frame.
		FragmentTransaction ft = getFragmentManager().beginTransaction();// getActivity().getSupportFragmentManager()
		ft.replace(R.id.customBaseInfoDetails, details);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
		// }
	}

	@Override
	public void onClick(View v) {

		// final FragmentDetail details = (FragmentDetail) getFragmentManager()
		// .findFragmentById(R.id.customBaseInfoDetails);
		int currentView = 0;
		if (details instanceof FragmentCustomBaseInfoShow) {
			currentView = ((FragmentCustomBaseInfoShow) details).getNowView();
		} else {
			currentView = ((FragmentCustomBaseInfoEdit) details).getNowView();
		}

		switch (v.getId()) {
		case R.id.btnEdit:
			showDetails(F_BASE_INFO_EDIT, currentView, customer);
			btnEdit.setVisibility(View.GONE);
			btnBars.setVisibility(View.VISIBLE);
			break;
		case R.id.btnConfirm:
			tempcustomer = ((FragmentCustomBaseInfoEdit) details)
					.saveCustomer();
			if (tempcustomer != null) {
				showDetails(F_BASE_INFO, currentView, customer);
				btnEdit.setVisibility(View.VISIBLE);
				btnBars.setVisibility(View.GONE);
				updateCustomerInfo();
			}
			break;
		case R.id.btnCancel:
		   	boolean checkedValue = ((FragmentCustomBaseInfoEdit)details).iSCustomerEditInformationChanged(customer);
		   	if (checkedValue) {
				final int currentViewIndex = currentView;
				CommonDialog cancelDialog = new CommonDialog(getActivity());
				cancelDialog.setTitle(R.string.alert);
				
				cancelDialog.setMessage("您更改了数据，点击确定，将不会保存您的数据，您确定要取消吗？");
				cancelDialog.setPositiveButton(new BtnClickedListener() {
					public void onBtnClicked() {
						btnEdit.setVisibility(View.VISIBLE);
						btnBars.setVisibility(View.GONE);
						showDetails(F_BASE_INFO, currentViewIndex, customer);
					}
				}, R.string.btn_ok);
				cancelDialog.setCancleButton(null, R.string.btn_cancle);
				cancelDialog.showDialog();

			}else {
				showDetails(F_BASE_INFO, currentView, customer);
				btnEdit.setVisibility(View.VISIBLE);
				btnBars.setVisibility(View.GONE);
			}
			break;
		default:
			break;
		}

	}

	private boolean updateCustomerInfo() {
		Gson gson = new Gson();
		Request request;
		JSONObject json;
		try {
			json = new JSONObject(gson.toJson(tempcustomer));
			json.remove("listContactNote");
			json.remove("investTotalAsserts");
			json.remove("salesId");// ????哪里传来的？
			json.remove("similarProductQuantity");
			json.remove("bindingStatusId");
			json.remove("contactNoteTitle");
			json.remove("prodDividendQty");

			Log.i(TAG, "更新后的用户json数据为" + json.toString());
			request = new Request(RequestDefine.RQ_CUSTOMER_UPDATE,
					tempcustomer.getId(), RequestType.PUT, json,
					handlerUpdateCustomer);

			CoreManager.getInstance().postRequest(request);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private Spinner province_spinner;
	private Spinner city_spinner;

	private Integer provinceId;

	private ArrayAdapter<CharSequence> province_adapter;
	private ArrayAdapter<CharSequence> city_adapter;
	private String[] ecRelationStatus;
	private String[] educationArr;
	private String[] jobArr;
	private String[] countryArr;
	private String[] investArr;

	private void initSpinner() {
		province_spinner = (Spinner) view.findViewById(R.id.spinnerProvince);
		province_spinner.setPrompt("请选择省份");
		province_adapter = ArrayAdapter.createFromResource(this.getActivity(),
				R.array.province_item, android.R.layout.simple_spinner_item);

		province_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		province_spinner.setAdapter(province_adapter);
		province_spinner.setSelection(
				province_adapter.getPosition(customer.getProvince()), true);
		// select(province_spinner, province_adapter, R.array.province_item);
		province_spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						provinceId = province_spinner.getSelectedItemPosition();
						city_spinner = (Spinner) view
								.findViewById(R.id.spinnerCity);
						if (true) {
							Log.v("test", "province: "
									+ province_spinner.getSelectedItem()
											.toString() + provinceId.toString());

							city_spinner.setPrompt("请选择城市");
							select(city_spinner, city_adapter, city[provinceId]);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

		initBaseSpinner(R.id.spinnerEducation, educationArr,
				customer.getDiploma());
		initBaseSpinner(R.id.spinnerProfession, jobArr, 0);// customer.getJob()
		initBaseSpinner(R.id.spinnerCountry, countryArr, 0);// customer.getCountry()
		initBaseSpinner(R.id.spinnerInvest, investArr, 0);// 还没有确定
	}

	private void initBaseSpinner(int id, String[] dataList, int selectedID) {

		Spinner sp = (Spinner) view.findViewById(id);
		ArrayAdapter adapter = new ArrayAdapter<String>(this.getActivity(),
				android.R.layout.simple_spinner_item, dataList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);
		sp.setSelection(selectedID, true);
	}

	public void showDatePickerDialog(int title, OnDateChangedListener listener) {
		DlgDatePicker picker = new DlgDatePicker(
				FragmentBaseInfo.this.getActivity(), title, listener);
		picker.show();
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		if (editBirthday != null) {
			editBirthday.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
			// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// try {
			// birthday = sdf.parse(editBirthday.getText().toString());
			// } catch (ParseException e) {
			// e.printStackTrace();
			// }
		}
	}

	private int[] city = { R.array.beijin_province_item,
			R.array.tianjin_province_item, R.array.heibei_province_item,
			R.array.shanxi1_province_item, R.array.neimenggu_province_item,
			R.array.liaoning_province_item, R.array.jilin_province_item,
			R.array.heilongjiang_province_item, R.array.shanghai_province_item,
			R.array.jiangsu_province_item, R.array.zhejiang_province_item,
			R.array.anhui_province_item, R.array.fujian_province_item,
			R.array.jiangxi_province_item, R.array.shandong_province_item,
			R.array.henan_province_item, R.array.hubei_province_item,
			R.array.hunan_province_item, R.array.guangdong_province_item,
			R.array.guangxi_province_item, R.array.hainan_province_item,
			R.array.chongqing_province_item, R.array.sichuan_province_item,
			R.array.guizhou_province_item, R.array.yunnan_province_item,
			R.array.xizang_province_item, R.array.shanxi2_province_item,
			R.array.gansu_province_item, R.array.qinghai_province_item,
			R.array.linxia_province_item, R.array.xinjiang_province_item,
			R.array.hongkong_province_item, R.array.aomen_province_item,
			R.array.taiwan_province_item };

	private void select(Spinner spin, ArrayAdapter<CharSequence> adapter,
			int arry) {
		adapter = ArrayAdapter.createFromResource(this.getActivity(), arry,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
		// spin.setSelection(0,true);
	}

}