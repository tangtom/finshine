package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

/**
 * 投资记录
 * 
 */
public class InvestmentRecord implements Serializable {

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
	 * 产品主键编号
	 */
	private long prodId;

	/*
	 * 合同主键编号
	 */
	private long contractId;

	/*
	 * 订单主键编号
	 */
	private long salesOrderId;

	/*
	 * 营销状态主键编号
	 */
	private long marketingStatusId;

	/*
	 * 产品第一级类型主键编号
	 */
	private long prodFirstType;

	/*
	 * 产品第一级类型名称
	 */
	private String prodFirstTypeName;

	/*
	 * 产品名称
	 */
	private String prodName;

	/*
	 * 预期收益
	 */
	private double expectedRevenue;

	/*
	 * 产品代码
	 */
	private String prodCode;

	/*
	 * 发行商偏好
	 */
	private String prodPreference;

	/*
	 * 起投金额
	 */
	private double prodStart;

	/*
	 * 投资金额
	 */
	private double changeAmount;

	/*
	 * 产品状态主键编号
	 */
	private long prodStatus;

	/*
	 * 产品状态名称
	 */
	private String prodStatusName;

	/*
	 * 发行日期开始
	 */
	private long prodOnDateTime;
	/*
	 * 发行日期结束
	 */
	private long prodEnDateTime;

	/*
	 * 产品到期状态主键编号 0全部、1即将到期、2未到期、3、已到期
	 */
	private long expireStatus;

	/*
	 * 产品到期状态名称
	 */
	private String expireStatusName;

	/*
	 * 收益
	 */
	private double obtainedProfit;

	/*
	 * 理财师名字
	 */
	private String userName;
	/*
	 * 客户名字
	 */
	private String customerName;

	/*
	 * 理财师名字
	 */
	private String salesOrderNumber;

	/*
	 * 购买时间
	 */
	private long purchasedDate;

	/*
	 * 投资期限 20140709 add 单位为天
	 */
	private long investmentPeriod;

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

	public long getProdId() {
		return prodId;
	}

	public void setProdId(long prodId) {
		this.prodId = prodId;
	}

	public long getContractId() {
		return contractId;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

	public long getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public long getMarketingStatusId() {
		return marketingStatusId;
	}

	public void setMarketingStatusId(long marketingStatusId) {
		this.marketingStatusId = marketingStatusId;
	}

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

	public String getProdName() {
		if (StrUtil.isBlank(prodName)) {
			return "";
		}
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public double getExpectedRevenue() {
		return expectedRevenue;
	}

	public void setExpectedRevenue(double expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}

	public String getProdCode() {
		if (StrUtil.isBlank(prodCode)) {
			return "";
		}
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getProdPreference() {
		if (StrUtil.isBlank(prodPreference)) {
			return "";
		}
		return prodPreference;
	}

	public void setProdPreference(String prodPreference) {
		this.prodPreference = prodPreference;
	}

	public double getProdStart() {
		return prodStart;
	}

	public void setProdStart(double prodStart) {
		this.prodStart = prodStart;
	}

	public double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(double changeAmount) {
		this.changeAmount = changeAmount;
	}

	public long getProdStatus() {
		return prodStatus;
	}

	public void setProdStatus(long prodStatus) {
		this.prodStatus = prodStatus;
	}

	public String getProdStatusName() {
		if (StrUtil.isBlank(prodStatusName)) {
			return "";
		}
		return prodStatusName;
	}

	public void setProdStatusName(String prodStatusName) {
		this.prodStatusName = prodStatusName;
	}

	public long getProdOnDateTime() {
		return prodOnDateTime;
	}

	public void setProdOnDateTime(long prodOnDateTime) {
		this.prodOnDateTime = prodOnDateTime;
	}

	public long getProdEnDateTime() {
		return prodEnDateTime;
	}

	public void setProdEnDateTime(long prodEnDateTime) {
		this.prodEnDateTime = prodEnDateTime;
	}

	public long getExpireStatus() {
		return expireStatus;
	}

	public void setExpireStatus(long expireStatus) {
		this.expireStatus = expireStatus;
	}

	public String getExpireStatusName() {
		if (StrUtil.isBlank(expireStatusName)) {
			return "";
		}
		return expireStatusName;
	}

	public void setExpireStatusName(String expireStatusName) {
		this.expireStatusName = expireStatusName;
	}

	public double getObtainedProfit() {
		return obtainedProfit;
	}

	public void setObtainedProfit(double obtainedProfit) {
		this.obtainedProfit = obtainedProfit;
	}

	public String getUserName() {
		if (StrUtil.isBlank(userName)) {
			return "";
		}
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCustomerName() {
		if (StrUtil.isBlank(customerName)) {
			return "";
		}
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSalesOrderNumber() {
		if (StrUtil.isBlank(salesOrderNumber)) {
			return "";
		}
		return salesOrderNumber;
	}

	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}

	public long getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(long purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public long getInvestmentPeriod() {
		return investmentPeriod;
	}

	public void setInvestmentPeriod(long investmentPeriod) {
		this.investmentPeriod = investmentPeriod;
	}
}
