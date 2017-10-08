package com.example.chen1.uncom.expression;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import javax.security.auth.Destroyable;

/**
 * Created by chen1 on 2017/7/25.
 */

public class ExpressionStandardPagedapter extends PagerAdapter {
    private ArrayMap<String, Integer> Emotion_map;
    private Integer ExpressionPageCount;
    private ArrayList<LinearLayout> viewList;

    public ExpressionStandardPagedapter(ArrayList<LinearLayout> viewlist,Integer pageCount) {
        this.viewList=viewlist;
        ExpressionPageCount=pageCount;
    }


    @Override
    public int getCount() {
        return ExpressionPageCount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(viewList.get(position),0);
        /* viewList.get(position).getLayoutParams().height=1000;
        viewList.get(position).setLayoutParams( viewList.get(position).getLayoutParams());*/
        /*Log.v("Standard Expression current height:", String.valueOf(itemWidth));*/
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }


}
