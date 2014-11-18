package com.incito.finshine.activity.adapter;

import java.util.ArrayList;
import java.util.List;

import com.incito.finshine.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class HorizontalListViewAdapter extends BaseAdapter{

	private List<Integer> pageNavigation ;
	private List<Integer> pageNavigationPress ;
	public HorizontalListViewAdapter(Context con, List<Integer> pageNavigation, List<Integer> pageNavigationPress){
		mInflater=LayoutInflater.from(con);
		this.pageNavigation = pageNavigation ;
		this.pageNavigationPress = pageNavigationPress ;
	}
	@Override
	public int getCount() {
		return pageNavigation == null ? 0 : pageNavigation.size();
	}
	private LayoutInflater mInflater;
	@Override
	public Object getItem(int position) {
		return pageNavigation == null ? null : pageNavigation.get(position);
	}
	private ViewHolder vh 	 =new ViewHolder();
	private static class ViewHolder {
		private ImageButton im;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.horizontallistview_item, null);
			vh.im=(ImageButton)convertView.findViewById(R.id.bt_page_task);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder)convertView.getTag();
		}
		if (position == selectItem) {
			vh.im.setImageResource(pageNavigationPress.get(position));
			convertView.setSelected(true);
		} else {
			vh.im.setImageResource(pageNavigation.get(position));			
		}
		return convertView;
	}
	

	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
	}

	private int selectItem = 0;
		
}