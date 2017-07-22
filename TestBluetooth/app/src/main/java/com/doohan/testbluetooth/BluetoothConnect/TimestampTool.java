package com.doohan.testbluetooth.BluetoothConnect;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;





/**
 * 时间与时间戳转换工具
 */
public class TimestampTool {

	
	
	public static String DateToString(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");  
		String str=sdf.format(date);  
		return str;
	}
	
	/**
	 * 
	 * TODO:输入一个时间，获取该时间的时间戳
	 * 
	 * @param @param dateString
	 * @param @return
	 * 
	 */
	public static long string2Timestamp(String dateString) {
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(dateString);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long temp = date1.getTime();// JAVA的时间戳长度是13位
		return temp;
	}

	//把字符串转为日期  
    public static Date string2Date(String strDate) throws Exception  
    {  
        Date df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);  
        return df;  
    }  
	
	
	public static long string2Timestamp2(String dateString) {
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm")
					.parse(dateString);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long temp = date1.getTime();// JAVA的时间戳长度是13位
		return temp;
	}
	
	/**
	 * 获取系统当前时间
	 * 
	 * @throws ParseException
	 * 
	 * @time 2014-4-24 下午5:16:01
	 */
	public static long getCurrentTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		return string2Timestamp(df.format(new Date()));// new Date()为获取当前系统时间
	}

	
	//比较两个时间戳的间隔是否大于n分钟
	
	
	public static boolean  CompareTimestamp(long Timestampone,long Timestamptwo,int n) {
		
		if(Math.abs(Timestampone-Timestamptwo)/ (1000 * 60)>=n)
			return true;
		else 
			return false;
		 
	}

	
	
	
	
	/**
	 * 获取系统时间戳
	 */
	public static String getTimestamp() {
		DecimalFormat mDecimalFormat = new DecimalFormat("#.######");
		String timestamp = mDecimalFormat
				.format(System.currentTimeMillis() / 1000.0);
		return timestamp;
	}

	/**
	 * 服务器时间转本地时间
	 */
	public static String transformTimeYear(String time) {
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss+0800");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			time = format1.format(format.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return time;
	}

	public static String transformTimeMonth(String time) {
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss+0800");
		SimpleDateFormat format1 = new SimpleDateFormat("yy-MM-dd HH:mm");
		try {
			time = format1.format(format.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return time;
		}
		return time;
	}

	
	public static String transformTimeMonth2(String time) {
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss+0800");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			time = format1.format(format.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return time;
		}
		return time;
	}

	
	// 时间yyyy-MM-dd HH:mm 转时间戳（秒）
	public static long StringToTimestamp(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		return date.getTime() / 1000;
	}
	
	
	public static CharSequence getTime(String string, String string2) {
	
		String a = string.replace("T", " ").replace("-", ".").substring(0, 19);
		String b = string2.replace("T", " ").replace("-", ".").substring(0, 19);

//		long start = TransTimeString(a);
//		long end = TransTimeString(b);
//		long now = System.currentTimeMillis();

		
		a = a.substring(0, 10);
		b = b.substring(0, 10);
		return a + "-" + b;
	}
	
//	public static long TransTimeString(String days) { // long current,
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/.MM/.dd");// HH:mm:ss");
//		Date dates = null;
//		try {
//			dates = sdf.parse(days);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		long time = dates.getTime();
//		return time;
//	}
	
}
