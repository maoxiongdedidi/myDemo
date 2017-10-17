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
