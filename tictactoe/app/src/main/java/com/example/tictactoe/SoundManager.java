package com.example.tictactoe;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundManager {

    private static MediaPlayer backgroundMusicPlayer;
    private static SoundPool soundPool;
    private static int clickSoundId;

    private static boolean isSoundEnabled = true;  // default to true
    private static boolean isClickSoundEnabled = true;  // default to true

    // Initialize the SoundManager resources
    public SoundManager(Context context) {
        initBackgroundMusic(context);
        initClickSound(context);
    }

    // Initialize the MediaPlayer for background music
    private void initBackgroundMusic(Context context) {
        if (backgroundMusicPlayer == null) {
            backgroundMusicPlayer = MediaPlayer.create(context, R.raw.background);
            backgroundMusicPlayer.setLooping(true); // Loop the music
        }
    }

    // Initialize the SoundPool for click sounds
    private void initClickSound(Context context) {
        if (soundPool == null) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();

            clickSoundId = soundPool.load(context, R.raw.click, 1); // Load the click sound
        }
    }

    // Enable background music
    public void enableSound() {
        isSoundEnabled = true;
        if (backgroundMusicPlayer != null && !backgroundMusicPlayer.isPlaying()) {
            backgroundMusicPlayer.start();
        }
    }

    // Disable background music
    public void disableSound() {
        isSoundEnabled = false;
        if (backgroundMusicPlayer != null && backgroundMusicPlayer.isPlaying()) {
            backgroundMusicPlayer.pause();
        }
    }

    // Enable click sound effects
    public void enableClickSound() {
        isClickSoundEnabled = true;
    }

    // Disable click sound effects
    public void disableClickSound() {
        isClickSoundEnabled = false;
    }

    // Play click sound effect
    public void playClickSound() {
        if (isClickSoundEnabled && soundPool != null) {
            soundPool.play(clickSoundId, 1, 1, 0, 0, 1);
        }
    }

    // Check if sound is enabled
    public static boolean isSoundEnabled() {
        return isSoundEnabled;
    }

    // Check if click sound is enabled
    public static boolean isClickSoundEnabled() {
        return isClickSoundEnabled;
    }

    // Release resources (if needed)
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
