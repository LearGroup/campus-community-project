package com.example.chen1.uncom.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 活动Bean
 * Created by chen1 on 2017/11/11.
 */
@Entity
public class RoutineBean {
    @Id(autoincrement = true)
    private  Long id;
    private String  activeId;//活动id
    private Integer count ;//活动需求总人数
    private Integer nowCount ;//当前的活动人数
    private String name;//活动名
    private String nameBackColor;//活动名栏的背景颜色
    private String authorId;//活动创建者id
    private String authorName;//活动创建者名字
    private String authorHeadImg;//活动创建者头像
    private String participantId;//活动参与者Id-list
    private String participantHeadImg;//活动参与者头像-list
    private Date createTime;//活动创建时间
    private String shortReadme;//短的自述消息
    private boolean online;//是否有线上活动
    private boolean ofline;//是否有线下活动
    private String abstractReadme;//简述
    private String tag;//标签 以 , 号为间隔点-list
    private String tagId;//标签Id -list
    private String tagColor; //标签的颜色
    private Integer timeType;//0以天为周期活动 1以周为周期活动 2以月为周期活动 目前支持最长周期为月
    private String activeTime;//活动时间 巧妙的利用0 1 表示活动 不进行 进行
    private float activePercent;//活动进行的程度 百分比
    private Integer quality;//活动质量 百分制
    private String address ;//活动地点
    @Generated(hash = 882248626)
    public RoutineBean(Long id, String activeId, Integer count, Integer nowCount,
            String name, String nameBackColor, String authorId, String authorName,
            String authorHeadImg, String participantId, String participantHeadImg,
            Date createTime, String shortReadme, boolean online, boolean ofline,
            String abstractReadme, String tag, String tagId, String tagColor,
            Integer timeType, String activeTime, float activePercent,
            Integer quality, String address) {
        this.id = id;
        this.activeId = activeId;
        this.count = count;
        this.nowCount = nowCount;
        this.name = name;
        this.nameBackColor = nameBackColor;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorHeadImg = authorHeadImg;
        this.participantId = participantId;
        this.participantHeadImg = participantHeadImg;
        this.createTime = createTime;
        this.shortReadme = shortReadme;
        this.online = online;
        this.ofline = ofline;
        this.abstractReadme = abstractReadme;
        this.tag = tag;
        this.tagId = tagId;
        this.tagColor = tagColor;
        this.timeType = timeType;
        this.activeTime = activeTime;
        this.activePercent = activePercent;
        this.quality = quality;
        this.address = address;
    }
    @Generated(hash = 1157967036)
    public RoutineBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getActiveId() {
        return this.activeId;
    }
    public void setActiveId(String activeId) {
        this.activeId = activeId;
    }
    public Integer getCount() {
        return this.count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public Integer getNowCount() {
        return this.nowCount;
    }
    public void setNowCount(Integer nowCount) {
        this.nowCount = nowCount;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNameBackColor() {
        return this.nameBackColor;
    }
    public void setNameBackColor(String nameBackColor) {
        this.nameBackColor = nameBackColor;
    }
    public String getAuthorId() {
        return this.authorId;
    }
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
    public String getAuthorName() {
        return this.authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getAuthorHeadImg() {
        return this.authorHeadImg;
    }
    public void setAuthorHeadImg(String authorHeadImg) {
        this.authorHeadImg = authorHeadImg;
    }
    public String getParticipantId() {
        return this.participantId;
    }
    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }
    public String getParticipantHeadImg() {
        return this.participantHeadImg;
    }
    public void setParticipantHeadImg(String participantHeadImg) {
        this.participantHeadImg = participantHeadImg;
    }
    public Date getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getShortReadme() {
        return this.shortReadme;
    }
    public void setShortReadme(String shortReadme) {
        this.shortReadme = shortReadme;
    }
    public boolean getOnline() {
        return this.online;
    }
    public void setOnline(boolean online) {
        this.online = online;
    }
    public boolean getOfline() {
        return this.ofline;
    }
    public void setOfline(boolean ofline) {
        this.ofline = ofline;
    }
    public String getAbstractReadme() {
        return this.abstractReadme;
    }
    public void setAbstractReadme(String abstractReadme) {
        this.abstractReadme = abstractReadme;
    }
    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getTagId() {
        return this.tagId;
    }
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
    public String getTagColor() {
        return this.tagColor;
    }
    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }
    public Integer getTimeType() {
        return this.timeType;
    }
    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }
    public String getActiveTime() {
        return this.activeTime;
    }
    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }
    public float getActivePercent() {
        return this.activePercent;
    }
    public void setActivePercent(float activePercent) {
        this.activePercent = activePercent;
    }
    public Integer getQuality() {
        return this.quality;
    }
    public void setQuality(Integer quality) {
        this.quality = quality;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
   


}
