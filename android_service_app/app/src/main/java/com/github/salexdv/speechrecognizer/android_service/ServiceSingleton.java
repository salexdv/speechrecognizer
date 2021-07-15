package com.github.salexdv.speechrecognizer.android_service;

public class ServiceSingleton {

    private boolean running;
    private static ServiceSingleton instance;

    private ServiceSingleton() {
        running = false;
    }

    public static ServiceSingleton getInstance() {

        if (instance == null) {
            instance = new ServiceSingleton();
        }

        return instance;

    }

    public void Start() {

        running = true;

    }

    public void Stop() {

        running = false;

    }

    public boolean isRunning() {

        return running;

    }
}
