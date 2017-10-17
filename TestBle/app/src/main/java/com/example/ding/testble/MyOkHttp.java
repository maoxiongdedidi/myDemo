package com.example.ding.testble;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.ding.testble.callback.HttpRuselt;
import com.example.ding.testble.callback.MyStringCallBack;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import okhttp3.Call;

/**
 * Created by ding on 2016/11/4.
 */
public class MyOkHttp {
    //设置单利模式
    private MyOkHttp() {
    }

    private static MyOkHttp myOkHttp = null;

    public static MyOkHttp getInstance() {
        if (myOkHttp == null) {
            myOkHttp = new MyOkHttp();
        }
        return myOkHttp;
    }

    public void getStringJson(String url, HashMap<String, String> map, final Context context, final HttpRuselt httpRuselt) {

        if (NetUtils.isNetworkConnected(context)) {
            if (NetUtils.isMobileConnected(context) || NetUtils.isWifiConnected(context)) {
                OkHttpUtils.get().tag(context).url(url)
                        .params(map)
                        .addHeader("Content-Type", "application/json")
                        .build().readTimeOut(10000).writeTimeOut(10000)
                        .execute(new MyStringCallBack() {
                            @Override
                            public void onResponse(Call call, String s) {
                                super.onResponse(call, s);
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    boolean success = jsonObject.getBoolean("success");
                                    if (!success) {
                                        String message = jsonObject.getString("message");


                                        if (message.equals("请登录")) {
                                            Intent logoutIntent = new Intent(context, LoginActivity.class);
                                            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(logoutIntent);
                                        }else{
                                            ToastUtil.showShortToast(message);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                httpRuselt.setString(s);
                            }

                            @Override
                            public void onError(Call call, Exception e) {
                                super.onError(call, e);
                                httpRuselt.seterr(e);
                                cancleTag(context);
                                String name = e.getClass().getName();
                                if (e.getClass() == SocketTimeoutException.class||e.getClass()==TimeoutException.class) {
                                    ToastUtil.showShortToast("连接超时");
                                } else if (e.getClass() == ConnectException.class) {
                                    ToastUtil.showShortToast("服务器连接异常");
                                } else {
                                    ToastUtil.showShortToast(name);
                                }

                            }
                        });
            } else {
                httpRuselt.seterr(new Exception());
                ToastUtil.showShortToast("当前网络不可用");
            }
        } else {
            httpRuselt.seterr(new Exception());
            ToastUtil.showShortToast("当前没有连接网络");
        }

    }

   /* public void fileDownlod(){
        OkHttpUtils.post().url("").build().execute(new FileCallBack() {
            @Override
            public void inProgress(float v, long l) {

            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Call call, File file) {

            }
        });
    }*/


        /**
         * 根据context取消某次请求
         *
         * @author 丁赵来
         * created at 2017/3/22
         */

        public static void cancleTag(Context context) {
            OkHttpUtils.getInstance().cancelTag(context);
        }





}
