package com.example.chen1.uncom.me;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.DaoMaster;
import com.example.chen1.uncom.bean.PersonDynamicsBean;
import com.example.chen1.uncom.bean.PersonDynamicsBeanDao;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.DisplayUtils;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class DynamicAlbum extends Fragment implements FragmentBackHandler{


    private View view;
    private RecyclerView dynamics;
    private DynamicAlbumAdapter dynamicsAdapter;
    private Flowable<ArrayList<PersonDynamicsBean>> flowable=Flowable.create(new FlowableOnSubscribe<ArrayList<PersonDynamicsBean>>() {
        @Override
        public void subscribe(FlowableEmitter<ArrayList<PersonDynamicsBean>> e) throws Exception {
            PersonDynamicsBeanDao dao= BeanDaoManager.getInstance().getDaoSession().getPersonDynamicsBeanDao();
            QueryBuilder queryBuilder=dao.queryBuilder();
                Query query=queryBuilder.where(PersonDynamicsBeanDao.Properties.User_id.eq(CoreApplication.newInstance().getUser_id())).orderDesc(PersonDynamicsBeanDao.Properties.Create_time).limit(15).offset(0).build();
                ArrayList<PersonDynamicsBean> beans = new ArrayList<>();
                if(query!=null){
                    beans= (ArrayList<PersonDynamicsBean>) query.list();
                }
                e.onNext(beans);
                e.onComplete();
        }
    }, BackpressureStrategy.ERROR);
    // TODO: Rename and change types of parameters

    public DynamicAlbum() {
        // Required empty public constructor
    }

    public static DynamicAlbum newInstance() {
        DynamicAlbum fragment = new DynamicAlbum();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dynamicsAdapter=new DynamicAlbumAdapter(new ArrayList<PersonDynamicsBean>(),DynamicAlbum.this,
                ((int)(DisplayUtils.getWindowWidth(CoreApplication.newInstance().getApplicationContext())*0.65)),((int)(DisplayUtils.getWindowWidth(CoreApplication.newInstance().getApplicationContext())*0.35)));
        flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ArrayList<PersonDynamicsBean>>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(final ArrayList<PersonDynamicsBean> beans) {
                CountDownTimer timer=new CountDownTimer(200,200) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        dynamicsAdapter.setDynamicsBeans(beans);
                    }
                };
                timer.start();
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null){
            view = inflater.inflate(R.layout.fragment_dynamic_album, container, false);
        }
        dynamics= (RecyclerView) view.findViewById(R.id.dynamics);
        dynamics.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        dynamics.setHasFixedSize(true);
        dynamics.setAdapter(dynamicsAdapter);
        return view;
    }




    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onBackPressed() {
        getFragmentManager().popBackStack();
        CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_open_right));
        return true;
    }
}
