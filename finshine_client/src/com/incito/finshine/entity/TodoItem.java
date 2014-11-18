package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

/**
 * Created with IntelliJ IDEA. User: junjun long: 14-4-18 Time: 上午8:05
 */
public class TodoItem implements Serializable {
	private static final long serialVersionUID = 1L;
	// Id
	private long id;
	// 理财师id
	private long userId;
	// 开始时间
	private long startTime;
	// 结束时间
	private long endTime;
	// 客户(可选)
	private long customerId;
	// 标题
	private String title;
	// 事项
	private String content;
	// 状态(已完成、未完成)
	private int status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
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

	public String getContent() {
		if (StrUtil.isBlank(content)) {
			return "";
		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
