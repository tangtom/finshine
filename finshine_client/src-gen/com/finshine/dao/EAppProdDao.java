package com.finshine.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.finshine.dao.EAppProd;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table EAPP_PROD.
*/
public class EAppProdDao extends AbstractDao<EAppProd, Long> {

    public static final String TABLENAME = "EAPP_PROD";

    /**
     * Properties of entity EAppProd.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property AppProdId = new Property(1, Long.class, "appProdId", false, "appProdId");
        public final static Property ProdName = new Property(2, String.class, "prodName", false, "prodName");
        public final static Property ProdMyid = new Property(3, String.class, "prodMyid", false, "prodMyid");
        public final static Property ProdAdmin = new Property(4, String.class, "prodAdmin", false, "prodAdmin");
        public final static Property UserId = new Property(5, Long.class, "userId", false, "userId");
        public final static Property Created = new Property(6, java.util.Date.class, "created", false, "created");
        public final static Property ProdAmount = new Property(7, Float.class, "prodAmount", false, "prodAmount");
        public final static Property ProdStatus = new Property(8, Long.class, "prodStatus", false, "prodStatus");
    };


    public EAppProdDao(DaoConfig config) {
        super(config);
    }
    
    public EAppProdDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'EAPP_PROD' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'appProdId' INTEGER," + // 1: appProdId
                "'prodName' TEXT," + // 2: prodName
                "'prodMyid' TEXT," + // 3: prodMyid
                "'prodAdmin' TEXT," + // 4: prodAdmin
                "'userId' INTEGER," + // 5: userId
                "'created' INTEGER," + // 6: created
                "'prodAmount' REAL," + // 7: prodAmount
                "'prodStatus' INTEGER);"); // 8: prodStatus
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EAPP_PROD'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, EAppProd entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long appProdId = entity.getAppProdId();
        if (appProdId != null) {
            stmt.bindLong(2, appProdId);
        }
 
        String prodName = entity.getProdName();
        if (prodName != null) {
            stmt.bindString(3, prodName);
        }
 
        String prodMyid = entity.getProdMyid();
        if (prodMyid != null) {
            stmt.bindString(4, prodMyid);
        }
 
        String prodAdmin = entity.getProdAdmin();
        if (prodAdmin != null) {
            stmt.bindString(5, prodAdmin);
        }
 
        Long userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(6, userId);
        }
 
        java.util.Date created = entity.getCreated();
        if (created != null) {
            stmt.bindLong(7, created.getTime());
        }
 
        Float prodAmount = entity.getProdAmount();
        if (prodAmount != null) {
            stmt.bindDouble(8, prodAmount);
        }
 
        Long prodStatus = entity.getProdStatus();
        if (prodStatus != null) {
            stmt.bindLong(9, prodStatus);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public EAppProd readEntity(Cursor cursor, int offset) {
        EAppProd entity = new EAppProd( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // appProdId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // prodName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // prodMyid
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // prodAdmin
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // userId
            cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)), // created
            cursor.isNull(offset + 7) ? null : cursor.getFloat(offset + 7), // prodAmount
            cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8) // prodStatus
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, EAppProd entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAppProdId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setProdName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setProdMyid(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setProdAdmin(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUserId(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setCreated(cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)));
        entity.setProdAmount(cursor.isNull(offset + 7) ? null : cursor.getFloat(offset + 7));
        entity.setProdStatus(cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(EAppProd entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(EAppProd entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
