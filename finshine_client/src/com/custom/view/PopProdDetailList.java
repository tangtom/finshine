package com.custom.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.android.core.util.AppToast;
import com.incito.finshine.R;
import com.incito.finshine.activity.dialog.DialogAppointShare;
import com.incito.finshine.entity.Product;
import com.incito.finshine.manager.CoreManager.AppStatus;
import com.incito.utility.CommonUtil;

public class PopProdDetailList {

	private Product prod;

	private Context context = null;

	private View popView = null;

	private View clickView = null;

	public PopupWindow getPopWin() {
		return popWin;
	}
	private PopupWindow popWin = null;

	/** popwindow 弹出方向 **/
	public static final int TOP_2_BOTTOM = 3;

	private int popDirection = TOP_2_BOTTOM;
	private List<Integer> icon;
	private List<Integer> text;

	public PopProdDetailList(Context context, View clickView, Product prod,
			int popDirection, List<Integer> icon, List<Integer> text) {

		super();
		this.prod = prod;

		this.icon = icon;
		this.text = text;

		this.clickView = clickView;
		this.context = context;

		this.popDirection = popDirection;

		initPopwindow(clickView);
	}

	private void initPopwindow(View clickView) {

		popView = LayoutInflater.from(context).inflate(R.layout.pop_prod_detail_list_1, null);
 
		popWin = new PopupWindow(popView, 260, 400, true);

		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
		popWin.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				CommonUtil.hideSoftInputFromWindow((Activity)context);
				
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
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,screenW - 290, location[1] + clickView.getHeight() + 3);
				break;
			default:
				break;
			}
		}
	}

	private void initData() {

		ListView listView = (ListView) popView.findViewById(R.id.listview_prod_detail);
		listView.setAdapter(new ProdDetailAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				switch (position) {
				case 0:
					AppToast.ShowToast(R.string.com_coming_soon);
					break;
				case 1:

					break;
				case 2:
					CommonUtil.dialPhone(context, prod.getProdCstTel());
					break;
				case 3:

					break;
				case 4:
					DialogAppointShare appShare = new DialogAppointShare(context, prod);
					appShare.getDialog().show();
					break;

				default:
					break;
				}
				closePopWindow();
			}
		});
	}

	public void closePopWindow() {
		if (popWin != null) {
			if (popWin.isShowing()) {
				popWin.dismiss();
//				popWin = null;
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

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			
			if (view == null) {
				view = LayoutInflater.from(context).inflate(R.layout.row_prod_detail_item, null);
			}
			
			Button iv = (Button)view.findViewById(R.id.ivProdInfo);
			iv.setTextColor(context.getResources().getColorStateList(R.drawable.select_common_text_brownd));
			iv.setText(text.get(position));
			iv.setCompoundDrawablesRelativeWithIntrinsicBounds(icon.get(position), 0, 0, 0);
			return view;
		}
	}
}
