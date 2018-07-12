package com.example.chen1.uncom.utils;

import com.example.chen1.uncom.bean.MessageHistoryBean;

import java.util.ArrayList;

/**
 * Created by chen1 on 2018/1/26.
 */

public class MessageEvent {

    private ArrayList<String> list;

    public MessageEvent(ArrayList<String> list){
        this.list=list;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
}
