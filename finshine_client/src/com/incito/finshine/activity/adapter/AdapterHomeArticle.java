package com.incito.finshine.activity.adapter;

import java.util.ArrayList;

import com.incito.finshine.customer.ContactHistory;
import com.incito.finshine.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterHomeArticle extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;

	private ArrayList<ContactHistory> mItems;

	public AdapterHomeArticle(Context context) {

		mInflater = LayoutInflater.from(context);
		this.context = context;

	}

	public void setItemList(ArrayList<ContactHistory> list) {
		mItems = list;

		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		int size = 0;
		if (mItems != null) {
			size = mItems.size();
		}
		return 5;
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

		// When convertView is not null, we can reuse it directly, there is no
		// need
		// to reinflate it. We only inflate a new View when the convertView
		// supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_home_article, null);

			// Creates a ViewHolder and store references to the two children
			// views
			// we want to bind data to.
			holder = new ViewHolder();

			convertView.setTag(holder);
		} else {
			// Get the ViewHolder back to get fast access to the TextView
			// and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		/*
		CardRecord item = mItems.get(position);
		if (mItems != null) {
			holder.fee.setText(String.valueOf(item.getFee()));
			holder.lastBalance.setText(ResUtils.getString(
					R.string.common_money, (int) item.getLastBalance()));
			holder.thisBalance.setText(ResUtils.getString(
					R.string.common_money, (int) item.getThisBalance()));
			holder.operate.setText(item.getOperTypeName());
			holder.place.setText(item.getPlaceName());
			holder.date.setText(item.getTransactionDate());
			int color = context.getResources().getColor(
					R.color.background_record_white);
			if (position % 2 == 1) {
				color = context.getResources().getColor(
						R.color.background_record_gray);
			}
			holder.background.setBackgroundColor(color);
		}
		*/
		return convertView;
	}

	static class ViewHolder {
		ImageView topBar;
		ImageView bottomBar;
		TextView date;
		TextView time;
		TextView description;
	}

}
