package com.incito.abs.sync;

public abstract class SyncTaskPull extends SyncTask{
	@Override
	public TaskType getType() {
		return TaskType.PULL;
	}
}
