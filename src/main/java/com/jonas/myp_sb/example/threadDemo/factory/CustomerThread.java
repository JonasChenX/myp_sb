package com.jonas.myp_sb.example.threadDemo.factory;

public class CustomerThread extends Thread{
    private Product p;

    public CustomerThread(Product p) {
        this.p = p;
    }

    @Override
    public void run() {
        synchronized (p){
            for (int i = 0; i <= 10; i++) {
                p.getProduct();
            }
        }
    }
}
