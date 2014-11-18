package com.incito.finshine.entity;

import java.util.Date;

import com.android.core.util.StrUtil;

public class Pointer {

	private long id;//订单ID
	private String orderNumber;//订单编号
	private String publisher;//发行商
	private Product product;//产品信息
	private double amount;//投资金额
	private Customer customer;//客户
	private User salesman;//理财师
	private Date dateOfCreate;//创建时间
	private double fixedCommissionRatio;//固定佣金比例
	private double extraCommissionRatio;//奖励佣金比例
	private double fixedCommission;//固定佣金数量
	private double extraCommission;//奖励佣金数量
	private long result_fk;//佣金结算结果
	private short isNew;//是否为new
	public short getIsNew() {
		return isNew;
	}
	public void setIsNew(short isNew) {
		this.isNew = isNew;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOrderNumber() {
		if (StrUtil.isBlank(orderNumber)) {
			return "";
		}
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getPublisher() {
		if (StrUtil.isBlank(publisher)) {
			return "";
		}
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
	public Date getDateOfCreate() {
		return dateOfCreate;
	}
	public void setDateOfCreate(Date dateOfCreate) {
		this.dateOfCreate = dateOfCreate;
	}
	public double getFixedCommissionRatio() {
		return fixedCommissionRatio;
	}
	public void setFixedCommissionRatio(double fixedCommissionRatio) {
		this.fixedCommissionRatio = fixedCommissionRatio;
	}
	public double getExtraCommissionRatio() {
		return extraCommissionRatio;
	}
	public void setExtraCommissionRatio(double extraCommissionRatio) {
		this.extraCommissionRatio = extraCommissionRatio;
	}
	public double getFixedCommission() {
		return fixedCommission;
	}
	public void setFixedCommission(double fixedCommission) {
		this.fixedCommission = fixedCommission;
	}
	public double getExtraCommission() {
		return extraCommission;
	}
	public void setExtraCommission(double extraCommission) {
		this.extraCommission = extraCommission;
	}
	public long getResult_fk() {
		return result_fk;
	}
	public void setResult_fk(long result_fk) {
		this.result_fk = result_fk;
	}
	
}
