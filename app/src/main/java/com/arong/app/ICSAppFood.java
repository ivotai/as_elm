package com.arong.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.arong.cookbook.image.ImageLoaderConfig;
import com.arong.cookbook.util.Constants;

public class ICSAppFood extends Application {
	private static ICSAppFood instance;

	private String sdCardPath;
	public String appResRoot;
	public static String dbPath = "";

	@Override
	public void onCreate() {
		super.onCreate();
		
		initSD();
		ImageLoaderConfig.initImageLoader(this, Constants.BASE_IMAGE_CACHE);
	}

	private void initSD() {
	
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();
			sdCardPath = sdDir.toString();
		}

		appResRoot = sdCardPath + "/arongFood";
		File rootFile = new File(appResRoot);
		if (!rootFile.exists()) {
			boolean success = rootFile.mkdirs();
			copyFilesFassets(this, dbPath);
		}
		dbPath = appResRoot + "/FOOD.db";
		
		
	}

	/**
	 * 从assets目录中复制整个文件夹内容
	 * 
	 * @param context
	 *            Context 使用CopyFiles类的Activity

	 * @param newPath
	 *            String 复制后路径 如：xx:/bb/cc
	 */
	public void copyFilesFassets(Context context, String newPath) {
		try {
			// 如果是文件
			InputStream is = context.getAssets().open("FOOD.db");
			FileOutputStream fos = new FileOutputStream(new File(newPath));
			byte[] buffer = new byte[1024];
			int byteCount = 0;
			while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取 buffer字节
				fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
			}
			fos.flush();// 刷新缓冲区
			is.close();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
