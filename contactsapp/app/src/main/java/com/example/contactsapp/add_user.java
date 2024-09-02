package com.example.contactsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import de.hdodenhof.circleimageview.CircleImageView;

public class add_user extends AppCompatActivity {

    EditText name, phone;
    DatabaseHelper my_db;
    Button btn;
    private static final int PICK_IMAGE = 1;
    private Uri photoUri;
    private CircleImageView photoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        name = findViewById(R.id.name_edit_text);
        phone = findViewById(R.id.number_edit_text);
        btn = findViewById(R.id.add_button);
        photoImageView = findViewById(R.id.profile_image);


        my_db = new DatabaseHelper(this);

        // select photo

        Button selectPhotoButton = findViewById(R.id.Add_upload_button);
        selectPhotoButton.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_IMAGE);
        });

        btn.setOnClickListener(v -> {
            String userName = name.getText().toString().trim();
            String userPhone = phone.getText().toString().trim();
            String photoPath = photoUri != null ? photoUri.toString() : null;

            if (!validateInputs(userName, userPhone)) {
                return;
            }

            // insert user into database

            boolean success = my_db.insertUser(new user(userName, userPhone, photoPath));

            if (success) {
                Toast.makeText(getApplicationContext(), "User added successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Error adding user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            photoUri = data.getData();
            if (photoUri != null) {
                photoImageView.setImageURI(photoUri);
            }
        }
    }

    private boolean validateInputs(String userName, String userPhone) {
        if (userName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter a name", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (userPhone.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter a phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidPhoneNumber(userPhone)) {
            Toast.makeText(getApplicationContext(), "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isValidPhoneNumber(String phone) {
        String phonePattern = "^[+]?[0-9]{10,13}$";
        return phone.matches(phonePattern);
    }
}