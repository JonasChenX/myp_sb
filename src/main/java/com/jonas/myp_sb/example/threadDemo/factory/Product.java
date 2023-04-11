package com.jonas.myp_sb.example.threadDemo.factory;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    //品牌
    private String brand;

    //商品名稱
    private String name;

    //判斷是否有商品
    private boolean flag = false; //預設為false 讓生產者先生產，再讓消費者消費

    //生產商品
    public synchronized void setProduct(String brand, String name){
        if(flag == true){
            //生產者不生產商品，等消費者消費
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.setBrand(brand);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.setName(name);
        System.out.println("生產者生產=> " + this.getBrand() + "-" + this.getName());

        //生產完後 為true
        flag = true;
        //告訴消費者來消費
        notify();
    }

    //消費商品
    public synchronized void getProduct(){
        if(!flag){ //flag == false 沒有商品，等待生產者生產
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 有商品消費
        System.out.println("消費者消費了=> "+ this.getBrand() + "-" + this.getName());

        //消費完為false
        flag = false;
        //告訴生產者可以生產了
        notify();
    }
}
