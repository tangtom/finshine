package com.incito.finshine.entity;

import java.util.Date;

import com.android.core.util.StrUtil;

public class IDType {
	private int id ;
	private String name ;
	private String status ;
	private long dateOfCreate ;
	private long dateOfModified ;	
	
	public IDType(int id) {
		super();
		this.id = id;
	}
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
	public long getDateOfModified() {
		return dateOfModified;
	}
	public void setDateOfModified(long dateOfModified) {
		this.dateOfModified = dateOfModified;
	}
	
	
}
