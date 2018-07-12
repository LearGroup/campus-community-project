package com.example.chen1.uncom.main;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RemoteViews;

import com.airbnb.lottie.LottieAnimationView;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.chat.PersonChatFragment;
import com.example.chen1.uncom.find.FindPageMainFragment;
import com.example.chen1.uncom.me.MePageMainFragment;
import com.example.chen1.uncom.relationship.RalationShipPageMainFragment;
import com.example.chen1.uncom.set.SetPageMainFragment;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.BadgeMessageUtil;
import com.example.chen1.uncom.utils.StateCode;
import com.squareup.haha.perflib.Main;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainFragment extends Fragment implements FragmentBackHandler,BottomNavigationBar.OnTabSelectedListener{


    private BottomNavigationBar bottomNavigationBar;
    private ViewPager viewPager;
    private SectionsAdapter sectionsAdapter;
    private FrameLayout frameLayout;
    private View view;
    private Toolbar toolbar;
    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view=inflater.inflate(R.layout.fragment_main, container, false);
        frameLayout= (FrameLayout) view.findViewById(R.id.app_bar_main);
        CoreApplication.newInstance().setRoot(frameLayout);
        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager=(ViewPager)view.findViewById(R.id.container);
        toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        bottomNavigationBar=(BottomNavigationBar)view.findViewById(R.id.bottom_navigation);
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBarBackgroundColor(R.color.colorWhite);
        bottomNavigationBar.setInActiveColor(R.color.colorIcon);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_dashboard_black_24dp, "聚合").setActiveColorResource(R.color.colorMain).setBadgeItem(BadgeMessageUtil.getBadgeItemByPosition(0).setText("1")))
                .addItem(new BottomNavigationItem(R.drawable.ic_vector_menu_relationship02, "关系").setActiveColorResource(R.color.colorMain).setBadgeItem(BadgeMessageUtil.getBadgeItemByPosition(1)))
                .addItem(new BottomNavigationItem(R.drawable.ic_vector_menu_find, "发现").setActiveColorResource(R.color.colorMain).setBadgeItem(BadgeMessageUtil.getBadgeItemByPosition(2)))
                .addItem(new BottomNavigationItem(R.drawable.ic_vector_my, "我").setActiveColorResource(R.color.colorMain).setBadgeItem(BadgeMessageUtil.getBadgeItemByPosition(3)))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成
        BadgeMessageUtil.setItem_1(BadgeMessageUtil.getItem_1());
        BadgeMessageUtil.setItem_2(BadgeMessageUtil.getItem_2());
        BadgeMessageUtil.setItem_3(BadgeMessageUtil.getItem_3());
        BadgeMessageUtil.setItem_4(BadgeMessageUtil.getItem_4());
        sectionsAdapter=new SectionsAdapter(getChildFragmentManager());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationBar.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(sectionsAdapter);
    }

    @Override
    public boolean onBackPressed() {
        return BackHandlerHelper.handleBackAllImmediate(this);
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                CoreApplication.newInstance().basePagerPosition=0;
                Log.v("currentPgaerPosition","0");
                viewPager.setCurrentItem(0);
                break;

            case 1:
                CoreApplication.newInstance().basePagerPosition=1;
                Log.v("currentPgaerPosition","1");
                viewPager.setCurrentItem(1);
                break;
            case 2:
                CoreApplication.newInstance().basePagerPosition=2;
                Log.v("currentPgaerPosition","2");
                viewPager.setCurrentItem(2);
                break;
            case 3:
                CoreApplication.newInstance().basePagerPosition=3;
                Log.v("currentPgaerPosition","3");
                viewPager.setCurrentItem(3);
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    public class SectionsAdapter extends FragmentPagerAdapter {

        public SectionsAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            switch (position){
                case  0:{
                    SetPageMainFragment fragment=SetPageMainFragment.newInstance();
                    fragment.setTargetFragment(MainFragment.this,11123);
                    return  fragment;
                }
                case  1:{
                    RalationShipPageMainFragment fragment=RalationShipPageMainFragment.newInstance();
                    fragment.setTargetFragment(MainFragment.this,11123);
                    return  fragment;
                }
                case  2:{
                    FindPageMainFragment fragment=FindPageMainFragment.newInstance();
                    fragment.setTargetFragment(MainFragment.this,11123);
                    return  fragment;
                }
                case  3:{
                    MePageMainFragment fragment=MePageMainFragment.newInstance();
                    fragment.setTargetFragment(MainFragment.this,11123);
                    return fragment;
                }
            }
            return new SetPageMainFragment();
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void syncMessage(MainMessage mainMessage){
        Log.v("MainFragment","parseEvent");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("onDestiry","rootView");
        EventBus.getDefault().unregister(this);
        CoreApplication.newInstance().setRoot(null);
    }

}
