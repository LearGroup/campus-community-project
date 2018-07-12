package com.example.chen1.uncom.me;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.UserBean;
import com.example.chen1.uncom.chat.PersonChatFragment;
import com.example.chen1.uncom.main.MainActivity;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.GestureListener;
import com.example.chen1.uncom.utils.GlideApp;
import com.example.chen1.uncom.utils.GlideCircleTransform;
import com.example.chen1.uncom.utils.LoadImageUtils;
import com.example.chen1.uncom.utils.SwipLayout;

import de.hdodenhof.circleimageview.CircleImageView;


public class MeDetailPage extends Fragment implements FragmentBackHandler,View.OnTouchListener{

    private AppCompatTextView editBtn;
    private ViewPager viewPager;
    private int request_code=2001;
    private  CollapsingToolbarLayout collapsing_toolbar_layout;
    private TabLayout tabLayout;
    private   Toolbar toolbar;
    private ImageView headImage;
    private FragmentTransaction fragmentTransaction;
    private TabPagerAdapter tabPagerAdapter;
    private LoadImageUtils loadImageUtils;
    private GestureDetector gestureDetector;
    private ImageView head_img;
    private UserBean userBean;
    private  TextView edit_btn;
    private  AppBarLayout appBarLayout;
    private  AppCompatImageView back_btn;
    private SwipLayout swipLayout;
    private  View view;
    public MeDetailPage() {
        loadImageUtils=new LoadImageUtils();
        // Required empty public constructor
    }


    public static MeDetailPage newInstance() {
        MeDetailPage fragment = new MeDetailPage();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(userBean==null){
            userBean=CoreApplication.newInstance().getUserBean();
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v("MeDetailPage","onCreateView");
        if(view==null){
            view= inflater.inflate(R.layout.fragment_me_detail_page, container, false);
        }
        ((MainActivity)getActivity()).unBindDrawer();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        swipLayout=new SwipLayout(getContext());
        swipLayout.setLayoutParams(params);
        swipLayout.setBackgroundColor(Color.TRANSPARENT);
        swipLayout.removeAllViews();
        swipLayout.addView(view);
        swipLayout.setParentView(CoreApplication.newInstance().getRoot()).setFragment(MeDetailPage.this);
        return swipLayout;


    }

    @Override
    public void onStart() {
        super.onStart();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
                    if(isAdded()){
                        back_btn.setImageResource(R.drawable.ic_vector_back_icon);
                        int color = getResources().getColor(R.color.colorFontColor);
                        edit_btn.setTextColor(R.color.colorFontColor);
                        edit_btn.setTextColor(color);
                    }
                }else{
                    if(isAdded()){
                        int color = getResources().getColor(R.color.colorTransParentFont);
                        back_btn.setImageResource(R.drawable.ic_vector_back_transparent);
                        edit_btn.setTextColor(color);
                    }
                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editBtn= (AppCompatTextView) view.findViewById(R.id.edit_btn);
        viewPager= (ViewPager) view.findViewById(R.id.mypage_viewpager);
        tabLayout=(TabLayout)view.findViewById(R.id.mypage_tablayout);
        toolbar = (Toolbar)view. findViewById(R.id.toolbar);
        toolbar.setTitleMarginTop(-35);
        toolbar.setTitleMarginStart(25);
        edit_btn= (TextView) view.findViewById(R.id.edit_btn);
        headImage= (ImageView) view.findViewById(R.id.back_img);
        back_btn= (AppCompatImageView) view.findViewById(R.id.back_btn);
        collapsing_toolbar_layout = (CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar_layout);
        appBarLayout=(AppBarLayout) view.findViewById(R.id.appbar_layout);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                getFragmentManager().popBackStack();
                CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(), R.anim.default_open_right));
            }
        });
        GlideApp.with(this)
                .load(userBean.getHeader_pic()).transition(new DrawableTransitionOptions().crossFade())
                .into(headImage);
        toolbar.setTitle(CoreApplication.newInstance().getUserBean().getUsername());
        collapsing_toolbar_layout.setTitle(CoreApplication.newInstance().getUserBean().getUsername());
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                MeInfoEdit meInfoEdit= (MeInfoEdit) fragmentManager.findFragmentByTag("MeInfoEdit");
                fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_leave_translate,
                        R.anim.default_fragment_switch_leave_translate
                );
                if(meInfoEdit!=null){
                    meInfoEdit.setTargetFragment(MeDetailPage.this,request_code);
                    fragmentTransaction.show(meInfoEdit).commitAllowingStateLoss();
                }else{
                    meInfoEdit=MeInfoEdit.newInstance();
                    meInfoEdit.setTargetFragment(MeDetailPage.this,request_code);
                     fragmentTransaction.add(R.id.drawer_layout, meInfoEdit, "MeInfoEdit").commitAllowingStateLoss();
                }
                getView().setAnimation((AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_leave_left)));

            }
        });
        tabPagerAdapter=new TabPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("个人主页");
        tabLayout.getTabAt(1).setText("详细资料");
    }

    public void updateInfo(){
        Log.v("updateInfo","success");
        Log.v("userName",CoreApplication.newInstance().getUserBean().getUsername());
        GlideApp.with(this)
                .load(userBean.getHeader_pic()).transition(new DrawableTransitionOptions().crossFade())
                .into(headImage);
        toolbar.setTitle(CoreApplication.newInstance().getUserBean().getUsername());
        collapsing_toolbar_layout.setTitle(CoreApplication.newInstance().getUserBean().getUsername());
        ((MePageMainFragment)getTargetFragment()).updateInfo();
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tabPagerAdapter=null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden==false){
            ((MainActivity)getActivity()).unBindDrawer();
        }
    }


    public void updateUserBean(UserBean bean){
        this.userBean=bean;
    }

    @Override
    public boolean onBackPressed() {
        fragmentTransaction=getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.default_fragment_switch_leave_translate,
                R.anim.default_fragment_switch_leave_translate,
                R.anim.default_fragment_switch_translate_open,
                R.anim.default_fragment_switch_translate_open
        ).hide(this).commitAllowingStateLoss();
        ((MainActivity)getActivity()).bindDrawer();
        CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_open_right));
        return true;
    }
}
