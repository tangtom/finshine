package com.incito.finshine.network;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.incito.finshine.common.Constant;
import com.incito.wisdomsdk.net.http.AsyncHttpResponseHandler;
import com.incito.wisdomsdk.net.http.RequestParams;

public class Request {
	public enum RequestType {
		POST, GET, PUT, DELETE
	};

	public static final String RESLUT_OK = "0";
	// 理财师ID
	public static long salesId;

	private int id = 0;
	private long customId;
	private long user_fk ;
	private long task_fk ;

	private JSONArray array;
	
	public long getCustomId() {
		return customId;
	}

	public void setCustomId(long customId) {
		this.customId = customId;
	}
	

	public long getTask_fk() {
		return task_fk;
	}

	public void setTask_fk(long task_fk) {
		this.task_fk = task_fk;
	}

	public long getUser_fk() {
		return user_fk;
	}

	public void setUser_fk(long user_fk) {
		this.user_fk = user_fk;
	}


	private RequestType type;
	// private RequestParams params;
	private JSONObject params;
	private AsyncHttpResponseHandler handler;
	private String appointAmount;// 预约份数

	private boolean isUploadFile = false;// false 不上传；true 是上传文件
	private RequestParams requestParms;// 上传文件传递的参数

	public RequestParams getRequestParms() {
		return requestParms;
	}

	private long productId;
	

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public void setRequestParms(RequestParams requestParms) {
		this.requestParms = requestParms;
	}

	public boolean isUploadFile() {
		return isUploadFile;
	}

	public void setUploadFile(boolean isUploadFile) {
		this.isUploadFile = isUploadFile;
	}

	public void setAppointAmount(String appointAmount) {
		this.appointAmount = appointAmount;
	}

	private boolean isDownLoadFile = false;

	public boolean isDownLoadFile() {
		return isDownLoadFile;
	}

	public void setDownLoadFile(boolean isDownLoadFile) {
		this.isDownLoadFile = isDownLoadFile;
	}

	public Request(long user_fk, long task_fk) {
		super();
		this.user_fk = user_fk;
		this.task_fk = task_fk;
	}

	public Request(int requestId, long customerId, long productId,
			RequestType type, JSONObject params,
			AsyncHttpResponseHandler handler) {
		this.id = requestId;
		this.customId = customerId;
		this.productId = productId;
		this.type = type;
		this.params = params;
		this.handler = handler;
	}

	public Request(int requestId, RequestType type, JSONObject params,
			AsyncHttpResponseHandler handler) {
		this.id = requestId;
		this.type = type;
		this.params = params;
		this.handler = handler;
	}
	
	public Request(int requestId, long customerId,long productId, RequestType type,
			JSONArray array, AsyncHttpResponseHandler handler,int status) {
		this.id = requestId;
		this.productId = productId;
		this.customId = customerId;
		this.type = type;
		this.array = array;
		this.handler = handler;
	}


	public Request(int requestId, long customerId, RequestType type,
			JSONObject params, AsyncHttpResponseHandler handler) {
		this.id = requestId;
		this.customId = customerId;
		this.type = type;
		this.params = params;
		this.handler = handler;
	}

