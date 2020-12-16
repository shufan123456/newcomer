 /*
  * Copyright 2020 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.tuhu.sf.newcomer.task.service.common;


 import java.io.Serializable;
 import java.util.Date;


 /**
  * @author 舒凡
  * @date 2020/12/9 19:27
  */
 public class EnrollList implements Serializable {
     private static final long serialVersionUID = 0000000000006L;

     //报名人员信息(姓名、证件号、手机号、国籍、年龄、身体状态)、报名的课程名称、讲师名称、开课时间、报名时间、审核状态、审核人
     /**
      * 用户相关属性
      */
     /**
      * 姓名
      */
     private String name;
     /**
      * 身份证号
      */
     private String idCardNum;
     /**
      * 国籍
      */
     private String nationality;
     /**
      * 年龄
      */
     private Integer age;
     /**
      * 手机号码
      */
     private String phone;
     /**
      * 用户健康状态，enable，disable
      */
     private String status;

     /**
      * 课程相关属性：课程名称、讲师名称、开课时间
      */
     /**
      * 用户id
      */
     private Long accountId;
     /**
      * /课程名称
      */
     private String courseName;
     /**
      * 讲师名称
      */
     private String teacher;
     /**
      * 开课时间
      */
     private Date beginTime;
     /**
      * 报名时间
      */
     private Date enrollTime;
     /**
      * 课程id
      */
     private Long courseId;
     /**
      * 审核相关属性
      */
     /**
      * 审核状态
      */
     private String auditStatus;
     /**
      * 审核人
      */
     private Long auditor;


     public EnrollList() {
     }

     @Override
     public String toString() {
         return "EnrollList{" +
                 "name='" + name + '\'' +
                 ", idCardNum='" + idCardNum + '\'' +
                 ", nationality='" + nationality + '\'' +
                 ", age=" + age +
                 ", phone='" + phone + '\'' +
                 ", status='" + status + '\'' +
                 ", accountId=" + accountId +
                 ", courseName='" + courseName + '\'' +
                 ", teacher='" + teacher + '\'' +
                 ", beginTime=" + beginTime +
                 ", enrollTime=" + enrollTime +
                 ", courseId=" + courseId +
                 ", auditStatus='" + auditStatus + '\'' +
                 ", auditor=" + auditor +
                 '}';
     }

     public static long getSerialVersionUID() {
         return serialVersionUID;
     }

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public String getIdCardNum() {
         return idCardNum;
     }

     public void setIdCardNum(String idCardNum) {
         this.idCardNum = idCardNum;
     }

     public String getNationality() {
         return nationality;
     }

     public void setNationality(String nationality) {
         this.nationality = nationality;
     }

     public Integer getAge() {
         return age;
     }

     public void setAge(Integer age) {
         this.age = age;
     }

     public String getPhone() {
         return phone;
     }

     public void setPhone(String phone) {
         this.phone = phone;
     }

     public String getStatus() {
         return status;
     }

     public void setStatus(String status) {
         this.status = status;
     }

     public Long getAccountId() {
         return accountId;
     }

     public void setAccountId(Long accountId) {
         this.accountId = accountId;
     }

     public String getCourseName() {
         return courseName;
     }

     public void setCourseName(String courseName) {
         this.courseName = courseName;
     }

     public String getTeacher() {
         return teacher;
     }

     public void setTeacher(String teacher) {
         this.teacher = teacher;
     }

     public Date getBeginTime() {
         return beginTime;
     }

     public void setBeginTime(Date beginTime) {
         this.beginTime = beginTime;
     }

     public Long getCourseId() {
         return courseId;
     }

     public void setCourseId(Long courseId) {
         this.courseId = courseId;
     }

     public String getAuditStatus() {
         return auditStatus;
     }

     public void setAuditStatus(String auditStatus) {
         this.auditStatus = auditStatus;
     }

     public Long getAuditor() {
         return auditor;
     }

     public void setAuditor(Long auditor) {
         this.auditor = auditor;
     }

     public Date getEnrollTime() {
         return enrollTime;
     }

     public void setEnrollTime(Date enrollTime) {
         this.enrollTime = enrollTime;
     }

     public EnrollList(String name, String idCardNum, String nationality, Integer age, String phone, String status, Long accountId, String courseName, String teacher, Date beginTime, Date enrollTime, Long courseId, String auditStatus, Long auditor) {
         this.name = name;
         this.idCardNum = idCardNum;
         this.nationality = nationality;
         this.age = age;
         this.phone = phone;
         this.status = status;
         this.accountId = accountId;
         this.courseName = courseName;
         this.teacher = teacher;
         this.beginTime = beginTime;
         this.enrollTime = enrollTime;
         this.courseId = courseId;
         this.auditStatus = auditStatus;
         this.auditor = auditor;
     }
 }
