package com.custom.view;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.incito.finshine.R;

public class CommonPopWindow {
	
	private static final int ITEM_WIDTH = 60;

	private static final int HORIZONTAL_SPACING = 8;
	
	private Context context = null;
	
	private View popView = null; 
	
	private View clickView = null;
	
	private PopupWindow popWin = null;
	
	private GridView gridV = null;
	
	private List<Integer> idList = null;
	
	private  int width = 0;
	
	private PopWindowAdapter popAda ;
	
	private int popDirection = 0;
	
	/**popwindow 弹出方向**/
	public static final int  LEFT_2_RIGHT = 1; 
	public static final int  RIGHT_2_LEFT = 2; 
	
	private int backGroundId = -1;
	/**popwindow 背景图片**/
	
	private Object obj = null;
 
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	public CommonPopWindow(Context context,View clickView,List<Integer> idList,int backGroundId,int popDirection) {
		
		super();
		this.idList = idList;
		this.clickView = clickView;
		this.context = context;
		
		this.backGroundId = backGroundId;
		this.popDirection = popDirection;
		
		initPopwindow(clickView);
	}
	
	
	
	private void initPopwindow(View clickView){
		
		popView = LayoutInflater.from(context).inflate(R.layout.common_pop_window, null);
		
		popWin = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT ,true);
		
		if (backGroundId > -1) {
			popView.findViewById(R.id.lt_pop).setBackgroundResource(backGroundId);
		} 
		
		// 设置 点击外部可以关闭
		popWin.setBackgroundDrawable(new ColorDrawable());
		popWin.setOutsideTouchable(true);
		popWin.setFocusable(true);
		
		showPop();
	}
	
	
    public void showPop(){
    	
         if (idList != null) {
			 gridV =(GridView)popView.findViewById(R.id.grid_popdata);
			 popAda = new PopWindowAdapter();
			 gridV.setAdapter(popAda);
			 
			  LayoutParams params = new LayoutParams(popAda.getCount() *ITEM_WIDTH + HORIZONTAL_SPACING*(popAda.getCount() - 1),LayoutParams.WRAP_CONTENT);
			  gridV.setLayoutParams(params);
			  gridV.setColumnWidth(ITEM_WIDTH);
			  gridV.setStretchMode(GridView.NO_STRETCH);
			  gridV.setHorizontalSpacing(HORIZONTAL_SPACING);
			  gridV.setNumColumns(popAda.getCount());
			  
			  gridV.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					closePopWindow();
					
					if (interCb != null) {
						interCb.doClick(position,obj);
					}
				}
			});
		 }
    }
    
    
    public void showPopWindow(){
    	if (popWin != null) {
    		int[] location = new int[2];
            clickView.getLocationOnScreen(location);
            width = popAda.getCount() *ITEM_WIDTH + HORIZONTAL_SPACING*(popAda.getCount() - 1) + 10;
            switch (popDirection) {
			case LEFT_2_RIGHT:
				// 从左到右弹出
				popWin.setAnimationStyle(R.style.anim_left_2_right);
				popWin.showAtLocation(clickView, Gravity.NO_GRAVITY, location[0] + clickView.getWidth() + 2, location[1] + clickView.getHeight()/2 - 52);
				break;
			case RIGHT_2_LEFT:
				// 从右到左弹出
				break;
			default:
				break;
			}
		}
    }
	
    public void closePopWindow(){
    	if (popWin != null) {
			if (popWin.isShowing()) {
				popWin.dismiss();
				popWin = null;
			}
		}
    }
    
	public boolean isShowing(){
		
		if (popWin != null) {
			if (popWin.isShowing()) {
				return true;
			}
		}
		return false;
	}
	
	
	private class PopWindowAdapter extends BaseAdapter{

		public PopWindowAdapter() {
			super();
		}

		@Override
		public int getCount() {
			return idList == null?0:idList.size();
		}

		@Override
		public Object getItem(int position) {
			return  idList == null?0:idList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			 
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.row_pop_window, null);
				holder = new ViewHolder();
				holder.icon = (ImageView)convertView.findViewById(R.id.iv_pop_window);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder)convertView.getTag();
			}
			holder.icon.setImageResource(idList.get(position));
			return convertView;
		}
		
		private class ViewHolder{
			public ImageView icon;
		}
	}
	
	private CallBackCLickEvent interCb = null;
 
	public void setCallBackCLickEvent(CallBackCLickEvent interCb) {
		this.interCb = interCb;
	}

	public interface CallBackCLickEvent{
		public void doClick(int position,Object obj);
	}
}
