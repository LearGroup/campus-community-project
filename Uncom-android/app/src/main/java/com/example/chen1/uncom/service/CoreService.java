package com.example.chen1.uncom.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.preference.Preference;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.DaoSession;
import com.example.chen1.uncom.bean.MessageHistoryBean;
import com.example.chen1.uncom.bean.MessageHistoryBeanDao;
import com.example.chen1.uncom.bean.NewRelationShipBean;
import com.example.chen1.uncom.bean.NewRelationShipBeanDao;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;
import com.example.chen1.uncom.bean.UserBean;
import com.example.chen1.uncom.bean.UserBeanDao;
import com.example.chen1.uncom.utils.CoreServiceCallBack;
import com.example.chen1.uncom.utils.PopupWindowUtils;
import com.example.chen1.uncom.utils.SharedPreferencesUtil;
import com.example.chen1.uncom.utils.UserBeanAndJsonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by chen1 on 2017/10/3.
 */

public class CoreService extends Service  {
    private Handler handler;
    private Handler getChatDataHandler;
    private Handler coreAppGetChatDataHandler;
    private ChatCoreBinder binder=new ChatCoreBinder(this);
    private Socket socket;
    private NewRelationShipBeanDao newRelationShipBeanDao;
    private RelationShipLevelBeanDao relationShipLevelBeanDao;
    private String user_id;
    private Thread thread;
    private Context context;
    private NewRelationShipBean newRelationShipBean;
    private UserBean userBean;
    private JSONObject loginData;
    private Handler sendChatHandler;
    private MessageHistoryBeanDao messageHistoryBeanDao;
    private Handler buildRelationHandler;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        coreAppGetChatDataHandler=CoreApplication.newInstance().getCoreAppGetChatDataHandler();
        user_id=SharedPreferencesUtil.getUserId(CoreApplication.newInstance().getApplicationContext());
        DaoSession session= BeanDaoManager.getInstance().getDaoSession();
        UserBeanDao userBeanDao = session.getUserBeanDao();
        userBean=userBeanDao.queryBuilder().where(UserBeanDao.Properties.Id.eq(user_id)).build().unique();
        Log.v("CoreServiceUser_id",user_id);
        HashMap<String ,String > login=new HashMap<>();
        if(userBean!=null){
            login.put("userName",userBean.getUsername());
            login.put("userId",userBean.getId());
            loginData=new JSONObject(login);
        }

