package com.incito.finshine.network;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.params.ClientPNames;

import android.util.Base64;
import android.util.Log;

import com.incito.finshine.manager.SPManager;
import com.incito.utility.StringUtil;
import com.incito.wisdomsdk.net.http.AsyncHttpClient;

public class NetworkManager {
	private final String TAG = NetworkManager.class.getSimpleName();
	private AsyncHttpClient client;
	private static NetworkManager networkManager = null;

	String userName,password;
	public void setLoginVariable(String username,String password)//ActActivity传递username,passord
	{
		//client.setBasicAuth(username, password);
		this.userName=username;
		this.password=password;
	}

	public static NetworkManager getInstance() {
		if (null == networkManager) {
			networkManager = new NetworkManager();
		}
		return networkManager;
	}
	
//	public static NetworkManager getInstanceByVariable(String username,String password){
//		if(null == networkManager){
//			networkManager = new NetworkManager();
//		}else if (!"".equals(username)) {
//			networkManager = new NetworkManager();
//		}
//		return networkManager;
//	}


	NetworkManager() {
		client = new AsyncHttpClient();
	}

//	NetworkManager() {
//		client = new AsyncHttpClient();
//		client.setBasicAuth("u0000001", "123456");
//	}
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
	public boolean postRequest(final Request request) {
		Log.i(TAG, "postRequest = " + request.getUrl());

		//client.addHeader("Authorization", "Basic dTAwMDAwMDE6MTIzNDU2");
		String loginFlagString=SPManager.getInstance().getStringValue(SPManager.LOGINFLAG);
		
		if(!StringUtil.isEmpty(loginFlagString)&&loginFlagString.equals("1")){
			String auth=SPManager.getInstance().getStringValue(SPManager.USERNAME)+":"+new String(Base64.decode(SPManager.getInstance().getStringValue(SPManager.PWD), Base64.DEFAULT));
			String secretAuthString=Base64.encodeToString(auth.getBytes(), Base64.DEFAULT);
			client.addHeader("Authorization", "Basic "+replaceBlank(secretAuthString));
		}
		else {
			String auth=this.userName+":"+this.password;
			String secretAuthString=Base64.encodeToString(auth.getBytes(), Base64.DEFAULT);
			client.addHeader("Authorization", "Basic "+replaceBlank(secretAuthString));
		}
		
  
		//client.addHeader("Authorization", "Basic dTAwMDAwMDE6MTIzNDU2");
		
		
		//Log.i("password", SPManager.getInstance().getStringValue(SPManager.PWD));
		//Log.i("password1",new String(Base64.decode(SPManager.getInstance().getStringValue(SPManager.PWD), Base64.DEFAULT)));
		//Log.i("secretAuthString", secretAuthString);
		//Log.i("auth", auth);
		//String loginFlagString=SPManager.getInstance().getStringValue(SPManager.LOGINFLAG);
//		if(!StringUtil.isEmpty(loginFlagString)&&loginFlagString.equals("1")){
//			//client.setBasicAuth(SPManager.getInstance().getStringValue("name"), "123456");
//			String auth=SPManager.getInstance().getStringValue(SPManager.USERNAME)+":"+new String(Base64.decode(SPManager.getInstance().getStringValue(SPManager.PWD), Base64.DEFAULT));
//			//Log.i("auth", auth);
//			//Log.i("", msg);
//			//Log.i("password", SPManager.getInstance().getStringValue(SPManager.PWD));
//			//Log.i("password1",new String(Base64.decode(SPManager.getInstance().getStringValue(SPManager.PWD), Base64.DEFAULT)));
//			String secretAuthString=Base64.encodeToString(auth.getBytes(), Base64.DEFAULT);
//			//client.addHeader("Authorization", "Basic "+secretAuthString);
//		}
		
		
		switch (request.getType()) {
		case POST:
			try {
				if (request.isUploadFile()) {
					// 如果是上传文件，就进行上传文件参数
					//setLoginVariable("u0000001","123456");
					//client.setBasicAuth("u0000001","123456");
					
					client.post(null,request.getUrl(),request.getRequestParms(),request.getHandler());
					client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true); 
				}else{ 
					try {
						if(request.getArray() != null){
							client.post(null, request.getUrl(), request.getArray(),
									"application/json", request.getHandler());
						}else{
							client.post(null, request.getUrl(), request.getParams(),
									"application/json", request.getHandler());
						}
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case PUT:
			try {
				client.put(null, request.getUrl(), request.getParams(),"application/json", request.getHandler());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			break;
		case GET:
			if (request.isUploadFile() || request.isDownLoadFile()) {
				client.get(null,request.getUrl(),null,null,request.getHandler());
			}else{
				client.get(request.getUrl(), request.getHandler());
//				client.getUser();
			}
			break;
		case DELETE:
			client.delete(request.getUrl(), request.getHandler());
			break;
		default:
			break;
		}

		return true;
	}

	public boolean sendRequest() {
		return true;
	}
	
}
