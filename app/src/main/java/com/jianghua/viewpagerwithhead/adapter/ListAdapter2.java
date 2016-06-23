package com.jianghua.viewpagerwithhead.adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jianghua.viewpagerwithhead.R;
import com.jianghua.viewpagerwithhead.widget.ScaleEvaluator;

import java.util.List;

/**
 * Created by App-Dev on 2016/2/24.
 */
public class ListAdapter2 extends MyBaseAdapter<String> {

    public ListAdapter2(List<String> list, Context context) {
        super(list, context);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_layout2, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        startAnim(viewHolder.imageView);
        return convertView;
    }

    private void startAnim(View view) {
        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1.0f);
        fadeAnim.setDuration(600);
        ScaleEvaluator scaleEvaluator = new ScaleEvaluator();
        scaleEvaluator.setDuration(200);
        fadeAnim.setEvaluator(scaleEvaluator);
        fadeAnim.start();

        ValueAnimator fadeAnim2 = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1.0f);
        fadeAnim2.setDuration(600);
        ScaleEvaluator scaleEvaluator2 = new ScaleEvaluator();
        scaleEvaluator2.setDuration(200);
        fadeAnim2.setEvaluator(scaleEvaluator2);
        fadeAnim2.start();
    }

    private class ViewHolder {

        public ImageView imageView;

        public ViewHolder(View view) {
            this.imageView = (ImageView) view.findViewById(R.id.myImageView);
        }
    }

}
