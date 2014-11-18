package com.finshine.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.finshine.dao.ESalesAppProd;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ESALES_APP_PROD.
*/
public class ESalesAppProdDao extends AbstractDao<ESalesAppProd, Long> {

    public static final String TABLENAME = "ESALES_APP_PROD";

    /**
     * Properties of entity ESalesAppProd.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property SalesAppProdId = new Property(1, Long.class, "salesAppProdId", false, "salesAppProdId");
        public final static Property SalesId = new Property(2, Long.class, "salesId", false, "salesId");
        public final static Property SalesXy = new Property(3, String.class, "salesXy", false, "salesXy");
        public final static Property SalesTopsale = new Property(4, String.class, "salesTopsale", false, "salesTopsale");
        public final static Property SalesOrderamount = new Property(5, String.class, "salesOrderamount", false, "salesOrderamount");
        public final static Property SalesPhone = new Property(6, String.class, "salesPhone", false, "salesPhone");
        public final static Property ProdAmount = new Property(7, Float.class, "prodAmount", false, "prodAmount");
    };


    public ESalesAppProdDao(DaoConfig config) {
        super(config);
    }
    
    public ESalesAppProdDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ESALES_APP_PROD' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'salesAppProdId' INTEGER," + // 1: salesAppProdId
                "'salesId' INTEGER," + // 2: salesId
                "'salesXy' TEXT," + // 3: salesXy
                "'salesTopsale' TEXT," + // 4: salesTopsale
                "'salesOrderamount' TEXT," + // 5: salesOrderamount
                "'salesPhone' TEXT," + // 6: salesPhone
                "'prodAmount' REAL);"); // 7: prodAmount
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ESALES_APP_PROD'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ESalesAppProd entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long salesAppProdId = entity.getSalesAppProdId();
        if (salesAppProdId != null) {
            stmt.bindLong(2, salesAppProdId);
        }
 
        Long salesId = entity.getSalesId();
        if (salesId != null) {
            stmt.bindLong(3, salesId);
        }
 
        String salesXy = entity.getSalesXy();
        if (salesXy != null) {
            stmt.bindString(4, salesXy);
        }
 
        String salesTopsale = entity.getSalesTopsale();
        if (salesTopsale != null) {
            stmt.bindString(5, salesTopsale);
        }
 
        String salesOrderamount = entity.getSalesOrderamount();
        if (salesOrderamount != null) {
            stmt.bindString(6, salesOrderamount);
        }
 
        String salesPhone = entity.getSalesPhone();
        if (salesPhone != null) {
            stmt.bindString(7, salesPhone);
        }
 
        Float prodAmount = entity.getProdAmount();
        if (prodAmount != null) {
            stmt.bindDouble(8, prodAmount);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ESalesAppProd readEntity(Cursor cursor, int offset) {
        ESalesAppProd entity = new ESalesAppProd( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // salesAppProdId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // salesId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // salesXy
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // salesTopsale
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // salesOrderamount
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // salesPhone
            cursor.isNull(offset + 7) ? null : cursor.getFloat(offset + 7) // prodAmount
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ESalesAppProd entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSalesAppProdId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setSalesId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setSalesXy(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSalesTopsale(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSalesOrderamount(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSalesPhone(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setProdAmount(cursor.isNull(offset + 7) ? null : cursor.getFloat(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ESalesAppProd entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ESalesAppProd entity) {
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