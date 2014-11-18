package com.incito.finshine.entity;

import java.util.Date;
import java.util.List;

import com.android.core.util.StrUtil;

public class SalesUser extends User {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int city;
    private String idNumber;
    private Date idValid;
    private String photo;
    private boolean certificated;
    private String company;
    private String title;
    private String bankCard;
    private List<Long> roleIds;
    
	public List<Long> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}
	public String getIdNumber() {
		if (StrUtil.isBlank(idNumber)) {
			return "";
		}
        return idNumber;
    }
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    public Date getIdValid() {
        return idValid;
    }
    public void setIdValid(Date idValid) {
        this.idValid = idValid;
    }
    public String getPhoto() {
    	if (StrUtil.isBlank(photo)) {
			return "";
		}
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public boolean isCertificated() {
        return certificated;
    }
    public void setCertificated(boolean certificated) {
        this.certificated = certificated;
    }
    public String getCompany() {
    	if (StrUtil.isBlank(company)) {
			return "";
		}
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
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
    public String getBankCard() {
		if (StrUtil.isBlank(bankCard)) {
			return "";
		}
        return bankCard;
    }
    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }
    public int getCity() {
        return city;
    }
    public void setCity(int city) {
        this.city = city;
    }
}
