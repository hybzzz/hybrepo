package com.hyb.quartz;

import com.hyb.anno.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by admin on 2018/4/4.
 */
@Mapper
public interface ScheduleJobDao {
    @Results({
            @Result(column="schedule_job_id",property="scheduleJobId"),
            @Result(column="job_name",property="jobName"),
            @Result(column="alias_name",property="aliasName"),
            @Result(column="job_group",property="jobGroup"),
            @Result(column="job_trigger",property="jobTrigger"),
            @Result(column="cron_expression",property="cronExpression"),
            @Result(column="is_sync",property="isSync"),
            @Result(column="gmt_create",property="gmtCreate"),
            @Result(column="gmt_modify",property="gmtModify")
    })
    @Select("select * from schedule_job where status='1'")
    List<ScheduleJob> getScheduleJobList();
    @Results({
            @Result(column="schedule_job_id",property="scheduleJobId"),
            @Result(column="job_name",property="jobName"),
            @Result(column="alias_name",property="aliasName"),
            @Result(column="job_group",property="jobGroup"),
            @Result(column="job_trigger",property="jobTrigger"),
            @Result(column="cron_expression",property="cronExpression"),
            @Result(column="is_sync",property="isSync"),
            @Result(column="gmt_create",property="gmtCreate"),
            @Result(column="gmt_modify",property="gmtModify")
    })
    @Select("select * from schedule_job where SCHEDULE_JOB_ID=#{_parameter}")
    ScheduleJob getScheduleJobById(String scheduleJobId);
}
