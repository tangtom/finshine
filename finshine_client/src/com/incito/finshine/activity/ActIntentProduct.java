package com.incito.finshine.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.custom.view.CommSortView;
import com.custom.view.CommonWaitDialog;
import com.custom.view.PopProdCompare;
import com.custom.view.CommSortView.RefreshSortListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterProductList;
import com.incito.finshine.activity.adapter.AdapterProductList.RefreshProdData;
import com.incito.finshine.entity.Product;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

/**
 * <dl>
 * <dt>ActIntentProduct.java</dt>
 * <dd>Description:意向产品</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-5-22 下午1:37:36</dd>
 * </dl>
 * 
 * @author lihs
 */
public class ActIntentProduct extends Activity implements OnClickListener {
	
	private static final String TAG = "ActIntentProduct";
	
	private EditText etProductNameSearch = null;
	
	private ListView productList = null; 
	private AdapterProductList productAdapter;
	
	private List<Product> productLists;
	
	private CommonWaitDialog dialog = null;
	
	private PopProdCompare prodCompare = null;
	
	private CommSortView sortView = null; 
	
	private JsonHttpResponseHandler handlerGetProduct = new JsonHttpResponseHandler() {
	@Override
	public void onSuccess(JSONObject response) {
		
		CommonUtil.showToast("获取产品成功", ActIntentProduct.this);
		closeDialog();
		Log.d(TAG, "success o= " + response.toString());
		try {
			JSONArray arr = response.getJSONArray("list");
			Gson gson = new Gson();
		    productLists = gson.fromJson(arr.toString(), new TypeToken<List<Product>>() {}.getType());
			productAdapter.setProductData(productLists);
//			for (Product pro : productLists) {
//				System.out.println(pro.getIsSave());
//			}
		} catch (JSONException e) {
			CommonUtil.showToast("获取产品失败", ActIntentProduct.this);
			e.printStackTrace();
		}
	}
	 
	@Override
	public void onFailure(Throwable error, String content) {
		Log.i(TAG, "onFailure = " + content);
		CommonUtil.showToast("查询意向产品异常"+content, ActIntentProduct.this);
		closeDialog();
	 }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_intent_product);

		initTopTitle();

		filterProductCondition(R.id.tv_intent_product);
		
//		TextView tv = (TextView) findViewById(R.id.tv_default_sort);
//		tv.setTextColor(getResources().getColor(R.color.white));
//		tv.setBackgroundColor(getResources().getColor(R.color.lightblue));
		
		
		
