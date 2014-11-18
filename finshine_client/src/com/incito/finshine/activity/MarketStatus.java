package com.incito.finshine.activity;

import com.incito.finshine.entity.MarketCsOrder;

public interface MarketStatus {

	// 获取当前
	public int getMarketStatus(MarketCsOrder marketCs);
	
	public MarketCsOrder getMarketCs();

	public boolean canEdit(long marketStatus);
	
}
