package com.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.incito.finshine.R;

 
/**
 * <dl>
 * <dt>CommonDialog.java</dt>
 * <dd>Description:通用对话框</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-5-28 下午5:23:34</dd>
 * </dl>
 * 
 * @author lihs
 */
public class CommonDialog {

    private View contentView;
    private View selfDifineView;
    private Dialog dialog;

    private Context context;
    private SparseArray<Button> btns = null;

    public CommonDialog(Context context) {
        this.context = context;

        btns = new SparseArray<Button>();
        dialog = new Dialog(context, R.style.CustomProgressDialog);
        contentView = LayoutInflater.from(context).inflate(R.layout.common_dialog_layout, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        dialog.addContentView(contentView, params);
        dialog.setCanceledOnTouchOutside(false);
        dialog.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_BACK));
        dialog.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,KeyEvent.KEYCODE_BACK));
        dialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) { 
            	
            }
        });
    }

    public Dialog getDialog() {
        return dialog;
    }

    public View getContentView() {
        return contentView;
    }

    public void setCancelable(boolean cancelable) {
    	if(dialog!=null){
        dialog.setCancelable(cancelable);
    }
    }

    public boolean isShowing() {
    	if(dialog!=null){
        return dialog.isShowing();
    	}else{
    		return false;
    	}
    }

    public void dismiss() {
    	if(dialog!=null){
        dialog.dismiss();
    }
    }
    
    // 添加自定义的 布局
    public void addView(int viewId) {
        if (contentView != null) {
            LinearLayout addLt = (LinearLayout) contentView
                    .findViewById(R.id.lt_difine);
            addLt.removeAllViews();
            selfDifineView = LayoutInflater.from(context).inflate(viewId, null);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            addLt.addView(selfDifineView, params);
        }
    }

    public View getSelfDifineView() {
        return selfDifineView;
    }

    public void showDialog() {
    	 
        dialog.show();
    }

    public interface BtnClickedListener {
        public void onBtnClicked();
    }
    
    public void setTitle(int id) {
        if (id < 0) {
            contentView.findViewById(R.id.tv_title).setVisibility(View.GONE);
        } else {
            TextView titleTv = (TextView) contentView
                    .findViewById(R.id.tv_title);
            titleTv.setText(id);
        }
    }

    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            contentView.findViewById(R.id.tv_title).setVisibility(View.GONE);
        } else {
            TextView titleTv = (TextView) contentView
                    .findViewById(R.id.tv_title);
            titleTv.setText(title);
        }
    }

    public void setMessage(int id) {
        if (id < 0) {
            contentView.findViewById(R.id.lt_difine).setVisibility(View.GONE);
        } else {
            contentView.findViewById(R.id.lt_difine)
                    .setVisibility(View.VISIBLE);
            ((TextView) contentView.findViewById(R.id.tv_message)).setText(id);
        }
    }
    public void setMessage(String id) {
        if (TextUtils.isEmpty(id)) {
            contentView.findViewById(R.id.lt_difine).setVisibility(View.GONE);
        } else {
            contentView.findViewById(R.id.lt_difine)
                    .setVisibility(View.VISIBLE);
            ((TextView) contentView.findViewById(R.id.tv_message)).setText(id);
        }
    }
    public void setCancleButton(final BtnClickedListener cancleOk, int text) {
        Button canBtn = (Button) contentView.findViewById(R.id.cancel_btn);
        canBtn.setVisibility(View.VISIBLE);
        canBtn.setText(text);
        btns.put(0, canBtn);
        canBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if (dialog != null) {
                dialog.dismiss();
            		dialog = null;
            	}
                if (cancleOk != null) {
                    cancleOk.onBtnClicked();
                }
            }
        });
    }
    
    public void setPositiveButton(final BtnClickedListener btnOk, int text) {

        Button sureBtn = (Button) contentView.findViewById(R.id.confirm_btn);
        sureBtn.setVisibility(View.VISIBLE);
        sureBtn.setText(text);
        btns.put(1, sureBtn);
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if (dialog != null) {
                dialog.dismiss();
            		dialog = null;
            	}
                if (btnOk != null) {
                    btnOk.onBtnClicked();
                }
            }
        });
    }

    
}
