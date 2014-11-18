package com.incito.finshine.manager;

import com.incito.finshine.command.Command;
import com.incito.finshine.customer.CustomerManager;
import com.incito.finshine.network.NetworkManager;
import com.incito.finshine.network.Request;
import com.incito.finshine.product.ProductManager;
import com.incito.finshine.schedule.ScheduleManager;
import com.incito.finshine.user.UserManager;

public class CoreManager {
	public enum AppStatus {
		UNKNOWN, UNINITED, READY, ERROR
	}

	private AppStatus status = AppStatus.UNKNOWN;
	CustomerManager customer;
	ProductManager product;
	ScheduleManager schedule;
	NetworkManager network;
	UserManager userManager;
	private static CoreManager coreManager = null;

	public ProductManager getProduct() {
		return product;
	}

	public static CoreManager getCoreManager() {
		return coreManager;
	}
	
	public NetworkManager getNetworkManager()
	{
		return network;
	}

//	public static void setCoreManager(CoreManager coreManager) {
//		CoreManager.coreManager = coreManager;
//	}

	public static CoreManager getInstance() {
		if (coreManager == null) {
			coreManager = new CoreManager();
		}
		return coreManager;
	}
	
//	public boolean postRequestForLogin(Request request,String username,String password) {
//		NetworkManager network = NetworkManager.getInstanceByVariable(username, password);
//		network.postRequest(request);
//		return true;
//	}

	public boolean init() {
		if (status == AppStatus.UNKNOWN) {
			network = NetworkManager.getInstance();
//			network = NetworkManager.getInstanceByVariable("","");
			customer = new CustomerManager();
			product = new ProductManager();
			schedule = new ScheduleManager();
			userManager = new UserManager();
		}
		return true;
	}

	public CustomerManager getCustomerManager() {
		return customer;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public boolean postRequest(Request request) {
		network.postRequest(request);
		return true;
	}
	public int postCommand(Command c)
	{
		return 0;
	}
	public AppStatus getStatus() {
		return status;
	}

	
}
