package com.example.chen1.uncom.find;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by chen1 on 2017/11/10.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return TabPageForAbs.getInstance();
            case 1:return TabPageForDetail.getInstance();
            case 2:return TabPageForData.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
