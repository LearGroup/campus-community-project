package com.example.chen1.uncom.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.BaseAdapter;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.chen1.uncom.access.AccessActivity;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.MessageHistoryBean;
import com.example.chen1.uncom.bean.MessageHistoryBeanDao;
import com.example.chen1.uncom.bean.NewRelationShipBean;
import com.example.chen1.uncom.bean.NewRelationShipBeanDao;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;
import com.example.chen1.uncom.bean.ThinkerBean;
import com.example.chen1.uncom.bean.UserBean;
import com.example.chen1.uncom.bean.UserBeanDao;
import com.example.chen1.uncom.chat.ChatMessage;
import com.example.chen1.uncom.main.Notification;
import com.example.chen1.uncom.relationship.PersonRelationShipAdapter;
import com.example.chen1.uncom.relationship.RalationShipPageMainFragment;
import com.example.chen1.uncom.service.ChatCoreBinder;
import com.example.chen1.uncom.service.CoreMessageHandler;
import com.example.chen1.uncom.service.CoreService;
import com.example.chen1.uncom.set.SetPageMainFragment;
import com.example.chen1.uncom.set.SetPageMainFragmentAdapter;
import com.example.chen1.uncom.thinker.ThinkerMainFragment;
import com.example.chen1.uncom.utils.BadgeMessageUtil;
import com.example.chen1.uncom.utils.SharedPreferencesUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chen1 on 2017/10/3.
 */

public class CoreApplication extends Application {

/*
   10.0.2.2:                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               m m m                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      m  sdr
  47.95.0.73
  192.168.43.143*/
    public boolean inApp;//判断是否在前台
    public String sk=null;
    public String ak=null;
    public String token=null;
    public String expiration=null;
    public Notification notification;
    public ArrayList<Object> dynamicsList;
    private Long uploadTime=null;//临时权限申请时间
    public static final int  WRITE_THINKE_FRAGMENT=1101;
    public int basePagerPosition=0;//当前处于显示状态的基 viewPgaer 中的fragment
    public int unLook;//判断是否有未读数据标志位  0 代表没有未读数据 1代表有个人聊天类的未读数据
    public String IP_ADDR="192.168.0.102";
    private boolean disPlay=true;
    public  boolean initPhoto=false;
    private String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ThinkIn/Image";
    private boolean disPlayType=true;//1 从RalationShipPageMainFragment到该fragment
    //0 从NewRelationShipResultsFragment 到该fragment
    private static final String SET_COOKIE_KEY = "set-cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "skey";
    private RalationShipPageMainFragment ralationShipPageMainFragment;//关系页面的引用
    private static CoreApplication instance;
    public Intent startIntent =null;
    private Activity activity;
    private RequestQueue requestQueue;
    private SharedPreferences preferences;
    private ArrayList<NewRelationShipBean> newRelationShipList;//新关系列表
    private Integer NewRelationActive;//新增的新关系数量（未查看）
    private Handler getChatDataHandler;  //当用户在对应的聊天界面，将数据发送到聊天界面
    private  String user_id;
    private MessageHistoryBeanDao messageHistoryBeanDao;
    private UserBean userBean;
    private Handler coreAppGetChatDataHandler;//获取消息数据 用户不在对应的聊天界面
    private RelationShipLevelBeanDao relationShipLevelBeanDao;
    private ArrayList<RelationShipLevelBean>personFrendList=null;
    private SetPageMainFragmentAdapter setPageMainFragmentAdapter;
    private PersonRelationShipAdapter PersonRelationShipAdapter;
    private View root;
    private ChatCoreBinder chatCoreBinder;
    private  CoreService coreService;
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            chatCoreBinder=(ChatCoreBinder)service;
            try {
                coreService=chatCoreBinder.getCoreService();
                setCoreService(coreService);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };



    public static CoreApplication newInstance() {
        return instance;
    }

