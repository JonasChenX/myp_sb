package com.jonas.myp_sb.example.threadDemo;


import com.jonas.myp_sb.example.threadDemo.factory.CustomerThread;
import com.jonas.myp_sb.example.threadDemo.factory.ProducerThread;
import com.jonas.myp_sb.example.threadDemo.factory.Product;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.FutureTask;

public class TestThread {

    /**
     * 方式一 繼承Thread類
     * */
    @Test
    public void BuyTicketThread1(){
        BuyTicketThread1 t1 = new BuyTicketThread1("窗口一");
        BuyTicketThread1 t2 = new BuyTicketThread1("窗口二");
        BuyTicketThread1 t3 = new BuyTicketThread1("窗口三");

        t1.start();
        t2.start();
        t3.start();
    }

    /**
     * 方式二 實現Runnable接口
     * */
    @Test
    public void BuyTicketThread2(){
        BuyTicketThread2 t = new BuyTicketThread2();
        Thread t1 = new Thread(t, "窗口一");
        Thread t2 = new Thread(t, "窗口二");
        Thread t3 = new Thread(t, "窗口三");

        t1.start();
        t2.start();
        t3.start();
    }

    /**
     * 方式三 實現Callable接口
     * 1.有返回值跟可以跑出異常
     * 2.不帶泛型->回傳Object 、 帶泛型->回傳相對應的類型
     * */
    @Test
    public void BuyTicketThread3(){
        BuyTicketThread3 t = new BuyTicketThread3();
        Thread t1 = new Thread(new FutureTask(t), "窗口一");
        Thread t2 = new Thread(new FutureTask(t), "窗口二");
        Thread t3 = new Thread(new FutureTask(t), "窗口三");

        t1.start();
        t2.start();
        t3.start();
    }

    /**
     * Priority
     * 設置優先級別
     * */
    @Test
    public void BuyTicketThread_Priority(){
        BuyTicketThread1 t1 = new BuyTicketThread1("窗口一");
        t1.setPriority(Thread.MAX_PRIORITY);
//        t1.setPriority(10); //優先級最高為10

        BuyTicketThread1 t2 = new BuyTicketThread1("窗口二");
        t2.setPriority(Thread.MIN_PRIORITY);
//        t2.setPriority(1); //優先級最低為1

        t1.start();
        t2.start();
    }

    /**
     * Join
     * 原來正執行的執行緒（或程式碼）會先暫停，先執行join的子線程
     * */
    @Test
    public void BuyTicketThread_join() throws InterruptedException {
        BuyTicketThread3 t = new BuyTicketThread3();
        for (int i = 1; i <= 10; i++){
            if(i == 6){
                Thread t1 = new Thread(new FutureTask(t), "子線程");
                t1.start();
                t1.join();
            }
            System.out.println("main線程---"+i);
        }
    }

    /**
     * sleep
     * 人為的製造阻塞事件
     * */
    @Test
    public void ShowTime_sleep(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        while (true){
            //獲取當前時間
            Date d = new Date();
            System.out.println(df.format(d));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 多線程同步交替
     * */
    @Test
    public void FactoryDemo(){
        //共享商品
        Product product = new Product();
        ProducerThread pt = new ProducerThread(product);
        CustomerThread ct = new CustomerThread(product);
        pt.start();
        ct.start();
    }

}
