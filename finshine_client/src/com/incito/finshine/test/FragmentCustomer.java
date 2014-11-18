package com.incito.finshine.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codans.blossom.datepicker.DlgDatePicker;
import com.custom.view.CommonDialog.BtnClickedListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.activity.FragmentDetail;
import com.incito.finshine.activity.adapter.AdapterCustomerBaseDetailViewPager;
import com.incito.finshine.entity.ContactNote;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.TodoItem;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.finshine.R;
import com.incito.wisdomsdk.net.http.AsyncHttpClient;
import com.incito.wisdomsdk.net.http.AsyncHttpResponseHandler;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker.OnDateChangedListener;

public class FragmentCustomer extends FragmentDetail {

	private static final String TAG = FragmentCustomer.class.getSimpleName();
	private TextView msg;

	private AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			if (msg != null) {
				msg.setText(content);
			}
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			if (msg != null) {
				msg.setText(content);
			}
		}
	};

	public static FragmentCustomer newInstance(int id) {

		FragmentCustomer f = new FragmentCustomer();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);

		return f;
	}

//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//
//		if (container == null) {
//			return null;
//		}
//		View view;
//		= inflater.inflate(R.layout.test_customer, container, false);
//		msg = (TextView) view.findViewById(R.id.textMessage);
//
//		Button create = (Button) view.findViewById(R.id.btnCreateCustomer);
//		create.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				JSONObject params = new JSONObject();
//				try {
//					params.put("name", "测试");
//					params.put("cellPhone1", "13206342333");
//					params.put("email1", "test@163.com");
//					params.put("birthday", System.currentTimeMillis());
//					params.put("gender", 1);
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//
//				Request request = new Request(RequestDefine.RQ_CUSTOMER_ADD,
//						RequestType.POST, params, handler);
//				CoreManager.getInstance().postRequest(request);
//			}
//		});
//		Button getCustomerList = (Button) view
//				.findViewById(R.id.btnGetCustomerList);
//		getCustomerList.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Request request = new Request(
//						RequestDefine.RQ_CUSTOMER_GET_LIST, RequestType.GET,
//						null, handler);
//				CoreManager.getInstance().postRequest(request);
//			}
//		});
//		Button getCustomer = (Button) view
//				.findViewById(R.id.btnGetSingleCustomer);
//		getCustomer.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Request request = new Request(
//						RequestDefine.RQ_CUSTOMER_GET_SINGLE, 2,
//						RequestType.GET, null, handler);
//				CoreManager.getInstance().postRequest(request);
//			}
//		});
//		Button updateCustomer = (Button) view
//				.findViewById(R.id.btnUpdateCustomer);
//		updateCustomer.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				JSONObject params = new JSONObject();
//				try {
//					params.put("name", "测试");
//					params.put("cellPhone1", "13206342333");
//					params.put("email1", "test@163.com");
//					params.put("gender", 1);
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				Request request = new Request(RequestDefine.RQ_CUSTOMER_UPDATE,
//						2, RequestType.PUT, params, handler);
//				CoreManager.getInstance().postRequest(request);
//			}
//		});
//		Button createContact = (Button) view
//				.findViewById(R.id.btnCreateContact);
//		createContact.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				JSONObject params = new JSONObject();
//
//				try {
//					params.put("title", "新增联系纪要");
//					params.put("content", "联系内容");
//					params.put("contactDate", System.currentTimeMillis());
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				Request request = new Request(
//						RequestDefine.RQ_CUSTOMER_CONTACT_ADD, 1,
//						RequestType.POST, params, handler);
//				CoreManager.getInstance().postRequest(request);
//			}
//		});
//		Button getContact = (Button) view.findViewById(R.id.btnGetContact);
//		getContact.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Request request = new Request(
//						RequestDefine.RQ_CUSTOMER_CONTACT_GET, 1,
//						RequestType.GET, null, handler);
//				CoreManager.getInstance().postRequest(request);
//			}
//		});
//
//
//		Button updateAsset = (Button) view.findViewById(R.id.btnUpdateAsset);
//		updateAsset.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				JSONObject params = new JSONObject();
//
//				try {
//					params.put("stock", 500000);
//					params.put("fund", 1000000);
//					params.put("bond", 2000000);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				
//				Request request = new Request(
//						RequestDefine.RQ_CUSTOMER_ASSETINFO_UPDATE, 1,
//						RequestType.POST, params, handler);
//				CoreManager.getInstance().postRequest(request);
//
//				request = new Request(RequestDefine.RQ_CUSTOMER_CONTACT_GET, 1,
//						RequestType.PUT, params, handler);
//				CoreManager.getInstance().postRequest(request);
//			}
//		});
//
//		Button getAsset = (Button) view.findViewById(R.id.btnGetAsset);
//		getAsset.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Request request = new Request(
//						RequestDefine.RQ_CUSTOMER_ASSETINFO_GET, 1,
//						RequestType.GET, null, handler);
//				CoreManager.getInstance().postRequest(request);
//			}
//		});

//		Button updateIncome = (Button) view.findViewById(R.id.btnUpdateIncome);
//		updateIncome.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				JSONObject params = new JSONObject();
//
//				try {
//					params.put("income", 1500000);
//					params.put("endBonus", 12000000);
//					params.put("interest", 32000000);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				Request request = new Request(
//						RequestDefine.RQ_CUSTOMER_INCOME_UPDATE, 1,
//						RequestType.POST, params, handler);
//				CoreManager.getInstance().postRequest(request);
//				request = new Request(RequestDefine.RQ_CUSTOMER_INCOME_UPDATE,
//						1, RequestType.PUT, params, handler);
//				CoreManager.getInstance().postRequest(request);
//			}
//		});
//
//		Button getIncome = (Button) view.findViewById(R.id.btnGetIncome);
//		getIncome.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Request request = new Request(
//						RequestDefine.RQ_CUSTOMER_INCOME_GET, 1,
//						RequestType.GET, null, handler);
//				CoreManager.getInstance().postRequest(request);
//			}
//		});
//		return view;
//	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	private void init() {

	}

}