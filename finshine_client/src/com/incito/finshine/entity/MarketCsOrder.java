package com.incito.finshine.entity;

import java.io.Serializable;
import java.util.List;

import com.android.core.util.StrUtil;

/**
 * <dl>
 * <dt>MarketCsOrder.java</dt>
 * <dd>Description:客户营销 客户订单列表数据</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-16 下午11:21:52</dd>
 * </dl>
 * 
 * @author lihs
 */

public class MarketCsOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;// 记录ID
	private long salesId;// 理财师ID
	private long customerId;// 客户iD
	private long prodId;// 产品id
	private long contractId;// 合同id
	private long salesOrderId;// 订单id
	

	// 营销状态 1:选定产品；2：绑定协议；3合同签订；4订单支付；5交易状态
	private long marketingStatusId;
	// 绑定协议状态 A已绑定协议；C尚未绑定协议
	private String status;// "A" "C"
	private long created;// : 1402913506000
	private long lastmod;// 1402913506000
	// 产品名称
	private String prodName;// : "产品1号"
	private String salesOrderNo;// 订单号码
	
	private String  bindProtecolId ="";
	
	private Product prod = null;
	private Customer customer = null;
	public Product getProd() {
		return prod;
	}

	public void setProd(Product prod) {
		this.prod = prod;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	// 客户姓名
	private String customerName = "";

	public String getCustomerName() {
		if (StrUtil.isBlank(customerName)) {
			return "";
		}
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBindProtecolId() {
		if (StrUtil.isBlank(bindProtecolId)) {
			return "";
		}
		return bindProtecolId;
	}

	public void setBindProtecolId(String bindProtecolId) {
		this.bindProtecolId = bindProtecolId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSalesOrderNo() {
		if (StrUtil.isBlank(salesOrderNo)) {
			return "";
		}
		return salesOrderNo;
	}

	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
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

	public String getProdName() {
		if (StrUtil.isBlank(prodName)) {
			return "";
		}
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
  private List<OrderInfoItem> orderlist = null;
   public List<OrderInfoItem> getOrderlist() {
	return orderlist;
}

  public void setOrderlists(List<OrderInfoItem> orderlist) {
	this.orderlist = orderlist;
}
  
  private ContractInfoItem contractinfo;
public ContractInfoItem getContractinfo() {
	return contractinfo;
}

public void setContractinfo(ContractInfoItem contractinfo) {
	this.contractinfo = contractinfo;
}
  
MarketStateReslut marketReslut;
public MarketStateReslut getMarketReslut() {
	return marketReslut;
}

public void setMarketReslut(MarketStateReslut marketReslut) {
	this.marketReslut = marketReslut;
}
}




