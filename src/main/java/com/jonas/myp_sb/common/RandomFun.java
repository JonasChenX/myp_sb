package com.jonas.myp_sb.common;

import java.util.Random;

public class RandomFun {

    /**
     * 生成隨機數
     * @param start 起始值
     * @param end 終止值
     * @return 範圍內的隨機數
     */
    public static int generateRandomNumber(int start, int end) {
        // 驗證起始值和終止值是否合法
        if (start >= end) {
            throw new IllegalArgumentException("起始值必須小於終止值");
        }

        // 創建一個Random對象
        Random random = new Random();

        // 生成起始到終止之間的隨機數（包括起始值，不包括終止值）
        return random.nextInt(end - start + 1) + start;
    }
}
