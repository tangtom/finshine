package com.incito.finshine.manager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.entity.MarKetCustomer;
import com.incito.finshine.entity.MarketCsOrder;
import com.incito.finshine.entity.MarketStateReslut;
import com.incito.finshine.entity.Product;
import com.incito.finshine.network.Request;

public class JsonParse {

	
	/** 
	 * @author: lihs
	 * @Title: getMarketList  
	 * @Description: 获取客户列表
	 * @param reslut 
	 * @return 
	 * @date: 2014-6-17 下午1:01:02
	 */
	
	public static List<MarKetCustomer> getMarketList(JSONObject reslut){
		Log.i("", reslut + "-------------------------");
		List<MarKetCustomer>  marketList = null;
		List<MarketCsOrder> maCsorder = null;
		MarKetCustomer msItem = null;
		if (Request.RESLUT_OK.equals(reslut.optString("status"))) {
			marketList = new ArrayList<MarKetCustomer>();
			try {
				JSONArray marketArray = reslut.getJSONArray("list");
				if (marketArray != null && marketArray.length() > 0) {
					JSONObject jsonItem = null;
					 for (int i = 0; i < marketArray.length(); i++) {
						 msItem = new MarKetCustomer();
						 jsonItem = marketArray.getJSONObject(i);
						 msItem.setId(jsonItem.optLong("id"));
						 msItem.setName(jsonItem.optString("name"));
						 msItem.setSalesId(jsonItem.optLong("salesId"));
						 msItem.setEmail1(jsonItem.optString("email1"));
						 msItem.setCellPhone1(jsonItem.optString("cellPhone1"));
						 msItem.setBindingStatusId(jsonItem.optLong("bindingStatusId"));
						 msItem.setMarketingQuantity(jsonItem.optLong("marketingQuantity"));
						 msItem.setPurchasedQuantity(jsonItem.optLong("purchasedQuantity"));
						 msItem.setPurchasedTotalAmount(jsonItem.optLong("purchasedTotalAmount"));
						 msItem.setProdDividendQty(jsonItem.optLong("prodDividendQty"));
						 
						 // 解析数据客户营销数据订单列表数据
						 try {
							 JSONArray marketArrays = jsonItem.getJSONArray("results");
							 if (marketArrays != null && marketArrays.length() > 0) {
								 maCsorder = new ArrayList<MarketCsOrder>();
								 Gson gson = new Gson();
								 maCsorder = gson.fromJson(marketArrays.toString(),new TypeToken<List<MarketCsOrder>>() {}.getType());
								 msItem.setMaCsorder(maCsorder);
							}
						} catch (JSONException e) {
							 e.printStackTrace();
						}
						 marketList.add(msItem);
					}
				}
				
			} catch (Exception e) {
				e.getLocalizedMessage();
			}
		}
		return marketList;
	}
	
	public static MarketStateReslut marketReslut = null;
	
	public static MarketStateReslut getMarketReslut() {
		return marketReslut;
	}

	public static void setMarketReslut(MarketStateReslut marketReslut) {
		JsonParse.marketReslut = marketReslut;
	}

	public static boolean setMarketStepReslut(JSONObject reslut){
		if (reslut == null) {
			return false;
		}
		String states = reslut.optString("status");
		if (Request.RESLUT_OK.equals(states)) {
			try {
				reslut = reslut.getJSONObject("item");
				Gson gson = new Gson();
				marketReslut = gson.fromJson(reslut.toString(), new TypeToken<MarketStateReslut>() {}.getType());
			    return true;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static MarketStateReslut getMarketStepResluts(JSONObject reslut){
		if (reslut == null) {
			return null;
		}
		
		//注意手机验证码的msg提示获取
		MarketStateReslut item =null;
		String states = reslut.optString("status");
		if (Request.RESLUT_OK.equals(states)) {
			try {
				//06-19 20:25:15.693: D/HttpUtils(15171): success o= {"msg":"操作成功","status":"0","item":5} 问题
				reslut = reslut.getJSONObject("item");
				Gson gson = new Gson();
				item = gson.fromJson(reslut.toString(), MarketStateReslut.class);//new TypeToken<MarketStateReslut>() {}.getType()
			     
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return item;
		}
		else return null;
//		else if (states.equals("2"))
//		{
			//验证码失败   怎么获取值？
//			return null;
//		}
	}
}
