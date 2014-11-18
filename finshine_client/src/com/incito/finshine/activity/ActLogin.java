package com.incito.finshine.activity;

import com.android.core.util.SharedUtil;
import com.custom.view.CommonWaitDialog;
import com.incito.finshine.R;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.NetworkManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.SharedKey;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActLogin extends Activity implements OnClickListener{

	private Button btnLogin;
	private EditText editUsername,editPassword;
	private final String TAG = ActLogin.class.getSimpleName();
//	private TextView versionCode;
	private SPManager spManager;
	private CommonWaitDialog dialog = null;
	private TextView tvVersionNumber ;
	
	private JsonHttpResponseHandler handlerLogin = new JsonHttpResponseHandler(){

		@Override
		public void onSuccess(JSONObject response) {
			try {
				closeDialog();
				Log.i(TAG,response.toString());
				String name = response.getString("name");
				long id = response.getLong("id");
				String username = editUsername.getText().toString();
				String password = editPassword.getText().toString();
				spManager.setStringValue(SPManager.NAME, name);
				spManager.setStringValue(SPManager.USERNAME,editUsername.getText().toString());
				spManager.setStringValue(SPManager.LOGINFLAG,"1");
				spManager.setStringValue(SPManager.PWD, android.util.Base64.encodeToString(editPassword.getText().toString().getBytes(), Base64.DEFAULT));
				spManager.setLongValue(SPManager.ID, id);
				spManager.editorCommit();
				Request.salesId = spManager.getLongValue(SPManager.ID);
//				Log.i(TAG, "--------" + spManager.getStringValue(SPManager.PWD) + "---" + spManager.getLongValue(SPManager.ID));
				SharedUtil.setPreferStr(SharedKey.USERNAME, username);
				SharedUtil.setPreferStr(SharedKey.PASSWORD, password);
				startActivity(new Intent(ActLogin.this,ActFinshineHomePage.class));
				
			} catch (JSONException e) {
				e.printStackTrace();
				closeDialog();
			}
			super.onSuccess(response);
		}

		@Override
		public void onFailure(Throwable error, String content) {
			if(content.isEmpty())
			{
				Toast.makeText(ActLogin.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(ActLogin.this, "网络连接错误", Toast.LENGTH_SHORT).show();
			}
			closeDialog();
		}
		
	};
	
	@Override
	public void onStart() {
		initData();
		initView();
		Log.i(TAG, "onStart");
		super.onStart();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_login);
		Log.i(TAG, "onCreate");
	}
	public void initView(){
		spManager.setStringValue(SPManager.LOGINFLAG,"0");
		spManager.editorCommit();
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);
		editUsername = (EditText)findViewById(R.id.edtName);
		editPassword = (EditText)findViewById(R.id.edtPassword);
		tvVersionNumber = (TextView)findViewById(R.id.tv_version_number) ;
		PackageManager packageManager = getPackageManager();
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tvVersionNumber.setText(packInfo.versionName); 
	}
	
	/**
	 * 初始化数据,设置默认的账户名和密码
	 */
	private void initData() {
		spManager = SPManager.getInstance();
	}
	
	/**
	 * 登录
	 */
	private void login() {
		if (TextUtils.isEmpty(editUsername.getText().toString())) {
			editUsername.setError("请输入用户名");
			editUsername.requestFocus();
		} else if (TextUtils.isEmpty(editPassword.getText().toString())) {
			editPassword.setError("请输入密码");
			editPassword.requestFocus();
		} else {
			
			offlineOrOnlineLogin();
		}
	}

	public void offlineOrOnlineLogin(){
		String username = editUsername.getText().toString();
		String password = editPassword.getText().toString();
		if(dialog == null){
			dialog = new CommonWaitDialog(this, "", R.string.load_data);
		}
		Request request = new Request(RequestDefine.RQ_LOGIN, RequestType.GET, null, handlerLogin);
		NetworkManager manager = CoreManager.getInstance().getNetworkManager();
		manager.setLoginVariable(username, password);
		CoreManager.getInstance().postRequest(request);
		/*if(spManager.getStringValue(SPManager.NAME).isEmpty())
		{
			if(dialog == null){
				dialog = new CommonWaitDialog(this, "", R.string.load_data);
			}
			Request request = new Request(RequestDefine.RQ_LOGIN, RequestType.GET, null, handlerLogin);
			NetworkManager manager = CoreManager.getInstance().getNetworkManager();
			Log.i(TAG, username + "--++" + password);
			manager.setLoginVariable(username, password);
			CoreManager.getInstance().postRequest(request);
		}
		else
		{
//			Log.i(TAG, username + "---->" + spManager.getStringValue(SPManager.USERNAME));
//			Log.i(TAG, android.util.Base64.encodeToString(password.getBytes(), Base64.DEFAULT) + "--->" + spManager.getStringValue(SPManager.PWD));
//			Log.i(TAG, spManager.getStringValue(SPManager.USERNAME).equals(username) + "--->" + spManager.getStringValue(SPManager.PWD).trim().equals(android.util.Base64.encodeToString(password.getBytes(), Base64.DEFAULT).trim()));
			if(spManager.getStringValue(SPManager.USERNAME).equals(username) && spManager.getStringValue(SPManager.PWD).trim().equals(android.util.Base64.encodeToString(password.getBytes(), Base64.DEFAULT).trim()))
			{
				Request.salesId = spManager.getLongValue(SPManager.ID);
				startActivity(new Intent(ActLogin.this,ActFinshinePage.class));
			}
			else
			{
				Toast.makeText(ActLogin.this, "登录失败", Toast.LENGTH_SHORT).show();
			}
		}*/
	}
	
	
	
	@Override
	protected void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		Log.i(TAG, "onPause");
		super.onPause();
	}
	@Override
	protected void onRestart() {
		Log.i(TAG, "onRestart");
		super.onRestart();
	}
	@Override
	protected void onResume() {
		Log.i(TAG, "onResume");
		super.onResume();
	}
	@Override
	protected void onStop() {
		Log.i(TAG, "onStop");
		super.onStop();
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnLogin:
			Log.i(TAG,"login");
			login();
			break;
		default:break;
		}
	}
	
	/**
	 * 打开wifi设置界面
	 */
/*	private void openWIFIConfig() {
		// TODO Auto-generated method stub
		ComponentName cn = new ComponentName("cn.ac.iscas.gz.mdm",
				"cn.ac.iscas.gz.mdm.ui.activity.NetActivity");
		// ComponentName cn = new ComponentName("com.android.settings",
		// "com.android.settings.wifi.WifiPickerActivity");
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setComponent(cn);
		try {
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	private  void closeDialog(){
		if (dialog != null) {
			dialog.clearAnimation();
			dialog = null;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
}
