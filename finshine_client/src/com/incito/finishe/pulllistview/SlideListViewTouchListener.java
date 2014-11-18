package com.incito.finishe.pulllistview;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.nineoldandroids.view.animation.AnimatorProxy;

public class SlideListViewTouchListener implements View.OnTouchListener {

    private static final String TAG = "SlideListViewTouchListener";
    private float screenWidth = 1;

    private float lastMotionX = 0;
    private float lastMotionY = 0;

    private float moveX = 0;
    private long animationTime = 200;

    private int touchSlop = 0;

    private boolean isScrollInY = false;

    private AnimationRemoveListView slideListView = null;

    private View itemView = null;
    private View showView = null;
    private View hideView = null;

    private int downPosition = 0;

    private Rect rect = new Rect();

    private int showItemRes = 0;
    private int hideItemRes = 0;
    private int showDelBtnRes = 0;
    private int showDelOpBtnRes = 0;

    private float moveDeltaX = 0f;
    private static final float DELTAX_DEL = 100f;
    private int headCount;
    private static boolean exceptItemFlg = false;

    public SlideListViewTouchListener(AnimationRemoveListView slideListView,
            int touchSlop) {
        this.slideListView = slideListView;
        this.touchSlop = touchSlop;
        headCount = this.slideListView.getHeaderViewsCount();
        downPosition = headCount;
    }

    public void setItemRes(int showRes, int hideRes) {
        this.showItemRes = showRes;
        this.hideItemRes = hideRes;
    }

    public void setShowDelBtnRes(int showDelBtnRes) {
        this.showDelBtnRes = showDelBtnRes;
    }

