package com.example.ding.testble.agreementholp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;

import com.example.ding.testble.BleAndLockState;
import com.example.ding.testble.BleService;
import com.example.ding.testble.BroadCastMap;
import com.example.ding.testble.DataTreatingUtils;
import com.example.ding.testble.agreement.ED;
import com.example.ding.testble.agreement.EF;
import com.example.ding.testble.agreement.F3;
import com.example.ding.testble.agreement.FF;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

/**
 * Created by 丁赵来 on 2017/7/6.
 */

public class F3Holper {
    private Context mContext;
    private F3BackCallBack cb;
    private ArrayList mArrayList ;

    public F3Holper(Context context, F3BackCallBack cb, ArrayList arrayList) {
        mContext = context;
        this.cb = cb;
        mArrayList = arrayList;
        F3Send();
    }

    private  int flag=0;
    private Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            flag=flag+1;
            if(flag>3){
                cb.isOK(false);
                mContext.unregisterReceiver(ffBackAction);
            }else {
                F3Send();
            }
        }
    };

    public void F3Send(){
        F3 f3 = new F3();
        f3.lamp_info_t.hi_lamp.set(Short.valueOf(mArrayList.get(0)+""));
        f3.lamp_info_t.low_lamp.set(Short.valueOf(mArrayList.get(1)+""));
        f3.lamp_info_t.L_lamp.set(Short.valueOf(mArrayList.get(2)+""));
        f3.lamp_info_t.R_lamp.set(Short.valueOf(mArrayList.get(3)+""));
        f3.lamp_info_t.atmosphere_lamp.set(Short.valueOf(mArrayList.get(4)+""));
        f3.lamp_info_t.one_key_lamp.set(Short.valueOf(mArrayList.get(5)+""));
        f3.lamp_info_t.break_lamp.set(Short.valueOf(mArrayList.get(6)+""));
        f3.lamp_info_t.hi_key_lamp.set(Short.valueOf(mArrayList.get(7)+""));
        byte[] allData=  f3.toBytes();
        byte[] checkNum = DataTreatingUtils.jiaoyan(allData);
        allData[allData.length-2]=checkNum[0];
        allData[allData.length-1]=checkNum[1];
        BleService.send(allData);
        Log.e("=======开锁",DataTreatingUtils.bytes2hex02(allData));

        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastMap.FFBackAction);
        filter.setPriority(Integer.MAX_VALUE);
        try {
            mContext.unregisterReceiver(ffBackAction);
            mContext.registerReceiver(ffBackAction, filter);
        }catch (Exception e){
            mContext.registerReceiver(ffBackAction, filter);
        }
        mHandler.postDelayed(mRunnable,1500);



    }

    private BroadcastReceiver ffBackAction = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mHandler.removeCallbacks(mRunnable);
            byte[] allDataByte = intent.getByteArrayExtra("data");
            FF ff = new FF();
            ff.setByteBuffer(ByteBuffer.wrap(allDataByte).order(ByteOrder.LITTLE_ENDIAN),0);
            String s = ff.ack_para + "";
            if(s.equals("1")){
                cb.isOK(true);
            }else{
                cb.isOK(false);
            }



        }
    };


    public interface F3BackCallBack{
        void isOK(Boolean result);
    }




}
