package com.ar.bootcampar.model;

import android.database.sqlite.SQLiteDatabase;

import com.ar.bootcampar.model.utilities.CursorWrapper;
import com.ar.bootcampar.model.utilities.IContentValuesWrapper;
import com.ar.bootcampar.model.utilities.ICursorWrapper;
import com.ar.bootcampar.model.utilities.ISQLiteDatabaseWrapper;

public class SQLiteDatabaseWrapper implements ISQLiteDatabaseWrapper {
    private final SQLiteDatabase sqliteDatabase;

    public SQLiteDatabaseWrapper(SQLiteDatabase sqliteDatabase) {
        this.sqliteDatabase = sqliteDatabase;
    }

    @Override
    public void close() {
        this.sqliteDatabase.close();
    }

    @Override
    public ICursorWrapper query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return new CursorWrapper(this.sqliteDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy));
    }

    @Override
    public void endTransaction() {
        this.sqliteDatabase.endTransaction();
    }

    @Override
    public void setTransactionSuccessful() {
        this.sqliteDatabase.setTransactionSuccessful();
    }

    @Override
    public void beginTransaction() {
        this.sqliteDatabase.beginTransaction();
    }

    @Override
    public long insert(String table, String nullColumnHack, IContentValuesWrapper values) {
        return this.sqliteDatabase.insert(table, nullColumnHack, values.generate());
    }

    @Override
    public int delete(String table, String whereClause, String[] whereArgs) {
        return this.sqliteDatabase.delete(table, whereClause, whereArgs);
    }

    @Override
    public int update(String table, IContentValuesWrapper values, String whereClause, String[] whereArgs) {
        return this.sqliteDatabase.update(table, values.generate(), whereClause, whereArgs);
    }

    @Override
    public void execSQL(String sql) {
        this.sqliteDatabase.execSQL(sql);
    }
}