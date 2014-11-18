/*
 * Copyright (c) 2012, Andy.fang Corporation, All Rights Reserved
 */
package com.net;

import com.android.core.util.AppToast;
import com.android.core.util.BitmapUtil;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import android.graphics.Bitmap;

import java.util.Map;

/**
 * @description
 * @author Andy.fang
 * @createDate 2014年8月14日
 * @version 1.0
 */
public class ImageRequestInco extends Request<Bitmap> {

	public ImageRequestInco(String url, Listener<Bitmap> listener) {
		this(Method.GET, url, listener);
	}

	public ImageRequestInco(int method, String url, Listener<Bitmap> listener) {
		super(method, url, listener);
	}

	public ImageRequestInco(String url, Listener<Bitmap> listener, Map<String, String> map) {
		super(url, listener, map);
	}

	@Override
	public void deliverResponse(Bitmap response) {
		AppToast.ShowToast(response.hashCode());
		mListener.onResponse(response);
	}

	@Override
	protected Response<Bitmap> parseNetworkResponse(NetworkResponse response) {
		byte[] data = response.data;
		AppToast.ShowToast("data=="+data.length);
		try {
			Bitmap bitmap = BitmapUtil.Bytes2Bimap(data);
			return Response.success(bitmap, HttpHeaderParser.parseCacheHeaders(response));
		} catch (Exception e) {
			AppToast.ShowToast(e.getMessage());
			return Response.error(new ParseError(e));
		}
		
		
	}

}
