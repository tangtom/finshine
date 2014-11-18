package com.incito.finshine.activity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.custom.view.DlgCommFilter;
import com.custom.view.PopDatePicker;
import com.custom.view.DlgCommFilter.RefreshFilterListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.incito.finshine.R;
import com.incito.finshine.common.Constant;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.entity.MarketCsOrder;
import com.incito.finshine.manager.BitmapManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.HttpUtils.FileSuccessReslut;
import com.incito.finshine.network.HttpUtils.SuccessReslut;
import com.incito.finshine.network.LogCito;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.wisdomsdk.event.EventBus;
import com.incito.wisdomsdk.net.http.RequestParams;
import com.incito.wisdomsdk.utils.BitmapUtils;
/**
 * 
 * <dl>
 * <dt>FragmentBindFirst.java</dt>
 * <dd>Description:客户营销  签订合同第二步  小二步 Fragment</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月16日 下午5:00:59</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class FragmentSignFifthStepFive extends FragmentDetail implements OnClickListener{
	
	View view;
	private TextView txtFifthContentOne;
	private Button btnPlanTableSign;
	private TextView txtFaxTableCompany;
	private TextView txtFaxTableSignDate;
	
	public static FragmentSignFifthStepFive newInstance(int id) {

		FragmentSignFifthStepFive f = new FragmentSignFifthStepFive();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);

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
		view = inflater.inflate(R.layout.act_sign_contract_5_5, container, false);
		initUI();
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

	private void initUI(){
//		txtFifthContentOne = (TextView) view.findViewById(R.id.txtFifthContentOne);
//		btnPlanTableSign = (Button) view.findViewById(R.id.btnPlanTableSign);
		txtFaxTableCompany = (TextView) view.findViewById(R.id.txtFaxTableCompany);
		txtFaxTableSignDate = (TextView) view.findViewById(R.id.txtFaxTableSignDate);
		
		
	}
	
	
	

	@Override
	public void onClick(final View v) {
	}
	
}
