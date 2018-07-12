package com.example.chen1.uncom.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 动态消息的动作实体类
 * 例如 点赞 评论
 * Created by chen1 on 2018/2/22.
 */

@Entity
public class DynamicsMessageBean {

    @Id(autoincrement = true)
    private Long id;

    private boolean like;
    private String  dynnamics_id;
    private String  comment;
    private String user_id;
    private String user_name;
    private String header_pic;
    @Generated(hash = 770867743)
    public DynamicsMessageBean(Long id, boolean like, String dynnamics_id,
            String comment, String user_id, String user_name, String header_pic) {
        this.id = id;
        this.like = like;
        this.dynnamics_id = dynnamics_id;
        this.comment = comment;
        this.user_id = user_id;
        this.user_name = user_name;
        this.header_pic = header_pic;
    }
    @Generated(hash = 1498889438)
    public DynamicsMessageBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean getLike() {
        return this.like;
    }
    public void setLike(boolean like) {
        this.like = like;
    }
    public String getDynnamics_id() {
        return this.dynnamics_id;
    }
    public void setDynnamics_id(String dynnamics_id) {
        this.dynnamics_id = dynnamics_id;
    }
    public String getComment() {
        return this.comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getUser_id() {
        return this.user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUser_name() {
        return this.user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getHeader_pic() {
        return this.header_pic;
    }
    public void setHeader_pic(String header_pic) {
        this.header_pic = header_pic;
    }



}
