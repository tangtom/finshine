package com.incito.finshine.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.core.util.AppToast;
import com.android.core.util.RefreshUtil;
import com.android.core.util.SharedUtil;
import com.codans.blossom.datepicker.DlgDatePicker;
import com.custom.view.CommSortView;
import com.custom.view.CommSortView.RefreshSortListener;
import com.custom.view.DlgCitySelected;
import com.custom.view.DlgCommFilter;
import com.custom.view.DlgCommFilter.RefreshFilterListener;
import com.custom.view.FirstPagePop;
import com.custom.view.PopDatePicker;
import com.custom.view.PopProdAnalyse;
import com.custom.view.PopProdCompare;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterProductList;
import com.incito.finshine.activity.adapter.AdapterProductList.RefreshProdData;
import com.incito.finshine.activity.dialog.CrmsDialog;
import com.incito.finshine.entity.Product;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.CoreManager.AppStatus;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FaliureResult;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.LogCito;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.finshine.product.ProductManager;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.utility.FilterUtil;
import com.incito.utility.SharedKey;
import com.incito.wisdomsdk.event.EventBus;

/**
 * <dl>
 * <dt>ActProductManagement.java</dt>
 * <dd>Description:产品管理或者意象产品（该界面为复用界面）</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-9 下午1:27:09</dd>
 * </dl>
 * 
 * @author lihs
 */

public class ActProductManagement extends Activity implements OnClickListener {

	private static final String TAG = "ActProductManagement";

	/** 从客户营销对客户选择意向产品 **/
	public static final String ACTION_FROM_MARKET_CSID = "action_from_market_id";
	public static final String ACTION_INTENT_PRODUCT = "action_intent_product";

	// public static final String FROM = "product_manager";

	private static final int ALL_PRODUCT = 1;
	private static final int INTENT_PRODUCT = 2;
	private int prodType = ALL_PRODUCT;

	// true 表示意象产品；false 表示所有产品
	private boolean isFromIntProd = false;
	private EditText etProductNameSearch = null;
	// true表示从营销过来，false表示不是
	private boolean isFromMarketing = false;
	// private TextView tvPublisherStart = null;
	// private TextView tvPublisherEnd = null;

//	private DlgDatePicker datePicker = null;

	private PullToRefreshListView productList = null;
	private AdapterProductList productAdapter;
	private List<Product> productLists;//存放所有产品
	private List<Product> intentProducts;//意向产品 add by SGDY
	private CommSortView sortView = null;
	private PopProdCompare prodCompare = null;
	private ProductManager prodManger = null;
	private HttpUtils httpUtil = null;

	private Button btnProdType;
	private Spinner spinnerProdStatus;
	private Spinner spinnerPublisher;

	private Button btnProfit;
	private Button btnDeadline;
	private Button btnStartMoney;
	private Button buttonProdStatus,buttonPublisher;// add by SGDY for BUG#4207

	private ImageView ivPopMenu;
	private ImageView search_icon;//根据客户姓名搜索输入框

	private EditText editPublishDate;
	// private EditText editPublishEndDate;
	PopDatePicker popDatePicker;
	// 客户营销选择产品传递客户id
	private long customerId = -1;

	// 当前产品的页数
	private int currentPage = 1;
	private int totalPage = 0;

	private String str1 = "";
	private String str2 = "";
//	// 查看更多
//	private TextView moreTextView;
//	// 正在加载进度条
//	private LinearLayout loadProgressBar;
	private boolean isFilter = false;//判断是否为筛选
//	private View mFooterView;//listview footview
	private CheckBox cbRecommand,cbCollect,cbOverDue;
	private String btnLongProdType = "";
	private Map<Integer,String[]> map;//add by SGDY for bug#5136
	private int sortType;//记录排序状态  add by SGDY for bug#4290
	private String sortStr;//记录向服务器发送的排序类型
	private int isFromMarket;//判断将要跳回哪个界面 add by SGDY
	
	private boolean isFirstJump = true;//是否是第一次跳转意向产品
	private String intentProductIds;//存放意向产品id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_product_management);
		
		showHelpProduct() ;				

		prodManger = CoreManager.getCoreManager().getProduct();

