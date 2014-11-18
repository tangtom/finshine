package com.incito.finshine.manager;

import com.incito.finshine.customer.CustomerManager;
import com.incito.finshine.user.UserManager;

public class DataManager {
	private CustomerManager customerManager = null;
	
	private UserManager userManager;
	private static DataManager manager = null;

	public static DataManager getInstance() {
		if (null == manager) {
			manager = new DataManager();
		}
		return manager;
	}
	public boolean init()
	{
		customerManager = new CustomerManager();
		customerManager.init();
		userManager = new UserManager();
		return true;
	}
	public CustomerManager getCustomerManager() {
		return customerManager;
	}
	public UserManager getUserManager() {
		return userManager;
	}
	
}
