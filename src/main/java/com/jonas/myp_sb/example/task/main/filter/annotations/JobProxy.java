package com.jonas.myp_sb.example.task.main.filter.annotations;

import com.jonas.myp_sb.example.task.main.scheduling.filter.TaskWorkerJobMetadataLookupStrategy;
import org.jobrunr.jobs.filters.JobFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JobProxy {
    Class<TaskWorkerJobMetadataLookupStrategy> jobMetadataLookupStrategy();

    Class<? extends JobFilter>[] jobFilters() default {};
}
