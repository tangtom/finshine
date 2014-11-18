package com.incito.finshine.activity;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.core.util.AppToast;
import com.custom.view.CommonWaitDialog;
import com.custom.view.FirstPagePop;
import com.custom.view.PopCustomerService;
import com.custom.view.PopPointerAnalyse;
import com.custom.view.PopPointerMarket;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterPointerCommission;
import com.incito.finshine.entity.Product;
import com.incito.finshine.entity.Props;
import com.incito.finshine.entity.PropsAvailable;
import com.incito.finshine.entity.PropsUsedItemWSEntity;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

public class ActQueryCommission extends Activity implements OnClickListener{

	private final String TAG = ActQueryCommission.class.getSimpleName();
	private ImageView imageBack,imgPointerAnalyse,imgPointerHeader,imgPointerMarket;
	private ListView listUseCommission;
	private AdapterPointerCommission adapterPointerCommission;
	private PropsAvailable propsAvailable;
	private SPManager spManager;
	private List<PropsUsedItemWSEntity> propLists;
	private TextView textTitle,textFixedCommissionRatio,textExtraCommissionRatio,textTotalCommissionRatio,textCommissionName;
	private Button buttonCommission,btnSure;
	private long productId;
	private String fromClass;
	private CommonWaitDialog dialog = null;
	private JsonHttpResponseHandler mhandler = new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(JSONObject response) {

			super.onSuccess(response);
		}
		
		 
		public void onSuccess(int statusCode, JSONObject response) {
			closeDialog();
			try {
				if (Request.RESLUT_OK.equals(response.optString("status"))) {
					response = response.getJSONObject("result"); 
					Log.i(TAG,response.toString());
					int user_fk = response.getInt("user_fk");
					double fixedCommissionRatio = response.getDouble("fixedCommissionRatio");
					double extraCommissionRatio = response.getDouble("extraCommissionRatio");
					double totalCommissionRatio = response.getDouble("totalCommissionRatio");
					JSONArray items = response.getJSONArray("items");
					Gson gson = new Gson();
					for(int i = 0;i < items.length();i ++){
						JSONObject json = items.getJSONObject(i);
						long master_fk = json.getLong("master_fk");
						double propertyValue = json.getDouble("propertyValue");
						int maxQty = json.getInt("maxQty");
						int qtyOfUsed = json.getInt("qtyOfUsed");
						int qtyOfRemaining = json.getInt("qtyOfRemaining");
						double subtotalRatio = json.getDouble("subtotalRatio");
						Props prop = gson.fromJson(json.getJSONObject("props").toString(),Props.class);
						PropsUsedItemWSEntity puiwse = new PropsUsedItemWSEntity();
						puiwse.setMaster_fk(master_fk);
						puiwse.setPropertyValue(propertyValue);
						puiwse.setMaxQty(maxQty);
						puiwse.setQtyOfRemaining(qtyOfRemaining);
						puiwse.setQtyOfUsed(qtyOfUsed);
						puiwse.setSubtotalRatio(subtotalRatio);
						puiwse.setProps(prop);
						propLists.add(puiwse);
						
					}
				   // propLists = gson.fromJson(items.toString(), new TypeToken<List<PropsUsedItemWSEntity>>() {}.getType());
				    Product product = gson.fromJson(response.getJSONObject("product").toString(), Product.class);
				    propsAvailable.setUser_fk(user_fk);
					propsAvailable.setFixedCommissionRatio(fixedCommissionRatio);
					propsAvailable.setExtraCommissionRatio(extraCommissionRatio);
					propsAvailable.setTotalCommissionRatio(totalCommissionRatio);
					propsAvailable.setProduct(product);
					propsAvailable.setItems(propLists);
					textCommissionName.setText(product.getProdName());
					textExtraCommissionRatio.setText("+" + String.valueOf(new BigDecimal(propsAvailable.getExtraCommissionRatio()).setScale(2,BigDecimal.ROUND_HALF_UP)) + "%");
					textFixedCommissionRatio.setText(String.valueOf(new BigDecimal(propsAvailable.getFixedCommissionRatio()).setScale(2,BigDecimal.ROUND_HALF_UP)) + "%");
					textTotalCommissionRatio.setText(String.valueOf(new BigDecimal(propsAvailable.getTotalCommissionRatio()).setScale(2,BigDecimal.ROUND_HALF_UP)) + "%");
					adapterPointerCommission.notifyDataSetChanged();
				}
			} catch (Exception e) {
				Log.i(TAG, e.getMessage());
				e.printStackTrace();
			}
			super.onSuccess(response);
		
			
		};

