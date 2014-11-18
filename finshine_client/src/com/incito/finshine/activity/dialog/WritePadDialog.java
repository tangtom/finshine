package com.incito.finshine.activity.dialog;

import com.custom.view.PaintView;
import com.incito.finshine.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class WritePadDialog extends Dialog {

	Context context;
	LayoutParams p;// 布局属性
	WritePadDialogListener dialogListener;// 自定义的一个接口 只有refreshActivity(Object

	static final int BACKGROUND_COLOR = Color.WHITE;

	static final int BRUSH_COLOR = Color.BLACK;

	PaintView mView;// 自定义view 对象

	public WritePadDialog(Context context, WritePadDialogListener dialogListener) {
		super(context, R.style.customer_dialog);
		this.setCanceledOnTouchOutside(false);
		this.context = context;
		this.dialogListener = dialogListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.write_pad);

		p = getWindow().getAttributes(); // 获取对话框当前的参数值
		p.height = 520;// (int) (d.getHeight() * 0.4); //高度设置为屏幕的0.4
		p.width = 780;// (int) (d.getWidth() * 0.6); //宽度设置为屏幕的0.6

		LayoutParams pLayoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		pLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
		mView = new PaintView(context, p);
		mView.setLayoutParams(pLayoutParams);

		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.tablet_view);
		frameLayout.addView(mView);
		mView.requestFocus();// 为当前view 设置焦点
		Button btnClear = (Button) findViewById(R.id.tablet_clear);
		btnClear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mView.isPaint = false;
				mView.clear();
			}
		});

		Button btnOk = (Button) findViewById(R.id.tablet_ok);
		btnOk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if (mView.isPaint) {
						dialogListener.onSigned(mView.getCachebBitmap());
						WritePadDialog.this.dismiss();
					} else {
						Toast.makeText(context, "对不起，请签名", Toast.LENGTH_SHORT)
								.show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Button btnCancel = (Button) findViewById(R.id.tablet_cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mView.isPaint = false;
				cancel();
			}
		});
	}

	public interface WritePadDialogListener {
		public void onSigned(Bitmap b);
	}
}
