package com.example.chen1.uncom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by chen1 on 2017/7/2.
 */

public class ExpessionStandardAdapter extends BaseAdapter {

    private  ArrayMap<String,Integer> EmotionSet;
    private int CurrentPage;
    private int PageCount;

    public ExpessionStandardAdapter(ArrayMap<String,Integer> Emotion,int PageCount,int page,int ElementSize){
        EmotionSet=Emotion;
        this.PageCount=PageCount;
        this.CurrentPage=page;
    }

    @Override
    public int getCount() {
        if(PageCount==CurrentPage){
           return EmotionSet.size()-(CurrentPage-1)*20+1;
        }
        return 21;
    }

    @Override
    public Object getItem(int position) {

            return (CurrentPage-1)*20+position;

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
