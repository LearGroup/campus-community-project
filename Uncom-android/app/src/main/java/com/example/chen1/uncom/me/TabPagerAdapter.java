package com.example.chen1.uncom.me;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by chen1 on 2017/11/19.
 */

public class TabPagerAdapter extends FragmentPagerAdapter{
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return TabPagerForMain.newInstance();
            case 1:return TabPagerForDetail.newInstance();
            default:return TabPagerForMain.newInstance();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
