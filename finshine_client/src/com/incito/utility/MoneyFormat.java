package com.incito.utility;

public class MoneyFormat{
	 private final String [] pattern ={"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
	 private final String [] cPattern ={"","拾","佰","仟","万","拾","佰","仟","亿"};
	 private final String [] cfPattern = {"","角","分"};
	 private final String ZEOR = "零";
	 public String format(String moneyString){
	  int dotPoint = moneyString.indexOf("."); //判断是否为小数
	  String moneyStr;      
	  if(dotPoint != -1){
	    moneyStr = moneyString.substring(0,moneyString.indexOf("."));
	  }
	  else{
	   moneyStr = moneyString;
	  }
	  StringBuffer fraction = null;   //小数部分的处理,以及最后的yuan.
	  StringBuffer ms = new StringBuffer(); 
	  for(int i = 0;i < moneyStr.length();i++){
	   ms.append(pattern[moneyStr.charAt(i) - 48]); //按数组的编号加入对应大写汉字
	  }
	  
	  int cpCursor = 1;
	  for(int j = moneyStr.length() - 1;j > 0;j--){
	   ms.insert(j,cPattern[cpCursor]);   //在j之后加字符,不影响j对原字符串的相对位置
	              //只是moneyStr.length()不断增加
	              //insert(j,"string")就在j位置处插入,j=0时为第一位
	   cpCursor = cpCursor == 8?1:cpCursor + 1;    //亿位之后重新循环
	  }
	  
	  
	  while(ms.indexOf("零拾") != -1){  //当十位为零时用一个"零"代替"零拾"
	            //replace的起始于终止位置
	   ms.replace(ms.indexOf("零拾"),ms.indexOf("零拾") + 2,ZEOR);
	  }
	  while(ms.indexOf("零佰") != -1){  //当百位为零时,同理
	   ms.replace(ms.indexOf("零佰"),ms.indexOf("零佰") + 2,ZEOR);
	  }
	  while(ms.indexOf("零仟") != -1){  //同理
	   ms.replace(ms.indexOf("零仟"),ms.indexOf("零仟") + 2,ZEOR);
	  }
	  while(ms.indexOf("零万") != -1){  //万需保留，中文习惯
	   ms.replace(ms.indexOf("零万"),ms.indexOf("零万") + 2,"万");
	  }
	  while(ms.indexOf("零亿") != -1){  //同上
	   ms.replace(ms.indexOf("零亿"),ms.indexOf("零亿") + 2,"亿");
	  }
	  while(ms.indexOf("零零") != -1){//有连续数位出现零，即有以下情况，此时根据习惯保留一个零即可
	   ms.replace(ms.indexOf("零零"),ms.indexOf("零零") + 2,ZEOR);
	  }
	  while(ms.indexOf("亿万") != -1){  //特殊情况，如:100000000,根据习惯保留高位
	   ms.replace(ms.indexOf("亿万"),ms.indexOf("亿万") + 2,"亿");
	  }
	  while(ms.lastIndexOf("零") == ms.length()-1){  //当结尾为零j，不必显示,经过处理也只可能出现一个零
	   ms.delete(ms.lastIndexOf("零"),ms.lastIndexOf("零") + 1);
	  }
	  
	  
	  int end;
	  if((dotPoint = moneyString.indexOf(".")) != -1 ){ //是小数的进入 
	   String fs = moneyString.substring(dotPoint + 1,moneyString.length());
	   if(fs.indexOf("00") == -1 || fs.indexOf("00") >= 2){//若前两位小数全为零，则跳过操作
	    end = fs.length() > 2?2:fs.length();  //仅保留两位小数
	    fraction = new StringBuffer(fs.substring(0,end));
	    for(int j = 0;j < fraction.length();j++){
	     fraction.replace(j,j+1,this.pattern[fraction.charAt(j) - 48]); //替换大写汉字
	    }
	    for(int i = fraction.length();i > 0;i--){  //插入中文标识
	     fraction.insert(i,cfPattern[i]);
	    }
	    fraction.insert(0,"元");      //为整数部分添加标识
	   }
	   else{
	    fraction = new StringBuffer("万元整"); 
	   }
	   
	  }
	  else{
	   fraction = new StringBuffer("万元整");
	  }
	   
	  ms.append(fraction);         //加入小数部分
	  return ms.toString();
	 }
	 
//	 public static void main(String [] ar){
//	  //System.out.println(new MoneyFormat().format("10005022.123009"));
//	  System.out.println(new MoneyFormat().format("999"));
//	 }
	} 