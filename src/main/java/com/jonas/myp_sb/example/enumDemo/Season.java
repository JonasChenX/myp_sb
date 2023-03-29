package com.jonas.myp_sb.example.enumDemo;

public enum Season {
    SPRING("春天",1),
    SUMMER("夏天",2),
    AUTUMN("秋天",3),
    WINTER("冬天",4);

    private final String seasonName;

    private final Integer code;

    Season(String seasonName, Integer code) {
        this.seasonName = seasonName;
        this.code = code;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public Integer getCode() {
        return code;
    }
}
