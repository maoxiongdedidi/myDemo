package com.doohan.testbluetooth.BluetoothConnect;

import java.io.IOException;
import java.util.ArrayList;


import com.doohan.testbluetooth.MainApplication;
import com.doohan.testbluetooth.MyApplication;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import android.util.Log;


@SuppressLint("NewApi")
public class BluetoothInterface  {// extends Service{
	private final static String TAG = BluetoothInterface.class.getSimpleName();

	private BluetoothGatt mBluetoothGatt;
	private BluetoothGattCharacteristic mCharacteristicWrite;
	private BluetoothManager mBluetoothManager;
	private BluetoothAdapter mBluetoothAdapter;
	private String mBluetoothDeviceAddress;
	private boolean mConnected = false;
	private boolean mServicesDiscovered = true;
	private boolean mService = true;
	private boolean mDisconnected = false;
	private byte[] mBuff = new byte[2048];
	private byte[] mBuffRx = new byte[2048];
	private int mBuffLen = 0;
	private boolean RxFinish = true;
	private double mTime;
	private String  mCurrentConnectDeviceBluetoothName ="";
	// private boolean writeFinish = true;

	private PackageProcessor mRxPackages = new PackageProcessor();
	private ArrayList<TCANRxPort> RxPortList = new ArrayList<TCANRxPort>();
	private static BluetoothInterface instance = null;
	//  防止获取太多次的快速数据
	 private static long lastReceiceFastInformTime;
	    public static boolean isRecentReceiceFastInform() {
	        long time = System.currentTimeMillis();
	        long timeD = time - lastReceiceFastInformTime;
	        if ( 0 < timeD && timeD < 1000) {
	            return true;
	        }

	        return false;
	   }



	private BluetoothInterface() {
	}

	public static BluetoothInterface getInstance() {
		if (instance == null)
			instance = new BluetoothInterface();
		return instance;
	}

	public int CreateRxPort() {
		TCANRxPort port = new TCANRxPort();
		RxPortList.add(port);

		return RxPortList.size() - 1;
	}

//	public int CreateRxPort(int TypeId) {
//		TCANRxPort port = new TCANRxPort();
//		port.mRxid = TypeId;
//		RxPortList.add(port);
//
//		return RxPortList.size() - 1;
//	}
//


	public int getRxPort() {
		return RxPortList.size();
	}

	public void RemoveRxPort() {
		// for (int i = 0; i < RxPortList.size(); i++) {
		// TCANRxPort port = RxPortList.remove(i);
		// port = null;
		// }
		RxPortList.clear();
	}

	public void RemoveRxPort(int position) {
		// for (int i = 0; i < RxPortList.size(); i++) {
		// TCANRxPort port = RxPortList.remove(i);
		// port = null;
		// }
		RxPortList.remove(position);
	}

	public boolean isConnected() {
		return mConnected;
	}

	public boolean isDisconnected() {
		return mDisconnected;
	}

	public boolean isGetServicesDiscovered() {
		return mServicesDiscovered;
	}

	public boolean isGetService() {
		return mService;
	}

	public boolean connect(BluetoothDevice device) {

		if (device == null) {
			Log.w(TAG, "Device not found.  Unable to connect.");
			return false;
		}

		if (mBluetoothGatt != null) {
			mBluetoothGatt.disconnect();
			mBluetoothGatt.close();
			mBluetoothGatt = null;
		}

		// if(mBluetoothGatt != null && mConnected == true) {
		// mBluetoothGatt.disconnect();
		// mBluetoothGatt.close();
		// mBluetoothGatt = null;
		// }

		if (mBluetoothGatt != null)
			mBluetoothGatt = null;

		mServicesDiscovered = true;
		mService = true;
		mConnected = false;
		mDisconnected = false;
		mBluetoothGatt = device.connectGatt(MainApplication.getContext(),
				false, mGattCallback);
		Log.e(TAG, "Trying to create a new connection.");

		return true;
	}

	public void disconnect() {
		if (mBluetoothGatt == null) {
			Log.e(TAG, "BluetoothAdapter not initialized");
			return;
		}
		mBluetoothGatt.disconnect();
		mConnected = false;
	}

