package com.tuhu.sf.newcomer.task.dao.mapper;


import com.tuhu.base.mapper.BaseReadMapper;
import com.tuhu.base.mapper.BaseWriteMapper;
import com.tuhu.sf.newcomer.task.dao.dataobject.Enroll;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/****
 * @Author:舒凡
 * @Description:报名 Mapper 接口
 * @date 2020/12/7 19:16
 *****/
public interface EnrollReadMapper extends BaseReadMapper<Enroll> {

    /**
     * 返回一个课程集合
     */
    @Select("select distinct(course_id) from sf_enroll where user_id = #{userId} ")
    List<Long> selectCoursesByAccount(@Param(value = "userId") Long userId);

    /**
     * 查看报名id对应版本号
     */
    @Select("select version from sf_enroll where id = #{enrollId}")
    Integer selectVersionByEnrollId(@Param("enrollId") Long enrollId);


}
