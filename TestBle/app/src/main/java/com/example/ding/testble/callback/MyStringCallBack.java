package com.example.ding.testble.callback;

import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ding on 2016/11/1.
 */
public class MyStringCallBack extends StringCallback {
    @Override
    public void onError(Call call, Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call, String s) {

    }

    @Override
    public String parseNetworkResponse(Response response) throws IOException {

        return super.parseNetworkResponse(response);
    }
}
