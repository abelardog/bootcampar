package com.ar.bootcampar.support;

import com.ar.bootcampar.model.IContentValuesWrapper;
import com.ar.bootcampar.model.ICursorWrapper;
import com.ar.bootcampar.model.ISQLiteDatabaseWrapper;

public class SqliteDatabaseWrapperSpy implements ISQLiteDatabaseWrapper {
    private String tableName;
    private long id;
    private IContentValuesWrapper insertedValues;
    private boolean transactionSuccessfulCalled;
    private boolean closeCalled;
    private boolean beginTransactionCalled;
    private boolean endTransactionCalled;

    public SqliteDatabaseWrapperSpy(long id) {
        this.id = id;
    }

    public SqliteDatabaseWrapperSpy() {
        this.id = 1;
    }

    @Override
    public void close() {
        this.closeCalled = true;
    }

    @Override
    public ICursorWrapper query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return null;
    }

    @Override
    public void endTransaction() {
        this.endTransactionCalled = true;
    }

    @Override
    public void setTransactionSuccessful() {
        this.transactionSuccessfulCalled = true;
    }

    @Override
    public void beginTransaction() {
        this.beginTransactionCalled = true;
    }

    @Override
    public long insert(String table, String nullColumnHack, IContentValuesWrapper values) {
        tableName = table;
        insertedValues = values;
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

    public String getTableName() { return tableName; }

    public IContentValuesWrapper getInsertedValues() { return insertedValues; }

    public boolean getBeginTransactionCalled() { return beginTransactionCalled; }
    public boolean getEndTransactionCalled() { return endTransactionCalled; }
    public boolean getTransactionSuccessfulCalled() { return transactionSuccessfulCalled; }
    public boolean getCloseCalled() { return closeCalled; }
}