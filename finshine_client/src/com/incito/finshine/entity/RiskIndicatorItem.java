package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

/**
 * 
 * @author xiaoming
 *  委托人风险评估表中的选择题的选项
 */
public class RiskIndicatorItem implements Serializable {

    private static final long serialVersionUID = 8560200513091326981L;

    private long id;

    /**
     * 风险指标ID
     */
    private long indicatorId;

    /**
     * 键值
     */
    private String key;

    /**
     * 风险指标值
     */
    private String value;

    /**
     * 风险指标分值
     */
    private int points;

    private boolean selected;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(long indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getKey() {
    	if (StrUtil.isBlank(key)) {
			return "";
		}
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
    	if (StrUtil.isBlank(value)) {
			return "";
		}
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
