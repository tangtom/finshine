package com.incito.finshine.entity;

import java.util.Date;

import com.android.core.util.StrUtil;

public class ProdProfitResult {
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column prod_profit.ID
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	private Long id;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column prod_profit.STATUS
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	private String status;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column prod_profit.CREATED
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	private long created;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column prod_profit.LASTMOD
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	private long lastmod;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column prod_profit.VERSION
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	private Long version;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column prod_profit.PROD_ID
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	private Long prodId;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column prod_profit.PROFIT_DATE
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	private long profitDate;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column prod_profit.PROFIT
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	private Float profit;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column prod_profit.LAST_DATE
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	private long lastDate;

	/**
	 * 产品名称
	 * 
	 * 
	 * 
	 */
	private String prodName;

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column prod_profit.ID
	 * 
	 * @return the value of prod_profit.ID
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column prod_profit.ID
	 * 
	 * @param id
	 *            the value for prod_profit.ID
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column prod_profit.STATUS
	 * 
	 * @return the value of prod_profit.STATUS
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public String getStatus() {
		if (StrUtil.isBlank(status)) {
			return "";
		}
		return status;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column prod_profit.STATUS
	 * 
	 * @param status
	 *            the value for prod_profit.STATUS
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column prod_profit.CREATED
	 * 
	 * @return the value of prod_profit.CREATED
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public long getCreated() {
		return created;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column prod_profit.CREATED
	 * 
	 * @param created
	 *            the value for prod_profit.CREATED
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public void setCreated(long created) {
		this.created = created;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column prod_profit.LASTMOD
	 * 
	 * @return the value of prod_profit.LASTMOD
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public long getLastmod() {
		return lastmod;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column prod_profit.LASTMOD
	 * 
	 * @param lastmod
	 *            the value for prod_profit.LASTMOD
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public void setLastmod(long lastmod) {
		this.lastmod = lastmod;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column prod_profit.VERSION
	 * 
	 * @return the value of prod_profit.VERSION
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column prod_profit.VERSION
	 * 
	 * @param version
	 *            the value for prod_profit.VERSION
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column prod_profit.PROD_ID
	 * 
	 * @return the value of prod_profit.PROD_ID
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public Long getProdId() {
		return prodId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column prod_profit.PROD_ID
	 * 
	 * @param prodId
	 *            the value for prod_profit.PROD_ID
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column prod_profit.PROFIT_long
	 * 
	 * @return the value of prod_profit.PROFIT_DATE
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public long getProfitDate() {
		return profitDate;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column prod_profit.PROFIT_DATE
	 * 
	 * @param profitDate
	 *            the value for prod_profit.PROFIT_DATE
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public void setProfitDate(long profitDate) {
		this.profitDate = profitDate;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column prod_profit.PROFIT
	 * 
	 * @return the value of prod_profit.PROFIT
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public Float getProfit() {
		return profit;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column prod_profit.PROFIT
	 * 
	 * @param profit
	 *            the value for prod_profit.PROFIT
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public void setProfit(Float profit) {
		this.profit = profit;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column prod_profit.LAST_DATE
	 * 
	 * @return the value of prod_profit.LAST_DATE
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public long getLastDate() {
		return lastDate;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column prod_profit.LAST_DATE
	 * 
	 * @param lastDate
	 *            the value for prod_profit.LAST_DATE
	 * 
	 * @ibatorgenerated Thu Jun 05 23:49:58 CST 2014
	 */
	public void setLastDate(long lastDate) {
		this.lastDate = lastDate;
	}

	public String getProdName() {
		if (StrUtil.isBlank(prodName)) {
			return "";
		}
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

}