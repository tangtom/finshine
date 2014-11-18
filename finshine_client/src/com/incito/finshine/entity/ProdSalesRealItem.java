package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

/**
 * <dl>
 * <dt>ProdSalesRealItem.java</dt>
 * <dd>Description:产品销售的实体类</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-25 下午12:43:01</dd>
 * </dl>
 * 
 * @author JOHN
 */
public class ProdSalesRealItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3686512691658865397L;

	private long salesId;
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
	public String getCustomerName() {
		if (StrUtil.isBlank(customerName)) {
			return "";
		}
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	public String getSalesOrderNo() {
		if (StrUtil.isBlank(salesOrderNo)) {
			return "";
		}
		return salesOrderNo;
	}
	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
	}
	public String getChangeAmount() {
		if (StrUtil.isBlank(changeAmount)) {
			return "";
		}
		return changeAmount;
	}
	public void setChangeAmount(String changeAmount) {
		this.changeAmount = changeAmount;
	}
	public long getOrderCreateDate() {
		return orderCreateDate;
	}
	public void setOrderCreateDate(long orderCreateDate) {
		this.orderCreateDate = orderCreateDate;
	}
	public long getTransactionStatusId() {
		return transactionStatusId;
	}
	public void setTransactionStatusId(long transactionStatusId) {
		this.transactionStatusId = transactionStatusId;
	}
	
	private long customerId;
	private long prodId;
	private String customerName;
	private long contractId;
	private long salesOrderId;
	private String salesOrderNo; 
	private String changeAmount; 
	private long orderCreateDate;
//	transactionStatusId 为1是代表已成交，为2时代表营销中。 至于营销中单数和已成交单数，前台可以根据这个栏位统计
	private long transactionStatusId;
}
