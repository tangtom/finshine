package com.finshine.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ETODO_ITEM.
*/
public class ETodoItemDao extends AbstractDao<ETodoItem, Long> {

    public static final String TABLENAME = "ETODO_ITEM";

    /**
     * Properties of entity ETodoItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property TodoItemId = new Property(1, Long.class, "todoItemId", false, "todoItemId");
        public final static Property UserId = new Property(2, Long.class, "userId", false, "userId");
        public final static Property StartTime = new Property(3, Long.class, "startTime", false, "startTime");
        public final static Property EndTime = new Property(4, Long.class, "endTime", false, "endTime");
        public final static Property CustomerId = new Property(5, Long.class, "customerId", false, "customerId");
        public final static Property Title = new Property(6, String.class, "title", false, "title");
        public final static Property Content = new Property(7, String.class, "content", false, "content");
        public final static Property Status = new Property(8, Integer.class, "status", false, "status");
    };


    public ETodoItemDao(DaoConfig config) {
        super(config);
    }
    
    public ETodoItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ETODO_ITEM' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'todoItemId' INTEGER," + // 1: todoItemId
                "'userId' INTEGER," + // 2: userId
                "'startTime' INTEGER," + // 3: startTime
                "'endTime' INTEGER," + // 4: endTime
                "'customerId' INTEGER," + // 5: customerId
                "'title' TEXT," + // 6: title
                "'content' TEXT," + // 7: content
                "'status' INTEGER);"); // 8: status
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ETODO_ITEM'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ETodoItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long todoItemId = entity.getTodoItemId();
        if (todoItemId != null) {
            stmt.bindLong(2, todoItemId);
        }
 
        Long userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(3, userId);
        }
 
        Long startTime = entity.getStartTime();
        if (startTime != null) {
            stmt.bindLong(4, startTime);
        }
 
        Long endTime = entity.getEndTime();
        if (endTime != null) {
            stmt.bindLong(5, endTime);
        }
 
        Long customerId = entity.getCustomerId();
        if (customerId != null) {
            stmt.bindLong(6, customerId);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(7, title);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(8, content);
        }
 
        Integer status = entity.getStatus();
        if (status != null) {
            stmt.bindLong(9, status);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ETodoItem readEntity(Cursor cursor, int offset) {
        ETodoItem entity = new ETodoItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // todoItemId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // userId
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // startTime
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // endTime
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // customerId
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // title
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // content
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8) // status
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ETodoItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTodoItemId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setUserId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setStartTime(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setEndTime(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setCustomerId(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setTitle(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setContent(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setStatus(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ETodoItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ETodoItem entity) {
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
