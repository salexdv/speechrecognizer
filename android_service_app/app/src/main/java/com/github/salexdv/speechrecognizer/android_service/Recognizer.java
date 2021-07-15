package com.github.salexdv.speechrecognizer.android_service;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.content.Context;
import java.util.ArrayList;
import java.util.Locale;

public class Recognizer implements RecognitionListener {

    private String recognizerTAG;
    private SpeechRecognizer recognizer;
    private boolean recStarted;
    private Intent speechRecognizerIntent;
    private Context context;
    private AudioController audioController;

    public Recognizer(Context applicationContext, String tag) {

        this.context = applicationContext;
        this.recognizerTAG = tag;

        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.EXTRA_LANGUAGE_MODEL);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        audioController = new AudioController(context);

    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        Log.d(recognizerTAG, "onReadyForSpeech");
        sendResponse(Intents.RECOGNIZER_ONREADY, "");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d(recognizerTAG, "onBeginningOfSpeech");
        sendResponse(Intents.RECOGNIZER_ONBEGIN, "");
    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
        Log.d(recognizerTAG, "onEndOfSpeech");
        sendResponse(Intents.RECOGNIZER_ONEND, "");
    }

    @Override
    public void onError(int i) {
        if (recStarted) {
            String error = "onError (" + String.valueOf(i) + ")";
            Log.d(recognizerTAG, error);
            recognizer.destroy();
            sendResponse(Intents.RECOGNIZER_ERROR, error);
            start();
        }
    }

    @Override
    public void onResults(Bundle bundle) {
        ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String recognitionResult = data.get(0);
        Log.d(recognizerTAG, "onResults (" + recognitionResult + ")");
        sendResponse(Intents.RECOGNIZER_RESULT, recognitionResult);
        recognizer.destroy();
        start();
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String recognitionResult = data.get(0);
        Log.d(recognizerTAG, "onPartialResults (" + recognitionResult + ")");
        sendResponse(Intents.RECOGNIZER_PARTRESULT, recognitionResult);
    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        Log.d(recognizerTAG, "onEvent (" + String.valueOf(i) + ")");
    }

    private void sendResponse(String action, String response) {

        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra("message", response);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.sendBroadcast(intent);

    }

    public void start() {

        audioController.mute();
        recognizer = SpeechRecognizer.createSpeechRecognizer(context);
        recognizer.setRecognitionListener(this);
        recognizer.startListening(speechRecognizerIntent);
        Log.d(recognizerTAG, context.getString(R.string.on_start_recognizer));
        recStarted = true;

    }

    public void stop() {

        if (recStarted) {
            audioController.unmute();
            recognizer.cancel();
            recognizer.stopListening();
            recognizer.destroy();
            Log.d(recognizerTAG, context.getString(R.string.on_stop_recognizer));
            recStarted = false;
        }

    }

    public void destroy() {

        recognizer.destroy();

    }
}