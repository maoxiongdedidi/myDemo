package com.example.ding.testble;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ding.testble.agreement.ReduceParse;
import com.example.ding.testble.agreementholp.NewVerSion;
import com.example.ding.testble.agreementholp.UpgradeBack;
import com.example.ding.testble.ble.BluetoothInterface;
import com.example.ding.testble.oldagreement.oldholp.OldUpgradeBack;

import java.util.Arrays;

/**
 * Created by 丁赵来 on 2017/7/4.
 */

public class BleService extends Service implements InformUserCallBack{
    public class MyBinder extends Binder {

        public BleService getService1(){
            return BleService.this;
        }
    }
    public static BluetoothInterface mInterface;
    Handler mHandler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(mInterface!=null){
                if (mInterface.getIsConnect()) {
                    mInterface.scanLeDevice(false);//开始蓝牙搜索
                } else {
                    //  mInstance.openBluetooth();
                    mInterface.scanLeDevice(true);
                }
            }

        }
    };
    private MyBinder myBinder = new MyBinder();

    @Override
    public void onCreate() {
        mInterface = BluetoothInterface.getInstance(MyApplication.sMainActivity, this);
        mInterface.openBluetooth();
        if(!mInterface.getIsConnect()){
            mHandler1.postDelayed(mRunnable, 3000);
        }else{
            mInterface.scanLeDevice(false);
        }
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mInterface!=null&&mInterface.getIsConnect()){
            mInterface.disconnect();
            mInterface=null;
        }
    }

    @Override
    public void getAddress(String address) {

    }

    @Override
    public void notGetAddress() {
        if(mInterface!=null){
            mHandler1.postDelayed(mRunnable, 5000);
        }

        BleAndLockState.bleState=0;
        Intent intent = new Intent(BroadCastMap.BleStateAction);
        sendBroadcast(intent);
    }

    @Override
    public void bluetoothBreak() {
        if(mInterface!=null){
            mHandler1.postDelayed(mRunnable, 1000);
        }
        BleAndLockState.bleState=0;
        Intent intent = new Intent(BroadCastMap.BleStateAction);
        sendBroadcast(intent);
    }

    @Override
    public void bluetoothConnect() {
        BleAndLockState.bleState=1;
        Intent intent = new Intent(BroadCastMap.BleStateAction);
        sendBroadcast(intent);
    }

    @Override
    public void receiveBluetoothData(String data, Context context) {
        byte[] hexBytes = DataTreatingUtils.getHexBytes(DataTreatingUtils.replaceBlank(data));
        Log.e("======BleService", data);
        for (int i = 0; i < hexBytes.length; i++) {
            ReduceParse.reduce(hexBytes[i], hexBytes.length, new ReduceParse.RedudeParseCallBack() {
                @Override
                public void ok(byte[] allData) {
                    int verson = ReduceParse.verson(allData);//0，老版本的命令；1，老版本的升级；2，新版本的命令；3，新版本的升级
                    //根据版本开始进入新老协议
                    if(verson==2){
                        NewVerSion.operationData(BleService.this,allData);
                    }else if(verson==3){
                        UpgradeBack.getbackData(BleService.this,allData);
                    }else if(verson==0){
                        OldVersion.operationData(BleService.this,allData);
                    }else if(verson==1){
                        OldUpgradeBack.getbackData(BleService.this,allData);
                    }
                }

                @Override
                public void failed() {

                }
            });
        }
    }

    @Override
    public void connectBluetooth() {

    }
    public static boolean isConnect(){
        if(mInterface.getIsConnect()){
            return true;
        }else {
            return false;
        }
    }
    //发送方法
    public static void send(byte[] a){
        if(mInterface.getIsConnect()){
            mInterface.sendInstruct(a);
        }else{
            ToastUtil.showShortToast("蓝牙未连接");
        }
    }
    public static void disConnect(){
        if(mInterface!=null){
            mInterface.disconnect();
        }
    }

    //发送方法
    public static void sendOrder(String a){
        if(mInterface.getIsConnect()){
            mInterface.send(a);
        }else{
            ToastUtil.showShortToast("蓝牙未连接");
        }
    }




}
