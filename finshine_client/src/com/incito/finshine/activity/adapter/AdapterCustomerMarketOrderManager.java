package com.incito.finshine.activity.adapter;

import java.util.ArrayList;
import java.util.List;

import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterArticleList.ViewHolder;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.HistoryOrderItem;
import com.incito.finshine.entity.MarKetCustomer;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

/**
 * <dl>
 * <dt>AdapterCustomerMarketOrderManager.java</dt>
 * <dd>Description:订单管理</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-18 上午12:17:43</dd>
 * </dl>
 * 
 * @author lihs
 */
public class AdapterCustomerMarketOrderManager extends BaseAdapter {
	
	private final String TAG = AdapterCustomerMarketOrderManager.class.getSimpleName();
	private LayoutInflater mInflater;
	private Context context;
	
	private List<HistoryOrderItem> mItems = null;// 全部数据
	private List<HistoryOrderItem> mItems1 = null;// 备用数据
	private String filterName;
	private Filter filter;
	
	public AdapterCustomerMarketOrderManager(Context context){
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
	}

	
	public void setItemList(List<HistoryOrderItem> list) {
		mItems = list;
		mItems1 = list;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mItems == null?0:mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;
		if (view == null){
			view = mInflater.inflate(R.layout.row_customer_market_order_manager, null);
			holder = new ViewHolder();
			holder.saleOrderCode = (TextView)view.findViewById(R.id.saleOrderCode);
			holder.createDate = (TextView)view.findViewById(R.id.createDate);
			holder.customerName = (TextView)view.findViewById(R.id.customerName);
			holder.prodName = (TextView)view.findViewById(R.id.prodName);
			holder.totalMoney = (TextView)view.findViewById(R.id.totalMoney);
			holder.payStates = (TextView)view.findViewById(R.id.payStates);
			holder.tradeStates = (TextView)view.findViewById(R.id.tradeStates);
			holder.failReson = (TextView)view.findViewById(R.id.failReson);
			view.setTag(holder);
		}else{
			holder = (ViewHolder)view.getTag();
		}
		
		HistoryOrderItem item = mItems.get(position);
		if (TextUtils.isEmpty(filterName)) {
			holder.saleOrderCode.setText(item.getSalesOrderNumber());
			holder.customerName.setText(item.getCustomerName());
		} else {
			if(filterName.charAt(0) < 58 && filterName.charAt(0) > 47)//判断首字母如果为数字，匹配订单编号
			{
				holder.saleOrderCode.setText(Html.fromHtml(item.getSalesOrderNumber()
						.replace(filterName, "<font color='#e50150'>"
								+ filterName + "</font>")));
			}
			else
			{
				holder.customerName.setText(Html.fromHtml(item.getCustomerName()
						.replace(filterName, "<font color='#e50150'>"
								+ filterName + "</font>").replace(filterName.toUpperCase(), "<font color='#e50150'>"
										+ filterName.toUpperCase() + "</font>")));
			}
		}
//		holder.saleOrderCode.setText(item.getSalesOrderNumber());
		holder.createDate.setText(item.getCreatedStr()); 
//		holder.customerName.setText(item.getCustomerName());
		holder.prodName.setText(item.getProdName());
		holder.totalMoney.setText(String.valueOf(item.getTotalAmount()) + "万元");
		holder.payStates.setText(context.getResources().getStringArray(R.array.pay_status)[(int)item.getOrderStatusId()]);
		holder.tradeStates.setText(context.getResources().getStringArray(R.array.trade_status)[(int)item.getTradingResultId()]);
		holder.failReson.setText(item.getFailReason().toString().length() > 0 ? (item.getFailReason().toString().length() > 10 ? "(" + item.getFailReason().toString().substring(0, 10) + "...)" : "(" + item.getFailReason().toString() + ")") : item.getFailReason());
		
		return view;
	}
	
	class ViewHolder{
		
		TextView saleOrderCode;//订单编号
		TextView createDate;
		TextView customerName;
		TextView prodName;
		TextView totalMoney;
		TextView payStates;
		TextView tradeStates;
		TextView failReson;
	}

	/*---------------    根据客户姓名搜索过滤       ---------------*/
	public Filter getFilter()
	{
		
		if(filter == null)
		{
			filter = new MyFilter();
		}
		return filter;
	}
	
	private class MyFilter extends Filter
	{

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub
			constraint = constraint.toString().toLowerCase();
			filterName = constraint.toString();
			FilterResults results = new FilterResults();
			List<HistoryOrderItem> lists = new ArrayList<HistoryOrderItem>();
			if(constraint != null && constraint.toString().length() > 0 && !mItems.isEmpty())
			{
				for(HistoryOrderItem mc : mItems)
				{
					if(constraint.charAt(0) < 58 && constraint.charAt(0) > 47)//判断首字母如果为数字，匹配订单编号
					{
						if(mc.getSalesOrderNumber().toLowerCase().contains(constraint))
						{
							lists.add(mc);
						}
					}
					else//如果不为数字，匹配用户名
					{
						if(mc.getCustomerName().toLowerCase().contains(constraint))
						{
							lists.add(mc);
						}
					}
				}
			}
			results.count = lists.size();
			results.values = lists;
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			// TODO Auto-generated method stub
			if(TextUtils.isEmpty(constraint))
			{
				mItems = mItems1;
				notifyDataSetChanged();
				return;
			}
			mItems = (ArrayList<HistoryOrderItem>)results.values;
			if(results.count > 0)
			{
				notifyDataSetChanged();
			}
			else
			{
				notifyDataSetInvalidated();
			}
		}
	}
	
}
