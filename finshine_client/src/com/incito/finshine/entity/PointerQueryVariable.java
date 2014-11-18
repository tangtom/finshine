package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

public class PointerQueryVariable implements Serializable{
	
	private long user_fk;//理财师ID
	private long status_fk;//订单状态 -1: 全部类型  1: 核实中 	2: 已到账
	private Date fromDate;//开始时间
	private Date toDate;//结束时间
	private String sortOrder;//排序方式  DEFAULT: 默认排序 	DEAL_DATE: 成交时间	PROD_NAME: 产品名称
	public long getUser_fk() {
		return user_fk;
	}
	public void setUser_fk(long user_fk) {
		this.user_fk = user_fk;
	}
	public long getStatus_fk() {
		return status_fk;
	}
	public void setStatus_fk(long status_fk) {
		this.status_fk = status_fk;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getSortOrder() {
		if (StrUtil.isBlank(sortOrder)) {
			return "";
		}
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	
}
