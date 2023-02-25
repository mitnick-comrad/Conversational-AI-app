package com.example.zane;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Locale;

public class TextSpeechServices extends Service {
    TextToSpeech t1;
    private final IBinder mBinder = (IBinder) new ServiceBinder();

    public void textToSpeech(String s){
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });

        t1.speak(s,TextToSpeech.QUEUE_FLUSH,null);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class ServiceBinder extends Binder {
        TextSpeechServices getService(){
            return TextSpeechServices.this;
        }
    }
}
