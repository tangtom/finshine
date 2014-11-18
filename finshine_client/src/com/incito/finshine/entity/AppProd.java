package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

public class AppProd implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1329741435411597988L;
	private Long id;
	private String prodName;
	private String prodMyid;
	private String prodAdmin;
	private Long userId;
	private Date created;
	private Float prodAmount;
	private Long prodStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProdName() {
		if (StrUtil.isBlank(prodName)) {
			prodName = "";
		}
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdMyid() {
		if (StrUtil.isBlank(prodMyid)) {
			return "";
		}
		return prodMyid;
	}

	public void setProdMyid(String prodMyid) {
		this.prodMyid = prodMyid;
	}

	public String getProdAdmin() {
		if (StrUtil.isBlank(prodAdmin)) {
			return "";
		}
		return prodAdmin;
	}

	public void setProdAdmin(String prodAdmin) {
		this.prodAdmin = prodAdmin;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Float getProdAmount() {
		return prodAmount;
	}

	public void setProdAmount(Float prodAmount) {
		this.prodAmount = prodAmount;
	}

	public Long getProdStatus() {
		return prodStatus;
	}

	public void setProdStatus(Long prodStatus) {
		this.prodStatus = prodStatus;
	}

}
