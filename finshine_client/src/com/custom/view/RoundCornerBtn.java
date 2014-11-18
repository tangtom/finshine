package com.custom.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * <dl>
 * <dt>LeftRoundCornerBtn.java</dt>
 * <dd>Description:圆角按钮</dd>
 * <dd>Copyright: Copyright (C) 2011</dd>
 * <dd>Company: 昆山灵动信息技术有限公司</dd>
 * <dd>CreateDate: 2014-6-2 下午10:14:26</dd>
 * </dl>
 * 
 * @author lihs
 */
public class RoundCornerBtn extends Button {

	public static final int LEFT_CORNER = 1;
	public static final int RIGHT_CORNER = 2;
	public static final int NO_CORNER = 3;

	private int currentCorner = NO_CORNER;

	public RoundCornerBtn(Context context, int leftOrRight) {
		super(context);
		this.currentCorner = leftOrRight;
		init(context, null);
	}

	public RoundCornerBtn(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public RoundCornerBtn(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private Paint paint;
	private int roundWidth = 5;
	private int roundHeight = 5;
	private Paint paint2;

	 
	public void setRound(int round) {
		roundWidth = round;
		roundHeight = round;
	}

	private void init(Context context, AttributeSet attrs) {

		float density = context.getResources().getDisplayMetrics().density;
		roundWidth = (int) (roundWidth * density);
		roundHeight = (int) (roundHeight * density);

		paint = new Paint();
		paint.setColor(Color.TRANSPARENT);
		paint.setAntiAlias(true);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

		paint2 = new Paint();
		paint2.setXfermode(null);
	}

	@Override
	public void draw(Canvas canvas) {
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
				Config.ARGB_8888);
		Canvas canvas2 = new Canvas(bitmap);
		super.draw(canvas2);

		switch (currentCorner) {
		case LEFT_CORNER:
			drawLiftUp(canvas2);
			drawLiftDown(canvas2);
			break;
		case RIGHT_CORNER:
			drawRightUp(canvas2);
			drawRightDown(canvas2);
			break;
		case NO_CORNER:
			break;
		default:
			break;
		}
		canvas.drawBitmap(bitmap, 0, 0, paint2);
	}

	private void drawLiftUp(Canvas canvas) {
		Path path = new Path();
		path.moveTo(0, roundHeight);
		path.lineTo(0, 0);
		path.lineTo(roundWidth, 0);
		path.arcTo(new RectF(0, 0, roundWidth * 2, roundHeight * 2), -90, -90);
		path.close();
		canvas.drawPath(path, paint);
	}

	private void drawLiftDown(Canvas canvas) {
		Path path = new Path();
		path.moveTo(0, getHeight() - roundHeight);
		path.lineTo(0, getHeight());
		path.lineTo(roundWidth, getHeight());
		path.arcTo(new RectF(0, getHeight() - roundHeight * 2,
				0 + roundWidth * 2, getHeight()), 90, 90);
		path.close();
		canvas.drawPath(path, paint);
	}

	private void drawRightDown(Canvas canvas) {
		Path path = new Path();
		path.moveTo(getWidth() - roundWidth, getHeight());
		path.lineTo(getWidth(), getHeight());
		path.lineTo(getWidth(), getHeight() - roundHeight);
		path.arcTo(new RectF(getWidth() - roundWidth * 2, getHeight()
				- roundHeight * 2, getWidth(), getHeight()), 0, 90);
		path.close();
		canvas.drawPath(path, paint);
	}

	private void drawRightUp(Canvas canvas) {
		Path path = new Path();
		path.moveTo(getWidth(), roundHeight);
		path.lineTo(getWidth(), 0);
		path.lineTo(getWidth() - roundWidth, 0);
		path.arcTo(new RectF(getWidth() - roundWidth * 2, 0, getWidth(),
				0 + roundHeight * 2), -90, 90);
		path.close();
		canvas.drawPath(path, paint);
	}
}
