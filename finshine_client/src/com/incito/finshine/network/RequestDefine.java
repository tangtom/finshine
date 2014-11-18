package com.incito.finshine.network;

public class RequestDefine {
	public static final int RQ_CUSTOMER_ADD = 0x0001;
	public static final int RQ_CUSTOMER_UPDATE = 0x0002;
	public static final int RQ_CUSTOMER_GET_LIST = 0x0003;
	public static final int RQ_CUSTOMER_GET_SINGLE = 0x0004;

	public static final int RQ_CUSTOMER_CONTACT_ADD = 0x0011;
	public static final int RQ_CUSTOMER_CONTACT_GET = 0x0012;

	public static final int RQ_CUSTOMER_ASSETINFO_UPDATE = 0x0013;
	public static final int RQ_CUSTOMER_ASSETINFO_GET = 0x0014;

	public static final int RQ_CUSTOMER_INCOME_UPDATE = 0x0015;
	public static final int RQ_CUSTOMER_INCOME_GET = 0x0016;

	public static final int RQ_TODO_ADD = 0x0021;
	public static final int RQ_TODO_GET_LIST = 0x0022;
	public static final int RQ_TODO_GET_SINGLE = 0x0023;
	public static final int RQ_TODO_UPDATE = 0x0024;
	public static final int RQ_TODO_DELETE = 0x0025;

	public static final int RQ_PRODUCT_GET = 0x0031;
	public static final int RQ_PRODUCT_GET_ADVANCE = 0x0032;
	public static final int RQ_PRODUCT_FAVORITE = 0x0033;
	public static final int RQ_PRODUCT_TARGET_CUSTOMER = 0x0034;
	public static final int RQ_PRODUCT_DETAIL_GET = 0x0035;
	public static final int RQ_PRODUCT_COLLECTION = 0x0036;
	public static final int RQ_PRODUCT_CANCLE_COLLECTION = 0x0037;
	public static final int RQ_PRODUCT_APPOINT_SHARE = 0x0038;
	public static final int RQ_QUERY_TARGET_CUSTOMER = 0x0039;
	public static final int RQ_DELETE_TARGET_CUSTOMER = 0x0040;
	public static final int RQ_ADD_TARGET_CUSTOMER = 0x0051;

	public static final int RQ_NOTICE_GET = 0x0041;
	public static final int RQ_ARTICLE_GET = 0x0042;
	public static final int RQ_ARTICLE_DETAIL_GET = 0x0043;
	public static final int RQ_ADVERTISEMENT_GET = 0x0044;

	public static final int RQ_BIND_QUERY = 0x0050;
	public static final int RQ_BIND_UPLOAD = 0x0051;
	public static final int RQ_BIND_SUBMIT = 0x0052;
	public static final int RQ_STATUS_ANALYSE = 0x0055;

	// TODO 测试客户营销接口
	public static final int MARKET_RQ_SELECT_PROD = 0x0061;
	public static final int MARKET_RQ_SMS_VERFICATION = 0x0062;
	public static final int MARKET_RQ_UPLOAD_PROTECAL_FILE = 0x0063;
	public static final int MARKET_RQ_BIND_PROTECAL = 0x0064;
	public static final int MARKET_RQ_CONTRACT_SIGN_FILE = 0x0065;
	public static final int MARKET_RQ_SIGN_CONTRACT = 0x0066;
	public static final int MARKET_RQ_ORDER_PAY = 0x0067;
	public static final int MARKET_RQ_DOWNLOAD_FILE = 0x0069;
	public static final int MARKET_RQ_QUERY_BIND_PROTECAL = 0x0068;
	public static final int MARKET_RQ_QUERY_CONTRACT_INFO = 0x0070;
	public static final int MARKET_RQ_QUERY_FIRSTPAGE_SATISTIC = 0x0071;
	public static final int MARKET_RQ_QUERY_SALE_ORDER = 0x0072;
	public static final int MARKET_RQ_QUERY_DUECS_LIST = 0x0073;

	public static final int MARKET_RQ_ORDER_PAY_SCANNER = 0x0074;

	public static final int MARKET_RQ_QUERY_CSINFO = 0x0075;// 营销记录

	public static final int MARKET_RQ_QUERY_ORDER_LIST = 0x0076;// 订单列表

	public static final int MARKET_RQ_QUERY_ORDER_PAYMENT = 0x00950;// 查询订单支付凭证

	public static final int MARKET_RQ_QUERY_HISTORY_ORDER = 0x0077;// 历史订单列表

	public static final int MARKET_RQ_QUERY_CS_BLOCK = 0x0078;

	public static final int MARKET_RQ_UPLOAD_PHOTO = 0x0079;
	public static final int MARKET_RQ_DOWNLOAD_PHOTO = 0x0080;

