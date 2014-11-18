package com.incito.finshine.entity;

import java.io.Serializable;

/**
 * 
 * 合同第二步获取所有的文本资料
 * @author xiaoming
 * 
 */
public class ContractBatch implements Serializable {

    private static final long serialVersionUID = 6989542513869168788L;

    /**
     * 委托人风险评估表
     */
    private RiskAssessment assessment;

    /**
     * 专项资产管理计划交易业务申请书
     */
    private TradingBusinessApplication application;

    /**
     * 专项资产管理计划传真交易协议书-1
     */
    private FaxTradingAgreement agreement1;

    /**
     * 专项资产管理计划传真交易协议书-2
     */
    private FaxTradingAgreement agreement2;

    public RiskAssessment getAssessment() {
        return assessment;
    }

    public void setAssessment(RiskAssessment assessment) {
        this.assessment = assessment;
    }

    public TradingBusinessApplication getApplication() {
        return application;
    }

    public void setApplication(TradingBusinessApplication application) {
        this.application = application;
    }

    public FaxTradingAgreement getAgreement1() {
        return agreement1;
    }

    public void setAgreement1(FaxTradingAgreement agreement1) {
        this.agreement1 = agreement1;
    }

    public FaxTradingAgreement getAgreement2() {
        return agreement2;
    }

    public void setAgreement2(FaxTradingAgreement agreement2) {
        this.agreement2 = agreement2;
    }

}
