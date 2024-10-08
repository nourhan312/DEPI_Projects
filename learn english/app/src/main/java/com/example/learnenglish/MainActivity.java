package com.example.learnenglish;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
  CardView cardView , cardView2 , cardView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
         cardView = findViewById(R.id.cardColors);
         cardView2 = findViewById(R.id.cardFamily);
         cardView3 = findViewById(R.id.cardAnimals);

        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, colors.class);
            startActivity(intent);
        });

        cardView2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, family.class);
            startActivity(intent);
        });

        cardView3.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, animals.class);
            startActivity(intent);
        });
    }


    }
