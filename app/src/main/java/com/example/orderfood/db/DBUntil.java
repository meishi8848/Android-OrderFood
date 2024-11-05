package com.example.orderfood.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.orderfood.R;
import com.example.orderfood.activity.user.RegisterUserActivity;
import com.example.orderfood.until.FileImgUntil;

/**
 * 链接数据库
 */
public class DBUntil extends SQLiteOpenHelper {//ALT＋回车

    private static final int version=25;//版本号，每次更改表结构都需要加1，否则不生效
    private static final String databaseName="db_takeaway.db";//数据库名称必须以db结尾
    private Context context;
    public static SQLiteDatabase con;//链接数据库的链接
    public DBUntil(Context context) {
        super(context, databaseName, null, version, null);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Drawable defaultDrawable= ContextCompat.getDrawable(context, R.drawable.upimg);//获取默认头像
        String path= FileImgUntil.getImgName();//获取图片的存储路径
        Bitmap bitmapDef = ((BitmapDrawable) defaultDrawable).getBitmap();

        FileImgUntil.saveBitmapAsync(bitmapDef,path);//保存图片

        //创建数据库
        db.execSQL("PRAGMA foreign_keys = false");

        db.execSQL("drop table if exists d_business");//如果这表存在则删除
        //商家id，密码，店名，描述，类型
        db.execSQL("create table d_business(s_id varchar(20) primary key," +
                "s_pwd varchar(20)," +
                "s_name varchar(20)," +
                "s_describe varchar(200)," +
                "s_type varchar(20)," +
                "s_img varchar(255))");//存储图片路径
        db.execSQL("insert into d_business(s_id,s_pwd,s_name,s_describe,s_type,s_img)"+" values(?,?,?,?,?,?)",
                new Object[]{"root","1234","火爆全网的家常菜","可送上门的美味","餐馆",path});

        db.execSQL("drop table if exists d_user");//如果这表存在则删除
        //用户id，密码，昵称，性别，地址，联系电话
        db.execSQL("create table d_user(s_id varchar(20) primary key," +
                "s_pwd varchar(20)," +
                "s_name varchar(20)," +
                "s_sex varchar(20)," +
                "s_address varchar(200)," +
                "s_phone varchar(20)," +
                "s_img varchar(255))");//存储图片路径
        db.execSQL("insert into d_user(s_id,s_pwd,s_name,s_sex,s_address,s_phone,s_img)"+" values(?,?,?,?,?,?,?)",
                new Object[]{"admin","1234","匿名学生","男","野兽邸","1919810",path});

        db.execSQL("drop table if exists d_food");//如果这表存在则删除
        //食物id，店铺id，食物名称，食物描述，食物价格，食物图片
        db.execSQL("create table d_food(s_food_id varchar(20) primary key," +
                "s_boss_id varchar(20)," +
                "s_food_name varchar(20)," +
                "s_food_describe varchar(200)," +
                "s_food_price varchar(20)," +
                "s_food_img varchar(255))");//存储图片路径
        String FoodImg=FileImgUntil.getImgName();//获取食物的图片名称
        FileImgUntil.saveSystemImgToPath(context,R.drawable.mlt,FoodImg);
        db.execSQL("insert into d_food(s_food_id,s_boss_id,s_food_name,s_food_describe,s_food_price,s_food_img)"+" values(?,?,?,?,?,?)",
                new Object[]{"1","root","东北麻辣烫","不吃后悔的麻辣烫","19.19",FoodImg});

        String FoodImg1=FileImgUntil.getImgName();//获取食物的图片名称
        FileImgUntil.saveSystemImgToPath(context,R.drawable.klm,FoodImg1);
        db.execSQL("insert into d_food(s_food_id,s_boss_id,s_food_name,s_food_describe,s_food_price,s_food_img)"+" values(?,?,?,?,?,?)",
                new Object[]{"2","root","东北烤冷面","不吃后悔的烤冷面","19.49",FoodImg1});

        String FoodImg2=FileImgUntil.getImgName();//获取食物的图片名称
        FileImgUntil.saveSystemImgToPath(context,R.drawable.dumpling,FoodImg2);
        db.execSQL("insert into d_food(s_food_id,s_boss_id,s_food_name,s_food_describe,s_food_price,s_food_img)"+" values(?,?,?,?,?,?)",
                new Object[]{"3","root","饺子","家的味道","8.88",FoodImg2});

        db.execSQL("drop table if exists d_order");//如果这表存在则删除
        //订单编号，订单时间，购买人id，商家id，订单状态，收货地址，商品详情id
        db.execSQL("create table d_order(s_order_id varchar(20) primary key," +
                "s_order_time varchar(20)," +
                "s_customer_id varchar(20)," +
                "s_boss_id varchar(20)," +
                "s_order_state varchar(20)," +//订单有三种状态 1未处理订单 2取消订单 3完成的订单
                "s_order_address varchar(200)," +
                "s_order_detail_id varchar(20))");//存储图片路径
        db.execSQL("insert into d_order(s_order_id,s_order_time,s_customer_id,s_boss_id,s_order_state,s_order_address,s_order_detail_id)"+" values(?,?,?,?,?,?,?)",
                new Object[]{"1","2024-10-14 13:10:00","admin","root","1","田同学-集美大道199号-13299908712","1"});

        db.execSQL("insert into d_order(s_order_id,s_order_time,s_customer_id,s_boss_id,s_order_state,s_order_address,s_order_detail_id)"+" values(?,?,?,?,?,?,?)",
                new Object[]{"2","2024-10-16 13:10:00","admin","root","1","*修院-集美大道199号-13191911451","2"});

        db.execSQL("insert into d_order(s_order_id,s_order_time,s_customer_id,s_boss_id,s_order_state,s_order_address,s_order_detail_id)"+" values(?,?,?,?,?,?,?)",
                new Object[]{"3","2024-10-16 13:10:00","admin","root","1","德同学-集美大道199号-14541996728","3"});

        db.execSQL("drop table if exists d_order_detail");//如果这表存在则删除
        db.execSQL("create table d_order_detail(s_order_detail_id varchar(20)," +
                "s_food_id varchar(20)," +
                "s_food_name varchar(20)," +
                "s_food_describe varchar(200)," +
                "s_food_price varchar(20)," +
                "s_food_num varchar(20)," +
                "s_food_img varchar(255))");//存储图片路径
        db.execSQL("insert into d_order_detail(s_order_detail_id,s_food_id,s_food_name,s_food_describe,s_food_price,s_food_num,s_food_img)"+" values(?,?,?,?,?,?,?)",
                new Object[]{"1","1","东北麻辣烫","不吃后悔的麻辣烫","19.19","5",FoodImg});

        db.execSQL("insert into d_order_detail(s_order_detail_id,s_food_id,s_food_name,s_food_describe,s_food_price,s_food_num,s_food_img)"+" values(?,?,?,?,?,?,?)",
                new Object[]{"1","2","东北烤冷面","不吃后悔的麻辣烫","19.49","10",FoodImg1});

        db.execSQL("insert into d_order_detail(s_order_detail_id,s_food_id,s_food_name,s_food_describe,s_food_price,s_food_num,s_food_img)"+" values(?,?,?,?,?,?,?)",
                new Object[]{"2","2","东北烤冷面","不吃后悔的麻辣烫","19.49","15",FoodImg1});

        db.execSQL("insert into d_order_detail(s_order_detail_id,s_food_id,s_food_name,s_food_describe,s_food_price,s_food_num,s_food_img)"+" values(?,?,?,?,?,?,?)",
                new Object[]{"3","1","东北麻辣烫","不吃后悔的麻辣烫","19.49","10",FoodImg});

        db.execSQL("drop table if exists d_comment");//如果这表存在则删除
        //评论id，评论顾客id，评论商家id，评论内容，评论时间，评论满意度，评论图片
        db.execSQL("create table d_comment(s_comment_id varchar(20) primary key," +
                "s_comment_customer_id varchar(20)," +
                "s_comment_boss_id varchar(20)," +
                "s_comment_content varchar(200)," +
                "s_comment_time varchar(20)," +
                "s_comment_score varchar(20)," +
                "s_comment_img varchar(255))");//存储图片路径
        db.execSQL("insert into d_comment(s_comment_id,s_comment_customer_id,s_comment_boss_id,s_comment_content,s_comment_time,s_comment_score,s_comment_img)"+" values(?,?,?,?,?,?,?)",
                new Object[]{"1","admin","root","非常的新鲜，非常的美味","2042-01-09 11:45","5",FoodImg1});

        db.execSQL("insert into d_comment(s_comment_id,s_comment_customer_id,s_comment_boss_id,s_comment_content,s_comment_time,s_comment_score,s_comment_img)"+" values(?,?,?,?,?,?,?)",
                new Object[]{"2","admin","root","这次点的东西比较一般","2042-01-14 11:45","3",FoodImg});

        db.execSQL("drop table if exists d_receiveAddress");//如果这表存在则删除
        //地址id，顾客id，收货人姓名，收货地址，联系电话
        db.execSQL("create table d_receiveAddress(s_address_id varchar(20) primary key," +
                "s_customer_id varchar(20)," +
                "s_customer_name varchar(20)," +
                "s_customer_address varchar(200)," +
                "s_customer_phone varchar(20))");//存储图片路径
        db.execSQL("insert into d_receiveAddress(s_address_id,s_customer_id,s_customer_name,s_customer_address,s_customer_phone)"+" values(?,?,?,?,?)",
                new Object[]{"home","admin","李同学","上海市，某会员餐厅附近","19111459191"});

        db.execSQL("PRAGMA foreign_keys = true");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
