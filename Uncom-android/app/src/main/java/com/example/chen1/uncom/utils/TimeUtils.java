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

    private static  String[]date={"六","日","一","二","三","四","五"};


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
        String temp = format.format(messageTime);
        String temp2=format.format(currentTime);
        Log.v("temp",temp);
        Log.v("temp2",temp2);
        Log.v("temp3", String.valueOf(messageTime.getTime()));
        Log.v("temp4", String.valueOf(currentTime.getTime()));
        String temp1[] = temp.split("-");
        Log.v("time...............", String.valueOf(((currentTime.getTime()-messageTime.getTime())/1000)));
        if (( currentTime.getTime()-messageTime.getTime() ) / 1000 > 604800) {
            //若时间超出了一个星期，则显示该消息发送的日期+具体时间
            String month = temp1[1];
            String day = temp1[2].split(" ")[0];
            String hour = temp1[2].split(" ")[1].split(":")[0] + temp1[2].split(" ")[1].split(":")[1];
            results = month + "-" + day + " " + temp1[2].split(" ")[1].split(":")[0] + ":" + temp1[2].split(" ")[1].split(":")[1];
            return results;
            //时间超出了一天 但小于一星期
        } else if (((currentTime.getTime()-messageTime.getTime()) / 1000 > 86400) && ((currentTime.getTime()-messageTime.getTime() ) / 1000 < 604800)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentTime);
            String week = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK) - 1);
            String hour = temp1[2].split(" ")[1].split(":")[0] +":"+ temp1[2].split(" ")[1].split(":")[1];
            results = "星期"+date[calendar.get(Calendar.DAY_OF_WEEK)-1]+ " " + hour;
            //时间超出了一小时 但小于二天
        }else if(((currentTime.getTime()-messageTime.getTime()) / 1000 > 3600)&&((currentTime.getTime()-messageTime.getTime()) / 1000 < 86400) ){

            String hour = temp1[2].split(" ")[1].split(":")[0] +":"+ temp1[2].split(" ")[1].split(":")[1];
            results="昨天 "+hourParse(hour)+hour;
        }
        else {

            //时间超出了一小时 但小于一天
            String hour = temp1[2].split(" ")[1].split(":")[0] +":"+ temp1[2].split(" ")[1].split(":")[1];
            Log.v("time", hour);
            return hourParse(hour)+hour;
        }
        return results;
    }


    public static String  hourParse(String hour){
        String hour_s=hour.split(":")[0];
        String result = null;
        Integer hour_i=Integer.parseInt(hour_s);
        Log.v("parseTime", String.valueOf(hour_i));
        if(hour_i<=12){
            Log.v("parsetime","check_one");
            if(hour_i<3){
                result="凌晨";
            }else if(hour_i>3){
                result="上午";
            }
        }else{
            if(hour_i<23){
                result="下午";
            }else{
                result="午夜";
            }
        }

        return result;
    }
}