    public static RefWatcher getRefWatcher(Context context) {
        CoreApplication application = (CoreApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        refWatcher = LeakCanary.install(this);
        startIntent= new Intent(this, CoreService.class);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        requestQueue = Volley.newRequestQueue(this);//HTTP网络请求队列初始化
        //初始化聚合(Set)页面消息接收器，将会从CoreService中获取即时消息
        coreAppGetChatDataHandler=new CoreMessageHandler();
        startServices();
    }


    public void startServices(){
        Intent startIntent =new Intent(this, CoreService.class);
        getApplicationContext().startService(startIntent);
        setServiceConnection(serviceConnection);
        getApplicationContext().bindService(startIntent,serviceConnection,BIND_AUTO_CREATE);
    }


    public void SyncData(Context context){
        if(getUser_id()==null){
           setUser_id(SharedPreferencesUtil.getUserId(context));
        }
    }

    /**
     * 获取指定的个人好友Bean
     * @param id userid
     * @return 好友Bean
     */
    public RelationShipLevelBean getRelationShipBean(String  id,RelationShipLevelBeanDao relationShipLevelBeanDao){
        for (int i = 0; i <personFrendList.size() ; i++) {
            if(id.equals(personFrendList.get(i).getMinor_user())){
                return personFrendList.get(i);
            }
        }
       RelationShipLevelBean bean= relationShipLevelBeanDao.queryBuilder().where(RelationShipLevelBeanDao.Properties.Minor_user.eq(id)).unique();
        if(bean!=null){
            return bean;
        }
        return null;
    }



    /**
     * 添加新关系请求列表
     * @param newRelationShipBean
     * @param status  1 更新NewRelationActive 0不更新该值（对应同步数据的操作）
     */
    public void addNewRelationShip(NewRelationShipBean newRelationShipBean ,int status){
        Log.v("开始更新新关系","ok");
        if(newRelationShipList!=null){
            for (int i = 0; i <newRelationShipList.size() ; i++) {
                if(newRelationShipBean.getUser_id().equals(newRelationShipList.get(i).getUser_id())){
                    Log.v("开始更新新关系","failed");
                    return ;
                }
            }
            newRelationShipList.add(newRelationShipBean);
            setNewRelationActive(1);
        }else{
            newRelationShipList=new ArrayList<>();
            newRelationShipList.add(newRelationShipBean);
            setNewRelationActive(getNewRelationActive());
        }

    }






    public SetPageMainFragmentAdapter getSetPageMainFragmentAdapter() {
        return setPageMainFragmentAdapter;
    }

    public void setSetPageMainFragmentAdapter(SetPageMainFragmentAdapter setPageMainFragmentAdapter) {
        this.setPageMainFragmentAdapter = setPageMainFragmentAdapter;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }


    public CoreService getCoreService() {
        return coreService;
    }

    public void setCoreService(CoreService coreService) {
        this.coreService = coreService;
    }

    /**
     * 检查返回的Response header中有没有session
     * @param responseHeaders Response Headers.
     */
    public final void checkSessionCookie(Map<String, String> responseHeaders) {
        Log.v("session", String.valueOf(responseHeaders));
        Log.v("sessionid", String.valueOf(responseHeaders.get(SET_COOKIE_KEY)));
        if (responseHeaders.containsKey(SET_COOKIE_KEY)) {
            String cookie = responseHeaders.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                SharedPreferences.Editor prefEditor = preferences.edit();
                prefEditor.putString(SESSION_COOKIE, cookie);
                prefEditor.commit();
            }
        }
    }
    public void setUploadTime(Date time){
        this.uploadTime=time.getTime();
        SharedPreferencesUtil.setUploadDate(getApplicationContext(),time);
    }

    public Long getUploadTime(){
        if(uploadTime==null){
            this.uploadTime=SharedPreferencesUtil.getUploadDate(getApplicationContext());
        }
        return uploadTime;
    }


    public void setUploadCertificate(String ak,String sk,String token,String expiration){
        this.ak=ak;
        this.sk=sk;
        this.token=token;
        this.expiration=expiration;
        SharedPreferencesUtil.setSk(getApplicationContext(),sk);
        SharedPreferencesUtil.setAk(getApplicationContext(),ak);
        SharedPreferencesUtil.setToken(getApplicationContext(),token);
        SharedPreferencesUtil.setExpiration(getApplicationContext(),expiration);
    }
    public boolean checkUploadCertificate(){
        Log.v("checkUploadCertificate","start");
        if (sk==null){
            if(getSk()==null){
                return false;
            }
        }
        if(uploadTime==null){
            uploadTime=SharedPreferencesUtil.getUploadDate(getApplicationContext());
        }
        Long diff=new Date().getTime() - uploadTime;
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
        long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
        Log.v("相差", String.valueOf(minutes));
        if(days>1){
            return  false;
        }
        if(hours>1){
            return  false;
        }
        if(minutes<=29){
            getUploadCertificate();
            return true;
        }else{
            return false;
        }
    }

    public  void getUploadCertificate(){
        if(ak==null){
            ak=SharedPreferencesUtil.getAk(getApplicationContext());
        }
        if(token==null){
            token=SharedPreferencesUtil.getToken(getApplicationContext());
        }
        if(expiration==null){
            expiration=SharedPreferencesUtil.getExpiration(getApplicationContext());
        }
    }


    private String getSk(){
        return SharedPreferencesUtil.getSk(getApplicationContext());
    }






