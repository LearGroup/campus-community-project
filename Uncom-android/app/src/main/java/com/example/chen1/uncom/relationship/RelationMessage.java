package com.example.chen1.uncom.relationship;

/**
 * Created by chen1 on 2018/2/18.
 */

public class RelationMessage {
    private Object message;
    private String STATE_CODE;

    public RelationMessage(Object message,String state_code){
        this.message=message;
        this.STATE_CODE=state_code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getSTATE_CODE() {
        return STATE_CODE;
    }

    public void setSTATE_CODE(String STATE_CODE) {
        this.STATE_CODE = STATE_CODE;
    }
}
