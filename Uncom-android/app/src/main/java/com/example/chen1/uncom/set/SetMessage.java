package com.example.chen1.uncom.set;

/**
 * Created by chen1 on 2018/2/17.
 */

public class SetMessage {
    public Object object;
    public String STATE_CODE;
    public int thinker_state;
    public SetMessage(Object object,String state_code){
        this.object=object;
        STATE_CODE=state_code;
    }

    public SetMessage(Object object,String state_code,int thinker_state){
        this.object=object;
        STATE_CODE=state_code;
        this.thinker_state=thinker_state;
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

    public int getThinker_state() {
        return thinker_state;
    }

    public void setThinker_state(int thinker_state) {
        this.thinker_state = thinker_state;
    }
}
