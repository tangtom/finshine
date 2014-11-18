package com.incito.finshine.manager;

public class Constants {
    public static final String FINSHINE_FILE_CACHE_SDCARD_PATH = "Incito/finshine/files";
    public static final String FINSHINE_FILE_DOWNLOAD_SDCARD_PATH = "Incito/finshine/download";
    public static final long POINTER_STATUS_DEFAULT = -1;//我的 积分 --全部类型
    public static final long POINTER_STATUS_VERIFING = 1;//我的积分 -- 核实中
    public static final long POINTER_STATUS_ACCOUNTED = 2;//我的积分 -- 已到账
    
    public static final String POINTER_ORDER_SORT_DEFAULT = "DEFAULT";//我的积分--默认排序
    public static final String POINTER_ORDER_SORT_DEAL_DATE = "DEAL_DATE";//我的积分--成交时间
    public static final String POINTER_ORDER_SORT_PROD_NAME="PROD_NAME";//我的积分--产品名称
}
