package com.ar.bootcampar;

import com.ar.bootcampar.model.ContentValuesWrapper;
import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.IContentValuesWrapper;
import com.ar.bootcampar.model.ISQLiteDatabaseWrapper;

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
    protected IContentValuesWrapper createContentValues() {
        return valuesSpy;
    }
}