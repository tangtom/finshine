package com.incito.finshine.download.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.Process;
import android.text.TextUtils;
import android.widget.Toast;

import com.incito.finshine.R;
import com.incito.finshine.download.provider.FinshineDownload.FinshineDownloads;
import com.incito.finshine.manager.Constants;
import com.incito.finshine.manager.FinshineApplication;
import com.incito.wisdomsdk.log.WLog;
import com.incito.wisdomsdk.net.downloadmanager.DownloadManager;
import com.incito.wisdomsdk.net.downloadmanager.Downloads;
import com.incito.wisdomsdk.utils.FileUtils;
import com.incito.wisdomsdk.utils.StorageUtils;

public class FileDownloadManager {
	
    public static final long DEFAULT_CACHE_SIZE = 200 * 1024 * 1024; // 200MB
    public static final int MODE_CACHE = 0x00;
    public static final int MODE_DOWNLOAD = 0x01;

    public static final int QUALITY_HIGHT = 0x10;
    public static final int QUALITY_NORMAL = 0x11;

    public static final int RESULT_OK = 0;
    public static final int RESULT_FAILED = 1;

    private static final String PDF_EXT = ".pdf";
    private static final String CACHE_PDF_EXT = ".cache";
    private static final String CACHING_PDF_EXT = ".cache.tmp";
    private static final String DOWNLOADING_PDF_EXT = ".pdf.tmp";

    private DownloadManager downloadManager;

    private ContentResolver contentResolver;

    private DownloadCompleteReceiver downloadReceiver;

    private DownloadManagerContentObserver observer;

    private Map<Long, ListenerEntity> listenerMap;

    private File cacheDir;

    private File filesDir;

    private long cacheMaxSize;

    private Context context;

    private UpdateThread updateThread;

    private int downloadStatusIndex = -1;

    private Handler mHandler;

    public FileDownloadManager(Context context) {
        this(context, DEFAULT_CACHE_SIZE);
    }

