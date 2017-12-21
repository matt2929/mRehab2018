package com.example.matt2929.strokeappdec2017.Utilities;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechCompleteListener;
import com.example.matt2929.strokeappdec2017.ListenersAndTriggers.SpeechInitListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * Created by matt2929 on 12/19/17.
 */
//Take care of all text to speech
public class Text2Speech extends UtteranceProgressListener implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private boolean ready = false;
    private List<SpeechCompleteListener> speechCompleteListeners = new ArrayList<>();
    private List<SpeechInitListener> initListeners = new ArrayList<>();

    private Context context;

    public Text2Speech(Context context) {
        this.context = context;
        tts = new TextToSpeech(context, this);
        tts.setOnUtteranceProgressListener(this);
    }

    //add listener for end of speaking
    public void addSpeechCompleteListener(SpeechCompleteListener speechCompleteListener) {
        speechCompleteListeners.add(speechCompleteListener);
    }

    //add listener for when tts was init
    public void addInitListener(SpeechInitListener speechInit) {
        initListeners.add(speechInit);
    }

    //say something
    public void speak(String text, String utteranceID) {
        if (ready) {
            HashMap<String, String> hashTts = new HashMap<String, String>();
            hashTts.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceID);
            tts.speak(text, TextToSpeech.QUEUE_ADD, hashTts);
        }
    }

    public void silence(long duration) {
        tts.playSilentUtterance(duration, TextToSpeech.QUEUE_ADD, "Silent");
    }
    //clear all speaking
    public void stop() {
        tts.speak("", TextToSpeech.QUEUE_FLUSH, null, "Silent");
    }

    //shut down
    public void destroy() {
        stop();
        tts.shutdown();
    }

    //called when TTS starts, dont do anything with tts before
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Change this to match your
            // locale
            tts.setLanguage(Locale.US);
            tts.setOnUtteranceProgressListener(this);
            ready = true;
            for (SpeechInitListener inits : initListeners) {
                inits.onInit();
            }
        } else {
            ready = false;
        }
    }

    @Override
    public void onStart(String s) {

    }
    //tts finished saying a word - s is the reference id
    @Override
    public void onDone(String s) {
        for (SpeechCompleteListener speechCompleteListener : speechCompleteListeners) {
            speechCompleteListener.Spoke(s);
        }
    }

    @Override
    public void onError(String s) {

    }
}
