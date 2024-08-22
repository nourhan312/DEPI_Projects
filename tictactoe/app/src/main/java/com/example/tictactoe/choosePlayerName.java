package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class choosePlayerName extends AppCompatActivity {
    private ImageView backbtn;
    private AppCompatButton Nextbtn;
    private EditText playerNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aiget_player_name);

        // initialize Views
        backbtn = findViewById(R.id.choose_player_back_icon);
        Nextbtn = findViewById(R.id.ai_player_name_btn);
        playerNameEditText = findViewById(R.id.ai_player_name_edttxt);

        // Back Button
        backbtn.setOnClickListener(v -> {
            Intent intent = new Intent(choosePlayerName.this, menu.class);
            startActivity(intent);
        });

        // Next Button
        Nextbtn.setOnClickListener(v -> {
            // Get Player Name
            String playerName = playerNameEditText.getText().toString().trim();

            // Intent to Start singlePlayer Activity
            Intent intent = new Intent(choosePlayerName.this, singlePlayer.class);
            intent.putExtra("PLAYER_NAME", playerName);
            startActivity(intent);
        });
    }
}
