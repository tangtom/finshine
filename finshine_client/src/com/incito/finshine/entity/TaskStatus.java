package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

/**
 * 任务状态
 * 
 * @author xiaoming
 * @since JDK 1.7
 * 
 */
public class TaskStatus implements Serializable {

    private static final long serialVersionUID = 1884983440037791508L;

    private int id;

    /**
     * 状态名称
     */
    private String name;

    /**
     * 是处理中的状态
     */
    private short isProcessing;

    /**
     * 是完成的状态
     */
    private short isFinished;

    /**
     * 创建时间
     */
    private Long dateOfCreate;

    /**
     * 更新时间
     */
    private Long dateOfUpdate;

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

    public short getIsProcessing() {
        return isProcessing;
    }

    public void setIsProcessing(short isProcessing) {
        this.isProcessing = isProcessing;
    }

    public short getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(short isFinished) {
        this.isFinished = isFinished;
    }

    public Long getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Long dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public Long getDateOfUpdate() {
        return dateOfUpdate;
    }

    public void setDateOfUpdate(Long dateOfUpdate) {
        this.dateOfUpdate = dateOfUpdate;
    }

    public short getIsValid() {
        return isValid;
    }

    public void setIsValid(short isValid) {
        this.isValid = isValid;
    }

}
