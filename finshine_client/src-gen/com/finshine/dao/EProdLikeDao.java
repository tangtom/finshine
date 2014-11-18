package com.finshine.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.finshine.dao.EProdLike;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table EPROD_LIKE.
*/
public class EProdLikeDao extends AbstractDao<EProdLike, Long> {

    public static final String TABLENAME = "EPROD_LIKE";

    /**
     * Properties of entity EProdLike.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ProdLikeId = new Property(1, Long.class, "prodLikeId", false, "prodLikeId");
        public final static Property Status = new Property(2, String.class, "status", false, "status");
        public final static Property Created = new Property(3, java.util.Date.class, "created", false, "created");
        public final static Property Lastmod = new Property(4, java.util.Date.class, "lastmod", false, "lastmod");
        public final static Property Version = new Property(5, Long.class, "version", false, "version");
        public final static Property SalesId = new Property(6, Long.class, "salesId", false, "salesId");
        public final static Property ProdId = new Property(7, Long.class, "prodId", false, "prodId");
    };


    public EProdLikeDao(DaoConfig config) {
        super(config);
    }
    
    public EProdLikeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'EPROD_LIKE' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'prodLikeId' INTEGER," + // 1: prodLikeId
                "'status' TEXT," + // 2: status
                "'created' INTEGER," + // 3: created
                "'lastmod' INTEGER," + // 4: lastmod
                "'version' INTEGER," + // 5: version
                "'salesId' INTEGER," + // 6: salesId
                "'prodId' INTEGER);"); // 7: prodId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EPROD_LIKE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, EProdLike entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long prodLikeId = entity.getProdLikeId();
        if (prodLikeId != null) {
            stmt.bindLong(2, prodLikeId);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(3, status);
        }
 
        java.util.Date created = entity.getCreated();
        if (created != null) {
            stmt.bindLong(4, created.getTime());
        }
 
        java.util.Date lastmod = entity.getLastmod();
        if (lastmod != null) {
            stmt.bindLong(5, lastmod.getTime());
        }
 
        Long version = entity.getVersion();
        if (version != null) {
            stmt.bindLong(6, version);
        }
 
        Long salesId = entity.getSalesId();
        if (salesId != null) {
            stmt.bindLong(7, salesId);
        }
 
        Long prodId = entity.getProdId();
        if (prodId != null) {
            stmt.bindLong(8, prodId);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public EProdLike readEntity(Cursor cursor, int offset) {
        EProdLike entity = new EProdLike( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // prodLikeId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // status
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)), // created
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // lastmod
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // version
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6), // salesId
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7) // prodId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, EProdLike entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setProdLikeId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setStatus(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreated(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
        entity.setLastmod(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setVersion(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setSalesId(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
        entity.setProdId(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(EProdLike entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(EProdLike entity) {
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
