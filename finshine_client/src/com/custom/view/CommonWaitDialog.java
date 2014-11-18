package com.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.incito.finshine.R;

/**
 * <dl>
 * <dt>CommonWaitDialog.java</dt>
 * <dd>Description:异步加载等待对话框</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 安徽青牛信息技术有限公司</dd>
 * <dd>CreateDate: 2014-1-17 下午3:09:23</dd>
 * </dl>
 * 
 * @author lihs
 */

public class CommonWaitDialog {

	private View aniView;

	private ImageView btn;

	private RotateAnimation mRotateAnimation;

	private Dialog dialog = null;
	
	private TextView tvShowContent;

	public CommonWaitDialog(Context context ,String alertMsg,int alertId) {
		
		super();
        
		init(context,alertMsg,alertId);
	}
	
	private void init(Context context,String alertMsg,int alertId){
		
		aniView = LayoutInflater.from(context).inflate(R.layout.wait_dialog_layout, null);
		btn = (ImageView) aniView.findViewById(R.id.btn_animation);
		tvShowContent = (TextView) aniView.findViewById(R.id.tv_animation);
		if (!TextUtils.isEmpty(alertMsg)) {
			tvShowContent.setText(alertMsg);
		}else{
			tvShowContent.setText(alertId);
		}
		mRotateAnimation = new RotateAnimation(0.0F, 360.0F, 1, 0.5F, 1, 0.5F);
		mRotateAnimation.setFillAfter(false);
		mRotateAnimation.setRepeatCount(Animation.INFINITE);
		mRotateAnimation.setDuration(800);
		mRotateAnimation.setInterpolator(new LinearInterpolator());

		dialog = new Dialog(context, R.style.transparentFrameWindowStyle);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		dialog.addContentView(aniView, params);
		dialog.setCancelable(false);
		if (dialog != null) {
			dialog.show();
			
			btn.post(new Runnable() {

				@Override
				public void run() {

					btn.startAnimation(mRotateAnimation);

				}
			});
		}
	}
	
	public boolean isShowing() {
	    if (dialog != null) {
	        return dialog.isShowing();
	    }
	    return false;
	}

	public void clearAnimation() {

		if (dialog != null && dialog.isShowing()) {
			btn.clearAnimation();
			dialog.dismiss();
			dialog = null;
		}
	}
}
