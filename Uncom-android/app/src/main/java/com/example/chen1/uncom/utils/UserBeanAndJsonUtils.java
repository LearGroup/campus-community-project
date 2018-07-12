package com.example.chen1.uncom.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.chen1.uncom.bean.NewRelationShipBean;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.UserBean;

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
       userBean=new UserBean();
        userBean=JSON.parseObject(response.toString(),userBean.getClass());
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


    public  static ArrayList<NewRelationShipBean> getNewRelationShipBean(JSONArray response) throws JSONException {
        ArrayList<NewRelationShipBean> list=new ArrayList<>();
        NewRelationShipBean newRelationShipBean;
        Log.v("relationship", String.valueOf(response));
        if(response.length()>0){
            for (int i = 0; i <response.length() ; i++) {
                JSONObject item =response.getJSONObject(i);
                Log.v("item", String.valueOf(item));
                newRelationShipBean=new NewRelationShipBean();
                newRelationShipBean=JSON.parseObject(item.toString(),newRelationShipBean.getClass());
                list.add(newRelationShipBean);
                Log.v("getRelationShipLevelBeanfor", String.valueOf(newRelationShipBean.getId()));
            }
            return list;
        }
        return list;
    }


}
