package com.arong.cookbook.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

import com.arong.hungry.app.R;

/**
 * @author mrjianrong
 * 
 *         功能说明：支付中心 fragment
 * 
 *         创建时间：2017-5-27 下午2:37:38
 */
@SuppressLint("NewApi")
public class PayCenterFragment extends Fragment implements OnClickListener {
	private View mView;

	private SlidingPaneLayout slidingPaneLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_paycenter, container, false);

		initID();
		setListener();
		return mView;
	}

	private TextView tv_meanu, tv_title, tv_zfbPay, tv_wxPay,tv_add_erweima;
	private ImageView iv_pay_zfb, iv_pay_wx;

	private void initID() {
		tv_meanu = (TextView) mView.findViewById(R.id.tv_meanu);
		tv_title = (TextView) mView.findViewById(R.id.tv_title);
		tv_zfbPay = (TextView) mView.findViewById(R.id.tv_zfbPay);
		tv_wxPay = (TextView) mView.findViewById(R.id.tv_wxPay);
		tv_add_erweima = (TextView) mView.findViewById(R.id.tv_add_erweima);
		iv_pay_zfb = (ImageView) mView.findViewById(R.id.iv_pay_zfb);
		iv_pay_wx = (ImageView) mView.findViewById(R.id.iv_pay_wx);

		slidingPaneLayout = (SlidingPaneLayout) getActivity().findViewById(
				R.id.slidingpanellayout);

	}

	private void setListener() {
		tv_meanu.setOnClickListener(this);
		tv_title.setText("支 付 中 心");
		tv_zfbPay.setOnClickListener(this);
		tv_wxPay.setOnClickListener(this);
		iv_pay_zfb.setOnClickListener(this);
		iv_pay_wx.setOnClickListener(this);
		tv_add_erweima.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_meanu:// 菜单开关键
			if (slidingPaneLayout.isOpen()) {
				slidingPaneLayout.closePane();
			} else {
				slidingPaneLayout.openPane();
			}
			break;
		case R.id.iv_pay_zfb:

			break;
		case R.id.iv_pay_wx:

			break;
		case R.id.tv_add_erweima://重新上传二维码
			
			break;
		case R.id.tv_zfbPay:
			tv_zfbPay.setBackground(getResources().getDrawable(R.drawable.pay_zfb_pressed));
			tv_wxPay.setBackground(getResources().getDrawable(R.drawable.pay_wx_normal));
			iv_pay_zfb.setVisibility(View.VISIBLE);
			iv_pay_wx.setVisibility(View.INVISIBLE);
			break;
		case R.id.tv_wxPay:
			tv_zfbPay.setBackground(getResources().getDrawable(R.drawable.pay_zfb_nomal));
			tv_wxPay.setBackground(getResources().getDrawable(R.drawable.pay_wx_pressed));
			iv_pay_zfb.setVisibility(View.INVISIBLE);
			iv_pay_wx.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

}
