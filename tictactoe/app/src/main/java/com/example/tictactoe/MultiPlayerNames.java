package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MultiPlayerNames extends AppCompatActivity {

    private AppCompatButton nextButton;
    private ImageView backBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_get_players_names);
        nextButton = findViewById(R.id.players_names_btn);
        backBtn = findViewById(R.id.choose_players_back_icon);
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(MultiPlayerNames.this, MultiPlayerGame.class);
            startActivity(intent);
        });
        backBtn.setOnClickListener(v->{
            Intent intent = new Intent(MultiPlayerNames.this , menu.class);
            startActivity(intent);
        });


    }
}
