package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.List;

import com.android.core.util.StrUtil;

public class User implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String password;
	private String salt;
	private List<Long> roleIds;
	private Boolean locked = Boolean.FALSE;
	private String name;
	private int age;
	private String email;
	private String tel;
	private int gender;
	private int userType;

	public String getCredentialsSalt() {
		return username + salt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		if (StrUtil.isBlank(username)) {
			return "";
		}
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		if (StrUtil.isBlank(password)) {
			return "";
		}
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		if (StrUtil.isBlank(salt)) {
			return "";
		}
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getName() {
		if (StrUtil.isBlank(name)) {
			return "";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		if (StrUtil.isBlank(email)) {
			return "";
		}
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		if (StrUtil.isBlank(tel)) {
			return "";
		}
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}
}
