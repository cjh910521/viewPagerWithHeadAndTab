package com.jianghua.viewpagerwithhead.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.jianghua.viewpagerwithhead.R;
import com.jianghua.viewpagerwithhead.adapter.ListAdapter2;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        listView = (ListView) findViewById(R.id.listView);
        List<String> s = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            s.add("" + i);
        }

        ListAdapter2 adapter = new ListAdapter2(s, this);
        listView.setAdapter(adapter);

    }

}
