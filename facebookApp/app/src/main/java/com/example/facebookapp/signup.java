package com.example.facebookapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class signup extends AppCompatActivity {
    private EditText username, password, email;
    private Button signupButton;

    private UserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        EdgeToEdge.enable(this);

        username = findViewById(R.id.usernameSignup);
        password = findViewById(R.id.passSignUp);
        email = findViewById(R.id.emailSignUp);
        signupButton = findViewById(R.id.buttonSignUp);

        db = new UserDatabase(this);

        signupButton.setOnClickListener(view -> {
            String Username = username.getText().toString().trim();
            String Password = password.getText().toString().trim();
            String Email = email.getText().toString().trim();


            if (Username.isEmpty()) {
                username.setError("Username cannot be empty");
                return;
            }
            if (Username.length() < 3 || Username.length() > 15) {
                username.setError("Username must be between 3 and 15 characters");
                return;
            }


            if (Email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                email.setError("Invalid email address");
                return;
            }


            if (Password.isEmpty()) {
                password.setError("Password cannot be empty");
                return;
            }
            if (Password.length() < 6) {
                password.setError("Password must be at least 6 characters long");
                return;
            }


            if (db.getUser(Email).getCount() > 0) {
                email.setError("Email is already in use");
                return;
            }

            if (db.addUser(Username, Email, Password)) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.putString("username", Username);
                editor.putString("email", Email);
                editor.apply();

                Toast.makeText(signup.this, "User created successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(signup.this, home.class);
                intent.putExtra("username", Username);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(signup.this, "Error creating user", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
