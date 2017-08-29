package com.example.ding.testble;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据校验工具
 * 
 * @author whl
 *
 */
public class DataVerificationUtil {
	/**
	 * 判断对象是否为null
	 * 
	 * @param obj
	 *            对象
	 * @return true 非null false 为null
	 */
	public static boolean is_null(Object... objs) {
		for (Object obj : objs) {
			if (null != obj) {
				if (obj instanceof String) {
					String s = String.valueOf(obj);
					if ("".equals(s.trim()))
						return false;
				}else if(obj instanceof Map) {
					Map map = (Map) obj;
					if(map.size() == 0)
						return false;
				}
			} else
				return false;
		}
		return true;
	}

	/**
	 * 通过反射校验对象中的属性
	 * 
	 * @param obj
	 *            被校验的对象
	 * @param type
	 *            校验方式：type为true propertie_names中的属性名为需要校验的; type为false
	 *            propertie_names中的属性名为不需要校验的
	 * @param propertie_names
	 *            属性名数组
	 * @return 未通过校验的属性名数组
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static List<String> checkObjectProperties_notNull(Object obj, boolean type, String... propertie_names) {
		List<String> file_names = new ArrayList<String>();
		if(obj == null) {
			file_names.add("obj");
			return file_names;
		}
		String[] names = null;
		if(propertie_names != null) {
			names = new String[propertie_names.length];
			for(int i = 0;i < propertie_names.length;i++) {
				names[i] = propertie_names[i].toLowerCase();
			}
		}else {
			names = new String[0];
		}
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				String field_name = field.getName().toLowerCase();
				if (in_array(names, field_name) && true == type){
					if (!is_null(field.get(obj))) {
						file_names.add(field_name);
					}
				}
				if(in_array(names, field_name) == false && false == type) {
					if (!is_null(field.get(obj))) {
						file_names.add(field_name);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out.println("校验结果：" );
		for(String str:file_names)
			System.out.print(str+",");
		return file_names;
	}

	/**
	 * 判断是否存在于数组中
	 * @param array 
	 * @param param
	 * @return true 存在   false 不存在
	 */
	public static boolean in_array(String array[],String param) {
		for(String str:array) {
			if(str.equals(param))
				return true;
		}
		return false;
	}
	/**
	 * 判断字符串是否符合邮箱地址
	 * 
	 * @param param
	 *            被校验的数据
	 * @return true 符合 false 不符合
	 */
	public static boolean is_email(String param) {
		if (is_null(param)) {
			Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
			Matcher matcher = pattern.matcher(param);
			if (matcher.find())
				return true;
			else
				return false;
		}
		return false;
	}

	/**
	 * 判断字符串是否符合手机号码
	 * 
	 * @param param
	 *            被校验的数据
	 * @return true 符合 false 不符合
	 */
	public static boolean is_mobile(String param) {
		if (is_null(param)) {
			Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher matcher = pattern.matcher(param);
			if (matcher.find())
				return true;
			else
				return false;
		}
		return false;
	}
	
	/**
	 * 对map进行校验
	 * @param map 需要校验的map
	 * @param type 校验方式：type为true propertie_names中的属性名为需要校验的; type为false
	 *            propertie_names中的属性名为不需要校验的
	 * @param propertie_names 属性名数组
	 * @return 未通过校验的属性名数组
	 */
	public static List<String> checkMap(Map<String, Object> map, boolean type, String... propertie_names) {
		List<String> file_names = new ArrayList<String>();
		Set<String> keys = map.keySet();
		for(String propertie_name:propertie_names) {
			//如果map的key集合中不包含  要检查的属性
			if (!keys.contains(propertie_name) && true == type) {
				file_names.add(propertie_name);
			}
			if(keys.contains(propertie_name) && false == type) {
				keys.remove(propertie_name);
			}
		}
		for (String key : keys) {
			if (!is_null(map.get(key))) {
				file_names.add(key);
			}
		}
		return file_names;
	}
	
}
