package com.jonas.myp_sb.example.task.main.filter;

import com.jonas.myp_sb.example.task.main.filter.annotations.JobMetadata;
import org.jobrunr.jobs.JobDetails;

import java.util.Optional;

public interface JobMetadataLookupStrategy {
    Optional<JobMetadata> lookup(JobDetails jobDetails);
}
