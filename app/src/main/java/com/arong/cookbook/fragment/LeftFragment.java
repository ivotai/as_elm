package com.arong.cookbook.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arong.cookbook.activity.HomePageActivity;
import com.arong.cookbook.util.ConfigUtil;
import com.arong.cookbook.util.Tools;
import com.arong.hungry.app.R;

/**
 * @author mrjianrong
 * 
 *         功能说明：测滑菜单 fragment
 * 
 *         创建时间：2017-5-27 下午2:37:57
 */
@SuppressLint("NewApi")
public class LeftFragment extends Fragment implements View.OnClickListener {

	private View currentView;
	private ImageView iv_login;
	private TextView bt_abouts, bt_gift, bt_invitation, bt_orders, btn_time,
			tv_sysVersion, tv_userName, tv_quit;
	private Drawable head_iocn;
	private SlidingPaneLayout slidingPaneLayout;

	public View getCurrentView() {
		return currentView;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		currentView = inflater
				.inflate(R.layout.fragment_left, container, false);
		slidingPaneLayout = (SlidingPaneLayout) getActivity().findViewById(
				R.id.slidingpanellayout);
		
		bt_abouts = (TextView) currentView.findViewById(R.id.btn_abouts);
		bt_gift = (TextView) currentView.findViewById(R.id.btn_gift);
//		btn_time = (TextView) currentView.findViewById(R.id.btn_time);
		tv_quit = (TextView) currentView.findViewById(R.id.tv_quit);
//		tv_sysVersion = (TextView) currentView.findViewById(R.id.tv_sysVersion);
		tv_userName = (TextView) currentView.findViewById(R.id.tv_userName);
		bt_invitation = (TextView) currentView
				.findViewById(R.id.btn_invitation);
		iv_login = (ImageView) currentView.findViewById(R.id.iv_login);
		initRoundImage();
		bt_orders = (TextView) currentView.findViewById(R.id.btn_order);
		bt_abouts.setOnClickListener(this);
		bt_gift.setOnClickListener(this);
		bt_invitation.setOnClickListener(this);
		bt_orders.setOnClickListener(this);
		iv_login.setOnClickListener(this);
		tv_quit.setOnClickListener(this);
//		btn_time.setOnClickListener(this);
		return currentView;
	}

	@SuppressWarnings("deprecation")
	private void initRoundImage() {
		Tools tools = new Tools();
		iv_login.setBackgroundDrawable(new BitmapDrawable(tools.toRoundBitmap(
				getActivity(), R.drawable.logo)));
		iv_login.getBackground().setAlpha(0);
		iv_login.setImageBitmap(tools.toRoundBitmap(getActivity(),
				R.drawable.logo));
		// 存储时间：
//		long read = ConfigUtil.read(getActivity(), "times", 0L);
//		if (101L == read) {
//			btn_time.setVisibility(View.GONE);
//			tv_sysVersion.setText("版本：永久免费版");
//			tv_userName.setText("VIP 会员");
//		} else {
//			timeManager();// 倒计时：
//		}
	}

	private void timeManager() {
		long read = ConfigUtil.read(getActivity(), "times",
				System.currentTimeMillis());
		long ll = read - System.currentTimeMillis();
		// 这个是原生的倒计时类，第一个参数是总毫秒数，第二个是倒计时的毫秒数。
		new CountDownTimer(ll, 1) {

			@Override
			public void onTick(long mss) {

				// 倒计时中的方法

				String time = formatDuring(mss);
//				btn_time.setEnabled(false);
				btn_time.setText("免费体验倒计时：\n" + time+"\n（ 点击解锁永久版 ）");
			}

			@Override
			public void onFinish() {
				// 倒计时结束后的方法
				btn_time.setText("免费试用期结束！");
				new AlertDialog.Builder(getActivity())
				        .setIcon(R.drawable.about)
						.setTitle("免费试用结束：")
						.setCancelable(false)
						.setMessage(
								"         尊敬的用户您好，您当前使用的-凯勃荣快捷点餐系统 免费试用版15天已结束，如您还需继续使用，请购买:\n\n凯勃荣快捷点餐系统——永久版")
						.setPositiveButton(" 确定 ", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								System.exit(0);
							}
						}).show();
			}

		}.start();

	}

	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		return days + " 天 " + hours + " 小时 " + minutes + " 分 " + seconds
				+ " 秒 ";
	}

	@SuppressLint("CommitTransaction")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		FragmentTransaction ft = getFragmentManager().beginTransaction();// 开始一个事物
		switch (v.getId()) {
		case R.id.btn_order:
			Fragment homeFragment = new HomeFragment();
			ft.replace(R.id.slidingpane_content, homeFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			((HomePageActivity) getActivity()).getSlidingPaneLayout().closePane();
			break;

		case R.id.btn_gift:
			Fragment giftFragment = new PayCenterFragment();
			ft.replace(R.id.slidingpane_content, giftFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			((HomePageActivity) getActivity()).getSlidingPaneLayout().closePane();
			break;
		case R.id.btn_invitation:
			Fragment invitationFragment = new MenuFragment();
			ft.replace(R.id.slidingpane_content, invitationFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			((HomePageActivity) getActivity()).getSlidingPaneLayout().closePane();
			break;
		case R.id.btn_abouts:
			Fragment aboutsFragment = new AboutSystemFragment();
			ft.replace(R.id.slidingpane_content, aboutsFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
			((HomePageActivity) getActivity()).getSlidingPaneLayout().closePane();
			break;
		case R.id.tv_quit:
			// 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			// 设置Title的图标
			builder.setIcon(R.drawable.about);
			// 设置Title的内容
			builder.setTitle("退出系统：");
			builder.setCancelable(false);
			// 设置Content来显示一个信息
			builder.setMessage("确定退出系统吗？");
			// 设置一个PositiveButton
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							System.exit(0);
						}
					});
			// 设置一个NegativeButton
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});

			builder.show();
			
			break;
//		 case R.id.btn_time:
//			 new AlertDialog.Builder(getActivity())
//				.setTitle("解锁永久版：")
//				  .setIcon(R.drawable.about)
//				.setCancelable(false)
//				.setMessage(
//						"请联系当地代理商，获取永久版ID")
//				.setPositiveButton(" 确定 ", null).show();
//		 break;
//		 case R.id.btn_time:
//			 break;
		}
	}
}
