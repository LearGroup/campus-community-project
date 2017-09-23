package com.example.chen1.uncom;

import java.util.Date;

/**
 * Created by chen1 on 2017/9/23.
 */

public class ChatMessgaeContent {
    private  String text;
    private  Date time;
    private  int headImageId;
    private  boolean MessageType;//1:own 0:opposite


    public boolean isMessageType() {
        return MessageType;
    }

    public void setMessageType(boolean messageType) {
        MessageType = messageType;
    }

    public ChatMessgaeContent() {


    }

    public ChatMessgaeContent(String text, Date time, int headImageId, boolean messageType) {
        this.text = text;
        this.time = time;
        this.headImageId = headImageId;
        MessageType = messageType;
    }

    public String getText() {
        return text;
    }

    public Date getTime() {
        return time;
    }

    public int getHeadImageId() {
        return headImageId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setHeadImageId(int headImageId) {
        this.headImageId = headImageId;
    }
}
