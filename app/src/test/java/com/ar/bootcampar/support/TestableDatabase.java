package com.ar.bootcampar.support;

import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.utilities.IContentValuesWrapper;
import com.ar.bootcampar.model.utilities.ISQLiteDatabaseWrapper;

public class TestableDatabase extends Database {
    private ISQLiteDatabaseWrapper databaseSpy;
    private IContentValuesWrapper valuesSpy;

    public TestableDatabase(ISQLiteDatabaseWrapper databaseSpy) {
        super(null, "bootcampar-test.db", null, 0);
        this.databaseSpy = databaseSpy;
        this.valuesSpy = new ContentValuesSpy();
    }

    @Override
    protected ISQLiteDatabaseWrapper getInternalWritableDatabase() {
        return databaseSpy;
    }

    @Override
    protected ISQLiteDatabaseWrapper getInternalReadableDatabase() {
        return databaseSpy;
    }

    @Override
    protected IContentValuesWrapper createContentValues() {
        return valuesSpy;
    }
}