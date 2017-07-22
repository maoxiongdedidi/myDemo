package com.doohan.testamap;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.SeekBar;

import com.amap.api.col.bw;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.animation.AlphaAnimation;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.trace.TraceLocation;

import java.util.ArrayList;

public class PathReplay extends AppCompatActivity implements AMap.OnMapLoadedListener,SeekBar.OnSeekBarChangeListener,View.OnClickListener{
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

                if(curProgress!=processBar.getMax())
                {
                    processBar.setProgress(curProgress+1);
                    timer.postDelayed(runnable, 1000);
                }else
                {
                    Button button = (Button) findViewById(R.id.replay_btn);
                    button.setText("回放 ");// 已执行到最后一个坐标 停止任务
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
        LatLng location1=new LatLng(30.33306595178262,120.1170618114471);
        LatLng location2=new LatLng(30.33337153473411,120.11701889610286);
        LatLng location3=new LatLng(30.333741937033068,120.11696525192257);
        LatLng location4=new LatLng(30.334167897944667,120.11685796356197);
        LatLng location5=new LatLng(30.334686456553385,120.11677213287349);
        LatLng location6=new LatLng(30.335047593962152,120.11669703102108);
        LatLng location7=new LatLng(30.334982774525372, 120.11632152175899);
        LatLng location8=new LatLng(30.334825355714653,120.11583872413631);
        LatLng location9=new LatLng(30.33474201624181,120.1152593669891);
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
       marker= aMap.addMarker(markerOptions);
        // aMap.addMarker(markerOptions);
    }

    /**
     * 添加人的图标，current0.1秒变化一次
     * @param current
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void AddAnimation(int current)
    {
        LatLng position=locationList.get(current-1);
        Log.e("aaa", ""+current );

      /*  float scale = aMap.getScalePerPixel();
        //代表range（米）的像素数量
        int pixel = Math.round(15 / scale);
        //小范围，小缩放级别（比例尺较大），有精度损失
        Projection projection = aMap.getProjection();
        //将地图的中心点，转换为屏幕上的点
        Point center = projection.toScreenLocation(position);
        //将屏幕上的点转换为地图上的点
        LatLng center1=projection.fromScreenLocation(center);*/





        Animation animation = new TranslateAnimation(position);

        //时间调小就可以正常
        long duration =1000;
        // PathInterpolator p = new PathInterpolator();
        animation.setDuration(duration);
       // animation.setInterpolator();

       // marker.setIcons( marker.getIcons());
        marker.setAnimation(animation);
        marker.startAnimation();

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
            AddAnimation(progress);
        }
        /*try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

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
                replayButton.setText("停止");
                timer.postDelayed(runnable, 10);
            }
        } else {

            // 移除定时器的任务
            timer.removeCallbacks(runnable);
            replayButton.setText(" 回放 ");
        }
    }

}
