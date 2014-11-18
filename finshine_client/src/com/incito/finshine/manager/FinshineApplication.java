package com.incito.finshine.manager;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.base.BaseApplication;
import com.incito.abs.model.GlobalModel;
import com.incito.wisdomsdk.cache.disk.impl.TotalSizeLimitedDiscCache;
import com.incito.wisdomsdk.cache.disk.naming.Md5FileNameGenerator;
import com.incito.wisdomsdk.cache.mem.AbstractMemoryCache;
import com.incito.wisdomsdk.image.loader.ImageLoader;
import com.incito.wisdomsdk.image.loader.ImageLoaderConfiguration;
import com.incito.wisdomsdk.image.loader.assist.LRULimitedMemoryCacheBitmapCache;
import com.incito.wisdomsdk.image.loader.assist.LRUMemoryCacheBitmapCache;
import com.incito.wisdomsdk.image.loader.assist.QueueProcessingType;
import com.incito.wisdomsdk.log.WLog;
import com.incito.wisdomsdk.net.download.BaseImageDownloader;
import com.incito.wisdomsdk.net.download.SlowNetworkImageDownloader;
import com.incito.wisdomsdk.utils.StorageUtils;

public class FinshineApplication extends BaseApplication {

    private static String IMEI;

    private static FinshineApplication sInstance;

    public static FinshineApplication getInst() {
        return sInstance;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        sInstance = this;
        GlobalModel.init(getApplicationContext());

        TelephonyManager tm = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        IMEI = tm.getDeviceId();

        File cacheDir = StorageUtils.getOwnCacheDirectory(
                getApplicationContext(), D.IAMGE_CACHE_SDCARD_PATH);
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 8);
        AbstractMemoryCache<String, Bitmap> memoryCache;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            memoryCache = new LRUMemoryCacheBitmapCache(memoryCacheSize);
        } else {
            memoryCache = new LRULimitedMemoryCacheBitmapCache(memoryCacheSize);
        }
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(memoryCache)
                .denyCacheImageMultipleSizesInMemory()
                .discCache(
                        new TotalSizeLimitedDiscCache(cacheDir,
                                new Md5FileNameGenerator(), 10 * 1024 * 1024))
                .imageDownloader(
                        new SlowNetworkImageDownloader(new BaseImageDownloader(
                                this)))
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // .enableLogging() // Not necessary in common
                .build();
        ImageLoader.getInstance().init(config);

        CoreManager.getInstance().init();
        SPManager.getInstance().init(this);
        WLog.d(FinshineApplication.class, "onCreate finish");
    }

}
