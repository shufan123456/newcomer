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
  * @date 2020/12/10 14:23
  */
 public enum RedisKey {
     /**
      * 用户报名队列
      */
     ACCOUNT_QUEUE_LIST("account_queue_list", "用户报名队列"),
     /**
      * 用户报名情况
      */
     ACCOUNT_QUEUE_STATUS("account_queue_status", "用户报名情况"),
     /**
      * 课程席位数key
      */
     COURSE_COUNT("course_count", "课程席位数key"),
     /**
      * 课程详细信息key
      */
     COURSE_KEY("course", "课程详细信息key");

     private final String code;


     RedisKey(String code, final String value) {
         this.code = code;
     }

     public String getCode() {
         return code;
     }


 }
