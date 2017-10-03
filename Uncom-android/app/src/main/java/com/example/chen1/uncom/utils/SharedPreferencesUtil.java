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
}
