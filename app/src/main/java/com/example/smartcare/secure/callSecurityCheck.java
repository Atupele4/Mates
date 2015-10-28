package com.example.smartcare.secure;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.admin.DevicePolicyManager;
import android.content.*;
import android.bluetooth.BluetoothClass;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Service;

import java.security.Provider;

import static android.bluetooth.BluetoothClass.*;


/**
 * Created by SmartCare on 25/10/2015.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class callSecurityCheck extends Service implements SensorEventListener2 {
    private  TriggerEventListener mListener;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    DevicePolicyManager dpMgr;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 3;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public callSecurityCheck(){

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_FASTEST);

        Toast.makeText(getApplicationContext(),"Created",Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
//        Toast.makeText(getApplicationContext(),"Destroid",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFlushCompleted(Sensor sensor) {

    }

    public boolean lock = false;
    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        if (mySensor.getType() == Sensor.TYPE_GYROSCOPE){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    last_x = x;
                    last_y = y;
                    last_z = z;
                    lock = true;
                    if(lock){
                        lock=false;
                        DevicePolicyManager mDPM =
                                (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
                        mDPM.lockNow();
                    }


                    //Toast.makeText(getApplicationContext(),"MESSAGE_ " +last_x + " _ " + last_y + " _ " + last_z + " | ",Toast.LENGTH_SHORT).show();
//                    xc.setText("MESSAGE_ " + last_x + " _ " + last_y + " _ " + last_z + " | ");
//                    Toast.makeText(getApplicationContext(),"TYPE_GYROSCOPE",Toast.LENGTH_SHORT).show();
                }
            }


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

