package com.doohan.testamap;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.SeekBar;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.trace.LBSTraceClient;
import com.amap.api.trace.TraceListener;
import com.amap.api.trace.TraceLocation;

import java.util.ArrayList;
import java.util.List;


public class PathPlayBlackActivity extends AppCompatActivity implements AMap.OnMapLoadedListener,SeekBar.OnSeekBarChangeListener,View.OnClickListener{
    private MapView mapView;
    private AMap aMap;
    private ArrayList<LatLng> locationList=new ArrayList<LatLng>();
    private ArrayList<TraceLocation> mTraceList=new ArrayList<TraceLocation>();
    private Marker marker;
    private Handler timer=new Handler();
    private Runnable runnable;
    private Button replayButton;
    private SeekBar processBar;
    private Handler handler =new Handler()
    {
        public void handleMessage(android.os.Message msg) {
            if(msg.what==1)
            {
                int curProgress=processBar.getProgress();
                Log.e("aaa", ""+curProgress);
                if(curProgress!=processBar.getMax())
                {
                    processBar.setProgress(curProgress+1);
                    timer.postDelayed(runnable, 500);
                }else
                {
                    Button button = (Button) findViewById(R.id.replay_btn);
                    button.setText(" 再次回放 ");// 已执行到最后一个坐标 停止任务
                }

            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_play_black);
        mapView=(MapView)findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        if(aMap==null)
        {
            aMap=mapView.getMap();
            aMap.setOnMapLoadedListener(this);

        }
        LatLng location1=new LatLng(43.828, 87.621);
        LatLng location2=new LatLng(43.8283, 87.622);
        LatLng location3=new LatLng(43.8285, 87.623);
        LatLng location4=new LatLng(43.8281, 87.624);
        LatLng location5=new LatLng(43.8282, 87.625);
        LatLng location6=new LatLng(43.8284, 87.626);
        LatLng location7=new LatLng(43.8286, 87.627);
        LatLng location8=new LatLng(43.8288, 87.628);
        LatLng location9=new LatLng(43.8289, 87.629);
        locationList.add(location1);
        locationList.add(location2);
        locationList.add(location3);
        locationList.add(location4);
        locationList.add(location5);
        locationList.add(location6);
        locationList.add(location7);
        locationList.add(location8);
        locationList.add(location9);
        AddPolyline();
        AddMarkers();

        InitView();
        runnable=new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                handler.sendMessage(Message.obtain(handler, 1));
            }
        };




    }
    //为所有经纬度点划线
    private void AddPolyline()
    {
        PolylineOptions polylineOptions=new PolylineOptions();
        polylineOptions.addAll(locationList);
        //设置线段的可见性
        polylineOptions.visible(true)
                //设置线段颜色
                .color(Color.RED)
                //设置线段宽度（像素值）
                .width(5);
        //设置线段为大地曲线（默认为false）
        polylineOptions.geodesic(true);
        aMap.addPolyline(polylineOptions);
    }

    private void AddMarkers()
    {
        ArrayList<MarkerOptions> markerOptionsList=new ArrayList<MarkerOptions>();
        //为经纬度设置marker（全部）
       /* for(int i=0;i<locationList.size();i++)
        {
            MarkerOptions markerOptions=new MarkerOptions();
            markerOptions.visible(true).draggable(false).position(locationList.get(i));
            markerOptionsList.add(markerOptions);
        }*/
        //为经纬度设置marker（首、尾）
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.visible(true).draggable(false).position(locationList.get(0)).icon(BitmapDescriptorFactory.fromResource(R.drawable.car));
        markerOptionsList.add(markerOptions);
       /* MarkerOptions markerOptions1=new MarkerOptions();
        markerOptions.visible(true).draggable(false).position(locationList.get(locationList.size()));
        markerOptionsList.add(markerOptions1);
*/
        aMap.addMarkers(markerOptionsList, false);
       // aMap.addMarker(markerOptions);
    }

    /**
     * 添加人的图标，current0.1秒变化一次
     * @param current
     */
    private void AddCarMarker(int current)
    {
        if(marker!=null)
        {
            marker.destroy();
        }
        LatLng position=locationList.get(current-1);
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(position).visible(true).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.my_address_icon_two)).anchor(0.5f, 0.5f);
        marker=aMap.addMarker(markerOptions);


    }
    private void InitView()
    {
        replayButton=(Button)findViewById(R.id.replay_btn);
        processBar=(SeekBar)findViewById(R.id.seekbar);
        processBar.setMax(locationList.size());
        processBar.setOnSeekBarChangeListener(this);
        replayButton.setOnClickListener(this);
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onMapLoaded() {
        // TODO Auto-generated method stub
       // aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        //返回一个CameraUpdate 对象，包括可视区域框移动目标点屏幕中心位置的经纬度以及缩放级别。
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationList.get(0),15) );
       // aMap.moveCamera(CameraUpdateFactory.changeLatLng(locationList.get(0)) );
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        // TODO Auto-generated method stub
        if(progress!=0)
        {
            AddCarMarker(progress);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onClick(View arg0) {
        // 根据按钮上的字判断当前是否在回放
        if (replayButton.getText().toString().trim().equals("回放")) {
            if (locationList.size() > 0) {
                // 假如当前已经回放到最后一点 置0
                if (processBar.getProgress() == processBar.getMax()) {
                    processBar.setProgress(0);
                }
                // 将按钮上的字设为"停止" 开始调用定时器回放
                Log.e("getMax",""+processBar.getMax());
                Log.e("getProgress",""+processBar.getProgress());
                replayButton.setText(" 停止 ");
                timer.postDelayed(runnable, 10);
            }
        } else {
            // 移除定时器的任务
            timer.removeCallbacks(runnable);
            replayButton.setText(" 回放 ");
        }
    }

}
