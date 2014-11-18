package com.incito.finshine.activity.adapter;

import java.util.List;

import com.custom.view.CommFlipDot;
import com.incito.finshine.R;
import com.incito.finshine.entity.Customer;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AdapterCustomerBaseDetailViewPager extends PagerAdapter {
	public List<View> mListViews;

	private Customer customer;
	
	public AdapterCustomerBaseDetailViewPager(List<View> lists) {
		this.mListViews = lists;
	}

	public AdapterCustomerBaseDetailViewPager(List<View> lists, Customer c) {
		this.mListViews = lists;
		this.customer = c;
	}

	@Override
	public void destroyItem(View arg0, int position, Object arg2) {
		Log.i("liningtest", "destroyItem From ViewPager");
		((ViewPager) arg0).removeView(mListViews.get(position));// 0 1
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public Object instantiateItem(View view, final int position) {
		Log.i("liningtest", "instantiateItem From ViewPager");

		((ViewPager) view).addView(mListViews.get(position), 0);// 0 1 2 3

		// switch (position) {
		// case 0:
		// name = (TextView) view.findViewById(R.id.textName);
		// gender = (TextView) view.findViewById(R.id.textGender);
		// age = (TextView) view.findViewById(R.id.textAge);
		// industry = (TextView) view.findViewById(R.id.textIndustry);
		// diploma = (TextView) view.findViewById(R.id.textDiploma);
		// city = (TextView) view.findViewById(R.id.textCity);
		// cellPhone1 = (TextView) view.findViewById(R.id.textCellPhone1);
		// email1 = (TextView) view.findViewById(R.id.textEmail1);
		// annualIncome = (TextView) view.findViewById(R.id.textAnnualIncome);
		// totalAsset = (TextView) view.findViewById(R.id.textTotalAssets);
		//
		// investNumber = (TextView) view.findViewById(R.id.textInvestNumber);
		// investSource = (TextView) view.findViewById(R.id.textInvestSource);
		// keyword = (TextView) view.findViewById(R.id.textKeyword);
		//
		// name.setText(customer.getName());
		// gender.setText(String.valueOf(customer.getGender()));
		// age.setText(String.valueOf(customer.getAge()));
		// industry.setText(customer.getIndustry());
		// // diploma.setText([customer.getDiploma()]);
		//
		// // getResources().getStringArray(R.array.education_status);
		// //
		// // keyword.setText(customer.getKeyword());
		// // age.setText(String.valueOf(customer.getAge()));
		// // gender.setText(intTagToString(GENDER, customer.getGender()));
		// //
		// // industry.setText(customer.getIndustry());
		// // job.setText(customer.getJob());
		// // cellPhone1.setText(customer.getCellPhone1());
		// // email1.setText(customer.getEmail1());
		// // currentInvestValue.setText(String.valueOf(customer
		// // .getCurrentInvestValue()));
		// // annualIncome.setText(String.valueOf(customer.getAnnualIncome()));
		// // // totalAsset.setText(String.valueOf(customer.getTotalAsset()));
		// // investNumber.setText(String.valueOf(customer.getInvestNumber()));
		// // investSource.setText(customer.getInvestSource());
		// // profit.setText(String.valueOf(customer.getProfit()));
		// break;
		//
		// case 1:
		//
		// break;
		//
		// case 2:
		//
		// break;
		//
		// case 3:
		//
		// break;
		//
		// default:
		// break;
		// }

		System.out.println("lihs arg1==" + position);
		return mListViews.get(position);
	}

	@Override
	public int getCount() {
		return mListViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}

}
