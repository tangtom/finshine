package com.incito.finshine.activity.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.core.util.BitmapUtil;
import com.custom.view.CommonPopWindow;
import com.custom.view.CommonPopWindow.CallBackCLickEvent;
import com.custom.view.CommonWaitDialog;
import com.custom.view.PopDividendWindow;
import com.custom.view.PopListWindow;
import com.incito.finshine.R;
import com.incito.finshine.activity.ActCustomerMarketOrderManager;
import com.incito.finshine.activity.ActProductManagement;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.CustomerSortInvestCurrent;
import com.incito.finshine.entity.CustomerSortInvestNumber;
import com.incito.finshine.entity.CustomerSortTotalAsset;
import com.incito.finshine.entity.Product;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.StringUtil;
import com.incito.wisdomsdk.net.http.BinaryHttpResponseHandler;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

public class AdapterCustomerDetail extends BaseAdapter implements
		Comparator<Customer> {
	private final String TAG = AdapterCustomerDetail.class.getSimpleName();
	private LayoutInflater mInflater;
	private Context context;
	private static Customer customer;
	private List<Customer> mItems = null;// 全部数据
	private List<Customer> tempCustomerList = null;// temp 适配数据
	
	private List<Customer> searchSpinnerList = null;
	private List<Bitmap> listBitmap = null;

	private ViewHolder holder;

	private List<Customer> seletCustomerData = null;// 选中的客户
	private StringBuffer selectedCustomerID = new StringBuffer();

	private String isEnable = "";// 有效客户
	

	public List<Customer> getSeletCustomerData() {
		return seletCustomerData;
	}

	private CheckBox allSeleted;
	
	private TextView tvSeleCount;
	// 保存选中的checkbox状态
	private Map<Integer, Boolean> indexMap;

	public AdapterCustomerDetail(Context context, CheckBox allCheck,TextView tvSeleCount) {
         
		mInflater = LayoutInflater.from(context);
		this.context = context;
		this.allSeleted = allCheck;
        this.tvSeleCount=tvSeleCount;
		seletCustomerData = new ArrayList<Customer>();
		searchSpinnerList = new ArrayList<Customer>();
		indexMap = new HashMap<Integer, Boolean>();
	}


	public boolean isCustomerSecleted() {
		if (tempCustomerList == null || tempCustomerList.size() < 1
				|| seletCustomerData == null || seletCustomerData.size() < 1) {
			CommonUtil.showToast("请先选择用户", context);
			return false;
		} else {
			return true;
		}
	}
	
	public void setBitmap(List<Bitmap> bmp)
	{
		this.listBitmap = bmp;
	}

	// 设为有效或者无效
	public void setEnabled(String isEnabled) {
		this.isEnable = isEnabled;
		for (int i = 0; i < seletCustomerData.size(); i++) {
			Customer c = seletCustomerData.get(i);
			if (i == seletCustomerData.size() - 1)
				selectedCustomerID.append(String.valueOf(c.getId()));
			else
				selectedCustomerID.append(String.valueOf(c.getId()) + ",");
		}
		 setCustomerEnable();
	}

	private JsonHttpResponseHandler handlerGetCustomerEnable = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONObject response) {
			Log.i(TAG, "success o= " + response.toString());

			int statues = response.optInt("status");
			if (0 == statues) {
				// JSONArray arr = response.optJSONArray("list");
				// Log.i(TAG, "success a= " + response.toString());
				// Gson gson = new Gson();
				// list = gson.fromJson(arr.toString(),
				// new TypeToken<List<Customer>>() {
				// }.getType());
				// for (int i = 0; i < list.size(); i++) {
				// Customer c = list.get(i);
				// if (i == list.size() - 1)
				// customerId.append(String.valueOf(c.getId()));
				// else
				// customerId.append(String.valueOf(c.getId()) + ",");
				// System.out.println(c.getName());
				// }
				// Log.i("拼接的customerID=", customerId.toString());
				for (Customer cs : seletCustomerData) {
					for (Customer cs1 : tempCustomerList) {
						if (cs.getId() == cs1.getId()) {
							cs1.setStatus(isEnable);
						}
					}
				}
				CommonUtil.showToast("更改客户有效性成功", context);
				notifyDataSetChanged();
			} else
				CommonUtil.showToast("更改客户有效性失败", context);
		}

		@Override
		public void onSuccess(JSONArray response) {
			Log.i(TAG, "success a= " + response.toString());
		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
		}
	};

	public boolean setCustomerEnable() {
		JSONObject json = new JSONObject();
		try {
			json.put("customerIds", selectedCustomerID);
			json.put("status", isEnable);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Request request = new Request(RequestDefine.RQ_CUSTOMER_POST_ENABLE,
				RequestType.POST, json, handlerGetCustomerEnable);
		CoreManager.getInstance().postRequest(request);
		return true;
	}

	public void setItemList(List<Customer> list) {

		if (list != null) {
			seletCustomerData.clear();
			mItems = list;
			tempCustomerList = mItems;
			searchSpinnerList = mItems;
			for (int i = 0; i < mItems.size(); i++) {
				indexMap.put(i, false);
			}
			notifyDataSetChanged();
		}
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
			addOneCustomer(mItems.get(position));
		} else {
			disabledOneCustomer(mItems.get(position));
		}
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
			seletCustomerData.addAll(tempCustomerList);
		}
		for (int i = 0; i < tempCustomerList.size(); i++) {
			indexMap.put(i, seletAllCustom);
		}
		notifyDataSetChanged();
	}

	@Override
	public void notifyDataSetChanged() {

		super.notifyDataSetChanged();
		int seletCount = seletCustomerData.size();
		int totleSize = tempCustomerList.size();
		
		if (totleSize != 0) {
			if (seletCount == totleSize) {
				allSeleted.setChecked(true);
			} else {
				allSeleted.setChecked(false);
			}
		} else {
			if (allSeleted.isChecked()) {
				allSeleted.setChecked(false);
			}
			seletCount = 0;
		}
		tvSeleCount.setText(Html.fromHtml("<font color ='#d7c093'>" + "已选择"
				+ "</font> <font color ='#ff0000'>" + seletCount + "</font>"
				+ "<font color ='#d7c093'>" + "个客户" + "</font>"));
	}

	@Override
	public int getCount() {
		int size = 0;
		if (tempCustomerList != null) {
			size = tempCustomerList.size();
		}
		return size;
	}

	@Override
	public Object getItem(int position) {
		return tempCustomerList.get(position);
	}

	@Override
	public long getItemId(int position) {
		long id = tempCustomerList.get(position).getId();
		return id;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		// When convertView is not null, we can reuse it directly, there is no
		// need
		// to reinflate it. We only inflate a new View when the convertView
		// supplied
		// by ListView is null.
		if (convertView == null) {

			convertView = mInflater.inflate(R.layout.row_customer_detail, null);

			// Creates a ViewHolder and store references to the two children
			// views
			// we want to bind data to.
			holder = new ViewHolder();
			
			holder.avatar = (ImageView) convertView
					.findViewById(R.id.imageBarTop);
			holder.icBind = (ImageView) convertView.findViewById(R.id.isBind);

			holder.name = (TextView) convertView.findViewById(R.id.textName);
			holder.age = (TextView) convertView.findViewById(R.id.textAge);
			holder.investCurrent = (TextView) convertView// 投资总额
					.findViewById(R.id.textInvestTotalAsserts);
			holder.investMax = (TextView) convertView
					.findViewById(R.id.textInvestMax);// 资产总额
			holder.investTimes = (TextView) convertView
					.findViewById(R.id.textInvestNumber);
			holder.married = (TextView) convertView
					.findViewById(R.id.textMarried);
			holder.job = (TextView) convertView.findViewById(R.id.textJob);

			holder.remark = (TextView) convertView
					.findViewById(R.id.textRemark);
			holder.newCustomer = (TextView) convertView
					.findViewById(R.id.textNewCustomer);
			holder.effectiveCustomer = (TextView) convertView
					.findViewById(R.id.textEffectiveCustomer);

			// holder.valid = (TextView)
			// convertView.findViewById(R.id.textValid);
			holder.btnShowItem = (ImageView) convertView
					.findViewById(R.id.btnShowItemBtn);
			holder.checkSelectSingle = (CheckBox) convertView
					.findViewById(R.id.checkSelect);
			if(listBitmap != null)
			{
				if(listBitmap.get(position) != null)
				{
					holder.avatar.setImageBitmap(listBitmap.get(position));
				}
				else
				{
					if(customer.getGender() == 1)
					{
						
					}
					else
					{
						
					}
				}
			}
			convertView.setTag(holder);
		} else {
			// Get the ViewHolder back to get fast access to the TextView
			// and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		}
		// Bind the data efficiently with the holder.
		final Customer item = tempCustomerList.get(position);
		if (tempCustomerList != null) {
			String[] married = context.getResources().getStringArray(
					R.array.married_status);
            /*
			int poi = position % 4;
			switch (poi) {
			case 0:
				holder.avatar.setImageResource(R.drawable.headicon1);
				break;
			case 1:
				holder.avatar.setImageResource(R.drawable.headicon2);
				break;
			case 2:
				holder.avatar.setImageResource(R.drawable.headicon3);
				break;
			default:
				holder.avatar.setImageResource(R.drawable.headicon4);
				break;
			}
            */
			holder.icBind
					.setVisibility(item.getBindingStatusId() > 0 ? View.VISIBLE
							: View.GONE);
			if (TextUtils.isEmpty(filterNum)) {
				holder.name.setText(String.valueOf(item.getName()));
			} else {
				holder.name
						.setText(Html.fromHtml(item
								.getName()
								.replace(
										filterNum,
										"<font color='#e50150'>" + filterNum
												+ "</font>")
								.replace(
										filterNum.toUpperCase(),
										"<font color='#e50150'>"
												+ filterNum.toUpperCase()
												+ "</font>")));
			}
			holder.age.setText(String.valueOf(item.getAge()));
			holder.investCurrent.setText(String.valueOf(item
					.getCurrentInvestValue()));// 投资总额
			holder.investMax.setText(String.valueOf(item.getNetAsset()));// 资产总额
			holder.investTimes.setText(String.valueOf(item.getInvestNumber()));
			holder.remark
					.setText(StringUtil.isEmpty(item.getContactNoteTitle()) ? "暂无"
							: item.getContactNoteTitle());
			holder.remark.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 下划线
			holder.remark.getPaint().setAntiAlias(true);
			// extView.setText(Html.fromHtml("<u>"+"hahaha"+"</u>"));
			if (item.getMaritalStatus() != 0) {
				holder.married.setText(married[item.getMaritalStatus() - 1]);
			} else {
				holder.married.setText(married[0]);
			}
			String[] jobArrString = context.getResources().getStringArray(
					R.array.customer_job);
			int jobId = (int) item.getJob();
			int index = jobId - 1;
			if (jobId > jobArrString.length) {
				jobId = jobArrString.length;
				index = jobId - 1;
			}

			if (index < 0)
				index = 0;

			holder.job.setText(jobArrString[index]);
			holder.newCustomer
					.setVisibility(item.getInvestNumber() > 0 ? View.GONE
							: View.VISIBLE);
			holder.effectiveCustomer.setText((item.getStatus() == null || item
					.getStatus().equals("D")) ? "无效客户" : "有效客户");

			// holder.valid.setText(String.valueOf(item.getAge()));
			if (indexMap.get(position) == null) {
				holder.checkSelectSingle.setChecked(false);
			} else {
				holder.checkSelectSingle.setChecked(indexMap.get(position));
			}
			holder.checkSelectSingle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					notifyDataSetChanged();
				}
			});
			// 弹出电话、短信popwindow
			holder.avatar.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(final View v) {
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
								String target = item.getEmail1();
								String[] targetEmail = null;
								if (target != null && !"".equals(target)) {
									targetEmail =  new String[]{target};
								}
								CommonUtil.sendEmail(context, targetEmail, null, null);
								break;
							case 1:
								Intent telIn = new Intent(Intent.ACTION_DIAL);
								telIn.setData(Uri.parse("tel:"
										+ item.getCellPhone1()));
								context.startActivity(telIn);
								break;
							case 2:
								CommonUtil.sendSms(
										AdapterCustomerDetail.this.context,
										item.getCellPhone1());
								break;

							default:
								break;
							}

						}
					});
				
					popWindow.showPopWindow();
				}
			});

			// 弹出隐藏的三个按钮
			holder.btnShowItem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {

					// popMoreContextMenu(v,null);

					List<Integer> list = new ArrayList<Integer>();
					list.add(R.string.sales_live);
					list.add(R.string.marketing);
					list.add(R.string.dividend);
					final PopListWindow popWindow = new PopListWindow(context,
							v, list, R.drawable.popbg_custom_manager_listitem,
							PopListWindow.RIGHT_2_LEFT);
					popWindow
							.setCallBackCLickEvent(new PopListWindow.CallBackCLickEvent() {

								@Override
								public void doClick(int posit, Object obj) {
									switch (posit) {
									case 0:
										CommonUtil.showToast("销售实况 ", context);
										Intent i1 = new Intent();
										i1.putExtra(Constant.SALES_ID,
												Request.salesId);
										i1.putExtra(Constant.CUSTOMER_ID,
												((Customer) getItem(position))
														.getId());
										i1.setClass(
												context,
												ActCustomerMarketOrderManager.class);
										context.startActivity(i1);
										break;
									case 1:
										Intent i = new Intent();
										i.setClass(
												AdapterCustomerDetail.this.context,
												ActProductManagement.class);
										i.putExtra(
												ActProductManagement.ACTION_INTENT_PRODUCT,
												2);
										i.putExtra(
												ActProductManagement.ACTION_FROM_MARKET_CSID,
												((Customer) getItem(position))
														.getId());
										context.startActivity(i);
										break;
									case 2:
										CommonUtil.showToast("分红 ", context);
										if (listView != null) {
											listView.setSelection(position);
										}

										PopDividendWindow popDivided = new PopDividendWindow(
												context, v, item, 0,
												PopDividendWindow.TOP_2_BOTTOM);
										popDivided.showPopWindow();
										// popShareMoneyWindow(null, v);
										break;

									default:
										break;
									}
									popWindow.closePopWindow();
								}
							});
					popWindow.showPopWindow();
				}
			});
		}
