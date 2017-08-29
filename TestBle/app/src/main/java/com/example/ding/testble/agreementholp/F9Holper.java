package com.example.ding.testble.agreementholp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;

import com.example.ding.testble.BleService;
import com.example.ding.testble.BroadCastMap;
import com.example.ding.testble.DataTreatingUtils;
import com.example.ding.testble.agreement.F9;
import com.example.ding.testble.agreement.FF;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by 丁赵来 on 2017/7/14.
 */

public class F9Holper {
    private Context mContext;
    private F9BackCallBack cb;
    private int cmd;
    private int para1;
    private int para2;
    private int para3;
    private int para4;

    public F9Holper(Context context, F9BackCallBack cb, int cmd, int para1, int para2, int para3, int para4) {
        mContext = context;
        this.cb = cb;
        this.cmd = cmd;
        this.para1 = para1;
        this.para2 = para2;
        this.para3 = para3;
        this.para4 = para4;
        F9Send();
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
                F9Send();
            }
        }
    };

    private void F9Send() {
        F9 f9= new F9();
        f9.cmd.set(cmd);
        f9.para1.set(para1);
        f9.para2.set(para2);
        f9.para3.set(para3);
        f9.para4.set(para4);
        byte[] allData =f9.toBytes();
        byte[] checkNum = DataTreatingUtils.jiaoyan(allData);
        allData[allData.length-2]=checkNum[0];
        allData[allData.length-1]=checkNum[1];

        Log.e("=====", DataTreatingUtils.bytes2hex02(allData));
        BleService.send(allData);
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
    public interface F9BackCallBack{
        void isOK(Boolean result);
    }

}
