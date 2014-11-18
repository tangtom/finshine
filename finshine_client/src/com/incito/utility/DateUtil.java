package com.incito.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.text.TextUtils;

@SuppressLint("SimpleDateFormat")
public class DateUtil {
    /** 15:03:34 */
    public static final String FORMAT_HH_MM_SS = "HH:mm:ss";
    /** 15:03 */
    public static final String FORMAT_HH_MM = "HH:mm";
    /** 12月12日 */
    public static final String FORMAT_MM_DD = "MM月dd日";
    /** 20120219 */
    public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
    /** 2012-02-19 */
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    /** 2012-02-19 */
    public static final String FORMAT_YYYY_MM_DD_ZH = "yyyy年MM月dd日";
    /** 2012-02-19 05:11 */
    public static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    /** 20120219150334 */
    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    /** 20120219150334 */
    public static final String FORMAT_DATABASE = "yyyyMMddHHmmss";
    /** 20120219150334 */
    public static final String FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    /** 2012-02-19 15:03:34 */
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /** 2012-02-19 15:03:34.876 */
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    /** 2012-02-19 12时19分 */
    public static final String FORMAT_YYYY_MM_DD_HHMM_WORD = "yyyy-MM-dd HH时mm分";
    /** 2012年02月19日12:19 */
    public static final String FORMAT_YYYY_MM_DD_HHMM_WORD_ZH = "yyyy年MM月dd日HH:mm";

    /**
     * @author: zhaguitao
     * @Title: formatString2Date
     * @Description: 将日期字符串转换成Date对象
     * @param dateStr
     *            日期字符串
     * @param format
     *            日期字符串样式
     * @return Date对象
     * @date: 2013-5-22 下午2:07:29
     */
    public static Date formatString2Date(String dateStr, String format) {
        if (TextUtils.isEmpty(dateStr)) {
            return null;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @author: zhaguitao
     * @Title: formatDate2String
     * @Description: 将日期对象转换成日期字符串
     * @param date
     *            日期对象
     * @param format
     *            日期字符串样式
     * @return 日期字符串
     * @date: 2013-5-22 下午2:12:17
     */
    public static String formatDate2String(Date date, String format) {
        if (date == null) {
            return "";
        }

        try {
            SimpleDateFormat formatPattern = new SimpleDateFormat(format);
            return formatPattern.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Description:转换数据库时间为UI显示样式
     * 
     * @param dbTime
     *            数据库查询出的时间<br>
     *            数据库内的时间格式统一为yyyyMMddHHmmss
     * @param formatType
     *            目标格式
     * @return 目标格式的字符串
     */
    public static String convertDBOperateTime2SpecifyFormat(String dbTime,
            String formatType) {
        // 转换数据库时间为UI显示样式
        return getNewFormatDateString(dbTime, FORMAT_DATABASE, formatType);
    }

    /**
     * @author: zhaguitao
     * @Title: formatDateString
     * @Description: 将日期字符串格式化成数据库样式
     * @param dateStr
     *            日期字符串
     * @param fromFormat
     *            原始样式
     * @return 数据库样式的日期字符串
     * @date: 2013-5-22 上午10:15:35
     */
    public static String getDBOperateTime(String dateStr, String fromFormat) {
        // 1、将原始日期字符串转换成Date对象
        Date date = formatString2Date(dateStr, fromFormat);

        // 2、将Date对象转换成数据库样式样式字符串
        return formatDate2String(date, FORMAT_DATABASE);
    }

    /**
     * Description:所有数据库操作时间取值都采用本方法
     * 
     * @return 当前时间yyyyMMddHHmmss格式，如：20120219111945
     */
    public static String getDBOperateTime() {
        return getCurrentTimeSpecifyFormat(FORMAT_DATABASE);
    }

    /**
     * Description:获取当前日期指定样式字符串
     * 
     * @param formatType
     *            指定的日期样式
     * @return 当前日期指定样式字符串
     */
    public static String getCurrentTimeSpecifyFormat(String formatType) {
        Date date = new Date();

        return formatDate2String(date, formatType);
    }

    /**
     * Description:获取昨天指定样式字符串
     * 
     * @param formatType
     *            指定的日期样式
     * @return 昨天日期指定样式字符串
     */
    public static String getYesterdaySpecifyFormat(String formatType) {
        Date date = new Date();
        date = new Date(date.getTime() - 1000 * 60 * 60 * 24);
        return formatDate2String(date, formatType);
    }

    /**
     * @author: zhaguitao
     * @Title: getNewFormatDateString
     * @Description: 格式化日期字符串
     * @param dateStr
     *            日期字符串
     * @param fromFormat
     *            原始样式
     * @param toFormat
     *            目标样式
     * @return 目标样式的日期字符串
     * @date: 2013-5-22 上午10:15:35
     */
    public static String getNewFormatDateString(String dateStr,
            String fromFormat, String toFormat) {

        // 1、将原始日期字符串转换成Date对象
        Date date = formatString2Date(dateStr, fromFormat);

        // 2、将Date对象转换成目标样式字符串
        return formatDate2String(date, toFormat);
    }

    /**
     * @author: zhaguitao
     * @Title: realDateIntervalDay
     * @Description: 计算日期之间的间隔天数
     * @param dateFrom
     *            起始日期
     * @param dateTo
     *            结束日期
     * @return 日期之间的间隔天数
     * @date: 2013-5-22 下午2:25:28
     */
    public static int realDateIntervalDay(Date dateFrom, Date dateTo) {
        Calendar fromCal = Calendar.getInstance();
        fromCal.setTime(dateFrom);

        Calendar toCal = Calendar.getInstance();
        toCal.setTime(dateTo);

        if (fromCal.after(toCal)) {
            // swap dates so that fromCal is start and toCal is end
            Calendar swap = fromCal;
            fromCal = toCal;
            toCal = swap;
        }
        int days = toCal.get(Calendar.DAY_OF_YEAR)
                - fromCal.get(Calendar.DAY_OF_YEAR);
        int y2 = toCal.get(Calendar.YEAR);
        if (fromCal.get(Calendar.YEAR) != y2) {
            fromCal = (Calendar) fromCal.clone();
            do {
                // 得到当年的实际天数
                days += fromCal.getActualMaximum(Calendar.DAY_OF_YEAR);
                fromCal.add(Calendar.YEAR, 1);
            } while (fromCal.get(Calendar.YEAR) != y2);
        }
        return days;
    }

    /**
     * @author: zhaguitao
     * @Title: getDateTimeByFormatAndMs
     * @Description: 将毫秒型日期转换成指定格式日期字符串
     * @param longTime
     * @param format
     * @return
     * @date: 2013-2-25 下午12:00:51
     */
    public static String getDateTimeByFormatAndMs(long longTime, String format) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(longTime);

        return formatDate2String(c.getTime(), format);
    }
}

