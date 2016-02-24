package com.jianghua.viewpagerwithhead.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.jianghua.viewpagerwithhead.R;
import com.jianghua.viewpagerwithhead.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment implements AbsListView.OnScrollListener {

    private PtrClassicFrameLayout mPtrLayout;
    private TextView mPtrText;
    private ListView mListView;

    private OnListViewIsTop onListViewIsTop;

    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        initView(inflater, view);
        initPtr(inflater);

        return view;
    }


    private void initView(LayoutInflater inflater, View view) {
        mPtrLayout = (PtrClassicFrameLayout) view.findViewById(R.id.fm_test_ptrLayout);
        mListView = (ListView) view.findViewById(R.id.fm_test_listView);
        View listHeader = inflater.inflate(R.layout.list_empty_header_layout, null);  //添加一个空的头部，避免上滑时第一个item显示不全
        mListView.addHeaderView(listHeader);
        mListView.setOnScrollListener(this);
        mListView.setFocusable(false); //左右滑动才能不跳到头部

        List<String> list = new ArrayList();
        for (int i = 0; i < 20; i++) {
            list.add("测试测试" + i);
        }
        mListView.setAdapter(new ListAdapter(list, getActivity()));
    }

    private void initPtr(LayoutInflater inflater) {
        View header = inflater.inflate(R.layout.fm_test_ptrhead_layout, null);
        mPtrText = (TextView) header.findViewById(R.id.loading_text);
        mPtrLayout.setDurationToCloseHeader(1000);
        mPtrLayout.setDurationToClose(2000);
        mPtrLayout.setHeaderView(header);
        mPtrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                mPtrText.setText(R.string.release);
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrText.setText(R.string.refreshing);
                mPtrLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //刷新显示的代码
                        mPtrLayout.refreshComplete();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onListViewIsTop = (OnListViewIsTop) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement IndexListener");
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        if (i == 0) {
            onListViewIsTop.isTop(true);
        } else {
            onListViewIsTop.isTop(false);
        }
    }

    public interface OnListViewIsTop {
        void isTop(boolean ifTop);
    }
}
