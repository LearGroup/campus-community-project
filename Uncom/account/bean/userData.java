package bean;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import annotation.Column;
import annotation.Table;


@Table(tableName="t_user")
public class userData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5335225424330706324L;
	
	

	@Column(type="varchar(30)",field="id",primarykey=true,defaultNull=false )
	private String id;//主键
	
	@Column(field="username",type="varchar(30)",defaultNull=false)
	private String username;//用户昵称
	
	@Column(field="password",type="varchar(30)",defaultNull=false)
	private String password;//密码
	
	@Column(field="headerPic",type="varchar(30)")
	private String headerPic;//头像·
	
	@Column(field="province",type="varchar(30)")
	private String province;//所在省
	
	@Column(field="town",type="varchar(30)")
	private String town;//市
	
	@Column(field="area",type="varchar(30)")
	private String area;//区
	
	@Column(field="sprovince",type="varchar(30)")
	private String sprovince;//学校所在省
	
	@Column(field="stown",type="varchar(30)")
	private String stown;//学校市
	
	@Column(field="sarea",type="varchar(30)")
	private String sarea;//学校区
	
	@Column(field="email",type="varchar(30)")
	private String email;//电子邮箱
	
	@Column(field="phone",type="varchar(17)")
	private String phone;//电话
	
	
	@Column(field="sex",type="varchar(2)")
	private Integer sex;//性别 1男 0女 3保密
	
	@Column(field="create_time",type="datetime")
	private String createTime;//创建时间
	
	@Column(field="update_time",type="timestamp")
	private String updateTime;//最后更新时间
	
	@Column(field="is_delete",type="int(1)")
	private String isDelete;//删除状态 1删除 0未删除
	
	
	@Column(field="qq_number",type="varchar(16)")
	private String qqNumber;//QQ号码
	
	@Column(field="wexin_number",type="varchar(30)")
	private String wexinNumber;//微信号码
	
	
	@Column(field="weibo",type="varchar(30)")
	private String weibo;//微博
	
	@Column(field="student_id",type="varchar(20)")
	private String studentId;//学号
	

	@Column(field="student_id_password",type="varchar(30)")
	private String studentIdPassword;//学号密码

	
	
	
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(generator = "uuid2" )   //指定生成器名称  
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )  
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the headerPic
	 */
	public String getHeaderPic() {
		return headerPic;
	}


	/**
	 * @param headerPic the headerPic to set
	 */
	public void setHeaderPic(String headerPic) {
		this.headerPic = headerPic;
	}


	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}


	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}


	/**
	 * @return the town
	 */
	public String getTown() {
		return town;
	}


	/**
	 * @param town the town to set
	 */
	public void setTown(String town) {
		this.town = town;
	}


	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}


	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}


	/**
	 * @return the sprovince
	 */
	public String getSprovince() {
		return sprovince;
	}


	/**
	 * @param sprovince the sprovince to set
	 */
	public void setSprovince(String sprovince) {
		this.sprovince = sprovince;
	}


	/**
	 * @return the stown
	 */
	public String getStown() {
		return stown;
	}


	/**
	 * @param stown the stown to set
	 */
	public void setStown(String stown) {
		this.stown = stown;
	}


	/**
	 * @return the sasetCreaterea
	 */
	public String getSarea() {
		return sarea;
	}


	/**
	 * @param sarea the sarea to set
	 */
	public void setSarea(String sarea) {
		this.sarea = sarea;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}


	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}


	/**
	 * @return the sex
	 */
	public Integer getSex() {
		return sex;
	}


	/**
	 * @param sex the sex to set
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}


	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}


	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(java.lang.String createTime) {
		this.createTime = createTime;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "user_data [id=" + id + ", username=" + username + ", password=" + password + ", headerPic=" + headerPic
				+ ", province=" + province + ", town=" + town + ", area=" + area + ", sprovince=" + sprovince
				+ ", stown=" + stown + ", sarea=" + sarea + ", email=" + email + ", phone=" + phone + ", sex=" + sex
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", isDelete=" + isDelete + ", qqNumber="
				+ qqNumber + ", wexinNumber=" + wexinNumber + ", weibo=" + weibo + ", studentId=" + studentId
				+ ", studentIdPassword=" + studentIdPassword + ", getId()=" + getId() + ", getUsername()="
				+ getUsername() + ", getPassword()=" + getPassword() + ", getHeaderPic()=" + getHeaderPic()
				+ ", getProvince()=" + getProvince() + ", getTown()=" + getTown() + ", getArea()=" + getArea()
				+ ", getSprovince()=" + getSprovince() + ", getStown()=" + getStown() + ", getSarea()=" + getSarea()
				+ ", getEmail()=" + getEmail() + ", getPhone()=" + getPhone() + ", getSex()=" + getSex()
				+ ", getCreateTime()=" + getCreateTime() + ", getUpdateTime()=" + getUpdateTime() + ", getIsDelete()="
				+ getIsDelete() + ", getQqNumber()=" + getQqNumber() + ", getWexinNumber()=" + getWexinNumber()
				+ ", getWeibo()=" + getWeibo() + ", getStudentId()=" + getStudentId() + ", getStudentIdPassword()="
				+ getStudentIdPassword() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}


	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}


	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}


	/**
	 * @return the isDelete
	 */
	public String getIsDelete() {
		return isDelete;
	}


	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}


	/**
	 * @return the qqNumber
	 */
	public String getQqNumber() {
		return qqNumber;
	}


	/**
	 * @param qqNumber the qqNumber to set
	 */
	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}


	/**
	 * @return the wexinNumber
	 */
	public String getWexinNumber() {
		return wexinNumber;
	}


	/**
	 * @param wexinNumber the wexinNumber to set
	 */
	public void setWexinNumber(String wexinNumber) {
		this.wexinNumber = wexinNumber;
	}


	/**
	 * @return the weibo
	 */
	public String getWeibo() {
		return weibo;
	}


	/**
	 * @param weibo the weibo to set
	 */
	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}


	/**
	 * @return the studentId
	 */
	public String getStudentId() {
		return studentId;
	}


	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}


	/**
	 * @return the studentIdPassword
	 */
	public String getStudentIdPassword() {
		return studentIdPassword;
	}


	/**
	 * @param studentIdPassword the studentIdPassword to set
	 */
	public void setStudentIdPassword(String studentIdPassword) {
		this.studentIdPassword = studentIdPassword;
	}


	
	

}
