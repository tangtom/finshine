package com.incito.finshine.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterArticleList;
import com.incito.finshine.common.IntentDefine;
import com.incito.finshine.entity.Article;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.RequestDefine;
import com.incito.finshine.network.Request.RequestType;
import com.incito.wisdomsdk.net.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ActArticleList extends Activity {
	private final String TAG = ActArticleList.class.getSimpleName();
	private AdapterArticleList adpArticle;
	private ListView list;
	private AsyncHttpResponseHandler handlerGetArticle = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			Gson gson = new Gson();
			ArrayList<Article> l = gson.fromJson(content,
					new TypeToken<List<Article>>() {
					}.getType());
			adpArticle = new AdapterArticleList(ActArticleList.this);
			adpArticle.setItemList(l);

			list.setAdapter(adpArticle);
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_article_list);
		init();
		Request request = new Request(RequestDefine.RQ_ARTICLE_GET,
				RequestType.GET, null, handlerGetArticle);
		CoreManager.getInstance().postRequest(request);
	}

	private boolean init() {
		TextView title = (TextView) findViewById(R.id.textTitle);
		
		ImageView back = (ImageView) findViewById(R.id.imageBack);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		title.setText(R.string.title_article_list);
		
		list = (ListView) findViewById(R.id.listArticle);
		list.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int pos,
					long id) {
				Log.i(TAG, "onItemClick = " + id);
				Intent i = new Intent();
				i.setClass(ActArticleList.this, ActArticleDetail.class);
				i.putExtra(IntentDefine.ARTICLE_ID, id);
				startActivity(i);
			}
		});
		return true;
	}
}
