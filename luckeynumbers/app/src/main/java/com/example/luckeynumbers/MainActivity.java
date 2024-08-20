package com.example.luckeynumbers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {
 private EditText name;
 private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
      name = findViewById(R.id.etName);
      button = findViewById(R.id.btnGenerateLuckyNumber);

      button.setOnClickListener(view -> {
        String text =  name.getText().toString();
        if(!text.isEmpty()){
            Intent intent = new Intent(MainActivity.this, luckey_number.class);
            intent.putExtra("name",text);
            startActivity(intent);
        }else{
            name.setError("Please enter your name");
        }
      });
    }
}