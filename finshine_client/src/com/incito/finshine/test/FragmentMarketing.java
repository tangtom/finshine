package com.incito.finshine.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.custom.view.DlgCommFilter;
import com.custom.view.DlgCommFilter.RefreshFilterListener;
import com.incito.finshine.R;
import com.incito.finshine.activity.FragmentDetail;
import com.incito.finshine.common.Constant;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.RequestCallBackHandler;
import com.incito.finshine.manager.UploadEntity;
import com.incito.finshine.manager.UploadFileEntity;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FileFaliureResult;
import com.incito.finshine.network.HttpUtils.FileSuccessReslut;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.net.http.AsyncHttpResponseHandler;
import com.incito.wisdomsdk.net.http.RequestParams;

public class FragmentMarketing extends FragmentDetail {

	private static final String TAG = FragmentMarketing.class.getSimpleName();
	private TextView msg;
    private EditText tvSms;
	private AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String content) {
			Log.i(TAG, "success = " + content);
			if (msg != null) {
				msg.setText(content);
			}
		};

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			if (msg != null) {
				msg.setText(content);
			}
		}
	};

	public static FragmentMarketing newInstance(int id) {

		FragmentMarketing f = new FragmentMarketing();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);

		return f;
	}
	
 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		View view = inflater.inflate(R.layout.test_marketing, container, false);
		msg = (TextView) view.findViewById(R.id.textMessage);
		Button photo = (Button) view.findViewById(R.id.btnPhoto);
		photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				 getActivity().startActivity(new Intent(getActivity(), TestPhoto.class));
			}
		});
		

		Button queryBind = (Button) view.findViewById(R.id.btnQueryBind);
		queryBind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 init();
			}
		});
		
		
		Button uploadBind = (Button) view.findViewById(R.id.btnUploadBind);
		uploadBind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Request request = new Request(RequestDefine.RQ_TODO_GET_LIST,
						RequestType.GET, null, handler);
				CoreManager.getInstance().postRequest(request);
			}
		});
		Button submitBind = (Button) view.findViewById(R.id.btnSubmitBind);
		submitBind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Request request = new Request(RequestDefine.RQ_TODO_GET_LIST,
						RequestType.GET, null, handler);
				CoreManager.getInstance().postRequest(request);
			}
		});

		Button order = (Button) view.findViewById(R.id.btnGetSalesOrder);
		order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Request request = new Request(RequestDefine.RQ_TODO_GET_SINGLE,
						1, RequestType.GET, null, handler);
				CoreManager.getInstance().postRequest(request);
			}
		});
		Button customerMarketing = (Button) view
				.findViewById(R.id.btnGetCustomerMarketing);
		customerMarketing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				JSONObject params = new JSONObject();
				try {
					params.put("customerId", 1);
					params.put("title", "刷新");
					params.put("content", "上海浦东");
					params.put("startTime", System.currentTimeMillis());
					params.put("endTime",
							System.currentTimeMillis() + 30 * 60 * 1000);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Request request = new Request(RequestDefine.RQ_TODO_UPDATE, 1,
						RequestType.PUT, params, handler);
				CoreManager.getInstance().postRequest(request);
			}
		});
		Button delete = (Button) view
				.findViewById(R.id.btnGetCustomerMarketing);
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Request request = new Request(RequestDefine.RQ_TODO_DELETE, 2,
						RequestType.DELETE, null, handler);
				CoreManager.getInstance().postRequest(request);
			}
		});
		
	    tvSms = (EditText)view.findViewById(R.id.etSmsVerfication);
		content = getActivity().getResources().getStringArray(R.array.cs_market);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

 
	private void init(View view) {

//		DlgCommFilter filter = new DlgCommFilter(getActivity(),
//				R.array.cs_market, R.string.title_customer_marketing, "查询绑定协议",
//				true);
//		filter.setListener(new RefreshFilterListener() {
//
//			@Override
//			public void doRefresh(String reslut, int position) {
//				CommonUtil.showToast(reslut, getActivity());
//				getData(position);
//			}
//		});
//		filter.showDialog();
 
	
//	  <string-array name="cs_market">
//      <item>查询绑定协议 </item>
//      <item>获取合同信息 </item>
//      <item> 查询销售订单 </item>
//      <item>号码产生器 </item>
//      <item>客户营销首页统计 </item>
//      <item>订单查询list </item>
//      <item>根据理财师和客户查询历史订单 </item>
//      <item>获取营销首页客户记录 </item>
//      <item>取客户快的营销记录 </item>
//      <item>获取到期客户列表信息 </item>
//      <item>选定产品 </item>   10
//      <item>获取短信验证码 </item>
//      <item>上传绑定协议的附件 </item>
//      <item>绑定协议 </item>
//      <item>上传合同签名附件 </item>
//      <item> 绑定协议s </item>
//      <item>上传合同签名附件 </item>
//      <item>合同签订 </item>/
//      <item>订单支付凭证扫描附件 </item>
//      <item>订单支付 </item>
//      <item>下载附件 </item> 
//      <item>删除绑定协议的附件 </item>
//      <item>删除合同的附件 </item>
//      <item>删除订单的附件  </item>
//      <item>获取客户快的营销记录2  </item>
	    // 查询营销记录
//	<item>上传头像 </item>
//    <item>下载头像 </item>
	
//  </string-array>
	}
	
	Handler myhandler = new Handler(){

		@Override
		public void handleMessage(Message msg1) {
		 
			
		}
		
	};
	private void init() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				new myName(msg);
				
			}
		}).start();
		
		
		
