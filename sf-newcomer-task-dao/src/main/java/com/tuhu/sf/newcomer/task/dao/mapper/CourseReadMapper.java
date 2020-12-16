package com.tuhu.sf.newcomer.task.dao.mapper;
import com.tuhu.base.mapper.BaseReadMapper;
import com.tuhu.sf.newcomer.task.dao.dataobject.Course;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/****
 * @Author:舒凡
 * @Description:课程 Mapper 接口
 * @date 2020/12/7 19:16
 *****/
public interface CourseReadMapper extends BaseReadMapper<Course> {

    /**
     * 查询课程id对应的版本号
     * @param courseId
     * @return
     */
    @Select("select version from sf_course where id = #{courseId}")
    Integer selectVersionByCourseId(@Param("courseId") Long courseId);


}
