package com.example.ding.testble;

/**
 * Created by 丁赵来 on 2017/7/4.
 */

public class BroadCastMap {
    public static final String BleStateAction = "com.doohan.capacity.blestate";//蓝牙状态
    public static final String LockIntermediateStateAction = "com.doohan.capacity.lockintermediatestate";//改变锁状态时中间的操作
    public static final String LockChangeStateAction="com.doohan.capacity.lockchangestate";//所得状态发生变化
    public static final String LockStateAction="com.doohan.capacity.lockultimatelystate";//锁的最终状态
    public static final String UpgradeBackAction="com.doohan.capacity.upgradebace";//监听收到升级的返回
    public static final String CarInformationAction="com.doohan.capacity.carinformation";//请求车辆信息的广播
    public static final String FFBackAction="com.doohan.capacity.ffback";//所有ff协议的返回广播
    public static final String OldUpgradeBackAction="com.doohan.capacity.oldupgradebace";//监听收到升级的返回
}
