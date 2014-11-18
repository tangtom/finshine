package com.custom.view;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.entity.ContactNote;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.wisdomsdk.net.http.AsyncHttpResponseHandler;

/**
 * <dl>
 * <dt>NewContSummery.java</dt>
 * <dd>Description:联系纪要</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-5 下午12:35:14</dd>
 * </dl>
 * 
 * @author lihs
 */
public class NewContactSummery implements OnClickListener {

	private static final String TAG = "NewContactSummery";

	private Context cx;

	private Dialog dialog;

	private View addView;

	private EditText editTitle;
	private EditText editContext;

	private Customer customer;

	private AsyncHttpResponseHandler handlerAddCustomerContact = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			Gson gson = new Gson();
			ContactNote note = gson.fromJson(content, ContactNote.class);
			customer.addContactNote(note);
			customer.setContactNoteTitle(note.getTitle());
			Log.i(TAG, customer.getContactNoteTitle());
			CoreManager.getInstance().getCustomerManager().updateCustomer(customer);
			CommonUtil.showToast("新增联系纪要成功", cx);
			if (listener != null) {
				listener.doRefresh(customer);
			}
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			CommonUtil.showToast("新增联系纪要失败", cx);
		}
	};

	public NewContactSummery(Context cx, Customer cs) {

		super();
		this.cx = cx;
		this.customer = cs;
		dialog = new Dialog(cx, R.style.CustomProgressDialog);
		addView = LayoutInflater.from(cx).inflate(R.layout.new_contact_summery,
				null);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		dialog.addContentView(addView, params);
		dialog.setCanceledOnTouchOutside(false);

		init();
	}

	private void init() {

		String date = DateUtil.formatDate2String(new Date(),
				DateUtil.FORMAT_YYYY_MM_DD_HHMM_WORD_ZH);
		TextView tvYMD = (TextView) addView.findViewById(R.id.tv_date);
		TextView tvhs = (TextView) addView.findViewById(R.id.et_mmss);
		if (!TextUtils.isEmpty(date)) {
			tvYMD.setText(date.substring(0, 11));
			tvhs.setText(date.substring(11, 16));
		}

		editTitle = (EditText) addView.findViewById(R.id.et_title);
		editContext = (EditText) addView.findViewById(R.id.et_content);
		addView.findViewById(R.id.btn_save_contact_summery).setOnClickListener(
				this);
		addView.findViewById(R.id.btn_cancle_contact_summery)
				.setOnClickListener(this);

	}

	private void addContact() {

		JSONObject params = new JSONObject();
		try {

			params.put("title", editTitle.getText().toString());
			// params.put("content",
			// URLEncoder.encode(editContext.getText().toString(),
			// "UTF-8"));//????编码格式
			params.put("content", editContext.getText().toString());//
			params.put("contactDate", System.currentTimeMillis());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Request request = new Request(RequestDefine.RQ_CUSTOMER_CONTACT_ADD,
				customer.getId(), RequestType.POST, params,
				handlerAddCustomerContact);
		CoreManager.getInstance().postRequest(request);
		closeDialog();

	}

	public void showDialog() {
		if (dialog != null) {
			dialog.show();
		}
	}

	public void closeDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
			dialog = null;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save_contact_summery:
			if (TextUtils.isEmpty(editTitle.getText().toString().trim())) {
				CommonUtil.showToast("请填写纪要标题", cx);
				return;
			}
			if (TextUtils.isEmpty(editContext.getText().toString().trim())) {
				CommonUtil.showToast("请填写纪要内容", cx);
				return;
			}
			addContact();
			break;
		case R.id.btn_cancle_contact_summery:
			closeDialog();
			break;

		default:
			break;
		}
	}

	private RefreshContactSuListener listener = null;

	public void setRefreshContactSuListener(RefreshContactSuListener listener) {
		this.listener = listener;
	}

	public interface RefreshContactSuListener {
		public void doRefresh(Customer cs);
	}

}
