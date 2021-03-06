package com.example.chen1.uncom.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by chen1 on 2017/10/4.
 */
@Entity
public class RelationShipLevelBean implements Parcelable{

    @Id
    private String id;
    private Integer level;
    private String minor_user;//用户Id
    private String header_pic;
    private String username;
    private Integer sex;
    private String email;
    private String self_abstract;//自述(心情短语)
    private String college;
    private String university;
    private String sprovince;
    private String sarea;
    private String stown;
    private String phone;
    private Integer age;
    private String last_message;//最后活动的消息
    private Date last_active_time;//最后的活动时间
    private Date connect_time;//与用户建立关系时间
    private boolean active;//是否在聚合(Set)界面中
    private Integer un_look;//未浏览信息总数


    protected RelationShipLevelBean(Parcel in) {
        id = in.readString();
        if (in.readByte() == 0) {
            level = null;
        } else {
            level = in.readInt();
        }
        minor_user = in.readString();
        header_pic = in.readString();
        username = in.readString();
        if (in.readByte() == 0) {
            sex = null;
        } else {
            sex = in.readInt();
        }
        email = in.readString();
        self_abstract = in.readString();
        college = in.readString();
        university = in.readString();
        sprovince = in.readString();
        sarea = in.readString();
        stown = in.readString();
        phone = in.readString();
        if (in.readByte() == 0) {
            age = null;
        } else {
            age = in.readInt();
        }
        last_message = in.readString();
        active = in.readByte() != 0;
        if (in.readByte() == 0) {
            un_look = null;
        } else {
            un_look = in.readInt();
        }
    }

    @Generated(hash = 326762254)
    public RelationShipLevelBean(String id, Integer level, String minor_user, String header_pic,
            String username, Integer sex, String email, String self_abstract, String college,
            String university, String sprovince, String sarea, String stown, String phone, Integer age,
            String last_message, Date last_active_time, Date connect_time, boolean active,
            Integer un_look) {
        this.id = id;
        this.level = level;
        this.minor_user = minor_user;
        this.header_pic = header_pic;
        this.username = username;
        this.sex = sex;
        this.email = email;
        this.self_abstract = self_abstract;
        this.college = college;
        this.university = university;
        this.sprovince = sprovince;
        this.sarea = sarea;
        this.stown = stown;
        this.phone = phone;
        this.age = age;
        this.last_message = last_message;
        this.last_active_time = last_active_time;
        this.connect_time = connect_time;
        this.active = active;
        this.un_look = un_look;
    }

    @Generated(hash = 831056549)
    public RelationShipLevelBean() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        if (level == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(level);
        }
        dest.writeString(minor_user);
        dest.writeString(header_pic);
        dest.writeString(username);
        if (sex == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sex);
        }
        dest.writeString(email);
        dest.writeString(self_abstract);
        dest.writeString(college);
        dest.writeString(university);
        dest.writeString(sprovince);
        dest.writeString(sarea);
        dest.writeString(stown);
        dest.writeString(phone);
        if (age == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(age);
        }
        dest.writeString(last_message);
        dest.writeByte((byte) (active ? 1 : 0));
        if (un_look == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(un_look);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getMinor_user() {
        return this.minor_user;
    }

    public void setMinor_user(String minor_user) {
        this.minor_user = minor_user;
    }

    public String getHeader_pic() {
        return this.header_pic;
    }

    public void setHeader_pic(String header_pic) {
        this.header_pic = header_pic;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSelf_abstract() {
        return this.self_abstract;
    }

    public void setSelf_abstract(String self_abstract) {
        this.self_abstract = self_abstract;
    }

    public String getCollege() {
        return this.college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getUniversity() {
        return this.university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getSprovince() {
        return this.sprovince;
    }

    public void setSprovince(String sprovince) {
        this.sprovince = sprovince;
    }

    public String getSarea() {
        return this.sarea;
    }

    public void setSarea(String sarea) {
        this.sarea = sarea;
    }

    public String getStown() {
        return this.stown;
    }

    public void setStown(String stown) {
        this.stown = stown;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLast_message() {
        return this.last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public Date getLast_active_time() {
        return this.last_active_time;
    }

    public void setLast_active_time(Date last_active_time) {
        this.last_active_time = last_active_time;
    }

    public Date getConnect_time() {
        return this.connect_time;
    }

    public void setConnect_time(Date connect_time) {
        this.connect_time = connect_time;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getUn_look() {
        return this.un_look;
    }

    public void setUn_look(Integer un_look) {
        this.un_look = un_look;
    }

    public static final Creator<RelationShipLevelBean> CREATOR = new Creator<RelationShipLevelBean>() {
        @Override
        public RelationShipLevelBean createFromParcel(Parcel in) {
            return new RelationShipLevelBean(in);
        }

        @Override
        public RelationShipLevelBean[] newArray(int size) {
            return new RelationShipLevelBean[size];
        }
    };
}
