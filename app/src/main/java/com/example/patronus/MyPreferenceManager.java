package com.example.patronus;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferenceManager {
    public static final String customMessage = "helpMessage";
    public static final String waitTime = "messageTime";
    public static final String defaultMessageId = "defualtMessageId";
    public static final String customSwitch = "customSwitch";
    private static final String managerName = "Preference_Manager";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public MyPreferenceManager(Context context)
    {
        sharedPreferences = context.getSharedPreferences(managerName,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setCustomSwitch(boolean state)
    {
        editor.putBoolean(customSwitch,state);
        editor.apply();
    }

    public void setCustomMessage(String msg)
    {
        editor.putString(customMessage,msg);
        editor.apply();
    }

    public void setWaitTime(int time)
    {
        editor.putLong(waitTime,time*1000);
        editor.apply();
    }

    public void setDefaultMessage(int id)
    {
        editor.putInt(defaultMessageId,id);
        editor.apply();
    }

    public boolean getCustomSwitchState()
    {
        return sharedPreferences.getBoolean(customSwitch,false);
    }

    public String getCustomMessage()
    {
        return sharedPreferences.getString(customMessage,"");
    }

    public long getWaitTime()
    {
        return sharedPreferences.getLong(waitTime,30000);
    }

    public int getDefaultId()
    {
        return sharedPreferences.getInt(defaultMessageId,0);
    }

}
