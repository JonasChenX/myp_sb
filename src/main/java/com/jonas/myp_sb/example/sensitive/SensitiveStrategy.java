package com.jonas.myp_sb.example.sensitive;

import lombok.Getter;

import java.util.function.Function;

@Getter
public enum SensitiveStrategy {

    //匹配除了第一個和最後一個字元以外的所有字元
    USERNAME(s -> s.replaceAll("(?<=.).(?=.)","*")),

    //匹配字串的開頭字符和結尾3個字符，而中間的部分用 .* 匹配，表示長度不限
    //用 $1 代表第一個匹配到的開頭字符，用 **** 代表中間匹配到的部分，最後用 $2 代表最後匹配到的結尾3個字符
    ID_CARD(s -> s.replaceAll("^(.).*(...)$","$1****$2")),

    //尋找長度至少為7的字串，將其中在第5到倒數第4個位置之間的任何一個數字都用星號替換
    PHONE(s -> s.replaceAll("(?<=\\d{4})\\d(?=\\d{3})","*")),

    ADDRESS(s -> s.replaceAll("(\\S{3})\\S{2}(\\S*)\\S{2}","$1****$2****")),

    //第四個字元開始到@會變成*字號
    EMAIL(s -> s.replaceAll("(?<=.{3}).(?=[^@]*?@)","*"))
    ;

    private final Function<String, String> desensitizer;

    SensitiveStrategy(Function<String, String> desensitizer) {
        this.desensitizer = desensitizer;
    }
}
