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
  * @date 2020/12/9 12:48
  */
 public enum  EnrollStatus {
  /**
   * 0：审核中
   */
  ZERO(0,"审核中"),
  /**
   * 1:已审核
   */
  ONE(1,"已审核"),
  /**
   * 2：审核失败
   */
  TWO(2, "审核失败");

  private final int code;

  EnrollStatus(int code, final String value){
   this.code = code;
  }

  public int getCode() {
   return code;
  }

 }
