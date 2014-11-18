package com.incito.finshine.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.android.core.util.StrUtil;

/**
 * 投资分析统计信息  左一
 * 
 */
public class InvestmentAnalysisStatistics implements Serializable {

	private static final long serialVersionUID = -6064590960102945421L;

	/*
	 * 理财师主键编号
	 */
	private long salesId;

	/*
	 * 客户主键编号
	 */
	private long customerId;
	/*
	 * 客户姓名
	 */
	private String appellation;

	/*
	 * 投资总额
	 */
	private BigDecimal investmentTotalAmount;

	/*
	 * 累计总收益
	 */
	private BigDecimal cumulativeTotalRevenue;

	/*
	 * 累计投资数
	 */
	private int totalPurchasedQty;

	/*
	 * 平均投资期限
	 */
	private BigDecimal averageInvestmentPeriod;

	/*
	 * 平均预期收益
	 */
	private BigDecimal averageExpectedRevenue;

	/*
	 * 平均实际收益
	 */
	private BigDecimal averageActualRevenue;

	public long getSalesId() {
		return salesId;
	}

	public void setSalesId(long salesId) {
		this.salesId = salesId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getAppellation() {
		if (StrUtil.isBlank(appellation)) {
			return "";
		}
		return appellation;
	}

	public void setAppellation(String appellation) {
		this.appellation = appellation;
	}

	public int getTotalPurchasedQty() {
		return totalPurchasedQty;
	}

	public void setTotalPurchasedQty(int totalPurchasedQty) {
		this.totalPurchasedQty = totalPurchasedQty;
	}

	public BigDecimal getInvestmentTotalAmount() {
		return investmentTotalAmount;
	}

	public void setInvestmentTotalAmount(BigDecimal investmentTotalAmount) {
		this.investmentTotalAmount = investmentTotalAmount;
	}

	public BigDecimal getCumulativeTotalRevenue() {
		return cumulativeTotalRevenue;
	}

	public void setCumulativeTotalRevenue(BigDecimal cumulativeTotalRevenue) {
		this.cumulativeTotalRevenue = cumulativeTotalRevenue;
	}

	public BigDecimal getAverageInvestmentPeriod() {
		return averageInvestmentPeriod;
	}

	public void setAverageInvestmentPeriod(BigDecimal averageInvestmentPeriod) {
		this.averageInvestmentPeriod = averageInvestmentPeriod;
	}

	public BigDecimal getAverageExpectedRevenue() {
		return averageExpectedRevenue;
	}

	public void setAverageExpectedRevenue(BigDecimal averageExpectedRevenue) {
		this.averageExpectedRevenue = averageExpectedRevenue;
	}

	public BigDecimal getAverageActualRevenue() {
		return averageActualRevenue;
	}

	public void setAverageActualRevenue(BigDecimal averageActualRevenue) {
		this.averageActualRevenue = averageActualRevenue;
	}

}
