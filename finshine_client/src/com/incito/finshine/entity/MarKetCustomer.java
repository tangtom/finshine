package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.List;

import com.android.core.util.StrUtil;

/**
 * <dl>
 * <dt>MarKetCustomer.java</dt>
 * <dd>Description:客户营销列表</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-17 下午12:52:46</dd>
 * </dl>
 * 
 * @author lihs
 */
public class MarKetCustomer implements Serializable{

	 
	private static final long serialVersionUID = 5882916676576474990L;

	public static final int HAS_ALREADY = 1;
	
	private long salesId;// 理财师ID
	private long id;// 客户iD
	private String  email1;// 产品id
	private String cellPhone1; 
	private long marketingQuantity;// 订单id
	private long purchasedQuantity;// 购买数量
	private long purchasedTotalAmount;//购买产品个数
	private long prodDividendQty;// 分红个数
	private long bindingStatusId;// 0 未绑定 ；1已绑定
	private String name; // 客户名称
	private List<MarketCsOrder> maCsorder = null;// 客户下的订单
	
	public long getSalesId() {
		return salesId;
	}
	public void setSalesId(long salesId) {
		this.salesId = salesId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail1() {
		if (StrUtil.isBlank(email1)) {
			return "";
		}
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getCellPhone1() {
		if (StrUtil.isBlank(cellPhone1)) {
			return "";
		}
		return cellPhone1;
	}
	public void setCellPhone1(String cellPhone1) {
		this.cellPhone1 = cellPhone1;
	}
	public long getMarketingQuantity() {
		return marketingQuantity;
	}
	public void setMarketingQuantity(long marketingQuantity) {
		this.marketingQuantity = marketingQuantity;
	}
	public long getPurchasedQuantity() {
		return purchasedQuantity;
	}
	public void setPurchasedQuantity(long purchasedQuantity) {
		this.purchasedQuantity = purchasedQuantity;
	}
	public long getPurchasedTotalAmount() {
		return purchasedTotalAmount;
	}
	public void setPurchasedTotalAmount(long purchasedTotalAmount) {
		this.purchasedTotalAmount = purchasedTotalAmount;
	}
	public long getProdDividendQty() {
		return prodDividendQty;
	}
	public void setProdDividendQty(long prodDividendQty) {
		this.prodDividendQty = prodDividendQty;
	}

	public long getBindingStatusId() {
		return bindingStatusId;
	}
	public void setBindingStatusId(long bindingStatusId) {
		this.bindingStatusId = bindingStatusId;
	}
	public String getName() {
		if (StrUtil.isBlank(name)) {
			return "";
		}
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<MarketCsOrder> getMaCsorder() {
		return maCsorder;
	}
	public void setMaCsorder(List<MarketCsOrder> maCsorder) {
		this.maCsorder = maCsorder;
	}
	
}
