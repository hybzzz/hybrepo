package com.hyb.quartz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dexcoder.commons.bean.BeanConverter;

/**
 * 定时任务
 *
 * Created by liyd on 12/19/14.
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

    /** 调度工厂Bean */
    @Autowired
    private Scheduler scheduler;

    /** 通用dao */
    @Autowired
    private ScheduleJobDao   scheduleJobDao;

    public void initScheduleJob() {
        List<ScheduleJob> scheduleJobList = scheduleJobDao.getScheduleJobList();
        if (CollectionUtils.isEmpty(scheduleJobList)) {
            return;
        }

        for (ScheduleJob scheduleJob : scheduleJobList) {
  
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobName(),
                scheduleJob.getJobGroup());

            //不存在，创建一个
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                //已存在，那么更新相应的定时设置
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    public void runOnce(String scheduleJobId) {

        ScheduleJob scheduleJob =  scheduleJobDao.getScheduleJobById(scheduleJobId);
        ScheduleUtils.runOnce(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }

    public void pauseJob(String scheduleJobId) {

        ScheduleJob scheduleJob = scheduleJobDao.getScheduleJobById(scheduleJobId);
        ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());

        
    }

    public void resumeJob(String scheduleJobId) {
        ScheduleJob scheduleJob =  scheduleJobDao.getScheduleJobById(scheduleJobId);
        ScheduleUtils.resumeJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());

      
    }


    public List<ScheduleJobVo> queryList(ScheduleJobVo scheduleJobVo) {

        List<ScheduleJob> scheduleJobs = scheduleJobDao.getScheduleJobList();

        List<ScheduleJobVo> scheduleJobVoList = BeanConverter.convert(ScheduleJobVo.class, scheduleJobs);
        try {
            for (ScheduleJobVo vo : scheduleJobVoList) {

                JobKey jobKey = ScheduleUtils.getJobKey(vo.getJobName(), vo.getJobGroup());
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                if (CollectionUtils.isEmpty(triggers)) {
                    continue;
                }

                //这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
                Trigger trigger = triggers.iterator().next();
                scheduleJobVo.setJobTrigger(trigger.getKey().getName());
                vo.setNextTime(trigger.getNextFireTime());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                vo.setStatus(triggerState.name());

                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    vo.setCronExpression(cronExpression);
                }
                
            }
        } catch (SchedulerException e) {
            
        }
        return scheduleJobVoList;
    }
	
    public List<ScheduleJobVo> queryExecutingJobList() {
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            List<ScheduleJobVo> jobList = new ArrayList<ScheduleJobVo>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                ScheduleJobVo job = new ScheduleJobVo();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setJobTrigger(trigger.getKey().getName());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
            return jobList;
        } catch (SchedulerException e) {
          
            return null;
        }

    }
}
