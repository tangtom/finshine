package com.incito.abs.sync;

public abstract class SyncTask {
	public enum TaskType{
		PULL,PUSH,MERGE,CLONE
	}
	protected abstract TaskType getType();
	protected abstract int excute();
}
