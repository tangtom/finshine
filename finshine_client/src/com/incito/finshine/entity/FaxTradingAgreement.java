package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

/**
 * 
 * @author xiaoming 专项资产管理计划传真交易协议书（最后两步）
 * 
 */
public class FaxTradingAgreement implements Serializable {

	private static final long serialVersionUID = -8350850915048487001L;

	private long id = -1;

	/**
	 * 销售订单ID
	 */
	private long salesOrderId;

	/**
	 * 甲方名称
	 */
	private String customerName;

	/**
	 * 乙方名称
	 */
	private String publisherName;

	/**
	 * 委托服务方传真
	 */
	private String serviceFax;

	/**
	 * 传真确认电话
	 */
	private String confirmedTelephone;

	/**
	 * 邮寄地址
	 */
	private String postAddress;

	/**
	 * 邮编
	 */
	private String postCode;

	/**
	 * 协议内容
	 */
	private String content;

	/**
	 * 签订日期
	 */
	private long dateOfSign;

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

	public String getCustomerName() {
		if (StrUtil.isBlank(customerName)) {
			return "";
		}
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPublisherName() {
		if (StrUtil.isBlank(publisherName)) {
			return "";
		}
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getServiceFax() {
		if (StrUtil.isBlank(serviceFax)) {
			return "";
		}
		return serviceFax;
	}

	public void setServiceFax(String serviceFax) {
		this.serviceFax = serviceFax;
	}

	public String getConfirmedTelephone() {
		if (StrUtil.isBlank(confirmedTelephone)) {
			return "";
		}
		return confirmedTelephone;
	}

	public void setConfirmedTelephone(String confirmedTelephone) {
		this.confirmedTelephone = confirmedTelephone;
	}

	public String getPostAddress() {
		if (StrUtil.isBlank(postAddress)) {
			return "";
		}
		return postAddress;
	}

	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}

	public String getPostCode() {
		if (StrUtil.isBlank(postCode)) {
			return "";
		}
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
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
