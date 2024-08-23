package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class choosePlayerName extends AppCompatActivity {
    private ImageView backbtn;
    private AppCompatButton Nextbtn;
    private EditText playerNameEditText;
    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aiget_player_name);

        // initialize Views
        soundManager = new SoundManager(this);
        backbtn = findViewById(R.id.choose_player_back_icon);
        Nextbtn = findViewById(R.id.ai_player_name_btn);
        playerNameEditText = findViewById(R.id.ai_player_name_edttxt);

        // Back Button
        backbtn.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(choosePlayerName.this, menu.class);
            startActivity(intent);
        });

        // Next Button
        Nextbtn.setOnClickListener(v -> {
            soundManager.playClickSound();
            // Get Player Name
            String playerName = playerNameEditText.getText().toString().trim();

            // Intent to Start singlePlayer Activity
            Intent intent = new Intent(choosePlayerName.this, singlePlayer.class);
            intent.putExtra("PLAYER_NAME", playerName);
            startActivity(intent);
        });
    }
}
