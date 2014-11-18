package com.incito.finshine.customer;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.incito.abs.model.AbsCustomer;
import com.incito.abs.model.AbsCustomerManager;
import com.incito.finshine.entity.Customer;

public class CustomerManager extends AbsCustomerManager {
	private List<Customer> listCustomer;

	public boolean init() {
		return true;
	}

	public List<Customer> getListCustomer() {
		return listCustomer;
	}

	public void setListCustomer(List<Customer> listCustomer) {
		this.listCustomer = listCustomer;
	}

	public Customer getCustomer(long id) {
		Customer customer = null;
		if (listCustomer != null && listCustomer.size() > 0) {
			for (Customer item : listCustomer) {
				if (item.getId() == id) {
					customer = item;
					break;
				}
			}
		}
		return customer;
	}

	public boolean addCustomer(Customer c) {
		if (listCustomer == null)
			listCustomer = new ArrayList<Customer>();

			listCustomer.add(c);

		return true;
	}

	public boolean addCustomer(Customer c, boolean addInBegain) {
		if (listCustomer == null)
			listCustomer = new ArrayList<Customer>();

			if (addInBegain) {
				listCustomer.add(0, c);
			} else {
				listCustomer.add(c);
			}

		return true;
	}

	@Override
	protected AbsCustomer createCustomer() {
		return null;
	}

	@Override
	protected AbsCustomer getCustomerById(long id) {
		return null;
	}

	@Override
	protected boolean updateCustomer(AbsCustomer customer) {

		return false;
	}

	public boolean updateCustomer(Customer customer) {
		int index = 0;
		if(listCustomer != null)
		{
			Log.i("", "---------------------");
			for (int i = 0; i < listCustomer.size(); i++) {
				if (listCustomer.get(i).getId() == customer.getId()) {// 更新数据
					index = i;
					break;
				}
			}
			listCustomer.set(index, customer);
		}
		return false;
	}

	@Override
	public void notifyObserver() {

	}
}
