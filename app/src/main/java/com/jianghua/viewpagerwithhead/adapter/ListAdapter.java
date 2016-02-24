package com.jianghua.viewpagerwithhead.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jianghua.viewpagerwithhead.R;

import java.util.List;

/**
 * Created by App-Dev on 2016/2/24.
 */
public class ListAdapter extends MyBaseAdapter<String> {

    public ListAdapter(List<String> list, Context context) {
        super(list, context);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position));

        return convertView;
    }

    private class ViewHolder {

        public TextView textView;

        public ViewHolder(View view) {
            this.textView = (TextView) view.findViewById(R.id.listView_layout_text);
        }
    }

}