        Log.v("createCoreService","ok");
        Log.v("CoreService_user_id", "ss"+user_id);
        if(thread==null){
         thread= new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.v("createNewThread","ok");
                        /*http://47.95.0.73:8081*/
                        /*http://10.0.2.2:8081*/
                        socket= IO.socket("http://47.95.0.73:8081");
                        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                Log.v("CONNECT","ok");
                                if(loginData!=null) {
                                    socket.emit("login",loginData);
                                }
                                if(handler!=null){
                                    Message message=new Message();
                                    message.what=1;
                                    handler.sendMessage(message);
                                }
                            }
                        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                Log.v("DISCONNECT","ok");
                                if(handler!=null){
                                    Message message=new Message();
                                    message.what=0;
                                    handler.sendMessage(message);
                                }
                            }
                        }).on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                Log.v("EVENT_RECONNECT","ok");
                                socket.emit("synchronization","synchronization");
                            }
                        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                Log.v("EVENT_RECONNECT_ERROR","ok");

                                if(handler!=null){
                                    Message message=new Message();
                                    message.what=0;
                                    handler.sendMessage(message);
                                }
                            }
                        }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                Log.v("EVENT_CONNECT_TIMEOUT","ok");
                                if(handler!=null){
                                    Message message=new Message();
                                    message.what=0;
                                    handler.sendMessage(message);
                                }

                            }
                        }).on("message", new Emitter.Listener() {
                            //接收信息
                            @Override
                            public void call(Object... args) {
                                Log.v("chatresponse", String.valueOf(args[0]));
                                JSONObject object=(JSONObject) args[0];
                                try {

                                    //当用户在对应的聊天界面，将数据发送到聊天界面
                                //    Log.v("chatresponse", String.valueOf(jsonArray));
                                    if(getChatDataHandler!=null && object.getString("status").equals("1")){
                                        JSONArray jsonArray=object.getJSONArray("results");
                                        Log.v("chatresponse", "1");
                                        Message message=new Message();
                                        message.what=0;
                                        message.obj=jsonArray;
                                        getChatDataHandler.sendMessage(message);
                                    }else if(object.getString("status").equals("1")){
                                        JSONArray jsonArray=object.getJSONArray("results");
                                    /*若getChatDataHandler=null说明chat界面尚未初始化*/
                                        Log.v("chatresponse", "2");
                                        Message message=new Message();
                                        message.what=0;
                                        message.obj=jsonArray;
                                        coreAppGetChatDataHandler.sendMessage(message);
                                        //CoreApplication.newInstance().updateActivePersonMessageList(relationShipLevelBean);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).on("checkStatus", new Emitter.Listener() {
                            //获取服务端传来的状态码：1 同步信息   2 退出账户
                            @Override
                            public void call(Object... args) {
                                JSONObject object=(JSONObject)args[0];
                                try {
                                    Log.v("checkStatus", object.getString("status"));
                                    if(object.getString("status").equals("1")){
                                        socket.emit("synchronization");
                                    }else if(object.getString("status").equals("2")){
                                        SharedPreferencesUtil.delSessionId(context);
                                        Message message=new Message();
                                        message.what=2;
                                        message.obj="ofline";
                                        Log.v("checkStatus","ok");
                                        coreAppGetChatDataHandler.sendMessage(message);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).on("requestBuildRelationShip", new Emitter.Listener() {
                            //接收新关系请求
                            @Override
                            public void call(Object... args) {
                                JSONObject  object= (JSONObject) args[0];
                                try {
                                    if(object.getString("status").equals("1")){
                                        JSONArray jsonArray=object.getJSONArray("results");
                                        for (int i=0;i<jsonArray.length();i++){
                                            Log.v("relationshipRresponse", String.valueOf(jsonArray));
                                            NewRelationShipBean bean=new Gson().fromJson(String.valueOf(jsonArray.getJSONObject(i)), NewRelationShipBean.class);
                                            bean.setView_type(1);
                                            bean.setResult_type(2);
                                            Message message =new Message();
                                            message.what=4;
                                            message.obj=bean;
                                            coreAppGetChatDataHandler.sendMessage(message);
                                            if(newRelationShipBeanDao==null){
                                                newRelationShipBeanDao= BeanDaoManager.getInstance().getDaoSession().getNewRelationShipBeanDao();
                                            }

                                            newRelationShipBeanDao.insert(bean);
                                        }
                                        HashMap<String,String>data=new HashMap<String, String>();
                                        data.put("status","1");
                                        JSONObject params = new JSONObject(data);
                                        socket.emit("requestBuildRelationShipResponse",params);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }).on("registerPersonRelationShip", new Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                //好友请求接受 添加新好友
                                Log.v("registerPersonRelationShip", String.valueOf(args[0]));
                                JSONObject  object= (JSONObject) args[0];
                                try {
                                    if(object.getString("status").equals("1")){
                                        JSONArray jsonArray=object.getJSONArray("results");
                                        ArrayList<RelationShipLevelBean> Array= UserBeanAndJsonUtils.getRelationShipLevelBean(object.getJSONArray("results"));
                                        for (int i=0;i<jsonArray.length();i++){
                                            Log.v("registerPersonRelationShip", String.valueOf(jsonArray.getJSONObject(i)));
                                            Log.v("registerPersonRelationShip", String.valueOf(jsonArray.length()));
                                            Message message =new Message();
                                            message.what=6;
                                            message.obj=Array.get(i);
                                            coreAppGetChatDataHandler.sendMessage(message);
                                            if(newRelationShipBeanDao==null){
                                                newRelationShipBeanDao= BeanDaoManager.getInstance().getDaoSession().getNewRelationShipBeanDao();
                                               newRelationShipBean= newRelationShipBeanDao.queryBuilder().where(NewRelationShipBeanDao.Properties.User_id.eq(Array.get(i).getMinor_user())).build().unique();
                                                if(newRelationShipBean!=null){
                                                    newRelationShipBean.setResult_type(1);
                                                    newRelationShipBeanDao.update(newRelationShipBean);
                                                }
                                            }
                                            HashMap<String,String>data=new HashMap<String, String>();
                                            data.put("status","1");
                                            JSONObject params = new JSONObject(data);
                                            socket.emit("registerPersonRelationShipResponse",params);
                                            JSONArray jsonArray1=new JSONArray();
                                            JSONObject json=new JSONObject();
                                            json.put("ownId",Array.get(i).getMinor_user());
                                            json.put("targetId",CoreApplication.newInstance().getUser_id());
                                            json.put("content","我们已经是好友了，开始聊天吧！");
                                            Long time=new Date().getTime();
                                            json.put("time", String.valueOf(time));
                                            jsonArray1.put(json);
                                            Message messages=new Message();
                                            messages.what=0;
                                            messages.obj=jsonArray1;
                                  //          Log.v("接受好友Message", String.valueOf(jsonArray1));
                                            coreAppGetChatDataHandler.sendMessage(messages);





                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        socket.connect();
                        Log.v("createNewThreaded","ok");
                    } catch (URISyntaxException e) {
                        Log.v("createPopupWindow","ok");
                        e.printStackTrace();
                    }
                    Looper.prepare();
                    sendChatHandler=new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            switch (msg.what){
                                case 0:{
                                    MessageHistoryBean messageHistoryBean= (MessageHistoryBean) msg.obj;
                                    Log.v("getSendWork",messageHistoryBean.getContent());
                                    Log.v("getSendWork",messageHistoryBean.getOwnId());
                                    Log.v("getSendWork", user_id);
                                    HashMap<String,String> data=new HashMap<String, String>();
                                    Long time=messageHistoryBean.getTime().getTime();
                                    data.put("time", String.valueOf(time));
                                    data.put("targetId", messageHistoryBean.getTargetId());
                                    data.put("content",messageHistoryBean.getContent());
                                    data.put("ownId",messageHistoryBean.getOwnId());
                                    JSONObject params = new JSONObject(data);
                                    socket.emit("message",params);
                                    break;
                                }
                                //账户退出动作：删除本地存储的sessionid,关闭socket连接，退出界面
                                case 1:{
                                    Log.v("ofline","ok");
                                    String sessionId =SharedPreferencesUtil.getSessionId(context);
                                    Log.v("ofline",sessionId);
                                    String id ="sess:"+sessionId.split("\\.")[0].substring(4);
                                    HashMap<String,String> data=new HashMap<String, String>();
                                    data.put("sessionId",id);
                                    Log.v("ofline",id);
                                    JSONObject params = new JSONObject(data);
                                    socket.emit("ofline",params);
                                    Log.v("ofline", String.valueOf(params));
                                    Log.v("oflined","ok");
                                    break;
                                }
                                //发送还有添加请求
                                case 2:{
                                    //发送新关系请求
                                    Log.v("CoreService","requestBuildRelationShip");
                                    JsonObject jsonObject= (JsonObject) msg.obj;
                                    socket.emit("requestBuildRelationShip",jsonObject);
                                }
                            }

                        }
                    };
                    Looper.loop();
                }
            });
            thread.start();
        }
    }

    public Handler getSendChatHandler() {
        return sendChatHandler;
    }

    public void setSendChatHandler(Handler sendChatHandler) {
        this.sendChatHandler = sendChatHandler;
    }

    public Handler getGetChatDataHandler() {
        return getChatDataHandler;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setGetChatDataHandler(Handler getChatDataHandler) {
        this.getChatDataHandler = getChatDataHandler;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public Handler getCoreAppGetChatDataHandler() {
        return coreAppGetChatDataHandler;
    }

    public void setCoreAppGetChatDataHandler(Handler coreAppGetChatDataHandler) {
        this.coreAppGetChatDataHandler = coreAppGetChatDataHandler;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }




}
