package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

/**
 * <dl>
 * <dt>ExpireCustomer.java</dt>
 * <dd>Description:到期客户列表</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-17 上午9:59:25</dd>
 * </dl>
 * 
 * @author JOHN
 */
public class ExpireCustomer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4844300104636515679L;

	private long id;// 客户id

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSalesId() {
		return salesId;
	}

	public void setSalesId(long salesId) {
		this.salesId = salesId;
	}

	public String getEmail1() {
		if (StrUtil.isBlank(email1)) {
			return "";
		}
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getCellPhone1() {
		if (StrUtil.isBlank(cellPhone1)) {
			return "";
		}
		return cellPhone1;
	}

	public void setCellPhone1(String cellPhone1) {
		this.cellPhone1 = cellPhone1;
	}

	public long getExpireProuctQty() {
		return expireProuctQty;
	}

	public void setExpireProuctQty(long expireProuctQty) {
		this.expireProuctQty = expireProuctQty;
	}

	public double getExpireOrderTotalAmount() {
		return expireOrderTotalAmount;
	}

	public void setExpireOrderTotalAmount(double expireOrderTotalAmount) {
		this.expireOrderTotalAmount = expireOrderTotalAmount;
	}

	private String name;

	public String getName() {
		if (StrUtil.isBlank(name)) {
			return "";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private long salesId;
	private String email1;
	private String cellPhone1;
	private long expireProuctQty;
	private double expireOrderTotalAmount;

	// id: 1
	// name: "李四123"
	// salesId: 1
	// email1: "mail@example.com"
	// cellPhone1: "1801265702356"
	// expireProuctQty: 2
	// expireOrderTotalAmount: 1700

}
