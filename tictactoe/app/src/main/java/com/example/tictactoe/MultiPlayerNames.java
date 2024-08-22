package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.text.NoCopySpan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MultiPlayerNames extends AppCompatActivity {

    private AppCompatButton NextButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_get_players_names);
        NextButton = findViewById(R.id.player_one_btn);

        NextButton.setOnClickListener(v -> {
            Intent intent = new Intent(MultiPlayerNames.this, MultiPlayerGame.class);
            startActivity(intent);
        });

    }
}
