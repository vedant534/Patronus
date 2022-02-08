package com.example.patronus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    MyPreferenceManager preferenceManager;
    Switch customMessageSwitch;
    EditText customMessageEditText,timerEditText;
    Button saveCustomMessage,saveTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Log.v("SettingsActivity.java","checked");
        radioGroup = findViewById(R.id.message_radiogroup);
        customMessageSwitch = (Switch) findViewById(R.id.custom_message_switch);
        customMessageEditText = (EditText) findViewById(R.id.custom_message_edittext);
        timerEditText = (EditText) findViewById(R.id.timer_edittext);
        saveCustomMessage = (Button) findViewById(R.id.message_save_button);
        saveTimer = (Button) findViewById(R.id.timer_save_button);

        preferenceManager = new MyPreferenceManager(this);
        Log.v("SettingsActivity.java","" + preferenceManager.getDefaultId());
        loadSavedChanges();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Log.v("SettingsActivity.java"," " + checkedId);
                switch (checkedId)
                {
                    //Log.v("SettingsActivity.java","checked");
                    case R.id.radiobutton1:
                        preferenceManager.setDefaultMessage(0);
                        Log.v("SettingsActivity.java","0");
                        break;
                    case R.id.radiobutton2:
                        preferenceManager.setDefaultMessage(1);
                        Log.v("SettingsActivity.java","checked 1");
                        break;
                    case R.id.radiobutton3:
                        preferenceManager.setDefaultMessage(2);
                        Log.v("SettingsActivity.java","checked 2");
                        break;
                    default:
                        preferenceManager.setDefaultMessage(0);
                        break;
                }
            }
        });

        customMessageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferenceManager.setCustomSwitch(isChecked);
            }
        });

        saveCustomMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = customMessageEditText.getText().toString();
                if(TextUtils.isEmpty(msg))
                {
                    Toast.makeText(v.getContext(),"Message can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                }

                preferenceManager.setCustomMessage(msg);
            }
        });

        saveTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = timerEditText.getText().toString();
                if(TextUtils.isEmpty(temp))
                {
                    Toast.makeText(v.getContext(),"Add time first",Toast.LENGTH_SHORT).show();
                    return;
                }

                int time = Integer.parseInt(temp);
                preferenceManager.setWaitTime(time);
            }
        });
    }

    void loadSavedChanges()
    {
        int id = preferenceManager.getDefaultId();
        Log.v("SettingsActivity.java","" + id);
        switch (id)
        {
            case 0:
                radioGroup.check(R.id.radiobutton1);
                break;
            case 1:
                radioGroup.check(R.id.radiobutton2);
                break;
            case 2:
                radioGroup.check(R.id.radiobutton3);
                break;
            default:
                radioGroup.check(R.id.radiobutton1);
        }

        boolean isChecked = preferenceManager.getCustomSwitchState();
        customMessageSwitch.setChecked(isChecked);

        String customMessage = preferenceManager.getCustomMessage();
        customMessageEditText.setHint(customMessage);

        long timer = preferenceManager.getWaitTime();
        timer /= 1000;
        timerEditText.setHint(""+timer);
    }


}