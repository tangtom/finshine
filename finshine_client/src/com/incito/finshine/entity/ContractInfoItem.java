package com.incito.finshine.entity;

import java.io.Serializable;

import com.android.core.util.StrUtil;

public class ContractInfoItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8900928393962214835L;

	private long id;;
	String contractNumber;;
	private long productQuantity;
	private long totalAmount;
	private long productTimeLimit;
	private long bankId;
	private String bankCardNumber;
	private long bankCardFrontalId;// 银行卡正面
	private long bankCardOppositeId;// 银行卡反面
	private long riskContractSignedId;// 风险合同签订
	private long assetContractSignedId;// 资产合同签订
	private long productContractSignedId;// 产品合同签订
	private String status;
	private String created;
	private String lastmod;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContractNumber() {
		if (StrUtil.isBlank(contractNumber)) {
			return "";
		}
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public long getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(long productQuantity) {
		this.productQuantity = productQuantity;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getProductTimeLimit() {
		return productTimeLimit;
	}

	public void setProductTimeLimit(long productTimeLimit) {
		this.productTimeLimit = productTimeLimit;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public String getBankCardNumber() {
		if (StrUtil.isBlank(bankCardNumber)) {
			return "";
		}
		return bankCardNumber;
	}

	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}

	public long getBankCardFrontalId() {
		return bankCardFrontalId;
	}

	public void setBankCardFrontalId(long bankCardFrontalId) {
		this.bankCardFrontalId = bankCardFrontalId;
	}

	public long getBankCardOppositeId() {
		return bankCardOppositeId;
	}

	public void setBankCardOppositeId(long bankCardOppositeId) {
		this.bankCardOppositeId = bankCardOppositeId;
	}

	public long getRiskContractSignedId() {
		return riskContractSignedId;
	}

	public void setRiskContractSignedId(long riskContractSignedId) {
		this.riskContractSignedId = riskContractSignedId;
	}

	public long getAssetContractSignedId() {
		return assetContractSignedId;
	}

	public void setAssetContractSignedId(long assetContractSignedId) {
		this.assetContractSignedId = assetContractSignedId;
	}

	public long getProductContractSignedId() {
		return productContractSignedId;
	}

	public void setProductContractSignedId(long productContractSignedId) {
		this.productContractSignedId = productContractSignedId;
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

	public String getCreated() {
		if (StrUtil.isBlank(created)) {
			return "";
		}
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastmod() {
		if (StrUtil.isBlank(lastmod)) {
			return "";
		}
		return lastmod;
	}

	public void setLastmod(String lastmod) {
		this.lastmod = lastmod;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
