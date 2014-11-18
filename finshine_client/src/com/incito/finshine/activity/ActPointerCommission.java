package com.incito.finshine.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
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
import com.custom.view.PopUseCommission;
import com.custom.view.PopUseCommission.PopUseCommissionListener;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterPointerCommission;
import com.incito.finshine.activity.adapter.AdapterPointerCommission.ActPointerCommissionListener;
import com.incito.finshine.entity.Product;
import com.incito.finshine.entity.Props;
import com.incito.finshine.entity.PropsAvailable;
import com.incito.finshine.entity.PropsUsedItemWSEntity;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.event.EventBus;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

public class ActPointerCommission extends Activity implements OnClickListener,PopUseCommissionListener,ActPointerCommissionListener{

	private final String TAG = ActPointerCommission.class.getSimpleName();
	private Button buttonCommission;
	private ImageView imageBack,imgPointerAnalyse,imgPointerHeader,imgPointerMarket;
	private ListView listUseCommission;
	private AdapterPointerCommission adapterPointerCommission;
	private Button btnSure,btnCancel;
	private SPManager spManager;
	private List<PropsUsedItemWSEntity> propLists;
	private CommonWaitDialog dialog = null;
	private boolean isUse = false;
	private PropsAvailable propsAvailable;
	private long productId;
	private String fromClass;
	private TextView textCommissionName;
	private TextView textTitle,textFixedCommissionRatio,textExtraCommissionRatio,textTotalCommissionRatio,txtNoCommission;
	private JsonHttpResponseHandler mHandler = new JsonHttpResponseHandler(){

		@Override
		public void onFailure(Throwable e, JSONObject errorResponse) {
			Log.i(TAG, errorResponse.toString() + "--------------->	");
			super.onFailure(e, errorResponse);
		}
		@Override
		public void onSuccess(JSONObject response) {
			super.onSuccess(response);
			Log.i(TAG, "1 " + response.toString());
		}
		@Override
		public void onSuccess(int statusCode, JSONObject response) {
			closeDialog();
			Log.i(TAG, "2 " + response.toString());
			if(!isUse){
				try {
					if (Request.RESLUT_OK.equals(response.optString("status"))) {
						Log.i(TAG,response.toString());
						response = response.getJSONObject("result");
						int user_fk = response.getInt("user_fk");
						double fixedCommissionRatio = response.getDouble("fixedCommissionRatio");
						double extraCommissionRatio = response.getDouble("extraCommissionRatio");
						double totalCommissionRatio = response.getDouble("totalCommissionRatio");
						JSONArray items = response.getJSONArray("items");
						Gson gson = new Gson();
						if(items.length() == 0)
						{
							listUseCommission.setVisibility(View.GONE);
							txtNoCommission.setVisibility(View.VISIBLE);
						}
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
					    Product product = gson.fromJson(response.getJSONObject("product").toString(), Product.class);
					    propsAvailable.setUser_fk(user_fk);
						propsAvailable.setFixedCommissionRatio(fixedCommissionRatio);
						propsAvailable.setExtraCommissionRatio(extraCommissionRatio);
						propsAvailable.setTotalCommissionRatio(totalCommissionRatio);
						propsAvailable.setProduct(product);
						propsAvailable.setItems(propLists);
						textCommissionName.setText(product.getProdName());
						textExtraCommissionRatio.setText("+" + String.valueOf(new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP)) + "%");
						textFixedCommissionRatio.setText(new BigDecimal(product.getProdCommissionBase()).setScale(2,BigDecimal.ROUND_HALF_UP) + "%");
						textTotalCommissionRatio.setText(new BigDecimal(product.getProdCommissionBase()).setScale(2,BigDecimal.ROUND_HALF_UP) + "%");
						adapterPointerCommission.notifyDataSetChanged();
					}else
					{
						listUseCommission.setVisibility(View.GONE);
						txtNoCommission.setVisibility(View.VISIBLE);
					}
				} catch (Exception e) {
//					Log.i(TAG, e.getMessage());
					e.printStackTrace();
				}
			}else{
				if(Request.RESLUT_OK.equals(response.optString("status")))
				{
					if(fromClass.equals("detail"))
					{
						Intent intent = new Intent(ActPointerCommission.this,ActProductDetail.class);
						intent.putExtra("product_id", productId);
						intent.putExtra(ActProductDetail.FLAG, 1);
						startActivity(intent);
					}
					else
					{
						Intent i = new Intent(ActPointerCommission.this,ActProductManagement.class);
						//i.putExtra("product_id", productId);
						startActivity(i);
					}
					ActPointerCommission.this.finish();
				}
			}
		}
		@Override
		public void onFailure(Throwable error, String content) {
			closeDialog();
			Log.i(TAG, " ++++++++++++++++++++ ");
			Log.i(TAG, content);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_use_commission);
		EventBus.getDefault().registerSticky(this);
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
		propsAvailable = new PropsAvailable();
		spManager = SPManager.getInstance();
		textTitle = (TextView)findViewById(R.id.textTitle);
		textTitle.setText(this.getString(R.string.user_commission));
		textExtraCommissionRatio = (TextView)findViewById(R.id.textExtraCommissionRatio);
		textFixedCommissionRatio = (TextView)findViewById(R.id.textFixedCommissionRatio);
		textTotalCommissionRatio = (TextView)findViewById(R.id.textTotalCommissionRatio);
		textCommissionName = (TextView)findViewById(R.id.textCommissionName);
		txtNoCommission = (TextView)findViewById(R.id.txtNoCommission);
		buttonCommission = (Button)findViewById(R.id.buttonCommission);
		buttonCommission.setOnClickListener(this);
		imageBack = (ImageView)findViewById(R.id.imageBack);
		imageBack.setOnClickListener(this);
		findViewById(R.id.imageNavigate).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initPullPop();
			}
		});
		imgPointerAnalyse = (ImageView)findViewById(R.id.imgPointerAnalyse);
		imgPointerAnalyse.setOnClickListener(this);
		imgPointerHeader = (ImageView)findViewById(R.id.imgPointerHeader);
		imgPointerHeader.setOnClickListener(this);
		imgPointerMarket = (ImageView)findViewById(R.id.imgPointerMarket);
		imgPointerMarket.setOnClickListener(this);
		listUseCommission = (ListView)findViewById(R.id.listUseCommission);
		propLists = new ArrayList<PropsUsedItemWSEntity>();
		adapterPointerCommission = new AdapterPointerCommission(this,propLists,true);
		listUseCommission.setAdapter(adapterPointerCommission);
		btnSure = (Button)findViewById(R.id.btnSure);
		btnSure.setOnClickListener(this);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
		Request request = new Request(RequestDefine.POINTER_RQ_PROPS_AVALIABLE,spManager.getLongValue(SPManager.ID),productId, RequestType.GET, null, mHandler);
		CoreManager.getInstance().postRequest(request);
		if(dialog == null){
			dialog = new CommonWaitDialog(this, "", R.string.load_data);
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.buttonCommission:
			Intent in = new Intent(this,ActFinshineHomePage.class);
			startActivity(in);
			break;
		case R.id.imageBack:
			if(fromClass.equals("detail"))
			{
				Intent intent = new Intent(this,ActProductDetail.class);
				intent.putExtra("product_id", productId).putExtra(ActProductDetail.FLAG, 1);
				startActivity(intent);
			}
			else
			{
				Intent i = new Intent(this,ActProductManagement.class);
				//i.putExtra("product_id", productId);
				startActivity(i);
			}
			this.finish();
			break;
		case R.id.btnSure:
			List<PropsUsedItemWSEntity> pList = new ArrayList<PropsUsedItemWSEntity>();
			for(PropsUsedItemWSEntity p : propLists){
				if(p.getQtyOfUsed() > 0){
					pList.add(p);
				}
			}
			propsAvailable.setItems(pList);
			if(!pList.isEmpty())
			{
				PopUseCommission popUseCommission = new PopUseCommission(this,v,propsAvailable);
				popUseCommission.showPopWindow();
			}
			else
			{
				CommonUtil.showToast("请选择佣金券", this);
			}
			break;
		case R.id.btnCancel:
			if(fromClass.equals("detail"))
			{
				Intent intent = new Intent(this,ActProductDetail.class);
				intent.putExtra("product_id", productId).putExtra(ActProductDetail.FLAG, 1);
				startActivity(intent);
			}
			else
			{
				Intent i = new Intent(this,ActProductManagement.class);
				//i.putExtra("product_id", productId);
				startActivity(i);
			}
			this.finish();
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
		default:break;
		}
	}

	public void useCommission(){
		isUse = true;
		JSONArray array = new JSONArray();
		List<PropsUsedItemWSEntity> propLists = propsAvailable.getItems();
		Product product = propsAvailable.getProduct();
		for(int i = 0;i < propLists.size();i ++){
			Props props = propLists.get(i).getProps();
			JSONObject jParams = new JSONObject();
			try {
				jParams.put("props_id", props.getId()).put("qtyOfUsed", propLists.get(i).getQtyOfUsed());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			array.put(jParams);
		}
		/*JSONObject jParams1 = new JSONObject();
		JSONObject jParams2 = new JSONObject();
		try {
			jParams1.put("product_id", 4).put("props_id", 12).put("qtyOfUsed", 1);
			jParams2.put("product_id", 4).put("props_id", 13).put("qtyOfUsed", 1);
			array.put(jParams1);
			array.put(jParams2);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		Log.i(TAG, array.toString());
		Request request = new Request(RequestDefine.POINTER_RQ_PROPS_USE,spManager.getLongValue(SPManager.ID),product.getId(),RequestType.POST,array,mHandler,0);
		CoreManager.getInstance().postRequest(request);
	}
	
	private  void closeDialog(){
		if (dialog != null) {
			dialog.clearAnimation();
			dialog = null;
		}
	}


	@Override
	public void sendMsgToServer() {
		if(dialog == null){
			dialog = new CommonWaitDialog(this, "", R.string.load_data);
		}
		useCommission();
	}

	public void onEvent(List<PropsUsedItemWSEntity> propLists)
	{
		double useTotal = 0.0;
		for(int i = 0;i < propLists.size();i ++){
			useTotal += propLists.get(i).getQtyOfUsed() * propLists.get(i).getPropertyValue();
		}
		textExtraCommissionRatio.setText("+" + new BigDecimal(useTotal).setScale(2,BigDecimal.ROUND_HALF_UP) + "%");
		textTotalCommissionRatio.setText(new BigDecimal(useTotal + propsAvailable.getProduct().getProdCommissionBase()).setScale(2,BigDecimal.ROUND_HALF_UP) + "%");
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			if(fromClass.equals("detail"))
			{
				Intent intent = new Intent(this,ActProductDetail.class);
				intent.putExtra("product_id", productId).putExtra(ActProductDetail.FLAG, 1);
				startActivity(intent);
			}
			else
			{
				Intent i = new Intent(this,ActProductManagement.class);
				//i.putExtra("product_id", productId);
				startActivity(i);
			}
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void setTextView(List<PropsUsedItemWSEntity> propLists) {
//		int useTotal = 0;
//		for(int i = 0;i < propLists.size();i ++){
//			useTotal += propLists.get(i).getQtyOfUsed() * propLists.get(i).getSubtotalRatio();
//		}
//		textExtraCommissionRatio.setText("+" + new BigDecimal(useTotal).setScale(2,BigDecimal.ROUND_HALF_UP) + "%");
//		textTotalCommissionRatio.setText(new BigDecimal(useTotal + propsAvailable.getProduct().getProdCommissionBase()).setScale(2,BigDecimal.ROUND_HALF_UP) + "%");
	}
}
