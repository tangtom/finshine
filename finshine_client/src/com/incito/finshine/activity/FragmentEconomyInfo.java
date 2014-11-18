package com.incito.finshine.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.custom.view.CommonWaitDialog;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.entity.AnnualIncomExpend;
import com.incito.finshine.entity.AssetInfo;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.RequestDefine;
import com.incito.finshine.network.Request.RequestType;
import com.incito.wisdomsdk.net.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 
 * <dl>
 * <dt>FragmentEconomyInfo.java</dt>
 * <dd>Description:客户管理界面中经济状况界面中各个碎片之间的切换</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年5月14日 下午1:56:40</dd>
 * </dl>
 * 
 * @author LiNing
 */

public class FragmentEconomyInfo extends FragmentDetail implements
		OnClickListener {
	private static final String TAG = FragmentEconomyInfo.class.getSimpleName();

	public static final int F_ECONOMY_INFO = 0;
	public static final int F_ECONOMY_INFO_EDIT = 1;

	private Button btnEdit;
	private Button btnConfirm;
	private Button btnCancel;
	private LinearLayout btnBars;

	FragmentDetail details;
	View v;

	private Customer customer;
	private AssetInfo assetInfo;
	private AnnualIncomExpend incomeInfo;

	private AssetInfo curAsset;
	private AnnualIncomExpend curIncome;

	private CommonWaitDialog waitDlg = null;

	public static FragmentEconomyInfo newInstance(int id) {

		FragmentEconomyInfo f = new FragmentEconomyInfo();
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
		v = inflater.inflate(R.layout.frag_base_info, container, false);// frag_economy_info

		customer = ((ActCustomerDetail) getActivity()).getCustomer();
		getAsset();
		initButton();
		return v;
	}

	// 获取前两页数据
	private boolean getAsset() {
		waitDlg = new CommonWaitDialog(this.getActivity(), "",
				R.string.load_data);

		Request request = new Request(RequestDefine.RQ_CUSTOMER_ASSETINFO_GET,
				customer.getId(), RequestType.GET, null, handlerGetAsset);
		CoreManager.getInstance().postRequest(request);
		return true;
	}

	// 获取后两页数据
	private boolean getIncome() {
		Request request = new Request(RequestDefine.RQ_CUSTOMER_INCOME_GET,
				customer.getId(), RequestType.GET, null, handlerGetIncome);
		CoreManager.getInstance().postRequest(request);
		return true;
	}

	private AsyncHttpResponseHandler handlerGetAsset = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			Gson gson = new Gson();
			assetInfo = gson.fromJson(content, AssetInfo.class);
			// CoreManager.getInstance().getCustomerManager().addCustomer(c);

			getIncome();
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			closeDialog();
			Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
		}
	};

	private AsyncHttpResponseHandler handlerGetIncome = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			Gson gson = new Gson();
			incomeInfo = gson.fromJson(content, AnnualIncomExpend.class);
			// CoreManager.getInstance().getCustomerManager().addCustomer(c);
			closeDialog();
			showDetails(F_ECONOMY_INFO, 0, assetInfo, incomeInfo);
			Toast.makeText(getActivity(), "获取成功", Toast.LENGTH_SHORT).show();
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			closeDialog();
			Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
		}
	};

	private AsyncHttpResponseHandler handlerUpdateAsset = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			Gson gson = new Gson();
			assetInfo = gson.fromJson(content, AssetInfo.class);
			// CoreManager.getInstance().getCustomerManager().addCustomer(c);

			updateIncome();
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			closeDialog();
			Toast.makeText(getActivity(), "更新失败", Toast.LENGTH_SHORT).show();
		}
	};

	private AsyncHttpResponseHandler handlerUpdateIncome = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			Gson gson = new Gson();
			incomeInfo = gson.fromJson(content, AnnualIncomExpend.class);
			// CoreManager.getInstance().getCustomerManager().addCustomer(c);
			// closeDialog();
			// showDetails(F_ECONOMY_INFO, 0, assetInfo, incomeInfo);

			boolean ret = details instanceof FragmentCustomFinancialInfo;
			if (ret) {
				((FragmentCustomFinancialInfo) details).initView1(assetInfo);
				((FragmentCustomFinancialInfo) details)
						.initView23(assetInfo);
				((FragmentCustomFinancialInfo) details)
						.initView23(incomeInfo);
				((FragmentCustomFinancialInfo) details).initView4(incomeInfo);
			}
			Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			closeDialog();
			Toast.makeText(getActivity(), "更新失败", Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onStart() {
		super.onStart();
	}

	private void initButton() {

		btnBars = (LinearLayout) v.findViewById(R.id.buttonBarsLinear);

		btnEdit = (Button) v.findViewById(R.id.btnEdit);
		btnEdit.setOnClickListener(this);

		btnConfirm = (Button) v.findViewById(R.id.btnConfirm);
		btnConfirm.setOnClickListener(this);

		btnCancel = (Button) v.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);

	}

	public void showDetails(int id, int position, AssetInfo assetInfo,
			AnnualIncomExpend incomeInfo) {
		Log.i(TAG, "showDetails = " + id);

		details = (FragmentDetail) getFragmentManager()// getActivity().getSupportFragmentManager()
				.findFragmentById(R.id.customBaseInfoDetails);

		// if (details == null || !(details.getFragmentId() == id)) {
		// Make new fragment to show this selection.
		switch (id) {
		case F_ECONOMY_INFO:
			details = FragmentCustomFinancialInfo.newInstance(id, position,
					assetInfo, incomeInfo);
			break;

		case F_ECONOMY_INFO_EDIT:
			details = FragmentCustomFinancialEdit.newInstance(id, position,
					assetInfo, incomeInfo);
			break;

		default:
			break;
		}
		// Execute a transaction, replacing any existing fragment
		// with this one inside the frame.
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.customBaseInfoDetails, details);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
		// }

	}

	@Override
	public void onClick(View v) {
		details = (FragmentDetail) getFragmentManager().findFragmentById(
				R.id.customBaseInfoDetails);
		int currentView = 0;
		if (details instanceof FragmentCustomFinancialInfo) {
			currentView = ((FragmentCustomFinancialInfo) details).getNowView();
		} else {
			currentView = ((FragmentCustomFinancialEdit) details).getNowView();
		}
		switch (v.getId()) {
		case R.id.btnEdit:
			showDetails(F_ECONOMY_INFO_EDIT, currentView, assetInfo, incomeInfo);
			btnEdit.setVisibility(View.GONE);
			btnBars.setVisibility(View.VISIBLE);
			break;
		case R.id.btnConfirm:
			curAsset = ((FragmentCustomFinancialEdit) details).saveAssInfo();
			curIncome = ((FragmentCustomFinancialEdit) details).saveIncome();
			showDetails(F_ECONOMY_INFO, currentView, assetInfo, incomeInfo);
			btnEdit.setVisibility(View.VISIBLE);
			btnBars.setVisibility(View.GONE);
			updateAsset();
			break;
		case R.id.btnCancel:
			showDetails(F_ECONOMY_INFO, currentView, assetInfo, incomeInfo);
			btnEdit.setVisibility(View.VISIBLE);
			btnBars.setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}

	private void updateAsset() {
		Gson gson = new Gson();
		Request request;
		JSONObject json;
		try {
			json = new JSONObject(gson.toJson(curAsset));

			Log.i(TAG, "更新后的用户经济json数据为" + json.toString());
			request = new Request(RequestDefine.RQ_CUSTOMER_ASSETINFO_UPDATE,
					customer.getId(), RequestType.POST, json,
					handlerUpdateAsset);
			CoreManager.getInstance().postRequest(request);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateIncome() {
		Gson gson = new Gson();
		Request request;
		JSONObject json;
		try {
			json = new JSONObject(gson.toJson(curIncome));

			Log.i(TAG, "更新后的用户经济json数据为" + json.toString());
			request = new Request(RequestDefine.RQ_CUSTOMER_INCOME_UPDATE,
					customer.getId(), RequestType.POST, json,
					handlerUpdateIncome);
			CoreManager.getInstance().postRequest(request);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void closeDialog() {

		if (waitDlg != null) {
			waitDlg.clearAnimation();
		}
	}
}