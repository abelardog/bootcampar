package com.ar.bootcampar.model;

import android.content.ContentValues;
import android.database.Cursor;

public interface ISQLiteDatabaseWrapper {
    void close();

    ICursorWrapper query(String table, String[] columns, String selection,
                 String[] selectionArgs, String groupBy, String having,
                 String orderBy);

    void endTransaction();

    void setTransactionSuccessful();

    void beginTransaction();

    long insert(String table, String nullColumnHack, IContentValuesWrapper values);

    int delete(String table, String whereClause, String[] whereArgs);

    int update(String table, IContentValuesWrapper values, String whereClause, String[] whereArgs);

    void execSQL(String sql);
}
