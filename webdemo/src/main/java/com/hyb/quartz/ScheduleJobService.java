package com.hyb.quartz;

import java.util.List;


/**
 * 定时任务service
 * @author yewg
 *
 */
public interface ScheduleJobService {

    /**
     * 初始化定时任务
     */
    void initScheduleJob();

    /**
     * 运行一次任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    void runOnce(String scheduleJobId);

    /**
     * 暂停任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    void pauseJob(String scheduleJobId);

    /**
     * 恢复任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    void resumeJob(String scheduleJobId);
    /**
     * 查询任务列表
     * 
     * @param scheduleJobVo
     * @return
     */
    List<ScheduleJobVo> queryList(ScheduleJobVo scheduleJobVo);

    /**
     * 获取运行中的任务列表
     *
     * @return
     */
    List<ScheduleJobVo> queryExecutingJobList();

}
