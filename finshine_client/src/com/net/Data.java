/*
 * Copyright (c) 2012, Andy.fang Corporation, All Rights Reserved
 */
package com.net;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @description
 * @author Andy.fang
 * @createDate 2014年8月19日
 * @version 1.0
 */
public class Data implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<HashMap<String, String>> result;
	private String msg;
	private String status;
	private String jsonStr;

	public Data(String jsonStr) {
		this.jsonStr = jsonStr;
		parseString();
	}

	public List<HashMap<String, String>> getResult() {
		return result;
	}

	public void setResult(List<HashMap<String, String>> result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @description
	 * @author Andy.fang
	 * @createDate 2014年8月19日
	 */
	public void parseString() {
		try {
			JSONObject jsonObj = new JSONObject(jsonStr);
			result = new ArrayList<HashMap<String, String>>();
			msg = String.valueOf(jsonObj.getString("msg"));
			status = String.valueOf(jsonObj.getString("status"));
			if (jsonObj.has("result")) {
				JSONArray jsonArray = jsonObj.getJSONArray("result");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject json = jsonArray.getJSONObject(i);
					HashMap<String, String> map = new HashMap<String, String>();
					Iterator<?> it = json.keys();
					while (it.hasNext()) {
						String key = (String) it.next();
						String val = String.valueOf(json.get(key));
						map.put(key, val);
					}
					result.add(map);
				}
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	
	

}
