package com.incito.finshine.activity.adapter;

import java.util.ArrayList;
import java.util.List;

import com.custom.view.MySeekBar;
import com.incito.finshine.R;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.InvestmentDistributionStatistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * <dl>
 * <dt>AdapterPopCustomerMarketing.java</dt>
 * <dd>Description:客户管理的首界面  pop出的投资数量的下部分  和  客户管理分页的投资分析左二界面</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月20日 上午11:36:05</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class AdapterPopCustomerMarketing extends BaseAdapter {
	
	private static final String TAG = AdapterPopCustomerMarketing.class.getSimpleName();
	
	private Context context;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	
	private int layoutId;
	
	private List<InvestmentDistributionStatistics> investList;
	
	public AdapterPopCustomerMarketing(Context context){
		mInflater = LayoutInflater.from(context);
		this.context = context;
		
	}
	
	public void setItemList(List<InvestmentDistributionStatistics> list) {

		investList = list;
		notifyDataSetChanged();
	}
	
	public void setLayout(int id){
		this.layoutId = id;
	}


	@Override
	public int getCount() {
		return investList.size();
	}

	@Override
	public Object getItem(int position) {
		return investList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = mInflater.inflate(layoutId, null);
			holder = new ViewHolder();
			holder.prodFirstTypeName = (TextView) convertView.findViewById(R.id.prodFirstTypeName);
			holder.purchasedQtyPercent = (MySeekBar) convertView.findViewById(R.id.purchasedQtyPercent);
			holder.purchasedQty = (TextView) convertView.findViewById(R.id.purchasedQty);
			
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		InvestmentDistributionStatistics item = investList.get(position);
		if(investList != null)
		{
			holder.prodFirstTypeName.setText(item.getProdFirstTypeName());
			holder.purchasedQtyPercent.setProgress(item.getPurchasedQty());
			holder.purchasedQty.setText(item.getPurchasedQty() + "单");
		}
		return convertView;
	}
	
	static class ViewHolder{
		TextView prodFirstTypeName;
		MySeekBar purchasedQtyPercent;
		TextView purchasedQty;
	}

}
