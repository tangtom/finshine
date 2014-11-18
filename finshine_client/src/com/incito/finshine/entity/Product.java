package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.Date;

import com.android.core.util.StrUtil;

public class Product implements Serializable {

	private static final long serialVersionUID = -6064590960102945421L;
	private		Long			id;		//主键
	private		String		status;		//可執行之Action
	private		long			created;		//首次建檔日期
	private		long			lastmod;		//最後修改日期
	private		Long			version;		//版本号
	private		String		prodMyid;		//产品编号
	private		Long			prodFirstType;		//产品类型1
	private		Long			prodSecondtype;		//产品类型2
	private		String		prodFirstTypeStr;//第一类型描述
	private 	String 		prodSecondtypeStr;//第二类型描述
	private		Long			prodStatus;		//产品状态 1新建 2审核 3预约 4预售 5在售 6封账 7下架
	private		String		prodElementsSrc;		//产品要素
	private		String		prodPptSrc;		//产品ppt
	private		String		prodInstructionsSrc;		//产品说明书
	private		String		prodContractSrc;		//产品合同
	private		String		prodNetreportSrc;		//净值报告
	private		String		prodTrainingSrc;		//产品培训资料
	private		String		prodName;		//产品名称
	private		String		prodHighLights;		//产品亮点
	private		String		prodAdmin;		//管理人
	private 	String		prodPreference;	//发行商偏好
	private		String		prodPublisher;		//发行商电话
	private		String		prodPubEmail;		//发行商EMAIL
	private		long		    prodOnDateTime;		//发行日期开始
	private		long		    prodEnDateTime;		//发行日期开始	
	private		Float			prodSize;		//产品规模
	private		Float			prodTime;		//产品期限
	private		Float			prodYieldFloating;		//投资者参考收益浮动
	private		Float			prodYieldFixed;		//投资者参考收益率固定
	private		Float			prodPrice;		//每份价格
	private		Float			prodStart;		//认购起点
	private		String		prodCounterParty;		//交易对手
	private		String		prodUse;		//资金用途
	private		String		prodRepayment;		//还款来源
	private		String		prodRisk;		//风险控制措施
	private		String		prodIncomeType;		//收益分配方式
	private		String		prodReceipts;		//收益发放日对应收益
	private		String		prodIncomeDateTime;		//收益发放日
	private		String		prodCstTel;		//客服电话
	private		String		prodEscrowacCount;		//托管账号
	private		Float			prodCommissionBase;		//佣金基数
	private 	Float			rewardCommission;		//奖励佣金
	private		String		prodComment;		//备注
	private		Long			userId;		//提交人
	private     String      prodCode; //产品代码
	private 	Long		   prodTop;//产品置顶
	@SuppressWarnings("unused")
	private String prodOnDateTimeStr; // 发行日期开始
	@SuppressWarnings("unused")
	private String prodEnDateTimeStr; // 发行日期结束

	private Long isSave;// 是否收藏 1. 已经收藏；其它未收藏

	private Long hotPoint;// 收藏的次数 最热排序按照来排序

	private Long checkUsed;// 是否使用佣金券
	private Long isHot;// 是否是最热产品
	private Long reservStatus;//预约状态 1是开放 2是关闭
	private Long appointmentStatus;//预约状态 0没有预约 1预约确认中 2预约已确认
	private Long distributionShare;//确认份额
	private Long salesShare;//销售份额

	public Long getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(Long appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}

	public Long getDistributionShare() {
		return distributionShare;
	}

	public void setDistributionShare(Long distributionShare) {
		this.distributionShare = distributionShare;
	}

	public Long getSalesShare() {
		return salesShare;
	}

	public void setSalesShare(Long salesShare) {
		this.salesShare = salesShare;
	}

	public Long getReservStatus() {
		return reservStatus;
	}

	public void setReservStatus(Long reservStatus) {
		this.reservStatus = reservStatus;
	}

	public Long getIsHot() {
		return isHot;
	}

	public void setIsHot(Long isHot) {
		this.isHot = isHot;
	}

	public Long getCheckUsed() {
		return checkUsed;
	}
	public Float getRewardCommission() {
		return rewardCommission;
	}
	public void setRewardCommission(Float rewardCommission) {
		this.rewardCommission = rewardCommission;
	}

	public void setCheckUsed(Long checkUsed) {
		this.checkUsed = checkUsed;
	}

	public Long getHotPoint() {
		return hotPoint;
	}

	public void setHotPoint(Long hotPoint) {
		this.hotPoint = hotPoint;
	}

	public Long getIsSave() {
		// long value = 0;
		// if (isSave != null) {
		// value = isSave.longValue();
		// }
		return isSave;
	}

	public void setIsSave(Long isSave) {
		this.isSave = isSave;
	}

	public String getProdOnDateTimeStr() {
		return DateFormat.hmsToDate(new Date(getProdOnDateTime()));
	}

