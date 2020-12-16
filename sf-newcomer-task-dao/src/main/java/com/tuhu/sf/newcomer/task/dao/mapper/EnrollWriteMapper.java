package com.tuhu.sf.newcomer.task.dao.mapper;


import com.tuhu.base.mapper.BaseWriteMapper;
import com.tuhu.sf.newcomer.task.dao.dataobject.Enroll;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/****
 * @Author:舒凡
 * @Description:报名 Mapper 接口
 * @date 2020/12/7 19:16
 *****/
public interface EnrollWriteMapper extends BaseWriteMapper<Enroll> {

    @Update("update sf_enroll set audit_status =#{auditStatus}," +
            " auditor = #{auditor} ,audit_time =#{auditTime},version =version+1 where id=#{enrollId} and version=#{version} " )
    /**
     * 同意审核修改报名状态
     */
    Integer updateAuditStatus(@Param("enrollId") Long enrollId, @Param("auditor") Long auditor,
                              @Param("auditStatus") Integer auditStatus,
                              @Param("version") Integer version, @Param("auditTime")Date auditTime ) ;


}
