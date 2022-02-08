package service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReciever extends BroadcastReceiver {
    String LOG_TAG = "ScreenReciever.java";
    boolean screenOn = true;

    boolean isScreenOn()
    {
        return screenOn;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() == Intent.ACTION_SCREEN_OFF)
        {
            screenOn = false;
            Log.d(LOG_TAG,"screen turned off");
        }
        else if(intent.getAction() == Intent.ACTION_SCREEN_ON)
        {
            screenOn = true;
            Log.d(LOG_TAG,"screen turned on");
        }
    }
}
