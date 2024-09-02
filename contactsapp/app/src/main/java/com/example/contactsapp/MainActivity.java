package com.example.contactsapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ArrayList<user> userList;
    private ListView listView;
    private FloatingActionButton fab;
    private EditText searchInput;
    private boolean isSearchVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);

        listView = findViewById(R.id.main_lw);
        fab = findViewById(R.id.floatingActionButton);
        ImageView searchIcon = findViewById(R.id.search_icon);
        searchInput = findViewById(R.id.search_input);

        dbHelper = new DatabaseHelper(this);
        userList = dbHelper.getAllUsers();
        Custom_Adapter myCustomAdapter = new Custom_Adapter(this, userList, R.layout.activity_contact);
        listView.setAdapter(myCustomAdapter);

        // Handle Search Icon Click
        searchIcon.setOnClickListener(v -> {
            if (!isSearchVisible) {
                // Show search input
                searchInput.setVisibility(View.VISIBLE);
                searchInput.requestFocus();  // Ensure keyboard shows up
                isSearchVisible = true;
            } else {
                // Hide search input
                searchInput.setVisibility(View.GONE);
                isSearchVisible = false;
                showAllContacts();  // Show all contacts when hiding search input
            }
        });

        // Add a TextWatcher to searchInput for real-time search
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Perform search as user types
                filterContacts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed here
            }
        });

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, add_user.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userList.clear();
        userList.addAll(dbHelper.getAllUsers());
        if (userList.isEmpty()) {
            Toast.makeText(this, "No contacts found", Toast.LENGTH_SHORT).show();
        } else {
            ((Custom_Adapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }


    private void filterContacts(String query) {
        ArrayList<user> filteredList = new ArrayList<>();
        for (user u : userList) {
            if (u.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(u);
            }
        }
        Custom_Adapter adapter = new Custom_Adapter(this, filteredList, R.layout.activity_contact);
        listView.setAdapter(adapter);
    }

    private void showAllContacts() {
        Custom_Adapter adapter = new Custom_Adapter(this, userList, R.layout.activity_contact);
        listView.setAdapter(adapter);
    }
}