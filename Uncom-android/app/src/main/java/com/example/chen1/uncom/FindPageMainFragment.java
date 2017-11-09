package com.example.chen1.uncom;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.telecom.PhoneAccount;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.design.widget.TabLayout;

import com.example.chen1.uncom.application.CoreApplication;

public class FindPageMainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private ListView listView;
    private static FindPageMainFragment findPageMainFragment=null;
    private OnFragmentInteractionListener mListener;
    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;
    private ObjectAnimator animator;
    private TabLayout tableLayout;


    public static FindPageMainFragment getInstance(){
        if(findPageMainFragment==null){
            findPageMainFragment=new FindPageMainFragment();
        }
        return findPageMainFragment;
    }
    public FindPageMainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -0.1f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(300);
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(300);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_find_page_main, container, false);
        AppCompatImageView moreBtn= (AppCompatImageView) view.findViewById(R.id.more_activity_info);
        final Animation anim =new RotateAnimation(0f, 90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        final Animation anim2 =new RotateAnimation(90f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);


        anim.setDuration(300);
        anim2.setDuration(300);
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View vs= (View) v.getParent().getParent().getParent();
                View rootView=vs.findViewById(R.id.find_activity_extend_linearlayout);
                if(rootView.getVisibility()==View.VISIBLE){
                    anim2.setFillAfter(true); // 设置保持动画最后的状态
                    anim2.setInterpolator(new AccelerateInterpolator()); // 设置插入器
                    v.startAnimation(anim2);
                    rootView.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.card_extend_hide));
                    rootView.setVisibility(View.GONE);
                }else{
                    if(rootView.findViewById(R.id.extend_layout_)==null){
                        LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.find_activity_extend_linearlayout);
                        linearLayout.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.card_extend_show));
                        linearLayout.setVisibility(View.VISIBLE);
                        View views= inflater.inflate(R.layout.find_activity_cardview_extend_layout,null);
                        tableLayout= (TabLayout) views.findViewById(R.id.extend_tabLayout);
                        tableLayout.addTab(tableLayout.newTab().setText("简述"));
                        tableLayout.addTab(tableLayout.newTab().setText("详细信息"));
                        tableLayout.addTab(tableLayout.newTab().setText("活动数据"));
                        linearLayout.addView(views);
                    }else{
                        rootView.findViewById(R.id.find_activity_extend_linearlayout).startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.card_extend_show));
                        rootView.findViewById(R.id.find_activity_extend_linearlayout).setVisibility(View.VISIBLE);
                    }
                    anim.setFillAfter(true); // 设置保持动画最后的状态
                    anim.setInterpolator(new AccelerateInterpolator()); // 设置插入器
                    v.startAnimation(anim);

                }

            }
        });
        return view;
    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
