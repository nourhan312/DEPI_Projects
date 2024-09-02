package com.example.contactsapp;

import android.content.Intent;
import android.graphics.Bitmap;
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

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class edit_contact extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText nameEditText, phoneEditText;
    private CircleImageView contactImageView;
    private Button saveButton, uploadButton, deleteButton;
    private DatabaseHelper dbHelper;
    private user contact;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        // Initialize UI components
        nameEditText = findViewById(R.id.edit_name);
        phoneEditText = findViewById(R.id.edit_phone);
        contactImageView = findViewById(R.id.contact_image);
        saveButton = findViewById(R.id.save_button);
        uploadButton = findViewById(R.id.upload_button);
        deleteButton = findViewById(R.id.delete_button);

        dbHelper = new DatabaseHelper(this);


        Intent intent = getIntent();
        int contactId = intent.getIntExtra("CONTACT_ID", -1);


        contact = dbHelper.getUserById(contactId);
        if (contact != null) {
            nameEditText.setText(contact.getName());
            phoneEditText.setText(contact.getNumber());


            if (contact.getPhotoPath() != null && !contact.getPhotoPath().isEmpty()) {
                contactImageView.setImageURI(Uri.parse(contact.getPhotoPath()));
            }
        }

        saveButton.setOnClickListener(v -> updateContact());
        uploadButton.setOnClickListener(v -> openGallery());
        deleteButton.setOnClickListener(v -> deleteContact());
    }

    private void updateContact() {
        String updatedName = nameEditText.getText().toString().trim();
        String updatedPhone = phoneEditText.getText().toString().trim();
        String photoPath = (selectedImageUri != null) ? selectedImageUri.toString() : contact.getPhotoPath(); // Use existing photo path if no new photo is selected

        if (updatedName.isEmpty() || updatedPhone.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        contact.setName(updatedName);
        contact.setNumber(updatedPhone);
        contact.setPhotoPath(photoPath);

        dbHelper.updateUser(contact);

        Toast.makeText(this, "Contact updated successfully", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }


    private void deleteContact() {
        if (contact != null) {
            dbHelper.deleteUser(contact.getId());
            Toast.makeText(this, "Contact deleted successfully", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                contactImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}