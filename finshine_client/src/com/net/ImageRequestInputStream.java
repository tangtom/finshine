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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * @description 
 * @author   Andy.fang
 * @createDate 2014年8月15日
 * @version  1.0
 */
public class ImageRequestInputStream extends Request<InputStream> {

	public ImageRequestInputStream(int method, String url, Listener<InputStream> listener) {
		super(method, url, listener);
	}

	public ImageRequestInputStream(String url, Listener<InputStream> listener) {
		this(Method.GET,url, listener);
	}

	public ImageRequestInputStream(String url, Listener<InputStream> listener, Map<String, String> map) {
		super(url, listener, map);
	}

	@Override
	public void deliverResponse(InputStream response) {
		mListener.onResponse(response);
	}

	@Override
	protected Response<InputStream> parseNetworkResponse(NetworkResponse response) {
		byte[] byt = response.data;
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(byt);
		} catch (Exception e) {
			return Response.error(new ParseError(e));
		}
		return Response.success(in, HttpHeaderParser.parseCacheHeaders(response));
	}

}
