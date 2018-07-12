package com.example.chen1.uncom.me;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * Created by chen1 on 2017/11/19.
 */

public class TabPagerAdapter extends FragmentPagerAdapter{
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    private TabPagerForMain tabPagerForMain=TabPagerForMain.newInstance();
    private TabPagerForDetail tabPagerForDetail=TabPagerForDetail.newInstance();

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return tabPagerForMain;
            case 1:return tabPagerForDetail;
            default:return tabPagerForDetail;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
