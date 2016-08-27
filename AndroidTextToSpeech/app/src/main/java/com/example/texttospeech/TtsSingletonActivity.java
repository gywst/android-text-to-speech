package com.example.texttospeech;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TtsSingletonActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = TtsSingletonActivity.class.getSimpleName();
    private TextToSpeech mTextToSpeech;
    private Button mButtonTextToSpeech;
    private EditText mEditTextSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts_singleton);

        mButtonTextToSpeech = (Button) findViewById(R.id.btnTTS);
        mEditTextSpeech = (EditText) findViewById(R.id.editTTS);
        mButtonTextToSpeech.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SpeechManager.getInstance().startTextToSpeech(App.getContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        SpeechManager.getInstance().stopTextToSpeech();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTTS:
                speakOutText();
                break;
        }
    }

    private void speakOutText() {
//        String speakIt = "Hello World";
        String speakIt = mEditTextSpeech.getText().toString();
        Toast.makeText(getApplicationContext(), speakIt, Toast.LENGTH_SHORT).show();

        boolean isTextToSpeechIsReady = SpeechManager.getInstance().isTextToSpeechIsReady();
        Log.d(TAG, "isTextToSpeechIsReady: " + isTextToSpeechIsReady);

        if (isTextToSpeechIsReady) {
            Log.d(TAG, "Speak it: " + speakIt);
            SpeechManager.getInstance().speakOutText(speakIt);
        }
    }
}
