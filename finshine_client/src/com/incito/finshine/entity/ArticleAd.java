package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

/**
 * 文章广告信息
 */
public class ArticleAd implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 轮播广告id
	 */
	private long id;

	/**
	 * 关联文章id
	 */
	private long articleId;

	/**
	 * 创建时间
	 */
	private Date created;

	/**
	 * 修改时间
	 */
	private Date lastmod;

	/**
	 * 是否删除（I：删除;A:未删除）
	 */
	private String status;

	private String title;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getLastmod() {
		return lastmod;
	}

	public void setLastmod(Date lastmod) {
		this.lastmod = lastmod;
	}

	public String getStatus() {
		if (StrUtil.isBlank(status)) {
			status = "";
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getTitle() {
		if (StrUtil.isBlank(title)) {
			title = "";
		}
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
