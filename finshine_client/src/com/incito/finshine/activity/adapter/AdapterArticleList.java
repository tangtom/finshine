package com.incito.finshine.activity.adapter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.incito.finshine.entity.Article;
import com.incito.finshine.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

public class AdapterArticleList extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;

	private ArrayList<Article> mItems;

	public AdapterArticleList(Context context) {

		mInflater = LayoutInflater.from(context);
		this.context = context;

	}

	public void setItemList(ArrayList<Article> list) {
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
		return mItems.get(position).getId();
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
			convertView = mInflater.inflate(R.layout.row_article_list, null);

			// Creates a ViewHolder and store references to the two children
			// views
			// we want to bind data to.
			holder = new ViewHolder();
			Field[] fields = ViewHolder.class.getDeclaredFields();
			try {
				for (Field f : fields) {

					Class cr = Class.forName("com.incito.finshine.R$id");
					int rid = cr.getField(f.getName()).getInt(cr);
					View v =  convertView.findViewById(rid);
					if (v != null) {
						f.set(holder, v);
					}

				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}
			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}
		Article item = mItems.get(position);
		if (item != null) {

			Field[] hfields = ViewHolder.class.getDeclaredFields();
			try {
				for (Field f : hfields) {
					String methodName = "get"
							+ f.getName().substring(0, 1).toUpperCase()
							+ f.getName().substring(1);
					Log.i("tag", "method name = " + methodName);
					Method get = Article.class.getMethod(methodName);
					if (get != null) {
						String value = String.valueOf(get.invoke(item));
						Method set = TextView.class.getMethod("setText",
								CharSequence.class);

						Field txt = ViewHolder.class.getDeclaredField(f
								.getName());

						Log.i("tag", "field = " + txt.getType());

						TextView tv = (TextView) txt.get(holder);

						set.invoke(tv, value);
					}

				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		return convertView;
	}

	static class ViewHolder {
		TextView author;
		TextView title;
		TextView content;
		TextView resource;
		TextView postTime;
	}

}
