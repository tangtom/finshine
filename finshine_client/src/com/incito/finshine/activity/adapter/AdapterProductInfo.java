package com.incito.finshine.activity.adapter;


import java.util.List;

import com.incito.finshine.R;
import com.incito.finshine.entity.Product;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterProductInfo extends BaseAdapter {
	private final String TAG = AdapterProductInfo.class.getSimpleName();
	private LayoutInflater mInflater;
	private Context context;

	private List<Product> mItems = null;

	public AdapterProductInfo(Context context) {

		mInflater = LayoutInflater.from(context);
		this.context = context;

	}

	public void setItemList(List<Product> list) {
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
		long id = mItems.get(position).getId();
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		// When convertView is not null, we can reuse it directly, there is no
		// need
		// to reinflate it. We only inflate a new View when the convertView
		// supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_product_info, null);

			holder = new ViewHolder();
			holder.id = (TextView)convertView.findViewById(R.id.txtProductId);
			holder.name = (TextView)convertView.findViewById(R.id.txtProductName);
			holder.type = (TextView)convertView.findViewById(R.id.txtType);
			holder.limit = (TextView)convertView.findViewById(R.id.txtLimit);
			holder.publish = (TextView)convertView.findViewById(R.id.txtPublish);
			holder.start = (TextView)convertView.findViewById(R.id.txtStart);
			holder.profit = (TextView)convertView.findViewById(R.id.txtProfit);
			holder.favorite = (ImageView)convertView.findViewById(R.id.imgFavorite);
			convertView.setTag(holder);
		} else {
			// Get the ViewHolder back to get fast access to the TextView
			// and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.

		Product item = mItems.get(position);
		

		return convertView;
	}

	static class ViewHolder {
		TextView id;
		TextView name;
		TextView type;
		TextView limit;
		TextView publish;
		TextView start;
		TextView profit;
		ImageView favorite;
	}

}
