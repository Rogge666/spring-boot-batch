package com.rogge.batch.module4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * [Description]
 * <p>
 * [How to use]
 * <p>
 * [Tips]
 *
 * @author Created by Rogge on 2018-02-06.
 * @since 1.0.0
 */
@Component
@EnableScheduling
@Slf4j
public class BatchComponent {

    @Resource
    private PersonProperties mPersonProperties;

    @Resource
    private JobLauncher jobLauncher;

    @Resource
    @Qualifier("c2cDataSendJob")
    private Job mJob;

    @Scheduled(cron = "0/20 * * * * ?")
    private void executeJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        log.error(mPersonProperties.getName());
        jobLauncher.run(mJob, new JobParametersBuilder().addDate("date", new Date()).toJobParameters());
    }


}
