package com.incito.finshine.activity.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.incito.finshine.R;
import com.incito.finshine.entity.PropsUsedItemWSEntity;

public class AdapterPopUseCommission extends BaseAdapter{

	private Context context;
	private final String TAG = AdapterPopUseCommission.class.getSimpleName();
	private List<PropsUsedItemWSEntity> propLists;
	public AdapterPopUseCommission(Context context,List<PropsUsedItemWSEntity> propLists){
		this.context = context;
		this.propLists = propLists;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return propLists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return propLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parentView) {
		ViewHolder viewHolder = null;
		if(convertView == null)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.row_consume_commission, null);
			viewHolder = new ViewHolder();
			viewHolder.textCommissionName = (TextView)convertView.findViewById(R.id.text_commission_name);
			viewHolder.textCommissionNum = (TextView)convertView.findViewById(R.id.text_commission_num);
			viewHolder.textConsume = (TextView)convertView.findViewById(R.id.text_consume);
			convertView.setTag(viewHolder);
		}else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		if(position == 0)
		{
			//viewHolder.textConsume.setText("消耗：");
			viewHolder.textConsume.setVisibility(View.VISIBLE);
		}else
		{
			//viewHolder.textConsume.setText("");
			viewHolder.textConsume.setVisibility(View.INVISIBLE);
		}
		PropsUsedItemWSEntity p = propLists.get(position);
		viewHolder.textCommissionName.setText(p.getProps().getName());
		viewHolder.textCommissionNum.setText(String.valueOf(p.getQtyOfUsed()));
		return convertView;
	}

	class ViewHolder{
		TextView textConsume;
		TextView textCommissionName;
		TextView textCommissionNum;
	}
}
