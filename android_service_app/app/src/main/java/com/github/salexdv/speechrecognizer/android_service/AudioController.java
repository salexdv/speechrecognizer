package com.github.salexdv.speechrecognizer.android_service;

import android.content.Context;
import android.media.AudioManager;

public class AudioController {

    private Context context;
    private AudioManager audioManager;

    public AudioController (Context applicationContext) {

        context = applicationContext;
        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

    }

    public void mute() {

        audioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0);
        audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_MUTE, 0);
        audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, 0);

    }

    public void unmute() {

        audioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_UNMUTE, 0);
        audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, 0);
        audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0);

    }

}
