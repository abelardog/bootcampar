package com.ar.bootcampar.model;

import android.database.Cursor;

public class CursorWrapper implements ICursorWrapper {
    private Cursor cursor;

    public CursorWrapper(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public void close() {
        this.cursor.close();
    }

    @Override
    public int getCount() {
        return this.cursor.getCount();
    }

    @Override
    public boolean moveToFirst() {
        return this.cursor.moveToFirst();
    }

    @Override
    public int getColumnIndex(String columnName) {
        return this.cursor.getColumnIndex(columnName);
    }

    @Override
    public long getLong(int columnIndex) {
        return this.cursor.getLong(columnIndex);
    }

    @Override
    public int getInt(int columnIndex) {
        return this.cursor.getInt(columnIndex);
    }

    @Override
    public String getString(int columnIndex) {
        return this.cursor.getString(columnIndex);
    }

    @Override
    public boolean moveToNext() {
        return this.cursor.moveToNext();
    }

    @Override
    public boolean isAfterLast() {
        return this.cursor.isAfterLast();
    }
}