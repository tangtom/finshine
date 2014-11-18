package com.incito.finshine.entity;

public class DataFilterModel {
	private String keyword;
	private String difficultyName;
	private long typeFK;
	private long statusFK;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDifficultyName() {
		return difficultyName;
	}

	public void setDifficultyName(String difficultyName) {
		this.difficultyName = difficultyName;
	}

	public long getTypeFK() {
		return typeFK;
	}

	public void setTypeFK(long typeFK) {
		this.typeFK = typeFK;
	}

	public long getStatusFK() {
		return statusFK;
	}

	public void setStatusFK(long statusFK) {
		this.statusFK = statusFK;
	}
}
