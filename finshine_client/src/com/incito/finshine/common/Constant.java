package com.incito.finshine.common;

import java.io.File;

import android.os.Environment;

public class Constant {

	 /**  跳转到订单管理 **/
	 public static final String  ACTION_TO_ORDER_MANAGER = "action_to_order_manager";
	
 
	 //----------------------------------订单状态----------------------------------------------------------------
	 //----------------------------------未支付----------------------------------------------------------------
	 public final static long UN_PAID = 1;
	 //----------------------------------支付确认中----------------------------------------------------------------
	 public final static long PAID_CON_FIRM = 2;
	 //----------------------------------已支付----------------------------------------------------------------
	 public final static long PAID = 3;
	 
	 //----------------------------------交易结果----------------------------------------------------------------
	 //----------------------------------审核中----------------------------------------------------------------
	 public final static long AUDIT = 1;
	 //----------------------------------购买成功----------------------------------------------------------------
	 public final static long BUY_SUCCESS = 2;
	 //----------------------------------购买失败----------------------------------------------------------------
	 public final static long BUY_FAILED = 3;
	
	 //----------------------------------营销状态----------------------------------------------------------------
	 //----------------------------------选定产品----------------------------------------------------------------
	 public final static long PUR_CHASE_PROD_STATUS = 1;
	 //----------------------------------绑定协议----------------------------------------------------------------
	 public final static long BIND_AGREE_MENT_STATUS = 2;
	 //----------------------------------合同签订----------------------------------------------------------------
	 public final static long CON_TRACT_SIGN_STATUS = 3;
	
	 //----------------------------------订单支付----------------------------------------------------------------
	 public final static long ORDER_PAYMENT_STATUS = 4;
	//----------------------------------交易状态----------------------------------------------------------------
	 public final static long TRADING_RESULT_STATUS = 5;
		
	 //----------------------------------交易类型----------------------------------------------------------------
	 //----------------------------------选定产品----------------------------------------------------------------
	 public final static long PUR_CHASE_PROD_TRAN = 1;
	 //----------------------------------绑定协议----------------------------------------------------------------
	 public final static long BIND_AGREE_MENT_TRAN = 2;
	 //----------------------------------合同签订----------------------------------------------------------------
	 public final static long CON_TRACT_SIGN_TRAN = 3;
	 //----------------------------------订单支付----------------------------------------------------------------
	 public final static long ORDER_PAYMENT_TRAN = 4;
	//----------------------------------交易结果----------------------------------------------------------------
	 public final static long TRAN_STATUS_TRAN = 5;
	 
	 //----------------------------------附件类型----------------------------------------------------------------
	 //----------------------------------绑定协议签名附件----------------------------------------------------------------
	 public final static long BIND_AGREE_MENT_SIN_ATT_TYPE = 1;
	 //----------------------------------银行卡正面附件----------------------------------------------------------------
	 public final static long BANK_CARD_FRONTAL_ATT_TYPE = 2;
	 //---------------------------------银行卡反面附件-----------------------------------------------------------------
	 public final static long BANK_CARD_OPPO_SITE_ATT_TYPE = 3;
	 //----------------------------------风险偏好合同签名附件----------------------------------------------------------------
	 public final static long RISK_CONTRACT_SIN_ATT_TYPE = 4;
	 //----------------------------------资产托管合同签名附件----------------------------------------------------------------
	 public final static long ASSET_CONTRACT_SIN_ATT_TYPE = 5;
	 //----------------------------------产品偏好合同签名附件----------------------------------------------------------------
	 public final static long PROD_CONTRACT_SIN_ATT_TYPE = 6;
	 //----------------------------------订单支付凭证附件----------------------------------------------------------------
	 public final static long ORDER_PAYMENT_ATT_TYPE = 7;
	
	//----------------------------------号码生成器参数----------------------------------------------------------------
   //----------------------------订单号码字母前缀----------------------------------------------------------------------
   public final static String SO_LETTER_PREFIX = "SO";
   //-------------------------------订单表明----------------------------------------------------------------
   public final static String SO_TABLE_NAME ="sd_sales_order";
   //----------------------------------订单号码字段名----------------------------------------------------------------
   public final static String SO_FIELD_NAME = "sales_order_no";
 
   //----------------------------合同号码字母前缀----------------------------------------------------------------------
   public final static String CT_LETTER_PREFIX = "CT";
   //-------------------------------订单表明-------------------------------------------------------------------------
   public final static String CT_TABLE_NAME ="sd_contract";
   //----------------------------------订单号码字段名-----------------------------------------------------------------
   public final static String CT_FIELD_NAME = "contract_no";
 
