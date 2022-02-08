package service;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.app.Service;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.patronus.MyPreferenceManager;

import java.text.DecimalFormat;

import data.MyContactsContract.MyContactsEntry;

public class MessageService extends Service implements SensorEventListener, LoaderManager.LoaderCallbacks<Cursor> {

    ScreenReciever screenReciever;
    SensorManager sensorManager;
    Sensor mAccelerometer;
    LocationManager locationManager;
    Cursor cursor;
    String latitude = "0",longitude = "0";
    long timeFallen = 0;
    boolean isFallen = false,isPicked = false;
    MyPreferenceManager preferenceManager;

    @Override
    public void onCreate() {
        Log.d("MessageSerivec.java","service started");

        screenReciever = new ScreenReciever();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenReciever,filter);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        MyLocationListener myLocationListener = new MyLocationListener();
        preferenceManager = new MyPreferenceManager(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,mAccelerometer,SensorManager.SENSOR_DELAY_FASTEST);
        Log.d("MessageSerive.java","SensorListenerRegistered");

        //cursor = getContentResolver().query(MyContactsEntry.CONTENT_URI,null,null,null,null);
        Log.v("MessageSerive.java","sending help messages");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        MyLocationListener myLocationListener = new MyLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        } else {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    10000,
                    100,
                    myLocationListener
            );
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    10000,
                    100,
                    myLocationListener
            );
        }

        getLocation();
        Log.v(latitude,longitude);
        
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Log.d("MessageSerivece.java","onSensorChangedcalled");


        try {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                double loX = event.values[0];
                double loY = event.values[1];
                double loZ = event.values[2];

                double loAccelerationReader = Math.sqrt(Math.pow(loX, 2)
                        + Math.pow(loY, 2)
                        + Math.pow(loZ, 2));

                DecimalFormat precision = new DecimalFormat("0.00");
                double ldAccRound = Double.parseDouble(precision.format(loAccelerationReader));

                //if(isFallen == true)
                    //Log.v("MessageService.java",""+ldAccRound);

                if (ldAccRound > 0 && ldAccRound <=  0.5 && isFallen == false) {
                    //Cursor res = db.getAllData();
                    Log.d("TAGGER", "onSensorChanged: " + "device fallen " + ldAccRound);
                    isFallen = true;
                    isPicked = false;
                    timeFallen = System.currentTimeMillis();
                    Log.v("TAGGER","onSensorChanged " + timeFallen);
                    (new sendMessageAsync()).execute();
                    //sendHelpMessages();
//                    if (res.getCount() == 0)
//                        Toast.makeText(this, "No contacts given", Toast.LENGTH_SHORT).show();
//                    else {
//                        new Async().execute();
//                    }
                    //return;
                }
//                else if(ldAccRound > 10 && isFallen == true && System.currentTimeMillis() - timeFallen > 2000)
//                {
//                    isPicked = true;
//                    isFallen = false;
//                    Log.v("TAGGER","onSensorChanged" + "Device has been Picked");
//                }
            }
        } catch (Exception e) {}

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this,MyContactsEntry.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        cursor = data;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        cursor = null;
    }

    void sendHelpMessages()
    {
        boolean isChecked = preferenceManager.getCustomSwitchState();
        String customMessage = preferenceManager.getCustomMessage();
        int id = preferenceManager.getDefaultId();

        String message = "";

        if(!isChecked || customMessage == "")
            message = getDefaultMessage(id);
        else
            message = customMessage;

        StringBuffer smsAddressLink = new StringBuffer();
        getLocation();
        smsAddressLink.append("http://maps.google.com/?q=");
        smsAddressLink.append(latitude);
        smsAddressLink.append(",");
        smsAddressLink.append(longitude);

        Cursor cursor = getContentResolver().query(MyContactsEntry.CONTENT_URI,null,null,null,null);

        message = message + System.getProperty("line.separator") +
                smsAddressLink.toString();

        SmsManager smsManager = SmsManager.getDefault();
        int numberColumnIndex = cursor.getColumnIndex(MyContactsEntry.COLUMN_NUMBER);

        while(cursor.moveToNext())
        {
            String number = cursor.getString(numberColumnIndex);
            smsManager.sendTextMessage(number,null,message,null,null);
        }

    }

    public class MyLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
            location.getProvider();

            //Log.v("MessageService.java",latitude);
        }
    }

    public void getLocation()
    {
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location;
        if(!isGPSEnabled && !isNetworkEnabled)
            return;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else
        {
            if(isNetworkEnabled)
            {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());
            }
            else if(isGPSEnabled)
            {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());
            }
        }

        return;
    }

    public class sendMessageAsync extends AsyncTask<Void,Void,Void> implements SensorEventListener{

        SensorManager sensorManager;
        Sensor mAccelerometer;

        public sendMessageAsync()
        {
            sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
            mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this,mAccelerometer,SensorManager.SENSOR_DELAY_FASTEST);
            Log.d("MessageSerive.java","SensorListenerRegistered");
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            double loX = event.values[0];
            double loY = event.values[1];
            double loZ = event.values[2];

            double loAccelerationReader = Math.sqrt(Math.pow(loX, 2)
                    + Math.pow(loY, 2)
                    + Math.pow(loZ, 2));

            DecimalFormat precision = new DecimalFormat("0.00");
            double ldAccRound = Double.parseDouble(precision.format(loAccelerationReader));

            if(ldAccRound > 11 && isFallen == true && System.currentTimeMillis() - timeFallen > 2000)
            {
                isPicked = true;
                isFallen = false;
                Log.v("TAGGER","onSensorChanged" + "Device has been Picked");
            }


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            long currentTime = System.currentTimeMillis();
            Log.d("TAGGER","current Time " + currentTime);

            long waitTime = preferenceManager.getWaitTime();

            while(System.currentTimeMillis() - currentTime <= waitTime)
            {
                if(isPicked == true)
                    break;
            }
            Log.d("TAGGER","lOOP BROKERN");
            if(isPicked == false){
                isFallen = false;
                isPicked = false;
                sendHelpMessages();
            }
            else
                Log.d("TAGGER","No need to send Message");

            isFallen = false;
            isPicked = false;

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            sensorManager.unregisterListener(this);
            return;
            //super.onPostExecute(unused);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


    }
    String getDefaultMessage(int id)
    {
        switch (id){
            case 0:
                return "Help me my Location is";
            case 1:
                return "My location is";
            case 2:
                return "I am in danger please help";
            default:
                return "Help me my Location is";
        }
    }
}