    /**
     * 添加session到Request header中
     */
    public final void addSessionCookie(Map<String, String> requestHeaders) {
        String sessionId = preferences.getString(SESSION_COOKIE, "");
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (requestHeaders.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(requestHeaders.get(COOKIE_KEY));
            }
            requestHeaders.put(COOKIE_KEY, builder.toString());

            Log.v("sessionid",sessionId);
        }
    }
    private void LoadPersonFrendList(final Runnable callBack){
        Log.v("LoadPersonFrendList","success");
        Flowable.create(new FlowableOnSubscribe<ArrayList<RelationShipLevelBean>>() {
            @Override
            public void subscribe(FlowableEmitter<ArrayList<RelationShipLevelBean>> e) throws Exception {
                ArrayList<RelationShipLevelBean> list=null;
                RelationShipLevelBeanDao relationShipLevelBeanDao= BeanDaoManager.getInstance().getDaoSession().getRelationShipLevelBeanDao();
                QueryBuilder queryBuilder=relationShipLevelBeanDao.queryBuilder();
                Query query=queryBuilder.where(RelationShipLevelBeanDao.Properties.Level.eq(4)).build();
                if(query!=null){
                    list= (ArrayList<RelationShipLevelBean>) query.list();
                    e.onNext(list);
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<RelationShipLevelBean>>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(ArrayList<RelationShipLevelBean> relationShipLevelBeans) {
               setPersonFrendList(relationShipLevelBeans);
               Log.v("results", String.valueOf(personFrendList.size()));
               callBack.run();
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    public ArrayList<RelationShipLevelBean> getPersonFrendList() {
        return personFrendList;
    }



    public void asynPersonFrendList(Runnable callBack){
        if(personFrendList==null){
            LoadPersonFrendList(callBack);
        }
    }

    public void setPersonFrendList(ArrayList<RelationShipLevelBean> personFrendList) {
        if(personFrendList==null){
            this.personFrendList=null;
        }else{
            this.personFrendList=new ArrayList<>();
            this.personFrendList.addAll(personFrendList);
        }
    }



    public Handler getCoreAppGetChatDataHandler() {
        return coreAppGetChatDataHandler;
    }

    public void setCoreAppGetChatDataHandler(Handler coreAppGetChatDataHandler) {
        this.coreAppGetChatDataHandler = coreAppGetChatDataHandler;
    }


    public ServiceConnection getServiceConnection() {
        return serviceConnection;
    }

    public void setServiceConnection(ServiceConnection serviceConnection) {
        this.serviceConnection = serviceConnection;
    }


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public PersonRelationShipAdapter getPersonRelationShipAdapter() {
        return PersonRelationShipAdapter;
    }

    public void setPersonRelationShipAdapter(PersonRelationShipAdapter personRelationShipAdapter) {
        this.PersonRelationShipAdapter = personRelationShipAdapter;
    }

    public String getUser_id() {
        if(user_id==null){
            setUser_id(SharedPreferencesUtil.getUserId(getApplicationContext()));
        }
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setRelationShipLevelBeanDao(RelationShipLevelBeanDao relationShipLevelBeanDao) {
        this.relationShipLevelBeanDao = relationShipLevelBeanDao;
    }


    public void setMessageHistoryBeanDao(MessageHistoryBeanDao messageHistoryBeanDao) {
        this.messageHistoryBeanDao = messageHistoryBeanDao;
    }

    public View getRoot() {
        return root;
    }

    public void setRoot(View root) {
        this.root = root;
    }


    public boolean isDisPlayType() {
        return disPlayType;
    }

    public void setDisPlayType(boolean disPlayType) {
        this.disPlayType = disPlayType;
    }

    public UserBean getUserBean() {
        if(userBean==null){
            UserBeanDao userBeanDao=BeanDaoManager.getInstance().getDaoSession().getUserBeanDao();
            setUserBean(userBeanDao.queryBuilder().where(UserBeanDao.Properties.Id.eq(getUser_id())).build().unique());
        }
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
        this.user_id=userBean.getId();
    }

    public ArrayList<NewRelationShipBean> getNewRelationShipList() {
        return newRelationShipList;
    }

    public void setNewRelationShipList(ArrayList<NewRelationShipBean> newRelationShipList) {
        this.newRelationShipList = newRelationShipList;
    }

    public Integer getNewRelationActive() {
        if(NewRelationActive==null){
            NewRelationActive=SharedPreferencesUtil.getNewRelationActive(getBaseContext());
        }
        return NewRelationActive;
    }

    public void setNewRelationActive(Integer newRelationActive) {
        NewRelationActive = newRelationActive;
        if(ralationShipPageMainFragment!=null){
            ralationShipPageMainFragment.updateNewRelaionShipActive();
        }
        SharedPreferencesUtil.setNewRelationActive(getBaseContext(),newRelationActive);
    }


    public void addRelationShipLevelBean(  RelationShipLevelBean bean){
        Log.v("CoreApplication","addRelationShipLevelBean");
        if(relationShipLevelBeanDao==null) {
            relationShipLevelBeanDao = BeanDaoManager.getInstance().getDaoSession().getRelationShipLevelBeanDao();
        }
        relationShipLevelBeanDao.insert(bean);
        personFrendList.add(bean);
        getPersonRelationShipAdapter().notifyDataSetChanged();
    }



    public String getDir() {
        return dir;
    }


}
