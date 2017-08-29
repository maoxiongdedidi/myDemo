package com.example.ding.testble;

import android.content.Context;
import android.content.Intent;

import com.example.ding.testble.agreement.ReduceParse;


/**
 * Created by 丁赵来 on 2017/7/7.
 */

public class OldVersion {

    public static void operationData(Context mContext, byte[] allDataByte){
        byte pId = ReduceParse.getAgreementName(DataTreatingUtils.bytes2hex02(allDataByte));
        switch (pId){
            case (byte) 0xff:
                Intent ffIntent = new Intent(BroadCastMap.FFBackAction);
                ffIntent.putExtra("data",allDataByte);
                mContext.sendBroadcast(ffIntent);
                Intent intent = new Intent(BroadCastMap.LockIntermediateStateAction);
                intent.putExtra("data",allDataByte);
                mContext.sendBroadcast(intent);
                break;
            case (byte) 0xef:
                Intent efIntent = new Intent(BroadCastMap.CarInformationAction);
                efIntent.putExtra("data",allDataByte);
                mContext.sendBroadcast(efIntent);
                break;
        }
    }




}
