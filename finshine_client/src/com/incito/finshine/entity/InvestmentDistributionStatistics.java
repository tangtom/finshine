package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

/**
 * 投资分布统计信息  左二
 * 
 */
public class InvestmentDistributionStatistics implements Serializable {

	private static final long serialVersionUID = -6064590960102945421L;

	/*
	 * 产品第一级类型主键编号
	 */
	private long prodFirstType;

	/*
	 * 产品第一级类型名称
	 */
	private String prodFirstTypeName;

	/*
	 * 投资金额
	 */
	private double investmentAmount;

	/*
	 * 成交订单数
	 */
	private int purchasedQty;

	public long getProdFirstType() {
		return prodFirstType;
	}

	public void setProdFirstType(long prodFirstType) {
		this.prodFirstType = prodFirstType;
	}

	public String getProdFirstTypeName() {
		if (StrUtil.isBlank(prodFirstTypeName)) {
			return "";
		}
		return prodFirstTypeName;
	}

	public void setProdFirstTypeName(String prodFirstTypeName) {
		this.prodFirstTypeName = prodFirstTypeName;
	}

	public double getInvestmentAmount() {
		return investmentAmount;
	}

	public void setInvestmentAmount(double investmentAmount) {
		this.investmentAmount = investmentAmount;
	}

	public int getPurchasedQty() {
		return purchasedQty;
	}

	public void setPurchasedQty(int purchasedQty) {
		this.purchasedQty = purchasedQty;
	}

}
