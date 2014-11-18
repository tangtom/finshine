package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

public class NoticeItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3561143892065057827L;

	private long id;
	private long articleId;
	private String content = "";
	private long created;
	private long lastmod;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
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

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public long getLastmod() {
		return lastmod;
	}

	public void setLastmod(long lastmod) {
		this.lastmod = lastmod;
	}

	public String getStatus() {
		if (StrUtil.isBlank(status)) {
			return "";
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	private String status;// 是否删除
	private String title;//
}
