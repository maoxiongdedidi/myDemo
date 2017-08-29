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
import com.example.ding.testble.agreement.F1;
import com.example.ding.testble.agreement.FA;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by 丁赵来 on 2017/7/4.
 */

public class F1Holper {
    private int handleOfLock;
    private F1 f1;
    private Context mContext;
    private F1CallBack cb;
    public F1Holper(int handleOfLock, Context mContext,F1CallBack cb) {
        this.handleOfLock = handleOfLock;
        this.mContext=mContext;
        this.cb=cb;
        f1 = new F1();
        logic();
    }
    public  void logic(){
        F1Send(handleOfLock,3);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastMap.LockIntermediateStateAction);
        filter.setPriority(Integer.MAX_VALUE);
        try {
            mContext.unregisterReceiver(lockIntermediateStateReceiver);
            mContext.registerReceiver(lockIntermediateStateReceiver, filter);
        }catch (Exception e){
            mContext.registerReceiver(lockIntermediateStateReceiver, filter);
        }
      //  mHandler.postDelayed(mRunnable,100000);
    }
    private  int flag=0;
    private Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            flag=flag+1;
            if(flag>3){
               cb.result(false);
                unRegisterReceiver();
            }else {
                logic();
            }
        }
    };



    private BroadcastReceiver lockIntermediateStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mHandler.removeCallbacks(mRunnable);
            byte[] datas = intent.getByteArrayExtra("data");
            FA fa= new FA();
            fa.setByteBuffer(ByteBuffer.wrap(datas).order(ByteOrder.LITTLE_ENDIAN),0);
            String s = fa.messageType + "";
            String para = fa.ack_para + "";
            if(para.equals("1")){
                if(s.equals("5")){
                    F1Send(handleOfLock,6);
                }else if(s.equals("7")){
                    cb.result(true);
                    unRegisterReceiver();
                }
            }else{
                cb.result(false);
                unRegisterReceiver();
            }
        }
    };


    public void F1Send(int para,int msgType){
        f1.msgType.set((short) msgType);
        f1.para.set((short) para);
        //new byte[f1.size()];
        byte[] aaa=f1.toBytes();
        byte[] checkNum = DataTreatingUtils.jiaoyan(aaa);
        aaa[aaa.length-2]=checkNum[0];
        aaa[aaa.length-1]=checkNum[1];
        BleService.send(aaa);
        Log.e("=======开锁",DataTreatingUtils.bytes2hex02(aaa));
    }


    public  void unRegisterReceiver(){
        try {
            mContext.unregisterReceiver(lockIntermediateStateReceiver);
        }catch (Exception e){
        }
    }
    public interface F1CallBack{
        void result(boolean res);
    }



}
