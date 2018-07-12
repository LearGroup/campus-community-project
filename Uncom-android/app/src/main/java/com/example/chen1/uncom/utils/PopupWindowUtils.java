package com.example.chen1.uncom.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.chen1.uncom.R;

/**
 * Created by chen1 on 2017/10/4.
 */

public class PopupWindowUtils {

    /**
     * 会覆盖appBar
     * @param str
     * @param layout
     * @param width
     * @param height
     * @param timers
     * @param context
     * @param rootView
     * @return
     */
    public static PopupWindow popupWindow(String str,int layout,int width,int height,int timers,Context context,View rootView) {
        View view = LayoutInflater.from(context).inflate(layout, null);
        final PopupWindow menu = new PopupWindow(view, width, height);
        if(str!=null){
            TextView textview = (TextView) view.findViewById(R.id.popup_win_textview);
            textview.setText(str);
            menu.setAnimationStyle(R.style.popwin_anim_style);
        }
        // PopupWindow定义，显示view，以及初始化长和宽
        // 显示在某个位置
        menu.showAtLocation(rootView, Gravity.TOP, 0, 0);
        if(timers==-1){
            return menu;
        }else{
            CountDownTimer timer = new CountDownTimer(1500, 10) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    menu.dismiss();
                }
            };
            timer.start();
        }
        return null;
    }

    /**
     * 会在appBar之下展示
     * @param str
     * @param layout
     * @param width
     * @param height
     * @param timers
     * @param context
     * @param rootView
     * @return
     */
    public static PopupWindow popupWindowNormal(String str,int layout,int width,int height,int timers,Context context,View rootView) {
        View view = LayoutInflater.from(context).inflate(layout, null);
        final PopupWindow menu = new PopupWindow(view, width, height);
        if(str!=null){
            TextView textview = (TextView) view.findViewById(R.id.popup_win_textview);
            textview.setText(str);
            menu.setAnimationStyle(R.style.popwin_anim_style);
        }
        // PopupWindow定义，显示view，以及初始化长和宽
        // 显示在某个位置
        menu.showAtLocation(rootView, Gravity.TOP, 0, 160);
        if(timers==-1){
            return menu;
        }else{
            CountDownTimer timer = new CountDownTimer(1500, 10) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    menu.dismiss();
                }
            };
            timer.start();
        }
        return null;
    }

}
