package com.incito.finshine.entity.spinner;

import java.io.Serializable;

/**
 * 合同批量保存输入参数
 * 
 * @author xiaoming
 * 
 */
public class ContractBatchParam implements Serializable {

	private static final long serialVersionUID = 2815885074185026847L;

	/**
	 * 理财师ID
	 */
	private long salesId;

	/**
	 * 客户ID
	 */
	private long customerId;

	/**
	 * 销售订单ID
	 */
	private long salesOrderId;

	/**
	 * 委托人风险评估表信息
	 */
	private RiskAssessmentParam riskAssessmentParam;

	/**
	 * 交易业务申请书信息
	 */
	private TradingBusinessApplicationParam tradingBusinessApplicationParam;

	/**
	 * 传真交易协议书1信息
	 */
	private FaxTradingAgreementParam faxTradingAgreementParam1;

	/**
	 * 传真交易协议书2信息
	 */
	private FaxTradingAgreementParam faxTradingAgreementParam2;

	public long getSalesId() {
		return salesId;
	}

	public void setSalesId(long salesId) {
		this.salesId = salesId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public RiskAssessmentParam getRiskAssessmentParam() {
		return riskAssessmentParam;
	}

	public void setRiskAssessmentParam(RiskAssessmentParam riskAssessmentParam) {
		this.riskAssessmentParam = riskAssessmentParam;
	}

	public TradingBusinessApplicationParam getTradingBusinessApplicationParam() {
		return tradingBusinessApplicationParam;
	}

	public void setTradingBusinessApplicationParam(
			TradingBusinessApplicationParam tradingBusinessApplicationParam) {
		this.tradingBusinessApplicationParam = tradingBusinessApplicationParam;
	}

	public FaxTradingAgreementParam getFaxTradingAgreementParam1() {
		return faxTradingAgreementParam1;
	}

	public void setFaxTradingAgreementParam1(
			FaxTradingAgreementParam faxTradingAgreementParam1) {
		this.faxTradingAgreementParam1 = faxTradingAgreementParam1;
	}

	public FaxTradingAgreementParam getFaxTradingAgreementParam2() {
		return faxTradingAgreementParam2;
	}

	public void setFaxTradingAgreementParam2(
			FaxTradingAgreementParam faxTradingAgreementParam2) {
		this.faxTradingAgreementParam2 = faxTradingAgreementParam2;
	}

}
