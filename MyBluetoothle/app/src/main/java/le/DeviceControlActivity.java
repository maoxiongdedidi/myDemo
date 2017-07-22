/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package le;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.doohan.mybluetoothle.FileUtils;
import com.doohan.mybluetoothle.MpFile;
import com.doohan.mybluetoothle.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javolution.io.Struct;

/**
 * For a given BLE device, this Activity provides the user interface to connect,
 * display data, and display GATT services and characteristics supported by the
 * device. The Activity communicates with {@code BluetoothLeService}, which in
 * turn interacts with the Bluetooth LE API.
 */
public class DeviceControlActivity extends Activity {
	private final static String TAG = DeviceControlActivity.class
			.getSimpleName();

	public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
	public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

	private TextView mConnectionState;
	private TextView mDataField;
	private String mDeviceName;
	private String mDeviceAddress;

	private Button button_send_value; // 数据发送按钮
//	private EditText edittext_input_value; // 输入发送的数据
	private TextView textview_return_result; // 返回结果按钮
	private ExpandableListView mGattServicesList;
	private BluetoothLeService mBluetoothLeService;
	private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
	private boolean mConnected = false;
	private BluetoothGattCharacteristic mNotifyCharacteristic;
	private final String LIST_NAME = "NAME";
	private final String LIST_UUID = "UUID";
	private HashMap<Integer,byte[]> map = new HashMap<>();
	private int serialNumber =0;  //表示应该选入的字符串所对应的key值
	private int count=0;	//失败次数
	private int stopcount=0; //文件传输成功的次数
	Handler mHandler = new Handler();
	// 服务启动监听，服务启动成功之后开启蓝牙连接
	private final ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName componentName,
				IBinder service) {
			mBluetoothLeService = ((BluetoothLeService.LocalBinder) service)
					.getService();
			if (!mBluetoothLeService.initialize()) {
				Log.e(TAG, "Unable to initialize Bluetooth");
				finish();
			}
			// Automatically connects to the device upon successful start-up
			// initialization.
			mBluetoothLeService.connect(mDeviceAddress);
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			mBluetoothLeService = null;
		}
	};


	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
				//连接服务器时调用
				mConnected = true;
				updateConnectionState(R.string.connected);
				invalidateOptionsMenu();
			} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED
					.equals(action)) {
				//与服务器断开连接时调用
				mConnected = false;
				updateConnectionState(R.string.disconnected);
				invalidateOptionsMenu();
				clearUI();
			} else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED
					.equals(action)) {
				//发现服务器时调用
				displayGattServices(mBluetoothLeService
						.getSupportedGattServices());
			} else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
				//设备就收到服务器的数据时调用
				displayData(intent
						.getStringExtra(BluetoothLeService.EXTRA_DATA));
			}
		}
	};

	// If a given GATT characteristic is selected, check for supported features.
	// This sample
	// demonstrates 'Read' and 'Notify' features. See
	// http://d.android.com/reference/android/bluetooth/BluetoothGatt.html for
	// the complete
	// list of supported characteristic features.
	private final ExpandableListView.OnChildClickListener servicesListClickListner = new ExpandableListView.OnChildClickListener() {
		@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			if (mGattCharacteristics != null) {
				final BluetoothGattCharacteristic characteristic = mGattCharacteristics
						.get(groupPosition).get(childPosition);
				Log.i("选项", groupPosition + "---" + childPosition);
				final int charaProp = characteristic.getProperties();
				if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
					// If there is an active notification on a characteristic,
					// clear
					// it first so it doesn't update the data field on the user
					// interface.
					if (mNotifyCharacteristic != null) {
						mBluetoothLeService.setCharacteristicNotification(
								mNotifyCharacteristic, false);
						mNotifyCharacteristic = null;
					}
					mBluetoothLeService.readCharacteristic(characteristic);
				}
				if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
					mNotifyCharacteristic = characteristic;
					mBluetoothLeService.setCharacteristicNotification(
							characteristic, true);
				}
				return true;
			}
			return false;
		}
	};
	private Button mNext;
	private Button mThisButton;
	private Button button_mp_calue;
	private int mp3OrFirmware=1;

	private void clearUI() {
		mGattServicesList.setAdapter((SimpleExpandableListAdapter) null);

		System.out.println("N0_Data @@@@@@@@@@@@"+R.string.no_data);
		mDataField.setText(R.string.no_data);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gatt_services_characteristics);
		makeActionOverflowMenuShown();
		final Intent intent = getIntent();
		mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
		mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
		Log.e("-----",mDeviceAddress);
		// Sets up UI references.
		((TextView) findViewById(R.id.device_address)).setText(mDeviceAddress);
		mGattServicesList = (ExpandableListView) findViewById(R.id.gatt_services_list);
		mGattServicesList.setOnChildClickListener(servicesListClickListner);
		mConnectionState = (TextView) findViewById(R.id.connection_state);
		mDataField = (TextView) findViewById(R.id.data_value);
		button_send_value = (Button) findViewById(R.id.button_send_value);
		textview_return_result = (TextView) findViewById(R.id.textview_return_result);
		button_mp_calue = (Button)findViewById(R.id.button_mp_value);
		button_send_value.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mp3OrFirmware=1;
				button_mp_calue.setEnabled(false);

				try {
					map.clear();
					FileUtils fileUtils = new FileUtils(DeviceControlActivity.this);
					byte[] s = fileUtils.readFile();
					map= fileUtils.initData(s);

				} catch (IOException e) {
					e.printStackTrace();
				}
				byte[] bb =getHexBytes("FFCC00040101010101D3");//创建文件命令
				mBluetoothLeService.writeLlsAlertLevel(2, bb);

			}
		});
		button_mp_calue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mp3OrFirmware=2;
				button_send_value.setEnabled(false);
				try {
					map.clear();
					MpFile fileUtils = new MpFile(DeviceControlActivity.this);
					byte[] s = fileUtils.readFile();
					map= fileUtils.initData(s);

				} catch (IOException e) {
					e.printStackTrace();
				}
				byte[] bb =getHexBytes("FFCC00040101010301D5");//创建文件命令
				mBluetoothLeService.writeLlsAlertLevel(2, bb);
			}
		});

		Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
		bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

		initData();

	}

	private void initData() {


	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
		if (mBluetoothLeService != null) {
			final boolean result = mBluetoothLeService.connect(mDeviceAddress);
			Log.d(TAG, "Connect request result=" + result);
		}
		TestHax t= new TestHax();
		t.name.set(1);
	}

   public void MyonClick(View view){
     switch (view.getId()) {
     //开始上传文件
	case R.id.button1:
		if (mBluetoothLeService != null) {
			final boolean result = mBluetoothLeService.connect(mDeviceAddress);
			Log.d(TAG, "Connect request result=" + result);
		}
		break;
	//停止测量
   case R.id.button2:
	 mBluetoothLeService.disconnect();
		break;

	}
   }
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mGattUpdateReceiver);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(mServiceConnection);
		mBluetoothLeService = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.gatt_services, menu);
		if (mConnected) {
			menu.findItem(R.id.menu_connect).setVisible(false);
			menu.findItem(R.id.menu_disconnect).setVisible(true);
		} else {
			menu.findItem(R.id.menu_connect).setVisible(true);
			menu.findItem(R.id.menu_disconnect).setVisible(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_connect:
			mBluetoothLeService.connect(mDeviceAddress);
			return true;
		case R.id.menu_disconnect:
			mBluetoothLeService.disconnect();
			return true;
		case android.R.id.home:
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateConnectionState(final int resourceId) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				System.out.println("ResourceId##########"+resourceId);
				mConnectionState.setText(resourceId);
			}
		});
	}

	private void displayData(String data) {
		if (data != null) {
			Log.e("---DATA", data);
			String substring = replaceBlank(data);
			mDataField.setText(data);
			textview_return_result.setText(data);
			String substring2 = substring.substring(substring.length() - 2, substring.length());//获取最后两位
			if (substring.length() > 8) {
				String substring1 = substring.substring(substring.length() - 8, substring.length() - 4);
				String substring3 = substring.substring(substring.length() - 14, substring.length() - 12);//获取透传的返回值
				switch (substring1) {
					case "2100":
						mBluetoothLeService.writeLlsAlertLevel(2, getHexBytes("FFCC0002010701D5"));//格式化flash
						break;
					case "2101":
						mHandler.postDelayed(mRunnable, 4000);  //因为播放声音的缘故，需要等4秒再上传文件
						break;
					case "2200":
						count = count + 1;
						if (count <= 3) {
							serialNumber = serialNumber - 1;
							mHandler.postDelayed(mRunnable, 10);
							mHandler.removeCallbacks(mRunnable3);//关闭续传
						} else {
							button_mp_calue.setEnabled(true);
							button_send_value.setEnabled(true);
							Toast.makeText(this,"升级失败，请重新升级",Toast.LENGTH_SHORT).show();
						}
						break;
					case "2201":
						stopcount = stopcount + 1;
						if (stopcount < map.size()) {
							mHandler.removeCallbacks(mRunnable);
							mHandler.post(mRunnable);
							Log.e("----m成功的数据", serialNumber + "----");
							mHandler.removeCallbacks(mRunnable3);                    //成功之后需要关闭续传的子线程防止错误

						} else {
							mHandler.postDelayed(mRunnable1, 10);
							mHandler.removeCallbacks(mRunnable3); //成功之后需要关闭续传的子线程防止错误
							mHandler.removeCallbacks(mRunnable);
						}
						break;
					case "2300":

						Toast.makeText(this,"确认文件失败",Toast.LENGTH_SHORT).show();
						break;
					case "2301":
						//开始重启蓝牙模块
						mBluetoothLeService.writeLlsAlertLevel(2, getHexBytes("FFCC0002010401D2"));
						Toast.makeText(DeviceControlActivity.this,"升级成功",Toast.LENGTH_SHORT).show();
						button_mp_calue.setEnabled(true);
						button_send_value.setEnabled(true);
						break;
					case "2401":

						break;
				}
				if (substring3.equals("26")) {
					mHandler.removeCallbacks(mRunnable3);//关闭续传
					String substring4 = substring.substring(substring.length() - 8, substring.length() - 4);//获取序号
					serialNumber = Integer.parseInt(substring4, 16);
					Log.e("----",serialNumber+"");
					if (serialNumber >= 0 && serialNumber < 10) {
						// mInstance.send("FFCC00040101010301D5");//
						if(mp3OrFirmware==1){
							mBluetoothLeService.writeLlsAlertLevel(2, getHexBytes("FFCC00040101010101D3"));
						}else{
							mBluetoothLeService.writeLlsAlertLevel(2, getHexBytes("FFCC00040101010301D5"));
						}

					} else if (serialNumber >= 10 && serialNumber < map.size()) {
						mHandler.post(mRunnable);
					}
					mHandler.postDelayed(mRunnable3, 5000);//再次开启续传监听
				} else if (substring3.equals("27")) {
					// mInstance.send("FFCC00040101010301D5");//格式化flash之后创建音乐文件命令
					if(mp3OrFirmware==1){
						mBluetoothLeService.writeLlsAlertLevel(2, getHexBytes("FFCC00040101010101D3"));
					}else{
						mBluetoothLeService.writeLlsAlertLevel(2, getHexBytes("FFCC00040101010301D5"));
					}
				}
			} else if (substring.length() < 8 && substring2.equals("46")) {
				mHandler.removeCallbacks(mRunnable3);//校验值错误，先关闭，然后立即开启请求包续，
				mHandler.post(mRunnable3);
			}
		}
	}


	/**
	 * 循环发送消息线程
	 */
	Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			byte[] s = map.get(serialNumber);
			send(serialNumber,s);
			serialNumber=serialNumber+1;
			Log.e("----m最后出错的地方",serialNumber+"需要重发的");
			//发送数据之后需要开启续传的子线程等待收到消息
			mHandler.postDelayed(mRunnable3,5000);
		}
	};
	/**
	 *确认文件线程
	 */
	Runnable mRunnable1= new Runnable() {
		@Override
		public void run() {
			byte []a2=getHexBytes("FFCC0002010301D1");
			mBluetoothLeService.writeLlsAlertLevel(2, a2);

		}
	};
	/**
	 *没有收到通知续传
	 */
	Runnable mRunnable3= new Runnable() {
		@Override
		public void run() {
			byte[] bb =getHexBytes("FFCC0002010601D4");//续传序号
			mBluetoothLeService.writeLlsAlertLevel(2, bb);
			mHandler.postDelayed(mRunnable3,5000);
			Toast.makeText(DeviceControlActivity.this, "重发",
					Toast.LENGTH_SHORT).show();
		}
	};

	Runnable mRunnable5 = new Runnable() {
		@Override
		public void run() {
			serialNumber = 1;
			byte[] s = map.get(0);
			mBluetoothLeService.writeLlsAlertLevel(2, s);
			mHandler.postDelayed(mRunnable3, 5000);
		}
	};

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	private void displayGattServices(List<BluetoothGattService> gattServices) {
		if (gattServices == null)
			return;
		String uuid = null;
		String unknownServiceString = getResources().getString(
				R.string.unknown_service);
		String unknownCharaString = getResources().getString(
				R.string.unknown_characteristic);
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

		SimpleExpandableListAdapter gattServiceAdapter = new SimpleExpandableListAdapter(
				this, gattServiceData,
				android.R.layout.simple_expandable_list_item_2, new String[] {
						LIST_NAME, LIST_UUID }, new int[] { android.R.id.text1,
						android.R.id.text2 }, gattCharacteristicData,
				android.R.layout.simple_expandable_list_item_2, new String[] {
						LIST_NAME, LIST_UUID }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		mGattServicesList.setAdapter(gattServiceAdapter);
	}

	private static IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
		intentFilter
				.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		return intentFilter;
	}


	/**
	 *数据处理
     */
	public byte[] allcontent(int m,byte[] s){
		Log.e("====1",System.currentTimeMillis()+"'");
		byte[] content =s;
		//byte[] content=getHexBytes("01");
		byte[] head = getHexBytes("FFCC00");

		byte[] order=getHexBytes("0102");
		Test t8= new Test();
		t8.name.set((short) (content.length+4));
		Log.e("====2",System.currentTimeMillis()+"'");
		byte[] sum=getHexBytes(replaceBlank(t8.toString()));
		Log.e("====3",System.currentTimeMillis()+"'");
		TestHax t16= new TestHax();
		t16.name.set(m);
		byte[] serial= new byte[2];
		t16.getByteBuffer().get(serial);
		Log.e("====4",System.currentTimeMillis()+"'");
		//byte[] serial = getHexBytes(replaceBlank(t16.toString()));
		Log.e("====5",System.currentTimeMillis()+"'");
		byte[] bytes = byteMerger(byteMerger(head, sum), order);
		Log.e("====6",System.currentTimeMillis()+"'");
		byte[] bytes1 = byteMerger(byteMerger(bytes, serial),content);
		Log.e("====7",System.currentTimeMillis()+"'");
		String s1 = bytes2hex02(bytes1);
		String[] hexBytes = getTenBytes(s1);
		int numbersum =0;
		Log.e("====8",System.currentTimeMillis()+"'");
		for (int z = 0; z <hexBytes.length ; z++) {
			numbersum=numbersum+Integer.parseInt(hexBytes[z],16);
		}
		Log.e("====9",System.currentTimeMillis()+"'");
		TestHax t16two= new TestHax();
		byte[] Checksum= new byte[2];
		t16two.name.set(numbersum);
		t16two.getByteBuffer().get(Checksum);
		byte[] allbyte = byteMerger(bytes1, Checksum);
		Log.e("====10",System.currentTimeMillis()+"'");


		return allbyte;
	}

	/**
	 * 消息发送
	 */
	public void send(int m,byte[] s){

		byte[] allcontent = allcontent(m, s);
		byte[] buf = new byte[20];
		int a, b;
		a = allcontent.length / 20;
		b = allcontent.length - a * 20;
		for (int i = 0; i < a; i++) {
			System.arraycopy(allcontent, i * 20, buf, 0, 20);
			Log.e("---buf",Arrays.toString(buf));
			mBluetoothLeService.writeLlsAlertLevel(2,buf);

				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}


		}
		if (b > 0) {
			byte[] end= new byte[b];
			System.arraycopy(allcontent, a * 20, end, 0, b);
			Log.e("---end",Arrays.toString(end));
			mBluetoothLeService.writeLlsAlertLevel(2,end);
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 转换成16进制
	 */
	public  String bytes2hex02(byte[] bytes)
	{
		StringBuilder sb = new StringBuilder();
		String tmp = null;
		for (byte b : bytes)
		{
			// 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
			tmp = Integer.toHexString(0xFF & b);
			if (tmp.length() == 1)// 每个字节8为，转为16进制标志，2个16进制位
			{
				tmp = "0" + tmp;
			}
			sb.append(tmp);
		}

		return sb.toString();

	}

	/**
	 * 转换成16进制两字节
	 */
	public  class TestHax extends Struct {

		public boolean isPacked() {
			System.out.println("");
			return true; // MyStruct is packed.
		}
		public final Unsigned16  name= new Unsigned16();
	}
	/**
	 * 转换成16进制一字节
	 */
	public  class Test extends Struct {

		public boolean isPacked() {
			System.out.println("");
			return true; // MyStruct is packed.
		}
		public final Unsigned8 name= new Unsigned8();
	}
	/**
	 *字符串去除空格
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	private String[] getTenBytes(String message) {
		int len = message.length() / 2;
		char[] chars = message.toCharArray();
		String[] hexStr = new String[len];
		byte[] bytes = new byte[len];
		for (int i = 0, j = 0; j < len; i += 2, j++) {
			hexStr[j] = "" + chars[i] + chars[i + 1];
			bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
		}
		return hexStr;
	}

	/**
	 *字符串转化成16进制数组
	 */

	private byte[] getHexBytes(String message) {
		int len = message.length() / 2;
		char[] chars = message.toCharArray();
		String[] hexStr = new String[len];
		byte[] bytes = new byte[len];
		for (int i = 0, j = 0; j < len; i += 2, j++) {
			hexStr[j] = "" + chars[i] + chars[i + 1];
			bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
		}
		return bytes;
	}


	/**
	 *数组合并
	 */
	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2){
		byte[] byte_3 = new byte[byte_1.length+byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}



	private void makeActionOverflowMenuShown() {
		//devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {

		}
	}

}
