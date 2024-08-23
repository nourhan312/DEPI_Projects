package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton startGameButton;
    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge layout
        EdgeToEdge.enable(this);

        // Set the content view to the main activity layout
        setContentView(R.layout.activity_main);

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
