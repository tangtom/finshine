package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

public class Photo implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private String mimeType;
	private byte[] photoData;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMimeType() {
		if (StrUtil.isBlank(mimeType)) {
			return "";
		}
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public byte[] getPhotoData() {
		return photoData;
	}

	public void setPhotoData(byte[] photoData) {
		this.photoData = photoData;
	}
}
