package com.ar.bootcampar.model;

import android.database.Cursor;

public class CursorHelper {
    private final Cursor cursor;

    public CursorHelper(Cursor cursor) {
        this.cursor = cursor;
    }

    private int getIndexOrThrow(String field) {
        int index = this.cursor.getColumnIndex(field);
        if (index < 0) {
            throw new RuntimeException(String.format("Cursor no tenia columna %s", field));
        }

        return index;
    }

    public long getLongFrom(String field) {
        return cursor.getLong(getIndexOrThrow(field));
    }

    public int getIntFrom(String field) {
        return cursor.getInt(getIndexOrThrow(field));
    }

    public String getStringFrom(String field) {
        return cursor.getString(getIndexOrThrow(field));
    }
}