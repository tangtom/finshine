package com.incito.finshine.activity.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.core.util.AppToast;
import com.android.core.util.BitmapUtil;
import com.custom.view.CommonPopWindow;
import com.custom.view.CommonPopWindow.CallBackCLickEvent;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.activity.ActCustomerMarketProgress;
import com.incito.finshine.activity.ActFindTargetCustomer;
import com.incito.finshine.activity.ActProductManagement;
import com.incito.finshine.activity.dialog.DialogProductAppointShare;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.MarketStateReslut;
import com.incito.finshine.entity.Product;
import com.incito.finshine.entity.RemainBudgetReturn;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.JsonParse;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FaliureResult;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.event.EventBus;
import com.incito.wisdomsdk.net.http.BinaryHttpResponseHandler;

public class AdapterFindCustomer extends BaseAdapter implements Comparator<Customer>{

	private LayoutInflater mInflater;
	private Context context;
	String[] married = null;
	private final String TAG;
	private List<Customer> customerData = null;// 全部数据
	private List<Customer> seletCustomerData = null;// 选中的客户
	private List<Customer> adapterCustomerData = null;// 适配的数据

	public List<Customer> getSeletCustomerData() {
		return seletCustomerData;
	}

	private CheckBox allSeleted;
	private TextView seletedCount;
	RemainBudgetReturn rbr = null;//检查产品预约详情

	// 保存选中的checkbox状态
	private Map<Integer, Boolean> indexMap;
	
	private Product prod;

	@SuppressLint("UseSparseArrays")
	public AdapterFindCustomer(Context context, CheckBox allCheck, TextView selectecCount,Product prod) {

		mInflater = LayoutInflater.from(context);
		this.context = context;
		this.prod = prod;
		TAG = context.getClass().getSimpleName();
		this.allSeleted = allCheck;
		this.seletedCount = selectecCount;
		seletCustomerData = new ArrayList<Customer>();
		indexMap = new HashMap<Integer, Boolean>();

		married = context.getResources().getStringArray(R.array.married_status);

	}

	// 设置数据
	public void setCustomerList(List<Customer> list) {
		customerData = list;
		adapterCustomerData = list;
		for (int i = 0; i < customerData.size(); i++) {
			indexMap.put(i, false);
		}
		notifyDataSetChanged();
	}

	// 选中一条客户
	public void addOneCustomer(Customer customer) {
		if (!seletCustomerData.contains(customer)) {
			seletCustomerData.add(customer);
		}
	}

	// 删除一条客户
	public void disabledOneCustomer(Customer customer) {
		if (seletCustomerData.contains(customer)) {
			seletCustomerData.remove(customer);
		}
	}

	public void select(int position, boolean isChecked) {
		indexMap.put(position, isChecked);
		if (isChecked) {
			addOneCustomer(customerData.get(position));
		} else {
			disabledOneCustomer(customerData.get(position)); 
		}
	}

	@Override
	public void notifyDataSetChanged() {
		//   刷新数据列表
		super.notifyDataSetChanged();
		int seletCount = seletCustomerData.size();
		int totleSize = adapterCustomerData.size();

		if (totleSize != 0) {
			if (seletCount == totleSize) {
				allSeleted.setChecked(true);
			} else {
				allSeleted.setChecked(false);
			}
		}
		seletedCount.setText(Html.fromHtml("<font color ='#d7c093'>" + "已选择"
				+ "</font> <font color ='#ff0000'>" + seletCount + "</font>"
				+ "<font color ='#d7c093'>" + "个目标客户" + "</font>"));
	}

	/**
	 * 
	 * @author: lihs
	 * @Title: isSeletAllCustomer
	 * @Description: 全选和反选
	 * @param seletAllCustom
	 *            true 全选； false 反选
	 * @date: 2014-5-15 上午11:05:55
	 */
	public void isSeletAllCustomer(boolean seletAllCustom) {

		seletCustomerData.clear();
		if (seletAllCustom) {
			seletCustomerData.addAll(adapterCustomerData);
		}
		for (int i = 0; i < adapterCustomerData.size(); i++) {
			indexMap.put(i, seletAllCustom);
		}
		notifyDataSetChanged();
	}

