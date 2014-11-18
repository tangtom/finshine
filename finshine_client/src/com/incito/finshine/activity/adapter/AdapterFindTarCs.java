package com.incito.finshine.activity.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.custom.view.CommonPopWindow;
import com.custom.view.CommonPopWindow.CallBackCLickEvent;
import com.incito.finshine.R;
import com.incito.finshine.entity.Customer;
import com.incito.utility.CommonUtil;

public class AdapterFindTarCs extends BaseAdapter implements Comparator<Customer>{
	
	private final String TAG = AdapterFindTarCs.class.getSimpleName();
	private LayoutInflater mInflater;
	private Context context;

	private List<Customer> mItems = null;// 全部数据
	private List<Customer> tempCustomerList = null;// temp 适配数据
	private List<Customer> searchSpinnerList = null;

	private ViewHolder holder;
	private List<Customer> seletCustomerData = null;// 选中的客户
	
	public List<Customer> getSeletCustomerData() {
		return seletCustomerData;
	}

	private CheckBox allSeleted;
	// 保存选中的checkbox状态
	private Map<Integer, Boolean> indexMap;

	public AdapterFindTarCs(Context context, CheckBox allCheck) {

		mInflater = LayoutInflater.from(context);
		this.context = context;
		this.allSeleted = allCheck;

		seletCustomerData = new ArrayList<Customer>();
		searchSpinnerList = new ArrayList<Customer>();
		indexMap = new HashMap<Integer, Boolean>();
	}

