package com.custom.view;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;

import com.codans.blossom.datepicker.DlgDatePicker;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.activity.ActCustomerManagement;
import com.incito.finshine.activity.ActProductManagement;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.MarKetCustomer;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.DateUtil;
import com.incito.utility.RegUtil;
import com.incito.wisdomsdk.net.http.AsyncHttpResponseHandler;

//新增投资人
public class PopAddMarketCustomer implements OnDateChangedListener {

	private Context context;
	private View contentView;
	private Dialog dialog;
    
	private EditText customerName;
    private Button btnEnsure;

	
	private AsyncHttpResponseHandler handlerAddCustomer = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			// Log.i(TAG, "success = " + content);
			Gson gson = new Gson();
			Customer c = gson.fromJson(content, Customer.class);
			CoreManager.getInstance().getCustomerManager().addCustomer(c, true);

			// clearAddCustomerView();
			Toast.makeText(context, "新增客户成功", Toast.LENGTH_SHORT).show();
			Intent i = new Intent();
			i.putExtra(ActProductManagement.ACTION_FROM_MARKET_CSID,c.getId());
			i.putExtra(ActProductManagement.ACTION_INTENT_PRODUCT, 0);
			i.setClass(context, ActProductManagement.class);
			context.startActivity(i);
			if (listener != null)
				listener.doRefresh(c);
		};

		@Override
		public void onFailure(Throwable error, String content) {
			// Log.i(TAG, "onFailure = " + content);
			Toast.makeText(context, "新增客户失败", Toast.LENGTH_SHORT).show();
		}
	};

	public PopAddMarketCustomer(Context context) {
		super();

		this.context = context;
		dialog = new Dialog(context, R.style.CustomProgressDialog);
		contentView = LayoutInflater.from(context).inflate(
				R.layout.pop_add_market_customer, null);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		dialog.addContentView(contentView, params);
		dialog.setCanceledOnTouchOutside(true);
		init();
	}

	private void init() {
		btnEnsure = (Button) contentView.findViewById(R.id.btn_ensure);
		customerName = (EditText) contentView.findViewById(R.id.editCustomName);
		btnEnsure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String strName = customerName.getText().toString();
				if(strName != null){
				if (strName.length() == 0) {
					Toast.makeText(PopAddMarketCustomer.this.context, "姓名不能为空",
							Toast.LENGTH_LONG).show();
					return;
				}else if (strName.length() >= 15) {
					Toast.makeText(PopAddMarketCustomer.this.context, "姓名不能超过15个字",
							Toast.LENGTH_LONG).show();
					return;
				}}
				else{
					Toast.makeText(PopAddMarketCustomer.this.context, "请输入新增客户姓名",
							Toast.LENGTH_LONG).show();
				}	
				JSONObject params = new JSONObject();
				try {
					params.put("name", strName);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				// params.put("cellPhone1", strPhone);
				// catch (UnsupportedEncodingException e) {
				//
				// e.printStackTrace();
				// }

				Request request = new Request(RequestDefine.RQ_CUSTOMER_ADD,
						RequestType.POST, params, handlerAddCustomer);
				CoreManager.getInstance().postRequest(request);

				/*
				 * if (email.length() == 0 || !Utility.isEmail(email)) {
				 * 
				 * return; }
				 */
				if (dialog != null) {
					dialog.dismiss();
					dialog = null;
				}
			}
		});
	}

	public void showDatePickerDialog(int title, OnDateChangedListener listener) {
		DlgDatePicker picker = new DlgDatePicker(PopAddMarketCustomer.this.context,
				title, listener);
		picker.show();
	}

	public void showAddInvestDlg() {
		if (dialog != null && !dialog.isShowing()) {
			dialog.show();
		}
	}

	private RefreshCustomerListener listener = null;

	public void setListener(RefreshCustomerListener listener) {
		this.listener = listener;
	}

	public interface RefreshCustomerListener {
		public void doRefresh(final Customer c);
	}

	@Override
	public void onDateChanged(DatePicker arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

}
