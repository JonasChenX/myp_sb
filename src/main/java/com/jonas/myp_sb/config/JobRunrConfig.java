package com.jonas.myp_sb.config;

//import com.cht.commons.scheduling.filter.DefaultJobFiltersCompositeFilter;
import com.jonas.myp_sb.example.task.main.filter.DefaultJobFiltersCompositeFilter;
import org.jobrunr.jobs.filters.JobFilter;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.server.BackgroundJobServer;
import org.jobrunr.server.BackgroundJobServerConfiguration;
import org.jobrunr.server.JobActivator;
import org.jobrunr.storage.StorageProvider;
import org.jobrunr.utils.mapper.JsonMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class JobRunrConfig {

    //
    @Bean
    public JobScheduler jobScheduler(StorageProvider storageProvider, DefaultJobFiltersCompositeFilter compositeFilter) {
        JobScheduler jobScheduler = new JobScheduler(storageProvider, Collections.singletonList((JobFilter) compositeFilter));
        BackgroundJob.setJobScheduler(jobScheduler);
        return jobScheduler;
    }

    @Bean
    public DefaultJobFiltersCompositeFilter defaultJobFiltersComposite(ApplicationContext applicationContext) {
        return new DefaultJobFiltersCompositeFilter(applicationContext);
    }

    //
    @Bean
    public BackgroundJobServer backgroundJobServer(StorageProvider storageProvider, JsonMapper jsonMapper, JobActivator jobActivator, BackgroundJobServerConfiguration backgroundJobServerConfiguration, DefaultJobFiltersCompositeFilter compositeFilter) {
        BackgroundJobServer backgroundJobServer = new BackgroundJobServer(storageProvider, jsonMapper, jobActivator, backgroundJobServerConfiguration);
        backgroundJobServer.setJobFilters(Collections.singletonList(compositeFilter));
        backgroundJobServer.start();
        return backgroundJobServer;
    }

}
