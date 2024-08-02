package com.jonas.myp_sb.web.myp502.model;

import javax.persistence.*;

@Entity
@Table(name = "onepiece_card_info")
public class OnePieceCardInfo {
    @Id
    @Column(name = "id", length = 255, nullable = false)
    private String id; //id

    @Column(name = "card_number", length = 255, nullable = false)
    private String cardNumber; //卡號

    @Column(name = "rarity", length = 255)
    private String rarity; //稀有度

    @Column(name = "type", length = 255)
    private String type; //種類

    @Column(name = "card_name", length = 255)
    private String cardName; //卡名

    @Column(name = "imageUrl", length = 255)
    private String imageUrl; //圖片連結

    @Column(name = "cost")
    private Integer cost; //費用

    @Column(name = "health")
    private Integer health; //生命

    @Column(name = "attribute", length = 255)
    private String attribute; //屬性

    @Column(name = "power")
    private Integer power; //力量

    @Column(name = "counter")
    private Integer counter; //反擊

    @Column(name = "color", length = 255)
    private String color; //顏色

    @Column(name = "feature", length = 255)
    private String feature; //特徵

    @Lob
    @Column(name = "effect")
    private String effect; //效果

    @Lob
    @Column(name = "trigger_event")
    private String triggerEvent; //觸發

    @Lob
    @Column(name = "acquisition_info")
    private String acquisitionInfo; //入手資訊

    @Lob
    @Column(name = "code")
    private String code; //分類代號

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "OnePieceCardInfo{" +
                "id='" + id + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", rarity='" + rarity + '\'' +
                ", type='" + type + '\'' +
                ", cardName='" + cardName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", cost=" + cost +
                ", health=" + health +
                ", attribute='" + attribute + '\'' +
                ", power=" + power +
                ", counter=" + counter +
                ", color='" + color + '\'' +
                ", feature='" + feature + '\'' +
                ", effect='" + effect + '\'' +
                ", triggerEvent='" + triggerEvent + '\'' +
                ", acquisitionInfo='" + acquisitionInfo + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
