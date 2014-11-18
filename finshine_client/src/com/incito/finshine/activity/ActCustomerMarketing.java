package com.incito.finshine.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.core.util.SharedUtil;
import com.custom.view.FirstPagePop;
import com.custom.view.PopAddMarketCustomer;
import com.custom.view.PopCustomerMarketDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterCustomerMarketing;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.ExpireCustomer;
import com.incito.finshine.entity.FirstPageCount;
import com.incito.finshine.entity.MarKetCustomer;
import com.incito.finshine.manager.JsonParse;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;

/**
 * 
 * <dl>
 * <dt>ActCustomerMarketing.java</dt>
 * <dd>Description:客户营销首页</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月10日 下午8:30:04</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class ActCustomerMarketing extends Activity {

	private ExpandableListView elistview;
	private AdapterCustomerMarketing marketCs;
	private HttpUtils httpUtil = null;
	private TextView et_search_text;
	private ImageView search_icon;
	Button btnAddCustomer;
	private boolean isCheck = false;
	private Customer focusCustomer = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_customer_marketing);
		
		showHelpCustom() ;

		TextView title = (TextView) findViewById(R.id.textTitle);
		title.setText(R.string.title_customer_marketing);
		ImageView back = (ImageView) findViewById(R.id.imageBack);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ActCustomerMarketing.this,ActFinshineHomePage.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
				ActCustomerMarketing.this.finish();
			}
		});

		// 显示增加联系人 Dialog
		btnAddCustomer = (Button) findViewById(R.id.btn_add_customer);

		
		btnAddCustomer.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if(arg1.getAction()==MotionEvent.ACTION_DOWN){
					Drawable ic_more;
					Resources res = getResources();
					ic_more = res.getDrawable(R.drawable.add_shape1);
					ic_more.setBounds(0, 0, ic_more.getMinimumWidth(), ic_more.getMinimumHeight());
					btnAddCustomer.setCompoundDrawables(ic_more, null, null, null);
				}
              if(arg1.getAction()==MotionEvent.ACTION_UP){
            	  Drawable ic_more;
					Resources res = getResources();
					ic_more = res.getDrawable(R.drawable.add_shape);
					ic_more.setBounds(0, 0, ic_more.getMinimumWidth(), ic_more.getMinimumHeight());
					btnAddCustomer.setCompoundDrawables(ic_more, null, null, null);
				}
				return false;
			}
		});

		btnAddCustomer.setOnClickListener(new OnClickListener() {
			
					@Override
					public void onClick(final View v) {
						
						/*btnAddCustomer.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_more, 0, 0, 0);*/
						PopAddMarketCustomer popAddView = new PopAddMarketCustomer(
								ActCustomerMarketing.this);
						popAddView.setListener(new PopAddMarketCustomer.RefreshCustomerListener() {

							@Override
							public void doRefresh(final Customer c) {
								focusCustomer = c;
							}
						});
						popAddView.showAddInvestDlg();
					}
		});
		
		// 营销客户
		ImageView imgMarket = (ImageView) findViewById(R.id.imgMarket);
		imgMarket.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
			   if (expireCustomer == null || expireCustomer.size() < 1) {
					CommonUtil.showToast("没有发现营销机会", ActCustomerMarketing.this);
					return;
				}
			   
				Intent i = new Intent();
				i.putExtra(ActCustomerMarket.IS_FROM_MARKET, (Serializable)expireCustomer);
				i.setClass(ActCustomerMarketing.this, ActCustomerMarket.class);
				startActivity(i);
			}
		});
		
		// 订单列表
		ImageView imgHistoricOrder = (ImageView) findViewById(R.id.imgHistoricOrder);
		imgHistoricOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(ActCustomerMarketing.this, ActCustomerMarketOrderManager.class);
				startActivity(i);
			}
		});
		
		// 显示详细的数量
		ImageView imgAnalise = (ImageView) findViewById(R.id.imageAnalyse);
		imgAnalise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				PopCustomerMarketDetail pop = new PopCustomerMarketDetail(ActCustomerMarketing.this, v,PopCustomerMarketDetail.TOP_2_BOTTOM);
				pop.setCsOrder(firstPageCount);
				pop.showPopWindow();
			}
		});
		
		et_search_text = (TextView)findViewById(R.id.et_search_text);//搜索输入框
		et_search_text.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int count) {
				// TODO Auto-generated method stub
				search_icon.setImageDrawable(cs.length() == 0 ? getResources().getDrawable(R.drawable.product_search_icon) : getResources().getDrawable(R.drawable.delete));
				if(marketCs != null)
				{
					marketCs.getFilter().filter(cs);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		search_icon = (ImageView)findViewById(R.id.search_icon);//搜素输入框中的按钮
		search_icon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(search_icon.getDrawable().getCurrent().getConstantState() == getResources().getDrawable(R.drawable.delete).getConstantState())
				{
					et_search_text.setText("");
				}
			}
		});
		
		CheckBox cb = (CheckBox)findViewById(R.id.educationBox);
		cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean b) {
				isCheck = b;
				initHttpType(CS_MARKET_LIST);
			}
		});

		elistview = (ExpandableListView) findViewById(R.id.elist);
		// 这里要把系统自带的图标去掉
		elistview.setGroupIndicator(null);
		marketCs = new AdapterCustomerMarketing(this);
		elistview.setAdapter(marketCs);
		List<MarKetCustomer> marCs = new ArrayList<MarKetCustomer>();
		marketCs.setMarketCsList(marCs);
		marketCSCount = (Button)findViewById(R.id.imgMarketCount);
		
		findViewById(R.id.imageNavigate).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initPullPop();
			}
		});
	}
	// add by SGDY at 2014/8/13 19:37
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK && event.getRepeatCount() == 0)//按下的如果是BACK，同时没有重复
		{
			startActivity(new Intent(this,ActFinshineHomePage.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void initPullPop(){
		 
		FirstPagePop firstPage = new FirstPagePop(this, findViewById(R.id.imageNavigate));
		firstPage.setPosition(3);
		firstPage.showPopWindow();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		initHttpType(CS_EXPIRE_LIST);
		initHttpType(CS_MARKET_LIST);
		initHttpType(CS_MARKET_COUNT);
	}
	
	private Button marketCSCount;
	private static final int CS_MARKET_COUNT = 2;
	private static final int CS_MARKET_LIST = 3;
	private static final int CS_EXPIRE_LIST = 4;// 到期客户列表是统计要营销客户列表的个数
	private List<MarKetCustomer> marketCsList= null;
	// 首页统计
	private FirstPageCount firstPageCount = null;
	private List<ExpireCustomer> expireCustomer = null;
	
	private void initHttpType(final int currentDataType){
		
		try {
			JSONObject params = new JSONObject();
			int reqId = 0;
			RequestType reqType = RequestType.POST;
			switch (currentDataType) {
			case CS_MARKET_COUNT:
				// 营销客户的统计
				params.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
				params.put("expirationDays", 90);
				Log.i("", params.toString());
				reqId = RequestDefine.MARKET_RQ_CS_MARKET_COUNT;
				httpUtil = new HttpUtils(this, reqId, reqType, params);
				httpUtil.setShowDiloag(false);
				break;
			case CS_MARKET_LIST:
				// 营销客户列表(所有客户)
				if(!isCheck)
				{
					params.put("filterDays", 90);
				}
				params.put("marketingStatusId", 5);
				params.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
				Log.i("", params.toString());
				reqId = RequestDefine.MARKET_RQ_CS_MARKET_LIST;
				httpUtil = new HttpUtils(this, reqId, reqType, params);
				break;
			case CS_EXPIRE_LIST:
				// 到期客户一个是统计到期的个数，一个点击后可以计算要营销的个数
				params.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
				params.put("expirationDays", 90); // 即将到期的客户，进行营销
				reqId = RequestDefine.MARKET_RQ_QUERY_DUECS_LIST;
				httpUtil = new HttpUtils(this, reqId, reqType, params);
				httpUtil.setShowDiloag(false);
				break;
			default:
				break;
			}
			
			httpUtil.setSuccessListener(new SuccessReslut() {
				
				@Override
				public void getResluts(JSONObject response) {
					
					if (TextUtils.isEmpty(response.toString())){
						return;
					}
					String states = response.optString("status");
					switch (currentDataType) {
					case CS_MARKET_COUNT:
						// 营销客户的首页统计
						if (Request.RESLUT_OK.equals(states)) {
							try {
								JSONObject obj = response.getJSONObject("item");//返回空值
								Gson gson = new Gson();
								Log.i("", obj.toString());
								firstPageCount = gson.fromJson(obj.toString(), FirstPageCount.class);
								if (firstPageCount != null) {
								    if (firstPageCount.getMarketingOppQty()  >  0) {
								    	//bug for #5558 change by SGDY
								    	Animation scaleAnimation = AnimationUtils.loadAnimation(ActCustomerMarketing.this,R.anim.img_selected_cnt_anim);
										marketCSCount.startAnimation(scaleAnimation);
								    	marketCSCount.setText(String.valueOf(firstPageCount.getMarketingOppQty()));
									}else{
										marketCSCount.setVisibility(View.GONE);
									}
									
								}else{
									marketCSCount.setVisibility(View.GONE);
								}
							  } catch (JSONException e) {
								e.printStackTrace();
								marketCSCount.setVisibility(View.GONE);
							}
						} 
						break;
					case CS_MARKET_LIST:
						// 营销客户列表
						if (Request.RESLUT_OK.equals(states)) {
							marketCsList = JsonParse.getMarketList(response);
							marketCs.setMarketCsList(marketCsList);
							for(int i = 0; i < marketCs.getGroupCount(); i++){  //展开所有列表
								elistview.expandGroup(i);  
							} 
						} 
						break;
					case CS_EXPIRE_LIST:
						if (Request.RESLUT_OK.equals(states)) {
							JSONArray jsonA = response.optJSONArray("list");
							if (jsonA != null && jsonA.length() > 0) {
								// 先缓存起来数据，然后在传递数据参数，显示到期客户数据
								try {
									jsonA = response.getJSONArray("list");
									Gson gson = new Gson();
									expireCustomer = gson.fromJson(jsonA.toString(), new TypeToken<List<ExpireCustomer>>() {}.getType());
//								    CommonUtil.showToast("到期客户数量"+expireCustomer.size(), ActCustomerMarketing.this);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							} 
						} 
						break;
					default:
						break;
					}
				}
			});
			httpUtil.executeRequest();
			
		}catch(JSONException e1){
			
		} catch (Exception e) {
	  }
	}

	public void showHelpCustom(){
		String saveHelpCustom = SharedUtil.getPreferStr("saveHelpCustom") ;
		if(TextUtils.isEmpty(saveHelpCustom)
				|| saveHelpCustom.equals("0")){
			Intent intent = new Intent() ;
			intent.setClass(ActCustomerMarketing.this, ActHelpCustom.class);
			ActCustomerMarketing.this.startActivity(intent);
		}		
	}
 
}
