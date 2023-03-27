package com.jonas.myp_sb.example.streamList;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestStreamList {

    private static List<Grade> gradeList;

    static {
        Grade gradeJonas = new Grade("1", "Jonas", "101", 90.0, 89.0, 74.3,"籃球,棒球");
        Grade gradeAlbee = new Grade("2", "Albee", "102", 70.0, 55.9, 95.0,"讀書");
        Grade gradeCindy = new Grade("3", "Cindy", "101", 80.3, 68.0, 76.6,"籃球,棒球");
        Grade gradeKa = new Grade("4", "Ka", "103", 100.0, 77.0, 89.7,"跳舞");
        Grade gradePo = new Grade("5", "Po", "103", 60.0, 99.0, 47.2,"讀書,射飛鏢");
        gradeList = Arrays.asList(gradeJonas, gradeAlbee, gradeCindy, gradeKa, gradePo);
    }

    @Test
    public void TestFilterByStream(){
        StreamList streamList = new StreamList();
        final List<Grade> grades = streamList.filterByStream(gradeList);
        System.out.println(grades);
    }

    @Test
    public void TestSortByStream(){
        StreamList streamList = new StreamList();
        final List<Grade> grades = streamList.sortByStream(gradeList);
        System.out.println(grades);
    }

    @Test
    public void TestGroupByStream(){
        StreamList streamList = new StreamList();
        final Map<String, List<Grade>> stringListMap = streamList.groupByStream(gradeList);
        System.out.println(stringListMap);
    }

    @Test
    public void TestAvgByStream(){
        StreamList streamList = new StreamList();
        Double avgGrade = streamList.avgByStream(gradeList);
        System.out.println(avgGrade);
    }

    @Test
    public void TestGetClassByStream(){
        StreamList streamList = new StreamList();
        List<String> classList = streamList.getClassByStream(gradeList);
        System.out.println(classList);
    }

    @Test
    public void TestGetScoreByStream(){
        StreamList streamList = new StreamList();
        List<Grade> scoreList = streamList.getScoreByStream(gradeList);
        System.out.println(scoreList);
    }

    @Test
    public void TestGetInterestByStream(){
        StreamList streamList = new StreamList();
        List<String> interestList = streamList.getInterestByStream(gradeList);
        System.out.println(interestList);
    }

    @Test
    public void TestIsNameByStream(){
        StreamList streamList = new StreamList();
        Boolean isName = streamList.isNameByStream(gradeList, "Jonas");
        System.out.println(isName);
    }

    @Test
    public void TestAllPassByStream(){
        StreamList streamList = new StreamList();
        Boolean isName = streamList.isAllPassByStream(gradeList);
        System.out.println(isName);
    }

    @Test
    public void TestGetStuInfo(){
        StreamList streamList = new StreamList();
        List<GradeDTO> stuInfo = streamList.getStuInfo(gradeList);
        System.out.println(stuInfo);
    }

}
