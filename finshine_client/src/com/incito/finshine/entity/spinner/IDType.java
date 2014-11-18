package com.incito.finshine.entity.spinner;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

/**
 * 
 * 证件类型主数据model
 * 
 * @author xiaoming
 * 
 */
public class IDType implements Serializable {

	private static final long serialVersionUID = -6895744291238208766L;

	private int id;

	/**
	 * 证件名称
	 */
	private String name;

	/**
	 * 证件名称
	 */
	private String idTypeName;

	/**
	 * 状态
	 */
	private String status = "A";

	/**
	 * 创建时间
	 */
	private long dateOfCreate;

	/**
	 * 更新时间
	 */
	private long dateOfUpdate;

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

	public String getIdTypeName() {
		if (StrUtil.isBlank(idTypeName)) {
			return "";
		}
		return idTypeName;
	}

	public void setIdTypeName(String idTypeName) {
		this.idTypeName = idTypeName;
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

}