    public FileDownloadManager(Context context, long cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
        this.context = context;
        downloadManager = new DownloadManager(context.getContentResolver(),
                context.getPackageName());
        downloadReceiver = new DownloadCompleteReceiver();
        contentResolver = context.getContentResolver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_PAUSED);
        context.registerReceiver(downloadReceiver, filter);
        listenerMap = new HashMap<Long, ListenerEntity>();
        cacheDir = StorageUtils.getOwnCacheDirectory(context,
                Constants.FINSHINE_FILE_CACHE_SDCARD_PATH);
        filesDir = StorageUtils.getOwnCacheDirectory(context,
                Constants.FINSHINE_FILE_DOWNLOAD_SDCARD_PATH);
        observer = new DownloadManagerContentObserver();
        contentResolver.registerContentObserver(
                Downloads.ALL_DOWNLOADS_CONTENT_URI, true, observer);
        mHandler = new Handler();
    }

    /**
     * @param url
     * @param mode
     *            MODE_CACHE or MODE_DOWNLOAD
     * @param quality
     *            QUALITY_HIGHT or QUALITY_NORMAL
     * @param listener
     */
    public synchronized void startDownload(final FileModel file,
            final int mode, final String fileUri, final int quality,
            final OnDownloadFinishedListener listener,
            final OnDownloadProgressListener progressListener) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String url = fileUri;
                Uri uri = Uri.parse(url);
                String sid = uri.getLastPathSegment();
                String dir = null;
                WLog.d(FileDownloadManager.class, "startDownload url === "
                        + url);
                if (mode == MODE_DOWNLOAD) {
                    dir = Constants.FINSHINE_FILE_DOWNLOAD_SDCARD_PATH;
                    File targetFile = new File(filesDir, getFileName(sid, mode,
                            quality));
                    boolean alreadyDownloadFinish = false;
                    boolean fileExists = false;
                    boolean insertDB = false;
                    int tipStringId = R.string.file_download_finish;
                    if (targetFile.exists()) {
                        // 本地文件存在
                        fileExists = true;
                    }
                    // 如果cache里有则将cache里面的文件copy到download目录中
                    File cacheFile = new File(cacheDir, getFileName(sid,
                            MODE_CACHE, quality));
                    if (!fileExists && cacheFile.exists()) {
                        if (FileUtils.copyFile(cacheFile, targetFile)) {
                            WLog.e(FileDownloadManager.class,  "copy file finish");
                            // TODO 判断一下缓存的文件是否已经下载完成
                            tipStringId = R.string.file_download_finish;
                            showToast(R.string.file_downloading);
                            alreadyDownloadFinish = true;
                            fileExists = true;
                        }
                    }

                    Cursor cursor = null;
                    try {
                        cursor = contentResolver.query(
                                FinshineDownloads.CONTENT_URI, null,
                                FinshineDownloads.COLUMN_NAME_FILE_ID + "=?",
                                new String[] { file.mId },
                                FinshineDownloads.DEFAULT_SORT_ORDER);
                        if (cursor != null && cursor.moveToFirst()) {
                            // 数据库中存在这条记录
                            insertDB = false;
                            if (fileExists) {
                                // 本地文件存在
                                tipStringId = R.string.file_downloaded;
                                if (downloadStatusIndex < 0) {
                                    downloadStatusIndex = cursor
                                            .getColumnIndex(FinshineDownloads.COLUMN_NAME_STATUS);
                                }
                                int downloadStatus = cursor
                                        .getInt(downloadStatusIndex);
                                if (FinshineDownloads.DOWNLOAD_STATUS_FINISH == downloadStatus) {
                                    alreadyDownloadFinish = true;
                                } else {
                                    // 未下载完成或者正在下载
                                    cursor.close();
                                    showToast(R.string.file_downloaded);
                                    return;
                                }
                            } else {
                                // 数据库中存在这条记录，但本地文件不存在，有可能用户把sd卡上的歌曲给删除掉了
                                alreadyDownloadFinish = false;
                            }
                        } else {
                            insertDB = true;
                        }
                    } catch (SQLiteException e) {
                    } finally {
                        if (cursor != null && !cursor.isClosed()) {
                            cursor.close();
                            cursor = null;
                        }
                    }
                    if (insertDB) {
                        ContentValues initialValues = new ContentValues();
                        initialValues
                                .put(FinshineDownloads.COLUMN_NAME_FILE_ID,
                                        file.mId);
                        initialValues.put(
                                FinshineDownloads.COLUMN_NAME_FILE_NAME,
                                file.mName);
                        initialValues.put(
                                FinshineDownloads.COLUMN_NAME_FILE_PATH,
                                targetFile.getAbsolutePath().toString());
                        initialValues
                                .put(FinshineDownloads.COLUMN_NAME_FILE_URL,
                                        fileUri);
                        initialValues
                                .put(FinshineDownloads.COLUMN_NAME_STATUS,
                                        alreadyDownloadFinish ? FinshineDownloads.DOWNLOAD_STATUS_FINISH
                                                : FinshineDownloads.DOWNLOAD_STATUS_DOWNLOADING);
                        initialValues.put(
                                FinshineDownloads.COLUMN_NAME_PROGRESS,
                                alreadyDownloadFinish ? 1.0f : 0.0f);
                        initialValues.put(
                                FinshineDownloads.COLUMN_NAME_CREATETIME,
                                System.currentTimeMillis());
                        contentResolver.insert(FinshineDownloads.CONTENT_URI,
                                initialValues);
                    }

                    if (alreadyDownloadFinish) {
                        onDownloadFinish(context, listener, RESULT_OK, url,
                                targetFile.getAbsolutePath(), tipStringId);
                        return;
                    } else {
                        showToast(R.string.file_downloading);
                    }
                } else {
                    dir = Constants.FINSHINE_FILE_CACHE_SDCARD_PATH;
                    long size = getFileSize(cacheDir);
                    if (size > cacheMaxSize) {
                        FileUtils.deleteEarliestFile(cacheDir,
                                getFileName(sid, mode, quality));
                    }
                }

                DownloadManager.Request down = new DownloadManager.Request(
                        Uri.parse(url));
                down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                        | DownloadManager.Request.NETWORK_WIFI);
                down.setDestinationInExternalPublicDir(dir,
                        getDownloadingFileName(sid, mode, quality));
                long downloadId = downloadManager.enqueue(down);
                listenerMap.put(downloadId, new ListenerEntity(url, sid, mode,
                        quality, listener, progressListener));
            }

        }.start();
    }

    public synchronized void removeDownload(String url) {
        Set<Long> keys = listenerMap.keySet();
        for (Long key : keys) {
            ListenerEntity le = listenerMap.get(key);
            if (le.url.equals(url)) {
                downloadManager.remove(key);
                listenerMap.remove(key);
                break;
            }
        }
        contentResolver.delete(FinshineDownloads.CONTENT_URI,
                FinshineDownloads.COLUMN_NAME_FILE_URL + "=?",
                new String[] { url });
    }

    public synchronized void restartUnFinishedDonwloads() {
        HashMap<String, ListenerEntity> currentDownloading = new HashMap<String, ListenerEntity>();
        Set<Long> keys = listenerMap.keySet();
        for (Long key : keys) {
            ListenerEntity le = listenerMap.get(key);
            if (!currentDownloading.containsKey(le.url)) {
                currentDownloading.put(le.url, le);
            }
        }

        int songUrlColumnIndex = 0;
        Cursor cursor = null;
        try {
            cursor = contentResolver
                    .query(FinshineDownloads.CONTENT_URI,
                            null,
                            FinshineDownloads.COLUMN_NAME_STATUS + "=?",
                            new String[] { Integer
                                    .toString(FinshineDownloads.DOWNLOAD_STATUS_DOWNLOADING) },
                            FinshineDownloads.DEFAULT_SORT_ORDER);
            if (cursor != null && cursor.moveToFirst()) {
                songUrlColumnIndex = cursor
                        .getColumnIndex(FinshineDownloads.COLUMN_NAME_FILE_URL);
                do {
                    String url = cursor.getString(songUrlColumnIndex);
                    if (!currentDownloading.containsKey(url)) {
                        Uri uri = Uri.parse(url);
                        String sid = uri.getLastPathSegment();
                        String dir = Constants.FINSHINE_FILE_DOWNLOAD_SDCARD_PATH;

                        DownloadManager.Request down = new DownloadManager.Request(
                                Uri.parse(url));
                        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                                | DownloadManager.Request.NETWORK_WIFI);
                        down.setDestinationInExternalPublicDir(dir,
                                getFileName(sid, MODE_DOWNLOAD, QUALITY_NORMAL));
                        long downloadId = downloadManager.enqueue(down);
                        listenerMap
                                .put(downloadId, new ListenerEntity(url, sid,
                                        MODE_DOWNLOAD, QUALITY_NORMAL, null,
                                        null));
                    }
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
                cursor = null;
            }
        }
    }

    /**
     * 先获取高质量，再获取低质量音乐。
     * 
     * @param url
     * @return the file path
     */
    public String getFile(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        String sid = Uri.parse(url).getLastPathSegment();
        return getFileWithFileId(sid);
    }

    /**
     * 先获取高质量，再获取低质量音乐。
     * 
     * @param url
     * @return the file path
     */
    public String getFileWithFileId(String fileId) {
        if (!StorageUtils.isExternalStorageReady()) {
            return null;
        }
        if (TextUtils.isEmpty(fileId)) {
            return null;
        }
        File[] dirs = new File[] { filesDir, cacheDir };
        int[] modes = new int[] { MODE_DOWNLOAD, MODE_CACHE };
        int[] qualities = new int[] { QUALITY_HIGHT, QUALITY_NORMAL };

        String sid = fileId;
        for (int i = 0; i < modes.length; i++) {
            int mode = modes[i];
            File dir = dirs[i];
            for (int j = 0; j < qualities.length; j++) {
                String name = getFileName(sid, mode, qualities[j]);
                File file = new File(dir, name);
                if (file.exists())
                    return file.getAbsolutePath();
            }
        }
        return null;
    }

    public String getFile(String url, int mode, int quality) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        String sid = Uri.parse(url).getLastPathSegment();
        return getFileWithSongId(sid, mode, quality);
    }

    public String getFileWithSongId(String sid, int mode, int quality) {
        if (!StorageUtils.isExternalStorageReady()) {
            return null;
        }
        if (TextUtils.isEmpty(sid)) {
            return null;
        }

        File dir = null;
        if (mode == MODE_DOWNLOAD) {
            dir = filesDir;
        } else if (mode == MODE_CACHE) {
            dir = cacheDir;
        } else {
            throw new IllegalArgumentException();
        }

        String name = getFileName(sid, mode, quality);
        File file = new File(dir, name);
        if (file.exists())
            return file.getAbsolutePath();
        return null;
    }

    private String getFileName(String sid, int mode, int quality) {
        String suffix = mode == MODE_CACHE ? CACHE_PDF_EXT : PDF_EXT;
        StringBuilder sb = new StringBuilder();
        sb.append(sid).append("_").append(quality).append(suffix);
        return sb.toString();
    }

    private String getDownloadingFileName(String sid, int mode, int quality) {
        String suffix = mode == MODE_CACHE ? CACHING_PDF_EXT
                : DOWNLOADING_PDF_EXT;
        StringBuilder sb = new StringBuilder();
        sb.append(sid).append("_").append(quality).append(suffix);
        return sb.toString();
    }

    final class ListenerEntity {
        String url;
        String sid;
        int mode;
        int quality;
        OnDownloadFinishedListener finishListener;
        OnDownloadProgressListener progressListener;

        public ListenerEntity(String url, String sid, int mode, int quality,
                OnDownloadFinishedListener listener,
                OnDownloadProgressListener progressListener) {
            this.url = url;
            this.sid = sid;
            this.mode = mode;
            this.quality = quality;
            this.finishListener = listener;
            this.progressListener = progressListener;
        }
    }

    public static interface OnDownloadFinishedListener {
        void onFinished(String url, int result, String filePath);
    }

    public static interface OnDownloadProgressListener {
        void onProgress(String url, double progress, String filePath);
    }

    /**
     * Receives notifications when the data in the content provider changes
     */
    class DownloadManagerContentObserver extends ContentObserver {

        public DownloadManagerContentObserver() {
            super(new Handler());
        }

        /**
         * Receives notification when the data in the observed content provider
         * changes.
         */
        public void onChange(final boolean selfChange) {
            updateDownloadProgress();
        }

    }

    private void updateDownloadProgress() {
        if (updateThread == null) {
            updateThread = new UpdateThread();
            updateThread.start();
        }
        updateThread.weekup();
    }

    class UpdateThread extends Thread {
        Object object = new Object();
        boolean running = true;
        DecimalFormat decimalFormat = new DecimalFormat("0.000");

        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            while (!releaseCalled) {
                if (updateThread != this) {
                    throw new IllegalStateException(
                            "multiple UpdateThreads in DownloadService");
                }
                synchronized (object) {
                    if (!running) {
                        try {
                            object.wait();
                            continue;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        running = false;
                    }
                }

                Cursor cursor = context.getContentResolver()
                        .query(Downloads.ALL_DOWNLOADS_CONTENT_URI,
                                null,
                                Downloads.COLUMN_STATUS + "=?",
                                new String[] { String
                                        .valueOf(Downloads.STATUS_RUNNING) },
                                null);
                // WLog.e(SongDownloadManager.class,"cursor count  == " +
                // cursor.getCount());
                if (cursor != null && cursor.getCount() > 0
                        && listenerMap.size() > 0) {
                    try {
                        int idColumn = cursor
                                .getColumnIndexOrThrow(Downloads._ID);

                        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
                                .moveToNext()) {
                            long id = cursor.getLong(idColumn);
                            if (!listenerMap.containsKey(id)) {
                                continue;
                            }
                            int loadedSize = cursor
                                    .getInt(cursor
                                            .getColumnIndex(Downloads.COLUMN_CURRENT_BYTES));
                            int totalSize = cursor
                                    .getInt(cursor
                                            .getColumnIndex(Downloads.COLUMN_TOTAL_BYTES));
                            if (totalSize <= 0) {
                                continue;
                            }
                            ListenerEntity le = listenerMap.get(id);

                            File file = new File(
                                    le.mode == MODE_CACHE ? cacheDir : filesDir,
                                    getFileName(le.sid, le.mode, le.quality));
                            String progress = decimalFormat.format(loadedSize
                                    * 1.0 / totalSize);
                            if (le.mode == MODE_DOWNLOAD) {
                                onDownloadProgressChanged(context, le.url,
                                        Double.parseDouble(progress));
                            }
                            if (le.progressListener != null) {
                                le.progressListener.onProgress(le.url,
                                        Double.parseDouble(progress),
                                        file.getAbsolutePath());
                            }
                        }
                    } finally {
                        cursor.close();
                    }
                }
            }
        }

        public void weekup() {
            synchronized (object) {
                running = true;
                object.notifyAll();
            }
        }
    }

    class DownloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            long downId = intent.getLongExtra(
                    DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (intent.getAction().equals(
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                int status = intent.getIntExtra(
                        DownloadManager.EXTRA_DOWNLOAD_STATUS, -1);
                switch (status) {
                case Downloads.STATUS_SUCCESS:
                    onDownloadFinish(status, downId, RESULT_OK, true);
                    break;
                case Downloads.STATUS_FILE_ALREADY_EXISTS_ERROR:
                    onDownloadFinish(status, downId, RESULT_OK, true);
                    break;
                default:
                    onDownloadFinish(status, downId, RESULT_FAILED, true);
                    break;
                }
            } else if (intent.getAction().equals(
                    DownloadManager.ACTION_DOWNLOAD_PAUSED)) {
                int status = intent.getIntExtra(
                        DownloadManager.EXTRA_DOWNLOAD_STATUS, -1);
                switch (status) {
                case Downloads.STATUS_WAITING_TO_RETRY:
                    break;
                case Downloads.STATUS_WAITING_FOR_NETWORK:
                    break;
                case Downloads.STATUS_QUEUED_FOR_WIFI:
                    break;
                default:
                }
                int count = downloadManager.remove(downId);
                onDownloadFinish(status, downId, RESULT_FAILED, count > 0);
            }
        }
    }

    private synchronized void onDownloadFinish(int reason, long downId,
            int result, boolean removeListener) {
        if (listenerMap.containsKey(downId)) {
            ListenerEntity le = listenerMap.get(downId);
            File cachingFile = new File(le.mode == MODE_CACHE ? cacheDir
                    : filesDir, getDownloadingFileName(le.sid, le.mode,
                    le.quality));
            File file = new File(le.mode == MODE_CACHE ? cacheDir : filesDir,
                    getFileName(le.sid, le.mode, le.quality));
            if (result == RESULT_OK
                    && reason != Downloads.STATUS_FILE_ALREADY_EXISTS_ERROR
                    && cachingFile.exists() && !file.exists()) {
                cachingFile.renameTo(file);
            }
            if (le.mode == MODE_DOWNLOAD) {
                if (result == RESULT_OK) {
                    // 如果是下载歌曲，可以在MediaStore中看到
                    MediaScannerConnection.scanFile(context,
                            new String[] { file.getAbsolutePath() },
                            new String[] { null }, null);
                }
                onDownloadFinish(context, le.finishListener, result, le.url,
                        file.getAbsolutePath(), R.string.file_download_finish);
            } else if (le.mode == MODE_CACHE) {
                onDownloadFinish(context, le.finishListener, result, le.url,
                        file.getAbsolutePath(), 0);
            }
            if (removeListener) {
                listenerMap.remove(downId);
            }
        }
    }

    private boolean releaseCalled = false;

    public void release() {
        releaseCalled = true;
        context.unregisterReceiver(downloadReceiver);
        context.getContentResolver().unregisterContentObserver(observer);
        listenerMap.clear();
        if (updateThread != null) {
            updateThread.weekup();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (!releaseCalled) {
            release();
        }
    }

    public static final long getFileSize(File file) {
        int size = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                size += getFileSize(f);
            }
            return size;
        } else {
            try {
                FileInputStream fin = new FileInputStream(file);
                size += fin.available();
                if (fin != null) {
                    fin.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return size;
        }
    }

    private synchronized void onDownloadProgressChanged(Context context,
            String url, double progress) {
        ContentValues values = new ContentValues();
        values.put(FinshineDownloads.COLUMN_NAME_STATUS,
                FinshineDownloads.DOWNLOAD_STATUS_DOWNLOADING);
        values.put(FinshineDownloads.COLUMN_NAME_PROGRESS, progress);
        contentResolver
                .update(FinshineDownloads.CONTENT_URI,
                        values,
                        FinshineDownloads.COLUMN_NAME_FILE_URL + "=? AND "
                                + FinshineDownloads.COLUMN_NAME_STATUS + "=?",
                        new String[] {
                                url,
                                Integer.toString(FinshineDownloads.DOWNLOAD_STATUS_DOWNLOADING) });
    }

    private synchronized void onDownloadFinish(Context context,
            OnDownloadFinishedListener listener, int result, String url,
            String filePath, int tipStringId) {
        ContentValues values = new ContentValues();
        if (RESULT_OK == result) {
            values.put(FinshineDownloads.COLUMN_NAME_STATUS,
                    FinshineDownloads.DOWNLOAD_STATUS_FINISH);
            values.put(FinshineDownloads.COLUMN_NAME_PROGRESS, 1.0f);
        } else {
            values.put(FinshineDownloads.COLUMN_NAME_STATUS,
                    FinshineDownloads.DOWNLOAD_STATUS_DOWNLOADING);
        }
        contentResolver.update(FinshineDownloads.CONTENT_URI, values,
                FinshineDownloads.COLUMN_NAME_FILE_URL + "=?",
                new String[] { url });
        if (listener != null) {
            listener.onFinished(url, result, filePath);
        }
        if (tipStringId != 0) {
            showToast(tipStringId);
        }
    }

    private void showToast(final int resid) {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(FinshineApplication.getInst(), resid,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
