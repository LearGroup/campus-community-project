package com.example.chen1.uncom.find;

import android.animation.ObjectAnimator;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.design.widget.TabLayout;
import android.widget.TextView;

import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.RoutineBean;
import com.example.chen1.uncom.chat.PersonChatFragment;
import com.example.chen1.uncom.haveLook.HaveALook;
import com.example.chen1.uncom.relationDynamics.DynamicsMessage;
import com.example.chen1.uncom.relationDynamics.RelationDynamics;
import com.example.chen1.uncom.thinker.ThinkerMainFragment;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.BadgeMessageUtil;
import com.example.chen1.uncom.utils.GestureListener;
import com.example.chen1.uncom.utils.StateCode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;

import lecho.lib.hellocharts.model.Line;

public class FindPageMainFragment extends Fragment implements FragmentBackHandler{

    private  FragmentTransaction fragmentTransaction;
    private static FindPageMainFragment findPageMainFragment=null;
    private LinearLayout thinkerButton;
    private LinearLayout nearbyButton;
    private LinearLayout haveALookBtn;
    private LinearLayout relationDynamic;
    private TextView dynamicsNum;
    private TextView dynamicsState;
    private View view;
    public static FindPageMainFragment getInstance(){
        if(findPageMainFragment==null){
            findPageMainFragment=new FindPageMainFragment();
        }
        return findPageMainFragment;
    }


    public static FindPageMainFragment newInstance(){
        return new FindPageMainFragment();
    }
    public FindPageMainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null){
            Log.v("有view","直接返回");
            view= inflater.inflate(R.layout.fragment_find_page_main, container, false);
        }
        // Inflate the layout for this fragment

        nearbyButton= (LinearLayout) view.findViewById(R.id.nearby_button);
        thinkerButton=(LinearLayout)view.findViewById(R.id.thinker_btn);
        haveALookBtn=(LinearLayout)view.findViewById(R.id.have_a_look);
        dynamicsNum= (TextView) view.findViewById(R.id.dynamics_num);
        dynamicsState= (TextView) view.findViewById(R.id.dynamics_state);
        relationDynamic=(LinearLayout)view.findViewById(R.id.relation_dynamics);

        Log.v("getRelation_dynamics", String.valueOf(BadgeMessageUtil.getRelation_dynamics()));
        Log.v("isDynamics_state", String.valueOf(BadgeMessageUtil.isDynamics_state()));
        if(BadgeMessageUtil.getRelation_dynamics()!=0){
            dynamicsNum.setVisibility(View.VISIBLE);
            dynamicsState.setVisibility(View.GONE);
            dynamicsNum.setText(BadgeMessageUtil.getRelation_dynamics()+"");
        }else{
            if(BadgeMessageUtil.isDynamics_state()){
                dynamicsState.setVisibility(View.VISIBLE);
            }else{
                dynamicsState.setVisibility(View.GONE);
            }
            dynamicsNum.setVisibility(View.GONE);
        }
        thinkerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                if(fragmentManager.findFragmentByTag("ThinkerMainFragment")!=null){
                    fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.default_fragment_switch_translate_open,
                            R.anim.default_fragment_switch_translate_open,
                            R.anim.default_fragment_switch_leave_translate,
                            R.anim.default_fragment_switch_leave_translate
                    ).show(fragmentManager.findFragmentByTag("ThinkerMainFragment")).commit();
                }else{
                    fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.default_fragment_switch_translate_open,
                            R.anim.default_fragment_switch_translate_open,
                            R.anim.default_fragment_switch_leave_translate,
                            R.anim.default_fragment_switch_leave_translate
                    ).add(R.id.drawer_layout,  ThinkerMainFragment.newInstance(), "ThinkerMainFragment").commit();

                }
                CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(), R.anim.default_leave_left));
            }
        });
        nearbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                NearbyActivityFragment nearbyActivityFragment = (NearbyActivityFragment) fragmentManager.findFragmentByTag("newRelationShipFragment");
                fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_leave_translate,
                        R.anim.default_fragment_switch_leave_translate
                );
                if(nearbyActivityFragment!=null){
                    fragmentTransaction.show(nearbyActivityFragment).commitAllowingStateLoss();
                }else{
                    nearbyActivityFragment=NearbyActivityFragment.newInstance();
                    fragmentTransaction.add(R.id.drawer_layout,nearbyActivityFragment,"newRelationShipFragment").commitAllowingStateLoss();
                }
                CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(), R.anim.default_leave_left));
            }
        });

        relationDynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BadgeMessageUtil.isDynamics_state()){
                    BadgeMessageUtil.setDynamics_state(false);
                    dynamicsState.setVisibility(View.GONE);
                }
//                BadgeMessageUtil.setDynamics_state(false);
//                dynamicsState.setVisibility(View.VISIBLE);
                FragmentManager fragmentManager=getFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                RelationDynamics relationDynamics= (RelationDynamics) fragmentManager.findFragmentByTag("RelationDynamics");
                fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_translate_open
                );
                if(relationDynamics!=null){
                    Log.v("findPage","show");
                    relationDynamics.setTargetFragment(FindPageMainFragment.this,11123);
                    fragmentTransaction.show(relationDynamics).commitAllowingStateLoss();
                }else{
                    Log.v("findPage","new");
                    relationDynamics=RelationDynamics.newInstance();
                    relationDynamics.setTargetFragment(FindPageMainFragment.this,11123);
                    fragmentTransaction.add(R.id.drawer_layout,relationDynamics,"RelationDynamics").commitAllowingStateLoss();
                }
                CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(), R.anim.default_leave_left));

            }
        });
        haveALookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HaveALook aLook=HaveALook.newInstance();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_leave_translate,
                        R.anim.default_fragment_switch_leave_translate
                ).replace(R.id.drawer_layout, aLook, "newRelationShipFragment").commit();
                CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(), R.anim.default_leave_left));

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void syncData(FindMessage message){
        Log.v("syncData",  message.getSTATE_CODE());
        if(message.getSTATE_CODE().equals(StateCode.PERSON_DYNAMICS_ADD)){
            if(dynamicsState.getVisibility()==View.GONE && dynamicsNum.getVisibility()!=View.VISIBLE){
                dynamicsState.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onBackPressed() {
        Log.v("popBackStack", String.valueOf(getFragmentManager().getFragments().size()));
        getFragmentManager().getFragments().size();
        for (int i = 0; i < getFragmentManager().getFragments().size(); i++) {
            getFragmentManager().popBackStackImmediate();
        }
        return BackHandlerHelper.handleBackPress(this);
    }
}
