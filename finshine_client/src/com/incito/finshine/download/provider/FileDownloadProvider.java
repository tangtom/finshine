package com.incito.finshine.download.provider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class FileDownloadProvider extends ContentProvider {
    private static final String TAG = "FileDownloadProvider";
    private static final String DATABASE_NAME = "finshine.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * A projection map used to select columns from the database
     */
    private static HashMap<String, String> sFileDownloadsProjectionMap;
    /*
     * Constants used by the Uri matcher to choose an action based on the pattern
     * of the incoming URI
     */
    private static final int FILE_DOWNLOADS = 1;
    private static final int FILE_DOWNLOADS_ID = 2;


    /**
     * A UriMatcher instance
     */
    private static final UriMatcher sUriMatcher;

    // Handle to a new DatabaseHelper.
    private DatabaseHelper mOpenHelper;


    /**
     * A block that instantiates and sets static objects
     */
    static {

        /*
         * Creates and initializes the URI matcher
         */
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(FinshineDownload.AUTHORITY, "finshinedownloads", FILE_DOWNLOADS);
        sUriMatcher.addURI(FinshineDownload.AUTHORITY, "finshinedownloads/#", FILE_DOWNLOADS_ID);


        /*
         * Creates and initializes a projection map that returns all columns
         */

        // Creates a new projection map instance. The map returns a column name
        // given a string. The two are usually equal.
        sFileDownloadsProjectionMap = new HashMap<String, String>();
        sFileDownloadsProjectionMap.put(FinshineDownload.FinshineDownloads._ID, FinshineDownload.FinshineDownloads._ID);
        sFileDownloadsProjectionMap.put(FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_ID, FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_ID);
        sFileDownloadsProjectionMap.put(FinshineDownload.FinshineDownloads.COLUMN_NAME_USER_ID, FinshineDownload.FinshineDownloads.COLUMN_NAME_USER_ID);
        sFileDownloadsProjectionMap.put(FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_NAME, FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_NAME);
        sFileDownloadsProjectionMap.put(FinshineDownload.FinshineDownloads.COLUMN_NAME_USER_NAME, FinshineDownload.FinshineDownloads.COLUMN_NAME_USER_NAME);
        sFileDownloadsProjectionMap.put(FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_PATH, FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_PATH);
        sFileDownloadsProjectionMap.put(FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_URL, FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_URL);
        sFileDownloadsProjectionMap.put(FinshineDownload.FinshineDownloads.COLUMN_NAME_STATUS, FinshineDownload.FinshineDownloads.COLUMN_NAME_STATUS);
        sFileDownloadsProjectionMap.put(FinshineDownload.FinshineDownloads.COLUMN_NAME_PROGRESS, FinshineDownload.FinshineDownloads.COLUMN_NAME_PROGRESS);
        sFileDownloadsProjectionMap.put(FinshineDownload.FinshineDownloads.COLUMN_NAME_CREATETIME, FinshineDownload.FinshineDownloads.COLUMN_NAME_CREATETIME);
    }

    /**
    *
    * This class helps open, create, and upgrade the database file. Set to package visibility
    * for testing purposes.
    */
   static class DatabaseHelper extends SQLiteOpenHelper {

       DatabaseHelper(Context context) {

           // calls the super constructor, requesting the default cursor factory.
           super(context, DATABASE_NAME, null, DATABASE_VERSION);
       }

       /**
        *
        * Creates the underlying database with table name and column names taken from the
        * NotePad class.
        */
       @Override
       public void onCreate(SQLiteDatabase db) {
           db.execSQL("CREATE TABLE " + FinshineDownload.FinshineDownloads.TABLE_NAME + " ("
                   + FinshineDownload.FinshineDownloads._ID + " INTEGER PRIMARY KEY,"
                   + FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_ID + " TEXT,"
                   + FinshineDownload.FinshineDownloads.COLUMN_NAME_USER_ID + " INTEGER,"
                   + FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_NAME + " TEXT,"
                   + FinshineDownload.FinshineDownloads.COLUMN_NAME_USER_NAME + " TEXT,"
                   + FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_PATH + " TEXT,"
                   + FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_URL + " TEXT,"
                   + FinshineDownload.FinshineDownloads.COLUMN_NAME_STATUS + " INTEGER,"
                   + FinshineDownload.FinshineDownloads.COLUMN_NAME_PROGRESS + " REAL,"
                   + FinshineDownload.FinshineDownloads.COLUMN_NAME_CREATETIME + " INTEGER"
                   + ");");
       }

       @Override
       public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

           // Logs that the database is being upgraded
           Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                   + newVersion + ", which will destroy all old data");

           // Kills the table and existing data
           db.execSQL("DROP TABLE IF EXISTS notes");

           // Recreates the database with a new version
           onCreate(db);
       }
   }

   @Override
   public boolean onCreate() {
       mOpenHelper = new DatabaseHelper(getContext());
       return true;
   }

   @Override
   public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
           String sortOrder) {

       // Constructs a new query builder and sets its table name
       SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
       qb.setTables(FinshineDownload.FinshineDownloads.TABLE_NAME);

       /**
        * Choose the projection and adjust the "where" clause based on URI pattern-matching.
        */
       switch (sUriMatcher.match(uri)) {
           case FILE_DOWNLOADS:
               qb.setProjectionMap(sFileDownloadsProjectionMap);
               break;
           case FILE_DOWNLOADS_ID:
               qb.setProjectionMap(sFileDownloadsProjectionMap);
               qb.appendWhere(
                       FinshineDownload.FinshineDownloads._ID + "=" +
                   uri.getPathSegments().get(FinshineDownload.FinshineDownloads.FILE_DOWNLOADS_ID_PATH_POSITION));
               break;                                                           
           default:
               // If the URI doesn't match any of the known patterns, throw an exception.
               throw new IllegalArgumentException("Unknown URI " + uri);
       }


       String orderBy;
       // If no sort order is specified, uses the default
       if (TextUtils.isEmpty(sortOrder)) {
           orderBy = FinshineDownload.FinshineDownloads.DEFAULT_SORT_ORDER;
       } else {
           // otherwise, uses the incoming sort order
           orderBy = sortOrder;
       }

       // Opens the database object in "read" mode, since no writes need to be done.
       SQLiteDatabase db = mOpenHelper.getReadableDatabase();

       /*
        * Performs the query. If no problems occur trying to read the database, then a Cursor
        * object is returned; otherwise, the cursor variable contains null. If no records were
        * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
        */
       Cursor c = qb.query(
           db,            // The database to query
           projection,    // The columns to return from the query
           selection,     // The columns for the where clause
           selectionArgs, // The values for the where clause
           null,          // don't group the rows
           null,          // don't filter by row groups
           orderBy        // The sort order
       );

       // Tells the Cursor what URI to watch, so it knows when its source data changes
       c.setNotificationUri(getContext().getContentResolver(), uri);
       return c;
   }

   @Override
   public String getType(Uri uri) {

       switch (sUriMatcher.match(uri)) {
           case FILE_DOWNLOADS:
               return FinshineDownload.FinshineDownloads.CONTENT_TYPE;
           case FILE_DOWNLOADS_ID:
               return FinshineDownload.FinshineDownloads.CONTENT_ITEM_TYPE;
           default:
               throw new IllegalArgumentException("Unknown URI " + uri);
       }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {

        // Validates the incoming URI. Only the full provider URI is allowed for inserts.
        if (sUriMatcher.match(uri) != FILE_DOWNLOADS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // A map to hold the new record's values.
        ContentValues values;

        // If the incoming values map is not null, uses it for the new values.
        if (initialValues != null) {
            values = new ContentValues(initialValues);

        } else {
            // Otherwise, create a new value map
            values = new ContentValues();
        }

        // Gets the current system time in milliseconds
        Long now = Long.valueOf(System.currentTimeMillis());

        // If the values map doesn't contain the creation date, sets the value to the current time.
        if (values.containsKey(FinshineDownload.FinshineDownloads.COLUMN_NAME_CREATETIME) == false) {
            values.put(FinshineDownload.FinshineDownloads.COLUMN_NAME_CREATETIME, now);
        }

        // If the values map doesn't contain a title, sets the value to the default title.
        if (values.containsKey(FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_NAME) == false) {
            Resources r = Resources.getSystem();
            values.put(FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_NAME, r.getString(android.R.string.untitled));
        }

        // If the values map doesn't contain artist, sets the value to an empty string.
        if (values.containsKey(FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_PATH) == false) {
            values.put(FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_PATH, "");
        }

        // If the values map doesn't contain singer, sets the value to an empty string.
        if (values.containsKey(FinshineDownload.FinshineDownloads.COLUMN_NAME_USER_NAME) == false) {
            values.put(FinshineDownload.FinshineDownloads.COLUMN_NAME_USER_NAME, "");
        }

        // Opens the database object in "write" mode.
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        // Performs the insert and returns the ID of the new note.
        long rowId = db.insert(
            FinshineDownload.FinshineDownloads.TABLE_NAME,        // The table to insert into.
            FinshineDownload.FinshineDownloads.COLUMN_NAME_FILE_PATH,  // A hack, SQLite sets this column value to null
                                             // if values is empty.
            values                           // A map of column names, and the values to insert
                                             // into the columns.
        );

        // If the insert succeeded, the row ID exists.
        if (rowId > 0) {
            // Creates a URI with the note ID pattern and the new row ID appended to it.
            Uri noteUri = ContentUris.withAppendedId(FinshineDownload.FinshineDownloads.CONTENT_ID_URI_BASE, rowId);

            // Notifies observers registered against this provider that the data changed.
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        // If the insert didn't succeed, then the rowID is <= 0. Throws an exception.
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {

        // Opens the database object in "write" mode.
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        String finalWhere;

        int count;

        // Does the delete based on the incoming URI pattern.
        switch (sUriMatcher.match(uri)) {

            // If the incoming pattern matches the general pattern for notes, does a delete
            // based on the incoming "where" columns and arguments.
            case FILE_DOWNLOADS:
                count = db.delete(
                    FinshineDownload.FinshineDownloads.TABLE_NAME,  // The database table name
                    where,                     // The incoming where clause column names
                    whereArgs                  // The incoming where clause values
                );
                break;

                // If the incoming URI matches a single note ID, does the delete based on the
                // incoming data, but modifies the where clause to restrict it to the
                // particular note ID.
            case FILE_DOWNLOADS_ID:
                /*
                 * Starts a final WHERE clause by restricting it to the
                 * desired note ID.
                 */
                finalWhere =
                        FinshineDownload.FinshineDownloads._ID +                              // The ID column name
                        " = " +                                          // test for equality
                        uri.getPathSegments().                           // the incoming note ID
                            get(FinshineDownload.FinshineDownloads.FILE_DOWNLOADS_ID_PATH_POSITION)
                ;

                // If there were additional selection criteria, append them to the final
                // WHERE clause
                if (where != null) {
                    finalWhere = finalWhere + " AND " + where;
                }

                // Performs the delete.
                count = db.delete(
                    FinshineDownload.FinshineDownloads.TABLE_NAME,  // The database table name.
                    finalWhere,                // The final WHERE clause
                    whereArgs                  // The incoming where clause values.
                );
                break;

            // If the incoming pattern is invalid, throws an exception.
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        /*Gets a handle to the content resolver object for the current context, and notifies it
         * that the incoming URI changed. The object passes this along to the resolver framework,
         * and observers that have registered themselves for the provider are notified.
         */
        getContext().getContentResolver().notifyChange(uri, null);

        // Returns the number of rows deleted.
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {

        // Opens the database object in "write" mode.
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        String finalWhere;

        // Does the update based on the incoming URI pattern
        switch (sUriMatcher.match(uri)) {

            // If the incoming URI matches the general notes pattern, does the update based on
            // the incoming data.
            case FILE_DOWNLOADS:
                // Does the update and returns the number of rows updated.
                count = db.update(
                    FinshineDownload.FinshineDownloads.TABLE_NAME, // The database table name.
                    values,                   // A map of column names and new values to use.
                    where,                    // The where clause column names.
                    whereArgs                 // The where clause column values to select on.
                );
                break;

            // If the incoming URI matches a single note ID, does the update based on the incoming
            // data, but modifies the where clause to restrict it to the particular note ID.
            case FILE_DOWNLOADS_ID:
                finalWhere =
                        FinshineDownload.FinshineDownloads._ID +                              // The ID column name
                        " = " +                                          // test for equality
                        uri.getPathSegments().                           // the incoming note ID
                            get(FinshineDownload.FinshineDownloads.FILE_DOWNLOADS_ID_PATH_POSITION)
                ;

                // If there were additional selection criteria, append them to the final WHERE
                // clause
                if (where !=null) {
                    finalWhere = finalWhere + " AND " + where;
                }


                // Does the update and returns the number of rows updated.
                count = db.update(
                    FinshineDownload.FinshineDownloads.TABLE_NAME, // The database table name.
                    values,                   // A map of column names and new values to use.
                    finalWhere,               // The final WHERE clause to use
                                              // placeholders for whereArgs
                    whereArgs                 // The where clause column values to select on, or
                                              // null if the values are in the where argument.
                );
                break;
            // If the incoming pattern is invalid, throws an exception.
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        /*Gets a handle to the content resolver object for the current context, and notifies it
         * that the incoming URI changed. The object passes this along to the resolver framework,
         * and observers that have registered themselves for the provider are notified.
         */
        getContext().getContentResolver().notifyChange(uri, null);

        // Returns the number of rows updated.
        return count;
    }

    DatabaseHelper getOpenHelperForTest() {
        return mOpenHelper;
    }
}
