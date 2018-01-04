package com.example.matt2929.strokeappdec2017.Utilities;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by matt2929 on 12/19/17.
 */

public class SFXPlayer {
	MediaPlayer mediaPlayer = new MediaPlayer();
	Context context;
    boolean playing = false;
    public SFXPlayer(Context context) {
        this.context = context;
    }

    public void loadSFX(int sfx) {
        mediaPlayer = MediaPlayer.create(context, sfx);
    }

    public void playSFX() {
        mediaPlayer.start();
        playing = true;
    }

	public void killAll() {
		playing = false;
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}
		if (mediaPlayer.isLooping()) {
			mediaPlayer.setLooping(false);
		}
	}

	public boolean isPlaying() {
        return playing;
    }

    public void loopSFX() {
        mediaPlayer.setLooping(true);
    }

    public void noLoopSFX() {
        mediaPlayer.setLooping(false);
    }

    public void pauseSFX() {
        mediaPlayer.pause();
        playing = false;
    }
}
