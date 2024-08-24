package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MultiPlayerNames extends AppCompatActivity {

    private AppCompatButton nextButton;
    private ImageView backBtn;
    private EditText PlayerOne, PlayerTwo;
    private SoundManager soundManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_players_names);
        EdgeToEdge.enable(this);

        soundManager = new SoundManager(this);
        PlayerOne = findViewById(R.id.player1_name_edttxt);
        PlayerTwo = findViewById(R.id.player2_name_edttxt);

        nextButton = findViewById(R.id.players_names_btn);
        backBtn = findViewById(R.id.choose_players_back_icon);

        nextButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            String player1 = PlayerOne.getText().toString().trim();
            String player2 = PlayerTwo.getText().toString().trim();

            if (TextUtils.isEmpty(player1)) {
                PlayerOne.setError(getString(R.string.player_one_name_cannot_be_empty)
                );
                PlayerOne.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(player2)) {
                PlayerTwo.setError(getString(R.string.player_two_name_cannot_be_empty));
                PlayerTwo.requestFocus();
                return;
            }

            Intent intent = new Intent(MultiPlayerNames.this, MultiPlayerSymbol.class);
            intent.putExtra("PLAYERONE_NAME", player1);
            intent.putExtra("PLAYERTWO_NAME", player2);
            startActivity(intent);
        });

        backBtn.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(MultiPlayerNames.this, menu.class);
            startActivity(intent);
        });
    }
}