//		DlgCommFilter filter = new DlgCommFilter(getActivity(), R.array.cs_market, R.string.title_customer_marketing, "查询绑定协议",true);
//		filter.setListener(new RefreshFilterListener() {
//
//			@Override
//			public void doRefresh(String reslut, int position) {
//				CommonUtil.showToast(reslut, getActivity());
//				getData(position);
//			}
//		});
//		filter.showDialog();
	}

	private String bindProtecalID = "0";// 绑定协议ID
	private String recordId = "";//营销记录ID
	private String bindStates= "";// 绑定状态 A已经绑定；C：尚未绑定
	private long salesId = 0;// 客户销售ID,选择产品后接口返回该字段
	private HttpUtils httpUtils = null;
	private String smsVerfication = "";// 短信验证码
	private String content [];
	Long fileId = 0l;// 附件ID
	Long contractId = 0l;// 合同id
	Long salesOrderID = 0l;
	
	// 绑定协议文件ID
	private long bindingAgreementFileId = 0l;
	
	private Long CUSTOMERID = 3l;
	private Long SALESID = 1l;
	private Long PROD_ID = 1l;
	
	private static String filterDay = "100";
	
	private String UPLOAD_PATH1 = Environment.getExternalStorageDirectory() + File.separator+ "incito/demo.jpg";
	private void getData(final int position)  {
		try {
		
		JSONObject params = new JSONObject();
		RequestParams reqParams = new RequestParams();
		switch (position) {
		
		case 4:
//			客户营销首页统计
			
			break;
		case 0:
			// 查询绑定协议
			params.put("salesId", SALESID);
			params.put("customerId", CUSTOMERID);
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_QUERY_BIND_PROTECAL, RequestType.POST, params);
			break;
		case 1:
			// 获取合同信息,要先查询营销信息
			params.put("id", contractId);
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_QUERY_CONTRACT_INFO, RequestType.POST, params);
			break;
		case 2:
			// 查询销售订单
			params.put("salesId", salesId);
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_QUERY_SALE_ORDER, RequestType.POST, params);
			break;
		case 5:
			// 订单查询list,根据客户id来查询所有的订单记录
			params.put("customerId", CUSTOMERID);
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_QUERY_ORDER_LIST, RequestType.POST, params);
			break;
		case 6:
			//根据理财师和客户查询历史订单
			params.put("customerId", CUSTOMERID);
			params.put("salesId", SALESID);
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_QUERY_HISTORY_ORDER, RequestType.POST, params);
			break;
		case 7:
			//获取营销首页客户记录
			params.put("salesId", SALESID);
//			params.put("id", CUSTOMERID);
			// 获取所有的客户信息和营销关系不太清楚获取客户ID在继续传
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_QUERY_HISTORY_ORDER, RequestType.POST, params);
			break;
		case 8:
			//取客户快的营销记录
//			"salesId":"1",
//			"customerId":"1",
//			"marketingStatusId":"5", 营销记录状态 <5 的订单全部查询出来 》5 的为历史订单；历史订单接口已经处理了
//			"filterDays":"100"
			params.put("customerId", CUSTOMERID);
			params.put("salesId", SALESID);
			params.put("marketingStatusId", "5");
			params.put("filterDays", filterDay);
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_QUERY_CS_BLOCK, RequestType.POST, params);
			break;
		case 9:
			//获取到期客户列表信息
			params.put("expirationDays", filterDay);
			params.put("salesId", SALESID);
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_QUERY_DUECS_LIST, RequestType.POST, params);
			break;
		case 25:
		    // 查询营销记录 ,获取营销状态
			params.put("id", recordId);
			params.put("customerId",1);
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_QUERY_CSINFO, RequestType.POST, params);
		    break;
		case 10:
			// 选定产品
			params.put("salesId", SALESID);
			params.put("customerId", CUSTOMERID);
			params.put("prodId", PROD_ID);
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_SELECT_PROD, RequestType.POST, params);
			break;
		case 11:
			// 获取短信验证码
			params.put("phoneNo", "15855131332");
			params.put("tranFk", bindProtecalID);
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_SMS_VERFICATION, RequestType.POST, params);
			break;
		case 12:
			//上传绑定协议附件
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_UPLOAD_PROTECAL_FILE, RequestType.POST, null);
			reqParams.put("bindingAgreementId", bindProtecalID);
			reqParams.put("bindingAgreementFile", getFile(UPLOAD_PATH, "bindingAgreementFile"));
