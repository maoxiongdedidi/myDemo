/**
 * 公共静态全局变量和类
 * @time 2014年5月22日上午10:53:29
 */
package com.doohan.testbluetooth.BluetoothConnect;

import java.io.File;
import java.util.Locale;
import java.util.UUID;


import android.bluetooth.BluetoothDevice;
import android.util.Log;
import android.util.SparseArray;

public class Common {

	// 定义一个CarData 的哈希表 ,方便根据号码 来获取对应的CARDATA
	// public static SparseArray<CarData> MyCar = new SparseArray<CarData>();
	// 当前车辆的号码
	public static Integer currentCarNum = null;
	// 当前车辆的数量
	public static int carsnum;
	// 当前用户的用户名/邮箱
	public static String Username = null;
	public static String TOKEN_STRING = "";

	public static String mycarusername="wangjiang";
	public static String mycarpassword="123456";
	public static String cid="LGXC14CG2B1131523";
	// 数据库表名
	public static String DBName = null;

	// 蓝牙密码
	// public static int BluetoothPassword;
	public static BluetoothDevice DEVICE;
     
	

	// 更新用户信息广播
	public static final String ACTION_UPDATA_USERDATA = "com.inmotion.ble.updata.userData";
	public static final String ACTION_UPDATA_MAIN_USERDATA = "com.inmotion.ble.updata.main.userData";
	public static final String ACTION_UPDATA_MAIN_CARLIST = "com.inmotion.ble.updata.main.carList";

	// 快速数据广播
	public static final String ACTION_FASTINFORMATION = "com.inmotion.ble.fastInformation";

	// 动作录制广播
	public static final String ACTION_RECORDER = "com.inmotion.ble.actionRecorder";
	
	// 模拟遥控广播
	public static final String ACTION_CONTROL = "com.inmotion.ble.actionControl";
	
	// 随手画广播
	public static final String PAINT = "com.inmotion.ble.paint";

	// 上传GPS广播
	public static final String POST_GPS = "com.inmotion.ble.post_gps";

	public static final String CAR_VERSION = "3.4.909";
	public static final String V3_MUSICFW_VERSION = "1.1.101";
	public static final String CAR_ACTIVATE_VERSION = "3.9.326";
	public static final String CAR_V3_ACTIVATE_VERSION = "1.7.319";
	public static final String CAR_V3_READLOG_VERSION ="1.7.429";

	// 是否发送快速数据广播
	public static boolean FastInformation_send = true;
	// 是否在小纸条界面
	public static int NoteDialogue = 0;
	// 小纸条界面对象是否是推送人
	public static String NoteUserName = null;
	// 手机是否支持蓝牙
	public static boolean Blutooth_support;
	// 程序是否正在运行
	public static boolean APP_RUNNING = false;
	// 是否小纸条通知
	public static boolean NOTE_BROAD_SEND = false;

	public static boolean Control_FastInformation =false;
	// 游客登陆
	public static boolean LOGIN_VISITOR = false;
	public static boolean LOGIN_LOAD = false;
	public static boolean LOGIN_SHARE_REFRESH_1 = false;
	public static boolean LOGIN_SHARE_REFRESH_2 = false;
	public static boolean LOGIN_SHARE_REFRESH_3 = false;
	public static boolean LOGIN_SHARE_COMMENT_REFRESH = false;
	public static boolean LOGIN_PLAY_REFRESH_2 = false;
	public static boolean LOGIN_PLAY_REFRESH_3 = false;
	public static boolean LOGIN_MYINFORMATION_REFRESH = false;
	public static boolean LOGIN_MYCAR_SEARCH_REFRESH = false;
	public static boolean LOGIN_MYCAR_CARLIST_REFRESH = false;
	public static boolean LOGIN_MYCAR_CARMANAGER_REFRESH = false;
	public static boolean LOGIN_FIND_EVENTS_DETAIL_REFRESH = false;
	public static boolean REGISTER = false;

	// 是否使用百度地图
	public static boolean IS_BAIDU_MAP = true;

	// 退出登录
	public static boolean LOGOUT = false;

	public static Language APP_Language = Language.System;

	public final static int SUCCESS_CODE = 1;
	public static String TOKEN = "";
	public static String NORMAL_CODE = "N00000";
	public static String ERROR_CODE = "E02000";
	public static String ERROR_CODE_PHONE_REGISTER = "E02001";
	public static String LOGOUT_CODE = "E03000";

