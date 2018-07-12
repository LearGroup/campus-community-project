package com.example.chen1.uncom.main;

/**
 * Created by chen1 on 2018/2/28.
 */

public class MainMessage {
    Object object;
    int status;
    public MainMessage(Object object,int state){
        this.object=object;
        this.status=state;
    }


    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
