package com.example.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class Login extends AppCompatActivity {

    private EditText editTextEmailAddress, editTextPassword;
    private AppCompatButton loginBtn;
    private DatabaseHelper dbHelper;
    private TextView register_redirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EdgeToEdge.enable(this);

        editTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        loginBtn = findViewById(R.id.ai_player_name_btn);
        dbHelper = new DatabaseHelper(this);

        register_redirect = findViewById(R.id.signupPage);

        register_redirect.setOnClickListener(view ->  {

                Intent intent = new Intent(Login.this, signup.class);
                startActivity(intent);

        });

        loginBtn.setOnClickListener(v-> {

                String email = editTextEmailAddress.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmailAddress.setError("Enter a valid email");
                    return;
                }

                if (password.isEmpty()) {
                    editTextPassword.setError("Password is required");
                    return;
                }

                if (dbHelper.checkUser(email, password)) {

                    // Save login state
                    SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("userEmail", email); // Save email if needed
                    editor.apply();

                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);

                    finish();

                } else {
                    Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }

        });
    }
}