	// public void setProdOnDateTimeStr(String prodOnDateTimeStr) {
	// setProdOnDateTime(DateFormat.StringToDate(prodOnDateTimeStr));
	// }
	public String getProdEnDateTimeStr() {
		return DateFormat.hmsToDate(new Date(getProdEnDateTime()));
	}

	// public void setProdEnDateTimeStr(String prodEnDateTimeStr) {
	// setProdEnDateTime(DateFormat.StringToDate(prodEnDateTimeStr));
	// }

	public Long getProdTop() {
		return prodTop;
	}

	public String getProdFirstTypeStr() {
		if (StrUtil.isBlank(prodFirstTypeStr)) {
			return "";
		}
		return prodFirstTypeStr;
	}

	public void setProdFirstTypeStr(String prodFirstTypeStr) {
		this.prodFirstTypeStr = prodFirstTypeStr;
	}

	public String getProdSecondtypeStr() {
		if (StrUtil.isBlank(prodSecondtypeStr)) {
			return "";
		}
		return prodSecondtypeStr;
	}

	public void setProdSecondtypeStr(String prodSecondtypeStr) {
		this.prodSecondtypeStr = prodSecondtypeStr;
	}

	public void setProdTop(Long prodTop) {
		this.prodTop = prodTop;
	}

