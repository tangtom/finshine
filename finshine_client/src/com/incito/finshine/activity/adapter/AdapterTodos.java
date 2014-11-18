package com.incito.finshine.activity.adapter;

import java.util.ArrayList;
import java.util.Date;

import com.incito.finshine.R;
import com.incito.finshine.entity.TodoItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterTodos extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;

	private ArrayList<TodoItem> mItems = new ArrayList<TodoItem>();

	public AdapterTodos(Context context) {

		mInflater = LayoutInflater.from(context);
		this.context = context;

	}

	public void setItemList(ArrayList<TodoItem> list) {
		mItems = list;

		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		int size = 0;
		if (mItems != null) {
			size = mItems.size();
		}
		return size;
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
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			
			convertView = mInflater.inflate(R.layout.row_schedule_record, null);
			holder = new ViewHolder();
			holder.title = (TextView)convertView.findViewById(R.id.txtTitle);
			holder.startDate = (TextView)convertView.findViewById(R.id.txtDate);
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		TodoItem item = mItems.get(position);
		if(item!=null){
			holder.title.setText(item.getTitle());
			Date date = new Date(item.getStartTime());
			holder.startDate.setText(date.toLocaleString());
		}
		return convertView;
	}

	class ViewHolder {
		TextView title;
		TextView startDate;
	}

}
