package com.example.chen1.uncom.utils;

import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;

/**
 * Created by chen1 on 2017/10/14.
 */

public class Anim {


    public  Animation defaultFragmentAnim(Context context, int transit, boolean enter, int nextAnim){
        if(enter){
            //进入动画
            return  AnimationUtils.loadAnimation(context,R.anim.default_fragment_switch_translate_open);

        }else{
            //退出动画
            return  AnimationUtils.loadAnimation(context,R.anim.default_fragment_switch_leave_translate);
        }
    }

}
