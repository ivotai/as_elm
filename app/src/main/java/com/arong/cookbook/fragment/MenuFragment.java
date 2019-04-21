package com.arong.cookbook.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arong.cookbook.bean.Food;
import com.arong.cookbook.db.DBHelper;
import com.arong.cookbook.util.T;
import com.arong.cookbook.view.CustomDialog;
import com.arong.hungry.app.R;

/**
 * @author mrjianrong
 * 
 *         功能说明：菜单管理 fragment 增删改查
 * 
 *         创建时间：2017-5-27 下午2:35:38
 */
@SuppressLint("NewApi")
public class MenuFragment extends Fragment implements OnClickListener {
	private View mView;
	private Handler mhandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case 1:// 刷新列表
				foodListData = DBHelper.getInstance(getActivity()).getAllFood();
				if (foodListData.size() > 0) {
					tv_bg.setVisibility(View.INVISIBLE);
				}
				myAdapter.notifyDataSetChanged();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_menu, container, false);

		tv_meanu = (TextView) mView.findViewById(R.id.tv_meanu);
		tv_title = (TextView) mView.findViewById(R.id.tv_title);
		tv_addFood = (TextView) mView.findViewById(R.id.tv_addFood);
		tv_bg = (TextView) mView.findViewById(R.id.tv_bg);
		lv_foodList = (ListView) mView.findViewById(R.id.lv_foodList);
		slidingPaneLayout = (SlidingPaneLayout) getActivity().findViewById(
				R.id.slidingpanellayout);
		setListener();
		return mView;
	}

	private void setListener() {
		tv_meanu.setOnClickListener(this);
		tv_addFood.setOnClickListener(this);
		tv_title.setText("菜 单 管 理");

		foodListData = DBHelper.getInstance(getActivity()).getAllFood();
		if (foodListData.size() > 0) {
			tv_bg.setVisibility(View.INVISIBLE);
		}
		myAdapter = new MyAdapter();
		lv_foodList.setAdapter(myAdapter);
	}

	private TextView tv_meanu, tv_title, tv_addFood, tv_bg;
	private SlidingPaneLayout slidingPaneLayout;
	private ListView lv_foodList;
	private ArrayList<Food> foodListData;
	private MyAdapter myAdapter;

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
		case R.id.tv_addFood:
			addFood();
			break;
		// case R.id.tv_addFood:
		// break;
		// case R.id.tv_addFood:
		// break;
		// case R.id.tv_addFood:
		// break;
		default:
			break;
		}
	}

	private void addFood() {
		final CustomDialog.Builder builder = new CustomDialog.Builder(
				getActivity());
		builder.setTitle("请添加菜品：");
		builder.setMessage("添加成功后，可多次添加菜品");
		builder.setPositiveButton("添 加", new DialogInterface.OnClickListener() {
			private Food food;

			public void onClick(DialogInterface dialog, int which) {
				food = new Food();
				food.type = builder.getFoodType();
				food.price = builder.getFoodPrice();
				food.name =  builder.getFoodType()+"-"+builder.getFoodName();

				if (food.name.equals("") || food.type.equals("")
						|| food.price.equals("")) {
					T.show(getActivity(), "三项输入内容，都不能为空 ！");
					return;
				}
				long num = DBHelper.getInstance(getActivity()).addFood(food);
				if (num != -1) {
					T.show(getActivity(), "添加成功，还可继续填加 ！");
				} else {
					T.show(getActivity(), "添加失败 ！");
				}
				food = null;
				mhandler.sendEmptyMessage(1);
			}
		});

		builder.setNegativeButton("取 消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();

	}

	class MyAdapter extends BaseAdapter {

		private Food food;

		@Override
		public int getCount() {
			return foodListData.size();
		}

		@Override
		public Object getItem(int position) {
			return foodListData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.item_list_food_manager, null);
				holder = new ViewHolder();

				holder.name = (TextView) convertView
						.findViewById(R.id.tv_foodName);
				holder.type = (TextView) convertView
						.findViewById(R.id.tv_foodType);
				holder.price = (TextView) convertView
						.findViewById(R.id.tv_foodPrice);
				holder.delete = (TextView) convertView
						.findViewById(R.id.tv_delete);
				holder.update = (TextView) convertView
						.findViewById(R.id.tv_update);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			food = foodListData.get(position);
			holder.name.setText(food.name.split("-")[1]);
			holder.type.setText(food.type);
			holder.price.setText(food.price + " 元/份");
			holder.delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int i = DBHelper.getInstance(getActivity())
							.deleteFood(food);
					if (i != -1) {
						T.show(getActivity(), "删除成功！");
					} else {
						T.show(getActivity(), "删除失败！");
					}
					mhandler.sendEmptyMessage(1);
				}
			});

			return convertView;
		}

		class ViewHolder {
			public TextView update;
			public TextView delete;
			public ImageView iv_location;
			public TextView name;
			public TextView price;
			public TextView type;

		}
	}
}
