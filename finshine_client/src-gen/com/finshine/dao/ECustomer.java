package com.finshine.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table ECUSTOMER.
 */
public class ECustomer implements java.io.Serializable {

    private Long id;
    private Integer age;
    private Long annualIncome;
    private Integer billType;
    private Long birthday;
    private String city;
    private String contactAddress;
    private String country;
    private Long currentInvestValue;
    private Integer diploma;
    private String district;
    private String email1;
    private String email2;
    private String employer;
    private String fax;
    private Integer gender;
    private String IDNumber;
    private java.util.Date IDValid;
    private String industry;
    private String investNumber;
    private String investPreference;
    private String investSource;
    private String keyword;
    private Integer maritalStatus;
    private String name;
    private String nationality;
    private String nickName;
    private java.util.Date passportEntryTime;
    private Long peakInvestValue;
    private String position;
    private String job;
    private Long profit;
    private String province;
    private String residentialAddress;
    private String telephone;
    private String zipcode;
    private Integer idtype;
    private Long customerId;
    private String salesId;

    public ECustomer() {
    }

    public ECustomer(Long id) {
        this.id = id;
    }

    public ECustomer(Long id, Integer age, Long annualIncome, Integer billType, Long birthday, String city, String contactAddress, String country, Long currentInvestValue, Integer diploma, String district, String email1, String email2, String employer, String fax, Integer gender, String IDNumber, java.util.Date IDValid, String industry, String investNumber, String investPreference, String investSource, String keyword, Integer maritalStatus, String name, String nationality, String nickName, java.util.Date passportEntryTime, Long peakInvestValue, String position, String job, Long profit, String province, String residentialAddress, String telephone, String zipcode, Integer idtype, Long customerId, String salesId) {
        this.id = id;
        this.age = age;
        this.annualIncome = annualIncome;
        this.billType = billType;
        this.birthday = birthday;
        this.city = city;
        this.contactAddress = contactAddress;
        this.country = country;
        this.currentInvestValue = currentInvestValue;
        this.diploma = diploma;
        this.district = district;
        this.email1 = email1;
        this.email2 = email2;
        this.employer = employer;
        this.fax = fax;
        this.gender = gender;
        this.IDNumber = IDNumber;
        this.IDValid = IDValid;
        this.industry = industry;
        this.investNumber = investNumber;
        this.investPreference = investPreference;
        this.investSource = investSource;
        this.keyword = keyword;
        this.maritalStatus = maritalStatus;
        this.name = name;
        this.nationality = nationality;
        this.nickName = nickName;
        this.passportEntryTime = passportEntryTime;
        this.peakInvestValue = peakInvestValue;
        this.position = position;
        this.job = job;
        this.profit = profit;
        this.province = province;
        this.residentialAddress = residentialAddress;
        this.telephone = telephone;
        this.zipcode = zipcode;
        this.idtype = idtype;
        this.customerId = customerId;
        this.salesId = salesId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(Long annualIncome) {
        this.annualIncome = annualIncome;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getCurrentInvestValue() {
        return currentInvestValue;
    }

    public void setCurrentInvestValue(Long currentInvestValue) {
        this.currentInvestValue = currentInvestValue;
    }

    public Integer getDiploma() {
        return diploma;
    }

    public void setDiploma(Integer diploma) {
        this.diploma = diploma;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public java.util.Date getIDValid() {
        return IDValid;
    }

    public void setIDValid(java.util.Date IDValid) {
        this.IDValid = IDValid;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getInvestNumber() {
        return investNumber;
    }

    public void setInvestNumber(String investNumber) {
        this.investNumber = investNumber;
    }

    public String getInvestPreference() {
        return investPreference;
    }

    public void setInvestPreference(String investPreference) {
        this.investPreference = investPreference;
    }

    public String getInvestSource() {
        return investSource;
    }

    public void setInvestSource(String investSource) {
        this.investSource = investSource;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public java.util.Date getPassportEntryTime() {
        return passportEntryTime;
    }

    public void setPassportEntryTime(java.util.Date passportEntryTime) {
        this.passportEntryTime = passportEntryTime;
    }

    public Long getPeakInvestValue() {
        return peakInvestValue;
    }

    public void setPeakInvestValue(Long peakInvestValue) {
        this.peakInvestValue = peakInvestValue;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Long getProfit() {
        return profit;
    }

    public void setProfit(Long profit) {
        this.profit = profit;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Integer getIdtype() {
        return idtype;
    }

    public void setIdtype(Integer idtype) {
        this.idtype = idtype;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

}
