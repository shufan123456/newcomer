 /*
  * Copyright 2020 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.tuhu.sf.newcomer.task.service.service.bizobject;

 import com.tuhu.sf.newcomer.task.common.entiy.IdCardNumUtil;
 import com.tuhu.sf.newcomer.task.common.entiy.IdWorker;
 import com.tuhu.sf.newcomer.task.common.entiy.Result;
 import com.tuhu.sf.newcomer.task.common.entiy.StatusCode;
 import com.tuhu.sf.newcomer.task.dao.dataobject.Account;
 import com.tuhu.sf.newcomer.task.dao.dataobject.Course;
 import com.tuhu.sf.newcomer.task.dao.dataobject.Enroll;
 import com.tuhu.sf.newcomer.task.dao.mapper.AccountMapper;
 import com.tuhu.sf.newcomer.task.dao.mapper.CourseMapper;
 import com.tuhu.sf.newcomer.task.dao.mapper.EnrollMapper;
 import com.tuhu.sf.newcomer.task.facade.request.TaskRequestFacade;
 import com.tuhu.sf.newcomer.task.service.common.*;
 import com.tuhu.sf.newcomer.task.service.service.TaskService;
 import com.tuhu.sf.newcomer.task.service.task.MultiThreadingCreatEnroll;
 import org.springframework.data.redis.core.RedisTemplate;
 import org.springframework.scheduling.annotation.Scheduled;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 import org.springframework.util.StringUtils;

 import tk.mybatis.mapper.entity.Example;

 import javax.annotation.Resource;
 import java.util.*;
 import java.util.concurrent.locks.ReentrantLock;

 /**
  * @author 舒凡
  * @date 2020/12/8 13:45
  */
 @Service
 public class TaskServiceImpl implements TaskService {
     /**
      * 用户Mapper
      */
     @Resource
     private AccountMapper accountMapper;
     /**
      * 课程Mapper
      */
     @Resource
     private CourseMapper courseMapper;
     /**
      * 报名Mapper
      */
     @Resource
     private EnrollMapper enrollMapper;
     /**
      * redis
      */
     @Resource
     private RedisTemplate redisTemplate;

     @Resource
     private MultiThreadingCreatEnroll multiThreadingCreatEnroll;

     /**
      * 课程新建
      *
      * @param course pojo
      * @return Result
      * @Descript 课程名称、讲师、开课时间、课程内容、课程状态
      */
     @Override
     public Result add(Course course) {
         ReentrantLock reentrantLock = new ReentrantLock();
         if (!checkCourseParam(course)) {
             return new Result(false, StatusCode.ERROR, "课程名称、讲师、开课时间、课程内容、课程状态不能为空");
         }
         if (course.getBeginTime().compareTo(new Date()) <= 0) {
             return new Result(false, StatusCode.ERROR, "开课时间不能小于当前时间");
         }
         Example example = new Example(Course.class);
         //判断课程与开课时间是否冲突
         Example.Criteria criteria = example.createCriteria();
         criteria.andEqualTo("name", course.getName());
         criteria.andEqualTo("beginTime", course.getBeginTime());
         if (courseMapper.selectCountByExample(example) > 0) {
             return new Result(false, StatusCode.ERROR, "课程名称与开课时间冲突");
         }
         IdWorker idWorker = new IdWorker(0L, 0L);
         //先进行数据库写入
         course.setId((Long) idWorker.nextId());
         course.setAddTime(new Date());
         reentrantLock.lock();
         try {
             //写入mysql
             if (courseMapper.insertSelective(course) > 0) {
                 //写入redis
                 redisTemplate.boundHashOps(RedisKey.COURSE_KEY.getCode()).put(course.getId(), course);
                 //写入课程席位数
                 redisTemplate.boundListOps(RedisKey.COURSE_COUNT.getCode() + "_" + course.getId())
                         .leftPushAll(putCourseIds(5, course.getId()));
                 return new Result(true, StatusCode.OK, "课程添加成功");
             }
             return new Result(true, StatusCode.OK, "课程添加失败");
         } finally {
             reentrantLock.unlock();
         }

     }

     /**
      * 对课程席位进行设值
      */
     public Long[] putCourseIds(Integer num, Long id) {
         Long[] ids = new Long[num];
         for (int i = 0; i < ids.length; i++) {
             ids[i] = id;
         }
         return ids;
     }


     /**
      * 课程修改
      *
      * @param course pojo
      * @return Result
      * @Descript 课程名称、讲师、开课时间、课程内容、课程状态
      */
     @Override
     public Result update(Course course) {
         ReentrantLock reentrantLock = new ReentrantLock();
         if (!checkCourseParam(course)) {
             return new Result(false, StatusCode.ERROR, "课程名称、讲师、开课时间、课程内容、课程状态不能为空");
         }
         if (StringUtils.isEmpty(course.getId())) {
             return new Result(false, StatusCode.ERROR, "课程id不能为空");
         }
         if (course.getBeginTime().compareTo(new Date()) <= 0) {
             return new Result(false, StatusCode.ERROR, "开课时间不能小于当前时间");
         }
         reentrantLock.lock();
         try {
             course.setUpdateTime(new Date());
             if (courseMapper.updateByPrimaryKeySelective(course) > 0) {
                 redisTemplate.boundHashOps(RedisKey.COURSE_KEY.getCode()).delete(course.getId());
                 redisTemplate.boundHashOps(RedisKey.COURSE_KEY.getCode()).put(course.getId(), course);
                 return new Result(true, StatusCode.OK, "修改课程信息成功");
             }
         } finally {
             reentrantLock.unlock();
         }
         return new Result(true, StatusCode.OK, "修改课程失败");
     }

     /**
      * 用户报名
      *
      * @param taskRequestFacade accountId 用户id
      * @param taskRequestFacade courseId 课程id
      * @return
      * @Descript
      */
     @Override
     public Result addEnroll(TaskRequestFacade taskRequestFacade) {
         if (StringUtils.isEmpty(taskRequestFacade.getAccountId())) {
             return new Result(false, StatusCode.ERROR, "用户id不能为空");
         }
         if (StringUtils.isEmpty(taskRequestFacade.getCourseId())) {
             return new Result(false, StatusCode.ERROR, "课程id不能为空");
         }
         //查看课程是否存在，存在说明可以进行报名
         if (!redisTemplate.boundHashOps(RedisKey.COURSE_KEY.getCode()).hasKey(taskRequestFacade.getCourseId())) {
             return new Result(false, StatusCode.ERROR, "该课程无法进行报名");
         }
         //记录课程下用户报名的次数
         Long userQueueCount = redisTemplate.boundHashOps(taskRequestFacade.getCourseId())
                 .increment(taskRequestFacade.getAccountId(), 1);
         if (userQueueCount > 1) {
             //20007表示重复排队
             return new Result(false, StatusCode.REPEAT, "不能对同一课程重复报名");
         }
         //从redis取出课程信息
         Course course = (Course) redisTemplate.boundHashOps(RedisKey.COURSE_KEY.getCode())
                 .get(taskRequestFacade.getCourseId());
         /**
          * 创建报名对象
          * accountId:用户id
          * name:课程名称
          * teacher：讲师
          * beginTime：开课时间
          * createTime：报名时间
          * status：审核状态（ 0:审核中，1:已审核,2:审核失败）
          * courseId：课程id，用来做席位回滚操作。
          */
         AccountQueueStatus accountStatus = new AccountQueueStatus(taskRequestFacade.getAccountId(), course.getName()
                 , course.getTeacher(), course.getBeginTime(), new Date(), EnrollStatus.ZERO.getCode(),
                 taskRequestFacade.getCourseId());
         //List队列类型，有序,记录用户报名排队
         redisTemplate.boundListOps(RedisKey.ACCOUNT_QUEUE_LIST.getCode()).leftPush(accountStatus);
         //用户报名状态->报名情况查询
         redisTemplate.boundHashOps(RedisKey.ACCOUNT_QUEUE_STATUS.getCode() + "&" +
                 taskRequestFacade.getAccountId()).put(taskRequestFacade.getCourseId(), accountStatus);
         //用异步处理，进行入库,以及席位控制。
         multiThreadingCreatEnroll.createEnroll();
         return new Result(true, StatusCode.OK, "报名申请排队中...");
     }

     /**
      * 用户查看位课程(未报名的)
      *
      * @param taskRequestFacade accountId 用户id
      * @return 返回未报名的课程结果
      * @Descript
      */
     @Override
     public Result<List<Course>> findCourse(TaskRequestFacade taskRequestFacade) {
         if (StringUtils.isEmpty(taskRequestFacade.getAccountId())) {
             return new Result(false, StatusCode.ERROR, "用户id不能为空");
         }
         //拿到所有课程key
         Set<Long> courseKeys = redisTemplate.boundHashOps(RedisKey.COURSE_KEY.getCode()).keys();
         if (courseKeys == null || courseKeys.size() <= 0) {
             return new Result(false, StatusCode.ERROR, "未查询到可报名的课程");
         }
         List<Course> courseList = new ArrayList<>();
         for (Long key : courseKeys) {
             //过滤掉报过名的课程
             if (redisTemplate.boundHashOps(RedisKey.ACCOUNT_QUEUE_STATUS.getCode() + "&" +
                     taskRequestFacade.getAccountId()).hasKey(key)) {
                 continue;
             }
             courseList.add((Course) redisTemplate.boundHashOps(RedisKey.COURSE_KEY.getCode()).get(key));
         }
         return new Result<>(true, StatusCode.OK, "查询课程信息成功", courseList);

     }


     /**
      * 用户查看报名情况
      *
      * @param taskRequestFacade accountId 用户id
      * @return
      * @Descript 课程名称、讲师名称、开课时间、报名时间、审核状态
      */
     @Override
     public Result<List<AccountQueueStatus>> findEnrollByAccountId(TaskRequestFacade taskRequestFacade) {
         if (StringUtils.isEmpty(taskRequestFacade.getAccountId())) {
             return new Result<>(false, StatusCode.ERROR, "用户id不能为空");
         }
         //拿到所有用户报名的课程key
         Set<Long> keys = redisTemplate.boundHashOps(RedisKey.ACCOUNT_QUEUE_STATUS.getCode() + "&" +
                 taskRequestFacade.getAccountId()).keys();
         if (keys == null || keys.size() <= 0) {
             return new Result<>(false, StatusCode.ERROR, "查询失败，暂未报名");
         }
         List<AccountQueueStatus> list = new ArrayList<>();
         for (Long key : keys) {
             list.add((AccountQueueStatus) redisTemplate.boundHashOps(RedisKey.ACCOUNT_QUEUE_STATUS.getCode()
                     + "&" + taskRequestFacade.getAccountId()).get(key));
         }
         return new Result<>(true, StatusCode.OK, "查询报名成功", list);

     }

     /**
      * 运营人员对报名名单查看
      *
      * @return
      * @Descript 返回 -> 报名人员信息(姓名、证件号、手机号、国籍、年龄、身体状态)、
      * 报名的课程名称、讲师名称、开课时间、报名时间、审核状态、审核人
      */
     @Override
     public Result<List<EnrollList>> findEnroll() {
         //查看是否有报名的名单
         List<Enroll> enrolls = enrollMapper.selectAll();
         if (enrolls != null && enrolls.size() > 0) {
             //定义返回对象
             List<EnrollList> lists = new ArrayList<>();
             for (Enroll enroll : enrolls) {
                 Account account = accountMapper.selectByPrimaryKey(enroll.getUserId());
                 if (null == account) {
                     continue;
                 }
                 Course course = courseMapper.selectByPrimaryKey(enroll.getCourseId());
                 if (null == course) {
                     continue;
                 }
                 EnrollList enrollList = setEnrollListParam(enroll, account, course);
                 lists.add(enrollList);
             }
             return new Result<>(true, StatusCode.OK, "查询报名名单成功", lists);
         } else {
             return new Result<>(false, StatusCode.OK, "查询报名名单失败");
         }
     }

     /**
      * 同意报名名单审核
      *
      * @param taskRequestFacade accountId 用户id
      * @param taskRequestFacade courseId 课程id
      * @return 返回审核结果
      * @Descript 报名名单审核
      */
     @Override
     public Result agreeEnrollCheck(TaskRequestFacade taskRequestFacade) {
         /**
          * 1、如果审核成功，需要修改一下数据库的报名表的审核状态以及redis中给用户查看报名情况的审核状态
          *     如果审核都通过，把课程状态改为("满员")，根据开课时间进行下一步状态判断。
          */
         if (!checkEnroolParam(taskRequestFacade)) {
             return new Result(false, StatusCode.ERROR, "用户id，课程id，报名id，审核人不能为空");
         }
         ReentrantLock lock = new ReentrantLock();
         lock.lock();
         try {
             //设置用户课程报名 —> 审核状态为已审核
             Integer result = enrollResult(taskRequestFacade, EnrollStatus.ONE.getCode());
             if (result > 0) {
                 //进行redis的数据更新
                 AccountQueueStatus aq = (AccountQueueStatus) redisTemplate.boundHashOps(
                         RedisKey.ACCOUNT_QUEUE_STATUS.getCode() + "&" +
                                 taskRequestFacade.getAccountId()).get(taskRequestFacade.getCourseId());
                 //设置用户查看报名情况为已审核状态
                 aq.setStatus(EnrollStatus.ONE.getCode());
                 //并且写入redis
                 redisTemplate.boundHashOps(RedisKey.ACCOUNT_QUEUE_STATUS.getCode() + "&" +
                         taskRequestFacade.getAccountId()).put(taskRequestFacade.getCourseId(), aq);
                 //检查一下审核通过的报名人数，然后修改课程表的状态
                 Example example = new Example(Enroll.class);
                 Example.Criteria criteria = example.createCriteria();
                 criteria.andEqualTo("courseId", taskRequestFacade.getCourseId());
                 criteria.andEqualTo("auditStatus", EnrollStatus.ONE.getCode());
                 //统计一下当前课程审核通过的条数
                 int agreeCount = enrollMapper.selectCountByExample(example);
                 if (agreeCount >= 5) {
                     //修改课程表的状态为已满员
                     Integer courseCount = courseResult(taskRequestFacade, CourseStatus.THREE.getCode());
                     if (courseCount < 1) {
                         return new Result(false, StatusCode.OK, "同意报名名单时，修改课程状态时出现异常");
                     }
                 }
                 return new Result(true, StatusCode.OK, "同意报名名单,审核成功");
             }
             return new Result(false, StatusCode.OK, "同意报名名单,审核失败");
         } finally {
             lock.unlock();
         }

     }

     /**
      * 修改课程表状态方法封装
      */
     private Integer courseResult(TaskRequestFacade taskRequestFacade, Integer statusCode) {
         Course course = new Course();
         course.setId(taskRequestFacade.getCourseId());
         course.setCourseStatus(String.valueOf(statusCode));
         return courseMapper.updateByPrimaryKeySelective(course);
     }

     /**
      * 审核时候的方法封装
      */
     private Integer enrollResult(TaskRequestFacade taskRequestFacade, Integer statusCode) {
         Enroll enroll = new Enroll();
         enroll.setId(taskRequestFacade.getEnrollId());
         //审核状态
         enroll.setAuditStatus(String.valueOf(statusCode));
         //审核者id
         enroll.setAuditor(taskRequestFacade.getAuditor());
         //审核时间
         enroll.setAuditTime(new Date());
         return enrollMapper.updateByPrimaryKeySelective(enroll);
     }

     /**
      * 审核时验证参数是否为空
      *
      * @param taskRequestFacade
      * @return
      */
     private Boolean checkEnroolParam(TaskRequestFacade taskRequestFacade) {
         if (StringUtils.isEmpty(taskRequestFacade.getEnrollId())) {
             return false;
         }
         if (StringUtils.isEmpty(taskRequestFacade.getAccountId())) {
             return false;
         }
         if (StringUtils.isEmpty(taskRequestFacade.getCourseId())) {
             return false;
         }
         if (StringUtils.isEmpty(taskRequestFacade.getAuditor())) {
             return false;
         }
         return true;

     }

     /**
      * 拒绝报名名单审核
      *
      * @param taskRequestFacade enrollId 用户id
      * @param taskRequestFacade courseId 课程id
      * @param taskRequestFacade enrollId 报名id
      * @return 返回审核结果
      * @Descript 报名名单审核
      */
     @Override
     public Result rejectEnrollCheck(TaskRequestFacade taskRequestFacade) {
         /**
          * 1、如果审核失败，需要把课程重新添加到redis中,并且对该课程的席位数,进行一个入栈操作
          */
         if (!checkEnroolParam(taskRequestFacade)) {
             return new Result(false, StatusCode.ERROR, "用户id，课程id，报名id，审核人不能为空");
         }
         ReentrantLock lock = new ReentrantLock();
         lock.lock();
         try {
             //设置用户课程报名 —> 审核状态为审核失败
             Integer result = enrollResult(taskRequestFacade, EnrollStatus.TWO.getCode());
             if (result > 0) {
                 //把对应的用户报名状态查询出来
                 AccountQueueStatus aqs = (AccountQueueStatus) redisTemplate.boundHashOps(RedisKey.ACCOUNT_QUEUE_STATUS.getCode() + "&" +
                         taskRequestFacade.getAccountId()).get(taskRequestFacade.getCourseId());
                 //修改为审核失败
                 aqs.setStatus(EnrollStatus.TWO.getCode());
                 //把对应的用户报名状态写入redis
                 redisTemplate.boundHashOps(RedisKey.ACCOUNT_QUEUE_STATUS.getCode() + "&" +
                         taskRequestFacade.getAccountId()).put(taskRequestFacade.getCourseId(), aqs);
                 //判断一下redis的课程是否存在，不存在把课程写入redis
                 if (!redisTemplate.boundHashOps(RedisKey.COURSE_KEY.getCode()).hasKey(taskRequestFacade.getCourseId())) {
                     Course course = courseMapper.selectByPrimaryKey(taskRequestFacade.getCourseId());
                     //对课程席位进行入队
                     redisTemplate.boundListOps(RedisKey.COURSE_COUNT.getCode() + "_" +
                             taskRequestFacade.getCourseId()).leftPush(taskRequestFacade.getCourseId());
                     //把课程写入到redis
                     redisTemplate.boundHashOps(RedisKey.COURSE_KEY.getCode())
                             .put(taskRequestFacade.getCourseId(), course);
                 } else {
                     //对课程席位进行入队
                     redisTemplate.boundListOps(RedisKey.COURSE_COUNT.getCode() + "_" +
                             taskRequestFacade.getCourseId()).leftPush(taskRequestFacade.getCourseId());
                 }
                 return new Result(true, StatusCode.OK, "拒绝报名名单,审核成功");
             }
             return new Result(false, StatusCode.OK, "拒绝报名名单,审核失败");
         } finally {
             lock.unlock();
         }
     }

     /**
      * job自动审核
      * 每天凌晨1点自动执行
      */
     /** Scheduled 表达式解释
      * 字段　　允许值　　允许的特殊字符
      * 秒     　 0-59 　　　　, - * /
      * 分     　 0-59　　　　 , - * /
      * 小时      0-23 　　　　, - * /
      * 日期      1-31 　　　　, - * ? / L W C
      * 月份      1-12 　　　　, - * /
      * 星期      1-7 　　　　  , - * ? / L C #
      * 年     1970-2099 　　, - * /
      */
     @Scheduled(cron = "0 0 1 ? * * ")
     public void EnrollPushTask() {
         System.out.println("任务计划job执行了！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
         /**
          * 1、把所有的待审核状态的数据查出来。
          * 2、判断用户的必填属性合法性(true,调用同意审核接口;false:调用拒绝审核接口)
          */
         Example example = new Example(Enroll.class);
         Example.Criteria criteria = example.createCriteria();
         criteria.andEqualTo("auditStatus", EnrollStatus.ZERO);
         List<Enroll> enrolls = enrollMapper.selectByExample(example);
         if (null == enrolls || enrolls.size() <= 0) {
             throw new RuntimeException("没有待审核的数据需要处理");
         }
         for (Enroll enl : enrolls) {
             Account account = accountMapper.selectByPrimaryKey(enl.getUserId());
             //属性合法
             if (checkAccountParamIsLegal(account)) {
                 //调用同意审核方法
                 agreeEnrollCheck(new TaskRequestFacade(enl.getUserId(),enl.getCourseId(),enl.getId(),8888L));
             } else {
                 //调用拒绝审核方法
                 rejectEnrollCheck(new TaskRequestFacade(enl.getUserId(),enl.getCourseId(),enl.getId(),8888L));
             }
         }
     }

     /**
      * 封装方法，给封装类进行属性赋值
      *
      * @param enroll
      * @param account
      * @param course
      * @return
      */
     private EnrollList setEnrollListParam(Enroll enroll, Account account, Course course) {
         EnrollList enrollList = new EnrollList();
         enrollList.setAccountId(enroll.getUserId());
         enrollList.setCourseId(enroll.getCourseId());
         enrollList.setName(account.getName());
         enrollList.setIdCardNum(account.getIdCardNum());
         enrollList.setNationality(account.getNationality());
         enrollList.setAge(account.getAge());
         enrollList.setPhone(account.getPhone());
         enrollList.setStatus(account.getStatus());
         enrollList.setCourseName(course.getName());
         enrollList.setTeacher(course.getTeacher());
         enrollList.setBeginTime(course.getBeginTime());
         enrollList.setAuditStatus(enroll.getAuditStatus());
         enrollList.setEnrollTime(enroll.getEnrollTime());
         enrollList.setAuditor(enroll.getAuditor());
         return enrollList;
     }

     /**
      * 检查用户属性是否合法有效
      *
      * @param account
      * @return
      */
     private Boolean checkAccountParamIsLegal(Account account) {
         if (StringUtils.isEmpty(account.getIdCardNum()) ||
                 (!StringUtils.isEmpty(account.getIdCardNum()) &&
                         !IdCardNumUtil.validateIdCard18(account.getIdCardNum()))) {
             return false;
         }
         if (StringUtils.isEmpty(account.getPhone()) ||
                 (!StringUtils.isEmpty(account.getPhone()) && account.getPhone().length() != 11)) {
             return false;
         }
         if (StringUtils.isEmpty(account.getAge()) ||
                 (!StringUtils.isEmpty(account.getAge())
                         && (account.getAge() > 150 || account.getAge() < 0))) {
             return false;
         }
         if (StringUtils.isEmpty(account.getStatus())) {
             return false;
         }
         if (!"enable".equals(account.getStatus())) {
             return false;
         }
         return true;
     }

     /**
      * 检查课程参数主要参数是否为空
      *
      * @param course
      * @return true：false
      */
     private Boolean checkCourseParam(Course course) {
         if (StringUtils.isEmpty(course.getName())) {
             return false;
         }
         if (StringUtils.isEmpty(course.getTeacher())) {
             return false;
         }
         if (StringUtils.isEmpty(course.getBeginTime())) {
             return false;
         }
         if (StringUtils.isEmpty(course.getContent())) {
             return false;
         }
         if (StringUtils.isEmpty(course.getCourseStatus())) {
             return false;
         }
         return true;
     }
 }
