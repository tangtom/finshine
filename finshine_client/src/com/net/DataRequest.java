/*
 * Copyright (c) 2012, Andy.fang Corporation, All Rights Reserved
 */
package com.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import java.util.Map;

/**
 * @description
 * @author Andy.fang
 * @createDate 2014年8月20日
 * @version 1.0
 */
public class DataRequest extends Request<Data> {

	public DataRequest(int method, String url, Listener<Data> listener) {
		super(method, url, listener);
	}

	public DataRequest(String url, Listener<Data> listener) {
		this(0, url, listener);
	}

	public DataRequest(String url, Listener<Data> listener, Map<String, String> map) {
		super(url, listener, map);
	}

	@Override
	public void deliverResponse(Data response) {
		if (mListener != null) {
			mListener.onResponse(response);
		}
	}

	@Override
	protected Response<Data> parseNetworkResponse(NetworkResponse response) {
		try {
			String str = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			return Response.success(new Data(str), HttpHeaderParser.parseCacheHeaders(response));
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error(new ParseError("网络连接错误"));
		}
	}

}
