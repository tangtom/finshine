package com.custom.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;

public class PaintView extends View {
	private Paint paint;
	private Canvas cacheCanvas;
	private Bitmap cachebBitmap;
	private Path path;
	private LayoutParams p;
	private float cur_x, cur_y;
	public boolean isPaint = false;

	public PaintView(Context context, LayoutParams lp) {
		super(context);
		this.p = lp;
		this.setLayoutParams(p);
		init();
	}

	public void clear() {
		if (cacheCanvas != null) {
			// 清除canvas上所有内容
			cacheCanvas.drawPaint(paint);
			// cachebBitmap = Bitmap.createBitmap(760, 420, Config.ARGB_8888);
			paint.setColor(Color.BLACK);
			cacheCanvas.drawColor(Color.WHITE);
			cacheCanvas.setBitmap(cachebBitmap);
			// init();
			invalidate();
		}
	}

	public Bitmap getCachebBitmap() {
		return cachebBitmap;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		isPaint = true;
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			System.out.println("down");
			cur_x = x;
			cur_y = y;
			path.moveTo(cur_x, cur_y);
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			System.out.println("move");
			path.quadTo(cur_x, cur_y, x, y);
			cur_x = x;
			cur_y = y;
			break;
		}

		case MotionEvent.ACTION_UP: {
			System.out.println("up");
			cacheCanvas.drawPath(path, paint);
			path.reset();
			break;
		}
		}

		invalidate();

		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(cachebBitmap, 0, 0, null);
		canvas.drawPath(path, paint);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// int curW = cachebBitmap != null ? cachebBitmap.getWidth() : 0;
		// int curH = cachebBitmap != null ? cachebBitmap.getHeight() : 0;
		// if (curW >= w && curH >= h) {
		// return;
		// }
		//
		// if (curW < w)
		// curW = w;
		// if (curH < h)
		// curH = h;
		//
		// Bitmap newBitmap = Bitmap.createBitmap(curW, curH,
		// Bitmap.Config.ARGB_8888);
		// Canvas newCanvas = new Canvas();
		// newCanvas.setBitmap(newBitmap);
		// if (cachebBitmap != null) {
		// newCanvas.drawBitmap(cachebBitmap, 0, 0, null);
		// }
		// cachebBitmap = newBitmap;
		// cacheCanvas = newCanvas;
	}

	private void init() {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(3);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.BLACK);
		path = new Path();
		cachebBitmap = Bitmap.createBitmap(750, 420, Config.ARGB_8888);
		cacheCanvas = new Canvas(cachebBitmap);
		cacheCanvas.drawColor(Color.WHITE);
	}
}
