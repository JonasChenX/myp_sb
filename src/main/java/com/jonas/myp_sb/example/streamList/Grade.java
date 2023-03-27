package com.jonas.myp_sb.example.streamList;

public class Grade {

    //學號
    private String stuNo;

    //姓名
    private String stuName;

    //班級
    private String stuClass;

    //國文成績
    private double chineseScore;

    //數學成績
    private double mathScore;

    //英文成績
    private double englishScore;

    //興趣
    private String interest;

    public Grade(String stuNo, String stuName, String stuClass, double chineseScore, double mathScore, double englishScore, String interest) {
        this.stuNo = stuNo;
        this.stuName = stuName;
        this.stuClass = stuClass;
        this.chineseScore = chineseScore;
        this.mathScore = mathScore;
        this.englishScore = englishScore;
        this.interest = interest;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuClass() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }

    public double getChineseScore() {
        return chineseScore;
    }

    public void setChineseScore(double chineseScore) {
        this.chineseScore = chineseScore;
    }

    public double getMathScore() {
        return mathScore;
    }

    public void setMathScore(double mathScore) {
        this.mathScore = mathScore;
    }

    public double getEnglishScore() {
        return englishScore;
    }

    public void setEnglishScore(double englishScore) {
        this.englishScore = englishScore;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "stuNo='" + stuNo + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuClass='" + stuClass + '\'' +
                ", chineseScore=" + chineseScore +
                ", mathScore=" + mathScore +
                ", englishScore=" + englishScore +
                ", interest='" + interest + '\'' +
                '}' + '\n';
    }
}
