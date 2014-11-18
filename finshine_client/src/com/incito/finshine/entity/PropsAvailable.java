package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.List;

public class PropsAvailable implements Serializable{
	private int user_fk;//用户ID
	private Product product;//产品
	private double fixedCommissionRatio;//产品固定佣金比例
	private double extraCommissionRatio;//产品奖励佣金比例
	private double totalCommissionRatio;//产品累计佣金比例
	private List<PropsUsedItemWSEntity> items;
	public List<PropsUsedItemWSEntity> getItems() {
		return items;
	}
	public void setItems(List<PropsUsedItemWSEntity> items) {
		this.items = items;
	}
	public int getUser_fk() {
		return user_fk;
	}
	public void setUser_fk(int user_fk) {
		this.user_fk = user_fk;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public double getFixedCommissionRatio() {
		return fixedCommissionRatio;
	}
	public void setFixedCommissionRatio(double fixedCommissionRatio) {
		this.fixedCommissionRatio = fixedCommissionRatio;
	}
	public double getExtraCommissionRatio() {
		return extraCommissionRatio;
	}
	public void setExtraCommissionRatio(double extraCommissionRatio) {
		this.extraCommissionRatio = extraCommissionRatio;
	}
	public double getTotalCommissionRatio() {
		return totalCommissionRatio;
	}
	public void setTotalCommissionRatio(double totalCommissionRatio) {
		this.totalCommissionRatio = totalCommissionRatio;
	}

}
