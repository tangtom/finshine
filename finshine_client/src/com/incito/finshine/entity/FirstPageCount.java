package com.incito.finshine.entity;

import java.io.Serializable;

/**
 * <dl>
 * <dt>FirstPageCount.java</dt>
 * <dd>Description:首页统计</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-17 上午9:32:36</dd>
 * </dl>
 * 
 * @author lihs
 */
public class FirstPageCount implements Serializable {

	private static final long serialVersionUID = 370254840309157949L;
     
	// 营销数量,当前正在进行中的
	private long marketingQty;
	// 购买数量，历史完成
	private long purchasedQuantity;
	// 客户总数
	private long customerQty;
	// 已经绑定客户的数量，产品和客户的数量
	private long bindQty;
	// 已购买客户的数量
	private long purchasedCustomerQty;
	// 营销机会的数量
	private long marketingOppQty;
	
	public long getMarketingQty() {
		return marketingQty;
	}
	public void setMarketingQty(long marketingQty) {
		this.marketingQty = marketingQty;
	}
	public long getPurchasedQuantity() {
		return purchasedQuantity;
	}
	public void setPurchasedQuantity(long purchasedQuantity) {
		this.purchasedQuantity = purchasedQuantity;
	}
	public long getCustomerQty() {
		return customerQty;
	}
	public void setCustomerQty(long customerQty) {
		this.customerQty = customerQty;
	}
	public long getBindQty() {
		return bindQty;
	}
	public void setBindQty(long bindQty) {
		this.bindQty = bindQty;
	}
	public long getPurchasedCustomerQty() {
		return purchasedCustomerQty;
	}
	public void setPurchasedCustomerQty(long purchasedCustomerQty) {
		this.purchasedCustomerQty = purchasedCustomerQty;
	}
	public long getMarketingOppQty() {
		return marketingOppQty;
	}
	public void setMarketingOppQty(long marketingOppQty) {
		this.marketingOppQty = marketingOppQty;
	}
	public double getPurchasedTotalAmount() {
		return purchasedTotalAmount;
	}
	public void setPurchasedTotalAmount(double purchasedTotalAmount) {
		this.purchasedTotalAmount = purchasedTotalAmount;
	}
	private double purchasedTotalAmount;
}
