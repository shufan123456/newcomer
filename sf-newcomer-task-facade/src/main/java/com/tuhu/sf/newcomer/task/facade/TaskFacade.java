 /*
  * Copyright 2020 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.tuhu.sf.newcomer.task.facade;

 import com.tuhu.sf.newcomer.task.common.entiy.Result;
 import com.tuhu.sf.newcomer.task.dao.dataobject.Course;
 import com.tuhu.sf.newcomer.task.facade.request.TaskRequestFacade;
 import org.springframework.web.bind.annotation.*;

 /**
  * @author 舒凡
  * @date 2020/12/7 19:28
  */
 @RequestMapping("/task")
 public interface TaskFacade<E> {

     /**
      * 课程新建
      *
      * @param course pojo
      * @return Result
      * @Descript 课程名称、讲师、开课时间、课程内容、课程状态
      */
     @PostMapping("/course/add")
     E add(@RequestBody(required = false) Course course);

     /**
      * 课程修改
      *
      * @param course pojo
      * @return Result
      * @Descript 课程名称、讲师、开课时间、课程内容、课程状态
      */
     @PostMapping("/course/update")
     E update(@RequestBody(required = false) Course course);

     /**
      * 运营人员对报名名单查看
      *
      * @return
      * @Descript 返回 -> 报名人员信息(姓名、证件号、手机号、国籍、年龄、身体状态)、报名的课程名称、讲师名称、开课时间、报名时间、审核状态、审核人
      */
     @GetMapping("/enroll/find")
     Result<E> findEnroll();

     /**
      * 同意报名名单审核
      *
      * @param taskRequestFacade enrollId 用户id
      * @param taskRequestFacade courseId 课程id
      * @param taskRequestFacade enrollId 报名id
      * @return 返回审核结果
      * @Descript 报名名单审核
      */
     @PostMapping("/enroll/agreeCheck")
     E agreeEnrollCheck(@RequestBody(required = false) TaskRequestFacade taskRequestFacade);

     /**
      * 拒绝报名名单审核
      *
      * @param taskRequestFacade enrollId 用户id
      * @param taskRequestFacade courseId 课程id
      * @param taskRequestFacade enrollId 报名id
      * @return 返回审核结果
      * @Descript 报名名单审核
      */
     @PostMapping("/enroll/rejectCheck")
     E rejectEnrollCheck(@RequestBody(required = false) TaskRequestFacade taskRequestFacade);

     /**
      * 用户查看位课程(未报名的)
      *
      * @param taskRequestFacade accountId 用户id
      * @return 返回未报名的课程结果
      * @Descript
      */
     @PostMapping("/user/findCourse")
     Result<E> findCourse(@RequestBody(required = false) TaskRequestFacade taskRequestFacade);

     /**
      * 查看报名情况
      *
      * @param taskRequestFacade accountId 用户id
      * @return
      * @Descript 课程名称、讲师名称、开课时间、报名时间、审核状态
      */
     @PostMapping("/user/findEnroll")
     Result<E> findEnrollByAccountId(@RequestBody(required = false) TaskRequestFacade taskRequestFacade);

     /**
      * 用户报名
      *
      * @param taskRequestFacade accountId 用户id
      * @param taskRequestFacade courseId 课程id
      * @return
      * @Descript
      */
     @PostMapping("/user/addEnroll")
     E addEnroll(@RequestBody(required = false) TaskRequestFacade taskRequestFacade);


 }
