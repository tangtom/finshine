package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.List;

import com.android.core.util.StrUtil;

/**
 * 
 * @author xiaoming
 * 委托人风险评估表中的选择题
 */
public class RiskIndicator implements Serializable {

    private static final long serialVersionUID = 8560200513091326981L;

    private long id;

    /**
     * 风险评估ID
     */
    private long assessmentId;

    /**
     * 标题
     */
    private String title;

    /**
     * 风险指标值
     */
    private String value;

    /**
     * 风险指标项
     */
    private List<RiskIndicatorItem> items;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getTitle() {
    	if (StrUtil.isBlank(title)) {
			return "";
		}
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<RiskIndicatorItem> getItems() {
        return items;
    }

    public void setItems(List<RiskIndicatorItem> items) {
        this.items = items;
    }

}
