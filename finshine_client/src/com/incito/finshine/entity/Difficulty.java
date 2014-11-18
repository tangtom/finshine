package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

/**
 * 难度系数
 * 
 *{"dateOfCreate":1401849143000,"coefficient":3,"aliasName":"中等","id":3,"isValid":1,"dateOfUpdate":1401379200000,"name":"普通"}
 * 
 * @author xiaoming
 * @since JDK 1.7
 * 
 */
public class Difficulty implements Serializable {

    private static final long serialVersionUID = 5943266551096243568L;

    private int id;

    /**
     * 难度名称
     */
    private String name;

    /**
     * 难度别名
     */
    private String aliasName;

    /**
     * 难度系数
     */
    private int coefficient;

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

    public String getAliasName() {
     	if (StrUtil.isBlank(aliasName)) {
			return "";
		}
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
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
