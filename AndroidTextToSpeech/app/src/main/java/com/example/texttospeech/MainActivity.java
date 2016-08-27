package com.example.texttospeech;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextToSpeech mTextToSpeech;
    private Button mButtonTextToSpeech;
    private Button mButtonTtsSingleton;
    private EditText mEditTextSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextToSpeech = new TextToSpeech(this, this);

        mButtonTtsSingleton = (Button) findViewById(R.id.btnTtsSingleton);
        mButtonTextToSpeech = (Button) findViewById(R.id.btnTextToSpeech);
        mEditTextSpeech = (EditText) findViewById(R.id.editTextSpeech);

        mButtonTextToSpeech.setOnClickListener(this);
        mButtonTtsSingleton.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = mTextToSpeech.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, "This Language is not supported");
            } else {
                mButtonTextToSpeech.setEnabled(true);
//                speakOutText();
            }

        } else {
            Log.e(TAG, "Initialization Failed!");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTextToSpeech:
                speakOutText();
                break;
            case R.id.btnTtsSingleton:
                Intent i = new Intent(getBaseContext(), TtsSingletonActivity.class);
                startActivity(i);
                break;
        }
    }

    private void speakOutText() {
//        String text = "Hello World";
        String text = mEditTextSpeech.getText().toString();
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
