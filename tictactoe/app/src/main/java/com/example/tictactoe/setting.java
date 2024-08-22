package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class setting extends AppCompatActivity {
    private ImageView backbtn;
    private Switch soundSwitch;
   private Switch clickedSwitch;
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        EdgeToEdge.enable(this);

        backbtn = findViewById(R.id.settings_back_icon);
        soundSwitch = findViewById(R.id.music_switch);
        clickedSwitch = findViewById(R.id.click_switch);
        backbtn.setOnClickListener(v -> {
            Intent intent = new Intent(setting.this, menu.class);
            startActivity(intent);

        });

        if(MyServices.CLICK_CHECK)
        {
            clickedSwitch.setChecked(true);
        }
        else if(!MyServices.CLICK_CHECK)
        {
            clickedSwitch.setChecked(false);
        }

        clickedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    MyServices.CLICK_CHECK =true;
                }
                else {
                    MyServices.CLICK_CHECK= false;
                }
            }

        });
    }
}
