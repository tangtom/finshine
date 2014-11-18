package com.custom.view;

import com.android.core.util.SharedUtil;
import com.incito.finshine.R;
import com.incito.finshine.activity.ActCustomerManagement;
import com.incito.finshine.activity.ActCustomerMarketing;
import com.incito.finshine.activity.ActFinshineHomePage;
import com.incito.finshine.activity.ActMyPointer;
import com.incito.finshine.activity.ActProductManagement;
import com.incito.finshine.activity.dialog.CrmsDialog;
import com.incito.utility.CommonUtil;
import com.incito.utility.SharedKey;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import java.util.ArrayList;
import java.util.List;

public class FirstPagePop {

	private Context context = null;

	private View popView = null;

	private View clickView = null;
	private int position = -1;// 记录点击进入此页面的位置

	public PopupWindow getPopWin() {
		return popWin;
	}

	private PopupWindow popWin = null;

	/** popwindow 弹出方向 **/
	public static final int TOP_2_BOTTOM = 1;
	private int popDirection = TOP_2_BOTTOM;

	private List<Integer> icon;
	private List<String> text;
	private ProdDetailAdapter prodDetailAdapter;
	
	public FirstPagePop(Context context, View clickView) {

		this.icon = new ArrayList<Integer>();
		this.text = new ArrayList<String>();

		icon.add(R.drawable.ic_first);
		icon.add(R.drawable.ic_customer1);
		icon.add(R.drawable.ic_product1);
		icon.add(R.drawable.ic_trade1);
		icon.add(R.drawable.ic_commision1);

		String[] array = context.getResources().getStringArray(
				R.array.first_page);
		text.add(array[0]);
		text.add(array[1]);
		text.add(array[2]);
		text.add(array[3]);
		text.add(array[4]);

		this.clickView = clickView;
		this.context = context;

		initPopwindow(clickView);
	}

	public void setPosition(int position) {
		this.position = position;
	}

	private void initPopwindow(View clickView) {
		popView = LayoutInflater.from(context).inflate(
				R.layout.pop_prod_detail_list, null);
		popWin = new PopupWindow(popView, 240, 400, true);

		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
		popWin.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				CommonUtil.hideSoftInputFromWindow((Activity) context);

			}
		});
	}

	public void showPopWindow() {

		initData();

		if (popWin != null) {
			int[] location = new int[2];
			clickView.getLocationOnScreen(location);
			switch (popDirection) {
			case TOP_2_BOTTOM:
				popWin.setAnimationStyle(R.style.anim_top_2_bottom);
				int screenW = CommonUtil.getScreenWidth(context);
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,
						screenW - 240, location[1] + clickView.getHeight() + 3);
				break;
			default:
				break;
			}
		}
	}

	private void initData() {
		ListView listView = (ListView) popView
				.findViewById(R.id.listview_prod_detail);
	 prodDetailAdapter = new ProdDetailAdapter();
		listView.setAdapter(prodDetailAdapter);
		prodDetailAdapter.setSelectItem(position);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				prodDetailAdapter.setSelectItem(position);
				
				Intent i = new Intent();
				if (position != FirstPagePop.this.position) {
					switch (position) {
					case 0:
						// 跳转到首页
						i.setClass(context, ActFinshineHomePage.class);
						context.startActivity(i);
						break;
					case 1:
						// 跳转到客户管理
						if (SharedUtil.getPreferBool(SharedKey.CONFIG_CRMS, false)) {
							i.setClass(context, ActCustomerManagement.class); 
							context.startActivity(i);
						} else {
							CrmsDialog dialog = new CrmsDialog(context);
							dialog.show();
						}

						i.setClass(context, ActCustomerManagement.class);

						break;
					case 2:
						// 跳转到产品管理
						i.setClass(context, ActProductManagement.class);
						context.startActivity(i);
						break;
					case 3:
						// 跳转到营销
						i.setClass(context, ActCustomerMarketing.class);
						context.startActivity(i);
						break;
					case 4:
						// 积分
						i.setClass(context, ActMyPointer.class);
						context.startActivity(i);
						break;
					default:
						break;
					}
					
					closePopWindow();
				}
			}
		});
	}

	public void closePopWindow() {
		if (popWin != null) {
			if (popWin.isShowing()) {
				popWin.dismiss();
				// popWin = null;
			}
		}
	}

	public boolean isShowing() {

		if (popWin != null) {
			if (popWin.isShowing()) {
				return true;
			}
		}
		return false;
	}

	private class ProdDetailAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return text == null ? 0 : text.size();
		}

		@Override
		public Object getItem(int position) {
			return text == null ? 0 : text.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int position, View view, ViewGroup parent) {

			if (view == null) {
				view = LayoutInflater.from(context).inflate(
						R.layout.row_prod_detail_item, null);
			}
			// view.setBackgroundResource(R.drawable.product_list_item_bg);
			Button iv = (Button) view.findViewById(R.id.ivProdInfo);
			// iv.setTextColor(context.getResources().getColorStateList(R.drawable.select_common_text_brownd));
			iv.setText(text.get(position));
			iv.setCompoundDrawablesRelativeWithIntrinsicBounds(
					icon.get(position), 0, 0, 0);
			
			if (position == selectItem) {
				iv.setTextColor(Color.parseColor("#ffffff"));
				view.setBackgroundColor(Color.parseColor("#F2EBD9"));
			} else {
				iv.setTextColor(context.getResources().getColorStateList(
						R.drawable.select_common_text_brownd));
				view.setBackgroundResource(R.drawable.product_list_item_bg);
				
			}
			
			return view;
		}

		public void setSelectItem(int selectItem) {
			this.selectItem = selectItem;
		}

		private int selectItem =-1;
	}

}