	// 蓝牙UID
//	public static UUID SEND_SERVICE_UUID = UUID
//			.fromString("0000ffe5-0000-1000-8000-00805f9b34fb");
//	public static UUID SEND_CHAR_UUID = UUID
//			.fromString("0000ffe9-0000-1000-8000-00805f9b34fb");
//	public static UUID RECIEVE_SERVICE_UUID = UUID
//			.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");
//	public static UUID RECIEVE_CHAR_UUID = UUID
//			.fromString("0000ffe4-0000-1000-8000-00805f9b34fb");
	
	public static UUID SEND_SERVICE_UUID = UUID
			.fromString("0000FFE5-0000-1000-8000-00805F9B34FB");
	public static UUID SEND_CHAR_UUID = UUID
			.fromString("0000FFE9-0000-1000-8000-00805F9b34FB");
	public static UUID RECIEVE_SERVICE_UUID = UUID
			.fromString("0000FFE0-0000-1000-8000-00805F9B34FB");
	public static UUID RECIEVE_CHAR_UUID = UUID
			.fromString("0000FFE4-0000-1000-8000-00805F9B34FB");
	
	
	public static UUID SERVICE_UUID_NEW = UUID
			.fromString("0003cdd0-0000-1000-8000-00805f9b0131");
	public static UUID SEND_CHAR_UUID_NEW = UUID
			.fromString("0003cdd2-0000-1000-8000-00805f9b0131");
	public static UUID RECIEVE_CHAR_UUID_NEW = UUID
			.fromString("0003cdd1-0000-1000-8000-00805f9b0131");
	public static UUID RECIEVE_CHARACTERISTIC_UUID_NEW = UUID
			.fromString("00002902-0000-1000-8000-00805f9b34fb");
	

	public enum CAN_FORMAT {
		STANDARD_FORMAT, EXTENDED_FORMAT
	};

	public enum CAN_FRAME {
		DATA_FRAME, REMOTE_FRAME
	};

	// SharedPreferences
	public static String CARS = "cars";
	public static String CAR_DEVICES = "car_devices"; // 设备地址

	// Can通讯接口
	public static int PC_GET_FAST_INFO = 0x0f550113; // 一次性获取较多的快速数据
	public static int PC_GET_SLOW_INFO = 0x0f550114; // 一次性获取较多的慢速数据
	public static int PC_REMOTE_CONTROL = 0x0f550116; // 遥控车体，实现遥控器的基本功能
	public static int PC_ACTION_REMOTE_CONTROL = 0x0f550804; // 遥控控制
	public static int SYSTEM_PRODUCT_MODEL = 0x0f550011; // 读写产品型号
	public static int PC_SET_SYS_PAR = 0x0f550115; // 设置系统参数
	public static int PC_MUSIC_CONTROL = 0x0f55010e; // 音乐控制
	public static int PC_LED_CONTROL = 0x0f55010d; // 灯光控制
	public static int PC_GPS_SWITCH = 0x0f550110; // GPS开关
	public static int PC_LANGUAGE_OPT = 0x0f550124; // 切换语言
	public static int PC_MUSIC_SET_VOLNME = 0x0f55060A; // 设置音量大小
	public static int PC_GET_LAST_EVENT = 0x0f550126; // 读取最近发生的导致车辆报错的事件ID
	public static int PC_SET_FORWRD_ID = 0x0f550402; // 设置转发ID
	public static int PC_ENABLE_RE_TRANSMIT = 0x0f550403; // 使能串口转发
	public static int ALL_MC_ENABLE_NAVIGATION = 0x0f3b010b; // 用于使能利用编码器进行导航运算
	public static int ALL_MC_ENABLE_RET_NAV_DATA = 0x0f3b010c; // 用于使能导航运算数据返回
	public static int ALL_MC_CLEAR_NAV_DATA = 0x0f3b010d; // 导航数据清零
	public static int GET_ALL_MC_ENABLE_RET_NAV_DATA = 0x003b010c; // 用于返回导航运算数据
	public static int PC_GPS_USE_OPT = 0x0f55012A; // R1N的GPS功能开关
	public static int PC_ACTION_USE_OPT = 0x0f55012B; // R1N的遥控行走功能开关
	public static int PC_MUSIC_BUF_INFO = 0x0f550601; // 读取音乐存储空间的大小
	public static int PC_MUSIC_WRITE_ADDRESS = 0x0f550604; // 设置写入指针偏移量
	public static int PC_MUSIC_WRITE_DATA = 0x0f550605; // 设置写入音乐
	public static int PC_MUSIC_PALY_MUSIC = 0x0f550609; // 播放指定的音乐
	public static int PC_BT_PASSWORD = 0x0f550306; // 操作蓝牙密码
	public static int PC_BT_CHECKPASSWORD = 0x0f550307; // 校验密码
	public static int PC_SHORTCUT_SWITCH = 0x0f55012E; // 读写快捷操作方式开关
	public static int PC_MUSIC_GET_MUSIC_NUM = 0x0f550608; // 获取有效的音乐条数，
	public static int PC_MUSIC_ADD_MUSIC = 0x0f55060B; // 获取有效的音乐条数，
	public static int PC_MUSIC_SET_SEEK = 0x0f55060C; // 设置写入指针位置
	public static int PC_MUSIC_GET_FINFO = 0x0f55060D; // 获取文件信息
	public static int PC_MUSIC_WRITE_FILE = 0x0f55060E; // 写入音乐数据
	public static int PC_MUSIC_ADD_END = 0x0f55060F; // 结束单段音乐下载

