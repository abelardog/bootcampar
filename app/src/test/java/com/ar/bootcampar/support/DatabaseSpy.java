package com.ar.bootcampar.support;

import android.content.ContentValues;

import com.ar.bootcampar.model.IContentValuesWrapper;
import com.ar.bootcampar.model.ICursorWrapper;
import com.ar.bootcampar.model.ISQLiteDatabaseWrapper;

public class DatabaseSpy implements ISQLiteDatabaseWrapper {
    private String tableName;
    private IContentValuesWrapper insertedValues;

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
        tableName = table;
        insertedValues = values;
        return 1;
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

    public String getTableName() { return tableName; }

    public IContentValuesWrapper getInsertedValues() { return insertedValues; }
}

