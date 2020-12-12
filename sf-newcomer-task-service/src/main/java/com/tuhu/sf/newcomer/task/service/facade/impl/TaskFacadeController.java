 /*
  * Copyright 2020 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.tuhu.sf.newcomer.task.service.facade.impl;

 import com.tuhu.sf.newcomer.task.common.entiy.Result;
 import com.tuhu.sf.newcomer.task.common.entiy.StatusCode;
 import com.tuhu.sf.newcomer.task.dao.dataobject.Course;
 import com.tuhu.sf.newcomer.task.facade.TaskFacade;
 import com.tuhu.sf.newcomer.task.facade.request.TaskRequestFacade;
 import com.tuhu.sf.newcomer.task.service.common.AccountQueueStatus;
 import com.tuhu.sf.newcomer.task.service.common.EnrollList;
 import com.tuhu.sf.newcomer.task.service.service.TaskService;
 import org.springframework.util.StringUtils;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RestController;

 import javax.annotation.Resource;
 import java.util.List;

 /**
  * @author 舒凡
  * @date 2020/12/8 13:43
  */
 @RestController
 public class TaskFacadeController implements TaskFacade {
     @Resource
     private TaskService taskService;

     /**
      * 课程新建
      *
      * @param course pojo
      * @return Result
      * @Descript 课程名称、讲师、开课时间、课程内容、课程状态
      */
     @Override
     public Result add(@RequestBody(required = false) Course course) {
         return StringUtils.isEmpty(course) ? CheckRequestParam() : taskService.add(course);
     }

     /**
      * 课程修改
      *
      * @param course pojo
      * @return Result
      * @Descript 课程名称、讲师、开课时间、课程内容、课程状态
      */
     @Override
     public Result update(@RequestBody(required = false) Course course) {
         return StringUtils.isEmpty(course) ? CheckRequestParam() : taskService.update(course);
     }

     /**
      * 运营人员对报名名单查看
      *
      * @return
      * @Descript 返回 -> 报名人员信息(姓名、证件号、手机号、国籍、年龄、身体状态)、报名的课程名称、讲师名称、开课时间、报名时间、审核状态、审核人
      */
     @Override
     public Result<List<EnrollList>> findEnroll() {
         return taskService.findEnroll();
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
     public Result AgreeEnrollCheck(@RequestBody(required = false) TaskRequestFacade taskRequestFacade) {
         return StringUtils.isEmpty(taskRequestFacade) ? CheckRequestParam() : taskService.agreeEnrollCheck(taskRequestFacade);
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
     public Result RejectEnrollCheck(@RequestBody(required = false) TaskRequestFacade taskRequestFacade) {
         return StringUtils.isEmpty(taskRequestFacade) ? CheckRequestParam() : taskService.rejectEnrollCheck(taskRequestFacade);
     }

     /**
      * 用户查看位课程(未报名的)
      *
      * @param taskRequestFacade accountId 用户id
      * @return 返回未报名的课程结果
      * @Descript
      */
     @Override
     public Result<List<Course>> findCourse(@RequestBody(required = false) TaskRequestFacade taskRequestFacade) {
         return StringUtils.isEmpty(taskRequestFacade) ? CheckRequestParam() : taskService.findCourse(taskRequestFacade);
     }

     /**
      * 查看报名情况
      *
      * @param taskRequestFacade accountId 用户id
      * @return
      * @Descript 课程名称、讲师名称、开课时间、报名时间、审核状态
      */
     @Override
     public Result<List<AccountQueueStatus>> findEnrollByAccountId(@RequestBody(required = false) TaskRequestFacade taskRequestFacade) {
         return StringUtils.isEmpty(taskRequestFacade) ? CheckRequestParam() : taskService.findEnrollByAccountId(taskRequestFacade);
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
     public Result addEnroll(@RequestBody(required = false) TaskRequestFacade taskRequestFacade) {
         return StringUtils.isEmpty(taskRequestFacade) ? CheckRequestParam() : taskService.addEnroll(taskRequestFacade);
     }

     /**
      * 返回一个入参对象为空的方法
      *
      * @return
      */
     private Result CheckRequestParam() {
         return new Result(false, StatusCode.ERROR, "入参对象为空");
     }
 }
