package com.example.orderfood.Bean;

import java.util.List;

//订单详情Bean
public class OrderDetailBean {

    public OrderDetailBean() {
    }

    @Override
    public String toString() {
        return "OrderDetailBean{" +
                "order_detail_id='" + order_detail_id + '\'' +
                ", food_id='" + food_id + '\'' +
                ", food_name='" + food_name + '\'' +
                ", food_des='" + food_des + '\'' +
                ", food_price='" + food_price + '\'' +
                ", food_num='" + food_num + '\'' +
                ", food_img='" + food_img + '\'' +
                '}';
    }

    public String getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(String order_detail_id) {
        this.order_detail_id = order_detail_id;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_des() {
        return food_des;
    }

    public void setFood_des(String food_des) {
        this.food_des = food_des;
    }

    public String getFood_price() {
        return food_price;
    }

    public void setFood_price(String food_price) {
        this.food_price = food_price;
    }

    public String getFood_num() {
        return food_num;
    }

    public void setFood_num(String food_num) {
        this.food_num = food_num;
    }

    public String getFood_img() {
        return food_img;
    }

    public void setFood_img(String food_img) {
        this.food_img = food_img;
    }

    public OrderDetailBean(String order_detail_id, String food_id, String food_name, String food_des, String food_price, String food_num, String food_img) {
        this.order_detail_id = order_detail_id;
        this.food_id = food_id;
        this.food_name = food_name;
        this.food_des = food_des;
        this.food_price = food_price;
        this.food_num = food_num;
        this.food_img = food_img;
    }

    private String order_detail_id;
    private String food_id;
    private String food_name;
    private String food_des;
    private String food_price;
    private String food_num;
    private String food_img;

}
