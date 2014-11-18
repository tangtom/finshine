package com.incito.finshine.activity.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.incito.finshine.R;
import com.incito.finshine.entity.Pointer;
import com.incito.utility.DateUtil;

public class AdapterMyPointer extends BaseAdapter{

	private Context context;
	private List<Pointer> pointerLists;
	private List<Pointer> pointerLists1;//备用Lists
	private String filterName;
	private Filter filter;
	
	public AdapterMyPointer(Context context,List<Pointer> pointerLists) {
		super();
		this.context = context;
		this.pointerLists = pointerLists;
		if(pointerLists1 == null)
		{
			pointerLists1 = pointerLists;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pointerLists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pointerLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(null == convertView){
			convertView = LayoutInflater.from(context).inflate(R.layout.row_pointer_detail, null);
			viewHolder = new ViewHolder();
			viewHolder.textOrderNumber = (TextView)convertView.findViewById(R.id.textOrderNumber);
			viewHolder.textPublisher = (TextView)convertView.findViewById(R.id.textPublisher);
			viewHolder.textAmount = (TextView)convertView.findViewById(R.id.textAmount);
			viewHolder.textSalemans = (TextView)convertView.findViewById(R.id.textSalemans);
			viewHolder.textDateOfCreate = (TextView)convertView.findViewById(R.id.textDateOfCreate);
			viewHolder.textTotalCommissionRatio = (TextView)convertView.findViewById(R.id.textTotalCommissionRatio);
			viewHolder.textFixedCommissionRatio = (TextView)convertView.findViewById(R.id.textFixedCommissionRatio);
			viewHolder.textExtraCommissionRatio = (TextView)convertView.findViewById(R.id.textExtraCommissionRatio);
			viewHolder.linearCommissionCount = (LinearLayout)convertView.findViewById(R.id.LinearCommissionCount);
			viewHolder.textTotalCommission = (TextView)convertView.findViewById(R.id.textTotalCommission);
			viewHolder.textFixedCommission = (TextView)convertView.findViewById(R.id.textFixedCommission);
			viewHolder.textExtraCommission = (TextView)convertView.findViewById(R.id.textExtraCommission);
			viewHolder.textResultFk = (TextView)convertView.findViewById(R.id.textResultFk);
			viewHolder.imgIsNews = (ImageView)convertView.findViewById(R.id.imgIsNews);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Pointer pointer = pointerLists.get(position);
		viewHolder.textPublisher.setText(pointer.getProduct().getProdName());
		viewHolder.textAmount.setText(String.valueOf(new BigDecimal(pointer.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP)) + "万");
		if (TextUtils.isEmpty(filterName)) {
			viewHolder.textOrderNumber.setText(pointer.getOrderNumber());
			viewHolder.textSalemans.setText(pointer.getCustomer().getName());
		} else {
			if(filterName.charAt(0) < 58 && filterName.charAt(0) > 47)//判断首字母如果为数字，匹配订单编号
			{
				viewHolder.textOrderNumber.setText(Html.fromHtml(pointer.getOrderNumber()
						.replace(filterName, "<font color='#e50150'>"
								+ filterName + "</font>")));
			}
			else
			{
				viewHolder.textSalemans.setText(Html.fromHtml(pointer.getCustomer().getName()
						.replace(filterName, "<font color='#e50150'>"
								+ filterName + "</font>").replace(filterName.toUpperCase(), "<font color='#e50150'>"
										+ filterName.toUpperCase() + "</font>")));
			}
		}
		viewHolder.textDateOfCreate.setText(DateUtil.formatDate2String(pointer.getDateOfCreate(), DateUtil.FORMAT_YYYY_MM_DD));
		viewHolder.textTotalCommissionRatio.setText(String.valueOf(new BigDecimal(pointer.getFixedCommissionRatio() + pointer.getExtraCommissionRatio()).setScale(2,BigDecimal.ROUND_HALF_UP)) + "%");
		viewHolder.textFixedCommissionRatio.setText(String.valueOf(new BigDecimal(pointer.getFixedCommissionRatio()).setScale(2,BigDecimal.ROUND_HALF_UP)) + "%");
		viewHolder.textExtraCommissionRatio.setText(String.valueOf(new BigDecimal(pointer.getExtraCommissionRatio()).setScale(2,BigDecimal.ROUND_HALF_UP)) + "%");
		viewHolder.textTotalCommission.setText(String.valueOf(new BigDecimal(pointer.getFixedCommission() + pointer.getExtraCommission()).setScale(2,BigDecimal.ROUND_HALF_UP)) + "万");
		viewHolder.textFixedCommission.setText(String.valueOf(new BigDecimal(pointer.getFixedCommission()).setScale(2,BigDecimal.ROUND_HALF_UP)) + "万");
		viewHolder.textExtraCommission.setText(String.valueOf(new BigDecimal(pointer.getExtraCommission()).setScale(2,BigDecimal.ROUND_HALF_UP)) + "万");
		if(pointer.getResult_fk() == 2){
			viewHolder.textResultFk.setText("已到账");
			viewHolder.textResultFk.setTextColor(Color.RED);
			viewHolder.linearCommissionCount.setVisibility(View.VISIBLE);
		}else{
			viewHolder.textResultFk.setText("审核中");
			viewHolder.textResultFk.setTextColor(context.getResources().getColor(R.color.text_gray_808080));
			viewHolder.linearCommissionCount.setVisibility(View.GONE);
		}
		if(pointer.getIsNew() == 0)
		{
			viewHolder.imgIsNews.setVisibility(View.INVISIBLE);
		}
		else if(pointer.getIsNew() == 1)
		{
			viewHolder.imgIsNews.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
	
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
			List<Pointer> lists = new ArrayList<Pointer>();
			if(constraint != null && constraint.toString().length() > 0 && !pointerLists.isEmpty())
			{
				for(Pointer p : pointerLists)
				{ 	
					if(constraint.charAt(0) < 58 && constraint.charAt(0) > 47)//判断首字母如果为数字，匹配订单编号
					{
						if(p.getOrderNumber().toLowerCase().contains(constraint))
						{
							lists.add(p);
						}
					}
					else//如果不为数字，匹配用户名
					{
						if(p.getCustomer().getName().toLowerCase().contains(constraint))
						{
							lists.add(p);
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
				pointerLists = pointerLists1;
				notifyDataSetChanged();
				return;
			}
			pointerLists = (ArrayList<Pointer>)results.values;
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

	class ViewHolder{
		TextView textOrderNumber;
		TextView textPublisher;
		TextView textAmount;
		TextView textSalemans;
		TextView textDateOfCreate;
		TextView textTotalCommissionRatio;
		TextView textFixedCommissionRatio;
		TextView textExtraCommissionRatio;
		LinearLayout linearCommissionCount;
		TextView textFixedCommission;
		TextView textExtraCommission;
		TextView textTotalCommission;
		TextView textResultFk;
		ImageView imgIsNews;//是否显示news图片
	}
}