		initData();
	}

	private void initTopTitle() {

		TextView topTitle = (TextView) findViewById(R.id.textTitle);
		topTitle.setText(getResources().getString(
				R.string.title_customer_marketing));

		ImageView btnBack = (ImageView) findViewById(R.id.imageBack);
		btnBack.setOnClickListener(this);

	}
	
	
	private void initData(){
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(R.string.product_manager_default_sort);
		ids.add(R.string.product_manager_publish_time);
		ids.add(R.string.product_manager_ecpected_income);
		ids.add(R.string.product_manager_commision);
		ids.add(R.string.product_manager_time_limit);
		ids.add(R.string.product_manager_scale);
		ids.add(R.string.product_manager_hotest);
		sortView = new CommSortView(this, ids, (LinearLayout)findViewById(R.id.lt_sort), R.string.product_manager_default_sort);
		sortView.setRefreshSortListener(new RefreshSortListener() {
			
			@Override
			public void doDataSort(int id) {
				if (productAdapter != null) {
					productAdapter.setSortMethodsByType(id);
					if (productLists != null && productLists.size() > 0) {
						productList.setSelection(0);
					}
				}
			}
		});
		
		etProductNameSearch = (EditText) findViewById(R.id.et_search_text);
		etProductNameSearch.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
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
		
		findViewById(R.id.tv_intent_product).setOnClickListener(this);
		findViewById(R.id.tv_all_product).setOnClickListener(this);
		
		 productAdapter = new AdapterProductList(this);
		 ArrayList<Product> datalist = new  ArrayList<Product>();
		 productList = (ListView)findViewById(R.id.listview_intent_prod);
		 productAdapter.setProductData(datalist);
		 productAdapter.setAdapterType(AdapterProductList.MARKET_INTENT_PRODUCT);
		 productList.setAdapter(productAdapter);
		 // 滚动监听
		 productList.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // 滚动中
//					if (View.VISIBLE == prodComFl.getVisibility()) {
////						prodComFl.setVisibility(View.GONE);
//					}
                } else {
                		
//                	if (productAdapter.getSelecCompProd().size() > 0) {
////                		prodComFl.setVisibility(View.VISIBLE);
//					}
                }
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				
			}
		 });
		 
		 productAdapter.setRefreshProdListener(new RefreshProdData() {
			
			@Override
			public void doRefresh(boolean b) {
				
				getData();
			}

			@Override
			public void doRefreshProdCompare() {
			 
//				Map<Integer, Product> map = productAdapter.getSelecCompProd();
				Map<Integer, Product> map = null;
					// 跳转到 产品对比详情界面
				List<Product> list = new ArrayList<Product>();
				if (map.size() > 0) {
					if (prodCompare == null) {
						prodCompare = new PopProdCompare(ActIntentProduct.this, findViewById(R.id.tv_intent_product), productAdapter);
						prodCompare.showPopWindow();
					}
					
				}else if (map.size() == 0) {
					if (prodCompare != null) {
						prodCompare.closePopWindow();
						prodCompare = null;
					}
				}
				for (Map.Entry<Integer, Product> item : map.entrySet()) {
						list.add(item.getValue());
			   	}
				if (list.size() == 1) {
					((TextView)prodCompare.getPopView().findViewById(R.id.tv_product_name1)).setText(list.get(0).getProdName());
					((TextView)prodCompare.getPopView().findViewById(R.id.tv_product_name2)).setText(null);
				}else if (list.size() == 2) {
					((TextView)prodCompare.getPopView().findViewById(R.id.tv_product_name1)).setText(list.get(0).getProdName());
					((TextView)prodCompare.getPopView().findViewById(R.id.tv_product_name2)).setText(list.get(1).getProdName());
				}
				productAdapter.notifyDataSetChanged();
			}

			@Override
			public void resresh4IntentProduct(Product product, boolean isAdd) {
				// TODO Auto-generated method stub
				
			}
		 });
		
		getData();
	    
	}

	private void getData(){
		 try {
			   if (dialog == null) {
				   dialog = new CommonWaitDialog(this, "", R.string.load_data);
			    }
				JSONObject json = new JSONObject();
				json.put("salesId",SPManager.getInstance().getLongValue(SPManager.ID));
				Request request = new Request(RequestDefine.RQ_PRODUCT_GET, RequestType.POST, json, handlerGetProduct);
				CoreManager.getInstance().postRequest(request);
			 } catch (Exception e) {
			}
	}

	private void filterProductCondition(int currentIntent){
		
		switch (currentIntent) {
		case R.id.tv_intent_product:
			
			findViewById(R.id.lt_all_prodct).setVisibility(View.GONE);
			findViewById(R.id.line_1).setVisibility(View.VISIBLE);
			findViewById(R.id.line_6).setVisibility(View.VISIBLE);
			findViewById(R.id.line_7).setVisibility(View.VISIBLE);
			findViewById(R.id.line_2).setVisibility(View.INVISIBLE);
			findViewById(R.id.line_3).setVisibility(View.INVISIBLE);
			findViewById(R.id.line_4).setVisibility(View.INVISIBLE);
			findViewById(R.id.line_5).setVisibility(View.INVISIBLE);
			((TextView)findViewById(R.id.tv_intent_product)).setTextColor(this.getResources().getColor(R.color.market_E2D1B7));
			((TextView)findViewById(R.id.tv_all_product)).setTextColor(this.getResources().getColor(R.color.market_9E9E9E));
			break;
		case R.id.tv_all_product:
			
			findViewById(R.id.lt_all_prodct).setVisibility(View.VISIBLE);
			//findViewById(R.id.lt_filter_1).setVisibility(View.GONE);
			//findViewById(R.id.lt_filter_2).setVisibility(View.GONE);
			//findViewById(R.id.lt_filter_3).setVisibility(View.GONE);
			
			findViewById(R.id.line_5).setVisibility(View.VISIBLE);
			findViewById(R.id.line_2).setVisibility(View.VISIBLE);
			findViewById(R.id.line_4).setVisibility(View.VISIBLE);
			findViewById(R.id.line_7).setVisibility(View.VISIBLE);
			
			findViewById(R.id.line_1).setVisibility(View.INVISIBLE);
			findViewById(R.id.line_6).setVisibility(View.INVISIBLE);
			findViewById(R.id.line_3).setVisibility(View.INVISIBLE);
			((TextView)findViewById(R.id.tv_intent_product)).setTextColor(this.getResources().getColor(R.color.market_9E9E9E));
			((TextView)findViewById(R.id.tv_all_product)).setTextColor(this.getResources().getColor(R.color.market_E2D1B7));
			break;	
		default:
			break;
		}
	}
   
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageBack:
			finish();
			break;
		case R.id.tv_intent_product:
			filterProductCondition(R.id.tv_intent_product);
			break;
		case R.id.tv_all_product:
			filterProductCondition(R.id.tv_all_product);
			break;
		default:
			break;
		}
	}
	
	 
	
	@Override
	protected void onDestroy() {
		 
		super.onDestroy();
		closeDialog();
	}
	
	private void closeDialog(){
		
		if (dialog != null) {
			dialog.clearAnimation();
		}
	}
}
