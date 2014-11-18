package com.incito.finshine.network;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.custom.view.CommonDialog;
import com.custom.view.CommonDialog.BtnClickedListener;
import com.custom.view.CommonWaitDialog;
import com.incito.finshine.R;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request.RequestType;
import com.incito.utility.CommonUtil;
import com.incito.wisdomsdk.net.http.BinaryHttpResponseHandler;
import com.incito.wisdomsdk.net.http.JsonHttpResponseHandler;
import com.incito.wisdomsdk.net.http.RequestParams;

/**
 * <dl>
 * <dt>HttpUtils.java</dt>
 * <dd>Description:http 通用接口请求</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-12 上午9:32:23</dd>
 * </dl>
 * 
 * @author lihs
 */
public class HttpUtils {

	private Context context;
	
	private CommonWaitDialog dialog = null;

	public CommonWaitDialog getDialog() {
		return dialog;
	}
	private static final String TAG = "HttpUtils";
	
	public static final String TIMEOUT = "网络连接超时";
	public static final String UNKNOWHOST = "未识别主机，请检查网络连接";
	public static final String INTERNET_INTERRUPT = "网络连接中断，请检查网络";
    
	private String alertMsg = "";
	public String getAlertMsg() {
		return alertMsg;
	}

	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}
	private boolean isShowDiloag = true;
	public void setShowDiloag(boolean isShowDiloag) {
		this.isShowDiloag = isShowDiloag;
	}
	private int requestId ;
	private RequestType type;
	private JSONObject params;
	private long customerId = -1;
	private boolean isDownLoadFile = false;
	private long productId;
	private long user_fk ;
	private long task_fk ;
	
	public long getUser_fk() {
		return user_fk;
	}

	public void setUser_fk(long user_fk) {
		this.user_fk = user_fk;
	}

	public long getTask_fk() {
		return task_fk;
	}

	public void setTask_fk(long task_fk) {
		this.task_fk = task_fk;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public boolean isDownLoadFile() {
		return isDownLoadFile;
	}

	public void setDownLoadFile(boolean isDownLoadFile) {
		this.isDownLoadFile = isDownLoadFile;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	private boolean isUploadFile = false;// false 不上传；true 是上传文件
	private RequestParams requestParms;// 上传文件传递的参数
	
	public RequestParams getRequestParms() {
		return requestParms;
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

	
	public HttpUtils(Context context,int requestId, RequestType type, JSONObject params) {
		super();
		this.context = context;
		this.params = params;
		this.type = type;
		this.requestId = requestId;
	}
	
	
	private JsonHttpResponseHandler handler =  new JsonHttpResponseHandler() {

		@Override
		public void onSuccess(JSONObject response) {
			try {
				closeDialog();
				Log.d(TAG, "success o= " + response.toString());
				if (successReslut != null) {
					successReslut.getResluts(response);
				}
			 } catch (Exception e) {
				e.printStackTrace();
				closeDialog();
			}
		}

		@Override
		public void onFailure(Throwable error, String content) {
			Log.i(TAG, "onFailure = " + content);
			if (error instanceof ConnectTimeoutException
				|| error instanceof SocketTimeoutException
				|| error instanceof SocketException){
				CommonUtil.showToast(TIMEOUT, context);
		    }else if(error instanceof UnknownHostException){
		    	CommonUtil.showToast(UNKNOWHOST, context);
		    }else if(error instanceof ClientProtocolException){
		    	CommonUtil.showToast(INTERNET_INTERRUPT, context);
		    }else{
		    	CommonUtil.showToast(content, context);
		    }
			if (listenerFaliureResult != null) {
				listenerFaliureResult.getResluts(content);
			}
			closeDialog();
	  } 
	};

 
	public void executeRequest() {
		
		if (!isNetworkConnected(context)) {
			networkError(context);
			CommonUtil.showToast("请检查网络连接状态", context);
			return;
		}
		try {
			if (isShowDiloag) {
				if (dialog == null) {
					if (TextUtils.isEmpty(alertMsg)) {
						dialog = new CommonWaitDialog(context, "", R.string.load_data);
					}else{
						dialog = new CommonWaitDialog(context, alertMsg, -1);
					}
				}
			}
			Request request = null;
			if (isDownLoadFile) {
				request = new Request(requestId,type, params, fileHandler);
				request.setDownLoadFile(true);
			}else{
				request = new Request(requestId,type, params, handler);
			}
			if (isUploadFile) {
				request.setUploadFile(true);
			}
			if(requestParms != null){
				request.setRequestParms(requestParms);
			}
			if (customerId != -1) {
				request.setCustomId(customerId);
			}
			if (user_fk != -1) {
				request.setUser_fk(user_fk);
			}
			if (task_fk != -1) {
				request.setTask_fk(task_fk);
			}
			if(productId != 0){
				request.setProductId(productId);
			}
			CoreManager.getInstance().postRequest(request);

		} catch (Exception e) {
			closeDialog();
		}
	}
	private SuccessReslut successReslut;
	public interface SuccessReslut{
		public void getResluts(JSONObject response);
	}
	
	public void setSuccessListener(SuccessReslut listenerResult){
		this.successReslut = listenerResult;
	}
	
	private FaliureResult listenerFaliureResult;
	
	public interface FaliureResult{
		public void getResluts(String msg);
	}
	public void setFaliureResult(FaliureResult listenerResult){
		this.listenerFaliureResult = listenerResult;
	}
	
	
	private FileSuccessReslut filesuccessReslut;
	public interface FileSuccessReslut{
		public void getResluts(int statesCode,byte[] binaryData);
	}
	
	public void setFileSuccessListener(FileSuccessReslut listenerResult){
		this.filesuccessReslut = listenerResult;
	}
	
	private FileFaliureResult filelistenerFaliureResult;
	
	public interface FileFaliureResult{
		public void getResluts(byte[] binaryData);
	}
	public void setFileFaliureResult(FileFaliureResult listenerResult){
		this.filelistenerFaliureResult = listenerResult;
	}
	public static boolean isNetworkConnected(Context context) {
	        if (context != null) {
	            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
	                    .getSystemService(Context.CONNECTIVITY_SERVICE);
	            NetworkInfo mNetworkInfo = mConnectivityManager
	                    .getActiveNetworkInfo();
	            if (mNetworkInfo != null) {
	                return mNetworkInfo.isConnected();
	            }
	        }
	        return false;
	 }
	
	 
	// 文件下载使用
	private BinaryHttpResponseHandler fileHandler = new BinaryHttpResponseHandler(){

		@Override
		public void onSuccess(int statusCode, byte[] binaryData) {
			super.onSuccess(statusCode, binaryData);
//			CommonUtil.showToast("文件下载成功：statusCode="+statusCode, context);
			if (filesuccessReslut != null) {
				filesuccessReslut.getResluts(statusCode,binaryData);
			}
		}

		@Override
		protected void sendFailureMessage(Throwable e, byte[] responseBody) {
			super.sendFailureMessage(e, responseBody);
			try {
				CommonUtil.showToast("文件下载失败", context);
				if (filelistenerFaliureResult != null) {
					filelistenerFaliureResult.getResluts(responseBody);
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
	};
	
	
	private void closeDialog() {
		if (dialog != null) {
			dialog.clearAnimation();
			dialog = null;
		}
	}
	
	
	public static void networkError(final Context activity) {
	        CommonDialog internetSett = new CommonDialog(activity);
	        internetSett.setTitle(R.string.alert);
	        internetSett.setMessage(R.string.can_not_connected_server);
	        internetSett.setPositiveButton(new BtnClickedListener() {

	            public void onBtnClicked() {

	                // 跳转到网络设置界面
	                if (android.os.Build.VERSION.SDK_INT > 10) {
	                	activity.startActivity(new Intent(
	                            Settings.ACTION_SETTINGS));
	                } else {
	                	activity.startActivity(new Intent(
	                            Settings.ACTION_WIRELESS_SETTINGS));
	                }
	            }
	        }, R.string.set_internet_btn);
	        internetSett.setCancleButton(null, R.string.btn_cancle);
	        internetSett.showDialog();
	    }
 }
 