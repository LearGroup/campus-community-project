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

    private String headerPic;

    private String province;

    private String town;

    private String area;

    private String sprovince;

    private String stown;

    private String sarea;

    private String email;

    private String phone;


    private Integer sex;

    private String createTime;

    private String updateTime;

    private Integer isDelete;

    private String qqNumber;

    private String wexinNumber;


    private String weibo;

    private String studentId;

    private String studentIdPassword;

    private Integer age ;

    @Generated(hash = 624161928)
    public UserBean(String id, String username, String password, String headerPic,
            String province, String town, String area, String sprovince,
            String stown, String sarea, String email, String phone, Integer sex,
            String createTime, String updateTime, Integer isDelete, String qqNumber,
            String wexinNumber, String weibo, String studentId,
            String studentIdPassword, Integer age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.headerPic = headerPic;
        this.province = province;
        this.town = town;
        this.area = area;
        this.sprovince = sprovince;
        this.stown = stown;
        this.sarea = sarea;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
        this.qqNumber = qqNumber;
        this.wexinNumber = wexinNumber;
        this.weibo = weibo;
        this.studentId = studentId;
        this.studentIdPassword = studentIdPassword;
        this.age = age;
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

    public String getHeaderPic() {
        return this.headerPic;
    }

    public void setHeaderPic(String headerPic) {
        this.headerPic = headerPic;
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

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDelete() {
        return this.isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getQqNumber() {
        return this.qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getWexinNumber() {
        return this.wexinNumber;
    }

    public void setWexinNumber(String wexinNumber) {
        this.wexinNumber = wexinNumber;
    }

    public String getWeibo() {
        return this.weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentIdPassword() {
        return this.studentIdPassword;
    }

    public void setStudentIdPassword(String studentIdPassword) {
        this.studentIdPassword = studentIdPassword;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


}
