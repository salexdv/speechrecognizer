package com.github.salexdv.speechrecognizer.android_service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.speech.SpeechRecognizer;
import android.util.Log;
import androidx.core.app.NotificationCompat;


public class RecService extends Service {

    public static final String CHANNEL_ID = "RecChannel";
    private final String recognizerTAG = "recognizer";
    private final String serviceTAG = "service";
    private BroadcastReceiver receiver;
    private Recognizer recognizer;

    public RecService() {
    }

    private void createNotificationChannel() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

    }

    private void sendResponce(String action, String responce) {

        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra("message", responce);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotificationChannel();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(getString(R.string.service_title))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setAutoCancel(false)
                .build();

        try {
            startForeground(1, notification);
            ServiceSingleton.getInstance().Start();
        }
        catch (Exception e) {
            String errorDescription = e.getMessage();
            Log.e(serviceTAG, errorDescription);
            sendResponce(Intents.RECOGNIZER_ERROR, errorDescription);
            ServiceSingleton.getInstance().Stop();
        }

        recognizer = new Recognizer(getApplicationContext(), recognizerTAG);

        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                switch (intent.getAction())
                {
                    case Intents.START_RECOGNIZER:
                        try {
                            if (SpeechRecognizer.isRecognitionAvailable(context)) {
                                recognizer.start();
                            }
                            else {
                                String errorDescription = getString(R.string.rec_not_available);
                                Log.d(recognizerTAG, errorDescription);
                                sendResponce(Intents.RECOGNIZER_NOTAVAILABLE, errorDescription);
                            }
                        }
                        catch (Exception e) {
                            String errorDescription = e.getMessage();
                            Log.e(recognizerTAG, errorDescription);
                            sendResponce(Intents.RECOGNIZER_ERROR, errorDescription);
                        }
                        break;
                    case Intents.STOP_RECOGNIZER:
                        recognizer.stop();
                        break;
                    case Intents.STATUS_RECOGNIZER:
                        String status = getString(R.string.server_status_ok);
                        Log.d(recognizerTAG, status);
                        sendResponce(Intents.RECOGNIZER_STATUS, status);
                        break;
                    default:
                        break;
                }

            }

        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intents.START_RECOGNIZER);
        filter.addAction(Intents.STOP_RECOGNIZER);
        filter.addAction(Intents.STATUS_RECOGNIZER);
        registerReceiver(receiver, filter);

        return START_NOT_STICKY;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ServiceSingleton.getInstance().Stop();
        recognizer.stop();
        recognizer = null;
        unregisterReceiver(receiver);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new Binder();

    }
}