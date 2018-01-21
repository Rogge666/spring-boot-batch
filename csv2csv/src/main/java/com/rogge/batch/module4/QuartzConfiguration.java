package com.rogge.batch.module4;

import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class QuartzConfiguration {

    @Resource
    private JobLauncher jobLauncher;

    @Resource
    private JobLocator jobLocator;


    /**
     * post处理器，能够将job在创建时自动注册进JobRegistry
     */
    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    /**
     * 管理作业任务，具体的作业任务类需要继承QuartzJobBean类
     */
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean jobFactory = new JobDetailFactoryBean();
        jobFactory.setJobClass(QuartzJobLauncher.class);
        Map<String, Object> jobDataMap = new HashMap<String, Object>();
        jobDataMap.put("jobName", "c2cDataSendJob");
        jobDataMap.put("jobLauncher", jobLauncher);
        jobDataMap.put("jobLocator", jobLocator);
        jobFactory.setJobDataAsMap(jobDataMap);
        jobFactory.setGroup("c2c_group");
        jobFactory.setName("c2c_job");
        return jobFactory;
    }


    /**
     * 基于时间刻度(可以设置具体时间)
     */
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean ctFactory = new CronTriggerFactoryBean();
        ctFactory.setJobDetail(jobDetailFactoryBean().getObject());
        ctFactory.setStartDelay(1000);    //延迟启动3秒
        ctFactory.setName("cron_trigger");
        ctFactory.setGroup("cron_group");
//        ctFactory.setCronExpression("0 0/2 * * * ? *"); //每2分钟执行一次
        ctFactory.setCronExpression("0/20 * * * * ?"); //每20秒执行一次
        return ctFactory;
    }


    /**
     * spring 通过SchedulerFactoryBean实现调度任务的配置
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(cronTriggerFactoryBean().getObject());
        return scheduler;
    }

}
