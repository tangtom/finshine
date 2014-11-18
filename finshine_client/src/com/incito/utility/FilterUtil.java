package com.incito.utility;

import android.content.res.Resources;

import com.incito.finshine.R;

public class FilterUtil {

	// 前一个是数字 后一个是带有两个单位的数字 eg：1-3个月  还有12个月以上
	public static int getSelectedBtnValue(String str, boolean isFirst) {
		if (str.equals("全部")) {
			if (isFirst)
				return -1;
			else
				return 0;
		} else {
			String[] selectedValue = str.split("-");
			if (selectedValue.length > 1)
			{
				if (isFirst) {
					return Integer.valueOf(selectedValue[0].substring(0,
							selectedValue[0].length()));
				} else {
					return Integer.valueOf(selectedValue[1].substring(0,
							selectedValue[1].length() - 2));
				}
			}
			else
			{
				if (isFirst) {
					return Integer.valueOf(selectedValue[0].substring(0,
							2));
				} else {
					return 0;
				}
			}
			
		}
	}

	// 前后都是数字 并且带有% eg：10%-12%   还有12%以上
	public static int getPercentBtnValue(String str, boolean isFirst) {
		if (str.equals("全部")) {
			if (isFirst)
				return -1;
			else
				return 0;
		} else {
			String[] selectedValue = str.split("-");
			if (selectedValue.length > 1)
			{
				if (isFirst) {
					return Integer.valueOf(selectedValue[0].substring(0,
							selectedValue[0].length() - 1));
				} else {
					return Integer.valueOf(selectedValue[1].substring(0,
							selectedValue[1].length() - 1));
				}
			}
			else
			{
				if (isFirst) {
					return Integer.valueOf(selectedValue[0].substring(0,
							2));
				} else {
					return 0;
				}
			}
			
		}
	}
	//产品期限
	public static int getProdTime(String str, boolean isFirst)
	{
		int index = 0;
		if (str.equals("全部"))
		{
			return index;
		}
		else
		{
			if(str.equals("1年以下"))
			{
				if(isFirst)
				{
					index = 0;
				}
				else
				{
					index = 1;
				}
			}
			else if(str.equals("1-2年"))
			{
				if(isFirst)
				{
					index = 1;
				}
				else
				{
					index = 2;
				}
			}
			else if(str.equals("2-5年"))
			{
				if(isFirst)
				{
					index = 2;
				}
				else
				{
					index = 5;
				}
			}
			else if(str.equals("5年+"))
			{
				if(isFirst)
				{
					index = 5;
				}
				else
				{
					index = 0;
				}
			}
			return index;
		}
	}

	// 投资偏好
	public static int getInvestmentValue(String str, boolean isFirst,
			String[] investValue) {
		int index = 0;
		if (str.equals("全部"))
			return index;
		else {
			String[] selectedValue = str.split("-");
			if (isFirst) {
				String firstStr = selectedValue[0];
				if (firstStr.equals("资管"))
					index = 1;
				else
					index = 3;// 信托
			} else {
				String secondStr = selectedValue[1];
				// String[] investValue = getResources().getStringArray(
				// R.array.investment2_status);
				for (int i = 0; i < investValue.length; i++) {
					if (secondStr.equals((investValue)[i])) {
						index = i;
						break;
					}
				}
			}
		}
		if(index == 6)
		{
			index = 7;//公募类（6）已去除
		}
		return index;
	}

	// 前一个是数字 后一个是带有一个单位的数字,并且有的只有一个值 eg：5-100万 300万+
	public static int getUnSureBtnValue(String str, boolean isFirst) {
		if (str.equals("全部")) {
			if (isFirst)
				return -1;
			else
				return 0;
		} else {
			String[] selectedValue = str.split("-");
			if (selectedValue.length > 1) {
				if (isFirst) {
					return Integer.valueOf(selectedValue[0].substring(0,
							selectedValue[0].length()));
				} else {
					return Integer.valueOf(selectedValue[1].substring(0,
							selectedValue[1].length() - 1));
				}
			} else {
				if (isFirst) {
					return Integer.valueOf(selectedValue[0].substring(0,
							selectedValue[0].length() - 2));
				} else {
					return 0;
				}
			}
		}
	}

	// 产品状态 0新建 1审核失败 2审核成功 3预约 4预售 5在售 6封账 7下架、不填就不传参 全部 预售 在售 封帐
	public static int getProductStatus(int position) {
		int index = -1;
		switch (position) {
		case 0:
			break;
		case 1:
		case 2:
		case 3:
			index = position + 3;
			break;
		case 4:
			index = position + 3;
			break;

		default:
			break;
		}
		return index;
	}
	
	public static int getProductStatus(String statusName){
		int index = -1;
		if(statusName.equals("在售"))
		{
			index = 5;
		}else if(statusName.equals("封帐"))
		{
			index = 6;
		}
		return index;
	}

}
