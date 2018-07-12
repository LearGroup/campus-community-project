package com.example.chen1.uncom.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 随笔记录Bean
 * Created by chen1 on 2017/12/3.
 */

@Entity
public class ThinkerBean {
    @Id(autoincrement = true)
    private Long Id;
    private String imgCacheUrl;//List 本地缓存地址
    private String imgOnlineUrl;//List 网络地址
    private String imgUrl;//List 图片地址
    private String content;
    private String authorId;
    private String title;
    private Date createTime;//创建时间
    private Date changeTime;//修改时间
    private String addr;//地址
    private String backColor;
    private boolean toMain;//置入主页
    private Date toMainTime;//置入主页的时间
    private boolean toTop;//主页置顶
    private String tag;//List 标签
    @Generated(hash = 1838387096)
    public ThinkerBean(Long Id, String imgCacheUrl, String imgOnlineUrl,
            String imgUrl, String content, String authorId, String title,
            Date createTime, Date changeTime, String addr, String backColor,
            boolean toMain, Date toMainTime, boolean toTop, String tag) {
        this.Id = Id;
        this.imgCacheUrl = imgCacheUrl;
        this.imgOnlineUrl = imgOnlineUrl;
        this.imgUrl = imgUrl;
        this.content = content;
        this.authorId = authorId;
        this.title = title;
        this.createTime = createTime;
        this.changeTime = changeTime;
        this.addr = addr;
        this.backColor = backColor;
        this.toMain = toMain;
        this.toMainTime = toMainTime;
        this.toTop = toTop;
        this.tag = tag;
    }
    @Generated(hash = 281500786)
    public ThinkerBean() {
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public String getImgCacheUrl() {
        return this.imgCacheUrl;
    }
    public void setImgCacheUrl(String imgCacheUrl) {
        this.imgCacheUrl = imgCacheUrl;
    }
    public String getImgOnlineUrl() {
        return this.imgOnlineUrl;
    }
    public void setImgOnlineUrl(String imgOnlineUrl) {
        this.imgOnlineUrl = imgOnlineUrl;
    }
    public String getImgUrl() {
        return this.imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getAuthorId() {
        return this.authorId;
    }
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getChangeTime() {
        return this.changeTime;
    }
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    public String getAddr() {
        return this.addr;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }
    public String getBackColor() {
        return this.backColor;
    }
    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }
    public boolean getToMain() {
        return this.toMain;
    }
    public void setToMain(boolean toMain) {
        this.toMain = toMain;
    }
    public Date getToMainTime() {
        return this.toMainTime;
    }
    public void setToMainTime(Date toMainTime) {
        this.toMainTime = toMainTime;
    }
    public boolean getToTop() {
        return this.toTop;
    }
    public void setToTop(boolean toTop) {
        this.toTop = toTop;
    }
    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

  

}
