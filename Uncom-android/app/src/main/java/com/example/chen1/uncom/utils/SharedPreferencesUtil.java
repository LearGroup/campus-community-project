package com.example.chen1.uncom.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.chen1.uncom.application.CoreApplication;

import java.util.Date;

/**
 * Created by chen1 on 2017/10/3.
 */

public class SharedPreferencesUtil {

    public static String getUserId(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String Id =sharedPreferences.getString("user_id","");
        Log.v("ShardPreferencesUtilsUser_id",Id);
        return Id;
    }

    public static  void delUsetId(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.remove("user_id");
        editor.commit();
    }
    public static void setUserId(String id,Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("user_id",id);
        editor.commit();
    }

    public static void setSoftInputHeight(int height ,Context context){

        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putInt("soft_input_height",height);
        editor.commit();
    }

    public static int getSoftInputHeight(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        int Id =sharedPreferences.getInt("soft_input_height",0);
        return Id;
    }


    public static String getSessionId(Context context){

        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        String sessionId =sharedPreferences.getString("skey","null");
        return sessionId;

    }

    public static String delSessionId(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.remove("skey");
        editor.commit();
        return "1";
    }


    public static void  setNewRelationActive(Context context,Integer sum){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putInt("newRelationActive",sum);
        editor.commit();
    }



    public static Integer  getNewRelationActive(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        Integer newRelationActive =sharedPreferences.getInt("newRelationActive",0);
        return newRelationActive;
    }

    public static void delNewRelationActive(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putInt("newRelationActive",0);
        editor.commit();
    }

    public static void setUploadDate(Context context,Date date){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putLong("uploadDate",date.getTime());
        editor.commit();
    }

    public static Long getUploadDate(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        Long date =sharedPreferences.getLong("uploadDate",0);
        return date;
    }

    public static void setAk(Context context,String ak){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("ak",ak);
        editor.commit();
    }
    public static String getAk(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        String date =sharedPreferences.getString("ak",null);
        return date;
    }
    public static void setSk(Context context,String sk){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("sk",sk);
        editor.commit();
    }
    public static String getSk(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        String date =sharedPreferences.getString("sk",null);
        return date;
    }
    public static void setToken(Context context,String token){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("token",token);
        editor.commit();
    }
    public static String getToken(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        String date =sharedPreferences.getString("token",null);
        return date;
    }

    public static void setExpiration(Context context,String expiration){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("expiration",expiration);
        editor.commit();
    }
    public static String getExpiration(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        String date =sharedPreferences.getString("expiration",null);
        return date;
    }


    public static  void setDynamicsAction(Context context,int num){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putInt("dynamics_action",num);
        editor.commit();
    }

    public static int getDynamicsAction(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        int date =sharedPreferences.getInt("dynamics_action",0);
        return date;
    }

    public static  void setRelationAction(Context context,int num){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putInt("relation_action",num);
        editor.commit();
    }

    public static int getRelationAction(Context context){
        Log.v("getRelationAction","success");
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        int date =sharedPreferences.getInt("relation_action",0);
        return date;
    }

    public static  void setMeAction(Context context,int num){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putInt("me_action",num);
        editor.commit();
    }

    public static int getMeAction(Context context){
        Log.v("getMeAction","success");
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        int date =sharedPreferences.getInt("me_action",0);
        return date;
    }


    public static  void setSetAction(Context context,int num){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putInt("set_action",num);
        editor.commit();
    }

    public static int getSetAction(Context context){
        Log.v("getSetAction","success");
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        int date =sharedPreferences.getInt("set_action",0);
        return date;
    }


    public static  void setFindAction(Context context,int num){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putInt("find_action",num);
        editor.commit();
    }

    public static int getFindAction(Context context){
        Log.v("getFindAction","success");
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        int date =sharedPreferences.getInt("find_action",0);
        return date;
    }

    public static void setDynamicsState(Context context,boolean state){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putBoolean("dynamics_state",state);
        editor.commit();
    }

    public static boolean getDynamicsState(Context context){
        Log.v("getFindAction","success");
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info"+ CoreApplication.newInstance().getUser_id(), Context.MODE_PRIVATE);
        boolean date =sharedPreferences.getBoolean("dynamics_state",false);
        return date;
    }
}
