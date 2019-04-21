package com.arong.cookbook.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.arong.cookbook.bean.Food;
import com.arong.cookbook.db.DBHelper;
import com.arong.cookbook.util.BadgeView;
import com.arong.cookbook.util.Tools;
import com.arong.cookbook.widget.stickylistheaders.StickyListHeadersAdapter;
import com.arong.hungry.app.R;
import com.arong.hungry.app.R.string;

/**
 * @author mrjianrong
 * 
 *         功能说明：菜单adapter
 * 
 *         创建时间：2017-5-27 上午11:21:36
 */
public class HomeFoodAdapter extends BaseAdapter implements
		StickyListHeadersAdapter, SectionIndexer {

	private final Activity mActivity;
	private int[] mFoodsNum;//
	private int[] mSectionIndices;
	private String[] mSectionLetters;
	private LayoutInflater mInflater;

	private TextView shopCart;//
	private ViewGroup anim_mask_layout;//
	private ImageView buyImg;//
	private int buyNum = 0;//
	private BadgeView buyNumView;//
	private ArrayList<Food> mFoods;

	
	//结账相关数据
	public int howMoney=0;
	public int howNumBuy=0;
	
	public HomeFoodAdapter(Activity activity, TextView order_cart) {
		mActivity = activity;
		shopCart = order_cart;
		mInflater = LayoutInflater.from(activity);
		mFoods = DBHelper.getInstance(activity).getAllFood();
		if (null != mFoods && mFoods.size() > 0) {
			initFoodNum();
			mSectionIndices = getSectionIndices();
			mSectionLetters = getSectionLetters();
		}
	}

	private void initFoodNum() {
		int leng = mFoods.size();
		mFoodsNum = new int[leng];
		for (int i = 0; i < leng; i++) {
			mFoodsNum[i] = 0;
		}
	}

	private int[] getSectionIndices() {
		ArrayList<Integer> sectionIndices = new ArrayList<Integer>();
		String lastFirstChar = mFoods.get(0).name.split("-")[0];
		sectionIndices.add(0);
		for (int i = 1; i < mFoods.size(); i++) {
			if (mFoods.get(i).name.split("-")[0] != lastFirstChar) {
				lastFirstChar = mFoods.get(i).name.split("-")[0];
				sectionIndices.add(i);
			}
		}
		int[] sections = new int[sectionIndices.size()];
		for (int i = 0; i < sectionIndices.size(); i++) {
			sections[i] = sectionIndices.get(i);
		}
		return sections;
	}

	private String[] getSectionLetters() {
		String[] letters = new String[mSectionIndices.length];
		for (int i = 0; i < mSectionIndices.length; i++) {
			letters[i] = mFoods.get(mSectionIndices[i]).name.split("-")[1];
		}
		return letters;
	}

	@Override
	public int getCount() {
		return mFoods.size();
	}

	@Override
	public Object getItem(int position) {
		return mFoods.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		final Food food = mFoods.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			// convertView = mInflater.inflate(R.layout.food_list_item,
			// parent,false);
			convertView = mInflater.inflate(R.layout.item_list_food, parent,
					false);
			holder.name = (TextView) convertView.findViewById(R.id.tv_foodName);
			holder.price = (TextView) convertView
					.findViewById(R.id.tv_foodPrice);
			holder.add = (TextView) convertView.findViewById(R.id.tv_add);
			holder.size = (TextView) convertView.findViewById(R.id.tv_buyNum);
			holder.minus = (TextView) convertView.findViewById(R.id.tv_delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.price.setText("￥: "+food.price +"  元/份");
		holder.name.setText(mFoods.get(position).name.split("-")[1]);
		
		holder.add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int num=++mFoodsNum[position];
				++howNumBuy;
				holder.size.setText(num+"");
				int moneyItem=(Integer.parseInt(food.price));
				if (num > 0) {
					holder.minus.setVisibility(View.VISIBLE);
				}else {
					holder.minus.setVisibility(View.INVISIBLE);
				}
				
				howMoney+=moneyItem;
				shopCart.setText("  数量：" + howNumBuy + "  共计：" + howMoney + " 元");
				
				int[] start_location = new int[2];//
				v.getLocationInWindow(start_location);//
				buyImg = new ImageView(mActivity);
				buyImg.setImageBitmap(getAddDrawBitMap(position));//
				setAnim(buyImg, start_location);//
				
			
			}
		});
		holder.minus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int num=--mFoodsNum[position];
				--howNumBuy;
				holder.size.setText(num+"");
				int moneyItem= (Integer.parseInt(food.price));
				
				if (num > 0) {
					holder.minus.setVisibility(View.VISIBLE);
				}else {
					holder.minus.setVisibility(View.INVISIBLE);
				}
				howMoney-=moneyItem;
				shopCart.setText("  数量：" + howNumBuy + "  共计：" + howMoney + " 元");
			}
		});

		return convertView;
	}

	public Bitmap getAddDrawBitMap(int position) {
		Tools tools = new Tools();
		View drawableViewPar = LayoutInflater.from(mActivity).inflate(
				R.layout.food_list_item_operation, null);
		TextView text = (TextView) drawableViewPar
				.findViewById(R.id.food_list_item_price_text_view);
		text.setText(" ￥ ：" + position);
		return tools.convertViewToBitmap(text);
	}

	/**
	 * @Description:
	 * @param
	 * @return void
	 * @throws
	 */
	private ViewGroup createAnimLayout() {
		ViewGroup rootView = (ViewGroup) mActivity.getWindow().getDecorView();
		LinearLayout animLayout = new LinearLayout(mActivity);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		animLayout.setLayoutParams(lp);
		animLayout.setId(Integer.MAX_VALUE);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}

	private View addViewToAnimLayout(final ViewGroup vg, final View view,
			int[] location) {
		int x = location[0];
		int y = location[1];
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x;
		lp.topMargin = y;
		view.setLayoutParams(lp);
		return view;
	}

	private void setAnim(final View v, int[] start_location) {
		anim_mask_layout = null;
		anim_mask_layout = createAnimLayout();
		anim_mask_layout.addView(v);//
		final View view = addViewToAnimLayout(anim_mask_layout, v,
				start_location);
		int[] end_location = new int[2];//
		shopCart.getLocationInWindow(end_location);//

		//
		int endX = 0 - start_location[0] + 40;//
		int endY = end_location[1] - start_location[1];//
		TranslateAnimation translateAnimationX = new TranslateAnimation(0,
				endX, 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setRepeatCount(0);//
		translateAnimationX.setFillAfter(true);

		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, endY);
		translateAnimationY.setInterpolator(new AccelerateInterpolator());
		translateAnimationY.setRepeatCount(0);//
		translateAnimationX.setFillAfter(true);

		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(translateAnimationY);
		set.addAnimation(translateAnimationX);
		set.setDuration(800);//
		view.startAnimation(set);

		set.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				v.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.GONE);
				buyNum++;//
				// buyNumView.setText(buyNum + "");//
				// buyNumView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
				// buyNumView.show();
			}
		});

	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder;

		if (convertView == null) {
			holder = new HeaderViewHolder();
			convertView = mInflater.inflate(
					R.layout.restaurant_food_list_header, parent, false);
			holder.text = (TextView) convertView
					.findViewById(R.id.tv_food_list_head);
			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder) convertView.getTag();
		}

		CharSequence headerChar = mFoods.get(position).name.split("-")[0];
		holder.text.setText(headerChar);

		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		return mFoods.get(position).name.subSequence(0, 1).charAt(0);
	}

	@Override
	public int getPositionForSection(int section) {
		if (mSectionIndices.length == 0) {
			return 0;
		}
		if (section >= mSectionIndices.length) {
			section = mSectionIndices.length - 1;
		} else if (section < 0) {
			section = 0;
		}
		return mSectionIndices[section];
	}

	@Override
	public int getSectionForPosition(int position) {
		for (int i = 0; i < mSectionIndices.length; i++) {
			if (position < mSectionIndices[i]) {
				return i - 1;
			}
		}
		return mSectionIndices.length - 1;
	}

	@Override
	public Object[] getSections() {
		return mSectionLetters;
	}

	class HeaderViewHolder {
		TextView text;
	}

	class ViewHolder {
		public TextView price;
		TextView name;
		TextView add;
		TextView size;
		TextView minus;
	}
	public int getHowMuchMoney(){
		return howMoney;
	}
}
