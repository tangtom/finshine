//Source file: D:\\workspace\\finshine\\finshine_server\\src\\main\\java\\com\\incito\\finshine\\crms\\mci\\domain\\AssetInfo.java

package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

public class AssetInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 按照理财目标类别标记
	 */
	private String goal;

	/**
	 * 现金
	 */
	private long cash;
	private float cashReturn;

	/**
	 * 活期存款
	 */
	private long demanDeposit;
	private float demanDepositReturn;

	/**
	 * 定期存款
	 */
	private long fixDeposit;
	private float fixDepositReturn;

	/**
	 * 股票
	 */
	private long stock;
	private float stockReturn;

	/**
	 * 基金
	 */
	private long fund;
	private float fundReturn;

	/**
	 * 债券
	 */
	private long bond;
	private float bondReturn;

	/**
	 * 房产
	 */
	private long estate;
	private float estateReturn;

	/**
	 * 其他资产
	 */
	private long otherAsset;
	private float otherAssetReturn;

	/**
	 * 资产总计
	 */
	private long totalAsset;

	/**
	 * 房屋公积金贷款
	 */
	private long houseFundLoan;

	/**
	 * 房屋商业贷款
	 */
	private long houseBusinessLoan;

	/**
	 * 汽车贷款
	 */
	private long carLoan;

	/**
	 * 消费贷款
	 */
	private long consumerLoan;

	/**
	 * 装修贷款
	 */
	private long renovationLoan;

	/**
	 * 民间贷款
	 */
	private long privateLoan;

	/**
	 * 信用卡未付款
	 */
	private long unpaidCreditCard;

	/**
	 * 其他负债
	 */
	private long otherLoan;

	/**
	 * 负债总计
	 */
	private long totalLoan;

	/**
	 * 净资产
	 */
	private long netAsset;
	private long id;
	private long customerId;

	public String getGoal() {
		if (StrUtil.isBlank(goal)) {
			return "";
		}
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public long getCash() {
		return cash;
	}

	public void setCash(long cash) {
		this.cash = cash;
	}

	public long getDemanDeposit() {
		return demanDeposit;
	}

	public void setDemanDeposit(long demanDeposit) {
		this.demanDeposit = demanDeposit;
	}

	public long getFixDeposit() {
		return fixDeposit;
	}

	public void setFixDeposit(long fixDeposit) {
		this.fixDeposit = fixDeposit;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public long getFund() {
		return fund;
	}

	public void setFund(long fund) {
		this.fund = fund;
	}

	public long getBond() {
		return bond;
	}

	public void setBond(long bond) {
		this.bond = bond;
	}

	public long getEstate() {
		return estate;
	}

	public void setEstate(long estate) {
		this.estate = estate;
	}

	public long getOtherAsset() {
		return otherAsset;
	}

	public void setOtherAsset(long otherAsset) {
		this.otherAsset = otherAsset;
	}

	public long getTotalAsset() {
		return totalAsset;
	}

	public void setTotalAsset(long totalAsset) {
		this.totalAsset = totalAsset;
	}

	public long getHouseFundLoan() {
		return houseFundLoan;
	}

	public void setHouseFundLoan(long houseFundLoan) {
		this.houseFundLoan = houseFundLoan;
	}

	public long getHouseBusinessLoan() {
		return houseBusinessLoan;
	}

	public void setHouseBusinessLoan(long houseBusinessLoan) {
		this.houseBusinessLoan = houseBusinessLoan;
	}

	public long getCarLoan() {
		return carLoan;
	}

	public void setCarLoan(long carLoan) {
		this.carLoan = carLoan;
	}

	public long getConsumerLoan() {
		return consumerLoan;
	}

	public void setConsumerLoan(long consumerLoan) {
		this.consumerLoan = consumerLoan;
	}

	public long getRenovationLoan() {
		return renovationLoan;
	}

	public void setRenovationLoan(long renovationLoan) {
		this.renovationLoan = renovationLoan;
	}

	public long getPrivateLoan() {
		return privateLoan;
	}

	public void setPrivateLoan(long privateLoan) {
		this.privateLoan = privateLoan;
	}

	public long getUnpaidCreditCard() {
		return unpaidCreditCard;
	}

	public void setUnpaidCreditCard(long unpaidCreditCard) {
		this.unpaidCreditCard = unpaidCreditCard;
	}

	public long getOtherLoan() {
		return otherLoan;
	}

	public void setOtherLoan(long otherLoan) {
		this.otherLoan = otherLoan;
	}

	public long getTotalLoan() {
		return totalLoan;
	}

	public void setTotalLoan(long totalLoan) {
		this.totalLoan = totalLoan;
	}

	public long getNetAsset() {
		return netAsset;
	}

	public void setNetAsset(long netAsset) {
		this.netAsset = netAsset;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public float getCashReturn() {
		return cashReturn;
	}

	public void setCashReturn(float cashReturn) {
		this.cashReturn = cashReturn;
	}

	public float getDemanDepositReturn() {
		return demanDepositReturn;
	}

	public void setDemanDepositReturn(float demanDepositReturn) {
		this.demanDepositReturn = demanDepositReturn;
	}

	public float getFixDepositReturn() {
		return fixDepositReturn;
	}

	public void setFixDepositReturn(float fixDepositReturn) {
		this.fixDepositReturn = fixDepositReturn;
	}

	public float getStockReturn() {
		return stockReturn;
	}

	public void setStockReturn(float stockReturn) {
		this.stockReturn = stockReturn;
	}

	public float getFundReturn() {
		return fundReturn;
	}

	public void setFundReturn(float fundReturn) {
		this.fundReturn = fundReturn;
	}

	public float getBondReturn() {
		return bondReturn;
	}

	public void setBondReturn(float bondReturn) {
		this.bondReturn = bondReturn;
	}

	public float getEstateReturn() {
		return estateReturn;
	}

	public void setEstateReturn(float estateReturn) {
		this.estateReturn = estateReturn;
	}

	public float getOtherAssetReturn() {
		return otherAssetReturn;
	}

	public void setOtherAssetReturn(float otherAssetReturn) {
		this.otherAssetReturn = otherAssetReturn;
	}
}
