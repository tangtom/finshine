package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

/**
 * 任务类型
 * 
 * @author xiaoming
 * @since JDK 1.7
 * 
 */
public class TaskType implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 839736128880824402L;

	/**
	 * 任务类型ID号
	 */
	private long id;

	/**
	 * 任务类型名称
	 */
	private String name;

	/**
	 * 任务类型描述
	 */
	private String memo;

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

	public String getMemo() {
		if (StrUtil.isBlank(memo)) {
			return "";
		}
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public long getDateOfCreate() {
		return dateOfCreate;
	}

	public void setDateOfCreate(long dateOfCreate) {
		this.dateOfCreate = dateOfCreate;
	}

	public long getLongOfUpdate() {
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
