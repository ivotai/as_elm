/**
 * 
 */
package com.arong.cookbook.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 功能介绍：自定义吐司
 * @author JianRong
 * @date2016-12-30上午11:09:00
 */
public class T {
	public static void show(Context context,String str){
		Toast.makeText(context, str, 0).show();
	}
}
