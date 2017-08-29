package com.example.ding.testble.ble;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;


import com.example.ding.testble.BleAndLockState;
import com.example.ding.testble.Constant;
import com.example.ding.testble.DataTreatingUtils;
import com.example.ding.testble.InformUserCallBack;
import com.example.ding.testble.MainActivity;
import com.example.ding.testble.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javolution.io.Struct;


/**
 * Created by ding on 2017/1/20.
 */
public class BluetoothInterface {


    private static BluetoothInterface instance = null;
    private Activity mActivity;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;//是否正在搜索
    private boolean mIsOpenSuccess;
    private Handler mHandler;
    private String devicesAddress;
    private InformUserCallBack ascb;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";
    private BluetoothLeService mBluetoothLeService;
    private boolean isConnect;
    // 服务启动监听，服务启动成功之后开启蓝牙连接
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName,
                                       IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service)
                    .getService();
            if (!mBluetoothLeService.initialize()) {
                //蓝牙服务初始化没有成功关闭页面
                mActivity.finish();
            }
            mBluetoothLeService.connect(devicesAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    /**
     * 监听蓝牙状态广播
     */
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (Constant.ACTION_GATT_CONNECTED.equals(action)) {

                //连接服务器时调用
                ascb.connectBluetooth();
            } else if (Constant.ACTION_GATT_DISCONNECTED
                    .equals(action)) {
                //与服务器断开连接时调用
                mBluetoothLeService.close();
                ascb.bluetoothBreak();
            } else if (Constant.ACTION_GATT_SERVICES_DISCOVERED
                    .equals(action)) {
                //发现服务器时调用
                mGattCharacteristics = displayGattServices(mBluetoothLeService
                        .getSupportedGattServices());
                //开始设置上传和监听返回监听的特征值
               // setServiceCharacteristic();
                ascb.bluetoothConnect();
            } else if (Constant.ACTION_DATA_AVAILABLE.equals(action)) {
                //设备就收到服务器的数据时调用
                 ascb.receiveBluetoothData(intent
                        .getStringExtra(Constant.EXTRA_DATA), mActivity);
            }
        }
    };

    public String getDevicesAddress() {
        return devicesAddress;
    }

    public void setDevicesAddress(String devicesAddress) {
        this.devicesAddress = devicesAddress;
    }

    public String devicesName;
    public String bleAdress;
    private ArrayList<BluetoothDevice> mLeDevices = new ArrayList<>();//存放搜索到的蓝牙（次方法时候用到暂时未知）

    private BluetoothInterface() {
        mHandler = new Handler();
    }

    public static BluetoothInterface getInstance(Activity context, InformUserCallBack ascb) {
        if (instance == null)
            instance = new BluetoothInterface();
        instance.mActivity = context;

        instance.ascb = ascb;

       /* SharedPreferences ble = context.getSharedPreferences("ble", Context.MODE_PRIVATE);
        if (ble.getString("vcuNo", "").length() != 0 && ble.getString("bluetoothAddress", "").length() != 0) {
            instance.devicesName = ble.getString("vcuNo", "");
            instance.bleAdress =ble.getString("bluetoothAddress", "");
        } else {
        }*/
        instance.bleAdress =  BleAndLockState.BLEADRESS;
        instance.devicesName =BleAndLockState.BLENAME;
        return instance;
    }

    /**
     * 开启蓝牙
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void openBluetooth() {
        // 检查当前手机是否支持ble 蓝牙,如果不支持退出程序
        if (!mActivity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtil.showShortToast("手机不支持ble蓝牙");

        }

        // 初始化 Bluetooth adapter, 通过蓝牙管理器得到一个参考蓝牙适配器(API必须在以上android4.3或以上和版本)
        final BluetoothManager bluetoothManager =
                (BluetoothManager) mActivity.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // 检查设备上是否支持蓝牙
        if (mBluetoothAdapter == null) {
            ToastUtil.showShortToast("手机不支持蓝牙");
        }
        // 为了确保设备上蓝牙能使用, 如果当前蓝牙设备没启用,弹出对话框向用户要求授予权限来启用
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                mActivity.startActivityForResult(enableBtIntent, Constant.OPENREQUESTCODE);
            }
        }
    }

    /**
     * 搜索蓝牙设备
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void scanLeDevice(final boolean enable) {
        if (enable)

        new Thread(mScanBleRunnble).start();
        else

        new Thread(mStopScanBleRunnble).start();
    }

    Runnable mScanBleRunnble = new Runnable() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void run() {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(mRunnable, 5000);//最后一个参数是设置搜索时间，单位是毫秒
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);

        }
    };



    Runnable mStopScanBleRunnble = new Runnable() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void run() {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    };


    Runnable mRunnable = new Runnable() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void run() {
            mScanning = false;
           // mBluetoothAdapter.stopLeScan(mLeScanCallback);
            new Thread(mStopScanBleRunnble).start();
            //十秒后会调用此方法，说明没有匹配到需要的蓝牙设备，需要告诉用户连接未找到
            ascb.notGetAddress();
        }
    };

    /**
     * 蓝牙设备搜索回调
     */
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            // if(!mLeDevices.contains(device)) {
            mLeDevices.add(device);
            //问题在这里
            String name = device.getName();
            if (name != null) {
                devicesAddress = device.getAddress();
                Log.e("++++devicesAddress", devicesAddress);
                 //现在蓝牙模块的物理地址 80:30:DC:D6:4F:52
                if (devicesAddress.equals(BleAndLockState.BLEADRESS)) {//使用这个保证升级成功之后，只要修改此字段，就不会出现自动连接到已经升级的蓝牙模块上
                    ascb.getAddress(devicesAddress);
                    //获取地址之后启动蓝牙的服务和广播并连接
                    Intent gattServiceIntent = new Intent(mActivity, BluetoothLeService.class);
                    mActivity.getApplicationContext().bindService(gattServiceIntent, mServiceConnection, mActivity.BIND_AUTO_CREATE);
                    mActivity.getApplicationContext().registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
                    if (mBluetoothLeService != null) {
                        mBluetoothLeService.connect(BleAndLockState.BLEADRESS);
                    }
                    mScanning = false;
                  //  mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    new Thread(mStopScanBleRunnble).start();
                    mHandler.removeCallbacks(mRunnable);
                }
            }
        }
    };





    /**
     * 主动断开蓝牙
     */
    public void disconnect() {
        if (mBluetoothLeService != null) {
            mBluetoothLeService.disconnect();

        }
    }

    public boolean getIsConnect() {
        if (mBluetoothLeService == null) {
            return false;
        }
        return mBluetoothLeService.isconnect();
    }
    /**
     * 判断蓝牙是否打开
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public boolean isBleOpen() {
        final BluetoothManager bluetoothManager =
                (BluetoothManager) mActivity.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter != null) {
            return mBluetoothAdapter.isEnabled();
        }
        return false;
    }
    /**
     * 开启的广播
     */
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_GATT_CONNECTED);
        intentFilter.addAction(Constant.ACTION_GATT_DISCONNECTED);
        intentFilter
                .addAction(Constant.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(Constant.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }
    /**
     * 把连接的蓝牙设备所有可用的特征值返回
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public ArrayList<ArrayList<BluetoothGattCharacteristic>> displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null)
            return null;
        String uuid = null;
        String unknownServiceString = "未知的服务";
        String unknownCharaString = "未知的特征值";
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
        // 循环有效的服务协议
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(LIST_NAME,
                    SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);
            ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics = gattService
                    .getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();
            // 循环有效的特征值
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(LIST_NAME,
                        SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }
        return mGattCharacteristics;
    }

    /**
     * 设置蓝牙特征值
     * 也可以通过mBluetoothGatt.getService(UUID.fromString("49535343-fe7d-4ae5-8fa9-9fafd205e455"))；获取服务对象
     * 然后获取此服务结点下的某个Characteristic对象BluetoothGattCharacteristic alertLevel =linkLossService.getCharacteristic(UUID.fromString(“49535343-8841-43f4-a8d4-ecbe34729bb3”));
     */
    public void setServiceCharacteristic() {
        final BluetoothGattCharacteristic characteristic = mGattCharacteristics
                .get(2).get(0);
        mBluetoothLeService.setCharacteristicNotification(
                characteristic, true);
        mBluetoothLeService.readCharacteristic(characteristic);//设置读取服务协议特征值
        final BluetoothGattCharacteristic characteristic2 = mGattCharacteristics
                .get(2).get(0);
        mBluetoothLeService.setCharacteristicNotification(
                characteristic2, true);
    }
    /**
     * 发送命令数据
     */
    public void send(String s) {
        mBluetoothLeService.writeLlsAlertLevel(2, DataTreatingUtils.getHexBytes(s));
    }

    /**
     * 发送指令
     */
    public void sendInstruct(byte[] s) {
        byte[] buf = new byte[20];
        int a, b;
        a = s.length / 20;
        b = s.length - a * 20;
        for (int i = 0; i < a; i++) {
            System.arraycopy(s, i * 20, buf, 0, 20);
            Log.e("---buf", Arrays.toString(buf));
            mBluetoothLeService.writeLlsAlertLevel(2, buf);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (b > 0) {
            byte[] end = new byte[b];
            System.arraycopy(s, a * 20, end, 0, b);
            Log.e("---end", Arrays.toString(end));
            mBluetoothLeService.writeLlsAlertLevel(2, end);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 消息发送
     */
    public void sendData(int m, byte[] s) {
        byte[] allcontent = allcontent(m, s);
        byte[] buf = new byte[20];
        int a, b;
        a = allcontent.length / 20;
        b = allcontent.length - a * 20;
        for (int i = 0; i < a; i++) {
            System.arraycopy(allcontent, i * 20, buf, 0, 20);
            Log.e("---buf", Arrays.toString(buf));
            mBluetoothLeService.writeLlsAlertLevel(2, buf);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (b > 0) {
            byte[] end = new byte[b];
            System.arraycopy(allcontent, a * 20, end, 0, b);
            Log.e("---end", Arrays.toString(end));
            mBluetoothLeService.writeLlsAlertLevel(2, end);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 数据处理
     */
    public byte[] allcontent(int m, byte[] s) {
        byte[] content = s;
        //byte[] content=getHexBytes("01");
        byte[] head = DataTreatingUtils.getHexBytes("FFCC00");

        byte[] order = DataTreatingUtils.getHexBytes("0102");
        Test t8 = new Test();
        t8.name.set((short) (content.length + 4));
        byte[] sum = DataTreatingUtils.getHexBytes(t8.toString());
        TestHax t16 = new TestHax();
        t16.name.set(m);
        byte[] serial = DataTreatingUtils.getHexBytes(DataTreatingUtils.replaceBlank(t16.toString()));
        byte[] bytes = DataTreatingUtils.byteMerger(DataTreatingUtils.byteMerger(head, sum), order);
        byte[] bytes1 = DataTreatingUtils.byteMerger(DataTreatingUtils.byteMerger(bytes, serial), content);
        byte[] Checksum = DataTreatingUtils.jiaoyan(bytes1);
        byte[] allbyte = DataTreatingUtils.byteMerger(bytes1, Checksum);
        return allbyte;
    }


    /**
     * 转换成16进制两字节
     */
    public class TestHax extends Struct {

        public boolean isPacked() {
            System.out.println("");
            return true; // MyStruct is packed.
        }

        public final Unsigned16 name = new Unsigned16();
    }

    /**
     * 转换成16进制一字节
     */
    public class Test extends Struct {

        public boolean isPacked() {
            System.out.println("");
            return true; // MyStruct is packed.
        }

        public final Unsigned8 name = new Unsigned8();
    }



/*
    public static void getAdress(final Context context){
        final HashMap<String ,String > bleMap = new HashMap<>();
        MyOkHttp instance = MyOkHttp.getInstance();
        LoginJson loginJson = instance.getusermessage(context);
        LoginJson.DataBean data = loginJson.getData();
        String sessionid = data.getSessionid();
        int userId = data.getUserId();
        HashMap<String, String> map = new HashMap<>();
        map.put("sessionid", sessionid);
        map.put("userId", userId + "");
        instance.getStringJson(Constant.GETBLUETOOTHADDRESS, map, context, new HttpRuselt() {
            @Override
            public void setString(String s) {
                Gson gson = new Gson();
                BleAddressJson bleAddressJson = gson.fromJson(s, BleAddressJson.class);
                if(bleAddressJson.isSuccess()){


                    bleAdress = DataTreatingUtils.bleTreating(bleAddressJson.getData().getBluetoothAddress());
                    devicesName = bleAddressJson.getData().getVcuNo();

                }
            }

            @Override
            public void seterr(Exception e) {
                e.printStackTrace();
            }
        });

    }*/


}
