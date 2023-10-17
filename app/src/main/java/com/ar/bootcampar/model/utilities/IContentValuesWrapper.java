package com.ar.bootcampar.model.utilities;

import android.content.ContentValues;

public interface IContentValuesWrapper {
    void put(String key, String value);
    void put(String key, Integer value);
    void put(String key, Long value);
    void put(String key, Boolean value);
    Object get(String key);
    ContentValues generate();
}