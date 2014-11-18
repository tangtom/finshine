package com.incito.abs.sync;

public abstract class SyncTaskClone extends SyncTask{

	@Override
	public TaskType getType() {
		return TaskType.CLONE;
	}

}
