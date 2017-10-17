package com.example.ding.testble.callback;

import android.content.Context;

/**
 * Created by ding on 2017/3/14.
 */
public interface InformUserCallBack {
    void getAddress(String address);
    void notGetAddress();
    void bluetoothBreak();
    void bluetoothConnect();
    void receiveBluetoothData(String data, Context context);
    void connectBluetooth();
}
