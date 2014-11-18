package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.android.core.util.StrUtil;
import com.incito.finshine.entity.spinner.AnnualIncome;
import com.incito.finshine.entity.spinner.Diploma;
import com.incito.finshine.entity.spinner.IDType;
import com.incito.finshine.entity.spinner.Job;

/**
 * 
 * @author xiaoming 委托人风险评估表（1）
 */
public class RiskAssessment implements Serializable {

	private static final long serialVersionUID = 8560200513091326981L;

	private long id = -1;

	/**
	 * 销售订单ID
	 */
	private long salesOrderId = -1;

	/**
	 * 客户
	 */
	private Customer applicant;

	/**
	 * 申请人
	 */
	private String applicantName;

	/**
	 * 是否首次填写 0:false 1:true
	 */
	private short firstTimeWrite;

	/**
	 * 证件类别
	 */
	private IDType idType;

	/**
	 * 证件有效期
	 */
	private long idDateOfExpire;// long

	/**
	 * 证件号码
	 */
	private String idNumber;

	/**
	 * 学历
	 */
	private Diploma diploma;

	/**
	 * 购买原因
	 */
	private String purchaseReasons;

	/**
	 * 职业
	 */
	private Job job;

	/**
	 * 投资资金来源
	 */
	private String investmentSources;

	/**
	 * 年收入
	 */
	private AnnualIncome annualIncome;

	/**
	 * 风险评估指标
	 */
	private List<RiskIndicator> indicators;

	/**
	 * 风险得分
	 */
	private int totalPoints;

	/**
	 * 风险描述
	 */
	private String riskTypeDesc;

	/**
	 * 交易行为 1  2
	 */
	private int behavior;

	/**
	 * 签订日期
	 */
	private long dateOfSign;

	/**
	 * 公司地址
	 */
	private String companyAddress;

	/**
	 * 电话
	 */
	private String telephone;

	/**
	 * 传真
	 */
	private String faxNumber;

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

	public short getFirstTimeWrite() {
		return firstTimeWrite;
	}

	public void setFirstTimeWrite(short firstTimeWrite) {
		this.firstTimeWrite = firstTimeWrite;
	}

	public IDType getIdType() {
		return idType;
	}

	public void setIdType(IDType idType) {
		this.idType = idType;
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

	public Diploma getDiploma() {
		return diploma;
	}

	public void setDiploma(Diploma diploma) {
		this.diploma = diploma;
	}

	public String getPurchaseReasons() {
		if (StrUtil.isBlank(purchaseReasons)) {
			return "";
		}
		return purchaseReasons;
	}

	public void setPurchaseReasons(String purchaseReasons) {
		this.purchaseReasons = purchaseReasons;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public String getInvestmentSources() {
		if (StrUtil.isBlank(investmentSources)) {
			return "";
		}
		return investmentSources;
	}

	public void setInvestmentSources(String investmentSources) {
		this.investmentSources = investmentSources;
	}

	public AnnualIncome getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(AnnualIncome annualIncome) {
		this.annualIncome = annualIncome;
	}

	public List<RiskIndicator> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<RiskIndicator> indicators) {
		this.indicators = indicators;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public String getRiskTypeDesc() {
		if (StrUtil.isBlank(riskTypeDesc)) {
			return "";
		}
		return riskTypeDesc;
	}

	public void setRiskTypeDesc(String riskTypeDesc) {
		this.riskTypeDesc = riskTypeDesc;
	}

	public int getBehavior() {
		return behavior;
	}

	public void setBehavior(int behavior) {
		this.behavior = behavior;
	}

	public long getDateOfSign() {
		return dateOfSign;
	}

	public void setDateOfSign(long dateOfSign) {
		this.dateOfSign = dateOfSign;
	}

	public String getCompanyAddress() {
		if (StrUtil.isBlank(companyAddress)) {
			return "";
		}
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
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

	public String getFaxNumber() {
		if (StrUtil.isBlank(faxNumber)) {
			return "";
		}
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

}
