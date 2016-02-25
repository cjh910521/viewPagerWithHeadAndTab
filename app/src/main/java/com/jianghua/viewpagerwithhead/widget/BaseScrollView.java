package com.jianghua.viewpagerwithhead.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by App-Dev on 2015/11/9.
 */
public class BaseScrollView extends ScrollView {

    private boolean ifIntercept = false;
    private boolean isTop = false;
    private boolean orientation;

    public boolean getOrientation() {
        return orientation;
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    public boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(boolean isTop) {
        this.isTop = isTop;
    }

    public boolean isIfIntercept() {
        return ifIntercept;
    }

    public void setIfIntercept(boolean ifIntercept) {
        this.ifIntercept = ifIntercept;
    }

    public BaseScrollView(Context context) {
        super(context);
    }

    public BaseScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ifIntercept || (isTop && orientation)) {
            orientation = false;
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }
}
