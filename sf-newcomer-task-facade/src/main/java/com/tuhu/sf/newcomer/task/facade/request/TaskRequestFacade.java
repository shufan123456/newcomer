 /*
  * Copyright 2020 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.tuhu.sf.newcomer.task.facade.request;
 import java.io.Serializable;


 /**
  * @author 舒凡
  * @date 2020/12/8 13:28
  */
 public class TaskRequestFacade implements Serializable {
  private static final long serialVersionUID = 0000000000005L;

  /**
   * 用户id
   */
  private Long accountId;
  /**
   * 课程id
   */
  private Long courseId;
  /**
   * 报名id
   */
  private Long  enrollId;

  /**
   * 审核人员id
   * @return
   */
  private Long auditor;


  public Long getAccountId() {
   return accountId;
  }

  public void setAccountId(Long accountId) {
   this.accountId = accountId;
  }

  public Long getCourseId() {
   return courseId;
  }

  public void setCourseId(Long courseId) {
   this.courseId = courseId;
  }

  public Long getEnrollId() {
   return enrollId;
  }

  public void setEnrollId(Long enrollId) {
   this.enrollId = enrollId;
  }

  public Long getAuditor() {
   return auditor;
  }

  public void setAuditor(Long auditor) {
   this.auditor = auditor;
  }

  public TaskRequestFacade() {
  }

  public TaskRequestFacade(Long accountId, Long courseId, Long enrollId, Long auditor) {
   this.accountId = accountId;
   this.courseId = courseId;
   this.enrollId = enrollId;
   this.auditor = auditor;
  }
 }
