package com.incito.finshine.user;

import java.util.ArrayList;

import com.incito.finshine.entity.TodoItem;

public class UserManager {
	private ArrayList<TodoItem> listTodos;

	public ArrayList<TodoItem> getListTodos() {
		return listTodos;
	}

	public void setListTodos(ArrayList<TodoItem> listTodos) {
		this.listTodos = listTodos;
	}
}
