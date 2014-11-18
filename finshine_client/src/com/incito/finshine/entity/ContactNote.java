//Source file: D:\\workspace\\finshine\\finshine_server\\src\\main\\java\\com\\incito\\finshine\\crms\\mci\\domain\\ContactNote.java

package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

/**
 * 投资人联系纪要 作为理财师，我能够记录和客户每次沟通的沟通纪要，包括投资意向
 */
public class ContactNote implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 沟通日期
	 */
	private long contactDate;

	/**
	 * 沟通标题
	 */
	private String title;

	/**
	 * 沟通记录内容
	 */
	private String content;
	private long id;

	/**
	 * 投资人编号
	 */
	private long customerId;

	/**
	 * 理财师id
	 */
	private long salesId;

	public long getContactDate() {
		return contactDate;
	}

	public void setContactDate(long contactDate) {
		this.contactDate = contactDate;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getSalesId() {
		return salesId;
	}

	public void setSalesId(long salesId) {
		this.salesId = salesId;
	}
}
