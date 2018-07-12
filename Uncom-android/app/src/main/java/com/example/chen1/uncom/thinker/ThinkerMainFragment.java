package com.example.chen1.uncom.thinker;

import android.animation.FloatArrayEvaluator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.MessageHistoryBean;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;
import com.example.chen1.uncom.bean.ThinkerBean;
import com.example.chen1.uncom.bean.ThinkerBeanDao;
import com.example.chen1.uncom.chat.ChatMessage;
import com.example.chen1.uncom.chat.PersonChatFragment;
import com.example.chen1.uncom.main.MainActivity;
import com.example.chen1.uncom.utils.AsynLoadImageUtils;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.GestureListener;
import com.example.chen1.uncom.utils.StateCode;
import com.example.chen1.uncom.utils.SwipLayout;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ThinkerMainFragment extends Fragment implements View.OnTouchListener  , FragmentBackHandler {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private FragmentTransaction fragmentTransaction;
    private WriteThinkFragment writeThinkFragment;
    private ThinkerBeanDao thinkerBeanDao;
    private FragmentManager fragmentManager ;
    private ArrayList<ThinkerBean> dataList;
    private int slop;
    private long pressStartTime;
    private float pressedX;
    private SwipLayout swipLayout;
    private float pressedY;
    private ThinkerAdapter thinkerAdapter;
    private ThinkerMainFragment fragment;
    private Fragment self;
    private View Rootview;
    private AsynLoadImageUtils asynLoadImageUtils;
    private AppCompatImageView backBtn;
    public ThinkerMainFragment() {
        // Required empty public constructor
        asynLoadImageUtils=new AsynLoadImageUtils();
    }

    public static ThinkerMainFragment newInstance() {
        ThinkerMainFragment fragment = new ThinkerMainFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment=this;
        EventBus.getDefault().register(this);
        if(dataList==null || CoreApplication.newInstance().initPhoto){
            dataList=new ArrayList<>();
            CountDownTimer timer=new CountDownTimer(280,280) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    freashData();
                }
            };
            timer.start();
        }



    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v("ThinkerMainFragment","onCreateView");
        View view =inflater.inflate(R.layout.fragment_thinker_main, container, false);
        view.setClickable(true);
        Rootview=view;
        self=this;
        ((MainActivity)getActivity()).unBindDrawer();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        swipLayout=new SwipLayout(getContext());
        swipLayout.setLayoutParams(params);
        swipLayout.setBackgroundColor(Color.TRANSPARENT);
        swipLayout.removeAllViews();
        swipLayout.addView(view);
        swipLayout.setParentView(CoreApplication.newInstance().getRoot()).setFragment(ThinkerMainFragment.this);
        return swipLayout;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v("ThinkerMainFragment","onViewCreated");
        backBtn= (AppCompatImageView) view.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                getFragmentManager().popBackStack();
                CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_open_right));
            }
        });
        fragmentManager = getFragmentManager();
        recyclerView= (RecyclerView) view.findViewById(R.id.thinker_recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        thinkerAdapter=new ThinkerAdapter(dataList,asynLoadImageUtils,this);
        thinkerAdapter.setItemOnClickListener(new ThinkerAdapter.ItemOnClickListener() {
            @Override
            public void onClick(View view, int position, ThinkerBean bean) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null).setCustomAnimations(
                        R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_leave_translate,
                        R.anim.default_fragment_switch_leave_translate
                );
                Log.v("bean",bean.toString());
                writeThinkFragment=new WriteThinkFragment();
                writeThinkFragment.setTargetFragment(self,123);
                writeThinkFragment.setAsynLoadImageUtils(asynLoadImageUtils);
                writeThinkFragment.setThinkerBean(bean);
                fragmentTransaction.add(R.id.drawer_layout, writeThinkFragment, "WriteThinkFragment");
                fragmentTransaction.setCustomAnimations(
                        R.anim.default_fragment_switch_leave_translate,
                        R.anim.default_leave_left,
                        R.anim.default_open_right,
                        R.anim.default_open_right)
                        .commitAllowingStateLoss();
                getView().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(), R.anim.default_leave_left));

            }
        });




        recyclerView.setAdapter(thinkerAdapter);
        floatingActionButton=(FloatingActionButton)view.findViewById(R.id.write_think);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction=fragmentManager.beginTransaction();
                writeThinkFragment=WriteThinkFragment.newInstance();
                if(asynLoadImageUtils!=null && writeThinkFragment!=null){
                    writeThinkFragment.setAsynLoadImageUtils(asynLoadImageUtils);
                    writeThinkFragment.fragment=fragment;
                }
                writeThinkFragment.setTargetFragment(ThinkerMainFragment.this,1123);
                fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.default_fragment_open_up,
                        R.anim.null_translate,
                        R.anim.null_translate,
                        R.anim.default_fragment_leave_down
                ).add(R.id.drawer_layout, writeThinkFragment, "WriteThinkFragment").commitAllowingStateLoss();

            }

        });
    }



    @Override
    public void onPause() {
        super.onPause();
        Log.v("ThinkerMainFragment","onPause");
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.v("ThinkerMainFragment","setUserVisibleHint");
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.v("ThinkerMainFragment","onResume");
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden==true){
            ((MainActivity)getActivity()).bindDrawer();
        }else{
            ((MainActivity)getActivity()).unBindDrawer();
        }
        Log.v("ThinkerMainFragment","onHiddenChanged");
    }

    private void freashData(){
        Flowable.create(new FlowableOnSubscribe<ArrayList<ThinkerBean>>() {
            @Override
            public void subscribe(FlowableEmitter<ArrayList<ThinkerBean>> e) throws Exception {
                ArrayList<ThinkerBean> list=null;
                thinkerBeanDao= BeanDaoManager.getInstance().getDaoSession().getThinkerBeanDao();
                QueryBuilder queryBuilder=thinkerBeanDao.queryBuilder();
                final Query query=queryBuilder.where(ThinkerBeanDao.Properties.AuthorId.eq(CoreApplication.newInstance().getUser_id())).orderDesc(ThinkerBeanDao.Properties.ChangeTime).build();
                if(query!=null){
                    list=(ArrayList<ThinkerBean>) query.list();
                    Log.v("user_id",CoreApplication.newInstance().getUser_id());
                    Log.v("datalist-length", String.valueOf(list.size()));
                    e.onNext(list);
                    e.onComplete();
                }
            }
        }, BackpressureStrategy.ERROR) .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<ThinkerBean>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(ArrayList<ThinkerBean> thinkerBeans) {
                        Log.v("notifyDataSetChanged","success");
                        dataList= thinkerBeans;
                        thinkerAdapter.setDataList(dataList);
                        CoreApplication.newInstance().initPhoto=false;
                    }

                    @Override
                    public void onError(Throwable t) {
                              }

                    @Override
                    public void onComplete() {
                        Log.v("notifyDataSetChanged","onComplete");
                    }
                });



    }


    /**
     *
     * @param thinkerBean
     * @param type 1 插入了新数据 0 更新数据 2 删除数据
     */
    public void freashView(ThinkerBean thinkerBean,String type){
        if(type.equals(StateCode.THINKER_BEAN_INSERT)){
            Log.v("freashView","insert bean");
            dataList.add(0,thinkerBean);
        }else if(type.equals(StateCode.THINKER_BEAN_UPDATE)){
            for (int i=0;i<dataList.size();i++){
                if(dataList.get(i).getCreateTime().equals(thinkerBean.getCreateTime())){
                    dataList.remove(i);
                    dataList.add(0,thinkerBean);
                }
            }
        }else if(type.equals(StateCode.THINKER_BEAN_DELETE)){
            Log.v("freashView","deleteBean");
            for (int i=0;i<dataList.size();i++){
                if(dataList.get(i).getCreateTime().equals(thinkerBean.getCreateTime())){
                    dataList.remove(i);
                }
            }
        }
        if(thinkerAdapter!=null){
            Log.v("freashView","notifyDataSetChanged");
            thinkerAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void syncMessage(ThinkMessage message) {
        freashView((ThinkerBean) message.getObject(),message.getState());
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        writeThinkFragment=null;
        EventBus.getDefault().unregister(this);
        RefWatcher refWatcher = CoreApplication.getRefWatcher(CoreApplication.newInstance().getApplicationContext());
        refWatcher.watch(this);
    }

    @Override
    public boolean onBackPressed() {

        fragmentTransaction=getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.default_fragment_switch_leave_translate,
                R.anim.default_fragment_switch_leave_translate,
                R.anim.default_fragment_switch_translate_open,
                R.anim.default_fragment_switch_translate_open
        ).hide(this).commitAllowingStateLoss();
        CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_open_right));
        return true;
    }





    private double distance(float x1,float y1,float x2,float y2){
        float x=0,y=0;
        if(x1 < x2){
            x=(x2-x1)*(x2-x1);
        }else{
            x=(x1-x2)*(x1-x2);
        }
        if (y1 < y2) {
            y=(y2-y1)*(y2-y1);
        }else{
            y=(y1-y2)*(y1-y2);
        }
        Log.v("length:", String.valueOf(Math.sqrt(x+y)));
        return Math.sqrt(x+y);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

}
