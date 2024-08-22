package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class setting extends AppCompatActivity {
    private ImageView backbtn;
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        EdgeToEdge.enable(this);

        backbtn = findViewById(R.id.settings_back_icon);
        backbtn.setOnClickListener(v -> {
            Intent intent = new Intent(setting.this, menu.class);
            startActivity(intent);

        });
    }
}
