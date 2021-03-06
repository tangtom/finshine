package com.finshine.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.finshine.dao.EProdReleasestrategy;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table EPROD_RELEASESTRATEGY.
*/
public class EProdReleasestrategyDao extends AbstractDao<EProdReleasestrategy, Long> {

    public static final String TABLENAME = "EPROD_RELEASESTRATEGY";

    /**
     * Properties of entity EProdReleasestrategy.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ProdReleasestrategyId = new Property(1, Long.class, "prodReleasestrategyId", false, "prodReleasestrategyId");
        public final static Property Status = new Property(2, String.class, "status", false, "status");
        public final static Property Created = new Property(3, java.util.Date.class, "created", false, "created");
        public final static Property Lastmod = new Property(4, java.util.Date.class, "lastmod", false, "lastmod");
        public final static Property Version = new Property(5, Long.class, "version", false, "version");
        public final static Property SalesIds = new Property(6, Long.class, "salesIds", false, "salesIds");
        public final static Property ProdId = new Property(7, Long.class, "prodId", false, "prodId");
    };


    public EProdReleasestrategyDao(DaoConfig config) {
        super(config);
    }
    
    public EProdReleasestrategyDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'EPROD_RELEASESTRATEGY' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'prodReleasestrategyId' INTEGER," + // 1: prodReleasestrategyId
                "'status' TEXT," + // 2: status
                "'created' INTEGER," + // 3: created
                "'lastmod' INTEGER," + // 4: lastmod
                "'version' INTEGER," + // 5: version
                "'salesIds' INTEGER," + // 6: salesIds
                "'prodId' INTEGER);"); // 7: prodId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EPROD_RELEASESTRATEGY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, EProdReleasestrategy entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long prodReleasestrategyId = entity.getProdReleasestrategyId();
        if (prodReleasestrategyId != null) {
            stmt.bindLong(2, prodReleasestrategyId);
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
 
        Long salesIds = entity.getSalesIds();
        if (salesIds != null) {
            stmt.bindLong(7, salesIds);
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
    public EProdReleasestrategy readEntity(Cursor cursor, int offset) {
        EProdReleasestrategy entity = new EProdReleasestrategy( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // prodReleasestrategyId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // status
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)), // created
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // lastmod
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // version
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6), // salesIds
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7) // prodId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, EProdReleasestrategy entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setProdReleasestrategyId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setStatus(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreated(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
        entity.setLastmod(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setVersion(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setSalesIds(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
        entity.setProdId(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(EProdReleasestrategy entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(EProdReleasestrategy entity) {
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
