package com.example.ding.testble;



import com.example.ding.testble.ble.SampleGattAttributes;

import java.util.UUID;

/**
 * Created by ding on 2016/11/1.
 */
public class Constant {
    public static  String username;
    public static String password;
    public static int VCUVERSION=1;
    public static final String LOGIN="http://webadmin.doohan.com.cn/admin/adminapp/check.json";
    /**
     * 所有固定值
     */
    public static final Integer OPENREQUESTCODE = 1000;
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_CONNECTED = 2;
    public final static String ACTION_GATT_CONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.example.bluetooth.le.EXTRA_DATA";
    public static final UUID SERVIE_UUID = UUID
            .fromString("0000FFe5-0000-1000-8000-00805f9b34fb");//设置发送数据的uuid
    public static final UUID RED_LIGHT_CONTROL_UUID = UUID
            .fromString("0000FFF4-0000-1000-8000-00805f9b34fb");//设置发送数据服务特征值的uuid
    public static final UUID
            RED_LIGHT_CONTROL_UUID_TWO = UUID
            .fromString("0000FFe9-0000-1000-8000-00805f9b34fb");//设置发送数据服务特征值的uuid
    public final static UUID UUID_HEART_RATE_MEASUREMENT =
            UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);
}
