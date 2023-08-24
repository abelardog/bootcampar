package com.ar.bootcampar.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CourseService extends Service {
    public CourseService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}