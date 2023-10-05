package com.ar.bootcampar.model;

public interface ICursorWrapper {
    void close();

    int getCount();

    boolean moveToFirst();

    int getColumnIndex(String columnName);

    long getLong(int columnIndex);

    int getInt(int columnIndex);

    String getString(int columnIndex);
}