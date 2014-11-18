package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

/**
 * <dl>对应后台的ProductTransactionRecord
 * <dt>MarketStateReslut.java</dt>
 * <dd>Description:客户营销过程中</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-17 下午10:41:48</dd>
 * </dl>
 * yingx xiao jilu
 * @author lihs
 */
public class MarketStateReslut implements Serializable {

	/**
	 * {"id":106,"customerId":24,"created":1405908269000,"status":"A","prodId":1,"salesOrderId":110,
	 * "lastmod":1407391552312,"contractId":83,"salesId":1,"marketingStatusId":3}
	 */
	private static final long serialVersionUID = -697873933624673542L;
	
	private long id;//营销记录主键编号id
	private long salesId;// 理财师id
	private long customerId;// 客户id
	private long prodId;// 产品id
	private long contractId;// 合同id
	private long salesOrderId;//订单id
	// 营销状态 1:选定产品；2：绑定协议；3合同签订；4订单支付；5交易状态
	private long marketingStatusId;// 营销状态
    // 每一张表 都有该状态 对应该表的记录， 比如该记录是绑定协议，那么该状态就是绑定状态
	private String status;// 绑定状态
	private long  created;
	private long lastmod;
	
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
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getProdId() {
		return prodId;
	}
	public void setProdId(long prodId) {
		this.prodId = prodId;
	}
	public long getContractId() {
		return contractId;
	}
	public void setContractId(long contractId) {
		this.contractId = contractId;
	}
	public long getSalesOrderId() {
		return salesOrderId;
	}
	public void setSalesOrderId(long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}
	public long getMarketingStatusId() {
		return marketingStatusId;
	}
	public void setMarketingStatusId(long marketingStatusId) {
		this.marketingStatusId = marketingStatusId;
	}
	public String  getStatus() {
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
	
	
	//红生加的
//	private String phone = "";// 传递参数传递手机号码
//	
//	public String getPhone() {
//		return phone;
//	}
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
//	
	private long bindProtelId;// 绑定协议id
	public long getBindProtelId() {
		return bindProtelId;
	}
	public void setBindProtelId(long bindProtelId) {
		this.bindProtelId = bindProtelId;
	}
}
