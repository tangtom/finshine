package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author LIU QIANG 2014年8月14日
 */
public class ProdNewAppointment implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id = -1;
    /**
     * 理财师ID
     */
    private long salesId;
    /**
     * 产品ID
     */
    private long prodId;
    /**
     * 预约份额
     */
    private float appointmentShare;
    /**
     * 已分配份额
     */
    private float distributionShare;
    /**
     * 已销售份额
     */
    private float salesShare;
    /**
     * 预约状态
     */
    private int status_fk = -1;
    /**
     * 预约时间
     */
    private long dateOfAppointment;

    /**
     * 是否为新预约
     */
    private short isNew = 1;
    /**
     * 创建时间
     */
    private long dateOfCreate;
    /**
     * 修改时间
     */
    private long dateOfUpdate;
    /**
     * 创建人员
     */
    private long creator_fk;
    /**
     * 更新人员
     */
    private long updater_fk;
    /**
     * 有效标识
     */
    private int is_valid = 1;

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

    public float getAppointmentShare() {
        return appointmentShare;
    }

    public void setAppointmentShare(float appointmentShare) {
        this.appointmentShare = appointmentShare;
    }

    public float getDistributionShare() {
        return distributionShare;
    }

    public void setDistributionShare(float distributionShare) {
        this.distributionShare = distributionShare;
    }

    public float getSalesShare() {
        return salesShare;
    }

    public void setSalesShare(float salesShare) {
        this.salesShare = salesShare;
    }

    public int getStatus_fk() {
        return status_fk;
    }

    public void setStatus_fk(int status_fk) {
        this.status_fk = status_fk;
    }


    public short getIsNew() {
        return isNew;
    }

    public void setIsNew(short isNew) {
        this.isNew = isNew;
    }


    public long getDateOfAppointment() {
		return dateOfAppointment;
	}

	public void setDateOfAppointment(long dateOfAppointment) {
		this.dateOfAppointment = dateOfAppointment;
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

	public long getCreator_fk() {
        return creator_fk;
    }

    public void setCreator_fk(long creator_fk) {
        this.creator_fk = creator_fk;
    }

    public long getUpdater_fk() {
        return updater_fk;
    }

    public void setUpdater_fk(long updater_fk) {
        this.updater_fk = updater_fk;
    }

    public int getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(int is_valid) {
        this.is_valid = is_valid;
    }

}
