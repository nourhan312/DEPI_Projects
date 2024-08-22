package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class singlePlayer  extends AppCompatActivity {
    private ImageView backButton;
    protected  void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ai_choose_symbol);
        backButton = findViewById(R.id.ai_back_icon);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, menu.class);
            startActivity(intent);

        });
    }
}
