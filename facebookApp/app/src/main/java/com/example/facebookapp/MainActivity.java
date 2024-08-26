package com.example.facebookapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
   private EditText email , password;
   private AppCompatButton CreateAccountbutton;
   private Button loginButton;

private  UserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        CreateAccountbutton = findViewById(R.id.createNewAccountButton);
        loginButton = findViewById(R.id.Loginbutton);


        db = new UserDatabase(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, home.class);
            startActivity(intent);
            finish();
        }

        loginButton.setOnClickListener(view -> {
            String Email = email.getText().toString().trim();
            String Password = password.getText().toString().trim();



            if (Email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                email.setError("Enter a valid email address");
                return;
            }

            if (Password.isEmpty()) {
                password.setError("Enter a password");
                return;
            }


            Cursor cursor = db.checkUser(Email, Password);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                Intent intent = new Intent(MainActivity.this, home.class);
                intent.putExtra("username", username);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.putString("username", username);
                editor.putString("email", Email);
                editor.apply();

                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });
        CreateAccountbutton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, signup.class);
            startActivity(intent);
        });
    }



    }
