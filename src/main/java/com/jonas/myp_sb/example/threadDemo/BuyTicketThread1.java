package com.jonas.myp_sb.example.threadDemo;

public class BuyTicketThread1 extends Thread {

    public BuyTicketThread1(String name) {
        super(name);
    }

    //一共10張票
    static int ticketNum = 10; //加static為 多個物件共享

    //每一個窗口都是一個多線程物件，每個物件執行的代碼放入run方法中
    @Override
    public void run() {
        //每個窗口後面有100個人在搶票
        for (int i = 1; i <= 100; i++){
            //對票數進行判斷，票數大於0才能購買
            if(ticketNum > 0){
                System.out.println("我在" + this.getName() + "買到第" + ticketNum-- + "張車票");
            }
        }

    }
}
