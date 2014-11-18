package com.incito.finshine.activity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.custom.view.CommFlipDot;
import com.incito.finshine.R;
import com.incito.finshine.activity.FragmentCustomFinancialInfo.ViewHolder1;
import com.incito.finshine.activity.FragmentCustomFinancialInfo.ViewHolder2;
import com.incito.finshine.activity.adapter.AdapterCustomerBaseDetailViewPager;
import com.incito.finshine.entity.AnnualIncomExpend;
import com.incito.finshine.entity.AssetInfo;

/**
 * 
 * <dl>
 * <dt>FragmentCustomFinancialEdit.java</dt>
 * <dd>Description:客户管理——经济状况——理财目标可编辑的fragment</dd>
 * <dd>CreateDate: 2014年5月13日 下午2:17:03</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class FragmentCustomFinancialEdit extends FragmentDetail implements
		TextWatcher {
	private static final String TAG = FragmentCustomFinancialEdit.class
			.getSimpleName();

	private ViewPager mPager;// 页卡内容
	private List<View> listViews; // Tab页面列表
	private View view1, view2, view3, view4;

	private static int currentView = 0;

	View v;

	private static AssetInfo assetInfo;
	private static AnnualIncomExpend incomeInfo;

	ViewHolder1 holder1;
	ViewHolder2 holder2;

	EditText cash;
	EditText demanDeposit;
	EditText fixDeposit;
	EditText stock;
	EditText fund;
	EditText bond;
	EditText estate;
	EditText otherAsset;
	EditText totalAsset;
	EditText houseFundLoan;
	EditText houseBusinessLoan;
	EditText carLoan;
	EditText consumerLoan;
	EditText renovationLoan;
	EditText privateLoan;
	EditText unpaidCreditCard;
	EditText otherLoan;
	EditText totalLoan;
	EditText netAsset;

	EditText income;
	EditText spouseIncome;
	EditText endBonus;
	EditText rental;
	EditText interest;
	EditText dividends;
	EditText otherDividends;
	EditText otherFixIncome;
	EditText otherUnfixIncome;
	EditText totalIncome;
	EditText livingCost;
	EditText housExpense;
	EditText medicExpense;
	EditText eduExpense;
	EditText insurance;
	EditText travel;
	EditText alimony;
	EditText otherFixExpense;
	EditText otherUnfixExpense;
	EditText totalExpenditure;
	EditText annualBalance;

	private EditText historicalView;

	private StringBuffer goal = new StringBuffer();

	public static FragmentCustomFinancialEdit newInstance(int id, int position,
			AssetInfo asset, AnnualIncomExpend income) {

		FragmentCustomFinancialEdit f = new FragmentCustomFinancialEdit();
		Bundle args = new Bundle();
		args.putInt(FIELD_ID, id);
		f.setArguments(args);

		currentView = position;
		assetInfo = asset;
		incomeInfo = income;
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		v = inflater.inflate(R.layout.frag_customer_base_info_viewpager,
				container, false);// custom_economy_financial_goal_edit
		
		initViewPager();

		init();
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

	private void initViewPager() {
		mPager = (ViewPager) v.findViewById(R.id.viewPagerCustomerDetail);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getActivity().getLayoutInflater();
		view1 = mInflater.inflate(
				R.layout.frag_custom_economy_financial_goal_edit, null);
		view2 = mInflater.inflate(
				R.layout.frag_custom_economy_family_assets_edit, null);
		view3 = mInflater.inflate(
				R.layout.frag_custom_economy_family_income_edit, null);
		view4 = mInflater.inflate(
				R.layout.frag_custom_economy_historical_invest_edit, null);
		listViews.add(view1);
		listViews.add(view2);
		listViews.add(view3);
		listViews.add(view4);
		mPager.setAdapter(new AdapterCustomerBaseDetailViewPager(listViews,
				null));
		mPager.setCurrentItem(currentView);
		flipDot = new CommFlipDot(getActivity(), 4,
				(LinearLayout) v.findViewById(R.id.ltDot));
		flipDot.setSeletion(currentView);
		mPager.setOnPageChangeListener(new MyPage(flipDot));
	}

	CommFlipDot flipDot = null;

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

	public void init() {

		holder1 = new ViewHolder1();
		holder2 = new ViewHolder2();
		initView1();
		initView2();
		initView3();
		initView4();

		initDate(assetInfo);
		initDate(incomeInfo);
	}

	private void initView1() {
		String[] financial = getActivity().getResources().getStringArray(
				R.array.financial_goal);
		for (int i = 0; i < financial.length; i++) {
			CheckBox cb = (CheckBox) view1.findViewById(R.id.financialBox1 + i);
			cb.setText(financial[i]);
		}
	}

	private void initView2() {
		cash = (EditText) view2.findViewById(R.id.cash);
		cash.addTextChangedListener(this);
		demanDeposit = (EditText) view2.findViewById(R.id.demanDeposit);
		demanDeposit.addTextChangedListener(this);
		fixDeposit = (EditText) view2.findViewById(R.id.fixDeposit);
		fixDeposit.addTextChangedListener(this);
		stock = (EditText) view2.findViewById(R.id.stock);
		stock.addTextChangedListener(this);
		fund = (EditText) view2.findViewById(R.id.fund);
		fund.addTextChangedListener(this);
		bond = (EditText) view2.findViewById(R.id.bond);
		bond.addTextChangedListener(this);
		estate = (EditText) view2.findViewById(R.id.estate);
		estate.addTextChangedListener(this);
		otherAsset = (EditText) view2.findViewById(R.id.otherAsset);
		otherAsset.addTextChangedListener(this);
		totalAsset = (EditText) view2.findViewById(R.id.totalAsset);
//		totalAsset.addTextChangedListener(this);
		houseFundLoan = (EditText) view2.findViewById(R.id.houseFundLoan);
		houseFundLoan.addTextChangedListener(this);
		houseBusinessLoan = (EditText) view2
				.findViewById(R.id.houseBusinessLoan);
		houseBusinessLoan.addTextChangedListener(this);
		carLoan = (EditText) view2.findViewById(R.id.carLoan);
		carLoan.addTextChangedListener(this);
		consumerLoan = (EditText) view2.findViewById(R.id.consumerLoan);
		consumerLoan.addTextChangedListener(this);
		renovationLoan = (EditText) view2.findViewById(R.id.renovationLoan);
		renovationLoan.addTextChangedListener(this);
		privateLoan = (EditText) view2.findViewById(R.id.privateLoan);
		privateLoan.addTextChangedListener(this);
		unpaidCreditCard = (EditText) view2.findViewById(R.id.unpaidCreditCard);
		unpaidCreditCard.addTextChangedListener(this);
		otherLoan = (EditText) view2.findViewById(R.id.otherLoan);
		otherLoan.addTextChangedListener(this);
		totalLoan = (EditText) view2.findViewById(R.id.totalLoan);
//		totalLoan.addTextChangedListener(this);
		netAsset = (EditText) view2.findViewById(R.id.netAsset);
//		netAsset.addTextChangedListener(this);
	}

	private void initView3() {
		income = (EditText) view3.findViewById(R.id.income);
		income.addTextChangedListener(this);
		spouseIncome = (EditText) view3.findViewById(R.id.spouseIncome);
		spouseIncome.addTextChangedListener(this);
		endBonus = (EditText) view3.findViewById(R.id.endBonus);
		endBonus.addTextChangedListener(this);
		rental = (EditText) view3.findViewById(R.id.rental);
		rental.addTextChangedListener(this);
		interest = (EditText) view3.findViewById(R.id.interest);
		interest.addTextChangedListener(this);
		dividends = (EditText) view3.findViewById(R.id.dividends);
		dividends.addTextChangedListener(this);
		otherDividends = (EditText) view3.findViewById(R.id.otherDividends);
		otherDividends.addTextChangedListener(this);
		otherFixIncome = (EditText) view3.findViewById(R.id.otherFixIncome);
		otherFixIncome.addTextChangedListener(this);
		otherUnfixIncome = (EditText) view3.findViewById(R.id.otherUnfixIncome);
		otherUnfixIncome.addTextChangedListener(this);
		totalIncome = (EditText) view3.findViewById(R.id.totalIncome);
//		totalIncome.addTextChangedListener(this);
		livingCost = (EditText) view3.findViewById(R.id.livingCost);
		livingCost.addTextChangedListener(this);
		housExpense = (EditText) view3.findViewById(R.id.housExpense);
		housExpense.addTextChangedListener(this);
		medicExpense = (EditText) view3.findViewById(R.id.medicExpense);
		medicExpense.addTextChangedListener(this);
		eduExpense = (EditText) view3.findViewById(R.id.eduExpense);
		eduExpense.addTextChangedListener(this);
		insurance = (EditText) view3.findViewById(R.id.insurance);
		insurance.addTextChangedListener(this);
		travel = (EditText) view3.findViewById(R.id.travel);
		travel.addTextChangedListener(this);
		alimony = (EditText) view3.findViewById(R.id.alimony);
		alimony.addTextChangedListener(this);
		otherFixExpense = (EditText) view3.findViewById(R.id.otherFixExpense);
		otherFixExpense.addTextChangedListener(this);
		otherUnfixExpense = (EditText) view3
				.findViewById(R.id.otherUnfixExpense);
		otherUnfixExpense.addTextChangedListener(this);
		totalExpenditure = (EditText) view3.findViewById(R.id.totalExpenditure);
//		totalExpenditure.addTextChangedListener(this);
		annualBalance = (EditText) view3.findViewById(R.id.annualBalance);
//		annualBalance.addTextChangedListener(this);
	}

	private void initView4() {
		historicalView = (EditText) view4.findViewById(R.id.editHistorical);
		historicalView.setText((incomeInfo == null || incomeInfo
				.getHistoryExperience() == null) ? "" : incomeInfo
				.getHistoryExperience());
	}

	public void initDate(Object obj) {
		if (obj instanceof AssetInfo) {
			String[] financial = getActivity().getResources().getStringArray(
					R.array.financial_goal);
			String[] goal = ((AssetInfo) obj).getGoal().split(",");
			for (int i = 0; i <goal.length; i++){
				for (int m = 0; m<financial.length; m++)
				{
					if (goal[i].equals(financial[m]))
					{
						CheckBox cb = (CheckBox) view1.findViewById(R.id.financialBox1 + m);
						cb.setChecked(true);
						continue;
					}
				}
			}
			
			cash.setText(longToString(((AssetInfo) obj).getCash()));
			demanDeposit.setText(longToString(((AssetInfo) obj).getDemanDeposit()));
			fixDeposit.setText(longToString(((AssetInfo) obj).getFixDeposit()));
			stock.setText(longToString(((AssetInfo) obj).getStock()));
			fund.setText(longToString(((AssetInfo) obj).getFund()));
			bond.setText(longToString(((AssetInfo) obj).getBond()));
			estate.setText(longToString(((AssetInfo) obj).getEstate()));
			otherAsset.setText(longToString(((AssetInfo) obj).getOtherAsset()));
			totalAsset.setText(longToString(((AssetInfo) obj).getTotalAsset()));
			houseFundLoan.setText(longToString(((AssetInfo) obj).getHouseFundLoan()));
			houseBusinessLoan.setText(longToString(((AssetInfo) obj).getHouseBusinessLoan()));
			carLoan.setText(longToString(((AssetInfo) obj).getCarLoan()));
			consumerLoan.setText(longToString(((AssetInfo) obj).getConsumerLoan()));
			renovationLoan.setText(longToString(((AssetInfo) obj).getRenovationLoan()));
			privateLoan.setText(longToString(((AssetInfo) obj).getPrivateLoan()));
			unpaidCreditCard.setText(longToString(((AssetInfo) obj).getUnpaidCreditCard()
					));
			otherLoan.setText(longToString(((AssetInfo) obj).getOtherLoan()));
			totalLoan.setText(longToString(((AssetInfo) obj).getTotalLoan()));
			netAsset.setText(longToString(((AssetInfo) obj).getNetAsset()));
		}

		else if (obj instanceof AnnualIncomExpend){
			income.setText(longToString(((AnnualIncomExpend) obj).getIncome()));
			spouseIncome.setText(longToString(((AnnualIncomExpend) obj).getSpouseIncome()
					));
			endBonus.setText(longToString(((AnnualIncomExpend) obj).getEndBonus()));
			rental.setText(longToString(((AnnualIncomExpend) obj).getRental()));
			interest.setText(longToString(((AnnualIncomExpend) obj).getInterest()));
			dividends.setText(longToString(((AnnualIncomExpend) obj).getDividends()));
			otherDividends.setText(longToString(((AnnualIncomExpend) obj)
					.getOtherDividends()));
			otherFixIncome.setText(longToString(((AnnualIncomExpend) obj)
					.getOtherFixIncome()));
			otherUnfixIncome.setText(longToString(((AnnualIncomExpend) obj)
					.getOtherUnfixIncome()));
			totalIncome
					.setText(longToString(((AnnualIncomExpend) obj).getTotalIncome()));
			livingCost.setText(longToString(((AnnualIncomExpend) obj).getLivingCost()));
			housExpense
					.setText(longToString(((AnnualIncomExpend) obj).getHousExpense()));
			medicExpense.setText(longToString(((AnnualIncomExpend) obj).getMedicExpense()
					));
			eduExpense.setText(longToString(((AnnualIncomExpend) obj).getEduExpense()));
			insurance.setText(longToString(((AnnualIncomExpend) obj).getInsurance()));
			travel.setText(longToString(((AnnualIncomExpend) obj).getTravel()));
			alimony.setText(longToString(((AnnualIncomExpend) obj).getAlimony()));
			otherFixExpense.setText(longToString(((AnnualIncomExpend) obj)
					.getOtherFixExpense()));
			otherUnfixExpense.setText(longToString(((AnnualIncomExpend) obj)
					.getOtherUnfixExpense()));
			totalExpenditure.setText(longToString(((AnnualIncomExpend) obj)
					.getTotalExpenditure()));
			annualBalance.setText(longToString(((AnnualIncomExpend) obj).getAnnualBalance()
					));
		}
	}
	
	
	private String longToString(long src){
		if (src == 0)
			return "";
		else
			return src + "";
	}

	private void saveTotalAsset() {
		long totalAssetNum = 0;
		long totalLoanNum = 0;

		totalAssetNum += Long.valueOf(cash.getText().toString().isEmpty() ? "0"
				: cash.getText().toString());
		totalAssetNum += Long.valueOf(demanDeposit.getText().toString()
				.isEmpty() ? "0" : demanDeposit.getText().toString());
		totalAssetNum += Long
				.valueOf(fixDeposit.getText().toString().isEmpty() ? "0"
						: fixDeposit.getText().toString());
		totalAssetNum += Long
				.valueOf(stock.getText().toString().isEmpty() ? "0" : stock
						.getText().toString());
		totalAssetNum += Long.valueOf(fund.getText().toString().isEmpty() ? "0"
				: fund.getText().toString());

		totalAssetNum += Long.valueOf(bond.getText().toString().isEmpty() ? "0"
				: bond.getText().toString());
		totalAssetNum += Long
				.valueOf(estate.getText().toString().isEmpty() ? "0" : estate
						.getText().toString());
		totalAssetNum += Long
				.valueOf(otherAsset.getText().toString().isEmpty() ? "0"
						: otherAsset.getText().toString());
		
		totalLoanNum += Long.valueOf(houseFundLoan.getText()
				.toString().isEmpty() ? "0" : houseFundLoan.getText()
				.toString());
		totalLoanNum += Long.valueOf(houseBusinessLoan
				.getText().toString().isEmpty() ? "0" : houseBusinessLoan
				.getText().toString());
		totalLoanNum += Long.valueOf(carLoan.getText().toString()
				.isEmpty() ? "0" : carLoan.getText().toString());
		totalLoanNum += Long
				.valueOf(consumerLoan.getText().toString().isEmpty() ? "0"
						: consumerLoan.getText().toString());
		totalLoanNum += Long.valueOf(renovationLoan.getText()
				.toString().isEmpty() ? "0" : renovationLoan.getText()
				.toString());
		totalLoanNum += Long.valueOf(privateLoan.getText().toString()
				.isEmpty() ? "0" : privateLoan.getText().toString());
		totalLoanNum += Long.valueOf(unpaidCreditCard
				.getText().toString().isEmpty() ? "0" : unpaidCreditCard
				.getText().toString());
		totalLoanNum += Long.valueOf(otherLoan.getText().toString()
				.isEmpty() ? "0" : otherLoan.getText().toString());

		totalAsset.setText(longToString(totalAssetNum));
		totalLoan.setText(longToString(totalLoanNum));
		netAsset.setText(longToString(totalAssetNum-totalLoanNum));
	}
	
	private void saveTotalIncome(){
		long totalIncomeNum = 0;
		long totalExpenditureNum = 0;
		
		totalIncomeNum += Long
				.valueOf(income.getText().toString().isEmpty() ? "0" : income
						.getText().toString());
		totalIncomeNum += Long.valueOf(spouseIncome.getText().toString()
				.isEmpty() ? "0" : spouseIncome.getText().toString());
		totalIncomeNum += Long.valueOf(endBonus.getText().toString()
				.isEmpty() ? "0" : endBonus.getText().toString());
		totalIncomeNum += Long
				.valueOf(rental.getText().toString().isEmpty() ? "0" : rental
						.getText().toString());
		totalIncomeNum += Long.valueOf(interest.getText().toString()
				.isEmpty() ? "0" : interest.getText().toString());
		totalIncomeNum += Long.valueOf(dividends.getText().toString()
				.isEmpty() ? "0" : dividends.getText().toString());
		totalIncomeNum += Long.valueOf(otherDividends.getText()
				.toString().isEmpty() ? "0" : otherDividends.getText()
				.toString());
		totalIncomeNum += Long.valueOf(otherFixIncome.getText()
				.toString().isEmpty() ? "0" : otherFixIncome.getText()
				.toString());
		totalIncomeNum += Long.valueOf(otherUnfixIncome.getText()
				.toString().isEmpty() ? "0" : otherUnfixIncome.getText()
				.toString());
		
		
		totalExpenditureNum += Long.valueOf(livingCost.getText().toString()
				.isEmpty() ? "0" : livingCost.getText().toString());
		totalExpenditureNum += Long.valueOf(housExpense.getText().toString()
				.isEmpty() ? "0" : housExpense.getText().toString());
		totalExpenditureNum += Long.valueOf(medicExpense.getText().toString()
				.isEmpty() ? "0" : medicExpense.getText().toString());
		totalExpenditureNum += Long.valueOf(eduExpense.getText().toString()
				.isEmpty() ? "0" : eduExpense.getText().toString());
		totalExpenditureNum += Long.valueOf(insurance.getText().toString()
				.isEmpty() ? "0" : insurance.getText().toString());
		totalExpenditureNum += Long
				.valueOf(travel.getText().toString().isEmpty() ? "0" : travel
						.getText().toString());
		totalExpenditureNum += Long
				.valueOf(alimony.getText().toString().isEmpty() ? "0" : alimony
						.getText().toString());
		totalExpenditureNum += Long.valueOf(otherFixExpense.getText()
				.toString().isEmpty() ? "0" : otherFixExpense.getText()
				.toString());
		totalExpenditureNum += Long.valueOf(otherUnfixExpense.getText()
				.toString().isEmpty() ? "0" : otherUnfixExpense.getText()
				.toString());
		
		totalIncome.setText(longToString(totalIncomeNum));
		totalExpenditure.setText(longToString(totalExpenditureNum));
		annualBalance.setText(longToString(totalIncomeNum - totalExpenditureNum));
	}
	
	

	public AssetInfo saveAssInfo() {
		AssetInfo curAssetInfo = new AssetInfo();

		if(goal.length() > 0)
		goal.delete(0, goal.length()-1);
		for (int i = 0; i < 11; i++) {
			CheckBox cb = (CheckBox) view1.findViewById(R.id.financialBox1 + i);
			if (cb.isChecked())
				goal.append(getActivity().getResources().getStringArray(
						R.array.financial_goal)[i]
						+ ",");
		}

		if (goal.length() > 0)
			goal.deleteCharAt(goal.length() - 1);
		Log.i("裁剪的理财目标为：", goal.toString());
		curAssetInfo.setGoal(goal.toString());

		curAssetInfo.setCash(Long
				.valueOf(cash.getText().toString().isEmpty() ? "0" : cash
						.getText().toString()));
		curAssetInfo.setDemanDeposit(Long
				.valueOf(demanDeposit.getText().toString().isEmpty() ? "0"
						: demanDeposit.getText().toString()));
		curAssetInfo.setFixDeposit(Long.valueOf(fixDeposit.getText().toString()
				.isEmpty() ? "0" : fixDeposit.getText().toString()));
		curAssetInfo.setStock(Long
				.valueOf(stock.getText().toString().isEmpty() ? "0" : stock
						.getText().toString()));
		curAssetInfo.setFund(Long
				.valueOf(fund.getText().toString().isEmpty() ? "0" : fund
						.getText().toString()));
		curAssetInfo.setBond(Long
				.valueOf(bond.getText().toString().isEmpty() ? "0" : bond
						.getText().toString()));
		curAssetInfo.setEstate(Long.valueOf(estate.getText().toString()
				.isEmpty() ? "0" : estate.getText().toString()));
		curAssetInfo.setOtherAsset(Long.valueOf(otherAsset.getText().toString()
				.isEmpty() ? "0" : otherAsset.getText().toString()));

		curAssetInfo.setTotalAsset(Long.valueOf(totalAsset.getText().toString()
				.isEmpty() ? "0" : totalAsset.getText().toString()));
		curAssetInfo.setHouseFundLoan(Long.valueOf(houseFundLoan.getText()
				.toString().isEmpty() ? "0" : houseFundLoan.getText()
				.toString()));
		curAssetInfo.setHouseBusinessLoan(Long.valueOf(houseBusinessLoan
				.getText().toString().isEmpty() ? "0" : houseBusinessLoan
				.getText().toString()));
		curAssetInfo.setCarLoan(Long.valueOf(carLoan.getText().toString()
				.isEmpty() ? "0" : carLoan.getText().toString()));

		curAssetInfo.setConsumerLoan(Long
				.valueOf(consumerLoan.getText().toString().isEmpty() ? "0"
						: consumerLoan.getText().toString()));
		curAssetInfo.setRenovationLoan(Long.valueOf(renovationLoan.getText()
				.toString().isEmpty() ? "0" : renovationLoan.getText()
				.toString()));
		curAssetInfo
				.setPrivateLoan(Long.valueOf(privateLoan.getText().toString()
						.isEmpty() ? "0" : privateLoan.getText().toString()));
		curAssetInfo.setUnpaidCreditCard(Long.valueOf(unpaidCreditCard
				.getText().toString().isEmpty() ? "0" : unpaidCreditCard
				.getText().toString()));

		curAssetInfo.setOtherLoan(Long.valueOf(otherLoan.getText().toString()
				.isEmpty() ? "0" : otherLoan.getText().toString()));
		curAssetInfo.setTotalLoan(Long.valueOf(totalLoan.getText().toString()
				.isEmpty() ? "0" : totalLoan.getText().toString()));
		curAssetInfo.setNetAsset(Long.valueOf(netAsset.getText().toString()
				.isEmpty() ? "0" : netAsset.getText().toString()));

		return curAssetInfo;
	}

	public AnnualIncomExpend saveIncome() {
		AnnualIncomExpend curInfo = new AnnualIncomExpend();

		curInfo.setIncome(Long
				.valueOf(income.getText().toString().isEmpty() ? "0" : income
						.getText().toString()));
		curInfo.setSpouseIncome(Long.valueOf(spouseIncome.getText().toString()
				.isEmpty() ? "0" : spouseIncome.getText().toString()));
		curInfo.setEndBonus(Long.valueOf(endBonus.getText().toString()
				.isEmpty() ? "0" : endBonus.getText().toString()));
		curInfo.setRental(Long
				.valueOf(rental.getText().toString().isEmpty() ? "0" : rental
						.getText().toString()));
		curInfo.setInterest(Long.valueOf(interest.getText().toString()
				.isEmpty() ? "0" : interest.getText().toString()));
		curInfo.setDividends(Long.valueOf(dividends.getText().toString()
				.isEmpty() ? "0" : dividends.getText().toString()));
		curInfo.setOtherDividends(Long.valueOf(otherDividends.getText()
				.toString().isEmpty() ? "0" : otherDividends.getText()
				.toString()));
		curInfo.setOtherFixIncome(Long.valueOf(otherFixIncome.getText()
				.toString().isEmpty() ? "0" : otherFixIncome.getText()
				.toString()));
		curInfo.setOtherFixIncome(Long.valueOf(otherUnfixIncome.getText()
				.toString().isEmpty() ? "0" : otherUnfixIncome.getText()
				.toString()));
		curInfo.setTotalIncome(Long.valueOf(totalIncome.getText().toString()
				.isEmpty() ? "0" : totalIncome.getText().toString()));
		curInfo.setLivingCost(Long.valueOf(livingCost.getText().toString()
				.isEmpty() ? "0" : livingCost.getText().toString()));
		curInfo.setHousExpense(Long.valueOf(housExpense.getText().toString()
				.isEmpty() ? "0" : housExpense.getText().toString()));
		curInfo.setMedicExpense(Long.valueOf(medicExpense.getText().toString()
				.isEmpty() ? "0" : medicExpense.getText().toString()));
		curInfo.setEduExpense(Long.valueOf(eduExpense.getText().toString()
				.isEmpty() ? "0" : eduExpense.getText().toString()));
		curInfo.setInsurance(Long.valueOf(insurance.getText().toString()
				.isEmpty() ? "0" : insurance.getText().toString()));
		curInfo.setTravel(Long
				.valueOf(travel.getText().toString().isEmpty() ? "0" : travel
						.getText().toString()));
		curInfo.setAlimony(Long
				.valueOf(alimony.getText().toString().isEmpty() ? "0" : alimony
						.getText().toString()));
		curInfo.setOtherFixExpense(Long.valueOf(otherFixExpense.getText()
				.toString().isEmpty() ? "0" : otherFixExpense.getText()
				.toString()));
		curInfo.setOtherUnfixExpense(Long.valueOf(otherUnfixExpense.getText()
				.toString().isEmpty() ? "0" : otherUnfixExpense.getText()
				.toString()));
		curInfo.setTotalExpenditure(Long.valueOf(totalExpenditure.getText()
				.toString().isEmpty() ? "0" : totalExpenditure.getText()
				.toString()));
		curInfo.setAnnualBalance(Long.valueOf(annualBalance.getText()
				.toString().isEmpty() ? "0" : annualBalance.getText()
				.toString()));

		curInfo.setHistoryExperience(historicalView.getText().toString());
		return curInfo;
	}

	public int getNowView() {
		return mPager.getCurrentItem();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(Editable s) {
		saveTotalAsset();
		saveTotalIncome();
	}

	// private void initView1() {
	// holder1 = new ViewHolder1();
	// Field[] fields = ViewHolder1.class.getDeclaredFields();
	// try {
	// for (Field f : fields) {
	//
	// Class cr = Class.forName("com.incito.finshine.R$id");
	// int rid = cr.getField(f.getName()).getInt(cr);
	// View v = view2.findViewById(rid);
	// if (v != null) {
	// f.set(holder1, v);
	// }
	//
	// }
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// } catch (NoSuchFieldException e) {
	// e.printStackTrace();
	// } catch (ClassNotFoundException e) {
	//
	// e.printStackTrace();
	// }

	// if (assetInfo != null) {
	// Field[] hfields = ViewHolder1.class.getDeclaredFields();
	// try {
	// for (Field f : hfields) {
	// String methodName = "get"
	// + f.getName().substring(0, 1).toUpperCase()
	// + f.getName().substring(1);
	// Log.i("tag", "method name = " + methodName);
	// Method get = AssetInfo.class.getMethod(methodName);
	//
	// if (get != null) {
	// String value = String.valueOf(get.invoke(assetInfo));
	// Method set = EditText.class.getMethod("setText",
	// CharSequence.class);
	//
	// Field txt = ViewHolder1.class.getDeclaredField(f
	// .getName());
	//
	// Log.i("tag", "field = " + txt.getType());
	//
	// EditText tv = (EditText) txt.get(holder1);
	// set.invoke(tv, value);
	// }
	//
	// }
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// e.printStackTrace();
	// } catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// } catch (NoSuchFieldException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// }

	// private void initView2() {
	// holder2 = new ViewHolder2();
	// Field[] fields = ViewHolder2.class.getDeclaredFields();
	// try {
	// for (Field f : fields) {
	//
	// Class cr = Class.forName("com.incito.finshine.R$id");
	// int rid = cr.getField(f.getName()).getInt(cr);
	// View v = view3.findViewById(rid);
	// if (v != null) {
	// f.set(holder2, v);
	// }
	//
	// }
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// } catch (NoSuchFieldException e) {
	// e.printStackTrace();
	// } catch (ClassNotFoundException e) {
	//
	// e.printStackTrace();
	// }
	//
	// if (incomeInfo != null) {
	// Field[] hfields = ViewHolder2.class.getDeclaredFields();
	// try {
	// for (Field f : hfields) {
	// String methodName = "get"
	// + f.getName().substring(0, 1).toUpperCase()
	// + f.getName().substring(1);
	// Log.i("tag", "method name = " + methodName);
	// Method get = AnnualIncomExpend.class.getMethod(methodName);
	//
	// if (get != null) {
	// String value = String.valueOf(get.invoke(incomeInfo));
	// Method set = EditText.class.getMethod("setText",
	// CharSequence.class);
	//
	// Field txt = ViewHolder2.class.getDeclaredField(f
	// .getName());
	//
	// Log.i("tag", "field = " + txt.getType());
	//
	// EditText tv = (EditText) txt.get(holder2);
	// set.invoke(tv, value);
	// }
	//
	// }
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// e.printStackTrace();
	// } catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// } catch (NoSuchFieldException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// }

}