package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class menu extends AppCompatActivity {
    private ImageView backbtn;
    private CardView singleplayercard, multiplayerCard , settingsCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);


        // Initialize SoundManager
        SoundManager soundManager = new SoundManager(this);


       backbtn = findViewById(R.id.menu_back_icon);
        singleplayercard = findViewById(R.id.singlePlayerCard);
        multiplayerCard = findViewById(R.id.multiplayerCard);
        settingsCard = findViewById(R.id.settingCard);


        backbtn.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(menu.this, MainActivity.class);
            startActivity(intent);
        });
        singleplayercard.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(menu.this, choosePlayerName.class);
            startActivity(intent);
        });
        settingsCard.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(menu.this, setting.class);
            startActivity(intent);
        });
        multiplayerCard.setOnClickListener(v -> {
            soundManager.playClickSound();

            Intent intent = new Intent(menu.this, MultiPlayerNames.class);
            startActivity(intent);
        });



    }
}