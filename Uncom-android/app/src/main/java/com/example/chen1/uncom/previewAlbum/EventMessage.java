package com.example.chen1.uncom.previewAlbum;

import java.util.ArrayList;

/**
 * Created by chen1 on 2018/1/29.
 */

public class EventMessage {

    public ArrayList<String> list;

    public EventMessage(ArrayList<String> data){
        this.list=data;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
}
