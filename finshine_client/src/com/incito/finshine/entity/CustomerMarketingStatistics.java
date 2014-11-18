package com.incito.finshine.entity;

import java.io.Serializable;

/**
 * 客户营销首页统计
 * 
 */
public class CustomerMarketingStatistics implements Serializable {

	private static final long serialVersionUID = -6064590960102945421L;

	/*
	 * 营销中的数量
	 */
	private int marketingQty;

	/*
	 * 累计签单数
	 */
	private int purchasedQuantity;

	/*
	 * 客户总数
	 */
	private int customerQty;

	/*
	 * 绑定客户数
	 */
	private int bindQty;

	/*
	 * 已经购买产品的客户总数
	 */
	private int purchasedCustomerQty;

	/*
	 * 营销机会数
	 */
	private int marketingOppQty;

	/*
	 * 累计签单额
	 */
	private double purchasedTotalAmount;

	public int getMarketingQty() {
		return marketingQty;
	}

	public void setMarketingQty(int marketingQty) {
		this.marketingQty = marketingQty;
	}

	public int getPurchasedQuantity() {
		return purchasedQuantity;
	}

	public void setPurchasedQuantity(int purchasedQuantity) {
		this.purchasedQuantity = purchasedQuantity;
	}

	public int getCustomerQty() {
		return customerQty;
	}

	public void setCustomerQty(int customerQty) {
		this.customerQty = customerQty;
	}

	public int getPurchasedCustomerQty() {
		return purchasedCustomerQty;
	}

	public void setPurchasedCustomerQty(int purchasedCustomerQty) {
		this.purchasedCustomerQty = purchasedCustomerQty;
	}

	public int getBindQty() {
		return bindQty;
	}

	public void setBindQty(int bindQty) {
		this.bindQty = bindQty;
	}

	public int getMarketingOppQty() {
		return marketingOppQty;
	}

	public void setMarketingOppQty(int marketingOppQty) {
		this.marketingOppQty = marketingOppQty;
	}

	public double getPurchasedTotalAmount() {
		return purchasedTotalAmount;
	}

	public void setPurchasedTotalAmount(double purchasedTotalAmount) {
		this.purchasedTotalAmount = purchasedTotalAmount;
	}

}
