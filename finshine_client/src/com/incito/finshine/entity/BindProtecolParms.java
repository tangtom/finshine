package com.incito.finshine.entity;

import java.io.Serializable;

public class BindProtecolParms implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8796164538596154500L;
	public long bindProId;
	public String smsVerfication;
	public String phone;
	public long prodId;
	public long recordId;
	
	public long customerId;
}