	// 客户管理
	public static final int RQ_CUSTOMER_POST_ECONOMY_LIST = 0x0091;
	public static final int RQ_CUSTOMER_POST_ENABLE = 0x0092;
	public static final int RQ_CUSTOMER_INVEST_RECORD = 0x0093;
	public static final int RQ_CUSTOMER_POST_ANALYSIS = 0x0094;
	public static final int RQ_CUSTOMER_POST_INVEST_DISTRIBUTION = 0x0095;
	public static final int RQ_CUSTOMER_POST_MARKETING = 0x0096;
	public static final int RQ_CUSTOMER_POST_DIVIDED = 0x0097;
	public static final int RQ_CUSTOMER_POST_ANALYSISLIST = 0x0098;

	public static final int MARKET_RQ_CS_MARKET_COUNT = 0x0081;// 客户营销统计
	public static final int MARKET_RQ_CS_MARKET_LIST = 0x0082;// 客户营销列表

	public static final int DOWN_UPLOAD_IDENFICATION_POSITIVE = 0x0083;// 上传下载证件正面
	public static final int DOWN_UPLOAD_IDENFICATION_OPPOSIVE = 0x0084;// 上传下载证件反面
	public static final int MARKET_QUERY_HISTORY_ORDERS = 0x0085;// 上传下载证件反面

	public static final int QUERY_INTENT_DATA = 0x0090;// 意向产品

	public static final int ADD_INTENT_PRODUCT = 0x0099;// 添加单笔意向产品
	public static final int CANCLE_INTENT_PRODUCT = 0x01100;// 取消单笔意向产品

	public static final int GET_ALL_NOTICE = 0x000111;// 获取公告

	public static final int QUERY_PROD_SHARE_MMONEY = 0x00112;
	public static final int QUERY_PROD_SALE_REAL = 0x00113;;

	// 登陆
	public static final int RQ_LOGIN = 0x0100;

	// 我的积分
	public static final int POINTER_RQ_MY_POINTER = 0x0101;
	public static final int POINTER_RQ_POP_STATISTICS = 0x0102;
	public static final int POINTER_RQ_PROPS_AVALIABLE = 0x0103;
	public static final int POINTER_RQ_PROPS_USED = 0x0104;
	public static final int POINTER_RQ_PROPS_USE = 0x0105;
	public static final int POINTER_RQ_ORDER_STATUS = 0x0106;
	public static final int POINTER_RQ_ORDER_STATUS_UPDATE = 0x0107;
	
	//预约
	public static final int APPOINT_RQ_SHARE = 0x0200;//查询预约状态
	public static final int APPOINT_RQ_CREATE = 0x0201;//创建预约
	public static final int APPOINT_RQ_PRODUCT_CHECK = 0x0202;//购买产品检查预约份额接口

	// public static final int DOWNLOAD_IDENFICATION_POSITIVE = 0x0085;// 下载证件正面
	// public static final int DOWNLOAD_IDENFICATION_OPPOSIVE = 0x0086;// 下载证件反面
	
	//合同签订新的接口
	public static final int MARKET_SC_FIRST_GET_BASEINFO = 0x1110;// 签订合同第一步 获取合同基本资料
	public static final int MARKET_SC_FIRST_SAVE_BASEINFO = 0x1111;// 签订合同第一步 保存合同基本资料
	public static final int MARKET_SC_FIRST_SAVE_ATTACHMENT = 0x1112;// 签订合同中所有的 保存附件信息
	public static final int MARKET_SC_DOWNLOAD_ATTACHMENT = 0x1113;// 签订合同  获取每个附件
	
	public static final int MARKET_SC_SECOND_GET_CONTRACT = 0x1114;//签订合同 第二步  获取所有的合同文本资料
	public static final int MARKET_SC_SECOND_SAVE_CONTRACT = 0x1115;//签订合同 第二步  保存所有的合同文本资料
	public static final int MARKET_SC_SECOND_SAVE_ATTACHMENT = 0x1116;//签订合同第二步  保存所有的合同附件
	
	public static final int MARKET_BIND_BASEINFO = 0x1150;// 绑定协议获取基本资料
	public static final int MARKET_BIND_DOWNLOAD_ATTACHMENT = 0x1151;// 绑定协议获取照片
	public static final int MARKET_BIND_UPDATE_ATTACHMENT = 0x1152;// 更新照片 andy.fang

	public static final int HOME_TASK_QUERY = 0x0152;// 绑定协议获取照片
	public static final int TASK_ACCEP_TASK = 0x0153;// 接受任务
	/*/道具/*/
	public static final int PROPERTY_SC_FIRST_GET_BASEINFO = 0x1121;
	

}
