package com.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

 
public class SideBar extends View {

    private static final String TAG = "SideBar";

    private SectionIndexer sectionIndexter = null;
    private ListView list;
    private View popView;
    private TextView mDialogText;
    public static boolean isCanRefreshListView = true;
    private Rect bound = new Rect();
    private boolean showBkg = false;
    int choose = -1;// 选中
    Paint paint = new Paint();

    private boolean isShown = false;

    private WindowManager winManager = null;
    private WindowManager.LayoutParams layoutParam = new WindowManager.LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT);

    public SideBar(Context context) {
        super(context);
        textSize = 16f;
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        textSize = 16f;
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        textSize = 16f;
    }

    /**
     * @author: sunkai
     * @Title: setListView
     * @Description: 设置快速定位条关联的listView
     * @param _list
     * @date: 2013-8-9 下午2:41:23
     */
    public void setListView(ListView _list) {
        list = _list;
        sectionIndexter = (SectionIndexer) _list.getAdapter();
    }

    /**
     * @author: sunkai
     * @Title: setTextView
     * @Description: 设置快速定位条滑动时，屏幕中央显示定位条选中的字母
     * @param mDialogText
     * @date: 2013-8-9 下午2:41:55
     */
    public void setTextView(TextView mDialogText) {
        this.mDialogText = mDialogText;
        mDialogText.setTextSize(34);
    }

    public void setPopView(View popView) {
        this.popView = popView;
        winManager = (WindowManager) popView.getContext().getSystemService(
                Context.WINDOW_SERVICE);
    }

    /**
     * @author: sunkai-PC
     * @Title: onTouchEvent
     * @Description: 滑动监听
     * @param event
     * @return
     * @date 2013-8-9 下午2:43:13
     */
    public boolean onTouchEvent(MotionEvent event) {
        String[] charArr = (String[]) sectionIndexter.getSections();
        if (charArr == null) {
            return true;
        }
        int length = charArr.length;
        float y = event.getY(); // 点击y坐标
        int oldChoose = choose;
        int c = (int) (y / getHeight() * length); // 获取每一个字母的高度
        super.onTouchEvent(event);
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            showBkg = true;
            if (oldChoose != c) {
                if (c >= 0 && c < length) {
                    if (popView != null) {
                        handler.removeMessages(0);
                        if (!isShown) {
                            winManager.addView(popView, layoutParam);
                            isShown = true;
                        }
                    }
                    mDialogText.setText(String.valueOf(charArr[c]));
                    listViewSelection(c);
                    choose = c;
                }
            }
            break;
        case MotionEvent.ACTION_MOVE:
            showBkg = true;
            if (oldChoose != c) {
                if (c >= 0 && c < length) {
                    if (popView != null) {
                        if (!isShown) {
                            winManager.addView(popView, layoutParam);
                            isShown = true;
                        }
                    }
                    mDialogText.setText(String.valueOf(charArr[c]));
                    listViewSelection(c);
                    choose = c;
                }
            }
            break;
        case MotionEvent.ACTION_UP:
            showBkg = false;
            choose = -1;
            handler.removeMessages(0);
            handler.sendEmptyMessageDelayed(0, 300); // 延迟影藏提示框
            break;
        }
        invalidate();// 刷新
        return true;
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (popView != null) {
                if (isShown) {
                    winManager.removeView(popView);
                    isShown = false;
                }
            }
        }

    };

    private void listViewSelection(int section) {
        if (sectionIndexter == null) {
            sectionIndexter = (SectionIndexer) list.getAdapter();
        }
        int position = sectionIndexter.getPositionForSection(section);
        if (position == -1) {
            return;
        }
        list.setSelection(position);
    }

    private float textSize;

    /**
     * @author: sunkai-PC
     * @Title: onDraw
     * @Description: 绘制导航字母
     * @param canvas
     * @date 2013-8-9 下午2:43:29
     */
    protected void onDraw(Canvas canvas) {

        if (showBkg) {
            canvas.drawColor(Color.parseColor("#40000000"));
        }
        String[] charArr = (String[]) sectionIndexter.getSections();
        if (charArr != null && charArr.length > 0) {
            float height = getMeasuredHeight() / charArr.length;
            int size = charArr.length;
            for (int i = 0; i < size; i++) {
                String letter = charArr[i];
                if (i == choose) {
                    paint.setColor(Color.parseColor("#3399ff"));
                } else {
                    paint.setColor(Color.BLACK);
                }
                paint.setTextSize(textSize);
                paint.setAntiAlias(true);
                // x坐标等于中间-字符串宽度的一半.
                paint.getTextBounds(letter, 0, letter.length(), bound);
                float xPos = getMeasuredWidth() / 2 - bound.width() / 2;
                float yPos = i * height + (height + bound.width()) / 2;
                canvas.drawText(letter, xPos, yPos, paint);
                paint.reset();
            }
        }
        this.invalidate();
        super.onDraw(canvas);
    }
}
