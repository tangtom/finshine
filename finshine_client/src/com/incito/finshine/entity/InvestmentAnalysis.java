package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 投资分析   分析图
 * 
 */
public class InvestmentAnalysis implements Serializable {

	private static final long serialVersionUID = -6064590960102945421L;

	/*
	 * 季度编号
	 */
	private int id;

	/*
	 * 原始起始日期
	 */
	private long zeroStartDate;

	/*
	 * 季度起始日期
	 */
	private long quarterStartDate;

	/*
	 * 季度结束日期
	 */
	private long quarterEndDate;

	/*
	 * 当前投入
	 */
	private double currentInvestment;

	/*
	 * 季度收益
	 */
	private double quarterReturn;

	/*
	 * 累计收益
	 */
	private double cumulativeGain;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getZeroStartDate() {
		return zeroStartDate;
	}

	public void setZeroStartDate(long zeroStartDate) {
		this.zeroStartDate = zeroStartDate;
	}

	public long getQuarterStartDate() {
		return quarterStartDate;
	}

	public void setQuarterStartDate(long quarterStartDate) {
		this.quarterStartDate = quarterStartDate;
	}

	public long getQuarterEndDate() {
		return quarterEndDate;
	}

	public void setQuarterEndDate(long quarterEndDate) {
		this.quarterEndDate = quarterEndDate;
	}

	public double getCurrentInvestment() {
		return currentInvestment;
	}

	public void setCurrentInvestment(double currentInvestment) {
		this.currentInvestment = currentInvestment;
	}

	public double getQuarterReturn() {
		return quarterReturn;
	}

	public void setQuarterReturn(double quarterReturn) {
		this.quarterReturn = quarterReturn;
	}

	public double getCumulativeGain() {
		return cumulativeGain;
	}

	public void setCumulativeGain(double cumulativeGain) {
		this.cumulativeGain = cumulativeGain;
	}

}
