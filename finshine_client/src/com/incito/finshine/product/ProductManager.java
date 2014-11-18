package com.incito.finshine.product;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.incito.finshine.entity.ContractBaseData;
import com.incito.finshine.entity.ContractBatch;
import com.incito.finshine.entity.FaxTradingAgreement;
import com.incito.finshine.entity.MarketStateReslut;
import com.incito.finshine.entity.Product;
import com.incito.finshine.entity.RiskAssessment;
import com.incito.finshine.entity.TradingBusinessApplication;
import com.incito.finshine.entity.spinner.FaxTradingAgreementParam;
import com.incito.finshine.entity.spinner.RiskAssessmentParam;
import com.incito.finshine.entity.spinner.TradingBusinessApplicationParam;

public class ProductManager {
	
	private List<Product> listaLLProduct;
	private List<Product> listIntentProduct;
	//暂时加在这里
	private MarketStateReslut marketResult;
	private ContractBaseData contractBaseData;
	private ContractBatch contractBatch;

	private RiskAssessment riskAssessment;
	private RiskAssessmentParam riskAssessmentParam;
	private TradingBusinessApplication trading;
	private TradingBusinessApplicationParam tradingParam;
	private FaxTradingAgreement fax1;
	private FaxTradingAgreementParam faxParam1;
	private FaxTradingAgreement fax2;
	private FaxTradingAgreementParam faxParam2;
	public List<Product> getListIntentProduct() {
		return listIntentProduct;
	}
	public void setListIntentProduct(List<Product> listIntentProduct) {
		this.listIntentProduct = listIntentProduct;
	}
	
	private List<Product> allProduct;
	
	public List<Product> getListaLLProduct() {
		
		return listaLLProduct;
	}
	public void setListaLLProduct(List<Product> listaLLProduct) {
		this.listaLLProduct = listaLLProduct;
	}
	public List<Product> getAllProduct() {
		if (allProduct == null) {
			allProduct = new ArrayList<Product>();
		}
		allProduct.clear();
		if (listIntentProduct != null) {
			allProduct.addAll(listIntentProduct);
		}
		if (listaLLProduct != null) {
			allProduct.addAll(listaLLProduct);
		}
		return allProduct;
	}
	public void setAllProduct(List<Product> allProduct) {
		this.allProduct = allProduct;
	}
	public void addListProduct(List<Product> addProd) {
		
		if (listaLLProduct == null) {
			this.listaLLProduct = addProd;
		}else{
			
			if (addProd == null || addProd.size() <=0) {
				return;
			}
			
			int size = listaLLProduct.size();
			for (int i = 0; i < size; i++) {
				 for (int j = 0; j < addProd.size(); j++) {
					if (listaLLProduct.get(i).getId().longValue() != addProd.get(j).getId().longValue()) {
						listaLLProduct.add(addProd.get(j));
					}
				}
			}
		}
	}
	
	// 根据条件进行过滤相应的产品数据
	/** 
	 * @author: lihs
	 * @Title: getProdByFilter 
	 * @Description: 是所有产品还是选择意向产品
	 * @param isSeleIntentProd true 是意向产品； false是所有产品
	 * @return 
	 * @date: 2014-6-14 下午7:40:55
	 */
	public List<Product> getProdByFilter(boolean isSeleIntentProd){
		List<Product> filterProd = null;
		 
			if (listaLLProduct != null  && listaLLProduct.size() > 0) {
				filterProd = new ArrayList<Product>();
				for (Product product : listaLLProduct) {
					if (isSeleIntentProd) {
						if (product.isIntentData()) {
							filterProd.add(product);
						}
					}else{
						if (!product.isIntentData()) {
							 filterProd.add(product);
						}
					}
				}
			}
		return filterProd;
	}
	
	// 点击收藏更新该产品的取消收藏还是添加收藏
	/** 
	 * @author: lihs
	 * @Title: updateProductById 
	 * @Description:   根据产品id 更新产品收藏的状态
	 * @param id 产品id
	 * @param isColl  true 收藏； false 取消收藏
	 * @date: 2014-6-14 下午7:38:38
	 */
	public void updateProductById(Long id,boolean isColl){
		Log.i("", "enter here----");
		if (listaLLProduct != null  && listaLLProduct.size() > 0) {
			for (Product product : listaLLProduct) {
				  if (product.getId().longValue() == id.longValue()) {
					  if (isColl) {
						  product.setIsSave(Long.valueOf(1l));
						  Log.i("", product.getId() + "--------------" + product.getProdName() + " ----------" + product.getIsSave());
					  }else{
						  product.setIsSave(Long.valueOf(0l));
					  }
				 }
			}
		}
	}
	
	// 根据产品id查询某一个产品
	public Product getProdctByProdId(Long id){
		
		List<Product> list = getAllProduct();
		Log.i("", "list size is " + list.isEmpty() + " " + list.size());
		if (list != null  && list.size() > 0) {
			for (Product product : list) {
				  if (product.getId().longValue() == id.longValue()) {
					   return product;
				 }
			}
		}
		return null;
	}
	
	public MarketStateReslut getMarketResult() {
		return marketResult;
	}
	public void setMarketResult(MarketStateReslut marketResult) {
		this.marketResult = marketResult;
	}
	
	
	
	public ContractBaseData getContractBaseData() {
		return contractBaseData;
	}
	public void setContractBaseData(ContractBaseData contractBaseData) {
		this.contractBaseData = contractBaseData;
	}
	
	public ContractBatch getContractBatch() {
		return contractBatch;
	}
	public void setContractBatch(ContractBatch contractBatch) {
		this.contractBatch = contractBatch;
	}
	
	public RiskAssessment getRiskAssessment() {
		return riskAssessment;
	}
	public void setRiskAssessment(RiskAssessment riskAssessment) {
		this.riskAssessment = riskAssessment;
	}
	
	public RiskAssessmentParam getRiskAssessmentParam() {
		return riskAssessmentParam;
	}
	public void setRiskAssessmentParam(RiskAssessmentParam riskAssessmentParam) {
		this.riskAssessmentParam = riskAssessmentParam;
	}
	public TradingBusinessApplication getTrading() {
		return trading;
	}
	public void setTrading(TradingBusinessApplication trading) {
		this.trading = trading;
	}
	public FaxTradingAgreement getFax1() {
		return fax1;
	}
	public void setFax1(FaxTradingAgreement fax1) {
		this.fax1 = fax1;
	}
	public FaxTradingAgreement getFax2() {
		return fax2;
	}
	public void setFax2(FaxTradingAgreement fax2) {
		this.fax2 = fax2;
	}
	
	public TradingBusinessApplicationParam getTradingParam() {
		return tradingParam;
	}
	public void setTradingParam(TradingBusinessApplicationParam tradingParam) {
		this.tradingParam = tradingParam;
	}
	public FaxTradingAgreementParam getFaxParam1() {
		return faxParam1;
	}
	public void setFaxParam1(FaxTradingAgreementParam faxParam1) {
		this.faxParam1 = faxParam1;
	}
	public FaxTradingAgreementParam getFaxParam2() {
		return faxParam2;
	}
	public void setFaxParam2(FaxTradingAgreementParam faxParam2) {
		this.faxParam2 = faxParam2;
	}

	
}
