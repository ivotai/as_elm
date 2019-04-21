package com.arong.cookbook.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * @author 高建荣
 *	
 *  功能说明：配置文件操作工具类
 *
 * 2017-5-18 上午10:21:44
 */
public class ConfigUtil {
	private static final String CONFIG_FILE = "ICS_CONF";
	//读取配置参数
	public static String read(Context context,String key,String defValue){
		SharedPreferences sp = context.getSharedPreferences(CONFIG_FILE,0);
		return sp.getString(key, defValue);
	}
	public static long read(Context context,String key,long defValue){
		SharedPreferences sp = context.getSharedPreferences(CONFIG_FILE,0);
		return sp.getLong(key, defValue);
	}
	public static int read(Context context,String key,int defValue){
		SharedPreferences sp = context.getSharedPreferences(CONFIG_FILE,0);
		return sp.getInt(key, defValue);
	}
	public static float read(Context context,String key,float defValue){
		SharedPreferences sp = context.getSharedPreferences(CONFIG_FILE,0);
		return sp.getFloat(key, defValue);
	}
	public static boolean read(Context context,String key,boolean defValue){
		SharedPreferences sp = context.getSharedPreferences(CONFIG_FILE,0);
		return sp.getBoolean(key, defValue);
	}
	//写入配置参数
	public static void write(Context context,String key,String value){
		SharedPreferences sp = context.getSharedPreferences(CONFIG_FILE,0);
		SharedPreferences.Editor editor=sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public static void write(Context context,String key,long value){
		SharedPreferences sp = context.getSharedPreferences(CONFIG_FILE,0);
		SharedPreferences.Editor editor=sp.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static void write(Context context,String key,int value){
		SharedPreferences sp = context.getSharedPreferences(CONFIG_FILE,0);
		SharedPreferences.Editor editor=sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void write(Context context,String key,float value){
		SharedPreferences sp = context.getSharedPreferences(CONFIG_FILE,0);
		SharedPreferences.Editor editor=sp.edit();
		editor.putFloat(key, value);
		editor.commit();
	}	
	public static void write(Context context,String key,boolean value){
		SharedPreferences sp = context.getSharedPreferences(CONFIG_FILE,0);
		SharedPreferences.Editor editor=sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void remove(Context context,String key){
		SharedPreferences sp = context.getSharedPreferences(CONFIG_FILE,0);
		SharedPreferences.Editor editor=sp.edit();
		editor.remove(key);
		editor.commit();
	}
}
