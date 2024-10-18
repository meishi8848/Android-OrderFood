package com.example.orderfood.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.db.DBUntil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 对食物的增删改查
 */
public class FoodDao {
    public static SQLiteDatabase db= DBUntil.con;

    public static List<FoodBean> queryAllFoods() {
        List<FoodBean> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * From d_food",null);
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id_food=cursor.getString(cursor.getColumnIndex("s_food_id"));
            @SuppressLint("Range") String id_boss=cursor.getString(cursor.getColumnIndex("s_boss_id"));
            @SuppressLint("Range") String name_food=cursor.getString(cursor.getColumnIndex("s_food_name"));
            @SuppressLint("Range") String des_food=cursor.getString(cursor.getColumnIndex("s_food_describe"));
            @SuppressLint("Range") String price_food=cursor.getString(cursor.getColumnIndex("s_food_price"));
            @SuppressLint("Range") String img_food=cursor.getString(cursor.getColumnIndex("s_food_img"));

            FoodBean foodBean=new FoodBean();
            foodBean.setFoodId(id_food);
            foodBean.setBossId(id_boss);
            foodBean.setFoodName(name_food);
            foodBean.setFoodDes(des_food);
            foodBean.setFoodPrice(price_food);
            foodBean.setFoodImg(img_food);
            list.add(foodBean);
        }

