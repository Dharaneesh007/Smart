package com.my.smart;

import static android.provider.Telephony.Mms.Part.TEXT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;

public class SettingsActivity extends AppCompatActivity {

    SwitchCompat fingerswitch;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fingerswitch = findViewById(R.id.fingerswitch);

        SharedPreferences sharedPreferences = getSharedPreferences("save",0);

        fingerswitch.setChecked(sharedPreferences.getBoolean("value",true));

        fingerswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("save",0).edit();
                    editor.putString(TEXT,"1");
                    editor.putBoolean("value",compoundButton.isChecked());
                    editor.apply();
                    compoundButton.setChecked(true);

                }else {
                    SharedPreferences.Editor editor = getSharedPreferences("save",0).edit();
                    editor.putString(TEXT,"0");
                    editor.putBoolean("value",compoundButton.isChecked());
                    editor.apply();
                    compoundButton.setChecked(false);

                }
            }
        });
    }
}