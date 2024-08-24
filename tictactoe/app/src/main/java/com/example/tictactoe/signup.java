package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class signup extends AppCompatActivity {

    private EditText editTextUserName, editTextEmailAddress, editTextPassword;
    private AppCompatButton signupBtn;
    private DatabaseHelper dbHelper;
   private   TextView login_redirect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        editTextUserName = findViewById(R.id.editTextUserName);
        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        signupBtn = findViewById(R.id.signup_btn);
        login_redirect = findViewById(R.id.login_redirect);
        dbHelper = new DatabaseHelper(this);



        login_redirect.setOnClickListener(view ->  {

                Intent intent = new Intent(signup.this, Login.class);
                startActivity(intent);

        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUserName.getText().toString().trim();
                String email = editTextEmailAddress.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (username.isEmpty()) {
                    editTextUserName.setError(getString(R.string.username_is_required));
                    return;
                }

                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmailAddress.setError(getString(R.string.enter_a_valid_email));
                    return;
                }

                if (password.isEmpty() || password.length() < 6) {
                    editTextPassword.setError(getString(R.string.password_must_be_at_least_6_characters));
                    return;
                }

                if (dbHelper.userExists(email)) {
                    editTextEmailAddress.setError(getString(R.string.email_already_exists));
                    return;
                }

                dbHelper.addUser(username, email, password);
                Toast.makeText(signup.this, R.string.sign_up_successful, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(signup.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