        return list;
    }

    public static List<FoodBean> queryFoodsBytitleAndBossId(String boss_id , String title) {
        String titleL="%"+title+"%";
        String data[]={boss_id,titleL};
        List<FoodBean> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * From d_food WHERE s_boss_id=? AND s_food_name like ?",data);
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id_food=cursor.getString(cursor.getColumnIndex("s_food_id"));
            @SuppressLint("Range") String id_boss=cursor.getString(cursor.getColumnIndex("s_boss_id"));
            @SuppressLint("Range") String name_food=cursor.getString(cursor.getColumnIndex("s_food_name"));
            @SuppressLint("Range") String des_food=cursor.getString(cursor.getColumnIndex("s_food_describe"));
            @SuppressLint("Range") String price_food=cursor.getString(cursor.getColumnIndex("s_food_price"));
            @SuppressLint("Range") String img_food=cursor.getString(cursor.getColumnIndex("s_food_img"));

            FoodBean foodBean=new FoodBean();
            foodBean.setFoodId(id_food);
            foodBean.setBossId(id_boss);
            foodBean.setFoodName(name_food);
            foodBean.setFoodDes(des_food);
            foodBean.setFoodPrice(price_food);
            foodBean.setFoodImg(img_food);
            list.add(foodBean);
        }

        return list;
    }

    public static FoodBean queryFoodById(String foodId) {
        FoodBean foodBean=new FoodBean();
        Cursor cursor=db.rawQuery("select * From d_food where s_food_id=?",new String[]{foodId});
        if(cursor.moveToNext()){
            @SuppressLint("Range") String id_food=cursor.getString(cursor.getColumnIndex("s_food_id"));
            @SuppressLint("Range") String id_boss=cursor.getString(cursor.getColumnIndex("s_boss_id"));
            @SuppressLint("Range") String name_food=cursor.getString(cursor.getColumnIndex("s_food_name"));
            @SuppressLint("Range") String des_food=cursor.getString(cursor.getColumnIndex("s_food_describe"));
            @SuppressLint("Range") String price_food=cursor.getString(cursor.getColumnIndex("s_food_price"));
            @SuppressLint("Range") String img_food=cursor.getString(cursor.getColumnIndex("s_food_img"));

            foodBean.setFoodId(id_food);
            foodBean.setBossId(id_boss);
            foodBean.setFoodName(name_food);
            foodBean.setFoodDes(des_food);
            foodBean.setFoodPrice(price_food);
            foodBean.setFoodImg(img_food);
        }else{
            return null;
        }

        return foodBean;
    }

    /**
     * 获取当前月的销售数量
     * @param Foodid
     * @return
     */
    public static int getMouthSaleNum(String Foodid){

        Cursor rs=db.rawQuery("SELECT * FROM d_order " +
                "WHERE strftime('%Y-%m', s_order_time) = strftime('%Y-%m','now');",null);
        List<String> list=new ArrayList<>();
        while(rs.moveToNext())
        {
            @SuppressLint("Range") String temp=rs.getString(rs.getColumnIndex("s_order_detail_id"));
            list.add(temp);
        }
        int saleNum=0;
        for(String s:list)
        {
            saleNum=saleNum+getOrderDetailByOrderIdAndFoodId(s,Foodid);
        }
        return saleNum;
    }

    /**
     * 通过订单id获取商品详情
     * @param orderId
     * @param foodId
     * @return
     */
    public static int getOrderDetailByOrderIdAndFoodId(String orderId,String foodId){

        String data[]={orderId,foodId};
        Cursor rs=db.rawQuery("SELECT * FROM d_order_detail " +
                "WHERE s_order_detail_id=? AND s_food_id=?;",data);
        List<String> list=new ArrayList<>();
        while(rs.moveToNext())
        {
            @SuppressLint("Range") int tm=rs.getInt(rs.getColumnIndex("s_food_num"));
            return tm;
        }
        return 0;
    }

    /**
     * 实现添加商品
     * @param s_boss_id
     * @param s_food_name
     * @param s_food_price
     * @param s_food_des
     * @param s_food_img
     * @return
     */
    public static boolean addFood(String s_boss_id,String s_food_name,String s_food_des,String s_food_price,String s_food_img){
        String s_food_id= UUID.randomUUID().toString().replace("-","");
        String data[]={s_food_id,s_boss_id,s_food_name,s_food_des,s_food_price,s_food_img};
        try{
            db.execSQL("insert into d_food(s_food_id,s_boss_id,s_food_name,s_food_describe,s_food_price,s_food_img)"+" values(?,?,?,?,?,?)",
                    data);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 实现更改食物
     * @param s_food_id
     * @param s_food_name
     * @param s_food_des
     * @param s_food_price
     * @param s_food_img
     * @return
     */
    public static boolean updateFood(String s_food_id,String s_food_name,String s_food_des,String s_food_price,String s_food_img){
        String data[]={s_food_name,s_food_des,s_food_price,s_food_img,s_food_id};
        try{
            db.execSQL("update d_food set s_food_name=?, s_food_describe=?, s_food_price=?, s_food_img=? WHERE s_food_id=?",
                    data);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 实现删除食物
     * @param s_food_id
     * @return
     */
    public static boolean delFoodById(String s_food_id){
        String data[]={s_food_id};
        try{
            db.execSQL("DELETE FROM d_food WHERE s_food_id=?",
                    data);
            return true;
        }catch (Exception e){
            return false;
        }
    }

//    public long insertFood(String foodId, String bossId, String foodName, String foodDescribe, String foodPrice, String foodImg) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_ID, foodId);
//        contentValues.put(COLUMN_BOSS_ID, bossId);
//        contentValues.put(COLUMN_FOOD_NAME, foodName);
//        contentValues.put(COLUMN_FOOD_DESCRIBE, foodDescribe);
//        contentValues.put(COLUMN_FOOD_PRICE, foodPrice);
//        contentValues.put(COLUMN_FOOD_IMG, foodImg);
//        return db.insert(TABLE_NAME, null, contentValues);
//    }
//
//
//
//    public int updateFood(String foodId, String bossId, String foodName, String foodDescribe, String foodPrice, String foodImg) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_BOSS_ID, bossId);
//        contentValues.put(COLUMN_FOOD_NAME, foodName);
//        contentValues.put(COLUMN_FOOD_DESCRIBE, foodDescribe);
//        contentValues.put(COLUMN_FOOD_PRICE, foodPrice);
//        contentValues.put(COLUMN_FOOD_IMG, foodImg);
//        return db.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{foodId});
//    }
//
//    public int deleteFood(String foodId) {
//        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{foodId});
//    }

}