   //----------------------------------号码生成器公用参数--------------------------------------------------------------
   //----------------------------------年的替代字符串------------------------------------------------------------------
   public final static String YEAR_MODULE = "{YYYY}";
   //----------------------------------月的替代字符串------------------------------------------------------------------
   public final static String MONTH_MODULE = "{MM}";
   //----------------------------------天的替代字符串------------------------------------------------------------------
   public final static String DAY_MODULE = "{DD}";
   //---------------流水号码长度--------------------------------------------------------------------------------------
   public final static int  SERIAL_NUMBER_LENGTH = 8;
   //---------------前缀总长度--------------------------------------------------------------------------------------
   public final static int  PREFIX_LENGTH = 10;
   
   //----------------------------------订单日志常量----------------------------------------------------------------
	 //----------------------------------订单生成----------------------------------------------------------------
	 public final static String UPDATE_TYPE_ORDER_CREATED = "订单生成";
	 //----------------------------------订单状态变更为----------------------------------------------------------------
	 public final static String UPDATE_TYPE_ORDER_STATUS_CHNAGE = "订单状态变更为";
	 //----------------------------------订单交易结果变更为----------------------------------------------------------------
	 public final static String  UPDATE_TYPE_TRADING_RESULT_CHNAGE = "订单交易结果变更为";
	 //----------------------------------系统操作者----------------------------------------------------------------
	 public final static String  SYS_OPERATOR = "系统:";
	 //----------------------------------管理员操作者----------------------------------------------------------------
	 public final static String  ADMIN_OPERATOR = "(管理员)";
	 
	 /**图片上传相关参数**/
	 public static final int REQUEST_TAKE_PHOTO = 1;
	 public static final int REQUEST_ALBUM_PHOTO_RESLUT = 2;
	 public static final int PHOTO_PICKED_WITH_DATA = 3;
	 public static final int REQUEST_CAPTURE_VIDEO = 4;
	 
	 /**杉容项目：图片上传下载本地文件Sd卡存储根目录**/
	 public static final String FINSHINE = Environment.getExternalStorageDirectory() + File.separator+ "finshine";
	 
	 /**营销信息头像上传和下载文件目录 ：头像命名为 上传：upload_+customerid ; 下载名称命名为: download_+customerid**/
	 public static final String HEAD = FINSHINE +  File.separator+ "headIcon";
	 public static final String DOWN_HEAD = "DOWN_CS_";
	 public static final String UP_HEAD = "UP_CS_";
	 
	 /**营销信息上传和下载文件目录 ：证件命名为 上传正面：up_opp_+customerid ; 下载名称命名为正面: down_opp+customerid;**/
	 public static final String CERTIFICATE = FINSHINE +  File.separator+ "certificate";
	 public static final String UP_CER_OPP =  "up_cer_opp_";// 证件照正面上传	 
	 public static final String UP_CER_DEF =  "up_cer_def_";// 证件照反面上传
	 
	 public static final String DOWN_CER_OPP = "down_cer_opp_";// 证件照正面下载
	 public static final String DOWN_CER_DEF =  "down_cer_def_";// 证件照反面下载
	 
	 public static final String UP_REAL_PHOTO =  "up_real_photo_";// 真实照片上传
	 public static final String DOWN_REAL_PHOTO =  "down_real_photo_";// 真实照下载
	 public static final String UP_AUTO_GRAPH =  "up_auto_graph_";// 签名照片上传
	 public static final String DOWN_AUTO_GRAPH =  "down_auto_graph_";// 签名照下载
	 
	 
	 /**营销信息上传和下载文件目录 ：证件命名为 上传：up_sign_+customerid ; 下载名称命名为: down_sign+customerid;**/
	 public static final String SIGNFILE = FINSHINE +  File.separator+ "signfile";
	 
	 
	 //这边所有的customerid都要换成salesOrderId
	 /**合同签订银行  上传和下载文件目录 ：银行卡命名为 上传：up_bankopp_+customerid ; 下载名称命名为: downbank_opp+customerid;upbank_de_+customerid ; 下载名称命名为: downbank_de_stomerid;**/
	 public static final String BANKCARD = FINSHINE +  File.separator+ "bankcard/";
	 public static final String UP_BANK_OPP = "up_bank_opp_";
	 public static final String UP_BANK_DEF = "up_bank_def_";
	 
	 public static final String DOWN_BANK_OPP = "down_bank_opp_";
	 public static final String DOWN_BANK_DEF = "down_bank_def_";
	 
	 /**电子合同合同上传下载 上传为 up_customerid;dn_customerid**/
	 public static final String RISK_PREF = FINSHINE +  File.separator+ "risk/";
	 public static final String UP_RISK = "up_risk_";
	 public static final String DOWN_RISK = "down_risk_";
	 
