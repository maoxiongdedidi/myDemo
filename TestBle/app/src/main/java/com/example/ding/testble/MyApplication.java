package com.example.ding.testble;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by ding on 2016/10/31.
 */
public   class MyApplication extends Application {
    public static int setmap=1;
    public static MainActivity sMainActivity;

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public MyApplication() {
        super();
    }

    public void getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        Integer integer = Integer.valueOf(str);
        if(integer>=24||integer<0){
            SharedPreferences theme = getSharedPreferences("theme", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = theme.edit();
            edit.putInt("nowtime",1);
            edit.commit();
        }else{
            SharedPreferences theme = getSharedPreferences("theme", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = theme.edit();
            edit.putInt("nowtime",0);
            edit.commit();
        }
    }


    /**
     * 设置夜间白天模式
     * @return
     */
    public int getMyTheme(){

        SharedPreferences theme = getSharedPreferences("theme", Context.MODE_PRIVATE);


        return theme.getInt("nowtime",0);
    }

    /**
     * 得到屏幕宽度
     * @return
     */
    public int getScreenWidth() {
        WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;

    }
    /**
     * 关闭软件盘
     */
    public void hideInputMethod(Activity activity) {
        View view = activity.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager)activity. getSystemService(INPUT_METHOD_SERVICE);
        if (view!=null&&imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
    *获取context
    *@author 丁赵来
    *created at 2017/3/17
    */
    public static Context getContext() {
        return mContext;
    }




}
