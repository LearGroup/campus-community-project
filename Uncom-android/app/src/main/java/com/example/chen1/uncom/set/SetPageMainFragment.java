package com.example.chen1.uncom.set;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;
import com.example.chen1.uncom.bean.ThinkerBean;
import com.example.chen1.uncom.bean.ThinkerBeanDao;
import com.example.chen1.uncom.chat.PersonChatFragment;
import com.example.chen1.uncom.main.MainActivity;
import com.example.chen1.uncom.thinker.ThinkerMainFragment;
import com.example.chen1.uncom.thinker.WriteThinkFragment;
import com.example.chen1.uncom.utils.AsynLoadImageUtils;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.BadgeMessageUtil;
import com.example.chen1.uncom.utils.StateCode;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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



public class SetPageMainFragment extends Fragment implements FragmentBackHandler{

    private int CONNECTION_ERROR =-1;
    private int RECONNECTION =0;
    private  View rootView;
    private View view;
    private int CONNECTION_STATUS=1;
    private static boolean isVisiable;
    private LinearLayout popupWindow;
    private  SetPageMainFragmentAdapter setPageMainFragmentAdapter;
    private SetPageMainFragment fragment;
    public  ArrayList<Object> dataList;
    // TODO: Rename and change types of parameters
    private static SetPageMainFragment setPageMainFragment=null;



    public int getCONNECTION_STATUS() {
        return CONNECTION_STATUS;
    }

    public void setCONNECTION_STATUS(int CONNECTION_STATUS) {
        if(popupWindow!=null){
            this.CONNECTION_STATUS = CONNECTION_STATUS;
            popupWindow=(LinearLayout) rootView.findViewById(R.id.popupwindow);
            if(CONNECTION_STATUS==CONNECTION_ERROR && rootView!=null){
                if(popupWindow.getVisibility()!=View.VISIBLE){
                    popupWindow.setVisibility(View.VISIBLE);
                }
            }else if(CONNECTION_STATUS==RECONNECTION && rootView!=null){
                if(popupWindow.getVisibility()!=View.GONE){
                    popupWindow.setVisibility(View.GONE);
                }
            }
        }
    }

    public SetPageMainFragment() {
        // Required empty public constructor

    }


