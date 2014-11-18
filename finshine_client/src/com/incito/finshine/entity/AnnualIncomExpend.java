//Source file: D:\\workspace\\finshine\\finshine_server\\src\\main\\java\\com\\incito\\finshine\\crms\\mci\\domain\\AnnualIncomExpend.java

package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

/**
 * 投资人年度收支
 */
public class AnnualIncomExpend implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private long customerId;

	/**
	 * 本人收入
	 */
	private long income = 0;

	/**
	 * 配偶收入
	 */
	private long spouseIncome = 0;

	/**
	 * 年终奖金
	 */
	private long endBonus = 0;

	/**
	 * 房租收入
	 */
	private long rental = 0;

	/**
	 * 存款债券利息
	 */
	private long interest = 0;

	/**
	 * 股利股息
	 */
	private long dividends = 0;

	/**
	 * 各类分红
	 */
	private long otherDividends = 0;

	/**
	 * 其他固定收入
	 */
	private long otherFixIncome = 0;

	/**
	 * 其他不固定收入
	 */
	private long otherUnfixIncome = 0;

	/**
	 * 收入合计
	 */
	private long totalIncome = 0;

	/**
	 * 家庭生活开销
	 */
	private long livingCost = 0;

	/**
	 * 房屋支出
	 */
	private long housExpense = 0;

	/**
	 * 医疗费
	 */
	private long medicExpense = 0;

	/**
	 * 子女教育费
	 */
	private long eduExpense = 0;

	/**
	 * 保险费
	 */
	private long insurance = 0;

	/**
	 * 旅游费
	 */
	private long travel = 0;

	/**
	 * 赡养费
	 */
	private long alimony = 0;

	/**
	 * 其他固定支出
	 */
	private long otherFixExpense = 0;

	/**
	 * 其他不固定支出
	 */
	private long otherUnfixExpense = 0;

	/**
	 * 支出合计
	 */
	private long totalExpenditure = 0;

	/**
	 * 每年结余
	 */
	private long annualBalance = 0;

	/**
	 * 历史投资经验 txt文本描述
	 */
	private String historyExperience = null;

	public long getIncome() {
		return income;
	}

	public void setIncome(long income) {
		this.income = income;
	}

	public long getSpouseIncome() {
		return spouseIncome;
	}

	public void setSpouseIncome(long spouseIncome) {
		this.spouseIncome = spouseIncome;
	}

	public long getEndBonus() {
		return endBonus;
	}

	public void setEndBonus(long endBonus) {
		this.endBonus = endBonus;
	}

	public long getRental() {
		return rental;
	}

	public void setRental(long rental) {
		this.rental = rental;
	}

	public long getInterest() {
		return interest;
	}

	public void setInterest(long interest) {
		this.interest = interest;
	}

	public long getDividends() {
		return dividends;
	}

	public void setDividends(long dividends) {
		this.dividends = dividends;
	}

	public long getOtherDividends() {
		return otherDividends;
	}

	public void setOtherDividends(long otherDividends) {
		this.otherDividends = otherDividends;
	}

	public long getOtherFixIncome() {
		return otherFixIncome;
	}

	public void setOtherFixIncome(long otherFixIncome) {
		this.otherFixIncome = otherFixIncome;
	}

	public long getOtherUnfixIncome() {
		return otherUnfixIncome;
	}

	public void setOtherUnfixIncome(long otherUnfixIncome) {
		this.otherUnfixIncome = otherUnfixIncome;
	}

	public long getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(long totalIncome) {
		this.totalIncome = totalIncome;
	}

	public long getLivingCost() {
		return livingCost;
	}

	public void setLivingCost(long livingCost) {
		this.livingCost = livingCost;
	}

	public long getHousExpense() {
		return housExpense;
	}

	public void setHousExpense(long housExpense) {
		this.housExpense = housExpense;
	}

	public long getMedicExpense() {
		return medicExpense;
	}

	public void setMedicExpense(long medicExpense) {
		this.medicExpense = medicExpense;
	}

	public long getEduExpense() {
		return eduExpense;
	}

	public void setEduExpense(long eduExpense) {
		this.eduExpense = eduExpense;
	}

	public long getInsurance() {
		return insurance;
	}

	public void setInsurance(long insurance) {
		this.insurance = insurance;
	}

	public long getTravel() {
		return travel;
	}

	public void setTravel(long travel) {
		this.travel = travel;
	}

	public long getAlimony() {
		return alimony;
	}

	public void setAlimony(long alimony) {
		this.alimony = alimony;
	}

	public long getOtherFixExpense() {
		return otherFixExpense;
	}

	public void setOtherFixExpense(long otherFixExpense) {
		this.otherFixExpense = otherFixExpense;
	}

	public long getOtherUnfixExpense() {
		return otherUnfixExpense;
	}

	public void setOtherUnfixExpense(long otherUnfixExpense) {
		this.otherUnfixExpense = otherUnfixExpense;
	}

	public long getTotalExpenditure() {
		return totalExpenditure;
	}

	public void setTotalExpenditure(long totalExpenditure) {
		this.totalExpenditure = totalExpenditure;
	}

	public long getAnnualBalance() {
		return annualBalance;
	}

	public void setAnnualBalance(long annualBalance) {
		this.annualBalance = annualBalance;
	}

	public String getHistoryExperience() {
		if (StrUtil.isBlank(historyExperience)) {
			return "";
		}
		return historyExperience;
	}

	public void setHistoryExperience(String historyExperience) {
		this.historyExperience = historyExperience;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
