package com.example.chen1.uncom.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chen1 on 2017/10/3.
 */

public class SharedPreferencesUtil {

    public static String getUserId(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String Id =sharedPreferences.getString("user_id","");
        return Id;
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
        SharedPreferences sharedPreferences =context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String sessionId =sharedPreferences.getString("SESSION_COOKIE","null");
        return sessionId;

    }


}