	public void setItemList(List<Customer> list) {

		seletCustomerData.clear();
		mItems = list;
		tempCustomerList = mItems;
		searchSpinnerList = mItems;
		for (int i = 0; i < mItems.size(); i++) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
 
		if (convertView == null) {

			convertView = mInflater.inflate(R.layout.row_customer_detail, null);
			holder = new ViewHolder();

			holder.avatar = (ImageView) convertView.findViewById(R.id.imageBarTop);
			holder.name = (TextView) convertView.findViewById(R.id.textName);
			holder.age = (TextView) convertView.findViewById(R.id.textAge);
			holder.investCurrent = (TextView) convertView
					.findViewById(R.id.textInvestTotalAsserts);
			holder.investMax = (TextView) convertView
					.findViewById(R.id.textInvestMax);
			holder.investTimes = (TextView) convertView
					.findViewById(R.id.textInvestNumber);
			holder.married = (TextView) convertView
					.findViewById(R.id.textMarried);
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

			holder.name.setText(String.valueOf(item.getName()));
			holder.age.setText(String.valueOf(item.getAge()));
			holder.investCurrent.setText(String.valueOf(item
					.getCurrentInvestValue()));//getInvestTotalAsserts()
			holder.investMax.setText(String.valueOf(item.getNetAsset()));
			holder.investTimes.setText(String.valueOf(item.getInvestNumber()));
			holder.remark.setText(item.getKeyword() == null ? "暂无" : item.getKeyword());
			holder.remark.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
			holder.remark.getPaint().setAntiAlias(true);
//			extView.setText(Html.fromHtml("<u>"+"hahaha"+"</u>"));
			if(item.getMaritalStatus() != 0)//当婚姻状况为0时，显示为空
			{
				holder.married.setText(married[item.getMaritalStatus() - 1]);
			}
			else
			{
				holder.married.setText(married[0]);
			}

			holder.newCustomer
					.setVisibility(item.getInvestNumber() > 0 ? View.GONE
							: View.VISIBLE);
			holder.effectiveCustomer.setText((item.getStatus() == "D") ? "无效客户"
					: "有效客户");

			if (indexMap.get(position) == null) {
				holder.checkSelectSingle.setChecked(false);
			}else{
				holder.checkSelectSingle.setChecked(indexMap.get(position));
			}

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
//								Intent emailIn = new Intent(
//										Intent.ACTION_SENDTO);
//								// emailIn.setType("plain/text");
//								// 设置邮件默认地址
//								emailIn.setData(Uri.parse("mailto:"
//										+ "787878@gmail.com"));
//								// emailIn.putExtra(Intent.EXTRA_EMAIL,
//								// item.getEmail1());
//								// Uri emailUri = Uri.parse(item.getEmail1());
//								context.startActivity(emailIn);
								// context.startActivity(Intent.createChooser(emailIn,
								// "请选择邮件发送软件"));
								Toast.makeText(context, "发邮件", Toast.LENGTH_SHORT);
								break;
							case 1:
								Intent telIn = new Intent(Intent.ACTION_DIAL);
								telIn.setData(Uri.parse("tel:"
										+ item.getCellPhone1()));
								context.startActivity(telIn);
								break;
							case 2:
								CommonUtil.sendSms(context,
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
					
				}
			});
		}

		return convertView;
	}

	public Filter searchFilter(int id) {

		final int spinnerId = id;
		Filter filter = new Filter() {

			String filterNum = null;

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
				List<Customer> currentList = new ArrayList<Customer>();

				if (searchSpinnerList != null && searchSpinnerList.size() > 0) {
					for (Customer cu : searchSpinnerList) {
						if (TextUtils.isEmpty(s)) {
							continue;
						}
						
						switch (spinnerId) {
						case R.id.spinnerCustom:
							if (cu.getName().indexOf(str) >= 0) {
								currentList.add(cu);
								}
							break;
						case R.id.spinnerEffectiveness:
							if (cu.getName().indexOf(str) >= 0) {
								currentList.add(cu);
								}
							break;
						case R.id.spinnerSaleChance:
							if (cu.getName().indexOf(str) >= 0) {
								currentList.add(cu);
								}
							break;
//						case R.id.spinnerDeadline:
//							if (cu.getName().indexOf(str) >= 0) {
//								currentList.add(cu);
//								}
//							break;
//						case R.id.spinnerPublisher:
//							if (cu.getName().indexOf(str) >= 0) {
//								currentList.add(cu);
//								}
//							break;
//						case R.id.spinnerProfit:
//							if (cu.getName().indexOf(str) >= 0) {
//								currentList.add(cu);
//								}
//							break;
//						case R.id.spinnerDividend:
//							if (cu.getName().indexOf(str) >= 0) {
//								currentList.add(cu);
//								}
//							break;
//							
						case R.id.spinnerInvestment1:
							if (cu.getName().indexOf(str) >= 0) {
								currentList.add(cu);
								}
							break;
						default:
							break;
						}

					}
				}
				results.values = currentList;
				results.count = currentList.size();
				return results;
			}
		};
		return filter;
	}

	// 搜索客户
	public Filter getFilter() {

		Filter filter = new Filter() {

			String filterNum = null;

			@Override
			@SuppressWarnings("unchecked")
			protected void publishResults(CharSequence constraint, FilterResults results) {

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
				results.values = contactList;
				results.count = contactList.size();
				return results;
			}
		};
		return filter;
	}

	public class ViewHolder {

		ImageView avatar;
		TextView name;
		TextView investCurrent;
		TextView investMax;
		TextView investTimes;
		TextView age;
		TextView remark;
		TextView married;
		TextView valid;
		ImageView btnShowItem;
		TextView newCustomer;
		TextView effectiveCustomer;
		CheckBox checkSelectSingle;
	}

	private int currentSortValue = R.string.total_asset;
	public void sortCustomer(int currentSort) {
		this.currentSortValue = currentSort;
		Collections.sort(tempCustomerList, this);
		notifyDataSetChanged();
	}

	@Override
	public int compare(Customer cs1, Customer cs2) {
		int i = 0;
		switch (currentSortValue) {
		case R.string.total_asset:
			if (cs1.getNetAsset() > cs2.getNetAsset())// 倒序  资产总额
				i = -1;
			else
				i = 1;
			break;

		case R.string.invest_total_money:
			if (cs1.getCurrentInvestValue() > cs2.getCurrentInvestValue())// 又变了
				i = -1;
			else
				i = 1;
			break;

		case R.string.invest_times:
			if (cs1.getInvestNumber() > cs2.getInvestNumber())
				i = -1;
			else
				i = 1;
			break;

		default:
			break;
		}
		return i;

	}
}
