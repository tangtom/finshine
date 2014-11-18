package com.custom.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.incito.finshine.R;

/**
 * <dl>
 * <dt>CommSortView.java</dt>
 * <dd>Description:通用排序控件</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-2 下午3:15:22</dd>
 * </dl>
 * 
 * @author lihs
 */
public class CommSortView  implements OnClickListener{

	
	private static final int COMMON_BTN_WIDTH = 140;
	private static final int COMMON_BTN_HEIGHT = 45;
	
	private  int width = 0;
	private int height = 0;

	
	private Context context;
	private List<Integer> ids;// 传递你点击的事件
	private List<Button> btnList;
	private LinearLayout addView;
	private int defalutShow = 0;
	
	public CommSortView(Context ctx,List<Integer> ids,LinearLayout addView,int defaultPosition
			,int width,int height) {
		
		super();
		this.width = width;
		this.height = height;
		this.context = ctx;
		this.ids = ids;
		this.addView = addView;
		this.defalutShow = defaultPosition;
		
		btnList = new ArrayList<Button>();
		
		init();
	}
	
	public CommSortView(Context ctx,List<Integer> ids,LinearLayout addView,int defaultPosition) {
		
		super();
		this.context = ctx;
		this.ids = ids;
		this.addView = addView;
		this.defalutShow = defaultPosition;
		
		btnList = new ArrayList<Button>();
		
		init();
	}
	
	private void init(){
		
		if (ids == null || ids.size() <= 0 || addView == null) {
			return;
		}
		int size = ids.size();
		Button btn = null;
		LinearLayout.LayoutParams params = null;
		
		// 按钮的布局外部存储器
//		LinearLayout.LayoutParams ltParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 47);
		// 中间线
		LinearLayout.LayoutParams line = new LinearLayout.LayoutParams(1, 45);
//		addView.setOrientation(LinearLayout.HORIZONTAL);
//		addView.setLayoutParams(ltParams);
		addView.setGravity(Gravity.CENTER);
		addView.setBackgroundResource(R.drawable.shape_common_radiusbtn);
		View view = null;
		for(int i = 0 ; i < size ; i ++ ){
			if (i == 0) {
				btn = new RoundCornerBtn(context,RoundCornerBtn.LEFT_CORNER);
			}else if (i == size -1) {
				btn = new RoundCornerBtn(context,RoundCornerBtn.RIGHT_CORNER);
			}else{
				btn = new RoundCornerBtn(context,RoundCornerBtn.NO_CORNER);
			}
			btn.setText(ids.get(i));
			btn.setTextColor(context.getResources().getColorStateList(R.drawable.select_common_text_brownd));
			if (width > 0 && height > 0) {
				params = new LinearLayout.LayoutParams(width, height);
				line = new LinearLayout.LayoutParams(1, height);
			}else{
				params = new LinearLayout.LayoutParams(COMMON_BTN_WIDTH, COMMON_BTN_HEIGHT);
			}
			btn.setLayoutParams(params);
			btn.setGravity(Gravity.CENTER);
			btn.setPadding(4, 4, 4, 4);
			btn.setTextSize(18);
			// Integer 转  int
			btn.setId(ids.get(i).intValue());
			btn.setOnClickListener(this);
			if (defalutShow == ids.get(i).intValue()) {
				//没有setStyle
				btn.setBackgroundColor(context.getResources().getColor(R.color.text_brown_d7c093));
				btn.setTextColor(context.getResources().getColor(R.color.white));
			}else{
				btn.setBackgroundColor(context.getResources().getColor(R.color.white));
				btn.setTextColor(context.getResources().getColor(R.color.text_brown_d7c093));
			}
			btn.setTextSize(16);
			btnList.add(btn);
			addView.addView(btn);
			if (i != (size -1)) {
				 view = new View(context);
				 view.setBackgroundColor(context.getResources().getColor(R.color.text_brown_d7c093));
				 view.setLayoutParams(line);
				 addView.addView(view);
			}
		}
	}

	@Override
	public void onClick(View v) {
		
		refreshBg(v.getId());
		
	}
	
	
	/** 
	 * @author: lihs
	 * @Title: selePointPosition  指定传入的ID
	 * @Description: 对指定的数据刷新
	 * @param id 
	 * @date: 2014-6-9 下午2:00:43
	 */
	public void selePointPosition(int id){
		refreshBg(id);
	}
	
	private void refreshBg(int id){
		for(Button btn : btnList){ 
			if (btn.getId() == id) {
				btn.setBackgroundColor(context.getResources().getColor(R.color.text_brown_d7c093));
				btn.setTextColor(context.getResources().getColor(R.color.white));
				if (listener != null) {
					 listener.doDataSort(id);
				}
			}else{
				// 其它的恢复原来的状态
				btn.setBackgroundColor(context.getResources().getColor(R.color.white));
				btn.setTextColor(context.getResources().getColor(R.color.text_brown_d7c093));
			}
		}
	}
	private RefreshSortListener listener = null;
	public void setRefreshSortListener(RefreshSortListener listener){
		this.listener = listener;
	}
	public interface RefreshSortListener{
		public void doDataSort(int id);
	}
}
