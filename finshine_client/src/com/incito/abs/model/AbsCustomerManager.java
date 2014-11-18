package com.incito.abs.model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsCustomerManager {
	private List<AbsCustomer> list;
	private List<ObserverCustomer> observers = new ArrayList<ObserverCustomer>();
	protected abstract AbsCustomer createCustomer();
	protected abstract AbsCustomer getCustomerById(long id);
	protected abstract boolean updateCustomer(AbsCustomer customer);
	protected List<AbsCustomer> getCustomers(){
		return list;
	}
	public void addObserver(ObserverCustomer observer){
		if(!observers.contains(observer))
		{
			observers.add(observer);
		}
	}
	public void removeObserver(ObserverCustomer observer){
		observers.remove(observer);
	}
	public void clearObservers()
	{
		observers.clear();
	}
	public abstract void notifyObserver();
}
