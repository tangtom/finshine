package com.incito.finshine.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.codans.blossom.datepicker.DlgDatePicker;
import com.custom.view.CommSortView;
import com.custom.view.CommSortView.RefreshSortListener;
import com.custom.view.CommonWaitDialog;
import com.custom.view.DeviceLinearLayout;
import com.custom.view.DlgCitySelected;
import com.custom.view.DlgCommFilter;
import com.custom.view.DlgCommFilter.RefreshFilterListener;
import com.custom.view.FirstPagePop;
import com.custom.view.PopAddInvester;
import com.custom.view.PopCsInvestDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterCustomerDetail;
import com.incito.finshine.common.IntentDefine;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.FilterUtil;
import com.incito.utility.RegUtil;
import com.incito.utility.StringUtil;
import com.incito.wisdomsdk.net.http.BinaryHttpResponseHandler;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

/**
 * <dl>
 * <dt>ActCustomerManagement.java</dt>
 * <dd>Description:客户管理首页</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月13日 下午5:43:43</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class ActCustomerManagement extends Activity implements
		OnDateChangedListener, OnItemSelectedListener {
	private final String TAG = ActCustomerManagement.class.getSimpleName();
	ListView listCustomer;
	AdapterCustomerDetail adapterCustomerDetail;
	private CheckBox selectAllCustomer;

	private LinearLayout layFilter;
	private List<Customer> list;
	private CommonWaitDialog waitDlg = null;
	// private Button cityBtn;
	private Spinner spinnerCustom;
	private Spinner spinnerEffectiveness;
	private Spinner spinnerSaleChance;
	private Spinner spinnerPublisher;

	private Button cityBtn;
	private Button btnInvestment1;

	private Button btnDeadline;
	private Button btnProfit;
	private Button btnDividend;
	private Map<Integer,String[]> map;//add by SGDY for bug#3783
	private EditText etCustomerSearch;
	private ImageView search_icon;
	private Context context;
	private Button reset;
	private Customer focusCustomer = null;
	private List<Bitmap> listBitMap;//存放客户图像

	// private StringBuffer customerId = new StringBuffer();

	private JsonHttpResponseHandler handlerGetCustomer = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.i(TAG, "success o= " + response.toString());

			int statues = response.optInt("status");
			if (0 == statues) {
				JSONArray arr = response.optJSONArray("list");
				Log.i(TAG, "success a= " + response.toString());
				Gson gson = new Gson();
				list = gson.fromJson(arr.toString(),
						new TypeToken<List<Customer>>() {
						}.getType());

				closeDialog();
				if (list.size() == 0) {
					CommonUtil.showToast("暂时还没有符合条件的客户",
							ActCustomerManagement.this);
				} else {
					CoreManager.getInstance().getCustomerManager()
							.setListCustomer(list);
				}
				// else {
				// for (int i = 0; i < list.size(); i++) {
				// Customer c = list.get(i);
				// if (i == list.size() - 1)
				// customerId.append(String.valueOf(c.getId()));
				// else
				// customerId.append(String.valueOf(c.getId()) + ",");
				// System.out.println(c.getName());
				// }
				// Log.i("拼接的customerID=", customerId.toString());
				//
				// // getCustomerEconomyInfoList();
				// }

				if (adapterCustomerDetail != null) {
					adapterCustomerDetail.setItemList(list);
					adapterCustomerDetail.sortCustomer(R.string.total_asset);// 默认排序

					if (focusCustomer != null) {
						Log.e(TAG, "focus = " + focusCustomer.getId());
						int pos = -1;
						for (int index = 0; index < list.size(); index++) {
							if (list.get(index).getId() == focusCustomer
									.getId()) {
								pos = index;
								Log.e(TAG, "focus pos= " + pos);
								break;
							}
						}
						if (pos > 0) {
							listCustomer.smoothScrollToPosition(pos);
							Log.e(TAG, "scroll to " + pos);
						}
						focusCustomer = null;
					}
				}

			}
			for(Customer c : list)
			{
				if(c != null)
				{
//					ActCustomerManagement.this.sendMessage(c);
				}
			}
		}

		@Override
		public void onSuccess(JSONArray response) {
			closeDialog();
			Log.i(TAG, "success a= " + response.toString());
			Gson gson = new Gson();
			list = gson.fromJson(response.toString(),
					new TypeToken<List<Customer>>() {
					}.getType());
			for (int i = 0; i < list.size(); i++) {
				Customer c = list.get(i);
				System.out.println(c.getName());
			}
			CoreManager.getInstance().getCustomerManager()
					.setListCustomer(list);
			// adapterCustomerDetail = new
			// AdapterCustomerDetail(ActCustomerManagement.this,selectAllCustomer);
			if (adapterCustomerDetail != null) {
				adapterCustomerDetail.setItemList(list);
				adapterCustomerDetail.sortCustomer(R.string.total_asset);// 默认排序
				Log.e(TAG, "refresh list ");

			}

			// listCustomer.setAdapter(adapterCustomerDetail);
		}

		@Override
		public void onFailure(Throwable error, String content) {
			closeDialog();
			Log.i(TAG, "onFailure = " + content);
		}
	};

	// 第二次获取客户的经济信息 包括：投资总数、投资总额（没用）、分红总数
	// private JsonHttpResponseHandler handlerGetCustomerEconumy = new
	// JsonHttpResponseHandler() {
	// @Override
	// public void onSuccess(JSONObject response) {
	// Log.i(TAG, "success o= " + response.toString());
	//
	// int statues = response.optInt("status");
	// if (0 == statues) {
	//
	// JSONArray arr = response.optJSONArray("list");
	//
	// for (int i = 0; i < arr.length(); i++) {// 遍历JSONArray
	//
	// try {
	// JSONObject oj = arr.getJSONObject(i);
	// if (oj.has("id")) {
	// for (Customer c : list) {
	// if (oj.optLong("id") == c.getId()) {
	// c.setInvestNumber(oj
	// .optInt("purchasedQuantity"));
	// c.setInvestTotalAsserts(oj
	// .optInt("purchasedTotalAmount"));
	// c.setDividentCount(oj
	// .optInt("prodDividendQty"));
	//
	// }
	// }
	// }
	//
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// CoreManager.getInstance().getCustomerManager()
	// .setListCustomer(list);
	//
	// if (adapterCustomerDetail != null) {
	// adapterCustomerDetail.setItemList(list);
	// adapterCustomerDetail.sortCustomer(R.string.total_asset);// 默认排序
	// }
	// closeDialog();
	// customerId.delete(0, customerId.length() - 1);
	// }
	// }
	//
	// @Override
	// public void onSuccess(JSONArray response) {
	// closeDialog();
	// Log.i(TAG, "success a= " + response.toString());
	// }
	//
	// @Override
	// public void onFailure(Throwable error, String content) {
	// closeDialog();
	// customerId.delete(0, customerId.length() - 1);
	// Log.i(TAG, "onFailure = " + content);
	// }
	// };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_customer_management);
		init();
		this.context = context;
		// JSONObject json = new JSONObject();
		// try {
		// json.put("salesId", 1);
		// json.put("expirationDays", 14);
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		getCustomerBaseInfoList();

		findViewById(R.id.imageNavigate).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						initPullPop();
					}
				});
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	private void initPullPop() {

		FirstPagePop firstPage = new FirstPagePop(this,
				findViewById(R.id.imageNavigate));
		firstPage.setPosition(1);
		firstPage.showPopWindow();
	}

	@Override
	public void onStart() {
		super.onStart();
	}
	
	// add by SGDY at 2014/8/13 19:40
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK && event.getRepeatCount() == 0)//按下的如果是BACK，同时没有重复
		{
			ActFinshineHomePage.backHome();
			startActivity(new Intent(this,ActFinshineHomePage.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	private boolean getCustomerBaseInfoList() {

		String[] effective = { "", "A", "D" };
		String[] investValue = getResources().getStringArray(
				R.array.investment2_status);
		JSONObject filterJson = new JSONObject();
		try {
			filterJson.put("salesId",
					SPManager.getInstance().getLongValue(SPManager.ID));
			filterJson.put("customerType",
					spinnerCustom.getSelectedItemPosition());
			filterJson.put("status",
					effective[spinnerEffectiveness.getSelectedItemPosition()]);
			filterJson.put("salesOpp",
					spinnerSaleChance.getSelectedItemPosition());
			filterJson.put("city",
					cityBtn.getText().toString().equals("全部") ? "" : cityBtn
							.getText().toString());
			filterJson.put(
					"prodPreference",
					spinnerPublisher.getSelectedItemPosition() == 0 ? ""
							: getResources().getStringArray(
									R.array.publisher_status)[spinnerPublisher
									.getSelectedItemPosition()]);

			filterJson.put("periodPreferencesLow",
					FilterUtil.getSelectedBtnValue(btnDeadline.getText()
							.toString(), true));
			filterJson.put("periodPreferencesHigh", FilterUtil
					.getSelectedBtnValue(btnDeadline.getText().toString(),
							false));

			filterJson.put("revenuePreferencesLow", FilterUtil
					.getPercentBtnValue(btnProfit.getText().toString(), true));
			filterJson.put("revenuePreferencesHigh", FilterUtil
					.getPercentBtnValue(btnProfit.getText().toString(), false));

			filterJson.put("bonusPreferencesLow",
					FilterUtil.getSelectedBtnValue(btnDividend.getText()
							.toString(), true));
			filterJson.put("bonusPreferencesHigh", FilterUtil
					.getSelectedBtnValue(btnDividend.getText().toString(),
							false));

			filterJson.put("prodFirstType", FilterUtil.getInvestmentValue(
					btnInvestment1.getText().toString(), true, investValue));
			filterJson.put("prodSecondtype", FilterUtil.getInvestmentValue(
					btnInvestment1.getText().toString(), false, investValue));

			filterJson.put("expirationDays", 14);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.i("筛选的客户管理的参数是", filterJson.toString());
		waitDlg = new CommonWaitDialog(this, "", R.string.load_data);

		Request request = new Request(RequestDefine.RQ_CUSTOMER_GET_LIST,
				RequestType.POST, filterJson, handlerGetCustomer);
		// Request request = new Request(RequestDefine.RQ_CUSTOMER_GET_LIST,
		// RequestType.GET, null, handlerGetCustomer);
		CoreManager.getInstance().postRequest(request);
		return true;
	}

	// private boolean getCustomerEconomyInfoList() {
	// JSONObject json = new JSONObject();
	// try {
	// json.put("customerIds", customerId);
	// json.put("expirationDays", 14);
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	//
	// Request request = new Request(
	// RequestDefine.RQ_CUSTOMER_POST_ECONOMY_LIST, RequestType.POST,
	// json, handlerGetCustomerEconumy);
	// CoreManager.getInstance().postRequest(request);
	//
	// return true;
	// }

	private boolean init() {
		
		listBitMap = new ArrayList<Bitmap>();
		customerSort();
		map = new HashMap<Integer,String[]>();
		TextView title = (TextView) findViewById(R.id.textTitle);
		title.setText(R.string.title_customer_management);
		ImageView back = (ImageView) findViewById(R.id.imageBack);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				ActFinshineHomePage.backHome();
				startActivity(new Intent(ActCustomerManagement.this,ActFinshineHomePage.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
				finish();
				
			}
		});

		// 隐藏的筛选条件
		layFilter = (LinearLayout) findViewById(R.id.filter);
		Button btnFilter = (Button) findViewById(R.id.btnFilter);
		btnFilter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (layFilter.isShown()) {
					layFilter.setVisibility(View.GONE);
				} else
					layFilter.setVisibility(View.VISIBLE);
			}
		});

		// 显示增加联系人 Dialog
		ImageView img_add_invest = (ImageView) findViewById(R.id.img_add_invest);
		img_add_invest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				PopAddInvester popAddView = new PopAddInvester(
						ActCustomerManagement.this);
				popAddView
						.setListener(new PopAddInvester.RefreshCustomerListener() {

							@Override
							public void doRefresh(final Customer c) {
								reset.performClick();
								getCustomerBaseInfoList();
								focusCustomer = c;
								
							}
						});
				popAddView.showAddInvestDlg();
			}
		});

		// 显示理财师投资信息PopupWindow
		ImageView img_data_analise = (ImageView) findViewById(R.id.img_data_analise);
		img_data_analise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				PopCsInvestDetail popDataAnalise = new PopCsInvestDetail(
						ActCustomerManagement.this, v, null,
						R.drawable.bg_cs_data_analyise,
						PopCsInvestDetail.TOP_2_BOTTOM);
				popDataAnalise.showPopWindow();
			}
		});

		// 搜索客户
		etCustomerSearch = (EditText) findViewById(R.id.et_search_text);
		// etCustomerSearch.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// etCustomerSearch.setText("");
		// }
		// });
		search_icon = (ImageView) findViewById(R.id.search_icon);
		search_icon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (search_icon.getDrawable().getCurrent().getConstantState() == getResources()
						.getDrawable(R.drawable.delete).getConstantState()) {
					etCustomerSearch.setText("");
				}
			}
		});
		etCustomerSearch.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
//				if (count != 0) {
//					search_icon.setImageDrawable(getResources().getDrawable(
//							R.drawable.delete));
//				} else {
//					search_icon.setImageDrawable(getResources().getDrawable(
//							R.drawable.product_search_icon));
//				}
				if(s.length() != 0){
					search_icon.setImageDrawable(getResources().getDrawable(R.drawable.delete));
				}else{
					search_icon.setImageDrawable(getResources().getDrawable(R.drawable.product_search_icon));
				}
				if (adapterCustomerDetail != null) {
					adapterCustomerDetail.getFilter().filter(s.toString());
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		});

		// 全选
		selectAllCustomer = (CheckBox) findViewById(R.id.checkSelectAll);
		selectAllCustomer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adapterCustomerDetail == null) {
					return;
				}
				adapterCustomerDetail.isSeletAllCustomer(selectAllCustomer
						.isChecked());
			}
		});

		// 设为有效
		Button btnEnable = (Button) findViewById(R.id.buttonEnable);
		btnEnable.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adapterCustomerDetail.isCustomerSecleted()) {
					adapterCustomerDetail.setEnabled("A");
					recreate();
				} else {
					return;
				}
			}
		});

		// 设为无效
		Button btnDisable = (Button) findViewById(R.id.buttonDisable);
		btnDisable.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adapterCustomerDetail.isCustomerSecleted()) {
					adapterCustomerDetail.setEnabled("D");
					recreate();
				} else {
					return;
				}
			}
 		});

		// 群发短信
		Button btnSms = (Button) findViewById(R.id.buttonSms);
		btnSms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				StringBuffer targetPhone = new StringBuffer();
				List<Customer> list = adapterCustomerDetail
						.getSeletCustomerData();
				for (Customer customer : list) {
					targetPhone.append(customer.getCellPhone1() + ",");
				}
				if (TextUtils.isEmpty(targetPhone.toString())) {
					CommonUtil
							.showToast("短信号码不能为空", ActCustomerManagement.this);
					return;
				}
				CommonUtil.sendSms(ActCustomerManagement.this,
						targetPhone.toString());

			}
		});

		// 群发邮件
		Button btnMail = (Button) findViewById(R.id.buttonEmail); 
		btnMail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (list == null) {
					CommonUtil.showToast("邮件号码为空", ActCustomerManagement.this);
					return;
				}
				String[] targetTempEmail = new String[list.size()];
				List<Customer> listS = adapterCustomerDetail
						.getSeletCustomerData();
				List<String>lists=new ArrayList<String>();
				for (int i = 0; i < listS.size(); i++) {
					if(!listS.get(i).getEmail1().equalsIgnoreCase("null")&&!StringUtil.isEmpty(listS.get(i).getEmail1())&&RegUtil.isEmailNumberValid(listS.get(i).getEmail1())){
						lists.add(listS.get(i).getEmail1()) ;
					}
					
				}
				int maxLenth=0;
				String tempString="";
				if (lists.size() == 0) {
					CommonUtil.showToast("邮件号码为空", ActCustomerManagement.this);
					return;
				}
				else {
					 
		 
					String[]emailsStrings=(String[])lists.toArray(new String[lists.size()]);
					if(emailsStrings.length==0){
						CommonUtil.showToast("邮件号码为空", ActCustomerManagement.this);
						return;
					}
					CommonUtil.sendEmail(ActCustomerManagement.this,emailsStrings ,"", "");
				}
				//String[] targetEmail = new String[maxLenth];
				
			}
		});

		listCustomer = (ListView) findViewById(R.id.listCustomerDetail);
		TextView tvSeleCount = (TextView)findViewById(R.id.tv_selected_customer);
		adapterCustomerDetail = new AdapterCustomerDetail(
				ActCustomerManagement.this, selectAllCustomer,tvSeleCount);
		ArrayList<Customer> infos = new ArrayList<Customer>();
		// Customer cs = null;
		// for (int i = 0; i < 5; i++) {
		// cs = new Customer();
		// cs.setId(i+1l);
		// infos.add(cs);
		// }

		adapterCustomerDetail.setItemList(infos);
		listCustomer.setAdapter(adapterCustomerDetail);

		listCustomer.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int pos,
					long id) {

				DeviceLinearLayout deviceImage = (DeviceLinearLayout) view
						.findViewById(R.id.lt_signal_select);
				CheckBox signleCb = (CheckBox) view
						.findViewById(R.id.checkSelect);
				if (deviceImage.clickFlag) {
					deviceImage.clickFlag = false;
					signleCb.toggle();

					adapterCustomerDetail.select(pos, signleCb.isChecked());
				} else {
					Intent i = new Intent();
					i.setClass(ActCustomerManagement.this,
							ActCustomerDetail.class);
					i.putExtra(IntentDefine.CUSTOMER_ID, id);
					startActivity(i);
					// CommonUtil.showToast("customer detail",
					// ActCustomerManagement.this);
				}
			}
		});

		// 弹出投资偏好
		btnInvestment1 = (Button) findViewById(R.id.btnInvestment1);
		btnInvestment1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DlgCitySelected cityDialog = new DlgCitySelected(
						ActCustomerManagement.this, btnInvestment1.getText()
								.toString(),
						DlgCitySelected.CATEGORY_SEL_INVEST_HOBBY, "投资偏好");
				cityDialog
						.setRefreshSortListener(new DlgCitySelected.SelctedCityListener() {

							@Override
							public void selectedCity(String city) {
								btnInvestment1.setText(city);
							}
						});
				cityDialog.showDialog();

			}
		});

		// 弹出省选城市
		cityBtn = (Button) findViewById(R.id.btnCity);
		cityBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DlgCitySelected cityDialog = new DlgCitySelected(
						ActCustomerManagement.this, cityBtn.getText()
								.toString(), DlgCitySelected.CATEGORY_SEL_CITY,
						"城市");//for bug 3780 yangjian 2014/08/14
				cityDialog
						.setRefreshSortListener(new DlgCitySelected.SelctedCityListener() {

							@Override
							public void selectedCity(String city) {
								cityBtn.setText(city);
							}
						});

				cityDialog.showDialog();
			}
		});

		loadSpinner();

		return true;
	}

	// 客户排序
	private void customerSort() {

		List<Integer> list = new ArrayList<Integer>();
		list.add(R.string.total_asset);
		list.add(R.string.invest_total_money);
		list.add(R.string.invest_times);
		CommSortView sortView = new CommSortView(this, list,
				(LinearLayout) findViewById(R.id.sortButton),
				R.string.total_asset);
		sortView.setRefreshSortListener(new RefreshSortListener() {

			@Override
			public void doDataSort(int id) {
				if (adapterCustomerDetail != null) {
					adapterCustomerDetail.sortCustomer(id);
				}
			}
		});
	}

	// private void clearAddCustomerView()
	// {
	// name.setText("");
	// editBirthday.setText("");
	// phone.setText("");
	// email.setText("");
	// job.setSelection(0, true);
	// }

	public void showDatePickerDialog(int title, OnDateChangedListener listener) {
		DlgDatePicker picker = new DlgDatePicker(ActCustomerManagement.this,
				title, listener);
		picker.show();
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// if (editBirthday != null) {
		// editBirthday.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// try {
		// birthday = sdf.parse(editBirthday.getText().toString());
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		// }

	}

	// private Spinner province_spinner;
	// private Spinner city_spinner;
	//
	// private Integer provinceId;
	//
	// private ArrayAdapter<CharSequence> province_adapter;
	// private ArrayAdapter<CharSequence> city_adapter;

	private void initSpinner(Spinner sp, String[] dataList, int selectIndex) {

		ArrayAdapter adapter = new ArrayAdapter<String>(this,
				R.layout.common_spinner_item, dataList);// android.R.layout.simple_spinner_item
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//
		sp.setAdapter(adapter);
		sp.setOnItemSelectedListener(this);
		sp.setSelection(selectIndex, true);
	}

	private void loadSpinner() {

		spinnerCustom = (Spinner) findViewById(R.id.spinnerCustom);
		spinnerEffectiveness = (Spinner) findViewById(R.id.spinnerEffectiveness);
		spinnerSaleChance = (Spinner) findViewById(R.id.spinnerSaleChance);
		spinnerPublisher = (Spinner) findViewById(R.id.spinnerPublisher);

		btnDeadline = (Button) findViewById(R.id.btnDeadline);
		btnProfit = (Button) findViewById(R.id.btnProfit);
		btnDividend = (Button) findViewById(R.id.btnDividend);

		initSpinner(spinnerCustom,
				getResources().getStringArray(R.array.custom_status), 0);
		initSpinner(spinnerEffectiveness,
				getResources().getStringArray(R.array.effectiveness_status), 1);
		initSpinner(spinnerSaleChance,
				getResources().getStringArray(R.array.sale_chance), 0);
		initSpinner(spinnerPublisher,
				getResources().getStringArray(R.array.publisher_status), 0);

		// 筛选弹出的列表加EditText
		initDlgCommonFilter(btnDeadline, R.array.deadline_status,
				R.string.deadlinePerference, true, 1);
		initDlgCommonFilter(btnDividend, R.array.dividend_status,
				R.string.dividendPerference, true, 1);
		initDlgCommonFilter(btnProfit, R.array.profit_status,
				R.string.profitPerference, true, 2);

		// 重置筛选条件
		reset = (Button) findViewById(R.id.buttonReset);
		reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				spinnerCustom.setSelection(0, true);
				spinnerPublisher.setSelection(0, true);
				spinnerSaleChance.setSelection(0, true);
				spinnerEffectiveness.setSelection(1, true);

				cityBtn.setText("全部");
				btnDeadline.setText("全部");
				btnProfit.setText("全部");
				btnDividend.setText("全部");
				btnInvestment1.setText("全部");
			}
		});

		// 筛选
		Button btnSearch = (Button) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				layFilter.setVisibility(View.GONE);
				getCustomerBaseInfoList();
			}
		});
	}

	private void initDlgCommonFilter(final Button btn, final int listId,
			final int title, final boolean isSingle, final int model) {

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {

				DlgCommFilter fDeadLind = new DlgCommFilter(
						ActCustomerManagement.this, listId, title, btn
								.getText().toString(), true, model);
				fDeadLind.setInputValue(map);
				fDeadLind.setListener(new RefreshFilterListener() {

					@Override
					public void doRefresh(String reslut, int position,
							boolean b, int title) {
						((Button) v).setText(reslut);
						if(b)
						{
							String[] str = reslut.split("-");
							String []s = new String[2];
							switch(title)
							{
							case R.string.profitPerference://收益偏好
								s[0] = str[0].split("%")[0];
								s[1] = str[1].split("%")[0];
								break;
							case R.string.dividendPerference://分红偏好
							case R.string.deadlinePerference://期限偏好
								s[0] = str[0];
								s[1] = str[1].split("个月")[0];
								break;
							}
							map.put(title, s);
						}
						else
						{
							Iterator<Map.Entry<Integer, String[]>> it = map.entrySet().iterator(); 
							while(it.hasNext())
							{
								Map.Entry<Integer, String[]> entry=it.next();
								if(entry.getKey() == title)
								{
									it.remove();
								}
							}
						}
					}
				});
				fDeadLind.showDialog();
			}
		});

	}

	// private String[] getSpinner() {
	// String[] choice = new String[9];
	// Spinner sp = null;
	// for (int i = 0; i < 9; i++) {
	// if (i == 3) {
	// Button btn = (Button) findViewById(R.id.spinnerCustom + i);// btnCity
	// choice[i] = btn.getText().toString();
	// } else {
	// sp = (Spinner) findViewById(R.id.spinnerCustom + i);
	// // choice[i] =
	// //
	// getResources().getStringArray(spinnerSource[i])[sp.getSelectedItemPosition()].toString();
	// choice[i] = sp.getSelectedItem().toString();
	// }
	// }
	// return choice;
	// }

	// private int[] spinnerSource = {R.array.custom_status,
	// R.array.effectiveness_status,
	// R.array.sale_chance, R.array.heibei_province_item,
	// R.array.deadline_status,
	// R.array.publisher_status, R.array.profit_status, R.array.dividend_status,
	// R.array.investment1_status};

	// private int[] city = { R.array.beijin_province_item,
	// R.array.tianjin_province_item, R.array.heibei_province_item,
	// R.array.shanxi1_province_item, R.array.neimenggu_province_item,
	// R.array.liaoning_province_item, R.array.jilin_province_item,
	// R.array.heilongjiang_province_item, R.array.shanghai_province_item,
	// R.array.jiangsu_province_item, R.array.zhejiang_province_item,
	// R.array.anhui_province_item, R.array.fujian_province_item,
	// R.array.jiangxi_province_item, R.array.shandong_province_item,
	// R.array.henan_province_item, R.array.hubei_province_item,
	// R.array.hunan_province_item, R.array.guangdong_province_item,
	// R.array.guangxi_province_item, R.array.hainan_province_item,
	// R.array.chongqing_province_item, R.array.sichuan_province_item,
	// R.array.guizhou_province_item, R.array.yunnan_province_item,
	// R.array.xizang_province_item, R.array.shanxi2_province_item,
	// R.array.gansu_province_item, R.array.qinghai_province_item,
	// R.array.linxia_province_item, R.array.xinjiang_province_item,
	// R.array.hongkong_province_item, R.array.aomen_province_item,
	// R.array.taiwan_province_item };

	private void select(Spinner spin, ArrayAdapter<CharSequence> adapter,
			int arry) {
		adapter = ArrayAdapter.createFromResource(this, arry,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
		// spin.setSelection(0,true);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// if (id > 0) {
		// Spinner sp = (Spinner) findViewById((int) id);
		// // adapterCustomerDetail.searchFilter((int)
		// // id).filter(sp.getSelectedItem().toString());
		// }
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeDialog();
	}

	@Override
	protected void onResume() {
		super.onResume();
		etCustomerSearch.setText("");
		if (adapterCustomerDetail != null) {
			list = CoreManager.getInstance().getCustomerManager()
					.getListCustomer();
			if (list != null) {
				adapterCustomerDetail.setItemList(list);
				// for(Customer c: list)
				// {
				// Log.i("liningtest2222222222222", c.getId()+"");
				// Log.i("liningtest22222222222222", c.getAge()+"");
				// }
			}

		}
	}

	private void closeDialog() {
		if (waitDlg != null) {
			waitDlg.clearAnimation();
			waitDlg = null;
		}
	}
	private BinaryHttpResponseHandler binaryHttpResponseHandler=new BinaryHttpResponseHandler(){
		
		@Override
		public void onSuccess(String str)
		{
		}
		@Override
		public void onSuccess(byte[] binaryData) {
			super.onSuccess(binaryData);
			closeDialog();
			Log.i(TAG, binaryData.length + "----");
 			/*if (binaryData != null && binaryData.length != 0) {*/
		if(binaryData != null && binaryData.length != 0){
 			//byte[] pic = android.util.Base64.decode(binaryData, Base64.DEFAULT);
 			BitmapFactory.Options opts = new BitmapFactory.Options();
 
 			opts.inSampleSize = 4; //这个的值压缩的倍数（2的整数倍），数值越小，压缩率越小，图片越清晰
 
 			Bitmap	bmap = BitmapFactory.decodeByteArray(binaryData, 0, binaryData.length, opts);
 			listBitMap.add(bmap);
 		}
 		else{
 			listBitMap.add(null);
 		}
		if(listBitMap.size() == list.size())
		{
			adapterCustomerDetail.notifyDataSetChanged();
		}
		}

		@Override
		public void onFailure(Throwable error, String content) {
//			super.onFailure(error, content);
			closeDialog();
			Log.i(TAG, content);
		}
		
	};
/*	//网络请求
	public void sendMessage(Customer customer) {
		 //将理财师ID传入requset中进行判断 0
		Request request = new Request(RequestDefine.MARKET_RQ_UPLOAD_PHOTO,customer.getId(),
				RequestType.GET, null, binaryHttpResponseHandler);
		CoreManager.getInstance().postRequest(request);
//		CoreManager.getInstance().postRequest(request);
//		if(waitDlg == null){
//			//context 上下文环境
//			waitDlg = new CommonWaitDialog(this, "", R.string.load_data);
//		}
	}*/
}
