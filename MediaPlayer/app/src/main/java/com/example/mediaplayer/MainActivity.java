package com.example.mediaplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Handler h = new Handler();
    private Button play;
    private Button pause;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.playButton);
        pause = findViewById(R.id.pauseButton);

        seekBar = findViewById(R.id.seekBar);

        mediaPlayer = MediaPlayer.create(this, R.raw.song);

        seekBar.setMax(mediaPlayer.getDuration());



        play.setOnClickListener(v -> {
            mediaPlayer.start();
            updateSeekBar();
        });

        pause.setOnClickListener(v -> mediaPlayer.pause());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
            });

        mediaPlayer.setOnCompletionListener(mediaPlayer
                -> h.removeCallbacks(updateRunnable));


        }

    private void updateSeekBar() {

        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        h.postDelayed(updateRunnable, 1000);
    }
    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        h.removeCallbacks(updateRunnable);
    }


}