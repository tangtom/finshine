package com.incito.finshine.activity.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.incito.finshine.R;
import com.incito.finshine.entity.Customer;

/**
 * 
 * <dl>
 * <dt>AdapterAddTargetCustomer.java</dt>
 * <dd>Description:添加目标客户数据适配器</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-5-15 下午1:19:14</dd>
 * </dl>
 * 
 * @author lihs
 */
public class AdapterAddTargetCustomer extends BaseAdapter implements
		Comparator<Customer> {

	private LayoutInflater mInflater;
	private Context context;
	String[] married = null;

	private List<Customer> customerData = null;// 全部数据
//	private List<Customer> customerData1 = null;//全部数据备份
	private List<Customer> seletCustomerData = null;// 选中的客户
	private List<Customer> adapterCustomerData = null;// 适配的数据

	public List<Customer> getSeletCustomerData() {
		return seletCustomerData;
	}

	private CheckBox allSeleted;
	private TextView seletedCount;

	// 保存选中的checkbox状态
	private Map<Integer, Boolean> indexMap;

	public AdapterAddTargetCustomer(Context context) {

		mInflater = LayoutInflater.from(context);
		this.context = context;
	}

	@SuppressLint("UseSparseArrays")
	public AdapterAddTargetCustomer(Context context, CheckBox allCheck,
			TextView selectecCount) {

		mInflater = LayoutInflater.from(context);
		this.context = context;

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
				+ "<font color ='#d7c093'>" + "个客户" + "</font>"));
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_add_target_customer,
					null);

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
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (adapterCustomerData == null) {
			return null;
		}
		final Customer item = adapterCustomerData.get(position);
		if (adapterCustomerData != null) {
			
			int poi = position%4;
			switch (poi) {
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
			}
			if(filterNum == null)
			{
				holder.name.setText(item.getName());
			}
			else
			{
				holder.name.setText(Html.fromHtml(item.getName().replace(filterNum  , "<font color='#e50150'>" + filterNum + "</font>")));
			}
			holder.age.setText(String.valueOf(item.getAge() + "岁"));
			holder.investCurrent.setText("资产总额"+String.valueOf(item.getNetAsset() )+"万");
			holder.investTimes.setText("投资次数"+String.valueOf(item.getInvestNumber())+"次");
			holder.investMax.setText("投资总额"+String.valueOf(item.getCurrentInvestValue())+"万");
 
			if(item.getMaritalStatus() != 0)//当婚姻状况为0时，显示为空
			{
				//change by SGDY for BUG#5281 AT 2014/8/15 10:15
				holder.married.setText(married[item.getMaritalStatus() - 1]);
			}
			else{
				holder.married.setText(married[0]);
			}
			holder.job.setText(item.getPosition());
			
			holder.remark.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
			holder.remark.getPaint().setAntiAlias(true);
			// 是否选中
			holder.cbSelected.setChecked(indexMap.get(position));
		}
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
					notifyDataSetInvalidated();
				}
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

}
