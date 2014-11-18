package com.incito.finshine.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPManager {

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String PWD = "password";
	public static final String USERNAME = "username";
	public static final String LOGINFLAG="loginflag";
	private SharedPreferences sp;
	private Editor editor;
	private static SPManager spManager;
	
	public static SPManager getInstance()
	{
		if(spManager == null)
		{
			spManager = new SPManager();
		}
		return spManager;
	}
	
	
	public void init(Context ctx)
	{
		this.sp = ctx.getSharedPreferences("user", ctx.MODE_PRIVATE);
	}
	
//	public SPManager(Context ctx){
//		
//	}
	
	public SPManager(){}
	
	public  String getStringValue(String variable){
		if(variable.equals(NAME)){
			return sp.getString(NAME,"");
		}else if(variable.equals(PWD))
		{
			return sp.getString(PWD, "");
		}else if(variable.equals(USERNAME))
		{
			return sp.getString(USERNAME, "");
		}
		else if(variable.equals(LOGINFLAG))
		{
			return sp.getString(LOGINFLAG, "");
		}
		return null;
	}
	
	public long getLongValue(String variable){
		if(variable.equals(ID)){
			return sp.getLong(ID, 0);
		}
		return 0;
	}
	
	public void setStringValue(String key,String value){
		if(this.editor == null){
			this.editor = this.sp.edit();
		}
		this.editor.putString(key, value);
	}
	
	public void setLongValue(String key,long value){
		if(this.editor == null){
			this.editor = this.sp.edit();
		}
		this.editor.putLong(key, value);
	}
	
	public Editor editorClear()
	{
		if(this.editor == null){
			this.editor = this.sp.edit();
		}
		this.editor.clear();
		return editor;
	}
	
	public Editor editorCommit(){
		if(this.editor != null){
			this.editor.commit();
		}
		return editor;
	}
}
