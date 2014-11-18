//Source file: D:\\workspace\\finshine\\finshine_server\\src\\main\\java\\com\\incito\\finshine\\crms\\mci\\domain\\Customer.java

package com.incito.finshine.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.android.core.util.StrUtil;

import android.util.Log;

/**
 * 投资人信息
 */
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Customer clone() throws CloneNotSupportedException {
		Customer customer = null;
		try {
			customer = (Customer) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return customer;
	}

	/**
	 * 年龄 投资人的最小年龄是多少
	 */
	private int age = 18;

	/**
	 * 年收入
	 */
	private long annualIncome = 0;

	/**
	 * 账单方式 1纸质 2电子
	 */
	private int billType = 1;

	/**
	 * 出生年月日
	 */
	private long birthday;

	/**
	 * 所在城市
	 */
	private String city;

	/**
	 * 联系地址
	 */
	private String contactAddress;

	/**
	 * 国家
	 */
	private String country;

	/**
	 * 当前已投金额 == 投资总额
	 */
	private long currentInvestValue = 0;

	/**
	 * 学历 1保密 2高中 3本科 4硕士
	 */
	private int diploma = 1;

	/**
	 * 区县
	 */
	private String district;

	/**
	 * 常用电子邮件
	 */
	private String email1;

	/**
	 * 备用电子邮件
	 */
	private String email2;

	/**
	 * 雇主 工作单位
	 */
	private String employer;

	/**
	 * 传真号码
	 */
	private String fax;

	/**
	 * 性别 1男性 2女性 3其他
	 */
	private int gender;

	/**
	 * 证件号码
	 */
	private String idNumber;

	/**
	 * 身份证有效期开始日
	 */
	private long idValidStart;

	/**
	 * 身份证有效期结束日
	 */
	private long idValidEnd;

	/**
	 * 行业 诸如：教育、医疗卫生、政府、互联网
	 */
	private long industry = 1;

	/**
	 * 已投资次数 == 投资次数
	 */
	private int investNumber = 0;

	/**
	 * 投资总额 （暂且没用）
	 */
	private int investTotalAsserts = 0;

	/**
	 * 投资偏好 是不是列表？
	 */
	private long investPreference;

	/**
	 * 投资来源
	 */
	private String investSource = null;

	/**
	 * 关键字 描述投资人的一段文本文字
	 */
	private String keyword = null;

	/**
	 * 婚姻： 1保密， 2未婚，3已婚
	 */
	private int maritalStatus = 1;

	/**
	 * 投资人姓名
	 */
	private String name;

	/**
	 * 国籍 1中国 2美国 3俄罗斯 4英国 5法国 6德国 7意大利8加拿大9日本10韩国11朝鲜12印度13 巴西14 巴基斯坦 15以色列
	 */
	private long nationality = 1;

	/**
	 * 昵称
	 */
	private String nickName = null;

	/**
	 * 护照最近一次入境时间
	 */
	private long passportEntryTime;
	private String passportEntryTimeStr;//add by SGDY
	/**
	 * 峰值投资额
	 */
	private long peakInvestValue = 0;

	/**
	 * 职务
	 */
	private String position = null;

	/**
	 * 职业信息
	 */
	private long job = 1;

	/**
	 * 已收益
	 */
	private long profit = 0;

	/**
	 * 所在省
	 */
	private String province;

	/**
	 * 住址
	 */
	private String residentialAddress;

	/**
	 * 座机
	 */
	private String telephone;

	/**
	 * 邮政编码
	 */
	private String zipcode;

	/**
	 * 证件类型 1-身份证 2-军官证 3-护照
	 */
	private int idType = 1;
	private long id;
	private long salesId;

	/**
	 * 证件背面照片id
	 */
	private long backPhotoId;

	/**
	 * 手机号码常用联系电话
	 */
	private String cellPhone1;

	/**
	 * 备用联系电话
	 */
	private String cellPhone2;

	/**
	 * 紧急联系人关系
	 */
	private String ecRelation;

	/**
	 * 紧急联系人姓名
	 */
	private String ecName;

	/**
	 * 头像 id
	 */
	private long photoId;

	/**
	 * 证件正面照片id
	 */
	private long positivePhotoId;

	/**
	 * 社交号码
	 */
	private String socialNumber;

	/**
	 * 社交号码类别 1无、2腾讯、3微博、4微信、5facebook、6skype、7twitter、8陌陌、9歪歪、10msn、
	 */
	private int socialType;

	/**
	 * 紧急联系人电话
	 */
	private String ecellPhone;

	/**
	 * 家庭成员信息
	 */
	private String familyInfo = null;

	/**
	 * 资产总额 暂时没用
	 */
	private long totalAsset;

	/**
	 * 负债总计
	 */
	private double totalLoan;

	/**
	 * 目前的资产总额 == 就是经济状况的资产总计
	 */
	private double netAsset;

	/**
	 * 是否为有效客户 状态 A:有效 D：无效
	 * 
	 */
	private String status;

	/**
	 * 是否绑定 值大于0表示已绑定，否则未绑定 20140709 add
	 */
	private long bindingStatusId;

	/**
	 * 最新的投资纪要
	 */
	private String contactNoteTitle;
	/**
	 * 客户产品分红次数
	 * 
	 */
	private int prodDividendQty;

	public String getPassportEntryTimeStr() {
		return passportEntryTimeStr;
	}

	public void setPassportEntryTimeStr(String passportEntryTimeStr) {
		this.passportEntryTimeStr = passportEntryTimeStr;
	}

	public long getBindingStatusId() {
		return bindingStatusId;
	}

	public void setBindingStatusId(long bindingStatusId) {
		this.bindingStatusId = bindingStatusId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(long annualIncome) {
		this.annualIncome = annualIncome;
	}

	public int getBillType() {
		return billType;
	}

	public void setBillType(int billType) {
		this.billType = billType;
	}

	public long getBirthday() {
		return birthday;
	}

	public void setBirthday(long birthday) {
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
		if (StrUtil.isBlank(country)) {
			return "";
		}
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public long getCurrentInvestValue() {
		return currentInvestValue;
	}

	public void setCurrentInvestValue(long currentInvestValue) {
		this.currentInvestValue = currentInvestValue;
	}

	public int getDiploma() {
		return diploma;
	}

	public void setDiploma(int diploma) {
		this.diploma = diploma;
	}

	public String getDistrict() {
		if (StrUtil.isBlank(district)) {
			return "";
		}
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getEmail1() {
		if (StrUtil.isBlank(email1)) {
			return "";
		}
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		if (StrUtil.isBlank(email2)) {
			return "";
		}
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getEmployer() {
		if (StrUtil.isBlank(employer)) {
			return "";
		}
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getFax() {
		if (StrUtil.isBlank(fax)) {
			return "";
		}
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getIDNumber() {
		return idNumber == null ? "" : idNumber;
	}

	public void setIDNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public long getIndustry() {
		return industry;
	}

	public void setIndustry(long industry) {
		this.industry = industry;
	}

	public int getInvestNumber() {
		return investNumber;
	}

	public void setInvestNumber(int investNumber) {
		this.investNumber = investNumber;
	}

	public int getInvestTotalAsserts() {
		return investTotalAsserts;
	}

	public void setInvestTotalAsserts(int investTotalAsserts) {
		this.investTotalAsserts = investTotalAsserts;
	}

	public long getInvestPreference() {
		return investPreference;
	}

	public void setInvestPreference(long investPreference) {
		this.investPreference = investPreference;
	}

	public String getInvestSource() {
		if (StrUtil.isBlank(investSource)) {
			return "";
		}
		return investSource;
	}

	public void setInvestSource(String investSource) {
		this.investSource = investSource;
	}

	public String getKeyword() {
		if (StrUtil.isBlank(keyword)) {
			return "";
		}
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(int maritalStatus) {
		this.maritalStatus = maritalStatus;
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

	public long getNationality() {
		return nationality;
	}

	public void setNationality(long nationality) {
		this.nationality = nationality;
	}

	public String getNickName() {
		if (StrUtil.isBlank(nickName)) {
			return "";
		}
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public long getPassportEntryTime() {
		return passportEntryTime;
	}

	public void setPassportEntryTime(long passportEntryTime) {
		this.passportEntryTime = passportEntryTime;
	}

	public long getPeakInvestValue() {
		return peakInvestValue;
	}

	public void setPeakInvestValue(long peakInvestValue) {
		this.peakInvestValue = peakInvestValue;
	}

	public String getPosition() {
		if (StrUtil.isBlank(position)) {
			return "";
		}
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public long getProfit() {
		return profit;
	}

	public void setProfit(long profit) {
		this.profit = profit;
	}

	public String getProvince() {
		if (StrUtil.isBlank(province)) {
			return "";
		}
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getResidentialAddress() {
		if (StrUtil.isBlank(residentialAddress)) {
			return "";
		}
		return residentialAddress;
	}

	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public String getTelephone() {
		if (StrUtil.isBlank(telephone)) {
			return "";
		}
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getZipcode() {
		if (StrUtil.isBlank(zipcode)) {
			return "";
		}
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public int getIDType() {
		return idType;
	}

	public void setIDType(int idType) {
		this.idType = idType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSalesId() {
		return salesId;
	}

	public void setSalesId(long salesId) {
		this.salesId = salesId;
	}

	public long getBackPhotoId() {
		return backPhotoId;
	}

	public void setBackPhotoId(long backPhotoId) {
		this.backPhotoId = backPhotoId;
	}

	public String getCellPhone1() {
		if (StrUtil.isBlank(cellPhone1)) {
			return "";
		}
		return cellPhone1;
	}

	public void setCellPhone1(String cellPhone1) {
		this.cellPhone1 = cellPhone1;
	}

	public String getCellPhone2() {
		if (StrUtil.isBlank(cellPhone2)) {
			return "";
		}
		return cellPhone2;
	}

	public void setCellPhone2(String cellPhone2) {
		this.cellPhone2 = cellPhone2;
	}

	public String getEcRelation() {
		if (StrUtil.isBlank(ecRelation)) {
			return "";
		}
		return ecRelation;
	}

	public void setEcRelation(String ecRelation) {
		this.ecRelation = ecRelation;
	}

	public String getEcName() {
		if (StrUtil.isBlank(ecName)) {
			return "";
		}
		return ecName;
	}

	public void setEcName(String ecName) {
		this.ecName = ecName;
	}

	public long getJob() {
		return job;
	}

	public void setJob(long job) {
		this.job = job;
	}

	public long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}

	public long getPositivePhotoId() {
		return positivePhotoId;
	}

	public void setPositivePhotoId(long positivePhotoId) {
		this.positivePhotoId = positivePhotoId;
	}

	public String getSocialNumber() {
		if (StrUtil.isBlank(socialNumber)) {
			return "";
		}
		return socialNumber;
	}

	public void setSocialNumber(String socialNumber) {
		this.socialNumber = socialNumber;
	}

	public int getSocialType() {
		return socialType;
	}

	public void setSocialType(int socialType) {
		this.socialType = socialType;
	}

	public String getEcellPhone() {
		if (StrUtil.isBlank(ecellPhone)) {
			return "";
		}
		return ecellPhone;
	}

	public void setEcellPhone(String ecellPhone) {
		this.ecellPhone = ecellPhone;
	}

	public String getFamilyInfo() {
		if (StrUtil.isBlank(familyInfo)) {
			return "";
		}
		return familyInfo;
	}

	public void setFamilyInfo(String familyInfo) {
		this.familyInfo = familyInfo;
	}

	public long getTotalAsset() {
		return totalAsset;
	}

	public void setTotalAsset(long totalAsset) {
		this.totalAsset = totalAsset;
	}

	// 同类产品数量
	private long similarProductQuantity;

	public long getSimilarProductQuantity() {
		return similarProductQuantity;
	}

	public void setSimilarProductQuantity(long similarProductQuantity) {
		this.similarProductQuantity = similarProductQuantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getIdValidStart() {
		return idValidStart;
	}

	public void setIdValidStart(long idValidStart) {
		this.idValidStart = idValidStart;
	}

	public long getIdValidEnd() {
		return idValidEnd;
	}

	public void setIdValidEnd(long idValidEnd) {
		this.idValidEnd = idValidEnd;
	}

	public double getTotalLoan() {
		return totalLoan;
	}

	public void setTotalLoan(double totalLoan) {
		this.totalLoan = totalLoan;
	}

	public double getNetAsset() {
		return netAsset;
	}

	public void setNetAsset(double netAsset) {
		this.netAsset = netAsset;
	}

	public String getContactNoteTitle() {
		if (StrUtil.isBlank(contactNoteTitle)) {
			return "";
		}
		return contactNoteTitle;
	}

	public void setContactNoteTitle(String contactNoteTitle) {
		this.contactNoteTitle = contactNoteTitle;
	}

	public int getProdDividendQty() {
		return prodDividendQty;
	}

	public void setProdDividendQty(int prodDividendQty) {
		this.prodDividendQty = prodDividendQty;
	}

	public void LOG() {

		Field[] fields = Customer.class.getDeclaredFields();
		try {
			for (Field f : fields) {
				Log.i("Customer", f.getName() + " = " + f.get(this));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private List<ContactNote> listContactNote = new ArrayList<ContactNote>();

	public List<ContactNote> getListContactNote() {
		return listContactNote;
	}

	public void setListContactNote(List<ContactNote> listContactNote) {
		this.listContactNote = listContactNote;
	}

	public void addContactNote(ContactNote object) {
		listContactNote.add(0, object);
	}

	// private List<AssetInfo> listAssetInfo = new ArrayList<AssetInfo>();
	//
	// public List<AssetInfo> getListAssetInfo(){
	// return listAssetInfo;
	// }
	//
	// public void setListAssetInfo(List<AssetInfo> listAssetInfo){
	// this.listAssetInfo = listAssetInfo;
	// }
	//
	// public void addAssetInfo(AssetInfo object){
	// listAssetInfo.add(object);
	// }
}
