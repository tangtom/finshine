package com.incito.finshine.entity;

import java.util.Date;

import com.android.core.util.StrUtil;

public class ProdAppointment {
  
    private Long id;

    private String status;

  
    private Date created;

  
    private Date lastmod;

  
    private Long version;

    private Long salesId;

  
    private Long prodId;

    private Integer prodQty;

    private Float prodAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   
    public String getStatus() {
		if (StrUtil.isBlank(status)) {
			return "";
		}
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    
    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastmod() {
        return lastmod;
    }

    
    public void setLastmod(Date lastmod) {
        this.lastmod = lastmod;
    }

   
    public Long getVersion() {
        return version;
    }

    
    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getSalesId() {
        return salesId;
    }

   
    public void setSalesId(Long salesId) {
        this.salesId = salesId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column prod_appointment.PROD_ID
     *
     * @return the value of prod_appointment.PROD_ID
     *
     * @ibatorgenerated Sat May 10 23:38:53 CST 2014
     */
    public Long getProdId() {
        return prodId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column prod_appointment.PROD_ID
     *
     * @param prodId the value for prod_appointment.PROD_ID
     *
     * @ibatorgenerated Sat May 10 23:38:53 CST 2014
     */
    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column prod_appointment.PROD_QTY
     *
     * @return the value of prod_appointment.PROD_QTY
     *
     * @ibatorgenerated Sat May 10 23:38:53 CST 2014
     */
    public Integer getProdQty() {
        return prodQty;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column prod_appointment.PROD_QTY
     *
     * @param prodQty the value for prod_appointment.PROD_QTY
     *
     * @ibatorgenerated Sat May 10 23:38:53 CST 2014
     */
    public void setProdQty(Integer prodQty) {
        this.prodQty = prodQty;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column prod_appointment.PROD_AMOUNT
     *
     * @return the value of prod_appointment.PROD_AMOUNT
     *
     * @ibatorgenerated Sat May 10 23:38:53 CST 2014
     */
    public Float getProdAmount() {
        return prodAmount;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column prod_appointment.PROD_AMOUNT
     *
     * @param prodAmount the value for prod_appointment.PROD_AMOUNT
     *
     * @ibatorgenerated Sat May 10 23:38:53 CST 2014
     */
    public void setProdAmount(Float prodAmount) {
        this.prodAmount = prodAmount;
    }
}