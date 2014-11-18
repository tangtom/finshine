package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

/**
 * 银行
 * 
 */
public class Bank implements Serializable {

	private static final long serialVersionUID = -6064590960102945421L;

	/*
	 * 银行主键编号
	 */
	private long id;

	/*
	 * 银行名称
	 */
	private String name;

	/*
	 * 银行名称
	 */
	private String bankName;

	/*
	 * 银行代码
	 */
	private String bankCode;

	/*
	 * 状态
	 */
	private String status;

	/*
	 * 创建日期
	 */
	private long created;

	/*
	 * 修改日期
	 */
	private long lastmod;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		if (StrUtil.isBlank(name)) {
			return "";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankName() {
		if (StrUtil.isBlank(bankName)) {
			return "";
		}
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		if (StrUtil.isBlank(bankCode)) {
			return "";
		}
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public long getLastmod() {
		return lastmod;
	}

	public void setLastmod(long lastmod) {
		this.lastmod = lastmod;
	}

}
