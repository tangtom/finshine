package com.incito.abs.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.incito.finshine.download.provider.FileDownloadManager;

public class GlobalModel {
	
    private static GlobalModel sInstance;
    private final Context mContext;
    public final SharedPreferences mPrefs;
    public final FileDownloadManager mFileDownloadManager;

    public static void init(Context context) {
        if (sInstance == null) {
            sInstance = new GlobalModel(context);
        }
    }

    public final static GlobalModel getInst() {
        return sInstance;
    }

    private GlobalModel(Context context) {
        mContext = context;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext
                .getApplicationContext());
        // Initialize song download manager
        mFileDownloadManager = new FileDownloadManager(
                context.getApplicationContext());
        mFileDownloadManager.restartUnFinishedDonwloads();
    }

}
