package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

/**
 * 道具類型
 * 
 * @author xiaoming
 * @since JDK 1.7
 * 
 */
public class PropsType implements Serializable {

    private static final long serialVersionUID = 8266415467971784883L;

    private int id;

    /**
     * 名称
     */
    private String name;

    /**
     * 道具描述
     */
    private String memo;

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

    public String getMemo() {
    	if (StrUtil.isBlank(memo)) {
			return "";
		}
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

/*    public Date getDateOfCreate() {
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
    }*/

    public short getIsValid() {
        return isValid;
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

	public void setIsValid(short isValid) {
        this.isValid = isValid;
    }

}
