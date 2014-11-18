package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

public class Props implements Serializable{

	private int id;//订单ID
	private String name;//订单编号
	private String memo;//产品固定佣金比例
	private int type_fk;//产品奖励佣金比例
	private String typeName;//类型名称
	private int property_fk;//属性类型
	private String propertyName;//属性名称
	private String propertyValue;//属性值
	private int bonusQty ;
	private String measureUnit;//属性值单位
	private String picture;//佣金券图片
	private long creator_fk;//创建人员
	private long updater_fk;//更新人员
	private long dateOfCreate;//创建时间
	private long dateOfUpdate;//更新时间
	private short isValid;//isValid 0: 未启用 1: 启用
	
	
	public int getBonusQty() {
		return bonusQty;
	}
	public void setBonusQty(int bonusQty) {
		this.bonusQty = bonusQty;
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
	public String getMemo() {
		if (StrUtil.isBlank(memo)) {
			return "";
		}
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getType_fk() {
		return type_fk;
	}
	public void setType_fk(int type_fk) {
		this.type_fk = type_fk;
	}
	public String getTypeName() {
		if (StrUtil.isBlank(typeName)) {
			return "";
		}
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getProperty_fk() {
		return property_fk;
	}
	public void setProperty_fk(int property_fk) {
		this.property_fk = property_fk;
	}
	public String getPropertyName() {
		if (StrUtil.isBlank(propertyName)) {
			return "";
		}
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
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
	public String getMeasureUnit() {
		if (StrUtil.isBlank(measureUnit)) {
			return "";
		}
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
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
	public short getIsValid() {
		return isValid;
	}
	public void setIsValid(short isValid) {
		this.isValid = isValid;
	}
}
