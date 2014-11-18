package com.incito.finshine.entity.spinner;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

/**
 * 
 * 受理类型主数据model
 * 
 * @author xiaoming
 * 
 */
public class AcceptanceType implements Serializable {

	private static final long serialVersionUID = 2160236735105960677L;

	private int id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 创建时间
	 */
	private long dateOfCreate;

	/**
	 * 更新时间
	 */
	private long dateOfUpdate;

	/**
	 * 状态
	 */
	private short isValid = 1;

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

	public short getIsValid() {
		return isValid;
	}

	public void setIsValid(short isValid) {
		this.isValid = isValid;
	}

}
