package com.jonas.myp_sb.example.task.main.mapper;

import com.jonas.myp_sb.example.task.main.model.AcsTaskDetails;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements TaskMapper{
    @Override
    public TaskDetailsTask form(AcsTaskDetails detail) {
        if ( detail == null ) {
            return null;
        }

        TaskDetailsTask taskDetailsTask = new TaskDetailsTask();

        if ( detail.getTaskId() != null ) {
            taskDetailsTask.setTaskId( detail.getTaskId() );
        }
        taskDetailsTask.setJobId( detail.getJobId() );
        taskDetailsTask.setStatus( detail.getStatus() );
        taskDetailsTask.setMessage( detail.getMessage() );
        taskDetailsTask.setDescription( detail.getDescription() );
        taskDetailsTask.setModelId( detail.getModelId() );
        taskDetailsTask.setCommitTimestamp( detail.getCommitTimestamp() );
        taskDetailsTask.setStartTimestamp( detail.getStartTimestamp() );
        taskDetailsTask.setEndTimestamp( detail.getEndTimestamp() );
        taskDetailsTask.setType( detail.getType() );
        taskDetailsTask.setCommitter( detail.getCommitter() );

        return taskDetailsTask;
    }
}
