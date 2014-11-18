package com.incito.finshine.command;

public class Command {
	private int id;
	private Object params;

	public Command(int id, Object params) {

	}

	public int getCommand() {
		return id;
	}

	public Object getParams() {
		return params;
	}
}
