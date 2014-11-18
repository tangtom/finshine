package com.incito.utility;

import java.util.Comparator;

import com.incito.finshine.entity.Pointer;
import com.incito.utility.DateUtil;
public class ComparatorPointerByTime  implements Comparator{

	@Override
	public int compare(Object obj1, Object obj2) {
		Pointer pointer1 = (Pointer)obj1;
		Pointer pointer2 = (Pointer)obj2;
		return DateUtil.formatDate2String(pointer2.getDateOfCreate(), DateUtil.FORMAT_YYYYMMDD).compareTo(
			DateUtil.formatDate2String(pointer1.getDateOfCreate(), DateUtil.FORMAT_YYYYMMDD)
		);
	}

}
