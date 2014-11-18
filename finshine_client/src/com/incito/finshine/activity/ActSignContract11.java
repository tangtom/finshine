package com.incito.finshine.activity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codans.blossom.datepicker.DlgDatePicker;
import com.custom.view.CommonDialog;
import com.custom.view.CommonDialog.BtnClickedListener;
import com.custom.view.DlgCommFilter;
import com.custom.view.DlgCommFilter.RefreshFilterListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.ContractInfoItem;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.MarketCsOrder;
import com.incito.finshine.entity.MarketStateReslut;
import com.incito.finshine.entity.OrderInfoItem;
import com.incito.finshine.entity.Product;
import com.incito.finshine.manager.BitmapManager;
import com.incito.finshine.manager.JsonParse;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FileSuccessReslut;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.LogCito;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.utility.MoneyFormat;
import com.incito.wisdomsdk.net.http.RequestParams;
import com.incito.wisdomsdk.utils.BitmapUtils;

/**
 * 
 * <dl>
 * <dt>ActSignContract.java</dt>
 * <dd>Description:客户营销——合同签订  未用</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月10日 上午11:18:35</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class ActSignContract11 extends FragmentDetail {

	private static int currentView = 0;
	private View view;

	private static final int F_SIGN_FIRST = 0;
	
	public static ActSignContract11 newInstance(int id, int position) {
		ActSignContract11 f = new ActSignContract11();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);

		currentView = position;
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		view = inflater.inflate(R.layout.act_sign_contract11, container, false);
		
		showDetails(currentView);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();

	}
	
	public void showDetails(int id) {

		FragmentDetail details = (FragmentDetail) getFragmentManager()
				.findFragmentById(R.id.details);
		if (details == null || details.getId() != id) {
			switch (id) {
			case F_SIGN_FIRST:
				details = FragmentSignFirst.newInstance(id,0);
				
				break;
				
			default:
				break;
			}
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.details, details);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}
	}

}
