package com.example.chen1.uncom.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.value;

/**
 * Created by chen1 on 2017/10/12.
 */

public class TimeUtils {

    public static String compareTime(Date currentTime, Date messageTime) {
        long realTime = (currentTime.getTime() - messageTime.getTime()) / 1000;
        if (realTime < 10) {
            return "刚刚";
        } else if (realTime <= 59) {
            return "" + realTime + "秒前";
        } else if (realTime < 3600) {
            return realTime / 60 + "分钟前";
        } else if (realTime < 86400) {
            return realTime / 3600 + "小时前";
        } else if (realTime < 604800) {
            return realTime / 86400 + "天前";
        } else if (realTime < 2419200) {
            return realTime / 604800 + "个星期前";
        }
        return "2和月前";
    }


    public static String compareTimeChatDisplay(Date currentTime, Date messageTime) {
        String results;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = format.format(currentTime);
        Log.v("temp",temp);
        String temp1[] = temp.split("-");
        if ((messageTime.getTime() - currentTime.getTime()) / 1000 > 604800000) {
            //若时间超出了一个星期，则显示该消息发送的日期+具体时间
            String month = temp1[1];
            String day = temp1[2].split(" ")[0];
            String hour = temp1[2].split(" ")[1].split(":")[0] + temp1[2].split(" ")[1].split(":")[1];
            results = month + "-" + day + " " + temp1[2].split(" ")[1].split(":")[0] + ":" + temp1[2].split(" ")[1].split(":")[1];
            return results;
            //时间超出了一天 但小于一星期
        } else if (((messageTime.getTime() - currentTime.getTime()) / 1000 > 86400000) && ((messageTime.getTime() - currentTime.getTime()) / 1000 < 604800000)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentTime);
            String week = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK) - 1);
            String hour = temp1[2].split(" ")[1].split(":")[0] + temp1[2].split(" ")[1].split(":")[1];
            results = week + " " + hour;

            //时间超出了一小时 但小于一天
        } else {

            String hour = temp1[2].split(" ")[1].split(":")[0] +":"+ temp1[2].split(" ")[1].split(":")[1];
            Log.v("time", hour);
            return hour;
        }
        return results;
    }
}


