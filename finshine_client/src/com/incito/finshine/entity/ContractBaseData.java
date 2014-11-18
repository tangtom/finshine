package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

/**
 * 
 * 合同签订一：合同基本信息
 * 
 */
public class ContractBaseData implements Serializable {

	private static final long serialVersionUID = 8560200513091326981L;

	private long id = -1;

	/**
	 * 销售订单ID
	 */
	private long salesOrderId;

	/**
	 * 客户
	 */
	private Customer customer;

	/**
	 * 理财师
	 */
	private User salesman;

	/**
	 * 认购份额 后台是float
	 */
	private int subscribeUnits;

	/**
	 * 认购金额大写
	 */
	private String capitalLetters;

	/**
	 * 收益银行名称
	 */
	private Bank bank;

	/**
	 * 银行卡号
	 */
	private String bankCardNumber;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public User getSalesman() {
		return salesman;
	}

	public void setSalesman(User salesman) {
		this.salesman = salesman;
	}

	public int getSubscribeUnits() {
		return subscribeUnits;
	}

	public void setSubscribeUnits(int subscribeUnits) {
		this.subscribeUnits = subscribeUnits;
	}

	public String getCapitalLetters() {
		if (StrUtil.isBlank(capitalLetters)) {
			return "";
		}
		return capitalLetters;
	}

	public void setCapitalLetters(String capitalLetters) {
		this.capitalLetters = capitalLetters;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
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

}
