package com.example.luckeynumbers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class luckey_number extends AppCompatActivity {


    private TextView tvNumber;

    private TextView tvName;
    private Button btnShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_luckey_number);

        tvNumber = findViewById(R.id.tvNumber);
        btnShare = findViewById(R.id.btnShare);
        tvName = findViewById(R.id.tvLuckyNumber);
        int randomNumber = (int) (Math.random() * 100);
        String name = getIntent().getStringExtra("name");
        tvName.setText(
                "Hello " + name + "\n" + " your lucky number is:");
        tvNumber.setText(String.valueOf(randomNumber));
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String message = "Hey " + name + "! Your lucky number is " + randomNumber + ".";
                shareIntent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(shareIntent, "Share via"));

            }
        });
    }
}