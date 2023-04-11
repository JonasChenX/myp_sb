package com.jonas.myp_sb.example.threadDemo;

import java.util.concurrent.Callable;

public class BuyTicketThread3 implements Callable<String> {

    int ticketNum = 10;

    @Override
    public String call(){
        String result = "";
        try {
            //每個窗口後面有100個人在搶票
            for (int i = 1; i <= 100; i++){
                //對票數進行判斷，票數大於0才能購買
                if(ticketNum > 0){
                    result = "我在" + Thread.currentThread().getName() + "買到第" + ticketNum-- + "張車票";
                    System.out.println(result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
