package com.github.salexdv.speechrecognizer.android_service;

public final class Intents {

    private Intents() {
    }

    // Incoming intents
    public static final String START_RECOGNIZER = "com.github.salexdv.speechrecognizer.START";
    public static final String STOP_RECOGNIZER = "com.github.salexdv.speechrecognizer.STOP";
    public static final String STATUS_RECOGNIZER = "com.github.salexdv.speechrecognizer.STATUS";
    // Outgoing intents
    public static final String RECOGNIZER_ONREADY = "com.github.salexdv.speechrecognizer.ON_READY";
    public static final String RECOGNIZER_ONBEGIN = "com.github.salexdv.speechrecognizer.ON_BEGININIG";
    public static final String RECOGNIZER_ONEND = "com.github.salexdv.speechrecognizer.ON_END";
    public static final String RECOGNIZER_RESULT = "com.github.salexdv.speechrecognizer.RESULT";
    public static final String RECOGNIZER_PARTRESULT = "com.github.salexdv.speechrecognizer.PARTIAL_RESULT";
    public static final String RECOGNIZER_ERROR = "com.github.salexdv.speechrecognizer.ERROR";
    public static final String RECOGNIZER_NOTAVAILABLE = "com.github.salexdv.speechrecognizer.NOT_AVAILABLE";
    public static final String RECOGNIZER_STATUS = "com.github.salexdv.speechrecognizer.SERVICE_STATUS";

}
