package com.finshine.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import com.finshine.dao.EAdvertisementDao;
import com.finshine.dao.EAnnualIncomExpendDao;
import com.finshine.dao.EAppProdDao;
import com.finshine.dao.EArticleDao;
import com.finshine.dao.EArticleAdDao;
import com.finshine.dao.EAssetInfoDao;
import com.finshine.dao.EContactNoteDao;
import com.finshine.dao.ECustomerDao;
import com.finshine.dao.ENoticeDao;
import com.finshine.dao.EPhotoDao;
import com.finshine.dao.EProdAppointmentDao;
import com.finshine.dao.EProdCollectionDao;
import com.finshine.dao.EProdInvestMentDao;
import com.finshine.dao.EProdLikeDao;
import com.finshine.dao.EProdReleasestrategyDao;
import com.finshine.dao.EProductDao;
import com.finshine.dao.EProductSearchDao;
import com.finshine.dao.ESalesAppProdDao;
import com.finshine.dao.ETodoItemDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 1): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        EAdvertisementDao.createTable(db, ifNotExists);
        EAnnualIncomExpendDao.createTable(db, ifNotExists);
        EAppProdDao.createTable(db, ifNotExists);
        EArticleDao.createTable(db, ifNotExists);
        EArticleAdDao.createTable(db, ifNotExists);
        EAssetInfoDao.createTable(db, ifNotExists);
        EContactNoteDao.createTable(db, ifNotExists);
        ECustomerDao.createTable(db, ifNotExists);
        ENoticeDao.createTable(db, ifNotExists);
        EPhotoDao.createTable(db, ifNotExists);
        EProdAppointmentDao.createTable(db, ifNotExists);
        EProdCollectionDao.createTable(db, ifNotExists);
        EProdInvestMentDao.createTable(db, ifNotExists);
        EProdLikeDao.createTable(db, ifNotExists);
        EProdReleasestrategyDao.createTable(db, ifNotExists);
        EProductDao.createTable(db, ifNotExists);
        EProductSearchDao.createTable(db, ifNotExists);
        ESalesAppProdDao.createTable(db, ifNotExists);
        ETodoItemDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        EAdvertisementDao.dropTable(db, ifExists);
        EAnnualIncomExpendDao.dropTable(db, ifExists);
        EAppProdDao.dropTable(db, ifExists);
        EArticleDao.dropTable(db, ifExists);
        EArticleAdDao.dropTable(db, ifExists);
        EAssetInfoDao.dropTable(db, ifExists);
        EContactNoteDao.dropTable(db, ifExists);
        ECustomerDao.dropTable(db, ifExists);
        ENoticeDao.dropTable(db, ifExists);
        EPhotoDao.dropTable(db, ifExists);
        EProdAppointmentDao.dropTable(db, ifExists);
        EProdCollectionDao.dropTable(db, ifExists);
        EProdInvestMentDao.dropTable(db, ifExists);
        EProdLikeDao.dropTable(db, ifExists);
        EProdReleasestrategyDao.dropTable(db, ifExists);
        EProductDao.dropTable(db, ifExists);
        EProductSearchDao.dropTable(db, ifExists);
        ESalesAppProdDao.dropTable(db, ifExists);
        ETodoItemDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(EAdvertisementDao.class);
        registerDaoClass(EAnnualIncomExpendDao.class);
        registerDaoClass(EAppProdDao.class);
        registerDaoClass(EArticleDao.class);
        registerDaoClass(EArticleAdDao.class);
        registerDaoClass(EAssetInfoDao.class);
        registerDaoClass(EContactNoteDao.class);
        registerDaoClass(ECustomerDao.class);
        registerDaoClass(ENoticeDao.class);
        registerDaoClass(EPhotoDao.class);
        registerDaoClass(EProdAppointmentDao.class);
        registerDaoClass(EProdCollectionDao.class);
        registerDaoClass(EProdInvestMentDao.class);
        registerDaoClass(EProdLikeDao.class);
        registerDaoClass(EProdReleasestrategyDao.class);
        registerDaoClass(EProductDao.class);
        registerDaoClass(EProductSearchDao.class);
        registerDaoClass(ESalesAppProdDao.class);
        registerDaoClass(ETodoItemDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}