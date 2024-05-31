package com.jonas.myp_sb.example.task.main.filter;

import com.jonas.myp_sb.example.task.main.filter.annotations.DefaultJobFilter;
import com.jonas.myp_sb.example.task.main.filter.annotations.JobMetadata;
import com.jonas.myp_sb.example.task.main.filter.annotations.JobProxy;
import org.jobrunr.jobs.AbstractJob;
import org.jobrunr.jobs.Job;
import org.jobrunr.jobs.JobDetails;
import org.jobrunr.jobs.filters.*;
import org.jobrunr.jobs.states.JobState;
import org.jobrunr.utils.JobUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultJobFiltersCompositeFilter implements ElectStateFilter, ApplyStateFilter, JobClientFilter, JobServerFilter{
    private static final Logger logger = LoggerFactory.getLogger(DefaultJobFiltersCompositeFilter.class);
    private final ApplicationContext context;
    private final Collection<Object> defaultJobFilters;
    private final Map<Class<?>, Object> jobFilterPool = new ConcurrentHashMap();

    public DefaultJobFiltersCompositeFilter(ApplicationContext context) {
        this.context = context;
        Stream var10001 = context.getBeansWithAnnotation(DefaultJobFilter.class).values().stream();
        JobFilter.class.getClass();
        this.defaultJobFilters = (Collection)var10001.filter(JobFilter.class::isInstance).filter((jobFilter) -> {
            return !(jobFilter instanceof DefaultJobFiltersCompositeFilter);
        }).peek((jobFilter) -> {
            logger.debug("Found default JobFilter : {}", jobFilter.getClass().getName());
        }).collect(Collectors.toList());
    }

    public void onCreating(AbstractJob abstractJob) {
        logger.debug("Invoke GenericFilter.onCreating {}", abstractJob.getId());
        this.triggerAllFilters(abstractJob.getJobDetails(), JobClientFilter.class, (filter) -> {
            filter.onCreating(abstractJob);
        });
    }

    public void onCreated(AbstractJob abstractJob) {
        logger.debug("Invoke GenericFilter.onCreated {}", abstractJob.getId());
        this.triggerAllFilters(abstractJob.getJobDetails(), JobClientFilter.class, (filter) -> {
            filter.onCreated(abstractJob);
        });
    }

    public void onProcessing(Job job) {
        logger.debug("Invoke GenericFilter.onProcessing {}", job.getId());
        this.triggerAllFilters(job.getJobDetails(), JobServerFilter.class, (filter) -> {
            filter.onProcessing(job);
        });
    }

    public void onProcessed(Job job) {
        logger.debug("Invoke GenericFilter.onProcessed {}", job.getId());
        this.triggerAllFilters(job.getJobDetails(), JobServerFilter.class, (filter) -> {
            filter.onProcessed(job);
        });
    }

    public void onStateApplied(Job job, JobState jobState, JobState jobState1) {
        logger.debug("Invoke GenericFilter.onStateApplied {}", job.getId());
        this.triggerAllFilters(job.getJobDetails(), ApplyStateFilter.class, (filter) -> {
            filter.onStateApplied(job, jobState, jobState1);
        });
    }

    public void onStateElection(Job job, JobState jobState) {
        logger.debug("Invoke GenericFilter.onStateElection {}", job.getId());
        this.triggerAllFilters(job.getJobDetails(), ElectStateFilter.class, (filter) -> {
            filter.onStateElection(job, jobState);
        });
    }

    private <T> void triggerAllFilters(JobDetails jobDetails, Class<T> filterType, Consumer<T> consumer) {
        this.triggerDefaultJobFilters(filterType, consumer);
        this.triggerJobProxyFilters(jobDetails, filterType, consumer);
        this.triggerJobMetadataFilters(jobDetails).ifPresent((jobMetadata) -> {
            this.triggerJobFilter(jobMetadata, filterType, consumer);
        });
    }

    private <T> void triggerDefaultJobFilters(Class<T> clazz, Consumer<T> consumer) {
        this.triggerJobFilter(this.defaultJobFilters.stream(), clazz, consumer);
    }

    private <T> void triggerJobProxyFilters(JobDetails jobDetails, Class<T> clazz, Consumer<T> consumer) {
        Optional.ofNullable(JobUtils.getJobMethod(jobDetails).getAnnotation(JobProxy.class)).ifPresent((jobProxy) -> {
            this.triggerJobFilter(jobProxy.jobFilters(), clazz, consumer);
        });
    }

    private Optional<JobMetadata> triggerJobMetadataFilters(JobDetails jobDetails) {
        Method method = JobUtils.getJobMethod(jobDetails);
        if (method.isAnnotationPresent(JobProxy.class)) {
            Class<? extends JobMetadataLookupStrategy> finder = ((JobProxy)method.getAnnotation(JobProxy.class)).jobMetadataLookupStrategy();
            return ((JobMetadataLookupStrategy)this.getOrCreateBean(finder)).lookup(jobDetails);
        } else {
            return Optional.ofNullable(method.getAnnotation(JobMetadata.class));
        }
    }

    private <T> void triggerJobFilter(JobMetadata jobMetadata, Class<T> clazz, Consumer<T> consumer) {
        this.triggerJobFilter(jobMetadata.jobFilters(), clazz, consumer);
    }

    private <T> void triggerJobFilter(Class<?>[] jobFilters, Class<T> clazz, Consumer<T> consumer) {
        this.triggerJobFilter(Arrays.stream(jobFilters).map(this::getOrCreateBean), clazz, consumer);
    }

    private <T> void triggerJobFilter(Stream<Object> jobFilters, Class<T> clazz, Consumer<T> consumer) {
        clazz.getClass();
        Stream var10000 = jobFilters.filter(clazz::isInstance);
        clazz.getClass();
        var10000.map(clazz::cast).peek((filter) -> {
            logger.debug("\ttrigger {}", filter);
        }).forEach(this.catchThrowable(consumer));
    }

    private <T> Object getOrCreateBean(Class<T> type) {
        Optional var10000 = Optional.ofNullable(this.jobFilterPool.get(type));
        type.getClass();
        return var10000.map(type::cast).orElseGet(() -> {
            T bean = this.context.getAutowireCapableBeanFactory().createBean(type);
            this.jobFilterPool.put(type, bean);
            return bean;
        });
    }

    private <T> Consumer<T> catchThrowable(Consumer<T> consumer) {
        return (jobClientFilter) -> {
            try {
                consumer.accept(jobClientFilter);
            } catch (Exception var3) {
                logger.error("Error evaluating jobFilter {}", jobClientFilter.getClass().getName(), var3);
            }

        };
    }

}
