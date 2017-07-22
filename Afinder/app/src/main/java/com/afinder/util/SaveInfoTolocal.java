package com.afinder.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Xml;

public class SaveInfoTolocal {
	/**
	 * 访问WebService获取球童或教练的xml文件并将其保存在本地,同时获取球童或教练的图片保存在本地.
	 * (使用URLConnection，soap不能将xml保存到本地)
	 * */
	private Context mContext;

	public SaveInfoTolocal(Context pContext) {
		this.mContext = pContext;
	}

	/**
	 * 获取本地路径
	 * **/
	private String getSDPath() {
		SharedPreferences mySharedpreferences;
		mySharedpreferences = mContext.getSharedPreferences("com.afinder", 0);
		String infopath;
		boolean hasSDCard = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if (hasSDCard) {
			infopath = Environment.getExternalStorageDirectory().toString()
					+ File.separator + "xautcate/";

		} else {
			infopath = Environment.getDownloadCacheDirectory().toString()
					+ File.separator + "xautcate/";
		}
		File file = new File(infopath);
		if (!file.exists()) {
			file.mkdir();
		}
		mySharedpreferences.edit().putString("infopath", infopath).commit();
		return infopath;
	}

	public static String getSdCardPath() {
		String infopath;
		boolean hasSDCard = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if (hasSDCard) {
			infopath = Environment.getExternalStorageDirectory().toString()
					+ File.separator + "xautcate/";

		} else {
			infopath = Environment.getDownloadCacheDirectory().toString()
					+ File.separator + "xautcate/";
		}
		File file = new File(infopath);
		if (!file.exists()) {
			file.mkdir();
		}
		return infopath;
	}

	/**
	 * 获取已经保存在本机上的文件和图片父路径。
	 * */
	public static String getSavePath(Context pContext) {
		boolean hasSDCard = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		String infopath;
		if (hasSDCard) {
			infopath = Environment.getExternalStorageDirectory().toString()
					+ File.separator + "xautcate/";

		} else {
			infopath = Environment.getDownloadCacheDirectory().toString()
					+ File.separator + "xautcate/";
		}
		SharedPreferences mySharedpreferences;
		mySharedpreferences = pContext.getSharedPreferences("com.afinder", 0);
		return mySharedpreferences.getString("infopath", infopath).toString();
	}

