package com.incito.finshine.entity;

import java.util.Date;

import com.android.core.util.StrUtil;

public class ProdCollection {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column prod_collection.ID
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    private Long id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column prod_collection.STATUS
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    private String status;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column prod_collection.CREATED
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    private Date created;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column prod_collection.LASTMOD
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    private Date lastmod;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column prod_collection.VERSION
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    private Long version;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column prod_collection.SALES_ID
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    private Long salesId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column prod_collection.PROD_ID
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    private Long prodId;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column prod_collection.ID
     *
     * @return the value of prod_collection.ID
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column prod_collection.ID
     *
     * @param id the value for prod_collection.ID
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column prod_collection.STATUS
     *
     * @return the value of prod_collection.STATUS
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    public String getStatus() {
		if (StrUtil.isBlank(status)) {
			return "";
		}
        return status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column prod_collection.STATUS
     *
     * @param status the value for prod_collection.STATUS
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column prod_collection.CREATED
     *
     * @return the value of prod_collection.CREATED
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    public Date getCreated() {
        return created;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column prod_collection.CREATED
     *
     * @param created the value for prod_collection.CREATED
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column prod_collection.LASTMOD
     *
     * @return the value of prod_collection.LASTMOD
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    public Date getLastmod() {
        return lastmod;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column prod_collection.LASTMOD
     *
     * @param lastmod the value for prod_collection.LASTMOD
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    public void setLastmod(Date lastmod) {
        this.lastmod = lastmod;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column prod_collection.VERSION
     *
     * @return the value of prod_collection.VERSION
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    public Long getVersion() {
        return version;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column prod_collection.VERSION
     *
     * @param version the value for prod_collection.VERSION
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column prod_collection.SALES_ID
     *
     * @return the value of prod_collection.SALES_ID
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    public Long getSalesId() {
        return salesId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column prod_collection.SALES_ID
     *
     * @param salesId the value for prod_collection.SALES_ID
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    public void setSalesId(Long salesId) {
        this.salesId = salesId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column prod_collection.PROD_ID
     *
     * @return the value of prod_collection.PROD_ID
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    public Long getProdId() {
        return prodId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column prod_collection.PROD_ID
     *
     * @param prodId the value for prod_collection.PROD_ID
     *
     * @ibatorgenerated Mon May 12 10:56:31 CST 2014
     */
    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }
}