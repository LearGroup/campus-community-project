package com.example.chen1.uncom.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chen1 on 2017/10/2.
 */

public class UserBeanAndJsonUtils {

    private static UserBean userBean;



    public static UserBean getUserBean(JSONObject response) throws JSONException {
       userBean=new UserBean(response.getString("id"),response.getString("username"),
               response.getString("password"), response.getString("headerPic"),
               response.getString("province"), response.getString("town"),
               response.getString("area"), response.getString("sprovince"),
               response.getString("stown"), response.getString("sarea"),
               response.getString("email"), response.getString("phone"),
               response.getInt("sex"), response.getString("createTime"),
               response.getString("updateTime"), response.getInt("isDelete"),
               response.getString("qqNumber"), response.getString("wexinNumber"),
               response.getString("weibo"),  response.getString("studentId"),
               response.getString("studentIdPassword"), response.getInt("age"));
        return userBean;
    }

    public static UserBean getUpdatedUserBean(UserBean userBean , JSONObject response) throws JSONException {
        userBean.setAge(response.getInt("age"));
        userBean.setArea(response.getString("area"));
        userBean.setCreateTime(response.getString("createTime"));
        userBean.setEmail(response.getString("email"));
        userBean.setHeaderPic(response.getString("headerPic"));
        userBean.setId(response.getString("id"));
        userBean.setIsDelete(response.getInt("isDelete"));
        userBean.setPassword(response.getString("password"));
        userBean.setPhone(response.getString("phone"));
        userBean.setProvince(response.getString("province"));
        userBean.setQqNumber(response.getString("qqNumber"));
        userBean.setSarea(response.getString("sarea"));
        userBean.setSprovince(response.getString("sprovince"));
        userBean.setStown(response.getString("stown"));
        userBean.setTown(response.getString("town"));
        userBean.setWexinNumber(response.getString("wexinNumber"));
        userBean.setWeibo(response.getString("weibo"));
        userBean.setUpdateTime(response.getString("updateTime"));
        userBean.setSex(response.getInt("sex"));
        userBean.setStudentId(response.getString("studentId"));
        userBean.setStudentIdPassword(response.getString("studentIdPassword"));
        userBean.setUsername(response.getString("username"));
        return userBean;
    }
}
