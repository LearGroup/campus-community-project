package com.example.chen1.uncom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.security.cert.CertificateExpiredException;
import java.util.List;

/**
 * Created by chen1 on 2017/6/28.
 */

public class ChatExpressionTypePageSwitchAdapter extends FragmentPagerAdapter {


    private int  ExpressionTypeCount=2;

    public ChatExpressionTypePageSwitchAdapter(FragmentManager fm, List<Integer>viewlist) {
        super(fm);
        ExpressionTypeCount=viewlist.size();
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
            case 0: return new ChatExpressionStandardFragment();
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
