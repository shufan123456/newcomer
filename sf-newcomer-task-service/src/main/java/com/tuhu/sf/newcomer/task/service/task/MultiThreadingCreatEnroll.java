 /*
  * Copyright 2020 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.tuhu.sf.newcomer.task.service.task;

 import com.tuhu.sf.newcomer.task.common.entiy.IdWorker;
 import com.tuhu.sf.newcomer.task.dao.dataobject.Account;
 import com.tuhu.sf.newcomer.task.dao.dataobject.Enroll;
 import com.tuhu.sf.newcomer.task.dao.mapper.AccountMapper;
 import com.tuhu.sf.newcomer.task.dao.mapper.CourseMapper;
 import com.tuhu.sf.newcomer.task.dao.mapper.EnrollMapper;
 import com.tuhu.sf.newcomer.task.service.common.AccountQueueStatus;
 import com.tuhu.sf.newcomer.task.service.common.EnrollStatus;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.redis.core.RedisTemplate;
 import org.springframework.scheduling.annotation.Async;
 import org.springframework.stereotype.Component;
 import org.springframework.util.StringUtils;

 import java.util.Date;
 import java.util.concurrent.locks.ReentrantLock;

 /**
  * @author 舒凡
  * @date 2020/12/10 14:06
  */
 @Component
 public class MultiThreadingCreatEnroll{
  //用户报名队列
  private static final String ACCOUNT_QUEUE_LIST = "account_queue_list";
  //用户报名情况
  private static final String ACCOUNT_QUEUE_STATUS ="account_queue_status";
  //课程席位数key
  private static final String COURSE_COUNT = "course_count";
  //课程详细信息key
  private static final String COURSE_KEY = "course";
  /**
   * 用户Mapper
   */
  @Autowired
  AccountMapper accountMapper;
  /**
   * 课程Mapper
   */
  @Autowired
  CourseMapper courseMapper;
  /**
   * 报名Mapper
   */
  @Autowired
  EnrollMapper enrollMapper;
  /**
   * redis
   */
  @Autowired
  RedisTemplate redisTemplate;

  /**
   * 多线程处理
   */
  @Async
  public  void createEnroll(){
   ReentrantLock reentrantLock = new ReentrantLock();
   reentrantLock.lock();
   try{
    //报名队列取一个出栈
    AccountQueueStatus accountQueueStatus = (AccountQueueStatus) redisTemplate.boundListOps(ACCOUNT_QUEUE_LIST).rightPop();
    if(accountQueueStatus == null){
     return;
    }
    Long accountId = accountQueueStatus.getAccountId();
    //判断是否是一个有效的用户
    Account account = accountMapper.selectByPrimaryKey(accountId);
    if(account == null){
     //设置为审核失败状态
     setQueueStatus(accountQueueStatus);
     throw  new RuntimeException("无效的用户id");
    }
    //查看用户信息是否完善
    if(!checkAccoutParam(account)){
     //设置为审核失败状态
     setQueueStatus(accountQueueStatus);
     throw  new RuntimeException("请完善个人信息：姓名、证件号、手机号、国籍、年龄、身体状态");
    }
    //先从COURSE_COUNT_courseId队列中，取出该课程信息，如果能获取，则可以正常报名
    Object obj = redisTemplate.boundListOps(COURSE_COUNT+"_"+accountQueueStatus.getCourseId()).rightPop();
    if(null == obj){
     throw new RuntimeException("暂无席位!");
    }
    //创建一个报名课程
    Enroll enroll = new Enroll();
    IdWorker idWorker = new IdWorker();
    //报名id
    enroll.setId(idWorker.nextId());
    //用户id
    enroll.setUserId(accountQueueStatus.getAccountId());
    //课程id
    enroll.setCourseId(accountQueueStatus.getCourseId());
    //审核状态
    enroll.setAuditStatus(String.valueOf(EnrollStatus.ZERO.getCode()));
    //报名时间
    enroll.setEnrollTime(accountQueueStatus.getCreateTime());
    //创建时间
    enroll.setAddTime(new Date());
    //写入数据库
    enrollMapper.insertSelective(enroll);

    //需要再次判断一下，席位的数量。
    Long size = redisTemplate.boundListOps(COURSE_COUNT+"_"+accountQueueStatus.getCourseId()).size();
    if(size <= 0){
     //设置为审核失败状态
     setQueueStatus(accountQueueStatus);
     //需要清理redis中的课程
     redisTemplate.boundHashOps(COURSE_KEY).delete(accountQueueStatus.getCourseId());
    }
   }catch(Exception e){
    e.printStackTrace();
   }finally {
    reentrantLock.unlock();
   }
  }

  /**
   * 设置为审核失败状态
   * @param accountQueueStatus
   */
  private void setQueueStatus(AccountQueueStatus accountQueueStatus) {
   accountQueueStatus.setStatus(EnrollStatus.TWO.getCode());
   redisTemplate.boundHashOps(ACCOUNT_QUEUE_STATUS +"&"+ accountQueueStatus.getAccountId())
           .put(accountQueueStatus.getCourseId() ,  accountQueueStatus);
  }

  /**
   * 清理用户排队报名信息,暂时先不清理。
   */
  public void clearAccountQueue(Long accountId,Long courseId){
   //排队标识
   redisTemplate.boundHashOps(courseId).delete(accountId);
   //排队信息状态清理
   redisTemplate.boundHashOps(ACCOUNT_QUEUE_STATUS +"&"+accountId).delete(courseId);
  }

  /**
   * 验证一下用户报名属性是否为空
   * @param account
   * @return
   */
  private Boolean checkAccoutParam(Account account){
   if(StringUtils.isEmpty(account.getName())){
    return false;
   }if(StringUtils.isEmpty(account.getIdCardNum())){
    return false;
   }if(StringUtils.isEmpty(account.getPhone())){
    return false;
   }if(StringUtils.isEmpty(account.getNationality())){
    return false;
   }if(StringUtils.isEmpty(account.getAge())){
    return false;
   }if(StringUtils.isEmpty(account.getStatus())){
    return false;
   }else{
    return true;
   }
  }

}
