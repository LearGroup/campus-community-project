package com.example.chen1.uncom.me;

import com.example.chen1.uncom.bean.UserBean;

/**
 * Created by chen1 on 2018/2/17.
 */

public class SyncMessage {

    private UserBean userBean;
    public SyncMessage(UserBean userBean){
        this.userBean=userBean;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}
