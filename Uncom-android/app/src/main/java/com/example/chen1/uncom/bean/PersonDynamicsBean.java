package com.example.chen1.uncom.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 个人 动态Bean
 * Created by chen1 on 2018/1/25.
 */
@Entity
public class PersonDynamicsBean implements Parcelable {
    @Id(autoincrement = false)
    private String id;
    private String user_id;
    private String username;
    private String user_photo;
    private String content;
    private String photo_list;//list类型
    private String photo_online_list;//list 类型
    private Date  create_time;
    private Integer like_count;
    private Integer comment_count;
    private Integer edit;//0 非编辑状态 1 编辑状态 2发送失败状态 3 正在发送状态 4已发布 5 图片已上传成功 动态未能上传
    private String  comment;//评论 json
    private String  like ;//赞 json
    @Generated(hash = 1254075029)
    public PersonDynamicsBean(String id, String user_id, String username,
            String user_photo, String content, String photo_list,
            String photo_online_list, Date create_time, Integer like_count,
            Integer comment_count, Integer edit, String comment, String like) {
        this.id = id;
        this.user_id = user_id;
        this.username = username;
        this.user_photo = user_photo;
        this.content = content;
        this.photo_list = photo_list;
        this.photo_online_list = photo_online_list;
        this.create_time = create_time;
        this.like_count = like_count;
        this.comment_count = comment_count;
        this.edit = edit;
        this.comment = comment;
        this.like = like;
    }
    @Generated(hash = 192359908)
    public PersonDynamicsBean() {
    }

    protected PersonDynamicsBean(Parcel in) {
        id = in.readString();
        user_id = in.readString();
        username = in.readString();
        user_photo = in.readString();
        content = in.readString();
        photo_list = in.readString();
        photo_online_list = in.readString();
        if (in.readByte() == 0) {
            like_count = null;
        } else {
            like_count = in.readInt();
        }
        if (in.readByte() == 0) {
            comment_count = null;
        } else {
            comment_count = in.readInt();
        }
        if (in.readByte() == 0) {
            edit = null;
        } else {
            edit = in.readInt();
        }
        comment = in.readString();
        like = in.readString();
    }

    public static final Creator<PersonDynamicsBean> CREATOR = new Creator<PersonDynamicsBean>() {
        @Override
        public PersonDynamicsBean createFromParcel(Parcel in) {
            return new PersonDynamicsBean(in);
        }

        @Override
        public PersonDynamicsBean[] newArray(int size) {
            return new PersonDynamicsBean[size];
        }
    };

    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUser_id() {
        return this.user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUser_photo() {
        return this.user_photo;
    }
    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getPhoto_list() {
        return this.photo_list;
    }
    public void setPhoto_list(String photo_list) {
        this.photo_list = photo_list;
    }
    public String getPhoto_online_list() {
        return this.photo_online_list;
    }
    public void setPhoto_online_list(String photo_online_list) {
        this.photo_online_list = photo_online_list;
    }
    public Date getCreate_time() {
        return this.create_time;
    }
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
    public Integer getLike_count() {
        return this.like_count;
    }
    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }
    public Integer getComment_count() {
        return this.comment_count;
    }
    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }
    public Integer getEdit() {
        return this.edit;
    }
    public void setEdit(Integer edit) {
        this.edit = edit;
    }
    public String getComment() {
        return this.comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getLike() {
        return this.like;
    }
    public void setLike(String like) {
        this.like = like;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(user_id);
        dest.writeString(username);
        dest.writeString(user_photo);
        dest.writeString(content);
        dest.writeString(photo_list);
        dest.writeString(photo_online_list);
        if (like_count == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(like_count);
        }
        if (comment_count == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(comment_count);
        }
        if (edit == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(edit);
        }
        dest.writeString(comment);
        dest.writeString(like);
    }
}
