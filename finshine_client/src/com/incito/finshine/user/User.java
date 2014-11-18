package com.incito.finshine.user;

import com.incito.finshine.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class User {
	private String name;
	private String title;
	private Bitmap avatar;
	User(Context c)
	{
		name = "Jorge Almeida";
		title = "���ʦ";
		avatar = BitmapFactory.decodeResource(c.getResources(), R.drawable.form_0004_form_bg);
	}
	public String getName() {
		return name;
	}
	public String getTitle() {
		return title;
	}
	public Bitmap getAvatar() {
		return avatar;
	}
}
