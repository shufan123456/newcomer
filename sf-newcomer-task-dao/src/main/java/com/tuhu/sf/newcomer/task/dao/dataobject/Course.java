package com.tuhu.sf.newcomer.task.dao.dataobject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:舒凡
 * @Description:sf_course课程表
 * @date 2020/12/7 19:16
 *****/
@Table(name = "sf_course")
public class Course implements Serializable {
    private static final long serialVersionUID = 0000000000001L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;//课程id

    @Column(name = "name")
    private String name;//课程名称

    @Column(name = "teacher")
    private String teacher;//课程讲师

    @Column(name = "content")
    private String content;//课程内容

    @Column(name = "course_status")
    private String courseStatus;//课程状态(0:待开课;1:开课中;2:已结束;3:满员)

    @Column(name = "begin_time")
    private Date beginTime;//开课时间

    @Column(name = "end_time")
    private Date endTime;//停课时间

    @Column(name = "creator")
    private Long creator;//创建人ID

    @Column(name = "creator_name")
    private String creatorName;//创建人名称

    @Column(name = "add_time")
    private Date addTime;//创建时间

    @Column(name = "updator")
    private Long updator;//修改人ID

    @Column(name = "updator_name")
    private String updatorName;//修改人名称

    @Column(name = "update_time")
    private Date updateTime;//修改时间


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