//			reqParams.put("bindingAgreementFile", new File(UPLOAD_PATH1));
			httpUtils.setRequestParms(reqParams);
			httpUtils.setUploadFile(true);
			// 接口应该返回给我 绑定协议ID  以供我去绑定协议（目前还是先查询绑定协议）
			break;
		case 13:
			//先获取短信验证码； 绑定协议，先查绑定协议ID
			params.put("recordId", recordId);
			params.put("bindingAgreementId", bindProtecalID);
			params.put("phoneNo", "15855131332");
			params.put("prodId","1");
			params.put("verificationCode", getSmsPhone());
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_BIND_PROTECAL, RequestType.POST, params);
			break;
		case 14:
			//上传合同签名附件
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_CONTRACT_SIGN_FILE, RequestType.POST, null);
			reqParams.put("contractId", String.valueOf(contractId));
			reqParams.put("bankCardFrontalFile", getFile(UPLOAD_PATH, "bankCardFrontal"));
			reqParams.put("bankCardOppositeFile", getFile(UPLOAD_PATH, "bankCardOpposite"));
			reqParams.put("riskContractSignedFile", getFile(UPLOAD_PATH, "riskContractSigned"));
			reqParams.put("assetContractSignedFile", getFile(UPLOAD_PATH, "assetContractSigned"));
			reqParams.put("productContractSignedFile", getFile(UPLOAD_PATH, "productContractSigned"));
			httpUtils.setRequestParms(reqParams);
			httpUtils.setUploadFile(true);
			break;
		case 15:
			//合同签订
			params.put("id", contractId);
			params.put("productQuantity", "2");
			params.put("productTimeLimit", "2");// 年限
			params.put("totalAmount", "4000");
			params.put("bankId", "4000");
			params.put("customerId", "1");
			params.put("bankCardNumber", "40001111");
			params.put("verificationCode", getSmsPhone());
			params.put("recordId",recordId);
			params.put("orderId", salesOrderID);
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_SIGN_CONTRACT, RequestType.POST, params);
			break;
		case 18:
			//订单支付凭证扫描附件 
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_ORDER_PAY_SCANNER, RequestType.POST, null);
			reqParams.put("paymentDocumentFile", CommonUtil.getFile(Constant.FINSHINE, "paymentDocument"));
			reqParams.put("salesOrderId", salesOrderID+"");
			httpUtils.setRequestParms(reqParams);
			httpUtils.setUploadFile(true);
			break;
		case 19:
			// 订单支付
			params.put("salesOrderId",salesOrderID);
			params.put("recordId", recordId);
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_ORDER_PAY, RequestType.POST, params);
			break;
		case 20:
			// 下载附件
