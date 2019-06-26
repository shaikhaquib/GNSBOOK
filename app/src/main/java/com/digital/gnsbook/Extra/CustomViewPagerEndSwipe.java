package com.digital.gnsbook.Extra;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class CustomViewPagerEndSwipe extends ViewPager {

    public CustomViewPagerEndSwipe(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    float mStartDragX;
    OnSwipeOutListener mListener;


    public void setOnSwipeOutListener(OnSwipeOutListener listener) {
        mListener = listener;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartDragX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mStartDragX < x && getCurrentItem() == 0) {
                    mListener.onSwipeOutAtStart();
                } else if (mStartDragX > x && getCurrentItem() == getAdapter().getCount() - 1) {
                    mListener.onSwipeOutAtEnd();
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public interface OnSwipeOutListener {
        public void onSwipeOutAtStart();

        public void onSwipeOutAtEnd();
    }
}