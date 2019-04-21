package com.arong.cookbook.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.arong.cookbook.adapter.HomeFoodAdapter;
import com.arong.cookbook.util.ConfigUtil;
import com.arong.cookbook.util.T;
import com.arong.cookbook.widget.stickylistheaders.StickyListHeadersListView;
import com.arong.hungry.app.R;

/**
 * @author mrjianrong
 * 
 *         功能说明：首页fragment
 * 
 *         创建时间：2017-5-27 上午9:50:30
 */
@SuppressLint("NewApi")
public class HomeFragment extends Fragment implements
		android.view.View.OnClickListener, OnItemClickListener {
	private String Name = "品味川菜";
	private View mView;

	private StickyListHeadersListView lv_foodlist;

	private LinearLayout ll_foodpay, ll_fragment_home;

	private Context mContext;
	private TextView order_cart;

	private TextView tv_meanu, tv_title, tv_commit;

	private SlidingPaneLayout slidingPaneLayout;
	private HomeFoodAdapter mAdapter;
	private PopupWindow popWindow;
	private Dialog dialog;
	private ImageView iv_pay_zfb;
	private ImageView iv_pay_wx;
	private TextView tv_zfbPay;
	private TextView tv_wxPay;
	private TextView tv_howmoney;
	private TextView tv_ok;
	private int howMuchMoney;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_home, container, false);

		lv_foodlist = (StickyListHeadersListView) mView
				.findViewById(R.id.lv_foodlist);// 菜单listview
		ll_fragment_home = (LinearLayout) mView
				.findViewById(R.id.ll_fragment_home);// 结算支付
		ll_foodpay = (LinearLayout) mView.findViewById(R.id.ll_foodpay);// 结算支付
		order_cart = (TextView) mView.findViewById(R.id.food_list_shipping_fee);// 结算支付

		tv_meanu = (TextView) mView.findViewById(R.id.tv_meanu);
		tv_title = (TextView) mView.findViewById(R.id.tv_title);
		tv_commit = (TextView) mView.findViewById(R.id.tv_commit);
		tv_meanu.setOnClickListener(this);
		tv_title.setOnClickListener(this);
		tv_commit.setOnClickListener(this);
		slidingPaneLayout = (SlidingPaneLayout) getActivity().findViewById(
				R.id.slidingpanellayout);
		setListener();
		return mView;
	}

	private void setListener() {
		String title = ConfigUtil.read(getActivity(), "name", Name);
		tv_title.setText(title);

		mAdapter = new HomeFoodAdapter(getActivity(), order_cart);
		lv_foodlist.setOnItemClickListener(this);
		// 添加头部局
		lv_foodlist.addHeaderView(getActivity().getLayoutInflater().inflate(
				R.layout.restaurant_list_header, null));

		lv_foodlist.setDrawingListUnderStickyHeader(true);
		lv_foodlist.setAreHeadersSticky(true);
		lv_foodlist.setAdapter(mAdapter);
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

		case R.id.tv_title:
			updateTitleName();
			break;
		case R.id.tv_commit:
			howMuchMoney = mAdapter.getHowMuchMoney();
			if (howMuchMoney>0) {
				showCommentDialog();
			}else {
				T.show(getActivity(), "请您先进行点餐");
			}
			break;
		case R.id.tv_ok:
			dialog.dismiss();
			break;
		case R.id.tv_zfbPay:
			tv_zfbPay.setBackground(getResources().getDrawable(R.drawable.pay_zfb_pressed));
			tv_wxPay.setBackground(getResources().getDrawable(R.drawable.pay_wx_normal));
			iv_pay_zfb.setVisibility(View.VISIBLE);
			iv_pay_wx.setVisibility(View.INVISIBLE);
			tv_howmoney.setText("您当前选择的是支付宝支付：\n\n共 计："+howMuchMoney+" 元");
			break;
		case R.id.tv_wxPay:
			tv_zfbPay.setBackground(getResources().getDrawable(R.drawable.pay_zfb_nomal));
			tv_wxPay.setBackground(getResources().getDrawable(R.drawable.pay_wx_pressed));
			iv_pay_zfb.setVisibility(View.INVISIBLE);
			iv_pay_wx.setVisibility(View.VISIBLE);
			tv_howmoney.setText("您当前选择的是微信支付：\n\n共 计："+howMuchMoney+" 元");
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	}

	private void updateTitleName() {
		final EditText et2 = new EditText(getActivity());
		et2.setHint(" 例：亚西亚美食城");
		new AlertDialog.Builder(mContext).setTitle("修改标题内容：")
				.setIcon(android.R.drawable.ic_dialog_info).setView(et2)
				.setMessage("请在下方输入要修改的名称内容")
				.setPositiveButton("确定", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String input = et2.getText().toString().trim();
						if (TextUtils.isEmpty(input)) {
							ConfigUtil.write(mContext, "name", Name);
							tv_title.setText(Name);
						} else {
							ConfigUtil.write(mContext, "name", input);
							tv_title.setText(input);
						}
					}
				}).setNegativeButton("重置", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						ConfigUtil.write(mContext, "name", Name);
						tv_title.setText(Name);
					}
				}).show();
	}

	private void showCommentDialog() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View v = View.inflate(getActivity(), R.layout.popupwindow_pay_view,
				null);
		dialog.setContentView(v);
		tv_howmoney = (TextView) v.findViewById(R.id.tv_howmoney);
		tv_ok = (TextView) v.findViewById(R.id.tv_ok);
		tv_zfbPay = (TextView) v.findViewById(R.id.tv_zfbPay);
		tv_wxPay = (TextView) v.findViewById(R.id.tv_wxPay);

		iv_pay_zfb = (ImageView) v.findViewById(R.id.iv_pay_zfb);
		iv_pay_wx = (ImageView) v.findViewById(R.id.iv_pay_wx);

		// et_comment = (EditText) v.findViewById(R.id.et_comment);
		tv_zfbPay.setOnClickListener(this);
		tv_wxPay.setOnClickListener(this);
		Window dialogWindow = dialog.getWindow();
		// WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.TOP);

		/*
		 * 将对话框的大小按屏幕大小的百分比设置
		 */
		WindowManager m = getActivity().getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = dialogWindow.getAttributes(); //
		p.y = dip2px(getActivity(), 70);

		dialogWindow.setAttributes(p);
		dialog.show();
		tv_ok.setOnClickListener(this);
		tv_howmoney.setText("您当前选择的是支付宝支付：\n\n共 计："+howMuchMoney+" 元");
		
	}

	public int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}