	public static int DFU_COM_CREAT_FILEE = 0x0f120201;// 创建(打开)固件文件
	public static int DFU_COM_SEE_FILEK = 0x0f120202; // 设置固件写入指针位置
	public static int DFU_COM_WRIT_FILEE = 0x0f120203;// 固件写入文件数据
	public static int DFU_COM_CLOSE = 0x0f120204;// 固件关闭文件
	public static int DFU_COM_DEL_FILE = 0x0f120205;// 固件删除指定文件
	public static int ALLMC_GET_CONFIGRATION = 0x0f3B0206;// 读取配置信息
	public static int ALLMC_SET_CONFIGRATION = 0x0f3B0207;// 设置配置参数
	public static int PC_MUSIC_GET_THEME_ID = 0x0f550611; // 获取音乐主题ID号

	public static int SYSTEM_ACTIVATION = 0x0f55000A; // 获取音乐主题ID号
	public static int SYSTEM_FACTORY_RESET = 0x0f55000F; // 恢复出厂设置A
	
	public static int PC_SILDE_SWITCH  = 0x0f550A02 ; //滑行驱动开关设置，数据帧写入，远程帧读取
	
	public static int PC_HALL_LEVEL = 0x0f550A03 ; //设置油门灵敏度。数据帧写入，远程帧读取
	
	public static int PC_LOG_GET_LOG_NUM= 0x0f550509; //获取某种类型日志数量
	public static int PC_LOG_GET_LOG_INFO = 0x0f55050A;//获取日志文件信息
	public static int PC_LOG_OPEN_LOG =0x0f55050B;//打开日志文件
	public static int PC_LOG_GET_DATA =0x0f55050C;//读取日志内容
	public static int PC_LOG_CLOSE_LOG =0x0f55050D;//关闭日志文件
	public static int PC_LOG_DEL_LOG =0x0f55050E;//删除日志文件
	
	public static int PC_L1_GET_RIDE_INFO = 0x0f550A06;//获取单次骑行统计数据
	public static int PC_L1_CLEAR_RIDE_INFO = 0x0f550A07;// 清除单次骑行统计数据
	

	public static int PC_TURN_ZEROCALI = 0x0f550119;// 校准转向零点，数据帧控制
	
//	public static int ALLMC_GET_CONFIGRATION = 0x0f3B0206;  //读取配置信息
//	public static int PC_L1_CLEAR_RIDE_INFO = 0x0f550A07;// 清除单次骑行统计数据
//	public static int PC_L1_CLEAR_RIDE_INFO = 0x0f550A07;// 清除单次骑行统计数据
//	public static int PC_L1_CLEAR_RIDE_INFO = 0x0f550A07;// 清除单次骑行统计数据
	
	
//	public static int 
	

	// 新手模式，普通模式，未开机
	public enum MODE {
		rookie, general, smoothly, unBoot, bldc , foc
	};

	// 空闲模式,行车模式,自动回零模式,上车角度过大模式,自检模式,锁车报警模式,错误停车模式, 助力模式, 自动行走模式, 睡眠关机,
	// PWMSTOP模式，畅行模式(短把手模式),未知模式
	public enum WORK_MODE {
		idle, drive, zero, largeAngle, chekc, lock, error, carry, remoteControl, shutdown, pomStop, unknown,unlock
	};

