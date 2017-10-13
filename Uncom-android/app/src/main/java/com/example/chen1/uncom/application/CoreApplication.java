package com.example.chen1.uncom.application;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.chen1.uncom.access.AccessActivity;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.MessageHistoryBean;
import com.example.chen1.uncom.bean.MessageHistoryBeanDao;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;
import com.example.chen1.uncom.relationship.person_relation_ship_adapter;
import com.example.chen1.uncom.service.CoreService;
import com.example.chen1.uncom.set.SetPageMainFragmentAdapter;
import com.example.chen1.uncom.utils.SharedPreferencesUtil;

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

    private static final String SET_COOKIE_KEY = "set-cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "skey";
    private CoreService coreService;
    private static CoreApplication instance;
    private ServiceConnection serviceConnection=null;//用户退出登陆时，通过该变量关闭Coreservice
    private Intent startIntent =null;
    private Activity activity;
    private RequestQueue requestQueue;
    private SharedPreferences preferences;
    private Handler getChatDataHandler;  //当用户在对应的聊天界面，将数据发送到聊天界面
    private  String user_id;
    private MessageHistoryBeanDao messageHistoryBeanDao;
    private MessageHistoryBean messageHistoryBean;
    private Handler coreAppGetChatDataHandler;//获取消息数据 用户不在对应的聊天界面
    private RelationShipLevelBeanDao relationShipLevelBeanDao;
    private ArrayList<RelationShipLevelBean>personFrendList;
    private ArrayList<RelationShipLevelBean>activePersonMessageList=new ArrayList<>();
    private SetPageMainFragmentAdapter setPageMainFragmentAdapter;
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
                        //System.exit(0);

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
     * 更新界面好友信息数据(Set)
     * @param relationShipLevelBean  消息Bean(当前指定为好友信息Bean)
     * @param isRealChange  true表示除了更新未读提示外，还会对该信息在聚合(Set)页面的位置进行更新 fals表示
     *                      只是更新未读提示
     */
 /*   public void updateActivePersonMessageList(RelationShipLevelBean relationShipLevelBean,boolean isRealChange){
        for (int i = 0; i <activePersonMessageList.size() ; i++) {
            if(activePersonMessageList.get(i).getMinor_user().equals(relationShipLevelBean.getMinor_user())){
                Log.v("updateMessage","ok");
                if(isRealChange==true){
                    //在未读信息之后加入该信息
                    //如果该用户会话状态在Set界面头部，则只需将其加到头部
                    if(i==0){
                        activePersonMessageList.set(0,relationShipLevelBean);
                    }else{//如不在头部则，从未读信息后加入
                        activePersonMessageList.remove(i);
                        for (int j = 0; j < activePersonMessageList.size(); j++) {
                            if(activePersonMessageList.get(j).getUn_look()==0 || activePersonMessageList.get(j).getUn_look()==null){
                                Log.v("searchResuls","s"+activePersonMessageList.get(j).getUn_look());
                                activePersonMessageList.add(j,relationShipLevelBean);
                                break;
                            }else if(j==(activePersonMessageList.size()-1)){
                                activePersonMessageList.add(relationShipLevelBean);
                                break;
                            }

                        }
                    }

                }else{
                    activePersonMessageList.set(i,relationShipLevelBean);
                }
                setPageMainFragmentAdapter.notifyDataSetChanged();
                return ;
            }
        }
        //当Set页面没有活动信息。则直接将其加入到头部
        Log.v("updateMessage","ok2");
        if(isRealChange==true){
            activePersonMessageList.add(0,relationShipLevelBean);
            setPageMainFragmentAdapter.notifyDataSetChanged();
        }
    }*/




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
            activePersonMessageList.add(0, relationShipLevelBean);
        }
        return "";
    }
     /*   //当Set页面没有活动信息。则直接将其加入到头部
     Log.v("updateMessage","ok2");
        if(isRealChange==1 || isRealChange==3 ){
            activePersonMessageList.add(0,relationShipLevelBean);
            setPageMainFragmentAdapter.notifyDataSetChanged();
        }*/


















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
}