	public void close() {
		if (mBluetoothGatt == null) {
			return;
		}
		mConnected = false;
		mBluetoothGatt.close();
		mBluetoothGatt = null;
	}

	public String getBluetoothName() {
		if (mConnected == true) {
			return mBluetoothGatt.getDevice().getName();
		}
		return null;
	}

	public String getBluetoothAddress() {
		if (mConnected == true) {
			return mBluetoothGatt.getDevice().getAddress();
		}
		return null;
	}

	private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status,
				int newState) {
			if (newState == BluetoothProfile.STATE_CONNECTED) { // 连接成功
				mTime = System.currentTimeMillis();
				Log.e("蓝牙", "连接成功");
				Log.e(TAG, "Connected to GATT server.");
				// Attempts to discover services after successful connection.
				mBluetoothGatt.discoverServices();
				// Log.e(TAG, "Attempting to start service discovery:"
				// + mBluetoothGatt.discoverServices());
			} else if (newState == BluetoothProfile.STATE_DISCONNECTED) { // 断开连接
				Log.e("蓝牙", "断开连接");
				mConnected = false;
				mDisconnected = true;
				Log.e(TAG, "Disconnected from GATT server.");
			} else if(newState == BluetoothProfile.STATE_DISCONNECTING){
				Log.e("蓝牙", "断开连接中");

			} else if(newState == BluetoothProfile.STATE_CONNECTING){

				Log.e("蓝牙", "连接中");
			}
		}

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) { // 获取服务
			mTime = System.currentTimeMillis() - mTime;
			mTime = mTime / 1000.0;
			Log.e("蓝牙", "服务:" + status + " " + mTime);
			if (status == BluetoothGatt.GATT_SUCCESS) {
				BluetoothGattService bluetoothGattServiceService = null;
				BluetoothGattCharacteristic characteristicService = null;
				bluetoothGattServiceService = gatt
						.getService(Common.RECIEVE_SERVICE_UUID); // 获取旧蓝牙读服务
				if (bluetoothGattServiceService == null) {
					bluetoothGattServiceService = gatt
							.getService(Common.SERVICE_UUID_NEW); // 获取新蓝牙读服务
					if (bluetoothGattServiceService == null) {
						mService = false;
						//disconnect();
						return;
					}
					//Log.e(TAG, "new service"+Common.SERVICE_UUID_NEW);
				}

				characteristicService = bluetoothGattServiceService
						.getCharacteristic(Common.RECIEVE_CHAR_UUID); // 获取旧蓝牙读特征值
				if (characteristicService == null) {
					characteristicService = bluetoothGattServiceService
							.getCharacteristic(Common.RECIEVE_CHAR_UUID_NEW); // 获取新蓝牙读特征值
					if (characteristicService == null) {
						mService = false;
						//disconnect();
						return;
					}
					//Log.e(TAG, "new chari"+Common.RECIEVE_CHAR_UUID_NEW);
				}
				// Log.e("获取接收特征值", "success");
				// Log.e("获取接收特征值", bluetoothGattServiceService.getUuid()
				// .toString());
				// Log.e("获取接收特征值", characteristicService.getUuid().toString());
				gatt.setCharacteristicNotification(characteristicService, true);

				if (characteristicService
						.getDescriptor(Common.RECIEVE_CHARACTERISTIC_UUID_NEW) != null) { // 开启数据接收接口
					BluetoothGattDescriptor descriptor = characteristicService
							.getDescriptor(Common.RECIEVE_CHARACTERISTIC_UUID_NEW);
					descriptor
							.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
					gatt.writeDescriptor(descriptor);

					//Log.e(TAG, "new descriptor"+Common.RECIEVE_CHARACTERISTIC_UUID_NEW);
				} else {
					mService = false;
					//disconnect();
					return;
				}

				// BluetoothGattDescriptor descriptor = characteristic
				// .getDescriptor(UUID
				// .fromString("00002902-0000-1000-8000-00805f9b34fb"));
				// descriptor
				// .setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
				// mBluetoothGatt.writeDescriptor(descriptor);
				BluetoothGattService bluetoothGattServiceSend = null;
				mCharacteristicWrite = null;
				bluetoothGattServiceSend = gatt
						.getService(Common.SEND_SERVICE_UUID); // 获取旧蓝牙写服务
				if (bluetoothGattServiceSend == null) {
					bluetoothGattServiceSend = gatt
							.getService(Common.SERVICE_UUID_NEW); // 获取新蓝牙写服务
					if (bluetoothGattServiceSend == null) {
						mService = false;
						//disconnect();
						return;
					}
					//Log.e(TAG, "new sevice"+Common.SERVICE_UUID_NEW);
				}

				mCharacteristicWrite = bluetoothGattServiceSend
						.getCharacteristic(Common.SEND_CHAR_UUID); // 获取旧蓝牙写特征值
				if (mCharacteristicWrite == null) {
					mCharacteristicWrite = bluetoothGattServiceSend
							.getCharacteristic(Common.SEND_CHAR_UUID_NEW); // 获取新蓝牙写特征值


					if (mCharacteristicWrite == null) {
						mService = false;
						//disconnect();
						return;
					}
					//Log.e(TAG, "new chari"+Common.SEND_CHAR_UUID_NEW);
				}
				 Log.e("获取发送特征值", "success");
			//	 Log.e("获取发送特征值",
//				 bluetoothGattServiceSend.getUuid().toString());
//				 Log.e("获取发送特征值", mCharacteristicWrite.getUuid().toString());
//				 mCharacteristicWrite.setValue(new byte[] { (byte) 0xaa,
//				 (byte) 0xbb, (byte) 0xcc });
//				 mBluetoothGatt.writeCharacteristic(mCharacteristicWrite);

//				 if (mCharacteristicWrite
//				 .getDescriptor(Common.RECIEVE_CHARACTERISTIC_UUID_NEW) !=
//				 null) {
//				 BluetoothGattDescriptor descriptor = mCharacteristicWrite
//				 .getDescriptor(Common.RECIEVE_CHARACTERISTIC_UUID_NEW);
//				 descriptor
//				 .setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//				 mBluetoothGatt.writeDescriptor(descriptor);
//				 } else {
//				 mService = false;
//				 return;
//				 }

				mConnected = true;
				//Log.e(TAG, "对应特殊值获取成功");
			} else {
				mServicesDiscovered = false;
				//disconnect();
				Log.e(TAG, "onServicesDiscovered received: " + status);
			}
		}

		@Override
		public void onDescriptorWrite(BluetoothGatt gatt,
				BluetoothGattDescriptor descriptor, int status) {
			//Log.e("onDescriptorWrite", "write");
			if (status == BluetoothGatt.GATT_SUCCESS) {
				//Log.e("onDescriptorWrite", "write success");
			}
		}

		@Override
		public void onCharacteristicRead(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			 //Log.v(TAG, "onCharacteristicRead");
			//Log.e("onCharacteristicRead", "reveice");
//			if (status == BluetoothGatt.GATT_SUCCESS) {
//
//			}
		}

		public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
		//	Log.e("onCharacteristicWrite", "write");
