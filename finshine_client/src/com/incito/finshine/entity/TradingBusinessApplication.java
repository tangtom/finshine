package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;
import com.incito.finshine.entity.spinner.AcceptanceType;
import com.incito.finshine.entity.spinner.IDType;
import com.incito.finshine.entity.spinner.Nationality;
import com.incito.finshine.entity.spinner.ShippingMethod;

/**
 * 
 * @author xiaoming 专项资产管理计划交易业务申请书（3）
 */
public class TradingBusinessApplication implements Serializable {

	private static final long serialVersionUID = 5392605677238352862L;

	private long id = -1;

	/**
	 * 销售订单ID
	 */
	private long salesOrderId = -1;

	/**
	 * 受理类型
	 */
	private AcceptanceType acceptanceType;

	/**
	 * 申请人
	 */
	private Customer applicant;

	/**
	 * 申请人
	 */
	private String applicantName;

	/**
	 * 性别
	 */
	private short gender;

	/**
	 * 证件类别
	 */
	private IDType idType;

	/**
	 * 国籍
	 */
	private Nationality nationality;

	/**
	 * 证件有效期
	 */
	private long idDateOfExpire;

	/**
	 * 证件号码
	 */
	private String idNumber;

	/**
	 * 银行户名
	 */
	private String bankOwner;

	/**
	 * 银行行号
	 */
	private String bankCode;

	/**
	 * 银行账号
	 */
	private String bankAccount;

	/**
	 * 银行全称
	 */
	private String bankFullName;

	/**
	 * 账单寄送方式
	 */
	private ShippingMethod shippingMethod;

	/**
	 * 手机
	 */
	private String cellPhone;

	/**
	 * 账单寄达地址
	 */
	private String shippingAddress;

	/**
	 * 传真
	 */
	private String faxNumber;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 电话
	 */
	private String telephone;

	/**
	 * 邮编
	 */
	private String postCode;

	/**
	 * 资产管理计划名称
	 */
	private String productName;

	/**
	 * 认购份额
	 */
	private int subscribeUnits;

	/**
	 * 大写金额
	 */
	private String capitalLetters;

	/**
	 * 投资人声明
	 */
	private String statement;

	/**
	 * 乙方
	 */
	private String publisherName;

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

	public AcceptanceType getAcceptanceType() {
		return acceptanceType;
	}

	public void setAcceptanceType(AcceptanceType acceptanceType) {
		this.acceptanceType = acceptanceType;
	}

	public Customer getApplicant() {
		return applicant;
	}

	public void setApplicant(Customer applicant) {
		this.applicant = applicant;
	}

	public String getApplicantName() {
		if (StrUtil.isBlank(applicantName)) {
			return "";
		}
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public short getGender() {
		return gender;
	}

	public void setGender(short gender) {
		this.gender = gender;
	}

	public IDType getIdType() {
		return idType;
	}

	public void setIdType(IDType idType) {
		this.idType = idType;
	}

	public Nationality getNationality() {
		return nationality;
	}

	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}

	public long getIdDateOfExpire() {
		return idDateOfExpire;
	}

	public void setIdDateOfExpire(long idDateOfExpire) {
		this.idDateOfExpire = idDateOfExpire;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getBankOwner() {
		if (StrUtil.isBlank(bankOwner)) {
			return "";
		}
		return bankOwner;
	}

	public void setBankOwner(String bankOwner) {
		this.bankOwner = bankOwner;
	}

	public String getBankCode() {
		if (StrUtil.isBlank(bankCode)) {
			return "";
		}
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankAccount() {
		if (StrUtil.isBlank(bankAccount)) {
			return "";
		}
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankFullName() {
		if (StrUtil.isBlank(bankFullName)) {
			return "";
		}
		return bankFullName;
	}

	public void setBankFullName(String bankFullName) {
		this.bankFullName = bankFullName;
	}

	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getCellPhone() {
		if (StrUtil.isBlank(cellPhone)) {
			return "";
		}
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getShippingAddress() {
		if (StrUtil.isBlank(shippingAddress)) {
			return "";
		}
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getFaxNumber() {
		if (StrUtil.isBlank(faxNumber)) {
			return "";
		}
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getEmail() {
		if (StrUtil.isBlank(email)) {
			return "";
		}
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		if (StrUtil.isBlank(telephone)) {
			return "";
		}
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public String getProductName() {
		if (StrUtil.isBlank(productName)) {
			return "";
		}
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getStatement() {
		if (StrUtil.isBlank(statement)) {
			return "";
		}
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
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

	public long getDateOfSign() {
		return dateOfSign;
	}

	public void setDateOfSign(long dateOfSign) {
		this.dateOfSign = dateOfSign;
	}

}
