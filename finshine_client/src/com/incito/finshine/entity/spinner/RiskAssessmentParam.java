package com.incito.finshine.entity.spinner;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;
import com.incito.finshine.entity.RiskAssessment;

/**
 * 委托人风险评估表输入参数
 * 
 * @author xiaoming
 * 
 */
public class RiskAssessmentParam implements Serializable {

	private static final long serialVersionUID = 9099232306434257443L;

	/**
	 * 申请人名称
	 */
	private String applicantName;

	/**
	 * 是否首次填写
	 */
	private short firstTimeWrite;

	/**
	 * 证件类型
	 */
	private int idTypeId;

	/**
	 * 证件有效期
	 */
	private long idDateOfExpire;

	/**
	 * 证件号码
	 */
	private String idNumber;

	/**
	 * 学历
	 */
	private int diplomaId;

	/**
	 * 购买原因
	 */
	private String purchaseReasons;

	/**
	 * 职业ID
	 */
	private int jobId;

	/**
	 * 投资资金来源
	 */
	private String investmentResources;

	/**
	 * 年收入
	 */
	private int annualIncomeId;

	/**
	 * 调查问卷1选择值
	 */
	private String indicatorValue1;

	/**
	 * 调查问卷2选择值
	 */
	private String indicatorValue2;

	/**
	 * 调查问卷4选择值
	 */
	private String indicatorValue3;

	/**
	 * 调查问卷4选择值
	 */
	private String indicatorValue4;

	/**
	 * 调查问卷5选择值
	 */
	private String indicatorValue5;

	/**
	 * 调查问卷6选择值
	 */
	private String indicatorValue6;

	/**
	 * 总分
	 */
	private int totalPoints;

	/**
	 * 风险属性典型分类
	 */
	private String riskTypeDesc;

	/**
	 * 交易行为
	 */
	private int behaviorId;

	/**
	 * 签名日期
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

	public RiskAssessmentParam(RiskAssessment assessment) {
		this.annualIncomeId = assessment.getAnnualIncome().getId();
		this.applicantName = assessment.getApplicantName();
		this.behaviorId = assessment.getBehavior();
		this.companyAddress = assessment.getCompanyAddress();
		this.dateOfSign = assessment.getDateOfSign();
		this.diplomaId = assessment.getDiploma().getId();
		this.faxNumber = assessment.getFaxNumber();
		this.firstTimeWrite = assessment.getFirstTimeWrite();
		this.idDateOfExpire = assessment.getIdDateOfExpire();
		this.idNumber = assessment.getIdNumber();
		this.idTypeId = assessment.getIdType().getId();
		this.investmentResources = assessment.getInvestmentSources();
		this.jobId = assessment.getJob().getId();
		this.purchaseReasons = assessment.getPurchaseReasons();
		this.riskTypeDesc = assessment.getRiskTypeDesc();
		this.telephone = assessment.getTelephone();
		this.totalPoints = assessment.getTotalPoints();
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

	public int getIdTypeId() {
		return idTypeId;
	}

	public void setIdTypeId(int idTypeId) {
		this.idTypeId = idTypeId;
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

	public int getDiplomaId() {
		return diplomaId;
	}

	public void setDiplomaId(int diplomaId) {
		this.diplomaId = diplomaId;
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

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getInvestmentResources() {
		if (StrUtil.isBlank(investmentResources)) {
			return "";
		}
		return investmentResources;
	}

	public void setInvestmentResources(String investmentResources) {
		this.investmentResources = investmentResources;
	}

	public int getAnnualIncomeId() {
		return annualIncomeId;
	}

	public void setAnnualIncomeId(int annualIncomeId) {
		this.annualIncomeId = annualIncomeId;
	}

	public String getIndicatorValue1() {
		if (StrUtil.isBlank(indicatorValue1)) {
			return "";
		}
		return indicatorValue1;
	}

	public void setIndicatorValue1(String indicatorValue1) {
		this.indicatorValue1 = indicatorValue1;
	}

	public String getIndicatorValue2() {
		if (StrUtil.isBlank(indicatorValue2)) {
			return "";
		}
		return indicatorValue2;
	}

	public void setIndicatorValue2(String indicatorValue2) {
		this.indicatorValue2 = indicatorValue2;
	}

	public String getIndicatorValue3() {
		if (StrUtil.isBlank(indicatorValue3)) {
			return "";
		}
		return indicatorValue3;
	}

	public void setIndicatorValue3(String indicatorValue3) {
		this.indicatorValue3 = indicatorValue3;
	}

	public String getIndicatorValue4() {
		if (StrUtil.isBlank(indicatorValue4)) {
			return "";
		}
		return indicatorValue4;
	}

	public void setIndicatorValue4(String indicatorValue4) {
		this.indicatorValue4 = indicatorValue4;
	}

	public String getIndicatorValue5() {
		if (StrUtil.isBlank(indicatorValue5)) {
			return "";
		}
		return indicatorValue5;
	}

	public void setIndicatorValue5(String indicatorValue5) {
		this.indicatorValue5 = indicatorValue5;
	}

	public String getIndicatorValue6() {
		if (StrUtil.isBlank(indicatorValue6)) {
			return "";
		}
		return indicatorValue6;
	}

	public void setIndicatorValue6(String indicatorValue6) {
		this.indicatorValue6 = indicatorValue6;
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

	public int getBehaviorId() {
		return behaviorId;
	}

	public void setBehaviorId(int behaviorId) {
		this.behaviorId = behaviorId;
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
