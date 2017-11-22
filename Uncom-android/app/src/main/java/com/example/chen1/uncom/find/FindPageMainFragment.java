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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.design.widget.TabLayout;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.RoutineBean;

import java.util.ArrayList;
import java.util.Date;

public class FindPageMainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private ListView listView;
    private static FindPageMainFragment findPageMainFragment=null;
    private OnFragmentInteractionListener mListener;
    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;
    private ObjectAnimator animator;
    private TabLayout tableLayout;
    private ArrayList<View>viewList;
    private ViewPager viewPager;
    private ArrayList<RoutineBean> activeList=new ArrayList<>();
    private RecyclerView recyclerView;

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
        activeList=new ArrayList<>();
       mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -0.1f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(300);
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(300);
        RoutineBean item =new RoutineBean();
        item.setAbstractReadme("\\u3000\\u3000 这是充满创造力的校园社区应用！我们希望通过社交行为的全新解释，创建能够对社交范围中的每个人带去更多价值的社区。\n" +
                "\\n\\nApply Condition:\\n  Scale: Node.js  Java Kotlin React Native 任一\\n  Time: 每周至少有两天的工作时间");
        item.setActivePercent((float) 0.7);
        item.setActiveTime("000000001100001100000000000000001100001100000000000000001100001100000000000000001100001100000000000000001100001100000000000000001100001100000000000000001100001100000000");
        item.setCount(4);
        item.setNowCount(3);
        item.setCreateTime(new Date());
        item.setOfline(true);
        item.setAddress("内蒙古工业大学 金川校区");
        item.setAuthorName(CoreApplication.newInstance().getUserBean().getUsername());
        item.setAuthorHeadImg(CoreApplication.newInstance().getUserBean().getHeader_pic());
        item.setAuthorId(CoreApplication.newInstance().getUser_id());
        item.setOnline(true);
        item.setNameBackColor("#037D66");
        item.setTagColor("#4DA2FD,#FCD838,#DAB8B2");
        item.setQuality(78);
        item.setTag("软件开发,技术学习,极客精神");
        item.setName("Uncom Community Project");
        item.setShortReadme("一个校园社交应用开发活动");
        item.setTimeType(1);
        activeList.add(item);
        activeList.add(item);
        activeList.add(item);
        activeList.add(item);

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
        recyclerView= (RecyclerView) view.findViewById(R.id.find_page_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        FindPageRecyclerAdapter adapter =new FindPageRecyclerAdapter(this,getContext(),activeList);
        adapter.setOnItemClickLitener(new FindPageRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int positon, RoutineBean routineBean) {
                View rootView=view.findViewById(R.id.find_activity_extend_linearlayout);
                if(rootView.getVisibility()==View.VISIBLE){
                 //   anim2.setFillAfter(true); // 设置保持动画最后的状态
                //    anim2.setInterpolator(new AccelerateInterpolator()); // 设置插入器
                 /*   v.startAnimation(anim2);*/
               //     rootView.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.card_extend_hide));
                    rootView.setVisibility(View.GONE);
                }else{
                //        rootView.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.card_extend_show));
                        rootView.setVisibility(View.VISIBLE);
            }
        }
        });
        Log.v("FindPageFragment ","length"+activeList.size());
        recyclerView.setAdapter(adapter);

     /*   moreBtn.setOnClickListener(new View.OnClickListener() {
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
                        viewPager= (ViewPager) views.findViewById(R.id.viewpager);
                        viewPager.setAdapter(new TabPagerAdapter(getFragmentManager()));
                        tableLayout.setupWithViewPager(viewPager);
                        tableLayout.getTabAt(0).setText("简述");
                        tableLayout.getTabAt(1).setText("详细信息");
                        tableLayout.getTabAt(2).setText("活动数据");

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

        });*/
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
