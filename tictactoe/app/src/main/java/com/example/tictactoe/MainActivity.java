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

    private AppCompatButton startGameButton , logoutBtn;
    private SoundManager soundManager;
     private SharedPreferences preferences;
     private TextView welcomeMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Load saved language preference

        // Enable edge-to-edge layout
        EdgeToEdge.enable(this);

        // Set the content view to the main activity layout
        setContentView(R.layout.activity_main);


         preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        String userName = preferences.getString("userName", "");


        if (!isLoggedIn) {
            // User is not logged in, navigate to Login Activity
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
            return;
        }
        welcomeMessageTextView = findViewById(R.id.WelcometextView);
        logoutBtn = findViewById(R.id.logout_button);
        logoutBtn.setOnClickListener( v->{
               soundManager.playClickSound();

                // Clear login state
                 preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();

                // Navigate to Login Activity
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();

        });


        welcomeMessageTextView.setText("Welcome , " + userName);

        // Initialize the SoundManager instance
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

        // Find the start game button by its ID
        startGameButton = findViewById(R.id.start_button);

        // Set an OnClickListener on the start game button
        startGameButton.setOnClickListener(v -> {
            // Play the click sound using SoundManager
            soundManager.playClickSound();

            // Create an intent to navigate to the menu activity
            Intent intent = new Intent(MainActivity.this, menu.class);
            startActivity(intent);
        });
    }



}
