package com.jonas.myp_sb.web.myp502.dto;

public class RequestCardInfoDTO {
    private String code;
    private String cardName;
    private String cardNum;
    private String attribute;
    private String color;
    private String type;
    private String counter;
    private String powerStart;
    private String powerEnd;
    private String effect;
    private String feature;
    private String rarity;
    private String cost;
    private Boolean isTriggerEvent;
    private Boolean isDistinct;
    private Boolean isCounterForNull;
    private Boolean isDifferent;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getPowerStart() {
        return powerStart;
    }

    public void setPowerStart(String powerStart) {
        this.powerStart = powerStart;
    }

    public String getPowerEnd() {
        return powerEnd;
    }

    public void setPowerEnd(String powerEnd) {
        this.powerEnd = powerEnd;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Boolean getIsTriggerEvent() {
        return isTriggerEvent;
    }

    public void setIsTriggerEvent(Boolean isTriggerEvent) {
        this.isTriggerEvent = isTriggerEvent;
    }

    public Boolean getIsDistinct() {
        return isDistinct;
    }

    public void setIsDistinct(Boolean isDistinct) {
        this.isDistinct = isDistinct;
    }

    public Boolean getIsCounterForNull() {
        return isCounterForNull;
    }

    public void setCounterForNull(Boolean isCounterForNull) {
        this.isCounterForNull = isCounterForNull;
    }

    public Boolean getIsDifferent() {
        return isDifferent;
    }

    public void setDifferent(Boolean isDifferent) {
        this.isDifferent = isDifferent;
    }

    @Override
    public String toString() {
        return "RequestCardInfoDTO{" +
                "code='" + code + '\'' +
                ", cardName='" + cardName + '\'' +
                ", cardNum='" + cardNum + '\'' +
                ", attribute='" + attribute + '\'' +
                ", color='" + color + '\'' +
                ", type='" + type + '\'' +
                ", counter='" + counter + '\'' +
                ", powerStart='" + powerStart + '\'' +
                ", powerEnd='" + powerEnd + '\'' +
                ", effect='" + effect + '\'' +
                ", feature='" + feature + '\'' +
                ", rarity='" + rarity + '\'' +
                ", cost='" + cost + '\'' +
                ", isTriggerEvent=" + isTriggerEvent +
                ", isDistinct=" + isDistinct +
                ", isCounterForNull=" + isCounterForNull +
                ", isDifferent=" + isDifferent +
                '}';
    }
}
