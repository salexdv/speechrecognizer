package com.github.salexdv.speechrecognizer.android_service;

import android.app.Application;

public class RecApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        ServiceSingleton.getInstance();

    }
}
