package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

/**
 * 道具属性
 * 
 * @author xiaoming
 * @since JDK 1.7
 * 
 */
public class PropsProperty implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1995741967417956757L;

	/**
	 * ID号
	 */
	private int id;

	/**
	 * 属性名称
	 */
	private String name;

	/**
	 * 属性描述
	 */
	private String memo;

	/**
	 * 单位
	 */
	private String measureUnit;

	/**
	 * 创建时间
	 */
	private long dateOfCreate;

	/**
	 * 更新时间
	 */
	private long dateOfUpdate;

	/**
	 * 所属类型
	 */
	private int propsType_fk;

	/**
	 * /** 是否数据有效
	 */
	private short isValid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		if (StrUtil.isBlank(memo)) {
			return "";
		}
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMeasureUnit() {
		if (StrUtil.isBlank(measureUnit)) {
			return "";
		}
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
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

	public int getPropsType_fk() {
		return propsType_fk;
	}

	public void setPropsType_fk(int propsType_fk) {
		this.propsType_fk = propsType_fk;
	}

}
