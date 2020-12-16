package com.tuhu.sf.newcomer.task.dao.dataobject;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


/****
 * @Author:舒凡
 * @Description:sf_enroll账户表
 * @date 2020/12/7 19:16
 *****/
@TableName("sf_enroll")
public class Enroll implements Serializable {
    private static final long serialVersionUID = 0000000000003L;
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;//报名表id

    @TableField("course_id")
    private Long courseId;//课程id

    @TableField("user_id")
    private Long userId;//用户id

    @TableField("auditor")
    private Long auditor;//审核人员

    @TableField("audit_status")
    private String auditStatus;//审核状态(0:审核中;1:已审核；2:审核失败)

    @TableField("audit_time")
    private Date auditTime;//审核时间

    @TableField("enroll_time")
    private Date enrollTime;//报名时间

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enroll enroll = (Enroll) o;
        return Objects.equals(id, enroll.id) &&
                Objects.equals(courseId, enroll.courseId) &&
                Objects.equals(userId, enroll.userId) &&
                Objects.equals(auditor, enroll.auditor) &&
                Objects.equals(auditStatus, enroll.auditStatus) &&
                Objects.equals(auditTime, enroll.auditTime) &&
                Objects.equals(enrollTime, enroll.enrollTime) &&
                Objects.equals(creator, enroll.creator) &&
                Objects.equals(creatorName, enroll.creatorName) &&
                Objects.equals(addTime, enroll.addTime) &&
                Objects.equals(updator, enroll.updator) &&
                Objects.equals(updatorName, enroll.updatorName) &&
                Objects.equals(updateTime, enroll.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, userId, auditor, auditStatus, auditTime, enrollTime, creator, creatorName, addTime, updator, updatorName, updateTime);
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
