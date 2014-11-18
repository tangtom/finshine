package com.incito.finshine.activity.adapter;

import java.util.Date;
import java.util.List;

import com.custom.view.MySeekBar;
import com.incito.finshine.R;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.InvestmentDistributionStatistics;
import com.incito.finshine.entity.ProdProfitResult;
import com.incito.utility.DateUtil;

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
 * <dd>Description:客户管理的首界面  pop出的分红</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月20日 上午11:36:05</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class AdapterPopCustomerDivided extends BaseAdapter {
	
	private static final String TAG = AdapterPopCustomerDivided.class.getSimpleName();
	
	private Context context;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	
	private List<ProdProfitResult> dividedList;
	
	public AdapterPopCustomerDivided(Context context){
		mInflater = LayoutInflater.from(context);
		this.context = context;
		
	}
	
	public void setItemList(List<ProdProfitResult> list) {

		dividedList = list;
		notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		return dividedList.size();
	}

	@Override
	public Object getItem(int position) {
		return dividedList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = mInflater.inflate(R.layout.row_customer_divided, null);
			holder = new ViewHolder();
			holder.prodName = (TextView) convertView.findViewById(R.id.prodName);
			holder.profitDate = (TextView) convertView.findViewById(R.id.profitDate);
			
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		ProdProfitResult item = dividedList.get(position);
		if(dividedList != null)
		{
			holder.prodName.setText(item.getProdName().length() > 8 ? (item.getProdName().trim().substring(0, 8) + "...") : item.getProdName().trim());
			holder.profitDate.setText(DateUtil.formatDate2String(new Date(item.getProfitDate()), DateUtil.FORMAT_YYYY_MM_DD_ZH));
		}
		return convertView;
	}
	
	static class ViewHolder{
		TextView prodName;
		TextView profitDate;
	}

}
