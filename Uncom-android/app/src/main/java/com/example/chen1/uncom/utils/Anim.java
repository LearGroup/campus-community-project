package com.example.chen1.uncom.utils;

import android.animation.AnimatorInflater;
import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.chen1.uncom.R;

/**
 * Created by chen1 on 2017/10/14.
 */

public class Anim {


    public static Animation defaultFragmentAnim(Activity activity, int transit, boolean enter, int nextAnim){
        if(enter){
            //进入动画
            return  AnimationUtils.loadAnimation(activity,R.anim.default_fragment_switch_translate_open);

        }else{
            //退出动画
            return  AnimationUtils.loadAnimation(activity,R.anim.default_fragment_switch_leave_translate);
        }
    }
}
