package com.example.chen1.uncom.expression;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.EditText;

import java.util.List;

/**
 * Created by chen1 on 2017/6/28.
 */

public class ChatExpressionTypePageSwitchAdapter extends FragmentPagerAdapter {


    private int  ExpressionTypeCount=2;
    private EditText editText;
    public ChatExpressionTypePageSwitchAdapter(FragmentManager fm, List<Integer>viewlist,EditText editText) {
        super(fm);
        ExpressionTypeCount=viewlist.size();
        this.editText=editText;
        Log.v("information: ", String.valueOf(ExpressionTypeCount));
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return  ChatExpressionStandardFragment.newInstance(editText);
            case 1: return new ChatExpressionCustomFragment(position);
        }
        return new ChatExpressionCustomFragment(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 3;
    }
}
