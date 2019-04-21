package com.arong.cookbook.activity;

import java.util.Random;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arong.cookbook.util.ConfigUtil;
import com.arong.cookbook.util.T;
import com.arong.cookbook.util.Tools;
import com.arong.hungry.app.R;

@TargetApi(Build.VERSION_CODES.ECLAIR)
public class WelcomeActivity extends Activity {

	private ImageView mSplashItem_iv;
	private TextView tv_systemId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		
		setContentView(R.layout.activtiy_welcome);
		mSplashItem_iv = (ImageView) findViewById(R.id.splash_loading_item);
		tv_systemId = (TextView) findViewById(R.id.tv_systemId);
		ImageView imageView_logo = (ImageView) findViewById(R.id.imageView_logo);

		Tools tools = new Tools();
		imageView_logo.setImageBitmap(tools.toRoundBitmap(this, R.drawable.logo));

		if (null == ConfigUtil.read(this, "sysID", null)) {
			setRandomID();
		}
		
		tv_systemId.setText("系统 ID：" + ConfigUtil.read(this, "sysID", null));
		startSplash();
		
	}

	private void startMainAvtivity() {
		startActivity(new Intent(WelcomeActivity.this, HomePageActivity.class));
		overridePendingTransition(0, 0);
		WelcomeActivity.this.finish();

	}

	private void setRandomID() {
		int nextInt = 100 + new Random().nextInt(100);
		ConfigUtil
				.write(WelcomeActivity.this, "sysID", "20170928010" + nextInt);
	}

	/**
	 * 跳转到主界面
	 */
	private void startSplash() {
		Animation translate = AnimationUtils.loadAnimation(this,
				R.anim.splash_loading);
		translate.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {		
				startMainAvtivity();
				//存储时间：
//				long read = ConfigUtil.read(WelcomeActivity.this, "times", 0L);
//				if (101L==read) {
//					startMainAvtivity();
//
//				}else if  (0==read) {
//					long t = System.currentTimeMillis();
//			    // 免费试用15天
//					long timeMillis=t+1296000000;
//				//  long timeMillis=t+19000;
//					ConfigUtil.write(WelcomeActivity.this, "times", timeMillis);
//
//					AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
//					builder.setTitle("欢迎使用：");
//					builder.setCancelable(false);
//					builder.setMessage("         尊敬的用户您好，欢迎使用-凯勃荣快捷点餐系统，您当前使用的软件版本为：免费试用版，试用时长为15天，若您想永久使用该软件，请购买:凯勃荣快捷点餐系统——永久版\n\n                   祝您畅玩愉快！");
//					builder.setPositiveButton(" 确定 ", new OnClickListener() {
//
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							startMainAvtivity();
//						}
//					});builder.show();
//				} else if (System.currentTimeMillis() > read) {
//					showIdDialog();
//				} else {
//					startMainAvtivity();
//				}
}
		});
		mSplashItem_iv.setAnimation(translate);
	}

	protected void showIdDialog() {

		final EditText et2 = new EditText(WelcomeActivity.this);
		et2.setHint("请输入永久版ID");

		new AlertDialog.Builder(WelcomeActivity.this)
				.setTitle("免费试用结束：")
				.setMessage(
						"         尊敬的用户您好，您当前使用的-凯勃荣快捷点餐系统-免费试用版15天已结束，如您还需继续使用，请购买:\n凯勃荣快捷点餐系统——永久版")
				.setView(et2)
				.setPositiveButton(" 解锁永久版 ", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (!et2.getText().toString().trim().matches("\\d*")) {
							T.show(WelcomeActivity.this, "您输的 ID无效，请重新输入 ！");
							showIdDialog();
							return;
						}
						
						String idStr = ConfigUtil.read(WelcomeActivity.this,
								"sysID", "20170928010808").substring(11);
						int id = (int) (Math.pow(Integer.parseInt(idStr), 2) + 1);
						String ids = String.valueOf(id).substring(1);
						if (et2.getText().toString().trim().equals(ids)) {
							ConfigUtil.write(WelcomeActivity.this, "times",
									101L);
							T.show(WelcomeActivity.this, "恭喜您，解锁成功 ！");
							startMainAvtivity();
						} else {
							T.show(WelcomeActivity.this, "您输的 ID不正确，请重新输入 ！");
							showIdDialog();
						}
						
					}
				}).show();

	}
}
