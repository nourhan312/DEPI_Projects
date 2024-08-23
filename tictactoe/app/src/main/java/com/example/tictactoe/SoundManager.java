package com.example.tictactoe;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundManager {

    private static MediaPlayer backgroundMusicPlayer;
    private static SoundPool soundPool;
    private static int clickSoundId;

    private static boolean isSoundEnabled = false;  // default to true
    private static boolean isClickSoundEnabled = true;  // default to true
    private static boolean isMusicInitialized = false;

    public SoundManager(Context context) {
        if (soundPool == null) {
            initClickSound(context);
        }

        if (!isMusicInitialized) {
            initBackgroundMusic(context);
        }
    }

    private void initBackgroundMusic(Context context) {
        backgroundMusicPlayer = MediaPlayer.create(context, R.raw.background);
        backgroundMusicPlayer.setLooping(true);
        isMusicInitialized = true;
    }

    private void initClickSound(Context context) {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        clickSoundId = soundPool.load(context, R.raw.click, 1);
    }

    public void enableSound() {
        isSoundEnabled = true;
        if (backgroundMusicPlayer != null && !backgroundMusicPlayer.isPlaying()) {
            backgroundMusicPlayer.start();
        }
    }

    public void disableSound() {
        isSoundEnabled = false;
        if (backgroundMusicPlayer != null && backgroundMusicPlayer.isPlaying()) {
            backgroundMusicPlayer.pause();
            backgroundMusicPlayer.seekTo(0);  // Restart music from the beginning when re-enabled
        }
    }

    public void enableClickSound() {
        isClickSoundEnabled = true;
    }

    public void disableClickSound() {
        isClickSoundEnabled = false;
    }

    public void playClickSound() {
        if (isClickSoundEnabled && soundPool != null) {
            soundPool.play(clickSoundId, 1, 1, 0, 0, 1);
        }
    }

    public static boolean isSoundEnabled() {
        return isSoundEnabled;
    }

    public static boolean isClickSoundEnabled() {
        return isClickSoundEnabled;
    }

    public void release() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.release();
            backgroundMusicPlayer = null;
        }

        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
