package com.incito.finshine.activity;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.custom.view.CommonWaitDialog;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.entity.Product;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

/**
 * <dl>
 * <dt>ActProdCompareDetail.java</dt>
 * <dd>Description:产品对比详情</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-5-22 上午10:02:34</dd>
 * </dl>
 * 
 * @author lihs
 */
public class ActProdCompareDetail extends Activity {
	
	private static final String TAG = "ActProdCompareDetail";
	
	public static final String ACTION_PROD = "action_prod.from_intent_prod";
	
	private CommonWaitDialog dialog = null;
	private SPManager spManager;
	private int success =0;
	
	private  List<Product> list = null;
	
	private JsonHttpResponseHandler handlerGetProductDetail = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.d(TAG, "success o= " + response.toString());
			try {
				if(response.getString("status").equals(Request.RESLUT_OK)){
					response = response.getJSONObject("result");
					success ++;
					JSONObject obj = response.getJSONObject("product");
					Gson gson = new Gson();
					Product product = gson.fromJson(obj.toString(), Product.class);
					if (product != null) {
						if (list.get(0).getId().longValue() == product.getId().longValue()) {
							refreshDataByInternet(product,FIRST_PROD_DETAIL);
						}else{
							refreshDataByInternet(product,SECOND_PROD_DETAIL);
						}
					}
					closeDialog();
				}
			} catch (JSONException e) {
				closeDialog();
				e.printStackTrace();
			}
		}


		@Override
		public void onFailure(Throwable error, String content) {
			
			success ++;
			Log.i(TAG, "onFailure = " + content);
			if (error instanceof ConnectTimeoutException
				|| error instanceof SocketTimeoutException
				|| error instanceof SocketException){
				CommonUtil.showToast(HttpUtils.TIMEOUT, ActProdCompareDetail.this);
		    }else if(error instanceof UnknownHostException){
		    	CommonUtil.showToast(HttpUtils.UNKNOWHOST, ActProdCompareDetail.this);
		    }else if(error instanceof ClientProtocolException){
		    	CommonUtil.showToast(HttpUtils.INTERNET_INTERRUPT, ActProdCompareDetail.this);
		    }else{
		    	CommonUtil.showToast(content, ActProdCompareDetail.this);
		    }
			closeDialog();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_prod_compare_detail);
		
		initTopTitle();
		
		initData();
 
	}
	
	
	private void initTopTitle() {
		spManager = SPManager.getInstance();
		TextView topTitle = (TextView) findViewById(R.id.textTitle);
		topTitle.setText(getResources().getString(R.string.title_prod_compare_detail));

		ImageView btnBack = (ImageView) findViewById(R.id.imageBack);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}
	
	 
	  @SuppressWarnings("unchecked")
	private void initData(){
			
		    list = (List<Product>)getIntent().getSerializableExtra(ACTION_PROD);
		    Log.i(TAG, "list size is " + list.size());
		    if (list != null && list.size() > 0) {
		    	if (dialog == null) {
		    		dialog = new CommonWaitDialog(this, "", R.string.load_data);
		 		}
		    	for (Product product : list) {
		   		   Request getProductDetail = new Request(RequestDefine.RQ_PRODUCT_DETAIL_GET,spManager.getLongValue(SPManager.ID),product.getId(), RequestType.GET, null, handlerGetProductDetail);
		 		   CoreManager.getInstance().postRequest(getProductDetail);
				}
			}
		}
	
 
	private static final int FIRST_PROD_DETAIL =1;
	private static final int SECOND_PROD_DETAIL =2;
	
	 private void refreshDataByInternet(Product product,int refreshProd){ 
		 
		String[] proTypeLevelFirst = (String[])getResources().getStringArray(
	  				R.array.product__type);;// 一级类别
	    String[] proTypeLevelSecondAffince = (String[]) getResources()
	  				.getStringArray(R.array.product__affiance_type);;// 二级类别 基金资管
	    String[] proTypeLevelSecondFund = (String[]) getResources()
	  				.getStringArray(R.array.product__fund_type);;// 二级类别 基金
	  	long type1 = product.getProdFirstType();
	  	long type2 = product.getProdSecondtype();
	  	StringBuffer sb = new StringBuffer();
	  	try {
	  		sb.append((type1 == 1) ? (proTypeLevelSecondFund[(int) (type2-1)]) : (proTypeLevelSecondAffince[(int) (type2-1)]));
		} catch (Exception e) {
			sb.append("");
		}
	  	
		switch (refreshProd) {
//		case FIRST_PROD_DETAIL:
//			 try {
//				  Long collected = product.getIsSave();
//				  if (collected != null && collected.longValue() == AdapterProductList.COLLECTED) {
//					  ((ImageView)findViewById(R.id.iv_coll_prod1)).setImageResource(R.drawable.ic_has_collected);
//				 }else{
//					 ((ImageView)findViewById(R.id.iv_coll_prod1)).setImageResource(R.drawable.ic_collect);
//				 }
//				  
//			  } catch (Exception e) {
//				  
//			}
//			// 刷新第一个产品详情
//			((TextView)findViewById(R.id.tv_prod_name_1)).setText(product.getProdName());
//			((TextView)findViewById(R.id.tv_prod_light_1)).setText(product.getProdHighLights());
//			try {
//				((TextView)findViewById(R.id.tv_prod_type_1)).setText(proTypeLevelFirst[(int) (type1-2)] + sb.toString());
//			} catch (Exception e) {
//				 
//			}
//			((TextView)findViewById(R.id.tv_prod_states_1)).setText(CommonUtil.getProductStates(product.getProdStatus()));
//			((TextView)findViewById(R.id.tv_prod_publish_time_1)).setText(
//					 DateUtil.getDateTimeByFormatAndMs(product.getProdOnDateTime(), DateUtil.FORMAT_YYYY_MM_DD)+" - "+
//							 DateUtil.getDateTimeByFormatAndMs(product.getProdEnDateTime(), DateUtil.FORMAT_YYYY_MM_DD));
//			((TextView)findViewById(R.id.tv_prod_scale_1)).setText(product.getProdSize()+"");
//			((TextView)findViewById(R.id.tv_prod_deadline_1)).setText(product.getProdTime()+"年");
//			((TextView)findViewById(R.id.tv_prod_invest_cankao_1)).setText(product.getProdYieldFloating()+"%");
//			((TextView)findViewById(R.id.tv_prod_risk1)).setText(product.getProdRisk());
//			((TextView)findViewById(R.id.tv_prod_buystart_1)).setText(product.getProdStart()+"万");
//			((TextView)findViewById(R.id.tv_prod_inc_shareway1)).setText(product.getProdIncomeType());
//			
//			((TextView)findViewById(R.id.tv_prod_inc_rate1)).setText(product.getProdYieldFixed()+"%");
//			
//			break;
//		 case SECOND_PROD_DETAIL:
//			 try {
//				  Long collected = product.getIsSave();
//				  if (collected != null && collected.longValue() == AdapterProductList.COLLECTED) {
//					  ((ImageView)findViewById(R.id.iv_coll_prod1)).setImageResource(R.drawable.ic_has_collected);
//				 }else{
//					 ((ImageView)findViewById(R.id.iv_coll_prod1)).setImageResource(R.drawable.ic_collect);
//				 }
//				  
//			  } catch (Exception e) {
//				  
//			}
//			((TextView)findViewById(R.id.tv_prod_name_2)).setText(product.getProdName());
//			((TextView)findViewById(R.id.tv_prod_light_2)).setText(product.getProdHighLights());
//			try {
//				((TextView)findViewById(R.id.tv_prod_type_2)).setText(proTypeLevelFirst[(int) (type1-2)] + sb.toString());
//			} catch (Exception e) {
//				 
//			}
//			((TextView)findViewById(R.id.tv_prod_states_2)).setText(CommonUtil.getProductStates(product.getProdStatus()));
//			((TextView)findViewById(R.id.tv_prod_publish_time_2)).setText(
//						 DateUtil.getDateTimeByFormatAndMs(product.getProdOnDateTime(), DateUtil.FORMAT_YYYY_MM_DD)+" 到 "+
//								 DateUtil.getDateTimeByFormatAndMs(product.getProdEnDateTime(), DateUtil.FORMAT_YYYY_MM_DD));
//			((TextView)findViewById(R.id.tv_prod_scale_2)).setText(product.getProdSize()+"");
//			((TextView)findViewById(R.id.tv_prod_deadline_2)).setText(product.getProdTime()+"年");
//			((TextView)findViewById(R.id.tv_prod_invest_cankao_2)).setText(product.getProdYieldFloating()+"%");
//			((TextView)findViewById(R.id.tv_prod_risk2)).setText(product.getProdRisk());
//			((TextView)findViewById(R.id.tv_prod_buystart_2)).setText(product.getProdStart()+"万");
//			((TextView)findViewById(R.id.tv_prod_inc_shareway2)).setText(product.getProdIncomeType());
//			((TextView)findViewById(R.id.tv_prod_inc_rate1)).setText(product.getProdYieldFixed()+"%");
//			break;
		case FIRST_PROD_DETAIL:
			// 刷新第一个产品详情
			((TextView)findViewById(R.id.tv_prod_name_1)).setText(product.getProdName());
			((TextView)findViewById(R.id.tv_prod_light_1)).setText(product.getProdHighLights());
			((TextView)findViewById(R.id.tv_prod_type_1)).setText(product.getProdFirstTypeStr() + product.getProdSecondtypeStr());
			((TextView)findViewById(R.id.tv_prod_states_1)).setText(CommonUtil.getProductStates(product.getProdStatus()));
			((TextView)findViewById(R.id.tv_prod_publish_time_1)).setText(
					 DateUtil.getDateTimeByFormatAndMs(product.getProdOnDateTime(), DateUtil.FORMAT_YYYY_MM_DD)+" 到 "+
							 DateUtil.getDateTimeByFormatAndMs(product.getProdEnDateTime(), DateUtil.FORMAT_YYYY_MM_DD));
			((TextView)findViewById(R.id.tv_prod_scale_1)).setText(product.getProdSize()+"");
			((TextView)findViewById(R.id.tv_prod_deadline_1)).setText(product.getProdTime()+"月");
			((TextView)findViewById(R.id.tv_prod_invest_cankao_1)).setText(product.getProdYieldFixed()+"%");
			((TextView)findViewById(R.id.tv_prod_risk1)).setText(product.getProdRisk());
			((TextView)findViewById(R.id.tv_prod_buystart_1)).setText(product.getProdStart()+"万");
			((TextView)findViewById(R.id.tv_prod_inc_shareway1)).setText(product.getProdIncomeType() + "个月");
			
			
			break;
		 case SECOND_PROD_DETAIL:
			 
			((TextView)findViewById(R.id.tv_prod_name_2)).setText(product.getProdName());
			((TextView)findViewById(R.id.tv_prod_light_2)).setText(product.getProdHighLights());
			((TextView)findViewById(R.id.tv_prod_type_2)).setText(product.getProdFirstTypeStr() + product.getProdSecondtypeStr());
			((TextView)findViewById(R.id.tv_prod_states_2)).setText(CommonUtil.getProductStates(product.getProdStatus()));
			((TextView)findViewById(R.id.tv_prod_publish_time_2)).setText(
						 DateUtil.getDateTimeByFormatAndMs(product.getProdOnDateTime(), DateUtil.FORMAT_YYYY_MM_DD)+" 到 "+
								 DateUtil.getDateTimeByFormatAndMs(product.getProdEnDateTime(), DateUtil.FORMAT_YYYY_MM_DD));
			((TextView)findViewById(R.id.tv_prod_scale_2)).setText(product.getProdSize()+"");
			((TextView)findViewById(R.id.tv_prod_deadline_2)).setText(product.getProdTime()+"月");
			((TextView)findViewById(R.id.tv_prod_invest_cankao_2)).setText(product.getProdYieldFixed()+"%");
			((TextView)findViewById(R.id.tv_prod_risk2)).setText(product.getProdRisk());
			((TextView)findViewById(R.id.tv_prod_buystart_2)).setText(product.getProdStart()+"万");
			((TextView)findViewById(R.id.tv_prod_inc_shareway2)).setText(product.getProdIncomeType() + "个月");
			break;
		default:
			break;
		}
	 }
	
	private  void closeDialog(){
		if (success == 2) {
			if (dialog != null) {
				dialog.clearAnimation();
			}
		}
	}
}
