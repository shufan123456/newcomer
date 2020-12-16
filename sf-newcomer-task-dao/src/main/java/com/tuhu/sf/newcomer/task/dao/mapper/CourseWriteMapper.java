 /*
  * Copyright 2020 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.tuhu.sf.newcomer.task.dao.mapper;

 import com.tuhu.base.mapper.BaseWriteMapper;
 import com.tuhu.sf.newcomer.task.dao.dataobject.Course;
 import org.apache.ibatis.annotations.Param;
 import org.apache.ibatis.annotations.Update;

 /**
  * @author 舒凡
  * @date 2020/12/14 18:20
  */
 public interface CourseWriteMapper extends BaseWriteMapper<Course> {

  /**
   * 课程状态修改
   */
  @Update(" update sf_course set version = version + 1,course_status = #{courseStatus} where id = #{courseId} and version = #{version}")
  Integer updateCourseStatus(@Param(value = "courseId")Long courseId,@Param(value = "courseStatus") Integer courseStatus
          ,@Param(value = "version") Integer version);

  /**
   * 课程席位递减
   */
  @Update(" update sf_course set num= num - #{num} , version = version + 1 where id = #{courseId} and version = #{version} and num >= #{num}")
  Integer decrCount(@Param(value = "courseId")Long courseId, @Param(value = "num") Integer num,@Param(value = "version") Integer version);


  /**
   * 课程席位递加
   */
  @Update(" update sf_course set num= num + #{num} , version = version + 1 where id = #{courseId} and version = #{version}")
  Integer increaseCount(@Param(value = "courseId")Long courseId, @Param(value = "num") Integer num,@Param(value = "version") Integer version);

 }
