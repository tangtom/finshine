package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

public class SalesAppProd implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1922225091275251229L;
	private Long id; // Id
	private Long salesId; // 理财师id
	private String salesXy; // 理财师信誉
	private String salesTopsale; // 理财师销售额
	private String salesOrderamount; // 理财师开单量
	private String salesPhone; // 理财师电话
	private Float prodAmount; // 预定额

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSalesId() {
		return salesId;
	}

	public void setSalesId(Long salesId) {
		this.salesId = salesId;
	}

	public String getSalesXy() {
		if (StrUtil.isBlank(salesXy)) {
			return "";
		}
		return salesXy;
	}

	public void setSalesXy(String salesXy) {
		this.salesXy = salesXy;
	}

	public String getSalesTopsale() {
		if (StrUtil.isBlank(salesTopsale)) {
			return "";
		}
		return salesTopsale;
	}

	public void setSalesTopsale(String salesTopsale) {
		this.salesTopsale = salesTopsale;
	}

	public String getSalesOrderamount() {
		if (StrUtil.isBlank(salesOrderamount)) {
			return "";
		}
		return salesOrderamount;
	}

	public void setSalesOrderamount(String salesOrderamount) {
		this.salesOrderamount = salesOrderamount;
	}

	public String getSalesPhone() {
		if (StrUtil.isBlank(salesPhone)) {
			return "";
		}
		return salesPhone;
	}

	public void setSalesPhone(String salesPhone) {
		this.salesPhone = salesPhone;
	}

	public Float getProdAmount() {
		return prodAmount;
	}

	public void setProdAmount(Float prodAmount) {
		this.prodAmount = prodAmount;
	}
}