		@Override
		public void onFailure(Throwable error, String content) {
			closeDialog();
			Log.i(TAG, content);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_selected_used_commission);
		
		initView();
	}
	
	private void initPullPop() {
		FirstPagePop firstPage = new FirstPagePop(this,
				findViewById(R.id.imageNavigate));
		firstPage.showPopWindow();
	}

	public void initView(){
		productId = getIntent().getExtras().getLong("productId");
		fromClass = getIntent().getExtras().getString("fromClass");
		spManager = SPManager.getInstance();
		buttonCommission = (Button)findViewById(R.id.buttonCommission);
		buttonCommission.setOnClickListener(this);
		btnSure = (Button)findViewById(R.id.btnSure);
		btnSure.setOnClickListener(this);
		textTitle = (TextView)findViewById(R.id.textTitle);
		textTitle.setText(this.getString(R.string.query_commission_used));
		textExtraCommissionRatio = (TextView)findViewById(R.id.textExtraCommissionRatio);
		textFixedCommissionRatio = (TextView)findViewById(R.id.textFixedCommissionRatio);
		textTotalCommissionRatio = (TextView)findViewById(R.id.textTotalCommissionRatio);
		textCommissionName = (TextView)findViewById(R.id.textCommissionName);
		imageBack = (ImageView)findViewById(R.id.imageBack);
		imageBack.setOnClickListener(this);
		imgPointerAnalyse = (ImageView)findViewById(R.id.imgPointerAnalyse);
		imgPointerAnalyse.setOnClickListener(this);
		imgPointerHeader = (ImageView)findViewById(R.id.imgPointerHeader);
		imgPointerHeader.setOnClickListener(this);
		imgPointerMarket = (ImageView)findViewById(R.id.imgPointerMarket);
		imgPointerMarket.setOnClickListener(this);
		listUseCommission = (ListView)findViewById(R.id.listUseCommission);
		propsAvailable = new PropsAvailable();
		findViewById(R.id.imageNavigate).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initPullPop();
			}
		});
		propLists = new ArrayList<PropsUsedItemWSEntity>();
		adapterPointerCommission = new AdapterPointerCommission(this,propLists,false);
		listUseCommission.setAdapter(adapterPointerCommission);
		Request request = new Request(RequestDefine.POINTER_RQ_PROPS_USED,spManager.getLongValue(SPManager.ID),getIntent().getExtras().getLong("productId"), RequestType.GET, null, mhandler);
		CoreManager.getInstance().postRequest(request);
		if(dialog == null){
			dialog = new CommonWaitDialog(this, "", R.string.load_data);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.imageBack:
//			if(fromClass.equals("detail"))
//			{
//				Intent intent = new Intent(this,ActProductDetail.class);
//				intent.putExtra("product_id", productId).putExtra(ActProductDetail.FLAG, 1);
//				startActivity(intent);
//			}
//			else
//			{
//				Intent i = new Intent(this,ActProductManagement.class);
//				//i.putExtra("product_id", productId);
//				startActivity(i);
//			}
			this.finish();
			break;
		case R.id.buttonCommission:
//			Intent intent = new Intent(this,ActPointerCommission.class);
//			startActivity(intent);
			break;
		case R.id.imgPointerAnalyse:
			PopPointerAnalyse popPointerAnalyse = new PopPointerAnalyse(this,v);
			popPointerAnalyse.showPopWindow();
			break;
		case R.id.imgPointerHeader:
//			PopCustomerService popCustomerService = new PopCustomerService(this,v);
//			popCustomerService.showPopWindow();
			AppToast.ShowToast(R.string.com_coming_soon);
			break;
		case R.id.imgPointerMarket:
//			PopPointerMarket popPointerMarket = new PopPointerMarket(this,v);
//			popPointerMarket.showPopWindow();
			AppToast.ShowToast(R.string.com_coming_soon);
			break;
		case R.id.btnSure:
//			if(fromClass.equals("detail"))
//			{
//				Intent intent = new Intent(this,ActProductDetail.class);
//				intent.putExtra("product_id", productId);
//				startActivity(intent);
//			}
//			else
//			{
//				Intent i = new Intent(this,ActProductManagement.class);
//				//i.putExtra("product_id", productId);
//				startActivity(i);
//			}
			this.finish();
			break;
		default:break;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	private  void closeDialog(){
		if (dialog != null) {
			dialog.clearAnimation();
			dialog = null;
		}
	}
}
