package com.incito.finshine.entity;

import java.io.Serializable;

public class CommissionStatistics implements Serializable{
	private long user_fk;//用户ID
	private double totalFixedCommission;//累计基础佣金
	private double totalExtraCommission;//累计奖励佣金
	private int rankingOfFixedCommission;//个人基础佣金排行
	private int rankingOfExtraCommission;//个人奖励佣金排行
	private double commissionInProcessing;//最高基础佣金额度
	private double totalPoint;//我的积分
	private double topFixedCommission;//最高基础佣金额度
	private double topExtraCommission;//最高奖励佣金额度
	private double totalCommissionRatio;//个人账户总佣金比例
	public long getUser_fk() {
		return user_fk;
	}
	public void setUser_fk(long user_fk) {
		this.user_fk = user_fk;
	}
	public double getTotalFixedCommission() {
		return totalFixedCommission;
	}
	public void setTotalFixedCommission(double totalFixedCommission) {
		this.totalFixedCommission = totalFixedCommission;
	}
	public double getTotalExtraCommission() {
		return totalExtraCommission;
	}
	public void setTotalExtraCommission(double totalExtraCommission) {
		this.totalExtraCommission = totalExtraCommission;
	}
	public int getRankingOfFixedCommission() {
		return rankingOfFixedCommission;
	}
	public void setRankingOfFixedCommission(int rankingOfFixedCommission) {
		this.rankingOfFixedCommission = rankingOfFixedCommission;
	}
	public int getRankingOfExtraCommission() {
		return rankingOfExtraCommission;
	}
	public void setRankingOfExtraCommission(int rankingOfExtraCommission) {
		this.rankingOfExtraCommission = rankingOfExtraCommission;
	}
	public double getCommissionInProcessing() {
		return commissionInProcessing;
	}
	public void setCommissionInProcessing(double commissionInProcessing) {
		this.commissionInProcessing = commissionInProcessing;
	}
	public double getTotalPoint() {
		return totalPoint;
	}
	public void setTotalPoint(double totalPoint) {
		this.totalPoint = totalPoint;
	}
	public double getTopFixedCommission() {
		return topFixedCommission;
	}
	public void setTopFixedCommission(double topFixedCommission) {
		this.topFixedCommission = topFixedCommission;
	}
	public double getTopExtraCommission() {
		return topExtraCommission;
	}
	public void setTopExtraCommission(double topExtraCommission) {
		this.topExtraCommission = topExtraCommission;
	}
	public double getTotalCommissionRatio() {
		return totalCommissionRatio;
	}
	public void setTotalCommissionRatio(double totalCommissionRatio) {
		this.totalCommissionRatio = totalCommissionRatio;
	}
}
