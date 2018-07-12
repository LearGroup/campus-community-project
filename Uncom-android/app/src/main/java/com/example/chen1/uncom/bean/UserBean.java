package com.example.chen1.uncom.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by chen1 on 2017/10/2.
 */
@Entity
public class UserBean {
    @Id
    private String id;

    private String username;

    private String password;

    private String header_pic;

    private String province;

    private String town;

    private String area;

    private String sprovince;

    private String stown;

    private String sarea;

    private String email;

    private String phone;

    private String self_abstract;

    private Integer sex;

    private String create_time;

    private String update_time;

    private Integer is_delete;

    private String qq_number;

    private String wexin_number;

    private String weibo;

    private String student_id;

    private String student_id_password;

    private Integer age ;

    private String university;//大学
    private String college;//学院
    private String class_session;//届别

    private String education;//教育程度
    private String major;//专业

    @Generated(hash = 930470029)
    public UserBean(String id, String username, String password, String header_pic,
            String province, String town, String area, String sprovince,
            String stown, String sarea, String email, String phone,
            String self_abstract, Integer sex, String create_time,
            String update_time, Integer is_delete, String qq_number,
            String wexin_number, String weibo, String student_id,
            String student_id_password, Integer age, String university,
            String college, String class_session, String education, String major) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.header_pic = header_pic;
        this.province = province;
        this.town = town;
        this.area = area;
        this.sprovince = sprovince;
        this.stown = stown;
        this.sarea = sarea;
        this.email = email;
        this.phone = phone;
        this.self_abstract = self_abstract;
        this.sex = sex;
        this.create_time = create_time;
        this.update_time = update_time;
        this.is_delete = is_delete;
        this.qq_number = qq_number;
        this.wexin_number = wexin_number;
        this.weibo = weibo;
        this.student_id = student_id;
        this.student_id_password = student_id_password;
        this.age = age;
        this.university = university;
        this.college = college;
        this.class_session = class_session;
        this.education = education;
        this.major = major;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeader_pic() {
        return this.header_pic;
    }

    public void setHeader_pic(String header_pic) {
        this.header_pic = header_pic;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTown() {
        return this.town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public Integer getIs_delete() {
        return this.is_delete;
    }

    public void setIs_delete(Integer is_delete) {
        this.is_delete = is_delete;
    }

    public String getQq_number() {
        return this.qq_number;
    }

    public void setQq_number(String qq_number) {
        this.qq_number = qq_number;
    }

    public String getWexin_number() {
        return this.wexin_number;
    }

    public void setWexin_number(String wexin_number) {
        this.wexin_number = wexin_number;
    }

    public String getWeibo() {
        return this.weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getStudent_id() {
        return this.student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_id_password() {
        return this.student_id_password;
    }

    public void setStudent_id_password(String student_id_password) {
        this.student_id_password = student_id_password;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSelf_abstract() {
        return this.self_abstract;
    }

    public void setSelf_abstract(String self_abstract) {
        this.self_abstract = self_abstract;
    }

    public String getUniversity() {
        return this.university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCollege() {
        return this.college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getClass_session() {
        return this.class_session;
    }

    public void setClass_session(String class_session) {
        this.class_session = class_session;
    }

    public String getEducation() {
        return this.education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }


}