	public String getProdCode() {
		if (StrUtil.isBlank(prodCode)) {
			return "";
		}
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getProdMyid() {
		if (StrUtil.isBlank(prodMyid)) {
			return "";
		}
		return prodMyid;
	}

	public void setProdMyid(String prodMyid) {
		this.prodMyid = prodMyid;
	}

	public Long getProdFirstType() {
		return prodFirstType;
	}

	public void setProdFirstType(Long prodFirstType) {
		this.prodFirstType = prodFirstType;
	}

	public Long getProdSecondtype() {
		return prodSecondtype;
	}

	public void setProdSecondtype(Long prodSecondtype) {
		this.prodSecondtype = prodSecondtype;
	}

	public void setProdStatus(Long prodStatus) {
		this.prodStatus = prodStatus;
	}

	public Long getProdStatus() {
		return prodStatus;
	}

	public String getProdElementsSrc() {
		if (StrUtil.isBlank(prodElementsSrc)) {
			return "";
		}
		return prodElementsSrc;
	}

	public void setProdElementsSrc(String prodElementsSrc) {
		this.prodElementsSrc = prodElementsSrc;
	}

	public String getProdPptSrc() {
		if (StrUtil.isBlank(prodPptSrc)) {
			return "";
		}
		return prodPptSrc;
	}

	public void setProdPptSrc(String prodPptSrc) {
		this.prodPptSrc = prodPptSrc;
	}

	public String getProdInstructionsSrc() {
		if (StrUtil.isBlank(prodInstructionsSrc)) {
			return "";
		}
		return prodInstructionsSrc;
	}

	public void setProdInstructionsSrc(String prodInstructionsSrc) {
		this.prodInstructionsSrc = prodInstructionsSrc;
	}

	public String getProdContractSrc() {
		if (StrUtil.isBlank(prodContractSrc)) {
			return "";
		}
		return prodContractSrc;
	}

	public void setProdContractSrc(String prodContractSrc) {
		this.prodContractSrc = prodContractSrc;
	}

	public String getProdNetreportSrc() {
		if (StrUtil.isBlank(prodNetreportSrc)) {
			return "";
		}
		return prodNetreportSrc;
	}

	public void setProdNetreportSrc(String prodNetreportSrc) {
		this.prodNetreportSrc = prodNetreportSrc;
	}

	public String getProdTrainingSrc() {
		if (StrUtil.isBlank(prodTrainingSrc)) {
			return "";
		}
		return prodTrainingSrc;
	}

	public void setProdTrainingSrc(String prodTrainingSrc) {
		this.prodTrainingSrc = prodTrainingSrc;
	}

	public String getProdName() {
		if (StrUtil.isBlank(prodName)) {
			return "";
		}
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdHighLights() {
		if (StrUtil.isBlank(prodHighLights)) {
			return "";
		}
		return prodHighLights;
	}

	public void setProdHighLights(String prodHighLights) {
		this.prodHighLights = prodHighLights;
	}

	public String getProdAdmin() {
		if (StrUtil.isBlank(prodAdmin)) {
			return "";
		}
		return prodAdmin;
	}

	public void setProdAdmin(String prodAdmin) {
		this.prodAdmin = prodAdmin;
	}

	public String getProdPublisher() {
		if (StrUtil.isBlank(prodPublisher)) {
			return "";
		}
		return prodPublisher;
	}

	public void setProdPublisher(String prodPublisher) {
		this.prodPublisher = prodPublisher;
	}

	public String getProdPubEmail() {
		if (StrUtil.isBlank(prodPubEmail)) {
			return "";
		}
		return prodPubEmail;
	}

	public void setProdPubEmail(String prodPubEmail) {
		this.prodPubEmail = prodPubEmail;
	}

	public long getProdOnDateTime() {
		return prodOnDateTime;
	}

	public void setProdOnDateTime(long prodOnDateTime) {
		this.prodOnDateTime = prodOnDateTime;
	}

	public long getProdEnDateTime() {
		return prodEnDateTime;
	}

	public void setProdEnDateTime(long prodEnDateTime) {
		this.prodEnDateTime = prodEnDateTime;
	}

	public Float getProdYieldFloating() {
		return prodYieldFloating;
	}

	public void setProdYieldFloating(Float prodYieldFloating) {
		this.prodYieldFloating = prodYieldFloating;
	}

	public Float getProdYieldFixed() {
		return prodYieldFixed;
	}

	public void setProdYieldFixed(Float prodYieldFixed) {
		this.prodYieldFixed = prodYieldFixed;
	}

	public Float getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(Float prodPrice) {
		this.prodPrice = prodPrice;
	}

	public Float getProdStart() {
		return prodStart;
	}

	public void setProdStart(Float prodStart) {
		this.prodStart = prodStart;
	}

	public String getProdCounterParty() {
		if (StrUtil.isBlank(prodCounterParty)) {
			return "";
		}
		return prodCounterParty;
	}

	public void setProdCounterParty(String prodCounterParty) {
		this.prodCounterParty = prodCounterParty;
	}

	public String getProdUse() {
		if (StrUtil.isBlank(prodUse)) {
			return "";
		}
		return prodUse;
	}

	public void setProdUse(String prodUse) {
		this.prodUse = prodUse;
	}

	public String getProdRepayment() {
		if (StrUtil.isBlank(prodRepayment)) {
			return "";
		}
		return prodRepayment;
	}

	public void setProdRepayment(String prodRepayment) {
		this.prodRepayment = prodRepayment;
	}

	public String getProdRisk() {
		if (StrUtil.isBlank(prodRisk)) {
			return "";
		}
		return prodRisk;
	}

	public void setProdRisk(String prodRisk) {
		this.prodRisk = prodRisk;
	}

	public String getProdIncomeType() {
		if (StrUtil.isBlank(prodIncomeType)) {
			return "";
		}
		return prodIncomeType;
	}

	public void setProdIncomeType(String prodIncomeType) {
		this.prodIncomeType = prodIncomeType;
	}

	public String getProdReceipts() {
		if (StrUtil.isBlank(prodReceipts)) {
			return "";
		}
		return prodReceipts;
	}

	public void setProdReceipts(String prodReceipts) {
		this.prodReceipts = prodReceipts;
	}

	public String getProdIncomeDateTime() {
		if (StrUtil.isBlank(prodIncomeDateTime)) {
			return "";
		}
		return prodIncomeDateTime;
	}

	public void setProdIncomeDateTime(String prodIncomeDateTime) {
		this.prodIncomeDateTime = prodIncomeDateTime;
	}

	public String getProdCstTel() {
		if (StrUtil.isBlank(prodCstTel)) {
			return "";
		}
		return prodCstTel;
	}

	public void setProdCstTel(String prodCstTel) {
		this.prodCstTel = prodCstTel;
	}

	public String getProdEscrowacCount() {
		if (StrUtil.isBlank(prodEscrowacCount)) {
			return "";
		}
		return prodEscrowacCount;
	}

	public void setProdEscrowacCount(String prodEscrowacCount) {
		this.prodEscrowacCount = prodEscrowacCount;
	}

	public Float getProdSize() {
		return prodSize;
	}

	public void setProdSize(Float prodSize) {
		this.prodSize = prodSize;
	}

	public Float getProdTime() {
		return prodTime;
	}

	public void setProdTime(Float prodTime) {
		this.prodTime = prodTime;
	}

	public Float getProdCommissionBase() {
		return prodCommissionBase;
	}

	public void setProdCommissionBase(Float prodCommissionBase) {
		this.prodCommissionBase = prodCommissionBase;
	}

	public String getProdComment() {
		if (StrUtil.isBlank(prodComment)) {
			return "";
		}
		return prodComment;
	}

	public void setProdComment(String prodComment) {
		this.prodComment = prodComment;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getProdPreference() {
		if (StrUtil.isBlank(prodPreference)) {
			return "";
		}
		return prodPreference;
	}

	public void setProdPreference(String prodPreference) {
		this.prodPreference = prodPreference;
	}

	private boolean isIntentData = false;// true 是意向产品； false是所有产品

	public boolean isIntentData() {
		return isIntentData;
	}

	public void setIntentData(boolean isIntentData) {
		this.isIntentData = isIntentData;
	}
}
