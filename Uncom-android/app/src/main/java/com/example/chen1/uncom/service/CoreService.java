package com.example.chen1.uncom.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
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
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;
import com.example.chen1.uncom.bean.UserBean;
import com.example.chen1.uncom.bean.UserBeanDao;
import com.example.chen1.uncom.utils.CoreServiceCallBack;
import com.example.chen1.uncom.utils.PopupWindowUtils;
import com.example.chen1.uncom.utils.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private String user_id;
    private Thread thread;
    private Context context;
    private UserBean userBean;
    private JSONObject loginData;
    private Handler sendChatHandler;
    private MessageHistoryBeanDao messageHistoryBeanDao;
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
        HashMap<String ,String > login=new HashMap<>();
        login.put("userName",userBean.getUsername());
        login.put("userId",userBean.getId());
        loginData=new JSONObject(login);
        Log.v("createCoreService","ok");
        if(thread==null){
         thread= new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.v("createNewThread","ok");
                        socket= IO.socket("http://10.0.2.2:8081");
                        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                Log.v("CONNECT","ok");
                                socket.emit("login",loginData);
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
                            }
                        }).on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                Log.v("EVENT_RECONNECT","ok");
                                if(handler!=null){
                                    Message message=new Message();
                                    message.what=0;
                                    handler.sendMessage(message);
                                }
                            }
                        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                Log.v("EVENT_RECONNECT","ok");

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

                            }
                        }).on("message", new Emitter.Listener() {
                            //接收信息
                            @Override
                            public void call(Object... args) {
                                Log.v("chatresponse", String.valueOf(args[0]));
                                JSONObject object=(JSONObject) args[0];
                                Log.v("chatresponse", String.valueOf(object));
                                if(getChatDataHandler!=null){

                                    Message message=new Message();
                                    message.what=0;
                                    message.obj=object;
                                    getChatDataHandler.sendMessage(message);
                                }else{
                                        Message message=new Message();
                                        message.what=0;
                                        message.obj=object;
                                        coreAppGetChatDataHandler.sendMessage(message);
                                        //CoreApplication.newInstance().updateActivePersonMessageList(relationShipLevelBean);

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
