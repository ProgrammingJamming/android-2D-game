package com.example.myapplication;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.myapplication.R;

/**
 * Created by asdfqwer on 7/26/2017.
 */

public class SoundPlayer {

    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX = 50;
    private static SoundPool soundPool;
    private static int enemyExplosion;
    private static int baseExplosion;
    private static int doorOpen;

    public SoundPlayer(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }
        enemyExplosion = soundPool.load(context, R.raw.enemyexplosion, 1);
        baseExplosion = soundPool.load(context, R.raw.baseexplosion, 1);
        doorOpen = soundPool.load(context, R.raw.open, 1);
    }

    public void playEffect(int id) {
        switch(id) {
            case 1:
                soundPool.play(enemyExplosion, .6f, .6f, 1, 0, 1.0f);
                break;
            case 2:
                soundPool.play(doorOpen, 1.0f, 1.0f, 1, 0, 1.0f);
                break;
            case 3:
                soundPool.play(baseExplosion, 1.0f, 1.0f, 1, 0, 1.0f);
                break;
        }
    }

    public void pause() {
        soundPool.autoPause();
    }

    public void resume() {
        soundPool.autoResume();
    }
}
