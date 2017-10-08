package com.example.chen1.uncom.chat;

import java.util.Date;

/**
 * Created by chen1 on 2017/9/23.
 */

public class ChatMessgaeContent {
    private  String text;
    private  String time;
    private  String headImageId;
    private  boolean MessageType;//1:own 0:opposite


    public boolean isMessageType() {
        return MessageType;
    }

    public void setMessageType(boolean messageType) {
        MessageType = messageType;
    }

    public ChatMessgaeContent() {


    }

    public ChatMessgaeContent(String text, String time, String headImageId, boolean messageType) {
        this.text = text;
        this.time = time;
        this.headImageId = headImageId;
        MessageType = messageType;//
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public String getHeadImageId() {
        return headImageId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setHeadImageId(String headImageId) {
        this.headImageId = headImageId;
    }
}
