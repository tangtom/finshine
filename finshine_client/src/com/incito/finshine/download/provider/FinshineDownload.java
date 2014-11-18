package com.incito.finshine.download.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * 文件下载模型
 * 
 * @author qiujiaheng
 * 
 */
public class FinshineDownload {
    public static final String AUTHORITY = "com.incito.finshine.download.provider.FinshineDownload";

    public FinshineDownload() {

    }

    /**
     * Notes table contract
     */
    public static class FinshineDownloads implements BaseColumns {
        // This class cannot be instantiated
        public FinshineDownloads() {
        }

        public static final String TABLE_NAME = "FinshineDownloads";

        /**
         * The scheme part for this provider's URI
         */
        private static final String SCHEME = "content://";

        /**
         * Path part for the FinshineDownloads URI
         */
        private static final String PATH_FILE_DOWNLOADS = "/finshinedownloads";
        /**
         * Path part for the FinshineDownloads ID URI
         */
        private static final String PATH_FILE_DONWLOADS_ID = "/finshinedownloads/";

        /**
         * 0-relative position of a FinshineDownloads ID segment in the path
         * part of a FinshineDownloads ID URI
         */
        public static final int FILE_DOWNLOADS_ID_PATH_POSITION = 1;

        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY
                + PATH_FILE_DOWNLOADS);

        /**
         * The content URI base for a single note. Callers must append a numeric
         * note id to this Uri to retrieve a note
         */
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME
                + AUTHORITY + PATH_FILE_DONWLOADS_ID);

        /**
         * The content URI match pattern for a single note, specified by its ID.
         * Use this to match incoming URIs or to construct an Intent.
         */
        public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME
                + AUTHORITY + PATH_FILE_DONWLOADS_ID + "/#");

        /*
         * MIME type definitions
         */

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of notes.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/com.incito.finshine.filedownload";

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single
         * note.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/com.incito.finshine.filedownload";

        /**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = "createtime DESC";

        public static final int DOWNLOAD_STATUS_DOWNLOADING = 0;
        public static final int DOWNLOAD_STATUS_FINISH = 1;
        public static final int DOWNLOAD_STATUS_DELETED = 2;

        /*
         * Column definitions
         */
        public static final String COLUMN_NAME_FILE_ID = "file_id";
        public static final String COLUMN_NAME_USER_ID = "uid";
        public static final String COLUMN_NAME_FILE_NAME = "filename";
        public static final String COLUMN_NAME_USER_NAME = "username";
        public static final String COLUMN_NAME_FILE_PATH = "filepath";
        public static final String COLUMN_NAME_FILE_URL = "fileurl";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_PROGRESS = "progress";
        public static final String COLUMN_NAME_CREATETIME = "createtime";

    }

}
