package com.example.orderfood.Bean;

import java.io.Serializable;

public class FoodBean implements Serializable {

    //ALT+ins


    public FoodBean() {
    }

    public FoodBean(String foodId, String foodImg, String bossId, String foodName, String foodDes, String foodPrice) {
        this.foodId = foodId;
        this.foodImg = foodImg;
        this.bossId = bossId;
        this.foodName = foodName;
        this.foodDes = foodDes;
        this.foodPrice = foodPrice;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getBossId() {
        return bossId;
    }

    public void setBossId(String bossId) {
        this.bossId = bossId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDes() {
        return foodDes;
    }

    public void setFoodDes(String foodDes) {
        this.foodDes = foodDes;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(String foodImg) {
        this.foodImg = foodImg;
    }

    @Override
    public String toString() {
        return "FoodBean{" +
                "foodId='" + foodId + '\'' +
                ", bossId='" + bossId + '\'' +
                ", foodName='" + foodName + '\'' +
                ", foodDes='" + foodDes + '\'' +
                ", foodPrice='" + foodPrice + '\'' +
                ", foodImg='" + foodImg + '\'' +
                '}';
    }

    private String foodId;
    private String bossId;
    private String foodName;
    private String foodDes;
    private String foodPrice;
    private String foodImg;



}
