package com.jonas.myp_sb.web.myp502.dto;

import java.util.List;

public class ResponseCardInfoDTO {
    private String cardNumber; //卡號
    private String rarity; //稀有度
    private String type; //種類
    private String cardName; //卡名
    private String imageUrl; //圖片連結
    private String cost; //費用
    private String health; //生命
    private List attribute; //屬性
    private String power; //力量
    private String counter; //反擊
    private List color; //顏色
    private List feature; //特徵
    private String effect; //效果
    private String triggerEvent; //觸發
    private String acquisitionInfo; //入手資訊

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public List getAttribute() {
        return attribute;
    }

    public void setAttribute(List attribute) {
        this.attribute = attribute;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public List getColor() {
        return color;
    }

    public void setColor(List color) {
        this.color = color;
    }

    public List getFeature() {
        return feature;
    }

    public void setFeature(List feature) {
        this.feature = feature;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getTriggerEvent() {
        return triggerEvent;
    }

    public void setTriggerEvent(String triggerEvent) {
        this.triggerEvent = triggerEvent;
    }

    public String getAcquisitionInfo() {
        return acquisitionInfo;
    }

    public void setAcquisitionInfo(String acquisitionInfo) {
        this.acquisitionInfo = acquisitionInfo;
    }

    @Override
    public String toString() {
        return "ResponseCardInfoDTO{" +
                "cardNumber='" + cardNumber + '\'' +
                ", rarity='" + rarity + '\'' +
                ", type='" + type + '\'' +
                ", cardName='" + cardName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", cost='" + cost + '\'' +
                ", health='" + health + '\'' +
                ", attribute='" + attribute + '\'' +
                ", power='" + power + '\'' +
                ", counter='" + counter + '\'' +
                ", color='" + color + '\'' +
                ", feature='" + feature + '\'' +
                ", effect='" + effect + '\'' +
                ", triggerEvent='" + triggerEvent + '\'' +
                ", acquisitionInfo='" + acquisitionInfo + '\'' +
                '}';
    }
}
