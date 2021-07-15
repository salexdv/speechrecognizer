package com.github.salexdv.speechrecognizer.addin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainApp mainApp = new MainApp(this, 0);
        mainApp.show();
        mainApp.startSpeechRecognition();
    }

    public void loadLibOnClick(View view) {
        // Used to load the 'native-lib' library on application startup.
        System.loadLibrary("com_github_salexdv_speechrecognizer_addin");
        MainApp mainApp = new MainApp(this, 0);
        mainApp.startSpeechRecognition();
    }
}
