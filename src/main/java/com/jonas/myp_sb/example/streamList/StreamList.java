package com.jonas.myp_sb.example.streamList;

import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.stream.Collectors;

public class StreamList {

    /**
     * 篩選出國文成績大於60
     * @param gradeList 成績列表
     * @return 國文成績大於60的列表
     * */
    public List<Grade> filterByStream(List<Grade> gradeList){
        return gradeList.stream()
                .filter(Objects::nonNull) // 過濾掉為Null
                .filter(grade -> grade.getChineseScore() > 60)
                .collect(Collectors.toList());
    }

    /**
     * 按照國文成績排列
     * @param gradeList 成績列表
     * @return 國文成績大於60的列表
     * */
    public List<Grade> sortByStream(List<Grade> gradeList){
        return gradeList.stream()
                .filter(Objects::nonNull) // 過濾掉為Null
                .sorted((Comparator.comparingDouble(Grade::getChineseScore))) //由小到大
                .collect(Collectors.toList());
    }

    /**
     * 按照班級分組(獲取每個班級的成績列表)
     * @param gradeList 成績列表
     * @return 依照班級分組,返回Map
     * */
    public Map<String,List<Grade>> groupByStream(List<Grade> gradeList){
        return gradeList.stream()
                .filter(Objects::nonNull) // 過濾掉為Null
                .collect(Collectors.groupingBy(Grade::getStuClass));
    }

    /**
     * 國文成績的平均分數
     * @param gradeList 成績列表
     * @return 國文成績的平均分
     * */
    public Double avgByStream(List<Grade> gradeList){
        return gradeList.stream()
                .filter(Objects::nonNull) // 過濾掉為Null
                .mapToDouble(Grade::getChineseScore)
                .average()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * 獲取class名稱
     * @param gradeList 成績列表
     * @return class名稱列表
     */
    public List<String> getClassByStream(List<Grade> gradeList){
        return gradeList.stream()
                .filter(Objects::nonNull) // 過濾掉為Null
                .map( grade -> grade.getStuClass())
                .distinct() //去重
                .collect(Collectors.toList());
    }

    /**
     * 獲取國文成績前三名
     * @param gradeList 成績列表
     * @return 國文成績前三名
     */
    public List<Grade> getScoreByStream(List<Grade> gradeList){
        return gradeList.stream()
                .filter(Objects::nonNull) // 過濾掉為Null
                .sorted(Comparator.comparingDouble(Grade::getChineseScore).reversed()) //由大到小
                .limit(3)
                .collect(Collectors.toList());
    }

    /**
     * 獲取興趣 (不能出現,格式)
     * @param gradeList 成績列表
     * @return 興趣列表
     */
    public List<String> getInterestByStream(List<Grade> gradeList){
        return gradeList.stream()
                .filter(Objects::nonNull) // 過濾掉為Null
                .flatMap(grade -> Arrays.stream(grade.getInterest().split(",")))
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 判斷是否有該學生
     * @param gradeList 成績列表
     * @param  name 學生名字
     * @return Boolean
     */
    public Boolean isNameByStream(List<Grade> gradeList, String name){
        return gradeList.stream()
                .filter(Objects::nonNull)
                .anyMatch(grade -> name.equals(grade.getStuName())); //其中一個匹配返回true
    }

    /**
     * 判斷國文成績是否學生都及格
     * @param gradeList 成績列表
     * @return Boolean
     */
    public Boolean isAllPassByStream(List<Grade> gradeList){
        return gradeList.stream()
                .filter(Objects::nonNull)
                .allMatch(grade -> grade.getChineseScore() >= 60); //全部匹配返回true
    }

    /**
     * 只獲取學生資料(不包含成績)
     * @param gradeList 成績列表
     * @return 學生資料列表
     */
    public List<GradeDTO> getStuInfo(List<Grade> gradeList){
        return gradeList.stream()
                .filter(Objects::nonNull)
                .map( item -> {
                    GradeDTO gradeDTO = new GradeDTO();
                    BeanUtils.copyProperties(item, gradeDTO);
                    return gradeDTO;
                }).collect(Collectors.toList());
    }


}
