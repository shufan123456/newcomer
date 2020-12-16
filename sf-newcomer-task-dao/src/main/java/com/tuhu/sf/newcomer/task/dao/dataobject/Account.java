package com.tuhu.sf.newcomer.task.dao.dataobject;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:舒凡
 * @Description:sf_account账户表
 * @date 2020/12/7 19:16
 *****/
@TableName("sf_account")
public class Account implements Serializable {
    private static final long serialVersionUID = 0000000000002L;
    @TableId(value = "id", type = IdType.INPUT)
    @Column(name = "id")
    private Long id;//主键

    @TableField("login_id")
    private String loginId;//账号ID

    @TableField("organization_code")
    private String organizationCode;//所属组织

    @TableField("phone")
    private String phone;//手机号码

    @TableField("role_code")
    private String roleCode;//角色

    @TableField("password")
    private String password;//密码

    @TableField("name")
    private String name;//姓名

    @TableField("nationality")
    private String nationality;//国籍

    @TableField("id_card_num")
    private String idCardNum;//身份证号

    @TableField("age")
    private Integer age;//年龄

    @TableField("simple_pinyin_name")
    private String simplePinyinName;//名字拼音首字母简写

    @TableField("type")
    private String type;//类型,用户,运营人员,管理员

    @TableField("status")
    private String status;//健康状态，enable，disable

    @TableField("email")
    private String email;//邮箱

    @TableField("gender")
    private String gender;//性别

    @TableField("birthday")
    private Date birthday;//出生日期

    @TableField("creator")
    private Long creator;//创建人ID

    @TableField("creator_name")
    private String creatorName;//创建人名称

    @TableField("add_time")
    private Date addTime;//创建时间

    @TableField("updator")
    private Long updator;//修改人ID

    @TableField("updator_name")
    private String updatorName;//修改人名称

    @TableField("update_time")
    private Date updateTime;//修改时间


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", loginId='" + loginId + '\'' +
                ", organizationCode='" + organizationCode + '\'' +
                ", phone='" + phone + '\'' +
                ", roleCode='" + roleCode + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                ", idCardNum='" + idCardNum + '\'' +
                ", age=" + age +
                ", simplePinyinName='" + simplePinyinName + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", creator=" + creator +
                ", creatorName='" + creatorName + '\'' +
                ", addTime=" + addTime +
                ", updator=" + updator +
                ", updatorName='" + updatorName + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }


    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }


    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleCode() {
        return roleCode;
    }


    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }


    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdCardNum() {
        return idCardNum;
    }


    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public Integer getAge() {
        return age;
    }


    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSimplePinyinName() {
        return simplePinyinName;
    }


    public void setSimplePinyinName(String simplePinyinName) {
        this.simplePinyinName = simplePinyinName;
    }

    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }


    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getCreator() {
        return creator;
    }


    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creatorName;
    }


    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getAddTime() {
        return addTime;
    }


    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Long getUpdator() {
        return updator;
    }


    public void setUpdator(Long updator) {
        this.updator = updator;
    }

    public String getUpdatorName() {
        return updatorName;
    }


    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }


    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


}
