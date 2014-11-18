package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

public class ProdInvestMent implements Serializable{

	private static final long serialVersionUID = -7387687905562686030L;
	//主键ID
	private Long id;
	//状态
	private String status;
	//创建日期
	private Date created;
	//最后修改日期
	private Date	lastMod;
	//版本号
	private Long version;
	//产品id
	private Long prodId;
	//客户id
	private String customIds;
	//理财师
	private Long salesId;
	
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
	
	public Date getLastMod() {
		return lastMod;
	}
	public void setLastMod(Date lastMod) {
		this.lastMod = lastMod;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
	public String getCustomIds() {
		if (StrUtil.isBlank(customIds)) {
			return "";
		}
		return customIds;
	}
	public void setCustomIds(String customIds) {
		this.customIds = customIds;
	}
	public Long getSalesId() {
		return salesId;
	}
	public void setSalesId(Long salesId) {
		this.salesId = salesId;
	}
	
	
	
}
