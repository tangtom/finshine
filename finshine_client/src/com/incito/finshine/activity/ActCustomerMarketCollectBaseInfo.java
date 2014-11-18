package com.incito.finshine.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.custom.view.CommSortView;
import com.custom.view.CommSortView.RefreshSortListener;
import com.custom.view.FirstPagePop;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.AnnualIncomExpend;
import com.incito.finshine.entity.AssetInfo;
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
 * <dt>ActCustomerMarketCollectBaseInfo.java</dt>
 * <dd>Description:营销模块的采集信息</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年7月16日 下午1:11:53</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class ActCustomerMarketCollectBaseInfo extends FragmentActivity {
	private final String TAG = ActCustomerMarketCollectBaseInfo.class
			.getSimpleName();
	public static final int F_BASEINFO = 0;
	public static final int F_ASSETSINFO = 1;

	Button confirmBtn;
	Button confirmAndChooseBtn;

	private Customer customer;
	private FragmentDetail details;

	private int investNumber;
	private AnnualIncomExpend incomeInfo;
	private AssetInfo assetInfo;

	private long customerID;
	private boolean isExit;//确定是否是保存并退出

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_customer_market_collect_baseinfo);
		Intent i = getIntent();
		if (i != null && i.hasExtra(Constant.CUSTOMER_ID)) {
			customerID = i.getLongExtra(Constant.CUSTOMER_ID, -1);
		}
		getCustomerInfo();
		init();
	}

	private void init() {
		TextView title = (TextView) findViewById(R.id.textTitle);
		title.setText(R.string.title_market_collect_baseinfo);
		ImageView back = (ImageView) findViewById(R.id.imageBack);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		List<Integer> list = new ArrayList<Integer>();
		list.add(R.string.cm_base_info);
		list.add(R.string.cm_economy_info);

		CommSortView sortView = new CommSortView(this, list,
				(LinearLayout) findViewById(R.id.sortButton),
				R.string.cm_base_info);
		sortView.setRefreshSortListener(new RefreshSortListener() {

			@Override
			public void doDataSort(int id) {
				if (id == R.string.cm_base_info) {
					showDetails(F_BASEINFO);
				} else {
					if (assetInfo == null && incomeInfo == null) {
						getAsset();
					} else
						showDetails(F_ASSETSINFO);
				}
			}
		});

		confirmBtn = (Button) findViewById(R.id.confirmBtn);
		confirmBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isExit = false;
				if (details instanceof FragmentCustomBaseInfoEdit) {
					customer = ((FragmentCustomBaseInfoEdit) details)
							.saveCustomer();
					if (customer != null)
					updateCustomerInfo();
				} else {
					assetInfo = ((FragmentCustomFinancialEdit) details)
							.saveAssInfo();
					incomeInfo = ((FragmentCustomFinancialEdit) details)
							.saveIncome();
					updateAsset();
				}
			}
		});
		confirmAndChooseBtn = (Button) findViewById(R.id.confirmAndChooseBtn);
		confirmAndChooseBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isExit = true;
				if (details instanceof FragmentCustomBaseInfoEdit) {
					customer = ((FragmentCustomBaseInfoEdit) details)
							.saveCustomer();
					if (customer != null)
					updateCustomerInfo();
				} else {
					assetInfo = ((FragmentCustomFinancialEdit) details)
							.saveAssInfo();
					incomeInfo = ((FragmentCustomFinancialEdit) details)
							.saveIncome();
					updateAsset();
				}

			}
		});
		findViewById(R.id.imageNavigate).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initPullPop();
			}
		});
	}
	private void initPullPop(){
		 
		FirstPagePop firstPage = new FirstPagePop(this, findViewById(R.id.imageNavigate));
		firstPage.showPopWindow();
	}
	private boolean getCustomerInfo() {
		if (customerID == -1)
			CommonUtil.showToast("获取客户信息失败", this);
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
//				getCustomerEconomyInfo();
				showDetails(F_BASEINFO);
			else
				Toast.makeText(ActCustomerMarketCollectBaseInfo.this, "更新用户失败",
						Toast.LENGTH_SHORT).show();

		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			Toast.makeText(ActCustomerMarketCollectBaseInfo.this, "更新用户失败",
					Toast.LENGTH_SHORT).show();
		}
	};