	 /**资产管理合同上传下载 上传为 up_customerid;dn_customerid**/
	 public static final String ASSET_CONTRACT = FINSHINE +  File.separator+ "asset_contract/";
	 public static final String UP_ASSET = "up_asset_";
	 public static final String DOWN_ASSET = "down_asset_";
	 
	 /**委托人风险评估表的签名照片  其他的签名也放在这里   不过要  用不同的id区分开来 up_investor_sign_ + salesOrderId**/
	 public static final String INVESTOR_SIGN = FINSHINE+"/investorSign/";
	 public static final String UP_INVESTOR_SIGN = "up_investor_sign_";
	 public static final String DOWN_INVESTOR_SIGN = "down_investor_sign_";
	 
	 
	 /**风险揭示下载 上传为 up_customerid;dn_customerid**/
	 public static final String RISK_SHOW = FINSHINE +  File.separator+ "risk_show";
	 public static final String UP_RISK_SHOW = "up_risk_show_";
	 public static final String DOWN_RISK_SHOW = "down_risk_show_";
	 
	 
	 public static final String CONTRACT = FINSHINE +  File.separator+ "risk_show";
	 public static final String UP_CONTRACT = "up_contract_";
	 public static final String DOWN_CONTRACT = "down_contract_";
	 
	 
	 /**订单支付 up_customerid;dn_customerid**/
	 public static final String ORDER_PAYMENT = FINSHINE +  File.separator+ "order_payment/";
	 public static final String UP_ORDER_PAYMENT_NAME =   "upload_order_payment_";
	 public static final String DOWN_ORDER_PAYMENT_NAME = "down_order_payment_";
	 
	 public static final  String OPPOSIVE = "idenfication_opposive.jpg";
	 public static final  String DEFENCISE = "idenfication_defense.jpg";
	 public static final  String DOWN_OPPOSIVE_JPG = "down_idenfication_opposive.jpg";
	 public static final  String DOWN_DEFENCISE_JPG = "down_idenfication_defencise.jpg";
//	 public static final String DOWN_USER_ICON = "down_user_icon.jpg";
	 
	 public static final String BIND_PROTECOL_FILE = "bind_protecol_file.txt";
	 public static final String BIND_PROTECOL_PARAMS = "bind_protecol_params";
	 
	 public static final String BANKCARDFRONTAL = "bankCardFrontal.txt";
	 public static final String BANKCARDOPPOSITE = "bankCardOpposite.txt";
	 public static final String RISKCONTRACTSIGNED = "riskContractSigned.txt";
	 public static final String ASSETCONTRACTSIGNED = "assetContractSigned.txt";
	 public static final String PRODUCTCONTRACTSIGNED = "productContractSigned.txt";
	 public static final String UPLOAD_PROD_ECPECTED = "upload_prod_ecpected.txt";
	 
	 public static final int USER_ICON_WIDTH = 120;
	 public static final int USER_ICON_HEIGHT = 120;
	 
	 public static final int COMMOM_WIDTH = 200;
	 public static final int COMMOM_HEIGHT= 100;
	 
	 public static final int SIGN_WIDTH = 120;
	 public static final int SIGN_HEIGHT = 35;
	 
	 // 该产品已经被绑定
	 public static final String CUSTOMER_ALREADY_BIND = "A";
	 
	 public static final int CONTRACT_SIGN = 3;
	 public static final int BIND_PROTECOL = 2;
	 
	 /**绑定协议的签名文件**/
	 public static final String SIGN_DIC_BIND = FINSHINE+"/signBind/";
	 public static final String SIGN_PROTECOL_FILE_UP = "up_cs_bind_sign_";
	 public static final String SIGN_PROTECOL_FILE_DOWN = "down_cs_bind_sign_";
	 
	 /**开发网**/
 	 public static final String FINSHINE_URL = "http://192.168.103.20:8080";
	 /**测试网**/
	/* public static final String FINSHINE_URL = "http://192.168.103.18";*/
	 /**实验网**/
//	 public static final String FINSHINE_URL = "http://srbanker.incito.com.cn:30001";
	 /**demo环境*/
//	 public static final String FINSHINE_URL = "http://58.211.121.116:8000";
	 public static final String SALES_ID = "sales_id";
	 public static final String CUSTOMER_ID = "customer_ID";
	 
	 /**
		 * @description
		 * @author Andy.fang
		 * @createDate 2014年8月19日
		 * @return
		 */
		public static String getRootUrl() {
			String path = FINSHINE_URL;
//			if (AppLog.DEBUG) {
//				 path = API_PATH2;
//			}
			return path;
		}
	 		
}
