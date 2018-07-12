package com.example.chen1.uncom.set;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.NewRelationShipBean;
import com.example.chen1.uncom.bean.NewRelationShipBeanDao;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;
import com.example.chen1.uncom.bean.UserBeanDao;
import com.example.chen1.uncom.utils.SharedPreferencesUtil;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by chen1 on 2017/12/26.
 */

public class LoadUtils {
    private Handler handler=new Handler();
    /**
     *从SQLlite数据库中同步数据到UI，由于数据比较多，所以新开线程进行数据同步处理
     * @param status 私聊信息同步状态控制 1 强制做状态同步 0 不强制，只有在frendlist列表为空时同步
     */
    public void syncData(final ArrayList<Object> dataList, final int status, final Context context, final SetPageMainFragment fragment){
        //当personFrendList 数据为空 说明为第一次进入状态或重载状态。开启子线程获取相关信息
       /* if(CoreApplication.newInstance().getPersonFrendList()==null || status==1){
            if(status==1 && CoreApplication.newInstance().getPersonFrendList()!=null){
                personFrendList.clear();
                if(newRelationShipList!=null){
                    newRelationShipList.clear();
                }
                Log.v("清除personFrendList", String.valueOf(personFrendList.size()));
            }

            if(status==1 && CoreApplication.newInstance().getPersonFrendList()!=null){
                activePersonMessageList.clear();
                Log.v("清除activePersonMessageList", String.valueOf(activePersonMessageList.size()));
            }*/
            Log.v("personFrendListNode","none");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //同步新关系请求，联系人，活动联系人
                    RelationShipLevelBeanDao relationShipLevelBeanDao= BeanDaoManager.getInstance().getDaoSession().getRelationShipLevelBeanDao();
                    QueryBuilder queryBuilder=relationShipLevelBeanDao.queryBuilder();
                    Query query=queryBuilder.where(RelationShipLevelBeanDao.Properties.Level.eq(4)).build();
                    query=queryBuilder.where(queryBuilder.and(RelationShipLevelBeanDao.Properties.Level.eq(4),RelationShipLevelBeanDao.Properties.Active.eq(true))).orderDesc(RelationShipLevelBeanDao.Properties.Last_active_time).build();
                    ArrayList<RelationShipLevelBean> list= (ArrayList<RelationShipLevelBean>) query.list();
                    Log.v("CoreApplicationActive", String.valueOf(list.size()));
                    for (int i = 0; i <list.size(); i++) {
                        Log.v("newtheard",list.get(i).getUsername());
                        if(list.get(i).getActive()==true){
                            dataList.add(list.get(i));
                        }
                    }

                   /* Message message1=new Message();
                    message1.what=1;
                    coreAppGetChatDataHandler.sendMessage(message1);*/
                 /*   for (int i=0;i<newRelationShipList.size();i++){
                        Log.v("NewRelationshipSize", String.valueOf(newRelationShipList.get(i)));
                        Message message2=new Message();
                        message2.what=5;
                        message2.obj=newRelationShipList.get(i);
                        coreAppGetChatDataHandler.sendMessage(message2);
                    }*/
                }
            }).start();
        }



}
