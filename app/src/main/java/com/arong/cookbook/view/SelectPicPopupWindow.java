package com.arong.cookbook.view;

	import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.arong.hungry.app.R;
	  /**
	   * 案例：发微博页面的加号按扭功能实现
	   * 
	   * 原理：弹出popupwindow对话框
	   *
	   */
	public class SelectPicPopupWindow extends PopupWindow {  
	  
	  
	    private View mMenuView;
		private LinearLayout ly_text,ly_camera,ly_photo,ly_yuyin,ly_saosao,ly_note,ll_cancel;  
	  
	    public SelectPicPopupWindow(Activity context) {  
	        super(context);  
	        
	        LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
	      
	        mMenuView = inflater.inflate(R.layout.popupwindow_pay_view, null);  
//	        ly_text = (LinearLayout) mMenuView.findViewById(R.id.ly_text);//文字
//	        ly_photo = (LinearLayout) mMenuView.findViewById(R.id.ly_photo); //相册
//	        ly_camera = (LinearLayout) mMenuView.findViewById(R.id.ly_camera);//拍照
//	        ly_yuyin = (LinearLayout) mMenuView.findViewById(R.id.ly_yuyin); //语音
//	        ly_saosao = (LinearLayout) mMenuView.findViewById(R.id.ly_saosao);//扫一扫
//	        ly_note = (LinearLayout) mMenuView.findViewById(R.id.ly_note); //便签
//	        ll_cancel = (LinearLayout) mMenuView.findViewById(R.id.ll_cancel); //取消
	       /* //取消按钮  
	        btn_cancel.setOnClickListener(new OnClickListener() {  
	  
	            public void onClick(View v) {  
	                //销毁弹出框  
	                dismiss();  
	            }  
	        });  */
//	        //设置按钮监听  
//	        ly_text.setOnClickListener(itemsOnClick);  
//	        ly_photo.setOnClickListener(itemsOnClick);  
//	        ly_camera.setOnClickListener(itemsOnClick);  
//	        ly_yuyin.setOnClickListener(itemsOnClick);  
//	        ly_saosao.setOnClickListener(itemsOnClick);  
//	        ly_note.setOnClickListener(itemsOnClick);  
//	        ll_cancel.setOnClickListener(itemsOnClick);  
	        
	        //设置SelectPicPopupWindow的View  
	        this.setContentView(mMenuView);  
	        //设置SelectPicPopupWindow弹出窗体的宽  
	        this.setWidth(LayoutParams.FILL_PARENT);  
	        //设置SelectPicPopupWindow弹出窗体的高  
	        this.setHeight(LayoutParams.WRAP_CONTENT);  
	        //设置SelectPicPopupWindow弹出窗体可点击  
	        this.setFocusable(true);  
	        //设置SelectPicPopupWindow弹出窗体动画效果  
	        this.setAnimationStyle(R.style.AppBaseTheme);  
	        //实例化一个ColorDrawable颜色为半透明  
	        ColorDrawable dw = new ColorDrawable(0xFFF5F5F5);  
	        //设置SelectPicPopupWindow弹出窗体的背景  
	        this.setBackgroundDrawable(dw);  
	        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框  
	        mMenuView.setOnTouchListener(new OnTouchListener() {  
	              
	            public boolean onTouch(View v, MotionEvent event) {  
	                  
	                int height = 600;  //设置popupwidow弹出框的高
	                int y=(int) event.getY();  
	                if(event.getAction()==MotionEvent.ACTION_UP){  
	                    if(y<height){  
	                        dismiss();  
	                    }  
	                }                 
	                return true;  
	            }  
	        });  
	  
	    }  
	  
}
