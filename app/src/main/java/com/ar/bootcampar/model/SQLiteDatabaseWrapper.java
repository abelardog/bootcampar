package com.ar.bootcampar.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteDatabaseWrapper implements ISQLiteDatabaseWrapper {
    private SQLiteDatabase sqliteDatabase;

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
    public long insert(String table, String nullColumnHack, ContentValues values) {
        return this.sqliteDatabase.insert(table, nullColumnHack, values);
    }

    @Override
    public int delete(String table, String whereClause, String[] whereArgs) {
        return this.sqliteDatabase.delete(table, whereClause, whereArgs);
    }

    @Override
    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return this.sqliteDatabase.update(table, values, whereClause, whereArgs);
    }
}