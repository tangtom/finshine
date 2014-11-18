package com.incito.finshine.entity.spinner;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;
import com.incito.finshine.entity.FaxTradingAgreement;

/**
 * 传真交易协议书输入参数
 * 
 * @author xiaoming
 * 
 */
public class FaxTradingAgreementParam implements Serializable {

	private static final long serialVersionUID = 7753366894377828633L;

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
	 * 签名日期
	 */
	private long dateOfSign;

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

	public long getDateOfSign() {
		return dateOfSign;
	}

	public void setDateOfSign(long dateOfSign) {
		this.dateOfSign = dateOfSign;
	}

	public FaxTradingAgreementParam(FaxTradingAgreement faxTrading) {
		this.confirmedTelephone = faxTrading.getConfirmedTelephone();
		this.customerName = faxTrading.getCustomerName();
		this.dateOfSign = faxTrading.getDateOfSign();
		this.postAddress = faxTrading.getPostAddress();
		this.postCode = faxTrading.getPostCode();
		this.publisherName = faxTrading.getPublisherName();
		this.serviceFax = faxTrading.getServiceFax();
	}

}
