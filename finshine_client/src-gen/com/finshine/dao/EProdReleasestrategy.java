package com.finshine.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table EPROD_RELEASESTRATEGY.
 */
public class EProdReleasestrategy {

    private Long id;
    private Long prodReleasestrategyId;
    private String status;
    private java.util.Date created;
    private java.util.Date lastmod;
    private Long version;
    private Long salesIds;
    private Long prodId;

    public EProdReleasestrategy() {
    }

    public EProdReleasestrategy(Long id) {
        this.id = id;
    }

    public EProdReleasestrategy(Long id, Long prodReleasestrategyId, String status, java.util.Date created, java.util.Date lastmod, Long version, Long salesIds, Long prodId) {
        this.id = id;
        this.prodReleasestrategyId = prodReleasestrategyId;
        this.status = status;
        this.created = created;
        this.lastmod = lastmod;
        this.version = version;
        this.salesIds = salesIds;
        this.prodId = prodId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdReleasestrategyId() {
        return prodReleasestrategyId;
    }

    public void setProdReleasestrategyId(Long prodReleasestrategyId) {
        this.prodReleasestrategyId = prodReleasestrategyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public java.util.Date getCreated() {
        return created;
    }

    public void setCreated(java.util.Date created) {
        this.created = created;
    }

    public java.util.Date getLastmod() {
        return lastmod;
    }

    public void setLastmod(java.util.Date lastmod) {
        this.lastmod = lastmod;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getSalesIds() {
        return salesIds;
    }

    public void setSalesIds(Long salesIds) {
        this.salesIds = salesIds;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

}
