package com.incito.finshine.entity;

import com.android.core.util.StrUtil;
import com.incito.utility.StringUtil;

/**
 * 首页文章信息
 */
public class Article {

	/**
	 * 文章id
	 */
	private long id;

	/**
	 * 文章标题
	 */
	private String title;

	/**
	 * 发布时间
	 */
	private long postTime;
	/**
	 * 创建时间
	 */
	private long created;

	/**
	 * 修改时间
	 */
	private long lastmod;

	/**
	 * 来源
	 */
	private String resource;

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 文章内容
	 */
	private String content;

	/**
	 * 是否删除（I：删除;A:未删除）
	 */
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		if (StrUtil.isBlank(title)) {
			this.title = "";
		}

		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getResource() {
		if (StrUtil.isBlank(resource)) {
			return "";
		}
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getAuthor() {
		if (StrUtil.isBlank(author)) {
			return "";
		}
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public String getStatus() {
		if (StrUtil.isBlank(status)) {
			return "";
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getPostTime() {
		return postTime;
	}

	public void setPostTime(long postTime) {
		this.postTime = postTime;
	}

}
