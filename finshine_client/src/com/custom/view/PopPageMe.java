package com.custom.view;

import java.util.List;

import com.incito.finshine.R;
import com.incito.finshine.activity.ActFinshineHomePage;
import com.incito.finshine.activity.ActMyPointer;
import com.incito.utility.CommonUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

public class PopPageMe implements OnClickListener {	
	private Context context = null;
	private View popView = null;
	private View clickView = null;
	private Window window;
	private PopPropertyManagement ppm;
	public PopupWindow getPopWin() {
		return popWin;
	}
	private PopupWindow popWin = null;
	/** popwindow 弹出方向 **/
	public static final int BOTTON_2_TOP = 3;
	private int popDirection = BOTTON_2_TOP;
	private List<Integer> findDetail ;

	private int backGroundId = -1;
	public PopPageMe(Context context, View clickView,int backGroundId,
			int popDirection, List<Integer> findDetail,Window window) {

		super();
		this.clickView = clickView;
		this.context = context;
		this.backGroundId = backGroundId;
		this.popDirection = popDirection;
		this.findDetail = findDetail ;		
		initPopwindow(clickView);
		this. window=window;
	}

	private void initPopwindow(View clickView) {

		popView = LayoutInflater.from(context).inflate(R.layout.pop_page_me, null);
		popWin = new PopupWindow(popView, 260, 245, true);
		if (backGroundId > -1) {
			popView.findViewById(R.id.lt_me_detail).setBackgroundResource(backGroundId);
		} 
		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
		popWin.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				CommonUtil.hideSoftInputFromWindow((Activity)context);
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
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY,clickView.getWidth() + 676, clickView.getHeight() + 330);
				break;
			default:
				break;
			}
		}
	}

	private void initData() {

		ListView listView = (ListView) popView.findViewById(R.id.listview_me_detail);
		listView.setAdapter(new FindDetailAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {			
				switch (position) {
				case 0:
					//PopPageMe popPageMe = new PopPageMe(ActFinshineHomePage.this, v, R.drawable.pop_page_me,
					//		PopPageMe.BOTTON_2_TOP, pageMeList);
					//popPageMe.showPopWindow();
					if(ppm == null)
					{
						ppm = new PopPropertyManagement(context,clickView,window);
					}
					ppm.showPopWindow();
					 WindowManager.LayoutParams lp = window.getAttributes();  
			          lp.alpha = 0.5f;  
			          window.setAttributes(lp); 
					      
					break;
				case 1:
					context.startActivity(new Intent(context,ActMyPointer.class));
					break;
				case 2:
					Toast.makeText(context, "敬请期待", 1).show();
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
				view = LayoutInflater.from(context).inflate(R.layout.row_find_detail_item, null);
			}
			
			Button iv = (Button)view.findViewById(R.id.ivProdInfo1);
			iv.setTextColor(context.getResources().getColorStateList(R.drawable.select_common_text_brown_gray));
			iv.setText(findDetail.get(position));
			return view;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
