package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
        setContentView(R.layout.activity_single_player_name);

        soundManager = new SoundManager(this);
        backbtn = findViewById(R.id.choose_player_back_icon);
        Nextbtn = findViewById(R.id.ai_player_name_btn);
        playerNameEditText = findViewById(R.id.ai_player_name_edttxt);


        backbtn.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(choosePlayerName.this, menu.class);
            startActivity(intent);
        });


        Nextbtn.setOnClickListener(v -> {
            soundManager.playClickSound();

            String playerName = playerNameEditText.getText().toString().trim();


            if (TextUtils.isEmpty(playerName)) {
                playerNameEditText.setError(getString(R.string.player_name_cannot_be_empty));
                playerNameEditText.requestFocus();
                return;
            }


            Intent intent = new Intent(choosePlayerName.this, singlePlayer.class);
            intent.putExtra("PLAYER_NAME", playerName);
            startActivity(intent);
        });
    }
}
