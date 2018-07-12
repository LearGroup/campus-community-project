package com.example.chen1.uncom.find;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.GridView;
import android.widget.ListView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.RoutineBean;

import java.util.ArrayList;
import java.util.Date;


public class NearbyActivityFragment extends Fragment {
    private ArrayList<RoutineBean> activeList=new ArrayList<>();
    private RecyclerView recyclerView;
    private  FindPageRecyclerAdapter adapter;
    public NearbyActivityFragment() {

    }

    public static NearbyActivityFragment newInstance() {
        NearbyActivityFragment fragment = new NearbyActivityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_nearby_activity, container, false);
        view.setClickable(true);
        AppCompatImageView moreBtn= (AppCompatImageView) view.findViewById(R.id.more_activity_info);
        return view;
    }





    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        activeList=new ArrayList<>();



        recyclerView= (RecyclerView) view.findViewById(R.id.find_page_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CoreApplication.newInstance().getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter =new FindPageRecyclerAdapter(CoreApplication.newInstance().getBaseContext(),activeList);
        adapter.setOnItemClickLitener(new FindPageRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int positon, RoutineBean routineBean) {
                View rootView=view.findViewById(R.id.find_activity_extend_linearlayout);
                if(rootView.getVisibility()==View.VISIBLE){
                        rootView.setVisibility(View.GONE);
                }else{
                          rootView.setVisibility(View.VISIBLE);
                }
            }
        });
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();

    }



    @Override
    public void onResume() {
        super.onResume();
        Log.v("onResume", String.valueOf(""));
        lazyLoad();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.v("onHiddenChanged", String.valueOf(hidden));
        if(hidden!=true){
            lazyLoad();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.v("setUserVisibleHint", String.valueOf(isVisibleToUser));
        if(isVisibleToUser){
                lazyLoad();
        }
    }




    private void lazyLoad(){
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
        adapter.lazyLoad(activeList);
        Log.v("lazyLoad","begin");
    }






}
