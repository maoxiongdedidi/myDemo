package com.example.ding.testble.agreementholp;

import android.content.Context;
import android.content.Intent;

import com.example.ding.testble.BroadCastMap;

/**
 * Created by 丁赵来 on 2017/7/5.
 */

public class UpgradeBack {
    public static void getbackData(Context mContext, byte[] backData){
        Intent intent = new Intent(BroadCastMap.UpgradeBackAction);
        intent.putExtra("data",backData);
        mContext.sendBroadcast(intent);
    }


}
