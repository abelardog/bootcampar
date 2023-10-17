package com.ar.bootcampar.model.utilities;

import android.content.ContentValues;

public class ContentValuesWrapper implements IContentValuesWrapper {
    private final ContentValues contentValues;

    public ContentValuesWrapper() {
        contentValues = new ContentValues();
    }

    @Override
    public void put(String key, String value) {
        contentValues.put(key, value);
    }

    @Override
    public void put(String key, Integer value) {
        contentValues.put(key, value);
    }

    @Override
    public void put(String key, Long value) {
        contentValues.put(key, value);
    }

    @Override
    public void put(String key, Boolean value) {
        contentValues.put(key, value);
    }

    @Override
    public Object get(String key) {
        return contentValues.get(key);
    }

    @Override
    public ContentValues generate() {
        return contentValues;
    }
}