package com.afinder.util;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Util {
	/**
	 * WebService��������,����activity�Ĺ�������,��������.
	 * */
	public static  String StartDate="";
	public static  String EndDate="";
	public static  String Date="";
	public static  String RestaurantName="";
	public static  String GoodType="";
	//public final static String CallInfo_Contact = "CallInfo/CallInfo_Contact.xml";
	// webservice����	
	// ��������ʱ��״̬
	public static final int DOWNLOAD_START = 0;// ���ʿ�ʼ
	public static final int DOWNLOAD_END = 1;// ����
	public static final int DOWNLOAD_OK = 2;// ����
	public static final int DOWNLOAD_ERROR = 3;// ����
	public static final int DOWNLOAD_OFFLINE = 4;// ����
	public static final int DOWNLOAD_DISCONNECT = 5;// δ��������	
	//���������ͼ��ı���״̬
	public static final int FirstPage=6;
	public static final int CallIndoPage=7;
	public static final int SetPage=8;
	public static final int hasItemNow=9;
	// ����״̬.1ͨ��,0δ����
	public static int NetState = 0;
	

	//��̬����
	public static String pathfile="";
	public static String CurrentPage="";
	public static String CurrentItem="FirstPage";
	public static int Position=0;
	
	/**
	 * ��ȡ��������ַ
	 * 
	 * @param context
	 * @return
	 */
	public static String getServerAddress(Context context) {
		SharedPreferences mySharedpreferences;
		mySharedpreferences = context.getSharedPreferences("com.wdgolf", 0);
		return mySharedpreferences.getString("server_address",
				"http://202.200.112.150:8080/");// ����
	}
	// ����״̬.1ͨ��,0δ����
		//public static int NetState = 0; 				
	/**
	 * ���÷�������ַ
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
	//�޸�����
		public final static String Changedpwd = "changedpwd";
	/**
	 * �����û���¼��
	 * 
	 * @param context
	 * @param server_address
	 */
	public static void SaveUserDataToLocal(Context pContext, String pUserName,String pUserPassword) {
		SharedPreferences mySharedpreferences;
		mySharedpreferences = pContext.getSharedPreferences("com.example.golfmanager", 0);
		Editor editor = mySharedpreferences.edit();//��ȡ�༭��
		editor.putString("UserName", pUserName);
		editor.putString("UserPassword", pUserPassword);
		editor.commit();//�ύ�޸�
	}

}
