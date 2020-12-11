package com.tuhu.sf.newcomer.task.dao.dataobject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:舒凡
 * @Description:sf_account账户表
 * @date 2020/12/7 19:16
 *****/
@Table(name="sf_account")
public class Account implements Serializable{
	private static final long serialVersionUID = 0000000000002L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;//主键

    @Column(name = "login_id")
	private String loginId;//账号ID

    @Column(name = "organization_code")
	private String organizationCode;//所属组织

    @Column(name = "phone")
	private String phone;//手机号码

    @Column(name = "role_code")
	private String roleCode;//角色

    @Column(name = "password")
	private String password;//密码

    @Column(name = "name")
	private String name;//姓名

    @Column(name = "nationality")
	private String nationality;//国籍

    @Column(name = "id_card_num")
	private String idCardNum;//身份证号

    @Column(name = "age")
	private Integer age;//年龄

    @Column(name = "simple_pinyin_name")
	private String simplePinyinName;//名字拼音首字母简写

    @Column(name = "type")
	private String type;//类型,用户,运营人员,管理员

    @Column(name = "status")
	private String status;//健康状态，enable，disable

    @Column(name = "email")
	private String email;//邮箱

    @Column(name = "gender")
	private String gender;//性别

    @Column(name = "birthday")
	private Date birthday;//出生日期

    @Column(name = "creator")
	private Long creator;//创建人ID

    @Column(name = "creator_name")
	private String creatorName;//创建人名称

    @Column(name = "add_time")
	private Date addTime;//创建时间

    @Column(name = "updator")
	private Long updator;//修改人ID

    @Column(name = "updator_name")
	private String updatorName;//修改人名称

    @Column(name = "update_time")
	private Date updateTime;//修改时间




	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public String getLoginId() {
		return loginId;
	}

	//set方法
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	//get方法
	public String getOrganizationCode() {
		return organizationCode;
	}

	//set方法
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	//get方法
	public String getPhone() {
		return phone;
	}

	//set方法
	public void setPhone(String phone) {
		this.phone = phone;
	}
	//get方法
	public String getRoleCode() {
		return roleCode;
	}

	//set方法
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	//get方法
	public String getPassword() {
		return password;
	}

	//set方法
	public void setPassword(String password) {
		this.password = password;
	}

	//get方法
	public String getName() {
		return name;
	}

	//set方法
	public void setName(String name) {
		this.name = name;
	}
	//get方法
	public String getNationality() {
		return nationality;
	}

	//set方法
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	//get方法
	public String getIdCardNum() {
		return idCardNum;
	}

	//set方法
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	//get方法
	public Integer getAge() {
		return age;
	}

	//set方法
	public void setAge(Integer age) {
		this.age = age;
	}
	//get方法
	public String getSimplePinyinName() {
		return simplePinyinName;
	}

	//set方法
	public void setSimplePinyinName(String simplePinyinName) {
		this.simplePinyinName = simplePinyinName;
	}
	//get方法
	public String getType() {
		return type;
	}

	//set方法
	public void setType(String type) {
		this.type = type;
	}
	//get方法
	public String getStatus() {
		return status;
	}

	//set方法
	public void setStatus(String status) {
		this.status = status;
	}
	//get方法
	public String getEmail() {
		return email;
	}

	//set方法
	public void setEmail(String email) {
		this.email = email;
	}
	//get方法
	public String getGender() {
		return gender;
	}

	//set方法
	public void setGender(String gender) {
		this.gender = gender;
	}
	//get方法
	public Date getBirthday() {
		return birthday;
	}

	//set方法
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	//get方法
	public Long getCreator() {
		return creator;
	}

	//set方法
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	//get方法
	public String getCreatorName() {
		return creatorName;
	}

	//set方法
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	//get方法
	public Date getAddTime() {
		return addTime;
	}

	//set方法
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	//get方法
	public Long getUpdator() {
		return updator;
	}

	//set方法
	public void setUpdator(Long updator) {
		this.updator = updator;
	}
	//get方法
	public String getUpdatorName() {
		return updatorName;
	}

	//set方法
	public void setUpdatorName(String updatorName) {
		this.updatorName = updatorName;
	}
	//get方法
	public Date getUpdateTime() {
		return updateTime;
	}

	//set方法
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


}
