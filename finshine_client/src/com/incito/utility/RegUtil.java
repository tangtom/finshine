package com.incito.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <dl>
 * <dt>RegUtil.java</dt>
 * <dd>Description:TODO</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014年7月14日 下午4:14:38</dd>
 * </dl>
 * 
 * @author LiNing
 */
public class RegUtil {

	/**
	 * 检查字符串是否为电话号码的方法,并返回true or false的判断值
	 */
	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;
		/**
		 * 可接受的电话格式有: ^//(? : 可以使用 "(" 作为开头 (//d{3}): 紧接着三个数字 //)? : 可以使用")"接续
		 * [- ]? : 在上述格式后可以使用具选择性的 "-". (//d{3}) : 再紧接着三个数字 [- ]? : 可以使用具选择性的
		 * "-" 接续. (//d{5})$: 以五个数字结束. 可以比较下列数字格式: (123)456-7890, 123-456-7890,
		 * 1234567890, (123)-456-7890
		 */

		String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";

		/**
		 * 可接受的电话格式有: ^//(? : 可以使用 "(" 作为开头 (//d{3}): 紧接着三个数字 //)? : 可以使用")"接续
		 * [- ]? : 在上述格式后可以使用具选择性的 "-". (//d{4}) : 再紧接着四个数字 [- ]? : 可以使用具选择性的
		 * "-" 接续. (//d{4})$: 以四个数字结束. 可以比较下列数字格式: (02)3456-7890, 02-3456-7890,
		 * 0234567890, (02)-3456-7890
		 */

		String expression2 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";

		CharSequence inputStr = phoneNumber;
		/* 创建Pattern */
		Pattern pattern = Pattern.compile(expression);
		/* 将Pattern 以参数传入Matcher作Regular expression */
		Matcher matcher = pattern.matcher(inputStr);
		/* 创建Pattern2 */
		Pattern pattern2 = Pattern.compile(expression2);
		/* 将Pattern2 以参数传入Matcher2作Regular expression */
		Matcher matcher2 = pattern2.matcher(inputStr);
		// if (matcher.matches())// || matcher2.matches())
		if (matcher.matches() || matcher2.matches()) {
			isValid = true;
		}
		return isValid;
	}
	//"^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$"
	
	public static boolean isEmailNumberValid(String emailNumber) {
		String strPattern = "^[A-Za-z0-9][\\w\\._]*[a-zA-Z0-9]+@[A-Za-z0-9-_]+\\.([A-Za-z]{2,4})";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(emailNumber);
		return m.matches();
	}

}
