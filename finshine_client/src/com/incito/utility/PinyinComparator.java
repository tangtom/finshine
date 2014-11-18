package com.incito.utility;

import java.util.Comparator;

import net.sourceforge.pinyin4j.PinyinHelper;
import android.util.Log;

import com.incito.finshine.entity.Pointer;

/**
 * 汉字转拼音进行比较
 * @author android
 *
 */

public class PinyinComparator implements Comparator{
	
	private String compareStr;//类名称，判断比较的是哪个类。

	public String getCompareStr() {
		return compareStr;
	}

	public void setCompareStr(String compareStr) {
		this.compareStr = compareStr;
	}

	@Override
	public int compare(Object o1, Object o2) {
		
		String str1 = "";
		String str2 = "";
		if(compareStr.equals("pointer"))
		{
			Pointer pointer1 = (Pointer)o1;
			Pointer pointer2 = (Pointer)o2;
			str1 = pointer1.getProduct().getProdName();
			str2 = pointer2.getProduct().getProdName();
		}
		 for (int i = 0; i < str1.length() && i < str2.length(); i++) {

	            int codePoint1 = str1.charAt(i);
	            int codePoint2 = str2.charAt(i);

	            if (codePoint1 != codePoint2) {
	                if (Character.isSupplementaryCodePoint(codePoint1)//确定指定字符是否为Unicode空白字符
	                        || Character.isSupplementaryCodePoint(codePoint2)) {
	                    return codePoint1 - codePoint2;
	                }

	                String pinyin1 = pinyin((char) codePoint1);
	                String pinyin2 = pinyin((char) codePoint2);

	                if (pinyin1 != null && pinyin2 != null) { // 两个字符都是汉字
	                    if (!pinyin1.equals(pinyin2)) {
	                        return pinyin1.compareTo(pinyin2);
	                    }
	                } else {
	                    return codePoint1 - codePoint2;
	                }
	            }
	        }
	        return str1.length() - str2.length();
	}

	/**
     * 字符的拼音，多音字就得到第一个拼音。不是汉字，就return null。
     */
    private String pinyin(char c) {
        String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
        if (pinyins == null) {
            return null;
        }
        for(String s : pinyins)
        {
        	Log.i("", s + "----------------->");
        }
        if(pinyins.length > 1){
        	return pinyins[1];
        }
        return pinyins[0];
    }
}
