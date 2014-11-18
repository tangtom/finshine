package com.incito.abs.sync;

public abstract class SyncTaskMerge extends SyncTask{

	@Override
	public TaskType getType() {
		return TaskType.MERGE;
	}

}
