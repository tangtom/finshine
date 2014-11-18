package com.incito.finshine.entity;

import java.util.*;
import java.text.*;  

public class DateFormat {
	
	
	public static String hmsToDate(Date d){
		if (d!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(d);
		}
		return null;
	}
	
	public static Date StringToDate(String str){
		if ("".equals(str)||str==null) {
			return null;
		}
		str = str.substring(0, 10);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");         
		Date date = null;                 
		try {    
		    date = format.parse(str);  
		} catch (ParseException e) {    
		    e.printStackTrace();    
		}    
		date = java.sql.Date.valueOf(str);  // 只保留日期部分，返回的是java.sql.Date  2007-01-18    
		return date;
	}
	
	public static Date StringToDateSendType(String str){
		//str = str.substring(0, 10);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");         
		Date date = null;                 
		try {    
		    date = format.parse(str);  
		} catch (ParseException e) {    
		}    
		//date = java.sql.Date.valueOf(str);  // 只保留日期部分，返回的是java.sql.Date  2007-01-18   
		return date;
	}
	
	public static String DateToStringForBatch(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");        
		String str = null;   
		str = format.format(date);  // 2007-01-18    
		return str;
	}
	public static String DateToString(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");        
		String str = null;   
		str = format.format(date);  // 2007-01-18    
		return str;
	}
	public static String DateToStringHMSForYD(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");        
		String str = "";   
		str = format.format(date);  // 2007-01-18
		String result = str.substring(0,8)+"T"+str.substring(8,str.length());
		return result;
	}
	public static String DateToFullString(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        
		String str = null;   
		str = format.format(date);  
		return str;
	}
	
	public static String DateToFullStringForBatchNo(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");        
		String str = null;   
		str = format.format(date);  
		return str;
	}
	
	//去除分秒时
	public static Date DateToDate(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");        
		String str = null;   
		str = format.format(date);  // 2007-01-18    
		return StringToDate(str);
	}
	
	public static String DateToStringForTB(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");        
		String str = null;   
		str = format.format(date);  // 2007-01-18    
		return str;
	}
	
	public static String DateToStringForYDTM(Date date){
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmm");        
		String str = null;   
		str = format.format(date);  // 2007-01-18    
		return str;
	}
	
	
	//去除分秒时
	public static String DateToStringForTransitPC(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = null;   
		str = format.format(date);  // 2007-01-18
		str = str.replace("-", "");
		str = str.substring(2,str.length());
		return str;
	}
	
	public static Date StringToFullDate(String str){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     
		Date date = null;                 
		try {
		    date = format.parse(str);  
		} catch (ParseException e) {    
		    e.printStackTrace();    
		}    
		return date;
	}
	
	public static Double TwoTimeDifference(String t1,String t2){
		Date d1 = null;
		Date d2 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			d1 = sdf.parse(t1);
			d2 = sdf.parse(t2);
		} catch (ParseException pe) {
			System.out.println(pe.getMessage());
		}
		long dd1 = d1.getTime();
		long dd2 = d2.getTime();
		double hms = dd2 - dd1 ;
		return hms;
	} 
	
	public static String format(long ms) {
		long date = ms / (60 * 60 * 1000 * 24);
		long hour = (ms - date * 60 * 60 * 1000 * 24) / (60 * 60 * 1000);
		long minute = (ms - hour * 60 * 60 * 1000) / (60 * 1000);
		long second = (ms - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
		if (second >= 60) {
			second = second % 60;
			minute += second / 60;
		}
		if (minute >= 60) {
			minute = minute % 60;
			hour += minute / 60;
		}
		if (hour >= 24) {
			hour = hour % 24;
			date += hour / 24;
		}
		return date + "天" + hour + "小时" + minute + "分" + second + "秒";
	}
	
	
	public static int temp(String YYMM)
    {
        String strDate = YYMM;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Calendar calendar = new GregorianCalendar();
        Date date = null;
        try
        {
            date = sdf.parse(strDate);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return day;
        
    }


}
