package com.example.chen1.uncom.main;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.chen1.uncom.find.FindPageMainFragment;
import com.example.chen1.uncom.me.MePageMainFragment;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.relationship.RalationShipPageMainFragment;
import com.example.chen1.uncom.set.SetPageMainFragment;
import com.example.chen1.uncom.service.ChatCoreBinder;
import com.example.chen1.uncom.service.CoreService;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.BadgeMessageUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationBar.OnTabSelectedListener{

    private View ll_root;
    /**
     * 窗体控件上一次的高度,用于监听键盘弹起
     */
    private int CONNECTION_ERROR =-1;
    private int RECONNECTION =0;
    private SetPageMainFragment setPageMainFragment;
    private RalationShipPageMainFragment ralationShipPageMainFragment;
    private FindPageMainFragment findPageMainFragment;
    private MePageMainFragment mePageMainFragment;
    private int mLastHeight;
    private PopupWindow popupWindow;
    private MenuItem menuItem=null;
    private ViewPager viewPager;
    private NavigationView navigationView;
    private ChatCoreBinder chatCoreBinder;
    private  CoreService coreService;
    private Handler coreHandler;
    private View rootView;
    private BottomNavigationBar bottomNavigationBar;
    private BottomNavigationView bottomNavigationView;
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            chatCoreBinder=(ChatCoreBinder)service;
            try {
                coreService=chatCoreBinder.getCoreService();
                CoreApplication.newInstance().setCoreService(coreService);
                coreService.setHandler(coreHandler);
                coreService.setContext(getApplicationContext());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private SectionsAdapter sectionsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("MainActivityOnceate",".............ok");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }




        setContentView(R.layout.activity_main);
        MIUISetStatusBarLightMode(getWindow(),true);
        FlymeSetStatusBarLightMode(getWindow(),true);
       // StatusBarColorUtil.setTranslateStatusBar(this);
       // StatusBarColorUtil.setStatusBarMode(this,false);
     /*   Toolbar toolbar = (Toolbar)this.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);*/
        bottomNavigationBar=(BottomNavigationBar)findViewById(R.id.bottom_navigation);
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBarBackgroundColor(R.color.colorPrimary);
        bottomNavigationBar.setInActiveColor(R.color.colorIcon);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_dashboard_black_24dp, "聚合").setActiveColorResource(R.color.colorMain).setBadgeItem(BadgeMessageUtil.getBadgeItemByPosition(0).setText("1")))
                .addItem(new BottomNavigationItem(R.drawable.ic_vector_menu_relationship02, "关系").setActiveColorResource(R.color.colorMain).setBadgeItem(BadgeMessageUtil.getBadgeItemByPosition(1)))
                .addItem(new BottomNavigationItem(R.drawable.ic_vector_menu_find, "发现").setActiveColorResource(R.color.colorMain).setBadgeItem(BadgeMessageUtil.getBadgeItemByPosition(2)))
                .addItem(new BottomNavigationItem(R.drawable.ic_vector_my, "我").setActiveColorResource(R.color.colorMain).setBadgeItem(BadgeMessageUtil.getBadgeItemByPosition(3)))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成



        navigationView = (NavigationView) findViewById(R.id.nav_view);
       // bottomNavigationView =(BottomNavigationView) findViewById(R.id.navigation);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    /*    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorIcon), PorterDuff.Mode.SRC_ATOP);
*/        sectionsAdapter=new SectionsAdapter(getSupportFragmentManager());
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager=(ViewPager) findViewById(R.id.container);
        rootView=findViewById(R.id.rootview);
        CoreApplication.newInstance().setRoot(rootView);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationBar.selectTab(position);
             /*   if (menuItem!=null){
                    menuItem.setChecked(false);
                }else{
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem=bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(sectionsAdapter);
      //  BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Intent startIntent =new Intent(this, CoreService.class);
        getApplicationContext().startService(startIntent);
        CoreApplication.newInstance().setServiceConnection(serviceConnection);
        getApplicationContext().bindService(startIntent,serviceConnection,BIND_AUTO_CREATE);
       //网络连接状态判断
        coreHandler= new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        SetPageMainFragment.getInstance().setCONNECTION_STATUS(CONNECTION_ERROR);
                        break;
                    case 1:
                        SetPageMainFragment.getInstance().setCONNECTION_STATUS(RECONNECTION);
                           break;
                    default:
                        break;
                }
            }
        };
        CoreApplication.newInstance().syncData(0);
    }

    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                viewPager.setCurrentItem(0);
                break;

            case 1:
                viewPager.setCurrentItem(1);
                break;
            case 2:
                viewPager.setCurrentItem(2);
                break;
            case 3:
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


    public class SectionsAdapter extends FragmentPagerAdapter{

        public SectionsAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            switch (position){
                case  0:return SetPageMainFragment.getInstance();
                case  1:return RalationShipPageMainFragment.getInstance();
                case  2:return FindPageMainFragment.getInstance();
                case  3:return MePageMainFragment.getInstance();
            }
            return new SetPageMainFragment();
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Toast toast=Toast.makeText(getApplicationContext(),"camera",Toast.LENGTH_SHORT);
            toast.show();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_ofline) {
            Log.v("点击退出登陆按钮","ok");
            Message message=new Message();
            message.what=1;
            message.obj="usr_session_id";
            CoreApplication.newInstance().setActivity(this);
            CoreApplication.newInstance().getCoreService().getSendChatHandler().sendMessage(message);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
