package com.example.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.Locale;

public class Login extends AppCompatActivity {

    private EditText editTextEmailAddress, editTextPassword;
    private AppCompatButton loginBtn;
    private DatabaseHelper dbHelper;
    private TextView register_redirect;
    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_login);
        EdgeToEdge.enable(this);

        // Initialize views

        editTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        loginBtn = findViewById(R.id.ai_player_name_btn);
        register_redirect = findViewById(R.id.signupPage);

        // Initialize SoundManager and DatabaseHelper
        soundManager = new SoundManager(this);
        dbHelper = new DatabaseHelper(this);

        // Check if the user is already logged in
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // If user is logged in, navigate to the main activity
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
            return; // Skip the rest of the onCreate code
        }

        register_redirect.setOnClickListener(view -> {
            soundManager.playClickSound();
            Intent intent = new Intent(Login.this, signup.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(v -> {
            soundManager.playClickSound();
            String email = editTextEmailAddress.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmailAddress.setError(getString(R.string.enter_valid_email));
                return;
            }

            if (password.isEmpty()) {
                editTextPassword.setError(getString(R.string.password_is_required));
                return;
            }

            if (dbHelper.checkUser(email, password)) {
                // Save login state





                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.putString("userEmail", email); // Save email if needed
                editor.putString("userName", dbHelper.getUserName(email)); // Save username
                editor.apply();

                Toast.makeText(Login.this, R.string.login_successful, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Login.this, R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
            }
            soundManager.playClickSound();
        });
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale); // Use setLocale method for better compatibility
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", ""); // Default to empty string if not set
        setLocale(language);
    }

    public void logout() {
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear(); // Clear all preferences
        editor.apply();

        Intent intent = new Intent(Login.this, Login.class);
        startActivity(intent);
        finish();
    }
}
