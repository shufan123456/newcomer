package com.tuhu.sf.newcomer.task.dao.dataobject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/****
 * @Author:舒凡
 * @Description:sf_course课程表
 * @date 2020/12/7 19:16
 *****/
@Table(name="sf_course")
public class Course implements Serializable{
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


	//get方法
	public Long getId() {
		return id;
	}

	//set方法
	public void setId(Long id) {
		this.id = id;
	}
	//get方法
	public String getName() {
		return name;
	}

	//set方法
	public void setName(String name) {
		this.name = name;
	}
	//get方法
	public String getTeacher() {
		return teacher;
	}

	//set方法
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	//get方法
	public String getContent() {
		return content;
	}

	//set方法
	public void setContent(String content) {
		this.content = content;
	}
	//get方法
	public String getCourseStatus() {
		return courseStatus;
	}

	//set方法
	public void setCourseStatus(String courseStatus) {
		this.courseStatus = courseStatus;
	}
	//get方法
	public Date getBeginTime() {
		return beginTime;
	}

	//set方法
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	//get方法
	public Date getEndTime() {
		return endTime;
	}

	//set方法
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	//get方法
	public Long getCreator() {
		return creator;
	}

	//set方法
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	//get方法
	public String getCreatorName() {
		return creatorName;
	}

	//set方法
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	//get方法
	public Date getAddTime() {
		return addTime;
	}

	//set方法
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	//get方法
	public Long getUpdator() {
		return updator;
	}

	//set方法
	public void setUpdator(Long updator) {
		this.updator = updator;
	}
	//get方法
	public String getUpdatorName() {
		return updatorName;
	}

	//set方法
	public void setUpdatorName(String updatorName) {
		this.updatorName = updatorName;
	}
	//get方法
	public Date getUpdateTime() {
		return updateTime;
	}

	//set方法
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


}
