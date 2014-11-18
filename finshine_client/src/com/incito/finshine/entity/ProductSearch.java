package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

public class ProductSearch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1174119619769246974L;
	// 查询参数设置
	private String searchProdName; // 用户输入产品名称 模糊查询
	private String searchProdOnDateTime;// 用户输入或选择 发行期开始
	private String searchProdEnDateTime;// 用户输入或选择 发行期结束
	private String searchProdFirstType;// 用户选择 一级类型
	private String searchProdSecondtype;// 用户选择 二级类型
	private String searchProdStatus;// 用户选择 状态
	private String searchProdStartLow;// 用户输入最低起投金额
	private String searchProdStartTop;// 用户输入最高起投金额
	private String searchFFType;// 用户选择 参考收益的方式 1只查浮动 2只查询固定 3查固定+浮动 查浮动
								// searchFFType ==1 查浮动 searchFFType==2查固定
								// searchFFType==3浮动+固定 searchFFLow 最低
								// searchFFTop最高
	private String searchFFTop;// 用户选择 最高收益
	private String searchFFLow;// 用户选择 最低收益
	private String searchProdTimeStart;// 用户选择 期限开始
	private String searchProdTimeEnd;// 用户选择 期限结束
	private String searchProdPreference;// 用户选择 发行商偏好 1金元惠
	private Long salesId;// 理财师id

	// 推荐表专用查询字段
	private String searchProdId;

	private String searchSalesLike;// 用户选择 只看收藏
	private String searchProdInvest;// 用户选择 只看推荐
	private String searchOverDue;// 用户选择 只看已过期
	private String searchProdTop; // 只看置顶

	// 发行商
	private String sortDesc; // 排序方向
	private String sortProdOnDateTime; // 排序 发行时间
	private String sortProdYieldFixed; // 排序 预期固定收益
	private String sortProdCommissionBase; // 排序 佣金
	private String sortProdTime; // 排序 期限
	private String sortProdSize; // 排序 规模
	// 最热 待确定 收藏最多
	private String sortHot; // 排序 收藏!! 最热

	// 分页 当前页
	private Integer pageNow = 0; // 分页使用
	private Integer pageStart; // pageSize * pageNow = pageStar 用于MySql
								// LIMIT分页使用
	private Integer pageSize = 10;

	public String getSortDesc() {
		if (StrUtil.isBlank(sortDesc)) {
			return "";
		}
		return sortDesc;
	}

	public void setSortDesc(String sortDesc) {
		this.sortDesc = sortDesc;
	}

	public String getSortProdOnDateTime() {
		if (StrUtil.isBlank(sortProdOnDateTime)) {
			return "";
		}
		return sortProdOnDateTime;
	}

	public void setSortProdOnDateTime(String sortProdOnDateTime) {
		this.sortProdOnDateTime = sortProdOnDateTime;
	}

	public String getSortProdYieldFixed() {
		if (StrUtil.isBlank(sortProdYieldFixed)) {
			return "";
		}
		return sortProdYieldFixed;
	}

	public void setSortProdYieldFixed(String sortProdYieldFixed) {
		this.sortProdYieldFixed = sortProdYieldFixed;
	}

	public String getSortProdCommissionBase() {
		if (StrUtil.isBlank(sortProdCommissionBase)) {
			return "";
		}
		return sortProdCommissionBase;
	}

	public void setSortProdCommissionBase(String sortProdCommissionBase) {
		this.sortProdCommissionBase = sortProdCommissionBase;
	}

	public String getSortProdTime() {
		if (StrUtil.isBlank(sortProdTime)) {
			return "";
		}
		return sortProdTime;
	}

	public void setSortProdTime(String sortProdTime) {
		this.sortProdTime = sortProdTime;
	}

	public String getSortProdSize() {
		if (StrUtil.isBlank(sortProdSize)) {
			return "";
		}
		return sortProdSize;
	}

	public void setSortProdSize(String sortProdSize) {
		this.sortProdSize = sortProdSize;
	}

	public String getSortHot() {
		if (StrUtil.isBlank(sortHot)) {
			return "";
		}
		return sortHot;
	}

	public void setSortHot(String sortHot) {
		this.sortHot = sortHot;
	}

	public Integer getPageNow() {
		return pageNow;
	}

	public void setPageNow(Integer pageNow) {
		this.pageNow = pageNow;
	}

	public Integer getPageStart() {
		pageStart = pageSize * pageNow;
		return pageStart;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearchProdName() {
		if (StrUtil.isBlank(searchProdName)) {
			return "";
		}
		return searchProdName;
	}

	public void setSearchProdName(String searchProdName) {
		this.searchProdName = searchProdName;
	}

	public String getSearchProdOnDateTime() {
		if (StrUtil.isBlank(searchProdOnDateTime)) {
			return "";
		}
		return searchProdOnDateTime;
	}

	public void setSearchProdOnDateTime(String searchProdOnDateTime) {
		this.searchProdOnDateTime = searchProdOnDateTime;
	}

	public String getSearchProdEnDateTime() {
		if (StrUtil.isBlank(searchProdEnDateTime)) {
			return "";
		}
		return searchProdEnDateTime;
	}

	public void setSearchProdEnDateTime(String searchProdEnDateTime) {
		this.searchProdEnDateTime = searchProdEnDateTime;
	}

	public String getSearchProdFirstType() {
		if (StrUtil.isBlank(searchProdFirstType)) {
			return "";
		}
		return searchProdFirstType;
	}

	public void setSearchProdFirstType(String searchProdFirstType) {
		this.searchProdFirstType = searchProdFirstType;
	}

	public String getSearchProdSecondtype() {
		if (StrUtil.isBlank(searchProdSecondtype)) {
			return "";
		}
		return searchProdSecondtype;
	}

	public void setSearchProdSecondtype(String searchProdSecondtype) {
		this.searchProdSecondtype = searchProdSecondtype;
	}

	public String getSearchProdStatus() {
		if (StrUtil.isBlank(searchProdStatus)) {
			return "";
		}
		return searchProdStatus;
	}

	public void setSearchProdStatus(String searchProdStatus) {
		this.searchProdStatus = searchProdStatus;
	}

	public String getSearchProdStartLow() {
		if (StrUtil.isBlank(searchProdStartLow)) {
			return "";
		}
		return searchProdStartLow;
	}

	public void setSearchProdStartLow(String searchProdStartLow) {
		this.searchProdStartLow = searchProdStartLow;
	}

	public String getSearchProdStartTop() {
		if (StrUtil.isBlank(searchProdStartTop)) {
			return "";
		}
		return searchProdStartTop;
	}

	public void setSearchProdStartTop(String searchProdStartTop) {
		this.searchProdStartTop = searchProdStartTop;
	}

	public String getSearchFFType() {
		if (StrUtil.isBlank(searchFFType)) {
			return "";
		}
		return searchFFType;
	}

	public void setSearchFFType(String searchFFType) {
		this.searchFFType = searchFFType;
	}

	public String getSearchFFTop() {
		if (StrUtil.isBlank(searchFFTop)) {
			return "";
		}
		return searchFFTop;
	}

	public void setSearchFFTop(String searchFFTop) {
		this.searchFFTop = searchFFTop;
	}

	public String getSearchFFLow() {
		if (StrUtil.isBlank(searchFFLow)) {
			return "";
		}
		return searchFFLow;
	}

	public void setSearchFFLow(String searchFFLow) {
		this.searchFFLow = searchFFLow;
	}

	public String getSearchProdTimeStart() {
		if (StrUtil.isBlank(searchProdTimeStart)) {
			return "";
		}
		return searchProdTimeStart;
	}

	public void setSearchProdTimeStart(String searchProdTimeStart) {
		this.searchProdTimeStart = searchProdTimeStart;
	}

	public String getSearchProdTimeEnd() {
		if (StrUtil.isBlank(searchProdTimeEnd)) {
			return "";
		}
		return searchProdTimeEnd;
	}

	public void setSearchProdTimeEnd(String searchProdTimeEnd) {
		this.searchProdTimeEnd = searchProdTimeEnd;
	}

	public String getSearchProdInvest() {
		if (StrUtil.isBlank(searchProdInvest)) {
			return "";
		}
		return searchProdInvest;
	}

	public void setSearchProdInvest(String searchProdInvest) {
		this.searchProdInvest = searchProdInvest;
	}

	public String getSearchSalesLike() {
		if (StrUtil.isBlank(searchSalesLike)) {
			return "";
		}
		return searchSalesLike;
	}

	public void setSearchSalesLike(String searchSalesLike) {
		this.searchSalesLike = searchSalesLike;
	}

	public String getSearchOverDue() {
		if (StrUtil.isBlank(searchOverDue)) {
			return "";
		}
		return searchOverDue;
	}

	public void setSearchOverDue(String searchOverDue) {
		this.searchOverDue = searchOverDue;
	}

	public String getSearchProdId() {
		if (StrUtil.isBlank(searchProdId)) {
			return "";
		}
		return searchProdId;
	}

	public void setSearchProdId(String searchProdId) {
		this.searchProdId = searchProdId;
	}

	public String getSearchProdPreference() {
		if (StrUtil.isBlank(searchProdPreference)) {
			return "";
		}
		return searchProdPreference;
	}

	public void setSearchProdPreference(String searchProdPreference) {
		this.searchProdPreference = searchProdPreference;
	}

	public Long getSalesId() {
		return salesId;
	}

	public void setSalesId(Long salesId) {
		this.salesId = salesId;
	}

	public String getSearchProdTop() {
		if (StrUtil.isBlank(searchProdTop)) {
			return "";
		}
		return searchProdTop;
	}

	public void setSearchProdTop(String searchProdTop) {
		this.searchProdTop = searchProdTop;
	}

}
