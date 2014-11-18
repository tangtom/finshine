package com.incito.abs.sync;

public abstract class SyncTaskPush extends SyncTask{
	@Override
	public TaskType getType() {
		return TaskType.PUSH;
	}
}
