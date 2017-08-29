package com.example.ding.testble.agreementholp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import com.example.ding.testble.BleService;
import com.example.ding.testble.BroadCastMap;
import com.example.ding.testble.DataTreatingUtils;
import com.example.ding.testble.agreement.FF;
import com.example.ding.testble.agreement.OneE;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by 丁赵来 on 2017/7/14.
 */

public class OneEHolper {
    private Context mContext;
    private OneEBackCallBack cb;
    private String data1;

    public OneEHolper(Context context, OneEBackCallBack cb,String data) {
        mContext = context;
        this.cb = cb;
        this.data1=data;
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
        OneE oneE = new OneE();
        oneE.VCU_SN.sn.set(data1);
        byte[] bytes = oneE.toBytes();
        byte[] jiaoyan = DataTreatingUtils.jiaoyan(bytes);
        bytes[bytes.length-2]=jiaoyan[0];
        bytes[bytes.length-1]=jiaoyan[1];


        BleService.send(bytes);
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
    public interface OneEBackCallBack{
        void isOK(Boolean result);
    }

}
