package com.example.chen1.uncom.relationship;

import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.chen1.uncom.R;

import java.util.zip.Inflater;

/**
 * Created by chen1 on 2017/6/21.
 */

public class GroupRelationShipAdapter extends BaseAdapter {



    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        ConstraintLayout constraintLayout= (ConstraintLayout) layoutInflater.inflate(R.layout.group_relation_ship_item_layout,null);
        return constraintLayout;
    }
}
