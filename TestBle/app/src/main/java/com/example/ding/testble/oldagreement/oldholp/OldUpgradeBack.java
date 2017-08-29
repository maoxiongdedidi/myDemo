package com.example.ding.testble.oldagreement.oldholp;

import android.content.Context;
import android.content.Intent;

import com.example.ding.testble.BroadCastMap;

/**
 * Created by 丁赵来 on 2017/7/8.
 */

public class OldUpgradeBack {
    public static void getbackData(Context mContext, byte[] backData){
        Intent intent = new Intent(BroadCastMap.OldUpgradeBackAction);
        intent.putExtra("data",backData);
        mContext.sendBroadcast(intent);
    }
}
