package com.doohan.testsensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //获取陀螺仪传感器
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        Toast.makeText(this,"x:"+x+"y:"+y+"z:"+z,Toast.LENGTH_SHORT).show();

        Log.e("onSensorChanged","x:"+x);
        Log.e("onSensorChanged","y:"+y);
        Log.e("onSensorChanged","z:"+z);



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
