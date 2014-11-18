package com.custom.view;

import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

import com.incito.finshine.R;
import com.incito.finshine.activity.ActCustomerMarketing;
import com.incito.finshine.activity.ActFinshineHomePage;
import com.incito.finshine.activity.ActProductManagement;
import com.incito.utility.CommonUtil;

public class PopPageFind implements OnClickListener {
	private Context context = null;
	private View popView = null;
	private View clickView = null;

	public PopupWindow getPopWin() {
		return popWin;
	}

	private PopupWindow popWin = null;
	/** popwindow 弹出方向 **/
	public static final int BOTTON_2_TOP = 3;
	private int popDirection = BOTTON_2_TOP;
	private List<Integer> findDetail;

	private int backGroundId = -1;

	public PopPageFind(Context context, View clickView, int backGroundId,
			int popDirection, List<Integer> findDetail) {

		super();
		this.clickView = clickView;
		this.context = context;
		this.backGroundId = backGroundId;
		this.popDirection = popDirection;
		this.findDetail = findDetail;
		initPopwindow(clickView);
	}
	
	private void initPopwindow(View clickView) {

		popView = LayoutInflater.from(context).inflate(R.layout.pop_page_find,
				null);
		popWin = new PopupWindow(popView, 260, 435, true);
		if (backGroundId > -1) {
			popView.findViewById(R.id.lt_find_detail).setBackgroundResource(
					backGroundId);
		}
		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
		popWin.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				CommonUtil.hideSoftInputFromWindow((Activity) context);
				ActFinshineHomePage.backHome();
			}
		});
	}

	public void showPopWindow() {

		initData();

		if (popWin != null) {
			int[] location = new int[2];
			clickView.getLocationOnScreen(location);
			switch (popDirection) {
			case BOTTON_2_TOP:
				popWin.setAnimationStyle(R.style.anim_bottom_2_top);
				int screenW = CommonUtil.getScreenWidth(context);
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,
						clickView.getWidth() + 360, clickView.getHeight() + 138);
				break;
			default:
				break;
			}
		}
	}

	private void initData() {

		ListView listView = (ListView) popView
				.findViewById(R.id.listview_find_detail);
		listView.setAdapter(new FindDetailAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent();
				switch (position) {
				case 0:					
					i.setClass(view.getContext(), ActProductManagement.class);
					view.getContext().startActivity(i);
					break;
				case 1:
					i.setClass(view.getContext(), ActCustomerMarketing.class);
					view.getContext().startActivity(i);
					break;
				case 2:
					openApp("com.kaiying.news");
					break;
				case 3:
					Toast.makeText(context, "敬请期待", 1).show();
					break;
				case 4:
					Toast.makeText(context, "敬请期待", 1).show();
					break;
				case 5:
					Toast.makeText(context, "敬请期待", 1).show();
					break;
				default:
					break;
				}
				closePopWindow();
			}
		});
	}

	protected void startActivity(Intent intent) {
		// TODO Auto-generated method stub

	}

	public void closePopWindow() {
		if (popWin != null) {
			if (popWin.isShowing()) {
				popWin.dismiss();
				// popWin = null;
			}
		}
	}
	 void openApp(String packageName) {
			PackageInfo pi;
			try {
				pi = context.getPackageManager().getPackageInfo(packageName, 0);
				Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
				resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				resolveIntent.setPackage(pi.packageName);
				 
				List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);
				 
				ResolveInfo ri = apps.iterator().next();
				if (ri != null ) {
				//String packageName = ri.activityInfo.packageName;
				String className = ri.activityInfo.name;
				 
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);
				 
				ComponentName cn = new ComponentName(packageName, className);
				 
				intent.setComponent(cn);
				if(intent.resolveActivity(context.getPackageManager()) != null){
					context.startActivity(intent);
				}
				else {
					Toast.makeText(context, "未找到该应用", Toast.LENGTH_LONG).show();
				}
				
				}
				else {
					Toast.makeText(context, "未找到该应用", Toast.LENGTH_LONG).show();
				}
				
			} catch (Exception e) {
				Toast.makeText(context, "未找到该应用", Toast.LENGTH_LONG).show();
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

	private class FindDetailAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return findDetail == null ? 0 : findDetail.size();
		}

		@Override
		public Object getItem(int position) {
			return findDetail == null ? 0 : findDetail.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {

			if (view == null) {
				view = LayoutInflater.from(context).inflate(
						R.layout.row_find_detail_item, null);
			}

			Button iv = (Button) view.findViewById(R.id.ivProdInfo1);
			iv.setTextColor(context.getResources().getColorStateList(
					R.drawable.select_common_text_brown_gray));
			iv.setText(findDetail.get(position));
			return view;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	
}
