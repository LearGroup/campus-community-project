package com.example.chen1.uncom.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
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
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        requestQueue = Volley.newRequestQueue(this);
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
                                messageHistoryBean=new MessageHistoryBean(null,object.getString("ownId"),user_id,object.getString("content"),date,false);
                                RelationShipLevelBean relationShipLevelBean= getRelationShipBean(messageHistoryBean.getOwnId());
                                relationShipLevelBean.setLast_message(messageHistoryBean.getContent());
                                relationShipLevelBean.setLast_active_time(messageHistoryBean.getTime());
                                if(relationShipLevelBean.getActive()==false){
                                    relationShipLevelBean.setActive(true);
                                }
                                updateActivePersonMessageList(relationShipLevelBean);
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
                }
            }
        };
    }

    public RelationShipLevelBean getRelationShipBean(String  id){
        for (int i = 0; i <personFrendList.size() ; i++) {
            if(id.equals(personFrendList.get(i).getMinor_user())){
                return personFrendList.get(i);
            }
        }
        return null;
    }

    public void updateActivePersonMessageList(RelationShipLevelBean relationShipLevelBean){
        for (int i = 0; i <activePersonMessageList.size() ; i++) {
            if(activePersonMessageList.get(i).getMinor_user().equals(relationShipLevelBean.getMinor_user())){
                Log.v("updateMessage","ok");
                activePersonMessageList.add(0,relationShipLevelBean);
                activePersonMessageList.remove(i+1);
                setPageMainFragmentAdapter.notifyDataSetChanged();
                return ;
            }
        }
        Log.v("updateMessage","ok2");
        activePersonMessageList.add(0,relationShipLevelBean);
        setPageMainFragmentAdapter.notifyDataSetChanged();
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
}
