package com.incito.abs.model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsProductManager {
	private List<AbsProduct> list;
	private List<ObserverProduct> observers = new ArrayList<ObserverProduct>();
	public void addObserver(ObserverProduct observer){
		if(!observers.contains(observer))
		{
			observers.add(observer);
		}
	}
	public void removeObserver(ObserverProduct observer){
		observers.remove(observer);
	}
	public void clearObservers()
	{
		observers.clear();
	}
	public abstract void notifyObserver();
}
