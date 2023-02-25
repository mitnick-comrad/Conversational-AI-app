package com.example.zane;

//import static android.os.Build.VERSION_CODES.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/*
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
*/
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextToSpeech t1;
    EditText textView;
    protected static final int RESULT_SPEECH =1;
    private SpeechRecognizer recognizer;

    private boolean mIsBound = false;
    private TextSpeechServices mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        @Override


        public void onServiceConnected(ComponentName name, IBinder
                binder1) {
            mServ = ((TextSpeechServices.ServiceBinder)binder1).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView =(EditText) findViewById(R.id.textView);
        Button button= findViewById(R.id.button);
        textView.setText("Hey how are you");
        //doBindService();
        //Intent tss= new Intent(this, TextSpeechServices.class);
        //startService(tss);
        speakit();
        initializationResult();
        pye(textView.getText().toString());
        speak(textView.getText().toString());






/*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //speakit();
                //getspeak();
                //mServ.textToSpeech(textView.getText().toString());








            }
        });*/

    }
    private void initializationResult(){
        if (SpeechRecognizer.isRecognitionAvailable(this)){
            recognizer = SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {
                    ArrayList<String> result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    Toast.makeText(MainActivity.this, ""+result.get(0), Toast.LENGTH_SHORT).show();
                    textView.setText(result.get(0));
                    pye(result.get(0));
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }
    public void speakit(){
        t1= new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (t1.getEngines().size()==0) {
                    Toast.makeText(MainActivity.this, "Engine is nor available", Toast.LENGTH_SHORT).show();
                }else{
                    t1.setLanguage(Locale.US);
                    //speak("Hi I am Jarvis");
                }
            }
        });

        //getspeak();
    }
    public void speak(String msg){
        t1.speak(msg,TextToSpeech.QUEUE_FLUSH, null);
    }


    public void startRecording(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);

        recognizer.startListening(intent);

    }
    public void sendAndRecieve(String s) throws IOException {

        URL url = new URL("http://example.com");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
    }


    public void pye(String s){
        /*if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        Python py= Python.getInstance();
        final PyObject obj= py.getModule("client1");
        PyObject on = obj.callAttr("chat", s);
        String a= on.toString();
        speak(on.toString());
        textView.setText(a);*/
    }

    public void getspeak(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        try {
            startActivityForResult(intent, RESULT_SPEECH);
            //textView.setText("");





        }
        catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(), "Your device doesn't support speech", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
/*
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








                }
                break;
        }
    }
  */  /*private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        @Override


        public void onServiceConnected(ComponentName name, IBinder
                binder1) {
            mServ = ((MusicService.ServiceBinder)binder1).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };*/


    @Override
    protected void onPause() {
  //      doUnbindService();

        if (t1 != null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }



/*
    void doBindService(){
        bindService(new Intent(this,TextSpeechServices.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }
*/

}
