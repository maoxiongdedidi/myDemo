package com.example.ding.testble.agreementholp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import com.example.ding.testble.BleService;
import com.example.ding.testble.BroadCastMap;
import com.example.ding.testble.DataTreatingUtils;
import com.example.ding.testble.agreement.F8;
import com.example.ding.testble.agreement.FF;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Calendar;

/**
 * Created by 丁赵来 on 2017/7/7.
 */

public class  F8Holper {
    private Context mContext;
    private F8BackCallBack cb;

    public F8Holper(Context context, F8BackCallBack cb) {
        mContext = context;
        this.cb = cb;
        F8Send();
    }

    private void F8Send() {
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);//获取年份
        short month=Short.valueOf((cal.get(Calendar.MONTH))+"") ;//获取月份
        short day=Short.valueOf(cal.get(Calendar.DATE)+"");//获取日
        short hour=Short.valueOf(cal.get(Calendar.HOUR_OF_DAY)+"");//小时
        short minute=Short.valueOf(cal.get(Calendar.MINUTE)+"");//分
        short second=Short.valueOf(cal.get(Calendar.SECOND)+"");//秒
        F8 f8= new F8();
        f8.time.tm_year.set(year);
        f8.time.tm_mon.set((short) (month+1));
        f8.time.tm_day.set(day);
        f8.time.tm_hour.set(hour);
        f8.time.tm_min.set(minute);
        f8.time.tm_sec.set(second);
        byte[] allData=  f8 .toBytes();
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
            mContext.unregisterReceiver(ffBackAction);


        }
    };
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
                F8Send();
            }
        }
    };

    public interface F8BackCallBack{
        void isOK(Boolean result);
    }











}