	// 车辆型号
	public enum MODEL {
		R1N, R1S, R1AP, R1CF, R1EX, R1Sample, R1T, R1O, V3, R2N, R2S, R2Sample, R2, R2EX,L6,V3C,V3S,V3PRO,R0,V5,V5PLUS
	};

	public enum Metric {
		KM, MILE
	};

	public enum Language {
		System, Chinese_simple, Chinese_traditional, English,Korean
	};

	public static int ByteToInt(int offset, byte data[]) {
		int ret = 0;
		if (data.length >= (offset + 4)) {
			ret |= (data[offset + 3] & 0xFF);
			ret <<= 8;
			ret |= (data[offset + 2] & 0xFF);
			ret <<= 8;
			ret |= (data[offset + 1] & 0xFF);
			ret <<= 8;
			ret |= (data[offset + 0] & 0xFF);
		}
		return ret;
	}

	public static long ByteToLong(int offset, byte data[]) {
		long ret = 0;
		if (data.length >= (offset + 8)) {
			ret |= (data[offset + 7] & 0xFF);
			ret <<= 8;
			ret |= (data[offset + 6] & 0xFF);
			ret <<= 8;
			ret |= (data[offset + 5] & 0xFF);
			ret <<= 8;
			ret |= (data[offset + 4] & 0xFF);
			ret <<= 8;
			ret |= (data[offset + 3] & 0xFF);
			ret <<= 8;
			ret |= (data[offset + 2] & 0xFF);
			ret <<= 8;
			ret |= (data[offset + 1] & 0xFF);
			ret <<= 8;
			ret |= (data[offset + 0] & 0xFF);
		}
		return ret;
	}

	public static long ByteToIntToLong(int offset, byte data[]) {
		long ret = 0;
		if (data.length >= (offset + 4)) {
			ret |= (data[offset + 3] & 0xFF);
			ret <<= 8;
			ret |= (data[offset + 2] & 0xFF);
			ret <<= 8;
			ret |= (data[offset + 1] & 0xFF);
			ret <<= 8;
			ret |= (data[offset + 0] & 0xFF);
		}
		return ret;

	}

	public static short ByteToShort(int offset, byte data[]) {
		short ret = 0;
		if (data.length >= (offset + 2)) {
			ret |= (data[offset + 1] & 0xFF);
			ret <<= 8;
			ret |= (data[offset + 0] & 0xFF);
		}
		return ret;
	}
	
	
	public static int TwoByteToInt(int offset, byte data[]) {
		int ret = 0;
		if (data.length >= (offset + 2)) {
			ret |= (data[offset + 1] & 0xFF);
			ret <<= 8;
			ret |= (data[offset + 0] & 0xFF);
		}
		return ret;
	}
	

	public static byte[] SignedIntToByte(int data) {
		byte[] bytes = new byte[4];
		bytes[3] = (byte) (data >> 24);
		bytes[2] = (byte) (data >> 16);
		bytes[1] = (byte) (data >> 8);
		bytes[0] = (byte) (data);
		return bytes;
	}

	public static byte[] SignedShortToByte(short data) {
		byte[] bytes = new byte[2];
		bytes[1] = (byte) (data >> 8);
		bytes[0] = (byte) (data);
		return bytes;
	}

	public static MODE IntToMode(int mode) {
		MODE Mode;
		if ((mode & (1 << 4)) != 0) {
			Mode = MODE.rookie;
		} else if ((mode & (1 << 5)) != 0) {
			Mode = MODE.general;
		} else if ((mode & (1 << 6)) != 0 && (mode & (1 << 7)) != 0) {
			Mode = MODE.smoothly;
		} else
			Mode = MODE.unBoot;
		return Mode;
	}

	public static MODE IntToModeWithL6(int mode) {
		MODE Mode;
		//Log.e("mode",Common.ByteToString(Common.intToByteArray1(mode)));
		if ((mode & 0x0F) != 0) {
			Mode = MODE.bldc;	
		} else
			Mode = MODE.foc;
		return Mode;
	}
	
	public static WORK_MODE IntToWorkModeWithL6(int mode) {
		WORK_MODE work_Mode;
		if ((mode & 0xF0) != 0) {
			work_Mode = WORK_MODE.lock;	
		} else
			work_Mode = WORK_MODE.unlock;
	
		return work_Mode;
	}
	
