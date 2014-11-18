package com.codans.blossom.datepicker;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint.Style;
import android.graphics.Shader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DrumPicker extends FrameLayout{
	private final static String TAG = DrumPicker.class.getSimpleName();
	private final static int LAYOUT_ID = 255255;
	
	public interface OnPositionChangedListener{
		public void onPositionChanged(int itemPos, int pos);
	}
	
	private OnPositionChangedListener mListener = null;
	private List<DrumPickerScrollView> mScrollViews = new ArrayList<DrumPickerScrollView>();
	private int mCount = 0;
	private int mLensWidth = 0;
	LinearLayout mLayout;
	View mLens;
	public DrumPicker(Context context) {
		this(context, null);
	}
	public DrumPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
		mLayout = new LinearLayout(context, attrs);
		mLayout.setOrientation(LinearLayout.HORIZONTAL);
		mLayout.setGravity(Gravity.CENTER);
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT);
		params.gravity = Gravity.CENTER;
		addView(mLayout, params);
		addLens();
	}
	private void addLens(){
		mLens = new View(getContext()){
			@Override
			protected void onDraw(Canvas canvas) {
				super.onDraw(canvas);
				//canvas.drawColor(Color.BLUE);
				drawLens(this,canvas);
			}
		};
		LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT);
		params.gravity = Gravity.CENTER;
		addView(mLens, params);
	}
	public void setOnPostionChangedListener(OnPositionChangedListener listener){
		mListener = listener;
	}
	
	/**
	 * ���ǉ�����
	 * @param params �I������
	 * @param width ��̕�
	 */
	public void addRow(List<String> params, int width){
		addRow(params.toArray(new String[]{}), width);
	}
	public void addRow(String[] params, int width) {
		addRow(params, width, params.length);
	}
	/**
	 * ���ǉ�����B
	 * @param params
	 * @param width
	 */
	public void addRow(String[] params, int width, int size) {
		//TODO �T�C�Y��肪���ߑł��Ȃ̂łȂ�Ƃ�����
		final int count = mCount;
		mCount++;
		LinearLayout layout = createCover();
		float scale = Util.getDisplayScale(getContext());
		width *= scale;
		mLensWidth += width;
		int height = (int) (150 * scale);
		height += height%2;	
		DrumPickerScrollView scroll = new DrumPickerScrollView(getContext(), (int)(50*Util.getDisplayScale(getContext())));
		mScrollViews.add(scroll);
		scroll.setVerticalScrollBarEnabled(false);
		scroll.setFadingEdgeLength(0);
		scroll.setOnPositionChangedListener(new DrumPickerScrollView.OnPositionChangedListener() {
			@Override
			public void onPositionChenged(int pos) {
				if(mListener != null){
					mListener.onPositionChanged(count, pos);
				}
			}
		});
		LinearLayout linear = new LinearLayout(getContext());
		linear.setOrientation(LinearLayout.VERTICAL);
		linear.setBackgroundColor(Color.WHITE);
		addTextView("", linear, -1);
		for(int i = 0; i < size; i++){
			String text = params[i];
			addTextView(text, linear, i);
		}
		addTextView("", linear, -2);
		linear.setId(LAYOUT_ID);
		scroll.addView(linear, new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT));
		
		FrameLayout frame = new FrameLayout(getContext());
		frame.setPadding((int)(2*scale), 0, (int)(2*scale), 0);
		frame.addView(scroll, new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT));		
		frame.addView(layout, new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT));

		mLayout.addView(frame, width, height);
	}
	
	private void addTextView(String text, ViewGroup layout, int id){
		//TODO �T�C�Y�Ƃ�
		TextView textView = new TextView(getContext());
		textView = new TextView(getContext());
		textView.setTextColor(Color.BLACK);
		textView.setTextSize(30);
		textView.setGravity(Gravity.CENTER);
		textView.setText(text);
		textView.setId(id);
		LayoutParams p =new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, (int)(50*Util.getDisplayScale(getContext())));
		layout.addView(textView, p);
	}
	
	/**
	 * �\�ʂ̉e�Ƃ�
	 * @return
	 */
	private LinearLayout createCover() {
		LinearLayout layout = new LinearLayout(getContext()) {
			private Paint paint = new Paint();
			private Bitmap shade;
			@Override
			protected void onDraw(Canvas canvas) {
				if (getWidth() > 0 && getHeight() > 0) {
					if (shade == null) {
						float scale = Util.getDisplayScale(getContext());
						shade = Bitmap.createBitmap(getWidth(), getHeight(),
								Config.ARGB_8888);
						Canvas c = new Canvas(shade);
											
						//�㉺�̉e
						Shader s = new LinearGradient(0, 0, 0, (60*scale), Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
						paint.setShader(s);
						c.drawRect(0,0,shade.getWidth(), shade.getHeight(), paint);
						c.rotate(180, shade.getWidth()/2, shade.getHeight()/2);
						c.drawRect(0,0,shade.getWidth(), shade.getHeight(), paint);
						
						//�g
						Paint stroke = new Paint();
						stroke.setColor(Color.DKGRAY);
						stroke.setStrokeWidth(4);
						stroke.setStyle(Style.STROKE);
						stroke.setAlpha(100);
						c.drawRect(2, 2, shade.getWidth()-2, shade.getHeight()-2, stroke);
						c.rotate(180, shade.getWidth()/2, shade.getHeight()/2);
						//drawLens(this, c);
					}
					canvas.drawBitmap(shade, 0, 0, paint);
				}
			}
		};
		layout.setWillNotDraw(false);
		return layout;
	}
	Bitmap mLensBitmap = null;
	private void drawLens(View view, Canvas canvas){
		//�����Y
		//Bitmap lens = (Bitmap)view.getTag();
		if(mLensBitmap == null && view.getWidth() > 0 && view.getHeight() > 0){
			
			mLensBitmap = Bitmap.createBitmap(mLensWidth, view.getHeight()/4, Config.ARGB_8888);
			Canvas c = new Canvas(mLensBitmap);
			Log.d(TAG, "canvas size:" + view.getWidth() + "::" + (view.getHeight()));
			Log.d(TAG, "create bitmap:" + c.getWidth() + "::" + (c.getHeight()));
			Paint paint = new Paint();
			paint.setColor(Color.GRAY);
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(2);
			paint.setAlpha(180);
			
			c.drawLine(0,1,mLensBitmap.getWidth(), 1, paint);
			c.drawLine(0,mLensBitmap.getHeight()-1,mLensBitmap.getWidth(), mLensBitmap.getHeight()-1, paint);
			paint.setColor(Color.argb(110, 195, 205, 225));
			paint.setStyle(Style.FILL);
			c.drawRect(0,1,mLensBitmap.getWidth(), mLensBitmap.getHeight()-1, paint);
			c.drawRect(0,1 + mLensBitmap.getHeight()/2,mLensBitmap.getWidth(), mLensBitmap.getHeight()-1, paint);
			
		}
		if(mLensBitmap != null){
			canvas.drawBitmap(mLensBitmap, view.getWidth()/2-mLensWidth/2, view.getHeight()/2-mLensBitmap.getHeight()/2, null);		
		}
	}
	/**
	 * �s�̈ʒu���Z�b�g����
	 * @param itemPos ��
	 * @param pos �s
	 */
	public void setPosition(int itemPos, int pos){
		if(itemPos >= 0 && mScrollViews.size() > itemPos){
			Log.d(TAG, "setPosition!");
			DrumPickerScrollView scroll = mScrollViews.get(itemPos);
			scroll.setPosition(pos);
			invalidate();
		}
		else{
			Log.d(TAG, "setPosition! error:" + itemPos + "::" + mScrollViews.size());
		}
	}
	
	public int getCount(int itemPos){
		if(itemPos >= 0 && mScrollViews.size() > itemPos){
			DrumPickerScrollView scroll = mScrollViews.get(itemPos);
			LinearLayout layout = (LinearLayout)scroll.findViewById(DrumPicker.LAYOUT_ID);
			return layout.getChildCount();
		}
		return -1;
	}
	
	public void resize(int itemPos, IsGoneListener l){
		DrumPickerScrollView scroll = mScrollViews.get(itemPos);
		LinearLayout layout = (LinearLayout)scroll.findViewById(LAYOUT_ID);
		int count = 0;
		for(int i = 0; i < layout.getChildCount(); i++){
			View v = layout.getChildAt(i);
			if(v.getId() >= 0){
				if(l.isGone(v, i-1)){
					if(v.getVisibility() == View.VISIBLE){
						count--;
					}
					v.setVisibility(View.GONE);
				}
				else{
					if(v.getVisibility() == View.GONE){
						count++;
					}
					v.setVisibility(View.VISIBLE);
				}
			}
		}
		//
		scroll.setPosition(scroll.getPosition()+count, false);
	}
	
	/**
	 * �A�C�e���̐���ς��鎞�Ɏg�����X�i�[
	 * @author yagi
	 *
	 */
	public interface IsGoneListener{
		boolean isGone(View item, int pos);
	}
}
