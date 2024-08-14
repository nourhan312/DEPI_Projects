package com.example.learnenglish;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class family extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
   private CardView cardView,cardView2,cardView3,cardView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_family);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cardView = findViewById(R.id.cardMother);
             cardView2 = findViewById(R.id.cardFather);
         cardView3 = findViewById(R.id.cardGrandfather);
         cardView4 = findViewById(R.id.cardGrandmother);

         cardView.setOnClickListener(view -> {
             mediaPlayer = MediaPlayer.create(this, R.raw.mother);
             mediaPlayer.start();
         });
         cardView2.setOnClickListener(view -> {
             mediaPlayer = MediaPlayer.create(this, R.raw.father);
             mediaPlayer.start();
         });
         cardView3.setOnClickListener(view -> {
             mediaPlayer = MediaPlayer.create(this, R.raw.grand_father);
             mediaPlayer.start();
         });
         cardView4.setOnClickListener(view -> {
             mediaPlayer = MediaPlayer.create(this, R.raw.grand_mother);
             mediaPlayer.start();
         });

         }


    private void playSound(int soundResourceId) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, soundResourceId);
        mediaPlayer.start();
    }
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    }

