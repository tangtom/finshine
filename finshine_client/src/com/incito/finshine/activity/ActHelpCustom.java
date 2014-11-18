package com.incito.finshine.activity;

import com.android.core.util.SharedUtil;
import com.incito.finshine.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * <dl>
 * <dt>ActFinshinePage.java</dt>
 * <dd>Description:山榕项目使用帮助</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-8-19 下午1:58:18</dd>
 * </dl>
 * 
 * @author zw
 */
public class ActHelpCustom extends Activity implements OnClickListener{
	
	public Button buttonHelpCustom ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_help_custom);
		init() ;
	}

	private void init() {
		buttonHelpCustom = (Button) this.findViewById(R.id.buttonKnow) ;
		buttonHelpCustom.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonKnow:
			SharedUtil.setPreferStr("saveHelpCustom", "1");
			finish();
			break;

		default:
			break;
		}
		
	}
	
}
