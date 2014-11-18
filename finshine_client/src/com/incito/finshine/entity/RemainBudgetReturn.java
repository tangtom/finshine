package com.incito.finshine.entity;


public class RemainBudgetReturn {
	/**
	 * 产品预约份额主键编号
	 * 
	 */
	private long prodAppointmentId;
	
	/**
	 * 产品名称
	 * 
	 */
    private String prodName;
    
	/**
	 * 发行商电话
	 * 
	 */
    private String issuerPhone;
    /**
	 * 预约原始份额
	 * 
	 */
    private double originalAmount;

    /**
	 * 预约剩余金额
	 * 
	 */
    private double remainAmount;

    /**
     * 信誉的星数
     */
    private int creditStarsQty;

    /**
     *检查状态
     */
    private String checkStatus;
    
	public long getProdAppointmentId() {
		return prodAppointmentId;
	}

	public void setProdAppointmentId(long prodAppointmentId) {
		this.prodAppointmentId = prodAppointmentId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getIssuerPhone() {
		return issuerPhone;
	}

	public void setIssuerPhone(String issuerPhone) {
		this.issuerPhone = issuerPhone;
	}

	public double getOriginalAmount() {
		return originalAmount;
	}

	public void setOriginalAmount(double originalAmount) {
		this.originalAmount = originalAmount;
	}

	public double getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(double remainAmount) {
		this.remainAmount = remainAmount;
	}

	public int getCreditStarsQty() {
		return creditStarsQty;
	}

	public void setCreditStarsQty(int creditStarsQty) {
		this.creditStarsQty = creditStarsQty;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

   
}