    public void setShowDelOpBtnRes(int showDelOpBtnRes) {
        this.showDelOpBtnRes = showDelOpBtnRes;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (screenWidth < 2) {
            screenWidth = slideListView.getWidth();
        }
        try {
            switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                moveDeltaX = 0;
                lastMotionX = motionEvent.getRawX();
                lastMotionY = motionEvent.getRawY();
                final int position = getPosition();
//                LogUtil.d(TAG, "ACTION_DOWN position=" + position);
                if (position < headCount) {
                    // header行及列表item以外的地方不支持拖动
                    exceptItemFlg = true;
                    return false;
                }
                final int firstVisiblePosition = slideListView
                        .getFirstVisiblePosition();
                final int lastVisiblePosition = slideListView
                        .getLastVisiblePosition();
                if ((downPosition != position)
                        && (downPosition >= firstVisiblePosition)
                        && (downPosition <= lastVisiblePosition)) {
                    itemView = slideListView.getChildAt(downPosition
                            - firstVisiblePosition);
                    showView = itemView.findViewById(showItemRes);
                    hideView = itemView.findViewById(hideItemRes);
                    ViewPropertyAnimator.animate(showView).translationX(0)
                            .setDuration(200);
                    ViewPropertyAnimator.animate(hideView).translationX(0)
                            .setDuration(200);
                    moveX = 0;
                }
                if (AnimatorProxy.NEEDS_PROXY && downPosition != -1
                        && itemView != null) {
                    // android 3.0 以下的场合
                    View showDelBtn = itemView.findViewById(showDelBtnRes);
                    if (showDelBtn != null
                            && showDelBtn.getVisibility() == View.VISIBLE) {
                        itemView.findViewById(showDelBtnRes).setVisibility(
                                View.GONE);
                        itemView.findViewById(showDelOpBtnRes).setVisibility(
                                View.VISIBLE);
                    }
                }
                downPosition = position;

                itemView = slideListView.getChildAt(downPosition
                        - slideListView.getFirstVisiblePosition());
                showView = itemView.findViewById(showItemRes);
                hideView = itemView.findViewById(hideItemRes);

                break;
            }
            case MotionEvent.ACTION_UP: {
                if (exceptItemFlg) {
                    exceptItemFlg = false;
                    return false;
                }
                if (AnimatorProxy.NEEDS_PROXY
                        && Math.abs(moveDeltaX) > DELTAX_DEL) {
                    // 横向拖动超过100个像素，则显示删除按钮
                    View showDelBtn = itemView.findViewById(showDelBtnRes);
                    if (showDelBtn != null
                            && showDelBtn.getVisibility() != View.VISIBLE) {
                        itemView.findViewById(showDelBtnRes).setVisibility(
                                View.VISIBLE);
                        itemView.findViewById(showDelOpBtnRes).setVisibility(
                                View.GONE);
                    }
                } else {
                    if (isScrollInY == true || downPosition == -1) {
                        break;
                    } else {
                        final int hideViewWidth = hideView.getWidth();
                        final float translationX = ViewHelper
                                .getTranslationX(showView);
                        float deltaX = 0;

                        if (translationX == -hideViewWidth) {
                            animationTime = 0;
                        } else {
                            animationTime = 200;
                        }

                        if (translationX > -hideViewWidth / 2) {
                            deltaX = 0;
                        } else if (translationX <= -hideViewWidth / 2) {
                            deltaX = -hideViewWidth;
                        }
                        moveX += deltaX;
                        if ((moveX >= 0) || (deltaX == 0)) {
                            moveX = 0;
                        } else if (moveX <= -hideViewWidth) {
                            moveX = -hideViewWidth;
                        }
                        ViewPropertyAnimator.animate(showView).translationX(deltaX)
                                .setDuration(animationTime);
                        ViewPropertyAnimator.animate(hideView)
                                .translationX(deltaX)
                                .setDuration(animationTime);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (exceptItemFlg) {
                    return false;
                }
                if (AnimatorProxy.NEEDS_PROXY) {
                    moveDeltaX = motionEvent.getRawX() - lastMotionX;
                } else {
                    if (downPosition == -1) {
                        break;
                    }
                    float deltaX = motionEvent.getRawX() - lastMotionX;
                    isScrollInY = IsMovingInY(motionEvent.getRawX(),
                            motionEvent.getRawY());
                    if (isScrollInY) {
                        return false;
                    }
                    // lihs 2013.12.12 友盟报了NullPointException
                    if (hideView == null) {
                        return false;
                    }
                    final int hideViewWidth = hideView.getWidth();
                    deltaX += moveX;
                    if (deltaX >= 0) {
                        deltaX = 0;
                    }
                    if (deltaX <= -hideViewWidth) {
                        deltaX = -hideViewWidth;
                    }
                    ViewHelper.setTranslationX(showView, deltaX);
                    ViewHelper.setTranslationX(hideView, deltaX);
                    return false;
                }
            }
            default: {
                return false;
            }
            }

        } catch (Exception e) {
//            LogUtil.e("SlideListViewTouchListener onTouch exception:"
//                    + e);
        }
        return false;
    }

    private boolean IsMovingInY(float x, float y) {
        final int xDiff = (int) Math.abs(x - lastMotionX);
        final int yDiff = (int) Math.abs(y - lastMotionY);
        final int touchSlop = this.touchSlop;
        if (xDiff == 0) {
            return true;
        }
        if ((yDiff / xDiff >= 1) && (yDiff > touchSlop)) {
            return true;
        } else {
            return false;
        }
    }

    private int getPosition() {
        final int childCount = slideListView.getChildCount();
        int[] listViewCoords = new int[2];
        slideListView.getLocationOnScreen(listViewCoords);
        final int x = (int) lastMotionX - listViewCoords[0];
        final int y = (int) lastMotionY - listViewCoords[1];
        View child;
        for (int i = 0; i < childCount; i++) {
            child = slideListView.getChildAt(i);
            child.getHitRect(rect);
            if (rect.contains(x, y)) {
                return slideListView.getPositionForView(child);
            }
        }
        return -1;
    }
}
