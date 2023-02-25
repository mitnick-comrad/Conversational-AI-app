package com.example.zane;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TTSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_t_s);

    }
}


/*package com.example.zane;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    protected static final  int RESULT_SPEECH = 1;
    EditText textView;
    TextToSpeech textToSpeech;
    TextToSpeech t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button= (Button) findViewById((R.id.button));
        textView =(EditText) findViewById(R.id.textView);
        textView.setText("");
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                textView.setText("");
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    //textView.setText("");

                    String tospeak= textView.getText().toString();
                    t1.speak(tospeak,TextToSpeech.QUEUE_FLUSH,null);


                }
                catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(), "Your device doesn't support speech", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }



            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        Python py= Python.getInstance();
        final PyObject obj= py.getModule("client1");


        switch (requestCode){
            case RESULT_SPEECH:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> text= data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //textView.setText(text.get(0));
                    PyObject on= obj.callAttr("chat", text.get(0));
                    textView.setText(on.toString());


                    textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status == TextToSpeech.SUCCESS ){
                                int lang= textToSpeech.setLanguage(Locale.US);
                                if (lang == TextToSpeech.LANG_MISSING_DATA|| lang == TextToSpeech.LANG_NOT_SUPPORTED){
                                    Toast.makeText(MainActivity.this, "Language is not support", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(MainActivity.this, "Language support", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
                    String dat= textView.getText().toString();
                    textToSpeech.setSpeechRate(0.75F);
                    textToSpeech.speak(dat,TextToSpeech.QUEUE_FLUSH, null);





                }
                break;
        }
    }

    @Override
    protected void onPause() {
        if ( t1 != null){
            //textToSpeech.stop();
            //textToSpeech.shutdown();
            t1.stop();
            t1.shutdown();

        }
        if ( textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }
}*/