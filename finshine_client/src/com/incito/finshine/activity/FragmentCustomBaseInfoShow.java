package com.incito.finshine.activity;

import com.android.core.net.http.volley.HttpService;
import com.android.core.util.AppToast;
import com.android.core.util.BitmapUtil;
import com.android.core.util.StrUtil;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.custom.view.CommFlipDot;
import com.custom.view.CommonPopWindow;
import com.custom.view.CommonPopWindow.CallBackCLickEvent;
import com.custom.view.CommonWaitDialog;
import com.custom.view.MyViewPager;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterCustomerBaseDetailViewPager;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.manager.SPManager;
import com.incito.finshine.network.HttpUtils;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.Request.RequestType;
import com.incito.finshine.network.RequestDefine;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.utility.RegUtil;
import com.incito.utility.StringUtil;
import com.incito.wisdomsdk.net.http.BinaryHttpResponseHandler;
import com.net.ImageRequestInco;
import com.net.ImageRequestInputStream;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * <dl>
 * <dt>FragmentCustomContentInfo.java</dt>
 * <dd>Description:客户管理里面的基本信息显示界面 ViewPager</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月10日 上午9:29:35</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class FragmentCustomBaseInfoShow extends FragmentDetail {
	private static final String TAG = FragmentCustomBaseInfoShow.class.getSimpleName();

	private MyViewPager mPager;// 页卡内容
	private List<View> listViews; // Tab页面列表
	private View view1, view2, view3, view4;
	private HttpUtils httpUtils = null;
	private static int currentView = 0;// 判断当前是哪一个

	private View view;

	private static Customer customer;

	private CommonWaitDialog dialog = null;
	private Context context;
	ImageView icon;
	TextView name;
	TextView gender;
	TextView age;
	TextView job;
	TextView diploma;
	TextView city;
	TextView cellPhone1;
	TextView email1;
	TextView annualIncome;
	TextView totalAsset;
	TextView investTotalAsset;
	TextView investNumber;
	TextView investSource;
	TextView keyword;

	TextView nickName;
	TextView employer;
	TextView nationality;
	TextView position;
	TextView birthday;
	TextView investPreference;
	TextView maritalStatus;
	TextView familyInfo;

	TextView contactAddress;
	TextView ecName;
	TextView residentialAddress;
	TextView ecellPhone;
	TextView telephone;// 座机
	TextView ecRelation;
	TextView fax;
	TextView socialNumber;
	TextView zipcode;

	TextView IDNumber;
	TextView passportEntryTime;

	public CommFlipDot flipDot = null;

	public static FragmentCustomBaseInfoShow newInstance(int id, int position, Customer cus) {

		FragmentCustomBaseInfoShow f = new FragmentCustomBaseInfoShow();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);
		currentView = position;
		customer = cus;
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		view = inflater.inflate(R.layout.frag_customer_base_info_viewpager,
				container, false);// frag_base_custom_normal
		this.context = this.getActivity();
		
		flipDot = new CommFlipDot(getActivity(), 4,
				(LinearLayout) view.findViewById(R.id.ltDot));

		initViewPager();
		sendMessage(binaryHttpResponseHandler, customer.getId());
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		// customer = ((ActCustomerDetail) getActivity()).getCustomer();

		super.onStart();
	}

	private void initViewPager() {

		mPager = (MyViewPager) view.findViewById(R.id.viewPagerCustomerDetail);
		// mPager.setPagingEnabled(true);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getActivity().getLayoutInflater();
		view1 = mInflater.inflate(R.layout.frag_customer_base_info1, null);
		listViews.add(view1);
		view2 = mInflater.inflate(R.layout.frag_customer_base_info2, null);
		listViews.add(view2);
		view3 = mInflater.inflate(R.layout.frag_customer_base_info3, null);
		listViews.add(view3);
		view4 = mInflater.inflate(R.layout.frag_customer_base_info4, null);
		listViews.add(view4);

		initUI();
		initUIData(customer);

		mPager.setAdapter(new AdapterCustomerBaseDetailViewPager(listViews, customer));
		mPager.setCurrentItem(currentView);
		flipDot.setSeletion(currentView);
		mPager.setOnPageChangeListener(new MyPage(flipDot));
		// sendMessage();
	}

	private void initUI() {
		icon = (ImageView) view1.findViewById(R.id.imageIcon);
//		String url = "http://222.208.168.90:8088/app/sales/1/customers/1/photo";
//		HttpService.VOLLEY.startImageLoader(icon, url);
		name = (TextView) view1.findViewById(R.id.textName);
		gender = (TextView) view1.findViewById(R.id.textGender);
		age = (TextView) view1.findViewById(R.id.textAge);
		job = (TextView) view1.findViewById(R.id.textJob);
		diploma = (TextView) view1.findViewById(R.id.textDiploma);
		city = (TextView) view1.findViewById(R.id.textCity);
		cellPhone1 = (TextView) view1.findViewById(R.id.textCellPhone1);
		email1 = (TextView) view1.findViewById(R.id.textEmail1);
		annualIncome = (TextView) view1.findViewById(R.id.textAnnualIncome);
		totalAsset = (TextView) view1.findViewById(R.id.textTotalAsset);
		investTotalAsset = (TextView) view1.findViewById(R.id.textCurrentInvestValue);
		investNumber = (TextView) view1.findViewById(R.id.textInvestNumber);
		investSource = (TextView) view1.findViewById(R.id.textInvestSource);
		keyword = (TextView) view1.findViewById(R.id.textKeyword);

		nickName = (TextView) view2.findViewById(R.id.textNickName);
		employer = (TextView) view2.findViewById(R.id.textEmployer);
		nationality = (TextView) view2.findViewById(R.id.textNationality);
		position = (TextView) view2.findViewById(R.id.textPosition);
		birthday = (TextView) view2.findViewById(R.id.textBirthday);
		investPreference = (TextView) view2.findViewById(R.id.textInvestPreference);
		maritalStatus = (TextView) view2.findViewById(R.id.textMaritalStatus);
		familyInfo = (TextView) view2.findViewById(R.id.textFamilyInfo);

		contactAddress = (TextView) view3.findViewById(R.id.textContactAddress);
		ecName = (TextView) view3.findViewById(R.id.textEcName);
		residentialAddress = (TextView) view3.findViewById(R.id.textResidentialAddress);
		ecellPhone = (TextView) view3.findViewById(R.id.textEcellPhone);
		telephone = (TextView) view3.findViewById(R.id.textTelephone);
		ecRelation = (TextView) view3.findViewById(R.id.textEcRelation);
		fax = (TextView) view3.findViewById(R.id.textFax);
		socialNumber = (TextView) view3.findViewById(R.id.textSocialNumber);
		zipcode = (TextView) view3.findViewById(R.id.textZipcode);

		IDNumber = (TextView) view4.findViewById(R.id.textIDNumber);
		passportEntryTime = (TextView) view4.findViewById(R.id.textPassportEntryTime);
	}

	public void initUIData(Customer customer) {
		if (customer != null) {
			final Customer c = customer;
			icon.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					final List<Integer> list = new ArrayList<Integer>();
					list.add(R.drawable.customer_manager_email);
					list.add(R.drawable.customer_manager_tel);
					list.add(R.drawable.customer_manager_sms);
					CommonPopWindow popWindow = new CommonPopWindow(getActivity(), v, list, R.drawable.popbg_custom_manager_content,
							CommonPopWindow.LEFT_2_RIGHT);
					// 回调函数
					popWindow.setCallBackCLickEvent(new CallBackCLickEvent() {

						@Override
						public void doClick(int position, Object obj) {
							switch (position) {
							case 0:
//								if(!StringUtil.isEmpty(c.getEmail1())&&!c.getEmail1().equalsIgnoreCase("null")&&RegUtil.isEmailNumberValid(c.getEmail1())){
//									String[]emailsStrings={c.getEmail1()};
//									CommonUtil.sendEmail(getActivity(),emailsStrings,"", "");
//								}
//								else {
//									CommonUtil.showToast("邮件号码为空", getActivity());
//									return;
//								}
								CommonUtil.showToast("show eamil", getActivity());
								
								break;
							case 1:
								Intent telIn = new Intent(Intent.ACTION_DIAL);
								telIn.setData(Uri.parse("tel:" + c.getCellPhone1()));
								getActivity().startActivity(telIn);
								break;
							case 2:
								CommonUtil.sendSms(getActivity(), c.getCellPhone1());
								break;

							default:
								break;
							}

						}
					});
					popWindow.showPopWindow();
				}
			});

			name.setText(customer.getName());
			gender.setText(customer.getGender() == 1 ? "男" : (customer.getGender() == 2 ? "女" : "未知"));
			age.setText(String.valueOf(customer.getAge()));
			job.setText(getActivity().getResources().getStringArray(R.array.customer_job)[(int) ((customer.getJob() == 0 ? 0 : customer.getJob() - 1))]);
			diploma.setText(getResources().getStringArray(R.array.diploma_status)[((customer.getDiploma() == 0 || customer.getDiploma() > 5) ? 0 : customer
					.getDiploma() - 1)]);
			city.setText(trimString(customer.getCity()));
			cellPhone1.setText(customer.getCellPhone1());
			email1.setText(customer.getEmail1());
			/* for bug #4549 liying 20140814 */

			annualIncome.setText(getIncomIndex());

			totalAsset.setText(String.valueOf(customer.getNetAsset()));// getTotalAsset()
			investTotalAsset.setText(customer.getCurrentInvestValue() + "");
			investNumber.setText(String.valueOf(customer.getInvestNumber()));
			investSource.setText(trimString(customer.getInvestSource()));
			keyword.setText(customer.getKeyword());

			nickName.setText(trimString(customer.getNickName()));
			employer.setText(trimString(customer.getEmployer()));

			nationality.setText(getActivity().getResources().getStringArray(R.array.nationality_status)[(int) (customer.getNationality() == 0 ? 0 : customer
					.getNationality() - 1)]);
			position.setText(trimString(customer.getPosition()));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			birthday.setText(sdf.format(new Date(customer.getBirthday())));
			String[] investArr = getActivity().getResources().getStringArray(R.array.invest_status);
			int investP = (int) customer.getInvestPreference();
			int index = 0;
			if (investP-1 > investArr.length) {
				investP = investArr.length;
			}
			index = investP - 1;
			if (index < 0)
				index = 0;
			investPreference.setText(investArr[index]);
			maritalStatus.setText(getActivity().getResources().getStringArray(R.array.married_status)[(int) ((customer.getMaritalStatus() == 0 ? 0 : customer
					.getMaritalStatus() - 1))]);
			familyInfo.setText(trimString(customer.getFamilyInfo()));

			contactAddress.setText(trimString(customer.getContactAddress()));
			ecName.setText(trimString(customer.getEcName()));
			residentialAddress.setText(trimString(customer.getResidentialAddress()));
			ecellPhone.setText(trimString(customer.getEcellPhone()));
			telephone.setText(trimString(customer.getTelephone()));
			ecRelation.setText(trimString(customer.getEcRelation()));
			fax.setText(trimString(customer.getFax()));
			socialNumber.setText(trimString(customer.getSocialNumber()));
			zipcode.setText(trimString(customer.getZipcode()));

			IDNumber.setText(customer.getIDNumber());
			passportEntryTime.setText(sdf.format(new Date(customer
					.getPassportEntryTime())));
			if(StrUtil.isNotBlank(customer.getPassportEntryTimeStr()))//add by SGDY
			{
				passportEntryTime.setText(DateUtil.formatDate2String(DateUtil.formatString2Date(customer.getPassportEntryTimeStr(), DateUtil.FORMAT_YYYY_MM_DD), DateUtil.FORMAT_YYYY_MM_DD));
			}

		}
	}

	/**
	 * 为兼容后台接口数值修改，需要处理customer.getAnnualIncome()
	 * 
	 * @return int
	 * @exception
	 * @since 1.0.0
	 */
	String getIncomIndex() {
		String[] incomes = getResources().getStringArray(R.array.customer_income);
		int incomeInt = Integer.parseInt(customer.getAnnualIncome() + "") - 1;
		if (incomeInt < 0) {
			incomeInt = 0;
		}
		if (incomeInt > incomes.length - 1) {
			incomeInt = incomes.length - 1;
		}
		return incomes[incomeInt];
	}

	private String trimString(String src) {
		if (src == null || src.trim().isEmpty())
			return "未知";
		else
			return src;

	}

	private class MyPage implements OnPageChangeListener {

		CommFlipDot flipDot = null;

		public MyPage(CommFlipDot flipDot) {
			super();
			
			this.flipDot = flipDot;
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			if (flipDot != null) {
				flipDot.setSeletion(arg0);
			}
		}

	}

	public int getNowView() {
		return mPager.getCurrentItem();
	}

	
	private BinaryHttpResponseHandler binaryHttpResponseHandler = new BinaryHttpResponseHandler() {

		@Override
		public void onSuccess(byte[] binaryData) {
			super.onSuccess(binaryData);
			Bitmap bitmap = BitmapUtil.Bytes2Bimap(binaryData);
			if (bitmap != null) {
				icon.setImageBitmap(bitmap);
			} else{
	 			icon.setImageResource(R.drawable.headicon1);
			}
		}
		
		@Override
		public void onFailure(Throwable error,String content){
			Log.i(TAG, content);
			icon.setImageResource(R.drawable.ts_avatar2);
		}

	};

	/* 网络请求 */
	public void sendMessage(BinaryHttpResponseHandler binaryHttpResponseHandler,long customerId ) {
		Request request = new Request(RequestDefine.MARKET_RQ_DOWNLOAD_PHOTO, customerId, RequestType.GET, null,
				binaryHttpResponseHandler);
		request.setDownLoadFile(true);
		CoreManager.getInstance().postRequest(request);
	}
	
}
