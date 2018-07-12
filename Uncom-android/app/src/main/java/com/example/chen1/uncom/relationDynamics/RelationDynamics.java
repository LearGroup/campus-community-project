package com.example.chen1.uncom.relationDynamics;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.PersonDynamicsBean;
import com.example.chen1.uncom.bean.PersonDynamicsBeanDao;
import com.example.chen1.uncom.find.FindPageMainFragment;
import com.example.chen1.uncom.main.MainActivity;
import com.example.chen1.uncom.me.SyncMessage;
import com.example.chen1.uncom.message.MessageAccess;
import com.example.chen1.uncom.previewAlbum.AlbumEdit;
import com.example.chen1.uncom.relationship.RalationShipPageMainFragment;
import com.example.chen1.uncom.thinker.WriteThinkFragment;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.GestureListener;
import com.example.chen1.uncom.utils.GlideApp;
import com.example.chen1.uncom.utils.GlideCircleTransform;
import com.example.chen1.uncom.utils.StateCode;
import com.example.chen1.uncom.utils.SwipLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class RelationDynamics extends Fragment implements  FragmentBackHandler {

    private AppCompatImageView writeDynamicsBtn;
    private FragmentTransaction fragmentTransaction;
    private AppCompatImageView backBtn;
    private SwipLayout layout;
    private View view;
    private RecyclerView dynamicsRecycler;
    private FragmentTransaction backTransaction;
    private DynamicsAdapter dynamicsAdapter;
    private ValueAnimator likeAnimator=ValueAnimator.ofInt(0,0);
    private GestureDetector gestureDetector;
    private ItemGestorListener itemGestorListener;
    private ImageView flaotUser;
    private AppBarLayout appBarLayout;
    private TextView username;
    private LinearLayout messageLayout;
    private ImageView  messageImage;
    private TextView messageText;

    public RelationDynamics() {
        // Required empty public constructor
    }

       public static RelationDynamics newInstance() {
        RelationDynamics fragment = new RelationDynamics();
              return fragment;
    }

    private void syncData(){
        if(CoreApplication.newInstance().dynamicsList==null){
            CountDownTimer timer=new CountDownTimer(200,200) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Flowable.create(new FlowableOnSubscribe<ArrayList<Object>>() {
                        @Override
                        public void subscribe(FlowableEmitter<ArrayList<Object>> e) throws Exception {
                            PersonDynamicsBeanDao dao = BeanDaoManager.getInstance().getDaoSession().getPersonDynamicsBeanDao();
                            QueryBuilder queryBuilder=dao.queryBuilder();
                            Query query=queryBuilder.where(
                                    PersonDynamicsBeanDao.Properties.Edit.notEq(1)).
                                    orderDesc(PersonDynamicsBeanDao.Properties.Create_time).limit(15).offset(0).build();
                            if(query!=null){
                                ArrayList<PersonDynamicsBean>temp=(ArrayList<PersonDynamicsBean>) query.list();
                                ArrayList<Object> list=new ArrayList<>();
                                for (int i = 0; i <temp.size() ; i++) {
                                    list.add(temp.get(i));
                                }
                                e.onNext(list);
                            }
                            e.onComplete();
                        }
                    }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                            subscribe(new Subscriber<ArrayList<Object>>() {
                                @Override
                                public void onSubscribe(Subscription s) {
                                    s.request(Long.MAX_VALUE);
                                }

                                @Override
                                public void onNext(ArrayList<Object> objects) {
                                    CoreApplication.newInstance().dynamicsList=objects;
                                    Log.v("onSubscribe", String.valueOf(CoreApplication.newInstance().dynamicsList.size()));
                                    if(CoreApplication.newInstance().dynamicsList==null){
                                        CoreApplication.newInstance(). dynamicsList=new ArrayList<>();
                                    }
                                    dynamicsAdapter.setDynamics(CoreApplication.newInstance().dynamicsList);

                                }

                                @Override
                                public void onError(Throwable t) {

                                }

                                @Override
                                public void onComplete() {
                                }
                            });
                }
            };
            timer.start();
        }else{
            dynamicsAdapter.setDynamics(CoreApplication.newInstance().dynamicsList);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
         itemGestorListener=new ItemGestorListener();
        gestureDetector=new GestureDetector(getContext(),itemGestorListener);
        backTransaction=getFragmentManager().beginTransaction();
        dynamicsAdapter=new DynamicsAdapter(new ArrayList<Object>(),RelationDynamics.this);
        syncData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v("RelatioDynamics","onCreateView");
        ((MainActivity)getActivity()).unBindDrawer();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout=new SwipLayout(getContext());
        layout.setLayoutParams(params);
        layout.setBackgroundColor(Color.TRANSPARENT);
        if(view==null){
            view= inflater.inflate(R.layout.fragment_relation_dynamics, container, false);
        }
        layout.removeAllViews();
        layout.addView(view);
        layout.setRunnable(new Runnable() {
            @Override
            public void run() {
                backParse(true);
            }
        }).setParentView(CoreApplication.newInstance().getRoot()).setFragment(RelationDynamics.this);
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v("dynmaics","onViewCreated");
        messageLayout= (LinearLayout) view.findViewById(R.id.message_layout);
        messageImage= (ImageView) view.findViewById(R.id.message_image);
        messageText= (TextView) view.findViewById(R.id.message_content);
        username= (TextView) view.findViewById(R.id.username);
        appBarLayout= (AppBarLayout) view.findViewById(R.id.appbar_layout);
        flaotUser= (ImageView) view.findViewById(R.id.float_user);
        writeDynamicsBtn= (AppCompatImageView) view.findViewById(R.id.write_dynamics);
        backBtn= (AppCompatImageView) view.findViewById(R.id.new_relationship_back_icon);
        dynamicsRecycler= (RecyclerView) view.findViewById(R.id.dynamics);
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        dynamicsRecycler.setLayoutManager(manager);
        dynamicsRecycler.setNestedScrollingEnabled(false);
        dynamicsRecycler.setAdapter(dynamicsAdapter);
        ((SimpleItemAnimator)dynamicsRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        dynamicsAdapter.setItemOnTouchListener(new DynamicsAdapter.ItemOnTouchListener() {
            @Override
            public boolean onTouch(View v, String type, Object bean, MotionEvent event) {
                itemGestorListener.setBean(bean).setDynamicsAdapter(dynamicsAdapter).setFragment(RelationDynamics.this).setType(type).setV(v);
                    return  gestureDetector.onTouchEvent(event);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                getFragmentManager().popBackStack();
                CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(), R.anim.default_open_right));
            }
        });
        writeDynamicsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getTargetFragment().getFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.null_translate,
                        R.anim.null_translate,
                        R.anim.null_translate,
                        R.anim.null_translate
                );
                    WriteDynamics writeDynamics;
                    writeDynamics =WriteDynamics.newInstance();
                    writeDynamics.setTargetFragment(RelationDynamics.this,12345);
                    fragmentTransaction.add(R.id.drawer_layout, writeDynamics, "WriteDynamics").commitAllowingStateLoss();


            }
        });
        username.setText(CoreApplication.newInstance().getUserBean().getUsername());
        GlideApp.with(this)
                .load(CoreApplication.newInstance().getUserBean().getHeader_pic()).transition(new DrawableTransitionOptions().crossFade())
                .transform(new GlideCircleTransform())
                .into(flaotUser);

        GlideApp.with(this)
                .load(CoreApplication.newInstance().getUserBean().getHeader_pic()).transition(new DrawableTransitionOptions().crossFade())
                .transform(new GlideCircleTransform())
                .into(messageImage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v("RelationDynamics","onDestroyView");
    }

    @Override
    public void onPause() {
        Log.v("RelationDynamics","onPause");
        super.onPause();
    }

    @Override
    public void onHiddenChanged(final boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden==true){
            ((MainActivity)getActivity()).bindDrawer();
        }else{
            ((MainActivity)getActivity()).unBindDrawer();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.v("RelationDynamics","onResume");
    }

    @Override
    public void onStart() {
        Log.v("RelationDynamics","onStart");
        super.onStart();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
                    if(isAdded()){
                        backBtn.setImageResource(R.drawable.ic_vector_back_icon);
                        writeDynamicsBtn.setImageResource(R.drawable.ic_vector_person_detaild_information_option_icon);
                    }
                }else{
                    if(isAdded()){
                        backBtn.setImageResource(R.drawable.ic_vector_back_transparent);
                        writeDynamicsBtn.setImageResource(R.drawable.ic_vector_person_detail_information_option_white_icon);
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void backParse(boolean swip){
        if(swip==true){

        }else{


            fragmentTransaction=getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.default_fragment_switch_leave_translate,
                    R.anim.default_fragment_switch_leave_translate,
                    R.anim.default_fragment_switch_translate_open,
                    R.anim.default_fragment_switch_translate_open
            ).hide(this).commitAllowingStateLoss();
            CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_open_right));

        }

    }

    private ArrayList<String> parseToArrayList(ArrayList<ArrayList<String>> list){
        ArrayList<String> data=new ArrayList<>();
        for (ArrayList<String> parm: list) {
            for (String str :parm){
                data.add(str);
            }
        }
        return data;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void syncData(DynamicsMessage message){
        if(message.getSTATE_CODE().equals(StateCode.PERSON_DYNAMICS_ADD)){
            dynamicsRecycler.scrollToPosition(0);
            dynamicsAdapter.add(message.getObject());
            Log.v("RelationDynamics","syncData");
        }
    }
    @Override
    public boolean onBackPressed() {
        Log.v("hidden status", String.valueOf(getUserVisibleHint()));
        backParse(false);
        return true;
       // return BackHandlerHelper.handleBackPress(this);
    }






}
