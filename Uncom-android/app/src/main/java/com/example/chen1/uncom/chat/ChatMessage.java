package com.example.chen1.uncom.chat;

import com.example.chen1.uncom.bean.MessageHistoryBean;
import com.example.chen1.uncom.expression.ChatExpressionStandardFragment;

/**
 * Created by chen1 on 2018/2/17.
 */

public class ChatMessage {
    private Object messageHistoryBean;
    private String type;
    public ChatMessage(Object bean,String type){
        this.type=type;
        this.messageHistoryBean=bean;
    }
    public Object getMessageHistoryBean() {
        return messageHistoryBean;
    }

    public void setMessageHistoryBean(Object messageHistoryBean) {
        this.messageHistoryBean = messageHistoryBean;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
