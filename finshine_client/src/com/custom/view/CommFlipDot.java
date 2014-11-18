package com.custom.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.incito.finshine.R;

public class CommFlipDot {

	
	private Context context;
	private LinearLayout addView;
	private List<ImageView> listIv;
	
	public CommFlipDot(Context ctx,int dotCount,LinearLayout addView) {
		
		super();
		this.context = ctx;
		this.addView = addView;
		
		listIv = new ArrayList<ImageView>();
		
		init(dotCount);
	}
	
	public void setSeletion(int currentPage){
		
		for (int i = 0; i < listIv.size(); i++) {
			if (currentPage == i) {
				listIv.get(i).setImageResource(R.drawable.bg_sel_flip);
			}else{
				listIv.get(i).setImageResource(R.drawable.bg_not_sel_flip);
			}
		}
	}
	
	private void init(int dotCount){
		
		if (dotCount <= 0 || addView == null) {
			return;
		}
		int size = dotCount;
		LinearLayout.LayoutParams params = null;
		addView.setBackgroundResource(R.color.white);
		ImageView ivDot;
		for(int i = 0 ; i < size ; i ++ ){
			ivDot = new ImageView(context);
			ivDot.setImageResource(R.drawable.bg_not_sel_flip);
			params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 0, 50, 0);
			ivDot.setLayoutParams(params);
			listIv.add(ivDot);
			addView.addView(ivDot);
		}
	}
}
