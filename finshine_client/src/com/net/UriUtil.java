/*
 * Copyright (c) 2012, Andy.fang Corporation, All Rights Reserved
 */
package com.net;

import com.android.core.util.AppLog;

/**
 * @description 网络请求url配置
 * @author Andy.fang
 * @createDate 2014年8月19日
 * @version 1.0
 */
public final class UriUtil {
	public static final String API_PATH1 = "http://192.168.103.20:8080";// 开发服务器
	public static final String API_PATH2 = "http://192.168.103.18";// 测试服务器
//	public static final String API_PATH1 = "http://srbanker.incito.com.cn:30001";// 试验网，生产环境
//	public static final String API_PATH2 = "http://58.211.121.116:8000";// DEMO环境
//	public static final String API_PATH1 = "http://222.208.168.90:8088";// 开发服务器
//	public static final String API_PATH2 = "http://222.208.168.90:8080";// 测试服务器

	/**
	 * @description
	 * @author Andy.fang
	 * @createDate 2014年8月19日
	 * @return
	 */
	public static String getRootUrl() {
		String path = API_PATH1;
		if (AppLog.DEBUG) {
			 path = API_PATH2;
		}
		return path;
	}
	
}
