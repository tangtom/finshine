package com.incito.finshine.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ActWeb189 extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 WebView webview = new WebView(this);
		 setContentView(webview);
		 WebSettings webSettings = webview.getSettings();       
	     webSettings.setJavaScriptEnabled(true);
		 webview.loadUrl("http://js.189.cn/");
		 

	}
}
