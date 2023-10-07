package com.ar.bootcampar.support;

import com.ar.bootcampar.model.ICursorWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CursorWrapperStub implements ICursorWrapper {
    public static class Builder {
        private List<String> columnas;
        private List<Object> valores;
        private int count;

        public Builder conColumnas(List<String> columnas) {
            this.columnas = columnas;
            return this;
        }

        public Builder conValores(List<Object> valores) {
            this.valores = valores;
            return this;
        }

        public Builder conCountRetornando(int count) {
            this.count = count;
            return this;
        }

        public CursorWrapperStub build() {
            return new CursorWrapperStub(columnas, valores, count);
        }
    }

    private final List<String> columnas;
    private final List<Object> valores;
    private final int count;
    private boolean closeCalled;

    private CursorWrapperStub(List<String> columnas, List<Object> valores, int count) {
        this.count = count;
        this.columnas = columnas;
        this.valores = valores;
    }

    @Override
    public void close() {
        closeCalled = true;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean moveToFirst() {
        return false;
    }

    @Override
    public int getColumnIndex(String columnName) {
        return columnas.indexOf(columnName);
    }

    @Override
    public long getLong(int columnIndex) {
        return (long)valores.get(columnIndex);
    }

    @Override
    public int getInt(int columnIndex) {
        return (int)valores.get(columnIndex);
    }

    @Override
    public String getString(int columnIndex) {
        return (String)valores.get(columnIndex);
    }

    public boolean getCloseCalled() {
        return closeCalled;
    }
}