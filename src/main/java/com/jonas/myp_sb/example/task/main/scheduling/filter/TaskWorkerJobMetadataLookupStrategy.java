package com.jonas.myp_sb.example.task.main.scheduling.filter;

import com.jonas.myp_sb.example.task.main.filter.DefaultJobFiltersCompositeFilter;
import com.jonas.myp_sb.example.task.main.filter.JobMetadataLookupStrategy;
import com.jonas.myp_sb.example.task.main.filter.annotations.JobMetadata;
import com.jonas.myp_sb.example.task.main.model.AcsTaskDetails;
import com.jonas.myp_sb.example.task.main.service.AcsTaskDetailsService;
import com.jonas.myp_sb.example.task.main.worker.TaskWorker;
import com.jonas.myp_sb.example.task.main.worker.TaskWorkerRepository;
import org.jobrunr.jobs.JobDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.jonas.myp_sb.example.task.main.scheduling.filter.JobDetailsUtils.isFromTaskDetail;

@Component
public class TaskWorkerJobMetadataLookupStrategy implements JobMetadataLookupStrategy {

    private static final Logger logger = LoggerFactory.getLogger(TaskWorkerJobMetadataLookupStrategy.class);

    private final AcsTaskDetailsService acsTaskDetailsService;
    private final TaskWorkerRepository taskWorkerRepository;

    protected TaskWorkerJobMetadataLookupStrategy(AcsTaskDetailsService acsTaskDetailsService, TaskWorkerRepository taskWorkerRepository) {
        this.acsTaskDetailsService = acsTaskDetailsService;
        this.taskWorkerRepository = taskWorkerRepository;
    }

    @Override
    public Optional<JobMetadata> lookup(JobDetails jobDetails) {
        logger.info("lookup");
        if (isFromTaskDetail(jobDetails)) {
            long taskId = Integer.parseInt(jobDetails.getJobParameters().get(0).getObject().toString());
            AcsTaskDetails task = this.acsTaskDetailsService.findById(taskId);
            String workerName = task.getType();
            TaskWorker taskWorker = this.taskWorkerRepository.getWorkerInstance(workerName);
            Class<?> targetClass = ClassUtils.getUserClass(taskWorker.getClass());
            String targetMethodName = "performWorker";
            final List<JobMetadata> metadata = Arrays
                    .stream(targetClass.getDeclaredMethods())
                    .filter(method -> method.getName().equals(targetMethodName))
                    .map(method -> AnnotationUtils.getAnnotation(method, JobMetadata.class))
                    .filter(Objects::nonNull)
                    .limit(2)
                    .collect(Collectors.toList());

            if (metadata.isEmpty()) {
                throw new IllegalStateException("Not able to find 'perform' method annotated by @JobMetadata in " + targetClass);
            } else if (metadata.size() > 1) {
                throw new IllegalStateException("Found more than one 'perform' methods annotated by @JobMetadata in " + targetClass);
            }

            return metadata.stream().findFirst();
        }
        return Optional.empty();
    }
}