//			if (status == BluetoothGatt.GATT_SUCCESS) {
//
//			}


		};


		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic) { // 数据发生改变
			// Log.v(TAG, "onCharacteristicChanged");
			//Log.v("onCharacteristicChanged", "change"  characteristic.g);
			//Log.e("onCharacteristicChanged", Common.ByteToString(characteristic.getValue()));
			 synchronized (this){
			if (readByte(characteristic)) {
				Log.e("read finish",mBuffLen+"");
				Log.v("read finish", Common.ByteToString(mBuffRx,mBuffLen));
				for (int i = 0; i < mBuffLen; i++) {
					RxFinish = false;
					if (mRxPackages.AnalyzePackage(mBuffRx[i])) {

						// 动作录制广播
						int id = 0;
						id |= mRxPackages.m_pRxBuf[3];
						id <<= 8;
						id |= mRxPackages.m_pRxBuf[2];
						id <<= 8;
						id |= mRxPackages.m_pRxBuf[1];
						id <<= 8;
						id |= mRxPackages.m_pRxBuf[0];
						// Log.v("广播1", "广播");
						//Log.e("数据返回",
						//Common.ByteToString(mRxPackages.m_pRxBuf));
						if (id == Common.PC_GET_FAST_INFO) {

							Log.e("接收到快速数据", "id"+id);
                            if(Common.Control_FastInformation == true){

                            	Intent intent = new Intent();
								intent.setAction(Common.ACTION_CONTROL);
								byte msgBuf[] = new byte[mRxPackages.m_RxPackageDataCount];
								for (int j = 0; j < mRxPackages.m_RxPackageDataCount; j++)
									msgBuf[j] = mRxPackages.m_pRxBuf[j];

								intent.putExtra("FastInformation", msgBuf);
								MyApplication.getInstance()
										.getApplicationContext()
										.sendBroadcast(intent);


							}else if (Common.FastInformation_send == false) {
								Intent intent = new Intent();
								intent.setAction(Common.ACTION_RECORDER);
								byte msgBuf[] = new byte[mRxPackages.m_RxPackageDataCount];
								for (int j = 0; j < mRxPackages.m_RxPackageDataCount; j++)
									msgBuf[j] = mRxPackages.m_pRxBuf[j];

								intent.putExtra("Action", msgBuf);
								MyApplication.getInstance()
										.getApplicationContext()
										.sendBroadcast(intent);
							} else {
								Intent intent = new Intent();
								intent.setAction(Common.ACTION_FASTINFORMATION);
								byte msgBuf[] = new byte[mRxPackages.m_RxPackageDataCount];
								for (int j = 0; j < mRxPackages.m_RxPackageDataCount; j++)
									msgBuf[j] = mRxPackages.m_pRxBuf[j];

								intent.putExtra("FastInformation", msgBuf);
								MyApplication.getInstance()
										.getApplicationContext()
										.sendBroadcast(intent);
							}
						}else{
							Log.e("接收到数据", "id"+id);
							OnRecvPackage(mRxPackages.m_pRxBuf,
									mRxPackages.m_RxPackageDataCount,id);

						}
						// if (id == Common.GET_ALL_MC_ENABLE_RET_NAV_DATA) {
						// Intent intent = new Intent();
						// intent.setAction(Common.PAINT);
						// byte msgBuf[] = new
						// byte[mRxPackages.m_RxPackageDataCount];
						// for (int j = 0; j < mRxPackages.m_RxPackageDataCount;
						// j++)
						// msgBuf[j] = mRxPackages.m_pRxBuf[j];
						//
						// intent.putExtra("Paint", msgBuf);
						// MyApplication.getInstance().getApplicationContext()
						// .sendBroadcast(intent);
						// }
						// Log.v("解析数据",
						// Common.ByteToString(mRxPackages.m_pRxBuf));

					}
					else{
						//Log.v("解析数据", "fail");
					}
					RxFinish = true;
				}
			}else{
				Log.v("read byte", "fail");

			}
			}
		}
	};

	private boolean readByte(BluetoothGattCharacteristic characteristic) {
		byte[] bytes = characteristic.getValue();
		Log.v("数据返回", Common.ByteToString(bytes));
		if (bytes[0] == (byte) 0xAA && bytes[1] == (byte) 0xAA) {
			// Log.v("头", "头");
			mBuffLen = 0;
		}

		for (int i = 0; i < bytes.length; i++) {
			mBuff[mBuffLen] = bytes[i];
			mBuffLen++;
		}

		if (bytes.length < 20
				|| (bytes[18] == (byte) 0x55 && bytes[19] == (byte) 0x55)) {
			//if (RxFinish == true) {
				System.arraycopy(mBuff, 0, mBuffRx, 0, mBuff.length);
				return true;
			//}
			//	mBuff = null;
		}
		return false;
	}

	public int OnRecvPackage(byte[] bytes, int len,int id) {
		// 这里只处理CAN MSG
		// mRxPackageCount ++;
	//	Log.e("receive  rx object", "1457"+RxPortList.size());
		// 发送消息到线程
		for (TCANRxPort portObj : RxPortList) {

			//Log.e("receive  rx object", "1457");
			if (portObj.mEnabled) {
				synchronized (portObj) {
					// 如果已满，则丢掉最开始的
					//if(portObj.mRxid == -1|| portObj.mRxid ==  id){

					if (portObj.mRxMsgList.isFull()) {
						@SuppressWarnings("unused")
						byte g[] = portObj.mRxMsgList.removeFirst();
						g = null;
					}
					byte msgBuf[] = new byte[len];
					for (int i = 0; i < len; i++)
						msgBuf[i] = bytes[i];

					portObj.mRxMsgList.addLast(msgBuf);
					portObj.notify();
					//}
					// if (portObj.mHandler != null) {
					// Log.e("", "test update!");
					// portObj.mHandler.obtainMessage(1, msgBuf)
					// .sendToTarget();
					// }
				}
			}
		}

		return 0;
	}

	// 发送和请求的消息ID相同，并使用txmsg为发送消息
	public CAN_msg RequestCANMsg(int port, CAN_msg txmsg, int timeout) {
		if (mBluetoothGatt == null) {
			Log.e(TAG, "BluetoothAdapter not initialized");
			return null;
		}

		CAN_msg rxmsg;
		if (!WriteCANMsg(txmsg)) {
			Log.e("send  指令", "写入失败");
			return null;
		}

		// Log.v("发送指令", Common.ByteToString(txmsg.getBytes()));
		for (;;) {
			try {
				rxmsg = ReadCANMsg(port, timeout);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

			if (rxmsg == null)
				return null;

			if (rxmsg.type == Common.CAN_FRAME.REMOTE_FRAME)
				continue;

			if ((rxmsg.id & 0x00FFFFFF) == (txmsg.id & 0x00FFFFFF))
				return rxmsg;
		}
	}

	public boolean WriteCANMsg(CAN_msg msg) {

		int len = 0;
		boolean result = false;

		if (this.mConnected == false)
			return false;

		if(msg.id  == Common.PC_GET_FAST_INFO){
		if(isRecentReceiceFastInform()){
			return false;
		}else{
			lastReceiceFastInformTime = System.currentTimeMillis();
		}
		}
		try {
			len = mRxPackages.WritePackage(msg, CAN_msg.CAN_MSG_LEN);
			if (len > 0)
				result = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	private void write(byte[] bytes, int len) throws IOException {
		if (mConnected) {
			// mTxCount += len;
			// 等待发送完毕
			synchronized (this) {

				byte[] buf = new byte[20];
				int a, b;
				a = len / 20;
				b = len - a * 20;
				for (int i = 0; i < a; i++) {
					System.arraycopy(bytes, i * 20, buf, 0, 20);
					mCharacteristicWrite.setValue(buf);
					mBluetoothGatt.writeCharacteristic(mCharacteristicWrite);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (b > 0) {
					System.arraycopy(bytes, a * 20, buf, 0, b);
					mCharacteristicWrite.setValue(buf);
					if(mBluetoothGatt!=null)
					mBluetoothGatt.writeCharacteristic(mCharacteristicWrite);
				}
				// writeFinish = true;
			}
		}
	}

	public CAN_msg ReadCANMsg(int port, int timeout)
			throws InterruptedException {
		CAN_msg msg = null;

		if (port >= this.RxPortList.size())
			return msg;

		TCANRxPort portObj = RxPortList.get(port);
		synchronized (portObj) {
			if (portObj.mRxMsgList.isEmpty()) {
				portObj.wait(timeout);
				if (portObj.mRxMsgList.isEmpty()) // 超时
					return null;
			}
			byte buf[] = portObj.mRxMsgList.removeFirst();
			msg = new CAN_msg(buf);
		}
		// Log.e("接收数据", Common.ByteToString(msg.data));
		return msg;
	}

	// 使用txmsg为发送消息,要求回复的消息ID与rxid相同
	public CAN_msg RequestCANMsg(int port, CAN_msg txmsg, int rxid, int timeout) {
		CAN_msg rxmsg;
		if (!WriteCANMsg(txmsg))
			return null;

		for (;;) {
			try {
				rxmsg = ReadCANMsg(port, timeout);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

			if (rxmsg == null)
				return null;

			if (rxmsg.type == Common.CAN_FRAME.REMOTE_FRAME)
				continue;

			if ((rxmsg.id & 0x00FFFFFF) == (rxid & 0x00FFFFFF))
				return rxmsg;
		}
	}

	// 使用txid作为ID发送远程帧，要求回复的消息ID与rxid相同
	public CAN_msg RequestCANMsg(int port, int txid, int rxid, int timeout) {
		CAN_msg txmsg = new CAN_msg(), rxmsg;
		txmsg.id = txid;
		txmsg.type = Common.CAN_FRAME.REMOTE_FRAME;

		if (!WriteCANMsg(txmsg))
			return null;

		for (;;) {
			try {
				rxmsg = ReadCANMsg(port, timeout);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

			if (rxmsg == null)
				return null;

			if (rxmsg.type == Common.CAN_FRAME.REMOTE_FRAME)
				continue;

			if ((rxmsg.id & 0x00FFFFFF) == (rxid & 0x00FFFFFF))
				return rxmsg;
		}
	}

	private class PackageProcessor {
		byte USART_LastByte = 0;
		boolean USART_BeginFlag = false;
		boolean USART_CtrlFlag = false;
		int USART_RevOffset = 0;
		byte CheckSum = 0;
		public int m_RxPackageDataCount = 0;
		public byte m_pRxBuf[] = new byte[m_MaxPackageSize];

		static final byte m_FrameHead = (byte) 0xAA, m_FrameTail = 0x55,
				m_FrameCtrl = (byte) 0xA5;
		static final int m_MaxPackageSize = 1024;

		public synchronized boolean AnalyzePackage(byte data) {
			if (((data == m_FrameHead) && (USART_LastByte == m_FrameHead))
					|| (USART_RevOffset > m_MaxPackageSize)) {
				// RESET
				USART_RevOffset = 0;
				USART_BeginFlag = true;
				USART_LastByte = data;
				return false;
			}
			if ((data == m_FrameTail) && (USART_LastByte == m_FrameTail)
					&& USART_BeginFlag) {
				USART_RevOffset--;// 得到除去头尾和控制符的数据的个数
				m_RxPackageDataCount = USART_RevOffset - 1;
				CheckSum -= m_FrameTail;
				CheckSum -= m_pRxBuf[m_RxPackageDataCount];
				USART_LastByte = data;
				USART_BeginFlag = false;
				if (CheckSum == m_pRxBuf[m_RxPackageDataCount]) // 解包成功
				{
					CheckSum = 0;
					return true;
				}
				CheckSum = 0;
				return false; // 解包失败
			}
			USART_LastByte = data;
			if (USART_BeginFlag) {
				if (USART_CtrlFlag) {
					m_pRxBuf[USART_RevOffset++] = data;
					CheckSum += data;
					USART_CtrlFlag = false;
					USART_LastByte = m_FrameCtrl;
				} else if (data == m_FrameCtrl) {
					USART_CtrlFlag = true;
				} else {
					m_pRxBuf[USART_RevOffset++] = data;
					CheckSum += data;
				}
			}

			return false;
		}

		public int WritePackage(CAN_msg msg, int len) throws IOException {
			byte[] buf = msg.getBytes();
			byte[] ex_data = msg.ex_data;
			byte pTxBuf[];
			int num = 0;
			if (msg.format == Common.CAN_FORMAT.EXTENDED_FORMAT) {
				num = Common.ByteToInt(0, msg.data);
				pTxBuf = new byte[len * 2 + 8 + num * 2];
			} else {
				pTxBuf = new byte[len * 2 + 8];
			}
			int offset = 0;
			byte CheckSum = 0;

			if (buf == null)
				return 0;

			pTxBuf[offset++] = m_FrameHead;
			pTxBuf[offset++] = m_FrameHead;

			for (int i = 0; i < len; i++) {
				if ((buf[i] == m_FrameCtrl) || (buf[i] == m_FrameHead)
						|| (buf[i] == m_FrameTail)) {
					pTxBuf[offset++] = m_FrameCtrl;
				}
				pTxBuf[offset++] = buf[i];
				CheckSum += buf[i];
			}

			// Send Tail USART_FRAMETAIL USART_FRAMETAIL

			if (ex_data != null) {
				for (int i = 0; i < num; i++) {
					if ((ex_data[i] == m_FrameCtrl)
							|| (ex_data[i] == m_FrameHead)
							|| (ex_data[i] == m_FrameTail)) {
						pTxBuf[offset++] = m_FrameCtrl;
					}
					pTxBuf[offset++] = ex_data[i];
					CheckSum += ex_data[i];
				}
			}

			// 校验和
			if ((CheckSum == m_FrameCtrl) || (CheckSum == m_FrameHead)
					|| (CheckSum == m_FrameTail)) {
				pTxBuf[offset++] = m_FrameCtrl;
			}
			pTxBuf[offset++] = CheckSum;

			pTxBuf[offset++] = m_FrameTail;
			pTxBuf[offset++] = m_FrameTail;
			len = offset;

			Log.v("send", Common.ByteToString(pTxBuf) + "" + len);
			write(pTxBuf, len);

			// mTxPackageCount ++;

			return len;
		}
		// public int WritePackage(byte[] buf, int len) throws IOException {
		// byte pTxBuf[] = new byte[len * 2 + 8];
		// int offset = 0;
		// byte CheckSum = 0;
		//
		// if (buf == null)
		// return 0;
		//
		// pTxBuf[offset++] = m_FrameHead;
		// pTxBuf[offset++] = m_FrameHead;
		//
		// for (int i = 0; i < len; i++) {
		// if ((buf[i] == m_FrameCtrl) || (buf[i] == m_FrameHead)
		// || (buf[i] == m_FrameTail)) {
		// pTxBuf[offset++] = m_FrameCtrl;
		// }
		// pTxBuf[offset++] = buf[i];
		// CheckSum += buf[i];
		// }
		//
		// // 校验和
		// if ((CheckSum == m_FrameCtrl) || (CheckSum == m_FrameHead)
		// || (CheckSum == m_FrameTail)) {
		// pTxBuf[offset++] = m_FrameCtrl;
		// }
		// pTxBuf[offset++] = CheckSum;
		//
		// // Send Tail USART_FRAMETAIL USART_FRAMETAIL
		// pTxBuf[offset++] = m_FrameTail;
		// pTxBuf[offset++] = m_FrameTail;
		//
		// len = offset;
		//
		// Log.v("send", Common.ByteToString(pTxBuf));
		// write(pTxBuf, len);
		//
		// // mTxPackageCount ++;
		//
		// return len;
		// }
	}

	class TCANRxPort {
		boolean mEnabled = true;
		//int mRxid = -1;
		RoundQueue<byte[]> mRxMsgList = new RoundQueue<byte[]>(100);
		Handler mHandler;
	}


	  public boolean initialize(Context context) {
	        // For API level 18 and above, get a reference to BluetoothAdapter
	        // through
	        // BluetoothManager.
	        if (mBluetoothManager == null) {
	            mBluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
	            if (mBluetoothManager == null) {

//	                Logger.datalog(" Unable to initialize BluetoothManager.",
//	                		context.getApplicationContext());

	                return false;
	            }
	        }

	        mBluetoothAdapter = mBluetoothManager.getAdapter();
	        if (mBluetoothAdapter == null) {

//	            Logger.datalog(" Unable to obtain a BluetoothAdapter.",
//	                    getApplicationContext());
	            return false;
	        }

	        return true;
	    }

	  public BluetoothAdapter  GetBluetoothAdapter(Context context){

		  initialize(context) ;
		  return mBluetoothAdapter;


	  }


//	@Override
//	public IBinder onBind(Intent intent) {
//		// TODO 自动生成的方法存根
//		return null;
//	};
}
