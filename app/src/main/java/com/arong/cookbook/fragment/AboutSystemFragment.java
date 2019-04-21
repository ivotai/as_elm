package com.arong.cookbook.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arong.hungry.app.R;

/**
 * @author mrjianrong
 * 
 *         功能说明：关于系统 fragment
 * 
 *         创建时间：2017-5-27 下午2:33:26
 */
@SuppressLint("NewApi")
public class AboutSystemFragment extends Fragment implements OnClickListener {
	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_aboutsystem, container,
				false);

		tv_meanu = (TextView) mView.findViewById(R.id.tv_meanu);
		tv_title = (TextView) mView.findViewById(R.id.tv_title);
		slidingPaneLayout = (SlidingPaneLayout) getActivity().findViewById(
				R.id.slidingpanellayout);
		
		setListener();
		return mView;
	}

	private void setListener() {
		tv_meanu.setOnClickListener(this);
		tv_title.setText("关 于 系 统");
	}

	private TextView tv_meanu,tv_title;
	private SlidingPaneLayout slidingPaneLayout;

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

		default:
			break;
		}
	}
}
