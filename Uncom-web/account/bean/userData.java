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
	private String id;//����
	
	
	@Column(field="username",type="varchar(30)",defaultNull=false)
	private String username;//�û��ǳ�
	
	@Column(field="password",type="varchar(30)",defaultNull=false)
	private String password;//����
	
	@Column(field="headerPic",type="varchar(30)")
	private String headerPic;//ͷ��
	
	@Column(field="province",type="varchar(30)")
	private String province;//����ʡ
	
	@Column(field="town",type="varchar(30)")
	private String town;//��
	
	@Column(field="area",type="varchar(30)")
	private String area;//��
	
	@Column(field="sprovince",type="varchar(30)")
	private String sprovince;//ѧУ����ʡ
	
	@Column(field="stown",type="varchar(30)")
	private String stown;//ѧУ��
	
	@Column(field="sarea",type="varchar(30)")
	private String sarea;//ѧУ��
	
	@Column(field="email",type="varchar(30)")
	private String email;//��������
	
	@Column(field="phone",type="varchar(17)")
	private String phone;//�绰
	
	
	@Column(field="sex",type="varchar(2)")
	private Integer sex;//�Ա� 1�� 0Ů 3����
	
	@Column(field="create_time",type="datetime")
	private String createTime;//����ʱ��
	
	@Column(field="update_time",type="timestamp")
	private String updateTime;//������ʱ��
	
	@Column(field="is_delete",type="int(1)")
	private Integer isDelete;//ɾ��״̬ 1ɾ�� 0δɾ��
	
	
	@Column(field="qq_number",type="varchar(16)")
	private String qqNumber;//QQ����
	
	@Column(field="wexin_number",type="varchar(30)")
	private String wexinNumber;//΢�ź���
	
	
	@Column(field="weibo",type="varchar(30)")
	private String weibo;//΢��
	
	@Column(field="student_id",type="varchar(20)")
	private String studentId;//ѧ��
	

	@Column(field="student_id_password",type="varchar(30)")
	private String studentIdPassword;//ѧ������

	private Integer age ;
	
	
	public Integer getAge() {
		return age;
	}


	public void setAge(Integer age) {
		this.age = age;
	}


	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(generator = "uuid2" )   //ָ������������  
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



	public Integer getIsDelete() {
		return isDelete;
	}


	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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


	@Override
	public String toString() {
		return "userData [id=" + id + ", username=" + username + ", password=" + password + ", headerPic=" + headerPic
				+ ", province=" + province + ", town=" + town + ", area=" + area + ", sprovince=" + sprovince
				+ ", stown=" + stown + ", sarea=" + sarea + ", email=" + email + ", phone=" + phone + ", sex=" + sex
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", isDelete=" + isDelete + ", qqNumber="
				+ qqNumber + ", wexinNumber=" + wexinNumber + ", weibo=" + weibo + ", studentId=" + studentId
				+ ", studentIdPassword=" + studentIdPassword + ", age=" + age + ", getAge()=" + getAge() + ", getId()="
				+ getId() + ", getUsername()=" + getUsername() + ", getPassword()=" + getPassword()
				+ ", getHeaderPic()=" + getHeaderPic() + ", getProvince()=" + getProvince() + ", getTown()=" + getTown()
				+ ", getArea()=" + getArea() + ", getSprovince()=" + getSprovince() + ", getStown()=" + getStown()
				+ ", getSarea()=" + getSarea() + ", getEmail()=" + getEmail() + ", getPhone()=" + getPhone()
				+ ", getSex()=" + getSex() + ", getCreateTime()=" + getCreateTime() + ", getIsDelete()=" + getIsDelete()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getQqNumber()=" + getQqNumber() + ", getWexinNumber()="
				+ getWexinNumber() + ", getWeibo()=" + getWeibo() + ", getStudentId()=" + getStudentId()
				+ ", getStudentIdPassword()=" + getStudentIdPassword() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}


	
	

}
