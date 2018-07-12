package com.example.chen1.uncom.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.chen1.uncom.communication.SendThread;
import com.example.chen1.uncom.find.FindPageMainFragment;
import com.example.chen1.uncom.me.MePageMainFragment;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.relationship.RalationShipPageMainFragment;
import com.example.chen1.uncom.set.SetMessage;
import com.example.chen1.uncom.set.SetPageMainFragment;
import com.example.chen1.uncom.service.ChatCoreBinder;
import com.example.chen1.uncom.service.CoreService;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.BadgeMessageUtil;
import com.example.chen1.uncom.utils.MessageEvent;
import com.example.chen1.uncom.utils.StateCode;
import com.huantansheng.easyphotos.EasyPhotos;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,BottomNavigationBar.OnTabSelectedListener{
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    private View ll_root;
    /**
     * 窗体控件上一次的高度,用于监听键盘弹起
     */
    private static int CONNECTION_ERROR =-1;
    private  static  int RECONNECTION =0;
    private Toolbar toolbar;
    private ArrayList<OnTouchListener> touchListeners=new ArrayList<>();
    private SetPageMainFragment setPageMainFragment;
    private RalationShipPageMainFragment ralationShipPageMainFragment;
    private FindPageMainFragment findPageMainFragment;
    private MePageMainFragment mePageMainFragment;
    private int mLastHeight;
    private PopupWindow popupWindow;
    private   DrawerLayout drawer;
    private MenuItem menuItem=null;
    private ViewPager viewPager;
    private NavigationView navigationView;
    private View rootView;
    private BottomNavigationBar bottomNavigationBar;
    private BottomNavigationView bottomNavigationView;
//    private SectionsAdapter sectionsAdapter;
    private MainFragment mainFragment;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CoreApplication.newInstance().inApp=true;
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
        CoreApplication.newInstance().SyncData(CoreApplication.newInstance().getApplicationContext());
        setContentView(R.layout.activity_main);
//        mainFragment=MainFragment.newInstance();
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,mainFragment,"MainFragment").commitAllowingStateLoss();

        MIUISetStatusBarLightMode(getWindow(),true);
        FlymeSetStatusBarLightMode(getWindow(),true);
        rootView=findViewById(R.id.app_bar_main);
        CoreApplication.newInstance().setRoot(rootView);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        viewPager=(ViewPager)findViewById(R.id.container);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        bottomNavigationBar=(BottomNavigationBar)findViewById(R.id.bottom_navigation);
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
        SectionsAdapter sectionsAdapter=new SectionsAdapter(getSupportFragmentManager());
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
        bindDrawer();

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
                    return  fragment;
                }
                case  1:{
                    RalationShipPageMainFragment fragment=RalationShipPageMainFragment.newInstance();
                    return  fragment;
                }
                case  2:{
                    FindPageMainFragment fragment=FindPageMainFragment.newInstance();
                    return  fragment;
                }
                case  3:{
                    MePageMainFragment fragment=MePageMainFragment.newInstance();
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.v("onNewIntent","MainActivity");
        CoreApplication.newInstance().notification.clean();
        if(intent.getStringExtra("type").equals(StateCode.PERSON_CHAT_PAGE)){
            EventBus.getDefault().post(new SetMessage(intent.getStringExtra("id"),StateCode.PERSON_CHAT_PAGE));
        }
        Log.v("id",intent.getStringExtra("id"));
        //int messageType=getIntent().getIntExtra("message",);

    }
    public DrawerLayout getDrawer(){
        return  drawer;
    }

    public void bindDrawer(){
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();
    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    navigationView.setItemIconTintList(null);
    navigationView.setNavigationItemSelectedListener(this);
}

public void unBindDrawer(){

    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
}








    public interface OnTouchListener{
        public boolean onTouch(MotionEvent ev);
    }

    public  boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
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

    public  boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("onActivityResult","success");
        Log.v("requestCode", String.valueOf(requestCode));
        Log.v("resultCode", String.valueOf(resultCode));
        ArrayList<String> resultPaths;
        if(requestCode==101 && resultCode==RESULT_OK){
            Log.v("executeds","success");
            resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
            EventBus.getDefault().post(new MessageEvent(resultPaths));
        }

        if(requestCode==CoreApplication.WRITE_THINKE_FRAGMENT && resultCode==RESULT_OK){
            Log.v("executed","success");
            resultPaths= data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
            EventBus.getDefault().post(new MessageEvent(resultPaths));
        }
    }






    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    Log.v("onNavigationItemSelected","begin");
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
            if(CoreApplication.newInstance().getCoreService()==null){
                Log.v("startService","success");
               CoreApplication.newInstance().startServices();
            }
            CoreApplication.newInstance().getCoreService().getSendChatHandler().sendMessage(message);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        CoreApplication.newInstance().inApp=true;
        Log.v("MainActivity","onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        CoreApplication.newInstance().inApp=false;
        Log.v("MainActivity","onStop");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CoreApplication.newInstance().setRoot(null);
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            Intent i= new Intent(Intent.ACTION_MAIN);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
        }
    }

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
