package com.jonas.myp_sb.example.api;

import java.util.Date;

public class Model {
    private Integer id;
    private Date dataYr;
    private String label;
    private Date dataUpdateTime;
    private String JsonData;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataYr() {
        return dataYr;
    }

    public void setDataYr(Date dataYr) {
        this.dataYr = dataYr;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getDataUpdateTime() {
        return dataUpdateTime;
    }

    public void setDataUpdateTime(Date dataUpdateTime) {
        this.dataUpdateTime = dataUpdateTime;
    }

    public String getJsonData() {
        return JsonData;
    }

    public void setJsonData(String jsonData) {
        JsonData = jsonData;
    }

    @Override
    public String toString() {
        return "model{" +
                "id=" + id +
                ", dataYr=" + dataYr +
                ", label='" + label + '\'' +
                ", dataUpdateTime=" + dataUpdateTime +
                ", JsonData='" + JsonData + '\'' +
                '}';
    }
}