//			bindingAgreementFileId = 19;
			params.put("attachmentId",bindingAgreementFileId);
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_DOWNLOAD_FILE, RequestType.POST, params);
			httpUtils.setDownLoadFile(true);
			break;
		case 26:
			// 上传头像
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_UPLOAD_PHOTO, RequestType.POST, null);
			reqParams.put("file", new File(UPLOAD_PATH1));
			httpUtils.setRequestParms(reqParams);
			httpUtils.setUploadFile(true);
			httpUtils.setCustomerId(1l);
			httpUtils.setShowDiloag(false);
			break;
		case 27:
			// 下载头像
			httpUtils = new HttpUtils(getActivity(), RequestDefine.MARKET_RQ_DOWNLOAD_PHOTO, RequestType.GET, null);
			httpUtils.setShowDiloag(false);
			httpUtils.setCustomerId(1l);
			httpUtils.setDownLoadFile(true);
			break;
		default:
			return;
		 }
		} catch (Exception e) {
			// TODO: handle exception
			CommonUtil.showToast("异常："+e.getLocalizedMessage(), getActivity());
		}
		httpUtils.setSuccessListener(new SuccessReslut() {
			
			@Override
			public void getResluts(JSONObject response) {
				
				CommonUtil.showToast(content[position]+"接口成功", getActivity());
				try {
					String contents =  msg.getText().toString();
					msg.setText(contents+ content[position]+":"+ response.toString()+"\n\r");
					JSONObject obj = response.getJSONObject("item");
					msg.setText(response.toString());
				    if (position == 10) {
					    // 选定产品获取交易id
						if (obj != null) {
							recordId = obj.optString("id");
							salesOrderID = obj.optLong("salesOrderId");
							contractId = obj.optLong("contractId");
						 }
				    }else if (position == 0) {
						   //bingding id
				    	if (obj != null) {
				    		bindProtecalID = obj.optString("id");
				    		bindStates =  obj.optString("status");
				    		salesId = obj.optLong("salesId");
				    		bindingAgreementFileId = obj.optLong("bindingAgreementId");
				    		Log.d(TAG_CS, "bindStates="+bindStates+"  bindStates="+bindStates+"  salesId="+salesId);
						}
				    } else if(position == 13){
				    	// 获取合同id 和客户营销的状态步骤
				    	 
				    }else if(position == 18){
				    	//下载附件 返回Io 流
				    	
				    }
			
				  } catch (Exception e) {
				}
			}
		});
		
		httpUtils.setFileSuccessListener(new FileSuccessReslut() {
			
			@Override
			public void getResluts(int responeCode,byte[] binaryData) {
				if (position == 20) {
					storeFile(binaryData,UPLOAD_PATH,"bindFile.txt");
				}else{
					storeFile(binaryData,UPLOAD_PATH,"download.jpg");
				}
				
			}
		});
		
		httpUtils.setFileFaliureResult(new FileFaliureResult() {
			
			@Override
			public void getResluts(byte[] binaryData) {
				CommonUtil.showToast("下载头像失败"+binaryData.toString(), getActivity());
			}
		});
		httpUtils.executeRequest();
	}

	private static final String TAG_CS = "CS_MARKET";
	private String UPLOAD_PATH = Environment.getExternalStorageDirectory() + File.separator+ "market";
	private File getFile(String url,String fileName){
		
		File file = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			// sd 卡可用
			file = new File(url);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(url, fileName+".txt");
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (Exception e) {
					Toast.makeText(getActivity(), "创建文件失败", Toast.LENGTH_LONG).show();
				}
			}
			byte [] buffer = new byte[10];
			for (int i = 0; i < 10; i++) {
				buffer[i] ='1';
			}
			OutputStream os;
			try {
				os = new FileOutputStream(file);
				try {
					os.write(buffer,0,buffer.length);
					os.flush();
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			Toast.makeText(getActivity(), "SD卡不可使用", Toast.LENGTH_LONG).show();
		}
		return file;
	  }
	
	private String getSmsPhone(){
		
		String phone = tvSms.getText().toString().trim();
		if (TextUtils.isEmpty(phone)) {
			CommonUtil.showToast("请输入手机号码", getActivity());
//		}else if (!phone.matches("^[1]{0-9}[10]$")) {
//			CommonUtil.showToast("请输入合法手机号码", getActivity());
//			return "";
		}
		return phone;
	}
	
	/**
	 * @author: lihs
	 * @Title: storeFileImag
	 * @Description: 拍照完成存储在本地文件中
	 * @param byteArray
	 * @date: 2013-8-1 下午4:17:34
	 */
	private void storeFile(byte[] byteArray,String path,String fileName) {

		String states = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(states)) {
			// sd卡必须在挂载的情况下方可使用
			try {
				File albumTakePhotoFile = new File(path);
				if (!albumTakePhotoFile.exists()) {
					 albumTakePhotoFile.mkdirs();
				}
				albumTakePhotoFile = new File(path,fileName);
                if (!albumTakePhotoFile.exists()) {
                    try {
                        albumTakePhotoFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
				OutputStream os = new FileOutputStream(albumTakePhotoFile);
				os.write(byteArray, 0, byteArray.length);
				os.flush();
				os.close();
			} catch (Exception e) {
			}
		}
	}
	
	
	
	private class myName {

		
		public myName(final TextView tv) {
			super();
			String param = "value";
			File textFile = new File("/path/to/file.txt");
			File binaryFile = new File("/path/to/file.bin");
			String boundary = Long.toHexString(System.currentTimeMillis());
			URLConnection connection;
			try {
				String url ="http://222.208.168.90:8088/app/sales/1/customers/5/photo";
				connection = new URL(url).openConnection();
				connection.setDoOutput(true);
				connection.setConnectTimeout(40*1000);
				connection.setRequestProperty("Content-Type","multipart/form-data; boundary=" + boundary);
				OutputStream output = connection.getOutputStream();
			    Message MS = myhandler.obtainMessage();
			    MS.obj = output;
			    myhandler.sendMessageDelayed(MS, 10l);
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (httpUtils != null) {
			httpUtils.getDialog().clearAnimation();
		}
	}

	 
}