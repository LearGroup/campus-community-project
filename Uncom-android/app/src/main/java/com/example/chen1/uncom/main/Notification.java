package com.example.chen1.uncom.main;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.utils.StateCode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chen1 on 2018/2/28.
 */

public class Notification {
    private int notifiCount=0;
    private int notifiUser=0;
    private String usernames="";
    public void sendNotificationForPersonChat(Context context,String username,String content,String user_id) {
        String contents;
        String users;
        if(notifiCount!=0){
            int count=notifiCount+1;
            contents="给你发了"+count+"条消息。";
        }else{
            contents=content;
        }
        notifiCount+=1;
        if(notifiUser>1){
            users=usernames+"等"+notifiUser+"个人";
        }else{
            users=username;
        }
        if(usernames.equals(username)==false){
            notifiUser+=1;
            if(usernames.length()>=12 && usernames.length()<=15){
                usernames+="...";
            }
            if(usernames.length()<12){
                usernames+=username;
            }
        }else{
            if(notifiUser==0){
                notifiUser+=1;
            }
        }
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.putExtra("id",user_id);
        Log.v("notifiUser", String.valueOf(notifiUser));
        if(notifiUser>1){
            mainIntent.putExtra("type", StateCode.SET_FRAGMENT);
            Log.v("sendNotificationForPersonChat","set_fragment_page");
        }else{
            mainIntent.putExtra("type",StateCode.PERSON_CHAT_PAGE);
            Log.v("sendNotificationForPersonChat","person_chat_page");
        }
        PendingIntent pendingIntent=PendingIntent.getActivity(context, 12,mainIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        //   RemoteViews remoteViews=new RemoteViews(getActivity().getPackageName(),R.layout.notification_person_chat);
        //获取NotificationManager实例
        Log.v("packegeName",context.getPackageName());



        NotificationManager notifyManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化NotificationCompat.Builde并设置相关属性
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.saturnplanets)
                //设置小图标
                .setContentTitle(users)
                .setContentText(contents);
        //设置通知标题;
        //设置通知时间，默认为系统发出通知的时间，通常不用设置
        //.setWhen(System.currentTimeMillis());
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        notifyManager.notify(1, builder.build());
    }


    public void clean(){
          notifiCount=0;
          notifiUser=0;
          usernames="";
    }
}
