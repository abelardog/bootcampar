package com.ar.bootcampar.support;

import com.ar.bootcampar.model.LogicServices;

public class TestableLogicServices extends LogicServices {
    public TestableLogicServices() {
        super(null, null);
    }

    @Override
    protected String getStringFromContext(int id) {
        return "";
    }
}
