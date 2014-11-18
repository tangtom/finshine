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

public class FragmentHome extends FragmentDetail {

	private static final String TAG = FragmentHome.class.getSimpleName();
	private TextView msg;

	
	
	private AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			if(msg!=null)
			{
				msg.setText(content);
			}
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			if(msg!=null)
			{
				msg.setText(content);
			}
		}
	};
	

	public static FragmentHome newInstance(int id) {

		FragmentHome f = new FragmentHome();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		View view = inflater.inflate(R.layout.test_home, container, false);
		msg = (TextView)view.findViewById(R.id.textMessage);
		
		Button create = (Button)view.findViewById(R.id.btnCreateTodo);
		create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				JSONObject params = new JSONObject();
				try {
					params.put("customerId", 1);
					params.put("title", "约见张总");
					params.put("content","上海浦东");
					params.put("startTime", System.currentTimeMillis());
					params.put("endTime", System.currentTimeMillis()+30*60*1000);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				

				Request request = new Request(RequestDefine.RQ_TODO_ADD,
						RequestType.POST, params, handler);
				CoreManager.getInstance().postRequest(request);
			}
		});
		Button getTodoList = (Button)view.findViewById(R.id.btnGetTodoList);
		getTodoList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Request request = new Request(RequestDefine.RQ_TODO_GET_LIST,
						RequestType.GET, null, handler);
				CoreManager.getInstance().postRequest(request);
			}
		});
		
		Button getSingleTodo = (Button)view.findViewById(R.id.btnGetSingleTodo);
		getSingleTodo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Request request = new Request(RequestDefine.RQ_TODO_GET_SINGLE,1,
						RequestType.GET, null, handler);
				CoreManager.getInstance().postRequest(request);
			}
		});
		Button update = (Button)view.findViewById(R.id.btnUpdateTodo);
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				JSONObject params = new JSONObject();
				try {
					params.put("customerId", 1);
					params.put("title", "刷新");
					params.put("content","上海浦东");
					params.put("startTime", System.currentTimeMillis());
					params.put("endTime", System.currentTimeMillis()+30*60*1000);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Request request = new Request(RequestDefine.RQ_TODO_UPDATE,1,
						RequestType.PUT, params, handler);
				CoreManager.getInstance().postRequest(request);
			}
		});
		Button delete = (Button)view.findViewById(R.id.btnDeleteTodo);
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Request request = new Request(RequestDefine.RQ_TODO_DELETE,2,
						RequestType.DELETE, null, handler);
				CoreManager.getInstance().postRequest(request);
			}
		});
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	private void init() {

	}

}