package com.jonas.myp_sb.example.enumDemo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * 獲取該枚舉類轉成Map
     * @param enumClass
     * @return
     */
    static Map<Integer, String> getEnumMap(Class<? extends BaseEnum> enumClass){
        BaseEnum[] enumConstants = enumClass.getEnumConstants();
        if(enumConstants != null && enumConstants.length >0){
            return Arrays.stream(enumConstants)
                    .collect(Collectors.toMap(BaseEnum::getValue, BaseEnum::getTitle));
        }
        return null;
    }

    /**
     * 獲取該枚舉類轉成OptionVo物件
     * @param enumClass
     * @return
     */
    static List<OptionVo<Integer>> getOptions(Class<? extends BaseEnum> enumClass) {
        BaseEnum[] enumConstants = enumClass.getEnumConstants();
        if (enumConstants != null && enumConstants.length > 0) {
            return Arrays.stream(enumConstants).map(enumC ->
                    OptionVo.<Integer>builder()
                            .label(enumC.getTitle())
                            .value(enumC.getValue())
                            .build()
            ).collect(Collectors.toList());
        }
        return null;
    }

}
