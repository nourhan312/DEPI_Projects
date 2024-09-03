package com.example.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton startGameButton, logoutBtn;
    private SoundManager soundManager;
    private SharedPreferences preferences;
    private TextView welcomeMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        //shared preferences

        preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        String userName = preferences.getString("userName", "");

        welcomeMessageTextView = findViewById(R.id.WelcometextView);
        logoutBtn = findViewById(R.id.logout_button);
        startGameButton = findViewById(R.id.start_button);

        soundManager = new SoundManager(this);

        welcomeMessageTextView.setText(getString(R.string.welcome) + userName); // Display username

        logoutBtn.setOnClickListener(v -> {
            soundManager.playClickSound();


            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();


            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        });


        startGameButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(MainActivity.this, menu.class);
            startActivity(intent);
        });



//        // Apply saved sound preferences
//        if (SoundManager.isSoundEnabled()) {
//            soundManager.enableSound();
//        } else {
//            soundManager.disableSound();
//        }
//
//        if (SoundManager.isClickSoundEnabled()) {
//            soundManager.enableClickSound();
//        } else {
//            soundManager.disableClickSound();
//        }


    }
}
