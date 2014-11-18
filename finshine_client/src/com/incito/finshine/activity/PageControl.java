package com.incito.finshine.activity;

import java.util.ArrayList;

import com.incito.finshine.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

public class PageControl extends LinearLayout implements OnGestureListener,OnTouchListener{
    interface onPageChanged {
        void onPageChaned(int index,int total);
    }
	int miCount = 0;
	int y = 0; 
	GestureDetector mGestureDetector;    
    private int mCurrentLayoutState;    
    private static final int FLING_MIN_DISTANCE = 100;    
    private static final int FLING_MIN_VELOCITY = 200; 
	
	private ViewFlipper mFlipper;  
	private onPageChanged listener;
	public PageControl(Context context , onPageChanged listener) {
		super(context);
		this.listener = listener;
		setBackgroundColor(Color.WHITE);
		
		mFlipper = new ViewFlipper(context); 
		//mFlipper.setBackgroundColor(Color.GREEN);
		LayoutParams lpFlipper = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		//lpFlipper.setMargins(1, 1, 1, 1);
		mFlipper.setLayoutParams(lpFlipper);
		
		
		this.addView(mFlipper);
		mGestureDetector = new GestureDetector(this);    
        mFlipper.setOnTouchListener(this);    
  
        mCurrentLayoutState = 0;    
        mFlipper.setLongClickable(true);  
        //setListData();
	}
	@Override
	protected void onAnimationEnd() {
		// TODO Auto-generated method stub
		super.onAnimationEnd();
	}
	@Override
	public void onDraw(Canvas canvas) {
	    if(miCount == 0){
	        return;
	    }
		mCurrentLayoutState = (Integer)mFlipper.getCurrentView().getTag();
		listener.onPageChaned(mCurrentLayoutState, miCount);
	}

	
	/**   
     * @param switchTo   
     */    
    public void switchLayoutStateTo(int switchTo) {    
        while (mCurrentLayoutState != switchTo) {    
            if (mCurrentLayoutState > switchTo) {    
                mCurrentLayoutState--;    
                mFlipper.setInAnimation(inFromLeftAnimation());    
                mFlipper.setOutAnimation(outToRightAnimation());    
                mFlipper.showPrevious();   
                invalidate();
            } else {    
                mCurrentLayoutState++;    
                mFlipper.setInAnimation(inFromRightAnimation());    
                mFlipper.setOutAnimation(outToLeftAnimation());    
                mFlipper.showNext();
                invalidate();
            }    
        }    
        ;    
    }    
  
  
    /**   
     * 定义从右侧进�亩Ч?  
     * @return   
     */    
    protected Animation inFromRightAnimation() {    
        Animation inFromRight = new TranslateAnimation(    
                Animation.RELATIVE_TO_PARENT, +1.0f,    
                Animation.RELATIVE_TO_PARENT, 0.0f,    
                Animation.RELATIVE_TO_PARENT, 0.0f,    
                Animation.RELATIVE_TO_PARENT, 0.0f);    
        inFromRight.setDuration(500);    
        inFromRight.setInterpolator(new AccelerateInterpolator());    
        return inFromRight;    
    }    
  
  
    /**   
     * 定义从左侧退出的动画效�  
     * @return   
     */    
    protected Animation outToLeftAnimation() {    
        Animation outtoLeft = new TranslateAnimation(    
                Animation.RELATIVE_TO_PARENT, 0.0f,    
                Animation.RELATIVE_TO_PARENT, -1.0f,    
                Animation.RELATIVE_TO_PARENT, 0.0f,    
                Animation.RELATIVE_TO_PARENT, 0.0f);    
        outtoLeft.setDuration(500);    
        outtoLeft.setInterpolator(new AccelerateInterpolator());    
        return outtoLeft;    
    }    
  
  
    /**   
     * 定义从左侧进�亩Ч?  
     * @return   
     */    
    protected Animation inFromLeftAnimation() {    
        Animation inFromLeft = new TranslateAnimation(    
                Animation.RELATIVE_TO_PARENT, -1.0f,    
                Animation.RELATIVE_TO_PARENT, 0.0f,    
                Animation.RELATIVE_TO_PARENT, 0.0f,    
                Animation.RELATIVE_TO_PARENT, 0.0f);    
        inFromLeft.setDuration(500);    
        inFromLeft.setInterpolator(new AccelerateInterpolator());    
        return inFromLeft;    
    }    
  
  
    /**   
     * 定义从右侧退出时的动画效�  
     * @return   
     */    
    protected Animation outToRightAnimation() {    
        Animation outtoRight = new TranslateAnimation(    
                Animation.RELATIVE_TO_PARENT, 0.0f,    
                Animation.RELATIVE_TO_PARENT, +1.0f,    
                Animation.RELATIVE_TO_PARENT, 0.0f,    
                Animation.RELATIVE_TO_PARENT, 0.0f);    
        outtoRight.setDuration(500);    
        outtoRight.setInterpolator(new AccelerateInterpolator());    
        return outtoRight;    
    }    
  
  
    @Override
	public boolean onDown(MotionEvent e) {    
        return false;    
    }    
  
  
    /*   
     * 用户按下触�痢⒖?僖贫笏煽创シ⒄飧鍪录?  
     * e1：第1个ACTION_DOWN MotionEvent   
     * e2：煮一个ACTION_MOVE MotionEvent   
     * velocityX：X轴上的移动速度，像素/�  
     * velocityY：Y轴上的移动速度，像素/�  
     * 触发条�：   
     * X轴的坐�灰拼笥贔LING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/�  
     */    
    @Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,    
            float velocityY) {    
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE    
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {    
            mFlipper.setInAnimation(inFromRightAnimation());    
            mFlipper.setOutAnimation(outToLeftAnimation());    
            mFlipper.showNext();   
            invalidate();
           
        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE    
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {    
            mFlipper.setInAnimation(inFromLeftAnimation());    
            mFlipper.setOutAnimation(outToRightAnimation());    
            mFlipper.showPrevious();  
            invalidate();
            
        }    
        return false;    
    }   

	@Override
	public boolean onTouch(View v, MotionEvent event) {
	
        return mGestureDetector.onTouchEvent(event); 
	}

	@Override
	public void onShowPress(MotionEvent e) {
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		
	}

	public void setListData() {
        for(int i=0; i<miCount; i++)
        {   
            LinearLayout layout = new LinearLayout(this.getContext());
            layout.setOrientation(HORIZONTAL);
            mFlipper.addView(layout);
            layout.setTag(i);
            
            LayoutParams lpImage = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT);
            ImageView imageView = new ImageView(this.getContext());  
            imageView.setLayoutParams(lpImage);  
            imageView.setScaleType(ScaleType.FIT_XY);
            Bitmap b = BitmapFactory.decodeResource(this.getResources(), R.drawable.banner1).copy(Bitmap.Config.ARGB_8888, true);
            imageView.setImageBitmap(b);
         
            layout.addView(imageView);
        }

    }
	public void setListData(ArrayList<DownloadItem> list) {
	    miCount = list.size();
		for(int i=0; i<miCount; i++)
		{	
			LinearLayout layout = new LinearLayout(this.getContext());
			layout.setOrientation(HORIZONTAL);
			mFlipper.addView(layout);
			layout.setTag(i);
			
			LayoutParams lpImage = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT);
			ImageView imageView = new ImageView(this.getContext());  
			imageView.setLayoutParams(lpImage);  
			imageView.setScaleType(ScaleType.FIT_XY);
			//Bitmap b = BitmapFactory.decodeResource(this.getResources(), R.drawable.banner1).copy(Bitmap.Config.ARGB_8888, true);
			//imageView.setImageBitmap(b);
			imageView.setImageDrawable(list.get(i).getDrawable());
			layout.addView(imageView);
			
		}

	}
	public ImageView[] setListData(int size) {
        miCount = size;
        ImageView[] views = new ImageView[miCount];
        for(int i=0; i<miCount; i++)
        {   
            LinearLayout layout = new LinearLayout(this.getContext());
            layout.setOrientation(HORIZONTAL);
            mFlipper.addView(layout);
            layout.setTag(i);
            
            LayoutParams lpImage = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT);
            ImageView imageView = new ImageView(this.getContext());  
            imageView.setLayoutParams(lpImage);  
            imageView.setScaleType(ScaleType.FIT_XY);
            //Bitmap b = BitmapFactory.decodeResource(this.getResources(), R.drawable.banner1).copy(Bitmap.Config.ARGB_8888, true);
            //imageView.setImageBitmap(b);
            //imageView.setImageDrawable(list.get(i).getDrawable());
            views[i] = imageView;
            layout.addView(imageView);
        }
        return views;
    }

}
