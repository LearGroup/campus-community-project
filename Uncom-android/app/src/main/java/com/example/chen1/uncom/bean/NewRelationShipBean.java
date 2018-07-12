package com.example.chen1.uncom.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 用于存放新关系增减的Bean
 * Created by chen1 on 2017/10/14.
 */
@Entity
public class NewRelationShipBean   implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    private Integer type;//0用户名搜索结果 1手机号搜索结果 2邮箱搜索结果 3未知方式...
    private String header_pic;//用户头像
    private String results;//结果展示
    private Date get_time;
    private String college;
    private String university;
    private String self_abstract;//自述信息，描述建立关系的原因
    private Integer result_type;  //添加的结果，(是否接受了 1已接受 2未接受)
    private String user_name;
    private Integer view_type;//视图需要展示的类型 0顶层搜索时弹出的搜索框 1进入新关系页面的默认布局(显示添加新关系的历史 2新关系搜索结果框)
    private String sprovince;//省/直辖市
    private String stown;//市
    private String sarea;//区
    private Integer sex;//性别
    private String user_id;//用户id


    protected NewRelationShipBean(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            type = null;
        } else {
            type = in.readInt();
        }
        header_pic = in.readString();
        results = in.readString();
        college = in.readString();
        university = in.readString();
        self_abstract = in.readString();
        if (in.readByte() == 0) {
            result_type = null;
        } else {
            result_type = in.readInt();
        }
        user_name = in.readString();
        if (in.readByte() == 0) {
            view_type = null;
        } else {
            view_type = in.readInt();
        }
        sprovince = in.readString();
        stown = in.readString();
        sarea = in.readString();
        if (in.readByte() == 0) {
            sex = null;
        } else {
            sex = in.readInt();
        }
        user_id = in.readString();
    }

    @Generated(hash = 106136241)
    public NewRelationShipBean(Long id, Integer type, String header_pic, String results, Date get_time,
            String college, String university, String self_abstract, Integer result_type,
            String user_name, Integer view_type, String sprovince, String stown, String sarea,
            Integer sex, String user_id) {
        this.id = id;
        this.type = type;
        this.header_pic = header_pic;
        this.results = results;
        this.get_time = get_time;
        this.college = college;
        this.university = university;
        this.self_abstract = self_abstract;
        this.result_type = result_type;
        this.user_name = user_name;
        this.view_type = view_type;
        this.sprovince = sprovince;
        this.stown = stown;
        this.sarea = sarea;
        this.sex = sex;
        this.user_id = user_id;
    }

    @Generated(hash = 1353939643)
    public NewRelationShipBean() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        if (type == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(type);
        }
        dest.writeString(header_pic);
        dest.writeString(results);
        dest.writeString(college);
        dest.writeString(university);
        dest.writeString(self_abstract);
        if (result_type == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(result_type);
        }
        dest.writeString(user_name);
        if (view_type == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(view_type);
        }
        dest.writeString(sprovince);
        dest.writeString(stown);
        dest.writeString(sarea);
        if (sex == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sex);
        }
        dest.writeString(user_id);
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

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getHeader_pic() {
        return this.header_pic;
    }

    public void setHeader_pic(String header_pic) {
        this.header_pic = header_pic;
    }

    public String getResults() {
        return this.results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public Date getGet_time() {
        return this.get_time;
    }

    public void setGet_time(Date get_time) {
        this.get_time = get_time;
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

    public String getSelf_abstract() {
        return this.self_abstract;
    }

    public void setSelf_abstract(String self_abstract) {
        this.self_abstract = self_abstract;
    }

    public Integer getResult_type() {
        return this.result_type;
    }

    public void setResult_type(Integer result_type) {
        this.result_type = result_type;
    }

    public String getUser_name() {
        return this.user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getView_type() {
        return this.view_type;
    }

    public void setView_type(Integer view_type) {
        this.view_type = view_type;
    }

    public String getSprovince() {
        return this.sprovince;
    }

    public void setSprovince(String sprovince) {
        this.sprovince = sprovince;
    }

    public String getStown() {
        return this.stown;
    }

    public void setStown(String stown) {
        this.stown = stown;
    }

    public String getSarea() {
        return this.sarea;
    }

    public void setSarea(String sarea) {
        this.sarea = sarea;
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public static final Creator<NewRelationShipBean> CREATOR = new Creator<NewRelationShipBean>() {
        @Override
        public NewRelationShipBean createFromParcel(Parcel in) {
            return new NewRelationShipBean(in);
        }

        @Override
        public NewRelationShipBean[] newArray(int size) {
            return new NewRelationShipBean[size];
        }
    };
}
