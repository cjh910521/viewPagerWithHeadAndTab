package com.jianghua.viewpagerwithhead.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jianghua.viewpagerwithhead.R;
import com.jianghua.viewpagerwithhead.adapter.SlidingPagerAdapter;
import com.jianghua.viewpagerwithhead.fragment.TestFragment;
import com.jianghua.viewpagerwithhead.utils.DensityUtil;
import com.jianghua.viewpagerwithhead.widget.BaseScrollView;
import com.jianghua.viewpagerwithhead.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements TestFragment.OnListViewIsTop, ViewPager.OnPageChangeListener {

    private Context mContext;
    private RelativeLayout mRootView;
    private PagerSlidingTabStrip mTabStrip;
    private ViewPager mViewPager;
    private BaseScrollView mScrollView;

    private List<Fragment> mFragments;
    private List<String> mTitle;

    private Timer mTimer;
    private float mLastScroll = 0;
    private boolean mGoInFlag = false;
    private boolean mListTop = true;
    private int mLimit;  //下滑进入listView的界限的高度

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                //重置viewpager的高度
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, msg.arg2 - msg.arg1 - DensityUtil.dip2px(mContext, 40));
                mViewPager.setLayoutParams(params);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        initData();
        initView();
    }

    private void initData() {
        mFragments = new ArrayList<>();
        mTitle = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mFragments.add(new TestFragment());
            mTitle.add("标题" + i);
        }
    }

    private void initView() {
        mRootView = (RelativeLayout) findViewById(R.id.rootView);
        mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.main_tab);
        mViewPager = (ViewPager) findViewById(R.id.main_viewPager);
        mScrollView = (BaseScrollView) findViewById(R.id.main_baseScrollView);

        mScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        SlidingPagerAdapter adapter = new SlidingPagerAdapter(getSupportFragmentManager(), mFragments, mTitle);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(this);

        mTabStrip.setViewPager(mViewPager);
        mTabStrip.setIndicatorColor(getResources().getColor(R.color.green));

        getLimitHeight();

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                int scrollY = mScrollView.getScrollY();
                if (scrollY > mLimit && !mGoInFlag) {
                    mGoInFlag = true;
                    mScrollView.setIfIntercept(true);
                }
                if (scrollY < mLimit) {
                    mGoInFlag = false;
                }
                try {
                    if (mRootView != null) {
                        Rect r = new Rect();
                        mRootView.getWindowVisibleDisplayFrame(r);
                        Message message = handler.obtainMessage(1);
                        message.arg1 = r.top;
                        message.arg2 = r.bottom;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (scrollY == 0) {
                    mScrollView.setIsTop(true);
                } else {
                    mScrollView.setIsTop(false);
                }

            }
        }, 0, 100);
    }

    /**
     * 获取头部高度
     */
    private void getLimitHeight() {
        findViewById(R.id.main_header).post(new Runnable() {
            @Override
            public void run() {
                int headHeight = findViewById(R.id.main_header).getMeasuredHeight();
                mLimit = headHeight + DensityUtil.dip2px(mContext, 1);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mLastScroll != 0 && mLastScroll - ev.getRawY() > 0) { //向下滑动，即手指向上
            if (mScrollView.getScrollY() == mLimit) {
                mScrollView.setIfIntercept(true);
                mGoInFlag = true;
            }
        }
        if (mLastScroll != 0 && mLastScroll - ev.getRawY() < 0) { //向上滑动,即手指向下
            if (mScrollView.getScrollY() == mLimit && mListTop) {
                mScrollView.setIfIntercept(false);
            }
            mScrollView.setOrientation(true);
        } else {
            mScrollView.setOrientation(false);
        }

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            mLastScroll = 0;
            mScrollView.setOrientation(false);
        } else {
            mLastScroll = ev.getRawY();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void isTop(boolean ifTop) {
        if (ifTop) {
            mScrollView.setIfIntercept(false);
            mGoInFlag = false;
            mListTop = true;
        } else {
            mListTop = false;
        }
    }

    @Override
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mListTop = true;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
