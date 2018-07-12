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

    private   String[]date={"日","一","二","三","四","五","六"};


    public  String compareTime(Date currentTime, Date messageTime) {



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


    public  String compareTimeChatDisplay(Date currentTime, Date messageTime) {
        String results="";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = format.format(messageTime);
        String temp2=format.format(currentTime);
        String temp1[] = temp.split("-");
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
            calendar.setTime(messageTime);
            String week = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK) - 1);
            String hour = temp1[2].split(" ")[1].split(":")[0] +":"+ temp1[2].split(" ")[1].split(":")[1];
            results = "星期"+date[calendar.get(Calendar.DAY_OF_WEEK)-1]+ " " + hourParse(hour);
            //时间超出了一小时 但小于二天
        }else if(((currentTime.getTime()-messageTime.getTime()) / 1000 > 3600)&&((currentTime.getTime()-messageTime.getTime()) / 1000 < 86400) ){
            String hour = temp1[2].split(" ")[1].split(":")[0] +":"+ temp1[2].split(" ")[1].split(":")[1];
            results="昨天 "+hourParse(hour);
        }
        else {
            //时间超出了一小时 但小于一天
            String hour = temp1[2].split(" ")[1].split(":")[0] +":"+ temp1[2].split(" ")[1].split(":")[1];
            results= hourParse(hour);
        }
        return results;
    }


    /**
     * 检查是否超过一天
     * @param now 当前时间
     * @param last_time 上一个时间
     * @return
     */
    public boolean checkOutDay(Date now ,Date last_time){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String temp = format.format(now);
        String temp2=format.format(last_time);
        String temp_[] = temp.split("-");
        String temp_2[]=temp2.split("-");
        Log.v("time",temp);
        if(Integer.parseInt(temp_2[0])>Integer.parseInt(temp_[0])){
            return  true;
        }
        if(Integer.parseInt(temp_2[1])>Integer.parseInt(temp_[1])){
            return  true;
        }
        if(Integer.parseInt(temp_2[2])>Integer.parseInt(temp_[2])){
            return  true;
        }
        return  false;
    }


    /**
     *
     * @param currentTime
     * @param messageTime
     * @return
     */
    public String compareTimeDynamicDisplay(Date currentTime, Date messageTime){
        String result="";
        long diff = currentTime.getTime() - messageTime.getTime();
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format_hourse=new SimpleDateFormat("HH:mm");
        String now[] = format.format(currentTime).split("-");
        String message[]=format.format(messageTime).split("-");
        if(Integer.parseInt(now[0])>Integer.parseInt(message[0])){
            result=message[0]+"年"+message[1]+"月"+message[2]+"日";
        }else if(day>7 && day>3){{
            result=message[1]+"月"+message[2]+"日";
        }
        }else if(day>1 && day<=3){
            result=day+"天前";
        }else if(hour>1){
            result=hour+"小时前";
        }else{
            if(min==0){
                result="刚刚";
            }else{
                result=min+"分钟前";
            }
        }
        return result;
    }

    public String getDayAndMonth(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String temp = format.format(date);
        String temp_[] = temp.split("-");
        String str="";
        str+=Integer.parseInt(temp_[1])+"月";
        str+=Integer.parseInt(temp_[2])+"日";
        return str;
    }


    public  String  hourParse(String hour){
        String hour_s=hour.split(":")[0];
        String result = "";
        int hourt;
        Integer hour_i=Integer.parseInt(hour_s);
        if(hour_i<=12){
            if(hour_i<3){
                result="凌晨";
            }else if(hour_i>3){
                result="上午";
            }
            result=result+hour;
        }else{
            hourt=hour_i-12;
            if(hour_i<23){
                result="下午";
            }else{
                result="午夜";
            }
            result=result+hourt+":"+hour.split(":")[1];
        }

        return result;
    }


    public   String  getTime(Date currentTime){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String temp = format.format(currentTime);
        String temp1[] = temp.split("-");
        String hour = temp1[2].split(" ")[1].split(":")[0] +":"+ temp1[2].split(" ")[1].split(":")[1];
        return  hourParse(hour);
    }
}