    public static SetPageMainFragment newInstance() {
        return  new SetPageMainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(CoreApplication.newInstance().getCoreService()==null){
            CoreApplication.newInstance().startServices();
        }
        EventBus.getDefault().register(this);
        if(dataList==null || CoreApplication.newInstance().unLook==1){
            dataList=new ArrayList<>();
            syncData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v("setPage","onCreateView");
        view=inflater.inflate(R.layout.fragment_set_page_main, container, false);
        rootView=view;
        fragment=this;

        CoreApplication.newInstance().basePagerPosition=0;
      //  toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorIcon), PorterDuff.Mode.SRC_ATOP);
        popupWindow=(LinearLayout) view.findViewById(R.id.popupwindow);
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.set_page_main_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CoreApplication.newInstance().getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        setPageMainFragmentAdapter =new SetPageMainFragmentAdapter(CoreApplication.newInstance().getBaseContext(), dataList,this);
        CoreApplication.newInstance().setSetPageMainFragmentAdapter(setPageMainFragmentAdapter);
        recyclerView.setAdapter(setPageMainFragmentAdapter);
        setPageMainFragmentAdapter.setOnItemClickListener(new SetPageMainFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object object) {
                if(object instanceof  RelationShipLevelBean){
                    BadgeMessageUtil.setSetPageIsVisible(false);
                    FragmentManager fragmentManager= getFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction
                            .setCustomAnimations(R.anim.default_fragment_switch_translate_open,
                                    R.anim.default_fragment_switch_translate_open
                            );
                    PersonChatFragment person_chat_fragment= (PersonChatFragment) fragmentManager.findFragmentByTag("chatPage");
                    if(person_chat_fragment!=null){
                        person_chat_fragment.setTargetFragment(SetPageMainFragment.this,3);
                        person_chat_fragment.setFrendData((RelationShipLevelBean) object);
                        fragmentTransaction.show(person_chat_fragment).commitAllowingStateLoss();
                        Log.v("chatPage","show");
                    }else{
                        Log.v("chatPage","add");
                        person_chat_fragment=PersonChatFragment.newInstance((RelationShipLevelBean) object);
                        person_chat_fragment.setTargetFragment(SetPageMainFragment.this,3);
                        fragmentTransaction.add(R.id.drawer_layout,person_chat_fragment,"chatPage").commitAllowingStateLoss();
                    }
                }else if(object instanceof ThinkerBean){
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.addToBackStack(null).setCustomAnimations(
                            R.anim.default_fragment_switch_translate_open,
                            R.anim.default_fragment_switch_translate_open,
                            R.anim.default_fragment_switch_leave_translate,
                            R.anim.default_fragment_switch_leave_translate
                    );
                    WriteThinkFragment writeThinkFragment;
                        writeThinkFragment=new WriteThinkFragment();
                        writeThinkFragment.fragment=fragment;
                        writeThinkFragment.setTargetFragment(SetPageMainFragment.this,11234);
                        writeThinkFragment.setThinkerBean((ThinkerBean) object);
                        writeThinkFragment.setAsynLoadImageUtils(new AsynLoadImageUtils());
                        fragmentTransaction.add(R.id.drawer_layout,writeThinkFragment,"WriteThinkFragment").commitAllowingStateLoss();
                }
                CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(), R.anim.default_leave_left));
            }
        });
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        dataList=null;
        EventBus.getDefault().unregister(this);
        RefWatcher refWatcher = CoreApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void syncMessage(SetMessage setMessage){
        Log.v("SetPageMainFragment","parseEvent");
        if(setMessage.STATE_CODE.equals(StateCode.RELATION_LEVEL_BEAN)){
            if(setPageMainFragmentAdapter!=null){
                if(((RelationShipLevelBean)setMessage.getObject()).getUn_look()>=1){
                    setPageMainFragmentAdapter.updateActivePersonMessageList(((RelationShipLevelBean)setMessage.getObject()),2);
                }else{
                    setPageMainFragmentAdapter.updateActivePersonMessageList(((RelationShipLevelBean)setMessage.getObject()),1);
                }
            }
        }
        if(setMessage.STATE_CODE.equals(StateCode.PERSON_CHAT_PAGE)){
            RelationShipLevelBean bean=getRelationShipBean((String) setMessage.getObject(),BeanDaoManager.getInstance().getDaoSession().getRelationShipLevelBeanDao());
            BadgeMessageUtil.setSetPageIsVisible(false);
            FragmentManager fragmentManager= getFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction
                    .setCustomAnimations(R.anim.default_fragment_switch_translate_open,
                            R.anim.default_fragment_switch_translate_open
                    );
            PersonChatFragment person_chat_fragment= (PersonChatFragment) fragmentManager.findFragmentByTag("chatPage");
            if(person_chat_fragment!=null){
                person_chat_fragment.setTargetFragment(SetPageMainFragment.this,3);
                person_chat_fragment.setFrendData(bean);
                fragmentTransaction.show(person_chat_fragment).commitAllowingStateLoss();
                Log.v("chatPage","show");
            }else{
                Log.v("chatPage","add");
                person_chat_fragment=PersonChatFragment.newInstance(bean);
                person_chat_fragment.setTargetFragment(SetPageMainFragment.this,3);
                fragmentTransaction.add(R.id.rootview,person_chat_fragment,"chatPage").commitAllowingStateLoss();
            }
        }
        if(setMessage.STATE_CODE.equals(StateCode.THINKER_BEAN)){
            if(setMessage.getThinker_state()==0){
                setPageMainFragmentAdapter.updateThinker(((ThinkerBean)setMessage.getObject()),setMessage.getThinker_state());
            }else if(setMessage.getThinker_state()==1){
                setPageMainFragmentAdapter.addThinker(((ThinkerBean)setMessage.getObject()));
            }else if(setMessage.getThinker_state()==-1){
                setPageMainFragmentAdapter.delThinker((ThinkerBean) setMessage.getObject());
            }else if(setMessage.getThinker_state()==3){
                setPageMainFragmentAdapter.updateThinker(((ThinkerBean)setMessage.getObject()),1);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            BadgeMessageUtil.setSetPageIsVisible(true);
        } else {

            BadgeMessageUtil.setSetPageIsVisible(false);
        }
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            BadgeMessageUtil.setSetPageIsVisible(false);
        } else {
            BadgeMessageUtil.setSetPageIsVisible(true);
        }
    }


    public void syncData(){

        Flowable.create(new FlowableOnSubscribe<ArrayList<Object>>() {
            @Override
            public void subscribe(FlowableEmitter<ArrayList<Object>> e) throws Exception {
                ArrayList<Object> datalist=new ArrayList<>();
                RelationShipLevelBeanDao relationShipLevelBeanDao= BeanDaoManager.getInstance().getDaoSession().getRelationShipLevelBeanDao();
                ThinkerBeanDao  thinkerBeanDao=BeanDaoManager.getInstance().getDaoSession().getThinkerBeanDao();
                QueryBuilder queryBuilder2=thinkerBeanDao.queryBuilder();
                QueryBuilder queryBuilder=relationShipLevelBeanDao.queryBuilder();
                Query query2=queryBuilder2.where(ThinkerBeanDao.Properties.ToMainTime.isNotNull()).orderAsc(ThinkerBeanDao.Properties.ToMainTime).build();
                Query query=queryBuilder.where(queryBuilder.and(RelationShipLevelBeanDao.Properties.Level.eq(4),RelationShipLevelBeanDao.Properties.Active.eq(true))).orderDesc(RelationShipLevelBeanDao.Properties.Last_active_time).build();
                ArrayList<RelationShipLevelBean> list= (ArrayList<RelationShipLevelBean>) query.list();
                ArrayList<ThinkerBean> list2= (ArrayList<ThinkerBean>) query2.list();
                for (int i = 0; i <list.size(); i++) {
                    if(list.get(i).getActive()){
                        datalist.add(list.get(i));
                    }
                }
                for (int i = 0; i <list2.size() ; i++) {
                    datalist.add(0,list2.get(i));
                }

                e.onNext(datalist);
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ArrayList<Object>>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(ArrayList<Object> objects) {
                dataList=objects;
                if(setPageMainFragmentAdapter!=null){
                    setPageMainFragmentAdapter.add(dataList);
                }
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
    public void onDestroyView() {
        super.onDestroyView();
        Log.v("setPage","onDestroyView");
    }

    @Override
    public boolean onBackPressed() {
        return BackHandlerHelper.handleBackAllImmediate(this);
    }


    /**
     * 获取指定的个人好友Bean
     * @param id userid
     * @return 好友Bean
     */
    public RelationShipLevelBean getRelationShipBean(String  id,RelationShipLevelBeanDao relationShipLevelBeanDao){
        if(CoreApplication.newInstance().getPersonFrendList()!=null){
            for (int i = 0; i < CoreApplication.newInstance().getPersonFrendList().size() ; i++) {
                if(id.equals(CoreApplication.newInstance().getPersonFrendList().get(i).getMinor_user())){
                    return CoreApplication.newInstance().getPersonFrendList().get(i);
                }
            }
        }
        RelationShipLevelBean bean= relationShipLevelBeanDao.queryBuilder().where(RelationShipLevelBeanDao.Properties.Minor_user.eq(id)).unique();
        if(bean!=null){
            return bean;
        }
        return null;
    }
}
