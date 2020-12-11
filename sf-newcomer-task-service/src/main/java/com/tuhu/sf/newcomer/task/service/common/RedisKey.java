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
 public class RedisKey {
  //用户报名队列
  private static final String ACCOUNT_QUEUE_LIST = "account_queue_list";
  //用户报名情况
  private static final String ACCOUNT_QUEUE_STATUS ="account_queue_status";
  //课程席位数key
  private static final String COURSE_COUNT = "course_count";
  //课程详细信息key
  private static final String COURSE_KEY = "course";
 }
