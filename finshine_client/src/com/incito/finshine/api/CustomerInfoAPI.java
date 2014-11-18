package com.incito.finshine.api;

import org.json.JSONException;
import org.json.JSONObject;

import com.incito.wisdomsdk.net.http.BasicResponse;
import com.incito.wisdomsdk.net.http.RequestParams;
import com.incito.wisdomsdk.net.http.WisdomCityServerAPI;

public class CustomerInfoAPI extends WisdomCityServerAPI {

	private static final String RELATIVE_URL = "/kingstone/customer/modifyAllCustomer";
	private final String mCustomerInfo;

	public CustomerInfoAPI(String customerInfo) {
		super(RELATIVE_URL);
		this.mCustomerInfo = customerInfo;
	}

	@Override
	protected String getPostString() {
		return mCustomerInfo;
	}

	@Override
	public RequestParams getRequestParams() {
		RequestParams ret = super.getRequestParams();
		ret.put("json", mCustomerInfo);
		return ret;
	}

	@Override
	public CustomerInfoAPIResponse parseResponse(JSONObject json) {
		try {
			return new CustomerInfoAPIResponse(json);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public class CustomerInfoAPIResponse extends BasicResponse {
		public String mCustomerMsg;

		public CustomerInfoAPIResponse(JSONObject json) throws JSONException {
			super(json);
			mCustomerMsg = json.toString();
		}
	}

	@Override
	public int getHttpRequestType() {
		return WisdomCityServerAPI.HTTP_REQUEST_TYPE_POST;
	}
}
