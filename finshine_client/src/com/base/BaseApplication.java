/*
 * Copyright (c) 2012, Andy.fang Corporation, All Rights Reserved
 */
package com.base;

import com.android.core.base.AApplication;
import com.android.core.util.AppToast;
import com.incito.finshine.common.Constant;
import android.os.Environment;
import java.io.File;

/**
 * @description 基类方便扩展
 * @author Andy.fang
 * @createDate 2014年8月6日
 * @version 1.0
 */
public class BaseApplication extends AApplication {

	@Override
	public void onCreate() {
		super.onCreate();
		initSd();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
	}

	/**
	 * @description 判断否有SD卡设备在启动时建立文件目录
	 * @author Andy.fang
	 * @createDate 2014年8月14日
	 */
	public void initSd() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File sdcard = Environment.getExternalStorageDirectory();
			if (sdcard.exists()) {// SD卡是否可用
				File rootFile = new File(Constant.FINSHINE);
				if (!rootFile.exists()) {//
					rootFile.mkdirs();
				}
			} else {
				AppToast.ShowToast("SD卡不能使用");
			}
		} else {
			File rootFile = new File("finshine");
			if (rootFile.exists()) {
				rootFile.mkdirs();
			} else {
				AppToast.ShowToast("内存卡不能用");
			}
		}
	}

}
