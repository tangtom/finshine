package com.incito.finshine.entity;

import java.io.Serializable;

public class PropsUsedItemWSEntity implements Serializable{
	private long master_fk;//使用道具主键ID
	private Props props;//道具对象
	private double propertyValue;//属性值
	private int maxQty;//最大使用道具数量
	private int qtyOfUsed;//已经使用道具数量
	private int qtyOfRemaining;//个人剩余道具数量
	private double subtotalRatio;//道具使用佣金比例(佣金比例*使用数量)
	public long getMaster_fk() {
		return master_fk;
	}
	public void setMaster_fk(long master_fk) {
		this.master_fk = master_fk;
	}
	public Props getProps() {
		return props;
	}
	public void setProps(Props props) {
		this.props = props;
	}
	public double getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(double propertyValue) {
		this.propertyValue = propertyValue;
	}
	public int getMaxQty() {
		return maxQty;
	}
	public void setMaxQty(int maxQty) {
		this.maxQty = maxQty;
	}
	public int getQtyOfUsed() {
		return qtyOfUsed;
	}
	public void setQtyOfUsed(int qtyOfUsed) {
		this.qtyOfUsed = qtyOfUsed;
	}
	public int getQtyOfRemaining() {
		return qtyOfRemaining;
	}
	public void setQtyOfRemaining(int qtyOfRemaining) {
		this.qtyOfRemaining = qtyOfRemaining;
	}
	public double getSubtotalRatio() {
		return subtotalRatio;
	}
	public void setSubtotalRatio(double subtotalRatio) {
		this.subtotalRatio = subtotalRatio;
	}
	
}
