package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

/**
 * 道具状态
 * 
 * @author xiaoming
 * @since JDK 1.7
 * 
 */
public class PropsStatus implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -569618571454175944L;

	/**
	 * ID号
	 */
	private int id;

	/**
	 * 状态名称
	 */
	private String name;

	/**
	 * 是否为启用的状态
	 */
	private short isEnabled;

	/**
	 * 是否为未启用的状态
	 */
	private short isDisabled;

	/**
	 * 创建时间
	 */
	private long dateOfCreate;

	/**
	 * 更新时间
	 */
	private long dateOfUpdate;

	/**
	 * 是否数据有效
	 */
	private short isValid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public short getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(short isEnabled) {
		this.isEnabled = isEnabled;
	}

	public short getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(short isDisabled) {
		this.isDisabled = isDisabled;
	}

	/*
	 * public Date getDateOfCreate() { return dateOfCreate; }
	 * 
	 * public void setDateOfCreate(Date dateOfCreate) { this.dateOfCreate =
	 * dateOfCreate; }
	 * 
	 * public Date getDateOfUpdate() { return dateOfUpdate; }
	 * 
	 * public void setDateOfUpdate(Date dateOfUpdate) { this.dateOfUpdate =
	 * dateOfUpdate; }
	 */

	public short getIsValid() {
		return isValid;
	}

	public long getDateOfCreate() {
		return dateOfCreate;
	}

	public void setDateOfCreate(long dateOfCreate) {
		this.dateOfCreate = dateOfCreate;
	}

	public long getDateOfUpdate() {
		return dateOfUpdate;
	}

	public void setDateOfUpdate(long dateOfUpdate) {
		this.dateOfUpdate = dateOfUpdate;
	}

	public void setIsValid(short isValid) {
		this.isValid = isValid;
	}

}
