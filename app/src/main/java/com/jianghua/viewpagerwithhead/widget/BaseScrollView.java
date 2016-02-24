package com.jianghua.viewpagerwithhead.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by App-Dev on 2015/11/9.
 */
public class BaseScrollView extends ScrollView {
    public static final int UP_SCROLL = 1;
    public static final int DOWN_SCROLL = 2;
    private int orientation = 3;

    private OnScrollListener onScrollListener;
    private OnJustScrollListener onJustScrollListener;
    //用在用户手指离开MyScrollView，MyScrollView还在继续滑动，用来保存Y的距离
    private int lastScrollY;
    private float y_temp1 = 0;
    private float y_temp2 = 0;
    private boolean ifIntercept = false;
    private boolean isTop = false;
    private boolean ort;

    public boolean getOrt() {
        return ort;
    }

    public void setOrt(boolean ort) {
        this.ort = ort;
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

    //写一个onScrollListener的监听回调方法
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public void setOnJustScrollListener(OnJustScrollListener onJustScrollListener) {
        this.onJustScrollListener = onJustScrollListener;
    }

    //用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            int scrollY = BaseScrollView.this.getScrollY();
            if (msg.what == 1) {
                //此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
                if (lastScrollY != scrollY) {
                    lastScrollY = scrollY;
                    handler.sendMessageDelayed(handler.obtainMessage(1), 5);
                } else {
                    if (onScrollListener != null) {
                        onScrollListener.scrollOver(scrollY);
                    }
                }
                if (onScrollListener != null) {
                    onScrollListener.onScroll(scrollY);
                }

                View contentView = getChildAt(0);
                if (contentView.getMeasuredHeight() <= getScrollY() + getHeight()) {
                    if (onScrollListener != null) {
                        onScrollListener.onBottom();
                    }
                }
            } else {
                //此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
                if (lastScrollY != scrollY) {
                    if (onJustScrollListener != null) {
                        if (lastScrollY - scrollY > 0) {
                            onJustScrollListener.onScroll(scrollY, DOWN_SCROLL);
                            orientation = DOWN_SCROLL;
                        }
                        if (lastScrollY - scrollY < 0) {
                            onJustScrollListener.onScroll(scrollY, UP_SCROLL);
                            orientation = UP_SCROLL;
                        }
                    }
                    lastScrollY = scrollY;
                    handler.sendMessageDelayed(handler.obtainMessage(2), 50);
                }
                if (onJustScrollListener != null && orientation != 3) {
                    onJustScrollListener.onScroll(scrollY, orientation);
                }
            }

        }

    };

    /**
     * 重写onTouchEvent， 当用户的手在MyScrollView上面的时候，
     * 直接将MyScrollView滑动的Y方向距离回调给onScroll方法中，当用户抬起手的时候，
     * MyScrollView可能还在滑动，所以当用户抬起手我们隔5毫秒给handler发送消息，在handler处理
     * MyScrollView滑动的距离
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float y = this.getScrollY();

        if (onScrollListener != null) {
            onScrollListener.onScroll(lastScrollY = this.getScrollY());
        }
        if (onJustScrollListener != null && orientation != 3) {
            onJustScrollListener.onScroll(lastScrollY = this.getScrollY(), orientation);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                handler.sendMessageDelayed(handler.obtainMessage(2), 5);
                y_temp1 = y;
                break;
            case MotionEvent.ACTION_MOVE:
                y_temp1 = this.getScrollY();
                if (y_temp1 != 0 && onJustScrollListener != null) {
                    if (y_temp1 - y_temp2 > 0) {
                        onJustScrollListener.onScroll(getScrollY(), UP_SCROLL);
                        orientation = UP_SCROLL;
                    }
                    if (y_temp1 - y_temp2 < 0) {
                        onJustScrollListener.onScroll(getScrollY(), DOWN_SCROLL);
                        orientation = DOWN_SCROLL;
                    }
                }
                y_temp2 = y_temp1;
                break;
            case MotionEvent.ACTION_UP:
                handler.sendMessageDelayed(handler.obtainMessage(1), 5);
                handler.sendMessageDelayed(handler.obtainMessage(2), 50);
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ifIntercept || (isTop && ort)) {
            ort = false;
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    //滚动的回调接口1
    public interface OnScrollListener {
        //返回滑动的Y的距离
        public void onScroll(int scrollY);

        public void scrollOver(int scrollY);

        public void onBottom();
    }

    //滚动的回调接口2
    public interface OnJustScrollListener {
        //返回滑动的Y的距离
        public void onScroll(int scrollY, int orientation);
    }
}
