package com.example.ding.testble.agreementholp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import com.example.ding.testble.BleService;
import com.example.ding.testble.BroadCastMap;
import com.example.ding.testble.DataTreatingUtils;
import com.example.ding.testble.agreement.F2;
import com.example.ding.testble.agreement.FF;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

/**
 * Created by 丁赵来 on 2017/7/8.
 */

public class F2Holper {
    private Context mContext;
    private F2BackCallBack cb;
    private int voice;

    public F2Holper(Context context, F2BackCallBack cb,int voice) {
        mContext = context;
        this.cb = cb;
       this.voice=voice;
        F2Send();
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
                F2Send();
            }
        }
    };

    private void F2Send() {
        F2 f2 = new F2();
        f2.para.set((short) voice);
        byte[] allData = f2.toBytes();
        byte[] checkNum = DataTreatingUtils.jiaoyan(allData);
        allData[allData.length-2]=checkNum[0];
        allData[allData.length-1]=checkNum[1];
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
    public interface F2BackCallBack{
        void isOK(Boolean result);
    }

}
