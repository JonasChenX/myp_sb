package com.jonas.myp_sb.example.enumDemo;

import lombok.Getter;

@Getter
public enum UserStatusEnum implements BaseEnum{

    ACTIVE(0, "有效"),
    DISABLED(1, "禁用"),
    LOG_OUT(2, "註銷"),
    ;

    public final Integer value;
    public final String title;

    UserStatusEnum(Integer value, String title) {
        this.value = value;
        this.title = title;
    }
}
