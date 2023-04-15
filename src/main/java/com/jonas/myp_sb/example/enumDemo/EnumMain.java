package com.jonas.myp_sb.example.enumDemo;

import lombok.Getter;

@Getter
public enum EnumMain implements BaseEnum{
    CHECK_STATUS(0,"審查狀態",CheckStatusEnum.class),
    USER_STATUS(1,"使用者狀態",UserStatusEnum.class)
    ;

    public final Integer value;
    public final String title;
    public final Class<? extends BaseEnum> enumClass;

    EnumMain(Integer value, String title, Class<? extends BaseEnum> enumClass) {
        this.value = value;
        this.title = title;
        this.enumClass = enumClass;
    }
}
