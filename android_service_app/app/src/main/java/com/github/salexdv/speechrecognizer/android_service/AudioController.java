package com.github.salexdv.speechrecognizer.android_service;

import android.content.Context;
import android.media.AudioManager;

public class AudioController {

    private Context context;
    private AudioManager audioManager;
    private int streamVolume;

    public AudioController (Context applicationContext) {

        context = applicationContext;
        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        this.streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

    }

    public void mute() {

        audioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0);
        audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_MUTE, 0);
        audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_PLAY_SOUND);

    }

    public void unmute() {

        audioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_UNMUTE, 0);
        audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, 0);
        audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, this.streamVolume, AudioManager.FLAG_PLAY_SOUND);

    }

}
