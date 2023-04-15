package com.jonas.myp_sb.example.enumDemo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestEnum {

    @Test
    public void getEnum(){
        Season summer = Season.SUMMER;
        System.out.println(summer);
    }

    @Test
    public void getAllEnum(){
        for (Season x : Season.values()){
            System.out.println(x);
            System.out.println(x.getCode());
            System.out.println(x.getSeasonName());
        }
    }


    @Test
    public void baseEnumTest(){
        //指定枚舉類依照value查出對應的內容
        BaseEnum baseEnum = BaseEnum.valueOf(CheckStatusEnum.class, 1);
        System.out.println(baseEnum.getTitle());
    }

    @Test
    public void baseEnumMainTest(){
        BaseEnum baseEnum = BaseEnum.valueOf(EnumMain.class, 1);
        EnumMain enumMain = (EnumMain) baseEnum;
        List<OptionVo<Integer>> options = BaseEnum.getOptions(enumMain.getEnumClass());
        Map<Integer, String> enumMap = BaseEnum.getEnumMap(enumMain.getEnumClass());
        System.out.println(options);
        System.out.println(enumMap);
    }



    /**
     * 將Code轉換成Season名稱
     * **/
    @Test
    public void changeSeasonName(){
        List<Integer> dataList = Arrays.asList(1, 1, 2, 3, 5);

        //將 Season 的枚舉值存儲在一個 Map 中，這樣可以減少每次遍歷的次數，提高效率
        Map<Integer, String> seasonMap = Arrays.stream(Season.values())
                .collect(Collectors.toMap(Season::getCode, Season::getSeasonName));

        List<String> seasonList = dataList.stream()
                .map(seasonMap::get)
                .collect(Collectors.toList());

        System.out.println(seasonList);
    }
}
