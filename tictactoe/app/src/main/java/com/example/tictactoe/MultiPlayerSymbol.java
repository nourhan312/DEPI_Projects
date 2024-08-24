package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MultiPlayerSymbol extends AppCompatActivity {
    private ImageView backButton;
    private AppCompatButton continueButton;
    private ImageView xSymbol, oSymbol, xRadio, oRadio;
    private TextView playerName ;
    private boolean isXSelected = true; // Default selection is 'X'
    private SoundManager soundManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mchoose_symbol);
        EdgeToEdge.enable(this);


        // Initialize Views
        soundManager = new SoundManager(this);
        backButton = findViewById(R.id.multi_back_icon);
        continueButton = findViewById(R.id.mulcontinue_btn);
        xSymbol = findViewById(R.id.xsymbol_img);
        oSymbol = findViewById(R.id.circle_img);
        xRadio = findViewById(R.id.x_radio);
        oRadio = findViewById(R.id.circle_radio);
        playerName= findViewById(R.id.pickside);

        // Get Player Name from Intent

        String player = getIntent().getStringExtra("PLAYERONE_NAME");
        // Set Player Name
        playerName.setText(player + R.string.pick_your_side);
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
            String playerOneName = getIntent().getStringExtra("PLAYERONE_NAME");
            String playerTwoName = getIntent().getStringExtra("PLAYERTWO_NAME");

            // Create Intent to Start game Activity
            Intent intent = new Intent(MultiPlayerSymbol.this, MultiPlayerGame.class);
            intent.putExtra("PLAYER1_NAME", playerOneName);
            intent.putExtra("PLAYER2_NAME", playerTwoName);
            intent.putExtra("PLAYER1_SYMBOL", isXSelected ? "X" : "O");
            startActivity(intent);
        });

        // Back Button
        backButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(MultiPlayerSymbol.this, menu.class);
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
