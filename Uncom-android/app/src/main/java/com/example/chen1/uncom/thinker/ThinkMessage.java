package com.example.chen1.uncom.thinker;

/**
 * Created by chen1 on 2018/3/21.
 */

public class ThinkMessage {
    private Object object;
    private String state;

    public ThinkMessage(Object object,String state){
        this.object=object;
        this.state=state;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
