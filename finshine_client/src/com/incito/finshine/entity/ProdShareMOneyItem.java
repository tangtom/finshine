package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

public class ProdShareMOneyItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5196479809014769968L;

	private long customerId;
	private String customerName;;
	private long salesId;;
	private String email1;
	private String cellPhone1;;
	private long prodId;;
	private String prodName;;
	private long profitDate;;
	private long expirationDays;
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
	public long getSalesId() {
		return salesId;
	}
	public void setSalesId(long salesId) {
		this.salesId = salesId;
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
	public long getProdId() {
		return prodId;
	}
	public void setProdId(long prodId) {
		this.prodId = prodId;
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
	public long getProfitDate() {
		return profitDate;
	}
	public void setProfitDate(long profitDate) {
		this.profitDate = profitDate;
	}
	public long getExpirationDays() {
		return expirationDays;
	}
	public void setExpirationDays(long expirationDays) {
		this.expirationDays = expirationDays;
	}

}
