package com.example.ding.testbezier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main3Activity extends AppCompatActivity {

    private BatteryView mViewById;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mViewById = (BatteryView)findViewById(R.id.battery);
        mViewById.setPower(100);

        mViewById.setBattery_head_height(5);
        mViewById.setBattery_head_width(5);
        mViewById.setBattery_inside_margin(5);
        mViewById.setBattery_top(100);
        mViewById.setBattery_left(100);
        mViewById.setBattery_height(50);
        mViewById.setBattery_width(30);
        /*       或者在xml文件中定义一下属性
        app:battery_head_height="5"
        app:battery_head_width="5"
        app:battery_inside_margin="5"
        app:battery_top="100"
        app:battery_left="100"
        app:battery_height="50"
        app:battery_width="30"*/

    }
}
