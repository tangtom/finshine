/*
 * DragSortListView.
 *
 * A subclass of the Android ListView component that enables drag
 * and drop re-ordering of list items.
 *
 * Copyright 2012 Carl Bauer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.incito.finishe.pulllistview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

/**
 * ListView subclass that mediates drag and drop resorting of items.
 * 
 * 
 * @author heycosmo
 * 
 */
public class AnimationRemoveListView extends ListView {
    
    private SlideListViewTouchListener touchListener = null;
    
    public AnimationRemoveListView(Context context) {
        super(context);
    }

    public AnimationRemoveListView(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
    }

    public AnimationRemoveListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initSlideListViewTouchListener() {
        final ViewConfiguration configuration = ViewConfiguration
                .get(getContext());
        final int touchSlop = ViewConfigurationCompat
                .getScaledPagingTouchSlop(configuration);
        touchListener = new SlideListViewTouchListener(this, touchSlop);
        setOnTouchListener(touchListener);
    }
    
    public void setShowDelBtnRes(int res){
        if(touchListener != null){
            touchListener.setShowDelBtnRes(res);
        }
    }
    
    public void setShowDelOpBtnRes(int res){
        if(touchListener != null){
            touchListener.setShowDelOpBtnRes(res);
        }
    }

    public void setItemRes(int showRes, int hideRes){
    	if(touchListener != null){
    		touchListener.setItemRes(showRes, hideRes);
    	}
    }

    private List<Animation> animationList = new ArrayList<Animation>();
    private int maxWidth = 0;

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    /**
     * 删除item，并播放动画,该方法仅用于删除多个View时调用
     * 
     * @param rowView
     *            播放动画的view
     * @param position
     *            要删除的item位置
     */
    public void removeListItem(View rowView, final int position) {
        Log.d("removeListItem", "removeListItem:" + position);
        // 多动画组合有问题，调用cancel方法的时候会调用onAnimationEnd，按现有逻辑会导致死循环
        // AnimationSet animationSet = new AnimationSet(true);
        if (maxWidth == 0) {
            maxWidth = 800;
        }
        Log.d("removeListItem", "maxWidth == " + maxWidth);
        // AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
        final MyAnimation translate = new MyAnimation(0, maxWidth, 0, 0);
        // final TranslateAnimation translate = new TranslateAnimation(0,
        // maxWidth, 0, 0);
        translate.setFillAfter(true);
        // animationSet.addAnimation(alpha);
        // animationSet.addAnimation(translate);
        translate.setDuration(400);
        // animationSet.setFillAfter(true);

        translate.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (translate.isCancel) {
                    return;
                }
                if (itemRemoveCallBack != null) {
//                    Log.d(NoticeFragment.NOTICE_TAG, "onAnimationEnd:"
//                            + position);
                    itemRemoveCallBack.itemRemoveCallBack(position);
                } else {
//                    Log.d(NoticeFragment.NOTICE_TAG,
//                            "itemRemoveCallBack == null");
                }
            }
        });
        if (rowView != null) {
            rowView.startAnimation(translate);
            animationList.add(translate);
        }
    }

    /**
     * @Title: allAnimationCancel
     * @Description: 取消全部动画，删除多个View完成后调用该方法，否则会导致部分view不能显示
     *               原因是view重用，动画设置了view停留在动画结束时位置(屏幕外)
     * @return: void
     */
    public void allAnimationCancel() {
        if (animationList != null) {
            int size = animationList.size();
            for (int count = 0; count < size; count++) {
                Log.d("allAnimationCancel", "cancel: " + count);
                animationList.get(count).cancel();
            }
            animationList.clear();
        }
    }

    private ItemRemoveCallBack itemRemoveCallBack;

    public void setItemRemoveCallBack(ItemRemoveCallBack callBack) {
        this.itemRemoveCallBack = callBack;
    }

    public interface ItemRemoveCallBack {
        public void itemRemoveCallBack(int position);
    }

    private class MyAnimation extends TranslateAnimation {

        private boolean isCancel = false;

        public MyAnimation(float fromXDelta, float toXDelta, float fromYDelta,
                float toYDelta) {
            super(fromXDelta, toXDelta, fromYDelta, toYDelta);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void cancel() {
            isCancel = true;
            super.cancel();
        }
    }
}
