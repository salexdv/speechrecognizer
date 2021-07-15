package com.github.salexdv.speechrecognizer.addin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;


public class MainApp implements Runnable {

  // in C/C++ code the function has name Java_com_github_salexdv_speechrecognizer_addin_OnRecognitionResult
  static native void OnRecognitionResult(long pObject, String data);
  // in C/C++ code the function has name Java_com_github_salexdv_speechrecognizer_addin_OnRecognitionResult
  static native void OnRecognitionPartialResult(long pObject, String data);
  // in C/C++ code the function has name Java_com_github_salexdv_speechrecognizer_addin_OnRecognitionReady
  static native void OnRecognitionReady(long pObject, String data);
  // in C/C++ code the function has name Java_com_github_salexdv_speechrecognizer_addin_RecServiceStatus
  static native void RecServiceStatus(long pObject, String data);
  // in C/C++ code the function has name Java_com_github_salexdv_speechrecognizer_addin_RecServiceStatus
  static native void onBeginningOfSpeech(long pObject, String data);

  private long m_V8Object; // 1C application context
  private Activity m_Activity; // custom activity of 1C:Enterprise
  private BroadcastReceiver m_Receiver;

  public MainApp(Activity activity, long v8Object)
  {
    m_Activity = activity;
    m_V8Object = v8Object;

  }

  public void run()
  {
    System.loadLibrary("com_github_salexdv_speechrecognizer_addin");
  }

  public void show()
  {
    m_Activity.runOnUiThread(this);
  }

  public void recognizerRequest(String action) {

    Intent intent = new Intent();
    intent.setAction(action);
    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
    m_Activity.sendBroadcast(intent);

  }

  public void playSoundAlert() {

    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    MediaPlayer mp = MediaPlayer.create(m_Activity.getApplicationContext(), notification);
    mp.start();

  }

  public void requestServiceStatus() {

    BroadcastReceiver statusReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        RecServiceStatus(m_V8Object, intent.getStringExtra("message"));
      }
    };

    IntentFilter filter = new IntentFilter(Intents.RECOGNIZER_STATUS);
    m_Activity.registerReceiver(statusReceiver, filter);
    recognizerRequest(Intents.STATUS_RECOGNIZER);

    final Handler handler = new Handler(Looper.getMainLooper());
    handler.postDelayed(() -> m_Activity.unregisterReceiver(statusReceiver), 1000);

  }

  public void startSpeechRecognition()
  {

    if (m_Receiver == null)
    {
      m_Receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

          switch (intent.getAction())
          {
            case Intents.RECOGNIZER_RESULT:
              OnRecognitionResult(m_V8Object, intent.getStringExtra("message"));
              break;
            case Intents.RECOGNIZER_PARTRESULT:
              OnRecognitionPartialResult(m_V8Object, intent.getStringExtra("message"));
              break;
            case Intents.RECOGNIZER_ONREADY:
              OnRecognitionReady(m_V8Object, intent.getStringExtra("message"));
              break;
            case Intents.RECOGNIZER_ONBEGIN:
              onBeginningOfSpeech(m_V8Object, "");
              break;
            case Intent.ACTION_SCREEN_ON:
              recognizerRequest(Intents.START_RECOGNIZER);
              break;
            case Intent.ACTION_SCREEN_OFF:
              recognizerRequest(Intents.STOP_RECOGNIZER);
              break;
            default:
              break;
          }

        }
      };

      IntentFilter filter = new IntentFilter();
      filter.addAction(Intent.ACTION_SCREEN_ON);
      filter.addAction(Intent.ACTION_SCREEN_OFF);
      filter.addAction(Intents.RECOGNIZER_ONREADY);
      filter.addAction(Intents.RECOGNIZER_ONBEGIN);
      filter.addAction(Intents.RECOGNIZER_ONEND);
      filter.addAction(Intents.RECOGNIZER_RESULT);
      filter.addAction(Intents.RECOGNIZER_PARTRESULT);
      filter.addAction(Intents.RECOGNIZER_ERROR);

      m_Activity.registerReceiver(m_Receiver, filter);

      recognizerRequest(Intents.START_RECOGNIZER);

    }

  }

  public void stopSpeechRecognition()
  {

    if (m_Receiver != null)
    {
      m_Activity.unregisterReceiver(m_Receiver);
      m_Receiver = null;
      recognizerRequest(Intents.STOP_RECOGNIZER);
    }

  }

  public native void testSpeechRecognition();

}
