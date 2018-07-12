package com.example.chen1.uncom.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by chen1 on 2017/12/2.
 */

public class DisplayUtils{


    public static int getWindowWidth(Context context){
        WindowManager wm = (WindowManager)context
                .getSystemService(Context.WINDOW_SERVICE);

        return  wm.getDefaultDisplay().getWidth();

    }

    public static int getWindowHeight(Context context){
        WindowManager wm = (WindowManager)context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }



}
