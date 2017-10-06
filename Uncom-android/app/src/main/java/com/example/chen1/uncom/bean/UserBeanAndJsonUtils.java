package com.example.chen1.uncom.bean;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by chen1 on 2017/10/2.
 */

public class UserBeanAndJsonUtils {

    private static UserBean userBean;



    public static UserBean getUserBean(JSONObject response) throws JSONException {
       userBean=new UserBean(response.getString("id"),response.getString("username"),
               response.getString("password"), response.getString("header_pic"),
               response.getString("province"), response.getString("town"),
               response.getString("area"), response.getString("sprovince"),
               response.getString("stown"), response.getString("sarea"),
               response.getString("email"), response.getString("phone"),
               response.getInt("sex"), response.getString("create_time"),
               response.getString("update_time"), response.getInt("is_delete"),
               response.getString("qq_number"), response.getString("wexin_number"),
               response.getString("weibo"),  response.getString("student_id"),
               response.getString("student_id_password"), response.getInt("age"));
        return userBean;
    }

    public static UserBean getUpdatedUserBean(UserBean userBean , JSONObject response) throws JSONException {
      JSON.parseObject(response.toString(),userBean.getClass());
        return userBean;
    }


    public  static ArrayList<RelationShipLevelBean> getRelationShipLevelBean(JSONArray response) throws JSONException {
        ArrayList<RelationShipLevelBean> list=new ArrayList<>();
        RelationShipLevelBean relationShipLevelBean;
        Log.v("relationship", String.valueOf(response));
        if(response.length()>0){
            for (int i = 0; i <response.length() ; i++) {
                JSONObject item =response.getJSONObject(i);
                Log.v("item", String.valueOf(item));
                relationShipLevelBean=new RelationShipLevelBean();
                relationShipLevelBean=JSON.parseObject(item.toString(),relationShipLevelBean.getClass());
                list.add(relationShipLevelBean);
                Log.v("getRelationShipLevelBeanfor", String.valueOf(relationShipLevelBean.getId()));
            }
            return list;


        }


        return list;
    }




}
