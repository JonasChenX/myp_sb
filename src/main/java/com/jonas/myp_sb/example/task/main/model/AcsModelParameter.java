package com.jonas.myp_sb.example.task.main.model;

import com.jonas.myp_sb.example.task.main.context.TaskParameter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "worker_model_parameter")
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "MODEL_TYPE", discriminatorType = DiscriminatorType.STRING)
public class AcsModelParameter implements Serializable, TaskParameter {

    @Id
    private Long taskId;

    private String modelId;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
}