	public static WORK_MODE IntToWorkMode(int mode) {
		WORK_MODE work_Mode;
		mode &= 0x0F;
		switch (mode) {
		case 0x00:
			work_Mode = WORK_MODE.idle;
			break;
		case 0x01:
			work_Mode = WORK_MODE.drive;
			break;
		case 0x02:
			work_Mode = WORK_MODE.zero;
			break;
		case 0x03:
			work_Mode = WORK_MODE.largeAngle;
			break;
		case 0x04:
			work_Mode = WORK_MODE.chekc;
			break;
		case 0x05:
			work_Mode = WORK_MODE.lock;
			break;
		case 0x06:
			work_Mode = WORK_MODE.error;
			break;
		case 0x07:
			work_Mode = WORK_MODE.carry;
			break;
		case 0x08:
			work_Mode = WORK_MODE.remoteControl;
			break;
		case 0x09:
			work_Mode = WORK_MODE.shutdown;
			break;
		case 0x10:
			work_Mode = WORK_MODE.pomStop;
			break;
		default:
			work_Mode = WORK_MODE.unknown;
			break;
		}
		return work_Mode;
	}

	public static MODEL ByteToModel(byte[] data) {
		MODEL Model = null;
		if (data.length >= 108) {
			switch (data[107]) {
			case 0x00:
				switch (data[104]) {
				case 0x00:
					Model = MODEL.R1N;
					break;
				case 0x01:
					Model = MODEL.R1S;
					break;
				case 0x02:
					Model = MODEL.R1CF;
					break;
				case 0x03:
					Model = MODEL.R1AP;
					break;
				case 0x04:
					Model = MODEL.R1EX;
					break;
				case 0x05:
					Model = MODEL.R1Sample;
					break;
				case 0x06:
					Model = MODEL.R1T;
					break;
				case 0x07:
					Model = MODEL.R1O;
					break;
				}
				break;
			case 0x01:
				Model = MODEL.V3;
				switch (data[104]) {
				case 0x01:
					Model = MODEL.V3C;
					break;
				case 0x02:
					Model = MODEL.V3PRO;
					break;
				case 0x03:
					Model = MODEL.V3S;
					break;
				
				}		
				break;
			case 0x02:
				Model = MODEL.R2;
				switch (data[104]) {
				case 0x01:
					Model = MODEL.R2;
					break;
				case 0x04:
					Model = MODEL.R2EX;
					break;
				// case 0x00:
				// Model = MODEL.R2N;
				// break;
				// case 0x01:
				// Model = MODEL.R2S;
				// break;
				// case 0x05:
				// Model = MODEL.R2Sample;
				// break;
				}
				break;
			case 0x03:
				Model = MODEL.R0;
				
				break;
			case 0x05:
				Model = MODEL.V5;
				switch (data[104]) {
				case 0x01:
					Model = MODEL.V5PLUS;
					break;
				}
				break;
			case 0x06:
				Model = MODEL.L6;	
				break;
			}
		}
		return Model;
	}

	// public static String IntToModeStr(int mode) {
	// String mode_str = "";
	// String mode_list[] = { "空闲模式", "行车模式", "自动回零模式", "上车角度过大模式", "自检模式",
	// "锁车报警模式", "错误停车模式", "助力模式", "自动行走模式", "睡眠关机", "PWMSTOP模式" };
	//
	// // 新手模式
	// if ((mode & (1 << 4)) != 0) {
	// mode_str = "新手模式 ";
	// return mode_str;
	// }
	// // 普通模式
	// else if ((mode & (1 << 5)) != 0) {
	// mode &= 0x0F;
	// if ((mode & 0x0F) <= 10)
	// mode_str = mode_list[mode];
	// else
	// mode_str = "未知工作模式";
	// } else
	// mode_str = "未开机 ";
	//
	// return mode_str;
	// }

	public static String ByteToString(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
		if (data != null && data.length > 0) {
			// final StringBuilder stringBuilder = new
			// StringBuilder(data.length);
			for (byte byteChar : data)
				stringBuilder.append(String.format("%02X ", byteChar));
		}
		return stringBuilder.toString();
	}
	
	
	public static String ByteToString(byte[] data,int length) {
		StringBuilder stringBuilder = new StringBuilder();
		if (data != null && data.length > 0) {
			// final StringBuilder stringBuilder = new
			// StringBuilder(data.length);
			for (int i = 0;i<length;i++)
				stringBuilder.append(String.format("%02X ", data[i]));
		}
		return stringBuilder.toString();
	}
	

