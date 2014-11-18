package com.incito.finshine.entity.spinner;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;
import com.incito.finshine.entity.TradingBusinessApplication;

/**
 * 交易业务申请书输入参数
 * 
 * @author xiaoming
 * 
 */
public class TradingBusinessApplicationParam implements Serializable {

	private static final long serialVersionUID = 4708706750696087326L;

	/**
	 * 受理类型ID
	 */
	private int acceptanceTypeId;

	/**
	 * 申请人
	 */
	private String applicantName;

	/**
	 * 性别
	 */
	private short gender;

	/**
	 * 证件类型ID
	 */
	private int idTypeId;

	/**
	 * 国籍ID
	 */
	private int nationalityId;

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
	private int shippingMethodId;

	/**
	 * 手机号
	 */
	private String cellphone;

	/**
	 * 账单寄送地址
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
	 * 邮编
	 */
	private String postCode;

	/**
	 * 资产管理计划名称
	 */
	private String productName;

	/**
	 * 金额
	 */
	private float amount;

	/**
	 * 大写金额
	 */
	private String capitalLetters;

	/**
	 * 签名日期
	 */
	private long dateOfSign;

	public int getAcceptanceTypeId() {
		return acceptanceTypeId;
	}

	public void setAcceptanceTypeId(int acceptanceTypeId) {
		this.acceptanceTypeId = acceptanceTypeId;
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

	public int getIdTypeId() {
		return idTypeId;
	}

	public void setIdTypeId(int idTypeId) {
		this.idTypeId = idTypeId;
	}

	public int getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(int nationalityId) {
		this.nationalityId = nationalityId;
	}

	public long getIdDateOfExpire() {
		return idDateOfExpire;
	}

	public void setIdDateOfExpire(long idDateOfExpire) {
		this.idDateOfExpire = idDateOfExpire;
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

	public int getShippingMethodId() {
		return shippingMethodId;
	}

	public void setShippingMethodId(int shippingMethodId) {
		this.shippingMethodId = shippingMethodId;
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

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
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

	public long getDateOfSign() {
		return dateOfSign;
	}

	public void setDateOfSign(long dateOfSign) {
		this.dateOfSign = dateOfSign;
	}

	public TradingBusinessApplicationParam(TradingBusinessApplication trad) {
		this.acceptanceTypeId = trad.getAcceptanceType().getId();
		this.amount = trad.getSubscribeUnits();
		this.applicantName = trad.getApplicantName();
		this.bankAccount = trad.getBankAccount();
		this.bankCode = trad.getBankCode();
		this.bankFullName = trad.getBankFullName();
		this.bankOwner = trad.getBankOwner();
		this.capitalLetters = trad.getCapitalLetters();
		this.cellphone = trad.getCellPhone();
		this.dateOfSign = trad.getDateOfSign();
		this.email = trad.getEmail();
		this.faxNumber = trad.getFaxNumber();
		this.gender = trad.getGender();
		this.idDateOfExpire = trad.getIdDateOfExpire();
		this.idNumber = trad.getIdNumber();
		this.idTypeId = trad.getIdType().getId();
		this.nationalityId = trad.getNationality().getId();
		this.postCode = trad.getPostCode();
		this.productName = trad.getProductName();
		this.shippingAddress = trad.getShippingAddress();
		this.shippingMethodId = trad.getShippingMethod().getId();
	}

}
