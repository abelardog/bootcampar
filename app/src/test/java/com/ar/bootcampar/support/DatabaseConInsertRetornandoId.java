package com.ar.bootcampar.support;

import com.ar.bootcampar.model.IContentValuesWrapper;
import com.ar.bootcampar.model.ICursorWrapper;
import com.ar.bootcampar.model.ISQLiteDatabaseWrapper;

public class DatabaseConInsertRetornandoId implements ISQLiteDatabaseWrapper {

    private long id;

    public DatabaseConInsertRetornandoId(long id) {
        this.id = id;
    }

    @Override
    public void close() {

    }

    @Override
    public ICursorWrapper query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return null;
    }

    @Override
    public void endTransaction() {

    }

    @Override
    public void setTransactionSuccessful() {

    }

    @Override
    public void beginTransaction() {

    }

    @Override
    public long insert(String table, String nullColumnHack, IContentValuesWrapper values) {
        return id;
    }

    @Override
    public int delete(String table, String whereClause, String[] whereArgs) {
        return 0;
    }

    @Override
    public int update(String table, IContentValuesWrapper values, String whereClause, String[] whereArgs) {
        return 0;
    }

    @Override
    public void execSQL(String sql) {

    }
}