	public String getUrl() {
		// if (params != null) {
		// Log.i("tag", params.toString());
		// }
		StringBuilder builder = new StringBuilder();
		builder.append(Constant.FINSHINE_URL + "/app/"); // 开发

		if (id == RequestDefine.RQ_LOGIN) {
			builder.append("login");
			return builder.toString();
		} else if (id == RequestDefine.POINTER_RQ_MY_POINTER) {
			builder.append("gamecenter/salesorder/list/");
			return builder.toString();
		} else if (id == RequestDefine.POINTER_RQ_POP_STATISTICS) {
			builder.append("gamecenter/home/account/" + customId);
			return builder.toString();
		} else if (id == RequestDefine.POINTER_RQ_PROPS_AVALIABLE) {
			builder.append("gamecenter/user/" + customId + "/product/"
					+ productId + "/props/available");
			return builder.toString();
		} else if (id == RequestDefine.POINTER_RQ_PROPS_USED) {
			builder.append("gamecenter/user/" + customId + "/product/"
					+ productId + "/props/used");
			return builder.toString();
		} else if (id == RequestDefine.POINTER_RQ_PROPS_USE) {
			builder.append("gamecenter/user/" + customId
					+ "/product/" + productId + "/props/use/");
			return builder.toString();
		}else if(id == RequestDefine.POINTER_RQ_ORDER_STATUS){
			builder.append("gamecenter/salesorder/" + customId + "/read");
			return builder.toString();
		}
		else if(id == RequestDefine.POINTER_RQ_ORDER_STATUS_UPDATE)
		{
			builder.append("gamecenter/salesorder/" + customId + "/read");
			return builder.toString();
		}
		else if(id == RequestDefine.RQ_STATUS_ANALYSE){
			builder.append("prod/totals");
			return builder.toString();
		}
		else if(id == RequestDefine.APPOINT_RQ_SHARE){
			builder.append("prod/"+productId+"/sales/"+salesId+"/appointment");
			return builder.toString();
		}
		else if(id == RequestDefine.APPOINT_RQ_CREATE){
			builder.append("prod/"+productId+"/sales/"+salesId+"/appointment/create");
			return builder.toString();
		}
		else if(id == RequestDefine.APPOINT_RQ_PRODUCT_CHECK){
			builder.append("sales/checkAppointmentShare");
			return builder.toString();
		}
		else if (id == RequestDefine.QUERY_PROD_SHARE_MMONEY) {
			builder.append("sales/getProdProfitCustomerList");
			return builder.toString();
		} else if (id == RequestDefine.QUERY_PROD_SALE_REAL) {
			builder.append("sales/getProudctSalesStatisticsList");
			return builder.toString();
		} else if (id == RequestDefine.GET_ALL_NOTICE) {
			builder.append("post/notice");
			return builder.toString();
		} else if (RequestDefine.QUERY_INTENT_DATA == id) {
			builder.append("prod/getProdInvestMentByCustom");
			return builder.toString();
		} else if (RequestDefine.ADD_INTENT_PRODUCT == id) {
			builder.append("prod/createProdInvestMentByCustom");
			return builder.toString();
		} else if (RequestDefine.CANCLE_INTENT_PRODUCT == id) {
			builder.append("prod/delProdInvestMentByCustom");
			return builder.toString();
		} else if (id == RequestDefine.RQ_PRODUCT_GET) {
			builder.append("prod/prodList/getProduct");
			return builder.toString();
		} else if (id == RequestDefine.RQ_PRODUCT_DETAIL_GET) {
			builder.append("gamecenter/user/" + customId + "/product/" + productId);
			return builder.toString();
		} else if (id == RequestDefine.RQ_ARTICLE_GET) {
			builder.append("post/article?pageno=1&pageSize=6");
			return builder.toString();
		} else if (id == RequestDefine.RQ_ARTICLE_DETAIL_GET) {
			builder.append("post/article/" + customId);
			return builder.toString();
		} else if (id == RequestDefine.RQ_PRODUCT_COLLECTION) {
			builder.append("prod/" + customId + "/salesId/" + salesId
					+ "/createdOrUpdateProdCollection");
			return builder.toString();
		} else if (id == RequestDefine.RQ_PRODUCT_CANCLE_COLLECTION) {
			// 
			builder.append("prod/" + customId + "/salesId/" + salesId
					+ "/createdOrUpdateProdCollection");
			return builder.toString();
		} else if (id == RequestDefine.RQ_PRODUCT_APPOINT_SHARE) {
			builder.append("prod/sales/" + salesId + "/prod/" + customId
					+ "/qty/" + appointAmount + "/insertAppProd");
			return builder.toString();
		} else if (id == RequestDefine.RQ_QUERY_TARGET_CUSTOMER) {
			// prod/{prodId}/sales/{salesId}/getProdInvestMent
			builder.append("prod/" + customId + "/sales/" + salesId
					+ "/getProdInvestMent");
			return builder.toString();
		} else if (id == RequestDefine.RQ_ADD_TARGET_CUSTOMER) {
			// prod/{prodId}/sales/{salesId}/createProdInvestMent
			builder.append("prod/" + customId + "/sales/" + salesId
					+ "/createProdInvestMent");
			return builder.toString();
		} else if (id == RequestDefine.RQ_DELETE_TARGET_CUSTOMER) {
			// http://222.208.168.90:8080/app/prod/10/sales/1/updateProdInvestMent
			builder.append("prod/" + customId + "/sales/" + salesId
					+ "/updateProdInvestMent");
			return builder.toString();
		} else if (id == RequestDefine.RQ_CUSTOMER_GET_LIST) {
			builder.append("sales/getCustomerList");
			return builder.toString();
		} else if (id == RequestDefine.RQ_CUSTOMER_POST_ECONOMY_LIST) {
			builder.append("sales/getSalesCustomerList");
			return builder.toString();
		} else if (id == RequestDefine.RQ_CUSTOMER_POST_ENABLE) {
			builder.append("sales/updateBatchCustomer");
			return builder.toString();
		} else if (id == RequestDefine.RQ_CUSTOMER_INVEST_RECORD) {
			builder.append("sales/getInvestmentRecordList");
			return builder.toString();
		} else if (id == RequestDefine.RQ_CUSTOMER_POST_ANALYSIS) {
			builder.append("sales/getInvestmentAnalysisStatistics");
			return builder.toString();
		} else if (id == RequestDefine.RQ_CUSTOMER_POST_INVEST_DISTRIBUTION) {
			builder.append("sales/getInvestmentDistributionStatistics");
			return builder.toString();
		} else if (id == RequestDefine.RQ_CUSTOMER_POST_MARKETING) {
			builder.append("sales/getCustomerMarketing");
			return builder.toString();
		} else if (id == RequestDefine.RQ_CUSTOMER_POST_DIVIDED) {
			builder.append("sales/getProdProfitList");
			return builder.toString();
		} else if (id == RequestDefine.RQ_CUSTOMER_POST_ANALYSISLIST) {
			builder.append("sales/getInvestmentAnalysisList");
			return builder.toString();
		}else if(id == RequestDefine.HOME_TASK_QUERY){
			// sales/getHistorySalesOrder
			builder.append("/gamecenter/home/task/list");
			return builder.toString();
		}else if(id == RequestDefine.TASK_ACCEP_TASK){
			builder.append("gamecenter/task/" + task_fk
					+ "/user/" + user_fk + "/accept");
			return builder.toString();
		}

		builder.append("sales/" + salesId);
		switch (id) {
		case RequestDefine.RQ_CUSTOMER_ADD:
			// case RequestDefine.RQ_CUSTOMER_GET_LIST:
			builder.append("/customers?pagesize=100");
			// builder.append("/customers/getCustomerList");
			break;
		// case RequestDefine.RQ_CUSTOMER_POST_ECONOMY_LIST:
		// builder.append("/getSalesCustomerList");
		// break;
		// case RequestDefine.RQ_CUSTOMER_POST_ENABLE:
		// builder.append("/customers/updateBatchCustomer");
		// break;
		case RequestDefine.RQ_CUSTOMER_GET_SINGLE:
		case RequestDefine.RQ_CUSTOMER_UPDATE:
			builder.append("/customers/" + customId);
			break;
		case RequestDefine.RQ_CUSTOMER_CONTACT_ADD:
		case RequestDefine.RQ_CUSTOMER_CONTACT_GET:
			builder.append("/customers");
			builder.append("/" + customId);
			builder.append("/notes");
			break;
		case RequestDefine.RQ_CUSTOMER_ASSETINFO_UPDATE:
		case RequestDefine.RQ_CUSTOMER_ASSETINFO_GET:
			builder.append("/customers");
			builder.append("/" + customId);
			builder.append("/assetinfo");
			break;
		case RequestDefine.RQ_CUSTOMER_INCOME_UPDATE:
		case RequestDefine.RQ_CUSTOMER_INCOME_GET:
			builder.append("/customers");
			builder.append("/" + customId);
			builder.append("/incomexpend");
			break;
		case RequestDefine.RQ_TODO_ADD:
			builder.append("/todos");
			break;
		case RequestDefine.RQ_TODO_GET_LIST:
			builder.append("/todos");
			String start = "";
			String end = "";
			builder.append("?start=" + start + "&end=" + end);
			break;
		case RequestDefine.RQ_TODO_GET_SINGLE:
		case RequestDefine.RQ_TODO_UPDATE:
		case RequestDefine.RQ_TODO_DELETE:
			builder.append("/todos");
			builder.append("/" + customId);
			break;
		default:
			builder = new StringBuilder();
			builder.append(Constant.FINSHINE_URL + "/app/");
			break;
		}

		// TODO 测试客户营销接口
		switch (id) {
		//新增的
		case RequestDefine.MARKET_BIND_BASEINFO:           //保存绑定协议基本信息
			builder.append("contract/bindingagreement");
			break;
		case RequestDefine.MARKET_BIND_UPDATE_ATTACHMENT:           //更新图片  Andy
			builder.append("contract/bindingagreementfile");
			break;
		case RequestDefine.MARKET_BIND_DOWNLOAD_ATTACHMENT: //绑定协议查询照片
			builder.append("system/attachment");
			break;


		case RequestDefine.MARKET_SC_FIRST_GET_BASEINFO:  //获取客户合同第一步的基本资料
		case RequestDefine.MARKET_SC_FIRST_SAVE_BASEINFO:  //保存合同第一步的合同信息
			builder.append("contract/contractbasedata");
			break;
			
		case RequestDefine.MARKET_SC_FIRST_SAVE_ATTACHMENT://合同签订第一步保存附件
			builder.append("contract/contractbasedatafile");
			break;
			
		case RequestDefine.MARKET_SC_DOWNLOAD_ATTACHMENT://获取每个附件
			builder.append("system/attachment");
			break;
		
		case RequestDefine.MARKET_SC_SECOND_GET_CONTRACT:  //获取客户合同第二步的基本资料
		case RequestDefine.MARKET_SC_SECOND_SAVE_CONTRACT:  //保存合同第二步的合同信息
			builder.append("contract/contractbatch");
			break;
			
		case RequestDefine.MARKET_SC_SECOND_SAVE_ATTACHMENT://合同签订第二步保存附件
			builder.append("contract/contractbatch/attachment");
			break;
			
			
		case RequestDefine.MARKET_RQ_QUERY_ORDER_PAYMENT:
			// sales/getPaymentDocument
			builder.append("sales/getPaymentDocument");
			break;
		case RequestDefine.MARKET_QUERY_HISTORY_ORDERS:
			// sales/orderManagement/getSalesOrderlist
			builder.append("sales/orderManagement/getSalesOrderlist");
			break;
		case RequestDefine.DOWN_UPLOAD_IDENFICATION_POSITIVE:
			// 上传正面；下载证件照反面 sales/{salesId}/customers/{customerId}/positivephoto
			builder.append("sales/" + salesId + "/customers/" + customId
					+ "/positivephoto");
			break;
		case RequestDefine.DOWN_UPLOAD_IDENFICATION_OPPOSIVE:
			// 上传下面；下载反面 sales/{salesId}/customers/{customerId}/backphoto
			builder.append("sales/" + salesId + "/customers/" + customId
					+ "/backphoto");
			break;
		case RequestDefine.MARKET_RQ_CS_MARKET_COUNT:
			// sales/getCustomerMarketing
			builder.append("sales/getCustomerMarketing");
			break;
		case RequestDefine.MARKET_RQ_CS_MARKET_LIST:
			// sales/getCustomerMarketingHome
			builder.append("sales/getCustomerMarketingHome");
			break;
		case RequestDefine.MARKET_RQ_UPLOAD_PHOTO:
			// http://10.66.52.59:8080/app/sales/{salesId}/customers/{customerId}/photo
			builder.append("sales/" + salesId + "/customers/" + customId
					+ "/photo");
			break;
		case RequestDefine.MARKET_RQ_DOWNLOAD_PHOTO:
			builder.append("sales/" + salesId + "/customers/" + customId
					+ "/photo");
			break;

		case RequestDefine.MARKET_RQ_SELECT_PROD:
			builder.append("sales/purchaseProduct");
			break;
		case RequestDefine.MARKET_RQ_QUERY_DUECS_LIST:
			// sales/getExpireCustomerList
			builder.append("sales/getExpireCustomerList");
			break;
		case RequestDefine.MARKET_RQ_QUERY_SALE_ORDER:
			builder.append("sales/getSalesOrder");
			break;
		case RequestDefine.MARKET_RQ_QUERY_FIRSTPAGE_SATISTIC:
			// sales/getSalesCustomerList
			builder.append("sales/getSalesCustomerList");
			break;
		case RequestDefine.MARKET_RQ_QUERY_CONTRACT_INFO:
			// sales/getContract
			builder.append("sales/getContract");
			break;
		case RequestDefine.MARKET_RQ_QUERY_BIND_PROTECAL:
			// /sales/customer/getBindSalesCustomer
			builder.append("sales/customer/getBindSalesCustomer");
			break;
		case RequestDefine.MARKET_RQ_DOWNLOAD_FILE:
			// sales/downloadAttachment
			builder.append("sales/downloadAttachment");
			break;
		case RequestDefine.MARKET_RQ_SIGN_CONTRACT:
			// /sales/signingContract
			builder.append("sales/signingContract");
			break;
		case RequestDefine.MARKET_RQ_BIND_PROTECAL:
			// http://222.208.168.90:8088/app/sales/bindingAgreement
			builder.append("sales/bindingAgreement");
			break;
		case RequestDefine.MARKET_RQ_UPLOAD_PROTECAL_FILE:
			// sales/binding/uploadAttachment
			builder.append("sales/binding/uploadAttachment");
			break;
		case RequestDefine.MARKET_RQ_SMS_VERFICATION:
			builder.append("sales/sendSmsVerifiCationCode");
			break;
		case RequestDefine.MARKET_RQ_CONTRACT_SIGN_FILE:
			// sales/contract/uploadAttachment
			builder.append("sales/contract/uploadAttachment");
			break;
		case RequestDefine.MARKET_RQ_ORDER_PAY:
			// sales/ordersTransferPayment
			builder.append("sales/ordersTransferPayment");
			break;
		case RequestDefine.MARKET_RQ_ORDER_PAY_SCANNER:
			// sales/order/uploadAttachment
			builder.append("sales/order/uploadAttachment");
			break;
		case RequestDefine.MARKET_RQ_QUERY_CSINFO:
			builder.append("sales/getRecordList");
			break;
		case RequestDefine.MARKET_RQ_QUERY_ORDER_LIST:
			builder.append("sales/orderManagement/getSalesOrderlist");
			break;
		case RequestDefine.MARKET_RQ_QUERY_HISTORY_ORDER:
			// sales/getHistorySalesOrder
			builder.append("sales/getHistorySalesOrder");
			break;
		case RequestDefine.MARKET_RQ_QUERY_CS_BLOCK:
			// sales/getHistorySalesOrder
			builder.append("sales/getProductTransactionRecordList");
			break;
		case RequestDefine.PROPERTY_SC_FIRST_GET_BASEINFO:
			builder.append("gamecenter/props/user/"+customId);
            break;
			
		default:
			break;
		}
		if (getRequestParms() != null) {
			Log.d("url", builder.toString() + "/"
					+ getRequestParms().toString());
		} else {
			if (params != null) {
				Log.d("url", builder.toString() + params.toString());
			}else{
				Log.d("url", builder.toString());
			}
		}
		return builder.toString();
	}

	public StringEntity getParams() throws UnsupportedEncodingException {
		if (params == null) {
			return null;
		}
		return new StringEntity(params.toString(), HTTP.UTF_8);
	}

	public AsyncHttpResponseHandler getHandler() {
		return handler;
	}

	public RequestType getType() {
		return type;
	}
	
	public StringEntity getArray() throws UnsupportedEncodingException {
		if (array == null) {
			return null;
		}
		return new StringEntity(array.toString(), HTTP.UTF_8);
	}
}
