package com.example.chen1.uncom.find;

/**
 *
 * 发现界面的消息类
 * Created by chen1 on 2018/2/22.
 */

public class FindMessage {
    private Object object;
    private String STATE_CODE;

    public FindMessage(Object object,String state_code){
        this.object=object;
        this.STATE_CODE=state_code;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getSTATE_CODE() {
        return STATE_CODE;
    }

    public void setSTATE_CODE(String STATE_CODE) {
        this.STATE_CODE = STATE_CODE;
    }
}
