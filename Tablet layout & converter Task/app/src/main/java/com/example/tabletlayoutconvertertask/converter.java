package com.example.tabletlayoutconvertertask;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class converter extends AppCompatActivity {
    EditText editText;
    TextView result;
    Button btn;    Double F, C;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_converter);


        editText = findViewById(R.id.calc_ed_first);
        result = findViewById(R.id.textView);
         btn = findViewById(R.id.button);

         btn.setOnClickListener(
                 view -> {
                     if (editText.getText().toString().isEmpty()) {
                         Toast.makeText(this,R.string.Empty_field,Toast.LENGTH_SHORT).show();
                     }else{
                         F = Double.parseDouble(editText.getText().toString());
                         C = (F - 32) * 5 / 9;
                         result.setText(String.format("%s °C", (C.toString()+"00000").substring(0, 7)));
                     }



                 }
         );
     
    }
}