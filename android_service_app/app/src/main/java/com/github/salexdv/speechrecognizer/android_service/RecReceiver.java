package com.github.salexdv.speechrecognizer.android_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class RecReceiver extends BroadcastReceiver {

    private final String recognizerTAG = "boot_receiver";

    public RecReceiver() {

    }

    public void startService(Context context) {

        Intent serviceIntent = new Intent(context, RecService.class);
        ContextCompat.startForegroundService(context, serviceIntent);

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            startService(context);
            Log.i(recognizerTAG, context.getString(R.string.on_start_service));
        }
        catch (Exception e) {
            Log.e(recognizerTAG, e.toString());
        }

    }
}