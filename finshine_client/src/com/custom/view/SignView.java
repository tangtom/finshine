package com.custom.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SignView extends View{
	private Paint mPaint;

	private static final float MINP = 0.25f;
	private static final float MAXP = 0.75f;

	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Path mPath;
	private Paint mBitmapPaint;


	public SignView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public SignView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mPath = new Path();
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
	}

	public SignView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public void setPaint(Paint paint) {
		this.mPaint = paint;
	}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(0x00000000);

		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

		canvas.drawPath(mPath, mPaint);
	}

	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;

	private void touch_start(float x, float y) {
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
	}

	private void touch_up() {
		mPath.lineTo(mX, mY);
		// commit the path to our offscreen
		mCanvas.drawPath(mPath, mPaint);
		// kill this so we don't double draw
		mPath.reset();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touch_start(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			touch_move(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			touch_up();
			invalidate();
			break;
		}
		return true;
	}
	
	public void paintClear()
	{
		Paint paint = new Paint();  
	    paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));  
	    mCanvas.drawPaint(paint);  
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC)); 
		invalidate();
	}
	
	public Bitmap getView()
	{
		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidht = ((float) 200 / width);
		float scaleHeight = ((float) 80 / height);
		matrix.postScale(scaleWidht, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(mBitmap, 0, 0, width, height,matrix, true);
		return newbmp;
	}

}
