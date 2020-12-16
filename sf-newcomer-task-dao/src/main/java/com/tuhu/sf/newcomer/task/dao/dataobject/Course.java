package com.tuhu.sf.newcomer.task.dao.dataobject;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/****
 * @Author:舒凡
 * @Description:sf_course课程表
 * @date 2020/12/7 19:16
 *****/
@TableName("sf_course")
public class Course implements Serializable {
    private static final long serialVersionUID = 0000000000001L;

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;//课程id

    @TableField("name")
    private String name;//课程名称

    @TableField("teacher")
    private String teacher;//课程讲师

    @TableField("content")
    private String content;//课程内容

    @TableField("course_status")
    private String courseStatus;//课程状态(0:待开课;1:开课中;2:已结束;3:满员)

    @TableField("begin_time")
    private Date beginTime;//开课时间

    @TableField("end_time")
    private Date endTime;//停课时间

    @TableField("creator")
    private Long creator;//创建人ID

    @TableField("creator_name")
    private String creatorName;//创建人名称

    @TableField("add_time")
    private Date addTime;//创建时间

    @TableField("updator")
    private Long updator;//修改人ID

    @TableField("updator_name")
    private String updatorName;//修改人名称

    @TableField("update_time")
    private Date updateTime;//修改时间

    @TableField("num")
    private Integer num;//席位数量

    @TableField("version")
    private Integer version;//版本号


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", content='" + content + '\'' +
                ", courseStatus='" + courseStatus + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", creator=" + creator +
                ", creatorName='" + creatorName + '\'' +
                ", addTime=" + addTime +
                ", updator=" + updator +
                ", updatorName='" + updatorName + '\'' +
                ", updateTime=" + updateTime +
                ", num=" + num +
                ", version=" + version +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) &&
                Objects.equals(name, course.name) &&
                Objects.equals(teacher, course.teacher) &&
                Objects.equals(content, course.content) &&
                Objects.equals(courseStatus, course.courseStatus) &&
                Objects.equals(beginTime, course.beginTime) &&
                Objects.equals(endTime, course.endTime) &&
                Objects.equals(creator, course.creator) &&
                Objects.equals(creatorName, course.creatorName) &&
                Objects.equals(addTime, course.addTime) &&
                Objects.equals(updator, course.updator) &&
                Objects.equals(updatorName, course.updatorName) &&
                Objects.equals(updateTime, course.updateTime) &&
                Objects.equals(num, course.num) &&
                Objects.equals(version, course.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, teacher, content, courseStatus, beginTime, endTime, creator, creatorName, addTime, updator, updatorName, updateTime, num, version);
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Long getUpdator() {
        return updator;
    }

    public void setUpdator(Long updator) {
        this.updator = updator;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
