package com.incito.finshine.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.custom.view.CommSortView;
import com.custom.view.CommonWaitDialog;
import com.custom.view.DlgCommFilter;
import com.custom.view.CommSortView.RefreshSortListener;
import com.custom.view.DlgCommFilter.RefreshFilterListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterInvestRecord;
import com.incito.finshine.customer.ContactHistory;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.InvestmentRecord;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.RequestDefine;
import com.incito.finshine.network.Request.RequestType;
import com.incito.utility.CommonUtil;
import com.incito.utility.FilterUtil;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

/**
 * <dl>
 * <dt>FragmentInvestRecord.java</dt>
 * <dd>Description:投资记录</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-5 下午4:09:57</dd>
 * </dl>
 * 
 * @author lihs
 */
public class FragmentInvestRecord extends FragmentDetail implements
		OnClickListener {

	private static final String TAG = FragmentInvestRecord.class
			.getSimpleName();

	private ListView listInvestRecord;
	private AdapterInvestRecord adapterInvestRecord;

	private View investView = null;
	private Customer customer;
	private List<InvestmentRecord> list;

	private CommonWaitDialog waitDlg = null;

	private Spinner spinnerMaturityStatus;
	private Button btnInvestment;

	private EditText etProdSearch;

	public static FragmentInvestRecord newInstance(int id) {

		FragmentInvestRecord f = new FragmentInvestRecord();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);

		return f;
	}

	private JsonHttpResponseHandler handlerGetInvestRecord = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.i(TAG, "success o= " + response.toString());

			int statues = response.optInt("status");
			if (0 == statues) {
				JSONArray arr = response.optJSONArray("list");
				Log.i(TAG, "success a= " + response.toString());
				Gson gson = new Gson();
				list = gson.fromJson(arr.toString(),
						new TypeToken<List<InvestmentRecord>>() {
						}.getType());

				if (list.size() == 0) {
					CommonUtil.showToast("暂时没有获取到符合条件的数据", getActivity());
				}
				if (adapterInvestRecord != null) {
					adapterInvestRecord.setItemList(list);
					listInvestRecord.setAdapter(adapterInvestRecord);
					adapterInvestRecord.sortProduct(R.string.product_manager_default_sort);
				}
			}
			closeDialog();
		}

		@Override
		public void onSuccess(JSONArray response) {
			closeDialog();
			Log.i(TAG, "success a= " + response.toString());
		}

		@Override
		public void onFailure(Throwable error, String content) {
			closeDialog();
			Log.i(TAG, "onFailure = " + content);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		investView = inflater.inflate(R.layout.fra_invest_records, container,
				false);
		customer = ((ActCustomerDetail) getActivity()).getCustomer();

		init();

		getInvestmentRecord();
		return investView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	private boolean getInvestmentRecord() {
		waitDlg = new CommonWaitDialog(this.getActivity(), "",
				R.string.load_data);

		JSONObject json = new JSONObject();
		try {
			json.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
			json.put("customerId", customer.getId());
			json.put("marketingStatusId", 5);
			json.put("expireStatus",
					spinnerMaturityStatus.getSelectedItemPosition());
			json.put("changeAmountLow", FilterUtil.getUnSureBtnValue(
					btnInvestment.getText().toString(), true));
			json.put("changeAmountHigh", FilterUtil.getUnSureBtnValue(
					btnInvestment.getText().toString(), false));
			json.put("expirationDays", 14);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Log.i("筛选的投资记录的参数是", json.toString());

		Request request = new Request(RequestDefine.RQ_CUSTOMER_INVEST_RECORD,
				RequestType.POST, json, handlerGetInvestRecord);
		CoreManager.getInstance().postRequest(request);
		return true;
	}

	private void init() {

		spinnerMaturityStatus = (Spinner) investView
				.findViewById(R.id.spinnerMaturityStatus);
		ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.common_spinner_item, getResources().getStringArray(
						R.array.maturity_status));// android.R.layout.simple_spinner_item
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//
		spinnerMaturityStatus.setAdapter(adapter);

		btnInvestment = (Button) investView.findViewById(R.id.btnInvestment);
		btnInvestment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				DlgCommFilter fDeadLind = new DlgCommFilter(getActivity(),
						R.array.investment, R.string.investment, btnInvestment.getText().toString(), true, 3);
				fDeadLind.setListener(new RefreshFilterListener() {

					@Override
					public void doRefresh(String reslut, int position,boolean b,int title) {

						btnInvestment.setText(reslut);
					}
				});
				fDeadLind.showDialog();
			}
		});

		// 重置筛选条件
		Button reset = (Button) investView.findViewById(R.id.buttonReset);
		reset.setOnClickListener(this);

		Button search = (Button) investView.findViewById(R.id.btnSearch);
		search.setOnClickListener(this);

		// 搜索客户
		etProdSearch = (EditText) investView.findViewById(R.id.et_search_text);
		etProdSearch.setOnClickListener(this);
		etProdSearch.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (adapterInvestRecord != null) {
					adapterInvestRecord.getFilter().filter(s.toString());
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		});

		List<Integer> ids = new ArrayList<Integer>();
		ids.add(R.string.product_manager_default_sort);
		ids.add(R.string.invest_money);
		ids.add(R.string.invest_deadline);
		ids.add(R.string.invest_ecpected_income_rate);
		CommSortView sortView = new CommSortView(getActivity(), ids,
				(LinearLayout) investView.findViewById(R.id.sortButton),
				R.string.product_manager_default_sort);
		sortView.setRefreshSortListener(new RefreshSortListener() {

			@Override
			public void doDataSort(int id) {
				if (adapterInvestRecord != null) {
					adapterInvestRecord.sortProduct(id);
				}
			}
		});

		investView.findViewById(R.id.btnFilter).setOnClickListener(this);
		investView.findViewById(R.id.buttonReset).setOnClickListener(this);
		investView.findViewById(R.id.btnSearch).setOnClickListener(this);

		listInvestRecord = (ListView) investView
				.findViewById(R.id.listview_invest_record);
		adapterInvestRecord = new AdapterInvestRecord(getActivity());
		ArrayList<InvestmentRecord> infos = new ArrayList<InvestmentRecord>();
		// ContactHistory item = null;
		// for (int i = 0; i < 6; i++) {
		// item = new ContactHistory();
		// infos.add(item);
		// }
		adapterInvestRecord.setItemList(infos);
		listInvestRecord.setAdapter(adapterInvestRecord);
		listInvestRecord.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent i = new Intent(getActivity(),
						ActProductDetail.class);
				i.putExtra(ActProductDetail.PRODUCT_ID, id);
				i.putExtra(ActProductDetail.FLAG, 0);
				getActivity().startActivity(i);
			}
		});

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnFilter:
			LinearLayout sortView = (LinearLayout) investView
					.findViewById(R.id.filter);
			if (View.VISIBLE == sortView.getVisibility()) {
				sortView.setVisibility(View.GONE);
			} else {
				sortView.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.buttonReset:// 重置
			spinnerMaturityStatus.setSelection(0, true);
			btnInvestment.setText("全部");
			break;
		case R.id.btnSearch:// 筛选
			getInvestmentRecord();
			break;
		case R.id.et_search_text:// 搜索产品名
			etProdSearch.setText("");
			break;
		default:
			break;
		}
	}

	private void closeDialog() {

		if (waitDlg != null) {
			waitDlg.clearAnimation();
		}
	}
}