package com.github.salexdv.speechrecognizer.addin;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

  @Test
  public void testSleep() {
    System.loadLibrary("com_github_salexdv_speechrecognizer_addin");
    MainApp mainApp = new MainApp(null, 0);
    mainApp.testSpeechRecognition();
  }
}
