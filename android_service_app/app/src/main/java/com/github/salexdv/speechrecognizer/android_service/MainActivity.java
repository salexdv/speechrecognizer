package com.github.salexdv.speechrecognizer.android_service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private BroadcastReceiver receiver;
    private ArrayList<String> testData;
    private ArrayAdapter<String> testAdapter;

    public MainActivity() {
    }

    public void updateEnabledStatus() {

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean serviceRunning = ServiceSingleton.getInstance().isRunning();

                Button button = (Button)findViewById(R.id.startService);
                button.setEnabled(!serviceRunning);

                button = (Button)findViewById(R.id.stopService);
                button.setEnabled(serviceRunning);

                button = (Button)findViewById(R.id.testService);
                button.setEnabled(serviceRunning);

                button = (Button)findViewById(R.id.requestStatus);
                button.setEnabled(serviceRunning);

            }
        }, 50);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.testService)).setText(R.string.start_test);

        testData = new ArrayList<>();
        ArrayAdapter<String> testAdapter = new ArrayAdapter<>(this, R.layout.textview_main, R.id.textView, testData);
        ListView testOutput = (ListView)findViewById(R.id.testOutput);
        testOutput.setAdapter(testAdapter);

    }

    @Override
    protected void onResume() {

        super.onResume();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
            }

        }

        updateEnabledStatus();

    }

    @Override
    protected void onPause() {

        super.onPause();

        if (receiver != null) {
            startStopTest();
        }

    }

    public void startService(View view) {

        Intent serviceIntent = new Intent(this, RecService.class);
        ContextCompat.startForegroundService(this, serviceIntent);

        updateEnabledStatus();

    }

    public void stopService(View view) {

        if (receiver != null)
            startStopTest();

        Intent serviceIntent = new Intent(this, RecService.class);
        stopService(serviceIntent);
        updateEnabledStatus();

    }

    private void sendRequest(String request) {

        Intent intent = new Intent();
        intent.setAction(request);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);

    }

    public void requestStatus(View view) {

        BroadcastReceiver statusReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(getApplicationContext(), intent.getStringExtra("message"), Toast.LENGTH_LONG).show();
                unregisterReceiver(this);
            }

        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intents.RECOGNIZER_STATUS);
        registerReceiver(statusReceiver, filter);

        sendRequest(Intents.STATUS_RECOGNIZER);

    }

    public void startStopTest() {

        if (receiver == null) {

            ListView testOutput = (ListView)findViewById(R.id.testOutput);
            ArrayAdapter<String> adapter = (ArrayAdapter<String>)testOutput.getAdapter();

            receiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    String action = intent.getAction();
                    String message = intent.getStringExtra("message");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    dateFormat.toString();
                    message = dateFormat.format(new Date())  + ": " + action + ((message.equals("")) ? "" : ": " + message);
                    testData.add(0, message);
                    adapter.notifyDataSetChanged();

                }

            };

            IntentFilter filter = new IntentFilter();
            filter.addAction(Intents.RECOGNIZER_ONREADY);
            filter.addAction(Intents.RECOGNIZER_ONBEGIN);
            filter.addAction(Intents.RECOGNIZER_ONEND);
            filter.addAction(Intents.RECOGNIZER_RESULT);
            filter.addAction(Intents.RECOGNIZER_PARTRESULT);
            filter.addAction(Intents.RECOGNIZER_ERROR);
            filter.addAction(Intents.RECOGNIZER_STATUS);
            filter.addAction(Intents.RECOGNIZER_NOTAVAILABLE);

            registerReceiver(receiver, filter);

            ((Button)findViewById(R.id.testService)).setText(R.string.stop_test);

            sendRequest(Intents.START_RECOGNIZER);

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        }

        else {

            unregisterReceiver(receiver);
            receiver = null;
            ((Button)findViewById(R.id.testService)).setText(R.string.start_test);

            sendRequest(Intents.STOP_RECOGNIZER);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        }

    }

    public void testService(View view) {

        startStopTest();

    }
}