		EventBus.getDefault().registerSticky(this);
		EventBus.getDefault().registerSticky(this,"setLongProdType");
		Intent i = getIntent();
		if (i != null && i.hasExtra(ACTION_INTENT_PRODUCT)) {// 从营销界面跳过来
			isFromIntProd = true;
			isFromMarket = i.getIntExtra(ACTION_INTENT_PRODUCT, 0);
			initTopTitle(1);
			customerId = i.getLongExtra(ACTION_FROM_MARKET_CSID, -1);
			isFromMarketing = true;
		} else {
			initTopTitle(0);
		}

		initUI();

		initData();
		
		findViewById(R.id.imageNavigate).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						initPullPop();
					}
				});

		CommonUtil.hideSoftInputFromWindow(this);

	}
	
	public void setLongProdType(Message msg){
		this.btnLongProdType = (String)msg.obj;
	}

	private void initPullPop() {

		FirstPagePop firstPage = new FirstPagePop(this,
				findViewById(R.id.imageNavigate));
		firstPage.setPosition(2);
		firstPage.showPopWindow();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void initTopTitle(int from) {

		TextView topTitle = (TextView) findViewById(R.id.textTitle);

		if (from == 0) {
			topTitle.setText(getResources().getString(
					R.string.title_product_management));
		} else
			topTitle.setText(getResources().getString(
					R.string.title_product_choose));

		ImageView btnBack = (ImageView) findViewById(R.id.imageBack);
		btnBack.setOnClickListener(this);
	}

	CommSortView intProd;

	private void initUI() {

		List<Integer> ids = new ArrayList<Integer>();
		if (isFromIntProd) {
			ids.add(R.string.intent_product);
			ids.add(R.string.all_product);
			intProd = new CommSortView(this, ids,
					(LinearLayout) findViewById(R.id.lt_intent_product),
					R.string.all_product);
			intProd.setRefreshSortListener(new RefreshSortListener() {

				@Override
				public void doDataSort(int id) {

					if (id == R.string.intent_product) {
						// 隐藏筛选
						findViewById(R.id.lt_prod_manager).setVisibility(
								View.GONE);
						findViewById(R.id.lt_sort).setVisibility(View.GONE);
						prodType = INTENT_PRODUCT;
						getIntentProduct();
					} else {
						// 显示筛选
						findViewById(R.id.lt_sort).setVisibility(View.VISIBLE);
						findViewById(R.id.lt_prod_manager).setVisibility(
								View.VISIBLE);
						prodType = ALL_PRODUCT;
						getData(prodType, getJsonObject());
					}
				}
			});
		}
		ids.clear();
		ids.add(R.string.product_manager_default_sort);
		// ids.add(R.string.product_manager_publish_time);
		ids.add(R.string.product_manager_ecpected_income);
		ids.add(R.string.product_manager_commision);
		ids.add(R.string.product_manager_time_limit);
		// ids.add(R.string.product_manager_scale);
		ids.add(R.string.product_manager_hotest);
		sortType = ids.get(0);
		sortStr = "sortDesc";
		sortView = new CommSortView(this, ids,
				(LinearLayout) findViewById(R.id.lt_sort),
				R.string.product_manager_default_sort);
		sortView.setRefreshSortListener(new RefreshSortListener() {

			@Override
			public void doDataSort(int id) {
				if (productAdapter != null) {
					sortType = id;
					switch(id)
					{
					case R.string.product_manager_default_sort:
						sortStr = "sortDesc";
						break;
					case R.string.product_manager_ecpected_income:
						sortStr = "sortProdYieldFixed";
						break;
					case R.string.product_manager_commision:
						sortStr = "sortProdCommissionBase";
						break;
					case R.string.product_manager_time_limit:
						sortStr = "sortProdTime";
						break;
					case R.string.product_manager_hotest:
						sortStr = "sortHot";
						break;
					}
					productAdapter.setSortMethodsByType(id);
					if (productLists != null && productLists.size() > 0) {
						productList.getRefreshableView().setSelection(0);
					}
				}
			}
		});

		findViewById(R.id.btnFilter).setOnClickListener(this);
		findViewById(R.id.buttonReset).setOnClickListener(this);
		findViewById(R.id.btnSearch).setOnClickListener(this);
		cbRecommand = (CheckBox)findViewById(R.id.cbRecommand);
		cbCollect = (CheckBox)findViewById(R.id.cbCollect);
		cbOverDue = (CheckBox)findViewById(R.id.cbOverDue);
		ivPopMenu = (ImageView) findViewById(R.id.ivPopMenu);
		ivPopMenu.setImageDrawable(getResources().getDrawable(
				R.drawable.ic_pointer_analyse));
		ivPopMenu.setVisibility(View.VISIBLE);
		ivPopMenu.setOnClickListener(this);
		search_icon = (ImageView)findViewById(R.id.search_icon);
		search_icon.setOnClickListener(this);
		etProductNameSearch = (EditText) findViewById(R.id.et_search_text);
		etProductNameSearch.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if(s.length() != 0){
					search_icon.setImageDrawable(getResources().getDrawable(R.drawable.delete));
				}else{
					search_icon.setImageDrawable(getResources().getDrawable(R.drawable.product_search_icon));
				}
				if (productAdapter != null) {
					productAdapter.getFilter().filter(s.toString());
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		});

		//筛选布局隐藏
		findViewById(R.id.filter).setVisibility(View.GONE);
		loadSpinner();

	}

	private void loadSpinner() {
		btnProdType = (Button) findViewById(R.id.btnProdType);
		spinnerProdStatus = (Spinner) findViewById(R.id.spinnerProdStatus);
		spinnerPublisher = (Spinner) findViewById(R.id.spinnerPublisher);

		btnProfit = (Button) findViewById(R.id.btnProfit);
		btnDeadline = (Button) findViewById(R.id.btnDeadline);
		btnStartMoney = (Button) findViewById(R.id.btnStartMoney);
		buttonProdStatus = (Button)findViewById(R.id.buttonProdStatus);
		buttonPublisher = (Button)findViewById(R.id.buttonPublisher);

		initSpinner(spinnerProdStatus,
				getResources().getStringArray(R.array.product_status), false);
		initSpinner(spinnerPublisher,
				getResources().getStringArray(R.array.publisher_status), false);

		// 筛选弹出的列表加EditText
		initDlgCommonFilter(btnProfit, R.array.profit_status,
				R.string.referenceIncome, true, 2);
		initDlgCommonFilter(btnDeadline, R.array.product_deadline,
				R.string.product_deadline, true, 4);
		initDlgCommonFilter(buttonProdStatus, R.array.product_status,
				R.string.product_states, true, 4);
		initDlgCommonFilter(buttonPublisher, R.array.publisher_status,
				R.string.publisher_prod, true, 4);
		initDlgCommonFilter(btnStartMoney, R.array.investment,
				R.string.startInvest,

				true, 3);

		// 弹出投资偏好 产品类型
		btnProdType = (Button) findViewById(R.id.btnProdType);
		btnProdType.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DlgCitySelected cityDialog;
				if(btnLongProdType.isEmpty())
				{
					cityDialog = new DlgCitySelected(
							ActProductManagement.this, btnProdType.getText()
									.toString(),
							DlgCitySelected.CATEGORY_SEL_INVEST_HOBBY, "产品类型");
				}
				else
				{
					cityDialog = new DlgCitySelected(
							ActProductManagement.this, btnLongProdType,
							DlgCitySelected.CATEGORY_SEL_INVEST_HOBBY, "产品类型");
				}
				cityDialog
						.setRefreshSortListener(new DlgCitySelected.SelctedCityListener() {

							@Override
							public void selectedCity(String city) {
								btnProdType.setText(city);
							}
						});
				cityDialog.showDialog();
			}
		});

		// 发行期
		editPublishDate = (EditText) findViewById(R.id.editPublishDate);
		// editPublishEndDate = (EditText)
		// findViewById(R.id.editPublishEndDate);
		editPublishDate.setKeyListener(null);

		editPublishDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (popDatePicker == null) {
					popDatePicker = new PopDatePicker(
							ActProductManagement.this, v);
					popDatePicker.setIsProductManager(true);
				}
				popDatePicker.showPopWindow();
			}
		});
		map = new HashMap<Integer,String[]>();
	}

	private void initSpinner(Spinner sp, String[] dataList,
			boolean isResaveDefaultValue) {

		if (isResaveDefaultValue) {
			sp.setSelection(0, true);
		} else {
			ArrayAdapter adapter = new ArrayAdapter<String>(this,
					R.layout.common_spinner_item, dataList);// android.R.layout.simple_spinner_item
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//
			sp.setAdapter(adapter);
			// sp.setOnItemSelectedListener(this);
		}
	}

	private void initDlgCommonFilter(final Button btn, final int listId,
			final int title, final boolean isSingle, final int model) {

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				DlgCommFilter fDeadLind = new DlgCommFilter(
						ActProductManagement.this, listId, title, btn.getText()
								.toString(), true, model);
				fDeadLind.setInputValue(map);
				fDeadLind.setListener(new RefreshFilterListener() {

					@Override
					public void doRefresh(String reslut, int position,boolean b,int title) {

						((Button) v).setText(reslut);
						if(b)
						{
							String[] str = reslut.split("-");
							String []s = new String[2];
							switch(title)
							{
							case R.string.referenceIncome://参考收益
								s[0] = str[0].split("%")[0];
								s[1] = str[1].split("%")[0];
								break;
							case R.string.startInvest://起投金额
								s[0] = str[0];
								s[1] = str[1].split("万")[0];
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


/*	//查看更多的布局
	private void addFooterView() {
		mFooterView = LayoutInflater.from(this).inflate(R.layout.list_page_load,
				null);
		moreTextView = (TextView) mFooterView.findViewById(R.id.more_id);
		loadProgressBar = (LinearLayout) mFooterView.findViewById(R.id.load_id);
		moreTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 隐藏"加载更多"
				moreTextView.setVisibility(View.GONE);
				// 显示进度条
				loadProgressBar.setVisibility(View.VISIBLE);
				if (prodType == INTENT_PRODUCT) {
					getIntentProduct();
				} else {
					Log.i(TAG, currentPage + "------");
					currentPage++;
					if (currentPage > totalPage) {
						CommonUtil.showToast("已经是最后一页数据",
								ActProductManagement.this);
						if (productList != null) {
							ifRefresh = false;
						}
						return;
					}
					ifRefresh = true;
					getData(prodType, getJsonObject());
				}
			}
		});
//		productList.addFooterView(mFooterView);
	}*/

	private void refreshData(final AdapterProductList adapter)
	{
		adapter.setRefreshProdListener(new RefreshProdData() {

			@Override
			public void doRefresh(boolean b) {

				if (prodType == INTENT_PRODUCT) {
					getIntentProduct();
				} else {
					if(b)
					{
						sendMsgForSearch();
					}
					else
					{
						getData(prodType, getJsonObject());
					}
//					if (currentPage > totalPage) {
//						CommonUtil.showToast("已经是最后一页数据",
//								ActProductManagement.this);
//						return;
//					}
					//change by SGDY for BUG#5305 at 2014/8/13 13:23
				}
			}

			@Override
			public void doRefreshProdCompare() {

				List<Long> prodId = adapter.getCompareProdId();
				// 跳转到 产品对比详情界面
				if (prodId.size() > 0) {
					if (prodCompare == null) {
						prodCompare = new PopProdCompare(
								ActProductManagement.this,
								findViewById(R.id.btnFilter), adapter);
						prodCompare.showPopWindow();
						prodCompare.getPopWin().setOnDismissListener(
								new OnDismissListener() {

									@Override
									public void onDismiss() {

										prodCompare = null;
									}
								});
					}

				} else if (prodId.size() == 0) {
					if (prodCompare != null) {
						prodCompare.closePopWindow();
						prodCompare = null;
					}
					return;
				}
				Product prod = null;
				if (prodId.size() == 1) {
					prod = prodManger.getProdctByProdId(prodId.get(0));
					((TextView) prodCompare.getPopView().findViewById(
							R.id.tv_product_name1)).setText(prod.getProdName());
					((TextView) prodCompare.getPopView().findViewById(
							R.id.tv_product_name2)).setText(null);
				} else if (prodId.size() == 2) {
					((TextView) prodCompare.getPopView().findViewById(
							R.id.tv_product_name1)).setText(prodManger
							.getProdctByProdId(prodId.get(0)).getProdName());
					((TextView) prodCompare.getPopView().findViewById(
							R.id.tv_product_name2)).setText(prodManger
							.getProdctByProdId(prodId.get(1)).getProdName());
				}
				adapter.notifyDataSetChanged();
			}

			@Override
			public void resresh4IntentProduct(Product product, boolean isAdd) {
				if(isAdd){//如果是添加意向产品
					if(intentProducts == null){
						intentProducts = new ArrayList<Product>();
					}
					if(!intentProducts.isEmpty()){
						for(Product p : intentProducts){
							if(p.getId() == product.getId()){
								intentProducts.remove(product);
							}
						}
					}
					intentProducts.add(product);
				}else{//如果是删除意向产品
					if(intentProducts != null){
						intentProducts.remove(product);
					}
				}
			}
		});
	}
	
	private void initData() {
		productList = (PullToRefreshListView) findViewById(R.id.listview);
		productList.setMode(Mode.PULL_FROM_END);
		productAdapter = new AdapterProductList(this);
		ArrayList<Product> datalist = new ArrayList<Product>();
		productAdapter.setProductData(datalist);
		productList.setOnRefreshListener(mRefreshListener);
		if (isFromIntProd) {
			productAdapter
					.setAdapterType(AdapterProductList.MARKET_INTENT_PRODUCT);
		}
		productAdapter.setCustomerId(customerId);
		productList.setAdapter(productAdapter);
		refreshData(productAdapter);

		productList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Product data = (Product) parent.getItemAtPosition(position);
				if (data == null) {
					return;
				}
				Intent intent = new Intent(ActProductManagement.this,
						ActProductDetail.class);
				intent.putExtra(ActProductDetail.PRODUCT_ID, data.getId());
				intent.putExtra("product_data", data);
				if (isFromMarketing)
					intent.putExtra(ActProductDetail.FLAG, 0);
				else
					intent.putExtra(ActProductDetail.FLAG, 1);
				startActivity(intent);
			}
		});
		if (isFromIntProd) {
			prodType = INTENT_PRODUCT;
			intProd.selePointPosition(R.string.intent_product);
			getIntentProduct();
		} else {
			prodType = ALL_PRODUCT;
			getData(prodType, getJsonObject());
		}
	}

	private boolean ifRefresh = false;

	private JSONObject getJsonObject() {
		JSONObject json = new JSONObject();
		try {
			json.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
			json.put("pageNow", currentPage);
			json.put(sortStr, "1");
			if (intentProductIds != null) {
				json.put("removeIds", intentProductIds);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	private void getData(final int prodType, JSONObject json) {

		httpUtil = new HttpUtils(this, RequestDefine.RQ_PRODUCT_GET,
				RequestType.POST, json);
		Log.d(TAG, "json is " + json.toString());
		if (ifRefresh) {
			httpUtil.setShowDiloag(false);
		} else {
			httpUtil.setShowDiloag(true);
		}
		httpUtil.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {
				if (productList != null) {
					ifRefresh = false;
					RefreshUtil.refreshComplete(productList);
				}
				Log.d(TAG, "success o= " + response.toString());
				try {

					if (Request.RESLUT_OK.equals(response.optString("status"))) {

						JSONArray arr = response.getJSONArray("list");
//						productList.removeFooterView(mFooterView);
						if (arr.length() > 0) {
							totalPage = response.optInt("pages");
//							if(currentPage == totalPage)
//							{
//								
//							}
//							if(currentPage < totalPage)
//							{
//								addFooterView();
//							}
							LogCito.d("当前产品总页数：" + response.getInt("pages"));
							LogCito.d("当前产品总页数：" + totalPage);
						}
						/*------------------------------*/
						Gson gson = new Gson();
						List<Product> temp = gson.fromJson(arr.toString(),
								new TypeToken<List<Product>>() {
								}.getType());
						/*----------------------删除状态为7(下架)的产品-----------------------*/
						List<Product> lists = new ArrayList<Product>();
						for(Product p : temp)
						{	
							Log.i(TAG,p.getProdName() + "----" + p.getProdStatus());
							if(p.getStatus() != null && p.getProdStatus() == 7)
							{
								lists.add(p);
							};
						}
						temp.removeAll(lists);
						
						// productLists = prodManger.getListProduct();
						if(isFilter)//判断是否为筛选，如果为筛选，数据清空，重新匹配
						{
							productLists.clear();
							isFilter = false;
						}
						lists.clear();//清空list数据，list复用
						if (productLists != null && productLists.size() > 0) {
							/*---          list添加重复数据              ---*/
							for (Product p : temp) {
								for (Product p1 : productLists) {
									if (p.getId() == p1.getId()) {
										Log.i(TAG, p.getId() + p.getProdName() +  "--------" + p1.getId() + p1.getProdName());
										lists.add(p);
									}
								}
							}
							productLists.addAll(temp);//添加后台分页返回数据
							Log.i(TAG,lists.size() + "-----------lists");
							productLists.removeAll(lists);//删除重复数据
							lists.clear();//清空数据
//							}
						} else {
							productLists = temp;
						}
						Log.i(TAG,temp.size() + "-----------");
					}
					// 如果数据不空 就直接从取下来的缓存数据；为空就掉接口取数据
					if (isFromIntProd) {
						productAdapter.setSelec_prod_type(AdapterProductList.SELE_PROD_ALL_PROD);
					}
					Log.i(TAG, productLists.size() + "------");
					/*-------------------------删除意向产品-----------------------------*/
					if(intentProducts != null){
						List<Product> lists = new ArrayList<Product>();
						for(Product p1 : intentProducts){
							for(Product p2 : productLists){
								if(p1.getId() == p2.getId()){
									lists.add(p2);
								}
							}
						}
						productLists.removeAll(lists);//如果意向产品不为空，则删除意向产品
					}
					prodManger.setListaLLProduct(productLists);
					productAdapter.setProductData(productLists);
					productAdapter.setSortMethodsByType(sortType);//for bug#4290 by SGDY
					productAdapter.notifyDataSetChanged();
					RefreshUtil.refreshComplete(productList);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});

		httpUtil.setFaliureResult(new FaliureResult() {

			@Override
			public void getResluts(String msg) {
				if (productList != null) {
					ifRefresh = false;
//					productList.onUnRefreshComplete();
				}
				RefreshUtil.refreshComplete(productList);
				productAdapter.setProductData(null);
				productAdapter.notifyDataSetChanged();
				RefreshUtil.refreshComplete(productList);
			}
		});
		httpUtil.executeRequest();
	}

	public void onEvent(String value) {
		if (value.equals("全部")) {
			str1 = "";
			str2 = "";
		} else {
			String[] str = value.split("----");
			str1 = str[0];
			str2 = str[1];
		}
		editPublishDate.setText(value);
	}
	
	@Override
	public void onClick(final View v) {

		switch (v.getId()) {
		case R.id.imageBack:
			if(isFromMarketing)
			{
				if(isFromMarket == 2)
				{
					if (SharedUtil.getPreferBool(SharedKey.CONFIG_CRMS, false)) {
						startActivity(new Intent(this, ActCustomerManagement.class));
					} else {
						CrmsDialog dialog = new CrmsDialog(ActProductManagement.this);
						dialog.show();
					}
				}
				else
				{
					startActivity(new Intent(this, ActCustomerMarketing.class));
				}
			}
			else
			{
				startActivity(new Intent(this,ActFinshineHomePage.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			}
			finish();
			break;
		case R.id.ivPopMenu:
			PopProdAnalyse popProdAnalyse = new PopProdAnalyse(this, v);
			popProdAnalyse.showPopWindow();
			break;
		case R.id.buttonReset:// 重置筛选条件
			btnProdType.setText("全部");
			btnProfit.setText("全部");
			btnDeadline.setText("全部");
			btnStartMoney.setText("全部");
			buttonProdStatus.setText("全部");
			buttonPublisher.setText("全部");
			spinnerProdStatus.setSelection(0, true);
			spinnerPublisher.setSelection(0, true);
			editPublishDate.setText("全部");
			cbRecommand.setChecked(false);
			cbCollect.setChecked(false);
			currentPage = 1;
			break;
		case R.id.btnSearch:// 搜索
			//筛选布局隐藏
			findViewById(R.id.filter).setVisibility(View.GONE);
			sendMsgForSearch();
			break;
		case R.id.btnFilter:
			if (View.VISIBLE == findViewById(R.id.filter).getVisibility()) {
				findViewById(R.id.filter).setVisibility(View.GONE);
			} else {
				findViewById(R.id.filter).setVisibility(View.VISIBLE);
			}
			break;
		case R.id.cbRecommand :
			break;
		case R.id.cbCollect:
			break;
		case R.id.cbOverDue:
			break;
		case R.id.search_icon:
			if(search_icon.getDrawable().getCurrent().getConstantState() == getResources().getDrawable(R.drawable.delete).getConstantState())
			{
				etProductNameSearch.setText("");
			}
			break;
		default:
			break;
		}

	}
	/*--------add by SGDY at 2013/8/13-------*/
	public void sendMsgForSearch(){
		String[] investValue = getResources().getStringArray(
				R.array.investment2_status);
		JSONObject filterJson = new JSONObject();
		try {
			filterJson.put(sortStr, "1");
			filterJson.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
			//一级类型
			filterJson.put("searchProdFirstType", FilterUtil
					.getInvestmentValue(btnProdType.getText().toString(),
							true, investValue));
			//二级类型
			filterJson.put("searchProdSecondtype", FilterUtil
					.getInvestmentValue(btnProdType.getText().toString(),
							false, investValue));
			/*------- change by SGDY for BUG#4207 at 2014/8/12  --------*/
			//产品状态
//			filterJson.put("searchProdStatus", FilterUtil
//					.getProductStatus(spinnerProdStatus
//							.getSelectedItemPosition()));
			filterJson.put("searchProdStatus", FilterUtil
					.getProductStatus(buttonProdStatus.getText().toString()));
			//发行商偏好
//			filterJson
//					.put("searchProdPreference",
//							spinnerPublisher.getSelectedItemPosition() == 0 ? ""
//									: getResources().getStringArray(
//											R.array.publisher_status)[spinnerPublisher
//											.getSelectedItemPosition()]);
			filterJson
			.put("searchProdPreference",buttonPublisher.getText().toString().equals("全部") ? "" : buttonPublisher.getText().toString());
			//最低收益
			filterJson.put("searchFFLow", FilterUtil.getPercentBtnValue(
					btnProfit.getText().toString(), true));
			//最高收益
			filterJson.put("searchFFTop", FilterUtil.getPercentBtnValue(
					btnProfit.getText().toString(), false));
			
			//期限开始
			if(FilterUtil.getProdTime(btnDeadline.getText().toString(),true) != 0)
			{
				filterJson.put("searchProdTimeStart", FilterUtil
						.getProdTime(btnDeadline.getText().toString(),
								true) * 12);
			}
//			//期限结束
			if(FilterUtil
					.getProdTime(btnDeadline.getText().toString(),
							false) != 0)
			{
				filterJson.put("searchProdTimeEnd", FilterUtil
						.getProdTime(btnDeadline.getText().toString(),
								false) * 12);
			}
			
			// searchProdTimeStart 
			//最低起投金额
			filterJson.put("searchProdStartLow", FilterUtil
					.getUnSureBtnValue(btnStartMoney.getText().toString(),
							true));
			//最高起投金额
			filterJson.put("searchProdStartTop", FilterUtil
					.getUnSureBtnValue(btnStartMoney.getText().toString(),
							false));
			if (!TextUtils.isEmpty(str1)) {//发行期开始
				Date currentDate = DateUtil.formatString2Date(str1,
						DateUtil.FORMAT_YYYY_MM_DD_ZH);
				filterJson.put("searchProdOnDateTime", DateUtil
						.formatDate2String(currentDate,
								DateUtil.FORMAT_YYYY_MM_DD));
			}

			if (!TextUtils.isEmpty(str2)) {//发行期结束
				Date currentDate = DateUtil.formatString2Date(str2,
						DateUtil.FORMAT_YYYY_MM_DD_ZH);
				filterJson.put("searchProdEnDateTime", DateUtil
						.formatDate2String(currentDate,
								DateUtil.FORMAT_YYYY_MM_DD));
			}
			if(cbRecommand.isChecked())//仅看推荐
			{
				filterJson.put("searchProdTop", "1");
			}
			if(cbOverDue.isChecked())//显示已过期
			{
				filterJson.put("searchOverDue", "1");
			}
			if(cbCollect.isChecked())//仅看收藏
			{
				filterJson.put("searchSalesLike", "1");
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		isFilter = true;
		Log.i("产品筛选条件是", filterJson.toString());
		getData(prodType, filterJson);
	}

/*	private void restoreDefaultQueryCondition() {

		etProductNameSearch.setText(null);

		// 恢复默认排序
		if (sortView != null) {
			sortView.selePointPosition(R.string.product_manager_default_sort);
		}

		// ((TextView)findViewById(R.id.tv_starttime)).setText(null);
		// ((TextView)findViewById(R.id.tv_endtime)).setText(null);

	}*/

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		datePicker = null;
		productAdapter = null;
		if (httpUtil != null && httpUtil.getDialog() != null) {
			httpUtil.getDialog().clearAnimation();
		}
	}

	// add by SGDY at 2014/8/13 19:40
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK && event.getRepeatCount() == 0)//按下的如果是BACK，同时没有重复
		{
			if(isFromMarketing)
			{
				if(isFromMarket == 2)
				{
					if (SharedUtil.getPreferBool(SharedKey.CONFIG_CRMS, false)) {
						startActivity(new Intent(this, ActCustomerManagement.class));
					} else {
						CrmsDialog dialog = new CrmsDialog(ActProductManagement.this);
						dialog.show();
					}
				}
				else
				{
					startActivity(new Intent(this, ActCustomerMarketing.class));
				}
			}
			else
			{
				startActivity(new Intent(this,ActFinshineHomePage.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			}
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	private void getIntentProduct() {

		JSONObject params = new JSONObject();
		try {
			params.put("salesId",
					SPManager.getInstance().getLongValue(SPManager.ID));
			params.put("customId", customerId);
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		Log.i(TAG, params.toString());
		HttpUtils httpUtils = new HttpUtils(this,
				RequestDefine.QUERY_INTENT_DATA, RequestType.POST, params);
		httpUtils.setShowDiloag(true);
		httpUtils.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {

				if (Request.RESLUT_OK.equals(response.optString("status"))) {

					try {
						List<Product> products = new ArrayList<Product>();
						if (response.isNull("list")) {
							if(isFirstJump)
							{
								isFirstJump = false;//不是第一次跳转
								intProd.selePointPosition(R.string.all_product);
								prodType = ALL_PRODUCT;
								getData(prodType, getJsonObject());
								return;
							}
						} else {
							JSONArray arr = response.getJSONArray("list");
//							for(JSONObject json)
							Gson gson = new Gson();
							products = gson.fromJson(arr.toString(),
									new TypeToken<List<Product>>() {
									}.getType());
						}
			/*----------------------删除状态为7(下架)的产品-----------------------*/
						List<Product> lists = new ArrayList<Product>();
						StringBuffer sb = new StringBuffer();
						for(Product p : products)
						{	
							Log.i(TAG,p.getProdName() + "----" + p.getProdStatus());
							if(p.getStatus() != null && p.getProdStatus() == 7)
							{
								lists.add(p);
							}else{
								sb.append(p.getId());
								sb.append(",");
							};
						}
						intentProductIds = sb.toString().substring(0, sb.length() - 1);
						products.removeAll(lists);
						intentProducts = products;
						if (prodType == INTENT_PRODUCT) {
							// 选择意向产品
							productAdapter
									.setSelec_prod_type(AdapterProductList.SELE_PROD_INTENT_PROD);
						}
	
						Log.i(TAG, products.size() + "----");
	//						if (products.size() < 10) {
	////							moreTextView.setVisibility(View.GONE);
	//						}
	//						productList.removeFooterView(mFooterView);
						productAdapter.setProductData(products);
						productAdapter.setSortMethodsByType(R.string.product_manager_default_sort);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		httpUtils.setFaliureResult(new FaliureResult() {

			@Override
			public void getResluts(String msg) {
				if (productList != null) {
					ifRefresh = false;
				}
				productAdapter.setProductData(null);
			}
		});
		httpUtils.executeRequest();
	}
	
	/**
	 * @description 上拉加载更多
	 * @author Andy.fang
	 */
	OnRefreshListener<ListView> mRefreshListener = new OnRefreshListener<ListView>() {
		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {

			
			if (prodType == INTENT_PRODUCT) {
				getIntentProduct();
			} else {
				Log.i(TAG, currentPage + "------");
				currentPage++;
				if (currentPage > totalPage) {
					CommonUtil.showToast("已经是最后一页数据",
							ActProductManagement.this);
					if (productList != null) {
						ifRefresh = false;
					}
					return;
					
				}
				ifRefresh = true;
				getData(prodType, getJsonObject());
			}
			
		}
	};
	
	public void showHelpProduct(){
		String saveHelpProduct = SharedUtil.getPreferStr("saveHelpProduct") ;
		if(TextUtils.isEmpty(saveHelpProduct)
				|| saveHelpProduct.equals("0")){
			Intent intent = new Intent() ;
			intent.setClass(ActProductManagement.this, ActHelpProduct.class);
			ActProductManagement.this.startActivity(intent);
		}		
	}
}