//	private boolean getCustomerEconomyInfo() {
//		JSONObject json = new JSONObject();
//		try {
//			json.put("customerIds", customer.getId());
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
//							investNumber = customer.getInvestNumber();
//						}
//
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//
//			}
//			showDetails(F_BASEINFO);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	private boolean updateCustomerInfo() {
		Gson gson = new Gson();
		Request request;
		JSONObject json;
		try {
			json = new JSONObject(gson.toJson(customer));
			json.remove("listContactNote");
			json.remove("investTotalAsserts");
			json.remove("dividentCount");
			json.remove("salesId");// ????哪里传来的？
			json.remove("similarProductQuantity");
			json.remove("bindingStatusId");
			json.remove("contactNoteTitle");
			json.remove("prodDividendQty");

			Log.i(TAG, "更新后的用户json数据为" + json.toString());
			request = new Request(RequestDefine.RQ_CUSTOMER_UPDATE,
					customer.getId(), RequestType.PUT, json,
					handlerUpdateCustomer);

			CoreManager.getInstance().postRequest(request);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private AsyncHttpResponseHandler handlerUpdateCustomer = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			Gson gson = new Gson();
			customer = gson.fromJson(content, Customer.class);
			if (customer != null) {
				customer.setInvestNumber(investNumber);
				Toast.makeText(ActCustomerMarketCollectBaseInfo.this, "更新用户成功",
						Toast.LENGTH_SHORT).show();

				((FragmentCustomBaseInfoEdit) details).initData(customer);
			}
			if(isExit)
			{
				Intent i = new Intent();
				i.putExtra(ActProductManagement.ACTION_FROM_MARKET_CSID,customer.getId());
				i.putExtra(ActProductManagement.ACTION_INTENT_PRODUCT, 0);
				i.setClass(ActCustomerMarketCollectBaseInfo.this, ActProductManagement.class);
				ActCustomerMarketCollectBaseInfo.this.startActivity(i); 
			}
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			Toast.makeText(ActCustomerMarketCollectBaseInfo.this, "更新用户失败",
					Toast.LENGTH_SHORT).show();
		}
	};

	// 获取前两页数据
	private boolean getAsset() {
		if (customer != null) {
			Request request = new Request(
					RequestDefine.RQ_CUSTOMER_ASSETINFO_GET, customer.getId(),
					RequestType.GET, null, handlerGetAsset);
			CoreManager.getInstance().postRequest(request);
		} else
			CommonUtil.showToast("获取客户经济信息失败", this);

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

			getIncome();
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			Toast.makeText(ActCustomerMarketCollectBaseInfo.this, "获取失败",
					Toast.LENGTH_SHORT).show();
		}
	};

	private AsyncHttpResponseHandler handlerGetIncome = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			Gson gson = new Gson();
			incomeInfo = gson.fromJson(content, AnnualIncomExpend.class);
			showDetails(F_ASSETSINFO);
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			Toast.makeText(ActCustomerMarketCollectBaseInfo.this, "获取失败",
					Toast.LENGTH_SHORT).show();
		}
	};

	private void updateAsset() {
		Gson gson = new Gson();
		Request request;
		JSONObject json;
		try {
			json = new JSONObject(gson.toJson(assetInfo));

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
			json = new JSONObject(gson.toJson(incomeInfo));

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

	private AsyncHttpResponseHandler handlerUpdateAsset = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			Gson gson = new Gson();
			assetInfo = gson.fromJson(content, AssetInfo.class);

			updateIncome();
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			Toast.makeText(ActCustomerMarketCollectBaseInfo.this, "更新失败",
					Toast.LENGTH_SHORT).show();
		}
	};

	private AsyncHttpResponseHandler handlerUpdateIncome = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			Gson gson = new Gson();
			incomeInfo = gson.fromJson(content, AnnualIncomExpend.class);

			((FragmentCustomFinancialEdit) details).initDate(assetInfo);
			((FragmentCustomFinancialEdit) details).initDate(incomeInfo);
			Toast.makeText(ActCustomerMarketCollectBaseInfo.this, "更新成功",
					Toast.LENGTH_SHORT).show();
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			Toast.makeText(ActCustomerMarketCollectBaseInfo.this, "更新失败",
					Toast.LENGTH_SHORT).show();
		}
	};

	private void showDetails(int id) {
		Log.i(TAG, "showDetails = " + id);
		details = (FragmentDetail) getSupportFragmentManager()
				.findFragmentById(R.id.fragCustomMarketCollectInfo);
		switch (id) {
		case F_BASEINFO:
			details = FragmentCustomBaseInfoEdit.newInstance(id, 0, customer,1);
			break;
		case F_ASSETSINFO:
			details = FragmentCustomFinancialEdit.newInstance(id, 0, assetInfo,
					incomeInfo);
			break;

		default:
			break;
		}

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.fragCustomMarketCollectInfo, details);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}

}
