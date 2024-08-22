package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class choosePlayerName extends AppCompatActivity {
    private ImageView backbtn;
    private AppCompatButton Nextbtn;
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aiget_player_name);
        EdgeToEdge.enable(this);

        backbtn = findViewById(R.id.choose_player_back_icon);
        Nextbtn = findViewById(R.id.ai_player_name_btn);
        backbtn.setOnClickListener(v -> {
            Intent intent = new Intent(choosePlayerName.this, menu.class);
            startActivity(intent);
        });
        Nextbtn.setOnClickListener(v -> {
            Intent intent = new Intent(choosePlayerName.this, singlePlayer.class);
            startActivity(intent);
        });
    }
}
