package com.example.patronus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import service.MessageService;
import service.ScreenReciever;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v("Mainctivity.java","main activity started");
        startMessageService();

        SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);
        boolean firstStart=prefs.getBoolean("firstStart",true);
        if(firstStart){
            Intent intent = new Intent(MainActivity.this,IntroSlides.class);
            startActivity(intent);
        }

        View policeView = findViewById(R.id.police_view);
        policeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),PoliceActivity.class);
                startActivity(intent);
            }
        });

        View hospitalView = findViewById(R.id.hospital_view);
        hospitalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HospitalActivity.class);
                startActivity(intent);
            }
        });

        View helplineView = findViewById(R.id.helpline_view);
        helplineView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),HelplineActivity.class);
                startActivity(intent);
            }
        });

        View contactsView = findViewById(R.id.contacts_view);
        contactsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ContactsActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton settingsButton = (FloatingActionButton) findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),SettingsActivity.class);
                startActivity(intent);
            }
        });

    }

    public void startMessageService()
    {
        Intent intent = new Intent(MainActivity.this,MessageService.class);
        //Log.d("MainActivity.java","service started");
        startService(intent);
    }
}