	public void filterByCustomerName(String queryConidtions) {

		List<Customer> tempList = adapterCustomerData;
		adapterCustomerData.clear();
		for (Customer custom : tempList) {
			if (custom.getName().contains(queryConidtions)) {
				adapterCustomerData.add(custom);
			}
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return adapterCustomerData == null ? 0 : adapterCustomerData.size();
	}

	@Override
	public Object getItem(int position) {
		return adapterCustomerData == null ? 0 : adapterCustomerData
				.get(position);
	}

	@Override
	public long getItemId(int position) {
		return adapterCustomerData
				.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.row_find_target_customer, null);
			holder = new ViewHolder();
			holder.investIcon = (ImageView) convertView
					.findViewById(R.id.imageBarTop);
			holder.name = (TextView) convertView.findViewById(R.id.tv_textName);
			holder.age = (TextView) convertView.findViewById(R.id.tv_textAge);
			holder.investCurrent = (TextView) convertView
					.findViewById(R.id.tv_textInvestCurrent);
			holder.investMax = (TextView) convertView
					.findViewById(R.id.tv_textInvestMax);
			holder.investTimes = (TextView) convertView
					.findViewById(R.id.tv_textInvestTimes);
			holder.married = (TextView) convertView
					.findViewById(R.id.tv_textMarried);
			holder.remark = (TextView) convertView
					.findViewById(R.id.tv_textRemark);
			holder.cbSelected = (CheckBox) convertView
					.findViewById(R.id.cb_selete_customer);
			holder.job = (TextView) convertView.findViewById(R.id.tv_textJob);
			holder.btnMarket = (Button)convertView.findViewById(R.id.btnMarket); ;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (adapterCustomerData == null) {
			return null;
		}
		final Customer item = adapterCustomerData.get(position);
		if (adapterCustomerData != null && item != null) {

			
			int poi = position%4;
			/*switch (poi) {
			case 0:
				holder.investIcon.setImageResource(R.drawable.headicon1);
				break;
			case 1:
				holder.investIcon.setImageResource(R.drawable.headicon2);
				break;
			case 2:
				holder.investIcon.setImageResource(R.drawable.headicon3);
				break;
			default:
				holder.investIcon.setImageResource(R.drawable.headicon4);
				break;
			}*/
			if(filterNum == null)
			{
				holder.name.setText(String.valueOf(item.getName()));
			}
			else
			{
				holder.name.setText(Html.fromHtml(item.getName().replace(filterNum, "<font color='#e50150'>" + filterNum + "</font>")));
			}
			holder.age.setText(String.valueOf(item.getAge() + "岁"));
			if(item.getMaritalStatus() != 0)//当婚姻状况为0时，显示为空
			{
				holder.married.setText(married[item.getMaritalStatus() - 1]);
			}
			else
			{
				holder.married.setText(married[0]);
			}
			try {
				holder.job.setText(context.getResources().getStringArray(R.array.customer_job)[(int)item.getJob()-1]);
			} catch (Exception e) {
			}
			holder.investCurrent.setText("目前投资："+String.valueOf(item.getCurrentInvestValue())+"万");
			holder.investTimes.setText("已购同类产品："+String.valueOf(item.getInvestNumber())+"次");
			holder.investMax.setText("投资峰值："+String.valueOf(item.getNetAsset())+"万");
			
			holder.remark.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
			holder.remark.getPaint().setAntiAlias(true);
			// 是否选中
			holder.cbSelected.setChecked(indexMap.get(position));
			select(position,holder.cbSelected.isChecked());
			
			//TODO 缺少绑定协议字段，
			holder.btnMarket.setOnClickListener(new Myclick(position));
			holder.investIcon.setOnClickListener(new Myclick(position));
			
			 
		}
		sendMessage(new BinaryHttpResponseHandler(holder.investIcon){
			ImageView icon  = holder.investIcon;
			@Override
			public void onSuccess(byte[] binaryData) {
				super.onSuccess(binaryData);
				Bitmap bitmap = BitmapUtil.Bytes2Bimap(binaryData);
				if (bitmap != null) {
					icon.setImageBitmap(bitmap);
				} else{
					icon.setImageResource(R.drawable.headicon1);
				}
			}
			
			@Override
			public void onFailure(Throwable error,String content){
				Log.i(TAG, content);
				icon.setImageResource(R.drawable.ts_avatar2);
			}
		},item.getId());
		return convertView;
	}
	String filterNum = null;
	/**
	 * @author: lihs
	 * @Title: getFilter
	 * @Description: 关键字过滤，根据输入的关键字，筛选出需要的数据放入显示列表中
	 */
	public Filter getFilter() {

		Filter filter = new Filter() {


			@Override
			@SuppressWarnings("unchecked")
			protected void publishResults(CharSequence constraint,
					FilterResults results) {

				// 在过滤数据的时候，要进行把之前选中的数据清空
				seletCustomerData.clear();
				for (int i = 0; i < adapterCustomerData.size(); i++) {
					indexMap.put(i, false);
				}

				if (TextUtils.isEmpty(filterNum)) {
					// 过滤为空时 显示所有的数据
					adapterCustomerData = customerData;
					notifyDataSetChanged();
					return;
				}

				adapterCustomerData = (ArrayList<Customer>) results.values;
				if (results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();				}
			}

			@Override
			@SuppressWarnings("unused")
			protected FilterResults performFiltering(CharSequence s) {
				String str = s.toString();
				filterNum = str;
				FilterResults results = new FilterResults();
				ArrayList<Customer> list = new ArrayList<Customer>();
				if (customerData != null && customerData.size() > 0) {
					for (Customer cb : customerData) {
						// TODO 屏蔽掉非法数据。有非法数据进行排除

						// 筛选数据：当没有输入筛选数字时
						if (TextUtils.isEmpty(s)) {
							continue;
						}

						// 根据关键字进行刷选
						if (cb.getName().indexOf(str) >= 0) {
							list.add(cb);
						}
					}
				}
				results.values = list;
				results.count = list.size();
				return results;
			}
		};
		return filter;
	}

