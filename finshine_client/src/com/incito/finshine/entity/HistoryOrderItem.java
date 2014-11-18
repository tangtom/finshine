package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

/**
 * <dl>
 * <dt>HistoryOrderItem.java</dt>
 * <dd>Description:订单管理列表一条记录数据</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-17 下午11:44:23</dd>
 * </dl>
 * 
 * @author lihs
 */
public class HistoryOrderItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1891740675729414941L;
	
    private String  salesOrderNumber ;// 订单编号
    private String  createdStr;//   生成时间
    private String  customerName;// 客户姓名
    private String  prodName; // 产品名称
    private double  totalAmount;// 订单总额
    private long    orderStatusId;// 订单状态 @Constant.UN_PAID 1:未支付;2:支付确认中;3:已支付
    private long tradingResultId;// 交易结果
	public String getSalesOrderNumber() {
	 	if (StrUtil.isBlank(salesOrderNumber)) {
			return "";
		}
		return salesOrderNumber;
	}
	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}
	public String getCreatedStr() {
		if (StrUtil.isBlank(createdStr)) {
			return "";
		}
		return createdStr;
	}
	public void setCreatedStr(String createdStr) {
		this.createdStr = createdStr;
	}
	public String getCustomerName() {
		if (StrUtil.isBlank(customerName)) {
			return "";
		}
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public long getOrderStatusId() {
		return orderStatusId;
	}
	public void setOrderStatusId(long orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
	public long getTradingResultId() {
		return tradingResultId;
	}
	public void setTradingResultId(long tradingResultId) {
		this.tradingResultId = tradingResultId;
	}
	public String getFailReason() {
		if (StrUtil.isBlank(failReason)) {
			return "";
		}
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	private String failReason;// 交易失败的原因
    
//id: 8
//salesOrderNumber: "SO2014061300000002"
//productQuantity: 0
//totalAmount: 0
//changeAmount: 0
//amountChangeReason: null
//productTimeLimit: 0
//orderStatusId: 1
//orderStatusName: "未支付"
//orderStatusChangeReason: null
//effectiveDate: null
//paymentTypeId: 0
//tradingResultId: 0
//tradingResultName: ""
//commissionResultId: 0
//failReason: null
//status: "C"
//created: 1402661668000
//lastmod: 1402661668000
//salesId: 1
//username: "张理财"
//customerId: 1
//customerName: "李四X"
//iDNumber: "1234567"
//prodId: 1
//prodName: "产品1号"
//prodCode: "CP1"
//bankCardNumber: null
//contractId: 0
//isPaymentAttachment: "未上传支付凭证"
//salesOrderLogStr: null
//createdStr: "2014-06-13 20:14:28"

}
