package com.jonas.myp_sb.example.task.main.model;

import com.jonas.myp_sb.example.task.main.context.TaskResult;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "worker_model_result")
@ToString
public class AcsModelResult implements Serializable, TaskResult {

    @Id
    private Long taskId;

    private String modelType;

    private String resultPage;

    private String taxpayerList;

    private Float resultCount;

    private String parameterDetail;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getResultPage() {
        return resultPage;
    }

    public void setResultPage(String resultPage) {
        this.resultPage = resultPage;
    }

    public String getTaxpayerList() {
        return taxpayerList;
    }

    public void setTaxpayerList(String taxpayerList) {
        this.taxpayerList = taxpayerList;
    }

    public Float getResultCount() {
        return resultCount;
    }

    public void setResultCount(Float resultCount) {
        this.resultCount = resultCount;
    }

    public String getParameterDetail() {
        return parameterDetail;
    }

    public void setParameterDetail(String parameterDetail) {
        this.parameterDetail = parameterDetail;
    }
}