	/** 返回一个Document对象 */
	private static Document getXml(String pNetXMLDataURL) {
		Document document = null;
		DocumentBuilderFactory documentBF = DocumentBuilderFactory
				.newInstance();
		documentBF.setNamespaceAware(true);
		try {
			DocumentBuilder documentB = documentBF.newDocumentBuilder();
			InputStream inputStream = getSoapInputStream(pNetXMLDataURL); // 具体webService相关
			document = documentB.parse(inputStream);
			inputStream.close();
		} catch (DOMException e) {
			e.printStackTrace();
			return null;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return document;
	}

	/** 返回InputStream对象 */
	private static InputStream getSoapInputStream(String pUrl) {
		InputStream inputStream = null;
		try {
			URL urlObj = new URL(pUrl);
			URLConnection urlConn = urlObj.openConnection();
			urlConn.setRequestProperty("Host", "www.webxml.com.cn"); // 具体webService相关
			urlConn.connect();
			inputStream = urlConn.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	/** 访问远程(WebService)xml数据后返回的xml格式字符串并生成为本地文件 */
	@SuppressLint("NewApi")
	private boolean saveXmlToLocal(Document pDocument, String pXmlName) {
		TransformerFactory transF = TransformerFactory.newInstance();
		try {
			Transformer transformer = transF.newTransformer();
			DOMSource source = new DOMSource(pDocument);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "YES");
			String path = getSDPath() + pXmlName;

			String folder = path.substring(0, path.lastIndexOf("/"));
			File file = new File(folder);
			if (!file.exists()) {
				file.mkdir();
			}
			PrintWriter pw = new PrintWriter(new FileOutputStream(path));
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result);
			System.out.println("生成xml文件成功!");
			return true;
		} catch (TransformerConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (TransformerException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	/**
	 * 获取Webservice的xml文件,并开始保存文件
	 * */
	public boolean startSaveXml(String pServerPath, String pXmlPath) {
		Document document = getXml(pServerPath);
		if (document == null) {
			SharedPreferences mySharedpreferences = mContext
					.getSharedPreferences("com.afinder", 0);
			mySharedpreferences.edit().putString("infopath", "0").commit();// 清空路径
			return false;
		}
		return saveXmlToLocal(document, pXmlPath);
	}

//	/**
//	 * 根据xml信息获取文件信息,并开始保存文件
//	 * **/
//	public boolean startSaveFile(String pServerFolder, String pXmlPath,
//			String pImgFolder) {
//		XmlPullParser xmlParse = Xml.newPullParser();
//		InputStream is;
//		File file;
//		String path = null;
//		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
//		HashMap<String, Object> map;
//		try {
//			path = getSDPath() + pXmlPath;
//			file = new File(path);
//			is = new FileInputStream(file);
//			xmlParse.setInput(is, "utf-8");
//			// 解析xml,获取名称和路径
//			int evnType = xmlParse.getEventType();
//			while (evnType != XmlPullParser.END_DOCUMENT) {
//				switch (evnType) {
//				case XmlPullParser.START_TAG:
//					String tag = xmlParse.getName();
//					// 教练
//					if (tag.equals("FilePath")) {
//						map = new HashMap<String, Object>();
//						String xmlText = xmlParse.nextText();
//						map.put("localpath", pImgFolder + xmlText);
//						map.put("serverpath", pServerFolder + xmlText);
//						list.add(map);
//					}
//					break;
//				case XmlPullParser.END_TAG:
//					break;
//				default:
//					break;
//				}
//				evnType = xmlParse.next();
//			}
//		} catch (FileNotFoundException e) {
//
//			e.printStackTrace();
//			return false;
//		} catch (XmlPullParserException e) {
//			e.printStackTrace();
//			return false;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//		// 获取文件保存在本地
//		String sdk = getSDPath();
//		for (int i = 0; i < list.size(); i++) {
//			String url = list.get(i).get("serverpath").toString();
//			String localpath = sdk + list.get(i).get("localpath").toString();
//			try {
//				FileDownLoadUtil.downLoadFile(url, localpath);
//			} catch (Exception e) {
//				SharedPreferences mySharedpreferences = mContext
//						.getSharedPreferences("com.yrsss", 0);
//				mySharedpreferences.edit().putString("infopath", "0").commit();// 清空路径
//				e.printStackTrace();
//				return false;
//			}
//		}
//		return true;
//	}

	/**
	 * 获取指定网络路径的图片数据字节。
	 * 
	 * **/

	public static byte[] getUrlImage(String pUrlpath) throws Exception {
		URL url = new URL(pUrlpath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(15 * 1000);
		// 别超过15秒。
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			return readStream(inputStream);
		}
		return null;
	}

	/**
	 * 读取网络数据流
	 * 
	 * */
	public static byte[] readStream(InputStream pInStream) throws Exception {
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = pInStream.read(buffer)) != -1) {
			outstream.write(buffer, 0, len);
		}
		outstream.close();
		pInStream.close();
		return outstream.toByteArray();
	}

	/**
	 * 将图片保存在本地
	 * 
	 * @throws Exception
	 * */
	@SuppressWarnings("unused")
	private void savePictureToLocal(String pServerPath, String pPath)
			throws Exception {
		byte[] data = null;
		data = getUrlImage(pServerPath);
		Bitmap bitmap = null;
		if (data.length > 0) {
			// 将图片数据字节转换为图片
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			if (bitmap == null)
				return;
		}
		String filePath = getSDPath() + pPath;
		String folder = filePath.substring(0, filePath.lastIndexOf("/"));
		File file = new File(folder);
		if (!file.exists()) {
			file.mkdir();
		}
		FileOutputStream out;
		file = new File(filePath);
		out = new FileOutputStream(file);
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		out.flush();
		out.close();
		System.out.println("保存图片");
	}

}
