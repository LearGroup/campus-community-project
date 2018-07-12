package com.example.chen1.uncom.find;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by chen1 on 2017/11/17.
 */

public class TabPageDatGridAdapter extends BaseAdapter {
    private int count;

    public TabPageDatGridAdapter(int Count){
        count=Count;
    }


    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
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
