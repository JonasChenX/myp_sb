package com.jonas.myp_sb.example.enumDemo;

public interface BaseEnum {

    Integer getValue();
    String getTitle();

    /**
     * 通過value 獲取枚舉物件
     * @param enumClass
     * @param value
     * @return
     */
    static BaseEnum valueOf(Class<? extends BaseEnum> enumClass, Integer value){
        if(value == null){
            return null;
        }
        BaseEnum[] enumConstants = enumClass.getEnumConstants();
        if(enumConstants != null && enumConstants.length >0){
            for (BaseEnum enumConstant : enumConstants){
                if(value.equals(enumConstant.getValue())){
                    return enumConstant;
                }
            }
        }
        return null;
    }
}
