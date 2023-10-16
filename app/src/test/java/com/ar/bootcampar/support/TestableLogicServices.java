package com.ar.bootcampar.support;

import com.ar.bootcampar.model.IDatabase;
import com.ar.bootcampar.model.LogicServices;

public class TestableLogicServices extends LogicServices {
    public TestableLogicServices() {
        super(null, null);
    }

    public TestableLogicServices(IDatabase database) {
        super(null, database);
    }

    @Override
    protected String getStringFromContext(int id) {
        return "";
    }
}
