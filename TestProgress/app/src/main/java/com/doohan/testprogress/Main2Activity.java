package com.doohan.testprogress;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class Main2Activity extends AppCompatActivity {
    private final boolean DEBUG = true;
    private final String TAG = "Main2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout mainLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.activity_main2, null);
        setContentView(mainLayout);
        CircleSeekBar circleSeekBar = (CircleSeekBar) findViewById(R.id.circle_seekbar);
        circleSeekBar.setProgress(100);

        circleSeekBar.setProgressFrontColor(Color.RED);
        circleSeekBar.setOnSeekBarChangeListener(new CircleSeekBarOnChangeListener());

        CircleSeekBar circleSeekBar2 = new CircleSeekBar(this);
        circleSeekBar2.setProgress(10);
        circleSeekBar2.setProgressFrontColor(Color.RED);
        circleSeekBar2.setProgressThumb(R.drawable.thumb);
        circleSeekBar2.setOnSeekBarChangeListener(new CircleSeekBarOnChangeListener());
        RelativeLayout.LayoutParams circleSeekBarParams = new RelativeLayout.LayoutParams(200, 200);
        circleSeekBarParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mainLayout.addView(circleSeekBar2, circleSeekBarParams);
    }

    private class CircleSeekBarOnChangeListener implements CircleSeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(int progress) {
            if(DEBUG) Log.d(TAG, "onProgressChanged progress = " + progress);
        }

        @Override
        public void onStartTrackingTouch() {
            if(DEBUG) Log.d(TAG, "onStartTrackingTouch");
        }

        @Override
        public void onStopTrackingTouch() {
            if(DEBUG) Log.d(TAG, "onStopTrackingTouch");
        }

    }

}

