package com.example.chen1.uncom.application;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

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
import com.example.chen1.uncom.bean.UserBean;
import com.example.chen1.uncom.bean.UserBeanDao;
import com.example.chen1.uncom.relationship.PersonRelationShipAdapter;
import com.example.chen1.uncom.relationship.RalationShipPageMainFragment;
import com.example.chen1.uncom.service.CoreService;
import com.example.chen1.uncom.set.SetPageMainFragmentAdapter;
import com.example.chen1.uncom.utils.SharedPreferencesUtil;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by chen1 on 2017/10/3.
 */

public class CoreApplication extends Application {

    private boolean disPlay=true;
    private boolean disPlayType=true;//1 从RalationShipPageMainFragment到该fragment
    //0 从NewRelationShipResultsFragment 到该fragment
    private static final String SET_COOKIE_KEY = "set-cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "skey";
    private CoreService coreService;
    private RalationShipPageMainFragment ralationShipPageMainFragment;//关系页面的引用
    private static CoreApplication instance;
    private ServiceConnection serviceConnection=null;//用户退出登陆时，通过该变量关闭Coreservice
    private Intent startIntent =null;
    private Activity activity;
    private RequestQueue requestQueue;
    private SharedPreferences preferences;
    private ArrayList<NewRelationShipBean> newRelationShipList;//新关系列表
    private Integer NewRelationActive;//新增的新关系数量（未查看）
    private Handler getChatDataHandler;  //当用户在对应的聊天界面，将数据发送到聊天界面
    private  String user_id;
    private MessageHistoryBeanDao messageHistoryBeanDao;
    private UserBean userBean;
    private MessageHistoryBean messageHistoryBean;
    private Handler coreAppGetChatDataHandler;//获取消息数据 用户不在对应的聊天界面
    private RelationShipLevelBeanDao relationShipLevelBeanDao;
    private ArrayList<RelationShipLevelBean>personFrendList;
    private ArrayList<RelationShipLevelBean>activePersonMessageList=new ArrayList<>();
    private SetPageMainFragmentAdapter setPageMainFragmentAdapter;
    private PersonRelationShipAdapter PersonRelationShipAdapter;
    private Fragment temperFragment;
    private View root;
    public static CoreApplication newInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        startIntent= new Intent(this, CoreService.class);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        requestQueue = Volley.newRequestQueue(this);//HTTP网络请求队列初始化
        //初始化聚合(Set)页面消息接收器，将会从CoreService中获取即时消息
        coreAppGetChatDataHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        JSONArray jsonArray=(JSONArray) msg.obj;
                        try {
                            /*获取relationShipLevelBeanDao*/
                            if(relationShipLevelBeanDao==null){
                                relationShipLevelBeanDao = BeanDaoManager.getInstance().getDaoSession().getRelationShipLevelBeanDao();
                            }
                            /*获取user_id*/
                            if(user_id==null){
                                user_id= SharedPreferencesUtil.getUserId(getApplicationContext());
                            }
                            /*获取messageHistoryBeanDao*/
                            if(messageHistoryBeanDao==null){
                                messageHistoryBeanDao = BeanDaoManager.getInstance().getDaoSession().getMessageHistoryBeanDao();
                            }
                            for (int i = 0; i <jsonArray.length() ; i++) {

                                JSONObject object=jsonArray.getJSONObject(i);
                                //MessageHistoryBean item2= new MessageHistoryBean(frendData.getId(),str,new Date().toString(),true);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String str=  object.getString("time");
                                str=str.replaceAll( "\\\\",  "");
                                str=str.replaceAll("\"","");
                                String d = format.format(Long.parseLong(str));
                                Date date=format.parse(d);
                                Log.v("time", String.valueOf(date));
                                messageHistoryBean=new MessageHistoryBean(null,object.getString("ownId"),user_id,object.getString("content"),date,true,false);
                                RelationShipLevelBean relationShipLevelBean= getRelationShipBean(messageHistoryBean.getOwnId());
                                relationShipLevelBean.setLast_message(messageHistoryBean.getContent());
                                relationShipLevelBean.setLast_active_time(messageHistoryBean.getTime());
                                if(relationShipLevelBean.getActive()==false){
                                    relationShipLevelBean.setActive(true);
                                }
                                Integer temp=relationShipLevelBean.getUn_look();
                                Log.v("sendTmper","null:"+ String.valueOf(temp));
                                if(temp!=null){
                                    relationShipLevelBean.setUn_look(temp+1);
                                }else{
                                    relationShipLevelBean.setUn_look(1);
                                }
                                updateActivePersonMessageList(relationShipLevelBean,1);
                                relationShipLevelBeanDao.update(relationShipLevelBean);
                                messageHistoryBeanDao.insert(messageHistoryBean);
                            }


                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        //退出登陆逻辑
                        Log.v("coreApplication:","退出登陆");
                        SharedPreferencesUtil.delUsetId(getApplicationContext());
                        getApplicationContext().unbindService(serviceConnection);
                        getApplicationContext().stopService(startIntent);
                        Intent intent=new Intent(getApplicationContext(), AccessActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        activity.finish();
                        break;
                        //System.exit(0);

                    case 1:
                        //执行Set页面UI更新
                        if(setPageMainFragmentAdapter!=null){
                            setPageMainFragmentAdapter.notifyDataSetChanged();
                        }
                        break;
                    case 3:
                        //执行关系页面个人关系UI更新
                        if(PersonRelationShipAdapter !=null){
                            PersonRelationShipAdapter.notifyDataSetChanged();
                        }
                        break;
                    case 4:
                        //执行新关系请求页面更新
                        Log.v("执行新关系请求页面更新","ok");
                        NewRelationShipBean newRelationShipBean=(NewRelationShipBean) msg.obj;
                        addNewRelationShip(newRelationShipBean);
                        break;
                }
            }
        };
    }

    /**
     * 获取指定的个人好友Bean
     * @param id userid
     * @return 好友Bean
     */
    public RelationShipLevelBean getRelationShipBean(String  id){
        for (int i = 0; i <personFrendList.size() ; i++) {
            if(id.equals(personFrendList.get(i).getMinor_user())){
                return personFrendList.get(i);
            }
        }
        return null;
    }

    /**
     * 添加新关系请求列表
     * @param newRelationShipBean
     */
    public void addNewRelationShip(NewRelationShipBean newRelationShipBean){
        Log.v("开始更新新关系","ok");
        if(newRelationShipList!=null){
            for (int i = 0; i <newRelationShipList.size() ; i++) {
                if(newRelationShipBean.getUser_id().equals(newRelationShipList.get(i))){
                    Log.v("开始更新新关系","failed");
                    return ;
                }
            }
        }else{
            newRelationShipList=new ArrayList<>();
            newRelationShipList.add(newRelationShipBean);
            setNewRelationActive(getNewRelationActive()+1);
        }

    }


    /**
     *从SQLlite数据库中同步数据到UI，由于数据比较多，所以新开线程进行数据同步处理
     * @param status 私聊信息同步状态控制 1 强制做状态同步 0 不强制，只有在frendlist列表为空时同步
     */
    public void syncData(final int status){
        //当personFrendList 数据为空 说明为第一次进入状态或重载状态。开启子线程获取相关信息
        if(CoreApplication.newInstance().getPersonFrendList()==null || status==1){
            if(status==1 && CoreApplication.newInstance().getPersonFrendList()!=null){
                personFrendList.clear();
                Log.v("清除personFrendList", String.valueOf(personFrendList.size()));
            }
            if(status==1 && CoreApplication.newInstance().getPersonFrendList()!=null){
                activePersonMessageList.clear();
                Log.v("清除activePersonMessageList", String.valueOf(activePersonMessageList.size()));
            }
            Log.v("personFrendListNode","none");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(getUser_id()==null){
                        setUser_id(SharedPreferencesUtil.getUserId(getBaseContext()));
                    }
                    NewRelationShipBeanDao newRelationShipBeanDao=BeanDaoManager.getInstance().getDaoSession().getNewRelationShipBeanDao();
                    UserBeanDao userBeanDao=BeanDaoManager.getInstance().getDaoSession().getUserBeanDao();
                    setUserBean(userBeanDao.queryBuilder().where(UserBeanDao.Properties.Id.eq(getUser_id())).build().unique());
                    RelationShipLevelBeanDao relationShipLevelBeanDao= BeanDaoManager.getInstance().getDaoSession().getRelationShipLevelBeanDao();
                    QueryBuilder queryBuilder=relationShipLevelBeanDao.queryBuilder();
                    Query query=queryBuilder.where(RelationShipLevelBeanDao.Properties.Level.eq(4)).build();
                    ArrayList<NewRelationShipBean> newRelationShipList= (ArrayList<NewRelationShipBean>) newRelationShipBeanDao.queryBuilder().list();
                    Message message=new Message();
                    message.what=3;
                    setPersonFrendList((ArrayList<RelationShipLevelBean>) query.list());
                    coreAppGetChatDataHandler.sendMessage(message);
                    Log.v("CoreApplicationListSize", String.valueOf(getPersonFrendList().size()));
                    query=queryBuilder.where(queryBuilder.and(RelationShipLevelBeanDao.Properties.Level.eq(4),RelationShipLevelBeanDao.Properties.Active.eq(true))).orderDesc(RelationShipLevelBeanDao.Properties.Last_active_time).build();
                    ArrayList<RelationShipLevelBean> list= (ArrayList<RelationShipLevelBean>) query.list();
                    Log.v("CoreApplicationActive", String.valueOf(list.size()));
                    for (int i = 0; i <list.size(); i++) {
                        Log.v("newtheard",list.get(i).getUsername());
                        if(list.get(i).getActive()==true){
                            getActivePersonMessageList().add(list.get(i));
                            Log.v("activePersonMessageListSize", String.valueOf(activePersonMessageList.size()));
                        }
                    }
                    Message message1=new Message();
                    message1.what=1;
                    coreAppGetChatDataHandler.sendMessage(message1);
                    Log.v("NewRelationshipSize", String.valueOf(newRelationShipList.size()));
                    for (int i=0;i<newRelationShipList.size();i++){
                        Log.v("NewRelationshipSize", String.valueOf(newRelationShipList.get(i)));
                        Message message2=new Message();
                        message2.what=4;
                        message2.obj=newRelationShipList.get(i);
                        coreAppGetChatDataHandler.sendMessage(message2);
                    }


                }
            }).start();
        }
    }

    /**
     * 更新界面好友信息数据(Set)
     * @param relationShipLevelBean  消息Bean(当前指定为好友信息Bean)
     * @param isRealChange  1表示除了更新未读提示外，还会对该信息在聚合(Set)页面的位置进行更新(example:某人给我发了信息，
     *                      而之前也有人给我发了信息，我认为这时，不能直接放到头部)
     *                      2表示只是更新未读提示(example:他只是看了这个人发来的的消息，而并没有给这个人回复)
     *                      3除了更新未读提示 还会更新其位置，并更新到最顶端(example:该用户不仅看了这个人发来的的消息，
     *                       并且也回复了他)
     */
    public String updateActivePersonMessageList(RelationShipLevelBean relationShipLevelBean,int isRealChange) {
        Log.v("进入Set消息排序界面","ok");
        for (int i = 0; i < activePersonMessageList.size(); i++) {
            if (activePersonMessageList.get(i).getMinor_user().equals(relationShipLevelBean.getMinor_user())) {
                Log.v("updateMessage", String.valueOf(isRealChange));
                if (isRealChange == 1) {
                    //在未读信息之后加入该信息
                    //如果该用户会话状态在Set界面头部，则只需将其加到头部
                    if (i == 0) {
                        activePersonMessageList.set(0, relationShipLevelBean);
                    } else {//如不在头部则，从未读信息后加入
                        activePersonMessageList.remove(i);
                        for (int j = 0; j < activePersonMessageList.size(); j++) {
                            if (activePersonMessageList.get(j).getUn_look() == 0 || activePersonMessageList.get(j).getUn_look() == null) {
                                activePersonMessageList.add(j, relationShipLevelBean);
                                Log.v("searchResuls", "s" + activePersonMessageList.get(j).getUn_look());
                                break;
                            } else if (j == (activePersonMessageList.size() - 1)) {
                                activePersonMessageList.add(relationShipLevelBean);
                                break;
                            }

                        }
                    }

                } else if (isRealChange == 2) {
                    activePersonMessageList.set(i, relationShipLevelBean);
                } else if (isRealChange == 3) {
                    //在未读信息之后加入该信息
                    //如果该用户会话状态在Set界面头部，则只需将其加到头部
                    Log.v("currentI   ", String.valueOf(i));
                    if (i == 0) {
                        activePersonMessageList.set(0, relationShipLevelBean);
                    } else {//如不在头部则将其加入到头部 如之前在Set界面，则删掉之前的位置
                        activePersonMessageList.add(0, relationShipLevelBean);
                        activePersonMessageList.remove(i + 1);

                    }
                }
                setPageMainFragmentAdapter.notifyDataSetChanged();
                return ";";
            }
            if ((i == activePersonMessageList.size() - 1) && (isRealChange == 1 || isRealChange == 3)) {
                activePersonMessageList.add(0, relationShipLevelBean);
                setPageMainFragmentAdapter.notifyDataSetChanged();
                return "";
            }
        }
        //对应一个特殊情况：处理该条消息时，聚合(Set)界面为空
        if(isRealChange == 1 || isRealChange == 3){
            Log.v("执行该语句","ok");
            activePersonMessageList.add(0, relationShipLevelBean);
            setPageMainFragmentAdapter.notifyDataSetChanged();
        }
        return "";
    }

    public Handler getGetChatDataHandler() {
        return getChatDataHandler;
    }

    public void setGetChatDataHandler(Handler getChatDataHandler) {
        this.getChatDataHandler = getChatDataHandler;
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

    public ArrayList<RelationShipLevelBean> getPersonFrendList() {
        return personFrendList;
    }

    public void setPersonFrendList(ArrayList<RelationShipLevelBean> personFrendList) {
        this.personFrendList = personFrendList;
    }

    public ArrayList<RelationShipLevelBean> getActivePersonMessageList() {
        return activePersonMessageList;
    }

    public void setActivePersonMessageList(ArrayList<RelationShipLevelBean> activePersonMessageList) {
        this.activePersonMessageList = activePersonMessageList;
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
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public RelationShipLevelBeanDao getRelationShipLevelBeanDao() {
        return relationShipLevelBeanDao;
    }

    public void setRelationShipLevelBeanDao(RelationShipLevelBeanDao relationShipLevelBeanDao) {
        this.relationShipLevelBeanDao = relationShipLevelBeanDao;
    }

    public MessageHistoryBeanDao getMessageHistoryBeanDao() {
        return messageHistoryBeanDao;
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

    public Fragment getTemperFragment() {
        return temperFragment;
    }

    public void setTemperFragment(Fragment temperFragment) {
        this.temperFragment = temperFragment;
    }

    public boolean isDisPlayType() {
        return disPlayType;
    }

    public void setDisPlayType(boolean disPlayType) {
        this.disPlayType = disPlayType;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
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

    public RalationShipPageMainFragment getRalationShipPageMainFragment() {
        return ralationShipPageMainFragment;
    }

    public void setRalationShipPageMainFragment(RalationShipPageMainFragment ralationShipPageMainFragment) {
        this.ralationShipPageMainFragment = ralationShipPageMainFragment;
    }
}
