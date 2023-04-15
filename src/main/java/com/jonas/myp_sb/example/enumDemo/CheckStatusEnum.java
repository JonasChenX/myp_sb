package com.jonas.myp_sb.example.enumDemo;

import lombok.Getter;

@Getter
public enum CheckStatusEnum implements BaseEnum {

    PENDING(0,"審核"),
    PASS(1,"通過"),
    FAIL(2,"失敗")
    ;

    public final Integer value;
    public final String title;

    CheckStatusEnum(Integer value, String title) {
        this.value = value;
        this.title = title;
    }
}