//		((Object) binaryHttpResponseHandler).setIcon(holder.avatar);
		
		sendMessage(new BinaryHttpResponseHandler(holder.avatar){
			ImageView icon  = holder.avatar;
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

	PopupWindow moreBtnPopwin = null;

	private void popMoreContextMenu(final View moreBtn, final Product prod) {

		// 弹出功能按钮popupwindown，注意：上传成功的消息才能被转发
		View view = LayoutInflater.from(context).inflate(
				R.layout.prod_list_more_layout, null);

		int popWinWidth = context.getResources().getDimensionPixelOffset(
				R.dimen.product_more_w);
		int popWinHeight = context.getResources().getDimensionPixelOffset(
				R.dimen.product_more_h);

		Button goalCs = (Button) view.findViewById(R.id.btn_goal_customer);
		goalCs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				moreBtnPopwin.dismiss();
				CommonUtil.showToast("goalCs", context);

			}
		});

		Button saling = (Button) view.findViewById(R.id.btn_saling);
		saling.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				moreBtnPopwin.dismiss();
				CommonUtil.showToast("saling", context);
			}
		});

		Button shareMoney = (Button) view.findViewById(R.id.btn_share_money);
		shareMoney.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				moreBtnPopwin.dismiss();
				CommonUtil.showToast("shareMoney", context);
				// 弹出一个popupwindow 在点击的位置上
			}
		});

		Button collected = (Button) view.findViewById(R.id.btn_collect);
		collected.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				moreBtnPopwin.dismiss();
				CommonUtil.showToast("collected", context);
			}
		});

		moreBtnPopwin = new PopupWindow(view, popWinWidth, popWinHeight, true);
		moreBtnPopwin.setAnimationStyle(R.style.product_more_windown_animstyle);
		moreBtnPopwin.setBackgroundDrawable(new BitmapDrawable());
		moreBtnPopwin.setOutsideTouchable(true);
		int[] location = new int[2];
		moreBtn.getLocationOnScreen(location);
		moreBtnPopwin.showAtLocation(moreBtn, Gravity.NO_GRAVITY, location[0]
				- moreBtnPopwin.getWidth() + 2,
				location[1] - (moreBtnPopwin.getHeight() - moreBtn.getHeight())
						/ 2 + 10);

	}

	String filterNum = null;

	// 搜索客户
	public Filter getFilter() {

		Filter filter = new Filter() {

			@Override
			@SuppressWarnings("unchecked")
			protected void publishResults(CharSequence constraint,
					FilterResults results) {

				if (TextUtils.isEmpty(filterNum)) {
					tempCustomerList = mItems;
					notifyDataSetChanged();
					return;
				}
				tempCustomerList = (List<Customer>) results.values;
				if (results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}

			@Override
			protected FilterResults performFiltering(CharSequence s) {
				String str = s.toString();
				filterNum = str;
				FilterResults results = new FilterResults();
				List<Customer> contactList = new ArrayList<Customer>();
				if (filterNum.charAt(0) > 57 || filterNum.charAt(0) < 48)// 判断首字母如果为数字
				{
					if (mItems != null && mItems.size() > 0) {
						for (Customer cu : mItems) {
							if (TextUtils.isEmpty(s)) {
								continue;
							}

							if (cu.getName().indexOf(str) >= 0) {
								contactList.add(cu);
							}
						}
					}
				}
				results.values = contactList;
				results.count = contactList.size();
				return results;
			}
		};
		return filter;
	}

	public class ViewHolder {

		ImageView avatar;
		ImageView icBind;
		TextView name;
		TextView investCurrent;
		TextView investMax;
		TextView investTimes;
		TextView age;
		TextView remark;
		TextView married;
		TextView job;
		TextView valid;
		ImageView btnShowItem;
		TextView newCustomer;
		TextView effectiveCustomer;
		CheckBox checkSelectSingle;
	}

	public void sortByInvestTimes() {
		Log.i(TAG, "sortByInvestTimes");
		Collections.sort(tempCustomerList, new CustomerSortInvestNumber());
		for (Customer c : tempCustomerList) {
			Log.i(TAG, "c = " + c.getInvestNumber());
		}
		notifyDataSetChanged();
	}

	public void sortByInvestCurrent() {
		Log.i(TAG, "sortByInvestCurrent");
		Collections.sort(tempCustomerList, new CustomerSortInvestCurrent());
		for (Customer c : tempCustomerList) {
			Log.i(TAG, "c = " + c.getCurrentInvestValue());
		}
		notifyDataSetChanged();
	}

	public void sortByTotalAsset() {
		Log.i(TAG, "sortByTotalAsset");
		Collections.sort(tempCustomerList, new CustomerSortTotalAsset());
		for (Customer c : tempCustomerList) {
			Log.i(TAG, "c = " + c.getNetAsset());
		}
		notifyDataSetChanged();
	}

	private int currentSortValue = R.string.total_asset;

	public void sortCustomer(int currentSort) {
		this.currentSortValue = currentSort;
		Collections.sort(tempCustomerList, this);
		notifyDataSetChanged();
	}

	/*
	 * jdk1.6之后需要处理幂等的情况
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Customer cs1, Customer cs2) {

		int i = 0;
		switch (currentSortValue) {
		case R.string.total_asset:
			if (cs1.getNetAsset() > cs2.getNetAsset())// 倒序 资产总额
				i = -1;
			else if (cs1.getNetAsset() == cs2.getNetAsset())
				i = 0;
			else
				i = 1;
			break;

		case R.string.invest_total_money:
			if (cs1.getCurrentInvestValue() > cs2.getCurrentInvestValue())// 又变了
				i = -1;
			else if (cs1.getCurrentInvestValue() == cs2.getCurrentInvestValue())
				i = 0;
			else
				i = 1;
			break;

		case R.string.invest_times:
			if (cs1.getInvestNumber() > cs2.getInvestNumber())
				i = -1;
			else if (cs1.getInvestNumber() == cs2.getInvestNumber())
				i = 0;
			else
				i = 1;
			break;

		default:
			break;
		}
		return i;

	}

	private PopupWindow popSMw = null;

	private ListView listView = null;

	public void setListView(ListView listView) {
		this.listView = listView;
	}

	private void popShareMoneyWindow(Customer cs, View view) {

		View view1 = LayoutInflater.from(context).inflate(
				R.layout.pop_share_money, null);
		popSMw = new PopupWindow(view1, 415, 315, true);
		popSMw.setAnimationStyle(R.style.anim_top_2_bottom);
		popSMw.setBackgroundDrawable(new ColorDrawable());
		popSMw.setOutsideTouchable(true);
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int screenW = CommonUtil.getScreenWidth(context);
		popSMw.showAtLocation(view, Gravity.NO_GRAVITY, screenW / 2 - 200,
				location[1] - 14);
	}

	public void refresh() {
		notifyDataSetChanged();
		notifyDataSetInvalidated();
	}
	//private JsonHttpResponseHandler handlerProperty = new JsonHttpResponseHandler(){};
	
//	public BinaryHttpResponseHandler binaryHttpResponseHandler = new BinaryHttpResponseHandler(ImageView icon) {
//		
//		public void setIcon(ImageView icon) {
//			this.icon = icon;
//		}
//
//		@Override
//		public void onSuccess(byte[] binaryData) {
//			super.onSuccess(binaryData);
//			Bitmap bitmap = BitmapUtil.Bytes2Bimap(binaryData);
//			if (bitmap != null) {
//				icon.setImageBitmap(bitmap);
//			} else {
//				icon.setImageResource(R.drawable.ts_avatar2);
//			}
//		}
//		
//		@Override
//		public void onFailure(Throwable error,String content){
//			Log.i(TAG, content);
//			icon.setImageResource(R.drawable.ts_avatar2);
//		}
//
//	};

	/* 网络请求 */
	public void sendMessage(BinaryHttpResponseHandler binaryHttpResponseHandler,long customerId ) {
		Request request = new Request(RequestDefine.MARKET_RQ_DOWNLOAD_PHOTO, customerId, RequestType.GET, null,
				binaryHttpResponseHandler);
		request.setDownLoadFile(true);
		CoreManager.getInstance().postRequest(request);
	}
	
	
	
	
}
