package com.jonas.myp_sb.example.programming;

import java.util.ArrayList;
import java.util.List;

public class Batch {

    /**
     若陣列元素過多的情況下
     能用批次的方式做處理
     */
    public static void main(String[] args) {
        List<Integer> dataList = initializeList(880);

        // 每次批次的量
        int BATCH_SIZE = 100;

        // 處理每個批次
        for (int i = 0; i < dataList.size(); i += BATCH_SIZE) {
            int endIndex = Math.min(i + BATCH_SIZE, dataList.size());

            // 執行處理的邏輯，這裡示範了一個簡單的印出批次的邏輯
            processBatch(dataList.subList(i, endIndex));
        }
    }

    private static List<Integer> initializeList(int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(i + 1);
        }
        return list;
    }

    private static void processBatch(List<Integer> batch) {
        // 在這裡執行對每個批次的處理邏輯
        System.out.println("Processing batch of size: " + batch.size());
        for (int value : batch) {
            // 在這裡處理單個元素
            System.out.print(value + " ");
        }
        System.out.println(); // 換行
    }
}
