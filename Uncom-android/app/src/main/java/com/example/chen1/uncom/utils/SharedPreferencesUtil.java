package com.example.chen1.uncom.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

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

        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putInt("soft_input_height",height);
        editor.commit();
    }

    public static int getSoftInputHeight(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        int Id =sharedPreferences.getInt("soft_input_height",0);
        return Id;
    }


    public static String getSessionId(Context context){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String sessionId =sharedPreferences.getString("skey","null");
        return sessionId;

    }

    public static String delSessionId(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.remove("skey");
        editor.commit();
        return "1";
    }


    public static void  setNewRelationActive(Context context,Integer sum){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putInt("newRelationActive",sum);
        editor.commit();
    }



    public static Integer  getNewRelationActive(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Integer newRelationActive =sharedPreferences.getInt("newRelationActive",0);
        return newRelationActive;
    }



}
