package com.example.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class setting extends AppCompatActivity {

    private ImageView backbtn;
    private Switch soundSwitch;
    private Switch clickedSwitch;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "SettingsPrefs";
    private static final String SOUND_PREF = "soundEnabled";
    private static final String CLICK_PREF = "clickEnabled";

    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        soundManager = new SoundManager(this);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        backbtn = findViewById(R.id.settings_back_icon);
        soundSwitch = findViewById(R.id.music_switch);
        clickedSwitch = findViewById(R.id.click_switch);

        boolean isSoundEnabled = sharedPreferences.getBoolean(SOUND_PREF, true);
        boolean isClickEnabled = sharedPreferences.getBoolean(CLICK_PREF, true);

        soundSwitch.setChecked(isSoundEnabled);
        clickedSwitch.setChecked(isClickEnabled);

        if (isSoundEnabled) {
            soundManager.enableSound();
        } else {
            soundManager.disableSound();
        }

        if (isClickEnabled) {
            soundManager.enableClickSound();
        } else {
            soundManager.disableClickSound();
        }

        backbtn.setOnClickListener(v -> {
            soundManager.playClickSound();
            Intent intent = new Intent(setting.this, menu.class);
            startActivity(intent);
        });

        soundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            savePreference(SOUND_PREF, isChecked);
            if (isChecked) {
                soundManager.enableSound();
            } else {
                soundManager.disableSound();
            }
        });

        clickedSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            savePreference(CLICK_PREF, isChecked);
            if (isChecked) {
                soundManager.enableClickSound();
            } else {
                soundManager.disableClickSound();
            }
        });
    }

    private void savePreference(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
