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
import com.example.ding.testble.Constant;
import com.example.ding.testble.DataTreatingUtils;
import com.example.ding.testble.agreement.ED;
import com.example.ding.testble.agreement.EF;
import com.example.ding.testble.agreement.OldED;
import com.example.ding.testble.agreement.OldEF;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by 丁赵来 on 2017/7/5.
 */

public class E0Holper {
    private Context mContext;
    private  EFBackCallBack cb;
    private  int flag=0;
    private Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            flag=flag+1;
            if(flag>3){
                cb.isOK(false);
                mContext.unregisterReceiver(carInformationAction);
            }else {
                E0Send();
            }
        }
    };
    public E0Holper(Context context, EFBackCallBack cb) {
        mContext = context;
        this.cb = cb;
        E0Send();
    }
    public void E0Send(){
        if(Constant.VCUVERSION==2){
            ED ed = new ED();
            byte[] allData=  ed.toBytes();
            byte[] checkNum = DataTreatingUtils.jiaoyan(allData);
            allData[allData.length-2]=checkNum[0];
            allData[allData.length-1]=checkNum[1];
            BleService.send(allData);
            Log.e("=======开锁",DataTreatingUtils.bytes2hex02(allData));
        }else if(Constant.VCUVERSION==1){
            OldED ed = new OldED();
            byte[] bytes = new byte[ed.size()];
            ed.getByteBuffer().get(bytes);
            byte[] jiaoyan = DataTreatingUtils.jiaoyan(bytes);
            bytes[bytes.length - 2] = jiaoyan[0];
            bytes[bytes.length - 1] = jiaoyan[1];
            BleService.send(bytes);
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastMap.CarInformationAction);
        filter.setPriority(Integer.MAX_VALUE);
        try {
            mContext.unregisterReceiver(carInformationAction);
            mContext.registerReceiver(carInformationAction, filter);
        }catch (Exception e){
            mContext.registerReceiver(carInformationAction, filter);
        }
        mHandler.postDelayed(mRunnable,1500);
    }

    private BroadcastReceiver carInformationAction = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mHandler.removeCallbacks(mRunnable);
            byte[] allDataByte = intent.getByteArrayExtra("data");
           if(Constant.VCUVERSION==2){
               EF ef = new EF();
               ef.setByteBuffer(ByteBuffer.wrap(allDataByte).order(ByteOrder.LITTLE_ENDIAN),0);
               BleAndLockState.lockState = Integer.valueOf(ef.mode + "");
               BleAndLockState.atmosphere_lamp = Integer.valueOf(ef.lamp_info_t.atmosphere_lamp + "");//氛围灯
               BleAndLockState.hi_lamp = Integer.valueOf(ef.lamp_info_t.hi_lamp + "");//远光灯
               BleAndLockState.low_lamp = Integer.valueOf(ef.lamp_info_t.low_lamp + "");//近光灯
               BleAndLockState.L_lamp = Integer.valueOf(ef.lamp_info_t.L_lamp + "");//左转向灯
               BleAndLockState.R_lamp = Integer.valueOf(ef.lamp_info_t.R_lamp + "");//右转向灯
               BleAndLockState.break_lamp = Integer.valueOf(ef.lamp_info_t.break_lamp + "");//刹车灯
               BleAndLockState.voice= Integer.valueOf(ef.voice+"");
               //ef回复之后通知一下锁的状态，
               Intent intent1 = new Intent(BroadCastMap.LockStateAction);
               mContext.sendBroadcast(intent1);
               cb.isOK(true);
               mContext.unregisterReceiver(carInformationAction);
           }else if(Constant.VCUVERSION==1){
               OldEF oldEF = new OldEF();
               oldEF.setByteBuffer(ByteBuffer.wrap(allDataByte),0);
               BleAndLockState.lockState = Integer.valueOf(oldEF.mode + "");
               BleAndLockState.atmosphere_lamp = Integer.valueOf(oldEF.lamp_info_t.atmosphere_lamp + "");//氛围灯
               BleAndLockState.hi_lamp = Integer.valueOf(oldEF.lamp_info_t.hi_lamp + "");//远光灯
               BleAndLockState.low_lamp = Integer.valueOf(oldEF.lamp_info_t.low_lamp + "");//近光灯
               BleAndLockState.L_lamp = Integer.valueOf(oldEF.lamp_info_t.L_lamp + "");//左转向灯
               BleAndLockState.R_lamp = Integer.valueOf(oldEF.lamp_info_t.R_lamp + "");//右转向灯
               BleAndLockState.break_lamp = Integer.valueOf(oldEF.lamp_info_t.break_lamp + "");//刹车灯
               BleAndLockState.voice= Integer.valueOf(oldEF.voice+"");
               Integer integer = Integer.valueOf(oldEF.speedctl_info_t.h_speed + "");
               if(integer>500){
                   Constant.VCUVERSION=2;
               }else if(integer<500){
                   Constant.VCUVERSION=1;
               }
               //ef回复之后通知一下锁的状态，
               Intent intent1 = new Intent(BroadCastMap.LockStateAction);
               mContext.sendBroadcast(intent1);
               cb.isOK(true);
               mContext.unregisterReceiver(carInformationAction);
           }
        }
    };
    public  interface EFBackCallBack{
        void isOK(Boolean result);
    }

}
