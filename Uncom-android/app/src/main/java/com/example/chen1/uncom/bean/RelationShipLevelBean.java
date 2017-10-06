package com.example.chen1.uncom.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by chen1 on 2017/10/4.
 */
@Entity
public class RelationShipLevelBean implements Parcelable{

    @Id
    private String id;
    private Integer level;
    private String minor_user;
    private String header_pic;
    private String username;
    private Integer sex;
    private String email;
    private String self_abstract;
    private String sprovince;
    private String sarea;
    private String stown;
    private String phone;
    private Integer age;
    @Generated(hash = 1409431939)
    public RelationShipLevelBean(String id, Integer level, String minor_user,
            String header_pic, String username, Integer sex, String email,
            String self_abstract, String sprovince, String sarea, String stown,
            String phone, Integer age) {
        this.id = id;
        this.level = level;
        this.minor_user = minor_user;
        this.header_pic = header_pic;
        this.username = username;
        this.sex = sex;
        this.email = email;
        this.self_abstract = self_abstract;
        this.sprovince = sprovince;
        this.sarea = sarea;
        this.stown = stown;
        this.phone = phone;
        this.age = age;
    }
    @Generated(hash = 831056549)
    public RelationShipLevelBean() {
    }

    protected RelationShipLevelBean(Parcel in) {
        id = in.readString();
        minor_user = in.readString();
        header_pic = in.readString();
        username = in.readString();
        email = in.readString();
        self_abstract = in.readString();
        sprovince = in.readString();
        sarea = in.readString();
        stown = in.readString();
        phone = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(minor_user);
        dest.writeString(header_pic);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(self_abstract);
        dest.writeString(sprovince);
        dest.writeString(sarea);
        dest.writeString(stown);
        dest.writeString(phone);
    }
}
