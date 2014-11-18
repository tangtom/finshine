package com.incito.finshine.entity;

import java.util.Date;

/**
 * 预约详情 实体类
 * author SGDY
 * time 2014/8/20 14:28
 */
public class ProdAppointmentWSEntity {
	private long id;//预约ID
	private long salesId;//理财师ID
	private long prodId;//产品ID
	private String productName;//产品名称
	private float appointmentShare;//已预约份额
	private float salesShare;//已销售份额
	private float confirmingShare;//确认中的份额
	private float remainingShare;//剩余份额
	private int credit;//信誉
	private long dateOfAppointment;//预约时间
	private int reservationStatus_fk;//预约状态 1: 开放预约 2: 关闭预约
	private int appoitmentStatus_fk;//预约管理状态1: 确认中	2: 确认
	private String publisherTelephone;//发行商电话
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
	public long getProdId() {
		return prodId;
	}
	public void setProdId(long prodId) {
		this.prodId = prodId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public float getAppointmentShare() {
		return appointmentShare;
	}
	public void setAppointmentShare(float appointmentShare) {
		this.appointmentShare = appointmentShare;
	}
	public float getSalesShare() {
		return salesShare;
	}
	public void setSalesShare(float salesShare) {
		this.salesShare = salesShare;
	}
	public float getConfirmingShare() {
		return confirmingShare;
	}
	public void setConfirmingShare(float confirmingShare) {
		this.confirmingShare = confirmingShare;
	}
	public float getRemainingShare() {
		return remainingShare;
	}
	public void setRemainingShare(float remainingShare) {
		this.remainingShare = remainingShare;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}

	public long getDateOfAppointment() {
		return dateOfAppointment;
	}
	public void setDateOfAppointment(long dateOfAppointment) {
		this.dateOfAppointment = dateOfAppointment;
	}
	public int getReservationStatus_fk() {
		return reservationStatus_fk;
	}
	public void setReservationStatus_fk(int reservationStatus_fk) {
		this.reservationStatus_fk = reservationStatus_fk;
	}
	public int getAppoitmentStatus_fk() {
		return appoitmentStatus_fk;
	}
	public void setAppoitmentStatus_fk(int appoitmentStatus_fk) {
		this.appoitmentStatus_fk = appoitmentStatus_fk;
	}
	public String getPublisherTelephone() {
		return publisherTelephone;
	}
	public void setPublisherTelephone(String publisherTelephone) {
		this.publisherTelephone = publisherTelephone;
	}
}
