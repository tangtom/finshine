package com.incito.finshine.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.custom.view.NewContactSummery;
import com.custom.view.NewContactSummery.RefreshContactSuListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterContactNote;
import com.incito.finshine.entity.ContactNote;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.wisdomsdk.net.http.AsyncHttpResponseHandler;

/**
 * <dl>
 * <dt>FraContactSummery.java</dt>
 * <dd>Description:客户管理 的 联系概要</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-5 上午9:55:18</dd>
 * </dl>
 * 
 * @author lihs
 */
public class FragmentContactSummery extends FragmentDetail {

	private View contactView = null;

	private Customer customer;
	private static final String TAG = "FraContactSummery";

	private ListView listContactNote;
	private AdapterContactNote adapterContactNote;

	public static FragmentContactSummery newInstance(int id) {

		FragmentContactSummery f = new FragmentContactSummery();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		contactView = (View) inflater.inflate(R.layout.frg_contaxt_summery,
				null);
		customer = ((ActCustomerDetail) getActivity()).getCustomer();
		getContactNote();
		return contactView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		contactView.findViewById(R.id.btn_add_contact_summery)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						// if (customer == null) {
						// customer = new Customer();
						// customer.setId(2l);
						// }
						// 弹出新增联系概要
						NewContactSummery ncs = new NewContactSummery(
								FragmentContactSummery.this.getActivity(),
								customer);
						ncs.setRefreshContactSuListener(new RefreshContactSuListener() {

							@Override
							public void doRefresh(Customer cs) {

								// CommonUtil.showToast("新增联系概要",
								// FraContactSummery.this.getActivity());

								if (adapterContactNote != null) {
									adapterContactNote.setItemList(customer
											.getListContactNote());
								}

							}
						});
						ncs.showDialog();
					}
				});

		listContactNote = (ListView) (getActivity()
				.findViewById(R.id.listview_contact_summery));
		adapterContactNote = new AdapterContactNote(getActivity());
		// List<ContactNote> infos = new ArrayList<ContactNote>();
		// infos.add(new ContactNote());
		// infos.add(new ContactNote());
		// infos.add(new ContactNote());
		// infos.add(new ContactNote());
		// infos.add(new ContactNote());
		// infos.add(new ContactNote());
		// infos.add(new ContactNote());
		// adapterContactNote.setItemList(infos);
		listContactNote.setAdapter(adapterContactNote);
		int count = listContactNote.getChildCount();
	}
	

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();

		// getContactNote();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	private void getContactNote() {

		// if (customer == null) {
		// customer = new Customer();
		// customer.setId(2l);
		// }
		Request request = new Request(RequestDefine.RQ_CUSTOMER_CONTACT_GET,
				customer.getId(), RequestType.GET, null,
				handlerGetCustomerContact);
		CoreManager.getInstance().postRequest(request);
	}

	private AsyncHttpResponseHandler handlerGetCustomerContact = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			Gson gson = new Gson();
			List<ContactNote> list = gson.fromJson(content,
					new TypeToken<List<ContactNote>>() {
					}.getType());
			customer.setListContactNote(list);
			adapterContactNote.setItemList(list);
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
		}
	};

}
