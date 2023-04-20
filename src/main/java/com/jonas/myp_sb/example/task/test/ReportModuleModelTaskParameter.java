package com.jonas.myp_sb.example.task.test;

import com.jonas.myp_sb.example.task.main.model.AcsModelParameter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Module")
@ToString
public class ReportModuleModelTaskParameter extends AcsModelParameter {

    private String parameters;

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
}
