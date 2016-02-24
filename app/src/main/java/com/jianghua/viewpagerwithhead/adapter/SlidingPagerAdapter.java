package com.jianghua.viewpagerwithhead.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 15-7-27.
 * 左右切换的viewPager的适配器
 */
public class SlidingPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private List<String> title = new ArrayList<>();

    public SlidingPagerAdapter(FragmentManager fm, List<Fragment> list,List<String> title) {
        super(fm);
        this.list = list;
        this.title = title;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

}
