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
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;

import com.codans.blossom.datepicker.DlgDatePicker;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.activity.ActCustomerManagement;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.DateUtil;
import com.incito.utility.RegUtil;
import com.incito.wisdomsdk.net.http.AsyncHttpResponseHandler;

//新增投资人
public class PopAddInvester implements OnDateChangedListener {

	private Context context;
	private View contentView;
	private Dialog dialog;

	private EditText editBirthday;
	private Date birthday = new Date();
	private EditText name;
	private EditText phone;
	private EditText email;
	private Spinner job;
    private ImageView imgCancle;
	
	private AsyncHttpResponseHandler handlerAddCustomer = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			// Log.i(TAG, "success = " + content);
			Gson gson = new Gson();
			Customer c = gson.fromJson(content, Customer.class);
			CoreManager.getInstance().getCustomerManager().addCustomer(c, true);

			// clearAddCustomerView();
			Toast.makeText(context, "新增投资人成功", Toast.LENGTH_SHORT).show();

			if (listener != null)
				listener.doRefresh(c);
		};

		@Override
		public void onFailure(Throwable error, String content) {
			// Log.i(TAG, "onFailure = " + content);
			Toast.makeText(context, "新增投资人失败", Toast.LENGTH_SHORT).show();
		}
	};

	public PopAddInvester(Context context) {
		super();

		this.context = context;
		dialog = new Dialog(context, R.style.CustomProgressDialog);
		contentView = LayoutInflater.from(context).inflate(
				R.layout.pop_add_invester, null);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		dialog.addContentView(contentView, params);
		dialog.setCanceledOnTouchOutside(false);

		init();
	}

	private void init() {

		name = (EditText) contentView.findViewById(R.id.editName);
		final RadioGroup radioGender = (RadioGroup) contentView
				.findViewById(R.id.radioGroupGender);
		editBirthday = (EditText) contentView.findViewById(R.id.editBirthday);
		editBirthday.setKeyListener(null);
		editBirthday.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDatePickerDialog(R.string.title_dialog_birthday,
						PopAddInvester.this);
				
			}
		});
//		editBirthday.setOnFocusChangeListener(new OnFocusChangeListener() {
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if (hasFocus) {
//					showDatePickerDialog(R.string.title_dialog_birthday,
//							PopAddInvester.this);
//				}
//			}
//		});
		imgCancle = (ImageView) contentView.findViewById(R.id.imgCancle);
		imgCancle.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					Drawable ic_more;
					Resources res = context.getResources();
					ic_more = res.getDrawable(R.drawable.cancle_click);
					ic_more.setBounds(0, 0, ic_more.getMinimumWidth(), ic_more.getMinimumHeight());
					imgCancle.setImageDrawable(res.getDrawable(R.drawable.cancle_click));
				}
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					Drawable ic_more;
					Resources res = context.getResources();
					/*ic_more = res.getDrawable(R.drawable.cancle);
					ic_more.setBounds(0, 0, ic_more.getMinimumWidth(), ic_more.getMinimumHeight());*/
					imgCancle.setImageDrawable(res.getDrawable(R.drawable.cancle));
				}
				return false;
			}
		});
		
		imgCancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				// 关闭的写法
				dialog.dismiss();
			}
		});

		
		phone = (EditText) contentView.findViewById(R.id.editPhone);
		email = (EditText) contentView.findViewById(R.id.editEmail);
		job = (Spinner) contentView.findViewById(R.id.spinnerJob);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this.context, R.array.customer_job,
				R.layout.common_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		job.setAdapter(adapter);
		job.setSelection(1, true);
		Button add = (Button) contentView.findViewById(R.id.btn_add_invester);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String strName = name.getText().toString();
				String strPhone = phone.getText().toString();
				String strEmail = email.getText().toString();
				String strBirthday = editBirthday.getText().toString();
				int genderId = radioGender.getCheckedRadioButtonId();
				int gender = genderId == R.id.radioMale ? 1 : 2;
				int jobId = job.getSelectedItemPosition() +1;
				if (strName.length() == 0) {
					Toast.makeText(PopAddInvester.this.context, "姓名不能为空",
							Toast.LENGTH_LONG).show();
					return;
				}
                //bug #4561 liying 20140808
				if (strPhone.length() == 0) {
					Toast.makeText(PopAddInvester.this.context, "手机号码不能为空",
							Toast.LENGTH_LONG).show();
					return;
				}
				
				if (!RegUtil.isPhoneNumberValid(strPhone))
				{
					Toast.makeText(PopAddInvester.this.context, "手机号码不符合条件",
							Toast.LENGTH_LONG).show();
					return;
				}
					
				JSONObject params = new JSONObject();
				try {
					params.put("name", strName);
					params.put("cellPhone1", strPhone);
					params.put("email1", strEmail);
					params.put("birthday", birthday.getTime()+1);
					params.put("gender", gender);
					params.put("job", jobId);
					Calendar calendar = Calendar.getInstance();	
					
					params.put("age", calendar.get(Calendar.YEAR)-Integer.parseInt(DateUtil.formatDate2String(birthday, DateUtil.FORMAT_YYYY_MM_DD).split("-")[0]));
					
					System.out.println("YEAR"+calendar.get(Calendar.YEAR));
					System.out.println("year"+birthday.getYear());
					
					params.put("status", "A");
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
		DlgDatePicker picker = new DlgDatePicker(PopAddInvester.this.context,
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
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		if (editBirthday != null) {
			editBirthday.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				birthday = sdf.parse(editBirthday.getText().toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

	}
}
