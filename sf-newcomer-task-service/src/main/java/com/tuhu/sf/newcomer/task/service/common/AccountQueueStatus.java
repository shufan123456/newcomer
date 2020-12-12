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
  * @date 2020/12/9 9:34
  * @Descipt 用户报名信息封装,//课程名称、讲师名称、开课时间、报名时间、审核状态返回值
  */
 public class AccountQueueStatus implements Serializable {

     private static final long serialVersionUID = 0000000000006L;

     /**
      * 用户id
      */

     private Long accountId;
     /**
      * 报名id
      */
     private Long enrollId;
     /**
      * 课程名称
      */
     private String name;
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
     private Date createTime;
     /**
      * 审核状态 0:审核中，1:已审核,2:审核失败,
      */
     private Integer status;
     /**
      * 报名失败->席位回滚
      */
     private Long courseId;

     public AccountQueueStatus() {
     }

     public AccountQueueStatus(Long accountId, String name, String teacher, Date beginTime, Date createTime, Integer status, Long courseId) {
         this.accountId = accountId;
         this.name = name;
         this.teacher = teacher;
         this.beginTime = beginTime;
         this.createTime = createTime;
         this.status = status;
         this.courseId = courseId;
     }

     public Long getEnrollId() {
         return enrollId;
     }

     public void setEnrollId(Long enrollId) {
         this.enrollId = enrollId;
     }

     public Long getAccountId() {
         return accountId;
     }

     public void setAccountId(Long accountId) {
         this.accountId = accountId;
     }

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
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

     public Date getCreateTime() {
         return createTime;
     }

     public void setCreateTime(Date createTime) {
         this.createTime = createTime;
     }

     public Integer getStatus() {
         return status;
     }

     public void setStatus(Integer status) {
         this.status = status;
     }

     public Long getCourseId() {
         return courseId;
     }

     public void setCourseId(Long courseId) {
         this.courseId = courseId;
     }
 }
