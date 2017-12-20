package com.example.matt2929.strokeappdec2017.Utilities;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by matt2929 on 12/19/17.
 */

public class SFXPlayer {
    MediaPlayer mediaPlayer;
    Context context;

    public SFXPlayer(Context context) {
        this.context = context;
    }

    public void playSFX(int sfx) {
        mediaPlayer = MediaPlayer.create(context, sfx);
        mediaPlayer.start();
    }
}
