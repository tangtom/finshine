package com.incito.finshine.activity.dialog;

import com.incito.finshine.entity.TodoItem;

public interface EditTodoListener {
	void onAdd(TodoItem item);
	void onSave(TodoItem item);
	void onDelete(TodoItem item);
	void onCancel(TodoItem item);
}
