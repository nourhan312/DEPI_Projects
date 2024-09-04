package com.example.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private ArrayList<user> userList;
    private ListView listView;
    private FloatingActionButton fab;
    private EditText searchInput;
    private ImageView searchIcon, clearButton, noContactsImage;
    private TextView toolbarTitle;
    private boolean isSearchVisible = false;
    private Custom_Adapter myCustomAdapter;
    private androidx.appcompat.widget.Toolbar toolbarMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.main_lw);
        fab = findViewById(R.id.floatingActionButton);
        searchIcon = findViewById(R.id.search_icon);
        searchInput = findViewById(R.id.search_input);
        noContactsImage = findViewById(R.id.no_contacts_image);
        toolbarMain = findViewById(R.id.toolbarMain);
        toolbarTitle = findViewById(R.id.toolbar_title);
        clearButton = findViewById(R.id.clear_button);

        dbHelper = new DatabaseHelper(this);
        userList = dbHelper.getAllUsers();
        myCustomAdapter = new Custom_Adapter(this, userList, R.layout.activity_contact);
        listView.setAdapter(myCustomAdapter);

        // Toggle search bar visibility
        searchIcon.setOnClickListener(v -> toggleSearchBar());

        clearButton.setOnClickListener(v -> {
            searchInput.setText(""); // Clear search text
            filterContacts(""); // Reset the contact list
        });

        // Listen for search input changes
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterContacts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Add new contact
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, add_user.class);
            startActivity(intent);
        });

        // Check if there are no contacts and display the placeholder image
        checkNoContacts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userList.clear();
        userList.addAll(dbHelper.getAllUsers());
        checkNoContacts();
        if (userList.isEmpty()) {
            Toast.makeText(this, "No contacts found", Toast.LENGTH_SHORT).show();
        } else {
            myCustomAdapter.notifyDataSetChanged();
        }
    }

    private void toggleSearchBar() {
        if (!isSearchVisible) {
            searchInput.setVisibility(View.VISIBLE);
            toolbarTitle.setVisibility(View.GONE);
            searchInput.requestFocus();
            isSearchVisible = true;
            showKeyboard(searchInput);
        } else {
            searchInput.setVisibility(View.GONE);
            searchInput.setText(""); // Clear search text
            toolbarTitle.setVisibility(View.VISIBLE);
            isSearchVisible = false;
            showAllContacts();
            hideKeyboard(searchInput);
        }
    }

    private void filterContacts(String query) {
        ArrayList<user> filteredList = new ArrayList<>();
        for (user u : userList) {
            if (u.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(u);
            }
        }
        myCustomAdapter = new Custom_Adapter(this, filteredList, R.layout.activity_contact);
        listView.setAdapter(myCustomAdapter);
        checkNoContacts();
    }

    private void showAllContacts() {
        myCustomAdapter = new Custom_Adapter(this, userList, R.layout.activity_contact);
        listView.setAdapter(myCustomAdapter);
        checkNoContacts();
    }

    private void checkNoContacts() {
        if (userList.isEmpty()) {
            noContactsImage.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            noContactsImage.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    }

    private void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