	public class ViewHolder {

		ImageView investIcon;// 投资人头像
		TextView name;// 投资人姓名
		TextView investCurrent;//
		TextView investMax;// 投资总额
		TextView investTimes;// 投资次数
		TextView age;// 年龄
		TextView remark;// 备注
		TextView job;// 备注
		TextView married;// 是否已婚
		public CheckBox cbSelected;// 单选和反选按钮
		Button btnMarket;
		ImageView ivBindProtecol;// 是否绑定协议图标
	}

	// 排序方式
	public static final int ASSETS_TOTLE = R.string.total_asset;
	public static final int INVEST_TOTLE = R.string.invest_total_money;
	public static final int INVEST_TIMES = R.string.invest_times;

	public static final int POSITIVE_SORTING = 1;// 默认正序
	public static final int REVERSE_SORTING = -1;// 反序
	private int currentSort = ASSETS_TOTLE;

	// 设置排序方式进行过滤
	public void setSortWays(int id) {
		currentSort = id;
		// 在过滤数据的时候，要进行把之前选中的数据清空
		seletCustomerData.clear();
		for (int i = 0; i < adapterCustomerData.size(); i++) {
			indexMap.put(i, false);
		}
		Log.i(TAG,adapterCustomerData.size() + "is size");
		Collections.sort(adapterCustomerData, this);

		notifyDataSetChanged();
	}