	public static String ByteToStringReverse(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
		if (data != null && data.length > 0) {
			// final StringBuilder stringBuilder = new
			// StringBuilder(data.length);
			for (int i = (data.length - 1); i >= 0; i--) {
				byte byteChar = data[i];
				stringBuilder.append(String.format("%02X", byteChar));
			}
		}
		return stringBuilder.toString();
	}

	public static String getToken() {
		return AES.encrypt(Common.TOKEN + "@" + TimestampTool.getTimestamp());
	}
	
	public static byte[] intToByteArray1(int i) {   
		  byte[] result = new byte[4];   
		  result[0] = (byte)((i >> 24) & 0xFF);
		//必须把我们要的值弄到最低位去，有人说不移位这样做也可以， result[0] = (byte)(i  & 0xFF000000);
		//，这样虽然把第一个字节取出来了，但是若直接转换为byte类型，会超出byte的界限，出现error。
		// 再提下数//之间转换的原则（不管两种类型的字节大小是否一样，原则是不改变值，内存内容可能会变，比如int转为
		// float肯定会变）所以此时的int转为byte会越界，只有int的前三个字节都为0的时候转byte才不会越界。虽
		// 然 result[0] = (byte)(i  & 0xFF000000); 这样不行，但是我们可以这样 result[0] = (byte)((i  & 0xFF000000) >>24);
		  result[1] = (byte)((i >> 16) & 0xFF);
		  result[2] = (byte)((i >> 8) & 0xFF); 
		  result[3] = (byte)(i & 0xFF);
		  return result;
		}

	public static int ByteToErrorIdWithL6(byte[] data) {
		// TODO 自动生成的方法存根
		int id = 1;
		int res = ByteToInt(0, data);
		while(res>0){
			
			if(res % 2  == 1){
				
				return id; 
				
			}
				
			id ++;	
			res = res /2 ;
		}
		
		
		return 0;
	}
	
	public static int IntToErrorIdWithL6(int res) {
		// TODO 自动生成的方法存根
		int id = 1;
		//int res = ByteToInt(0, data);
		while(res>0){
			
			if(res % 2  == 1){
				
				return id; 
				
			}
				
			id ++;	
			res = res /2 ;
		}
		
		
		return 0;
	}
	
	
	
	public static boolean IsCarTypeBiggerThanInputType(String carType,String Type){
		
		
		if(carType.length() == 1)
			return false;
		
		
		
		
		if(carType.substring(0,1).compareTo(Type)>0){
			Log.e("cartype", "BiggerThanInputType cartype"+carType);
			return true;
			
			
		
		}
		else 
			return false;
		
	
		
		
	}
	
	
	public static boolean IsCarTypeBelongToInputType(String carType,String Type){
		
	
			
		if(Type.equals("0")){
			
			if(carType.length() == 1)
				return true;
			else
				return false;
			
		}else{
			
			if(carType.substring(0,1).equals(Type)&&carType.length()==2)
				return true;
			else
				return false;
		
		}
		

		
		
	}
	
	
	
	
	
	public static int compareVersion(String s1, String s2) throws Exception {
		
		   //  public int compare(String s1, String s2){
		         if( s1 == null && s2 == null )
		             return 0;
		         else if( s1 == null )
		             return -1;
		         else if( s2 == null )
		             return 1;

		         String[]
		             arr1 = s1.split("[^a-zA-Z0-9]+"),
		             arr2 = s2.split("[^a-zA-Z0-9]+")
		         ;

		         int i1, i2, i3;

		         for(int ii = 0, max = Math.min(arr1.length, arr2.length); 
		                 ii <= max; ii++){
		             if( ii == arr1.length )
		                 return ii == arr2.length ? 0 : -1;
		             else if( ii == arr2.length )
		                 return 1;

		             try{
		                 i1 = Integer.parseInt(arr1[ii]);
		             }
		             catch (Exception x){
		                 i1 = Integer.MAX_VALUE;
		             }

		             try{
		                 i2 = Integer.parseInt(arr2[ii]);
		             }
		             catch (Exception x){
		                 i2 = Integer.MAX_VALUE;
		             }

		             if( i1 != i2 ){
		                 return i1 - i2;
		             }

		             i3 = arr1[ii].compareTo(arr2[ii]);

		             if( i3 != 0 )
		                 return i3;
		         }

		         return 0;
		     
			}
			
	
	
	public static boolean isInstallByread(String packageName)  
    {  
        return new File("/data/data/" + packageName).exists();  
    } 
	
	
	
	
	
	
	
}