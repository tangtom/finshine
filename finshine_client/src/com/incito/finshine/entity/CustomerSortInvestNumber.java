package com.incito.finshine.entity;

import java.util.Comparator;

import android.util.Log;

public class CustomerSortInvestNumber implements Comparator<Customer> {

	@Override
	public int compare(Customer cus1, Customer cus2) {
		Log.i("comparator", "compare"+ cus1.getInvestNumber() + "=="+cus2.getInvestNumber());
		if (cus1.getInvestNumber() > cus2.getInvestNumber())
			return 1;
		else
			return -1;
	}

}
