package com.example.ding.testble.agreementholp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.ding.testble.BleAndLockState;
import com.example.ding.testble.BroadCastMap;
import com.example.ding.testble.DataTreatingUtils;
import com.example.ding.testble.agreement.FC;
import com.example.ding.testble.agreement.ReduceParse;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by 丁赵来 on 2017/7/4.
 */

public class NewVerSion {
    public static void operationData(Context mContext,byte[] allDataByte){
        byte pId = ReduceParse.getAgreementName(DataTreatingUtils.bytes2hex02(allDataByte));
        switch (pId){
            case (byte) 0xfa:
                Intent intent = new Intent(BroadCastMap.LockIntermediateStateAction);
                intent.putExtra("data",allDataByte);
                mContext.sendBroadcast(intent);
                break;
            case (byte) 0xfc:
                FC fc = new FC();
                fc.setByteBuffer(ByteBuffer.wrap(allDataByte).order(ByteOrder.LITTLE_ENDIAN),0);
                String s = fc.ack_para + "";
                switch (s){
                    case "1":
                        BleAndLockState.lockState=1;
                        break;
                    case "2":
                        BleAndLockState.lockState=2;
                        break;
                    case "3":
                        BleAndLockState.lockState=3;
                        break;
                    case "4":
                        BleAndLockState.lockState=4;
                        break;
                    case "5":
                        BleAndLockState.lockState=5;
                        break;
                }
                Intent intent1 = new Intent(BroadCastMap.LockStateAction);
                mContext.sendBroadcast(intent1);
                break;
            case (byte) 0xef:
                Intent efIntent = new Intent(BroadCastMap.CarInformationAction);
                efIntent.putExtra("data",allDataByte);
                mContext.sendBroadcast(efIntent);
                break;
            case (byte) 0xff:
                Intent ffIntent = new Intent(BroadCastMap.FFBackAction);
                ffIntent.putExtra("data",allDataByte);
                mContext.sendBroadcast(ffIntent);
                break;
        }
    }


}
