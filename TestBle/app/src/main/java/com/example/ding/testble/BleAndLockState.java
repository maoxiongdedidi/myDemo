package com.example.ding.testble;

/**
 * Created by 丁赵来 on 2017/7/4.
 */

public class BleAndLockState {
    public static int bleState=0;//蓝牙连接状态   0,未连接；1连接
    public static int lockState=1;//锁的状态  1：防盗模式，2：开锁模式，3：驾驶。4.娱乐，5.升级
    public static int hi_lamp=0;//远光灯    0:关闭；1：打开
    public static int low_lamp=0;//近光灯
    public static int L_lamp=0;//左转向灯
    public static int R_lamp=0 ;//右转向灯
    public static int atmosphere_lamp= 0;//氛围灯
    public static int one_key_lamp= 0;//一键启动的
    public static int break_lamp= 0;//刹车灯
    public static int hi_key_lamp= 0;//H键灯
    public static int voice=0;//音量
    public static String BLEADRESS="";
    public static String BLENAME="";
    public static String bin_adress="";
    public static String mp3_adress="";
    public static int type=0;//0表示第一次升级，1表示上次升级的是固件，2表示上次升级的是音乐
}
