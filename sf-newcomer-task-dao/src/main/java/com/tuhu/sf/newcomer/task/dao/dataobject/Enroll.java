package com.tuhu.sf.newcomer.task.dao.dataobject;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/****
 * @Author:舒凡
 * @Description:sf_enroll账户表
 * @date 2020/12/7 19:16
 *****/
@Table(name="sf_enroll")
public class Enroll implements Serializable{
	private static final long serialVersionUID = 0000000000003L;
	@Id
    @Column(name = "id")
	private Long id;//报名表id

    @Column(name = "course_id")
	private Long courseId;//课程id

    @Column(name = "user_id")
	private Long userId;//用户id

    @Column(name = "auditor")
	private Long auditor;//审核人员

    @Column(name = "audit_status")
	private String auditStatus;//审核状态(0:审核中;1:已审核；2:审核失败)

    @Column(name = "audit_time")
	private Date auditTime;//审核时间

    @Column(name = "enroll_time")
	private Date enrollTime;//报名时间

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


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAuditor() {
		return auditor;
	}

	public void setAuditor(Long auditor) {
		this.auditor = auditor;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Date getEnrollTime() {
		return enrollTime;
	}

	public void setEnrollTime(Date enrollTime) {
		this.enrollTime = enrollTime;
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
