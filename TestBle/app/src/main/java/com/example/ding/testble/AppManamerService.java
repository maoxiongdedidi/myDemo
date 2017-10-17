package com.example.ding.testble;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.ding.testble.callback.HttpRuselt;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by 丁赵来 on 2017/9/19.
 */

public class AppManamerService extends Service {
    private ScreenManager mScreenManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler.post(mRunnable);
        mScreenManager = ScreenManager.getScreenManager();
    }

    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            loginJudge();
            mHandler.postDelayed(mRunnable,5000);
        }
    };














    /**
     * 登录的判断
     */
    public void loginJudge() {

        mHandler.postDelayed(mRunnable, 6000);//登录6秒超时

        if(NetUtils.isNetworkConnected(this)){
            if(NetUtils.isMobileConnected(this)||NetUtils.isWifiConnected(this)){
                HashMap<String,String> map = new HashMap<>();
                map.put("username",Constant.username);
                map.put("password",Constant.password);
                MyOkHttp.getInstance().getStringJson(Constant.LOGIN, map, this, new HttpRuselt() {
                    @Override
                    public void setString(String s) {
                        Gson gson = new Gson();
                        LoginJson loginJson = gson.fromJson(s, LoginJson.class);
                        if(loginJson.isSuccess()){
                            if(loginJson.getData().equals("0")){

                            }else{
                               // android.os.Process.killProcess(android.os.Process.myPid());
                                mScreenManager.AppExit(AppManamerService.this);
                            }
                        }else{
                            mScreenManager.AppExit(AppManamerService.this);
                        }
                    }
                    @Override
                    public void seterr(Exception e) {
                        mScreenManager.AppExit(AppManamerService.this);
                    }
                });
            }else{
                mScreenManager.AppExit(AppManamerService.this);
            }
        }else{
            mScreenManager.AppExit(AppManamerService.this);
        }


    }






}