	@Override
	public int compare(Customer customer1, Customer customer2) {

		int sorway = 0;
		switch (currentSort) {
		case ASSETS_TOTLE:
			if (customer1.getNetAsset() < customer2.getNetAsset()) {
				sorway = POSITIVE_SORTING;
			} else if (customer1.getNetAsset() > customer2.getNetAsset()) {
				sorway = REVERSE_SORTING;
			}
			break;
		case INVEST_TOTLE:
			if (customer1.getCurrentInvestValue() < customer2
					.getCurrentInvestValue()) {
				sorway = POSITIVE_SORTING;
			} else if (customer1.getCurrentInvestValue() > customer2
					.getCurrentInvestValue()) {
				sorway = REVERSE_SORTING;
			}
			break;
		case INVEST_TIMES:
			if (customer1.getInvestNumber() < customer2.getInvestNumber()) {
				sorway = POSITIVE_SORTING;
			} else if (customer1.getInvestNumber() > customer2
					.getInvestNumber()) {
				sorway = REVERSE_SORTING;
			}
			break;

		default:
			break;
		}
		return sorway;
	}
	//检查产品预约状态
	private void checkProductAppointStatus(final Product p,final View v,final int clickPosition){
		JSONObject json = new JSONObject();
		try {
			json.put("prodId",p.getId());
			json.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		HttpUtils httpUtils = new HttpUtils(context,RequestDefine.APPOINT_RQ_PRODUCT_CHECK,RequestType.POST,json);
		httpUtils.setSuccessListener(new SuccessReslut() {
			
			@Override
			public void getResluts(JSONObject response) {
				// TODO Auto-generated method stub
				Log.i("", response.toString());
				try {
					String status = response.getString("status");
					if(status.equals("0")){
						if(!response.isNull("item")){
							initPurchaseProduct(clickPosition);
						}
					}else if(status.equals("1")){
						if(!response.isNull("item")){
							Gson gson = new Gson();
							rbr = gson.fromJson(response.getJSONObject("item").toString(), RemainBudgetReturn.class);
							if(rbr != null){
								switch(Integer.parseInt(rbr.getCheckStatus())){
								case 1:
								case 2:
								case 3:
								case 4:
									Window window = ((ActFindTargetCustomer)context).getWindow();
									DialogProductAppointShare pop = new DialogProductAppointShare(context,v,window,false);
									pop.setProduct(p);
									pop.showPopWindow();
									pop.setRemainBudgetReturn(rbr);
									WindowManager.LayoutParams lp = window.getAttributes();  
							        lp.alpha = 0.5f;  
							        window.setAttributes(lp);
									break;
								}
							}
						}else{
							AppToast.ShowToast("获取已预约份额失败!");
						}
					}					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		httpUtils.setFaliureResult(new FaliureResult() {
			
			@Override
			public void getResluts(String msg) {
				// TODO Auto-generated method stub
				AppToast.ShowToast("获取已预约份额失败!");
			}
		});
		httpUtils.executeRequest();
	}
	private class Myclick implements OnClickListener{

		private  int clickPosition = 0;
		public Myclick(int clickPosition) {
			super();
			this.clickPosition = clickPosition;
		}
		
		@Override
		public void onClick(final View v) {
			 switch (v.getId()) {
			 case R.id.btnMarket:
				 checkProductAppointStatus(prod,v,clickPosition);//check产品预约状态
				break;
			 case R.id.imageBarTop:
				final List<Integer> list = new ArrayList<Integer>();
				list.add(R.drawable.customer_manager_email);
				list.add(R.drawable.customer_manager_tel);
				list.add(R.drawable.customer_manager_sms);
				CommonPopWindow popWindow = new CommonPopWindow(context, v,
						list, R.drawable.popbg_custom_manager_content,
						CommonPopWindow.LEFT_2_RIGHT);
				// 回调函数
				popWindow.setCallBackCLickEvent(new CallBackCLickEvent() {

					@Override
					public void doClick(int position, Object obj) {
						switch (position) {
						case 0:
							Toast.makeText(context, "发邮件异常", Toast.LENGTH_SHORT);
							break;
						case 1:
							Intent telIn = new Intent(Intent.ACTION_DIAL);
							telIn.setData(Uri.parse("tel:" + ((Customer) getItem(clickPosition)) .getCellPhone1()));
							context.startActivity(telIn);
							break;
						case 2:
							CommonUtil.sendSms(context, ((Customer) getItem(clickPosition)).getCellPhone1());
							break;
						default:
							break;
						}

					}
				});
				popWindow.showPopWindow();

				 break;

			default:
				break;
			}
			
		}
	}
	
	private void initPurchaseProduct(int position) {

		JSONObject params = new JSONObject();
		try {
			params.put("salesId", SPManager.getInstance().getLongValue(SPManager.ID));
			params.put("customerId", ((Customer) getItem(position)).getId());
			params.put("prodId", prod.getId());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		HttpUtils httpUtils = new HttpUtils(context, RequestDefine.MARKET_RQ_SELECT_PROD, RequestType.POST, params);
		httpUtils.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {
				if (JsonParse.setMarketStepReslut(response)) {
					CommonUtil.showToast("选择产品成功", context);
					JSONObject json = response.optJSONObject("item");
					Gson gson = new Gson();
					MarketStateReslut msr = gson.fromJson(json.toString(), MarketStateReslut.class);
					if(msr != null)
					{
						Intent i = new Intent(context,
								ActCustomerMarketProgress.class);
						if(msr.getMarketingStatusId() == 2)
						{
							i.putExtra("Position", ActCustomerMarketProgress.F_BIND);
						}else if(msr.getMarketingStatusId() == 3)
						{
							i.putExtra("Position", ActCustomerMarketProgress.F_SIGN_FIRST);//更改
							i.putExtra("product", (Serializable)prod);
						}
						context.startActivity(i);
					}
					// 根据营销状态查看，绑定状态如果已绑定就，跳转到合同签订，否则跳转到绑定协议
					//initHttpUtils(JsonParse.getMarketReslut());
				} else {
					CommonUtil.showToast("购买产品失败", context);
				}
			}
		});
		httpUtils.executeRequest();
	}
	
	private void initHttpUtils(MarketStateReslut result) {

		HttpUtils httpUtils = null;
		JSONObject params = new JSONObject();
		try {
			params.put("salesId", result.getSalesId());
			params.put("customerId", result.getCustomerId());
			httpUtils = new HttpUtils(context, RequestDefine.MARKET_RQ_QUERY_BIND_PROTECAL,RequestType.POST, params);
			httpUtils.setShowDiloag(true);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		httpUtils.setSuccessListener(new SuccessReslut() {

			@Override
			public void getResluts(JSONObject response) {
				JSONObject obj;
				try {

					if (Request.RESLUT_OK.equals(response.optString("status"))) {
						obj = response.getJSONObject("item");
						System.out.println("绑定协议iD:"
								+ String.valueOf(obj.optLong("id")));
						Intent i = new Intent(context,
								ActCustomerMarketProgress.class);
						if (Constant.CUSTOMER_ALREADY_BIND.equals(obj.optString("status"))) {
							// 跳转到合同签订； 之前的只能预览了
							i.putExtra("Position", ActCustomerMarketProgress.F_SIGN_FIRST);//更改
							i.putExtra("product", (Serializable)prod);

						} else {
							// 跳转到绑定协议
							i.putExtra("Position", ActCustomerMarketProgress.F_BIND);
						}
						context.startActivity(i);
						((Activity) context).finish();
					} else {
						CommonUtil.showToast("购买产品失败", context);
					}

				} catch (JSONException e) {
					e.printStackTrace();
			  }
			}
		});
		httpUtils.executeRequest();
	  }
	
	/* 网络请求 */
	public void sendMessage(BinaryHttpResponseHandler binaryHttpResponseHandler,long customerId ) {
		Request request = new Request(RequestDefine.MARKET_RQ_DOWNLOAD_PHOTO, customerId, RequestType.GET, null,
				binaryHttpResponseHandler);
		request.setDownLoadFile(true);
		CoreManager.getInstance().postRequest(request);
	}
	
	
	}
	 