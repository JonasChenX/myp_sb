package com.jonas.myp_sb.example.threadDemo.factory;

public class ProducerThread extends Thread{
    //共享商品
    private Product p;

    public ProducerThread(Product p) {
        this.p = p;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++){
            p.setProduct("A","巧克力");
        }
    }


}
