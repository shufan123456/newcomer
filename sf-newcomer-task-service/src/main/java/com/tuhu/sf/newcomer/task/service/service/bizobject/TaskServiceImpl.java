 /*
  * Copyright 2020 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.tuhu.sf.newcomer.task.service.service.bizobject;

 import com.baomidou.mybatisplus.mapper.EntityWrapper;

 import com.tuhu.sf.newcomer.task.common.entiy.IdCardNumUtil;
 import com.tuhu.sf.newcomer.task.common.entiy.IdWorker;
 import com.tuhu.sf.newcomer.task.common.entiy.Result;
 import com.tuhu.sf.newcomer.task.common.entiy.StatusCode;
 import com.tuhu.sf.newcomer.task.dao.dataobject.Account;
 import com.tuhu.sf.newcomer.task.dao.dataobject.Course;
 import com.tuhu.sf.newcomer.task.dao.dataobject.Enroll;
 import com.tuhu.sf.newcomer.task.dao.mapper.*;
 import com.tuhu.sf.newcomer.task.facade.request.TaskRequestFacade;
 import com.tuhu.sf.newcomer.task.service.common.*;
 import com.tuhu.sf.newcomer.task.service.service.TaskService;


 import lombok.extern.slf4j.Slf4j;
 import org.springframework.data.redis.core.RedisTemplate;

 import org.springframework.scheduling.annotation.Scheduled;
 import org.springframework.stereotype.Service;

 import org.springframework.transaction.annotation.Transactional;
 import org.springframework.util.StringUtils;

 import javax.annotation.Resource;
 import java.util.*;


 /**
  * @author 舒凡
  * @date 2020/12/8 13:45
  */
 @Service
 @Slf4j(topic = "sf-newcomer-task-service")
 @SuppressWarnings("unchecked")
 public class TaskServiceImpl implements TaskService {
     /**
      * 用户Mapper
      */
     @Resource
     private AccountReadMapper accountReadMapper;
     /**
      * 课程Mapper(读)
      */
     @Resource
     private CourseReadMapper courseReadMapper;
     /**
      * 课程Mapper(写)
      */
     @Resource
     private CourseWriteMapper courseWriteMapper;
     /**
      * 报名Mapper
      */
     @Resource
     private EnrollWriteMapper enrollWriteMapper;

     @Resource
     private EnrollReadMapper enrollReadMapper;
     /**
      * redis
      */
     @Resource
     private RedisTemplate redisTemplate;

     @Resource
     private IdWorker idWorker;


     /**
      * 课程新建
      *
      * @param course pojo
      * @return Result
      * @Descript 课程名称、讲师、开课时间、课程内容、课程状态
      */
     @Override
     public Result add(Course course) {
         if (!checkCourseParam(course)) {
             return new Result(false, StatusCode.ERROR, "课程名称、讲师、开课时间、课程内容、课程状态不能为空");
         }
         if (course.getBeginTime().compareTo(new Date()) <= 0) {
             return new Result(false, StatusCode.ERROR, "开课时间不能小于当前时间");
         }
         if (!checkCourseParamConflict(course)) {
             return new Result(false, StatusCode.ERROR, "课程名字与开课时间已存在");
         }
         course.setId(idWorker.nextId());
         course.setAddTime(new Date());
         //写入mysql
         if (courseWriteMapper.insert(course) > 0) {
             return new Result(true, StatusCode.OK, "课程添加成功");
         }
         return new Result(true, StatusCode.ERROR, "课程添加失败");
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
         if (!checkCourseParam(course)) {
             return new Result(false, StatusCode.ERROR, "课程名称、讲师、开课时间、课程内容、课程状态不能为空");
         }
         if (StringUtils.isEmpty(course.getId())) {
             return new Result(false, StatusCode.ERROR, "课程id不能为空");
         }
         if (course.getBeginTime().compareTo(new Date()) <= 0) {
             return new Result(false, StatusCode.ERROR, "开课时间不能小于当前时间");
         }
         course.setUpdateTime(new Date());
         if (courseWriteMapper.updateById(course) > 0) {
             return new Result(true, StatusCode.OK, "修改课程信息成功");
         }
         return new Result(false, StatusCode.ERROR, "修改课程失败");

     }

     /**
      * 课程名字与开课时间是否冲突
      *
      * @param course
      * @return
      */
     private Boolean checkCourseParamConflict(Course course) {
         EntityWrapper<Course> entityWrapper = new EntityWrapper<>();
         entityWrapper.eq("name", course.getName());
         entityWrapper.eq("begin_time", course.getBeginTime());
         if (courseReadMapper.selectCount(entityWrapper) > 0) {
             return false;
         }
         return true;
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
     @Transactional(rollbackFor = Exception.class)
     public Result addEnroll(TaskRequestFacade taskRequestFacade) {
         if (StringUtils.isEmpty(taskRequestFacade.getAccountId())) {
             return new Result(false, StatusCode.ERROR, "用户id不能为空");
         }
         if (StringUtils.isEmpty(taskRequestFacade.getCourseId())) {
             return new Result(false, StatusCode.ERROR, "课程id不能为空");
         }
         //判断是否是一个有效的用户
         Account account = accountReadMapper.selectById(taskRequestFacade.getAccountId());
         if (account == null) {
             return new Result(false, StatusCode.ERROR, "无效的用户id");
         }
         //查看用户信息,是否为空，是否合法,是否禁用
         if (!checkAccoutParam(account)) {
             return new Result(false, StatusCode.ERROR, "请完善个人信息：姓名、证件号、手机号、国籍、年龄、身体状态");
         }
         //从数据库把课程信息查询出来
         Course course = courseReadMapper.selectById(taskRequestFacade.getCourseId());
         //是否是一个有效的课程
         //1、为空
         //2、不为空，状态不是0
         //3、席位<= 0
         //4、 开课时间小于当前时间
         if (StringUtils.isEmpty(course)
                 || (!StringUtils.isEmpty(course) && !course.getCourseStatus().equals(String.valueOf(CourseStatus.ZERO.getCode())))
                 || (!StringUtils.isEmpty(course) && course.getNum() <= 0)
                 || (!StringUtils.isEmpty(course) && course.getBeginTime().compareTo(new Date()) < 0)) {
             return new Result(false, StatusCode.ERROR, "该课程无法进行报名");
         }
         //从数据库把报名信息查询出来
         EntityWrapper<Enroll> entityWrapper = new EntityWrapper<>();
         entityWrapper.eq("course_id", taskRequestFacade.getCourseId());
         entityWrapper.eq("user_id", taskRequestFacade.getAccountId());
         Integer accoutEnrollCount = enrollReadMapper.selectCount(entityWrapper);

         if (accoutEnrollCount > 0) {
             //20007表示重复排队
             return new Result(false, StatusCode.REPEAT, "不能对同一课程重复报名");
         }
         //创建一个报名课程
         Enroll enroll = new Enroll();
         //报名id
         enroll.setId(idWorker.nextId());
         //用户id
         enroll.setUserId(taskRequestFacade.getAccountId());
         //课程id
         enroll.setCourseId(taskRequestFacade.getCourseId());
         //审核状态
         enroll.setAuditStatus(String.valueOf(EnrollStatus.ZERO.getCode()));
         //报名时间
         enroll.setEnrollTime(new Date());
         //创建时间
         enroll.setAddTime(new Date());
         //报名信息写入数据库
         Integer enroolResult = enrollWriteMapper.insert(enroll);
         //查询出当前课程版本号
         Integer courseVersion = courseReadMapper.selectVersionByCourseId(taskRequestFacade.getCourseId());
         //修改课程席位
         Integer courseResult = courseWriteMapper.decrCount(taskRequestFacade.getCourseId(), 1, courseVersion);
         return new Result(true, StatusCode.OK, "报名成功");
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
         //用户已经报名的课程
         List<Long> accountCoures = enrollReadMapper.selectCoursesByAccount(taskRequestFacade.getAccountId());
         EntityWrapper<Course> entityWrapper = new EntityWrapper<>();
         if (accountCoures == null || accountCoures.size() <= 0) {
             //过滤掉已经报过名的，并且：是待开课状态的，席位大于0的，开课时间是大于当前时间的
             entityWrapper.eq("course_status", CourseStatus.ZERO.getCode());
             entityWrapper.gt("num", 0);
             entityWrapper.gt("begin_time", new Date());
         } else {
             entityWrapper.notIn("id", accountCoures);
         }
         List<Course> courseList = courseReadMapper.selectList(entityWrapper);
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
         EntityWrapper<Enroll> enrollEntityWrapper = new EntityWrapper<>();
         enrollEntityWrapper.eq("user_id", taskRequestFacade.getAccountId());
         List<Enroll> enrolls = enrollReadMapper.selectList(enrollEntityWrapper);
         if (enrolls == null || enrolls.size() <= 0) {
             return new Result<>(false, StatusCode.ERROR, "查询失败，暂未报名");
         }
         List<AccountQueueStatus> list = new ArrayList<>();
         for (Enroll enroll : enrolls) {
             Course course = courseReadMapper.selectById(enroll.getCourseId());
             list.add(new AccountQueueStatus(enroll.getUserId(), enroll.getId(), course.getName(), course.getTeacher()
                     , course.getBeginTime(), enroll.getAddTime(), Integer.parseInt(enroll.getAuditStatus()), course.getId()));
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
         EntityWrapper<Enroll> entityWrapper = new EntityWrapper<>();
         entityWrapper.orderBy("enroll_time", true);
         List<Enroll> enrolls = enrollReadMapper.selectList(entityWrapper);
         if (enrolls != null && enrolls.size() > 0) {
             //定义返回对象
             List<EnrollList> lists = new ArrayList<>();
             for (Enroll enroll : enrolls) {
                 Account account = accountReadMapper.selectById(enroll.getUserId());
                 if (null == account) {
                     continue;
                 }
                 Course course = courseReadMapper.selectById(enroll.getCourseId());
                 if (null == course) {
                     continue;
                 }
                 //调用属性赋值的封装方法
                 EnrollList enrollList = setEnrollListParam(enroll, account, course);
                 lists.add(enrollList);
             }
             return new Result<>(true, StatusCode.OK, "查询报名名单成功", lists);
         } else {
             return new Result<>(false, StatusCode.ERROR, "暂无用户报名");
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
     @Transactional(rollbackFor = RuntimeException.class)
     public Result agreeEnrollCheck(TaskRequestFacade taskRequestFacade) {
         /**
          * 1、如果审核成功，如果审核都通过，把课程状态改为("满员")，根据开课时间进行下一步状态判断。
          */
         if (!checkEnroolParam(taskRequestFacade)) {
             return new Result(false, StatusCode.ERROR, "用户id，课程id，报名id，审核人不能为空");
         }
         Enroll enroll = enrollReadMapper.selectById(taskRequestFacade.getEnrollId());
         if (!enroll.getAuditStatus().equals(String.valueOf(EnrollStatus.ZERO.getCode()))) {
             return new Result(false, StatusCode.ERROR, "只允许修改审核中的报名名单！");
         }
         //设置用户课程报名 —> 审核状态为已审核
         Integer result = enrollResult(taskRequestFacade, EnrollStatus.ONE.getCode());
         if (result > 0) {
             //统计一下当前课程 审核中的条数
             EntityWrapper<Enroll> entityWrapper = new EntityWrapper<>();
             entityWrapper.eq("course_id", taskRequestFacade.getCourseId());
             entityWrapper.eq("audit_status", EnrollStatus.ZERO.getCode());
             Integer count = enrollReadMapper.selectCount(entityWrapper);
             //统计一下课程席位数
             Course course = courseReadMapper.selectById(taskRequestFacade.getCourseId());
             if (course.getNum() <= 0 && count <= 0) {
                 //修改课程表的状态为已满员
                 Integer courseCount = courseResult(taskRequestFacade, CourseStatus.THREE.getCode());
             }
             return new Result(true, StatusCode.OK, "同意报名名单，审核成功");
         }
         return new Result(false, StatusCode.OK, "同意报名名单，审核失败");
     }


     /**
      * 修改课程表状态方法封装
      */
     private Integer courseResult(TaskRequestFacade taskRequestFacade, Integer statusCode) {
         Integer version = courseReadMapper.selectVersionByCourseId(taskRequestFacade.getCourseId());
         return courseWriteMapper.updateCourseStatus(taskRequestFacade.getCourseId(), statusCode, version);
     }

     /**
      * 审核时候的方法封装
      */
     private Integer enrollResult(TaskRequestFacade taskRequestFacade, Integer statusCode) {
         //拿到version
         Integer enrollVersion = enrollReadMapper.selectVersionByEnrollId(taskRequestFacade.getEnrollId());
         //执行update
         return enrollWriteMapper.updateAuditStatus(taskRequestFacade.getEnrollId(), taskRequestFacade.getAuditor(),
                 statusCode, enrollVersion, new Date());
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
     @Transactional(rollbackFor = RuntimeException.class)
     public Result rejectEnrollCheck(TaskRequestFacade taskRequestFacade) {
         /**
          * 1、如果审核失败，需要对席位进行递加,把课程状态改为待开课。
          */
         if (!checkEnroolParam(taskRequestFacade)) {
             return new Result(false, StatusCode.ERROR, "用户id，课程id，报名id，审核人不能为空");
         }
         Enroll enroll = enrollReadMapper.selectById(taskRequestFacade.getEnrollId());
         if (!enroll.getAuditStatus().equals(String.valueOf(EnrollStatus.ZERO.getCode()))) {
             return new Result(false, StatusCode.ERROR, "只允许修改审核中的报名名单！");
         }
         //设置用户课程报名 —> 审核状态为审核失败
         Integer result = enrollResult(taskRequestFacade, EnrollStatus.TWO.getCode());
         if (result > 0) {
             //统计一下当前课程 审核失败的条数
             EntityWrapper<Enroll> entityWrapper = new EntityWrapper<>();
             entityWrapper.eq("course_id", taskRequestFacade.getCourseId());
             entityWrapper.eq("audit_status", EnrollStatus.TWO.getCode());
             Integer failCount = enrollReadMapper.selectCount(entityWrapper);

             //把当前课程信息查询出来
             Course course = courseReadMapper.selectById(taskRequestFacade.getCourseId());
             //得到当前课程版本号
             Integer courseVersion = course.getVersion();
             //递加席位数
             Integer courseResult = courseWriteMapper.increaseCount(taskRequestFacade.getCourseId(), 1, courseVersion);

             //如果不是待开课状态需要进行修改
             if (!course.getCourseStatus().equals(CourseStatus.ZERO.getCode())) {
                 //得到当前课程版本号
                 Integer version = course.getVersion();
                 //修改课程状态 -> 待开课
                 courseResult(taskRequestFacade, CourseStatus.ZERO.getCode());
             }
             return new Result(true, StatusCode.OK, "拒绝报名名单,审核成功");
         }
         return new Result(false, StatusCode.OK, "拒绝报名名单,审核失败");

     }

     /**
      * job自动审核
      * 每天凌晨1点自动执行
      * Scheduled 表达式解释
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
         /**
          * 1、把所有的待审核状态的数据查出来。
          * 2、判断用户的必填属性合法性(true,调用同意审核接口;false:调用拒绝审核接口)
          */

         EntityWrapper<Enroll> enrollEntityWrapper = new EntityWrapper<>();
         enrollEntityWrapper.eq("audit_status", EnrollStatus.ZERO.getCode());
         List<Enroll> enrolls = enrollReadMapper.selectList(enrollEntityWrapper);
         if (null == enrolls || enrolls.size() <= 0) {
             log.debug("nullData..{}",enrolls);
         }
         for (Enroll enl : enrolls) {
             Account account = accountReadMapper.selectById(enl.getUserId());
             //属性合法
             try{
                 if (checkAccoutParam(account)) {
                     //调用同意审核方法
                     agreeEnrollCheck(new TaskRequestFacade(enl.getUserId(), enl.getCourseId(), enl.getId(), 8888L));
                 } else {
                     //调用拒绝审核方法
                     rejectEnrollCheck(new TaskRequestFacade(enl.getUserId(), enl.getCourseId(), enl.getId(), 8888L));
                 }
             }catch (Exception e){
                 log.debug("EnrollPushTask..Exception..{}",account);
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
      * 验证一下用户报名属性是否为空以及检查用户属性是否合法有效
      *
      * @param account
      * @return
      */
     private Boolean checkAccoutParam(Account account) {
         if (StringUtils.isEmpty(account.getName())) {
             return false;
         }
         if (StringUtils.isEmpty(account.getIdCardNum())) {
             return false;
         }
         if (StringUtils.isEmpty(account.getPhone())) {
             return false;
         }
         if (StringUtils.isEmpty(account.getNationality())) {
             return false;
         }
         if (StringUtils.isEmpty(account.getAge())) {
             return false;
         }
         if (StringUtils.isEmpty(account.getStatus())) {
             return false;
         }
         //检查用户属性是否合法有效
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
