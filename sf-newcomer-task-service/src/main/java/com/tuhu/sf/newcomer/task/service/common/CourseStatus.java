 /*
  * Copyright 2020 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.tuhu.sf.newcomer.task.service.common;

 /**
  * @author 舒凡
  * @date 2020/12/8 13:58
  */
 public enum CourseStatus {
  /**
   * 0：待开课
   */
  ZERO(0,"待开课"),
  /**
   * 1:已开课
   */
  ONE(1,"已开课"),
  /**
   * 2：已结束
   */
  TWO(2,"已结束"),
  /**
   * 3：已满员
   */
  THREE(3,"已满员");


  private final int code;

  CourseStatus(int code,final String value) {
   this.code = code;
  }

  public int getCode() {
   return code;
  }

  
 }
