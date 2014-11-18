package com.incito.finshine.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.codans.blossom.datepicker.DlgDatePicker;
import com.custom.view.CommFlipDot;
import com.custom.view.CommonWaitDialog;
import com.custom.view.DlgCitySelected;
import com.custom.view.DlgCommFilter;
import com.custom.view.DlgCommFilter.RefreshFilterListener;
import com.custom.view.MyViewPager;
import com.incito.finshine.R;
import com.incito.finshine.activity.adapter.AdapterCustomerBaseDetailViewPager;
import com.incito.finshine.entity.Customer;
import com.incito.finshine.manager.CoreManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.network.RequestDefine;
import com.incito.finshine.network.Request.RequestType;
import com.incito.utility.CommonUtil;
import com.incito.utility.DateUtil;
import com.incito.utility.RegUtil;
import com.incito.wisdomsdk.net.http.BinaryHttpResponseHandler;

/**
 * 
 * <dl>
 * <dt>FragmentCustomDetailInfo.java</dt>
 * <dd>Description:编辑状态的客户管理的基本信息的ViewPager</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年6月8日 下午9:14:32</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class FragmentCustomBaseInfoEdit extends FragmentDetail implements OnItemSelectedListener, OnDateChangedListener {
	private static final String TAG = FragmentCustomBaseInfoEdit.class.getSimpleName();

	View v;
	private Context context;
	private static Customer customer;

	private MyViewPager mPager;// 页卡内容
	private List<View> listViews; // Tab页面列表
	private View view1, view2, view3, view4;
	private static int currentView = 0;
	private CommonWaitDialog dialog = null;
	private long birthday = 0;
    private int age = 0;
	EditText nameEdit;
	Button btnCity;
	TextView investNumber;
	EditText cellPhoneEdit;
	Spinner spinnerAnnualIncome;
	RadioGroup radioGroupGender;
	TextView totalAsset;
	/*EditText ageEdit;*/
	EditText editAge;
	TextView txtEdit;
	EditText currentInvestValue;
	Spinner spinnerDiploma;
	ImageView icon;

	/*spinnerInvestSource应该为Spinner*/

	Button spinnerInvestSource;
	EditText email1Edit;
	Spinner spinnerJob;
	EditText keywordEdit;

	EditText nickNameEdit;
	EditText employerEdit;
	Spinner spinnerNationality;
	EditText positionEdit;
	EditText editBirthday;
	Spinner spinnerInvestPreference;
	RadioGroup radioGroupMarried;
	EditText familyInfoEdit;

	EditText contactAddressEdit;
	EditText ecNameEdit;
	EditText residentialAddressEdit;
	EditText editEcellPhone;
	EditText editTelephone;
	EditText ecRelationEdit;
	EditText faxEdit;
	EditText socialNumberEdit;
	EditText zipcodeEdit;

	ImageView imageView1;
	ImageView imageView2;
	// EditText IDNumberEdit;
	// EditText passportEntryTimeEdit;

	TextView IDNumber;
	TextView passportEntryTime;
    
	private int count = 0;
    private int k = 0;
	public static FragmentCustomBaseInfoEdit newInstance(int id, int position, Customer cus) {

		FragmentCustomBaseInfoEdit f = new FragmentCustomBaseInfoEdit();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);

		currentView = position;
		customer = cus;
		return f;
	}
	
	public static FragmentCustomBaseInfoEdit newInstance(int id, int position, Customer cus,int k) {
		FragmentCustomBaseInfoEdit f = new FragmentCustomBaseInfoEdit();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);
		currentView = position;
		customer = cus;
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// customer = ((ActCustomerDetail) getActivity()).getCustomer();

		if (container == null) {
			return null;
		}
		v = inflater.inflate(R.layout.frag_customer_base_info_viewpager, container, false);// frag_base_custom_detail
		this.context = this.getActivity();
		initViewPager();
		sendMessage();
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	CommFlipDot flipDot = null;

	private void initViewPager() {
		mPager = (MyViewPager) v.findViewById(R.id.viewPagerCustomerDetail);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getActivity().getLayoutInflater();
		view1 = mInflater.inflate(R.layout.frag_customer_base_info1_edit, null);
		view2 = mInflater.inflate(R.layout.frag_customer_base_info2_edit, null);
		view3 = mInflater.inflate(R.layout.frag_customer_base_info3_edit, null);
		
		listViews.add(view1);
		listViews.add(view2);
		listViews.add(view3);
		int dots = 3;
		
		//for bug 5010 yangjian
		/*if(customer.getBindingStatusId()>0 && k ==0 ){*/
		if(k == 0){
			view4 = mInflater.inflate(R.layout.frag_customer_base_info4, null);
			listViews.add(view4);
			dots = 4;
		}
		initUI();
		initData(customer);

		mPager.setAdapter(new AdapterCustomerBaseDetailViewPager(listViews, customer));
		mPager.setCurrentItem(currentView);
		flipDot = new CommFlipDot(getActivity(), dots, (LinearLayout) v.findViewById(R.id.ltDot));
		flipDot.setSeletion(currentView);
		mPager.setOnPageChangeListener(new MyPage(flipDot));

		// initChildPager();
	}

	private void initUI() {

		nameEdit = (EditText) view1.findViewById(R.id.editName);
		nameEdit.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				mPager.setPagingEnabled(true);
			}
		});
		nameEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPager.setPagingEnabled(true);
			}
		});

		btnCity = (Button) view1.findViewById(R.id.btnCity);
		investNumber = (TextView) view1.findViewById(R.id.textInvestNumber);
		cellPhoneEdit = (EditText) view1.findViewById(R.id.editCellPhone1);
		icon = (ImageView) view1.findViewById(R.id.imageBarTop);
		cellPhoneEdit.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				mPager.setPagingEnabled(true);
			}
		});
		cellPhoneEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPager.setPagingEnabled(true);
			}
		});
		spinnerAnnualIncome = (Spinner) view1.findViewById(R.id.spinnerAnnualIncome);
		radioGroupGender = (RadioGroup) view1.findViewById(R.id.radioGroupGender);
		totalAsset = (TextView) view1.findViewById(R.id.textTotalAsset);

		
		currentInvestValue = (EditText) view1.findViewById(R.id.editCurrentInvestValue);
		spinnerDiploma = (Spinner) view1.findViewById(R.id.spinnerDiploma);
		spinnerInvestSource = (Button) view1.findViewById(R.id.spinnerInvestSource);
		email1Edit = (EditText) view1.findViewById(R.id.editEmail1);
		spinnerJob = (Spinner) view1.findViewById(R.id.spinnerJob);
		keywordEdit = (EditText) view1.findViewById(R.id.editKeyword);
		editAge = (EditText) view1.findViewById(R.id.editAge);
		
		nickNameEdit = (EditText) view2.findViewById(R.id.editNickName);
		employerEdit = (EditText) view2.findViewById(R.id.editEmployer);
		spinnerNationality = (Spinner) view2.findViewById(R.id.spinnerNationality);
		positionEdit = (EditText) view2.findViewById(R.id.editPosition);
		editBirthday = (EditText) view2.findViewById(R.id.editBirthday);
		
		editBirthday.setKeyListener(null);
		editBirthday.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					showDatePickerDialog(R.string.title_dialog_birthday, FragmentCustomBaseInfoEdit.this);
				}
			}
		});

		editAge.setKeyListener(null);
		editAge.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					showDatePickerDialog(R.string.title_dialog_birthday, FragmentCustomBaseInfoEdit.this);
				}
			}
		});
		
		
		spinnerInvestPreference = (Spinner) view2.findViewById(R.id.spinnerInvestPreference);
		radioGroupMarried = (RadioGroup) view2.findViewById(R.id.radioGroupMarried);
		familyInfoEdit = (EditText) view2.findViewById(R.id.editFamilyInfo);

		contactAddressEdit = (EditText) view3.findViewById(R.id.editContactAddress);
		ecNameEdit = (EditText) view3.findViewById(R.id.editEcName);
		residentialAddressEdit = (EditText) view3.findViewById(R.id.editResidentialAddress);
		editEcellPhone = (EditText) view3.findViewById(R.id.editEcellPhone);
		editTelephone = (EditText) view3.findViewById(R.id.editTelephone);
		ecRelationEdit = (EditText) view3.findViewById(R.id.editEcRelation);
		faxEdit = (EditText) view3.findViewById(R.id.editFax);
		socialNumberEdit = (EditText) view3.findViewById(R.id.editSocialNumber);
		zipcodeEdit = (EditText) view3.findViewById(R.id.editZipcode);

		// IDNumberEdit = (EditText) view4.findViewById(R.id.editIDNumber);
		// passportEntryTimeEdit = (EditText) view4
		// .findViewById(R.id.editPassportEntryTime);
		
		//for bug 5010 yangjian
		if (null!=view4) {
			IDNumber = (TextView) view4.findViewById(R.id.textIDNumber);
			passportEntryTime = (TextView) view4.findViewById(R.id.textPassportEntryTime);
			imageView1 = (ImageView) view4.findViewById(R.id.imageView1);
			imageView2 = (ImageView) view4.findViewById(R.id.imageView2);
		}
		
		loadSpinner();
	}

	public void showDatePickerDialog(int title, OnDateChangedListener listener) {
		DlgDatePicker picker = new DlgDatePicker(getActivity(), title, listener);
		picker.show();
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		if (editBirthday != null || editAge != null) {
			editBirthday.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
//				birthday = sdf.parse(editBirthday.getText().toString()).getTime();	
				birthday = DateUtil.formatString2Date(editBirthday.getText().toString(), DateUtil.FORMAT_YYYY_MM_DD).getTime();
				//Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
				Calendar calendar = Calendar.getInstance();
				int nowYear = calendar.get(Calendar.YEAR);
				Log.i("year:", (nowYear-year)+"_"+nowYear+"_"+year);
				editAge.setText(String.valueOf(nowYear-year));
				age = nowYear-year;
//				if(birthday!=0){
//					age = (int) (curDate.getTime() - birthday)/(1000*60*60*24*365);
//				}
//				else {
//				
//					age = curDate.getYear();
//				}
				//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");       
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			/*for bug liying #4979 201408012*/
			/*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");       
			Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
			String str = formatter.format(curDate);       
            editBirthday.setText(str);*/
		}
	}

	public void initData(Customer customer) {

		if (customer != null) {
			nameEdit.setText(customer.getName());
			if (customer.getBindingStatusId() > 0) {
				nameEdit.setEnabled(false);
				spinnerNationality.setEnabled(false);
				cellPhoneEdit.setEnabled(false);
			}

			else {
				nameEdit.setEnabled(true);
				spinnerNationality.setEnabled(true);
				cellPhoneEdit.setEnabled(true);
			}

			btnCity.setText(customer.getCity());

			// 弹出省选城市
			btnCity.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					DlgCitySelected cityDialog = new DlgCitySelected(getActivity(), btnCity.getText().toString());
					cityDialog.setRefreshSortListener(new DlgCitySelected.SelctedCityListener() {

						@Override
						public void selectedCity(String city) {
							btnCity.setText(city);
						}
					});

					cityDialog.showDialog();
				}
			});

			investNumber.setText(String.valueOf(customer.getInvestNumber()));
			cellPhoneEdit.setText(customer.getCellPhone1());


			spinnerAnnualIncome.setSelection(getIncomIndex());

			((RadioButton) radioGroupGender.getChildAt(customer.getGender() == 2 ? 1 : 0)).setChecked(true);
			totalAsset.setText(String.valueOf(customer.getNetAsset()));// getTotalAsset()
			/*ageEdit.setText(String.valueOf(customer.getAge()));*/
			currentInvestValue.setText(customer.getCurrentInvestValue() + "");
			spinnerDiploma.setSelection((customer.getDiploma() - 1), true);

			/*spinnerInvestSource.setText((customer.getInvestSource() == null) ? "保密" : customer.getInvestSource());*/
			/*for bug liying #4976 201408012*/
			spinnerInvestSource.setText(customer.getInvestSource());
			if(customer.getAge()>0){
				editAge.setText(String.valueOf(customer.getAge()));
//				editAge.setText(customer.getAge());
			}
			
			email1Edit.setText(customer.getEmail1());
			spinnerJob.setSelection((int) (customer.getJob() - 1), true);
			keywordEdit.setText(customer.getKeyword());
			
			nickNameEdit.setText(customer.getNickName());
			employerEdit.setText(customer.getEmployer());
			spinnerNationality.setSelection((int) (customer.getNationality() - 1), true);
			positionEdit.setText(customer.getPosition());
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			editBirthday.setText(sdf.format(new Date(customer.getBirthday())));
			editBirthday.setText(DateUtil.getDateTimeByFormatAndMs(customer.getBirthday(), DateUtil.FORMAT_YYYY_MM_DD));
			editAge.setText(String.valueOf(customer.getAge()));
			String[] investArr=getActivity().getResources().getStringArray(R.array.invest_status);
			int investP=(int)customer.getInvestPreference();
			/*if(investP>investArr.length){
				investP=investArr.length;
			}*/
			spinnerInvestPreference.setSelection(investP-1, true);
			
			Log.i(TAG, (customer
					.getMaritalStatus() - 1) + "+++++++++++++=========");
			((RadioButton) radioGroupMarried.getChildAt(customer.getMaritalStatus() == 0 ? 0 : (customer
					.getMaritalStatus() - 1))).setChecked(true);
			familyInfoEdit.setText(customer.getFamilyInfo());

			contactAddressEdit.setText(customer.getContactAddress());
			ecNameEdit.setText(customer.getEcName());
			residentialAddressEdit.setText(customer.getResidentialAddress());
			editEcellPhone.setText(customer.getEcellPhone());
			editTelephone.setText(customer.getTelephone());
			ecRelationEdit.setText(customer.getEcRelation());
			faxEdit.setText(customer.getFax());
			socialNumberEdit.setText(customer.getSocialNumber());
			zipcodeEdit.setText(customer.getZipcode());
			//for bug 5010 yangjian
			if (null!=view4) {
				IDNumber.setText(customer.getIDNumber());
				passportEntryTime.setText(DateUtil.getDateTimeByFormatAndMs(customer.getPassportEntryTime(), DateUtil.FORMAT_YYYY_MM_DD));
				
			}
		}

	}

	/**  
	 * 为兼容后台接口数值修改，需要处理customer.getAnnualIncome()
	 * @return   
	 * int  
	 * @exception   
	 * @since  1.0.0  
	*/
	int getIncomIndex() {
		String[] incomes = getResources().getStringArray(R.array.customer_income);
		int incomeInt = Integer.parseInt(customer.getAnnualIncome() + "") - 1;
		if (incomeInt < 0) {
			incomeInt = 0;
		}
		if (incomeInt > incomes.length - 1) {
			incomeInt = incomes.length - 1;
		}
		return incomeInt;
	}

	private void loadSpinner() {

		initSpinner(spinnerDiploma, getResources().getStringArray(R.array.diploma_status), false);
		initSpinner(spinnerJob, getResources().getStringArray(R.array.customer_job), false);
		initSpinner(spinnerNationality, getResources().getStringArray(R.array.nationality_status), false);
		initSpinner(spinnerInvestPreference, getResources().getStringArray(R.array.invest_status), false);
		initSpinner(spinnerAnnualIncome, getResources().getStringArray(R.array.customer_income), false);

		// 筛选弹出的列表加EditText
		initDlgCommonFilter(spinnerInvestSource, R.array.investSource, R.string.investResource, false, 0);
	}

	private void initDlgCommonFilter(final Button btn, final int listId, final int title, final boolean isSingle,
			final int model) {

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				DlgCommFilter fDeadLind = new DlgCommFilter(getActivity(), listId, title, btn.getText().toString(),
						isSingle, model);
				fDeadLind.setListener(new RefreshFilterListener() {

					@Override
					public void doRefresh(String reslut, int position,boolean b,int title) {

						((Button) v).setText(reslut);
					}
				});
				fDeadLind.setHiddenInput(true);
				fDeadLind.showDialog();
			}
		});

	}

	private void initSpinner(Spinner sp, String[] dataList, boolean isResaveDefaultValue) {

		if (isResaveDefaultValue) {
			sp.setSelection(0, true);
		} else {
			ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.common_spinner_item, dataList);// android.R.layout.simple_spinner_item
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//
			sp.setAdapter(adapter);
			sp.setOnItemSelectedListener(this);
		}
	}

	public Customer saveCustomer() {
		Customer curCustomer = new Customer();// 要拷贝~~~
		// try {
		// curCustomer = customer.clone();
		// } catch (CloneNotSupportedException e) {
		// e.printStackTrace();
		// }
		curCustomer.setId(customer.getId());

		if (TextUtils.isEmpty(nameEdit.getText().toString())) {
			CommonUtil.showToast("姓名不可以不填", getActivity());
			nameEdit.setFocusable(true);
			return null;
		} else {
			curCustomer.setName(nameEdit.getText().toString());
		}
//		curCustomer.setBirthday()
		curCustomer.setCity(btnCity.getText().toString());
		curCustomer.setInvestNumber(Integer.parseInt((investNumber.getText().toString().isEmpty() ? "0" : investNumber
				.getText().toString())));
		if (TextUtils.isEmpty(cellPhoneEdit.getText().toString())) {
			CommonUtil.showToast("电话不可以不填", getActivity());
			cellPhoneEdit.setFocusable(true);
			return null;
		} else if (!RegUtil.isPhoneNumberValid(cellPhoneEdit.getText().toString())) {
			cellPhoneEdit.setFocusable(true);
			mPager.setPagingEnabled(false);
			CommonUtil.showToast("电话输入不符合条件", getActivity());
			return null;
		} else {
			curCustomer.setCellPhone1(cellPhoneEdit.getText().toString());
		}

		curCustomer.setAnnualIncome(spinnerAnnualIncome.getSelectedItemPosition() + 1);
		curCustomer.setGender(radioGroupGender.getCheckedRadioButtonId() - R.id.radioMale + 1);

		curCustomer.setCurrentInvestValue(currentInvestValue.getText().toString().isEmpty() ? 0 : Long
				.valueOf(currentInvestValue.getText().toString()));
		/*curCustomer.setAge(Integer.parseInt((ageEdit.getText().toString().isEmpty() ? "0" : ageEdit.getText()
				.toString())));*/
		// curCustomer.setNetAsset(Double.valueOf(d)totalAsset.getText().toString());
		curCustomer.setDiploma(spinnerDiploma.getSelectedItemPosition() + 1);
		curCustomer.setInvestSource(spinnerInvestSource.getText().toString());// 投资来源

		if ((!TextUtils.isEmpty(email1Edit.getText().toString()))
				&& (!RegUtil.isEmailNumberValid(email1Edit.getText().toString()))) {
			CommonUtil.showToast("邮箱输入不符合条件", getActivity());
			email1Edit.setFocusable(true);
			return null;
		}

		else {
			curCustomer.setEmail1(email1Edit.getText().toString());
		}

		curCustomer.setJob(spinnerJob.getSelectedItemPosition() + 1);
		curCustomer.setKeyword(keywordEdit.getText().toString());

		curCustomer.setNickName(nickNameEdit.getText().toString());
		curCustomer.setEmployer(employerEdit.getText().toString());
		curCustomer.setNationality(spinnerNationality.getSelectedItemPosition() + 1);
		curCustomer.setPosition(positionEdit.getText().toString());
		if(birthday != 0)
		{
			curCustomer.setBirthday(birthday);
		}
		else
		{
			curCustomer.setBirthday(customer.getBirthday());
		}
		curCustomer.setAge(age);
		curCustomer.setInvestPreference(spinnerInvestPreference.getSelectedItemPosition() + 1);
		curCustomer.setMaritalStatus(radioGroupMarried.getCheckedRadioButtonId() - R.id.radioMarried1 + 1);
//		Log.i(TAG, radioGroupMarried.getCheckedRadioButtonId() - R.id.radioMarried1 + 1 + "+++++++++++++=========");
		curCustomer.setFamilyInfo(familyInfoEdit.getText().toString());
        
		curCustomer.setContactAddress(contactAddressEdit.getText().toString());
		curCustomer.setEcName(ecNameEdit.getText().toString());
		curCustomer.setResidentialAddress(residentialAddressEdit.getText().toString());
		curCustomer.setEcellPhone(editEcellPhone.getText().toString());
		curCustomer.setTelephone(editTelephone.getText().toString());
		curCustomer.setEcRelation(ecRelationEdit.getText().toString());
		curCustomer.setFax(faxEdit.getText().toString());
		curCustomer.setSocialNumber(socialNumberEdit.getText().toString());
		curCustomer.setZipcode(zipcodeEdit.getText().toString());

		// curCustomer.setIDNumber(IDNumberEdit.getText().toString());
		// curCustomer.setPassportEntryTime(Integer.parseInt(passportEntryTimeEdit.getText().toString()));

		return curCustomer;
	}

	private class MyPage implements OnPageChangeListener {

		CommFlipDot flipDot = null;

		public MyPage(CommFlipDot flipDot) {
			super();
			this.flipDot = flipDot;
		}

		@Override
		public void onPageScrollStateChanged(int status) {// 2 4 7
			Log.i("lining", "onPageScrollStateChanged");
		}

		@Override
		public void onPageScrolled(int curPage, float arg1, int arg2) {// 1 3 6
																		// 首次就会执行
			if ((curPage == 0) && (count > 0)) {
				if (nameEdit.getText().toString().isEmpty()) {
					nameEdit.setFocusable(true);
					mPager.setPagingEnabled(false);
					CommonUtil.showToast("姓名不可以不填", getActivity());
				} else if (cellPhoneEdit.getText().toString().isEmpty()) {
					cellPhoneEdit.setFocusable(true);
					mPager.setPagingEnabled(false);
					CommonUtil.showToast("电话不可以不填", getActivity());
				} else if (!RegUtil.isPhoneNumberValid(cellPhoneEdit.getText().toString())) {
					cellPhoneEdit.setFocusable(true);
					mPager.setPagingEnabled(false);
					CommonUtil.showToast("电话输入不符合条件", getActivity());
				} else if ((!TextUtils.isEmpty(email1Edit.getText().toString()))
						&& (!RegUtil.isEmailNumberValid(email1Edit.getText().toString()))) {
					email1Edit.setFocusable(true);
					CommonUtil.showToast("邮箱输入不符合条件", getActivity());
				}
				else
					mPager.setPagingEnabled(true);
			} else
				mPager.setPagingEnabled(true);
			if (count == 0)
				count++;
		}

		@Override
		public void onPageSelected(int curPage) {// 5
			Log.i("lining", "onPageSelected");

			if (flipDot != null) {
				flipDot.setSeletion(curPage);
			}
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	public int getNowView() {
		return mPager.getCurrentItem();
	}

	private BinaryHttpResponseHandler binaryHttpResponseHandler=new BinaryHttpResponseHandler(){
		
		@Override
		public void onSuccess(String str)
		{
		}
		@Override
		public void onSuccess(byte[] binaryData) {
			super.onSuccess(binaryData);
			closeDialog();
 			/*if (binaryData != null && binaryData.length != 0) {*/
			if(binaryData != null && binaryData.length != 0){
 			//byte[] pic = android.util.Base64.decode(binaryData, Base64.DEFAULT);
 			BitmapFactory.Options opts = new BitmapFactory.Options();
 
 			opts.inSampleSize = 4; //这个的值压缩的倍数（2的整数倍），数值越小，压缩率越小，图片越清晰
 
 			Bitmap	bmap = BitmapFactory.decodeByteArray(binaryData, 0, binaryData.length, opts);
 			
 			icon.setImageBitmap(bmap);
 		} else{
 			icon.setImageResource(R.drawable.headicon1);
 		}
 			System.out.println("icon="+icon);
		}

		@Override
		public void onFailure(Throwable error, String content) {
//			super.onFailure(error, content);
			closeDialog();
			Log.i(TAG, content);
		}
		
	};
	
	private void closeDialog()
	{
		if(dialog != null)
		{
			dialog.clearAnimation();
			dialog = null;
		}
	}
	
	 //网络请求
		public void sendMessage() {
			 //将理财师ID传入requset中进行判断 0
			Request request = new Request(RequestDefine.MARKET_RQ_UPLOAD_PHOTO,customer.getId(),
					RequestType.GET, null, binaryHttpResponseHandler);
			CoreManager.getInstance().postRequest(request);
//			CoreManager.getInstance().postRequest(request);
			if(dialog == null){
				//context 上下文环境
				dialog = new CommonWaitDialog(context, "", R.string.load_data);
			}
		}
		
		public boolean iSCustomerEditInformationChanged(Customer customer) {

			/**
			 * First page editTexts
			 */
			String name = nameEdit.getText().toString();
			String totalAssetValue = totalAsset.getText().toString();
			String email = email1Edit.getText().toString();
			String keyword = keywordEdit.getText().toString();
			String cellPhone = cellPhoneEdit.getText().toString();
			/**
			 * Detail page editTexts
			 */
			String nickName = nickNameEdit.getText().toString();
			String employer = employerEdit.getText().toString();
			String position = positionEdit.getText().toString();
			String family = familyInfoEdit.getText().toString();

			/**
			 * Connect fragment
			 */
			String contactAddress = contactAddressEdit.getText().toString();
			String ecName = ecNameEdit.getText().toString();
			String residentialAddress = residentialAddressEdit.getText().toString();
			String eiditEcellPhone = editEcellPhone.getText().toString();
			String editTelePhone = editTelephone.getText().toString();
			String ecRelation = ecRelationEdit.getText().toString();
			String fax = faxEdit.getText().toString();
			String socialNumber = socialNumberEdit.getText().toString();
			String zipCode = zipcodeEdit.getText().toString();
			String birthday = editBirthday.getText().toString();

			// Check all editText value
			if (!name.equals(customer.getName())) {
				return true;
			}
			if (!totalAssetValue.equals(String.valueOf(customer.getNetAsset()))) {
				return true;
			}
			if (!email.equals(customer.getEmail1())) {
				return true;
			}
			if (!keyword.equals(customer.getKeyword())) {
				return true;
			}
			if (!cellPhone.equals(customer.getCellPhone1())) {
				return true;
			}

			if (!nickName.equals(customer.getNickName())) {
				return true;
			}

			if (!employer.equals(customer.getEmployer())) {
				return true;
			}

			if (!position.equals(customer.getPosition())) {
				return true;
			}

			if (!family.equals(customer.getFamilyInfo())) {
				return true;
			}

			if (!contactAddress.equals(customer.getContactAddress())) {
				return true;
			}

			if (!ecName.equals(customer.getEcName())) {
				return true;
			}
			if (!residentialAddress.equals(customer.getResidentialAddress())) {
				return true;
			}
			if (!eiditEcellPhone.equals(customer.getEcellPhone())) {
				return true;
			}

			if (!editTelePhone.equals(customer.getTelephone())) {
				return true;
			}

			if (!ecRelation.equals(customer.getEcRelation())) {
				return true;
			}

			if (!fax.equals(customer.getFax())) {
				return true;
			}

			if (!socialNumber.equals(customer.getSocialNumber())) {
				return true;
			}

			if (!zipCode.equals(customer.getZipcode())) {
				return true;
			}
			if (!birthday.equals(new SimpleDateFormat("yyyy-MM-dd")
					.format(new Date(customer.getBirthday())))) {
				return true;
			}

			// Current age has wrong show?
			/*
			 * if (age != customer.getAge()) { return true; }
			 */

			// Check all spinner value

			int selectId = spinnerDiploma.getSelectedItemPosition() + 1;

			if (!((selectId) == customer.getDiploma())) {
				return true;
			}

			if (!((spinnerJob.getSelectedItemPosition() + 1) == customer.getJob())) {
				return true;
			}

			if (!((spinnerNationality.getSelectedItemPosition() + 1) == customer
					.getNationality())) {
				return true;
			}

			if (!((spinnerAnnualIncome.getSelectedItemPosition() + 1) == customer
					.getAnnualIncome())) {
				return true;
			}

			if (!((spinnerInvestPreference.getSelectedItemPosition() + 1) == customer
					.getInvestPreference())) {
				return true;
			}

			// Check all buttons
			if (!(btnCity.getText().toString()).equals(customer.getCity())) {
				return true;
			}

			if (!(spinnerInvestSource.getText().toString()).equals(customer
					.getInvestSource())) {
				return true;
			}

			// Check all radioGroups

			if (radioGroupGender.getChildAt(customer.getGender() - 1).getId() != radioGroupGender
					.getCheckedRadioButtonId()) {
				return true;
			}

			if (radioGroupMarried.getChildAt(customer.getMaritalStatus() - 1)
					.getId() != radioGroupMarried.getCheckedRadioButtonId()) {
				return true;
			}

			return false;

		}
}