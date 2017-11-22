package com.example.chen1.uncom.find;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.chen1.uncom.R;

import java.util.ArrayList;

/**
 * Created by chen1 on 2017/11/10.
 */

public class TabPagerAdapter extends PagerAdapter {
    private Context context;

    public TabPagerAdapter(Context context){
        this.context=context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        switch (position){
            case 0:
                view = View.inflate(context, R.layout.fragment_tab_page_for_abs, null);
                break;
            case 1:
                view = View.inflate(context, R.layout.fragment_tab_page_for_detail, null);
                break;
            case 2:
                view = View.inflate(context, R.layout.fragment_tab_page_for_data, null);
                break;
        }
        container.addView(view);
        return view;
    }



    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

   /* public TabPagerAdapter(FragmentManager fm) {
        super(fm);
        Log.v("构造Adapter","ok");
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return  new TabPageForAbs();
            case 1:return  new TabPageForDetail();
            case 2:return  new TabPageForData();
            default:return new TabPageForAbs();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }*/
}
