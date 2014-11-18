package com.incito.finshine.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import com.google.gson.Gson;
import com.incito.finshine.R;
import com.incito.finshine.common.IntentDefine;
import com.incito.finshine.entity.Article;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.RequestDefine;
import com.incito.finshine.network.Request.RequestType;
import com.incito.wisdomsdk.net.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ActArticleDetail extends Activity {
	
	private final String TAG = ActArticleDetail.class.getSimpleName();

	private AsyncHttpResponseHandler handlerGetArticle = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			Gson gson = new Gson();
			Article a = gson.fromJson(content, Article.class);
			setData(a);
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
		}
	};
	ImageGetter imgGetter = new Html.ImageGetter() {
		@Override
		public Drawable getDrawable(String source) {
			Log.i("tag", "drawable = " + source);
			Drawable drawable = null;
			URL url;
			try {
				url = new URL(source);
				drawable = Drawable.createFromStream(url.openStream(), "");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (drawable == null) {
				return null;
			}
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			return drawable;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_article_detail);
		init();
		Bundle b = getIntent().getExtras();
		long articleId = b.getLong(IntentDefine.ARTICLE_ID, -1);
		Request request = new Request(RequestDefine.RQ_ARTICLE_DETAIL_GET,articleId,
				RequestType.GET, null, handlerGetArticle);
		CoreManager.getInstance().postRequest(request);
	}

	private boolean init() {

		ImageView back = (ImageView) findViewById(R.id.imgBack);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		return true;
	}
	private boolean setData(Article article) {
		TextView title = (TextView)findViewById(R.id.txtTitle);
		title.setText(article.getTitle());
		
		TextView content = (TextView)findViewById(R.id.txtContent);
		content.setText(Html.fromHtml(article.getContent(), imgGetter, null));
		
		TextView author = (TextView)findViewById(R.id.txtAuthor);
		author.setText(article.getAuthor());
		
		TextView publish = (TextView)findViewById(R.id.txtPublish);
		String date = (new Date(article.getPostTime())).toString();
		publish.setText(date);
		
		TextView resource = (TextView)findViewById(R.id.txtResource);
		resource.setText(article.getResource());
		return true;
	}
}
