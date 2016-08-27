package com.example.texttospeech;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Created by guywest on 23/08/2016.
 */
public class SpeechManager implements TextToSpeech.OnInitListener {

    private static final String TAG = SpeechManager.class.getSimpleName();
    private static SpeechManager instance;
    private TextToSpeech mTextToSpeech;
    private boolean mTextToSpeechIsReady = false;

    public SpeechManager() {
//        mTextToSpeech = new TextToSpeech(App.getContext(), this);
    }

    public static synchronized SpeechManager getInstance() {
        if (instance == null) {
            instance = new SpeechManager();
        }
        return instance;
    }


    public void speakOutText(String text) {
        if (mTextToSpeech != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    public void startTextToSpeech(Context context) {
        mTextToSpeech = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTextToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, "TTS - This Language is not supported");
                mTextToSpeechIsReady = false;
            } else {
                Log.d(TAG, " TextToSpeech Is Ready");
                mTextToSpeechIsReady = true;
            }
        } else {
            Log.e(TAG, "TTS: Initilization Failed!");
            mTextToSpeechIsReady = false;
        }
    }

    public void stopTextToSpeech() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
            mTextToSpeech = null;
        }
    }

    public boolean isTextToSpeechIsReady() {
        return mTextToSpeechIsReady;
    }
}