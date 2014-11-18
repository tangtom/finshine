package com.incito.finshine.test;

import com.incito.finshine.R;
import com.incito.wisdomsdk.net.http.AsyncHttpClient;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActNetworkTest extends Activity {
	private String urlGetCustomer = "http://10.66.52.59:8080/finshine/rest/sales/{salesId}/customers/{customerId}";
	private String urlCustomerList = "http://10.66.52.59:8080/finshine/rest/sales/1/customers";
	private String urlCustomerNote = "http://10.66.52.59:8080/finshine/rest/sales/{salesId}/customers/{customerId}/notes";
	private String urlAssetinfo= "http://10.66.52.59:8080/finshine/rest/sales/{salesId}/customers/{customerId}/assetinfo";
	private String urlFamilyInfo = "http://10.66.52.59:8080/finshine/rest/sales/{salesId}/customers/{customerId}/familyinfo";
	private String urlCreateTodos = "http://10.66.52.59:8080/finshine/rest/sales/{salesId}/todos";
	private String urlTodos = "http://10.66.52.59:8080/finshine/rest/sales/{salesId}/todos/{todoId}";
	private String urlQueryTodos = "http://10.66.52.59:8080/finshine/rest/sales/{salesId}/todos?start={start}&end={end}";
	
	
	private TextView msg;
	private AsyncHttpClient client = new AsyncHttpClient();
	private JsonHttpResponseHandler handler = new JsonHttpResponseHandler(){
		@Override
		public void onSuccess(String content) {
			if(msg!=null)
			{
				msg.setText(content);
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_network);
		init();
	}
	private boolean init()
	{
		msg = (TextView)findViewById(R.id.textMessage);
		Button btnCreateCustomer = (Button)findViewById(R.id.buttonCreateCustomer);
		btnCreateCustomer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//client.post(urlCustomerList, handler);
				client.get(urlCustomerList, handler);
			}
		});
		return true;
	}
}
