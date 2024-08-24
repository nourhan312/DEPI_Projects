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

        preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        String userName = preferences.getString("userName", ""); // Retrieve username

        if (!isLoggedIn) {
            // User is not logged in, navigate to Login Activity
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
            return;
        }

        welcomeMessageTextView = findViewById(R.id.WelcometextView);
        logoutBtn = findViewById(R.id.logout_button);
        logoutBtn.setOnClickListener(v -> {
            soundManager.playClickSound();

            // Clear login state
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            // Navigate to Login Activity
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        });

        welcomeMessageTextView.setText(getString(R.string.welcome) + userName); // Display username

        soundManager = new SoundManager(this);

        // Apply saved sound preferences
        if (SoundManager.isSoundEnabled()) {
            soundManager.enableSound();
        } else {
            soundManager.disableSound();
        }

        if (SoundManager.isClickSoundEnabled()) {
            soundManager.enableClickSound();
        } else {
            soundManager.disableClickSound();
        }

        startGameButton = findViewById(R.id.start_button);
        startGameButton.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(MainActivity.this, menu.class);
            startActivity(intent);
        });
    }
}
