package com.finshine.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table EPRODUCT_SEARCH.
 */
public class EProductSearch implements java.io.Serializable {

    private Long id;
    private String searchProdName;
    private String searchProdOnDateTime;
    private String searchProdEnDateTime;
    private String searchProdFirstType;
    private String searchProdSecondtype;
    private String searchProdStatus;
    private String searchProdStartLow;
    private String searchProdStartTop;
    private String searchFFType;
    private String searchFFTop;
    private String searchFFLow;
    private String searchProdTimeStart;
    private String searchProdTimeEnd;
    private String searchProdPreference;
    private Long salesId;
    private String searchProdId;
    private String searchSalesLike;
    private String searchProdInvest;
    private String searchOverDue;
    private String searchProdTop;
    private String sortDesc;
    private String sortProdOnDateTime;
    private String sortProdYieldFixed;
    private String sortProdCommissionBase;
    private String sortProdTime;
    private String sortProdSize;
    private String sortHot;
    private Integer pageNow;
    private Integer pageStart;
    private Integer pageSize;

    public EProductSearch() {
    }

    public EProductSearch(Long id) {
        this.id = id;
    }

    public EProductSearch(Long id, String searchProdName, String searchProdOnDateTime, String searchProdEnDateTime, String searchProdFirstType, String searchProdSecondtype, String searchProdStatus, String searchProdStartLow, String searchProdStartTop, String searchFFType, String searchFFTop, String searchFFLow, String searchProdTimeStart, String searchProdTimeEnd, String searchProdPreference, Long salesId, String searchProdId, String searchSalesLike, String searchProdInvest, String searchOverDue, String searchProdTop, String sortDesc, String sortProdOnDateTime, String sortProdYieldFixed, String sortProdCommissionBase, String sortProdTime, String sortProdSize, String sortHot, Integer pageNow, Integer pageStart, Integer pageSize) {
        this.id = id;
        this.searchProdName = searchProdName;
        this.searchProdOnDateTime = searchProdOnDateTime;
        this.searchProdEnDateTime = searchProdEnDateTime;
        this.searchProdFirstType = searchProdFirstType;
        this.searchProdSecondtype = searchProdSecondtype;
        this.searchProdStatus = searchProdStatus;
        this.searchProdStartLow = searchProdStartLow;
        this.searchProdStartTop = searchProdStartTop;
        this.searchFFType = searchFFType;
        this.searchFFTop = searchFFTop;
        this.searchFFLow = searchFFLow;
        this.searchProdTimeStart = searchProdTimeStart;
        this.searchProdTimeEnd = searchProdTimeEnd;
        this.searchProdPreference = searchProdPreference;
        this.salesId = salesId;
        this.searchProdId = searchProdId;
        this.searchSalesLike = searchSalesLike;
        this.searchProdInvest = searchProdInvest;
        this.searchOverDue = searchOverDue;
        this.searchProdTop = searchProdTop;
        this.sortDesc = sortDesc;
        this.sortProdOnDateTime = sortProdOnDateTime;
        this.sortProdYieldFixed = sortProdYieldFixed;
        this.sortProdCommissionBase = sortProdCommissionBase;
        this.sortProdTime = sortProdTime;
        this.sortProdSize = sortProdSize;
        this.sortHot = sortHot;
        this.pageNow = pageNow;
        this.pageStart = pageStart;
        this.pageSize = pageSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchProdName() {
        return searchProdName;
    }

    public void setSearchProdName(String searchProdName) {
        this.searchProdName = searchProdName;
    }

    public String getSearchProdOnDateTime() {
        return searchProdOnDateTime;
    }

    public void setSearchProdOnDateTime(String searchProdOnDateTime) {
        this.searchProdOnDateTime = searchProdOnDateTime;
    }

    public String getSearchProdEnDateTime() {
        return searchProdEnDateTime;
    }

    public void setSearchProdEnDateTime(String searchProdEnDateTime) {
        this.searchProdEnDateTime = searchProdEnDateTime;
    }

    public String getSearchProdFirstType() {
        return searchProdFirstType;
    }

    public void setSearchProdFirstType(String searchProdFirstType) {
        this.searchProdFirstType = searchProdFirstType;
    }

    public String getSearchProdSecondtype() {
        return searchProdSecondtype;
    }

    public void setSearchProdSecondtype(String searchProdSecondtype) {
        this.searchProdSecondtype = searchProdSecondtype;
    }

    public String getSearchProdStatus() {
        return searchProdStatus;
    }

    public void setSearchProdStatus(String searchProdStatus) {
        this.searchProdStatus = searchProdStatus;
    }

    public String getSearchProdStartLow() {
        return searchProdStartLow;
    }

    public void setSearchProdStartLow(String searchProdStartLow) {
        this.searchProdStartLow = searchProdStartLow;
    }

    public String getSearchProdStartTop() {
        return searchProdStartTop;
    }

    public void setSearchProdStartTop(String searchProdStartTop) {
        this.searchProdStartTop = searchProdStartTop;
    }

    public String getSearchFFType() {
        return searchFFType;
    }

    public void setSearchFFType(String searchFFType) {
        this.searchFFType = searchFFType;
    }

    public String getSearchFFTop() {
        return searchFFTop;
    }

    public void setSearchFFTop(String searchFFTop) {
        this.searchFFTop = searchFFTop;
    }

    public String getSearchFFLow() {
        return searchFFLow;
    }

    public void setSearchFFLow(String searchFFLow) {
        this.searchFFLow = searchFFLow;
    }

    public String getSearchProdTimeStart() {
        return searchProdTimeStart;
    }

    public void setSearchProdTimeStart(String searchProdTimeStart) {
        this.searchProdTimeStart = searchProdTimeStart;
    }

    public String getSearchProdTimeEnd() {
        return searchProdTimeEnd;
    }

    public void setSearchProdTimeEnd(String searchProdTimeEnd) {
        this.searchProdTimeEnd = searchProdTimeEnd;
    }

    public String getSearchProdPreference() {
        return searchProdPreference;
    }

    public void setSearchProdPreference(String searchProdPreference) {
        this.searchProdPreference = searchProdPreference;
    }

    public Long getSalesId() {
        return salesId;
    }

    public void setSalesId(Long salesId) {
        this.salesId = salesId;
    }

    public String getSearchProdId() {
        return searchProdId;
    }

    public void setSearchProdId(String searchProdId) {
        this.searchProdId = searchProdId;
    }

    public String getSearchSalesLike() {
        return searchSalesLike;
    }

    public void setSearchSalesLike(String searchSalesLike) {
        this.searchSalesLike = searchSalesLike;
    }

    public String getSearchProdInvest() {
        return searchProdInvest;
    }

    public void setSearchProdInvest(String searchProdInvest) {
        this.searchProdInvest = searchProdInvest;
    }

    public String getSearchOverDue() {
        return searchOverDue;
    }

    public void setSearchOverDue(String searchOverDue) {
        this.searchOverDue = searchOverDue;
    }

    public String getSearchProdTop() {
        return searchProdTop;
    }

    public void setSearchProdTop(String searchProdTop) {
        this.searchProdTop = searchProdTop;
    }

    public String getSortDesc() {
        return sortDesc;
    }

    public void setSortDesc(String sortDesc) {
        this.sortDesc = sortDesc;
    }

    public String getSortProdOnDateTime() {
        return sortProdOnDateTime;
    }

    public void setSortProdOnDateTime(String sortProdOnDateTime) {
        this.sortProdOnDateTime = sortProdOnDateTime;
    }

    public String getSortProdYieldFixed() {
        return sortProdYieldFixed;
    }

    public void setSortProdYieldFixed(String sortProdYieldFixed) {
        this.sortProdYieldFixed = sortProdYieldFixed;
    }

    public String getSortProdCommissionBase() {
        return sortProdCommissionBase;
    }

    public void setSortProdCommissionBase(String sortProdCommissionBase) {
        this.sortProdCommissionBase = sortProdCommissionBase;
    }

    public String getSortProdTime() {
        return sortProdTime;
    }

    public void setSortProdTime(String sortProdTime) {
        this.sortProdTime = sortProdTime;
    }

    public String getSortProdSize() {
        return sortProdSize;
    }

    public void setSortProdSize(String sortProdSize) {
        this.sortProdSize = sortProdSize;
    }

    public String getSortHot() {
        return sortHot;
    }

    public void setSortHot(String sortHot) {
        this.sortHot = sortHot;
    }

    public Integer getPageNow() {
        return pageNow;
    }

    public void setPageNow(Integer pageNow) {
        this.pageNow = pageNow;
    }

    public Integer getPageStart() {
        return pageStart;
    }

    public void setPageStart(Integer pageStart) {
        this.pageStart = pageStart;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}