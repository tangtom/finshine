package com.incito.finshine.activity;

import com.incito.finshine.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class Dots extends LinearLayout implements PageControl.onPageChanged{

	int mCount = 0;
    private int mCurrent;
    Bitmap dotRed;
    Bitmap dotWhite;
    public Dots(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
	public Dots(Context context) {
		super(context);
		init();
	}
	private void init(){
	    mCount = 0;
	    mCurrent = 0;
	    dotRed = BitmapFactory.decodeResource(this.getResources(),R.drawable.dot_red);
        dotWhite = BitmapFactory.decodeResource(this.getResources(),R.drawable.dot_white);
	}
	@Override
	public void onDraw(Canvas canvas) {
	    if(mCount == 0){
	        return;
	    }
	    //Log.i("dot", "onDraw = " + mCurrent);
	   
        int dotW = dotRed.getWidth() + 20;
        int dotH = (getHeight()-dotRed.getHeight())/2;
        int parentW =  getWidth();
        int leftPos = (parentW-dotW*mCount)/2;
        
        for(int i=0;i<mCount;i++)
        {
            Bitmap tar;
            if(i == mCurrent)
            {
                tar = dotRed;
            }else
            {
                tar = dotWhite;
            }
            int left = leftPos+i*dotW;
            
            canvas.drawBitmap(tar, left, dotH, null);
        }
       
	}
    @Override
    public void onPageChaned(int index,int total) {
        mCurrent = index;
        mCount = total;
        invalidate();
    }
}
