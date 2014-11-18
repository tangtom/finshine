package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

/**
 * 道具，业务中也叫物品券
 * 
 * @author xiaoming
 * @since JDK 1.7
 * 
 */
public class PropsWSEntity implements Serializable {

	private static final long serialVersionUID = -5530779691170842574L;

	private long id = -1;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 道具描述
	 */
	private String memo;

	/**
	 * 道具类型
	 */
	private PropsType type;

	/**
	 * 道具属性
	 */
	private PropsProperty property;

	/**
	 * 道具属性值
	 */
	private String propertyValue;

	private String picture;

	/**
	 * 用户手中总数量
	 */
	private int totalQty;

	/**
	 * 剩余数量
	 */
	private int remainingQty = -1;

	/**
	 * 道具状态
	 */
	private PropsStatus status;

	/**
	 * 失效时间
	 */
	private Date dateOfExpire;

	/**
	 * 创建人员ID
	 */
	private long creator_fk = -1;

	/**
	 * 更新人员ID
	 */
	private long updater_fk = -1;

	/**
	 * 创建时间
	 */
	private Date dateOfCreate = new Date();

	/**
	 * 更新时间
	 */
	private Date dateOfUpdate = new Date();

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

	public PropsType getType() {
		return type;
	}

	public void setType(PropsType type) {
		this.type = type;
	}

	public PropsProperty getProperty() {
		return property;
	}

	public void setProperty(PropsProperty property) {
		this.property = property;
	}

	public String getPropertyValue() {
		if (StrUtil.isBlank(propertyValue)) {
			return "";
		}
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getPicture() {
		if (StrUtil.isBlank(picture)) {
			return "";
		}
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(int totalQty) {
		this.totalQty = totalQty;
	}

	public int getRemainingQty() {
		return remainingQty;
	}

	public void setRemainingQty(int remainingQty) {
		this.remainingQty = remainingQty;
	}

	public PropsStatus getStatus() {
		return status;
	}

	public void setStatus(PropsStatus status) {
		this.status = status;
	}

	public Date getDateOfExpire() {
		return dateOfExpire;
	}

	public void setDateOfExpire(Date dateOfExpire) {
		this.dateOfExpire = dateOfExpire;
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

	public Date getDateOfCreate() {
		return dateOfCreate;
	}

	public void setDateOfCreate(Date dateOfCreate) {
		this.dateOfCreate = dateOfCreate;
	}

	public Date getDateOfUpdate() {
		return dateOfUpdate;
	}

	public void setDateOfUpdate(Date dateOfUpdate) {
		this.dateOfUpdate = dateOfUpdate;
	}

	public short getIsValid() {
		return isValid;
	}

	public void setIsValid(short isValid) {
		this.isValid = isValid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropsWSEntity other = (PropsWSEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
