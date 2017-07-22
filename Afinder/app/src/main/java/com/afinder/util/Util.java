package com.afinder.util;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Util {
	/**
	 * WebService公共方法,各个activity的公共方法,公共属性.
	 * */
	public static  String StartDate="";
	public static  String EndDate="";
	public static  String Date="";
	public static  String RestaurantName="";
	public static  String GoodType="";
	//public final static String CallInfo_Contact = "CallInfo/CallInfo_Contact.xml";
	// webservice方法	
	// 访问网络时的状态
	public static final int DOWNLOAD_START = 0;// 访问开始
	public static final int DOWNLOAD_END = 1;// 结束
	public static final int DOWNLOAD_OK = 2;// 正常
	public static final int DOWNLOAD_ERROR = 3;// 错误
	public static final int DOWNLOAD_OFFLINE = 4;// 离线
	public static final int DOWNLOAD_DISCONNECT = 5;// 未连上网络	
	//更换被点击图标的背景状态
	public static final int FirstPage=6;
	public static final int CallIndoPage=7;
	public static final int SetPage=8;
	public static final int hasItemNow=9;
	// 网络状态.1通畅,0未连上
	public static int NetState = 0;
	

	//静态参数
	public static String pathfile="";
	public static String CurrentPage="";
	public static String CurrentItem="FirstPage";
	public static int Position=0;
	
	/**
	 * 获取服务器地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getServerAddress(Context context) {
		SharedPreferences mySharedpreferences;
		mySharedpreferences = context.getSharedPreferences("com.wdgolf", 0);
		return mySharedpreferences.getString("server_address",
				"http://202.200.112.150:8080/");// 外网
	}
	// 网络状态.1通畅,0未连上
		//public static int NetState = 0; 				
	/**
	 * 设置服务器地址
	 *    
	 * @param context
	 * @param server_address
	 */
	public static void setServerAddress(Context context, String server_address) {
		SharedPreferences mySharedpreferences;
		mySharedpreferences = context.getSharedPreferences("com.wdgolf", 0);
		mySharedpreferences.edit().putString("server_address", server_address)
				.commit();
	}
	//修改密码
		public final static String Changedpwd = "changedpwd";
	/**
	 * 设置用户登录名
	 * 
	 * @param context
	 * @param server_address
	 */
	public static void SaveUserDataToLocal(Context pContext, String pUserName,String pUserPassword) {
		SharedPreferences mySharedpreferences;
		mySharedpreferences = pContext.getSharedPreferences("com.example.golfmanager", 0);
		Editor editor = mySharedpreferences.edit();//获取编辑器
		editor.putString("UserName", pUserName);
		editor.putString("UserPassword", pUserPassword);
		editor.commit();//提交修改
	}

}
