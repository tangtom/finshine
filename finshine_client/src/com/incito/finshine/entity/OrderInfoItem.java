package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

/*
 *  一条订单信息
 */
public class OrderInfoItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5506558605044725063L;

	private long id; // 订单id
	private String salesOrderNumber;// 订单流水号 : "SO2014061300000001"
	private long productQuantity;// 产品数量 : 0
	private long totalAmount;// 总数量 : 0
	private long changeAmount;// : 订单总额


//	private long amountChangeReason;// : null
//	private long productTimeLimit;// 产品年限 : 0
//	private long orderStatusId;// 订单状态 : 1
//	private String orderStatusName;// 订单状态名称 : "未支付"
//	private String orderStatusChangeReason;// : null
//	private long effectiveDate;// 有效期 : null
//	private long paymentTypeId;// 支付类型id : 0
//	private long tradingResultId;// 交易结果id : 0
//	private String tradingResultName;// 交易结果名称 : ""
	

	private String amountChangeReason;// : null
	private long productTimeLimit;//产品年限 : 0
	private long orderStatusId;//订单状态 : 1
	private String orderStatusName;//订单状态名称 : "未支付"
	private String orderStatusChangeReason;// : null
	private long effectiveDate = 0;//有效期 : null
	private long paymentTypeId;//支付类型id : 0
	private long tradingResultId;//交易结果id : 0
	private String tradingResultName;//交易结果名称 : ""
	

	private long commissionResultId;// : 0
	private String failReason;// : null
	private String status;// : "C";// 绑定状态
	long created;// : 1402661655000
	long lastmod;// : 1402661655000
	long salesId;// : 1;//理财师id
	String username;// : "张理财" 理财师姓名
	long customerId;// : 1 客户id
	String customerName;// : "李四X" 客户姓名
	String iDNumber;// : "1234567" 证件号码
	long prodId;// : 2 产品id
	String prodName;// : "惠通达2号" 产品名称
	String prodCode;// : "HTD2" 产品代码
	String bankCardNumber;// : null 银行卡号
	long contractId;// : 0 合同id
	String isPaymentAttachment;// : "未上传支付凭证" 是否上传支付凭证
	String salesOrderLogStr;// : null
	String createdStr;// : "2014-06-13 20:14:15" 订单创建时间

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public long getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(long productQuantity) {
		this.productQuantity = productQuantity;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(long changeAmount) {
		this.changeAmount = changeAmount;
	}

	 
	public String getAmountChangeReason() {
		return amountChangeReason;
	}
	public void setAmountChangeReason(String amountChangeReason) {
		this.amountChangeReason = amountChangeReason;
	}

	public long getProductTimeLimit() {
		return productTimeLimit;
	}

	public void setProductTimeLimit(long productTimeLimit) {
		this.productTimeLimit = productTimeLimit;
	}

	public long getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(long orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public String getOrderStatusChangeReason() {
		return orderStatusChangeReason;
	}

	public void setOrderStatusChangeReason(String orderStatusChangeReason) {
		this.orderStatusChangeReason = orderStatusChangeReason;
	}

	public long getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(long effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public long getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(long paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public long getTradingResultId() {
		return tradingResultId;
	}

	public void setTradingResultId(long tradingResultId) {
		this.tradingResultId = tradingResultId;
	}

	public String getTradingResultName() {
		return tradingResultName;
	}

	public void setTradingResultName(String tradingResultName) {
		this.tradingResultName = tradingResultName;
	}

	public long getCommissionResultId() {
		return commissionResultId;
	}

	public void setCommissionResultId(long commissionResultId) {
		this.commissionResultId = commissionResultId;
	}

	public String getFailReason() {
		if (StrUtil.isBlank(failReason)) {
			return "";
		}
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getStatus() {
		if (StrUtil.isBlank(status)) {
			return "";
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public long getLastmod() {
		return lastmod;
	}

	public void setLastmod(long lastmod) {
		this.lastmod = lastmod;
	}

	public long getSalesId() {
		return salesId;
	}

	public void setSalesId(long salesId) {
		this.salesId = salesId;
	}

	public String getUsername() {
		if (StrUtil.isBlank(username)) {
			return "";
		}
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
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

	public String getiDNumber() {
		if (StrUtil.isBlank(iDNumber)) {
			return "";
		}
		return iDNumber;
	}

	public void setiDNumber(String iDNumber) {
		this.iDNumber = iDNumber;
	}

	public long getProdId() {
		return prodId;
	}

	public void setProdId(long prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
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

	public String getBankCardNumber() {
		if (StrUtil.isBlank(bankCardNumber)) {
			return "";
		}
		return bankCardNumber;
	}

	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}

	public long getContractId() {
		return contractId;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

	public String getIsPaymentAttachment() {
		if (StrUtil.isBlank(isPaymentAttachment)) {
			return "";
		}
		return isPaymentAttachment;
	}

	public void setIsPaymentAttachment(String isPaymentAttachment) {
		this.isPaymentAttachment = isPaymentAttachment;
	}

	public String getSalesOrderLogStr() {
		if (StrUtil.isBlank(salesOrderLogStr)) {
			return "";
		}
		return salesOrderLogStr;
	}

	public void setSalesOrderLogStr(String salesOrderLogStr) {
		this.salesOrderLogStr = salesOrderLogStr;
	}

	public String getCreatedStr() {
		if (StrUtil.isBlank(createdStr)) {
			return "";
		}
		return createdStr;
	}

	public void setCreatedStr(String createdStr) {
		this.createdStr = createdStr;
	}

}
