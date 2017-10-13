package com.example.chen1.uncom.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

/**
 * Created by chen1 on 2017/10/6.
 */
@Entity
public class MessageHistoryBean implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    private String ownId;//发送者的id
    private String targetId;//发送目标的id
    private String content;
    private Date time;
    private boolean looke ; //true 浏览过 false未浏览
    private  boolean MessageType;//1:own 0:opposite

    protected MessageHistoryBean(Parcel in) {
        ownId = in.readString();
        targetId = in.readString();
        content = in.readString();
        looke = in.readByte() != 0;
        MessageType = in.readByte() != 0;
    }

    @Generated(hash = 1393772422)
    public MessageHistoryBean(Long id, String ownId, String targetId, String content, Date time,
            boolean looke, boolean MessageType) {
        this.id = id;
        this.ownId = ownId;
        this.targetId = targetId;
        this.content = content;
        this.time = time;
        this.looke = looke;
        this.MessageType = MessageType;
    }

    @Generated(hash = 830674591)
    public MessageHistoryBean() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ownId);
        dest.writeString(targetId);
        dest.writeString(content);
        dest.writeByte((byte) (looke ? 1 : 0));
        dest.writeByte((byte) (MessageType ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnId() {
        return this.ownId;
    }

    public void setOwnId(String ownId) {
        this.ownId = ownId;
    }

    public String getTargetId() {
        return this.targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean getLooke() {
        return this.looke;
    }

    public void setLooke(boolean looke) {
        this.looke = looke;
    }

    public boolean getMessageType() {
        return this.MessageType;
    }

    public void setMessageType(boolean MessageType) {
        this.MessageType = MessageType;
    }

    public static final Creator<MessageHistoryBean> CREATOR = new Creator<MessageHistoryBean>() {
        @Override
        public MessageHistoryBean createFromParcel(Parcel in) {
            return new MessageHistoryBean(in);
        }

        @Override
        public MessageHistoryBean[] newArray(int size) {
            return new MessageHistoryBean[size];
        }
    };
}
