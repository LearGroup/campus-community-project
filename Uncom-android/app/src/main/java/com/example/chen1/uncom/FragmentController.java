package com.example.chen1.uncom;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by chen1 on 2017/7/1.
 */

public class FragmentController {
    private static FragmentManager fragmentManager;
    private static Fragment last=null;
    public FragmentController(FragmentManager fmt){
        fragmentManager=fmt;
    }
    public static void addFragment(Fragment fragment, String tag){
        if(!fragment.isAdded()){
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(null).add(fragment,tag).commit();
            last=fragment;
        }
    }

    public static void showFragment(Fragment fragment,Fragment hidefragment){
        if(fragment.equals(last)){
            Log.v("fragment equals", "equals");
          fragmentManager.beginTransaction().addToBackStack(null).hide(hidefragment).show(fragment).commit();
        }else{
            fragmentManager.beginTransaction().addToBackStack(null).hide(last).show(fragment).commit();
            last=fragment;
        }


    }


}
