package com.incito.finshine.activity.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.incito.finshine.R;
import com.incito.finshine.entity.ContactNote;
import com.incito.utility.DateUtil;

public class AdapterContactNote extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;

	private ArrayList<ContactNote> mItems;

	public AdapterContactNote(Context context) {

		mInflater = LayoutInflater.from(context);
		this.context = context;

	}

	public void setItemList(List<ContactNote> list) {
//		Collections.reverse(list);
		mItems = (ArrayList<ContactNote>) list;
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
			convertView = mInflater.inflate(R.layout.row_contact_note, null);
 
			holder = new ViewHolder();
			holder.date = (TextView) convertView.findViewById(R.id.textDate);
			holder.time = (TextView) convertView.findViewById(R.id.textTime);
			holder.description1 = (TextView) convertView.findViewById(R.id.textDescription1);
			holder.description2 = (TextView) convertView.findViewById(R.id.textDescription2);
			holder.btnTitle = (Button)convertView.findViewById(R.id.btn_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position == 0) {
			holder.btnTitle.setVisibility(View.VISIBLE);
		}else{
			holder.btnTitle.setVisibility(View.INVISIBLE);
		}
		
		ContactNote item = mItems.get(position);
		if(null!= item) {
			String date = DateUtil.formatDate2String(new Date(item.getContactDate()), DateUtil.FORMAT_YYYY_MM_DD_HHMM_WORD_ZH);
			if (!TextUtils.isEmpty(date)) {
				holder.date.setText(date.substring(0, 11));
				holder.time.setText(date.substring(11, 16));
			}
			holder.description1.setText(item.getTitle());
			holder.description2.setText(item.getContent());
		}
		return convertView;
	}

	static class ViewHolder {
		
		ImageView topBar;
		ImageView bottomBar;
		TextView date;
		TextView time;
		TextView description1;
		TextView description2;
		
		Button btnTitle;
	}

}
