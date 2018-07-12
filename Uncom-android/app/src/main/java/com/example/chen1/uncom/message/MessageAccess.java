package com.example.chen1.uncom.message;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.MessageHistoryBean;
import com.example.chen1.uncom.bean.MessageHistoryBeanDao;
import com.example.chen1.uncom.bean.PersonDynamicsBean;
import com.example.chen1.uncom.bean.PersonDynamicsBeanDao;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;
import com.example.chen1.uncom.chat.ChatMessage;
import com.example.chen1.uncom.find.FindMessage;
import com.example.chen1.uncom.main.Notification;
import com.example.chen1.uncom.relationDynamics.DynamicsMessage;
import com.example.chen1.uncom.relationship.RelationMessage;
import com.example.chen1.uncom.set.SetMessage;
import com.example.chen1.uncom.utils.BadgeMessageUtil;
import com.example.chen1.uncom.utils.StateCode;
import com.example.chen1.uncom.utils.SyncPersonInfoUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by chen1 on 2018/2/16.
 */

public class MessageAccess implements Access{

    private MessageHistoryBean messageHistoryBean;
    private RelationShipLevelBean relationShipLevelBean;
    private RelationShipLevelBeanDao relationShipLevelBeanDao;
    private MessageHistoryBeanDao messageHistoryBeanDao;
    private PersonDynamicsBeanDao personDynamicsBeanDao;
    public MessageAccess(){
        personDynamicsBeanDao=BeanDaoManager.getInstance().getDaoSession().getPersonDynamicsBeanDao();
        relationShipLevelBeanDao = BeanDaoManager.getInstance().getDaoSession().getRelationShipLevelBeanDao();
        messageHistoryBeanDao=BeanDaoManager.getInstance().getDaoSession().getMessageHistoryBeanDao();
    }

    @Override
    public void parseMessage(Handler handler, JSONArray jsonArray) throws JSONException, ParseException {
        for (int i = 0; i <jsonArray.length() ; i++) {
            JSONObject object=jsonArray.getJSONObject(i);
            Log.v("mseeageObject", String.valueOf(object));
            if(object.getString("type").equals(StateCode.PERSON_CHAT_MESSAGE)){
                receivedMessageForPersonChat(handler,object);
            }else if(object.getString("type").equals(StateCode.PERSON_INFO_MESSAGE)){
                receivedMessageForPersonInfo(handler,object);
            }else if(object.getString("type").equals(StateCode.PERSON_DYNAMICS)){
                receivedMessageForPersonDynamics(handler,object);
            }
        }
    }

    @Override
    public void receivedMessageForPersonChat(Handler handler, JSONObject object) throws JSONException, ParseException {
        //MessageHistoryBean item2= new MessageHistoryBean(frendData.getId(),str,new Date().toString(),true);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=  object.getString("time");
        str=str.replaceAll( "\\\\",  "");
        str=str.replaceAll("\"","");
        String d = format.format(Long.parseLong(str));
        Date date=format.parse(d);
        Log.v("time", String.valueOf(date));
        messageHistoryBean=new MessageHistoryBean(null,object.getString("ownId"),CoreApplication.newInstance().getUserBean().getId(),object.getString("content"),date,true,false);
        relationShipLevelBean= getRelationShipBean(messageHistoryBean.getOwnId(),relationShipLevelBeanDao);
        if(relationShipLevelBean!=null) {
            relationShipLevelBean.setLast_message(messageHistoryBean.getContent());
            Log.v("mseeage", messageHistoryBean.getContent());
            relationShipLevelBean.setLast_active_time(messageHistoryBean.getTime());
            if (relationShipLevelBean.getActive() == false) {
                relationShipLevelBean.setActive(true);
            }
            Integer temp = relationShipLevelBean.getUn_look();
            Log.v("sendTmper", "null:" + String.valueOf(temp));
            if (temp != null) {
                relationShipLevelBean.setUn_look(temp + 1);
            } else {
                relationShipLevelBean.setUn_look(1);
            }
            CoreApplication.newInstance().unLook = 1;
            // updateActivePersonMessageList(relationShipLevelBean,1);
            relationShipLevelBeanDao.update(relationShipLevelBean);
            messageHistoryBeanDao.insert(messageHistoryBean);
            if(CoreApplication.newInstance().inApp==false){
                if(CoreApplication.newInstance().notification==null){
                    CoreApplication.newInstance().notification=new Notification();
                }
                CoreApplication.newInstance().notification.sendNotificationForPersonChat(CoreApplication.newInstance().getApplicationContext(),relationShipLevelBean.getUsername(),messageHistoryBean.getContent(),messageHistoryBean.getOwnId());
            }
        }

        EventBus.getDefault().post(new ChatMessage(messageHistoryBean,StateCode.PERSON_CHAT_MESSAGE));
        EventBus.getDefault().post(new SetMessage(relationShipLevelBean,StateCode.RELATION_LEVEL_BEAN));
        Message message=new Message();
        message.what=0;
        message.obj=object;
        handler.sendMessage(message);
    }

    @Override
    public void receivedMessageForGroupChat(Handler handler, JSONObject object) {

    }

    @Override
    public void receivedMessageForPersonInfo(Handler handler, JSONObject object) throws JSONException {
        relationShipLevelBean=getRelationShipBean(object.getString("user_id"),relationShipLevelBeanDao);
        if(relationShipLevelBean!=null){
            relationShipLevelBean.setUsername(object.getString("username"));
            relationShipLevelBean.setSelf_abstract(object.getString("self_abstract"));
            relationShipLevelBean.setSex(object.getInt("sex"));
            relationShipLevelBean.setEmail(object.getString("email"));
            relationShipLevelBean.setCollege(object.getString("college"));
            relationShipLevelBean.setUniversity(object.getString("university"));
            relationShipLevelBean.setHeader_pic(object.getString("header_pic"));
            relationShipLevelBeanDao.update(relationShipLevelBean);
            new SyncPersonInfoUtils().syncPersonInfo(relationShipLevelBean);
            EventBus.getDefault().post(new RelationMessage(relationShipLevelBean,StateCode.PERSON_INFO_MESSAGE));

        }
    }

    @Override
    public void receivedMessageForPersonDynamics(Handler handler, JSONObject jsonObject) throws JSONException {
        PersonDynamicsBean bean;
        if(jsonObject.getString("type").equals(StateCode.PERSON_DYNAMICS)){
            bean= JSON.parseObject(jsonObject.toString(),PersonDynamicsBean.class);
            personDynamicsBeanDao.insert(bean);
            BadgeMessageUtil.setDynamics_state(true);
            EventBus.getDefault().post(new FindMessage(bean,StateCode.PERSON_DYNAMICS_ADD));
            EventBus.getDefault().post(new DynamicsMessage(bean,StateCode.PERSON_DYNAMICS_ADD));
        }else{
            bean=getPersonDynamicsBean(jsonObject.getString("id"));
        }
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

    public PersonDynamicsBean getPersonDynamicsBean(String id){
        PersonDynamicsBean bean=personDynamicsBeanDao.queryBuilder().where(PersonDynamicsBeanDao.Properties.Id.eq(id)).unique();
        return bean;
    }
}
