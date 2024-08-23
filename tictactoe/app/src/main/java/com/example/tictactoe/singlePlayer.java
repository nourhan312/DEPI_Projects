package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class singlePlayer extends AppCompatActivity {
    private ImageView backButton;
    private AppCompatButton continueButton;
    private ImageView xSymbol, oSymbol, xRadio, oRadio;
    private boolean isXSelected = true; // Default selection is 'X'
    private  SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_choose_symbol);
        EdgeToEdge.enable(this);

        // Initialize Views
        soundManager = new SoundManager(this);
        backButton = findViewById(R.id.ai_back_icon);
        continueButton = findViewById(R.id.ai_pick_side_continue_btn);
        xSymbol = findViewById(R.id.xsymbol_img);
        oSymbol = findViewById(R.id.circle_img);
        xRadio = findViewById(R.id.x_radio);
        oRadio = findViewById(R.id.circle_radio);

        // Handle Symbol Selection
        xSymbol.setOnClickListener(v -> {

            soundManager.playClickSound();
            selectXSymbol();
        });
        oSymbol.setOnClickListener(v -> {
            soundManager.playClickSound();
            selectOSymbol();
        });

        // Continue Button
        continueButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            // Get Player Name from Intent
            String playerName = getIntent().getStringExtra("PLAYER_NAME");

            // Create Intent to Start game Activity
            Intent intent = new Intent(singlePlayer.this, game.class);
            intent.putExtra("PLAYER_NAME", playerName);
            intent.putExtra("PLAYER_SYMBOL", isXSelected ? "X" : "O");
            startActivity(intent);
        });

        // Back Button
        backButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(singlePlayer.this, menu.class);
            startActivity(intent);
        });
    }

    private void selectXSymbol() {
        isXSelected = true;
        xRadio.setImageResource(R.drawable.radio_button_checked);
        oRadio.setImageResource(R.drawable.radio_button_unchecked);
        xSymbol.setAlpha(1.0f);
        oSymbol.setAlpha(0.4f);
    }

    private void selectOSymbol() {
        isXSelected = false;
        xRadio.setImageResource(R.drawable.radio_button_unchecked);
        oRadio.setImageResource(R.drawable.radio_button_checked);
        xSymbol.setAlpha(0.4f);
        oSymbol.setAlpha(1.0f);
    }
}
