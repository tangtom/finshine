package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

/**
 * 绑定协议
 * 
 * @author xiaoming
 * 
 */
public class BindingAgreement implements Serializable {

	private static final long serialVersionUID = -661342933390509995L;

	private long id;

	/**
	 * 客户
	 */
	private Customer customer;

	/**
	 * 理财师
	 */
	private User salesman;

	/**
	 * 客户姓名
	 */
	private String customerName;

	/**
	 * 证件类型
	 */
	private IDType idType;

	/**
	 * 证件号码
	 */
	private String idNumber;

	/**
	 * 证件有效期
	 */
	private long idDateOfExpire;

	/**
	 * 手机号码
	 */
	private String cellphone;

	/**
	 * 协议内容
	 */
	private String content;

	/**
	 * 签名日期
	 */
	private long dateOfSign;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getCustomerName() {
		if (StrUtil.isBlank(customerName)) {
			return "";
		}
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public IDType getIdType() {
		return idType;
	}

	public void setIdType(IDType idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		if (StrUtil.isBlank(idNumber)) {
			return "";
		}
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public long getIdDateOfExpire() {
		return idDateOfExpire;
	}

	public void setIdDateOfExpire(long idDateOfExpire) {
		this.idDateOfExpire = idDateOfExpire;
	}

	public String getCellphone() {
		if (StrUtil.isBlank(cellphone)) {
			return "";
		}
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getContent() {
		if (StrUtil.isBlank(content)) {
			return "";
		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getDateOfSign() {
		return dateOfSign;
	}

	public void setDateOfSign(long dateOfSign) {
		this.dateOfSign = dateOfSign;
	}

}
