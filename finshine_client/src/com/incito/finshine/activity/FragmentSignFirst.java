package com.incito.finshine.activity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Currency;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
 * 不用
 * <dl>
 * <dt>FragmentBindFirst.java</dt>
 * <dd>Description:客户营销 签订合同part1 Fragment</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月16日 下午5:00:59</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class FragmentSignFirst extends FragmentDetail implements
		OnClickListener {

	View view;
//	private static final int F_SIGN_FIRST_1 = 0;
//	private static final int F_SIGN_FIRST_2 = 1;
//	private static final int F_SIGN_SECOND_1 = 2;
//	private static final int F_SIGN_SECOND_2 = 3;
//	private static final int F_SIGN_SECOND_3 = 4;
//	private static final int F_SIGN_SECOND_4 = 5;
//	private static final int F_SIGN_THIRD_1 = 6;
//	private static final int F_SIGN_THIRD_2 = 7;
//	private static final int F_SIGN_THIRD_3 = 8;
//	private static final int F_SIGN_THIRD_4 = 9;
//	private static final int F_SIGN_FIFTH_1 =10;
//	private static final int F_SIGN_FIFTH_2 = 11;
//	private static final int F_SIGN_FIFTH_3 = 12;
//	private static final int F_SIGN_FIFTH_4 = 13;
//	private static final int F_SIGN_FIFTH_5 = 14;
	
	private static final int F_SIGN_FIRST = 0;
	private static final int F_SIGN_SECOND = 1;
	private static final int F_SIGN_THIRD = 2;
	private static final int F_SIGN_FIFTH = 3;
	
	private static int currentView = 0;
	private static int currentStep = 0;
	
	private  MarketCsOrder marketCs;

	public static FragmentSignFirst newInstance(int id,  int position) {

		FragmentSignFirst f = new FragmentSignFirst();
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
		view = inflater.inflate(R.layout.frag_sign_contract_first, container,
				false);
		initUI();
		showDetails(currentStep, currentView);
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

	private void initUI() {
		
//		marketCs = ((ActCustomerMarketProgress)getActivity()).getMarketCs();
//		// 上一步
//		Button btnPre = (Button) view.findViewById(R.id.btnPre);
//		btnPre.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				/*FragmentDetail details = (FragmentDetail) getFragmentManager()
//						.findFragmentById(R.id.linearStep);
//				boolean ret = details instanceof FragmentSignFirstStepOne;
//				if (ret) {
//					showDetails(F_SIGN_FIRST_1);
//				} else {
//					// 跳到绑定协议第二步
//					((ActCustomerMarketProgress) getActivity()).showDetails(
//							ActCustomerMarketProgress.F_BIND, 1);
//				}*/
//				currentView --;
//				switch(currentView){
//				case -1:
//					((ActCustomerMarketProgress) getActivity()).showDetails(
//							ActCustomerMarketProgress.F_BIND, 1);
//					break;
//				case 0:
//					showDetails(F_SIGN_FIRST_1);
//					break;
//				case 1:
//					showDetails(F_SIGN_FIRST_2);
//					break;
//				case 2:
//					showDetails(F_SIGN_SECOND_1);
//					break;
//				case 3:
//					showDetails(F_SIGN_SECOND_2);
//					break;
//				case 4:
//					showDetails(F_SIGN_SECOND_3);
//					break;
//				case 5:
//					showDetails(F_SIGN_SECOND_4);
//					break;
//				case 6:
//					showDetails(F_SIGN_THIRD_1);
//					break;
//				case 7:
//					showDetails(F_SIGN_THIRD_2);
//					break;
//				case 8:
//					showDetails(F_SIGN_THIRD_3);
//					break;
//				case 9:
//					showDetails(F_SIGN_THIRD_4);
//					break;
//				case 10:
//					showDetails(F_SIGN_FIFTH_1);
//					break;
//				case 11:
//					showDetails(F_SIGN_FIFTH_2);
//					break;
//				case 12:
//					showDetails(F_SIGN_FIFTH_3);
//					break;
//				case 13:
//					showDetails(F_SIGN_FIFTH_4);
//					break;
//				case 14:
//					showDetails(F_SIGN_FIFTH_5);
//					break;
//				default:
//					break;
//				}
//			}
//		});
//
//		// 下一步
//		Button btnNext = (Button) view.findViewById(R.id.btnNext);
//		btnNext.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
////				FragmentDetail details = (FragmentDetail) getFragmentManager()
////						.findFragmentById(R.id.linearStep);
////				boolean ret = details instanceof FragmentSignFirstStepOne;
//				currentView ++;
//				Log.i("", currentView + "-------------");
//				switch(currentView){
//				case 1:
//					showDetails(F_SIGN_FIRST_2);
//					break;
//				case 2:
//					showDetails(F_SIGN_SECOND_1);
//					break;
//				case 3:
//					showDetails(F_SIGN_SECOND_2);
//					break;
//				case 4:
//					showDetails(F_SIGN_SECOND_3);
//					break;
//				case 5:
//					showDetails(F_SIGN_SECOND_4);
//					break;
//				case 6:
//					showDetails(F_SIGN_THIRD_1);
//					break;
//				case 7:
//					showDetails(F_SIGN_THIRD_2);
//					break;
//				case 8:
//					showDetails(F_SIGN_THIRD_3);
//					break;
//				case 9:
//					showDetails(F_SIGN_THIRD_4);
//					break;
//				case 10:
//					showDetails(F_SIGN_FIFTH_1);
//					break;
//				case 11:
//					showDetails(F_SIGN_FIFTH_2);
//					break;
//				case 12:
//					showDetails(F_SIGN_FIFTH_3);
//					break;
//				case 13:
//					showDetails(F_SIGN_FIFTH_4);
//					break;
//				case 14:
//					showDetails(F_SIGN_FIFTH_5);
//					break;
//				case 15:
//					((ActCustomerMarketProgress) getActivity()).showDetails(
//							ActCustomerMarketProgress.F_ORDER_PAYMENT1, 0);
//					break;
//				}
//			}
//		});
     
	}

	public void showDetails(int step, int position) {

		FragmentDetail details = (FragmentDetail) getFragmentManager()
				.findFragmentById(R.id.linearStep);
		if (details == null || details.getId() != step) {
			switch (step) {
//			case F_SIGN_FIRST:
//				details = FragmentSignFirstStepOne.newInstance(step, position, marketCs);
//				break;
////			case F_SIGN_FIRST_2:
////				details = FragmentSignFirstStepTwo.newInstance(id);
////				break;
//			case F_SIGN_SECOND:
//				details = FragmentSignSecondStepOne.newInstance(position);
//				break;
//			case F_SIGN_SECOND_2:
//				details = FragmentSignSecondStepTwo.newInstance(id);
//				break;
//			case F_SIGN_SECOND_3:
//				details = FragmentSignSecondStepThree.newInstance(id);
//				break;
//			case F_SIGN_SECOND_4:
//				details = FragmentSignSecondStepFour.newInstance(id);
//				break;
//			case F_SIGN_THIRD:
//				details = FragmentSignThirdStepOne.newInstance(position);
//				break;
//			case F_SIGN_THIRD_2:
//				details = FragmentSignThirdStepTwo.newInstance(id);
//				break;
//			case F_SIGN_THIRD_3:
//				details = FragmentSignThirdStepThree.newInstance(id);
//				break;
//			case F_SIGN_THIRD_4:
//				details = FragmentSignThirdStepFour.newInstance(id);
//				break;
//			case 	F_SIGN_FIFTH:
//				details = FragmentSignFifthStepOne.newInstance(position);
//				break;
//			case 	F_SIGN_FIFTH_2:
//				details = FragmentSignFifthStepTwo.newInstance(id);
//				break;
//			case 	F_SIGN_FIFTH_3:
//				details = FragmentSignFifthStepThree.newInstance(id);
//				break;
//			case 	F_SIGN_FIFTH_4:
//				details = FragmentSignFifthStepFour.newInstance(id);
//				break;
//			case 	F_SIGN_FIFTH_5:
//				details = FragmentSignFifthStepFive.newInstance(id);
//				break;
			default:
				break;
			}
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.linearStep, details);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}
	}

	@Override
	public void onClick(final View v) {
	}

}
