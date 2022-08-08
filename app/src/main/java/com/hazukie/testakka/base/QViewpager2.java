package com.hazukie.testakka.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.qmuiteam.qmui.widget.QMUIViewPager;

public class QViewpager2  extends QMUIViewPager {
    private boolean isCanScroll=false;
    public QViewpager2(Context context) {
        super(context);
    }

    public QViewpager2(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(isCanScroll) return super.onInterceptTouchEvent(ev);
        else return false;
    }

    public void setCanScroll(boolean isCanScroll){
        this.isCanScroll=isCanScroll;
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }
}
