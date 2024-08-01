package com.jonas.myp_sb.web.myp502.dto;

public class ResponseFeatureOptionDTO {
    private String featureName;
    private String featureCode;

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    @Override
    public String toString() {
        return "ResponseFeatureOptionDTO{" +
                "featureName='" + featureName + '\'' +
                ", featureCode='" + featureCode + '\'' +
                '}';
    }
}
