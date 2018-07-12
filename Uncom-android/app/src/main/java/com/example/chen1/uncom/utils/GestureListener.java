package com.example.chen1.uncom.utils;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;

/**
 * Created by chen1 on 2018/1/22.
 */

public class GestureListener   extends  GestureDetector.SimpleOnGestureListener{
    private int verticalMinistance = 160;            //水平最小识别距离
    private int minVelocity = 10;            //最小识别速度
    private FragmentManager fragmentManager;
    private Runnable runnable;
    public GestureListener(int verticalMinistance, int minVelocity, FragmentManager fragmentManager){
        this.verticalMinistance=verticalMinistance;
        this.minVelocity=minVelocity;
        this.fragmentManager=fragmentManager;
    }


    public void setCallBack(Runnable runnable){
        this.runnable=runnable;
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.v("velocityX", String.valueOf(velocityX));
        if(e1!=null && e2!=null){
            if (e1.getX() - e2.getX() > verticalMinistance && Math.abs(velocityX) > minVelocity) {
                String str="left";
            } else if (e2.getX() - e1.getX() > verticalMinistance && Math.abs(velocityX) > minVelocity) {
                Log.v("moveing","success");
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentManager.popBackStack();
                CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(), R.anim.default_open_right));
                if(runnable!=null){
                    runnable.run();
                }
            } else if (e1.getY() - e2.getY() > verticalMinistance && Math.abs(velocityY) > minVelocity) {

                String str="up";
            } else if (e2.getY() - e1.getY() > verticalMinistance && Math.abs(velocityY) > minVelocity) {
                String str="down";
            }
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }



}
