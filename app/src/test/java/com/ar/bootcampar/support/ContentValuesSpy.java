package com.ar.bootcampar.support;

import android.content.ContentValues;

import com.ar.bootcampar.model.IContentValuesWrapper;

import java.util.HashMap;
import java.util.Map;

public class ContentValuesSpy implements IContentValuesWrapper {
    private HashMap<String, Long> hashMapLong;
    private HashMap<String, Integer> hashMapInt;
    private HashMap<String, String> hashMapString;
    private HashMap<String, Boolean> hashMapBoolean;

    public ContentValuesSpy() {
        hashMapLong = new HashMap<>();
        hashMapInt = new HashMap<>();
        hashMapString = new HashMap<>();
        hashMapBoolean = new HashMap<>();
    }

    @Override
    public void put(String key, String value) {
        hashMapString.put(key, value);
    }

    @Override
    public void put(String key, Integer value) {
        hashMapInt.put(key, value);
    }

    @Override
    public void put(String key, Long value) {
        hashMapLong.put(key, value);
    }

    @Override
    public void put(String key, Boolean value) {
        hashMapBoolean.put(key, value);
    }

    @Override
    public Object get(String key) {
        if (hashMapString.containsKey(key)) return hashMapString.get(key);
        if (hashMapLong.containsKey(key)) return hashMapLong.get(key);
        if (hashMapInt.containsKey(key)) return hashMapInt.get(key);
        if (hashMapBoolean.containsKey(key)) return hashMapBoolean.get(key);

        throw new RuntimeException("No existe el item " + key);
    }

    @Override
    public ContentValues generate() {
        ContentValues values = new ContentValues();
        for (Map.Entry<String, String> item : hashMapString.entrySet()) {
            values.put(item.getKey(), item.getValue());
        }

        for (Map.Entry<String, Integer> item : hashMapInt.entrySet()) {
            values.put(item.getKey(), item.getValue());
        }

        for (Map.Entry<String, Long> item : hashMapLong.entrySet()) {
            values.put(item.getKey(), item.getValue());
        }

        for (Map.Entry<String, Boolean> item : hashMapBoolean.entrySet()) {
            values.put(item.getKey(), item.getValue());
        }

        return values;
    }
}