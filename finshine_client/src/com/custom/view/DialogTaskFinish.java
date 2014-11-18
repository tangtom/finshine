package com.custom.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.core.util.SharedUtil;
import com.incito.finshine.R;
import com.incito.finshine.entity.UserTaskWSEntity;

public class DialogTaskFinish extends DialogTaskGoingon {
	private Context context;
	UserTaskWSEntity userTaskWSEntity;
	public DialogTaskFinish(Context context, UserTaskWSEntity userTaskWSEntity) {
		super(context, R.style.DialogTaskAccept, userTaskWSEntity, 0);
          this.context=context;
          this.userTaskWSEntity=userTaskWSEntity;
	}

	public DialogTaskFinish(Context context) {
		super(context);
		this.context = context;
	}

	public DialogTaskFinish(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dlg_task_finish);
		loadViewData();
		acceptButton.setVisibility(View.VISIBLE);
		acceptButton.setText("确定");
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_accept:
			SharedUtil.setPreferStr("task_"+userTaskWSEntity.getTask().getId(), "1");
			dismiss();
			break;

		default:
			break;
		}
	}
}
