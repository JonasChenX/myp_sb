package com.jonas.myp_sb.web.myp502.dto;

public class ResponseEffectOptionDTO {
    private String effectName;
    private String effectCode;

    public String getEffectName() {
        return effectName;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

    public String getEffectCode() {
        return effectCode;
    }

    public void setEffectCode(String effectCode) {
        this.effectCode = effectCode;
    }

    @Override
    public String toString() {
        return "ResponseEffectOptionDTO{" +
                "effectName='" + effectName + '\'' +
                ", effectCode='" + effectCode + '\'' +
                '}';
    }
}
