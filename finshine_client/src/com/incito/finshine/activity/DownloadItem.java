package com.incito.finshine.activity;

import java.io.File;

import android.graphics.drawable.Drawable;

public class DownloadItem {
	public int index =-1;
	public String url;
	public Drawable d;
	public String localPath;
	public boolean bFinish = false;
	
	public DownloadItem(String url,Drawable d) {
		this.url = url;
		this.d = d;
	}
	public DownloadItem(String url)
	{
		this.url = url;
	}
	public DownloadItem(String url,int index)
	{
		this.index = index;
		this.url = url;
	}
	public int getIndex(){
	    return index;
	}
	public void setDrawable(Drawable d)
	{
		this.d = d;
		bFinish = true;
	}
	public String getUrl()
	{
		return this.url;
	}
	public String getLocalPath()
	{
		return this.localPath;
	}
	public Drawable getDrawable() {
		if (d != null){
			return d;}
		else if (localPath == null){
			return null;
		}
		else if(new File(localPath).exists()){
			return Drawable.createFromPath(localPath);
		}
		return null;
	}
}
