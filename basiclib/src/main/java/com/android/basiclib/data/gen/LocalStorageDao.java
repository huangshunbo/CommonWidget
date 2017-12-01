package com.android.basiclib.data.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.android.basiclib.bean.LocalStorage;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOCAL_STORAGE".
*/
public class LocalStorageDao extends AbstractDao<LocalStorage, Long> {

    public static final String TABLENAME = "LOCAL_STORAGE";

    /**
     * Properties of entity LocalStorage.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Key = new Property(1, String.class, "key", false, "KEY");
        public final static Property Value = new Property(2, String.class, "value", false, "VALUE");
    }


    public LocalStorageDao(DaoConfig config) {
        super(config);
    }
    
    public LocalStorageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOCAL_STORAGE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "\"KEY\" TEXT NOT NULL ," + // 1: key
                "\"VALUE\" TEXT NOT NULL );"); // 2: value
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOCAL_STORAGE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LocalStorage entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindString(2, entity.getKey());
        stmt.bindString(3, entity.getValue());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LocalStorage entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindString(2, entity.getKey());
        stmt.bindString(3, entity.getValue());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public LocalStorage readEntity(Cursor cursor, int offset) {
        LocalStorage entity = new LocalStorage( //
            cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // key
            cursor.getString(offset + 2) // value
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LocalStorage entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setKey(cursor.getString(offset + 1));
        entity.setValue(cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LocalStorage entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LocalStorage entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LocalStorage entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}