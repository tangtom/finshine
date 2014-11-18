package com.incito.abs.model;

public abstract class AbsPerson {
	private long id;
	private String name;
	private long birthday;
	private int gender;
	private int age;
	private AbsAvatar avatar;
	private AbsContact contact;

	protected long getId() {
		return id;
	}

	protected String getName() {
		return name;
	}

	protected long getBirthday() {
		return birthday;
	}

	protected int getGender() {
		return gender;
	}

	protected int getAge() {
		return age;
	}

	public AbsAvatar getAvatar() {
		return avatar;
	}

	public void setAvatar(AbsAvatar avatar) {
		this.avatar = avatar;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public AbsContact getContact() {
		return contact;
	}

	public void setContact(AbsContact contact) {
		this.contact = contact;
	}
}
