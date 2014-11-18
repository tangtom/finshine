package com.incito.finshine.entity;

import java.util.Comparator;

import android.util.Log;

public class CustomerSortInvestCurrent implements Comparator<Customer> {

	@Override
	public int compare(Customer cus1, Customer cus2) {
		Log.i("comparator", "compare"+ cus1.getCurrentInvestValue() + "=="+cus2.getCurrentInvestValue());
		if (cus1.getCurrentInvestValue() > cus2.getCurrentInvestValue())
			return 1;
		else
			return -1;
	}

}
