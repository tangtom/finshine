package com.incito.finshine.entity;

import java.util.Comparator;

import android.util.Log;

public class CustomerSortTotalAsset implements Comparator<Customer> {

	@Override
	public int compare(Customer cus1, Customer cus2) {
		Log.i("comparator", "compare"+ cus1.getTotalAsset() + "=="+cus2.getTotalAsset());
		if (cus1.getTotalAsset() > cus2.getTotalAsset())
			return 1;
		else
			return -1;
	}

}
