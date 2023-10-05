package com.ar.bootcampar.model;

import android.content.ContentValues;

import java.util.HashMap;
import java.util.Map;

public class ContentValuesWrapper implements IContentValuesWrapper {
    private HashMap<String, Long> hashMapLong;
    private HashMap<String, Integer> hashMapInt;
    private HashMap<String, String> hashMapString;

    public ContentValuesWrapper() {
        hashMapLong = new HashMap<String, Long>();
        hashMapInt = new HashMap<String, Integer>();
        hashMapString = new HashMap<String, String>();
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
    public Object get(String key) {
        if (hashMapString.containsKey(key)) return hashMapString.get(key);
        if (hashMapLong.containsKey(key)) return hashMapLong.get(key);
        if (hashMapInt.containsKey(key)) return hashMapInt.get(key);

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

        return values;
    }
}
