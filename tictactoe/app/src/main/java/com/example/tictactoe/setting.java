package com.example.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class setting extends AppCompatActivity {

    private ImageView backbtn;
    private Switch soundSwitch;
    private Switch clickedSwitch;

    private ImageView btnEnglish, btnArabic, btnGerman, btnFrench , btnSpanish , btnRussian , btnHindi ;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "SettingsPrefs";
    private static final String SOUND_PREF = "soundEnabled";
    private static final String CLICK_PREF = "clickEnabled";

    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
         EdgeToEdge.enable(this);
        soundManager = new SoundManager(this);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // language

        btnEnglish = findViewById(R.id.img_english);
        btnArabic = findViewById(R.id.img_arabic);
        btnGerman = findViewById(R.id.img_german);
        btnFrench = findViewById(R.id.img_french);
        btnSpanish = findViewById(R.id.img_spanish);



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

        // Set click listeners for each button
        btnEnglish.setOnClickListener(v -> setLocale("en"));
        btnArabic.setOnClickListener(v -> setLocale("ar"));
        btnGerman.setOnClickListener(v -> setLocale("de"));
        btnFrench.setOnClickListener(v -> setLocale("fr"));
        btnSpanish.setOnClickListener(v -> setLocale("sp"));



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

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Save the selected language to SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

        // Restart the activity to apply the language change
        Intent refresh = new Intent(this, setting.class);
        startActivity(refresh);
        finish();
    }

    // Load the saved language when the app starts
    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", setting.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }
}
